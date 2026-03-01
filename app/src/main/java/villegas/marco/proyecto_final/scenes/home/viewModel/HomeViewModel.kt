package villegas.marco.proyecto_final.scenes.home.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import villegas.marco.proyecto_final.scenes.home.model.HomeModel
import villegas.marco.proyecto_final.scenes.home.view.HomeActivity
import villegas.marco.proyecto_final.scenes.main.viewModel.MainViewModel

class HomeViewModel(val context: Context, val activity: HomeActivity): ViewModel() {
    // VARIABLES PRIVADAS
    private val TAG = MainViewModel::class.java.simpleName
    private val model = HomeModel()

    // Funciona como las propiedades estaticas en JAVA
    companion object {
        val PARAM_NAME = "PARAM_NAME"
    }

    init {
        this.model.name.value = (this.activity.intent.getSerializableExtra(PARAM_NAME) as? String) ?: ""
    }

    val name: LiveData<String>
        get() = this.model.name
}