# Use latest yocto kernel

COMPATIBLE_MACHINE = "hikirk"

DEPENDS += " lzop-native test-harness-native "
RDEPENDS_${PN} += " mtd-utils gawk busybox "

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-3.10:"

SRC_URI_append_hikirk =  " \
    file://hikirk-setup.patch \
    file://m25p80-noBP.patch \
    file://defconfig \
"

SRC_URI +=  " \
    file://PLX-Tech-3380-driver.patch \
    git://github.com/DFE/darmok.git;destsuffix=darmok;type=not-kmeta;tag="darmok_v0.10" \
"

# The parameter 'type' in a git-SRC_URI is a workaround. The error is in file
# openembedded-core/meta/classes/kernel-yocto.bbclass in function find_kernel_feature_dirs.
# If the parameter is missing, then an exception is thrown. Parameter 'type'
# should not have the value kmeta. Value kmeta is used to mark a source 
# directory as kernel feature directory.

do_configure_prepend() {
	# The defconfig has been copied by oe-framework. Since last (2013-03)
	# oe layer update, it is not copied into the build directory. The 
	# cause is unknown. As long as it does not work, it is copied here.
	cp ${WORKDIR}/defconfig ${B}/.config
}

do_patch_append() {
	mkdir -p ${S}/drivers/darmok
	cp ${WORKDIR}/darmok/drbcc-kmod/drbcc-kmod-sources/* ${S}/drivers/darmok
	patch -p1 < ${WORKDIR}/darmok/drbcc-kmod/drbcc-kmod-sources/linux-kirkwood_3.10.patch
}

