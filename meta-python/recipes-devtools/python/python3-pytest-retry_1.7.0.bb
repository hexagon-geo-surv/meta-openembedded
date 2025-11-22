SUMMARY = "Adds the ability to retry flaky tests in CI environments"
HOMEPAGE = "https://github.com/str0zzapreti/pytest-retry"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c25bd3d1db774af27b10acd6f80bf939"
RECIPE_MAINTAINER = "Tom Geelen <t.f.g.geelen@gmail.com>"

SRC_URI[sha256sum] = "f8d52339f01e949df47c11ba9ee8d5b362f5824dff580d3870ec9ae0057df80f"

inherit pypi python_setuptools_build_meta ptest-python-pytest

RDEPENDS:${PN} += "\
    python3-pytest (>=7.0.0) \
"

PYPI_PACKAGE = "pytest_retry"
UPSTREAM_CHECK_PYPI_PACKAGE = "${PYPI_PACKAGE}"
