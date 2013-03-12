require hydraip-image.inc

PR_append = ".8"

export IMAGE_BASENAME = "hydraip-image"

IMAGE_FSTYPES = "tar.bz2 squashfs"

addtask package_write after do_rootfs before do_build

