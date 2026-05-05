package villegas.marco.proyecto_final.scenes.register.model

import androidx.lifecycle.MutableLiveData
import villegas.marco.proyecto_final.dataClass.RegisterUser

// Modelo de registro. Guarda datos, validaciones y mensajes de error.
data class RegisterModel(
    // Datos escritos por el usuario.
    var user: RegisterUser = RegisterUser(),

    // Validaciones individuales por campo.
    val isValidName: MutableLiveData<Boolean> = MutableLiveData(false),
    val isValidEmail: MutableLiveData<Boolean> = MutableLiveData(false),
    val isValidPassword: MutableLiveData<Boolean> = MutableLiveData(false),
    val isValidPasswordConfirm: MutableLiveData<Boolean> = MutableLiveData(false),

    // Validacion general del formulario.
    val isValidForm: MutableLiveData<Boolean> = MutableLiveData(false),

    // Mensajes que se muestran debajo de cada campo cuando algo falla.
    val nameError: MutableLiveData<String?> = MutableLiveData(null),
    val emailError: MutableLiveData<String?> = MutableLiveData(null),
    val passwordError: MutableLiveData<String?> = MutableLiveData(null),
    val passwordConfirmError: MutableLiveData<String?> = MutableLiveData(null),

    // Estados para mostrar loading y saber cuando el registro termino.
    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false),
    val registerSuccess: MutableLiveData<Boolean> = MutableLiveData(false)
)
