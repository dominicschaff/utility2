package dev.schaff.utility.stats

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.JsonObject
import dev.schaff.utility.databinding.ActivityFuelBinding
import dev.schaff.utility.helpers.appendToFile
import dev.schaff.utility.helpers.sdFile
import dev.schaff.utility.helpers.toast
import java.util.*

class FuelActivity : AppCompatActivity() {
    var selectedtime = ""
    private lateinit var binding: ActivityFuelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFuelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupDateTimeSelectors()

        binding.add.setOnClickListener {
            JsonObject().apply {
                addProperty("date", selectedtime)
                addProperty("vehicle", "i20")
                addProperty("type", "fuel")
                addProperty("cost_per_litre", binding.costPerLitre.text.toString().toFloat())
                addProperty("fuel_amount", binding.amountOfFuel.text.toString().toFloat())
                addProperty("cost_amount", binding.totalCost.text.toString().toFloat())
                addProperty("odometer", binding.odometer.text.toString().toInt())
                addProperty("vehicle_usage", binding.vehicleUsage.text.toString().toFloat())
                addProperty("notes", binding.notes.text.toString())
            }.appendToFile(sdFile("data.json"))

            toast("Record saved")
            finish()
        }
    }
    private fun setupDateTimeSelectors() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH) + 1
        val day = c.get(Calendar.DAY_OF_MONTH)
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        binding.time.text = "%02d:%02d".format(hour, minute)
        binding.date.text = "%04d-%02d-%02d".format(year, month, day)
        selectedtime = "${binding.date.text}T${binding.time.text}:00.0+02:00"

        binding.date.setOnClickListener {
            DatePickerDialog(this).apply {
                setOnDateSetListener { _, year, month, dayOfMonth ->
                    binding.date.text = "%04d-%02d-%02d".format(year, month + 1, dayOfMonth)
                    selectedtime = "${binding.date.text}T${binding.time.text}:00.0+02:00"
                }
                show()
            }
        }
        binding.time.setOnClickListener {
            TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                binding.time.text = "%02d:%02d".format(hourOfDay, minute)
                selectedtime = "${binding.date.text}T${binding.time.text}:00.0+02:00"
            }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show()
        }
    }
}