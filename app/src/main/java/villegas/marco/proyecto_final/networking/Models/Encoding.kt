package villegas.marco.proyecto_final.networking.Models

// Indica como se mandan los parametros de una peticion.
enum class Encoding(val rawValue: String) {
    // Parametros en la URL, por ejemplo ?c=Seafood.
    URL("url"),

    // Parametros en el body como JSON.
    JSON("json")
}
