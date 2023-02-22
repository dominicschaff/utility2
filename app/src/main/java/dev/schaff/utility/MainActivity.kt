package dev.schaff.utility

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.schaff.utility.databinding.ActivityMainBinding
import dev.schaff.utility.helpers.goto
import dev.schaff.utility.helpers.toast

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.gotoOsmMaps.setOnClickListener { goto(MapActivity::class.java) }
        binding.gotoDownload.setOnClickListener { goto(DownloadActivity::class.java) }
        binding.gotoNotes.setOnClickListener { goto(NotesActivity::class.java) }
        binding.gotoScan.setOnClickListener { goto(ScannerActivity::class.java) }
    }
}