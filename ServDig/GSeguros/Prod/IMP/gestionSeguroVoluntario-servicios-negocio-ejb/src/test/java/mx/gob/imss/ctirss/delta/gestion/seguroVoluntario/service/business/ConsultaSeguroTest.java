package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.business;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.DerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.exception.beneficio.BeneficioRissException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.MunicipioImssNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.UmfNoLocalizadaException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.model.EmailPayloadType;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.beneficio.service.interfaces.BeneficioRissServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBussinessExternosRemote;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.dto.DomicilioServiceDtoRemote;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.*;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.EstadoPagoEnum;
import mx.gob.imss.ctirss.delta.gestion.patronal.global.service.interfaces.AfiliacionGlobalServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.baja.RegistroPatronalServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.exception.IvroException;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.*;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.test.Ambiente;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.test.EjbLocator;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.test.JaxbUtilT;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.integration.EMailProducer;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.global.interfaces.DeltaSolicitudServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.ConcluirAltaPatronalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudQueueProducerRemote;
import mx.gob.imss.ctirss.delta.global.model.SolicitudTO;
import mx.gob.imss.ctirss.delta.global.model.TramiteTO;
import mx.gob.imss.ctirss.delta.model.beneficio.Beneficio;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Parentesco;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.seguro.ProcesaSeguroEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.digital.modelo.cobranza.*;
import mx.gob.imss.digital.modelo.domicilio.Domicilio;
import mx.gob.imss.digital.modelo.persona.Persona;
import mx.gob.imss.digital.modelo.seguros.AsignacionNssIvro;
import mx.gob.imss.digital.modelo.seguros.DocumentoSeguro;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;
import mx.gob.imss.digital.modelo.seguros.SegurosIvro;
import mx.gob.imss.digital.modelo.sindo.*;
import mx.gob.imss.digital.modelo.tramite.Tramite;
import mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvro;
import mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvroMod33;
import mx.gob.imss.digital.modelo.util.UmaResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ConsultaSeguroTest {

    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(ConsultaSeguroTest.class);
    }

    @Test
    public void testActualizarDetalleTramiteSeguroIvro() {

        String xml = "/home/marco/Escritorio/seguro33Test.xml";

        SolicitudBusinessRemote solicitudService = EjbLocator
                .find(SolicitudBusinessRemote.class, Ambiente.LOCAL);

        try {
            TramiteSeguroIvro tramiteMod33 = JaxbUtilT.unmarshaller(xml,
                    TramiteSeguroIvro.class);

            solicitudService.actualizarXmlTramite(tramiteMod33);
        } catch (TramiteNoEncontradoException e) {
            LOGGER.error(e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.error(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Test
    public void testGeneraCotizacionSeguroIndividual() {
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
                SeguroIvro seguroActivo = EjbLocator.find(ConsultaSeguroIvroServiceRemote.class, Ambiente.LOCAL).buscaSeguro(seguro);
                lstSegurosActivos.add(seguroActivo);
            }

            int contador = 0;
            for (SeguroIvro seguro : lstSegurosActivos) {
                contador++;

                Persona persona = seguro.getTitular();

                DatosCalculoCuota datosCalculoCuota = EjbLocator.find(DatosCotizacionSeguroRemote.class, Ambiente.LOCAL).datosCotizacionIndividual(persona);
                datosCalculoCuota.setFechaInicioCalculo(fechaInicioCalculo);
                datosCalculoCuota.setFechaFinCalculo(fechaFinCalculo);

                Cotizacion cotizacion = EjbLocator.find(CuotaServiceRemote.class, Ambiente.LOCAL).generaCotizacion(datosCalculoCuota);
                // JaxB
                JAXBContext jaxbContext = JAXBContext.newInstance(new Class[] {
                        Cotizacion.class, CalculoCuota.class,
                        EmpleadoCuota.class, PeriodoCuota.class,
                        Parentesco.class, RamaCalculo.class,
                        MovimientoEmpleado.class });
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
    public void testPagosVencidosMod40() throws NamingException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        DatosCompra[] datosCompras = EjbLocator.find(CompraServiceRemote.class, Ambiente.LOCAL).pagosVencidosMod40();
        System.out.println("datosCompras.length = " + datosCompras.length);
        for (DatosCompra datosCompra : datosCompras) {
            System.out.println("datosCompra = " + BeanUtils.describe(datosCompra));
        }

    }

    @Test
    public void testGeneraComprobantes() throws NamingException {
        SegurosIvro segurosIvro = new SegurosIvro();
        SeguroIvro seguroIvro1 = new SeguroIvro();
        seguroIvro1.setCveIdSeguroIvro(103600L);
        segurosIvro.setSeguroIvro(new SeguroIvro[]{seguroIvro1});

        DocumentoSeguro documentoSeguro = EjbLocator.find(ComprobanteSeguroRemote.class, Ambiente.LOCAL).generaComprobantes(segurosIvro);

        System.out.println("documentoSeguro.getArchivo() = " + documentoSeguro.getArchivo());

        FileOutputStream fos;
        try {
            fos = new FileOutputStream("D:\\tmp\\"+new SimpleDateFormat("yyyyMMdd_hh_mm").format(new Date())+documentoSeguro.getNombreArchivo());
            System.out.println("file = " + "D:\\tmp\\"+new SimpleDateFormat("yyyyMMdd_hh_mm").format(new Date())+documentoSeguro.getNombreArchivo());
            fos.write(documentoSeguro.getArchivo());
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Test
    public void testPagosPagados() throws NamingException {
        ActualizacionCompra actualizacionCompra = EjbLocator.find(CompraServiceRemote.class, Ambiente.LOCAL).pagosPagados(new String[]{"F09X82I94BKC2AQDU31160000H1P0000S5D000000000000004CXA","F09X82I94BKC2AQJF31160000HAJ0000SK200000000000000IDFU","F09X82I94BMU2B49V31160000H140000S4F00000000000000RTX1","F09X82I94BMU2B4FC31160000EYN0000OPG00000000000000TE3H","F09X82I94BMW2B4KZ31160000GAH0000QWF000000000000005HOX","F09X82I94BMW2B4QI31160000FHU0000PL500000000000000GX41","F09X82I94BMY2B4W331160000FU00000Q5900000000000000JBUJ"});
        System.out.println("actualizacionCompra:"+mx.gob.imss.distss.digital.jaxb.util.JaxbUtil.objectToXml(actualizacionCompra));
    }

    @Test
    public void testObtenerBeneficioPorNSS() throws NamingException, BeneficioRissException {

        Calendar fechaInicio = Calendar.getInstance();
        fechaInicio.clear();
        fechaInicio.set(2014, 00, 01, 13, 20, 00);
        Calendar fechaFin = Calendar.getInstance();
        fechaFin.clear();
        fechaFin.set(2016, 11, 31, 20, 00, 05);

        EjbLocator.find(BeneficioRissServiceBusinessRemote.class,Ambiente.LOCAL).obtenerBeneficioPorNSS("67108613430", fechaInicio.getTime(), fechaFin.getTime());
    }

    @Test
    public void testFindPagoById() throws NamingException {
        try {
            Pago pago = EjbLocator.find(CompraServiceRemote.class, Ambiente.LOCAL).findPagoById(109969L);//
            //System.out.println(String.valueOf(BeanUtils.describe(resp)));
            //System.out.println(String.valueOf(pago.getSuaPago().getPatron().getFolioSUA()));

            // JaxB
            JAXBContext jaxbContext = JAXBContext.newInstance(new Class[]{Pago.class});
            final Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            final Writer writer = new StringWriter();

            marshaller.marshal(pago, writer);
            String xml = writer.toString();
            System.out.println(xml);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testValidaVigenciaTrabajador() throws NamingException {

        Context iCtx = null;
        final Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "weblogic.jndi.WLInitialContextFactory");
        env.put(Context.PROVIDER_URL, "t3://localhost:7001");
        env.put(Context.SECURITY_PRINCIPAL, "weblogic");
        env.put(Context.SECURITY_CREDENTIALS, "weblogic1");
        iCtx = new InitialContext(env);

        ValidaVigenciaRemote ejb = (ValidaVigenciaRemote)iCtx.lookup("validaVigenciaBusinesss#mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ValidaVigenciaRemote");
        System.out.println("ejb = " + ejb);
        try {

            String xml = "<java:validaVigenciaTrabajador xmlns:java=\"java:mx.gob.imss.digital.modelo.sindo\">" +
                    "    <java:VigenciaTrabajdor>" +
                    "      <java:ClaveError>00</java:ClaveError>" +
                    "      <java:MensajeError>Consulta Exitosa</java:MensajeError>" +
                    "      <java:Resultado>" +
                    "        <java:IndicadorVigente>true</java:IndicadorVigente>" +
                    "        <java:ModalidadesFechaVigente>" +
                    "          <java:Modalidad>40</java:Modalidad>" +
                    "          <java:Fecha>01/08/2015</java:Fecha>" +
                    "        </java:ModalidadesFechaVigente>" +
                    "      </java:Resultado>" +
                    "    </java:VigenciaTrabajdor>" +
                    "  </java:validaVigenciaTrabajador>";

            JAXBContext jaxbContext = JAXBContext.newInstance(new Class[]{VigenciaTrabajdor.class});
            final Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            VigenciaTrabajdor vigenciaTrabajdor = new VigenciaTrabajdor();
            vigenciaTrabajdor.setClaveError("00");
            vigenciaTrabajdor.setMensajeError("Consulta Exitosa");
            ResultadoVigenciaTrabajdor resultado = new ResultadoVigenciaTrabajdor();
            resultado.setIndicadorVigente(true);
            ModalidadTrabajador[] mod = new ModalidadTrabajador[1];
            mod[0] = new ModalidadTrabajador();
            mod[0].setFecha("01/08/2015");
            mod[0].setModalidad("40");
            resultado.setModalidadesFechaVigente(mod);
            vigenciaTrabajdor.setResultado(resultado);

            RespuestaValidacionTrabajador respuestaValidacionTrabajador = ejb.validaVigenciaTrabajador(vigenciaTrabajdor);

            System.out.println("respuestaValidacionTrabajador = " + respuestaValidacionTrabajador);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testGeneraCotizacion() throws Exception {

        JAXBContext jaxbContext = JAXBContext.newInstance(new Class[]{DatosCalculoCuota.class,Cotizacion.class});
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Marshaller marshaller = jaxbContext.createMarshaller();

        String strXml = "<ns2:datosCalculoCuota xmlns:ns2='http://mx.gob.imss.digital.modelo.cobranza'>\n" +
                "<fechaInicioCalculo>2017-01-15T12:54:54.628-06:00</fechaInicioCalculo>\n" +
                "    <fechaFinCalculo>2017-01-31T12:54:54.628-06:00</fechaFinCalculo>\n" +
                "    <numeroRegistroPatronal>A4999999402</numeroRegistroPatronal>\n" +
                "    <modalidad>20</modalidad>\n" +/**/
                "    <zonaSalarial>B</zonaSalarial>\n" +
                "    <renovacion>true</renovacion>\n" +
                "    <empleados>\n" +
                "        <numeroSeguridadSocial>01725312522</numeroSeguridadSocial>\n" +
                "        <salario>2001</salario>\n" +
                "        <edad>0</edad>\n" +
                "        <parentesco>-1</parentesco>\n" +
                "    </empleados>\n" +
                "    <salarioMinimo>1000</salarioMinimo>\n" +
                "    <recargos>true</recargos>\n" +
                "    <aplicaRecargoPorFechaBaja>true</aplicaRecargoPorFechaBaja>\n"+
                "</ns2:datosCalculoCuota>";

        InputStream stream = new ByteArrayInputStream(strXml.getBytes("UTF-8"));

        System.out.println("datosCalculoCuota = " + strXml);

        DatosCalculoCuota calculoCuota = (DatosCalculoCuota)unmarshaller.unmarshal(stream);


        Cotizacion cotizacion = EjbLocator.find(CuotaServiceRemote.class, Ambiente.STAGE).generaCotizacion(calculoCuota);

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        final Writer writer = new StringWriter();

        marshaller.marshal(cotizacion, writer);
        String xml = writer.toString();
        System.out.println("cotizacion = " + xml);

    }

    @Test
    public void testGetUmaResponse() throws Exception {

        JAXBContext jaxbContext = JAXBContext.newInstance(new Class[]{UmaResponse.class});
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Marshaller marshaller = jaxbContext.createMarshaller();

        String strXml = "\t<m:getUmaResponse  \txmlns:m=\"java:mx.gob.imss.digital.modelo.seguros\" xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "\t<m:uma>73.04</m:uma>\n" +
                "\t</m:getUmaResponse>";

        InputStream stream = new ByteArrayInputStream(strXml.getBytes("UTF-8"));

        System.out.println("datosCalculoCuota = " + strXml);

        UmaResponse umaResponse = (UmaResponse)unmarshaller.unmarshal(stream);

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        final Writer writer = new StringWriter();

        System.out.println("umaResponse = " + BeanUtils.describe(umaResponse));

    }

    @Test
    public void testGeneraCompra() throws Exception {

        JAXBContext jaxbContext = JAXBContext.newInstance(new Class[]{Cotizacion.class,Compra.class});
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Marshaller marshaller = jaxbContext.createMarshaller();

        String strXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<java:cotizacion xmlns:java=\"http://mx.gob.imss.digital.modelo.cobranza\">\n" +
                "  <idCotizacion>67854</idCotizacion>\n" +
                "  <fecha>2016-12-28T14:52:09.659-06:00</fecha>\n" +
                "  <fechaVigencia>2016-12-28T14:52:09.659-06:00</fechaVigencia>\n" +
                "  <cuotaTotal>5113.66</cuotaTotal>\n" +
                "  <detalle>\n" +
                "    <fechaInicioCalculo>2016-12-01T14:52:09.575-06:00</fechaInicioCalculo>\n" +
                "    <fechaFinCalculo>2016-12-31T14:52:09.575-06:00</fechaFinCalculo>\n" +
                "    <numeroRegistroPatronal>Y5899999402</numeroRegistroPatronal>\n" +
                "    <modalidad>20</modalidad>\n" +
                "    <zonaSalarial>A</zonaSalarial>\n" +
                "    <cuotaTotal>5113.66</cuotaTotal>\n" +
                "    <conBeneficio>false</conBeneficio>\n" +
                "    <conRecargos>true</conRecargos>\n" +
                "    <versionSUA/>\n" +
                "    <empleados>\n" +
                "      <numeroSeguridadSocial>10745515220</numeroSeguridadSocial>\n" +
                "      <cuotaTotal>5113.66</cuotaTotal>\n" +
                "      <salario>1619</salario>\n" +
                "      <periodos>\n" +
                "        <orden>1</orden>\n" +
                "        <inicioPeriodo>2016-12-01T00:00:00.000-06:00</inicioPeriodo>\n" +
                "        <finPeriodo>2016-12-31T00:00:00.000-06:00</finPeriodo>\n" +
                "        <total>5113.66</total>\n" +
                "        <factorActualizacion/>\n" +
                "        <factorRecargo>0.0113</factorRecargo>\n" +
                "        <cuotaRecargo>57.12</cuotaRecargo>\n" +
                "        <salarioPeriodo>1619</salarioPeriodo>\n" +
                "      </periodos>\n" +
                "      <conBeneficio>false</conBeneficio>\n" +
                "      <nombreTrabajador>LUIS AGUILAR MEJIA</nombreTrabajador>\n" +
                "      <cuotaRecargo>57.12</cuotaRecargo>\n" +
                "      <edad>61</edad>\n" +
                "      <curp>AUML550923HOCGJS09</curp>\n" +
                "      <parentesco>\n" +
                "        <idParentesco>-1</idParentesco>\n" +
                "        <descripcion/>\n" +
                "      </parentesco>\n" +
                "    </empleados>\n" +
                "    <concepto>1</concepto>\n" +
                "    <renovacion>false</renovacion>\n" +
                "    <cuotaRecargo>57.12</cuotaRecargo>\n" +
                "    <aplicaRecargoPorFechaBaja>false</aplicaRecargoPorFechaBaja>\n" +
                "  </detalle>\n" +
                "  <concepto>1</concepto>\n" +
                "  <renovacion>false</renovacion>\n" +
                "  <errorFormGeneral/>\n" +
                "  <aplicaCuestionario>false</aplicaCuestionario>\n" +
                "</java:cotizacion>";

        InputStream stream = new ByteArrayInputStream(strXml.getBytes("UTF-8"));

        System.out.println("strXml = " + strXml);

        Cotizacion cotizacion = (Cotizacion)unmarshaller.unmarshal(stream);


        Compra compra = EjbLocator.find(GeneradorCompraServiceRemote.class, Ambiente.LOCAL).generaCompra(cotizacion);

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        final Writer writer = new StringWriter();

        marshaller.marshal(compra, writer);
        String xml = writer.toString();
        System.out.println("xml = " + xml);

    }

    @Test
    public void testConcluirAltaPatronal() throws NamingException, SUAException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, JAXBException, UnsupportedEncodingException, GestionPatronalBusinessException {

        Long idSolicitud = 40200301L;//5364l se puede usar esta tambien
        String numeroRegistroPatronal="Y3824564102";//H4621428146
        String numeroRegistroPatronal14=null;

        EjbLocator.find(AfiliacionGlobalServiceRemote.class, Ambiente.LOCAL).concluirAltaPatronal(idSolicitud,numeroRegistroPatronal,numeroRegistroPatronal14);
    }

    @Test
    public void testNotificaRenovacion() throws NamingException, SUAException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, JAXBException, UnsupportedEncodingException, GestionPatronalBusinessException {

        EjbLocator.find(NotificacionSegurosRemote.class, Ambiente.LOCAL).notificaRenovacion();
    }

    @Test
    public void testValidaTrabajadorSeguroAnterior() throws NamingException, SUAException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, JAXBException, UnsupportedEncodingException, GestionPatronalBusinessException {

        RespuestaValidacionTrabajador respuestaValidacionTrabajador = EjbLocator.find(ValidaTrabajadorDomesticoRemote.class, Ambiente.LOCAL).validaTrabajadorSeguroAnterior(4287071L, null, "04876812969");
        System.out.println("respuestaValidacionTrabajador = " + BeanUtils.describe(respuestaValidacionTrabajador));
    }

    @Test
    public void testConsultarPorIdTramite() throws Exception {
        Solicitud solicitud = EjbLocator.find(SolicitudBusinessRemote.class, Ambiente.LOCAL).consultarPorIdTramite(44919752L);
        System.out.println("solicitud = " + solicitud);

    }

    @Test
    public void testRegistrarDomicilioFiscal() throws Exception {
        DomicilioServiceBusinessRemote ejb = EjbLocator.find(DomicilioServiceBusinessRemote.class, Ambiente.LOCAL);

        mx.gob.imss.ctirss.delta.model.domicilio.Domicilio filter = new mx.gob.imss.ctirss.delta.model.domicilio.Domicilio();
        filter.setClave(519174);

        try {
            mx.gob.imss.ctirss.delta.model.domicilio.Domicilio domicilio = ejb.consultarDomicilio(filter);
            System.out.println(domicilio);

            domicilio.setClave(null);

            DomicilioFiscal domicilioFiscal = new DomicilioFiscal();

            domicilioFiscal.setClave(domicilio.getClave());
            domicilioFiscal.setCalle(domicilio.getCalle());
            domicilioFiscal.setColonia(domicilio.getColonia());
            domicilioFiscal.setNumExterior1(domicilio.getNumExterior1());
            domicilioFiscal.setNumExteriorAlf(domicilio.getNumExteriorAlf());
            domicilioFiscal.setNumExterior2(domicilio.getNumExterior2());
            domicilioFiscal.setNumInterior(domicilio.getNumInterior());
            domicilioFiscal.setNumInteriorAlf(domicilio.getNumInteriorAlf());
            domicilioFiscal.setLatitud(domicilio.getLatitud());
            domicilioFiscal.setLongitud(domicilio.getLongitud());
            domicilioFiscal.setCodigoPostal(domicilio.getCodigoPostal());
            domicilioFiscal.setAsentamiento(domicilio.getAsentamiento());
            domicilioFiscal.setAmbito(domicilio.getAmbito());
            domicilioFiscal.setVialidadPrimaria(domicilio.getVialidadPrimaria());
            domicilioFiscal.setVialidadReferenciaPrimaria(domicilio.getVialidadReferenciaPrimaria());
            domicilioFiscal.setVialidadReferenciaSecundaria(domicilio.getVialidadReferenciaSecundaria());
            domicilioFiscal.setVialidadReferenciaPosterior(domicilio.getVialidadReferenciaPosterior());
            domicilioFiscal.setTipoDomicilio(domicilio.getTipoDomicilio());
            domicilioFiscal.setDescripcion(domicilio.getDescripcion());

            ejb.registrarDomicilioFiscal(domicilioFiscal);
        } catch (DomicilioNoLocalizadoException e) {
            e.printStackTrace();
        }
        catch (DomicilioNoValidoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConsultarFolioTest() {
        Solicitud solicitud = new Solicitud();
        solicitud.setNoFolioSolicitud("149376669739374728582");

        try {
            Solicitud solicitudFound = EjbLocator.find(SolicitudBusinessRemote.class, Ambiente.LOCAL).consultarFolio(solicitud);
            System.out.println("solicitudFound.toString() = " + solicitudFound.toString());
        } catch (SolicitudNoEncontradaException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConversionTramiteTest() {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<ns7:tramiteSeguroIvro xmlns:ns29=\"http://mx.gob.imss.delta.global.service/\" xmlns:ns25=\"http://www.mx.gob.imss.ctirss.delta/movimientoasignacion\" xmlns:ns26=\"http://delta.ctirss.imss.gob.mx/SIME\" xmlns:ns27=\"http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado\" xmlns:ns28=\"http://www.mx.gob.imss.distss.derechohabientes.adimss/WSConsultaAdimssService/\" xmlns:ns21=\"http://www.mx.gob.imss.ctirss.delta/trabajadoresImssInfonavit\" xmlns:ns22=\"http://mx.gob.imss.ctirss.delta/movimientosua\" xmlns:ns23=\"http://www.mx.gob.imss.ctirss.delta/movimientoRiss\" xmlns:ns24=\"http://www.mx.gob.imss.ctirss.delta/integracion_reingreso\" xmlns:ns20=\"http://mx.gob.imss.delta.global.services/\" xmlns:ns16=\"http://mx.gob.imss.digital.modelo.cuestionario\" xmlns:ns17=\"http://www.sat.gob.mx/cfd/3\" xmlns:ns14=\"mx.gob.imss.digital.modelo.derechohabiente\" xmlns:ns15=\"http://mx.gob.imss.digital.modelo.cobranza\" xmlns:ns18=\"http://www.sat.gob.mx/TimbreFiscalDigital\" xmlns:ns19=\"http://www.mx.gob.imss.ctirss.delta.cobranza\" xmlns:ns9=\"http://mx.gob.imss.digital.modelo.persona\" xmlns:mx=\"http://mx.gob.imss.email.model\" xmlns:ns30=\"http://mx.gob.imss.digital.modelo.seguros\" xmlns:ns12=\"http://mx.gob.imss.digital.modelo.comun\" xmlns:ns5=\"http://www.mx.gob.imss.ctirss.delta/movimiento06Sindo\" xmlns:ns31=\"http://mx.gob.imss.digital.modelo.solicitud\" xmlns:ns13=\"http://mx.gob.imss.digital.modelo.patron\" xmlns:ns6=\"http://www.mx.gob.imss.ctirss.delta/movtoPatSujetoObligado\" xmlns:ns10=\"http://mx.gob.imss.digital.modelo.domicilio\" xmlns:ns7=\"http://mx.gob.imss.digital.modelo.tramite\" xmlns:ns11=\"http://mx.gob.imss.digital.modelo.medio.contacto\" xmlns:ns8=\"http://mx.gob.imss.digital.modelo.documento.probatorio\" xmlns:ns4=\"http://www.mx.gob.imss.ctirss.delta.model.derechohabiente.negocio/tramiteDerechohabiente\" xmlns:ns3=\"modelo\">\n" +
                "    <estadoTramite>\n" +
                "        <idEstadoTramitePersona>2</idEstadoTramitePersona>\n" +
                "        <descripcion>CERRADO</descripcion>\n" +
                "    </estadoTramite>\n" +
                "    <tramiteId>44922755</tramiteId>\n" +
                "    <tipoTramite>\n" +
                "        <idTipoTramite>121</idTipoTramite>\n" +
                "    </tipoTramite>\n" +
                "    <persona>\n" +
                "        <idPersona>2357</idPersona>\n" +
                "        <tipoPersona>\n" +
                "            <idTipoPersona>1</idTipoPersona>\n" +
                "        </tipoPersona>\n" +
                "        <domicilioParticular>\n" +
                "            <numExterior1>78</numExterior1>\n" +
                "            <numExterior2>0</numExterior2>\n" +
                "            <numInterior>0</numInterior>\n" +
                "            <codigoPostal>06600</codigoPostal>\n" +
                "            <asentamiento>\n" +
                "                <clave>047293</clave>\n" +
                "                <nombre>Ju�rez</nombre>\n" +
                "                <localidad>\n" +
                "                    <clave>0001</clave>\n" +
                "                    <nombre>CUAUHT�MOC</nombre>\n" +
                "                    <municipio>\n" +
                "                        <clave>015</clave>\n" +
                "                        <nombre>CUAUHT�MOC</nombre>\n" +
                "                        <entidadFederativa>\n" +
                "                            <clave>09</clave>\n" +
                "                            <nombre>DISTRITO FEDERAL</nombre>\n" +
                "                        </entidadFederativa>\n" +
                "                    </municipio>\n" +
                "                </localidad>\n" +
                "                <municipio>\n" +
                "                    <clave>015</clave>\n" +
                "                    <nombre>CUAUHT�MOC</nombre>\n" +
                "                    <entidadFederativa>\n" +
                "                        <clave>09</clave>\n" +
                "                        <nombre>DISTRITO FEDERAL</nombre>\n" +
                "                    </entidadFederativa>\n" +
                "                </municipio>\n" +
                "                <codigoPostal>06600</codigoPostal>\n" +
                "                <periodo>0</periodo>\n" +
                "            </asentamiento>\n" +
                "            <vialidadPrimaria>\n" +
                "                <clave>289951</clave>\n" +
                "                <nombre>HAMBURGO</nombre>\n" +
                "                <tipoVialidad>\n" +
                "                    <clave>8</clave>\n" +
                "                    <descripcion>CERRADA</descripcion>\n" +
                "                </tipoVialidad>\n" +
                "            </vialidadPrimaria>\n" +
                "        </domicilioParticular>\n" +
                "        <rfc>SIN_RFC</rfc>\n" +
                "        <lugarNacimiento/>\n" +
                "        <sexo/>\n" +
                "        <nss>01038105597</nss>\n" +
                "        <nssCifrado>cvCh5EZRE87IQIcLOldxZw.</nssCifrado>\n" +
                "    </persona>\n" +
                "    <cotizacion>\n" +
                "        <idCotizacion>21059</idCotizacion>\n" +
                "        <fecha>2016-09-20T13:06:53.549-05:00</fecha>\n" +
                "        <fechaVigencia>2016-09-20T13:06:53.549-05:00</fechaVigencia>\n" +
                "        <cuotaTotal>16193.80</cuotaTotal>\n" +
                "        <concepto>1</concepto>\n" +
                "        <renovacion>false</renovacion>\n" +
                "        <detalle>\n" +
                "            <fechaInicioCalculo>2016-10-01T13:06:53.488-05:00</fechaInicioCalculo>\n" +
                "            <fechaFinCalculo>2017-09-30T13:06:53.488-05:00</fechaFinCalculo>\n" +
                "            <numeroRegistroPatronal></numeroRegistroPatronal>\n" +
                "            <modalidad>16</modalidad>\n" +
                "            <zonaSalarial>A</zonaSalarial>\n" +
                "            <cuotaTotal>16193.80</cuotaTotal>\n" +
                "            <cuotaRecargo>1472.24</cuotaRecargo>\n" +
                "            <conBeneficio>false</conBeneficio>\n" +
                "            <conRecargos>true</conRecargos>\n" +
                "            <aplicaRecargoPorFechaBaja>false</aplicaRecargoPorFechaBaja>\n" +
                "            <versionSUA></versionSUA>\n" +
                "            <renovacion>false</renovacion>\n" +
                "            <empleados>\n" +
                "                <numeroSeguridadSocial>12814202177</numeroSeguridadSocial>\n" +
                "                <salario>233.33</salario>\n" +
                "                <cuotaTotal>16193.80</cuotaTotal>\n" +
                "                <periodos>\n" +
                "                    <orden>1</orden>\n" +
                "                    <inicioPeriodo>2016-10-01T00:00:00-05:00</inicioPeriodo>\n" +
                "                    <finPeriodo>2016-10-31T00:00:00-06:00</finPeriodo>\n" +
                "                    <total>1375.37</total>\n" +
                "                    <factorRecargo>0.1</factorRecargo>\n" +
                "                    <cuotaRecargo>125.05</cuotaRecargo>\n" +
                "                    <salarioPeriodo>233.33</salarioPeriodo>\n" +
                "                </periodos>\n" +
                "                <periodos>\n" +
                "                    <orden>2</orden>\n" +
                "                    <inicioPeriodo>2016-11-01T00:00:00-06:00</inicioPeriodo>\n" +
                "                    <finPeriodo>2016-12-31T00:00:00-06:00</finPeriodo>\n" +
                "                    <total>2706.37</total>\n" +
                "                    <factorRecargo>0.1</factorRecargo>\n" +
                "                    <cuotaRecargo>246.05</cuotaRecargo>\n" +
                "                    <salarioPeriodo>233.33</salarioPeriodo>\n" +
                "                </periodos>\n" +
                "                <periodos>\n" +
                "                    <orden>3</orden>\n" +
                "                    <inicioPeriodo>2017-01-01T00:00:00-06:00</inicioPeriodo>\n" +
                "                    <finPeriodo>2017-02-28T00:00:00-06:00</finPeriodo>\n" +
                "                    <total>2617.61</total>\n" +
                "                    <factorRecargo>0.1</factorRecargo>\n" +
                "                    <cuotaRecargo>237.97</cuotaRecargo>\n" +
                "                    <salarioPeriodo>233.33</salarioPeriodo>\n" +
                "                </periodos>\n" +
                "                <periodos>\n" +
                "                    <orden>4</orden>\n" +
                "                    <inicioPeriodo>2017-03-01T00:00:00-06:00</inicioPeriodo>\n" +
                "                    <finPeriodo>2017-04-30T00:00:00-05:00</finPeriodo>\n" +
                "                    <total>2706.37</total>\n" +
                "                    <factorRecargo>0.1</factorRecargo>\n" +
                "                    <cuotaRecargo>246.05</cuotaRecargo>\n" +
                "                    <salarioPeriodo>233.33</salarioPeriodo>\n" +
                "                </periodos>\n" +
                "                <periodos>\n" +
                "                    <orden>5</orden>\n" +
                "                    <inicioPeriodo>2017-05-01T00:00:00-05:00</inicioPeriodo>\n" +
                "                    <finPeriodo>2017-06-30T00:00:00-05:00</finPeriodo>\n" +
                "                    <total>2706.37</total>\n" +
                "                    <factorRecargo>0.1</factorRecargo>\n" +
                "                    <cuotaRecargo>246.05</cuotaRecargo>\n" +
                "                    <salarioPeriodo>233.33</salarioPeriodo>\n" +
                "                </periodos>\n" +
                "                <periodos>\n" +
                "                    <orden>6</orden>\n" +
                "                    <inicioPeriodo>2017-07-01T00:00:00-05:00</inicioPeriodo>\n" +
                "                    <finPeriodo>2017-08-31T00:00:00-05:00</finPeriodo>\n" +
                "                    <total>2750.69</total>\n" +
                "                    <factorRecargo>0.1</factorRecargo>\n" +
                "                    <cuotaRecargo>250.05</cuotaRecargo>\n" +
                "                    <salarioPeriodo>233.33</salarioPeriodo>\n" +
                "                </periodos>\n" +
                "                <periodos>\n" +
                "                    <orden>7</orden>\n" +
                "                    <inicioPeriodo>2017-09-01T00:00:00-05:00</inicioPeriodo>\n" +
                "                    <finPeriodo>2017-09-30T00:00:00-05:00</finPeriodo>\n" +
                "                    <total>1331.02</total>\n" +
                "                    <factorRecargo>0.1</factorRecargo>\n" +
                "                    <cuotaRecargo>121.02</cuotaRecargo>\n" +
                "                    <salarioPeriodo>233.33</salarioPeriodo>\n" +
                "                </periodos>\n" +
                "                <conBeneficio>false</conBeneficio>\n" +
                "                <nombreTrabajador>MARIA CLARA GLORIA VAZQUEZ</nombreTrabajador>\n" +
                "                <edad>-1</edad>\n" +
                "                <parentesco/>\n" +
                "                <curp>GOVC420812MGTLZL07</curp>\n" +
                "                <cuotaRecargo>1472.24</cuotaRecargo>\n" +
                "            </empleados>\n" +
                "        </detalle>\n" +
                "        <aplicaCuestionario>false</aplicaCuestionario>\n" +
                "        <errorFormGeneral></errorFormGeneral>\n" +
                "    </cotizacion>\n" +
                "    <modalidad>\n" +
                "        <idModalidad>16</idModalidad>\n" +
                "    </modalidad>\n" +
                "    <beneficiarios>\n" +
                "        <nombre>MARIA CLARA GLORIA VAZQUEZ</nombre>\n" +
                "        <lugarNacimiento/>\n" +
                "        <sexo/>\n" +
                "        <nss>12814202177</nss>\n" +
                "    </beneficiarios>\n" +
                "    <renovacion>false</renovacion>\n" +
                "    <cuetionarios>\n" +
                "        <idPersona>2357</idPersona>\n" +
                "        <respuestasCuestionario>\n" +
                "            <tipoCuestionario>1</tipoCuestionario>\n" +
                "            <respuestas>\n" +
                "                <cvePregunta>59</cvePregunta>\n" +
                "                <valores>\n" +
                "                    <clave>129</clave>\n" +
                "                    <descripcion>SI</descripcion>\n" +
                "                    <valor>0</valor>\n" +
                "                    <habilitarDependencia>false</habilitarDependencia>\n" +
                "                </valores>\n" +
                "                <numPregunta>1</numPregunta>\n" +
                "                <numSeccion>5</numSeccion>\n" +
                "            </respuestas>\n" +
                "            <respuestas>\n" +
                "                <cvePregunta>60</cvePregunta>\n" +
                "                <valores>\n" +
                "                    <clave>132</clave>\n" +
                "                    <descripcion>NO</descripcion>\n" +
                "                    <valor>0</valor>\n" +
                "                    <habilitarDependencia>false</habilitarDependencia>\n" +
                "                </valores>\n" +
                "                <numPregunta>2</numPregunta>\n" +
                "                <numSeccion>5</numSeccion>\n" +
                "            </respuestas>\n" +
                "            <respuestas>\n" +
                "                <cvePregunta>63</cvePregunta>\n" +
                "                <valores>\n" +
                "                    <clave>139</clave>\n" +
                "                    <descripcion>NO</descripcion>\n" +
                "                    <valor>0</valor>\n" +
                "                    <habilitarDependencia>false</habilitarDependencia>\n" +
                "                </valores>\n" +
                "                <numPregunta>3</numPregunta>\n" +
                "                <numSeccion>5</numSeccion>\n" +
                "            </respuestas>\n" +
                "            <respuestas>\n" +
                "                <cvePregunta>66</cvePregunta>\n" +
                "                <valores>\n" +
                "                    <clave>146</clave>\n" +
                "                    <descripcion>NO</descripcion>\n" +
                "                    <valor>0</valor>\n" +
                "                    <habilitarDependencia>false</habilitarDependencia>\n" +
                "                </valores>\n" +
                "                <numPregunta>4</numPregunta>\n" +
                "                <numSeccion>5</numSeccion>\n" +
                "            </respuestas>\n" +
                "            <respuestas>\n" +
                "                <cvePregunta>67</cvePregunta>\n" +
                "                <valores>\n" +
                "                    <clave>148</clave>\n" +
                "                    <descripcion>ENTRE 1.50 Y 1.70 MTS</descripcion>\n" +
                "                    <valor>0</valor>\n" +
                "                    <habilitarDependencia>false</habilitarDependencia>\n" +
                "                </valores>\n" +
                "                <numPregunta>5</numPregunta>\n" +
                "                <numSeccion>5</numSeccion>\n" +
                "            </respuestas>\n" +
                "            <respuestas>\n" +
                "                <cvePregunta>68</cvePregunta>\n" +
                "                <valores>\n" +
                "                    <clave>151</clave>\n" +
                "                    <descripcion>ENTRE 50 Y 80 KG</descripcion>\n" +
                "                    <valor>0</valor>\n" +
                "                    <habilitarDependencia>false</habilitarDependencia>\n" +
                "                </valores>\n" +
                "                <numPregunta>6</numPregunta>\n" +
                "                <numSeccion>5</numSeccion>\n" +
                "            </respuestas>\n" +
                "            <respuestas>\n" +
                "                <cvePregunta>69</cvePregunta>\n" +
                "                <valores>\n" +
                "                    <clave>154</clave>\n" +
                "                    <descripcion>NO</descripcion>\n" +
                "                    <valor>0</valor>\n" +
                "                    <habilitarDependencia>false</habilitarDependencia>\n" +
                "                </valores>\n" +
                "                <numPregunta>1</numPregunta>\n" +
                "                <numSeccion>6</numSeccion>\n" +
                "            </respuestas>\n" +
                "            <respuestas>\n" +
                "                <cvePregunta>70</cvePregunta>\n" +
                "                <valores>\n" +
                "                    <clave>156</clave>\n" +
                "                    <descripcion>NO</descripcion>\n" +
                "                    <valor>0</valor>\n" +
                "                    <habilitarDependencia>false</habilitarDependencia>\n" +
                "                </valores>\n" +
                "                <numPregunta>2</numPregunta>\n" +
                "                <numSeccion>6</numSeccion>\n" +
                "            </respuestas>\n" +
                "            <respuestas>\n" +
                "                <cvePregunta>71</cvePregunta>\n" +
                "                <valores>\n" +
                "                    <clave>158</clave>\n" +
                "                    <descripcion>NO</descripcion>\n" +
                "                    <valor>0</valor>\n" +
                "                    <habilitarDependencia>false</habilitarDependencia>\n" +
                "                </valores>\n" +
                "                <numPregunta>3</numPregunta>\n" +
                "                <numSeccion>6</numSeccion>\n" +
                "            </respuestas>\n" +
                "            <respuestas>\n" +
                "                <cvePregunta>72</cvePregunta>\n" +
                "                <valores>\n" +
                "                    <clave>-1</clave>\n" +
                "                    <descripcion>NINGUNA DE LAS OPCIONES</descripcion>\n" +
                "                    <valor>0</valor>\n" +
                "                    <habilitarDependencia>false</habilitarDependencia>\n" +
                "                </valores>\n" +
                "                <numPregunta>4</numPregunta>\n" +
                "                <numSeccion>6</numSeccion>\n" +
                "            </respuestas>\n" +
                "            <respuestas>\n" +
                "                <cvePregunta>73</cvePregunta>\n" +
                "                <valores>\n" +
                "                    <clave>164</clave>\n" +
                "                    <descripcion>NO</descripcion>\n" +
                "                    <valor>0</valor>\n" +
                "                    <habilitarDependencia>false</habilitarDependencia>\n" +
                "                </valores>\n" +
                "                <numPregunta>5</numPregunta>\n" +
                "                <numSeccion>6</numSeccion>\n" +
                "            </respuestas>\n" +
                "            <respuestas>\n" +
                "                <cvePregunta>74</cvePregunta>\n" +
                "                <valores>\n" +
                "                    <clave>166</clave>\n" +
                "                    <descripcion>NO</descripcion>\n" +
                "                    <valor>0</valor>\n" +
                "                    <habilitarDependencia>false</habilitarDependencia>\n" +
                "                </valores>\n" +
                "                <numPregunta>6</numPregunta>\n" +
                "                <numSeccion>6</numSeccion>\n" +
                "            </respuestas>\n" +
                "            <respuestas>\n" +
                "                <cvePregunta>75</cvePregunta>\n" +
                "                <valores>\n" +
                "                    <clave>168</clave>\n" +
                "                    <descripcion>NO</descripcion>\n" +
                "                    <valor>0</valor>\n" +
                "                    <habilitarDependencia>false</habilitarDependencia>\n" +
                "                </valores>\n" +
                "                <numPregunta>7</numPregunta>\n" +
                "                <numSeccion>6</numSeccion>\n" +
                "            </respuestas>\n" +
                "            <respuestas>\n" +
                "                <cvePregunta>76</cvePregunta>\n" +
                "                <valores>\n" +
                "                    <clave>170</clave>\n" +
                "                    <descripcion>NO</descripcion>\n" +
                "                    <valor>0</valor>\n" +
                "                    <habilitarDependencia>false</habilitarDependencia>\n" +
                "                </valores>\n" +
                "                <numPregunta>8</numPregunta>\n" +
                "                <numSeccion>6</numSeccion>\n" +
                "            </respuestas>\n" +
                "            <respuestas>\n" +
                "                <cvePregunta>77</cvePregunta>\n" +
                "                <valores>\n" +
                "                    <clave>172</clave>\n" +
                "                    <descripcion>NO</descripcion>\n" +
                "                    <valor>0</valor>\n" +
                "                    <habilitarDependencia>false</habilitarDependencia>\n" +
                "                </valores>\n" +
                "                <numPregunta>9</numPregunta>\n" +
                "                <numSeccion>6</numSeccion>\n" +
                "            </respuestas>\n" +
                "            <respuestas>\n" +
                "                <cvePregunta>78</cvePregunta>\n" +
                "                <valores>\n" +
                "                    <clave>174</clave>\n" +
                "                    <descripcion>NO</descripcion>\n" +
                "                    <valor>0</valor>\n" +
                "                    <habilitarDependencia>false</habilitarDependencia>\n" +
                "                </valores>\n" +
                "                <numPregunta>10</numPregunta>\n" +
                "                <numSeccion>6</numSeccion>\n" +
                "            </respuestas>\n" +
                "            <respuestas>\n" +
                "                <cvePregunta>79</cvePregunta>\n" +
                "                <valores>\n" +
                "                    <clave>176</clave>\n" +
                "                    <descripcion>NO</descripcion>\n" +
                "                    <valor>0</valor>\n" +
                "                    <habilitarDependencia>false</habilitarDependencia>\n" +
                "                </valores>\n" +
                "                <numPregunta>11</numPregunta>\n" +
                "                <numSeccion>6</numSeccion>\n" +
                "            </respuestas>\n" +
                "            <respuestas>\n" +
                "                <cvePregunta>80</cvePregunta>\n" +
                "                <valores>\n" +
                "                    <clave>178</clave>\n" +
                "                    <descripcion>NO</descripcion>\n" +
                "                    <valor>0</valor>\n" +
                "                    <habilitarDependencia>false</habilitarDependencia>\n" +
                "                </valores>\n" +
                "                <numPregunta>12</numPregunta>\n" +
                "                <numSeccion>6</numSeccion>\n" +
                "            </respuestas>\n" +
                "            <respuestas>\n" +
                "                <cvePregunta>81</cvePregunta>\n" +
                "                <valores>\n" +
                "                    <clave>180</clave>\n" +
                "                    <descripcion>NO</descripcion>\n" +
                "                    <valor>0</valor>\n" +
                "                    <habilitarDependencia>false</habilitarDependencia>\n" +
                "                </valores>\n" +
                "                <numPregunta>13</numPregunta>\n" +
                "                <numSeccion>6</numSeccion>\n" +
                "            </respuestas>\n" +
                "            <respuestas>\n" +
                "                <cvePregunta>82</cvePregunta>\n" +
                "                <valores>\n" +
                "                    <clave>182</clave>\n" +
                "                    <descripcion>NO</descripcion>\n" +
                "                    <valor>0</valor>\n" +
                "                    <habilitarDependencia>false</habilitarDependencia>\n" +
                "                </valores>\n" +
                "                <numPregunta>14</numPregunta>\n" +
                "                <numSeccion>6</numSeccion>\n" +
                "            </respuestas>\n" +
                "            <respuestas>\n" +
                "                <cvePregunta>83</cvePregunta>\n" +
                "                <valores>\n" +
                "                    <clave>184</clave>\n" +
                "                    <descripcion>NO</descripcion>\n" +
                "                    <valor>0</valor>\n" +
                "                    <habilitarDependencia>false</habilitarDependencia>\n" +
                "                </valores>\n" +
                "                <numPregunta>15</numPregunta>\n" +
                "                <numSeccion>6</numSeccion>\n" +
                "            </respuestas>\n" +
                "            <respuestas>\n" +
                "                <cvePregunta>84</cvePregunta>\n" +
                "                <valores>\n" +
                "                    <clave>186</clave>\n" +
                "                    <descripcion>NO</descripcion>\n" +
                "                    <valor>0</valor>\n" +
                "                    <habilitarDependencia>false</habilitarDependencia>\n" +
                "                </valores>\n" +
                "                <numPregunta>16</numPregunta>\n" +
                "                <numSeccion>6</numSeccion>\n" +
                "            </respuestas>\n" +
                "            <respuestas>\n" +
                "                <cvePregunta>85</cvePregunta>\n" +
                "                <valores>\n" +
                "                    <clave>188</clave>\n" +
                "                    <descripcion>NO</descripcion>\n" +
                "                    <valor>0</valor>\n" +

                "                    <habilitarDependencia>false</habilitarDependencia>\n" +
                "                </valores>\n" +
                "                <numPregunta>17</numPregunta>\n" +
                "                <numSeccion>6</numSeccion>\n" +
                "            </respuestas>\n" +
                "            <respuestas>\n" +
                "                <cvePregunta>86</cvePregunta>\n" +
                "                <valores>\n" +
                "                    <clave>190</clave>\n" +
                "                    <descripcion>NO</descripcion>\n" +
                "                    <valor>0</valor>\n" +
                "                    <habilitarDependencia>false</habilitarDependencia>\n" +
                "                </valores>\n" +
                "                <numPregunta>18</numPregunta>\n" +
                "                <numSeccion>6</numSeccion>\n" +
                "            </respuestas>\n" +
                "            <respuestas>\n" +
                "                <cvePregunta>87</cvePregunta>\n" +
                "                <valores>\n" +
                "                    <clave>192</clave>\n" +
                "                    <descripcion>NO</descripcion>\n" +
                "                    <valor>0</valor>\n" +
                "                    <habilitarDependencia>false</habilitarDependencia>\n" +
                "                </valores>\n" +
                "                <numPregunta>19</numPregunta>\n" +
                "                <numSeccion>6</numSeccion>\n" +
                "            </respuestas>\n" +
                "            <sumatoriaRespuestas>0</sumatoriaRespuestas>\n" +
                "        </respuestasCuestionario>\n" +
                "        <cuestionarioValido>true</cuestionarioValido>\n" +
                "        <nssPersona>12814202177</nssPersona>\n" +
                "    </cuetionarios>\n" +
                "    <aplicaCuestionario>true</aplicaCuestionario>\n" +
                "    <desdeExtranjero>false</desdeExtranjero>\n" +
                "    <soloSolicitante>false</soloSolicitante>\n" +
                "    <registroPatronal>\n" +
                "        <centrotrabajo>\n" +
                "            <numExterior1>78</numExterior1>\n" +
                "            <numExterior2>0</numExterior2>\n" +
                "            <numInterior>0</numInterior>\n" +
                "            <codigoPostal>06600</codigoPostal>\n" +
                "            <asentamiento>\n" +
                "                <clave>047293</clave>\n" +
                "                <nombre>Ju�rez</nombre>\n" +
                "                <localidad>\n" +
                "                    <clave>0001</clave>\n" +
                "                    <nombre>CUAUHT�MOC</nombre>\n" +
                "                    <municipio>\n" +
                "                        <clave>015</clave>\n" +
                "                        <nombre>CUAUHT�MOC</nombre>\n" +
                "                        <entidadFederativa>\n" +
                "                            <clave>09</clave>\n" +
                "                            <nombre>DISTRITO FEDERAL</nombre>\n" +
                "                        </entidadFederativa>\n" +
                "                    </municipio>\n" +
                "                </localidad>\n" +
                "                <municipio>\n" +
                "                    <clave>015</clave>\n" +
                "                    <nombre>CUAUHT�MOC</nombre>\n" +
                "                    <entidadFederativa>\n" +
                "                        <clave>09</clave>\n" +
                "                        <nombre>DISTRITO FEDERAL</nombre>\n" +
                "                    </entidadFederativa>\n" +
                "                </municipio>\n" +
                "                <codigoPostal>06600</codigoPostal>\n" +
                "                <periodo>0</periodo>\n" +
                "            </asentamiento>\n" +
                "            <vialidadPrimaria>\n" +
                "                <clave>289951</clave>\n" +
                "                <nombre>HAMBURGO</nombre>\n" +
                "                <tipoVialidad>\n" +
                "                    <clave>8</clave>\n" +
                "                    <descripcion>CERRADA</descripcion>\n" +
                "                </tipoVialidad>\n" +
                "            </vialidadPrimaria>\n" +
                "        </centrotrabajo>\n" +
                "    </registroPatronal>\n" +
                "</ns7:tramiteSeguroIvro>\n";
        Tramite xmlActual = (Tramite)mx.gob.imss.distss.digital.jaxb.util.JaxbUtil.xmlToObject(xml);
        System.out.println("xmlActual = " + xmlActual);
    }

    @Test
    public void testActualizaTramitesSolicitud() throws Exception {
        Solicitud solicitud = new Solicitud();
        //solicitud.setSolicitudId(40201209L);
        solicitud.setSolicitudId(40200962L);
        solicitud = EjbLocator.find(SolicitudBusinessRemote.class, Ambiente.LOCAL).consultar(solicitud);
        Solicitud solicitud1 = EjbLocator.find(SolicitudBusinessRemote.class, Ambiente.LOCAL).actualizarTramites(solicitud);
        System.out.println("solicitud1 = " + solicitud1);
    }

    @Test
    public void testDetalleDerechohabienteGrupoFamiliar() throws Exception {
        GrupoFamiliar grupoFamiliar = EjbLocator.find(DerechohabienteServiceRemote.class, Ambiente.LOCAL).detalleDerechohabienteGrupoFamiliar(62913978L, 61578365L);

        System.out.println("grupoFamiliar = " + grupoFamiliar);

    }

    @Test
    public void testConsultarDomicilio() throws Exception {
        mx.gob.imss.ctirss.delta.model.domicilio.Domicilio domicilio =  new mx.gob.imss.ctirss.delta.model.domicilio.Domicilio();

        domicilio.setClave(527568);

        mx.gob.imss.ctirss.delta.model.domicilio.Domicilio domicilio1 = EjbLocator.find(DomicilioServiceBusinessRemote.class, Ambiente.STAGE).consultarDomicilio(domicilio);

        System.out.println("domicilio1 = " + domicilio1);

    }

    @Test
    public void testObtenerSujetosObligadosParaBeneficio() throws Exception {
        List<SujetoObligado> list = EjbLocator.find(BeneficioRissServiceBusinessRemote.class, Ambiente.LOCAL).obtenerSujetosObligadosParaBeneficio("AARJ5801196K6");
        System.out.println("list = " + list);
    }

    @Test
    public void testGenerarTramaAltaPatronal(){
        List<String> folios = new ArrayList<String>();

        folios.add("148651746318791855403");


        Solicitud solicitud = null;
        TramiteSujetoObligado tramiteSujetoObligado = null;
        String xml = null;
        SujetoObligado sujetoObligado = null;

        for (int i = 0; i < folios.size(); i++) {
            solicitud = new Solicitud();
            solicitud.setNoFolioSolicitud(folios.get(i));
            try {
                solicitud = EjbLocator.find(SolicitudBusinessRemote.class, Ambiente.LOCAL).consultarFolio(solicitud);

                for (mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite tramite : solicitud.getTramites()) {
                    if (tramite instanceof TramiteSujetoObligado) {
                        tramiteSujetoObligado = (TramiteSujetoObligado) tramite;
                        System.out.println("Se encontro tramiteAsegurado con id -> "
                                + tramiteSujetoObligado.getTramiteId());

                        sujetoObligado = tramiteSujetoObligado.getSujetoObligado();
                    }
                }

                EjbLocator.find(ConcluirAltaPatronalBusinessRemote.class, Ambiente.LOCAL).concluirAltaPatronal(sujetoObligado.getNumeroRegistroPatronal(), solicitud);

                System.out.println("=============Movimiento=============");
                System.out.println(xml);

            } catch (SolicitudNoEncontradaException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Test
    public void buscarPersonaFisicaPorCurpEnRenapo() throws ClienteWebserviceRenapoCurpException {
        Fisica fisica = EjbLocator.find(PersonaBusinessRemote.class, Ambiente.LOCAL).buscarPersonaFisicaPorCurpEnRenapo("AIVB710613MDFVGR08");
        System.out.println("fisica = " + fisica.getNombreCompleto());
    }

    @Test
    public void testEncolarSolicitudAConcluir(){
        EjbLocator.find(SolicitudQueueProducerRemote.class, Ambiente.LOCAL).encolarSolicitudAConcluir("148850239435995869439");
    }

    @Test
    public void testObtenerAsignacionNss() throws Exception {
        AsignacionNssIvro asignacionNssIvro = EjbLocator.find(VigenciaIvroServiceRemote.class, Ambiente.LOCAL).obtenerAsignacionNss("31058810735");

        System.out.println("asignacionNssIvro = " + asignacionNssIvro);

    }

    @Test
    public void testAgendarCorreoElectronico() throws IvroException, JAXBException, UnsupportedEncodingException {

        String xml = "<mx:EmailPayload xmlns:mx=\"http://mx.gob.imss.email.model\">\n" +
                "    <mx:To>eduardo.serrano@softtek.com,luisg.gonzalez@softtek.com</mx:To>\n" +
                "    <mx:Subject>Aviso Continuación Voluntaria CVRO</mx:Subject>\n" +
                "    <mx:Content></mx:Content>\n" +
                "    <mx:ContentType>text/html</mx:ContentType>\n" +
                "    <mx:Parameters>\n" +
                "        <mx:entry>\n" +
                "            <mx:key>fechaOperacion</mx:key>\n" +
                "            <mx:value>20/04/2017</mx:value>\n" +
                "        </mx:entry>\n" +
                "        <mx:entry>\n" +
                "            <mx:key>idTipoTramite</mx:key>\n" +
                "            <mx:value>124</mx:value>\n" +
                "        </mx:entry>\n" +
                "        <mx:entry>\n" +
                "            <mx:key>tipoPago</mx:key>\n" +
                "            <mx:value>Mensual Anticipado</mx:value>\n" +
                "        </mx:entry>\n" +
                "        <mx:entry>\n" +
                "            <mx:key>umf</mx:key>\n" +
                "            <mx:value>0</mx:value>\n" +
                "        </mx:entry>\n" +
                "        <mx:entry>\n" +
                "            <mx:key>fechaFinVigencia</mx:key>\n" +
                "            <mx:value>30/04/2017</mx:value>\n" +
                "        </mx:entry>\n" +
                "        <mx:entry>\n" +
                "            <mx:key>nss</mx:key>\n" +
                "            <mx:value>30998128885</mx:value>\n" +
                "        </mx:entry>\n" +
                "        <mx:entry>\n" +
                "            <mx:key>tipoOperacion</mx:key>\n" +
                "            <mx:value>90</mx:value>\n" +
                "        </mx:entry>\n" +
                "        <mx:entry>\n" +
                "            <mx:key>nombreCompleto</mx:key>\n" +
                "            <mx:value>CARLOS FRANCISCO RODRIGUEZ SANCHEZ</mx:value>\n" +
                "        </mx:entry>\n" +
                "        <mx:entry>\n" +
                "            <mx:key>curp</mx:key>\n" +
                "            <mx:value>ROSC811017HDFDNR09</mx:value>\n" +
                "        </mx:entry>\n" +
                "        <mx:entry>\n" +
                "            <mx:key>modalidadAseguramiento</mx:key>\n" +
                "            <mx:value>40 - CONTINUACIÓN VOLUNTARIA EN EL RÉGIMEN OBLIGATORIO</mx:value>\n" +
                "        </mx:entry>\n" +
                "        <mx:entry>\n" +
                "            <mx:key>fechaInicioVigencia</mx:key>\n" +
                "            <mx:value>01/04/2017</mx:value>\n" +
                "        </mx:entry>\n" +
                "        <mx:entry>\n" +
                "            <mx:key>cantidadPagada</mx:key>\n" +
                "            <mx:value>633.06</mx:value>\n" +
                "        </mx:entry>\n" +
                "    </mx:Parameters>\n" +
                "    <mx:Extraparameters/>\n" +
                "</mx:EmailPayload>";

        EmailPayloadType emailRequest = JaxbUtilT.obtainObjectFromXml(EmailPayloadType.class, xml);


        EjbLocator.find(EMailProducer.class, Ambiente.STAGE).agendarCorreoElectronico(emailRequest);
    }

    @Test
    public void testConsultarUltimoDomicilioParticilar() throws IvroException, JAXBException, UnsupportedEncodingException, DomicilioNoLocalizadoException, MunicipioImssNoLocalizadoException {

        Domicilio domicilio = EjbLocator.find(DomicilioServiceBussinessExternosRemote.class, Ambiente.LOCAL).consultarUltimoDomicilioParticilar(34824L);
        System.out.println("domicilio = " + domicilio);
    }

    /**
     *
     * @throws GestionPatronalBusinessException
     */
    @Test
    public void testObtenerNrpConvencionalPorDomicilioYModalidad() throws GestionPatronalBusinessException, DomicilioNoLocalizadoException, MunicipioImssNoLocalizadoException, SolicitudNoEncontradaException {
        String modalidad = "40";

        //String s = EjbLocator.find(RegistroPatronalServiceBusinessRemote.class, Ambiente.STAGE).obtenerNrpConvencionalPorDomicilioYModalidad("050", "31", "97070", modalidad);
        String s = EjbLocator.find(RegistroPatronalServiceBusinessRemote.class, Ambiente.STAGE).obtenerNrpConvencionalPorDomicilioYModalidad("039", "14", "44450", modalidad);

        System.out.println("s = " + s);

    }

    @Test
    public void testGetUmfByCodigoPostal() throws UmfNoLocalizadaException {
        List<UnidadMedicaFamiliar> umfByCodigoPostal = EjbLocator.find(DomicilioServiceBusinessRemote.class, Ambiente.STAGE).getUmfByCodigoPostal("06600");
        System.out.println("umfByCodigoPostal = " + umfByCodigoPostal);
    }

    //SuaServiceRemote
   @Test
    public void testGeneraDatosSua() throws UmfNoLocalizadaException, SUAException {
       Cotizacion cotizacion = EjbLocator.find(CotizacionServiceRemote.class, Ambiente.STAGE).findCotizacion(76367L);

       SUAPago[] suaPagos = EjbLocator.find(SuaServiceRemote.class, Ambiente.STAGE).generaDatosSua(cotizacion.getDetalle(), null);
       System.out.println("suaPagos = " + suaPagos);
    }

}
