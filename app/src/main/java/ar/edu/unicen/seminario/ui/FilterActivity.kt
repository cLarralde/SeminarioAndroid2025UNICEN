package ar.edu.unicen.seminario.ui

import androidx.appcompat.app.AppCompatActivity
import ar.edu.unicen.seminario.databinding.FilteractivityBinding
import android.os.Bundle
import android.widget.ArrayAdapter
import dagger.hilt.android.AndroidEntryPoint
import android.app.Activity
import android.content.Intent
import ar.edu.unicen.seminario.R



@AndroidEntryPoint
class FilterActivity : AppCompatActivity() {
    private lateinit var binding: FilteractivityBinding
    private lateinit var platformOptions: Array<String>
    private lateinit var genreOptions: Array<String>
    private lateinit var orderOptions: Array<String>
    private lateinit var platformMap: Map<String, String?>
    private lateinit var genreMap: Map<String, String?>
    private lateinit var orderMap: Map<String, String?>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FilteractivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // carga las opciones del string.xml
        platformOptions = resources.getStringArray(R.array.platform_options)
        genreOptions = resources.getStringArray(R.array.genre_options)
        orderOptions = resources.getStringArray(R.array.order_options)

        // carga de las opciones de plataforma y genero un mapa para definir los IDs de la API
        platformMap = mapOf(
            platformOptions[0] to null,   // Todas
            platformOptions[1] to "4",    // PC
            platformOptions[2] to "187",  // PS5
            platformOptions[3] to "18",   // PS4
            platformOptions[4] to "1",    // Xbox One
            platformOptions[5] to "186",  // Xbox Series X
            platformOptions[6] to "7",    // Switch
            platformOptions[7] to "3"     // iOS
        )

        genreMap = mapOf(
            genreOptions[0] to null,      // Todos
            genreOptions[1] to "4",       // Accion
            genreOptions[2] to "3",       // Aventura
            genreOptions[3] to "15",      // Deporte
            genreOptions[4] to "10",      // Estrategia
            genreOptions[5] to "5",       // RPG
            genreOptions[6] to "14",      // Simulacion
            genreOptions[7] to "7",       // Puzzle
            genreOptions[8] to null       // Otros
        )

        orderMap = mapOf(
            orderOptions[0] to "name",
            orderOptions[1] to "released"
        )

        setupSpinners()
        setupApplyButton()
    }

    private fun setupSpinners() {
        val platAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, platformOptions)
        platAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPlatform.adapter = platAdapter

        val genreAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genreOptions)
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerGenre.adapter = genreAdapter

        val orderAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, orderOptions)
        orderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerOrdering.adapter = orderAdapter

    }

    private fun setupApplyButton() {
        binding.btnApplyFilters.setOnClickListener {

            val selectedPlatform = binding.spinnerPlatform.selectedItem.toString()
            val selectedGenre = binding.spinnerGenre.selectedItem.toString()
            val selectedOrder = binding.spinnerOrdering.selectedItem.toString()

            val resultIntent = Intent().apply {
                putExtra("platform", platformMap[selectedPlatform])
                putExtra("genre", genreMap[selectedGenre])
                putExtra("ordering", orderMap[selectedOrder])
            }

            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
