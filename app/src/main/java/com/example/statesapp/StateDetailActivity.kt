package com.example.statesapp

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.MapStyleOptions

class StateDetailActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private lateinit var stateName: String
    private var statePopulation: Int = 0
    private lateinit var stateFlagUrl: String

    // Daftar koordinat negara bagian (contoh)
    private val stateCoordinates = mapOf(
        "Alabama" to LatLng(32.806671, -86.791130),
        "Alaska" to LatLng(61.370716, -152.404419),
        "Arizona" to LatLng(33.729759, -111.431221),
        "Arkansas" to LatLng(34.969704, -92.373123),
        "California" to LatLng(36.116203, -119.681564),
        "Colorado" to LatLng(39.059811, -105.311104),
        "Connecticut" to LatLng(41.597782, -72.755371),
        "Delaware" to LatLng(39.318523, -75.507141),
        "District of Columbia" to LatLng(38.897438, -77.026817),
        "Florida" to LatLng(27.766279, -81.686783),
        "Georgia" to LatLng(33.040619, -83.643074),
        "Hawaii" to LatLng(21.094318, -157.498337),
        "Idaho" to LatLng(44.240459, -114.478828),
        "Illinois" to LatLng(40.349457, -88.986137),
        "Indiana" to LatLng(39.849426, -86.258278),
        "Iowa" to LatLng(42.011539, -93.210526),
        "Kansas" to LatLng(38.526600, -96.726486),
        "Kentucky" to LatLng(37.668140, -84.670067),
        "Louisiana" to LatLng(31.169546, -91.867805),
        "Maine" to LatLng(44.693947, -69.381927),
        "Maryland" to LatLng(39.063946, -76.802101),
        "Massachusetts" to LatLng(42.230171, -71.530106),
        "Michigan" to LatLng(43.326618, -84.536095),
        "Minnesota" to LatLng(45.694454, -93.900192),
        "Mississippi" to LatLng(32.741646, -89.678696),
        "Missouri" to LatLng(38.456085, -92.288368),
        "Montana" to LatLng(46.921925, -110.454353),
        "Nebraska" to LatLng(41.125370, -98.268082),
        "Nevada" to LatLng(38.313515, -117.055374),
        "New Hampshire" to LatLng(43.452492, -71.563896),
        "New Jersey" to LatLng(40.298904, -74.521011),
        "New Mexico" to LatLng(34.840515, -106.248482),
        "New York" to LatLng(42.165726, -74.948051),
        "North Carolina" to LatLng(35.630066, -79.806419),
        "North Dakota" to LatLng(47.528912, -99.784012),
        "Ohio" to LatLng(40.388783, -82.764915),
        "Oklahoma" to LatLng(35.565342, -96.928917),
        "Oregon" to LatLng(44.572021, -122.070938),
        "Pennsylvania" to LatLng(40.590752, -77.209755),
        "Rhode Island" to LatLng(41.680893, -71.511780),
        "South Carolina" to LatLng(33.856892, -80.945007),
        "South Dakota" to LatLng(44.299782, -99.438828),
        "Tennessee" to LatLng(35.747845, -86.692345),
        "Texas" to LatLng(31.054487, -97.563461),
        "Utah" to LatLng(40.150032, -111.862434),
        "Vermont" to LatLng(44.045876, -72.710686),
        "Virginia" to LatLng(37.769337, -78.169968),
        "Washington" to LatLng(47.400902, -121.490494),
        "West Virginia" to LatLng(38.491226, -80.954453),
        "Wisconsin" to LatLng(44.268543, -89.616508),
        "Wyoming" to LatLng(42.755966, -107.302490),
        "Puerto Rico" to LatLng(18.220833, -66.590149)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_state_detail)

        // Ambil data dari intent
        stateName = intent.getStringExtra("STATE_NAME") ?: "Unknown State"
        statePopulation = intent.getIntExtra("STATE_POPULATION", 0)
        stateFlagUrl = intent.getStringExtra("STATE_FLAG_URL") ?: ""

        // Inisialisasi SupportMapFragment
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // Set style peta berdasarkan mode tema (light atau dark)
        val isNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
        val mapStyleRes = if (isNightMode) R.raw.map_style_dark else R.raw.map_style_light
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, mapStyleRes))

        // Dapatkan koordinat negara bagian dari stateCoordinates
        val stateLocation = stateCoordinates[stateName] ?: LatLng(37.0902, -95.7129)

        // Tambahkan marker
        val marker = googleMap.addMarker(
            MarkerOptions()
                .position(stateLocation)
                .title(stateName)
        )

        // Pindahkan kamera ke lokasi negara bagian dengan animasi
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(stateLocation, 7f), 1000, null)

        // Preload gambar bendera menggunakan Glide
        Glide.with(this@StateDetailActivity)
            .load(stateFlagUrl)
            .preload() // Preload gambar ke cache

        // Menentukan warna teks berdasarkan tema yang aktif
        val textColor = if (isNightMode) {
            R.color.text_color_dark // Putih untuk dark mode
        } else {
            R.color.text_color_light // Hitam untuk light mode
        }

        // Set custom info window adapter
        googleMap.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
            override fun getInfoWindow(marker: Marker): View? {
                return null // Gunakan default info window frame
            }

            override fun getInfoContents(marker: Marker): View {
                // Inflate layout kustom
                val view = layoutInflater.inflate(R.layout.custom_info_window, null)

                // Tampilkan data
                val imgFlag = view.findViewById<ImageView>(R.id.imgFlag)
                val tvStateName = view.findViewById<TextView>(R.id.tvStateName)
                val tvPopulation = view.findViewById<TextView>(R.id.tvPopulation)

                tvStateName.text = stateName
                tvPopulation.text = "Population: $statePopulation"

                // Set warna teks sesuai tema
                tvStateName.setTextColor(resources.getColor(textColor, null))
                tvPopulation.setTextColor(resources.getColor(textColor, null))

                // Load gambar bendera menggunakan Glide
                Glide.with(this@StateDetailActivity)
                    .load(stateFlagUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .override(100, 100)
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder)
                    .into(imgFlag)

                return view
            }
        })

        // Tampilkan info window secara otomatis saat marker diklik
        marker?.showInfoWindow()

        // Tambahkan tombol kembali di ActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // Handle tombol kembali di ActionBar
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
