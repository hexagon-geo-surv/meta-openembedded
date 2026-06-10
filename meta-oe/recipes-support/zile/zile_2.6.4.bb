SUMMARY = "Zile is lossy Emacs"
HOMEPAGE = "http://zile.sourceforge.net/"
DEPENDS = "ncurses bdwgc"

LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=1ebbd3e34237af26da5dc08a4e440464"

SRC_URI = "${GNU_MIRROR}/zile/${BP}.tar.gz \
           file://remove-help2man.patch \
"

SRC_URI[sha256sum] = "d5d44b85cb490643d0707e1a2186f3a32998c2f6eabaa9481479b65caeee57c0"

inherit autotools pkgconfig

do_install:append() {
    rm -rf ${D}${libdir}/charset.alias
    rmdir --ignore-fail-on-non-empty ${D}${libdir} || true
}

PACKAGECONFIG ??= ""
PACKAGECONFIG:append = " ${@bb.utils.filter('DISTRO_FEATURES', 'acl', d)}"

PACKAGECONFIG[acl] = "--enable-acl,--disable-acl,acl,"
