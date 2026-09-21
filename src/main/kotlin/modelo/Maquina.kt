package modelo

import java.time.LocalDateTime

abstract class Maquina(
    val codigo: String,
    val marca: String,
    val modelo: String,
    val tipoUsuario: TipoUsuario,
    val ingreso: LocalDateTime = LocalDateTime.now()
) {
    abstract val tipoMaquina: String

    abstract val tarifaBase: Double

    abstract fun calcularCostoBase(minutos: Int): Double

    open fun detalle(): String = "$tipoMaquina $marca $modelo"

    open fun permiteCobroCero(minutos: Int): Boolean = false

    protected fun aplicarDescuentoSuscriptor(monto: Double): Double {
        return if (tipoUsuario == TipoUsuario.SUSCRIPTOR) monto * 0.80 else monto
    }

    protected fun horas(minutos: Int): Double = minutos / 60.0
}
