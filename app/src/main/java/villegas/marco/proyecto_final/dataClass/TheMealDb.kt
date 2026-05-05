package villegas.marco.proyecto_final.dataClass

data class TheMealDbFilterResponse(
    val meals: List<TheMealDbMeal>?
)

data class TheMealDbMeal(
    val strMeal: String,
    val strMealThumb: String,
    val idMeal: String
)