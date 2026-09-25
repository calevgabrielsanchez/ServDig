/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.test.sua.service.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import junit.framework.Assert;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.NssRelacionadoVariasPersonasException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonasNoLocalizadasException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.business.SuaServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.entity.ParametrosEntity;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.ParametrosEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.entity.GeneradorDatosPatronEntity;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.entity.GeneradorDatosTrabajadorEntity;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.entity.GeneradorSumarioYValidacionEntity;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.interfaces.GeneradorDatosPatronLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.interfaces.GeneradorDatosTrabajadorLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.interfaces.GeneradorSumarioYValidacionLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.test.sua.util.JaxbUtilT;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.SUAConstants;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CentroTrabajo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.digital.modelo.cobranza.CalculoCuota;
import mx.gob.imss.digital.modelo.cobranza.SUAPago;

import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

/**
 * Prueba del servicio ue genera los datos del SUA
 * @author NOVUTECK1
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SuaServiceBusiness.class)
public class SuaServiceBusinessTest {

    /**
     * Xml para obtener el objeto calculo cuota
     */
    private static final String XML = "src/test/resources/CalculoCuotaTest.xml";
    
    private static final String NRP = "Y5846422102";
    
    private static final String NSS = "01004200216";
    
    SuaServiceBusiness suaService = new SuaServiceBusiness();
    
    private SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusiness;
    /**
     * Servicoi para la consulta de personas fisicas
     */
    private PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;
    /**
     * Servicio para obtener los parametros necesarios
     */
    private ParametrosEntityLocal parametrosEntity;
    /**
     * Servicio para la generacion de los datos del patron
     */
    private GeneradorDatosPatronLocal generadorDatosPatron = new GeneradorDatosPatronEntity();
    /**
     * Servicio para la generacion de los datos de un trabajador
     */
    private GeneradorDatosTrabajadorLocal generadorDatosTrabajador = new GeneradorDatosTrabajadorEntity();
    /**
     * Servicio para la generacion del sumario y registro de validacion
     */
    private GeneradorSumarioYValidacionLocal generadorSumarioYValidacion = new GeneradorSumarioYValidacionEntity();
    
    @Test
    public void testGeneraDatosSua() throws Exception {
        CalculoCuota calculo = JaxbUtilT.unmarshaller(XML, CalculoCuota.class);
        SujetoObligado sujeto = obtenSujetoObligado(true);
        iniciaMock(sujeto, null);
        SUAPago[] suas = suaService.generaDatosSua(calculo, null);
        Assert.assertNotNull("La respuesta no puede ser nula", suas);
//        Assert.assertFalse("La lista de suas no puede ser vacia",suas.isEmpty());
        Assert.assertEquals("Deben existir 7 suas", 7,suas.length);
        terminaMock();
    }
    
    @Test
    public void testGeneraDatosSua_NoTrabajador() throws Exception {
        CalculoCuota calculo = JaxbUtilT.unmarshaller(XML, CalculoCuota.class);
        SujetoObligado sujeto = obtenSujetoObligado(true);
        iniciaMock(sujeto, new PersonasNoLocalizadasException());
        try {
            suaService.generaDatosSua(calculo, null);
            Assert.fail("No debe encontrar un patron");
        } catch (SUAException e) {
            Assert.assertEquals("Debe generarse el error de patron no encontrado", 
                    e.getFaultcode(), SUAConstants.COD_NO_TRABAJADOR);
        }
        terminaMock();
    }
    
    @Test
    public void testGeneraDatosSua_MultipleTrabajador() throws Exception {
        CalculoCuota calculo = JaxbUtilT.unmarshaller(XML, CalculoCuota.class);
        SujetoObligado sujeto = obtenSujetoObligado(true);
        iniciaMock(sujeto, new NssRelacionadoVariasPersonasException());
        try {
            suaService.generaDatosSua(calculo, null);
            Assert.fail("No debe encontrar un patron");
        } catch (SUAException e) {
            Assert.assertEquals("Debe generarse el error de patron no encontrado", 
                    e.getFaultcode(), SUAConstants.COD_NO_TRABAJADOR);
        }
        terminaMock();
    }
    
    @Test
    public void testGeneraDatosSua_NoPatron() throws Exception {
        CalculoCuota calculo = JaxbUtilT.unmarshaller(XML, CalculoCuota.class);
        iniciaMock(null, null);
        try {
            suaService.generaDatosSua(calculo, null);
            Assert.fail("No debe encontrar un patron");
        } catch (SUAException e) {
            Assert.assertEquals("Debe generarse el error de patron no encontrado", 
                    e.getFaultcode(), SUAConstants.COD_NO_PATRON);
        }
        terminaMock();
    }
    
    private void iniciaMock(SujetoObligado sujeto, Exception e) throws Exception {
        Whitebox.setInternalState(suaService, "generadorDatosPatron", generadorDatosPatron);
        Whitebox.setInternalState(suaService, "generadorDatosTrabajador", generadorDatosTrabajador);
        Whitebox.setInternalState(suaService, "generadorSumarioYValidacion", generadorSumarioYValidacion);
        
        sujetoObligadoServiceBusiness = EasyMock.createMock(SujetoObligadoServiceBusinessRemote.class);
        
        EasyMock.expect(sujetoObligadoServiceBusiness.consultarPorNumeroRegistroPatronal(NRP
                )).andReturn(sujeto);
        EasyMock.expect(sujetoObligadoServiceBusiness.obtenerDetalleRP(sujeto
                )).andReturn(sujeto);        
        Whitebox.setInternalState(suaService, "sujetoObligadoServiceBusiness", sujetoObligadoServiceBusiness);
        
        personaFisicaServiceBusiness = EasyMock.createMock(PersonaFisicaServiceBusinessRemote.class);
        if(e == null){
            EasyMock.expect(personaFisicaServiceBusiness.localizarPersonaFisicaPorNss(NSS)).andReturn(getPersona());
        } else {
            EasyMock.expect(personaFisicaServiceBusiness.localizarPersonaFisicaPorNss(NSS)).andThrow(e);
        }
        
        Whitebox.setInternalState(suaService, "personaFisicaServiceBusiness", personaFisicaServiceBusiness);
        
        parametrosEntity = EasyMock.createMock(ParametrosEntity.class);
        EasyMock.expect(parametrosEntity.getDiasFeriados()).andReturn(new ArrayList<Date>());
        Whitebox.setInternalState(suaService, "parametrosEntity", parametrosEntity);
        
        EasyMock.replay(sujetoObligadoServiceBusiness, personaFisicaServiceBusiness, parametrosEntity);
    }
    
    private void terminaMock() {
        //EasyMock.verify(sujetoObligadoServiceBusiness, personaFisicaServiceBusiness, parametrosEntity);
    }
    
    private SujetoObligado obtenSujetoObligado(boolean pFisica) {
        SujetoObligado sujeto = new SujetoObligado();
        Subdelegacion subdelegacion = new Subdelegacion();
        subdelegacion.setClave("15");
        Delegacion delegacion = new Delegacion();
        delegacion.setClave("10");
        subdelegacion.setDelegacion(delegacion);
        sujeto.setSubdelegacion(subdelegacion);
        Clasificacion clasificacion = new Clasificacion();
        clasificacion.setGiro("GIRO DEL NEGOCIO");
        clasificacion.setPrimaSRTActual(BigDecimal.TEN);
        sujeto.setClasificacion(clasificacion);
        
        CentroTrabajo centroTrabajo = new CentroTrabajo();
        centroTrabajo.setCalle("Calle");
        centroTrabajo.setNumInteriorAlf("Mz11");
        centroTrabajo.setColonia("Colonia");
        Localidad localidad = new Localidad();
        localidad.setClave("www");
        Municipio municipio = new  Municipio();
        municipio.setClave("15");
        municipio.setNombre("Cuauhtemoc");
        EntidadFederativa entidadFederativa = new EntidadFederativa();
        entidadFederativa.setClave("45");
        municipio.setEntidadFederativa(entidadFederativa);
        localidad.setMunicipio(municipio);
        centroTrabajo.setLocalidad(localidad);
        sujeto.setCntroTrabajo(centroTrabajo);
        if(pFisica){
            Fisica fisica = new Fisica();
            fisica.setRfc("sdsdsd");
            fisica.setNombre("PEpe");
            fisica.setPrimerApellido("Pecas");
            fisica.setSegundoApellido("PEcas");
            sujeto.setFisica(fisica);
        }else {
            Moral moral = new Moral();
            moral.setRfc("dsdsdddd");
            moral.setRazonSocial("RAzon social ");
            sujeto.setMoral(moral);
        }
        return sujeto;
    }
    
    private Fisica getPersona() {
        Fisica persona = new Fisica();
        persona.setNombre("Pepe");
        persona.setPrimerApellido("Pecas");
        persona.setSegundoApellido("Pecas");
        persona.setNss(NSS);
        return persona;
    }
}
