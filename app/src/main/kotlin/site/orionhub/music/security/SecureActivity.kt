package site.orionhub.music.security

import android.app.AlertDialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import site.orionhub.music.BuildConfig

open class SecureActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!BuildConfig.DEBUG) {
            performSecurityCheck()
        }
    }

    override fun onResume() {
        super.onResume()
        if (!BuildConfig.DEBUG) {
            performSecurityCheck()
        }
    }

    private fun performSecurityCheck() {
        val report = SecurityGuard.scan(this)
        if (report.hasThreat) {
            AlertDialog.Builder(this)
                .setTitle("Security Alert")
                .setMessage(
                    "A security threat has been detected:\n\n${report.describe()}" +
                        "\n\nThe app will close for your protection.",
                )
                .setCancelable(false)
                .setPositiveButton("Close") { _, _ -> finishAffinity() }
                .show()
        }
    }
}
