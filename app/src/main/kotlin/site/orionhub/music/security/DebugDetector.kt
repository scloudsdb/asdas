package site.orionhub.music.security

import android.content.Context
import android.content.pm.ApplicationInfo
import android.os.Debug
import java.io.File

object DebugDetector {
    fun detect(context: Context): Boolean =
        Debug.isDebuggerConnected() || isDebuggable(context) || hasTracer()

    private fun isDebuggable(context: Context): Boolean =
        (context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0

    private fun hasTracer(): Boolean = runCatching {
        val status = File("/proc/self/status")
        if (status.exists()) {
            val line = status.readLines().find { it.startsWith("TracerPid:") }
            val pid = line?.substringAfter(":")?.trim()?.toIntOrNull() ?: 0
            pid != 0
        } else {
            false
        }
    }.getOrDefault(false)
}
