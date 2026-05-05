package villegas.marco.proyecto_final.networking.Models

import org.json.JSONObject

/**
 * Interface para las peticiones
 * @property url: URL de la petición
 * @property method: Method de la petición
 * @property encoding: Codificación de la petición
 * @property parameters: Parámetros de la petición, pueden no existir en peditiones GET
 */
interface TargetType {
    var url: String
    var method: HTTPMethod
    var encoding: Encoding
    var parameters: JSONObject?
}
