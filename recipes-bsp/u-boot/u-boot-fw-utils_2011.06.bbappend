PRINC := "${@int(PRINC) + 1}"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append_hidav-kirkwood +=  " file://fw_env.config \
	    "

do_install_append_hidav-kirkwood () {
	install -d ${D}/etc
	install -m 755 ${WORKDIR}/fw_env.config ${D}/etc/fw_env.config
}
