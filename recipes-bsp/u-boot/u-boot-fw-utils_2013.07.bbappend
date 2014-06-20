FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append_hikirk +=  " file://fw_env.config \
	      file://hikirk-board-support.patch \
	    "

do_install_append_hikirk () {
	install -d ${D}/etc
	install -m 755 ${WORKDIR}/fw_env.config ${D}/etc/fw_env.config
}
