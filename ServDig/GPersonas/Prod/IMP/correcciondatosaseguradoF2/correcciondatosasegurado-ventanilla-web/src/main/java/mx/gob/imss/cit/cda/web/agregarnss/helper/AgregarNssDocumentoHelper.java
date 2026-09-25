package mx.gob.imss.cit.cda.web.agregarnss.helper;

import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.cit.cda.core.events.UpdateEvent;
import mx.gob.imss.cit.cda.core.events.UpdatedEvent;
import mx.gob.imss.cit.cda.core.helper.UpdateHelper;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.responsable.model.CorreccionDatos;
import mx.gob.imss.cit.cda.web.app.responsable.model.NSS;
import mx.gob.imss.cit.cda.web.validator.NSSValidator;
import mx.gob.imss.cit.cda.web.vo.NSSVOError;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;

@Component(BeansConstants.AGREGAR_NSS_DOCUMENTO_HELPER)
public class AgregarNssDocumentoHelper implements
        UpdateHelper<NSS, List<CorreccionDatos>> {

  @Autowired
  private NSSValidator nSSValidator;

  @Autowired
  private ServiceBusinessRemote serviceBusiness;

  private static final Logger logger = LoggerFactory.getLogger(
          AgregarNssDocumentoHelper.class);

  private UpdatedEvent<List<CorreccionDatos>> step1(
          UpdateEvent<NSS> requestUpdateEvent) {

    NSSVOError nssvo = new NSSVOError();
    nssvo.setNSS(requestUpdateEvent.getData().getNss());
    
    final Errors errors = new BindException(nssvo, "model");
    CorreccionDatos correccion;
    String msgErr = "";
    List<CorreccionDatos> obtener = new ArrayList<CorreccionDatos>();

    if ( requestUpdateEvent.getData().getTipoCorreccion() == null
         || !requestUpdateEvent.getData().getTipoCorreccion().equals("EditandoNSS") ) {
      nSSValidator.validate(nssvo, errors);

      if (errors.hasErrors()) {
        logger.debug("[.: :.]procesa errores de captura NSS");
        
        if ("field.NSS.invalido".equals( nssvo.getErrField() )) {
          logger.debug("[.: :.]field.NSS.invalido");
          msgErr = "El formato del Número de Seguridad Social no es válido.";
        }
        if ("field.NSS.formatoIncorrecto".equals( nssvo.getErrField() )) {
          logger.debug("[.: :.]field.NSS.formatoIncorrecto");
          msgErr = "El formato del Número de Seguridad Social no es válido.";
        }
        if ("field.NSS.bloqueado".equals( nssvo.getErrField() )) {
          logger.debug("[.: :.]field.NSS.bloqueado");
          msgErr = "El NSS que intenta agregar se encuentra involucrado en otra Solicitud de Corrección de Datos. No es posible incluir el NSS a la solicitud.";
        }                  

        correccion = new CorreccionDatos();
        correccion.setMsgError(msgErr);
        obtener.add(correccion);
        return new UpdatedEvent<List<CorreccionDatos>>(requestUpdateEvent.getKey(), obtener);
      }
    }
    return null;
  }

  private UpdatedEvent<List<CorreccionDatos>> step2( UpdateEvent<NSS> requestUpdateEvent ) {

    NSSVOError nssvo = new NSSVOError();    
    nssvo.setNSS(requestUpdateEvent.getData().getNss());

    CorreccionDatos correccion;
    List<CorreccionDatos> obtener = new ArrayList<CorreccionDatos>();

    List<Fisica> personasFuenteNSS = serviceBusiness
            .getAseguradoByNSSLegadosyBDTU(requestUpdateEvent.getData().getNss(),
                    true);

    if (personasFuenteNSS != null && !personasFuenteNSS.isEmpty()) {
      for (Fisica f : personasFuenteNSS) {
        correccion = new CorreccionDatos();

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
                (f.getPais() != null) ?
                f.getPais().getNacionalidad() :
                "");

        String entidadFederativa = (f.getDocumentosProbatorios()
                .size() != 0) ?
                        f.getDocumentosProbatorios().get(0).getPersona()
                                .getActaNacimiento()
                                .getIdEntidadFederativa() :
                        " ";
        String municipio = (f.getDocumentosProbatorios().size() != 0) ?
                f.getDocumentosProbatorios().get(0).getPersona()
                        .getActaNacimiento().getMunicipio().getNombre() :
                " ";

        String anoRegistro = String
                .valueOf((f.getDocumentosProbatorios().size() != 0) ?
                        f.getDocumentosProbatorios().get(0).getPersona()
                                .getActaNacimiento().getAnio() :
                        " ");
        String tomo = (f.getDocumentosProbatorios().size() != 0) ?
                f.getDocumentosProbatorios().get(0).getPersona()
                        .getActaNacimiento().getTomo() :
                " ";
        String cript = (f.getDocumentosProbatorios().size() != 0) ?
                f.getDocumentosProbatorios().get(0).getPersona()
                        .getActaNacimiento().getCrip() :
                " ";

        String foja = (f.getDocumentosProbatorios().size() != 0) ?
                f.getDocumentosProbatorios().get(0).getPersona()
                        .getActaNacimiento().getNoFoja() :
                " ";

        String noacta = (f.getDocumentosProbatorios().size() != 0) ?
                f.getDocumentosProbatorios().get(0).getPersona()
                        .getActaNacimiento().getNoActa() :
                " ";
        String noLibro = (f.getDocumentosProbatorios().size() != 0) ?
                f.getDocumentosProbatorios().get(0).getPersona()
                        .getActaNacimiento().getNoLibro() :
                " ";
        String docProbatorios = "Entidad: " + entidadFederativa + "\n"
                + "Municipio: " + municipio + "\n" + " Ano: de registro: "
                + anoRegistro + "\n" + " Tomo: " + tomo + "\n"
                + " Numero de Acta: "
                + noacta + "\n" + " CRIP: " + cript + "\n"
                + " Numero de Libro: "
                + noLibro + "\n" + " Numero de Foja: " + foja;

        correccion.setDatosDocumentoProbatorio(docProbatorios);
        obtener.add(correccion);
      }
    } else {
      logger.debug(
              " ******************************    ERROR AL OBTENER LOS DATOS DEL NSS     ***************************************************** ");
    }
    return new UpdatedEvent<List<CorreccionDatos>>(requestUpdateEvent.getKey(),
            obtener);

  }

  @SuppressWarnings("unchecked")
  @Override
  public UpdatedEvent<List<CorreccionDatos>> requestEvent(
          UpdateEvent<NSS> requestUpdateEvent) {
    logger.debug("inicia la busqueda de nss : " + requestUpdateEvent.getData().
            getNss());
    logger.debug("inicia la busqueda de nss edicion : " + requestUpdateEvent.
            getData().getTipoCorreccion());

    UpdatedEvent<List<CorreccionDatos>> updatedEvent;

    updatedEvent = step1(requestUpdateEvent);

    if (updatedEvent != null) {
      return updatedEvent;
    }

    return step2(requestUpdateEvent);

  }

}
