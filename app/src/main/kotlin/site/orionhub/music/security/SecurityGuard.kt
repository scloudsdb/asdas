package site.orionhub.music.security

import android.content.Context

object SecurityGuard {
    data class ThreatReport(
        val rootDetected: Boolean = false,
        val integrityFailed: Boolean = false,
        val fridaDetected: Boolean = false,
        val xposedDetected: Boolean = false,
        val debuggerAttached: Boolean = false,
    ) {
        val hasThreat: Boolean
            get() = rootDetected || integrityFailed || fridaDetected ||
                xposedDetected || debuggerAttached

        fun describe(): String = buildList {
            if (rootDetected) add("Root access detected")
            if (integrityFailed) add("APK integrity compromised")
            if (fridaDetected) add("Instrumentation framework detected")
            if (xposedDetected) add("Xposed/LSPosed framework detected")
            if (debuggerAttached) add("Debugger attached")
        }.joinToString("\n").ifEmpty { "No threats detected" }
    }

    fun scan(context: Context): ThreatReport {
        IntegrityChecker.initialize(context)
        return ThreatReport(
            rootDetected = RootDetector.detect(context),
            integrityFailed = IntegrityChecker.detect(context),
            fridaDetected = FridaDetector.detect(),
            xposedDetected = XposedDetector.detect(),
            debuggerAttached = DebugDetector.detect(context),
        )
    }
}
