"""Minimal Kotlin source scanner: string literals, bracket matching, top-level splitting."""
import re

IDENT = re.compile(r"[A-Za-z0-9_]")


def skip_string(s, i):
    """s[i] == '"'. Returns index just past the literal. Handles raw strings and ${...} templates."""
    if s.startswith('"""', i):
        j = s.index('"""', i + 3)
        while s.startswith('"', j + 3):  # trailing quotes belong to the literal
            j += 1
        return j + 3
    j = i + 1
    while j < len(s):
        c = s[j]
        if c == "\\":
            j += 2
            continue
        if c == '"':
            return j + 1
        if c == "$" and j + 1 < len(s) and s[j + 1] == "{":
            j = match(s, j + 1) + 1
            continue
        if c == "\n":
            raise ValueError(f"unterminated string at {i}")
        j += 1
    raise ValueError(f"unterminated string at {i}")


def skip_comment(s, i):
    if s.startswith("//", i):
        n = s.find("\n", i)
        return len(s) if n < 0 else n
    if s.startswith("/*", i):
        return s.index("*/", i + 2) + 2
    return i


def match(s, i):
    """s[i] is an opening bracket; returns index of its matching closer."""
    pairs = {"(": ")", "{": "}", "[": "]"}
    stack = [pairs[s[i]]]
    j = i + 1
    while j < len(s):
        c = s[j]
        if c == '"':
            j = skip_string(s, j)
            continue
        if c == "'":
            j = s.index("'", j + 2 if s[j + 1] == "\\" else j + 1) + 1
            continue
        if s.startswith("//", j) or s.startswith("/*", j):
            j = skip_comment(s, j)
            continue
        if c in pairs:
            stack.append(pairs[c])
        elif c in ")}]":
            if c != stack[-1]:
                raise ValueError(f"bracket mismatch at {j}: {s[max(0,j-80):j+20]!r}")
            stack.pop()
            if not stack:
                return j
        j += 1
    raise ValueError("unbalanced")


def literals(s, start=0, end=None):
    """Yield (a, b) spans of string literals in s[start:end] (not inside comments)."""
    end = len(s) if end is None else end
    j = start
    while j < end:
        c = s[j]
        if c == '"':
            k = skip_string(s, j)
            yield (j, k)
            j = k
            continue
        if c == "'":
            j = s.index("'", j + 2 if s[j + 1] == "\\" else j + 1) + 1
            continue
        if s.startswith("//", j) or s.startswith("/*", j):
            j = skip_comment(s, j)
            continue
        j += 1


def split_top(s, a, b, sep=","):
    """Split s[a:b] on top-level separators. Returns list of (start, end) spans."""
    parts, j, cur = [], a, a
    while j < b:
        c = s[j]
        if c == '"':
            j = skip_string(s, j)
            continue
        if c == "'":
            j = s.index("'", j + 2 if s[j + 1] == "\\" else j + 1) + 1
            continue
        if s.startswith("//", j) or s.startswith("/*", j):
            j = skip_comment(s, j)
            continue
        if c in "({[":
            j = match(s, j) + 1
            continue
        if s.startswith(sep, j):
            parts.append((cur, j))
            j += len(sep)
            cur = j
            continue
        j += 1
    parts.append((cur, b))
    return parts


def is_single_literal(text):
    t = text.strip()
    if not t.startswith('"'):
        return False
    try:
        return skip_string(t, 0) == len(t)
    except ValueError:
        return False
