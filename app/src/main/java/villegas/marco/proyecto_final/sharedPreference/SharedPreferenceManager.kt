package villegas.marco.proyecto_final.sharedPreference

import android.content.Context
import android.content.SharedPreferences

// Clase helper para guardar y leer datos simples en SharedPreferences.
class SharedPreferenceManager(context: Context) {

    // Archivo donde Android guarda los datos de forma privada para la app.
    private val sharedPreference: SharedPreferences = context.getSharedPreferences(
        SharedPreferenceConstants.NAME_SHARE_PREFERENCE,
        Context.MODE_PRIVATE
    )

    // Editor usado para escribir datos en SharedPreferences.
    private var editor: SharedPreferences.Editor = this.sharedPreference.edit()

    // Borra toda la informacion guardada.
    fun clearInformation(): Boolean {
        return try {
            this.editor.clear()
            this.editor.commit()
            true
        } catch (e: Exception) {
            false
        }
    }

    // Guarda un texto con la llave indicada.
    fun setString(key: String, defValue: String) {
        this.editor.putString(key, defValue)
        this.editor.commit()
    }

    // Lee un texto guardado o regresa texto vacio si no existe.
    fun getString(key: String): String {
        return this.sharedPreference.getString(key, "") ?: ""
    }

    // Guarda un valor verdadero o falso.
    fun setBoolean(key: String, defValue: Boolean) {
        this.editor.putBoolean(key, defValue)
        this.editor.commit()
    }

    // Lee un valor booleano o regresa false si no existe.
    fun getBoolean(key: String): Boolean {
        return this.sharedPreference.getBoolean(key, false)
    }
}
