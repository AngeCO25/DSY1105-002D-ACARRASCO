package servicio

import modelo.Lavadora
import modelo.LavasecaIndustrial
import modelo.Maquina
import modelo.Secadora

object FabricaMaquinas {

    fun crearLavadora(codigo: String, marca: String, modelo: String, usuario: String): Maquina {
        return Lavadora(
            Validador.validarCodigo(codigo),
            marca,
            modelo,
            Validador.validarTipoUsuario(usuario)
        )
    }

    fun crearSecadora(codigo: String, marca: String, modelo: String, usuario: String): Maquina {
        return Secadora(
            Validador.validarCodigo(codigo),
            marca,
            modelo,
            Validador.validarTipoUsuario(usuario)
        )
    }

    fun crearLavasecaIndustrial(
        codigo: String,
        marca: String,
        modelo: String,
        usuario: String,
        conVapor: Boolean
    ): Maquina {
        return LavasecaIndustrial(
            Validador.validarCodigo(codigo),
            marca,
            modelo,
            Validador.validarTipoUsuario(usuario),
            conVapor
        )
    }
}
