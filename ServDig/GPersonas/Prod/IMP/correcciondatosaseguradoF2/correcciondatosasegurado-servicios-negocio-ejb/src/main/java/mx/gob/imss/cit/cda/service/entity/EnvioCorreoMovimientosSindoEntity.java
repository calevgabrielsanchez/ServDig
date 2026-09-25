package mx.gob.imss.cit.cda.service.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.cit.cda.service.interfaces.ResponsablesDelegacionRemote;
import mx.gob.imss.cit.cda.service.utility.CorreosRespuestaSINDOUtilityLocal;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.ClienteWebserviceResponsablesSubdelegacionException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.EnvioCorreoElectronicoBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.BitacoraMovimientoSindoCDA;
import mx.gob.imss.ctirss.delta.model.dto.CorreoElectronicoDTO;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(name = "envioCorreoMovimientosSindo", mappedName = "envioCorreoMovimientosSindo")
public class EnvioCorreoMovimientosSindoEntity implements
        EnvioCorreoMovimientosSindoLocal {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private static final String MAIL_PROPERTIES_ADRESS = "serviciosdigitales@imss.gob.mx";
    private static final String PROCESADO_SIN_ERROR = "000";

    @EJB
    private CorreosRespuestaSINDOUtilityLocal correosRespuestaSINDOUtility;

    @EJB
    private CorreccionDatosAseguradoLocal correccionDatosAseguradoEntity;

    @EJB(name = "envioCorreoElectronicoBusiness", mappedName = "envioCorreoElectronicoBusiness")
    private EnvioCorreoElectronicoBusinessRemote envioCorreoElectronicoBusinessRemote;

    @EJB(name = "solicitudBusiness", mappedName = "solicitudBusiness")
    private SolicitudBusinessRemote solicitudBusiness;

    @EJB(name = "responsablesDelegacionBusiness", mappedName = "responsablesDelegacionBusiness")
    private ResponsablesDelegacionRemote responsablesDelegacionBusiness;

    public void enviarCorreo(
            Map<String, List<BitacoraMovimientoSindoCDA>> bitacoraSINDO,
            String folio) {

        String curpResponsable = correccionDatosAseguradoEntity
                .obtenerResponsableTramiteCDA(folio);
        Solicitud busqueda = new Solicitud();
        busqueda.setNoFolioSolicitud(folio);
        Solicitud sol;
        if (curpResponsable != null) {
            try {
                sol = solicitudBusiness.consultarFolio(busqueda);
                Fisica asegurado = ((TramiteCorreccionCurp) sol.getTramites()
                        .get(0)).getPersonaRENAPO();
                // obtener nombre de usuario y correo
                Usuario responsable = responsablesDelegacionBusiness
                        .recuperaUsuarioEsquemaSeguridadByCURP(curpResponsable);
                String nombreCompleto = curpResponsable;

                if (responsable != null
                        && responsable.getFisica() != null
                        && responsable.getFisica().getNombreCompleto() != null
                        && responsable.getFisica().getCorreoElectronico() != null
                        && responsable.getFisica().getCorreoElectronico()
                                .getCorreo() != null) {

                    if (bitacoraSINDO.get(folio) != null) {
                        // agrupar resultados y enviar correos
                        Collection<BitacoraMovimientoSindoCDA> exitosos = CollectionUtils
                                .select(bitacoraSINDO.get(folio),
                                        getResultadoPredicate());
                        Collection<BitacoraMovimientoSindoCDA> erroneos = CollectionUtils
                                .selectRejected(bitacoraSINDO.get(folio),
                                        getResultadoPredicate());

                        if (exitosos != null && !exitosos.isEmpty()) {
                            nombreCompleto = responsable.getFisica()
                                    .getNombreCompleto();
                            String correo = getCuerpoCorreo(folio,
                                    nombreCompleto,
                                    asegurado.getNombreCompleto(),
                                    asegurado.getCurp(),
                                    new ArrayList<BitacoraMovimientoSindoCDA>(
                                            exitosos));
                            envioCorreo(responsable, correo, folio);
                            log.info(
                                    "---CDA--- Se envio correo de procesamiento movimiento exitoso SINDO a {}",
                                    responsable.getFisica()
                                            .getCorreoElectronico().getCorreo());
                        }

                        if (erroneos != null && !erroneos.isEmpty()) {
                            nombreCompleto = responsable.getFisica()
                                    .getNombreCompleto();
                            String correo = getCuerpoCorreo(folio,
                                    nombreCompleto,
                                    asegurado.getNombreCompleto(),
                                    asegurado.getCurp(),
                                    new ArrayList<BitacoraMovimientoSindoCDA>(
                                            erroneos));
                            envioCorreo(responsable, correo, folio);
                            log.info(
                                    "---CDA--- Se envio correo de procesamiento movimiento erroneo SINDO a {}",
                                    responsable.getFisica()
                                            .getCorreoElectronico().getCorreo());
                        }

                    }

                }
            } catch (ClienteWebserviceResponsablesSubdelegacionException e) {
                // TODO Auto-generated catch block
                log.debug(
                        "---CDA--- Error ClienteWebserviceResponsablesSubdelegacionException {}",
                        e);
            } catch (Exception e) {
                log.error("---CDA--- No fue posible enviar correo SINDO "
                        + folio, e);
            }
        }
    }

    private void envioCorreo(Usuario responsable, String correo, String folio)
            throws Exception {
        // enviar correo
        CorreoElectronicoDTO correoElectronicoDTO = new CorreoElectronicoDTO();
        correoElectronicoDTO
                .setAsunto(StringEscapeUtils
                        .unescapeHtml("Solicitud de Regularizaci&oacute;n y/o Correcci&oacute;n de Datos Personales del Asegurado. Folio: "
                                + folio + "."));
        correoElectronicoDTO.setCuerpoCorreo(correo);
        correoElectronicoDTO.setCorreoPara(new String[] { responsable
                .getFisica().getCorreoElectronico().getCorreo() });
        envioCorreoElectronicoBusinessRemote.enviarCorreo(correoElectronicoDTO,
                MAIL_PROPERTIES_ADRESS);
    }

    private String getCuerpoCorreo(String folio, String nombreResponsable,
            String nombreAsegurado, String curp,
            List<BitacoraMovimientoSindoCDA> bitacoraSINDO) {
        String correo;
        String estatusSINDO = bitacoraSINDO != null && !bitacoraSINDO.isEmpty()
                && bitacoraSINDO.get(0) != null ? bitacoraSINDO.get(0)
                .getResultado() : "";
        if (estatusSINDO.equals(PROCESADO_SIN_ERROR)) {
            correo = correosRespuestaSINDOUtility
                    .contenidoCorreoMovimientoExitoso(folio, nombreResponsable,
                            nombreAsegurado, curp, bitacoraSINDO);
        } else {
            correo = correosRespuestaSINDOUtility
                    .contenidoCorreoMovimientoNoExitoso(folio,
                            nombreResponsable, nombreAsegurado, curp,
                            bitacoraSINDO);
        }
        return correo;
    }

    private Predicate getResultadoPredicate() {
        return new BeanPropertyValueEqualsPredicate("resultado",
                PROCESADO_SIN_ERROR, true);
    }

}
