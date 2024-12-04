package dev.schaff.utility.stats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.schaff.utility.databinding.ActivityStatsSelectionBinding
import dev.schaff.utility.helpers.goto

class StatsSelectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStatsSelectionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatsSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.electricity.setOnClickListener { goto(ElectricityActivity::class.java) }
        binding.expense.setOnClickListener { goto(ExpenseActivity::class.java) }
        binding.fuel.setOnClickListener { goto(FuelActivity::class.java) }
        binding.generic.setOnClickListener { goto(GenericActivity::class.java) }
        binding.device.setOnClickListener { goto(DeviceStatsActivity::class.java) }
    }
}