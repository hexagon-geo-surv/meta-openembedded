# Copyright (C) 2012 Khem Raj <raj.khem@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Linux kernel style configuration framework for other projects"
DESCRIPTION = "The kconfig-frontends project aims at centralising \
the effort of keeping an up-to-date, out-of-tree, packaging of the \
kconfig infrastructure, ready for use by third-party projects. \
The kconfig-frontends package provides the kconfig parser, as well as all \
the frontends"
HOMEPAGE = "https://gitlab.com/ymorin/kconfig-frontends"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=9b8cf60ff39767ff04b671fca8302408"
SECTION = "devel"
DEPENDS += "ncurses flex-native gperf-native bison-native"
RDEPENDS:${PN} += "python3 bash"
SRC_URI = "git://gitlab.com/ymorin/kconfig-frontends.git;protocol=https;branch=4.11.x \
	   file://0001-Makefile-ensure-frontends-exits-before-writing-into-.patch \
           file://0001-Switch-utils-kconfig-diff-to-use-Python-3.patch \
           file://0001-Avoid-using-hard-coded-usr-include-paths.patch"

SRCREV = "f22fce3a308be1c7790ebefc6bbedb33c5f7c86a"

# Upstream repo does not tag
UPSTREAM_CHECK_COMMITS = "1"


inherit autotools pkgconfig
do_configure:prepend () {
	mkdir -p ${S}/scripts/.autostuff/m4
}

do_install:append() {
	ln -s kconfig-conf ${D}${bindir}/conf
	ln -s kconfig-mconf ${D}${bindir}/mconf
}

EXTRA_OECONF += "--disable-gconf --disable-qconf"

# Some packages have the version preceeding the .so instead properly
# versioned .so.<version>, so we need to reorder and repackage.
SOLIBS = "-${@d.getVar('PV')[:-2]}.so"
FILES_SOLIBSDEV = "${libdir}/libkconfig-parser.so"

BBCLASSEXTEND = "native"
