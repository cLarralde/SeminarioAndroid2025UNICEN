package ar.edu.unicen.seminario.ui
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import ar.edu.unicen.seminario.databinding.GameactivityBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import android.content.Intent
import android.app.Activity
import android.content.res.Configuration
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager



@AndroidEntryPoint
class GameActivity : AppCompatActivity() {
    private lateinit var binding: GameactivityBinding
    private val viewModel by viewModels<GameViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = GameactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //abajo para modificar la lista de juegos segun la orientacion del celular
        val recyclerView = binding.gameslist
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.layoutManager = GridLayoutManager(this, 2)
        } else {
            recyclerView.layoutManager = LinearLayoutManager(this)
        }

        subscribeToUi()
        subscribeToViewModel()
    }

    private fun subscribeToUi() {
        binding.loadGamesButton.setOnClickListener {
            viewModel.getGames(20)
        }
        // Botón para navegar a pantalla de filtros
        binding.filterButton.setOnClickListener {
            val intent = Intent(this, FilterActivity::class.java)
            //Si se aprieta el boton para filtros, se genera un intentcon el codigo 123 para luego detectar cuando finalice el activity
            //se capte la funcion onActivityResult detallando que la activity fue la de filtros y se pueden aplicar los mismos
            startActivityForResult(intent, 123)
        }
        binding.retryButton.setOnClickListener {
            viewModel.getGames(20)

            binding.loadGamesButton.visibility = View.VISIBLE
            binding.filterButton.visibility = View.VISIBLE

            binding.error.visibility = View.GONE
            binding.retryButton.visibility = View.GONE
        }

    }
    private fun subscribeToViewModel() {
    viewModel.loading.onEach { loading ->
        if(loading){
            binding.progressBar.visibility = android.view.View.VISIBLE
        }else{
            binding.progressBar.visibility = android.view.View.INVISIBLE
        }
        binding.loadGamesButton.isEnabled = !loading
        }.launchIn(lifecycleScope)
        viewModel.games.onEach { games ->

            binding.gameslist.adapter = GameAdapter(games= games?: emptyList(), onGameClick = { game ->
                val intent = Intent(this, GameDetailActivity::class.java)
                //le envio la id del juego seleccionado
                intent.putExtra("id", game.id)
                startActivity(intent)

            }
            )
        }.launchIn(lifecycleScope)
        viewModel.error.onEach { error ->
            if (error != null) {
                binding.error.visibility = View.VISIBLE
                binding.retryButton.visibility = View.VISIBLE
                binding.loadGamesButton.visibility= View.GONE
                binding.filterButton.visibility = View.GONE
                binding.error.text = getString(error)
            } else {
                binding.error.visibility = View.INVISIBLE
                binding.retryButton.visibility = View.INVISIBLE
                binding.filterButton.visibility = View.VISIBLE
                binding.loadGamesButton.visibility= View.VISIBLE
            }
        }.launchIn(lifecycleScope)
    }
    //El requestcode 123 corresponde a la activity de filtros, cuando la misma se destruye se entra en esta funcion
    //donde obtiene lo seleccionado por el usuario y se llama a getGames con los filtros seleccionados
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123 && resultCode == Activity.RESULT_OK) {
            val platform = data?.getStringExtra("platform")
            val genre = data?.getStringExtra("genre")
            val ordering = data?.getStringExtra("ordering")
            viewModel.getGames(20, platform, genre, ordering)
        }
    }

}