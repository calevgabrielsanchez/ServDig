package mx.gob.imss.ctirss.delta.gestion.motorCalculo.test.motorCalculo.service.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import junit.framework.Assert;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.entity.GeneradorPeriodosCobroEntity;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.utility.model.PeriodoCalculoCuota;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.utility.model.ValoresCalculoEmpleado;
import mx.gob.imss.ctirss.delta.model.beneficio.Beneficio;
import mx.gob.imss.ctirss.delta.model.beneficio.DescuentoBeneficio;
import mx.gob.imss.digital.modelo.cobranza.DatosEmpleado;
import mx.gob.imss.digital.modelo.cobranza.MovimientoEmpleado;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Prueba para el generador de periodos de pago
 * @author NOVUTECK1
 *
 */
public class GeneradorPeriodosCobroTest {
    /**
     * Logger de la clase
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneradorPeriodosCobroTest.class);
    
    
    @Test
    public void testGeneraPeriodosCalculo() {
        Calendar fechaInicioCalculo = Calendar.getInstance();
        fechaInicioCalculo.set(2014, 0, 1);
        Calendar fechaFin = (Calendar) fechaInicioCalculo.clone();
        fechaFin.add(Calendar.MONTH, 6);
        fechaFin.add(Calendar.DATE, -1);
        LOGGER.debug("Fechas de calculo {}  _-   {}", fechaInicioCalculo.getTime(), fechaFin.getTime());
        ValoresCalculoEmpleado valores = new ValoresCalculoEmpleado();
        valores.setFechaInicioCalculo((Calendar)fechaInicioCalculo.clone());
        valores.setFechaFinCalculo(fechaFin);
        
        valores.setEmpleado(new DatosEmpleado());
        
        
        MovimientoEmpleado movimiento = new MovimientoEmpleado();
        movimiento.setFecha(fechaInicioCalculo.getTime());
        movimiento.setDias(2);
        MovimientoEmpleado[] movimientos = new MovimientoEmpleado[]{movimiento}; 
        
        valores.getEmpleado().setMovimientos(movimientos);
        
        GeneradorPeriodosCobroEntity generador = new GeneradorPeriodosCobroEntity();
        
        List<PeriodoCalculoCuota> periodos = generador.generaPeriodosCalculo(valores, null);
        Assert.assertEquals("PAra mes impar y rango de 6 ese se generan 3 periodos", 3, periodos.size());
        for(PeriodoCalculoCuota periodo : periodos){
            // paa el primer periodo debe haber movimientos
            if(periodo.getOrden() == 1 ){
                Assert.assertFalse("La lista no puede ser vacias", periodo.getMovimientos().isEmpty());
            }else {
                //Despues del periodo 1 no debe tener movimientos
                Assert.assertTrue("La lista debe ser vacias", periodo.getMovimientos().isEmpty());
            }
        }
        // Se reccore un mes la fecha del periodo para probar cortes a mitad de periodos fiscales
        valores.getFechaInicioCalculo().add(Calendar.MONTH, 1);
        valores.getFechaFinCalculo().add(Calendar.MONTH, 1);
        
        
        DescuentoBeneficio descuento = new DescuentoBeneficio();
        descuento.setFecInicio(fechaInicioCalculo.getTime());
        Calendar fechaFinDescuento = (Calendar)fechaInicioCalculo.clone();
        fechaFinDescuento.add(Calendar.MONTH, 3);        
        descuento.setFechaFin(fechaFinDescuento.getTime());
        descuento.setPorcentajeDescuento(BigDecimal.ZERO);
        
        Beneficio beneficio = new Beneficio();
        beneficio.setListaDescuentosBeneficio(new ArrayList<DescuentoBeneficio>());
        beneficio.getListaDescuentosBeneficio().add(descuento);
        LOGGER.debug("PEriodo descuentos = {}  , {}", descuento.getFecInicio(), descuento.getFechaFin());
        periodos = generador.generaPeriodosCalculo(valores, beneficio);
        Assert.assertEquals("PAra mes par y rango de 6 ese se generan 4 periodos", 4, periodos.size());
        //PAra el periodo de deceuento anterior debe agregarse descuebto a los 2 primeros periodos
        for(PeriodoCalculoCuota periodo : periodos){
            // paa el primer periodo debe haber descuentos
            if(periodo.getOrden() == 1 || periodo.getOrden() == 2 ){
                Assert.assertFalse("La lista descuentos no puede ser vacia " + periodo.getOrden(), 
                        periodo.getDescuentos().isEmpty());
            }else {
                //Despues del periodo 2 no debe tener descuentos
                Assert.assertTrue("La lista descuentos debe ser vacia", periodo.getDescuentos().isEmpty());
            }
        }
        
    }

}
