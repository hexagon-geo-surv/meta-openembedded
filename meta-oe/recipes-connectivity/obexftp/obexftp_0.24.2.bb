DESCRIPTION = "A tool for transfer files to/from any OBEX enabled device"
LICENSE = "GPL-2.0-only & PD & LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://LGPL-2.1.txt;md5=4fbd65380cdd255951079008b364516c \
                    file://GPL-2.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://License.txt;md5=fcbddc3c1debed80dd80da2d3e5f0dc1 \
                   "

DEPENDS += "openobex obexftp-native"
SRC_URI = "http://downloads.sourceforge.net/openobex/${BP}-Source.tar.gz \
           file://Remove_some_printf_in_obexftpd.patch \
           file://0001-apps-CMakeLists.txt-Explicitly-link-libbfb-and-libmu.patch \
           file://make_fuse_swig_optional.patch \
"
SRC_URI[sha256sum] = "d40fb48e0a0eea997b3e582774b29f793919a625d54b87182e31a3f3d1c989a3"

UPSTREAM_CHECK_URI = "https://sourceforge.net/projects/openobex/files/obexftp/"
UPSTREAM_CHECK_REGEX = "${BPN}/(?P<pver>\d+(\.\d+)+)"

inherit cmake pkgconfig

OECMAKE_GENERATOR = "Unix Makefiles"

PACKAGECONFIG ?= ""
# fuse support will need meta-filesystems layer
PACKAGECONFIG[fuse] = "-DENABLE_FUSE=ON,-DENABLE_FUSE=OFF,fuse"
PACKAGECONFIG[swig] = "-DENABLE_SWIG=ON,-DENABLE_SWIG=OFF,swig"

DEPENDS:remove:class-native = "fuse-native"

S = "${UNPACKDIR}/${BP}-Source"

EXTRA_OECMAKE += "-DCMAKE_SKIP_RPATH=ON \
                  -DENABLE_PERL=OFF -DENABLE_PYTHON=OFF \
                  -DENABLE_RUBY=OFF -DENABLE_TCL=OFF \
                  -DCMAKE_POLICY_VERSION_MINIMUM=3.5 \
"

do_compile:class-native () {
    oe_runmake crctable
}

do_install:class-native () {
    install -D -m 0755 ${B}/bfb/crctable ${D}${bindir}/crctable
}

BBCLASSEXTEND = "native"
