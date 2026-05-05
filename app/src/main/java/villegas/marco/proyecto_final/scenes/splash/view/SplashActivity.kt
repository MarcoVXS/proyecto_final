package villegas.marco.proyecto_final.scenes.splash.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import villegas.marco.proyecto_final.databinding.ActivitySplashBinding
import villegas.marco.proyecto_final.scenes.splash.viewModel.SplashViewModel

// Pantalla inicial que se muestra antes del login.
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        this.configureActivity()
    }

    // Configura la vista del splash.
    private fun configureActivity() {
        this.initActivityView()
        this.supportActionBar?.hide()
    }

    // Infla el layout e inicia el ViewModel que controla el tiempo de espera.
    private fun initActivityView() {
        this.binding = ActivitySplashBinding.inflate(layoutInflater)
        this.setContentView(this.binding.root)
        this.viewModel = SplashViewModel(this, this)
    }
}
