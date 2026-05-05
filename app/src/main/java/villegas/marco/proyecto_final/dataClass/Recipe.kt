package villegas.marco.proyecto_final.dataClass

// Categorias que se muestran en los chips de la pantalla principal.
enum class Category {
    SEAFOOD, BEEF, CHICKEN
}

// Modelo que representa una receta lista para mostrarse en la app.
data class Recipe(
    val id: String,
    val title: String,
    val minutes: Int,
    val kcal: Int,
    val rating: Double,
    val imageUrl: String,
    val category: Category
)
