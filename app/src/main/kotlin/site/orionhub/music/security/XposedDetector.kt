package site.orionhub.music.security

object XposedDetector {
    private val targetClasses = arrayOf(
        "de.robv.android.xposed.XposedBridge",
        "de.robv.android.xposed.XposedHelpers",
        "io.github.libxposed.api.XposedInterface",
        "org.lsposed.lspd.core.Main",
    )

    fun detect(): Boolean =
        hasXposedClasses() || isInStackTrace()

    private fun hasXposedClasses(): Boolean = targetClasses.any { cls ->
        runCatching { Class.forName(cls) }.isSuccess
    }

    private fun isInStackTrace(): Boolean = runCatching {
        Thread.currentThread().stackTrace.any { frame ->
            val name = frame.className.lowercase()
            name.contains("xposed") || name.contains("lsposed")
        }
    }.getOrDefault(false)
}
