package dev.schaff.utility

import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import dev.schaff.utility.databinding.ActivityLoyaltyBinding
import dev.schaff.utility.databinding.ActivityMainBinding
import dev.schaff.utility.helpers.*

data class Loyalty(
    val name: String,
    val card: String,
    val type: String
)


class LoyaltyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoyaltyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoyaltyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loyalty = configFile().a("loyalty").mapObject {
            Loyalty(
                s("name"),
                s("card"),
                s("type", "code_128")
            )
        }
        ArrayAdapter<String>(this, android.R.layout.simple_spinner_item).also {
            it.addAll(loyalty.map { l -> l.name })
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.loyaltyList.adapter = it
        }

        binding.loyaltyList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                binding.cardNumber.text = loyalty[position].card
                binding.barcode.setImageBitmap(encodeAsBitmap(loyalty[position]))
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

    }


    @Throws(WriterException::class)
    private fun encodeAsBitmap(card: Loyalty): Bitmap? {
        val size = 1024
        val result: BitMatrix
        try {
            result = MultiFormatWriter().encode(
                card.card,
                when (card.type) {
                    "code_128" -> BarcodeFormat.CODE_128
                    "code_39" -> BarcodeFormat.CODE_39
                    "qr_code" -> BarcodeFormat.QR_CODE
                    "data_matrix" -> BarcodeFormat.DATA_MATRIX
                    "pdf_417" -> BarcodeFormat.PDF_417
                    "ean_13" -> BarcodeFormat.EAN_13
                    "ean_8" -> BarcodeFormat.EAN_8
                    else -> BarcodeFormat.QR_CODE
                }, size, size, null
            )

            val w = result.width
            val h = result.height
            val pixels = IntArray(w * h)
            for (y in 0 until h) {
                val offset = y * w
                for (x in 0 until w)
                    pixels[offset + x] = if (result.get(x, y)) Color.BLACK else Color.WHITE
            }
            val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
            bitmap.setPixels(pixels, 0, size, 0, 0, w, h)
            return bitmap
        } catch (iae: Exception) {
            iae.printStackTrace()
            return null
        }
    }
}