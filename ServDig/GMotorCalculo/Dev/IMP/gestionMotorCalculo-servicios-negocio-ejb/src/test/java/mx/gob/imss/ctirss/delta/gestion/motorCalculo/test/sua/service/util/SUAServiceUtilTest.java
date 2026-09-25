/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.test.sua.service.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import junit.framework.Assert;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.util.CompraFactoryUtil;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.util.CotizadorFactoryUtil;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.util.MotorFactoryUtil;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.util.PeriodoUtil;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.util.RamaCalculoUtil;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.util.SalarioUtil;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.util.SUAServiceUtil;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.util.VersionSUAIvroEnum;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.utility.model.PeriodoSUA;
import mx.gob.imss.digital.modelo.cobranza.Patron;
import mx.gob.imss.digital.modelo.cobranza.PeriodoCuota;
import mx.gob.imss.distss.digital.jaxb.util.JaxbUtil;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;

/**
 * Clase de pruebas para servicios utilitarios de SUA
 * @author NOVUTECK1
 *
 */
public class SUAServiceUtilTest {
    
    @Test
    public void testAgregaPeriodo() {
        new SUAServiceUtil() {
        };
        PeriodoSUA periodoAgregar = new PeriodoSUA();
        periodoAgregar.setFechaInicio(Calendar.getInstance());
        periodoAgregar.setFechaFin(Calendar.getInstance());
        periodoAgregar.getFechaFin().add(Calendar.MONTH, 2);
        
        List<PeriodoSUA> periodosNuevos = new ArrayList<PeriodoSUA>();
                
        List<PeriodoSUA> periodos = SUAServiceUtil.agregaPeriodo(periodosNuevos, periodoAgregar);
        
        Assert.assertEquals("Debe existir solo un periodo ", 1, periodos.size());
        Assert.assertNull("El patron debe ser nulo", periodos.get(0).getPatron());
        
        periodoAgregar.setPatron(new Patron());
        
        periodos = SUAServiceUtil.agregaPeriodo(periodos, periodoAgregar);
        Assert.assertEquals("Si se agrega el mismo periodo no incrementa la lista", 
                1, periodos.size());
        Assert.assertNotNull("El patron no debe ser nulo por el nuevo periodo", periodos.get(0).getPatron());
    }
    
    @Test
    public void obtenPeriodoSUA() {
        
        PeriodoCuota periodo = new PeriodoCuota();
        periodo.setInicioPeriodo(Calendar.getInstance());
        periodo.setFinPeriodo(Calendar.getInstance());
        periodo.getFinPeriodo().add(Calendar.MONTH, 2);
        PeriodoSUA periodoSUA = SUAServiceUtil.obtenPeriodoSUA(new ArrayList<PeriodoSUA>(), periodo);
        Assert.assertNotNull("No se puede regresar un periodo NUlo", periodoSUA);
        Assert.assertNull("Debe ser nulo el patron", periodoSUA.getPatron());
        List<PeriodoSUA> periodosSUA = new ArrayList<PeriodoSUA>();
        periodoSUA.setPatron(new Patron());
        periodosSUA.add(periodoSUA);
        
        PeriodoSUA periodoSUA2 = SUAServiceUtil.obtenPeriodoSUA(periodosSUA, periodo);
        // si se busca un periodo en el mismo tiempo debe reregresar el mismo
        Assert.assertNotNull("No se puede regresar un periodo NUlo", periodoSUA2);
        Assert.assertNotNull("Debe ser nulo el patron", periodoSUA2.getPatron());
        Assert.assertTrue("La fecha debe ser igual", 
                DateUtils.isSameDay(periodoSUA.getFechaInicio(), periodoSUA2.getFechaInicio()));
    }
    
    @Test
    public void testObtenPeriodoSUA_primero() {
        
        PeriodoCuota periodo = new PeriodoCuota();
        periodo.setInicioPeriodo(Calendar.getInstance());
        periodo.setFinPeriodo(Calendar.getInstance());
        periodo.getFinPeriodo().add(Calendar.MONTH, 2);
        
        PeriodoSUA periodoSUA2 = SUAServiceUtil.obtenPeriodoSUA(null, periodo);
        // si se busca un periodo en el mismo tiempo debe reregresar el mismo
        Assert.assertNotNull("No se puede regresar un periodo NUlo", periodoSUA2);
        Assert.assertNull("Debe ser nulo el patron", periodoSUA2.getPatron());
        
    }
    
    /**
     * PRueba dummy para instanciacion de las clases abstractas
     */
    @Test 
    public void testInstanciacion() {
        new SalarioUtil() {
        };
        new RamaCalculoUtil() {
        };
        new PeriodoUtil() {
        };
        new MotorFactoryUtil() {
        };
        new CotizadorFactoryUtil() {
        };
        new CompraFactoryUtil() {
        };
        new JaxbUtil() {
        };
        Assert.assertNull("No tienen que encontrar version ", VersionSUAIvroEnum.fromId(9768l));
    }

}
