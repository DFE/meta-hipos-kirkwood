require recipes-bsp/u-boot/u-boot_2012.04.01.bb

COMPATIBLE_MACHINE = "hidav-kirkwood"

SRC_URI +=  " file://kwbimage_hikirk_533ddr3_nand.cfg \
	      file://kwbimage_hikirk_533ddr3_uart.cfg \
	      file://kwbimage_hikirk_533ddr3_sata.cfg \
	      file://kwbimage_hikirk_533ddr3_spi.cfg \
	      file://hikirk-board-support.patch \
	    "

do_compile_append_hidav-kirkwood () {
	for kwcfg in ${WORKDIR}/kwbimage_hikirk_*.cfg
	do
		KW_BOOT_TYPE=${kwcfg##*kwbimage_hikirk_}
		KW_BOOT_TYPE=${KW_BOOT_TYPE%.cfg}
		${S}/tools/mkimage -n ${kwcfg} -T kwbimage -a 0x00600000 -e 0x00600000 -d ${UBOOT_BINARY} u-boot_hikirk_${KW_BOOT_TYPE}.bin
	done
}

do_install_append_hidav-kirkwood () {
	install ${S}/u-boot_hikirk_*.bin ${D}/boot/
}

do_deploy_append_hidav-kirkwood () {
	rm -f ${DEPLOYDIR}/u-boot_hikirk_*.bin
	install ${S}/u-boot_hikirk_*.bin ${DEPLOYDIR}/
}

