package villegas.marco.proyecto_final.dataClass

// Respuesta principal del endpoint filter.php de TheMealDB.
data class TheMealDbFilterResponse(
    val meals: List<TheMealDbMeal>?
)

// Receta tal como llega desde el API de TheMealDB.
data class TheMealDbMeal(
    val strMeal: String,
    val strMealThumb: String,
    val idMeal: String
)
