package site.orionhub.music.security

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import java.security.MessageDigest

object IntegrityChecker {
    @Volatile
    private var baselineHash: String? = null

    fun initialize(context: Context) {
        if (baselineHash == null) {
            baselineHash = computeSignatureHash(context)
        }
    }

    fun detect(context: Context): Boolean =
        hasSignatureChanged(context)

    private fun computeSignatureHash(context: Context): String? = runCatching {
        val signers = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            context.packageManager
                .getPackageInfo(context.packageName, PackageManager.GET_SIGNING_CERTIFICATES)
                .signingInfo?.apkContentsSigners
        } else {
            @Suppress("DEPRECATION")
            context.packageManager
                .getPackageInfo(context.packageName, PackageManager.GET_SIGNATURES)
                .signatures
        }
        signers?.firstOrNull()?.let { sig ->
            MessageDigest.getInstance("SHA-256")
                .digest(sig.toByteArray())
                .joinToString("") { "%02x".format(it) }
        }
    }.getOrNull()

    private fun hasSignatureChanged(context: Context): Boolean {
        val current = computeSignatureHash(context) ?: return false
        return baselineHash != null && current != baselineHash
    }
}
