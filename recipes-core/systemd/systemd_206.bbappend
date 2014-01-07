# install hipos.rules for udev

PRINC := "${@int(PRINC) + 2}"
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_hikirk =  " file://hipos.rules "

do_install_append_hikirk () {
    install -m 0644 ${WORKDIR}/hipos.rules ${D}${sysconfdir}/udev/rules.d/hipos.rules
}
