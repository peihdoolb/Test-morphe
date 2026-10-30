package com.zeldrisho.patches.zalo.shared

import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility

object Constants {
    const val TESTED_ZALO_VERSION_CODE = 260801903
    const val CURRENT_ZALO_VERSION_CODE = 261002903

    val COMPATIBILITY_ZALO = Compatibility(
        name = "Zalo",
        packageName = "com.zing.zalo",
        apkFileType = null,
        appIconColor = 0x0068FF,
        targets = listOf(
            AppTarget(
                version = "26.08.01",
                minSdk = 24,
            ),
            AppTarget(
                version = "26.10.02",
                minSdk = 24,
            ),
        ),
    )
}
