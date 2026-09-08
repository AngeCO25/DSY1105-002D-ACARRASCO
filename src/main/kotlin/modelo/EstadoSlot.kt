package modelo

sealed class EstadoSlot {
    data object Libre : EstadoSlot()
    data class EnUso(val maquina: Maquina) : EstadoSlot()

    data class EnCicloFinal(val motivo: String) : EstadoSlot()

    data class FueraDeServicio(val motivo: String) : EstadoSlot()
}
