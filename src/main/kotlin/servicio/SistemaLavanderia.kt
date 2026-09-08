package servicio

import errores.MaquinaNoEncontradaException
import errores.SinCapacidadException
import kotlinx.coroutines.delay
import modelo.EstadoSlot
import modelo.Maquina
import modelo.Slot
import modelo.Ticket
import modelo.TipoUsuario

class SistemaLavanderia(
    val nombre: String = "LavExpress",
    cantidadSlots: Int = 10
) {

    private val slots: List<Slot> = List(cantidadSlots) { indice -> Slot(indice + 1) }

    private val historial: MutableList<Ticket> = mutableListOf()

    private var contadorTickets = 0

    var recaudacionTotal: Double = 0.0
        private set

    private val recaudacionPorTipo: MutableMap<String, Double> = mutableMapOf()

    suspend fun registrarEntrada(maquina: Maquina) {
        val slot = buscarPrimerSlotLibre() ?: throw SinCapacidadException(nombre)

        slot.estado = EstadoSlot.EnCicloFinal("registrando entrada")
        println("  Slot ${slot.numero}: ${slot.descripcionEstado()}...")

        // Espera simulada de la respuesta del sensor de entrada
        delay(3000)

        slot.estado = EstadoSlot.EnUso(maquina)
        println("  Entrada confirmada | ${maquina.codigo} | ${maquina.detalle()} | Usuario: ${maquina.tipoUsuario.descripcion} | Slot ${slot.numero}")
    }

    suspend fun registrarSalida(codigo: String, minutos: Int): Ticket {
        val slot = buscarSlotPorCodigo(codigo) ?: throw MaquinaNoEncontradaException(codigo.uppercase())

        // El estado EnUso guarda la maquina, aqui se recupera antes de cambiar el estado
        val maquina = (slot.estado as EstadoSlot.EnUso).maquina

        slot.estado = EstadoSlot.EnCicloFinal("calculando tarifa")
        println("  Slot ${slot.numero}: ${slot.descripcionEstado()}...")

        // Espera simulada del sensor de salida
        delay(6500)

        val monto = try {
            CalculadoraTarifa.calcularMontoTotal(maquina, minutos)
        } catch (e: Exception) {
            // Si la tarifa resulta invalida, la maquina vuelve a quedar en uso
            // para que el turno pueda continuar sin perder el registro
            slot.estado = EstadoSlot.EnUso(maquina)
            throw e
        }

        contadorTickets++
        val ticket = Ticket(contadorTickets, maquina, minutos, monto)

        historial.add(ticket)
        recaudacionTotal += monto
        recaudacionPorTipo[maquina.tipoMaquina] = (recaudacionPorTipo[maquina.tipoMaquina] ?: 0.0) + monto

        slot.estado = EstadoSlot.Libre
        return ticket
    }

    fun ponerFueraDeServicio(numeroSlot: Int, motivo: String) {
        val slot = slots.firstOrNull { it.numero == numeroSlot } ?: return
        slot.estado = EstadoSlot.FueraDeServicio(motivo)
        println("  Slot ${slot.numero}: ${slot.descripcionEstado()}")
    }

    private fun buscarPrimerSlotLibre(): Slot? = slots.firstOrNull { it.estado is EstadoSlot.Libre }

    private fun buscarSlotPorCodigo(codigo: String): Slot? {
        val buscado = codigo.trim().uppercase()
        return slots.firstOrNull { slot ->
            val estado = slot.estado
            estado is EstadoSlot.EnUso && estado.maquina.codigo == buscado
        }
    }


    fun slotsDisponibles(): Int = slots.count { it.estado is EstadoSlot.Libre }

    fun maquinasDeSuscriptores(): List<Maquina> =
        historial.map { it.maquina }.filter { it.tipoUsuario == TipoUsuario.SUSCRIPTOR }

    fun ingresoPromedio(): Double =
        if (historial.isEmpty()) 0.0 else recaudacionTotal / historial.size

    fun codigosFinalizados(): List<String> = historial.map { it.maquina.codigo }

    fun maquinaConMasTiempo(): Ticket? = historial.maxByOrNull { it.minutosUso }

    fun tipoQueMasRecaudo(): String =
        recaudacionPorTipo.maxByOrNull { it.value }?.key ?: "Sin datos"

    fun historialTickets(): List<Ticket> = historial.toList()

    fun recaudacionDetallada(): Map<String, Double> = recaudacionPorTipo.toMap()

    fun estadoDeLosSlots(): List<String> = slots.map { "Slot ${it.numero}: ${it.descripcionEstado()}" }
}
