package ar.edu.unicen.seminario.ddl.data

import ar.edu.unicen.seminario.ddl.models.Game
import javax.inject.Inject
import java.io.IOException




class GameRepository @Inject constructor(private val gameRemoteDataSource: GameRemoteDataSource){
    suspend fun getGame(id: Int): Game?{
        try {
            return gameRemoteDataSource.getGame(id)
        }catch (e: IOException){
            throw e;
        }
    }
    suspend fun getGames(
        quantity: Int,
        platform: String? = null,
        genre: String? = null,
        order: String? = null
    ): List<Game>? {

        try {
            return gameRemoteDataSource.getGames(
                quantity,
                platform,
                genre,
                order
            )
        }catch (e: IOException){
            throw e;
        }
    }
}

