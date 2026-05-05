package villegas.marco.proyecto_final.networking.Models

import org.json.JSONObject
import villegas.marco.proyecto_final.networking.APIConstants
import java.net.URLEncoder

// Modelo de peticion especifico para TheMealDB.
open class TheMealAPI(
    override var url: String,
    override var method: HTTPMethod,
    override var encoding: Encoding,
    override var parameters: JSONObject?
) : TargetType {
    companion object {
        // Construye la peticion para buscar recetas por categoria.
        fun filterMealsByCategory(category: String): TheMealAPI {
            // Encode evita problemas si la categoria tuviera espacios o caracteres especiales.
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
