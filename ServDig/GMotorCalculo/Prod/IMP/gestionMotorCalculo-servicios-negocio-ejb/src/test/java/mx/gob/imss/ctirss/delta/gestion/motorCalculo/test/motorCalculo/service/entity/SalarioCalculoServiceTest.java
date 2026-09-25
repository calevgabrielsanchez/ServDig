/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.test.motorCalculo.service.entity;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import junit.framework.Assert;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.entity.SalarioCalculoServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.utility.model.ValoresCalculoEmpleado;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.SUAConstants;
import mx.gob.imss.ctirss.delta.model.enums.AreaGeograficaEnum;
import mx.gob.imss.digital.modelo.cobranza.DatosEmpleado;

import org.apache.commons.lang.time.DateUtils;
import org.easymock.EasyMock;
import org.junit.Ignore;
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
@PrepareForTest(SalarioCalculoServiceEntity.class)
public class SalarioCalculoServiceTest {
    
    /**
     * SAlario minimo de pruebas
     */
    private static final BigDecimal SALARIO_MIN = new BigDecimal("65.13");
    /**
     * Salario asociado al empleado
     */
    private static final BigDecimal SALARIO_EMPLEADO = new BigDecimal("250");
    /**
     * FEcha para el calculo de salarios en las pruebas
     */
    private static final Calendar fechaCalculo = Calendar.getInstance();
    /**
     * Zona salarial para las pruebas
     */
    private static final String zonaSalarial = "A";
    /**
     * Enum de la zona salarial A
     */
    private static final AreaGeograficaEnum AREA_G = AreaGeograficaEnum.geFromClave(zonaSalarial);
    
    /**
     * Servicio de consulta salarios
     */
    SalarioCalculoServiceEntity salarioService = new SalarioCalculoServiceEntity();
    /**
     * entitiy manager mock
     */
    EntityManager entityManager;
    /**
     * TypedQuery Mock
     */
    TypedQuery<BigDecimal> typedQuery;
    
    /**
     * PRueba para obtener los salarios de las modalidades ivro que se calculalan con un salario minimo
     * @throws Exception
     */
    @Test
    @Ignore
    public void testAgregaSalariosIvro() throws Exception {
        
        long idModalidad = 22;
        ValoresCalculoEmpleado valores = getValoresCalculo(idModalidad);
                
        iniciaMock(valores, null);
        
        ValoresCalculoEmpleado valoresSalario = salarioService.agregaSalariosCalculo(valores);
        Assert.assertEquals("El salario de calculo debe ser el minimo", SALARIO_MIN, valoresSalario.getSalarioCalculo());
        Assert.assertEquals("El salario cuota fija debe ser el minimo", SALARIO_MIN, valoresSalario.getSalarioCuotaFija());
        Assert.assertEquals("El salario excedente debe ser cero", BigDecimal.ZERO, valoresSalario.getSalarioExedente());        
        
        terminaMock();
    }   
    
    /**
     * Prueba de la obtencion de salarios con modalidades que obtiene su valor salarial
     * @throws Exception
     */
    @Test
    @Ignore
    public void testAgregaSalariosDomestico() throws Exception {      
        // id mod 16  -> mod 34 domesticos
        long idModalidad = 16;
        ValoresCalculoEmpleado valores = getValoresCalculo(idModalidad);
        
        iniciaMock(valores, null);
        
        ValoresCalculoEmpleado valoresSalario = salarioService.agregaSalariosCalculo(valores);
        Assert.assertEquals("El salario de calculo debe ser el del empleado", SALARIO_EMPLEADO, valoresSalario.getSalarioCalculo());
        Assert.assertEquals("El salario cuota fija debe ser el minimo", SALARIO_MIN, valoresSalario.getSalarioCuotaFija());
        // El excedente para es el salario real menos 3 minimos
        BigDecimal salarioExcedente = SALARIO_EMPLEADO.subtract((SALARIO_MIN.multiply(new BigDecimal(3))));
        Assert.assertEquals("El salario excedente debe ser el del empleado", salarioExcedente, valoresSalario.getSalarioExedente());
        
        terminaMock();        
    }
    
