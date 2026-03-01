package villegas.marco.proyecto_final.scenes.splash.router

import android.content.Context
import android.content.Intent
import villegas.marco.proyecto_final.scenes.main.view.MainActivity
import villegas.marco.proyecto_final.scenes.splash.view.SplashActivity

class SplashRouter(val context: Context, val activity: SplashActivity) {
    // Navegar al login de la app
    fun routeToMainView() {
        // Levanta otra actividad
        this.context.startActivity(Intent(this.context, MainActivity::class.java))

        // Finaliza la actividad actual para no poder retroceder
        this.activity.finish()
    }
}