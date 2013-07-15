# Use latest yocto kernel

COMPATIBLE_MACHINE = "hipos-kirkwood"

DEPENDS += " lzop-native test-harness-native "
RDEPENDS_${PN} += " mtd-utils gawk busybox bootconfig "

MACHINE_KERNEL_PR = "r16"
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI +=  " file://defconfig \
	      file://xor-min-byte-count.patch \
              file://spi-enable.patch \
	      file://PLX-Tech-3380-driver.patch \
	      file://mpp_host_hdd_bctrl.patch \
	      file://mac-address.patch \
	      file://hikirk_power_off.patch \
	    "

do_configure_prepend() {
	# The defconfig has been copied by oe-framework. Since last (2013-03)
	# oe layer update, it is not coopied into the build directory. The 
	# cause is unknown. As long as it does not work, it is copied here.
	cp ${WORKDIR}/defconfig ${B}/.config
}

