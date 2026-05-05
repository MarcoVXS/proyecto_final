package villegas.marco.proyecto_final.scenes.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

// Activity base para registrar el ciclo de vida de las pantallas.
open class BaseActivity: AppCompatActivity() {
    private val TAG: String = this::class.java.simpleName

    // Se ejecuta cuando la Activity se crea por primera vez.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
    }

    // Se llama cuando la Activity se vuelve visible.
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    // Se ejecuta cuando la Activity puede interactuar con el usuario.
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    // Se llama cuando la Activity pierde el foco.
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    // Se ejecuta cuando la Activity deja de verse en pantalla.
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    // Se llama cuando la Activity se destruye completamente.
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    // Se invoca cuando una Activity detenida vuelve a iniciar.
    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart")
    }
}
