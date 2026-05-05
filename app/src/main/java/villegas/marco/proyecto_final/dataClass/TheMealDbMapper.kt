package villegas.marco.proyecto_final.dataClass

import villegas.marco.proyecto_final.utils.RecipeRandom

// Convierte el modelo del API al modelo que usa la interfaz.
fun TheMealDbMeal.toRecipe(category: Category): Recipe {
    return Recipe(
        id = idMeal,
        title = strMeal,
        // Estos valores son generados porque el endpoint usado no los incluye.
        minutes = RecipeRandom.minutes(),
        kcal = RecipeRandom.kcal(),
        rating = RecipeRandom.rating(),
        imageUrl = strMealThumb,
        category = category
    )
}
