package dev.schaff.utility

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.JsonObject
import dev.schaff.utility.helpers.appendToFile
import dev.schaff.utility.helpers.sdFile
import dev.schaff.utility.helpers.toDateFull
import dev.schaff.utility.helpers.toast
import java.util.*
import kotlin.math.min

class TextReceiveActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get intent, action and MIME type
        val intent = intent
        val action = intent.action
        val type: String? = intent.type

        if (Intent.ACTION_SEND == action && type != null) {
            if ("text/plain" == type) {
                handleSendText(intent) // Handle text being sent
            }
        } else {
            toast("Didn't know what to do exiting...")
        }
        finish()
    }

    private fun handleSendText(intent: Intent) {
        val sharedText: String? = intent.getStringExtra(Intent.EXTRA_TEXT)
        if (sharedText != null) {
            JsonObject().apply {
                addProperty("event_time", Date().toDateFull())
                addProperty("event_type", "sharedMessage")
                addProperty("text", sharedText)
            }.appendToFile(sdFile("shared.json"))

            toast(
                "Received and saved text: ${
                    sharedText.substring(
                        0,
                        min(sharedText.length, 200)
                    )
                }", Toast.LENGTH_SHORT
            )
        }

    }
}
