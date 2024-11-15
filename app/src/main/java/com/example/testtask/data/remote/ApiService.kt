import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("characters")
    suspend fun getCharacters(): Response<List<CharacterModel>>
}
