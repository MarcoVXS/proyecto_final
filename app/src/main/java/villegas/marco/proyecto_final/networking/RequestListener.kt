package villegas.marco.proyecto_final.networking

// Permite responder cuando una peticion termina bien o falla.
interface RequestListener {
    // Se ejecuta cuando el API regresa una respuesta correcta.
    fun onResponse(response: String)

    // Se ejecuta cuando Volley detecta un error en la peticion.
    fun onError(error: String)
}
