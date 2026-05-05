package villegas.marco.proyecto_final.utils

import kotlin.random.Random

object RecipeRandom {
    fun minutes(): Int = Random.nextInt(10, 61)        // 10..60
    fun kcal(): Int = Random.nextInt(180, 751)         // 180..750
    fun rating(): Double = (Random.nextInt(35, 50)) / 10.0  // 3.5..4.9
}