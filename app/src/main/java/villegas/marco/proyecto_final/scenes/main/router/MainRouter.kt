package villegas.marco.proyecto_final.scenes.main.router

import android.content.Context
import android.content.Intent
import villegas.marco.proyecto_final.scenes.base.BaseActivity
import villegas.marco.proyecto_final.scenes.home.view.HomeActivity
import villegas.marco.proyecto_final.scenes.home.viewModel.HomeViewModel

class MainRouter(val context: Context, val activity: BaseActivity) {
    private val TAG = this::class.java.simpleName

    fun routeToHomeView(name: String) {
        // Activity a la que queremos llamar para viajar ahi
        val intent = Intent(this.context, HomeActivity::class.java)
        // PutExtra -> Envio de parametros
        intent.putExtra(HomeViewModel.PARAM_NAME, name)
        this.context.startActivity(intent)

        this.activity.finish()
    }
}