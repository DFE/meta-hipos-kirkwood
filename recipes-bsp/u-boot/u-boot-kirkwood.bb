require recipes-bsp/u-boot/u-boot_2013.07.bb

COMPATIBLE_MACHINE = "hikirk"

PR = "r3"

SRC_URI_append_hikirk = " \
	file://kwbimage_hikirk_533ddr3.data \
	file://kwbimage_hikirk_500ddr3.data \
	file://kwbimage_nand.hdr \
	file://kwbimage_uart.hdr \
	file://kwbimage_sata.hdr \
	file://kwbimage_spi.hdr \
	file://hikirk-board-support.patch \
	file://speed_up_spi.patch \
"

do_compile_append_hikirk () {
	for kwdata in ${WORKDIR}/kwbimage_hikirk_*.data
	do
		KW_BOOT_DATA=${kwdata##*kwbimage_hikirk_}
		KW_BOOT_DATA=${KW_BOOT_DATA%.data}
		for kwhdr in ${WORKDIR}/kwbimage_*.hdr
		do
			KW_BOOT_HDR=${kwhdr##*kwbimage_}
			KW_BOOT_HDR=${KW_BOOT_HDR%.hdr}
			CNF_FILE="${KW_BOOT_DATA}_${KW_BOOT_HDR}.cfg"
			cp ${kwhdr} ${CNF_FILE}
			cat ${kwdata} >> ${CNF_FILE}
			${S}/tools/mkimage -n ${CNF_FILE} -T kwbimage -a 0x00600000 -e 0x00600000 -d ${UBOOT_BINARY} u-boot_hikirk_${KW_BOOT_DATA}_${KW_BOOT_HDR}.bin
		done
	done
}

do_install_append_hikirk () {
	install ${S}/u-boot_hikirk_*.bin ${D}/boot/
}

do_deploy_append_hikirk () {
	rm -f ${DEPLOYDIR}/u-boot_hikirk_*.bin
	install ${S}/u-boot_hikirk_*.bin ${DEPLOYDIR}/
}

