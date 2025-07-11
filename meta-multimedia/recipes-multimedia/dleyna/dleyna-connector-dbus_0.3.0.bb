SUMMARY = "D-Bus connector for dLeyna libraries"
HOMEPAGE = "https://01.org/dleyna/"

LICENSE = "LGPL-2.1-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=4fbd65380cdd255951079008b364516c \
                    file://src/connector-dbus.c;endline=21;md5=0a1695cef53beefc36651de439f643b5"

DEPENDS = "glib-2.0 dbus dleyna-core"

SRC_URI = "git://github.com/01org/${BPN}.git;branch=master;protocol=https"
SRCREV = "de913c35e5c936e2d40ddbd276ee902cd802bd3a"

inherit autotools pkgconfig

FILES:${PN} += "${libdir}/dleyna-1.0/connectors/*.so"
FILES:${PN}-dev += "${libdir}/dleyna-1.0/connectors/*.la"
FILES:${PN}-dbg += "${libdir}/dleyna-1.0/connectors/.debug/*.so"

SKIP_RECIPE[dleyna-connector-dbus] ?= "Upstream is dead moreover needs porting to work with latest gupnp >= 1.2"
