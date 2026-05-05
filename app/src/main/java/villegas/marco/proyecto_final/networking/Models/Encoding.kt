package villegas.marco.proyecto_final.networking.Models

/**
 * Encode de parámetros
 * @property URL: Los parámetros van dentro de la URL
 * @property JSON: Los parámetros van dentro del body de la petición
 */
enum class Encoding(val rawValue: String) {
    URL("url"),
    JSON("json")
}
