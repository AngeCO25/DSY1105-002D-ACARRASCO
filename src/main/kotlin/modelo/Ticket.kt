package modelo

data class Ticket(
    val numero: Int,
    val maquina: Maquina,
    val minutosUso: Int,
    val montoPagado: Double
)
