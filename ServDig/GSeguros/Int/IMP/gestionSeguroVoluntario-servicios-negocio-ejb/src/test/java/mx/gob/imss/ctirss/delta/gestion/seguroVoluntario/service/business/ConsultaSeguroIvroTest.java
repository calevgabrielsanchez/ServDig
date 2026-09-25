package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.business;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.test.EjbLocator;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ConsultaSeguroIvroServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ComprobanteSeguroRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.test.EjbLocator;
import mx.gob.imss.digital.modelo.persona.Persona;
import mx.gob.imss.digital.modelo.seguros.SegurosIvro;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;
import mx.gob.imss.digital.modelo.seguros.ComprobantesSeguroReporte;
import mx.gob.imss.digital.modelo.seguros.DocumentoSeguro;
import org.junit.Assert;
import org.junit.Test;

public class ConsultaSeguroIvroTest {

//    private transient ConsultaSeguroIvroServiceRemote consultaSeguroIvroService = EjbLocator.getConsultaSeguroIvroServiceRemote();
//    private transient ComprobanteSeguroRemote comprobanteDatos = EjbLocator.getComprobanteSeguroRemote();

//    @Test
//    public void testGEneraComprobante() {
//        Persona persona = new Persona();
//        persona.setIdPersona(50652084L);
//        SegurosIvro seguros = consultaSeguroIvroService.buscaSegurosCVRO(persona);
//        System.out.println("Prueba1:>>>" + seguros.getSeguroIvro()[0].getCveIdSeguroIvro());
//        System.out.println("Prueba2:>>>" + String.valueOf(seguros.getSeguroIvro().length));
//        DocumentoSeguro generaComprobantes = comprobanteDatos.generaComprobantes(seguros);
//        System.out.println("Prueba2:>>>" + String.valueOf(generaComprobantes.getListComprobanteSeguro()));
//
//    }

//	@Test
//    public void testGeneraComprobanteDomicilio() {
//        SegurosIvro seguros = new SegurosIvro();
//        SeguroIvro seguroIvro = new SeguroIvro();
//        seguroIvro.setCveIdSeguroIvro(92950L);
//        SeguroIvro[] listaSeguros = new SeguroIvro[1];
//        listaSeguros[0] = seguroIvro;
//
//        seguros.setSeguroIvro(listaSeguros);
//        ComprobantesSeguroReporte comprobanteSeguroReporte = comprobanteDatos.generaDatosComprobante(seguros);
//        System.out.println("Prueba1:>>>" + comprobanteSeguroReporte.getComprobante()[0].getDomicilio());
//    }
//
//    @Test
//    public void generaComprobanteSeguro() {
//
//        Persona persona = new Persona();
//        persona.setIdPersona(50652084L);
//        SegurosIvro seguros = consultaSeguroIvroService.buscaSegurosCVRO(persona);
//        mx.gob.imss.digital.modelo.seguros.SeguroIvro segAnalizar = null;
//        for (mx.gob.imss.digital.modelo.seguros.SeguroIvro seg : seguros.getSeguroIvro()) {
//            if (seg.getCveIdSeguroIvro().longValue() == 93584l) {
//                segAnalizar = seg;
//            }
//        }
//
//        mx.gob.imss.digital.modelo.seguros.SeguroIvro[] segurosIvro = new mx.gob.imss.digital.modelo.seguros.SeguroIvro[]{segAnalizar};
//        seguros.setSeguroIvro(segurosIvro);
//        byte[] file = comprobanteDatos.generaComprobantes(seguros).getArchivo();
//        FileOutputStream fos;
//        try {
//            fos = new FileOutputStream("/home/softtekop/comprobanteIvro.pdf");
//            fos.write(file);
//            fos.close();
//            Assert.assertNotNull(file);
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//    }
}
