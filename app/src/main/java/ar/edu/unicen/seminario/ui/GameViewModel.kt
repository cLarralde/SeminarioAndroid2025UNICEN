package ar.edu.unicen.seminario.ui

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import ar.edu.unicen.seminario.ddl.data.GameRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unicen.seminario.ddl.models.Game
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.IOException
import retrofit2.HttpException
import ar.edu.unicen.seminario.R
import kotlin.collections.isNullOrEmpty


@HiltViewModel
class GameViewModel @Inject constructor(private val gameRepository: GameRepository) : ViewModel() {
    private val _games = MutableStateFlow<List<Game>?>(null)
    val games = _games.asStateFlow()
    private val _game = MutableStateFlow<Game?>(null)
    val game = _game.asStateFlow()
    private val _error = MutableStateFlow<Int?>(null)
    val error = _error.asStateFlow()
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()
    fun getGames(quantity: Int,
                 platform: String? = null,
                 genre: String? = null,
                 order: String? = null) {
        viewModelScope.launch {
            try {
                _loading.value = true
                _error.value = null
                _games.value = null
                val games: List<Game>? = gameRepository.getGames(quantity, platform, genre, order)
                _loading.value = false
                if (games.isNullOrEmpty()) {
                    _error.value = R.string.error
                } else {
                    _games.value = games
                }
            } catch (e: IOException) {
                _error.value = R.string.errorInternet
            } catch (e: HttpException) {
                _error.value = R.string.errorServer
            } catch (e: Exception) {
                _error.value = R.string.error
                e.printStackTrace()
            } finally {
                _loading.value = false
            }
        }
    }

    fun getGame(id: Int) {
        viewModelScope.launch {
            try {
                _loading.value = true
                _error.value = null
                _game.value = null
                val game: Game? = gameRepository.getGame(id)
                _loading.value = false
                if (game == null) {
                    _error.value = R.string.error
                } else {
                    _game.value = game
                }
            } catch (e: IOException) {
                _error.value = R.string.errorInternet
            } catch (e: HttpException) {
                _error.value = R.string.errorServer
            } catch (e: Exception) {
                _error.value = R.string.error
                e.printStackTrace()
            } finally {
                _loading.value = false
            }
        }
    }
}
