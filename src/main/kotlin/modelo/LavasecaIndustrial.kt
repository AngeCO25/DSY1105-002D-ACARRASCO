package modelo

class LavasecaIndustrial(
    codigo: String,
    marca: String,
    modelo: String,
    tipoUsuario: TipoUsuario,
    val conVapor: Boolean            // se registra al ingreso y no cambia
) : Maquina(codigo, marca, modelo, tipoUsuario) {

    override val tipoMaquina: String = "LavasecaIndustrial"

    override val tarifaBase: Double = 2800.0

    private val recargoVapor = 0.30

    override fun calcularCostoBase(minutos: Int): Double {
        var costo = tarifaBase * horas(minutos)

        if (conVapor) costo *= (1 + recargoVapor)

        return aplicarDescuentoSuscriptor(costo)
    }

    override fun detalle(): String {
        val vapor = if (conVapor) "con vapor" else "sin vapor"
        return "$tipoMaquina $marca $modelo ($vapor)"
    }
}
