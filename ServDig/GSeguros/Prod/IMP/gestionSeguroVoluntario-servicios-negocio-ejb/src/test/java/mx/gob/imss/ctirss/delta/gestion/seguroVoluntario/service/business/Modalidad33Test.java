/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.business;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import junit.framework.Assert;
import mx.gob.imss.ctirss.delta.exception.domicilio.UmfNoLocalizadaException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaConVariosNSSException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaSinNSSException;
import mx.gob.imss.ctirss.delta.exception.individuo.AsignacionNssPersonaException;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ValidaNssBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CuotaServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ComprobanteSeguroRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ConsultaSeguroIvroServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.DatosCotizacionSeguroRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.NotificacionSegurosRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.SolicitudContinuacionVoluntariaRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ValidaIntegranteSeguroFamiliarRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ValidaVigenciaRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.test.Ambiente;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.test.EjbLocator;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Parentesco;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.enums.TipoOperacionNotificacionIVROEnum;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;
import mx.gob.imss.digital.modelo.cobranza.CalculoCuota;
import mx.gob.imss.digital.modelo.cobranza.Cotizacion;
import mx.gob.imss.digital.modelo.cobranza.DatosCalculoCuota;
import mx.gob.imss.digital.modelo.cobranza.EmpleadoCuota;
import mx.gob.imss.digital.modelo.cobranza.MovimientoEmpleado;
import mx.gob.imss.digital.modelo.cobranza.PeriodoCuota;
import mx.gob.imss.digital.modelo.cobranza.RamaCalculo;
import mx.gob.imss.digital.modelo.persona.Persona;
import mx.gob.imss.digital.modelo.seguros.DocumentoSeguro;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;
import mx.gob.imss.digital.modelo.seguros.SegurosIvro;
import mx.gob.imss.digital.modelo.sindo.ModalidadTrabajador;
import mx.gob.imss.digital.modelo.sindo.RespuestaValidacionTrabajador;
import mx.gob.imss.digital.modelo.sindo.ResultadoVigenciaSeguroFamiliar;
import mx.gob.imss.digital.modelo.sindo.VigenciaSeguroFamiliar;
//import mx.gob.imss.ws.vo.Return;

//import static org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author alexis.corrales
 */
