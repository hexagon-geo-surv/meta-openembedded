SUMMARY = "SpiderMonkey is Mozilla's JavaScript engine written in C/C++"
HOMEPAGE = "https://developer.mozilla.org/en-US/docs/Mozilla/Projects/SpiderMonkey"
LICENSE = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=dc9b6ecd19a14a54a628edaaf23733bf"

SRC_URI = "https://archive.mozilla.org/pub/firefox/releases/${PV}esr/source/firefox-${PV}esr.source.tar.xz \
           file://0001-Cargo.toml-do-not-abort-on-panic.patch \
           file://0002-moz.configure-do-not-look-for-llvm-objdump.patch \
           file://0003-rust.configure-do-not-try-to-find-a-suitable-upstrea.patch \
           file://0004-use-asm-sgidefs.h.patch \
           file://riscv32.patch \
           file://0001-util.configure-fix-one-occasionally-reproduced-confi.patch \
           file://0001-rewrite-cargo-host-linker-in-python3.patch  \
           file://musl-disable-stackwalk.patch \
           file://0001-add-arm-to-list-of-mozinline.patch \
           file://armv5.patch \
           file://0001-Link-with-icu-uc-to-fix-build-with-ICU-76.patch \
           file://riscv.patch \
           "
SRC_URI[sha256sum] = "25d633eb81499cbda44b8c64fa1c1a5879d55024b864ef495d4997154d68358f"

UPSTREAM_CHECK_URI = "https://tracker.debian.org/pkg/mozjs128"
UPSTREAM_CHECK_REGEX = "(?P<pver>\d+(\.\d+)+)"

S = "${UNPACKDIR}/firefox-${PV}"

inherit pkgconfig perlnative python3native rust cargo

DEPENDS += "zlib cbindgen-native python3 icu"
DEPENDS:remove:mipsarch = "icu"
DEPENDS:remove:powerpc:toolchain-clang = "icu"

B = "${WORKDIR}/build"

export PYTHONPATH = "${S}/build:\
${S}/third_party/python/PyYAML/lib3:\
${S}/testing/mozbase/mozfile:\
${S}/python/mozboot:\
${S}/third_party/python/distro:\
${S}/testing/mozbase/mozinfo:\
${S}/config:\
${S}/testing/mozbase/manifestparser:\
${S}/third_party/python/pytoml:\
${S}/testing/mozbase/mozprocess:\
${S}/third_party/python/six:\
${S}/python/mozbuild:\
${S}/python/mozbuild/mozbuild:\
${S}/python/mach:\
${S}/third_party/python/jsmin:\
${S}/python/mozversioncontrol"

export HOST_CC = "${BUILD_CC}"
export HOST_CXX = "${BUILD_CXX}"
export HOST_CFLAGS = "${BUILD_CFLAGS}"
export HOST_CPPFLAGS = "${BUILD_CPPFLAGS}"
export HOST_CXXFLAGS = "${BUILD_CXXFLAGS}"

export AS = "${CC}"

export MOZBUILD_STATE_PATH = "${WORKDIR}/mozbuild_state"
export RUSTFLAGS

JIT ?= ""
JIT:mipsarch = "--disable-jit"
ICU ?= "--with-system-icu"
ICU:mipsarch = ""
ICU:powerpc:toolchain-clang = ""

LDFLAGS:append:riscv32 = " -latomic"

do_configure() {
    cd ${B}
    python3 ${S}/configure.py \
        --enable-project=js \
        --target=${RUST_HOST_SYS} \
        --host=${BUILD_SYS} \
        --prefix=${prefix} \
        --libdir=${libdir} \
        --disable-jemalloc \
        --disable-strip \
        ${JIT} \
        ${ICU}
}
do_configure[cleandirs] += "${B}"

# The main build system is a Makefile that call cargo downstream.
# We inherit cargo to get the environnement but need to switch back to
# base_do_compile to do the Makefile base compilation.
do_compile() {
    base_do_compile
}

do_install() {
    oe_runmake 'DESTDIR=${D}' install
}

inherit multilib_script multilib_header

MAJ_VER = "${@oe.utils.trim_version("${PV}", 1)}"
MULTILIB_SCRIPTS += "${PN}-dev:${bindir}/js${MAJ_VER}-config"

do_install:append() {
    oe_multilib_header mozjs-${MAJ_VER}/js-config.h
    sed -e 's@${STAGING_DIR_HOST}@@g' \
        -i ${D}${bindir}/js${MAJ_VER}-config
    rm -f ${D}${libdir}/libjs_static.ajs
}

INSANE_SKIP += "32bit-time"
PACKAGE_DEBUG_SPLIT_STYLE = "debug-without-src"
PACKAGES =+ "lib${BPN}"
FILES:lib${BPN} += "${libdir}/lib*"
