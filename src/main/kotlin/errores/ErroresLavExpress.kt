package errores

// Errores propios del negocio.
class CodigoInvalidoException(codigo: String) :
    Exception("El codigo \"$codigo\" no cumple el formato requerido (dos letras, dos digitos, dos letras). Ejemplo valido: LV12CD.")

class TipoUsuarioInvalidoException(valor: String) :
    Exception("El tipo de usuario \"$valor\" no existe. Valores permitidos: regular, suscriptor, empresa.")

class TarifaInvalidaException(codigo: String, monto: Double) :
    Exception("El monto calculado para la maquina $codigo es invalido ($monto). Debe ser mayor a cero.")

class MaquinaNoEncontradaException(codigo: String) :
    Exception("No hay ninguna maquina con codigo $codigo ocupando un slot del sistema.")

class SinCapacidadException(nombreSistema: String) :
    Exception("$nombreSistema no tiene slots libres en este momento. No se puede registrar la entrada.")
