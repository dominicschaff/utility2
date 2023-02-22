package dev.schaff.utility

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.TextView
import com.koushikdutta.ion.Ion
import dev.schaff.utility.databinding.ActivityDownloadBinding
import dev.schaff.utility.helpers.externalFile
import dev.schaff.utility.helpers.formatSize
import dev.schaff.utility.helpers.homeDir
import dev.schaff.utility.helpers.toDateFull
import java.io.File
import java.util.*

class DownloadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDownloadBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download)
        binding = ActivityDownloadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ArrayAdapter.createFromResource(
            this,
            R.array.urls,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.downloadList.adapter = adapter
        }
        binding.btnDownloadList.setOnClickListener {
            val url = (binding.downloadList.selectedItem) as String
            val fileName = url.split("/").last()
            Ion.with(this)
                .load(url)
                .progressBar(binding.progress)
                .progress { downloaded, total -> print("$downloaded / $total") }
                .write(externalFile(fileName))
        }
        binding.btnDownloadLink.setOnClickListener {
            val url = binding.downloadLink.text.toString()
            val fileName = url.split("/").last()
            Ion.with(this)
                .load(url)
                .progressBar(binding.progress)
                .progress { downloaded, total -> print("$downloaded / $total") }
                .write(externalFile(fileName))
        }
        val files = ArrayList<File>()
        homeDir().listFiles()?.forEach {
            if (it.isFile) {
                files.add(it)
            }
        }

        files.forEach {
            val modified = Date(it.lastModified())
            val tv= TextView(this)
            tv.text = "${it.name} - ${it.length().formatSize()} - ${modified.toDateFull()}"
            binding.localFiles.addView(tv)
        }
    }
}