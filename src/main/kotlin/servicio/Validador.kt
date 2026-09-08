package servicio

import errores.CodigoInvalidoException
import errores.TipoUsuarioInvalidoException
import modelo.TipoUsuario

object Validador {

    private val patronCodigo = Regex("^[A-Za-z]{2}\\d{2}[A-Za-z]{2}$")

    fun esCodigoValido(codigo: String): Boolean = patronCodigo.matches(codigo.trim())

    fun validarCodigo(codigo: String): String {
        if (!esCodigoValido(codigo)) throw CodigoInvalidoException(codigo)
        return codigo.trim().uppercase()
    }

    fun validarTipoUsuario(valor: String): TipoUsuario {
        return TipoUsuario.desdeTexto(valor) ?: throw TipoUsuarioInvalidoException(valor)
    }
}
