SUMMARY = "Fast property caching"
HOMEPAGE = "https://github.com/aio-libs/propcache"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

SRC_URI += "file://0001-Fix-build-with-cython-3.1.x.patch"
SRC_URI[sha256sum] = "40d980c33765359098837527e18eddefc9a24cea5b45e078a7f3bb5b032c6ecf"

inherit pypi python_setuptools_build_meta ptest-python-pytest cython

DEPENDS += " \
	python3-expandvars-native \
"

RDEPENDS:${PN}-ptest += " \
	python3-pytest-codspeed \
	python3-pytest-xdist \
	python3-rich \
	python3-statistics \
"

