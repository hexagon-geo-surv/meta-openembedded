SUMMARY = "ssmping is a tool for checking whether one can receive SSM from a given host"
HOMEPAGE = "http://www.venaas.no/multicast/ssmping/"
SECTION = "net"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://asmping.c;beginline=2;endline=11;md5=1ca8d1a1ca931e5cfe604ebf20a78b71"

SRC_URI = "${DEBIAN_MIRROR}/main/s/${BPN}/${BPN}_${PV}.orig.tar.gz;downloadfilename=${BP}.tar.gz \
    file://0001-Makefile-tweak-install-dir.patch \
"
SRC_URI[sha256sum] = "22103a37eaa28489169a0927bc01e0596c3485fc4d29fc8456c07fd2c70fca6d"

CFLAGS += "-D_GNU_SOURCE "

do_install() {
    oe_runmake 'DESTDIR=${D}' 'PREFIX=${prefix}' install
}

# http://errors.yoctoproject.org/Errors/Details/766895/
# ssmping.c:55:51: error: passing argument 3 of 'getsockname' from incompatible pointer type [-Wincompatible-pointer-types]
CFLAGS += "-Wno-error=incompatible-pointer-types"
