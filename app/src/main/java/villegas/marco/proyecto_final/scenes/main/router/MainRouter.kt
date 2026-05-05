package villegas.marco.proyecto_final.scenes.main.router

import android.content.Context
import android.content.Intent
import villegas.marco.proyecto_final.scenes.base.BaseActivity
import villegas.marco.proyecto_final.scenes.home.view.HomeActivity

// Router del login. Se encarga de cambiar de pantalla.
class MainRouter(val context: Context, val activity: BaseActivity) {
    // Navega a Home y manda el nombre del usuario.
    fun routeToHomeView(name: String) {
        val intent = Intent(this.context, HomeActivity::class.java)
        intent.putExtra("EXTRA_USER_NAME", name)
        this.context.startActivity(intent)

        // Cierra el login para que el usuario no regrese con back.
        this.activity.finish()
    }
}
