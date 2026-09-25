/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.util;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.utility.model.ValoresCalculoEmpleado;
import mx.gob.imss.digital.modelo.cobranza.CalculoCuota;
import mx.gob.imss.digital.modelo.cobranza.DatosCalculoCuota;
import mx.gob.imss.digital.modelo.cobranza.EmpleadoCuota;
import mx.gob.imss.digital.modelo.cobranza.RamaCalculo;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Clase utilitaria para generar objetos que se utilizan en el motor de calculo
 * 
 * @author NOVUTECK1
 * 
 */
public abstract class MotorFactoryUtil {

    /**
     * MEtodo que genera un objeto calculoCuota a partir de los valores de datos
     * calculo
     * 
     * @param datosCalculoCuota el objeto base para crear la copia de valores
     * @return el objeto nuevo creado
     */
    public static final CalculoCuota copiaValoresCuota(DatosCalculoCuota datosCalculoCuota) {
        CalculoCuota calculo = new CalculoCuota();
        calculo.setNumeroRegistroPatronal(datosCalculoCuota.getNumeroRegistroPatronal());
        calculo.setFechaInicioCalculo(datosCalculoCuota.getFechaInicioCalculo());
        calculo.setFechaFinCalculo(datosCalculoCuota.getFechaFinCalculo());
        calculo.setZonaSalarial(datosCalculoCuota.getZonaSalarial());
        calculo.setEmpleados(new EmpleadoCuota[0]);
        calculo.setModalidad(datosCalculoCuota.getModalidad());
        calculo.setRenovacion(datosCalculoCuota.getRenovacion());
        calculo.setConRecargos(datosCalculoCuota.getRecargos());
        calculo.setAplicaRecargoPorFechaBaja(datosCalculoCuota.getAplicaRecargoPorFechaBaja());
        return calculo;
    }

    /**
     * MEtodo que genera un objeto calculoCuota a partir de los valores de datos
     * calculo
     * 
     * @param datosCalculoCuota objeto base para la copia de sus datos
     * @return El objeto creado
     */
    public static final ValoresCalculoEmpleado copiaValoresCuotaEmpleado(
            DatosCalculoCuota datosCalculoCuota) {
        ValoresCalculoEmpleado valores = new ValoresCalculoEmpleado();
        valores.setNumeroRegistroPatronal(datosCalculoCuota.getNumeroRegistroPatronal());
        valores.setFechaInicioCalculo(datosCalculoCuota.getFechaInicioCalculo());
        valores.setFechaFinCalculo(datosCalculoCuota.getFechaFinCalculo());
        valores.setZonaSalarial(datosCalculoCuota.getZonaSalarial());
        valores.setModalidad(datosCalculoCuota.getModalidad());
        valores.setUltimoSalarioCotizado(datosCalculoCuota.getSalarioMinimo());
        return valores;
    }

    /**
     * Genera una rama de calculo en cero a partir de la rama que se va a
     * calcular
     * 
     * @param rama la rama base para crear
     * @return la rama nueva generada
     */
    public static final RamaCalculo generaCalculoCero(RamaCalculo rama) {
        RamaCalculo cuota = new RamaCalculo();
        cuota.setAportacion(BigDecimal.ZERO);
        cuota.setDesTipoAportacion(rama.getDesTipoAportacion());
        cuota.setIdRama(rama.getIdRama());
        cuota.setIdTipoAportacion(rama.getIdTipoAportacion());
        cuota.setNombre(rama.getNombre());
        cuota.setFactorCalculo(rama.getFactorCalculo());
        cuota.setDescuento(BigDecimal.ZERO);
        return cuota;
    }
    
    public static final  RamaCalculo generaCalculoAportacionParcial(RamaCalculo rama, int numeroPeriodos) {
    	BigDecimal aportacionInicial = rama.getAportacion();
    	BigDecimal aportacionParcial = aportacionInicial.divide(new BigDecimal(numeroPeriodos), 2, RoundingMode.HALF_UP);

    	RamaCalculo cuota = new RamaCalculo();
		cuota.setAportacion(aportacionParcial);
        cuota.setDesTipoAportacion(rama.getDesTipoAportacion());
        cuota.setIdRama(rama.getIdRama());
        cuota.setIdTipoAportacion(rama.getIdTipoAportacion());
        cuota.setNombre(rama.getNombre());
        cuota.setFactorCalculo(rama.getFactorCalculo());
 
        return cuota;
    }

    /**
     * Redondea las cantidades big decimal a dos decimales tomendo en cuenta
     * solo tres decimales para el redondeo
     * 
     * @param cantidad la cantidad a redondear 
     * @return la cantidad redoneada
     */
    public static final BigDecimal redondeoCantidades(BigDecimal cantidad) {
        return cantidad.setScale(2, RoundingMode.HALF_UP);
    }
}
