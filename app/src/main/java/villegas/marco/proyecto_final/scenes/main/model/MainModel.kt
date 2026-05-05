package villegas.marco.proyecto_final.scenes.main.model

import androidx.lifecycle.MutableLiveData
import villegas.marco.proyecto_final.dataClass.User

// Modelo del login. Guarda el usuario y los estados de validacion.
data class MainModel (
    // Datos que el usuario escribe en el formulario.
    var user: User = User(),

    // Indica si todo el formulario esta listo para iniciar sesion.
    val isValidForm: MutableLiveData<Boolean> = MutableLiveData<Boolean>(),

    // Indica si el correo tiene contenido.
    val isValidEmail: MutableLiveData<Boolean> = MutableLiveData<Boolean>(),

    // Indica si la contrasena tiene contenido.
    val isValidPassword: MutableLiveData<Boolean> = MutableLiveData<Boolean>(),
)
