package villegas.marco.proyecto_final.scenes.register.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import villegas.marco.proyecto_final.dataClass.RegisterUser
import villegas.marco.proyecto_final.scenes.base.BaseActivity
import villegas.marco.proyecto_final.scenes.register.model.RegisterModel
import villegas.marco.proyecto_final.sharedPreference.SharedPreferenceConstants
import villegas.marco.proyecto_final.sharedPreference.SharedPreferenceManager

// ViewModel del registro. Valida datos y los guarda en SharedPreferences.
class RegisterViewModel(val context: Context, val activity: BaseActivity) : ViewModel() {

    private val model = RegisterModel()
    private val sharedPreferenceManager = SharedPreferenceManager(context)

    // Permite leer y actualizar el usuario dentro del modelo.
    var user: RegisterUser
        get() = this.model.user
        set(value) { this.model.user = value }

    // Estados observables del formulario.
    val isValidForm: LiveData<Boolean>
        get() = this.model.isValidForm

    val isValidEmail: LiveData<Boolean>
        get() = this.model.isValidEmail

    val isValidName: LiveData<Boolean>
        get() = this.model.isValidName

    val isValidPassword: LiveData<Boolean>
        get() = this.model.isValidPassword

    val isValidPasswordConfirm: LiveData<Boolean>
        get() = this.model.isValidPasswordConfirm

    // Mensajes de error observables para cada campo.
    val emailError: LiveData<String?>
        get() = this.model.emailError

    val nameError: LiveData<String?>
        get() = this.model.nameError

    val passwordError: LiveData<String?>
        get() = this.model.passwordError

    val passwordConfirmError: LiveData<String?>
        get() = this.model.passwordConfirmError

    // Estados para loading y registro exitoso.
    val isLoading: LiveData<Boolean>
        get() = this.model.isLoading

    val registerSuccess: LiveData<Boolean>
        get() = this.model.registerSuccess

    // El formulario solo es valido si todos los campos son validos.
    private fun validateForm() {
        val ok =
            (this.model.isValidEmail.value == true) &&
            (this.model.isValidName.value == true) &&
            (this.model.isValidPassword.value == true) &&
            (this.model.isValidPasswordConfirm.value == true)

        this.model.isValidForm.value = ok
    }

    // Valida que el correo no este vacio y tenga formato de email.
    fun validateEmail() {
        val email = this.user.email.trim()

        when {
            email.isBlank() -> {
                this.model.emailError.value = "El correo es obligatorio"
                this.model.isValidEmail.value = false
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                this.model.emailError.value = "Correo invalido"
                this.model.isValidEmail.value = false
            }
            else -> {
                this.model.emailError.value = null
                this.model.isValidEmail.value = true
            }
        }

        validateForm()
    }

    // Valida que el nombre tenga longitud correcta y solo letras.
    fun validateName() {
        val name = this.user.userName.trim()

        when {
            name.isBlank() -> {
                this.model.nameError.value = "El nombre es obligatorio"
                this.model.isValidName.value = false
            }
            name.length !in 2..30 -> {
                this.model.nameError.value = "Debe tener entre 2 y 30 caracteres"
                this.model.isValidName.value = false
            }
            !Regex("^[\\p{L} ]+$").matches(name) -> {
                this.model.nameError.value = "Solo se permiten letras"
                this.model.isValidName.value = false
            }
            else -> {
                this.model.nameError.value = null
                this.model.isValidName.value = true
            }
        }

        validateForm()
    }

    // Valida que la contrasena sea segura para este ejercicio.
    fun validatePassword() {
        val password = this.user.password.trim()

        when {
            password.isBlank() -> {
                this.model.passwordError.value = "La contrasena es obligatoria"
                this.model.isValidPassword.value = false
            }
            password.length < 8 -> {
                this.model.passwordError.value = "Debe tener al menos 8 caracteres"
                this.model.isValidPassword.value = false
            }
            password.contains(" ") -> {
                this.model.passwordError.value = "No se permiten espacios"
                this.model.isValidPassword.value = false
            }
            else -> {
                val hasUpper = password.any { it.isUpperCase() }
                val hasLower = password.any { it.isLowerCase() }
                val hasDigit = password.any { it.isDigit() }

                if (!(hasUpper && hasLower && hasDigit)) {
                    this.model.passwordError.value = "Incluye mayuscula, minuscula y numero"
                    this.model.isValidPassword.value = false
                } else {
                    this.model.passwordError.value = null
                    this.model.isValidPassword.value = true
                }
            }
        }

        // Confirmar contrasena depende de la contrasena principal.
        validatePasswordConfirm()
        validateForm()
    }

    // Valida que la confirmacion coincida con la contrasena.
    fun validatePasswordConfirm() {
        val password = this.user.password.trim()
        val confirm = this.user.passwordConfirm.trim()

        when {
            confirm.isBlank() -> {
                this.model.passwordConfirmError.value = "Confirma la contrasena"
                this.model.isValidPasswordConfirm.value = false
            }
            password != confirm -> {
                this.model.passwordConfirmError.value = "Las contrasenas no coinciden"
                this.model.isValidPasswordConfirm.value = false
            }
            else -> {
                this.model.passwordConfirmError.value = null
                this.model.isValidPasswordConfirm.value = true
            }
        }

        validateForm()
    }

    // Guarda el usuario registrado y simula una pequena carga.
    fun register() {
        if (this.model.isValidForm.value != true) return

        this.model.isLoading.value = true
        this.model.registerSuccess.value = false

        // Datos que despues usa el login para validar acceso.
        this.sharedPreferenceManager.setString(SharedPreferenceConstants.PASSWORD_KEY, user.password)
        this.sharedPreferenceManager.setString(SharedPreferenceConstants.USER_NAME_KEY, user.userName)
        this.sharedPreferenceManager.setString(SharedPreferenceConstants.USER_EMAIL_KEY, user.email)
        this.sharedPreferenceManager.setBoolean(SharedPreferenceConstants.IS_REGISTERED_KEY, true)

        viewModelScope.launch {
            delay(1200)
            this@RegisterViewModel.model.isLoading.value = false
            this@RegisterViewModel.model.registerSuccess.value = true
        }
    }
}
