package modelo

class Secadora(
    codigo: String,
    marca: String,
    modelo: String,
    tipoUsuario: TipoUsuario
) : Maquina(codigo, marca, modelo, tipoUsuario) {

    override val tipoMaquina: String = "Secadora"

    override val tarifaBase: Double = 1000.0

    private val minutosMinimosCobro = 30

    override fun calcularCostoBase(minutos: Int): Double {

        if (minutos < minutosMinimosCobro) return 0.0

        val costo = tarifaBase * horas(minutos)
        return aplicarDescuentoSuscriptor(costo)
    }

    override fun permiteCobroCero(minutos: Int): Boolean = minutos < minutosMinimosCobro
}
