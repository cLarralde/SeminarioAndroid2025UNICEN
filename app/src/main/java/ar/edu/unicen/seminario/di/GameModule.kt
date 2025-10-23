package ar.edu.unicen.seminario.di
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ar.edu.unicen.seminario.ddl.data.GameApi
import ar.edu.unicen.seminario.BuildConfig

@Module
@InstallIn(SingletonComponent::class)
class GameModule {
    @Provides
    fun providesRetrofit(): Retrofit{
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).addConverterFactory(
            GsonConverterFactory.create())
            .build()
    }
    @Provides
    fun providesGameApi(retrofit: Retrofit): GameApi{
        return retrofit.create(GameApi::class.java)
    }
}