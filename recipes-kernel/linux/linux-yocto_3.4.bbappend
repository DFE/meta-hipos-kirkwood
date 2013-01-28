# Use latest yocto kernel

COMPATIBLE_MACHINE = "hidav-kirkwood"

DEPENDS += " lzop-native test-harness-native "
RDEPENDS += " mtd-utils gawk busybox bootconfig "

MACHINE_KERNEL_PR = "r5"
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI +=  " file://defconfig \
	      file://xor-min-byte-count.patch \
	    "

