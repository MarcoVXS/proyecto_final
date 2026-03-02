package villegas.marco.proyecto_final.dataClass

import androidx.annotation.DrawableRes

    enum class Category {
        SEAFOOD, BEEF, VEGAN
    }

    data class Recipe(
        val id: String,
        val title: String,
        val minutes: Int,
        val kcal: Int,
        val rating: Double,
        @DrawableRes val imageRes: Int,
        val category: Category
    )