package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ValidaIncorporarRissIvroRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ValidaVigenciaRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.test.Ambiente;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.test.EjbLocator;
import mx.gob.imss.digital.modelo.satRiss.DatosRiss;
import mx.gob.imss.digital.modelo.sindo.ModalidadTrabajador;

import mx.gob.imss.digital.modelo.sindo.RespuestaValidacionTrabajador;
import mx.gob.imss.digital.modelo.sindo.ResultadoVigenciaTrabajdor;
import mx.gob.imss.digital.modelo.sindo.VigenciaTrabajdor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Modalidad35Test {

    private static final Logger LOGGER;

//	Declaracion de EJB's
//    private ValidaVigenciaRemote validaVigenciaRemote;
////    private ValidaIncorporarRissIvroRemote validaIncorporarRissIvroRemote;
//
    static {
        LOGGER = LoggerFactory.getLogger(Modalidad35Test.class);
    }
//
////    @Before
////    public void init() {
////        validaVigenciaRemote = EjbLocator.getValidaVigenciaRemote();
////        validaIncorporarRissIvroRemote = EjbLocator.getValidaIncorporarRissIvroRemote();
////        
////    }
//
//    /**
//     * Prueba que se encarga de verificar que el solicitante realiza renovacion oportuna como
//     * maximo el 25 de cada mes o posterior al dia 25 del mes
//     */
//    @Test
//    public void renovacionOportunaFechaTest() {
//
//        Calendar fechaActual = Calendar.getInstance();
//        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
//        LOGGER.debug("La Fecha Actual con el formato [dd/MM/yyyy] es: " + fechaActual.getTime());
//        String fechaSisActual = formato.format(fechaActual.getTime());
//        LOGGER.debug("La fecha es: " + fechaSisActual);
//        String fechaRO = "26/05/2017";//fecha que se debe de setear
//        LOGGER.debug("La fecha ingresada es:"+fechaRO);
//        String renovacioOp = "";
//        boolean fechaPasable = false;
//
//        try {
//            Date fechaDate1 = formato.parse(fechaRO);
//            Date fechaDate2 = formato.parse(fechaSisActual);
//            if (fechaDate1.before(fechaDate2)||fechaDate1.equals(fechaDate2)) {
//                fechaPasable=true;
//                renovacioOp = "La Fecha [" + fechaRO + "] es anterior al dia 25 del mes o esta en el limite";
//                LOGGER.debug("La fecha ingresada es: "+renovacioOp);
//                Assert.assertTrue(fechaPasable);
//            }else{
//                fechaPasable = false;
//                Assert.assertFalse(fechaPasable);
//                LOGGER.debug("Es posterior al dia 25 del mes");
//            }
//        } catch (ParseException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        Assert.assertNotNull(renovacioOp);
//        LOGGER.debug("El dato de la fecha no es nulo");
//        
//    }
//    
//    
//    /**
//     * Prueba que se encarga de validar modalidad en la que se encuentra vigente el solicitante
//     */
//    @Test
//    public void validaVigenciaModalidadTest() {
//
//        VigenciaTrabajdor vigenciaTrabajdor = new VigenciaTrabajdor();
//        String modalidades[] = {"10", "13", "14", "17", "30", "31", "32", "33", "34", "35", "36", "38", "40", "42"};
////        ModalidadTrabajador mt = new ModalidadTrabajador();
////        ModalidadTrabajador[] modalidadeFechaBaja = new ModalidadTrabajador[1];
////        ModalidadTrabajador modalidadesFechaVigente[];
//
//        vigenciaTrabajdor.setClaveError("0");
//        vigenciaTrabajdor.setMensajeError("Consulta Exitosa");
//        vigenciaTrabajdor.setResultado(new ResultadoVigenciaTrabajdor());
//        vigenciaTrabajdor.setModalidadSolicitada("10");
//        vigenciaTrabajdor.getResultado().setIndicadorVigente(true);
////        vigenciaTrabajdor.getResultado().setModalidadesFechaBaja(modalidadesFechaBaja);
////        vigenciaTrabajdor.getResultado().setModalidadesFechaVigente(modalidadesFechaVigente);
//        vigenciaTrabajdor.getResultado().getNrpBaja();
//        vigenciaTrabajdor.getResultado().setNumeroSemanaAseguramientoBaja(8);
//        vigenciaTrabajdor.getResultado().setTipoAseguradoBaja(10);
//        for (int i = 0; i < modalidades.length; i++) {
//            if (modalidades[i] == vigenciaTrabajdor.getModalidadSolicitada()) {
//                LOGGER.debug("El solicitante esta vigente en la modalidad " + modalidades[i]);
//            }
//        }
//        RespuestaValidacionTrabajador vigenciaEnMod = EjbLocator.find(ValidaVigenciaRemote.class, Ambiente.STAGE).validaVigenciaTrabajador(vigenciaTrabajdor);
//        Assert.assertNotNull(vigenciaEnMod);
//    }
//    
//    @Test
//    public void validaVigenciaIVROCurpRISSTest(){
//        
//        VigenciaTrabajdor vigenciaT = new VigenciaTrabajdor();
//        ModalidadTrabajador [] modalidadesFechaBaja = {};
//        ModalidadTrabajador [] modalidadesFechaVigente = {};
//        
//        vigenciaT.setClaveError("0");
//        vigenciaT.setMensajeError("Consulta Exitosa");
//        vigenciaT.setModalidadSolicitada("");
//        vigenciaT.setResultado(new ResultadoVigenciaTrabajdor());
//        vigenciaT.getResultado().setIndicadorVigente(true);
//        vigenciaT.getResultado().setModalidadesFechaBaja(modalidadesFechaBaja);
//        vigenciaT.getResultado().setModalidadesFechaVigente(modalidadesFechaVigente);
//        vigenciaT.getResultado().setNrpBaja("");
//        vigenciaT.getResultado().setNumeroSemanaAseguramientoBaja(Integer.MIN_VALUE);
//        vigenciaT.getResultado().setTipoAseguradoBaja(Integer.MIN_VALUE);
//        
////        RespuestaValidacionTrabajador vigenciaCurpRISS = validaVigenciaRemote.validaVigenciaTrabajador(vigenciaT);
////        Assert.assertNotNull(vigenciaCurpRISS);
////        LOGGER.debug("Respuesta Validacion Trabajador" + vigenciaCurpRISS.getMensajeValidacion());
////        boolean resVT = vigenciaCurpRISS.getValido();
////        Assert.assertTrue(resVT);
//                
//    }
//    
    @Test
    public void validaIncorporacionRISSTest(){
        
        DatosRiss riss = new DatosRiss();
        riss.setIdPersona(36949310L);
        riss.setRfc("MOGM7102106H0");
        riss.setNss("42937113746");
        riss.setIdOrigenSolicitud(Long.MIN_VALUE);
        riss.setUsuario("");
        
        DatosRiss incorRiss = EjbLocator.find(ValidaIncorporarRissIvroRemote.class, Ambiente.STAGE).validaIncorporacionBeneficioRiss(riss);
        Assert.assertNotNull(incorRiss);
        LOGGER.debug("Incorporacion RISS");
        
    }

}
