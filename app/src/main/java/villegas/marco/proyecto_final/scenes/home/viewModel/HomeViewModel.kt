package villegas.marco.proyecto_final.scenes.home.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import villegas.marco.proyecto_final.scenes.home.model.HomeModel
import villegas.marco.proyecto_final.scenes.home.view.HomeActivity

// ViewModel de Home. Mantiene datos de la pantalla separados de la Activity.
class HomeViewModel(val context: Context, val activity: HomeActivity): ViewModel() {
    private val model = HomeModel()

    // Nombre de la llave que puede venir en el Intent.
    companion object {
        const val PARAM_NAME = "PARAM_NAME"
    }

    init {
        // Lee el nombre recibido por Intent y usa texto vacio si no existe.
        this.model.name.value = (this.activity.intent.getSerializableExtra(PARAM_NAME) as? String) ?: ""
    }

    // LiveData para que la vista pueda observar el nombre.
    val name: LiveData<String>
        get() = this.model.name
}
