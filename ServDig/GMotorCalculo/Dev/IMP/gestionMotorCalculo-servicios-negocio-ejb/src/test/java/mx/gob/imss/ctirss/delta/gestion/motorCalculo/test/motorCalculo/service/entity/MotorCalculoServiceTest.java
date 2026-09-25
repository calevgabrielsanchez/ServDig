/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.test.motorCalculo.service.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import junit.framework.Assert;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.entity.MotorCalculoServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.util.MotorFactoryUtil;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.utility.model.PeriodoCalculoCuota;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.utility.model.ValoresCalculoEmpleado;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.ClavesRama;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.ClavesTipoAportacion;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.ClavesTipoMovimiento;
import mx.gob.imss.ctirss.delta.model.beneficio.DescuentoBeneficio;
import mx.gob.imss.digital.modelo.cobranza.DatosEmpleado;
import mx.gob.imss.digital.modelo.cobranza.EmpleadoCuota;
import mx.gob.imss.digital.modelo.cobranza.MovimientoEmpleado;
import mx.gob.imss.digital.modelo.cobranza.PeriodoCuota;
import mx.gob.imss.digital.modelo.cobranza.RamaCalculo;

import org.junit.Test;

/**
 * @author NOVUTECK1
 *
 */
public class MotorCalculoServiceTest {

    private static final BigDecimal FACTOR_FIJO = new BigDecimal("1");
    private static final BigDecimal FACTOR_EXCEDENTE = new BigDecimal("1.5");
    private static final BigDecimal FACTOR_RETIRO = new BigDecimal("0.5");
    
    private static final BigDecimal SALARIO = new BigDecimal("50");
    
    private static final int DIAS_PERIODO = 20;    
    private static final int DIAS_AUSENTISMO = 5;
    private static final BigDecimal DIAS_CALCULO = new BigDecimal("15");
    
    private static final BigDecimal DESCUENTO = new BigDecimal("50");
    @Test
	public void testCalculaCuota() throws SUAException {
        ValoresCalculoEmpleado valores = generaDatosPrueba();
        MotorCalculoServiceEntity motor = new MotorCalculoServiceEntity();
        EmpleadoCuota cuota = motor.calculaCuota(valores);
        BigDecimal factorFijoPorcentaje = FACTOR_FIJO.divide(new BigDecimal(100));
        BigDecimal factorExcedentePorcentaje = FACTOR_EXCEDENTE.divide(new BigDecimal(100));
        BigDecimal factorRetiroPorcentaje = FACTOR_RETIRO.divide(new BigDecimal(100));
        
        BigDecimal fijaPeriodo1 = MotorFactoryUtil.redondeoCantidades(
                factorFijoPorcentaje.multiply(SALARIO).multiply(DIAS_CALCULO));
        BigDecimal excedentePeriodo1 = MotorFactoryUtil.redondeoCantidades(
                factorExcedentePorcentaje.multiply(SALARIO).multiply(DIAS_CALCULO));
        BigDecimal retiroPeriodo1 = MotorFactoryUtil.redondeoCantidades(
                factorRetiroPorcentaje.multiply(SALARIO).multiply(DIAS_CALCULO));
        
        BigDecimal fijaPeriodo2 = MotorFactoryUtil.redondeoCantidades(
                (factorFijoPorcentaje.multiply(SALARIO).multiply(DIAS_CALCULO)).divide(new BigDecimal(2)));
        BigDecimal excedentePeriodo2 = MotorFactoryUtil.redondeoCantidades(
                (factorExcedentePorcentaje.multiply(SALARIO).multiply(DIAS_CALCULO)).divide(new BigDecimal(2)));
        BigDecimal retiroPeriodo2 = MotorFactoryUtil.redondeoCantidades(
                (factorRetiroPorcentaje.multiply(SALARIO).multiply(DIAS_CALCULO)).divide(new BigDecimal(2)));
        
        BigDecimal total1 = fijaPeriodo1.add(excedentePeriodo1).add(retiroPeriodo1);
        BigDecimal total2 = fijaPeriodo2.add(excedentePeriodo2).add(retiroPeriodo2);
        BigDecimal total = total1.add(total2);
        
        for(PeriodoCuota periodo : cuota.getPeriodos()){
            if(periodo.getOrden() == 1){
                Assert.assertEquals("Los totales periodo 1 deben ser iguales", total1, periodo.getTotal());
                for(RamaCalculo rama : periodo.getCuotas()){
                    switch (rama.getIdRama()) {
                    case ClavesRama.CUOTA_FIJA:
                        Assert.assertEquals("La cuota fija 1 debe ser igual", fijaPeriodo1, rama.getAportacion());                        
                        break;
                    case ClavesRama.EXCEDENTE:
                        Assert.assertEquals("La cuota excedente 1 debe ser igual", excedentePeriodo1, rama.getAportacion());                        
                        break;
                    case ClavesRama.RETIRO:
                        Assert.assertEquals("La cuota retiro 1 debe ser igual", retiroPeriodo1, rama.getAportacion());                        
                        break;
                    default:
                        Assert.fail("Estan generando ramas desconocidas para la prueba");
                        break;
                    }
                }
            } else if(periodo.getOrden() == 2){
                Assert.assertEquals("Los totales periodo 2 deben ser iguales", total2, periodo.getTotal());
                
                for(RamaCalculo rama : periodo.getCuotas()){
                    switch (rama.getIdRama()) {
                    case ClavesRama.CUOTA_FIJA:
                        Assert.assertEquals("La cuota fija 2 debe ser igual", fijaPeriodo2, rama.getAportacion());                        
                        break;
                    case ClavesRama.EXCEDENTE:
                        Assert.assertEquals("La cuota excedente 2 debe ser igual", excedentePeriodo2, rama.getAportacion());                        
                        break;
                    case ClavesRama.RETIRO:
                        Assert.assertEquals("La cuota retiro 2 debe ser igual", retiroPeriodo2, rama.getAportacion());                        
                        break;
                    default:
                        Assert.fail("Estan generando ramas desconocidas para la prueba");
                        break;
                    }
                }
            } else {
                Assert.fail("Solo debe calcular 2 periodos");
            }
        }
        Assert.assertEquals("Los totales deben ser iguales", total, cuota.getCuotaTotal());
        
    }
    
