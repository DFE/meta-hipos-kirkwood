#@TYPE: Machine
#@DESCRIPTION: Machine configuration for various different Marvell Kirkwood based devices

require conf/machine/include/tune-arm926ejs.inc
TARGET_ARCH = "arm"


SERIAL_CONSOLE = "115200 ttyS0"

# Currently all the orion devices we support use uImage
KERNEL_IMAGETYPE = "uImage"
UBOOT_ENTRYPOINT = "0x00008000"
UBOOT_LOADADDRESS = "0x00008000"

