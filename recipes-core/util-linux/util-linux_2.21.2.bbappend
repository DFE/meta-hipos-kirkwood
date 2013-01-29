
PR_append = "+r0"
COMPATIBLE_MACHINE = "hidav-kirkwood"
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI += " file://tcsetpgrp_hang_fix.patch \
	   "

