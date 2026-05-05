package villegas.marco.proyecto_final.networking.Models

import com.android.volley.Request.Method

// Metodos HTTP que puede usar una peticion de Volley.
enum class HTTPMethod(val rawValue: Int) {
    // Obtiene informacion del servidor.
    GET(Method.GET),

    // Solo pide headers de la respuesta.
    HEAD(Method.HEAD),

    // Envia informacion nueva.
    POST(Method.POST),

    // Reemplaza informacion existente.
    PUT(Method.PUT),

    // Actualiza una parte de la informacion.
    PATCH(Method.PATCH),

    // Elimina informacion.
    DELETE(Method.DELETE)
}
