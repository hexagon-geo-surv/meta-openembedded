SUMMARY = "Xfce screensaver Application"
DESCRIPTION = "Xfce screensaver is a screen saver and locker that aims to have simple, sane, secure defaults and be well integrated with the desktop."
HOMEPAGE = "https://git.xfce.org/apps/xfce4-screensaver/about/"
SECTION = "x11/application"

LICENSE = "GPL-2.0-or-later & LGPL-2.0-or-later "
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://COPYING.LGPL;md5=4fbd65380cdd255951079008b364516c \
                    "

DEPENDS = "dbus-glib garcon gtk+3 libxklavier libxscrnsaver virtual/libx11 xfconf libwnck3"

inherit xfce-app

SRC_URI:append = " file://fix-cross-compile.patch"
SRC_URI[sha256sum] = "cf717d032d2d0555978c479299da992af6dc3363ae7e758af9515c7166eac170"

do_install:append() {
    install -D -m 0644 ${S}/data/xfce4-screensaver.common-auth ${D}${sysconfdir}/pam.d/xfce4-screensaver
}

FILES:${PN} += "${datadir}/dbus-1 ${datadir}/desktop-directories"

RDEPENDS:${PN} += "python3-core"
