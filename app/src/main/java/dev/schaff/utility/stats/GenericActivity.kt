package dev.schaff.utility.stats

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.google.gson.JsonObject
import dev.schaff.utility.databinding.ActivityGenericBinding
import dev.schaff.utility.helpers.appendToFile
import dev.schaff.utility.helpers.sdFile
import dev.schaff.utility.helpers.toast
import java.util.*

@SuppressLint("SetTextI18n")
open class GenericActivity : AppCompatActivity() {
    private var selectedTime = ""
    private lateinit var binding: ActivityGenericBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGenericBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupDateTimeSelectors()

        // Get a reference to the AutoCompleteTextView in the layout.
        val textView = binding.autocompleteType
        val categories =
            arrayOf(
                "Weight - kg",
                "Measurement Waist - cm",
                "Measurement - cm",
                "Value",
                "Water/Liquid - ml",
                "Shot - count",
                "Sugar - count"
            )
        ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            categories
        ).also { adapter ->
            textView.setAdapter(adapter)
        }


        binding.add.setOnClickListener {
            JsonObject().apply {
                addProperty("date", selectedTime)
                addProperty("type", "generic")
                addProperty("category", binding.autocompleteType.text.toString())
                addProperty("amount", binding.value.text.toString().toFloat())
                addProperty("description", binding.description.text.toString())
            }.appendToFile(sdFile("data.json"))

            toast("Record saved")
            finish()
        }
    }

    private fun setupDateTimeSelectors() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        binding.time.text = "%02d:%02d".format(hour, minute)
        binding.date.text = "%04d-%02d-%02d".format(year, month, day)
        selectedTime = "${binding.date.text}T${binding.time.text}:00.0+02:00"

        binding.date.setOnClickListener {
            DatePickerDialog(this).apply {
                setOnDateSetListener { _, year, month, dayOfMonth ->
                    binding.date.text = "%04d-%02d-%02d".format(year, month, dayOfMonth)
                    selectedTime = "${binding.date.text}T${binding.time.text}:00.0+02:00"
                }
                show()
            }
        }
        binding.time.setOnClickListener {
            TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                binding.time.text = "%02d:%02d".format(hourOfDay, minute)
                selectedTime = "${binding.date.text}T${binding.time.text}:00.0+02:00"
            }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show()
        }
    }
}