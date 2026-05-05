package villegas.marco.proyecto_final.scenes.home.view

import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import org.json.JSONObject
import villegas.marco.proyecto_final.R
import villegas.marco.proyecto_final.dataClass.Category
import villegas.marco.proyecto_final.dataClass.Recipe
import villegas.marco.proyecto_final.dataClass.TheMealDbMeal
import villegas.marco.proyecto_final.dataClass.toRecipe
import villegas.marco.proyecto_final.databinding.ActivityHomeBinding
import villegas.marco.proyecto_final.networking.Models.TheMealAPI
import villegas.marco.proyecto_final.networking.RequestListener
import villegas.marco.proyecto_final.networking.RequestManager
import villegas.marco.proyecto_final.scenes.base.BaseActivity
import villegas.marco.proyecto_final.scenes.home.adapter.RecipeAdapter
import villegas.marco.proyecto_final.scenes.home.viewModel.HomeViewModel
import villegas.marco.proyecto_final.scenes.profile.ProfileFragment
import villegas.marco.proyecto_final.sharedPreference.SharedPreferenceConstants
import villegas.marco.proyecto_final.sharedPreference.SharedPreferenceManager

// Pantalla principal despues del login. Muestra recetas y el perfil.
class HomeActivity: BaseActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var recipeAdapter: RecipeAdapter

    private companion object {
        // Llave para recibir el nombre del usuario desde el login.
        const val EXTRA_USER_NAME = "EXTRA_USER_NAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        this.configureActivity()
    }

    override fun onResume() {
        super.onResume()
        // Se actualiza por si el usuario cambio su foto en el perfil.
        updateAvatarFromPrefs()
    }

    // Agrupa la configuracion inicial de la pantalla.
    private fun configureActivity() {
        this.initActivityView()
        this.configureListeners()
    }

    // Infla el layout, configura RecyclerView y carga la categoria inicial.
    private fun initActivityView() {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateGreeting()

        recipeAdapter = RecipeAdapter()
        binding.rvRecipes.layoutManager = LinearLayoutManager(this)
        binding.rvRecipes.adapter = recipeAdapter

        loadRecipes(Category.SEAFOOD)

        viewModel = HomeViewModel(this, this)
    }

    // Configura clicks de navegacion y chips de categoria.
    private fun configureListeners() {
        binding.navHome.setOnClickListener {
            showHome()
        }

        binding.navProfile.setOnClickListener {
            showProfile()
        }

        binding.cgCategories.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.chipSeafood -> loadRecipes(Category.SEAFOOD)
                R.id.chipBeef -> loadRecipes(Category.BEEF)
                R.id.chipChicken -> loadRecipes(Category.CHICKEN)
            }
        }

        showHome()
    }

    // Muestra el saludo usando el nombre del Intent o el guardado en preferencias.
    private fun updateGreeting() {
        val nameFromIntent = intent.getStringExtra(EXTRA_USER_NAME)?.trim().orEmpty()
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
            getString(R.string.home_greeting)
        }
    }

    // Lee la foto guardada y la coloca en el avatar de Home.
    private fun updateAvatarFromPrefs() {
        val prefs = SharedPreferenceManager(this)
        val savedUri = prefs.getString(SharedPreferenceConstants.PROFILE_PHOTO_URI_KEY)
        if (savedUri.isNotBlank()) {
            binding.ivAvatar.setImageURI(Uri.parse(savedUri))
        } else {
            binding.ivAvatar.setImageResource(R.drawable.ic_person)
        }
    }

    // Muestra el RecyclerView de recetas y oculta el contenedor del fragment.
    private fun showHome() {
        this.binding.rvRecipes.visibility = android.view.View.VISIBLE
        this.binding.flFragmentContainer.visibility = android.view.View.GONE
        setBottomNavSelected(isHomeSelected = true)
    }

    // Muestra el fragment de perfil y oculta la lista de recetas.
    private fun showProfile() {
        this.binding.rvRecipes.visibility = android.view.View.GONE
        this.binding.flFragmentContainer.visibility = android.view.View.VISIBLE

        val current = supportFragmentManager.findFragmentById(R.id.fl_fragment_container)
        if (current !is ProfileFragment) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fl_fragment_container, ProfileFragment())
                .commit()
        }

        setBottomNavSelected(isHomeSelected = false)
    }

    // Cambia colores de iconos y textos segun la opcion seleccionada.
    private fun setBottomNavSelected(isHomeSelected: Boolean) {
        val primary = ContextCompat.getColor(this, R.color.primary)
        val inactive = ContextCompat.getColor(this, R.color.text_hint)

        this.binding.tvNavHome.setTextColor(if (isHomeSelected) primary else inactive)
        this.binding.tvNavProfile.setTextColor(if (isHomeSelected) inactive else primary)
        this.binding.ivNavHome.setColorFilter(if (isHomeSelected) primary else inactive)
        this.binding.ivNavProfile.setColorFilter(if (isHomeSelected) inactive else primary)
    }

    // Convierte la categoria local al texto esperado por TheMealDB.
    private fun categoryToApiValue(category: Category): String = when (category) {
        Category.SEAFOOD -> "Seafood"
        Category.BEEF -> "Beef"
        Category.CHICKEN -> "Chicken"
    }

    // Llama al API y actualiza la lista cuando llega la respuesta.
    private fun loadRecipes(category: Category) {
        val apiCategory = categoryToApiValue(category)
        val target = TheMealAPI.filterMealsByCategory(apiCategory)

        RequestManager(this).request(target, authorized = false, object : RequestListener {
            override fun onResponse(response: String) {
                val recipes = parseRecipes(response, category)
                recipeAdapter.submitList(recipes)
            }

            override fun onError(error: String) {
                recipeAdapter.submitList(emptyList())
            }
        })
    }

    // Convierte el JSON del API en una lista de recetas para el adapter.
    private fun parseRecipes(response: String, category: Category): List<Recipe> {
        return try {
            val mealsArray = JSONObject(response).optJSONArray("meals") ?: return emptyList()
            val meals = mutableListOf<TheMealDbMeal>()

            for (index in 0 until mealsArray.length()) {
                val mealJson = mealsArray.optJSONObject(index) ?: continue
                meals.add(
                    TheMealDbMeal(
                        strMeal = mealJson.optString("strMeal"),
                        strMealThumb = mealJson.optString("strMealThumb"),
                        idMeal = mealJson.optString("idMeal")
                    )
                )
            }

            meals.map { it.toRecipe(category) }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
