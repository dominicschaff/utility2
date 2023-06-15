package dev.schaff.utility

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.schaff.utility.dashboards.DashboardActivity
import dev.schaff.utility.databinding.ActivityMainBinding
import dev.schaff.utility.helpers.goto
import dev.schaff.utility.helpers.toast
import dev.schaff.utility.stats.StatsSelectionActivity

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
        binding.gotoLoyalty.setOnClickListener { goto(LoyaltyActivity::class.java) }
        binding.gotoStats.setOnClickListener { goto(StatsSelectionActivity::class.java) }
        binding.gotoDashboard.setOnClickListener { goto(DashboardActivity::class.java) }
    }
}