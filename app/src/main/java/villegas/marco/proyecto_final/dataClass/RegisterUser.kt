package villegas.marco.proyecto_final.dataClass

// Datos que el usuario llena en la pantalla de registro.
data class RegisterUser(
    var userName: String = "",
    var email: String = "",
    var password: String = "",
    var passwordConfirm: String = ""
)
