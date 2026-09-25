package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.business;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.EstadoPagoEnum;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.exception.IvroException;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ConsultaSeguroIvroServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.SeguroIvroServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.test.Ambiente;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.test.EjbLocator;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.test.JaxbUtilT;
import mx.gob.imss.digital.modelo.cobranza.ActualizacionCompra;
import mx.gob.imss.digital.modelo.cobranza.Compra;
import mx.gob.imss.digital.modelo.cobranza.DatosCompra;
import mx.gob.imss.digital.modelo.cobranza.Pago;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;
import mx.gob.imss.digital.modelo.sindo.MovimientoTrabajadorSindo;
import mx.gob.imss.digital.modelo.sindo.MovimientosTrabajadorSindo;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.NamingException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by eduardo.serrano on 17/05/2017.
 */
public class SeguroIvroServiceBusinessTest {

    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(SeguroIvroServiceBusinessTest.class);
    }

    @Test
    public void testGenerarMovimientoBaja() {
        try {

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            SortedMap<Long, List<Date>> segs = new TreeMap<Long, List<Date>>();

            segs.put(104546L, Arrays.asList(new Date[]{formatter.parse("31/05/2017")/*fRecepMovi*/,formatter.parse("31/05/2017")/*fMovto*/}));

            int contador = 0;
            SeguroIvro seguro;
            for (Map.Entry<Long, List<Date>> entry : segs.entrySet()) {
                contador++;
                seguro = new SeguroIvro();
                seguro.setCveIdSeguroIvro(entry.getKey());

                seguro = EjbLocator.find(ConsultaSeguroIvroServiceRemote.class, Ambiente.STAGE).buscaSeguro(seguro);

                Compra compra = seguro.getCompra();
                Pago[] pagos = compra.getPagos();

                List<Pago> listPagos = Arrays.asList(pagos);
                List<Pago> listPagosOrdenados = ordenarListaPagos(listPagos);

                Pago pagoReferencia = null;
                for (Pago pagoRev : listPagosOrdenados) {
                    if (pagoRev.getEstadoPago().getIdEstadoPago() == EstadoPagoEnum.PAGADO.getId()) {
                        pagoReferencia = pagoRev;
                        break;
                    }
                }

                DatosCompra datosCompra = new DatosCompra();
                datosCompra.setIdCompra(seguro.getCompra().getIdCompra());
                List<DatosCompra> lstDatosCompra = new ArrayList<DatosCompra>();
                lstDatosCompra.add(datosCompra);

                // Generacion de movimientos
                ActualizacionCompra comprasVencidas = new ActualizacionCompra();
                DatosCompra[] arrDatosCompra = lstDatosCompra.toArray(new DatosCompra[lstDatosCompra.size()]);
                comprasVencidas.setCompras(arrDatosCompra);
                MovimientoTrabajadorSindo[] arrMovBaja = EjbLocator.find(SeguroIvroServiceRemote.class, Ambiente.LOCAL).generaMovimientosBaja(comprasVencidas);

                // JaxB
                JAXBContext jaxbContext = JAXBContext.newInstance(new Class[]{MovimientoTrabajadorSindo.class});
                final Marshaller marshaller = jaxbContext.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                final Writer writer = new StringWriter();

                if (arrMovBaja.length > 0) {
                    MovimientoTrabajadorSindo movimiento = arrMovBaja[0];

                    movimiento.setfMovto(entry.getValue().get(0) != null?entry.getValue().get(0):pagoReferencia.getFechaFinPeriodo());
                    movimiento.setfRecepMovi(entry.getValue().get(1) != null?entry.getValue().get(1):pagoReferencia.getFechaFinPeriodo());

                    //movimiento.setfMovto(formatter.parse("30/04/2017")/*fMovto*/);
                    //movimiento.setfRecepMovi(formatter.parse("30/04/2017")/*fRecepMovi*/);

                    marshaller.marshal(movimiento, writer);
                    String xml = writer.toString();
                    xml = xml.substring(xml.indexOf("\n")+1);
                    System.out.println("<!-- " + contador + " - " + seguro.getCveIdSeguroIvro() + " -->");
                    System.out.println(xml);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGenerarMovimientoAlta() {

        try {

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            SortedMap<Long, List<Date>> segs = new TreeMap<Long, List<Date>>();

            segs.put(104922L, Arrays.asList(new Date[]{formatter.parse("01/05/2017")/*fRecepMovi*/,formatter.parse("01/05/2017")/*fMovto*/}));

            int contador = 0;

            for (Map.Entry<Long, List<Date>> entry : segs.entrySet()) {

                List<SeguroIvro> lstSeguros = new ArrayList<SeguroIvro>();


                SeguroIvro seg = new SeguroIvro();
                seg.setCveIdSeguroIvro(entry.getKey());

                lstSeguros.add(seg);

                // Se determina que seguro aplica baja
                List<SeguroIvro> lstSegurosActivos = new ArrayList<SeguroIvro>();
                for(SeguroIvro seguro: lstSeguros){
                    SeguroIvro seguroActivo = EjbLocator.find(ConsultaSeguroIvroServiceRemote.class, Ambiente.STAGE).buscaSeguro(seguro);
                    lstSegurosActivos.add(seguroActivo);
                }

                for (SeguroIvro seguro : lstSegurosActivos) {
                    Compra compra = seguro.getCompra();
                    Pago[] pagos = compra.getPagos();

                    List<Pago> listPagos = Arrays.asList(pagos);
                    List<Pago> listPagosOrdenados = ordenarListaPagos(listPagos);

                    Pago pagoReferencia = null;
                    for (Pago pagoRev : listPagosOrdenados) {
                        if (pagoRev.getEstadoPago().getIdEstadoPago() == EstadoPagoEnum.PAGADO.getId()) {
                            pagoReferencia = pagoRev;
                            break;
                        }
                    }

                    DatosCompra datosCompra = new DatosCompra();
                    datosCompra.setIdCompra(seguro.getCompra().getIdCompra());
                    List<DatosCompra> lstDatosCompra = new ArrayList<DatosCompra>();
                    lstDatosCompra.add(datosCompra);

                    // Generacion de movimientos
                    ActualizacionCompra comprasPagadas = new ActualizacionCompra();
                    DatosCompra[] arrDatosCompra = lstDatosCompra.toArray(new DatosCompra[lstDatosCompra.size()]);
                    comprasPagadas.setCompras(arrDatosCompra);
                    MovimientosTrabajadorSindo movAlta = EjbLocator.find(SeguroIvroServiceRemote.class, Ambiente.STAGE).generaMovimientosAlta(comprasPagadas);

                    //if (pagoReferencia != null && pagoReferencia.getFechaInicioPeriodo() != null) {
                    MovimientoTrabajadorSindo[] listMovimientoTrabajador = movAlta.getMovimientoTrabajadorSindo();

                    if (listMovimientoTrabajador.length > 0) {
                        MovimientoTrabajadorSindo movimiento = listMovimientoTrabajador[0];
                        Date fechaReferencia = new Date();//pagoReferencia.getFechaInicioPeriodo();
                        Calendar calFechaRef = Calendar.getInstance();
                        calFechaRef.setTime((Date) fechaReferencia.clone());

                        movimiento.setfRecepMovi(entry.getValue().get(0));
                        movimiento.setfMovto(entry.getValue().get(1));
                    }
                    //}

                    // JaxB
                    JAXBContext jaxbContext = JAXBContext.newInstance(new Class[]{MovimientoTrabajadorSindo.class, MovimientosTrabajadorSindo.class});
                    final Marshaller marshaller = jaxbContext.createMarshaller();
                    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                    final Writer writer = new StringWriter();

                    marshaller.marshal(movAlta, writer);
                    String xml = writer.toString();
                    contador = contador + 1;
                    System.out.println("<!-- " + contador + " - " + seguro.getCveIdSeguroIvro() + " -->");
                    System.out.println(xml);
                }

            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Pago> ordenarListaPagos(List<Pago> pagosSeguroIvro) {
        Collections.sort(pagosSeguroIvro, new Comparator<Pago>() {
            @Override
            public int compare(Pago pago2, Pago pago1) {
                return pago1.getFechaLimitePago().compareTo(
                        pago2.getFechaLimitePago());
            }
        });

        return pagosSeguroIvro;
    }

    @Test
    public void testValidarGeneracionNuevoPeriodoPagoCvroTest() {
        SeguroIvroServiceRemote ejb = EjbLocator.find(SeguroIvroServiceRemote.class, Ambiente.LOCAL);

        long idSeguro = 46592L;


        Pago pago =	ejb.validarGeneracionNuevoPeriodoPagoCvro(idSeguro);

        System.out.println("Pago -> " + pago.getErrorFormGeneral());

    }

    @Test
    public void testConcluirSeguros() {
        try {
            MovimientoTrabajadorSindo[] movimientosConclusionSeguro = EjbLocator.find(SeguroIvroServiceRemote.class, Ambiente.LOCAL).vencimientoSeguroVigencia();

            // JaxB
            JAXBContext jaxbContext = JAXBContext.newInstance(new Class[] { MovimientoTrabajadorSindo.class });
            final Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            final Writer writer = new StringWriter();

            if (movimientosConclusionSeguro.length > 0) {
                int contador = 0;
                for (MovimientoTrabajadorSindo movimiento : movimientosConclusionSeguro) {
                    marshaller.marshal(movimiento, writer);
                    String xml = writer.toString();
                    contador = contador + 1;
                    System.out.println("<!-- " + contador + " - " + movimiento.getNumSegSoc()+movimiento.getDigVrNss() + " -->");
                    System.out.println(xml);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConcluirSeguroVigencia() {
        try {
            SeguroIvro[] seguroIvros = EjbLocator.find(SeguroIvroServiceRemote.class, Ambiente.LOCAL).concluirSeguroVigencia();

            System.out.println("seguroIvros = " + seguroIvros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testActivaSeguro() throws NamingException, IvroException, JAXBException, UnsupportedEncodingException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<ns15:actualizacionCompras xmlns:ns29=\"http://mx.gob.imss.delta.global.service/\" xmlns:ns25=\"http://www.mx.gob.imss.ctirss.delta/movimientoasignacion\" xmlns:ns26=\"http://delta.ctirss.imss.gob.mx/SIME\" xmlns:ns27=\"http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado\" xmlns:ns28=\"http://www.mx.gob.imss.distss.derechohabientes.adimss/WSConsultaAdimssService/\" xmlns:ns21=\"http://www.mx.gob.imss.ctirss.delta/trabajadoresImssInfonavit\" xmlns:ns22=\"http://mx.gob.imss.ctirss.delta/movimientosua\" xmlns:ns23=\"http://www.mx.gob.imss.ctirss.delta/movimientoRiss\" xmlns:ns24=\"http://www.mx.gob.imss.ctirss.delta/integracion_reingreso\" xmlns:ns20=\"http://mx.gob.imss.delta.global.services/\" xmlns:ns16=\"http://mx.gob.imss.digital.modelo.cuestionario\" xmlns:ns17=\"http://www.sat.gob.mx/cfd/3\" xmlns:ns14=\"mx.gob.imss.digital.modelo.derechohabiente\" xmlns:ns15=\"http://mx.gob.imss.digital.modelo.cobranza\" xmlns:ns18=\"http://www.sat.gob.mx/TimbreFiscalDigital\" xmlns:ns19=\"http://www.mx.gob.imss.ctirss.delta.cobranza\" xmlns:ns9=\"http://mx.gob.imss.digital.modelo.persona\" xmlns:ns30=\"http://mx.gob.imss.digital.modelo.seguros\" xmlns:ns12=\"http://mx.gob.imss.digital.modelo.comun\" xmlns:ns5=\"http://www.mx.gob.imss.ctirss.delta/movimiento06Sindo\" xmlns:ns31=\"http://mx.gob.imss.digital.modelo.solicitud\" xmlns:ns13=\"http://mx.gob.imss.digital.modelo.patron\" xmlns:ns6=\"http://www.mx.gob.imss.ctirss.delta/movtoPatSujetoObligado\" xmlns:ns10=\"http://mx.gob.imss.digital.modelo.domicilio\" xmlns:ns7=\"http://mx.gob.imss.digital.modelo.tramite\" xmlns:ns11=\"http://mx.gob.imss.digital.modelo.medio.contacto\" xmlns:ns8=\"http://mx.gob.imss.digital.modelo.documento.probatorio\" xmlns:ns2=\"http://mx.gob.imss.email.model\" xmlns:ns4=\"http://www.mx.gob.imss.ctirss.delta.model.derechohabiente.negocio/tramiteDerechohabiente\" xmlns:ns3=\"modelo\">\n" +
                "    <compras>\n" +
                "        <idCompra>53030</idCompra>\n" +
                "        <bimestral>false</bimestral>\n" +
                "        <primerPago>true</primerPago>\n" +
                "    </compras>\n" +
                "</ns15:actualizacionCompras>";

        MovimientosTrabajadorSindo movimientosTrabajadorSindo = EjbLocator.find(SeguroIvroServiceRemote.class, Ambiente.LOCAL).activaSeguro(JaxbUtilT.obtainObjectFromXml(ActualizacionCompra.class, xml));
        System.out.println("movimientosTrabajadorSindo:"+movimientosTrabajadorSindo);

    }
}
