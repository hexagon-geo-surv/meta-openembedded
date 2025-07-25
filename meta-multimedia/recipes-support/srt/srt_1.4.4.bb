SUMMARY = "Secure Reliable Transport (SRT) Protocol"
DESCRIPTION = "Secure Reliable Transport (SRT) is an open source transport technology \
that optimizes streaming performance across unpredictable networks, such as the Internet."
SECTION = "libs"
HOMEPAGE = "https://github.com/Haivision/srt"
LICENSE = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=815ca599c9df247a0c7f619bab123dad"

SRCREV = "8b32f3734ff6af7cc7b0fef272591cb80a2d1aae"
SRC_URI = "git://github.com/Haivision/srt;protocol=https;branch=master \
           file://0001-don-t-install-srt-ffplay.patch \
           file://0002-allow-build-with-cmake-4.patch \
           "
UPSTREAM_CHECK_GITTAGREGEX = "v(?P<pver>\d+(\.\d+)+)"

inherit cmake pkgconfig

EXTRA_OECMAKE = "-DCMAKE_BUILD_TYPE=Release -DENABLE_UNITTESTS=OFF"

PACKAGECONFIG ??= "crypt"
PACKAGECONFIG[debug] = "-DENABLE_DEBUG=1,,"
PACKAGECONFIG[crypt] = "-DENABLE_ENCRYPTION=ON,-DENABLE_ENCRYPTION=OFF,openssl"
PACKAGECONFIG[utils] = "-DENABLE_APPS=ON,-DENABLE_APPS=OFF,"

PACKAGES += "${@bb.utils.contains('PACKAGECONFIG', 'utils', '${PN}-utils', '', d)}"
FILES:${PN}-utils += "${bindir}"
RDEPENDS:${PN}-utils += "${PN}"
