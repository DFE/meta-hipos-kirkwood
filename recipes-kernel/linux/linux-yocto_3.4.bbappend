# Use latest yocto kernel

COMPATIBLE_MACHINE = "hidav-kirkwood"

DEPENDS += " lzop-native test-harness-native "
RDEPENDS_${PN} += " mtd-utils gawk busybox bootconfig "

MACHINE_KERNEL_PR = "r14"
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI +=  " file://defconfig \
	      file://xor-min-byte-count.patch \
              file://spi-enable.patch \
	      file://PLX-Tech-3380-driver.patch \
	    "

do_configure_prepend() {
	# The defconfig has been copied by oe-framework. Since last (2013-03)
	# oe layer update, it is not coopied into the build directory. The 
	# cause is unknown. As long as it does not work, it is copied here.
	cp ${WORKDIR}/defconfig ${B}/.config
}

