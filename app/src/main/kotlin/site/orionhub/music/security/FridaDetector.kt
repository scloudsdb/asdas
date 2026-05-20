package site.orionhub.music.security

import java.io.File
import java.net.Socket

object FridaDetector {
    private val knownPorts = intArrayOf(27042, 27043)
    private val knownLibNames = arrayOf("frida", "frida-agent", "frida-gadget")

    fun detect(): Boolean =
        isPortOpen() || isLibraryLoaded() || hasLocalArtifacts()

    private fun isPortOpen(): Boolean = knownPorts.any { port ->
        runCatching { Socket("127.0.0.1", port).use { true } }.getOrDefault(false)
    }

    private fun isLibraryLoaded(): Boolean = runCatching {
        val maps = File("/proc/self/maps")
        if (maps.exists()) {
            val content = maps.readText().lowercase()
            knownLibNames.any { content.contains(it) }
        } else {
            false
        }
    }.getOrDefault(false)

    private fun hasLocalArtifacts(): Boolean = runCatching {
        val dir = File("/data/local/tmp")
        dir.exists() && dir.listFiles()?.any {
            it.name.lowercase().let { n -> n.contains("frida") || n.contains("re.frida") }
        } == true
    }.getOrDefault(false)
}
