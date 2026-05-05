package villegas.marco.proyecto_final.networking.Models

import org.json.JSONObject
import villegas.marco.proyecto_final.networking.APIConstants
import java.net.URLEncoder

/**
 * Modelo para las peticiones
 * @property url: URL de la petición
 * @property method: Method de la petición
 * @property encoding: Codificación de la petición
 * @property parameters: Parámetros de la petición, pueden no existir en peditiones GET
 */
open class TheMealAPI(
    override var url: String,
    override var method: HTTPMethod,
    override var encoding: Encoding,
    override var parameters: JSONObject?
) : TargetType {
    companion object {
        fun filterMealsByCategory(category: String): TheMealAPI {
            val encodedCategory = URLEncoder.encode(category, "UTF-8")
            return TheMealAPI(
                url = "${APIConstants.MAIN_SERVER}${APIConstants.EndPoints.FILTER_BY_CATEGORY}?c=$encodedCategory",
                method = HTTPMethod.GET,
                encoding = Encoding.URL,
                parameters = null
            )
        }
    }
}
