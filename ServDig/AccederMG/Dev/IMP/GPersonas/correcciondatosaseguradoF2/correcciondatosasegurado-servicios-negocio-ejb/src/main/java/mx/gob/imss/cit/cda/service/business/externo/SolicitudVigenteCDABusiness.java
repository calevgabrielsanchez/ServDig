package mx.gob.imss.cit.cda.service.business.externo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.cit.cda.service.interfaces.RegistroSolicitudCorreccionDatosAseguradoRemote;
import mx.gob.imss.cit.cda.service.interfaces.externo.SolicitudVigenteCDARemote;
import mx.gob.imss.ctirss.delta.model.externo.cda.ValidaSolicitudVigenteCDAResponse;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(name = "solicitudVigenteCDABusiness", mappedName = "solicitudVigenteCDABusiness")
public class SolicitudVigenteCDABusiness implements SolicitudVigenteCDARemote {

    private final Logger log = LoggerFactory
            .getLogger(SolicitudVigenteCDABusiness.class);

    private static final String ORIGEN_SOLICITUD_INTERNET = "INTERNET";

    @EJB
    private RegistroSolicitudCorreccionDatosAseguradoRemote registroSolicitudCorreccionDatosAseguradoBusiness;

    @Override
    public ValidaSolicitudVigenteCDAResponse validaSolicitudVigenteCDA(
            String curp, String nss, String correo) {
        ValidaSolicitudVigenteCDAResponse validaSolicitudVigenteCDAResponse = new ValidaSolicitudVigenteCDAResponse();
        Solicitud solicitud = null;
        List<String> listCurps = null;
        try {
            if (curp != null) {
                listCurps = new ArrayList<String>();
                listCurps.add(curp);

                solicitud = registroSolicitudCorreccionDatosAseguradoBusiness
                        .obtenerUltimaSolicitudSeguimientoCDA(listCurps,
                                ORIGEN_SOLICITUD_INTERNET);

                if (solicitud != null) {
                    validaSolicitudVigenteCDAResponse
                            .setIdEstadoSolicitud(solicitud
                                    .getEstadoSolicitud()
                                    .getIdEstadoSolicitud());
                    validaSolicitudVigenteCDAResponse
                            .setDescripcionSolicitud(solicitud
                                    .getEstadoSolicitud().getDescripcion());
                    validaSolicitudVigenteCDAResponse
                            .setNumeroSolicitud(solicitud.getNoFolioSolicitud());

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                            "dd/mm/yyyy");
                    String fechaActualizacion = simpleDateFormat
                            .format(solicitud.getFechaActualizacion());

                    validaSolicitudVigenteCDAResponse
                            .setFechaActualizacion(fechaActualizacion);
                } else {
                    validaSolicitudVigenteCDAResponse.setCodigo("000");
                    validaSolicitudVigenteCDAResponse
                            .setMensaje("La persona con la CURP " + curp
                                    + " no tienen solicitudes vigentes");
                    return validaSolicitudVigenteCDAResponse;
                }
            } else {
                validaSolicitudVigenteCDAResponse.setCodigo("002");
                validaSolicitudVigenteCDAResponse
                        .setMensaje("El parametro CURP no puede ser nulo");
                return validaSolicitudVigenteCDAResponse;
            }
        } catch (Exception ex) {
            log.error("Error al validar la solicitud vigente para la curp: "
                    + curp, ex);
            validaSolicitudVigenteCDAResponse.setCodigo("001");
            validaSolicitudVigenteCDAResponse
                    .setMensaje("Error al validar la vigencia de solicitud CDA para la curp "
                            + curp);
            return validaSolicitudVigenteCDAResponse;
        }
        return validaSolicitudVigenteCDAResponse;
    }

}
