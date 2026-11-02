"""Find every English source literal that needs a Hebrew counterpart, and every language switch site.

Usage: python3 scan.py <kotlin src root> <out.json>
"""
import json
import os
import re
import sys

sys.path.insert(0, os.path.dirname(__file__))
import kt  # noqa: E402

ROOT = sys.argv[1] if len(sys.argv) > 1 else ""
WHEN_RE = re.compile(r"when\s*\(\s*(lang|resolved|l|app|this\.lang)\s*\)\s*\{")
T_RE = re.compile(r"(?<![A-Za-z0-9_.])t\(")
ELSE_RE = re.compile(r"else\s*->")
INSTANCES = {  # file -> (english instance declaration regex)
    "i18n/Strings.kt": r"private val EN = Strings\(",
    "ui/mobile/MobileStrings.kt": r"val EN = MobileStrings\(",
}


def top_level_positions(s, a, b):
    """Positions in s[a:b] that are at bracket depth 0 and outside strings/comments."""
    j = a
    while j < b:
        c = s[j]
        if c == '"':
            j = kt.skip_string(s, j)
            continue
        if c == "'":
            j = s.index("'", j + 2 if s[j + 1] == "\\" else j + 1) + 1
            continue
        if s.startswith("//", j) or s.startswith("/*", j):
            j = kt.skip_comment(s, j)
            continue
        if c in "({[":
            j = kt.match(s, j) + 1
            continue
        yield j
        j += 1


def find_else(s, a, b):
    for p in top_level_positions(s, a, b):
        m = ELSE_RE.match(s, p)
        if m and (p == 0 or not kt.IDENT.match(s[p - 1])):
            return p, m.end()
    return None


def scan_file(rel):
    s = open(os.path.join(ROOT, rel), encoding="utf-8").read()
    lit_spans = list(kt.literals(s))
    inside = lambda pos: any(a < pos < b for a, b in lit_spans)  # noqa: E731
    out = {"t_calls": [], "whens": [], "manual": [], "instance": None}

    # 1) t(en, fr, es, ar) calls
    defines_t = re.search(r"fun (?:DesignStrings\.)?t\(", s)
    if defines_t:
        for m in T_RE.finditer(s):
            if inside(m.start()) or s[max(0, m.start() - 4):m.start()] == "fun ":
                continue
            open_ = m.end() - 1
            close = kt.match(s, open_)
            args = kt.split_top(s, open_ + 1, close)
            first = s[args[0][0]:args[0][1]].strip()
            if any(s[x:y].strip().startswith("he =") for x, y in args):
                continue
            out["t_calls"].append({"pos": open_, "en": first if kt.is_single_literal(first) else None})

    # 2) when (lang) { ... } blocks mentioning Arabic
    for m in WHEN_RE.finditer(s):
        if inside(m.start()):
            continue
        ob = m.end() - 1
        cb = kt.match(s, ob)
        body = s[ob + 1:cb]
        if "AppLang.Arabic" not in body or "AppLang.Hebrew" in body:
            continue
        line = s.count("\n", 0, m.start()) + 1
        e = find_else(s, ob + 1, cb)
        if not e:
            out["manual"].append({"line": line, "why": "no else", "text": s[m.start():cb + 1][:300]})
            continue
        expr = s[e[1]:cb].strip()
        if re.fullmatch(r"[A-Za-z_][A-Za-z0-9_.]*", expr):
            out["manual"].append({"line": line, "why": f"picker else={expr}", "text": s[m.start():cb + 1][:300]})
            continue
        lits = [s[a:b] for a, b in kt.literals(s, e[1], cb)]
        out["whens"].append({"line": line, "else": expr, "literals": lits})

    # 3) full English instance
    if rel in INSTANCES:
        m = re.search(INSTANCES[rel], s)
        ob = m.end() - 1
        cb = kt.match(s, ob)
        out["instance"] = [s[a:b] for a, b in kt.literals(s, ob, cb)]
    return out


def main():
    files = []
    for dp, _, fns in os.walk(ROOT):
        for fn in fns:
            if fn.endswith(".kt"):
                p = os.path.join(dp, fn)
                if "AppLang" in open(p, encoding="utf-8").read():
                    files.append(os.path.relpath(p, ROOT))

    report, literal_set = {}, []
    for rel in sorted(files):
        r = scan_file(rel)
        report[rel] = r
        for c in r["t_calls"]:
            if c["en"]:
                literal_set.append(c["en"])
        for w in r["whens"]:
            literal_set.extend(w["literals"])
        if r["instance"]:
            literal_set.extend(r["instance"])

    uniq = list(dict.fromkeys(literal_set))
    json.dump({"report": report, "literals": uniq}, open(sys.argv[2], "w", encoding="utf-8"), ensure_ascii=False, indent=1)
    for rel, r in report.items():
        nonlit = sum(1 for c in r["t_calls"] if not c["en"])
        print(f"{rel}: t={len(r['t_calls'])} (non-literal {nonlit}) whens={len(r['whens'])} manual={len(r['manual'])} inst={len(r['instance'] or [])}")
    print("unique literals:", len(uniq))


if __name__ == "__main__":
    main()
