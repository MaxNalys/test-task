class CharacterRepository(private val apiService: ApiService) {
    suspend fun getCharacters(): List<CharacterModel> {
        val response = apiService.getCharacters()
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            throw Exception("Failed to fetch characters: ${response.code()}")
        }
    }
}