    @Test
    @Ignore
    public void testAgregaSalariosIvro_NoSalario() throws Exception {
        
        long idModalidad = 22;
        ValoresCalculoEmpleado valores = getValoresCalculo(idModalidad);
                
        iniciaMock(valores, new NoResultException());
        try {
            salarioService.agregaSalariosCalculo(valores);
        } catch (SUAException e) {
            Assert.assertEquals("Debe generar error no salario encontrad ", 
                    e.getFaultcode(), SUAConstants.COD_NO_SALARIO);
        }
        terminaMock();
    }
    
    @Test
    @Ignore
    public void testAgregaSalariosIvro_MultipleSAlario() throws Exception {
        
        long idModalidad = 22;
        ValoresCalculoEmpleado valores = getValoresCalculo(idModalidad);
                
        iniciaMock(valores, new NonUniqueResultException());
        
        try {
            salarioService.agregaSalariosCalculo(valores);
        } catch (SUAException e) {
            Assert.assertEquals("Debe generar error Varios salarios encontrados ", 
                    e.getFaultcode(), SUAConstants.COD_MULTIPLE_SALARIO);
        }        
        
        terminaMock();
    }
    
    /**
     * Inicia los mocks para realizar las pruebas
     * @param valores
     */
    @SuppressWarnings("unchecked")
    private void iniciaMock (ValoresCalculoEmpleado valores, Exception ex) {
        entityManager = EasyMock.createMock(EntityManager.class);
        Date fechaConsulta = DateUtils.truncate(valores.getFechaInicioCalculo().getTime(), Calendar.DATE);        
        typedQuery = EasyMock.createMock(TypedQuery.class);                
        
        EasyMock.expect(typedQuery.setParameter("idArea", (long) AREA_G.getId())
                ).andReturn(typedQuery);
        EasyMock.expect(typedQuery.setParameter("fecha", fechaConsulta, 
                TemporalType.DATE)).andReturn(typedQuery);        
        
        if(ex != null){
            EasyMock.expect(typedQuery.getSingleResult()).andThrow(ex);
        } else {
            EasyMock.expect(typedQuery.getSingleResult()).andReturn(SALARIO_MIN);
        }        
                
        EasyMock.expect(entityManager.createQuery(getQuerySalario(), BigDecimal.class)).andReturn(typedQuery);
        
        Whitebox.setInternalState(salarioService, "entityManager", entityManager);
        
        EasyMock.replay(typedQuery, entityManager);
    }
    /**
     * Termina los mocks
     */
    private void terminaMock() {
        EasyMock.verify(typedQuery, entityManager);
    }
    
    /**
     * Genera los valores para las pruebas
     * @param idModalidad
     * @return
     */
    private ValoresCalculoEmpleado getValoresCalculo(long idModalidad) {
        ValoresCalculoEmpleado valores = new ValoresCalculoEmpleado();
        valores.setFechaInicioCalculo(fechaCalculo);
        valores.setModalidad(idModalidad);
        valores.setZonaSalarial(zonaSalarial);
        valores.setEmpleado(new DatosEmpleado());
        valores.getEmpleado().setSalario(SALARIO_EMPLEADO);
        return valores;
    }
    /**
     * Obtiene el query que se ejecuta en el servicio
     * @return
     */
    private String getQuerySalario() {
        return new StringBuilder("select salario.salarioMinimo ")
        .append("From DitSalarioGeneral salario ")
        .append("join salario.dicAreaGeografica  areaGeografica ")
        .append("where areaGeografica.cveIdAreaGeografica = :idArea ")
        .append("and salario.fecInicioVigencia <= :fecha ")
        .append("and salario.fecFinVigencia >= :fecha ").toString();        
    }
    
    
}
