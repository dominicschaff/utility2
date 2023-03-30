package dev.schaff.utility.stats

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import dev.schaff.utility.databinding.ActivityGenericBinding
import java.util.*

@SuppressLint("SetTextI18n")
open class GenericActivity : AppCompatActivity() {
    var selectedtime = ""
    private lateinit var binding: ActivityGenericBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGenericBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupDateTimeSelectors()

        // Get a reference to the AutoCompleteTextView in the layout.
        val textView = binding.autocompleteCountry
// Get the string array.
        val countries = arrayOf("Thing1", "Thing2")
// Create the adapter and set it to the AutoCompleteTextView.
        ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries).also { adapter ->
            textView.setAdapter(adapter)
        }
    }
    fun setupDateTimeSelectors() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        binding.time.text = "%02d:%02d".format(hour, minute)
        binding.date.text = "%04d-%02d-%02d".format(year, month, day)
        selectedtime = "${binding.date.text}T${binding.time.text}:00.0+02:00"

        binding.date.setOnClickListener {
            DatePickerDialog(this).apply {
                setOnDateSetListener { _, year, month, dayOfMonth ->
                    binding.date.text = "%04d-%02d-%02d".format(year, month, dayOfMonth)
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