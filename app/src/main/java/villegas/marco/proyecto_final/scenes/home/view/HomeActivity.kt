package villegas.marco.proyecto_final.scenes.home.view

import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import villegas.marco.proyecto_final.R
import villegas.marco.proyecto_final.dataClass.Category
import villegas.marco.proyecto_final.dataClass.Recipe
import villegas.marco.proyecto_final.databinding.ActivityHomeBinding
import villegas.marco.proyecto_final.scenes.base.BaseActivity
import villegas.marco.proyecto_final.scenes.home.adapter.RecipeAdapter
import villegas.marco.proyecto_final.scenes.home.viewModel.HomeViewModel
import villegas.marco.proyecto_final.scenes.profile.ProfileFragment
import villegas.marco.proyecto_final.sharedPreference.SharedPreferenceConstants
import villegas.marco.proyecto_final.sharedPreference.SharedPreferenceManager

class HomeActivity: BaseActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit  var viewModel: HomeViewModel
    private lateinit var recipeAdapter: RecipeAdapter
    private var allRecipes: List<Recipe> = emptyList()
    private val TAG = HomeActivity::class.java.simpleName

    private companion object {
        const val EXTRA_USER_NAME = "EXTRA_USER_NAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Para probar antes de implementar API
        allRecipes = listOf(
            Recipe("seafood1", "Spanish Seafood Rice", 15, 240, 4.8, R.drawable.spanish_seafood_rice, Category.SEAFOOD),
            Recipe("seafood2", "Seafood Fideuà", 25, 410, 4.9, R.drawable.seafood_fideua, Category.SEAFOOD),
            Recipe("seafood3", "Seafood Rice", 10, 190, 4.5, R.drawable.seafood_rice, Category.SEAFOOD),

            Recipe("beef1", "Massaman Beef Curry", 20, 520, 4.7, R.drawable.beef_curry, Category.BEEF),
            Recipe("beef2", "Beef Lo Mein", 18, 430, 4.6, R.drawable.beef_lomein, Category.BEEF),
            Recipe("beef3", "Beef Empanadas", 20, 475, 4.5, R.drawable.beef_empanadas, Category.BEEF),

            Recipe("chicken1", "Teriyaki Chicken Casserole", 15, 320, 4.8, R.drawable.chicken_teriyaki, Category.CHICKEN),
            Recipe("chicken2", "Vietnamese chicken salad", 10, 280, 4.7, R.drawable.chicken_salad, Category.CHICKEN),
            Recipe("chicken3", "Thai green chicken soup", 12, 195, 4.9, R.drawable.chicken_soup, Category.CHICKEN),
        )

        this.configureActivity()
    }

    override fun onResume() {
        super.onResume()
        updateAvatarFromPrefs()
    }

    private fun configureActivity() {
        this.initActivityView()
        this.configureListeners()
    }

    private fun initActivityView() {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateGreeting()

        // RecyclerView setup
        recipeAdapter = RecipeAdapter()
        binding.rvRecipes.layoutManager = LinearLayoutManager(this)
        binding.rvRecipes.adapter = recipeAdapter

        applyCategory(Category.SEAFOOD)

        viewModel = HomeViewModel(this, this)
    }

    private fun configureListeners() {
        binding.navHome.setOnClickListener {
            showHome()
        }

        binding.navProfile.setOnClickListener {
            showProfile()
        }

        // Chips listener para cambiar categoria cuando usuario interactua
        binding.cgCategories.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.chipSeafood -> applyCategory(Category.SEAFOOD)
                R.id.chipBeef -> applyCategory(Category.BEEF)
                R.id.chipChicken -> applyCategory(Category.CHICKEN)
            }
        }

        // Estado inicial
        showHome()
    }

    private fun updateGreeting() {
        // Intentar agarrar el nombre de usuario mediante parametros de router intent
        val nameFromIntent = intent.getStringExtra(EXTRA_USER_NAME)?.trim().orEmpty()

        // Fallback a SharedPreferences si viene vacío
        val name = if (nameFromIntent.isNotBlank()) {
            nameFromIntent
        } else {
            SharedPreferenceManager(this)
                .getString(SharedPreferenceConstants.USER_NAME_KEY)
                .trim()
        }

        this.binding.tvGreeting.text = if (name.isNotBlank()) {
            "Hola, $name \uD83D\uDC4B"
        } else {
            // text fijo como fallback si no se encuentra el nombre de usuario
            getString(R.string.home_greeting)
        }
    }

    // Intenta asignar foto de perfil guardada en sharedPreference al avatar de inicio
    private fun updateAvatarFromPrefs() {
        val prefs = SharedPreferenceManager(this)
        val savedUri = prefs.getString(SharedPreferenceConstants.PROFILE_PHOTO_URI_KEY)
        if (savedUri.isNotBlank()) {
            binding.ivAvatar.setImageURI(Uri.parse(savedUri))
        } else {
            binding.ivAvatar.setImageResource(R.drawable.ic_person) // default
        }
    }

    // Filtra la lista asociada a la categoria seleccionada
    private fun applyCategory(category: Category) {
        val filtered = allRecipes.filter { it.category == category }
        recipeAdapter.submitList(filtered)
    }

    // Mostrar pantalla de inicio
    private fun showHome() {
        this.binding.rvRecipes.visibility = android.view.View.VISIBLE
        this.binding.flFragmentContainer.visibility = android.view.View.GONE
        setBottomNavSelected(isHomeSelected = true)
    }

    // Mostrar pantalla de perfil de usuario
    private fun showProfile() {
        this.binding.rvRecipes.visibility = android.view.View.GONE
        this.binding.flFragmentContainer.visibility = android.view.View.VISIBLE

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
        this.binding.tvNavHome.setTextColor(if (isHomeSelected) primary else inactive)
        this.binding.tvNavProfile.setTextColor(if (isHomeSelected) inactive else primary)

        // Tint para iconos
        this.binding.ivNavHome.setColorFilter(if (isHomeSelected) primary else inactive)
        this.binding.ivNavProfile.setColorFilter(if (isHomeSelected) inactive else primary)
    }
}