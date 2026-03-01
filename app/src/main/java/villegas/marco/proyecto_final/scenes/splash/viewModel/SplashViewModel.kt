package villegas.marco.proyecto_final.scenes.splash.viewModel

import android.content.Context
import android.os.Handler
import android.util.Log
import androidx.lifecycle.ViewModel
import villegas.marco.proyecto_final.scenes.splash.router.SplashRouter
import villegas.marco.proyecto_final.scenes.splash.view.SplashActivity

class SplashViewModel(context: Context, activity: SplashActivity): ViewModel() {

    private val TAG = SplashViewModel::class.java.simpleName
    private var router = SplashRouter(context, activity)


    init {
        countdown()
    }

    // Metodo que se ejecuta cuando se inicializa el ViewModel
    private fun countdown() {
        Log.i(TAG, "Inicia cuenta regresiva")
        Handler().postDelayed({
            Log.i(TAG, "Navegamos al login")
            this.router.routeToMainView()
        }, 2500)
    }
}