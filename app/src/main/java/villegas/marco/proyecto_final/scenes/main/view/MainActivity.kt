package villegas.marco.proyecto_final.scenes.main.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import villegas.marco.proyecto_final.databinding.ActivityMainBinding
import villegas.marco.proyecto_final.scenes.base.BaseActivity
import villegas.marco.proyecto_final.scenes.main.viewModel.MainViewModel
import villegas.marco.proyecto_final.scenes.register.view.RegisterActivity

// Pantalla de login de la app.
class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        this.configureActivity()
    }

    // Ordena la configuracion inicial de la pantalla.
    private fun configureActivity() {
        this.initActivityView()
        this.configureListeners()
        this.initComponents()
        this.setObservers()
    }

    // Conecta ViewBinding y crea el ViewModel.
    private fun initActivityView() {
        this.binding = ActivityMainBinding.inflate(layoutInflater)
        this.setContentView(this.binding.root)
        this.viewModel = MainViewModel(this, this)
    }

    // El boton inicia deshabilitado hasta que el formulario sea valido.
    private fun initComponents() {
        this.binding.btnLogin.isEnabled = false
    }

    // Observa los LiveData del ViewModel para actualizar errores y boton.
    private fun setObservers () {
        this.viewModel.isValidForm.observe(this, Observer { isValid ->
            this.binding.btnLogin.isEnabled = isValid
        })

        this.viewModel.isValidEmail.observe(this, Observer { isValid ->
            this.binding.btnLogin.isEnabled = isValid
            this.binding.etEmail.error = if (isValid) null else "El correo electronico es requerido"
        })

        this.viewModel.isValidPassword.observe(this, Observer { isValid ->
            this.binding.btnLogin.isEnabled = isValid
            this.binding.etPassword.error = if (isValid) null else "La contrasena es requerida"
        })
    }

    // Configura clicks y cambios de texto del formulario.
    private fun configureListeners() {
        this.binding.btnLogin.setOnClickListener {
            this.viewModel.validateLogin()
        }

        this.binding.etEmail.addTextChangedListener {
            this.viewModel.user.email = it.toString().trim()
            this.viewModel.validateEmail()
        }

        this.binding.etPassword.addTextChangedListener {
            this.viewModel.user.password = it.toString().trim()
            this.viewModel.validatePassword()
        }

        this.binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
