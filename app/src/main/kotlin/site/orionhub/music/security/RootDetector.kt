package site.orionhub.music.security

import android.content.Context
import java.io.File

object RootDetector {
    private val suBinaryPaths = arrayOf(
        "/sbin/su", "/system/bin/su", "/system/xbin/su",
        "/data/local/xbin/su", "/data/local/bin/su",
        "/system/sd/xbin/su", "/system/bin/failsafe/su",
        "/data/local/su", "/su/bin/su",
    )

    private val rootIndicatorPackages = arrayOf(
        "com.noshufou.android.su", "eu.chainfire.supersu",
        "com.koushikdutta.superuser", "com.topjohnwu.magisk",
        "com.kingroot.kinguser", "com.kingo.root",
    )

    fun detect(context: Context): Boolean {
        return hasSuBinary() || hasRootPackages(context) || hasTestKeys() || hasRwSystem()
    }

    private fun hasSuBinary(): Boolean =
        suBinaryPaths.any { File(it).exists() }

    private fun hasRootPackages(context: Context): Boolean {
        val pm = context.packageManager
        return rootIndicatorPackages.any { pkg ->
            runCatching { pm.getPackageInfo(pkg, 0) }.isSuccess
        }
    }

    private fun hasTestKeys(): Boolean =
        android.os.Build.TAGS?.contains("test-keys") == true

    private fun hasRwSystem(): Boolean = runCatching {
        val process = Runtime.getRuntime().exec("mount")
        val output = process.inputStream.bufferedReader().readText()
        process.waitFor()
        output.contains("/system") && output.contains("rw,")
    }.getOrDefault(false)
}
