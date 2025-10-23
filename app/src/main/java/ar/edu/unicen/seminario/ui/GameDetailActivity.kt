package ar.edu.unicen.seminario.ui
import dagger.hilt.android.AndroidEntryPoint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ar.edu.unicen.seminario.databinding.GamedetailactivityBinding
import ar.edu.unicen.seminario.ddl.models.Game
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.onEach
import kotlin.getValue
import kotlinx.coroutines.flow.launchIn
import android.view.View
import androidx.lifecycle.lifecycleScope
import ar.edu.unicen.seminario.R



@AndroidEntryPoint
class GameDetailActivity : AppCompatActivity() {

    private lateinit var binding: GamedetailactivityBinding
    private val viewModel by viewModels<GameViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = GamedetailactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener el juego del intent
       val id: Int = intent.getIntExtra("id",0)
        viewModel.getGame(id);

        subscribeToViewModel()

        binding.homeButton.setOnClickListener {
            finish()
        }
        binding.retryButton.setOnClickListener {
            viewModel.getGame(id)
        }
    }
    private fun subscribeToViewModel(){
        viewModel.loading.onEach { loading ->
            if(loading){
                binding.progressBar.visibility = android.view.View.VISIBLE
            }else{
                binding.progressBar.visibility = android.view.View.INVISIBLE
            }
        }.launchIn(lifecycleScope)

        viewModel.error.onEach { error ->
            if (error != null) {
                binding.error.visibility = View.VISIBLE
                binding.error.text = getString(error)
                binding.retryButton.visibility = View.VISIBLE

                binding.gameName.visibility = View.GONE
                binding.gameDescription.visibility = View.GONE
                binding.gameReleased.visibility = View.GONE
                binding.gameGenres.visibility = View.GONE
                binding.gamePlatforms.visibility = View.GONE
                binding.homeButton.visibility = View.GONE
            } else {
                binding.error.visibility = View.GONE
                binding.retryButton.visibility = View.GONE

                binding.gameName.visibility = View.VISIBLE
                binding.gameDescription.visibility = View.VISIBLE
                binding.gameReleased.visibility = View.VISIBLE
                binding.gameGenres.visibility = View.VISIBLE
                binding.gamePlatforms.visibility = View.VISIBLE
                binding.homeButton.visibility = View.VISIBLE


            }
        }.launchIn(lifecycleScope)

        viewModel.game.onEach { game ->
            if (game != null) {
                showGameDetails(game)
            }
        }.launchIn(lifecycleScope)
    }
    private fun showGameDetails(game: Game) {
        binding.gameName.text = game.name

        Glide.with(this)
            .load(game.thumbnail)
            .into(binding.imgGame)

        binding.gameDescription.text = game.info?.description ?: getString(R.string.unknown)
        binding.gameReleased.text = getString(R.string.released, game.info?.released ?: getString(R.string.unknown))
        binding.gameGenres.text = getString(R.string.genre, game.info?.genres?.joinToString(", ") ?: getString(R.string.unknown))
        binding.gamePlatforms.text = getString(R.string.platform, game.info?.platforms?.joinToString(", ") ?: getString(R.string.unknown))
    }
}