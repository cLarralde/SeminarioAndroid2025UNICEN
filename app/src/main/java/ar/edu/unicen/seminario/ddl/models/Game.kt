package ar.edu.unicen.seminario.ddl.models

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Game(val id: Int,
           val name: String?,
           val thumbnail: String?,
    val info: GameDetails?):Parcelable{
}