public class Modalidad33Test {

    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(ConsultaSeguroTest.class);
    }

    @Test
    public void obtenerEdadPesonaTest() {
        Long idPerson = 23456L;
        Integer edad = EjbLocator.find(PersonaBusinessRemote.class, Ambiente.STAGE).obtenerEdadPersona(idPerson);
        assertNotNull(edad);
        LOGGER.debug("IdPersona no es Nulo");
//        Assert.assertEquals(edad, edad);
//        LOGGER.debug("Se obttuvo la edad del idPersona: " + edad);

    }

    @Test
    public void existeRechazoTest() {
        Long idPerson = 4732094L;
        boolean existeRechazo = EjbLocator.find(SolicitudContinuacionVoluntariaRemote.class, Ambiente.STAGE).existeRechazo(idPerson);
        Assert.assertNotNull(existeRechazo);
        System.out.println(existeRechazo);
        LOGGER.debug("El IdPersona no es Nulo");
        Assert.assertFalse(existeRechazo);
        LOGGER.debug("Existe rechazo");
    }

    @Test
    public void obtenerCurpPersonaTest() {
        Long idPersona = 213457L;
        String obtenerCurpPersona = EjbLocator.find(PersonaBusinessRemote.class, Ambiente.STAGE).obtenerCurpPersona(idPersona);
        System.out.println("CURP: " + obtenerCurpPersona);
        assertNotNull("CURP Vacia", obtenerCurpPersona);
        LOGGER.debug("CURP no es nulo");
        assertEquals("CURP Longitud", 18, obtenerCurpPersona.length());
    }

    @Test
    public void aplicaValidacionNssAlmacenTest() {

        boolean nss = EjbLocator.find(ValidaNssBusinessRemote.class, Ambiente.STAGE).aplicaValidacionNssAlmacen("12345678912");
        Assert.assertNotNull(nss);
        LOGGER.debug("Aplica validacion NSS almacen" + nss);
        Assert.assertTrue(nss);
        LOGGER.debug("Aplica validacion NSS almacen");

    }

    @Test
    public void validaVigenciaSeguroFamiliarRenovacionTest() {

//        AsignacionNssIvro asignacionNss = new AsignacionNssIvro();
//        asignacionNss.setCveIdAsignacionNss(26966702L);
        VigenciaSeguroFamiliar vigencia = new VigenciaSeguroFamiliar();
        //Resultado res = ret.getResultado().getValue();
        vigencia.setClaveError(0);
        vigencia.setMensajeError("Consulta Exitosa");
        vigencia.setResultado(new ResultadoVigenciaSeguroFamiliar());
        vigencia.getResultado().setEstadoVigencia("0");
        vigencia.getResultado().setFecUltimaBajaMod33(null);
        vigencia.getResultado().setFecUltimaBajaObligatorio("2013-10-15");
        vigencia.getResultado().setIndPension("0");
        vigencia.getResultado().setIndTrabajadorIMSS("0");
        vigencia.getResultado().setSemanasCotizadas("0");
        ModalidadTrabajador[] listModVigentes = new ModalidadTrabajador[0];
        vigencia.getResultado().setListModVigentes(listModVigentes);
        vigencia.getResultado().setSemanasCotizadas("0");

        EjbLocator.find(ValidaVigenciaRemote.class, Ambiente.STAGE).validaVigenciaSeguroFamiliarRenovacion(vigencia);

        Assert.assertNotNull(vigencia);
        LOGGER.debug("Vigencia no es Nulo");

    }

    @Test
    public void validarExistenciaCorreoElectronicoPersonaFisicaTest() {

        boolean correoExistente = EjbLocator.find(ServiciosPersonaBusinessRemote.class, Ambiente.STAGE).validarExistenciaCorreoElectronicoPersonaFisica(4732094L);
        Assert.assertNotNull("El IdPersona", correoExistente);
        LOGGER.debug("IdPersona no es Nulo");
        Assert.assertFalse(correoExistente);
        LOGGER.debug("Correo No existente");
//        Prueba para cuando existe correo
//        Assert.assertTrue(correoExistente);
//        LOGGER.debug("Correo Existente");
    }

    @Test
    public void obtenerNssPersonaTest() {
        String nss = "";
        try {
            nss = EjbLocator.find(PersonaBusinessRemote.class, Ambiente.STAGE).obtenerNssPersona(47117045L);
            LOGGER.info("NSS persona: " + nss);
        } catch (PersonaConVariosNSSException ex) {
            LOGGER.debug("Persona con varios NSS", ex);
        } catch (PersonaSinNSSException ex) {
            LOGGER.debug("Persona sin NSS", ex);
        }

        assertNotNull(nss);
        Assert.assertEquals(11, nss.length());
    }

    @Test
    public void buscaSegurosFamiliaresTest() {
        Persona persona = new Persona();
        persona.setIdPersona(47117045L);
        SegurosIvro segurosIvro = EjbLocator.find(ConsultaSeguroIvroServiceRemote.class, Ambiente.STAGE).buscaSegurosFamiliares(persona);
        assertNotNull(segurosIvro);
        LOGGER.debug("No es nulo");
    }

    @Test
    public void obtenerIdPersonaPorNssTest() {
        String NSS = "15955500184";
        Long idPersona;
        try {
            idPersona = EjbLocator.find(ServiceBusinessRemote.class, Ambiente.STAGE).obtenerIdPersonaPorNSS(NSS);
            System.out.println("IDPERSONA: " + idPersona);
            assertNotNull(idPersona);
            LOGGER.debug("el idPersona no es nullo");

        } catch (Exception ex) {
            Assert.assertEquals(11, NSS.length());
            System.out.println("NSS: " + ex.getMessage());
            LOGGER.error("No se localizo NSS para la persona " + NSS, ex);
        }
    }

    @Test
    public void datosCotizacionSeguroFamiliarTest() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

            String strFechaInicio = "01-01-2016";
            String strFechaFin = "31-12-2016";

            Date fechaInicio = sdf.parse(strFechaInicio);
            Date fechaFin = sdf.parse(strFechaFin);

            Calendar fechaInicioCalculo = Calendar.getInstance();
            fechaInicioCalculo.setTime(fechaInicio);
            Calendar fechaFinCalculo = Calendar.getInstance();
            fechaFinCalculo.setTime(fechaFin);

            List<SeguroIvro> lstSeguros = new ArrayList<SeguroIvro>();

            // Lista de ids a buscar
            List<Long> lstIdSeguro = new ArrayList<Long>();
            lstIdSeguro.add(47150L);

            // Asignacion de id a lista
            for (Long idSeguro : lstIdSeguro) {
                SeguroIvro seguro = new SeguroIvro();
                seguro.setCveIdSeguroIvro(idSeguro);

                lstSeguros.add(seguro);
            }

            // Se realiza busqueda de seguro
            List<SeguroIvro> lstSegurosActivos = new ArrayList<SeguroIvro>();
            for (SeguroIvro seguro : lstSeguros) {
//                SeguroIvro seguroActivo = consultaSeguroIvroServiceRemote.buscaSeguro(seguro);
                SeguroIvro seguroActivo = EjbLocator.find(ConsultaSeguroIvroServiceRemote.class, Ambiente.STAGE).buscaSeguro(seguro);
                lstSegurosActivos.add(seguroActivo);
            }

            int contador = 0;
            for (SeguroIvro seguro : lstSegurosActivos) {
                contador++;

                Persona persona = seguro.getTitular();

//                DatosCalculoCuota datosCalculoCuota = datosCotizacionSeguroRemote.datosCotizacionSeguroFamiliar(persona);
                DatosCalculoCuota datosCalculoCuota = EjbLocator.find(DatosCotizacionSeguroRemote.class, Ambiente.STAGE).datosCotizacionSeguroFamiliar(persona);
                datosCalculoCuota.setFechaInicioCalculo(fechaInicioCalculo);
                datosCalculoCuota.setFechaFinCalculo(fechaFinCalculo);

//                Cotizacion cotizacion = cuotaServiceRemote.generaCotizacion(datosCalculoCuota);
                Cotizacion cotizacion = EjbLocator.find(CuotaServiceRemote.class, Ambiente.STAGE).generaCotizacion(datosCalculoCuota);
                Assert.assertNotNull("el idPersona", cotizacion);
                LOGGER.debug("la persona no es Nulo");

                // JaxB
                JAXBContext jaxbContext = JAXBContext.newInstance(new Class[]{
                    Cotizacion.class, CalculoCuota.class,
                    EmpleadoCuota.class, PeriodoCuota.class,
                    Parentesco.class, RamaCalculo.class,
                    MovimientoEmpleado.class});
                final Marshaller marshaller = jaxbContext.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                final Writer writer = new StringWriter();

                marshaller.marshal(cotizacion, writer);
                String xml = writer.toString();
                System.out.println(xml);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validaDatosPersonaRenapoPersonaNssBdtuTest() {
        Boolean esValido = null;
        try {
            esValido = EjbLocator.find(ServiceBusinessRemote.class, Ambiente.STAGE).validaDatosPersonaRenapoPersonaNSSBdtu("MAPS671004MDFLML07", "96016700369");
        } catch (AsignacionNssPersonaException ex) {
            LOGGER.error("Error en validaDatosPersonaRenapoPersonaNSSBdtu:", ex);
        }
        assertNotNull(esValido);
        Assert.assertTrue(esValido);
    }

    @Test
    public void validaTrabajadorSeguroAnteriorTest() {
        Long idEmpleador = 47117045L;
        String nss = "72886324424";
        RespuestaValidacionTrabajador trabajadorSeguroAnterior = EjbLocator.find(ValidaIntegranteSeguroFamiliarRemote.class, Ambiente.STAGE).validaTrabajadorSeguroAnterior(idEmpleador, nss);
        System.out.println("SeguroAnterior:" + trabajadorSeguroAnterior.getMensajeValidacion());
        Assert.assertNotNull("idEmpleador vacio y nss" + trabajadorSeguroAnterior);
        //LOGGER.error("Validando informacion que informacion trabajadorSeguroAnterior sea correcta:"+ trabajadorSeguroAnterior.getValido()+ trabajadorSeguroAnterior.getAplicaCuestionario());
    }

    @Test
    public void estadoVigenciaTest() {

        RespuestaValidacionTrabajador trabajador = new RespuestaValidacionTrabajador();
        VigenciaSeguroFamiliar vigente = new VigenciaSeguroFamiliar();
        vigente.setClaveError(0);
        vigente.setMensajeError("Consulta Exitosa");
        vigente.setResultado(new ResultadoVigenciaSeguroFamiliar());
        vigente.getResultado().setEstadoVigencia("0");
        vigente.getResultado().setFecUltimaBajaMod33(null);
        vigente.getResultado().setFecUltimaBajaObligatorio("2013-10-15");
        vigente.getResultado().setIndPension("0");
        vigente.getResultado().setIndTrabajadorIMSS("0");
        vigente.getResultado().setSemanasCotizadas("0");
        ModalidadTrabajador[] listModVigentes = new ModalidadTrabajador[14];
        ModalidadTrabajador modalidad10 = crearModalidad("10", "12-09-2017");
        ModalidadTrabajador modalidad13 = crearModalidad("13", "12-09-2017");
        ModalidadTrabajador modalidad14 = crearModalidad("14", "12-09-2017");
        ModalidadTrabajador modalidad17 = crearModalidad("17", "12-09-2017");
        ModalidadTrabajador modalidad30 = crearModalidad("30", "12-09-2017");
        ModalidadTrabajador modalidad31 = crearModalidad("31", "12-09-2017");
        ModalidadTrabajador modalidad32 = crearModalidad("32", "12-09-2017");
        ModalidadTrabajador modalidad34 = crearModalidad("34", "12-09-2017");
        ModalidadTrabajador modalidad35 = crearModalidad("35", "12-09-2017");
        ModalidadTrabajador modalidad36 = crearModalidad("36", "12-09-2017");
        ModalidadTrabajador modalidad38 = crearModalidad("38", "12-09-2017");
        ModalidadTrabajador modalidad42 = crearModalidad("42", "12-09-2017");
        ModalidadTrabajador modalidad43 = crearModalidad("43", "12-09-2017");
        ModalidadTrabajador modalidad44 = crearModalidad("44", "12-09-2017");

        listModVigentes[0] = modalidad10;
        listModVigentes[1] = modalidad13;
        listModVigentes[2] = modalidad14;
        listModVigentes[3] = modalidad17;
        listModVigentes[4] = modalidad30;
        listModVigentes[5] = modalidad31;
        listModVigentes[6] = modalidad32;
        listModVigentes[7] = modalidad34;
        listModVigentes[8] = modalidad35;
        listModVigentes[9] = modalidad36;
        listModVigentes[10] = modalidad38;
        listModVigentes[11] = modalidad42;
        listModVigentes[12] = modalidad43;
        listModVigentes[13] = modalidad44;

        vigente.getResultado().setListModVigentes(listModVigentes);
        vigente.getResultado().setSemanasCotizadas("0");

        trabajador = EjbLocator.find(ValidaVigenciaRemote.class, Ambiente.STAGE).validaVigenciaSeguroFamiliarRenovacion(vigente);

        Assert.assertNotNull(vigente);
        LOGGER.debug("Vigente no es Nulo");

    }

    private ModalidadTrabajador crearModalidad(String i, String fecha) {
        ModalidadTrabajador modalidad = new ModalidadTrabajador();
        modalidad.setModalidad(i);
        modalidad.setFecha(fecha);
        return modalidad;
    }

    @Test
    public void validaVigenciaModalidadesTest() {
        VigenciaSeguroFamiliar vigenciaSeguroFamiliar = new VigenciaSeguroFamiliar();
        ModalidadTrabajador modTrabajador10 = new ModalidadTrabajador();
//    	ModalidadTrabajador modTrabajador14 = new ModalidadTrabajador();
        ModalidadTrabajador[] listModVigentes = new ModalidadTrabajador[1];
        //Llenado de la lista
        modTrabajador10.setModalidad("10");
        modTrabajador10.setFecha("2017-01-01");
//    	modTrabajador14.setModalidad("14");
//    	modTrabajador14.setFecha("2017-01-01");
        listModVigentes[0] = modTrabajador10;
//    	listModVigentes[1] = modTrabajador14;
        LOGGER.debug("Respuesta lista: " + listModVigentes[0]);
        vigenciaSeguroFamiliar.setClaveError(0);
        vigenciaSeguroFamiliar.setMensajeError("Consulta Exitosa");
        vigenciaSeguroFamiliar.setResultado(new ResultadoVigenciaSeguroFamiliar());
        vigenciaSeguroFamiliar.getResultado().setEstadoVigencia("1");
        vigenciaSeguroFamiliar.getResultado().setFecUltimaBajaMod33("true");
        vigenciaSeguroFamiliar.getResultado().setFecUltimaBajaObligatorio("2017-01-01");
        vigenciaSeguroFamiliar.getResultado().setIndPension("0");
        vigenciaSeguroFamiliar.getResultado().setIndTrabajadorIMSS("0");
        vigenciaSeguroFamiliar.getResultado().setListModVigentes(listModVigentes);
        vigenciaSeguroFamiliar.getResultado().setSemanasCotizadas("16");

//        RespuestaValidacionTrabajador validaModalidad = validaVigenciaRemote.validaVigenciaSeguroFamiliar(vigenciaSeguroFamiliar);
        RespuestaValidacionTrabajador validaModalidad = EjbLocator.find(ValidaVigenciaRemote.class, Ambiente.STAGE).validaVigenciaSeguroFamiliar(vigenciaSeguroFamiliar);
        assertNotNull(validaModalidad);
        LOGGER.debug("Respuesta: " + validaModalidad.getMensajeValidacion());
        Assert.assertFalse(validaModalidad.getValido());
        LOGGER.debug("Respuesta: " + validaModalidad.getValido());
    }

    private Map<Long, List<String>> lineasCaptura() {
        Map<Long, List<String>> relacionPagos = new HashMap<Long, List<String>>();
        List<String> lineacaptura1 = new ArrayList<String>();
        List<String> lineacaptura2 = new ArrayList<String>();
        List<String> lineacaptura3 = new ArrayList<String>();
        List<String> lineacaptura4 = new ArrayList<String>();
        List<String> lineacaptura5 = new ArrayList<String>();
        List<String> lineacaptura6 = new ArrayList<String>();
        List<String> lineacaptura7 = new ArrayList<String>();
        List<String> lineacaptura8 = new ArrayList<String>();
        List<String> lineacaptura9 = new ArrayList<String>();
        List<String> lineacaptura10 = new ArrayList<String>();
        List<String> lineacaptura11 = new ArrayList<String>();
        List<String> lineacaptura12 = new ArrayList<String>();
        List<String> lineacaptura13 = new ArrayList<String>();
        List<String> lineacaptura14 = new ArrayList<String>();
        List<String> lineacaptura15 = new ArrayList<String>();
        List<String> lineacaptura16 = new ArrayList<String>();
        List<String> lineacaptura17 = new ArrayList<String>();
        List<String> lineacaptura18 = new ArrayList<String>();
        List<String> lineacaptura19 = new ArrayList<String>();
        List<String> lineacaptura20 = new ArrayList<String>();
        List<String> lineacaptura21 = new ArrayList<String>();
        List<String> lineacaptura22 = new ArrayList<String>();
        List<String> lineacaptura23 = new ArrayList<String>();
        List<String> lineacaptura24 = new ArrayList<String>();

        lineacaptura1.add("Y2PKPGMH4BPQ29GB33130000A6IG0000000000000000000004GBS");
        lineacaptura1.add("Y2PKPGMH4BPQ2B63531300005SC0000000000000000000000ACF4");
        lineacaptura1.add("Y2PKPGMH4BPQ2E3TM3130000A6IG0000000000000000000005ZE1");
        lineacaptura1.add("Y2PKPGMH4BPQ22YFZ3130000FN9S000000000000000000000BW5U");
        lineacaptura2.add("Y2SVS1R24BPQ2HU823130000A6IG0000000000000000000005MG7");
        lineacaptura3.add("Y2SVS1R24BPQ22VAA3130000ATNS0000000000000000000007ZF8");
        lineacaptura4.add("Y2SVS1R24BPQ26SA431300006UWW0000000000000000000009TM3");
        lineacaptura5.add("Y2SVS1R24BPQ22NKU3130000ATNS0000000000000000000005M0S");
        lineacaptura6.add("Y2SVS1R24BPQ225Z131300006UWW000000000000000000000630L");
        lineacaptura7.add("Y2SVS1R24BPQ29F9131300006UWW0000000000000000000000RBF");
        lineacaptura8.add("Y2SVS1R24BPQ2L8XE3130000A6IG000000000000000000000630L");
        lineacaptura9.add("Y2SVS1R24BPQ28YTC3130000ATNS00000000000000000000055GE");
        lineacaptura10.add("Y2SVS1R24BPQ22IBR3130000ATNS0000000000000000000005C94");
        lineacaptura11.add("Y2PKPGMH4BPQ24THD3130000ATNS0000000000000000000003YNI");
        lineacaptura12.add("Y2M9MRMZ4BPQ290WH3130000GEA00000000000000000000004EZT");
        lineacaptura13.add("Y2PKPGMH4BPQ2HWI93130000FN9S000000000000000000000N5ZR");
        lineacaptura14.add("Y2PKPGMH4BPQ2330E3130000FN9S000000000000000000000JE1B");
        lineacaptura15.add("R0N5IQJF4BPQ2GDFG3130000ATNS000000000000000000000ANZQ");
        lineacaptura16.add("Y2ZHXJL74BPQ2DD4L31300007ACG000000000000000000000GJ9L");
        lineacaptura17.add("Y2PKPGMH4BPQ27K2T3130000ATNS0000000000000000000000E69");
        lineacaptura18.add("Y2PKPGMH4BPQ2AFU43130000FN9S000000000000000000000BREJ");
        lineacaptura19.add("Y2PKPGMH4BPQ22RC83130000ATNS000000000000000000000BMN8");
        lineacaptura19.add("Y2PKPGMH4BPQ232OH3130000A6IG00000000000000000000086XA");
        lineacaptura20.add("Y2PKPGMH4BPQ24S1B3130000FN9S000000000000000000000IOVU");
        lineacaptura21.add("Y2PKPGMH4BPQ24T343130000ATNS00000000000000000000061FG");
        lineacaptura21.add("Y2PKPGMH4BPQ2FBXW3130000ATNS0000000000000000000004ZD0");
        lineacaptura21.add("Y2PKPGMH4BPQ22R5L3130000FN9S0000000000000000000008SOE");
        lineacaptura21.add("Y2PKPGMH4BPQ24TET3130000FN9S000000000000000000000M8OM");
        lineacaptura22.add("Y2PKPGMH4BPQ2B6UR3130000ATNS000000000000000000000AL99");
        lineacaptura22.add("Y2PKPGMH4BPQ23LJL31300006UWW0000000000000000000008EEH");
        lineacaptura22.add("Y2PKPGMH4BPQ24YPE31300005SC0000000000000000000000AH6F");
        lineacaptura22.add("Y2PKPGMH4BPQ2GEF831300006UWW0000000000000000000001XWZ");
        lineacaptura23.add("Y2PKPGMH4BPQ231AK3130000ATNS0000000000000000000001BHE");
        lineacaptura23.add("Y2PKPGMH4BPQ28LX83130000ATNS0000000000000000000008JU9");

        relacionPagos.put(26944180L, lineacaptura1);
        relacionPagos.put(27345093L, lineacaptura2);
        relacionPagos.put(34694230L, lineacaptura3);
        relacionPagos.put(38120853L, lineacaptura4);
        relacionPagos.put(38673394L, lineacaptura5);
        relacionPagos.put(40143138L, lineacaptura6);
        relacionPagos.put(41017953L, lineacaptura7);
        relacionPagos.put(48395771L, lineacaptura8);
        relacionPagos.put(56201491L, lineacaptura9);
        relacionPagos.put(57606346L, lineacaptura10);
        relacionPagos.put(49815107L, lineacaptura11);
        relacionPagos.put(26770604L, lineacaptura12);
        relacionPagos.put(49796998L, lineacaptura13);
        relacionPagos.put(46655725L, lineacaptura14);
        relacionPagos.put(49787363L, lineacaptura15);
        relacionPagos.put(50301248L, lineacaptura16);
        relacionPagos.put(49829650L, lineacaptura17);
        relacionPagos.put(1125740L, lineacaptura18);
        relacionPagos.put(49705171L, lineacaptura19);
        relacionPagos.put(49816012L, lineacaptura20);
        relacionPagos.put(49851135L, lineacaptura21);
        relacionPagos.put(146552645L, lineacaptura22);
        relacionPagos.put(6344137L, lineacaptura23);

        return relacionPagos;
    }

    @Test
    public void enviaCorreoFinalizacionTest() {

        Map<Long, String> personas = new HashMap<Long, String>();
        Map<Long, List<String>> relacionPagos = lineasCaptura();
        personas.put(26944180l, "gohc@mail.com");
        personas.put(49705171l, "ieme@mail.com");
        personas.put(49851135l, "pipf@mail.com");
        personas.put(146552645l, "pesy1@mail.com");
        personas.put(6344137l, "gofn1@mail.com");
        int tipo = TipoOperacionNotificacionIVROEnum.FIN_TRAMITE.getCodigo();
        for (Map.Entry<Long, String> entry : personas.entrySet()) {
            List<String> correos = new ArrayList<String>();
            Long id = entry.getKey();
            String correo = entry.getValue();
            correos.add(correo);
            LOGGER.info("Se envia correo para " + correo + " con idPersona " + id);
            SeguroIvro seguroTitular = null;
            Persona persona = new Persona();
            persona.setIdPersona(id);
            SegurosIvro segurosIvro = EjbLocator.find(ConsultaSeguroIvroServiceRemote.class, Ambiente.STAGE).buscaSegurosFamiliares(persona);
            assertNotNull(segurosIvro);
            SeguroIvro[] lstSeguroIvro = segurosIvro.getSeguroIvro();
            Map<String, byte[]> adjuntos = new HashMap<String, byte[]>();
            int numDocumento = 1;
            LOGGER.info("Se encontraron " + lstSeguroIvro.length + " seguros para idPersona " + id);
            for (SeguroIvro seguro : lstSeguroIvro) {
                LOGGER.info("Se genera comprobante del seguro " + seguro.getCveIdSeguroIvro() + " seguros para idPersona " + id);
                SeguroIvro seg = new SeguroIvro();
                seg.setCveIdSeguroIvro(seguro.getCveIdSeguroIvro());
                SeguroIvro[] segsIvro = new SeguroIvro[]{seg};
                SegurosIvro seguros = new SegurosIvro();
                seguros.setSeguroIvro(segsIvro);
                DocumentoSeguro documento = EjbLocator.find(ComprobanteSeguroRemote.class, Ambiente.STAGE).generaComprobantes(seguros);
                if (seguro.getTitular().getCurp().equals(seguro.getTramite().getBeneficiarios()[0].getCurp())) {
                    seguroTitular = seguro;
                    adjuntos.put(documento.getNombreArchivo(), documento.getArchivo());
                } else {
                    adjuntos.put(documento.getNombreArchivo().replace(".pdf", numDocumento + ".pdf"), documento.getArchivo());
                    numDocumento++;
                }
            }
            LOGGER.info("Se obtuvieon " + numDocumento + 1 + " comprobantes para idPersona " + id);
            if (seguroTitular != null) {
                List<String> lineas = relacionPagos.get(id);
                for (String linea : lineas) {
                    try {
                        File file = new File("/home/gibrann/lineas/" + linea + ".pdf");
                        byte[] bytesArray = new byte[(int) file.length()];
                        FileInputStream fis = new FileInputStream(file);
                        fis.read(bytesArray);
                        fis.close();
                        adjuntos.put(linea + ".pdf", bytesArray);
                        LOGGER.info("Se adjunta linea " + linea + " comprobantes para idPersona " + id);
                    } catch (IOException ex) {
                        LOGGER.error("error de linea de captura con error " + linea, ex);
                    }
                }
                EjbLocator.find(NotificacionSegurosRemote.class, Ambiente.STAGE).enviaCorreo(seguroTitular, correos, tipo, adjuntos);
            }
        }
    }
    
    @Test
    public void enviaCorreoFinalizacionCvroTest() {

        Map<Long, String> seguros = new HashMap<Long, String>();
        Map<Long, List<String>> relacionPagos = lineasCaptura();
        seguros.put(26944180l, "gohc@mail.com");
        seguros.put(49705171l, "ieme@mail.com");
        seguros.put(49851135l, "pipf@mail.com");
        seguros.put(146552645l, "pesy1@mail.com");
        seguros.put(6344137l, "gofn1@mail.com");
        int tipo = TipoOperacionNotificacionIVROEnum.FIN_TRAMITE.getCodigo();
        for (Map.Entry<Long, String> entry : seguros.entrySet()) {
            List<String> correos = new ArrayList<String>();
            Long id = entry.getKey();
            String correo = entry.getValue();
            correos.add(correo);
            LOGGER.info("Se envia correo para " + correo + " con idPersona " + id);
            SeguroIvro seguroTitular = new SeguroIvro();
            seguroTitular.setCveIdSeguroIvro(id);
            SeguroIvro seguroIvro = EjbLocator.find(ConsultaSeguroIvroServiceRemote.class, Ambiente.STAGE).buscaSeguro(seguroTitular);
            assertNotNull(seguroIvro);
            Map<String, byte[]> adjuntos = new HashMap<String, byte[]>();
            int numDocumento = 1;
//            LOGGER.info("Se encontraron " + lstSeguroIvro.length + " seguros para idPersona " + id);

                LOGGER.info("Se genera comprobante del seguro " + seguroIvro.getCveIdSeguroIvro() + " seguros para idPersona " + id);
                SeguroIvro seg = new SeguroIvro();
                seg.setCveIdSeguroIvro(seguroIvro.getCveIdSeguroIvro());
                SeguroIvro[] segsIvro = new SeguroIvro[]{seg};
                SegurosIvro segurosIvro = new SegurosIvro();
                segurosIvro.setSeguroIvro(segsIvro);
                DocumentoSeguro documento = EjbLocator.find(ComprobanteSeguroRemote.class, Ambiente.STAGE).generaComprobantes(segurosIvro);
                
                    adjuntos.put(documento.getNombreArchivo().replace(".pdf", numDocumento + ".pdf"), documento.getArchivo());
                    numDocumento++;

            LOGGER.info("Se obtuvieon " + numDocumento + 1 + " comprobantes para idPersona " + id);
            if (seguroTitular != null) {
                List<String> lineas = relacionPagos.get(id);
                for (String linea : lineas) {
                    try {
                        File file = new File("/home/gibrann/lineas/" + linea + ".pdf");
                        byte[] bytesArray = new byte[(int) file.length()];
                        FileInputStream fis = new FileInputStream(file);
                        fis.read(bytesArray);
                        fis.close();
                        adjuntos.put(linea + ".pdf", bytesArray);
                        LOGGER.info("Se adjunta linea " + linea + " comprobantes para idPersona " + id);
                    } catch (IOException ex) {
                        LOGGER.error("error de linea de captura con error " + linea, ex);
                    }
                }
                EjbLocator.find(NotificacionSegurosRemote.class, Ambiente.STAGE).enviaCorreo(seguroTitular, correos, tipo, adjuntos);
            }
        }
    }

    @Test
    public void enviaCorreoTest() {
        List<Long> idSeguros = new ArrayList<Long>();

        idSeguros.add(105020l);
//        idSeguros.add(105024l);
//        idSeguros.add(105030l);
//        idSeguros.add(105031l);
//        idSeguros.add(105035l);
//        idSeguros.add(105040l);
//        idSeguros.add(105042l);
//        idSeguros.add(105078l);
//        idSeguros.add(105081l);
//        idSeguros.add(105095l);
//        idSeguros.add(105097l);
//        idSeguros.add(105100l);
//        idSeguros.add(105361l);
//        idSeguros.add(105366l);
//        idSeguros.add(105367l);
//        idSeguros.add(105368l);
//        idSeguros.add(105369l);
//        idSeguros.add(105362l);
//        idSeguros.add(105032l);
//        idSeguros.add(105029l);
//        idSeguros.add(104783l);
//        idSeguros.add(105382l);
//        idSeguros.add(105383l);
//        idSeguros.add(105384l);
//        idSeguros.add(105385l);
//        idSeguros.add(105386l);
//        idSeguros.add(105387l);
//        idSeguros.add(105388l);
//        idSeguros.add(105395l);
//        idSeguros.add(105478l);
//        idSeguros.add(105480l);
//        idSeguros.add(105491l);
//        idSeguros.add(105492l);
//        idSeguros.add(105498l);
//        idSeguros.add(105501l);
//        idSeguros.add(105502l);
//        idSeguros.add(105503l);
//        idSeguros.add(105504l);
//        idSeguros.add(105506l);
//        idSeguros.add(105507l);
//        idSeguros.add(105508l);
//        idSeguros.add(105509l);
//        idSeguros.add(105510l);
//        idSeguros.add(105511l);
        int tipo = 10;
        for (Long lista : idSeguros) {
            SeguroIvro seguroIvro = new SeguroIvro();
            seguroIvro.setCveIdSeguroIvro(lista);
            EjbLocator.find(NotificacionSegurosRemote.class, Ambiente.LOCAL).enviaCorreo(seguroIvro, tipo);
        }

    }

    @Test
    public void getUMFByCodigoPostalTest() {
        List<UnidadMedicaFamiliar> umfByCodigoPostal = null;
        try {
            umfByCodigoPostal = EjbLocator.find(DomicilioServiceBusinessRemote.class, Ambiente.STAGE).getUmfByCodigoPostal("06600");
        } catch (UmfNoLocalizadaException ex) {
            java.util.logging.Logger.getLogger(Modalidad33Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertNotNull(umfByCodigoPostal);
    }

    @Test
    public void generaComprobanteSeguro() {
        SeguroIvro seg = new SeguroIvro();
        seg.setCveIdSeguroIvro(105511L);
        SeguroIvro[] segurosIvro = new SeguroIvro[]{seg};
        SegurosIvro seguros = new SegurosIvro();
        seguros.setSeguroIvro(segurosIvro);
        byte[] file = EjbLocator.find(ComprobanteSeguroRemote.class, Ambiente.STAGE).generaComprobantes(seguros).getArchivo();
        FileOutputStream fos;
        try {
            fos = new FileOutputStream("/home/gibrann/testComp.pdf");
            fos.write(file);
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
