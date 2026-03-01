package villegas.marco.proyecto_final.scenes.register.model

import androidx.lifecycle.MutableLiveData
import villegas.marco.proyecto_final.dataClass.RegisterUser

data class RegisterModel(
    var user: RegisterUser = RegisterUser(),

    // Validaciones por campo
    val isValidName: MutableLiveData<Boolean> = MutableLiveData(false),
    val isValidEmail: MutableLiveData<Boolean> = MutableLiveData(false),
    val isValidPassword: MutableLiveData<Boolean> = MutableLiveData(false),
    val isValidPasswordConfirm: MutableLiveData<Boolean> = MutableLiveData(false),

    // Validación general del formulario
    val isValidForm: MutableLiveData<Boolean> = MutableLiveData(false),

    // Errores
    val nameError: MutableLiveData<String?> = MutableLiveData(null),
    val emailError: MutableLiveData<String?> = MutableLiveData(null),
    val passwordError: MutableLiveData<String?> = MutableLiveData(null),
    val passwordConfirmError: MutableLiveData<String?> = MutableLiveData(null),

    // Loading
    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false),
    val registerSuccess: MutableLiveData<Boolean> = MutableLiveData(false)
)