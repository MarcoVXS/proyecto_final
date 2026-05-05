package villegas.marco.proyecto_final.networking.Models

import org.json.JSONObject

// Contrato que debe cumplir cualquier modelo usado por RequestManager.
interface TargetType {
    // URL completa de la peticion.
    var url: String

    // Metodo HTTP que se va a ejecutar.
    var method: HTTPMethod

    // Forma en la que se mandan los parametros.
    var encoding: Encoding

    // Parametros opcionales de la peticion.
    var parameters: JSONObject?
}
