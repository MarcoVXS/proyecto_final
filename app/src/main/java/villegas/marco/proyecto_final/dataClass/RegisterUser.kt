package villegas.marco.proyecto_final.dataClass

data class RegisterUser(
    var userName: String = "",
    var email: String = "",
    var password: String = "",
    var passwordConfirm: String = ""
)