    private ValoresCalculoEmpleado generaDatosPrueba() {
        ValoresCalculoEmpleado valores = new ValoresCalculoEmpleado();
        valores.setCuotas(generaRamasCalculo());
        valores.setEmpleado(new DatosEmpleado());
        valores.getEmpleado().setNumeroSeguridadSocial("234444");
        valores.setFechaInicioCalculo(Calendar.getInstance());
        valores.setPeriodos(generaPeriodos());
        valores.setSalarioCalculo(SALARIO);
        valores.setSalarioCuotaFija(SALARIO);
        valores.setSalarioExedente(SALARIO);
        
        return valores;
    }
    
    private List<RamaCalculo> generaRamasCalculo(){
        List<RamaCalculo> ramas = new ArrayList<RamaCalculo>();
        
        RamaCalculo excedente = new RamaCalculo();
        excedente.setFactorCalculo(FACTOR_EXCEDENTE);
        excedente.setIdRama(ClavesRama.EXCEDENTE);
        excedente.setIdTipoAportacion(ClavesTipoAportacion.OBRERA);
        ramas.add(excedente);
        
        RamaCalculo retiro = new RamaCalculo();
        retiro.setFactorCalculo(FACTOR_RETIRO);
        retiro.setIdRama(ClavesRama.RETIRO);
        retiro.setIdTipoAportacion(ClavesTipoAportacion.OBRERA);
        ramas.add(retiro);
        
        RamaCalculo fija = new RamaCalculo();
        fija.setFactorCalculo(FACTOR_FIJO);
        fija.setIdRama(ClavesRama.CUOTA_FIJA);
        fija.setIdTipoAportacion(ClavesTipoAportacion.OBRERA);
        ramas.add(fija);
        
        return ramas;
    }
    
    private List<PeriodoCalculoCuota> generaPeriodos() {
        List<PeriodoCalculoCuota> periodos = new ArrayList<PeriodoCalculoCuota>();
        PeriodoCalculoCuota periodo1 = generaPeriodo(ClavesTipoMovimiento.AUSENTISMO);
        periodo1.setOrden(1);
        periodos.add(periodo1);
        
        PeriodoCalculoCuota periodo2 = generaPeriodo(ClavesTipoMovimiento.AUSENTISMO);
        periodo2.setOrden(2);
        DescuentoBeneficio descuento = new DescuentoBeneficio();
        descuento.setPorcentajeDescuento(DESCUENTO);
        descuento.setFecInicio(periodo2.getFechaInicial().getTime());
        descuento.setFechaFin(periodo2.getFechaFinal().getTime());
        periodo2.setDescuentos(new ArrayList<DescuentoBeneficio>());
        periodo2.getDescuentos().add(descuento);
        periodos.add(periodo2);
        
        return periodos;
    }
    
    private PeriodoCalculoCuota generaPeriodo(int tipoMovimieno){
        Calendar fechaIni = Calendar.getInstance();
        fechaIni.set(2014, 1, 10);
        Calendar fechaFin = (Calendar)fechaIni.clone();
        fechaFin.add(Calendar.DATE, DIAS_PERIODO);
        
        PeriodoCalculoCuota periodo = new PeriodoCalculoCuota();
        periodo.setFechaInicial(fechaIni);
        periodo.setFechaFinal(fechaFin);
        periodo.setMovimientos(new ArrayList<MovimientoEmpleado>());
        MovimientoEmpleado mov = new MovimientoEmpleado();
        mov.setDias(DIAS_AUSENTISMO);
        mov.setTipoMovimiento(tipoMovimieno);
        Calendar fechaMov = (Calendar)fechaIni.clone();
        fechaMov.add(Calendar.DATE, 10);
        mov.setFecha(fechaMov.getTime());
        periodo.getMovimientos().add(mov);
        
        return periodo;
    }
}
