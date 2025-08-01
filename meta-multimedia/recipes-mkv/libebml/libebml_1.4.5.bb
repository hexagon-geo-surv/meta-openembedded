SUMMARY = "C++ library to parse EBML files"
HOMEPAGE = "https://github.com/Matroska-Org/libebml"
LICENSE = "LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://LICENSE.LGPL;md5=4fbd65380cdd255951079008b364516c"

SRC_URI = "git://github.com/Matroska-Org/libebml.git;branch=v1.x;protocol=https \
        file://0001-allow-build-with-cmake-4.patch"

SRCREV = "1878e784321673561039a6a37076b2736f4dc98f"

inherit pkgconfig cmake dos2unix

EXTRA_OECMAKE = "-DBUILD_SHARED_LIBS=ON"

