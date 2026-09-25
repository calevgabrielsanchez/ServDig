/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.business;

import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import junit.framework.Assert;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaConVariosNSSException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaSinNSSException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.MunicipioImssNoLocalizadoException;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBussinessExternosRemote;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.dto.DomicilioServiceDtoRemote;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CuotaServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ConsultaSeguroIvroServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.DatosCotizacionSeguroRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.NotificacionSegurosRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.SolicitudContinuacionVoluntariaRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ValidaVigenciaRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.test.Ambiente;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.test.EjbLocator;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Parentesco;
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
import mx.gob.imss.digital.modelo.domicilio.Domicilio;
import mx.gob.imss.digital.modelo.persona.Persona;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;
import mx.gob.imss.digital.modelo.seguros.SegurosIvro;
import mx.gob.imss.digital.modelo.sindo.RespuestaValidacionTrabajador;
import mx.gob.imss.digital.modelo.sindo.ResultadoVigenciaContVoluntaria;
import mx.gob.imss.digital.modelo.sindo.VigenciaContVoluntaria;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author alexis.corrales
 */
public class Modalidad40Test {

    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(ConsultaSeguroTest.class);
    }


    @Test
    public void buscaSeguroCVROTest() {
        //assert para que array se llena 4732094 55014555
        Persona person = new Persona();
        person.setIdPersona(10086194L);
        SegurosIvro segurosCVRO = EjbLocator.find(ConsultaSeguroIvroServiceRemote.class, Ambiente.STAGE).buscaSegurosCVRO(person);
        assertNotNull("BuscaSeguro CVRO: ", segurosCVRO);
        LOGGER.debug("IdPersona no es Nulo" + segurosCVRO);
    }

    @Test
    public void validarExistenciaCorreoElectronicoPersonaFisicaTest() {
//        Long idPerson = 10086194L;
//        boolean correoExistente = EjbLocator.find(ServiciosPersonaBusinessRemote.class, Ambiente.DESARROLLO).validarExistenciaCorreoElectronicoPersonaFisica(idPerson);
//        Assert.assertNotNull("El IdPersona", correoExistente);
//        LOGGER.debug("IdPersona no es Nulo");
//        Assert.assertTrue(correoExistente);
//        LOGGER.debug("Correo  existente");
    }

    @Test
    public void existeRechazoTest() {
        Long idPerson = 10086194L;
        boolean existeRechazo = EjbLocator.find(SolicitudContinuacionVoluntariaRemote.class, Ambiente.STAGE).existeRechazo(idPerson);
        Assert.assertNotNull(existeRechazo);
        System.out.println(existeRechazo);
        LOGGER.debug("El IdPersona no es Nulo");
        Assert.assertFalse(existeRechazo);
        LOGGER.debug("Existe rechazo");
    }

    @Test
    public void obtenerNssVigentePersonaTest() {

        try {
            String nss = EjbLocator.find(PersonaBusinessRemote.class, Ambiente.STAGE).obtenerNssVigentePersona(4732094L);
            Assert.assertEquals(11, nss.length());
        } catch (PersonaConVariosNSSException ex) {
            LOGGER.debug("Ocurrio una Exception PersonaConVariosNSSException");
        } catch (PersonaSinNSSException ex) {
            LOGGER.debug("Ocurrio una Exception PersonaSinNSSException");
        }

    }

    @Test
    public void obtenerCurpPersonaTest() {
        Long idPersona = 21L;
        String obtenerCurpPersona = EjbLocator.find(PersonaBusinessRemote.class, Ambiente.STAGE).obtenerCurpPersona(idPersona);
        System.out.println("CURP: " + obtenerCurpPersona);
        assertNotNull("CURP Vacia", obtenerCurpPersona);
        assertEquals("CURP Longitud", 18, obtenerCurpPersona.length());
    }

    @Test
    public void consultarDomicilioTest() {
        Long idPersona = 21L;
        try {
            Domicilio consultarUltimoDomicilioParticular = EjbLocator.find(DomicilioServiceBussinessExternosRemote.class, 
                    Ambiente.STAGE).consultarUltimoDomicilioParticilar(idPersona);
            System.out.println(" " + consultarUltimoDomicilioParticular);
            assertNotNull("DOMICILIO Vacio", consultarUltimoDomicilioParticular);
        } catch (DomicilioNoLocalizadoException ex) {
            LOGGER.error("No se localizo domicilio para la persona " + idPersona, ex);
        } catch (MunicipioImssNoLocalizadoException ex) {
            LOGGER.error("No se localizo Municipio IMDD para la persona " + idPersona, ex);
        }

    }

    @Test
    public void getSalarioMinimoDfPorFechaTest() {
        Calendar calendar = Calendar.getInstance();
        BigDecimal salarioMinimo = EjbLocator.find(DatosCotizacionSeguroRemote.class, Ambiente.STAGE).getSalarioMinimoDfPorFecha("A",calendar).setScale(2,RoundingMode.DOWN);
        assertTrue(salarioMinimo.doubleValue() == 80.04);
    }

    @Test
    public void generaCotizacionContinuacionVoluntariaTest() {
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
            lstIdSeguro.add(101461L);

            // Asignacion de id a lista
            for (Long idSeguro : lstIdSeguro) {
                SeguroIvro seguro = new SeguroIvro();
                seguro.setCveIdSeguroIvro(idSeguro);

                lstSeguros.add(seguro);
            }

            // Se realiza busqueda de seguro
            List<SeguroIvro> lstSegurosActivos = new ArrayList<SeguroIvro>();
            for (SeguroIvro seguro : lstSeguros) {
                SeguroIvro seguroActivo = EjbLocator.find(ConsultaSeguroIvroServiceRemote.class, Ambiente.STAGE).buscaSeguro(seguro);
                assertNotNull(seguroActivo);
                lstSegurosActivos.add(seguroActivo);
            }

            int contador = 0;
            for (SeguroIvro seguro : lstSegurosActivos) {
                contador++;

                Persona persona = seguro.getTitular();

                DatosCalculoCuota datosCalculoCuota = EjbLocator.find(DatosCotizacionSeguroRemote.class, Ambiente.STAGE).datosCotizacionContinuacionVoluntaria(persona);
                datosCalculoCuota.setFechaInicioCalculo(fechaInicioCalculo);
                datosCalculoCuota.setFechaFinCalculo(fechaFinCalculo);

                Cotizacion cotizacion = EjbLocator.find(CuotaServiceRemote.class, Ambiente.STAGE).generaCotizacion(datosCalculoCuota);
                assertNotNull(cotizacion);
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

    /**
     * Prueba para Compra Esta prueba funciona para validar la vigencia de un
     * seguro CVRO La cual permite validar que el asegurado demuestra que puede
     * hacer un tramite de renovacion Esta prueba consulta un webService que se
     * llama wSConsultaMod40 enviando como unico dato el idAsignacionNSS de la
     * persona Se comprueba la validacion por medio de la fecUltimoMov
     */
    @Test
    public void validaVigenciaContinuacionVoluntariaTest() {

        VigenciaContVoluntaria vigenciaContVoluntaria = new VigenciaContVoluntaria();
        vigenciaContVoluntaria.setClaveError("0");
        vigenciaContVoluntaria.setMensajeError("Consulta Exitosa");
        vigenciaContVoluntaria.setModalidad40(new ResultadoVigenciaContVoluntaria());
        vigenciaContVoluntaria.getModalidad40().setRegPatUltimoMov("B4894962");
        vigenciaContVoluntaria.getModalidad40().setModUltimoMov("10");
        vigenciaContVoluntaria.getModalidad40().setTipoUltimoMov("02");
        vigenciaContVoluntaria.getModalidad40().setFecUltimoMov("2017-03-25");
        vigenciaContVoluntaria.getModalidad40().setEstadoVigencia(0);
        vigenciaContVoluntaria.getModalidad40().setIndPension(0);
        vigenciaContVoluntaria.getModalidad40().setIndTrabajadorIMSS(0);
        vigenciaContVoluntaria.getModalidad40().setSemanasCotizadas(261);
        vigenciaContVoluntaria.getModalidad40().setRegPatUltimoObligatorio("B4894962");
        vigenciaContVoluntaria.getModalidad40().setModUltimoObligatorio("10");
        vigenciaContVoluntaria.getModalidad40().setTipoMovObligatorio("02");
        vigenciaContVoluntaria.getModalidad40().setFecMovObligatorio("2017-03-25");
        vigenciaContVoluntaria.getModalidad40().setSalarioObligatorio(85.0f);

//        RespuestaValidacionTrabajador validaVigenciaContinuacionVoluntaria = validaVigenciaRemote.validaVigenciaContinuacionVoluntaria(vigenciaContVoluntaria);
        RespuestaValidacionTrabajador validaVigenciaContinuacionVoluntaria = EjbLocator.find(ValidaVigenciaRemote.class, Ambiente.STAGE).validaVigenciaContinuacionVoluntaria(vigenciaContVoluntaria);
        Assert.assertNotNull(validaVigenciaContinuacionVoluntaria);
        LOGGER.debug("Respuesta: " + validaVigenciaContinuacionVoluntaria.getMensajeValidacion());
        boolean resValidaVVCV = validaVigenciaContinuacionVoluntaria.getValido();
        Assert.assertTrue(resValidaVVCV);

    }

    /**
     * Prueba para Renovacion Esta prueba funciona para validar la vigencia de
     * un seguro CVRO La cual permite validar que el asegurado demuestra que
     * puede hacer un tramite de renovacion Esta prueba consulta un webService
     * que se llama wSConsultaMod40 enviando como unico dato el idAsignacionNSS
     * de la persona Se comprueba la validacion por medio de la fecUltimoMov
     */
    @Test
    public void validaVigenciaContinuacionVoluntariaRenovacionTest() {

        VigenciaContVoluntaria vigenciaContVoluntaria = new VigenciaContVoluntaria();
        vigenciaContVoluntaria.setClaveError("0");
        vigenciaContVoluntaria.setMensajeError("Consulta Exitosa");
        vigenciaContVoluntaria.setModalidad40(new ResultadoVigenciaContVoluntaria());
        vigenciaContVoluntaria.getModalidad40().setRegPatUltimoMov("B4894962");
        vigenciaContVoluntaria.getModalidad40().setModUltimoMov("10");
        vigenciaContVoluntaria.getModalidad40().setTipoUltimoMov("02");
        vigenciaContVoluntaria.getModalidad40().setFecUltimoMov("2017-03-25");
        vigenciaContVoluntaria.getModalidad40().setEstadoVigencia(0);
        vigenciaContVoluntaria.getModalidad40().setIndPension(0);
        vigenciaContVoluntaria.getModalidad40().setIndTrabajadorIMSS(0);
        vigenciaContVoluntaria.getModalidad40().setSemanasCotizadas(261);
        vigenciaContVoluntaria.getModalidad40().setRegPatUltimoObligatorio("B4894962");
        vigenciaContVoluntaria.getModalidad40().setModUltimoObligatorio("10");
        vigenciaContVoluntaria.getModalidad40().setTipoMovObligatorio("02");
        vigenciaContVoluntaria.getModalidad40().setFecMovObligatorio("2017-03-25");
        vigenciaContVoluntaria.getModalidad40().setSalarioObligatorio(85.0f);

        RespuestaValidacionTrabajador validaVigenciaContinuacionVoluntariaRenovacion = EjbLocator.find(ValidaVigenciaRemote.class, Ambiente.STAGE).validaVigenciaContinuacionVoluntariaRenovacion(vigenciaContVoluntaria);
        Assert.assertNotNull(validaVigenciaContinuacionVoluntariaRenovacion);
        LOGGER.debug("Respuesta: " + validaVigenciaContinuacionVoluntariaRenovacion.getMensajeValidacion());
        boolean resValidaVVCV = validaVigenciaContinuacionVoluntariaRenovacion.getValido();
        Assert.assertTrue(resValidaVVCV);
    }

    @Test
    public void enviaCorreoTest() {
//        try {
            SeguroIvro seguroIvro = new SeguroIvro();
            seguroIvro.setCveIdSeguroIvro(105034l);
            List<String> correos = new ArrayList<String>();
//            correos.add("eduardo.serrano@softtek.com");
            correos.add("luisg.gonzalez@softtek.com");
            int tipo = TipoOperacionNotificacionIVROEnum.FIN_TRAMITE.getCodigo();
//            File f = new File("C:\\Documents and Settings\\abc\\Desktop\\abc.pdf");
//            File file = new File("/home/gibrann/test.pdf");
//            byte[] bytesArray = new byte[(int) file.length()];
//            FileInputStream fis = new FileInputStream(file);
//            fis.read(bytesArray); 
//            fis.close();
            
//            File file2 = new File("/home/gibrann/test.pdf");
//            byte[] bytesArray2 = new byte[(int) file2.length()];
//            FileInputStream fis2 = new FileInputStream(file2);
//            fis2.read(bytesArray2); 6
//            fis2.close();
            
            Map<String,byte[]> adjuntos = new HashMap<String, byte[]>();
//            adjuntos.put("testLalo.pdf", bytesArray);
//            adjuntos.put("test2.pdf", bytesArray2);
            EjbLocator.find(NotificacionSegurosRemote.class, Ambiente.STAGE).enviaCorreo(seguroIvro, correos, tipo, adjuntos);
            
//        } catch (FileNotFoundException ex) {
//            LOGGER.error("ERROR: ",ex);
//        } catch (IOException ex) {
//            LOGGER.error("ERROR: ",ex);
//        } 
    }

}
