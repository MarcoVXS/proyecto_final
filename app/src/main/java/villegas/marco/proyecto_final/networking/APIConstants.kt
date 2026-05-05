package villegas.marco.proyecto_final.networking

// Guarda URLs y endpoints usados para consultar TheMealDB.
object APIConstants {
    // Servidor principal del API.
    const val MAIN_SERVER = "https://www.themealdb.com/"

    // Imagen de respaldo por si se necesita mostrar una receta sin imagen.
    const val DEFAULT_IMAGE = "https://www.themealdb.com/images/media/meals/1548772327.jpg"

    // Endpoints disponibles para esta app.
    object EndPoints {
        const val FILTER_BY_CATEGORY = "api/json/v1/1/filter.php"
    }
}
