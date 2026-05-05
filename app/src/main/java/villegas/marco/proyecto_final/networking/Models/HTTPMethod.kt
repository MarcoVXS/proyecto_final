package villegas.marco.proyecto_final.networking.Models

import com.android.volley.Request.Method

/**
 * Method de petición
 * @property GET: Petición GET
 * @property HEAD: Petición HEAD
 * @property POST: Petición POST
 * @property PUT: Petición PUT
 * @property PATCH: Petición PATCH
 * @property DELETE: Petición DELETE
 */
enum class HTTPMethod(val rawValue: Int) {
    GET(Method.GET),
    HEAD(Method.HEAD),
    POST(Method.POST),
    PUT(Method.PUT),
    PATCH(Method.PATCH),
    DELETE(Method.DELETE)
}
