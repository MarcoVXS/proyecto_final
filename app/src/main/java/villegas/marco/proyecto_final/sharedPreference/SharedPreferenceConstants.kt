package villegas.marco.proyecto_final.sharedPreference

// Llaves usadas para guardar y leer datos en SharedPreferences.
object SharedPreferenceConstants {
    // Nombre del archivo interno de preferencias.
    const val NAME_SHARE_PREFERENCE = "NAME_SHARE_PREFERENCE"

    // Llaves de datos del usuario registrado.
    const val PASSWORD_KEY = "PASSWORD_KEY"
    const val USER_NAME_KEY = "USER_NAME_KEY"
    const val USER_EMAIL_KEY = "USER_EMAIL_KEY"

    // Llaves para estados de sesion y registro.
    const val IS_LOGGED_KEY = "IS_LOGGED_KEY"
    const val IS_REGISTERED_KEY = "IS_REGISTERED_KEY"

    // Llave donde se guarda la URI de la foto de perfil.
    const val PROFILE_PHOTO_URI_KEY = "PROFILE_PHOTO_URI_KEY"
}
