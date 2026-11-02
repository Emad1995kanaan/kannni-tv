"""Insert Hebrew everywhere Arabic is handled. Usage: python3 apply.py <kotlin src root> <he_map.json>"""
import json
import os
import re
import sys

sys.path.insert(0, os.path.dirname(__file__))
import kt  # noqa: E402
from scan import WHEN_RE, T_RE, INSTANCES, find_else  # noqa: E402

ROOT = sys.argv[1]
HE = json.load(open(sys.argv[2], encoding="utf-8"))
missing = []


def tr_literals(text):
    """Replace every string literal in text with its Hebrew counterpart."""
    out, last = [], 0
    for a, b in kt.literals(text):
        lit = text[a:b]
        out.append(text[last:a])
        if lit in HE:
            out.append(HE[lit])
        else:
            missing.append(lit)
            out.append(lit)
        last = b
    out.append(text[last:])
    return "".join(out)


def branch_insert(s, ob, cb, expr):
    """Edit inserting `AppLang.Hebrew -> expr` before the top-level else of the when body s[ob+1:cb]."""
    e = find_else(s, ob + 1, cb)
    body = s[ob + 1:cb]
    if "\n" in body.strip():
        line_start = s.rfind("\n", 0, e[0]) + 1
        indent = s[line_start:e[0]]
        return (e[0], 0, f"AppLang.Hebrew -> {expr}\n{indent}")
    return (e[0], 0, f"AppLang.Hebrew -> {expr}; ")


def process(rel):
    path = os.path.join(ROOT, rel)
    s = open(path, encoding="utf-8").read()
    if "AppLang.Hebrew" in s:
        print("skip (already done)", rel)
        return
    lit_spans = list(kt.literals(s))
    inside = lambda pos: any(a < pos < b for a, b in lit_spans)  # noqa: E731
    edits = []

    # t(...) helper definition: add `he` parameter.
    for m in re.finditer(r"fun (?:DesignStrings\.)?t\(", s):
        op = m.end() - 1
        cp = kt.match(s, op)
        params = s[op + 1:cp]
        assert params.strip().startswith("en: String"), (rel, params)
        edits.append((cp, 0, ", he: String = en"))

    # t(...) calls: append the Hebrew argument.
    if re.search(r"fun (?:DesignStrings\.)?t\(", s):
        for m in T_RE.finditer(s):
            if inside(m.start()) or s[max(0, m.start() - 4):m.start()] == "fun ":
                continue
            op = m.end() - 1
            cp = kt.match(s, op)
            args = kt.split_top(s, op + 1, cp)
            first = s[args[0][0]:args[0][1]].strip()
            he = HE.get(first)
            if he is None:
                missing.append(first)
                continue
            last = s[args[-1][0]:args[-1][1]]
            if last.strip() == "":  # trailing comma
                edits.append((args[-1][0], 0, f" he = {he},"))
            else:
                edits.append((args[-1][1], 0, f", he = {he}"))

    # when (lang) blocks.
    for m in WHEN_RE.finditer(s):
        if inside(m.start()):
            continue
        ob = m.end() - 1
        cb = kt.match(s, ob)
        body = s[ob + 1:cb]
        if "AppLang.Arabic" not in body:
            continue
        e = find_else(s, ob + 1, cb)
        expr = s[e[1]:cb].strip()
        if expr == "en":
            edits.append(branch_insert(s, ob, cb, "he"))
        elif expr == "EN":
            edits.append(branch_insert(s, ob, cb, "HE"))
        elif expr == "java.util.Locale.ENGLISH":
            edits.append(branch_insert(s, ob, cb, 'java.util.Locale.forLanguageTag("he")'))
        else:
            edits.append(branch_insert(s, ob, cb, tr_literals(expr)))

    # Full instance: clone EN as HE right after it.
    if rel in INSTANCES:
        m = re.search(INSTANCES[rel], s)
        ob = m.end() - 1
        cb = kt.match(s, ob)
        decl_start = m.start()
        block = s[decl_start:cb + 1]
        he_block = tr_literals(block).replace("val EN =", "val HE =", 1)
        line_start = s.rfind("\n", 0, decl_start) + 1
        indent = s[line_start:decl_start]
        edits.append((cb + 1, 0, f"\n\n{indent}{he_block}"))

    for pos, dl, ins in sorted(edits, key=lambda e: e[0], reverse=True):
        s = s[:pos] + ins + s[pos + dl:]
    open(path, "w", encoding="utf-8").write(s)
    print(f"{rel}: {len(edits)} edits")


targets = [
    "i18n/DesignStrings.kt", "i18n/DesignStringsB1.kt", "i18n/PersonalizationStrings.kt", "i18n/Strings.kt",
    "i18n/TraktStrings.kt", "ui/account/AccountScreen.kt", "ui/downloads/DownloadStrings.kt", "ui/live/LivePreview.kt",
    "ui/mobile/MobileStrings.kt", "ui/player/PlayerExtraStrings.kt", "ui/profile/ProfileStrings.kt",
    "ui/settings/CloudSyncStrings.kt", "ui/settings/DnsStrings.kt",
]
for rel in targets:
    process(rel)
print("missing translations:", len(missing), missing[:10])
