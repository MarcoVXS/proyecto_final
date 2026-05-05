package villegas.marco.proyecto_final.scenes.home.model

import androidx.lifecycle.MutableLiveData

// Modelo de Home para guardar datos observables de la pantalla.
data class HomeModel (
    // Nombre que se muestra en el saludo del usuario.
    val name: MutableLiveData<String> = MutableLiveData<String>()
)
