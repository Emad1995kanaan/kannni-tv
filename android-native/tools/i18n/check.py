import json,re,sys
L=json.load(open('scan.json'))['literals']
he={}
for fn in ['he_0.tsv','he_1.tsv','he_2.tsv']:
    for ln in open(fn,encoding='utf-8').read().split('\n'):
        if not ln.strip(): continue
        i,t=ln.split('\t',1); he[int(i)]=t
missing=[i for i in range(len(L)) if i not in he]
print('total',len(L),'translated',len(he),'missing',missing[:20])
tok=re.compile(r'\$\{[^}]*\}|\$[A-Za-z_][A-Za-z0-9_]*|%\d*\\?\$?\d*[sd]|%\d*[sd]|%02d|\\[n"$\\]')
bad=0
for i,l in enumerate(L):
    src=l[1:-1]; t=he.get(i,'')
    a=sorted(tok.findall(src)); b=sorted(tok.findall(t))
    if a!=b: bad+=1; print('TOKENS',i,a,b)
    # template var glued to a letter (Kotlin would extend the identifier)
    for m in re.finditer(r'\$[A-Za-z_][A-Za-z0-9_]*',t):
        nxt=t[m.end():m.end()+1]
        if nxt and (nxt.isalnum() or nxt=='_'): bad+=1; print('GLUED',i,t)
    if re.search(r'(?<!\\)"',t): bad+=1; print('QUOTE',i,t)
print('problems',bad)
json.dump({L[i]:'"'+he[i]+'"' for i in range(len(L)) if i in he}, open('he_map.json','w',encoding='utf-8'),ensure_ascii=False,indent=0)
