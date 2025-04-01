package dev.schaff.utility.helpers

import android.location.Location
import android.location.LocationManager
import com.google.gson.JsonObject
import dev.schaff.utility.R
import org.oscim.core.GeoPoint
import org.oscim.layers.vector.geometries.Style

fun Float.bearingToCompass(): String = when {
    this < 28 -> "N"
    this < 73 -> "NE"
    this < 118 -> "E"
    this < 163 -> "SE"
    this < 208 -> "S"
    this < 253 -> "SW"
    this < 298 -> "W"
    this < 343 -> "NW"
    else -> "N"
}

fun Location.distanceTo(point: GeoPoint): Float =
    this.distanceTo(Location(LocationManager.GPS_PROVIDER).apply {
        this.latitude = point.latitude
        this.longitude = point.longitude
    })

fun colourStyle(f: String): Style = Style.builder()
    .buffer(0.5)
    .fillColor(f)
    .fillAlpha(0.2F).build()

val markerColours = mapOf(
    R.drawable.ic_place_green to "green",
    R.drawable.ic_place_blue to "blue",
    R.drawable.ic_place_pink to "pink",
    R.drawable.ic_place_red to "red",
    R.drawable.ic_place_black to "black",
    R.drawable.ic_place_light_blue to "light_blue",
    R.drawable.ic_place_purple to "purple",
    R.drawable.ic_place_cyan to "cyan",
    R.drawable.ic_place_home to "home",
    R.drawable.ic_place_heart to "heart",
    R.drawable.ic_place_heart_broken to "heart_broken",
    R.drawable.ic_place_heart_cold to "heart_cold",
    R.drawable.ic_place_heart_crowned to "heart_crowned",
    R.drawable.ic_place_store to "store",
    R.drawable.ic_place_hotel to "hotel",
    R.drawable.ic_place_drink to "drink",
    R.drawable.ic_place_coffee to "coffee",
    R.drawable.ic_place_office to "office",
    R.drawable.ic_place_bed to "bed",
    R.drawable.ic_place_bone to "bone",
    R.drawable.ic_place_entertainment to "entertainment",
    R.drawable.ic_place_magic to "magic",
    R.drawable.ic_place_paw to "paw",
    R.drawable.ic_place_shower to "shower",
    R.drawable.ic_place_work to "work",
    R.drawable.ic_place_campfire to "campfire",
    R.drawable.ic_place_plane to "plane",
    R.drawable.ic_place_money to "money",
    R.drawable.ic_place_train to "train",
    R.drawable.ic_place_restaurant to "restaurant",
    R.drawable.ic_place_user_pin to "person",
    R.drawable.ic_place_tree to "tree",
)

inline fun JsonObject.toGeoPoint() = GeoPoint(d("latitude"), d("longitude"))

data class LocationPoint(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val colour: String = "blue"
)
inline fun LocationPoint.toGeoPoint() = GeoPoint(latitude, longitude)