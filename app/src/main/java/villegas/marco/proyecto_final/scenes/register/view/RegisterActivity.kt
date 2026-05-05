package villegas.marco.proyecto_final.scenes.register.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import villegas.marco.proyecto_final.databinding.ActivityRegisterBinding
import villegas.marco.proyecto_final.scenes.base.BaseActivity
import villegas.marco.proyecto_final.scenes.register.viewModel.RegisterViewModel

// Pantalla donde el usuario crea una cuenta.
class RegisterActivity : BaseActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.configureActivity()
    }

    // Agrupa la configuracion inicial.
    private fun configureActivity() {
        this.initActivityView()
        this.configureListeners()
        this.initComponents()
        this.setObservers()
    }

    // Conecta ViewBinding y crea el ViewModel.
    private fun initActivityView() {
        this.binding = ActivityRegisterBinding.inflate(layoutInflater)
        this.setContentView(this.binding.root)
        this.viewModel = RegisterViewModel(this, this)
    }

    // Estado inicial de boton y progress bar.
    private fun initComponents() {
        this.binding.btnRegister.isEnabled = false
        this.binding.pbLoading.visibility = View.GONE
    }

    // Observa los estados del ViewModel para actualizar la interfaz.
    private fun setObservers() {
        this.viewModel.isValidForm.observe(this, Observer { isValid ->
            this.binding.btnRegister.isEnabled = isValid && (this.viewModel.isLoading.value != true)
        })

        this.viewModel.isValidEmail.observe(this, Observer { isValid ->
            this.binding.etEmail.error = if (isValid) null else this.viewModel.emailError.value
        })

        this.viewModel.isValidName.observe(this, Observer { isValid ->
            this.binding.etName.error = if (isValid) null else this.viewModel.nameError.value
        })

        this.viewModel.isValidPassword.observe(this, Observer { isValid ->
            this.binding.tilPassword.error = if (isValid) null else this.viewModel.passwordError.value
        })

        this.viewModel.isValidPasswordConfirm.observe(this, Observer { isValid ->
            this.binding.tilConfirmPassword.error = if (isValid) null else this.viewModel.passwordConfirmError.value
        })

        this.viewModel.isLoading.observe(this, Observer { loading ->
            this.binding.pbLoading.visibility = if (loading) View.VISIBLE else View.GONE
            this.binding.btnRegister.isEnabled = (this.viewModel.isValidForm.value == true) && !loading
        })

        this.viewModel.registerSuccess.observe(this, Observer { success ->
            if (success == true) {
                finish()
            }
        })
    }

    // Configura boton de regreso, campos y boton de registro.
    private fun configureListeners() {
        this.binding.ivBack.setOnClickListener { finish() }

        this.binding.etEmail.addTextChangedListener {
            this.viewModel.user.email = it.toString()
            this.viewModel.validateEmail()
        }

        this.binding.etName.addTextChangedListener {
            this.viewModel.user.userName = it.toString()
            this.viewModel.validateName()
        }

        this.binding.etPassword.addTextChangedListener {
            this.viewModel.user.password = it.toString()
            this.viewModel.validatePassword()
        }

        this.binding.etConfirmPassword.addTextChangedListener {
            this.viewModel.user.passwordConfirm = it.toString()
            this.viewModel.validatePasswordConfirm()
        }

        this.binding.btnRegister.setOnClickListener {
            this.viewModel.register()
            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
        }
    }
}
