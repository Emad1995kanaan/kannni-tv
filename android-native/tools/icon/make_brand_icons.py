#!/usr/bin/env python3
"""Génère les icônes Kanaani (lanceur, adaptatif, monochrome, bannière TV) depuis le logo PNG transparent.
Usage : python3 make_brand_icons.py <logo.png> <dossier res>"""
import os
import sys

from PIL import Image, ImageDraw, ImageFilter

SRC, RES = sys.argv[1], sys.argv[2]
NAVY = (6, 12, 34)          # fond bleu nuit
GLOW = (20, 70, 200)        # halo bleu (reprend le bleu du logo)

logo = Image.open(SRC).convert("RGBA")
logo = logo.crop(logo.getchannel("A").getbbox())


def fit(img, w, h):
    """Redimensionne en conservant les proportions pour tenir dans w×h."""
    r = min(w / img.width, h / img.height)
    return img.resize((max(1, int(img.width * r)), max(1, int(img.height * r))), Image.LANCZOS)


def backdrop(w, h):
    """Fond bleu nuit avec un halo bleu radial au centre."""
    bg = Image.new("RGBA", (w, h), NAVY + (255,))
    halo = Image.new("L", (w, h), 0)
    ImageDraw.Draw(halo).ellipse([w * 0.15, h * 0.1, w * 0.85, h * 0.9], fill=150)
    halo = halo.filter(ImageFilter.GaussianBlur(min(w, h) * 0.18))
    bg.paste(Image.new("RGBA", (w, h), GLOW + (255,)), (0, 0), halo)
    return bg


def centered(canvas, img):
    canvas.alpha_composite(img, ((canvas.width - img.width) // 2, (canvas.height - img.height) // 2))
    return canvas


# Icônes de repli (carrées arrondies / rondes) : logo sur 86 % de la largeur.
for dpi, px in {"mdpi": 48, "hdpi": 72, "xhdpi": 96, "xxhdpi": 144, "xxxhdpi": 192}.items():
    n = px * 4
    for name, round_ in (("ic_launcher", False), ("ic_launcher_round", True)):
        canvas = centered(backdrop(n, n), fit(logo, int(n * (0.80 if round_ else 0.86)), int(n * 0.8)))
        mask = Image.new("L", (n, n), 0)
        d = ImageDraw.Draw(mask)
        if round_:
            d.ellipse([0, 0, n - 1, n - 1], fill=255)
        else:
            d.rounded_rectangle([0, 0, n - 1, n - 1], radius=int(n * 0.22), fill=255)
        out = Image.new("RGBA", (n, n), (0, 0, 0, 0))
        out.paste(canvas, (0, 0), mask)
        out.resize((px, px), Image.LANCZOS).save(os.path.join(RES, f"mipmap-{dpi}", f"{name}.png"), optimize=True)

# Icône adaptative : premier plan 108 dp (432 px en xxxhdpi), logo dans la zone sûre (≈ 66 dp → 264 px).
os.makedirs(os.path.join(RES, "drawable-xxxhdpi"), exist_ok=True)
fg = Image.new("RGBA", (432, 432), (0, 0, 0, 0))
centered(fg, fit(logo, 290, 264)).save(os.path.join(RES, "drawable-xxxhdpi", "ic_launcher_foreground.png"), optimize=True)
bgad = backdrop(432, 432)
bgad.save(os.path.join(RES, "drawable-xxxhdpi", "ic_launcher_background_img.png"), optimize=True)

# Monochrome (icônes thématiques Android 13+) : silhouette blanche du logo.
alpha = fg.getchannel("A")
mono = Image.new("RGBA", fg.size, (255, 255, 255, 0))
mono.putalpha(alpha)
mono.save(os.path.join(RES, "drawable-xxxhdpi", "ic_launcher_monochrome.png"), optimize=True)

# Bannière Android TV 320×180 dp en xhdpi (640×360 px).
ban = centered(backdrop(640, 360), fit(logo, 520, 300))
ban.convert("RGB").save(os.path.join(RES, "drawable-xhdpi", "banner.png"), optimize=True)

os.makedirs(os.path.join(RES, "drawable-nodpi"), exist_ok=True)
print("ok")

# Pastille carrée utilisée dans l'interface (barre latérale, assistant, synchro) à la place de LogoMark.
n = 512
canvas = centered(backdrop(n, n), fit(logo, int(n * 0.88), int(n * 0.8)))
mask = Image.new("L", (n, n), 0)
ImageDraw.Draw(mask).rounded_rectangle([0, 0, n - 1, n - 1], radius=int(n * 0.22), fill=255)
mark = Image.new("RGBA", (n, n), (0, 0, 0, 0))
mark.paste(canvas, (0, 0), mask)
mark.save(os.path.join(RES, "drawable-nodpi", "brand_mark.png"), optimize=True)
print("mark ok")
