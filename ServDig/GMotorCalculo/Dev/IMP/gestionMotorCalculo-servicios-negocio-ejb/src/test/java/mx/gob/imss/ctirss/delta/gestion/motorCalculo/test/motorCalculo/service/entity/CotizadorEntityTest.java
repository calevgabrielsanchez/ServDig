/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.test.motorCalculo.service.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityManager;

import junit.framework.Assert;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.entity.CotizadorEntity;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.util.CotizadorFactoryUtil;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.test.sua.util.JaxbUtilT;
import mx.gob.imss.ctirss.delta.persistence.cobranza.DitCotizacion;
import mx.gob.imss.digital.modelo.cobranza.CalculoCuota;
import mx.gob.imss.digital.modelo.cobranza.Cotizacion;

import org.apache.commons.lang.time.DateUtils;
import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

/**
 * Clase de prueba para los servicio del cotizador con BD
 * @author NOVUTECK1
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(CotizadorEntity.class)
public class CotizadorEntityTest {
    
    /**
     * Servicio a probar
     */
    CotizadorEntity cotizadorEntity = new CotizadorEntity();
    /**
     * Entity manager mock
     */
    EntityManager entityManager;
    /**
     * Archivo con un xml para los datos que se calcularon
     */
    private static final String XML_CALCULO_CUOTA = "src/test/resources/CalculoCuotaTest.xml";

    @Test
    public void testGeneraYGuardaCotizacion() throws SUAException {
        entityManager = EasyMock.createMock(EntityManager.class);
        entityManager.persist(EasyMock.anyObject());
        
        EasyMock.expectLastCall().anyTimes();
        
        Whitebox.setInternalState(cotizadorEntity, "entityManager", entityManager);
        EasyMock.replay(entityManager);
        
        Cotizacion cotizacion = cotizadorEntity.generaYGuardaCotizacion(getCalculoCuota());
        Assert.assertNotNull("La cotizacion no puede ser nula", cotizacion);
        Assert.assertTrue("La cotizaciond ebe ser con la fecha actual", 
                DateUtils.isSameDay(new Date(), cotizacion.getFecha()));
        Assert.assertNotNull("El detalle no puede ser nula", cotizacion.getDetalle());
        Assert.assertEquals("La cuota totl debe ser igual", cotizacion.getCuotaTotal(), new BigDecimal("7531.55"));
        EasyMock.verify(entityManager); 
    }
    
    @Test(expected = SUAException.class)
    public void testGeneraYGuardaCotizacion_EX() throws SUAException {
        cotizadorEntity.generaYGuardaCotizacion(null);
    }
    
    @Test(expected = SUAException.class)
    public void testGeneraCotizacion_EX() throws SUAException {
        Cotizacion cotizacion = new Cotizacion();
        CotizadorFactoryUtil.generaDeModeloXml(cotizacion);
    }
    
    @Test
    public void testFindCotizacion() throws SUAException {
        entityManager = EasyMock.createMock(EntityManager.class);
        EasyMock.expect(entityManager.find(DitCotizacion.class, new Long(1))).andReturn(new DitCotizacion());
        Whitebox.setInternalState(cotizadorEntity, "entityManager", entityManager);
        EasyMock.replay(entityManager);
        
        Cotizacion cotizacion = cotizadorEntity.findCotizacion(1);
        Assert.assertNotNull("La cotizacion no puede ser nula", cotizacion);
        EasyMock.verify(entityManager);
        
    }
    
    @Test(expected = SUAException.class)
    public void testFindCotizacion_Ex() throws SUAException {
        entityManager = EasyMock.createMock(EntityManager.class);
        EasyMock.expect(entityManager.find(DitCotizacion.class, new Long(1))).andReturn(null);
        Whitebox.setInternalState(cotizadorEntity, "entityManager", entityManager);
        EasyMock.replay(entityManager);
        
        Cotizacion cotizacion = cotizadorEntity.findCotizacion(1);
        Assert.assertNotNull("La cotizacion no puede ser nula", cotizacion);
        EasyMock.verify(entityManager);
        
    }
    
  
    /**
     * Obtiene el objeo de calculo cuota default
     * @return
     */
    private CalculoCuota getCalculoCuota() {
        try {
            return JaxbUtilT.unmarshaller(XML_CALCULO_CUOTA, CalculoCuota.class);
        } catch (Exception e) {
            return null;
        }
        
    }
}
