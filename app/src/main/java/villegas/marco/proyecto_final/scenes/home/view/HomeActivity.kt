package villegas.marco.proyecto_final.scenes.home.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import villegas.marco.proyecto_final.R
import villegas.marco.proyecto_final.databinding.ActivityHomeBinding
import villegas.marco.proyecto_final.scenes.base.BaseActivity
import villegas.marco.proyecto_final.scenes.home.viewModel.HomeViewModel
import villegas.marco.proyecto_final.scenes.profile.ProfileFragment

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
        binding.navHome.setOnClickListener {
            showHome()
        }

        binding.navProfile.setOnClickListener {
            showProfile()
        }

        // Estado inicial
        showHome()
    }

    // Mostrar pantalla de inicio
    private fun showHome() {
        binding.nsvContent.visibility = android.view.View.VISIBLE
        binding.flFragmentContainer.visibility = android.view.View.GONE
        setBottomNavSelected(isHomeSelected = true)
    }

    // Mostrar pantalla de perfil de usuario
    private fun showProfile() {
        binding.nsvContent.visibility = android.view.View.GONE
        binding.flFragmentContainer.visibility = android.view.View.VISIBLE

        // Cargar fragment solo si aun no esta cargado
        val current = supportFragmentManager.findFragmentById(R.id.fl_fragment_container)
        if (current !is ProfileFragment) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fl_fragment_container, ProfileFragment())
                .commit()
        }

        setBottomNavSelected(isHomeSelected = false)
    }

    // Cambia la apariencia de los botones de navegacion cuando el usuario interactua con ellos
    private fun setBottomNavSelected(isHomeSelected: Boolean) {
        val primary = ContextCompat.getColor(this, R.color.primary)
        val inactive = ContextCompat.getColor(this, R.color.text_hint)

        // Tint para textos
        binding.tvNavHome.setTextColor(if (isHomeSelected) primary else inactive)
        binding.tvNavProfile.setTextColor(if (isHomeSelected) inactive else primary)

        // Tint para iconos
        binding.ivNavHome.setColorFilter(if (isHomeSelected) primary else inactive)
        binding.ivNavProfile.setColorFilter(if (isHomeSelected) inactive else primary)
    }
}