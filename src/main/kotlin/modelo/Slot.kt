package modelo

// Un slot es una posicion de la lavanderia donde se puede asignar una maquina.
class Slot(val numero: Int) {

    // var porque el estado si cambia durante el turno
    var estado: EstadoSlot = EstadoSlot.Libre

    // Texto legible del estado actual. El when revisa los cuatro estados posibles.
    fun descripcionEstado(): String = when (val actual = estado) {
        is EstadoSlot.Libre -> "Libre"
        is EstadoSlot.EnUso -> "En uso por ${actual.maquina.codigo}"
        is EstadoSlot.EnCicloFinal -> "En ciclo final (${actual.motivo})"
        is EstadoSlot.FueraDeServicio -> "Fuera de servicio (${actual.motivo})"
    }
}
