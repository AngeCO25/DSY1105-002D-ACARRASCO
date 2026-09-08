package servicio

import errores.TarifaInvalidaException
import modelo.Maquina
import modelo.TipoUsuario
import kotlin.math.round

object CalculadoraTarifa {

    private const val IVA = 0.19
    private const val DESCUENTO_EMPRESA = 0.50

    fun calcularMontoTotal(maquina: Maquina, minutos: Int): Double {
        val costoBase = maquina.calcularCostoBase(minutos)

        var total = costoBase * (1 + IVA)

        if (maquina.tipoUsuario == TipoUsuario.EMPRESA) {
            total *= (1 - DESCUENTO_EMPRESA)
        }

        val montoFinal = redondear(total)

        if (montoFinal <= 0.0 && !maquina.permiteCobroCero(minutos)) {
            throw TarifaInvalidaException(maquina.codigo, montoFinal)
        }

        return montoFinal
    }

    private fun redondear(valor: Double): Double = round(valor * 100) / 100
}
