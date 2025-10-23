package ar.edu.unicen.seminario.ddl.data

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import ar.edu.unicen.seminario.ddl.models.Game
import ar.edu.unicen.seminario.ddl.models.GameDetails
import android.util.Log



@Keep
class GameDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("background_image")
    val thumbnail: String,
    @SerializedName("description")
    val description:String,
    @SerializedName("genres")
    val genres:List<GenreDto>,
    @SerializedName("released")
    val released: String,
    @SerializedName("platforms")
    val platforms: List<PlatformDto>) {
    fun toGame(): Game {
        Log.d("DEBUG_TO_GAME", "id=$id, name=$name, thumbnail=$thumbnail, genres=${genres?.map { it.name }}, platforms=${platforms?.map { it.platform?.name }}")

        return Game(
            id = id,
            name = name,
            thumbnail = thumbnail,
            info = GameDetails(
                description = description,
                genres = genres?.mapNotNull { it.name } ?: emptyList(),
                released = released,
                platforms = platforms?.mapNotNull { it.platform?.name } ?: emptyList()
            )
        )
    }

}