package villegas.marco.proyecto_final.scenes.home.view

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.Fragment
import villegas.marco.proyecto_final.R
import villegas.marco.proyecto_final.databinding.ActivityHomeBinding
import villegas.marco.proyecto_final.scenes.base.BaseActivity
import villegas.marco.proyecto_final.scenes.home.viewModel.HomeViewModel

class HomeActivity: BaseActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit  var viewModel: HomeViewModel
    private val TAG = HomeActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        this.configureActivity()
    }

    private fun configureActivity() {
        this.initActivityView()
        this.configureListeners()
    }

    private fun initActivityView() {
        this.binding = ActivityHomeBinding.inflate(layoutInflater)
        this.setContentView(this.binding.root)
        this.viewModel = HomeViewModel(this, this)
    }

    private fun configureListeners() {

    }
}