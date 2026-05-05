package villegas.marco.proyecto_final.dataClass

import villegas.marco.proyecto_final.dataClass.Category
import villegas.marco.proyecto_final.dataClass.Recipe
import villegas.marco.proyecto_final.utils.RecipeRandom

fun TheMealDbMeal.toRecipe(category: Category): Recipe {
    return Recipe(
        id = idMeal,
        title = strMeal,
        minutes = RecipeRandom.minutes(),
        kcal = RecipeRandom.kcal(),
        rating = RecipeRandom.rating(),
        imageUrl = strMealThumb,
        category = category
    )
}