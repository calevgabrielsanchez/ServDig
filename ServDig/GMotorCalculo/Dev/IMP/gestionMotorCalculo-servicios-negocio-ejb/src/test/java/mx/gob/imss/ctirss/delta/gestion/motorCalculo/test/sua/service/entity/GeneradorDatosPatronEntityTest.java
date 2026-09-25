/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.test.sua.service.entity;

import java.math.BigDecimal;
import java.util.Calendar;

import junit.framework.Assert;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.entity.GeneradorDatosPatronEntity;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.test.sua.util.JaxbUtilT;
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
import mx.gob.imss.digital.modelo.cobranza.Patron;
import mx.gob.imss.digital.modelo.cobranza.PeriodoCuota;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Prueba Unitaria para la generacion de los datos de un patron para el archivo sua
 * @author NOVUTECK1
 *
 */
public class GeneradorDatosPatronEntityTest {

    /**
     * Xml para obtener el objeto calculo cuota
     */
    private static final String XML = "src/test/resources/CalculoCuotaTest.xml";
    /**
     * RFC de la persona fisica
     */
    private static final String RFC_FISICA = "FISICA4567";
    /**
     * rfc de la persona moral
     */
    private static final String RFC_MORAL = "MoralA4567";
    /**
     * Servicio a probar
     */
    GeneradorDatosPatronEntity generadorDatos = new GeneradorDatosPatronEntity();
    
    @Test
    @Ignore
    public void testGeneraPatron_Fisica() throws Exception {
        CalculoCuota calculo = JaxbUtilT.unmarshaller(XML, CalculoCuota.class);
        PeriodoCuota periodo = calculo.getEmpleados()[0].getPeriodos()[0];
        //Patron patron = generadorDatos.generaPatron(calculo, obtenSujetoObligado(true), periodo);
//        Assert.assertEquals("Debe coincidir el area de los salarios", 
//                patron.getAreaGeograficaSalariosMinimos(), calculo.getZonaSalarial());
//        Assert.assertEquals("Debe coincidir el Rfc de fisica", RFC_FISICA, patron.getRfcPatron());
//        Calendar periodoPago = Calendar.getInstance();
//        periodoPago.setTime(patron.getPeriodoDePago());
//        Assert.assertEquals("El primer periodo es diciembre", Calendar.DECEMBER, 
//                periodoPago.get(Calendar.MONTH));
    }
    
    @Test
    @Ignore
    public void testGeneraPatron_Moral() throws Exception {
        CalculoCuota calculo = JaxbUtilT.unmarshaller(XML, CalculoCuota.class);
        PeriodoCuota periodo = calculo.getEmpleados()[0].getPeriodos()[6];
//        Patron patron = generadorDatos.generaPatron(calculo, obtenSujetoObligado(false), periodo);
//        Assert.assertEquals("Debe coincidir el area de los salarios", 
//                patron.getAreaGeograficaSalariosMinimos(), calculo.getZonaSalarial());
//        Assert.assertEquals("Debe coincidir el Rfc de Moral", RFC_MORAL, patron.getRfcPatron());
//        Calendar periodoPago = Calendar.getInstance();
//        periodoPago.setTime(patron.getPeriodoDePago());
//        Assert.assertEquals("El ultimo periodo de pago es diciembre aunque se acabe en noviembre", 
//                Calendar.DECEMBER, periodoPago.get(Calendar.MONTH));
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
            fisica.setRfc(RFC_FISICA);
            fisica.setNombre("PEpe");
            fisica.setPrimerApellido("Pecas");
            fisica.setSegundoApellido("PEcas");
            sujeto.setFisica(fisica);
        }else {
            Moral moral = new Moral();
            moral.setRfc(RFC_MORAL);
            moral.setRazonSocial("RAzon social ");
            sujeto.setMoral(moral);
        }
        return sujeto;
    }
    
    
}
