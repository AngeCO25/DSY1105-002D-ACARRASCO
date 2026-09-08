package modelo

enum class TipoUsuario(val descripcion: String) {
    REGULAR("Regular"),
    SUSCRIPTOR("Suscriptor"),
    EMPRESA("Empresa");

    companion object {

        fun desdeTexto(texto: String): TipoUsuario? {
            return entries.firstOrNull { it.name.equals(texto.trim(), ignoreCase = true) }
        }
    }
}
