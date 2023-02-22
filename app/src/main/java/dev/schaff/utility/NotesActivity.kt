package dev.schaff.utility

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.schaff.utility.databinding.ActivityNotesBinding
import dev.schaff.utility.helpers.externalFile
import dev.schaff.utility.helpers.ignore

class NotesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
        binding = ActivityNotesBinding.inflate(layoutInflater)
        setContentView(binding.root);
        {
            binding.text.setText(externalFile("utility.json").readText())
        }.ignore()
        binding.save.setOnClickListener {
            externalFile("utility.json").writeText(binding.text.text.toString())
        }
    }
}