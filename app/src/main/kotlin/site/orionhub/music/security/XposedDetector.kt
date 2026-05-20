package site.orionhub.music.security

object XposedDetector {
    private val targetClasses = arrayOf(
        "de.robv.android.xposed.XposedBridge",
        "de.robv.android.xposed.XposedHelpers",
        "io.github.libxposed.api.XposedInterface",
        "org.lsposed.lspd.core.Main",
    )

    fun detect(): Boolean = hasXposedClasses()

    private fun hasXposedClasses(): Boolean = targetClasses.any { cls ->
        runCatching { Class.forName(cls) }.isSuccess
    }
}
