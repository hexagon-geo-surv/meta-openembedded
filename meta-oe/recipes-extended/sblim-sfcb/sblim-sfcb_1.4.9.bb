SUMMARY = "Small Footprint CIM Broker"
DESCRIPTION = "\
Small Footprint CIM Broker (sfcb) is a CIM server conforming to the CIM \
Operations over HTTP protocol. It is robust, with low resource consumption \
and therefore specifically suited for embedded and resource constrained \
environments. sfcb supports providers written against the Common \
Manageability Programming Interface (CMPI)."
HOMEPAGE = "http://www.sblim.org"
SECTION = "Applications/System"
LICENSE = "EPL-1.0"
LIC_FILES_CHKSUM = "file://COPYING;md5=f300afd598546add034364cd0a533261"
DEPENDS = "curl libpam openssl sblim-sfc-common unzip-native"

inherit features_check
REQUIRED_DISTRO_FEATURES = "pam"

SRC_URI = "http://downloads.sourceforge.net/sblim/${BP}.tar.bz2 \
           file://sfcb.service \
           file://sblim-sfcb-1.3.9-sfcbrepos-schema-location.patch \
           file://sblim-sfcb-1.3.15-fix-provider-debugging.patch \
           file://sblim-sfcb-1.3.16-maxMsgLen.patch \
           file://sblim-sfcb-1.4.5-service.patch \
           file://sblim-sfcb-1.3.16-multilib-man-cfg.patch \
           file://sblim-sfcb-1.4.8-default-ecdh-curve-name.patch \
           file://sblim-sfcb-1.4.9-fix-ftbfs.patch \
           file://0001-include-stdint.h-system-header-for-UINT16_MAX.patch \
           file://0001-Replace-need-for-error.h-when-it-does-not-exist.patch \
           file://sblim-sfcb-1.4.9-fix-sfcbinst2mof.patch \
           file://0001-Avoid-variable-definition-in-header-files.patch \
           file://0001-configure-Check-for-function-from-respective-library.patch \
           file://0001-include-missing-system-headers.patch \
"

SRC_URI[sha256sum] = "634a67b2f7ac3b386a79160eb44413d618e33e4e7fc74ae68b0240484af149dd"

CVE_STATUS[CVE-2012-3381] = "fixed-version: The CPE in the NVD database doesn't reflect correctly the vulnerable versions."

inherit autotools
inherit systemd

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "sblim-sfcb.service"
SYSTEMD_AUTO_ENABLE = "enable"

LDFLAGS:append = "${@bb.utils.contains('DISTRO_FEATURES', 'ld-is-lld', ' -Wl,--allow-shlib-undefined ', '', d)}"

CFLAGS += "-std=gnu17"

EXTRA_OECONF = '--enable-debug \
                --enable-ssl \
                --enable-pam \
                --enable-ipv6 \
                CFLAGS="${CFLAGS} -D_GNU_SOURCE"'

# make all with -j option is unsafe.
PARALLEL_MAKE = ""

INSANE_SKIP:${PN} = "dev-so"
CONFIG_SITE = "${WORKDIR}/config-site.${P}"

do_install() {
    cp -f ${S}/sfcb.cfg.pre.in ${S}/sfcb.cfg

    oe_runmake DESTDIR=${D} install

    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${UNPACKDIR}/sfcb.service ${D}${systemd_unitdir}/system/sblim-sfcb.service

    install -d ${D}${sysconfdir}/init.d
    mv ${D}${sysconfdir}/init.d/sfcb ${D}${sysconfdir}/init.d/sblim-sfcb
    sed -i -e 's/\/var\/lock\/subsys\/sfcb/\/var\/lock\/subsys\/sblim-sfcb/g' ${D}${sysconfdir}/init.d/sblim-sfcb

    rm -rf ${D}${libdir}/sfcb/*.la
}

pkg_postinst:${PN} () {
    $INTERCEPT_DIR/postinst_intercept delay_to_first_boot ${PKG} mlprefix=${MLPREFIX}
}

pkg_postinst_ontarget:${PN} () {
    ${datadir}/sfcb/genSslCert.sh ${sysconfdir}/sfcb
    ${bindir}/sfcbrepos -f
}

FILES:${PN} += "${libdir}/sfcb ${datadir}/sfcb"
FILES:${PN}-dbg += "${libdir}/sfcb/.debug"

RDEPENDS:${PN} = "perl bash"

# This one is reproducible only on 32bit MACHINEs
# http://errors.yoctoproject.org/Errors/Details/766970/
# sblim-sfcb-1.4.9/trace.c:214:18: error: passing argument 1 of 'gmtime_r' from incompatible pointer type [-Wincompatible-pointer-types]
CFLAGS += "-Wno-error=incompatible-pointer-types"
