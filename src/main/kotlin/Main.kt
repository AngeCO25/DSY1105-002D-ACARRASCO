import kotlinx.coroutines.runBlocking
import modelo.Maquina
import reporte.ReporteTurno
import servicio.FabricaMaquinas
import servicio.SistemaLavanderia

fun main() = runBlocking {

    val sistema = SistemaLavanderia()

    ReporteTurno.titulo("Inicio de turno - ${sistema.nombre}")
    println("Capacidad total: 10 slots")

    for (numero in 6..10) {
        sistema.ponerFueraDeServicio(numero, "mantencion programada")
    }

    ReporteTurno.titulo("Registro de entradas")

    entradaSegura(sistema) {
        FabricaMaquinas.crearLavadora("LV12CD", "Samsung", "WW90", "suscriptor")
    }
    entradaSegura(sistema) {
        FabricaMaquinas.crearLavadora("LV99ZA", "LG", "F4WV509", "regular")
    }
    entradaSegura(sistema) {
        FabricaMaquinas.crearSecadora("SC22TO", "Bosch", "WTH85200", "regular")
    }
    entradaSegura(sistema) {
        FabricaMaquinas.crearLavasecaIndustrial("LI44RG", "Miele", "PW6", "empresa", conVapor = true)
    }
    entradaSegura(sistema) {
        FabricaMaquinas.crearLavasecaIndustrial("LI77RG", "Speed Queen", "SF7", "regular", conVapor = false)
    }
    entradaSegura(sistema) {
        FabricaMaquinas.crearLavadora("123ABC", "Whirlpool", "WFW", "regular")
    }

    entradaSegura(sistema) {
        FabricaMaquinas.crearSecadora("SC55MN", "Electrolux", "EDV", "regular")
    }

    ReporteTurno.titulo("Registro de salidas")

    salidaSegura(sistema, "LV12CD", 0)

    salidaSegura(sistema, "LV12CD", 75)
    salidaSegura(sistema, "LV99ZA", 180)
    salidaSegura(sistema, "SC22TO", 25)
    salidaSegura(sistema, "LI44RG", 120)
    salidaSegura(sistema, "LI77RG", 45)

    salidaSegura(sistema, "XX11YY", 60)

    ReporteTurno.consultasDeNegocio(sistema)
    ReporteTurno.cierreDeTurno(sistema)
}

private suspend fun entradaSegura(sistema: SistemaLavanderia, crearMaquina: () -> Maquina) {
    try {
        sistema.registrarEntrada(crearMaquina())
    } catch (e: Exception) {
        println("  [Aviso] ${e.message}")
    }
}

private suspend fun salidaSegura(sistema: SistemaLavanderia, codigo: String, minutos: Int) {
    try {
        val ticket = sistema.registrarSalida(codigo, minutos)
        ReporteTurno.imprimirTicket(ticket)
    } catch (e: Exception) {
        println("  [Aviso] ${e.message}")
    }
}
