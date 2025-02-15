package com.example.statesapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var stateListAdapter: StateListAdapter
    private lateinit var fabSort: FloatingActionButton
    private val states = mutableListOf<State>() // Data asli
    private val displayedStates = mutableListOf<State>() // Data yang ditampilkan

    private val stateFlagMap = mapOf(
        "Alabama" to "https://upload.wikimedia.org/wikipedia/commons/thumb/5/5c/Flag_of_Alabama.svg/1200px-Flag_of_Alabama.svg.png",
        "Alaska" to "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e6/Flag_of_Alaska.svg/1200px-Flag_of_Alaska.svg.png",
        "Arizona" to "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9d/Flag_of_Arizona.svg/1200px-Flag_of_Arizona.svg.png",
        "Arkansas" to "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9d/Flag_of_Arkansas.svg/1200px-Flag_of_Arkansas.svg.png",
        "California" to "https://upload.wikimedia.org/wikipedia/commons/thumb/0/01/Flag_of_California.svg/1200px-Flag_of_California.svg.png",
        "Colorado" to "https://upload.wikimedia.org/wikipedia/commons/thumb/4/46/Flag_of_Colorado.svg/1200px-Flag_of_Colorado.svg.png",
        "Connecticut" to "https://upload.wikimedia.org/wikipedia/commons/thumb/9/96/Flag_of_Connecticut.svg/1200px-Flag_of_Connecticut.svg.png",
        "Delaware" to "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c6/Flag_of_Delaware.svg/1200px-Flag_of_Delaware.svg.png",
        "District of Columbia" to "https://upload.wikimedia.org/wikipedia/commons/thumb/0/03/Flag_of_Washington%2C_D.C.svg/1920px-Flag_of_Washington%2C_D.C.svg.png",
        "Florida" to "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f7/Flag_of_Florida.svg/1200px-Flag_of_Florida.svg.png",
        "Georgia" to "https://upload.wikimedia.org/wikipedia/commons/thumb/0/08/Flag_of_the_State_of_Georgia.svg/1920px-Flag_of_the_State_of_Georgia.svg.png",
        "Hawaii" to "https://upload.wikimedia.org/wikipedia/commons/thumb/e/ef/Flag_of_Hawaii.svg/1200px-Flag_of_Hawaii.svg.png",
        "Idaho" to "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a4/Flag_of_Idaho.svg/1200px-Flag_of_Idaho.svg.png",
        "Illinois" to "https://upload.wikimedia.org/wikipedia/commons/thumb/0/01/Flag_of_Illinois.svg/1200px-Flag_of_Illinois.svg.png",
        "Indiana" to "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/Flag_of_Indiana.svg/1200px-Flag_of_Indiana.svg.png",
        "Iowa" to "https://upload.wikimedia.org/wikipedia/commons/thumb/a/aa/Flag_of_Iowa.svg/1200px-Flag_of_Iowa.svg.png",
        "Kansas" to "https://upload.wikimedia.org/wikipedia/commons/thumb/d/da/Flag_of_Kansas.svg/1200px-Flag_of_Kansas.svg.png",
        "Kentucky" to "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8d/Flag_of_Kentucky.svg/1200px-Flag_of_Kentucky.svg.png",
        "Louisiana" to "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e0/Flag_of_Louisiana.svg/1200px-Flag_of_Louisiana.svg.png",
        "Maine" to "https://upload.wikimedia.org/wikipedia/commons/thumb/3/35/Flag_of_Maine.svg/1200px-Flag_of_Maine.svg.png",
        "Maryland" to "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a0/Flag_of_Maryland.svg/1200px-Flag_of_Maryland.svg.png",
        "Massachusetts" to "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f2/Flag_of_Massachusetts.svg/1200px-Flag_of_Massachusetts.svg.png",
        "Michigan" to "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b5/Flag_of_Michigan.svg/1200px-Flag_of_Michigan.svg.png",
        "Minnesota" to "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b9/Flag_of_Minnesota.svg/1200px-Flag_of_Minnesota.svg.png",
        "Mississippi" to "https://upload.wikimedia.org/wikipedia/commons/thumb/4/42/Flag_of_Mississippi.svg/1200px-Flag_of_Mississippi.svg.png",
        "Missouri" to "https://upload.wikimedia.org/wikipedia/commons/thumb/5/5a/Flag_of_Missouri.svg/1200px-Flag_of_Missouri.svg.png",
        "Montana" to "https://upload.wikimedia.org/wikipedia/commons/thumb/c/cb/Flag_of_Montana.svg/1200px-Flag_of_Montana.svg.png",
        "Nebraska" to "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4d/Flag_of_Nebraska.svg/1200px-Flag_of_Nebraska.svg.png",
        "Nevada" to "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f1/Flag_of_Nevada.svg/1200px-Flag_of_Nevada.svg.png",
        "New Hampshire" to "https://upload.wikimedia.org/wikipedia/commons/thumb/2/28/Flag_of_New_Hampshire.svg/1200px-Flag_of_New_Hampshire.svg.png",
        "New Jersey" to "https://upload.wikimedia.org/wikipedia/commons/thumb/9/92/Flag_of_New_Jersey.svg/1200px-Flag_of_New_Jersey.svg.png",
        "New Mexico" to "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c3/Flag_of_New_Mexico.svg/1200px-Flag_of_New_Mexico.svg.png",
        "New York" to "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1a/Flag_of_New_York.svg/1200px-Flag_of_New_York.svg.png",
        "North Carolina" to "https://upload.wikimedia.org/wikipedia/commons/thumb/b/bb/Flag_of_North_Carolina.svg/1200px-Flag_of_North_Carolina.svg.png",
        "North Dakota" to "https://upload.wikimedia.org/wikipedia/commons/thumb/e/ee/Flag_of_North_Dakota.svg/1200px-Flag_of_North_Dakota.svg.png",
        "Ohio" to "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4c/Flag_of_Ohio.svg/1200px-Flag_of_Ohio.svg.png",
        "Oklahoma" to "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6e/Flag_of_Oklahoma.svg/1200px-Flag_of_Oklahoma.svg.png",
        "Oregon" to "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b9/Flag_of_Oregon.svg/1200px-Flag_of_Oregon.svg.png",
        "Pennsylvania" to "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f7/Flag_of_Pennsylvania.svg/1200px-Flag_of_Pennsylvania.svg.png",
        "Puerto Rico" to "https://upload.wikimedia.org/wikipedia/commons/thumb/2/28/Flag_of_Puerto_Rico.svg/1280px-Flag_of_Puerto_Rico.svg.png",
        "Rhode Island" to "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f3/Flag_of_Rhode_Island.svg/1200px-Flag_of_Rhode_Island.svg.png",
        "South Carolina" to "https://upload.wikimedia.org/wikipedia/commons/thumb/6/69/Flag_of_South_Carolina.svg/1200px-Flag_of_South_Carolina.svg.png",
        "South Dakota" to "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1a/Flag_of_South_Dakota.svg/1200px-Flag_of_South_Dakota.svg.png",
        "Tennessee" to "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9e/Flag_of_Tennessee.svg/1200px-Flag_of_Tennessee.svg.png",
        "Texas" to "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f7/Flag_of_Texas.svg/1200px-Flag_of_Texas.svg.png",
        "Utah" to "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f6/Flag_of_Utah.svg/1200px-Flag_of_Utah.svg.png",
        "Vermont" to "https://upload.wikimedia.org/wikipedia/commons/thumb/4/49/Flag_of_Vermont.svg/1200px-Flag_of_Vermont.svg.png",
        "Virginia" to "https://upload.wikimedia.org/wikipedia/commons/thumb/4/47/Flag_of_Virginia.svg/1200px-Flag_of_Virginia.svg.png",
        "Washington" to "https://upload.wikimedia.org/wikipedia/commons/thumb/5/54/Flag_of_Washington.svg/1200px-Flag_of_Washington.svg.png",
        "West Virginia" to "https://upload.wikimedia.org/wikipedia/commons/thumb/2/22/Flag_of_West_Virginia.svg/1200px-Flag_of_West_Virginia.svg.png",
        "Wisconsin" to "https://upload.wikimedia.org/wikipedia/commons/thumb/2/22/Flag_of_Wisconsin.svg/1200px-Flag_of_Wisconsin.svg.png",
        "Wyoming" to "https://upload.wikimedia.org/wikipedia/commons/thumb/b/bc/Flag_of_Wyoming.svg/1200px-Flag_of_Wyoming.svg.png"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Atur judul Toolbar
        supportActionBar?.title = "Daftar Negara Bagian Amerika Serikat"

        // Inisialisasi RecyclerView dan FAB
        recyclerView = findViewById(R.id.rvStates)
        fabSort = findViewById(R.id.fabSort)

        stateListAdapter = StateListAdapter(displayedStates) { state ->
            val intent = Intent(this, StateDetailActivity::class.java)
            intent.putExtra("STATE_NAME", state.name)
            intent.putExtra("STATE_POPULATION", state.population)
            intent.putExtra("STATE_FLAG_URL", state.imageUrl)
            startActivity(intent)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = stateListAdapter

        // Menangani klik FAB
        fabSort.setOnClickListener {
            showSortDialog()
        }

        loadStatesFromAPI()
    }

    // Menampilkan dialog pilihan sorting
    private fun showSortDialog() {
        val options = arrayOf(
            "Sort by Name (A-Z)",
            "Sort by Name (Z-A)",
            "Sort by Population (Low to High)",
            "Sort by Population (High to Low)"
        )

        AlertDialog.Builder(this)
            .setTitle("Sort By")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> sortByNameAsc()
                    1 -> sortByNameDesc()
                    2 -> sortByPopulationAsc()
                    3 -> sortByPopulationDesc()
                }
            }
            .show()
    }

    // Fungsi untuk sorting berdasarkan nama (A-Z)
    private fun sortByNameAsc() {
        displayedStates.sortBy { it.name }
        stateListAdapter.notifyDataSetChanged()
    }

    // Fungsi untuk sorting berdasarkan nama (Z-A)
    private fun sortByNameDesc() {
        displayedStates.sortByDescending { it.name }
        stateListAdapter.notifyDataSetChanged()
    }

    // Fungsi untuk sorting berdasarkan populasi (rendah ke tinggi)
    private fun sortByPopulationAsc() {
        displayedStates.sortBy { it.population }
        stateListAdapter.notifyDataSetChanged()
    }

    // Fungsi untuk sorting berdasarkan populasi (tinggi ke rendah)
    private fun sortByPopulationDesc() {
        displayedStates.sortByDescending { it.population }
        stateListAdapter.notifyDataSetChanged()
    }

    // Memuat data dari API
    private fun loadStatesFromAPI() {
        val apiService = ApiService.create()
        apiService.getStateList().enqueue(object : Callback<StatesResponse> {
            override fun onResponse(
                call: Call<StatesResponse>,
                response: Response<StatesResponse>
            ) {
                if (response.isSuccessful) {
                    val statesResponse = response.body()
                    statesResponse?.data?.let { data ->
                        states.clear()
                        displayedStates.clear()

                        // Memproses data dan mengisi negara bagian
                        val newStates = data.map { stateData ->
                            val stateName = stateData.state ?: "Unknown"
                            val population = stateData.population ?: 0

                            val flagUrl = stateFlagMap[stateName]
                                ?: "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a4/Flag_of_the_United_States.svg/1920px-Flag_of_the_United_States.svg.png"

                            State(
                                name = stateName,
                                population = population,
                                imageUrl = flagUrl
                            )
                        }
                        states.addAll(newStates)
                        displayedStates.addAll(newStates) // Menyalin data ke displayedStates
                        stateListAdapter.notifyDataSetChanged()
                    }
                } else {
                    Log.e("API Error", "Error response: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<StatesResponse>, t: Throwable) {
                Log.e("API Error", "Request failed", t)
                t.printStackTrace()
            }
        })
    }
}