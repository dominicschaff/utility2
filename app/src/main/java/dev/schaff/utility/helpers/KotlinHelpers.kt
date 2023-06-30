@file:Suppress("NOTHING_TO_INLINE")

package dev.schaff.utility.helpers

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.documentfile.provider.DocumentFile
import java.io.File
import kotlin.experimental.and
import android.os.Environment
import com.google.gson.JsonObject
import dev.schaff.utility.helpers.asJsonObject


fun Context.alert(content: String, clickListener: DialogInterface.OnClickListener? = null) {
    {
        AlertDialog.Builder(this).apply {
            setCancelable(clickListener == null)
            setMessage(content)

            if (clickListener != null)
                setNeutralButton("OK", clickListener)

            show()
        }
    }.ignore()

}

fun Context.request(content: String, input_type:Int, f:(String)->Unit) {
    val builder = AlertDialog.Builder(this)
    builder.setTitle(content)
    val input = EditText(this)
    input.inputType = input_type
    builder.setView(input)
    builder.setPositiveButton(
        "OK"
    ) { _, _ -> f(input.text.toString()) }
    builder.setNegativeButton(
        "Cancel"
    ) { dialog, _ -> dialog.cancel() }
    builder.show()
}

fun Context.createChooser(
    title: String,
    options: Array<String>,
    clickListener: DialogInterface.OnClickListener
) {
    AlertDialog.Builder(this)
        .setTitle(title)
        .setItems(options, clickListener)
        .show()
}


fun Activity.requestPermissions(permissions: Array<String>): Boolean {
    permissions.forEach {
        if (ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED) {
            toast("Ask for $it")
            ActivityCompat.requestPermissions(this, permissions, 1)
            return false
        }
    }
    return true
}

inline fun <T> (() -> T).or(f: () -> T): T =
    try {
        this()
    } catch (e: Exception) {
        f()
    }


inline fun (() -> Unit?).or(f: (e: Exception) -> Unit) {
    try {
        this()
    } catch (e: Exception) {
        f(e)
    }
}

inline fun <T> (() -> T).orCatch(d: T): T = try {
    this()
} catch (e: Exception) {
    d
}

inline fun (() -> Unit?).ignore() {
    try {
        this()
    } catch (ignored: Exception) {
    }
}

inline fun <T> (() -> T).ignore(a: T? = null): T? = try {
    this()
} catch (ignored: Exception) {
    a
}

inline fun (() -> Unit?).orPrint() {
    try {
        this()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

inline fun Context.toast(s: String, length: Int = Toast.LENGTH_LONG) =
    Toast.makeText(this, s, length).show()

inline fun View.show() {
    visibility = View.VISIBLE
}

inline fun View.hide() {
    visibility = View.GONE
}

inline fun Activity.openFile(f: File) {
    try {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data =
            FileProvider.getUriForFile(this, applicationContext.packageName + ".provider", f)
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        startActivity(intent)
    } catch (e: Exception) {
        toast("No application to open this file")
    }
}

inline fun Activity.openFile(f: DocumentFile) {
    try {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = f.uri
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        startActivity(intent)
    } catch (e: Exception) {
        toast("No application to open this file")
    }
}

inline fun consume(f: () -> Unit): Boolean {
    f()
    return true
}

private val hexArray = "0123456789ABCDEF".toCharArray()
fun ByteArray.toHex(): String {
    val hexChars = CharArray(size * 2)
    for (j in indices) {
        val v: Int = (this[j] and 0xFF.toByte()).toInt()
        hexChars[j * 2] = hexArray[v.ushr(4)]
        hexChars[j * 2 + 1] = hexArray[v and 0x0F]
    }
    return String(hexChars)
}

inline fun <T> Array<T>.randomIndex() = (Math.random() * size).toInt()
inline fun <T> List<T>.randomIndex() = (Math.random() * size).toInt()

inline fun <T> List<T>.random(): T = this[this.randomIndex()]
inline fun <T> Array<T>.random() = this[this.randomIndex()]

fun String.extension(): String = substring(lastIndexOf(".")).lowercase()

fun StringBuilder.add(format: String, value: Int) {
    try {
        if (value > 0) {
            this.append(format.format(value))
            this.append("\n")
        }
    } catch (ignored: Exception) {
    }
}

fun StringBuilder.add(format: String, value: Double) {
    try {
        if (value > 0) {
            this.append(format.format(value))
            this.append("\n")
        }
    } catch (ignored: Exception) {
    }
}

fun StringBuilder.add(format: String, value: String?) {
    try {
        if (!value.isNullOrEmpty()) {
            this.append(format.format(value))
            this.append("\n")
        }
    } catch (ignored: Exception) {
    }
}


inline fun Context.externalFile(path: String) = File(homeDir(), path)
inline fun Context.sdFile(path: String) = File(Environment.getExternalStorageDirectory(), path)

inline fun Context.homeDir() = sdFile("dev.schaff.utility")
inline fun Context.configFile() = try {
    externalFile("utility.json").asJsonObject()
} catch (e: java.lang.Exception) {
    JsonObject()
}