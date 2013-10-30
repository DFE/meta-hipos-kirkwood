require recipes-bsp/u-boot/u-boot_2013.07.bb

COMPATIBLE_MACHINE = "hikirk"


SRC_URI_append_hikirk = " \
          file://kwbimage_hikirk_533ddr3_nand.cfg \
	      file://kwbimage_hikirk_533ddr3_uart.cfg \
	      file://kwbimage_hikirk_533ddr3_sata.cfg \
	      file://kwbimage_hikirk_533ddr3_spi.cfg \
	      file://kwbimage_hikirk_500ddr3_uart.cfg \
	      file://kwbimage_hikirk_500ddr3_sata.cfg \
	      file://kwbimage_hikirk_500ddr3_spi.cfg \
	      file://hikirk-board-support.patch \
          file://speed_up_spi.patch \
"

do_compile_append_hikirk () {
	for kwcfg in ${WORKDIR}/kwbimage_hikirk_*.cfg
	do
		KW_BOOT_TYPE=${kwcfg##*kwbimage_hikirk_}
		KW_BOOT_TYPE=${KW_BOOT_TYPE%.cfg}
		${S}/tools/mkimage -n ${kwcfg} -T kwbimage -a 0x00600000 -e 0x00600000 -d ${UBOOT_BINARY} u-boot_hikirk_${KW_BOOT_TYPE}.bin
	done
}

do_install_append_hikirk () {
	install ${S}/u-boot_hikirk_*.bin ${D}/boot/
}

do_deploy_append_hikirk () {
	rm -f ${DEPLOYDIR}/u-boot_hikirk_*.bin
	install ${S}/u-boot_hikirk_*.bin ${DEPLOYDIR}/
}

