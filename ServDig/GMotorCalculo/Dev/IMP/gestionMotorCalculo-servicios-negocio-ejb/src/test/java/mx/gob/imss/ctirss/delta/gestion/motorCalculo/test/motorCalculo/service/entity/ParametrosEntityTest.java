/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.test.motorCalculo.service.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import junit.framework.Assert;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.entity.ParametrosEntity;
import mx.gob.imss.ctirss.delta.service.interfaces.ParametrosServiceRemote;

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
@PrepareForTest(ParametrosEntity.class)
public class ParametrosEntityTest {
    
    /**
     * CLave del catalogo para obtener los dias
     */
    private static final String CLAVE_DIAS = "NUM_DIAS_VIG";
    /**
     * entitiy manager mock
     */
    private EntityManager entityManager;
    /**
     * TypedQuery Mock
     */
    private TypedQuery<Date> typedQuery;
    /**
     * Servicio moc de los parametros mock
     */
    private ParametrosServiceRemote parametrosRemote;
    /**
     * Servicio de prueba
     */
    private ParametrosEntity parametrosEntity = new ParametrosEntity();
    
    @Test
    public void testGetDiasFeriados() {
        iniciaMock();
        List<Date> dias = parametrosEntity.getDiasFeriados();
        Assert.assertNotNull("Los dias no pueden ser nulos", dias);
        terminaMock();
    }
    /**
     * Inicia los mocks para realizar las pruebas
     * @param valores
     */
    @SuppressWarnings("unchecked")
    private void iniciaMock () {
        entityManager = EasyMock.createMock(EntityManager.class);                
        typedQuery = EasyMock.createMock(TypedQuery.class);                
        EasyMock.expect(typedQuery.getResultList()).andReturn(new ArrayList<Date>());
                
        EasyMock.expect(entityManager.createQuery("select dias.fecDiaFestivo from DicDiasFestivo dias", 
                Date.class)).andReturn(typedQuery);
        
        Whitebox.setInternalState(parametrosEntity, "entityManager", entityManager);
        
        EasyMock.replay(typedQuery, entityManager);
    }
    /**
     * Termina los mocks
     */
    private void terminaMock() {
        EasyMock.verify(typedQuery, entityManager);
    }

    @Test
    public void testGetDiasVEncimiento(){
        parametrosRemote = EasyMock.createMock(ParametrosServiceRemote.class);
        EasyMock.expect(parametrosRemote.obtenValorPorLlave(CLAVE_DIAS)).andReturn("10");
        Whitebox.setInternalState(parametrosEntity, "parametrosServiceComun", parametrosRemote);
        
        EasyMock.replay(parametrosRemote);
        
        Assert.assertEquals("Debe regresar 10 dias de vigencia", parametrosEntity.getDiasVEncimiento(), Integer.valueOf(-10));
        
        EasyMock.verify(parametrosRemote);
    }
    
    @Test
    public void testGetDiasVEncimientoNUll(){
        parametrosRemote = EasyMock.createMock(ParametrosServiceRemote.class);
        EasyMock.expect(parametrosRemote.obtenValorPorLlave(CLAVE_DIAS)).andReturn(null);
        Whitebox.setInternalState(parametrosEntity, "parametrosServiceComun", parametrosRemote);
        
        EasyMock.replay(parametrosRemote);
        
        Assert.assertEquals("Debe regresar 10 dias de vigencia", parametrosEntity.getDiasVEncimiento(), Integer.valueOf(-5));
        
        EasyMock.verify(parametrosRemote);
    }
    
    @Test
    public void testGetDiasVEncimientoDatoBasura(){
        parametrosRemote = EasyMock.createMock(ParametrosServiceRemote.class);
        EasyMock.expect(parametrosRemote.obtenValorPorLlave(CLAVE_DIAS)).andReturn("er");
        Whitebox.setInternalState(parametrosEntity, "parametrosServiceComun", parametrosRemote);
        
        EasyMock.replay(parametrosRemote);
        
        Assert.assertEquals("Debe regresar 10 dias de vigencia", parametrosEntity.getDiasVEncimiento(), Integer.valueOf(-5));
        
        EasyMock.verify(parametrosRemote);
    }
}
