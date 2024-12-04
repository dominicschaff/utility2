package dev.schaff.utility.stats

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import dev.schaff.utility.R
import dev.schaff.utility.databinding.ActivityDeviceStatsBinding
import dev.schaff.utility.helpers.appendToFile
import dev.schaff.utility.helpers.sdFile

class DeviceStatsActivity : AppCompatActivity(), SensorEventListener, LocationListener {
    private lateinit var binding: ActivityDeviceStatsBinding
    private lateinit var manager: SensorManager
    private lateinit var locationManager: LocationManager
    private var sensorTypes: Array<Int> = arrayOf(
        Sensor.TYPE_LIGHT,
        Sensor.TYPE_GRAVITY,
        Sensor.TYPE_PRESSURE,
        Sensor.TYPE_PROXIMITY,
        Sensor.TYPE_ACCELEROMETER,
        Sensor.TYPE_HEADING,
        Sensor.TYPE_AMBIENT_TEMPERATURE,
        Sensor.TYPE_GYROSCOPE,
        Sensor.TYPE_MAGNETIC_FIELD,
        Sensor.TYPE_RELATIVE_HUMIDITY
    )
    private var sensorTimes: HashMap<Int, Long> = HashMap()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeviceStatsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        manager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

    }

    @SuppressLint("MissingPermission")
    override fun onResume() {
        super.onResume()
        sensorTypes.forEach {
            manager.registerListener(
                this,
                manager.getDefaultSensor(it),
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0F, this)
    }

    override fun onPause() {
        locationManager.removeUpdates(this)
        manager.unregisterListener(this)
        super.onPause()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent) {
        val sensor = event.sensor
        val time = sensorTimes[sensor.type]
        val now = System.currentTimeMillis() / 1000

        if ((time ?: 0) >= now) {
            return
        }
        sensorTimes[sensor.type] = now

        val values = JsonArray()
        event.values.forEach { values.add(it) }

        val json = JsonObject().apply {
            addProperty("sensorName", sensor.name)
            addProperty("sensorType", sensor.type)
            addProperty("sensorId", sensor.id)
            addProperty("accuracy", event.accuracy)
            add("values", values)
        }

        binding.data.text = json.toString()
        json.appendToFile(sdFile("data_sensors.json"))
    }

    override fun onLocationChanged(location: Location) {

        val json = JsonObject().apply {
            addProperty("time", location.time)
            addProperty("accuracy", location.accuracy)
            addProperty("latitude", location.latitude)
            addProperty("longitude", location.longitude)
            addProperty("provider", location.provider)
            addProperty("altitude", location.altitude)
            addProperty("bearing", location.bearing)
            addProperty("bearingAccuracyDegrees", location.bearingAccuracyDegrees)
            addProperty("speed", location.speed)
            addProperty("verticalAccuracyMeters", location.verticalAccuracyMeters)
        }

        binding.location.text = json.toString()
        json.appendToFile(sdFile("data_location.json"))
    }
}