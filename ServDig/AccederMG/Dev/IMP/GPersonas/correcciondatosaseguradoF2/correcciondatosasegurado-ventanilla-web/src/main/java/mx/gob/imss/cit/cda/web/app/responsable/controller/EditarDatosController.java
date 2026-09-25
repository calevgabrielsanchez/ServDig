package mx.gob.imss.cit.cda.web.app.responsable.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.service.interfaces.CorreccionDatosRemote;
import mx.gob.imss.cit.cda.web.app.common.controller.AbstractReadController;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.constants.RequestMappingConstants;
import mx.gob.imss.cit.cda.web.app.responsable.model.CorreccionDatos;
import mx.gob.imss.cit.cda.web.app.responsable.model.DatosAGuradarNSS;
import mx.gob.imss.cit.cda.web.app.responsable.model.DatosNSSBD;
import mx.gob.imss.cit.cda.web.bandeja.vo.RequestSolicitudBandejaPage;
import mx.gob.imss.cit.cda.web.bandeja.vo.SolicitudBandeja;
import mx.gob.imss.cit.cda.web.support.model.Page;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EditarDatosController extends AbstractReadController<RequestSolicitudBandejaPage, Page<SolicitudBandeja>> {

    @Autowired
    private ServiceBusinessRemote serviceBusiness;

    @Autowired
    private SolicitudBusinessRemote solicitudBusiness;
    
    @Autowired
    private CorreccionDatosRemote correccionDatosBusiness;
    

    private final Logger log = LoggerFactory
            .getLogger(EditarDatosController.class);

    @Autowired
    @Qualifier(BeansConstants.READ_TRAMITES_ASIGNADOS_HELPER)
    private ReadHelper<RequestSolicitudBandejaPage, Page<SolicitudBandeja>> service;

    @Override
    public ReadHelper<RequestSolicitudBandejaPage, Page<SolicitudBandeja>> getHelper() {
        return service;
    }

    @RequestMapping(RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLEE)
    @ResponseBody
    public List<CorreccionDatos> datosNSSBD(@RequestBody DatosNSSBD input,
            HttpServletRequest request) throws IOException {
        List<Fisica> personasFuenteNSS = serviceBusiness
                .getAseguradoByNSSLegadosyBDTU(input.getNss(), true);
        List<CorreccionDatos> obtener = new ArrayList<CorreccionDatos>();

        for (Fisica f : personasFuenteNSS) {
            CorreccionDatos correccion = new CorreccionDatos();

            correccion.setCurp(f.getCurp());
            correccion.setApellidoPaterno((f.getPrimerApellido()));
            correccion.setApellidoMaterno((f.getSegundoApellido()));
            correccion.setNombre(f.getNombre());
            correccion.setSexo(f.getSexo().getDescripcion());
            correccion.setFechaNacimiento(f.getFechaNacimientoFormateada());
            correccion.setLugarNacimiento(f.getLugarNacimiento().getNombre());
            correccion.setPertenecebd(
                    f.getIdentificadores().get(0).getIdentificadora());
            correccion.setNacionalidad(
                    (f.getPais()!=null) ? f.getPais().getNacionalidad() : "");

            String entidadFederativa = (f.getDocumentosProbatorios()
                    .size() != 0)
                            ? f.getDocumentosProbatorios().get(0).getPersona()
                                    .getActaNacimiento()
                                    .getIdEntidadFederativa()
                            : " ";
            String municipio = (f.getDocumentosProbatorios().size() != 0)
                    ? f.getDocumentosProbatorios().get(0).getPersona()
                            .getActaNacimiento().getMunicipio().getNombre()
                    : " ";

            String anoRegistro = String
                    .valueOf((f.getDocumentosProbatorios().size() != 0)
                            ? f.getDocumentosProbatorios().get(0).getPersona()
                                    .getActaNacimiento().getAnio()
                            : " ");
            String tomo = (f.getDocumentosProbatorios().size() != 0)
                    ? f.getDocumentosProbatorios().get(0).getPersona()
                            .getActaNacimiento().getTomo()
                    : " ";
            String cript = (f.getDocumentosProbatorios().size() != 0)
                    ? f.getDocumentosProbatorios().get(0).getPersona()
                            .getActaNacimiento().getCrip()
                    : " ";

            String foja = (f.getDocumentosProbatorios().size() != 0)
                    ? f.getDocumentosProbatorios().get(0).getPersona()
                            .getActaNacimiento().getNoFoja()
                    : " ";

            String noacta = (f.getDocumentosProbatorios().size() != 0)
                    ? f.getDocumentosProbatorios().get(0).getPersona()
                            .getActaNacimiento().getNoActa()
                    : " ";
            String noLibro = (f.getDocumentosProbatorios().size() != 0)
                    ? f.getDocumentosProbatorios().get(0).getPersona()
                            .getActaNacimiento().getNoLibro()
                    : " ";
            String docProbatorios = "Entidad: " + entidadFederativa +"\n"
                    + "Municipio: " + municipio +"\n"+ " Ano: de registro: "
                    + anoRegistro +"\n" + " Tomo: " + tomo +"\n" +  " Numero de Acta: "
                    + noacta +"\n" + " CRIP: " + cript +"\n" + " Numero de Libro: "
                    + noLibro +"\n" + " Numero de Foja: " + foja;

            correccion.setDatosDocumentoProbatorio(docProbatorios);
            obtener.add(correccion);
        }
        return obtener;
    }
    
    
    /*
     * Realiza el guardado parcial y actualizacion de tipo de correccion.
     */
    @RequestMapping(RequestMappingConstants.GUARDARDATOSNSSACTUALIZADOS)
    public void datosAguardarNSSBD(@RequestBody DatosAGuradarNSS input,
            HttpServletRequest request) {
                
        log.error("---CDA--- GUARDADO REGULARIZACION  {}", input.getIdTramitePrincipal());
        
//        correccionDatosBusiness.guardarCorrecionNssCDA(input.getIdTramitePrincipal(),input.getNssCorreccion(), Long.parseLong(input.getTipoNSS()),Long.parseLong(input.getTipoRegularizacion()) , input.getTipoCorreccion());
        
    }
    

    
    /*
     * Realiza el guardado parcial y actualizacion de tipo de correccion.
     */
//    @RequestMapping(RequestMappingConstants.GUARDARDATOSNSSACTUALIZADOS)
//    public void datosAguardarNSSBD(@RequestBody DatosAGuradarNSS input,
//            HttpServletRequest request) {
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//
//        Date fechaNacimiento = null;
//        try {
//
//            if ((!input.getFechaNacimientodata().equalsIgnoreCase(""))
//                    || (!input.getFechaNacimientodata().equalsIgnoreCase("")))
//            {
//                fechaNacimiento = formatter
//                        .parse(input.getFechaNacimientodata());
//            }
//        } catch (ParseException e1) {
//
//            log.error("---CDA--- Solicitud  {}", e1);
//        }
//        try {
            // Obtengo Solicitud

//            Solicitud solicitud = solicitudBusiness.consultarPorIdTramite(
//                    Long.parseLong(input.getIdTramitePrincipal()));
//            
//
//            solicitud = orderByTramite(solicitud);
//
//            Identificador identificador = new Identificador();
//            identificador.setIdentificadora(input.getFuente());
//            
//            List<Fisica> listFisica = new ArrayList<Fisica>();
//            Fisica personaCanase = new Fisica();
//
//            personaCanase.getIdentificadores().add(identificador);
//            personaCanase.setNombre(
//                    (input.getNombredat()!=null) ? input.getNombredat() : "");
//            personaCanase.setPrimerApellido((input.getApellidoPdata()!=null)
//                    ? input.getApellidoPdata() : "");
//            personaCanase.setSegundoApellido((input.getApellidoMdata()!=null)
//                    ? input.getApellidoMdata() : "");
//            personaCanase.getSexo().setDescripcion(
//                    (input.getSexodata()!=null) ? input.getSexodata() : "");
//            personaCanase.setFechaNacimiento(fechaNacimiento);
//            personaCanase.getLugarNacimiento()
//                    .setNombre((input.getLugarNacimientodata()!=null)
//                            ? input.getLugarNacimientodata() : "");
//            if(! input .getNacionalidaddata().equals(""))
//            {
//                personaCanase.getPais()
//                .setDescripcion((!input.getNacionalidaddata().equals(""))
//                        ? input.getNacionalidaddata() : "");
//            }
//           
//            if (solicitud.getTramites().equals(null)) 
//            {
//
//                solicitud.getTramites().get(0).setPersonas(listFisica);
//                solicitud.getTramites().get(0).getPersonas().add(personaCanase);
//                log.trace("contenido de Peronsa \n",solicitud.getTramites().get(0).getPersonas().toString());
//                log.trace("contenido de Tramite \n",solicitud.getTramites().get(0));
//                solicitudBusiness.actualizarXmlTramite(solicitud.getTramites().get(0));
//             }
//        } catch (NumberFormatException e) {
//            // TODO Auto-generated catch block
//            log.error("Editar datos- Number Format ERROR -----",e);
//        } catch (SolicitudNoEncontradaException e) {
//            // TODO Auto-generated catch block
//            log.error("Editar datos--- No se encuentra Excepcion ERROR -----",e);
//        } catch (TramiteNoEncontradoException e) {
//            // TODO Auto-generated catch block
//            log.error("Editar datos--- Tramite no encontado ERROR -----",e);
//        } catch (IllegalArgumentException e) {
//            // TODO Auto-generated catch block
//            log.error("Editar datos--- Argumento ilegal ERROR -----",e);          
//        }
//       
//         String idtramite = input.getIdTramitePrincipal();
        // Actualizar registro DIT_Detalle_NSS
//         String tipoCorrecion="";
//
//         solicitudBusiness.actualizarTipoTramite(idtramite, input.getTipoCertificacion() , input.getNumNSS() );
//
//        // Actualizar registro DIT_CORRECCION_DATOS_ASEG
//
//         solicitudBusiness.actualizarTipoNSS(idtramite,  input.getTipoCertificacion());
//        
//
//    }

    /*
     * DatosAGuradarNSS Valida que el tramite tenga datos de RENAPO
     */

    public Solicitud orderByTramite(Solicitud solicitudActiva) {
        TramiteCorreccionCurp firstTramite = new TramiteCorreccionCurp();
        Iterator<Tramite> iterador = solicitudActiva.getTramites().iterator();

        while (iterador.hasNext()) {
            TramiteCorreccionCurp tramiteCurp = ((TramiteCorreccionCurp) iterador
                    .next());

            if (EstadoTramiteEnum.CANCELADO.getCodigo()
                    .intValue() == tramiteCurp.getEstadoTramite()
                            .getIdEstadoTramitePersona().intValue()) {
                iterador.remove();
            } else {
                if (tramiteCurp.getPersonaRENAPO() != null) {
                    firstTramite = tramiteCurp;
                    iterador.remove();
                }
            }
        }
        solicitudActiva.getTramites().add(0, firstTramite);
        return solicitudActiva;
    }
}
