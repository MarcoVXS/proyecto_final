package villegas.marco.proyecto_final.scenes.main.viewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import villegas.marco.proyecto_final.dataClass.User
import villegas.marco.proyecto_final.scenes.base.BaseActivity
import villegas.marco.proyecto_final.scenes.main.model.MainModel
import villegas.marco.proyecto_final.scenes.main.router.MainRouter
import villegas.marco.proyecto_final.sharedPreference.SharedPreferenceConstants
import villegas.marco.proyecto_final.sharedPreference.SharedPreferenceManager

// ViewModel del login. Valida entradas y decide si el usuario puede entrar.
class MainViewModel(val context: Context, val activity: BaseActivity): ViewModel() {
    private val TAG = MainViewModel::class.java.simpleName
    private val model = MainModel()
    private val router = MainRouter(context, activity)
    private val sharedPreferenceManager = SharedPreferenceManager(context)

    // Permite leer y actualizar el usuario guardado en el modelo.
    var user: User
        get() = this.model.user
        set(value) {
            this.model.user = value
        }

    // Estado general del formulario.
    val isValidForm: LiveData<Boolean>
        get() = this.model.isValidForm

    // Estado del campo correo.
    val isValidEmail: LiveData<Boolean>
        get() = this.model.isValidEmail

    // Estado del campo contrasena.
    val isValidPassword: LiveData<Boolean>
        get() = this.model.isValidPassword

    // Revisa si correo y contrasena tienen texto.
    fun validateForm() {
        Log.i(TAG, "User: ${this.user}")
        this.model.isValidForm.value = !(this.user.email.isEmpty() || this.user.password.isEmpty())
        Log.i(TAG, "isValid: ${this.model.isValidForm.value}")
    }

    // Valida el campo de correo y despues recalcula todo el formulario.
    fun validateEmail() {
        Log.i(TAG, "User: ${this.user}")
        this.model.isValidEmail.value = this.user.email.isNotEmpty()
        Log.i(TAG, "isValid: ${this.model.isValidEmail.value}")
        this.validateForm()
    }

    // Valida el campo de contrasena y despues recalcula todo el formulario.
    fun validatePassword() {
        Log.i(TAG, "User: ${this.user}")
        this.model.isValidPassword.value = this.user.password.isNotEmpty()
        Log.i(TAG, "isValid: ${this.model.isValidPassword.value}")
        this.validateForm()
    }

    // Compara los datos del login contra los datos guardados en registro.
    fun validateLogin() {
        // Primero se revisa que exista un usuario registrado.
        val isRegistered = sharedPreferenceManager.getBoolean(SharedPreferenceConstants.IS_REGISTERED_KEY)
        if (!isRegistered) {
            Toast.makeText(context, "Primero debes registrarte", Toast.LENGTH_SHORT).show()
            return
        }

        // Datos guardados cuando el usuario se registro.
        val savedEmail = sharedPreferenceManager.getString(SharedPreferenceConstants.USER_EMAIL_KEY)
        val savedPass = sharedPreferenceManager.getString(SharedPreferenceConstants.PASSWORD_KEY)

        // Datos escritos en el login.
        val inputEmail = this.user.email
        val inputPass = this.user.password

        // Solo entra si correo y contrasena coinciden.
        val match = (inputEmail == savedEmail) && (inputPass == savedPass)
        if (!match) {
            Toast.makeText(context, "Usuario o contrasena incorrectos", Toast.LENGTH_SHORT).show()
            return
        }

        val name = sharedPreferenceManager.getString(SharedPreferenceConstants.USER_NAME_KEY)
        this.router.routeToHomeView(name)
    }
}
