package villegas.marco.proyecto_final.sharedPreference

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceManager(context: Context) {

    private val sharedPreference: SharedPreferences = context.getSharedPreferences(
        SharedPreferenceConstants.NAME_SHARE_PREFERENCE,
        Context.MODE_PRIVATE
    )
    private var editor: SharedPreferences.Editor = this.sharedPreference.edit()

    fun clearInformation(): Boolean {
        return try {
            this.editor.clear()
            this.editor.commit()
            true
        }
        catch (e: Exception) {
            false
        }
    }

    fun setString(key: String, defValue: String) {
        this.editor.putString(key, defValue)
        this.editor.commit()
    }

    fun getString(key: String): String {
        return this.sharedPreference.getString(key, "") ?: ""
    }

    fun setBoolean(key: String, defValue: Boolean) {
        this.editor.putBoolean(key, defValue)
        this.editor.commit()
    }

    fun getBoolean(key: String): Boolean {
        return this.sharedPreference.getBoolean(key, false)
    }
}