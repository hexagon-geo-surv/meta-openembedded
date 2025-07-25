SUMMARY = "display dialog boxes from shell scripts"
DESCRIPTION = "Dialog lets you to present a variety of questions \
or display messages using dialog boxes from a shell \
script (or any scripting language)."
HOMEPAGE = "http://invisible-island.net/dialog/"
SECTION = "console/utils"
DEPENDS = "ncurses"
LICENSE = "LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=a6f89e2100d9b6cdffcea4f398e37343"

SRC_URI = "https://invisible-mirror.net/archives/${BPN}/${BP}.tgz"
SRC_URI[sha256sum] = "68406329827b783d0a8959cc20a94c6e1791ac861a27f854e06e9020541816dd"

# hardcoded here for use in dialog-static recipe
S = "${UNPACKDIR}/dialog-${PV}"

inherit autotools-brokensep pkgconfig multilib_script

PACKAGECONFIG ??= "${@bb.utils.filter('DISTRO_FEATURES', 'x11', d)}"

PACKAGECONFIG[x11] = "--with-x --x-includes=${STAGING_INCDIR} --x-libraries=${STAGING_LIBDIR},--without-x,virtual/libx11"

EXTRA_OECONF = "--with-ncurses \
                --disable-rpath-hack"

MULTILIB_SCRIPTS = "${PN}:${bindir}/dialog"

do_configure() {
    gnu-configize --force
    sed -i 's,${cf_ncuconfig_root}6-config,${cf_ncuconfig_root}-config,g' -i configure
    sed -i 's,cf_have_ncuconfig=unknown,cf_have_ncuconfig=yes,g' -i configure
    oe_runconf
}
do_install:append () {
        ln -sf ${bindir}/${HOST_SYS}-dialog ${D}${bindir}/${BPN}
}
