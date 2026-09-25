/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.test.sua.service.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.business.GeneradorCompraServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CompraServiceLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CotizadorEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.SuaServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.test.sua.util.JaxbUtilT;
import mx.gob.imss.digital.modelo.cobranza.Compra;
import mx.gob.imss.digital.modelo.cobranza.Cotizacion;
import mx.gob.imss.digital.modelo.cobranza.RegistroValidacion;
import mx.gob.imss.digital.modelo.cobranza.SUAPago;

import org.apache.commons.lang.time.DateUtils;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IAnswer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

/**
 * Clase de prueba para la generacion de una compra
 * @author NOVUTECK1
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(GeneradorCompraServiceBusiness.class)
public class GeneradorCompraServiceTest {

    /**
     * Bean a probar
     */
    private GeneradorCompraServiceBusiness generadorCompraService = new GeneradorCompraServiceBusiness();
    
    /**
     * Mock de la cotizacion
     */
    private CotizadorEntityLocal cotizadorEntityLocal;
    /**
     * Servicio para generar los SUas mock
     */
    private SuaServiceRemote suaServiceRemote;
    /**
     * Servicio paa el manejo de compras a nivel persistencia mock
     */
    private CompraServiceLocal compraServiceLocal;
    
    /**
     * Archivo con un xml para los datos que se calcularon
     */
    private static final String XML_COTIZACION = "src/test/resources/CotizacionTest.xml";
    
    @Test
    public void testGeneraCompra() throws SUAException {
        Cotizacion cotizacionPrueba = getCotizacion();
        
        cotizadorEntityLocal = EasyMock.createMock(CotizadorEntityLocal.class);
        EasyMock.expect(cotizadorEntityLocal.findCotizacion(cotizacionPrueba.getIdCotizacion().longValue()))
        .andReturn(cotizacionPrueba);
        Whitebox.setInternalState(generadorCompraService, "cotizadorEntityLocal", cotizadorEntityLocal);
        
        suaServiceRemote = EasyMock.createMock(SuaServiceRemote.class);
        EasyMock.expect(suaServiceRemote.generaDatosSua(cotizacionPrueba.getDetalle(), null)).andReturn(getSuas());
        Whitebox.setInternalState(generadorCompraService, "suaServiceRemote", suaServiceRemote);
        
        compraServiceLocal = EasyMock.createMock(CompraServiceLocal.class);
        final Capture<Compra> compraC = new Capture<Compra>();
        EasyMock.expect(compraServiceLocal.guardaCompra(EasyMock.capture(compraC))).andAnswer(
                new IAnswer<Compra>() {
                    @Override
                    public Compra answer() throws Throwable {                        
                        return compraC.getValue();
                    }                    
        });
        Whitebox.setInternalState(generadorCompraService, "compraServiceLocal", compraServiceLocal);
        
        EasyMock.replay(cotizadorEntityLocal, suaServiceRemote, compraServiceLocal);
        Compra compra = generadorCompraService.generaCompra(cotizacionPrueba);
        
        Assert.assertNotNull("La compra no puede ser vacia", compra);
        Assert.assertNotNull("La compra no puede ser vacia", compra.getPagos());
        Assert.assertEquals("Solo contiene un pago", compra.getPagos().length, 1);
        Assert.assertTrue("La fecha de compra debe ser la actual", DateUtils.isSameDay(compra.getFechaCompra(), new Date()));
        Assert.assertTrue("La fecha limite debe ser la actual", DateUtils.isSameDay(compra.getFechaLimite(), new Date()));
        
        EasyMock.verify(cotizadorEntityLocal, suaServiceRemote, compraServiceLocal);
    }
    
    
    @Test
    public void testGeneraCompra_cotSinId() throws SUAException {
        Cotizacion cotizacionPrueba = getCotizacion();
        cotizacionPrueba.setIdCotizacion(null);
        
        Cotizacion cotizacionPrueba2 = getCotizacion();
        cotizacionPrueba2.getDetalle().setNumeroRegistroPatronal(null);
        
        cotizadorEntityLocal = EasyMock.createMock(CotizadorEntityLocal.class);
        EasyMock.expect(cotizadorEntityLocal.guardaCotizacion(cotizacionPrueba)).andReturn(cotizacionPrueba2);
        EasyMock.expect(cotizadorEntityLocal.actualizaCotizacion(cotizacionPrueba2)).andReturn(cotizacionPrueba2);
        Whitebox.setInternalState(generadorCompraService, "cotizadorEntityLocal", cotizadorEntityLocal);
        
        suaServiceRemote = EasyMock.createMock(SuaServiceRemote.class);
        EasyMock.expect(suaServiceRemote.generaDatosSua(cotizacionPrueba2.getDetalle(), null)).andReturn(getSuas());
        Whitebox.setInternalState(generadorCompraService, "suaServiceRemote", suaServiceRemote);
        
        compraServiceLocal = EasyMock.createMock(CompraServiceLocal.class);
        final Capture<Compra> compraC = new Capture<Compra>();
        EasyMock.expect(compraServiceLocal.guardaCompra(EasyMock.capture(compraC))).andAnswer(
                new IAnswer<Compra>() {
                    @Override
                    public Compra answer() throws Throwable {                        
                        return compraC.getValue();
                    }                    
        });
        Whitebox.setInternalState(generadorCompraService, "compraServiceLocal", compraServiceLocal);
        
        EasyMock.replay(cotizadorEntityLocal, suaServiceRemote, compraServiceLocal);
        Compra compra = generadorCompraService.generaCompra(cotizacionPrueba);
        
        Assert.assertNotNull("La compra no puede ser vacia", compra);
        Assert.assertNotNull("La compra no puede ser vacia", compra.getPagos());
        Assert.assertEquals("Solo contiene un pago", compra.getPagos().length, 1);
        Assert.assertTrue("La fecha de compra debe ser la actual", DateUtils.isSameDay(compra.getFechaCompra(), new Date()));
        Assert.assertTrue("La fecha limite debe ser la actual", DateUtils.isSameDay(compra.getFechaLimite(), new Date()));
        
        EasyMock.verify(cotizadorEntityLocal, suaServiceRemote, compraServiceLocal);
    }
    
    @Test(expected = SUAException.class)
    public void testGeneraCompra_ExNRP() throws SUAException {
        Cotizacion cotizacionPrueba = getCotizacion();
        cotizacionPrueba.setIdCotizacion(null);
        cotizacionPrueba.getDetalle().setNumeroRegistroPatronal(null);
        cotizadorEntityLocal = EasyMock.createMock(CotizadorEntityLocal.class);
        EasyMock.expect(cotizadorEntityLocal.guardaCotizacion(cotizacionPrueba)).andReturn(cotizacionPrueba);
        Whitebox.setInternalState(generadorCompraService, "cotizadorEntityLocal", cotizadorEntityLocal);
        
        
        EasyMock.replay(cotizadorEntityLocal);
        
        generadorCompraService.generaCompra(cotizacionPrueba);
        
        EasyMock.verify(cotizadorEntityLocal);
    }
    /**
     * Obtiene el objeo de calculo cuota default
     * @return
     */
    private Cotizacion getCotizacion () {
        try {
            return JaxbUtilT.unmarshaller(XML_COTIZACION, Cotizacion.class);
        } catch (Exception e) {
            return null;
        }
        
    }
    
    private SUAPago[] getSuas() {
        SUAPago sua = new SUAPago(); 
        sua.setFechaInicio(new Date());
        sua.setFechaFin(new Date());
        sua.setRegistroValidacion(new RegistroValidacion());
        sua.getRegistroValidacion().setFechaLimiteDePago(new Date());
        sua.setMonto(BigDecimal.TEN);
        List<SUAPago> suas = new ArrayList<SUAPago>();
        suas.add(sua);
        return suas.toArray(new SUAPago[suas.size()]);
    }
}
