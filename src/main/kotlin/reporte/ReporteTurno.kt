package reporte

import modelo.Ticket
import servicio.SistemaLavanderia
object ReporteTurno {

    private const val ANCHO = 78

    fun titulo(texto: String) {
        println("")
        println("=".repeat(ANCHO))
        println(texto.uppercase())
        println("=".repeat(ANCHO))
    }

    fun imprimirTicket(ticket: Ticket) {
        println("  Ticket #${ticket.numero} | ${ticket.maquina.codigo} | ${ticket.maquina.detalle()}")
        println("  Tiempo de uso: ${ticket.minutosUso} min | Total a pagar: ${moneda(ticket.montoPagado)}")
    }

    fun consultasDeNegocio(sistema: SistemaLavanderia) {
        titulo("Consultas de negocio")

        println("Slots disponibles ahora: ${sistema.slotsDisponibles()}")

        val suscriptores = sistema.maquinasDeSuscriptores()
        val listaSuscriptores = if (suscriptores.isEmpty()) "ninguna"
        else suscriptores.joinToString(", ") { it.codigo }
        println("Maquinas de clientes suscriptores: $listaSuscriptores")

        println("Ingreso promedio por maquina: ${moneda(sistema.ingresoPromedio())}")

        println("Codigos finalizados en el turno: ${sistema.codigosFinalizados().joinToString(", ")}")

        val mayorTiempo = sistema.maquinaConMasTiempo()
        if (mayorTiempo == null) {
            println("Maquina con mas tiempo de uso: no hubo ciclos finalizados")
        } else {
            println("Maquina con mas tiempo de uso: ${mayorTiempo.maquina.codigo} (${mayorTiempo.minutosUso} min)")
        }
    }

    fun cierreDeTurno(sistema: SistemaLavanderia) {
        titulo("Reporte de cierre de turno - ${sistema.nombre}")

        val tickets = sistema.historialTickets()

        println("Detalle de maquinas atendidas:")
        for (ticket in tickets) {
            println(
                "  #${ticket.numero} | ${ticket.maquina.tipoMaquina} | ${ticket.maquina.codigo} | " +
                    "${ticket.minutosUso} min | ${moneda(ticket.montoPagado)}"
            )
        }

        println("")
        println("Recaudacion por tipo de maquina:")
        for ((tipo, monto) in sistema.recaudacionDetallada()) {
            println("  $tipo: ${moneda(monto)}")
        }

        println("")
        println("Total recaudado: ${moneda(sistema.recaudacionTotal)}")
        println("Maquinas atendidas: ${tickets.size}")
        println("Ingreso promedio: ${moneda(sistema.ingresoPromedio())}")
        println("Tipo de maquina con mayor ingreso: ${sistema.tipoQueMasRecaudo()}")
        println("Slots disponibles al cierre: ${sistema.slotsDisponibles()}")

        println("")
        println("Estado final de los slots:")
        sistema.estadoDeLosSlots().forEach { println("  $it") }
    }

    private fun moneda(valor: Double): String = "$" + String.format("%,.2f", valor)
}
