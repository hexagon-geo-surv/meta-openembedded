SUMMARY = "A library for loose coupling of C++ method calls"
SECTION = "libs"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=959bffe2993816eb32ec4bc1ec1d5875"

DEPENDS = "mm-common"

SRC_URI = "${GNOME_MIRROR}/libsigc++/3.6/libsigc++-${PV}.tar.xz"
SRC_URI[sha256sum] = "c3d23b37dfd6e39f2e09f091b77b1541fbfa17c4f0b6bf5c89baef7229080e17"

UPSTREAM_CHECK_URI = "https://download.gnome.org/sources/libsigc++/3.6/"
UPSTREAM_CHECK_REGEX = "(?P<pver>\d+(\.\d+)+)"

S = "${UNPACKDIR}/libsigc++-${PV}"

inherit setuptools3 meson

FILES:${PN}-dev += "${libdir}/sigc++-*/"
FILES:${PN}-doc += "${datadir}/devhelp"

BBCLASSEXTEND = "native nativesdk"
SECURITY_CFLAGS = "${SECURITY_NO_PIE_CFLAGS}"
