package villegas.marco.proyecto_final.scenes.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity: AppCompatActivity() {
    private val TAG: String = this::class.java.simpleName

    // Se ejecuta cuando la Activity (ventana) se crea por primera vez y se inicializan componentes
    // básicos como la interfaz, variables, ViewBinding, listeners, etc.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
    }

    // Se llama cuando la Activity se vuelve visible para el usuario.
    // La Activity está en pantalla pero todavía no es interactiva.
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    // Se ejecuta justo antes de que la Activity empiece a interactuar con el usuario.
    // Aquí se reanudan animaciones, sensores o recursos que se pausaron.
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    // Se llama cuando la Activity pierde el foco, pero aún puede ser visible.
    // Aquí se deben pausar tareas que consumen recursos, como animaciones o audio.
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    // Se ejecuta cuando la Activity deja de ser visible.
    // Aquí se liberan recursos pesados o se guardan datos persistentes.
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    // Se llama cuando la Activity se destruye completamente.
    // Aquí se realiza la limpieza final de recursos para evitar memory leaks.
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    // se invoca cuando una actividad que ha sido detenida (onStop()) vuelve a ser iniciada por el
    // usuario, preparándola para regresar al primer plano. Es el paso intermedio antes de onStart()
    // y permite actualizar datos específicos.
    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart")
    }
}