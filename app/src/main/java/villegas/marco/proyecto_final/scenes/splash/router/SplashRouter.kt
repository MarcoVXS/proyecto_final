package villegas.marco.proyecto_final.scenes.splash.router

import android.content.Context
import android.content.Intent
import villegas.marco.proyecto_final.scenes.main.view.MainActivity
import villegas.marco.proyecto_final.scenes.splash.view.SplashActivity

// Router del splash. Solo navega hacia el login.
class SplashRouter(val context: Context, val activity: SplashActivity) {
    // Abre MainActivity y cierra el splash.
    fun routeToMainView() {
        this.context.startActivity(Intent(this.context, MainActivity::class.java))
        this.activity.finish()
    }
}
