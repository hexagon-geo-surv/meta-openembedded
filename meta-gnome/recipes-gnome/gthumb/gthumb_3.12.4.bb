SUMMARY = "Image viewer and browser"
LICENSE="GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=59530bdf33659b29e73d4adb9f9f6552"


REQUIRED_DISTRO_FEATURES = "polkit gobject-introspection-data"

DEPENDS = " \
    glib-2.0-native \
    bison-native \
    yelp-tools-native \
    gtk+3 \
    gsettings-desktop-schemas \
    zlib \
    jpeg \
    exiv2 \
    colord \
    lcms \
    libraw \
    librsvg \
    libsoup \
    json-glib \
    libsecret \
"

inherit features_check gnomebase gnome-help gsettings itstool mime-xdg

SRC_URI[archive.sha256sum] = "add693ac0aeb9a30d829ba03a06208289d3f6868dc3b02573549e88190c794e8"

FILES:${PN} += "${datadir}/metainfo"
