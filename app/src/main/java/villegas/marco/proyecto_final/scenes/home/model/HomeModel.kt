package villegas.marco.proyecto_final.scenes.home.model

import androidx.lifecycle.MutableLiveData

data class HomeModel (
    val name: MutableLiveData<String> = MutableLiveData<String>()
)