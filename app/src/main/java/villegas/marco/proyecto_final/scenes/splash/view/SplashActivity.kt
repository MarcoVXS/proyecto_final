package villegas.marco.proyecto_final.scenes.splash.view

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import villegas.marco.proyecto_final.databinding.ActivitySplashBinding
import villegas.marco.proyecto_final.scenes.splash.viewModel.SplashViewModel

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit  var viewModel: SplashViewModel
    private val TAG = SplashActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        this.configureActivity()
    }

    private fun configureActivity() {
        this.initActivityView()
        this.supportActionBar?.hide() // Esconde la barra de accion
    }

    private fun initActivityView() {
        this.binding = ActivitySplashBinding.inflate(layoutInflater)
        this.setContentView(this.binding.root)
        // Llama la funcion init de la clase MainViewModel
        this.viewModel = SplashViewModel(this, this)
    }
}

