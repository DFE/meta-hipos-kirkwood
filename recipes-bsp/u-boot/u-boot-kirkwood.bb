require recipes-bsp/u-boot/u-boot_2013.07.bb

COMPATIBLE_MACHINE = "hikirk"

PR = "r5"

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
	UBOOT_VERSION=`basename ${UBOOT_IMAGE} .bin`
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
			${S}/tools/mkimage -n ${CNF_FILE} -T kwbimage -a 0x00600000 -e 0x00600000 -d ${UBOOT_BINARY} ${UBOOT_VERSION}_${KW_BOOT_DATA}_${KW_BOOT_HDR}.bin
		done
	done
}

python do_headerhikirk () {
    import glob

    workdir = d.getVar('WORKDIR', True)
    srcdir = d.getVar('S', True)
    ubootbin = d.getVar('UBOOT_IMAGE', True)[:-4]

    def calc_checksum(buf):
        sum = 0
        for byte in buf:
            sum = ((sum + byte) & 0xFF)
        return sum

    def set_header(hdr, src, identifier):
        hdr[0] = (identifier & 0xFF)
        hdr[0xC] = (src & 0xFF)
        hdr[0xD] = ((src>>8)  & 0xFF)
        hdr[0xE] = ((src>>16) & 0xFF)
        hdr[0xF] = ((src>>24) & 0xFF)
        hdr[0x1F] = calc_checksum(hdr[:31])
        return hdr

    with open(ubootbin + "_500ddr3_sata.bin", mode='rb+') as fp:
        hdr = bytearray(fp.read(512))
        img = fp.read()

        bb.note("Create spi header with offset 0x010000")
        hdr = set_header(hdr, 0x010000, 0x5a)
        with open("u-boot_hikirk_500ddr3_spi_64k.hdr", 'wb') as f:
            f.write(hdr)
        bb.note("Create spi header with offset 0x0F0000")
        hdr = set_header(hdr, 0x0F0000, 0x5a)
        with open("u-boot_hikirk_500ddr3_spi_960k.hdr", 'wb') as f:
            f.write(hdr)
        bb.note("Create image without header")
        with open(ubootbin + "_500ddr3.bin", 'wb') as f:
            f.write(img)

        bb.note("Set sata image header source offset to 2")
        hdr = set_header(hdr, 0x02, 0x78)
        fp.seek(0)
        fp.write(hdr[:32])
}

addtask headerhikirk after do_compile before do_install

do_install_append_hikirk () {
	install ${S}/u-boot-hikirk-*.bin ${D}/boot/
	install ${S}/u-boot_hikirk_*.hdr ${D}/boot/
}

do_deploy_append_hikirk () {
	rm -f ${DEPLOYDIR}/u-boot-hikirk-*.bin
	rm -f ${DEPLOYDIR}/u-boot_hikirk_*.hdr
	install ${S}/u-boot-hikirk-*.bin ${DEPLOYDIR}/
	install ${S}/u-boot_hikirk_*.hdr ${DEPLOYDIR}/
}

