package villegas.marco.proyecto_final.scenes.main.viewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import villegas.marco.proyecto_final.sharedPreference.SharedPreferenceConstants
import villegas.marco.proyecto_final.sharedPreference.SharedPreferenceManager
import villegas.marco.proyecto_final.dataClass.User
import villegas.marco.proyecto_final.scenes.base.BaseActivity
import villegas.marco.proyecto_final.scenes.main.model.MainModel
import villegas.marco.proyecto_final.scenes.main.router.MainRouter


class MainViewModel(val context: Context, val activity: BaseActivity): ViewModel() {
    // VARIABLES PRIVADAS
    private val TAG = MainViewModel::class.java.simpleName
    private val model = MainModel()
    private val router = MainRouter(context, activity)
    private val sharedPreferenceManager = SharedPreferenceManager(context)

    // Variable que permite obtener el usuario y cambiar su valor desde el model

    var user: User
    // get() -> obtiene el usuario del model
    get() = this.model.user

    set(value) {
        this.model.user = value
    }

    val isValidForm: LiveData<Boolean>
        get() = this.model.isValidForm

    val isValidEmail: LiveData<Boolean>
        get() = this.model.isValidEmail

    val isValidPassword: LiveData<Boolean>
        get() = this.model.isValidPassword

    fun validateForm() {
        Log.i(TAG, "User: ${this.user}")
        this.model.isValidForm.value = !(this.user.email.isEmpty() || this.user.password.isEmpty())
        Log.i(TAG, "isValid: ${this.model.isValidForm.value}")
    }

    // TODO: Agregar observers unicos de usuario y contrasena.

    fun validateEmail() {
        Log.i(TAG, "User: ${this.user}")
        this.model.isValidEmail.value = !this.user.email.isEmpty()
        Log.i(TAG, "isValid: ${this.model.isValidEmail.value}")
        this.validateForm()
    }

    fun validatePassword() {
        Log.i(TAG, "User: ${this.user}")
        this.model.isValidPassword.value = !this.user.password.isEmpty()
        Log.i(TAG, "isValid: ${this.model.isValidPassword.value}")
        this.validateForm()
    }


    // Funcion para iniciar sesion
    fun validateLogin() {
        // Verificar que haya un usuario registrado
        val isRegistered = sharedPreferenceManager.getBoolean(SharedPreferenceConstants.IS_REGISTERED_KEY)
        if (!isRegistered) {
            Toast.makeText(context, "Primero debes registrarte", Toast.LENGTH_SHORT).show()
            return
        }

        // Leer valores del usuario guardados
        val savedEmail = sharedPreferenceManager.getString(SharedPreferenceConstants.USER_EMAIL_KEY)
        val savedPass = sharedPreferenceManager.getString(SharedPreferenceConstants.PASSWORD_KEY)

        // Obtener el input del usuario en el formulario login
        val inputEmail = this.user.email
        val inputPass = this.user.password

        // Comparar valores ingresados en el login con los valores del usuario registrado
        val match = (inputEmail == savedEmail) && (inputPass == savedPass)

        if (!match) {
            Toast.makeText(context, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            return
        }

        val name = sharedPreferenceManager.getString(SharedPreferenceConstants.USER_NAME_KEY)
        this.router.routeToHomeView(name)
    }
}