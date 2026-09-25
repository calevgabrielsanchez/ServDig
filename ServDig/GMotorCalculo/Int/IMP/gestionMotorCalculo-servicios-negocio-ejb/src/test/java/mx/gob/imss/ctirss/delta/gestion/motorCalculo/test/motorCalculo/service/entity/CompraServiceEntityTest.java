/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.test.motorCalculo.service.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import junit.framework.Assert;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.entity.CompraServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.entity.ParametrosEntity;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CompraServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CotizacionServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.PublicaCompraVencida;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.test.sua.util.JaxbUtilT;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.EstadoPagoEnum;
import mx.gob.imss.ctirss.delta.persistence.cobranza.DicEstadoPago;
import mx.gob.imss.ctirss.delta.persistence.cobranza.DitCompra;
import mx.gob.imss.ctirss.delta.persistence.cobranza.DitPago;
import mx.gob.imss.digital.modelo.cobranza.ActualizacionCompra;
import mx.gob.imss.digital.modelo.cobranza.Compra;
import mx.gob.imss.digital.modelo.cobranza.DatosCompra;
import mx.gob.imss.digital.modelo.cobranza.Pago;

import org.apache.commons.lang.time.DateUtils;
import org.easymock.EasyMock;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

/**
 * Clase de prubas paa el servicio de compras
 * @author NOVUTECK1
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(CompraServiceEntity.class)
public class CompraServiceEntityTest {

    /**
     * Servicio de prueba
     */
    private CompraServiceEntity compraServiceEntity = new CompraServiceEntity();
    /**
     * Entity manager mock
     */
    private EntityManager entityManager;
    /**
     * Archivo con un xml para los datos que se calcularon
     */
    private static final String XML_COMPRA = "src/test/resources/CompraTest.xml";
    
    /**
     * Query para obtener las compras y pagos bimestrales a ser marcados como
     * pagados
     */
    private static final String GET_PAGO_LC_BIMESTRAL = "SELECT compra, pago FROM DitPago pago "
            + " JOIN pago.ditCompra compra WHERE compra.dicFormaPgo = :bimestral AND pago.desLineaCaptura in (:lc)";

    /**
     * Query para obtener las compras y pagos bimestrales a ser marcados como
     * pagados
     */
    private static final String GET_PAGO_LC_ANUAL = "SELECT pago FROM DitPago pago JOIN pago.ditCompra compra "
            + "WHERE compra.dicFormaPgo = :anual AND pago.desLineaCaptura in (:lc)";
    
    /**
     * Query para obtener las compras y pagos bimestrales a ser marcados como
     * pagados
     */
    private static final String GET_COMPRA_LC_ANUAL = "SELECT Distinct compra FROM DitPago pago "
            + " JOIN pago.ditCompra compra WHERE compra.dicFormaPgo = :anual AND pago.desLineaCaptura in (:lc)";
    

    /**
     * Query para obtener pagos vencidos
     */
    private static final String PAGOS_VENCIDOS = "FROM DitPago pago where pago.fecLimitePago < :fechaLimite and "
            + " pago.dicEstadoPago = :estadoPorPagar";

    /**
     * Query para obtener compras vencidas
     */
    private static final String COMPRAS_VENCIDAS = "SELECT distinct compra FROM DitCompra compra "
            + " JOIN compra.ditPagos pago "
            + " WHERE compra.dicEstadoCompra = :compraPorPagar AND pago.dicEstadoPago = :estadoVencido ";
    
    @Test
    public void testGuardaCompra() throws SUAException {
        entityManager = EasyMock.createMock(EntityManager.class);
        entityManager.persist(EasyMock.anyObject());
        
        EasyMock.expectLastCall().anyTimes();
        
        Whitebox.setInternalState(compraServiceEntity, "entityManager", entityManager);
        EasyMock.replay(entityManager);
        
        Compra compra = compraServiceEntity.guardaCompra(getCompraPrueba());
        Assert.assertNotNull("La compra no puede ser nula", compra);
        Assert.assertTrue("La compra ebe ser con la fecha actual", 
                DateUtils.isSameDay(new Date(), compra.getFechaCompra()));
        EasyMock.verify(entityManager); 
    }
    
    @Test
    public void testactualizaPagosLC() throws SUAException {
        
        entityManager = EasyMock.createMock(EntityManager.class);
        
        
        EasyMock.expect(entityManager.find(DitPago.class, new Long(113))).andReturn(new DitPago());        
        entityManager.merge(EasyMock.anyObject());
        EasyMock.expectLastCall().andReturn(new DitPago());
        EasyMock.expectLastCall().atLeastOnce();
        
        Whitebox.setInternalState(compraServiceEntity, "entityManager", entityManager);
        EasyMock.replay(entityManager);
        Pago[] pagosAct = getCompraPrueba().getPagos();
        
        List<Pago> pagos = compraServiceEntity.actualizaPagosLC(Arrays.asList(pagosAct));
        Assert.assertNotNull("Los pagos no pueden ser nulos", pagos);
        for (Pago pago : pagos) {
            Assert.assertNull("El pago no debe tener detalle", pago.getSuaPago());            
        }        
        EasyMock.verify(entityManager); 
    }
    
    /**
     * Sin id de pago no se puede actualizar
     * @throws SUAException
     */
    @Test(expected = SUAException.class)
    public void testactualizaPagosLC_ex() throws SUAException {
        
        List<Pago> pagosAct = new ArrayList<Pago>();
        pagosAct.add(new Pago());
        compraServiceEntity.actualizaPagosLC(pagosAct);
         
    }
    
    /**
     * Prueba de consulta de una compra
     * @throws SUAException
     */
    @Test
    @Ignore
    public void testFindCompraById() throws SUAException {
        entityManager = EasyMock.createMock(EntityManager.class);
        EasyMock.expect(entityManager.find(DitCompra.class, new Long(1))).andReturn(new DitCompra());
        Whitebox.setInternalState(compraServiceEntity, "entityManager", entityManager);
        EasyMock.replay(entityManager);
        
        Compra compra = compraServiceEntity.findCompraById(1);
        Assert.assertNotNull("La compra no puede ser nula", compra);
        EasyMock.verify(entityManager);
    }
    
    /**
     * Prueba de una consulta que no se encuentra
     * @throws SUAException
     */
    @Test(expected = SUAException.class)
    public void testFindCompraById_Ex() throws SUAException {
        entityManager = EasyMock.createMock(EntityManager.class);
        EasyMock.expect(entityManager.find(DitCompra.class, new Long(1))).andReturn(null);
        Whitebox.setInternalState(compraServiceEntity, "entityManager", entityManager);
        EasyMock.replay(entityManager);        
        compraServiceEntity.findCompraById(1);        
        EasyMock.verify(entityManager);
    }
    
    /**
     * Prueba de consulta de un pago
     * @throws SUAException
     */
    @Test
    public void testFindPagoById() throws SUAException {
        entityManager = EasyMock.createMock(EntityManager.class);
        EasyMock.expect(entityManager.find(DitPago.class, new Long(1))).andReturn(new DitPago());
        Whitebox.setInternalState(compraServiceEntity, "entityManager", entityManager);
        EasyMock.replay(entityManager);
        
        Pago pago = compraServiceEntity.findPagoById(1);
        Assert.assertNotNull("El pago no puede ser nulo", pago);
        EasyMock.verify(entityManager);
    }
    
    /**
     * Prueba de consulta de un pago no existente
     * @throws SUAException
     */
    @Test(expected = SUAException.class)
    public void testFindPagoById_ex() throws SUAException {
        entityManager = EasyMock.createMock(EntityManager.class);
        EasyMock.expect(entityManager.find(DitPago.class, new Long(1))).andReturn(null);
        Whitebox.setInternalState(compraServiceEntity, "entityManager", entityManager);
        EasyMock.replay(entityManager);        
        compraServiceEntity.findPagoById(1);        
        EasyMock.verify(entityManager);
    }
    
    /**
     * Compra de prueba
     * @return
     */
    private Compra getCompraPrueba() {
        try {
            return JaxbUtilT.unmarshaller(XML_COMPRA, Compra.class);
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Prueba para verificar el marcado de compras pagadas
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testPagosPagados() {
        List<String> lineasCaptura = new ArrayList<String>();
        lineasCaptura.add("N212R5N74BHK2C7T8219700011ZP00009RV00000000000000352R");
                
        entityManager = EasyMock.createMock(EntityManager.class);
        // Pagos bimestrales
        Query query = EasyMock.createMock(Query.class);        
        EasyMock.expect(entityManager.createQuery(GET_PAGO_LC_BIMESTRAL)).andReturn(query);
        EasyMock.expect(query.setParameter("lc", lineasCaptura)).andReturn(query);
        EasyMock.expect(query.setParameter(EasyMock.anyString(), EasyMock.anyObject())).andReturn(query);
        DitPago pago1 = getPago(true);
        DitPago pago2 = getPago(false);
        DitCompra compra = new DitCompra();
        List<DitPago> ditPagos = new ArrayList<DitPago>();
        
        ditPagos.add(pago1);
        ditPagos.add(pago2);
        compra.setDitPagos(ditPagos);
        compra.setCveIdCompra(1L);
        pago1.setDitCompra(compra);
        pago2.setDitCompra(compra);
        List<Object[]> resultados = new ArrayList<Object[]>();
        resultados.add(new Object[]{compra, pago1});
        EasyMock.expect(query.getResultList()).andReturn(resultados);

        EasyMock.expect(entityManager.merge(EasyMock.anyObject())).andReturn(pago1);
        
        // // Actualizacion de pagos anuales
        TypedQuery<DitPago> typedQuery = EasyMock.createMock(TypedQuery.class);        
        EasyMock.expect(entityManager.createQuery(GET_PAGO_LC_ANUAL, DitPago.class)).andReturn(typedQuery);
        EasyMock.expect(typedQuery.setParameter("lc", lineasCaptura)).andReturn(typedQuery);
        EasyMock.expect(typedQuery.setParameter(EasyMock.anyString(), EasyMock.anyObject())).andReturn(typedQuery);
        EasyMock.expect(typedQuery.getResultList()).andReturn(ditPagos);
        EasyMock.expect(entityManager.merge(pago1)).andReturn(pago1);
        EasyMock.expect(entityManager.merge(pago2)).andThrow(new NoResultException());
        
        // Actualizacion de compras anuales
        List<DitCompra> ditCompras = new ArrayList<DitCompra>();
        ditCompras.add(compra);
        TypedQuery<DitCompra> typedQueryC = EasyMock.createMock(TypedQuery.class);        
        EasyMock.expect(entityManager.createQuery(GET_COMPRA_LC_ANUAL, DitCompra.class)).andReturn(typedQueryC);
        EasyMock.expect(typedQueryC.setParameter("lc", lineasCaptura)).andReturn(typedQueryC);
        EasyMock.expect(typedQueryC.setParameter(EasyMock.anyString(), EasyMock.anyObject())).andReturn(typedQueryC);
        EasyMock.expect(typedQueryC.getResultList()).andReturn(ditCompras);
        EasyMock.expect(entityManager.merge(compra)).andReturn(compra);
        EasyMock.expect(entityManager.merge(compra)).andReturn(compra);
                
        Whitebox.setInternalState(compraServiceEntity, "entityManager", entityManager);
        
        EasyMock.replay(query, entityManager, typedQuery, typedQueryC);
        
        ActualizacionCompra actualizacion = compraServiceEntity.pagosPagados(lineasCaptura);
        Assert.assertNotNull("La actualizacion no puede ser nula", actualizacion);
        Assert.assertNotNull("La compras no puede ser nula", actualizacion.getCompras());
        Assert.assertNotNull("La lineas en error no puede ser nula", actualizacion.getLineasEnError());
        Assert.assertFalse("La compras no puede ser nula", actualizacion.getCompras().length == 0);
        Assert.assertFalse("La lineas en error no puede ser nula", actualizacion.getLineasEnError().length == 0);
        
        String[] lineasError = actualizacion.getLineasEnError();
        DatosCompra[] datosCompra = actualizacion.getCompras();
        Assert.assertEquals("Debe generarse una linea de error", lineasError.length, 1);
        Assert.assertEquals("Debe generarse dos compra pagadas", datosCompra.length, 2);
        boolean anual = false; 
        boolean bimestral = false;
        for(DatosCompra dato : datosCompra) {
            if(dato.getBimestral()){
                bimestral = true;
            } else {
                anual = true;
            }
        }
        Assert.assertEquals("Debe generarse una compra anual", anual, true);
        Assert.assertEquals("Debe generarse una compra bimestral", bimestral, true);
        EasyMock.verify(query, entityManager);
    }
    
    /**
     * MEtodo utilitario para generar pagos de prueba
     * @param actual indica si es el pago actual de una compa
     * @return le pago generado
     */
    private DitPago getPago(boolean actual) {
        DitPago pago = new DitPago();
        pago.setCveIdPago(actual ? 1L : 2L);
        Calendar hoy = Calendar.getInstance();
        if(!actual){
            hoy.add(Calendar.MONTH, 2);
        }
        pago.setFecIniPeriodo(hoy.getTime());
        pago.setFecFinPeriodo(hoy.getTime());
        pago.setFecLimitePago(hoy.getTime());
        DicEstadoPago estado = new DicEstadoPago();
        estado.setCveIdEstadoPago(EstadoPagoEnum.POR_PAGAR.getId());
        
        DicEstadoPago pagado = new DicEstadoPago();
        pagado.setCveIdEstadoPago(EstadoPagoEnum.PAGADO.getId());
        
        pago.setDicEstadoPago(actual ? pagado : estado);
        return pago;
    }
    
    /**
     * Prueba para verificar el marcado de pagos y compras vencidas        
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testPagosVencidosQueue() { 
        
        ParametrosEntity parametrosEntity = EasyMock.createMock(ParametrosEntity.class);
        EasyMock.expect(parametrosEntity.getDiasVEncimiento()).andReturn(Integer.valueOf(5));
        Whitebox.setInternalState(compraServiceEntity, "parametrosEntity", parametrosEntity);
        
        entityManager = EasyMock.createMock(EntityManager.class);
        DitPago pago1 = getPago(true);
        DitPago pago2 = getPago(false);
        List<DitPago> ditPagos = new ArrayList<DitPago>();        
        ditPagos.add(pago1);
        ditPagos.add(pago2);
        
        Calendar fechaLimite = Calendar.getInstance();
        fechaLimite.add(Calendar.DATE, 5);
        fechaLimite = DateUtils.truncate(fechaLimite, Calendar.DATE);
        
        TypedQuery<DitPago> typedQuery = EasyMock.createMock(TypedQuery.class);        
        EasyMock.expect(entityManager.createQuery(PAGOS_VENCIDOS, DitPago.class)).andReturn(typedQuery);
        EasyMock.expect(typedQuery.setParameter(EasyMock.anyString(), EasyMock.anyObject())).andReturn(typedQuery);
        EasyMock.expect(typedQuery.setParameter("fechaLimite", fechaLimite.getTime(), TemporalType.DATE)).andReturn(typedQuery);
        EasyMock.expect(typedQuery.getResultList()).andReturn(ditPagos);
        
        EasyMock.expect(entityManager.merge(pago1)).andReturn(pago1);
        EasyMock.expect(entityManager.merge(pago2)).andReturn(pago2);
        
        DitCompra compra = new DitCompra();
        compra.setCveIdCompra(1L);
        List<DitCompra> compras = new ArrayList<DitCompra>();
        compras.add(compra);
        TypedQuery<DitCompra> typedQueryC = EasyMock.createMock(TypedQuery.class);        
        EasyMock.expect(entityManager.createQuery(COMPRAS_VENCIDAS, DitCompra.class)).andReturn(typedQueryC);
        EasyMock.expect(typedQueryC.setParameter(EasyMock.anyString(), EasyMock.anyObject())).andReturn(typedQueryC);
        EasyMock.expect(typedQueryC.setParameter(EasyMock.anyString(), EasyMock.anyObject())).andReturn(typedQueryC);
        EasyMock.expect(typedQueryC.getResultList()).andReturn(compras);
        
        EasyMock.expect(entityManager.merge(compra)).andReturn(compra);
        
        Whitebox.setInternalState(compraServiceEntity, "entityManager", entityManager);
        PublicaCompraVencida  publicaCompraVencida = EasyMock.createMock(PublicaCompraVencida.class);
        publicaCompraVencida.publicaCompraVencida(EasyMock.anyObject(ActualizacionCompra.class));
        EasyMock.expectLastCall();
        Whitebox.setInternalState(compraServiceEntity, "publicaCompraVencida", publicaCompraVencida);
        
        
        EasyMock.replay(parametrosEntity, entityManager, typedQuery, typedQueryC, publicaCompraVencida);
        List<DatosCompra> vencidas = compraServiceEntity.pagosVencidosQueue();
        Assert.assertNotNull("LA compras vencidas no pueden ser nulas", vencidas);
        Assert.assertFalse("La comras vencidas no pueden ser vacias", vencidas.isEmpty());
        Assert.assertEquals("a compra vencida debe ser id 1", (Long)(vencidas.get(0).getIdCompra()), (Long)1L);
        
        EasyMock.verify(parametrosEntity, entityManager);
    }
    
    
    /**
     * Prueba de ejecucion de un EJB 
     * @throws NamingException
     */
    //@Test
    @Ignore
    public void testPublica() throws Exception {
        Context iCtx = null; 
        try {
            final Hashtable<String, String> env = new Hashtable<String, String>(); 
            env.put(Context.INITIAL_CONTEXT_FACTORY,
                    "weblogic.jndi.WLInitialContextFactory");
            env.put(Context.PROVIDER_URL, "t3://localhost:8001");
            env.put(Context.SECURITY_PRINCIPAL, "weblogic");
            env.put(Context.SECURITY_CREDENTIALS, "weblogic1");
            iCtx = new InitialContext(env);

        } catch (NamingException e) {
            e.printStackTrace();
        }
        CompraServiceRemote compraEjb = (CompraServiceRemote)iCtx.lookup("compraServiceBusiness#" +
        		"mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CompraServiceRemote");
        
        //compraEjb.pagosVencidosQueue();
        CotizacionServiceRemote cot = (CotizacionServiceRemote) iCtx.lookup(
        "cotizacionServiceBusiness#mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CotizacionServiceRemote");
        cot.findCotizacion(27);
    }
}
