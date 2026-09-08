package modelo

class Lavadora(
    codigo: String,
    marca: String,
    modelo: String,
    tipoUsuario: TipoUsuario
) : Maquina(codigo, marca, modelo, tipoUsuario) {

    override val tipoMaquina: String = "Lavadora"

    override val tarifaBase: Double = 1200.0

    override fun calcularCostoBase(minutos: Int): Double {
        val costo = tarifaBase * horas(minutos)
        // Si el usuario es suscriptor se le descuenta el 20% del costo calculado
        return aplicarDescuentoSuscriptor(costo)
    }
}
