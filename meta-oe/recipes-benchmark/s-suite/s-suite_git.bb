SUMMARY = "Small collection of benchmarks for storage I/O"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=b529aaa6a0c50f15d29f89609b5c22f3"

SRCREV = "f97f1ae321d1fb8111a2c638075702ed2512ff07"
PV = "3.6"
SRC_URI = "git://github.com/Algodev-github/S.git;protocol=https;branch=master"

# Current PV is not a git tag but a Readme content, track commits to detect
# upstream updates
UPSTREAM_CHECK_COMMITS = "1"


# installing in /opt/S-suite since the package has
# dependencies to the directory structure.
do_install() {
    install -d ${D}/opt/S-suite
    for i in $(find ${S}/* -type d); do
        install -d ${D}/opt/S-suite/$(basename $i)
        install -m0755 -p ${S}/$(basename $i)/* ${D}/opt/S-suite/$(basename $i)
    done

    install -m0755 ${S}/def_config.sh ${D}/opt/S-suite
    install -m0755 ${S}/config_params.sh ${D}/opt/S-suite
    install -m0755 ${S}/create_config.sh ${D}/opt/S-suite
    install -m0755 ${S}/process_config.sh ${D}/opt/S-suite
}

RDEPENDS:${PN} = "bash bc coreutils gawk g++ gcc fio libaio libaio-dev sysstat \
		  git"

FILES:${PN} = "/opt/S-suite/"

# added to INSANE_SKIP since s-suite have an runtime
# dependency (RDEPENDS) on libaio-dev.
INSANE_SKIP:${PN} += "dev-deps"
