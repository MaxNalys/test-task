import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers

class CharacterViewModel(private val repository: CharacterRepository) : ViewModel() {
    val characters = liveData(Dispatchers.IO) {
        emit(repository.getCharacters())
    }
}