SUMMARY = "JUCE's Projucer"
DESCRIPTION = "Projucer is used to build and generate support files and build infrastructure for all \
JUCE supported platforms, including Linux and Embedded Linux."
SECTION = "utils"
HOMEPAGE = "http://juce.com/"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=12e9c2db7897c0349a4cc1c6d2a8c5d5"

inherit pkgconfig features_check

REQUIRED_DISTRO_FEATURES = "x11"

DEPENDS = "libx11 libxext libxinerama libxrandr libxcursor freetype alsa-lib curl bzip2 libpng harfbuzz glib-2.0 libpcre"
DEPENDS_append_libc-musl = " libexecinfo"

SRCREV = "90e8da0cfb54ac593cdbed74c3d0c9b09bad3a9f"
BRANCH = "master"
SRC_URI = "git://github.com/WeAreROLI/JUCE.git;protocol=https;branch=${BRANCH} \
"

S = "${WORKDIR}/git"
PV = "6.0.8"

JUCE_PROJUCER_BUILD_PATH = "${B}/extras/Projucer/Builds"
JUCE_PROJUCER_MAKEFILE_PATH = "${JUCE_PROJUCER_BUILD_PATH}/LinuxMakefile"
JUCE_PROJUCER = "${JUCE_PROJUCER_MAKEFILE_PATH}/build/Projucer"

# export dummy TARGET_ARCH that doesn't do much,
# but disables "-march=native" optimization which does
# not work on cross builds
EXTRA_OEMAKE = "V=1 JUCE_ARCH_LABEL=${TARGET_ARCH} TARGET_ARCH='-g'"

LDFLAGS_append_mipsarcho32 = " -latomic"
LDFLAGS_append_powerpc = " -latomic"
LDFLAGS_append_riscv64 = " -latomic"
LDFLAGS_append_riscv32 = " -latomic"
LDFLAGS_append_armv5 = " -latomic"

do_configure() {
  cd ${JUCE_PROJUCER_MAKEFILE_PATH}
  CONFIG=Release oe_runmake clean
}

do_compile() {
  cd ${JUCE_PROJUCER_MAKEFILE_PATH}
  CONFIG=Release oe_runmake
}

do_install() {
  install -d ${D}${bindir}
  install -m 0755 ${JUCE_PROJUCER} ${D}${bindir}
}
# error: '_NL_IDENTIFICATION_LANGUAGE' was not declared in this scope
COMPATIBLE_HOST_libc-musl = "null"
