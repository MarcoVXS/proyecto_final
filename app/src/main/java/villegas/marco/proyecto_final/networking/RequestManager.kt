package villegas.marco.proyecto_final.networking

import android.content.Context
import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.NetworkError
import com.android.volley.NoConnectionError
import com.android.volley.ParseError
import com.android.volley.Response
import com.android.volley.ServerError
import com.android.volley.TimeoutError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import villegas.marco.proyecto_final.networking.Models.TargetType
import villegas.marco.proyecto_final.sharedPreference.SharedPreferenceManager

// Clase encargada de ejecutar peticiones HTTP usando Volley.
class RequestManager(var context: Context) {
    // TAG ayuda a identificar los logs de esta clase.
    private val TAG = this::class.java.simpleName

    // SharedPreferences se usa si alguna peticion necesita autorizacion.
    private val sharedPreference: SharedPreferenceManager = SharedPreferenceManager(context)

    // Ejecuta la peticion indicada por target y avisa el resultado con requestListener.
    fun request(target: TargetType, authorized: Boolean, requestListener: RequestListener) {
        try {
            Log.i(TAG, "URL: ${target.url}")
            Log.i(TAG, "BODY: ${target.parameters}")

            // Cola donde Volley administra las peticiones de red.
            val queue = Volley.newRequestQueue(this.context)

            // JsonObjectRequest sirve para llamadas que regresan un objeto JSON.
            val request = object: JsonObjectRequest(
                target.method.rawValue,
                target.url,
                target.parameters,
                Response.Listener { response ->
                    try {
                        Log.i(TAG, "JSON RESPONSE: $response")
                        requestListener.onResponse(response.toString())
                    } catch (e: Exception) {
                        Log.e(TAG, "JSON RESPONSE: $e")
                        requestListener.onError("Internal error")
                    }
                },
                Response.ErrorListener { error ->
                    Log.e(TAG, "RESPONSE ERROR: $error")
                    // Se traduce el error tecnico a un mensaje sencillo para la app.
                    when (error) {
                        is NetworkError, is NoConnectionError -> {
                            requestListener.onError("Network Error")
                        }
                        is ServerError, is AuthFailureError, is ParseError -> {
                            requestListener.onError("Internal Server Error")
                        }
                        is TimeoutError -> {
                            requestListener.onError("Time Out")
                        }
                    }
                }
            ) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    if (authorized) {
                        // Si la peticion requiere token, se agrega en Authorization.
                        headers["Authorization"] = sharedPreference.getString("JWT")
                    }
                    Log.i(TAG, "HEADERS: $headers")
                    return headers
                }
            }

            // Define tiempo de espera y reintentos para la peticion.
            request.retryPolicy = DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )

            // Se agrega la peticion a la cola para que Volley la ejecute.
            queue.add(request)
        } catch (e: Exception) {
            Log.e(TAG, "CATCH: $e")
            requestListener.onError("Internal error")
        }
    }
}
