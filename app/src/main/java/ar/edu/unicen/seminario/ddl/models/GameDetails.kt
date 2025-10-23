package ar.edu.unicen.seminario.ddl.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameDetails(val description:String?,
                  val genres:List<String>?,
                  val released: String?,
                  val platforms: List<String>?): Parcelable {
}