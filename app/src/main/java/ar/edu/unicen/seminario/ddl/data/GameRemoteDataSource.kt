package ar.edu.unicen.seminario.ddl.data
import ar.edu.unicen.seminario.ddl.models.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import ar.edu.unicen.seminario.BuildConfig
import java.io.IOException



class GameRemoteDataSource @Inject constructor(private val gameApi: GameApi){
    suspend fun getGame(id:Int): Game?{
        return withContext(Dispatchers.IO){
            try {
                val response: Response<GameDto> = gameApi.getGame(id, BuildConfig.API_KEY)
                val game: Game? = response.body()?.toGame()
                return@withContext game
            }catch (e: IOException) {
                throw e
            }
        }
    }
    suspend fun getGames(quantity: Int,
                                 platform: String? = null,
                                 genre: String? = null,
                                 order: String? = null): List<Game>? {
                    return withContext(Dispatchers.IO) {
                    try {
                        val response:Response<GamesResponse> = gameApi.getGames(BuildConfig.API_KEY, quantity,platform,genre,order)
                        val games: List<Game>? = response.body()?.results?.map { it.toGame()  }?.filter {
                            //para evitar mostrar juegos sin nombre ni portada
                            !it.name.isNullOrBlank() && !it.thumbnail.isNullOrBlank() 
                        }
                        return@withContext games
                    } catch (e: IOException) {
                        throw e
                    }
                }
    }
}


