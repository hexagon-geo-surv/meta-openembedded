SUMMARY = "Linux dynamic kernel patching infrastructure"
DESCRIPTION = "kpatch is a Linux dynamic kernel patching infrastructure which allows you to patch a running kernel without rebooting or restarting any processes."
LICENSE = "GPL-2.0-only & LGPL-2.0-only"
DEPENDS = "elfutils bash"

SRC_URI = "git://github.com/dynup/kpatch.git;protocol=https;branch=master \
	file://0001-kpatch-build-add-cross-compilation-support.patch \
	file://0002-kpatch-build-allow-overriding-of-distro-name.patch \
	"

EXTRA_OEMAKE = " \
	PREFIX=${prefix} \
	BINDIR=${D}${bindir} \
	SBINDIR=${D}${sbindir} \
	LIBDIR=${D}${libdir} \
	MANDIR=${D}${mandir}/man1 \
	SYSTEMDDIR=${D}${systemd_system_unitdir} \
	UPSTARTDIR=${D}${sysconfdir}/init \
	DESTDIR=${D} \
	ARCH=${TARGET_ARCH} \
	BUILDMOD=no \
	CC='${CC}' \
	"


do_install () {
	oe_runmake install
}

PACKAGES =+ "kpatch-build"
PROVIDES += "kpatch-build"

COMPATIBLE_HOST = "(x86_64).*-linux"
COMPATIBLE_HOST:libc-musl = "null"

RDEPENDS:${PN} = "bash binutils"
RDEPENDS:kpatch-build = "bash glibc-utils"

FILES:${PN} = " \
	${sbindir}/kpatch \
	${systemd_system_unitdir}/kpatch.service \
	${mandir}/man1/kpatch.1.gz \
	${sysconfdir}/init/kpatch.conf \
	"
FILES:kpatch-build = " \
	${bindir}/kpatch-build \
	${libexecdir}/* \
	${datadir}/kpatch \
	${mandir}/man1/kpatch-build.1.gz \
	"

SYSTEMD_SERVICE:${PN} = "kpatch.service"
