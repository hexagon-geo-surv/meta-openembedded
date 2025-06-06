SUMMARY = "Typer, build great CLIs. Easy to code. Based on Python type hints."
DESCRIPTION = "\
    Typer is a library for building CLI applications that users will love using and developers will love creating. Based on Python type hints. \
    It's also a command line tool to run scripts, automatically converting them to CLI applications. \
"
HOMEPAGE = "https://github.com/tiangolo/typer"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=173d405eb704b1499218013178722617"

SRC_URI[sha256sum] = "89507b104f9b6a0730354f27c39fae5b63ccd0c95b1ce1f1a6ba0cfd329997c3"

inherit pypi python_setuptools_build_meta ptest

DEPENDS += "\
    python3-pdm-backend-native \
    python3-pdm-native \
"

SRC_URI:append = " \
    file://run-ptest \
"

PYPI_PACKAGE = "typer"

RDEPENDS:${PN} += "\
    python3-click \
    python3-typing-extensions \
"
# python3-misc for webbrowser module
RDEPENDS:${PN}-ptest += "\
    python3-misc \
    python3-mypy \
    python3-pytest \
    python3-pytest-cov \
    python3-pytest-xdist \
    python3-rich \
    python3-shellingham \
    python3-toml \
    python3-unittest-automake-output \
"

do_install_ptest() {
    install -d ${D}${PTEST_PATH}/tests
    cp -rf ${S}/tests/* ${D}${PTEST_PATH}/tests/
}
