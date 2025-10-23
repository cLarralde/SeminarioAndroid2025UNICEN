package ar.edu.unicen.seminario.ddl.data
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GameApi {
    @GET("games")
    suspend fun getGames(
        @Query("key") apiKey: String,
        @Query("page_size") pageSize: Int,
        @Query("platforms") platform: String? = null,
        @Query("genres") genre: String? = null,
        @Query("ordering") ordering: String? = null
    ): Response<GamesResponse>

    @GET("games/{id}")
    suspend fun getGame(@Path("id") id: Int, @Query("key") apiKey: String): Response<GameDto>


}


