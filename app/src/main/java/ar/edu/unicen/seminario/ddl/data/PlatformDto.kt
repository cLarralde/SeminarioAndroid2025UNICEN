package ar.edu.unicen.seminario.ddl.data
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
 class PlatformDto ( @SerializedName("platform") val platform: PlatformNameDto) {
}