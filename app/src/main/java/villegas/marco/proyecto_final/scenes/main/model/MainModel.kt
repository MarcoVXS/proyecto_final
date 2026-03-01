package villegas.marco.proyecto_final.scenes.main.model

import androidx.lifecycle.MutableLiveData
import villegas.marco.proyecto_final.dataClass.User

data class MainModel (
    var user: User = User(),
    val isValidForm: MutableLiveData<Boolean> = MutableLiveData<Boolean>(),

    // TODO: Agregar observers unicos de usuario y contrasena.
    val isValidEmail: MutableLiveData<Boolean> = MutableLiveData<Boolean>(),
    val isValidPassword: MutableLiveData<Boolean> = MutableLiveData<Boolean>(),
)