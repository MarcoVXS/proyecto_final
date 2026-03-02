package villegas.marco.proyecto_final.scenes.home.view

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

class HomeActivity: BaseActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit  var viewModel: HomeViewModel
    private lateinit var recipeAdapter: RecipeAdapter
    private var allRecipes: List<Recipe> = emptyList()
    private val TAG = HomeActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 1) Data base (después lo puedes mover a JSON/API)
        allRecipes = listOf(
            Recipe("sea1", "Spanish seafood rice", 15, 240, 4.8, R.drawable.spanish_seafood_rice, Category.SEAFOOD),
            Recipe("sea2", "Seafood fideuà", 25, 410, 4.9, R.drawable.seafood_fideua, Category.SEAFOOD),
            Recipe("sea3", "Seafood rice", 10, 190, 4.5, R.drawable.seafood_rice, Category.SEAFOOD),

            // Recipe("beef1", "Beef bowl", 20, 520, 4.7, R.drawable.beef_bowl, Category.BEEF),
            // Recipe("beef2", "Steak salad", 18, 430, 4.6, R.drawable.steak_salad, Category.BEEF),

            // Recipe("veg1", "Vegan tofu stir-fry", 15, 320, 4.8, R.drawable.tofu_stir_fry, Category.VEGAN),
            // Recipe("veg2", "Chickpea salad", 10, 280, 4.7, R.drawable.chickpea_salad, Category.VEGAN),
        )

        this.configureActivity()
    }

    private fun configureActivity() {
        this.initActivityView()
        this.configureListeners()
    }

    private fun initActivityView() {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        // 4) Chips listener
        binding.cgCategories.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.chipSeafood -> applyCategory(Category.SEAFOOD)
                R.id.chipBeef -> applyCategory(Category.BEEF)
                R.id.chipVegan -> applyCategory(Category.VEGAN)
            }
        }

        // Estado inicial
        showHome()
    }

    private fun applyCategory(category: Category) {
        val filtered = allRecipes.filter { it.category == category }
        recipeAdapter.submitList(filtered)
    }

    // Mostrar pantalla de inicio
    private fun showHome() {
        binding.rvRecipes.visibility = android.view.View.VISIBLE
        binding.flFragmentContainer.visibility = android.view.View.GONE
        setBottomNavSelected(isHomeSelected = true)
    }

    // Mostrar pantalla de perfil de usuario
    private fun showProfile() {
        binding.rvRecipes.visibility = android.view.View.GONE
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