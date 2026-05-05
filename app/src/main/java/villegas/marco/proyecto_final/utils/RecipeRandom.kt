package villegas.marco.proyecto_final.utils

import kotlin.random.Random

// Genera datos de presentacion para recetas cuando el API no los manda.
object RecipeRandom {
    // Tiempo estimado entre 10 y 60 minutos.
    fun minutes(): Int = Random.nextInt(10, 61)

    // Calorias estimadas entre 180 y 750 kcal.
    fun kcal(): Int = Random.nextInt(180, 751)

    // Rating estimado entre 3.5 y 4.9.
    fun rating(): Double = (Random.nextInt(35, 50)) / 10.0
}
