/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.test.motorCalculo.service.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import junit.framework.Assert;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.entity.DicFactorModalidadRamaEntity;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.ClavesRama;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.ClavesTipoAportacion;
import mx.gob.imss.digital.modelo.cobranza.RamaCalculo;

import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

/**
 * @author NOVUTECK1
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(DicFactorModalidadRamaEntity.class)
public class DicFactorModalidadRamaTest {
    /**
     * MOdalidad de prueba que solo obtiene la lista de ramas
     */
    private static final long MODALIDAD = 22;
    /**
     * NUmero de registro patronal de prueba
     */
    private static final String NRP = "NRP22233";
    /**
     * Modalidad de prueba que debe obtener prima de riesgo por parte del patron
     */
    private static final long MODALIDAD_35 = 17;
    /**
     * Servicio para la consulta de modalidades de pago
     */
    DicFactorModalidadRamaEntity dicFactor = new DicFactorModalidadRamaEntity();
    
    /**
     * Busca las cuotas asociadas a una modalidad    
     * @throws Exception
     */
    @Test
    public void testBuscarRamasModalidad() throws Exception{
               
        EntityManager entityManager = EasyMock.createMock(EntityManager.class);
        @SuppressWarnings("unchecked")
        TypedQuery<RamaCalculo> typedQuery = EasyMock.createMock(TypedQuery.class);
                
        EasyMock.expect(typedQuery.setParameter(1, MODALIDAD)).andReturn(typedQuery);
        EasyMock.expect(typedQuery.getResultList()).andReturn(new ArrayList<RamaCalculo>());       
        
        EasyMock.expect(entityManager.createQuery(getQueryRamasModalidad(), RamaCalculo.class)).andReturn(typedQuery);
        
        Whitebox.setInternalState(dicFactor, "entityManager", entityManager);
        
        EasyMock.replay(typedQuery, entityManager);
        
        List<RamaCalculo> ramas = dicFactor.buscarRamasModalidad(MODALIDAD);
        Assert.assertNotNull("Las ramas de calculo no pueden se nula", ramas);        
        EasyMock.verify(typedQuery, entityManager); 
        
    }
    
    /**
     * Busca la prima de riesgo asociada a un patron
     */
    @Test
    public void testBuscarRamaSRT () throws SUAException {
        
        EntityManager entityManager = EasyMock.createMock(EntityManager.class);
        @SuppressWarnings("unchecked")
        TypedQuery<BigDecimal> typedQuery = EasyMock.createMock(TypedQuery.class);
                
        EasyMock.expect(typedQuery.setParameter(1, NRP)).andReturn(typedQuery);
        EasyMock.expect(typedQuery.getSingleResult()).andReturn(BigDecimal.TEN);        
        EasyMock.expect(entityManager.createQuery(getQueryRamaSRT(), BigDecimal.class)).andReturn(typedQuery);
        
        Whitebox.setInternalState(dicFactor, "entityManager", entityManager);
        
        EasyMock.replay(typedQuery, entityManager);
        
        RamaCalculo rama = dicFactor.buscarRamaSRT(NRP);
        Assert.assertNotNull("LA rama de calculo no puede ser nula", rama);
        Assert.assertEquals("El tipo de rama debe ser igual", 
                rama.getIdRama().intValue(), ClavesRama.RIESGOS_TRABAJO);
        Assert.assertEquals("El tipo de aportacion debe ser igual", 
                rama.getIdTipoAportacion().intValue(), ClavesTipoAportacion.PATRONAL);
        Assert.assertEquals("El Valor de la aportacion debe ser 10", 
                rama.getFactorCalculo(), BigDecimal.TEN);
                
        EasyMock.verify(typedQuery, entityManager);
    }
    
    /**
     * Busca la prima de riesgo asociada al patron con error en la consulta
     */
    @Test
    public void testBuscarRamaSRT_ex () throws SUAException {
        
        EntityManager entityManager = EasyMock.createMock(EntityManager.class);
        @SuppressWarnings("unchecked")
        TypedQuery<BigDecimal> typedQuery = EasyMock.createMock(TypedQuery.class);
                
        EasyMock.expect(typedQuery.setParameter(1, NRP)).andReturn(typedQuery);
        EasyMock.expect(typedQuery.getSingleResult()).andThrow(new NoResultException("No hay resultaos desde la prueba"));        
        EasyMock.expect(entityManager.createQuery(getQueryRamaSRT(), BigDecimal.class)).andReturn(typedQuery);
        
        Whitebox.setInternalState(dicFactor, "entityManager", entityManager);
        
        EasyMock.replay(typedQuery, entityManager);
        
        RamaCalculo rama = dicFactor.buscarRamaSRT(NRP);
        Assert.assertNotNull("LA rama de calculo no puede ser nula", rama);
        Assert.assertEquals("El tipo de rama debe ser igual", 
                rama.getIdRama().intValue(), ClavesRama.RIESGOS_TRABAJO);
        Assert.assertEquals("El tipo de aportacion debe ser igual", 
                rama.getIdTipoAportacion().intValue(), ClavesTipoAportacion.PATRONAL);
        Assert.assertEquals("El Valor de la aportacion debe ser 0 si hay error en la consulta", 
                rama.getFactorCalculo(), BigDecimal.ZERO);
                
        EasyMock.verify(typedQuery, entityManager);
    }
    
    /**
     * Busaca todas la ramas de callcul a sociadas a un trabajador 
     * @throws Exception
     */
    @Test
    public void testBuscarRamasCalculo() throws Exception{
               
        EntityManager entityManager = EasyMock.createMock(EntityManager.class);
        @SuppressWarnings("unchecked")
        TypedQuery<RamaCalculo> typedQuery = EasyMock.createMock(TypedQuery.class);
                
        EasyMock.expect(typedQuery.setParameter(1, MODALIDAD)).andReturn(typedQuery);
        EasyMock.expect(typedQuery.getResultList()).andReturn(new ArrayList<RamaCalculo>());       
        
        EasyMock.expect(entityManager.createQuery(getQueryRamasModalidad(), RamaCalculo.class)).andReturn(typedQuery);
        
        Whitebox.setInternalState(dicFactor, "entityManager", entityManager);
        
        EasyMock.replay(typedQuery, entityManager);
        
        List<RamaCalculo> ramas = dicFactor.buscarRamasCalculo(MODALIDAD, NRP);
        Assert.assertNotNull("Las ramas de calculo no pueden se nula", ramas);
                
        EasyMock.verify(typedQuery, entityManager); 
        
    }
    
    /**
     * busca todas las ramas de calculo asociadas a un trabajador incluyendo la sobreescritura de la prima 
     * de riesgo de trabajao
     * @throws Exception
     */
    @Test
    public void testBuscarRamasCalculo_PrimaRiesgo() throws Exception{
               
        DicFactorModalidadRamaEntity factorRama = EasyMock.createMockBuilder(
                DicFactorModalidadRamaEntity.class).addMockedMethod("buscarRamasModalidad").addMockedMethod("buscarRamaSRT").createMock();
        
        RamaCalculo ramaRiesgoTrabajo = new RamaCalculo("NRP", "NRP", BigDecimal.ZERO, 
                Integer.valueOf(ClavesRama.RIESGOS_TRABAJO), Integer.valueOf(ClavesTipoAportacion.PATRONAL));
        RamaCalculo ramaCuotaFija = new RamaCalculo("NRP", "NRP", BigDecimal.ZERO, 
                Integer.valueOf(ClavesRama.CUOTA_FIJA), Integer.valueOf(ClavesTipoAportacion.PATRONAL));
        List<RamaCalculo> ramasMock = new ArrayList<RamaCalculo>();
        ramasMock.add(ramaRiesgoTrabajo);
        ramasMock.add(ramaCuotaFija);
        
        EasyMock.expect(factorRama.buscarRamasModalidad(MODALIDAD_35)).andReturn(ramasMock);
        EasyMock.expect(factorRama.buscarRamaSRT(NRP)).andReturn(ramaRiesgoTrabajo);
        
        EasyMock.replay(factorRama);
        
        List<RamaCalculo> ramas = factorRama.buscarRamasCalculo(MODALIDAD_35, NRP);
        Assert.assertNotNull("Las ramas de calculo no pueden se nula", ramas);
        Assert.assertFalse("Las ramas de calculo no pueden ser vacias", ramas.isEmpty());
        Assert.assertEquals("Las ramas solo deben ser una por sobreescribir la rt", ramas.size(), 2);
        
        EasyMock.verify(factorRama); 
        
    }
    
    private String getQueryRamasModalidad () {
        StringBuilder q = new StringBuilder("select ")
        .append("new mx.gob.imss.digital.modelo.cobranza.RamaCalculo(\n")
        .append("rama.desRama,\n")
        .append("tipoAportacion.desTipoAportacion,\n")
        .append("factormodalidadramas.numFactor,\n")
        .append("rama.cveIdRama,\n")
        .append("tipoAportacion.cveIdTipoAportacion)\n")
        .append("From DicRama rama join rama.dicFactorModalidadRamas factormodalidadramas\n")
        .append("join factormodalidadramas.dicTipoAportacion tipoAportacion\n")
        .append("where factormodalidadramas.dicModalidad.cveIdModalidad = ?");
        return q.toString();
    }
    
    private String getQueryRamaSRT() {
        StringBuilder q = new StringBuilder("select \n").append("clasificacion.numPrimaPago\n")
                .append("FROM DitClasificacion clasificacion \n")
                .append("join clasificacion.ditPatronSujetoObligado sujetoObligado\n")
                .append("join sujetoObligado.ditPatronGenerals patronGeneral\n")
                .append("where clasificacion.fecRegistroBaja is null\n")
                .append("and patronGeneral.regPatron = ?");
        return q.toString();
    }

}
