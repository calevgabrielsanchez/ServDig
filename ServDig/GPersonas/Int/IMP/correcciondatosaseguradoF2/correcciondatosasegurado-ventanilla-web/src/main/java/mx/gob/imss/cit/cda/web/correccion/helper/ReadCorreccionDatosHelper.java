package mx.gob.imss.cit.cda.web.correccion.helper;

import mx.gob.imss.cit.cda.core.events.ReadEvent;
import mx.gob.imss.cit.cda.core.events.RequestReadEvent;
import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.service.interfaces.CorreccionDatosRemote;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.responsable.model.Solicitud;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.CorreccionDatos;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.OrigenInformacion;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(BeansConstants.READ_CORRECION_DATOS_HELPER)
public class ReadCorreccionDatosHelper implements
        ReadHelper<Solicitud, CorreccionDatos> {
    
    
    @Autowired
    private CorreccionDatosRemote correccionDatosBusiness;
    
    @Autowired
    private SolicitudBusinessRemote solicitudBusiness;
    
    @Autowired
    private PersonaBusinessRemote personaBusiness;

    @Override
    public ReadEvent<CorreccionDatos> requestEvent(
            RequestReadEvent<Solicitud> requestReadEvent) {
        try {
        
        mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud sol = solicitudBusiness.consultarPorFolioSolicitud(requestReadEvent.getData().getFolio());
        
//        CorreccionDatos correcion = correccionDatosBusiness
//                .obtenerTramitesCorreccion(Long.parseLong(requestReadEvent
//                        .getData().getIdSolicitud()));

        CorreccionDatos correcion = correccionDatosBusiness.obtenerFuentesDatos(sol);
        String curp = requestReadEvent.getData().getInformacionRENAPO().getCurp();
        String lugarNacimiento = "";
		try {
		    if (StringUtils.isNotBlank(curp)) {
                lugarNacimiento = personaBusiness.buscarPersonaFisicaPorCurpEnRenapo(curp).getLugarNacimiento().getClave();
            }
		} catch (ClienteWebserviceRenapoCurpException e) {
			e.printStackTrace();
		}
        
            OrigenInformacion renapoOrigen = new OrigenInformacion();
            correcion.setRenapo(new OrigenInformacion());

            if (requestReadEvent.getData().getInformacionRENAPO() != null) {
                renapoOrigen.setNombre(requestReadEvent.getData()
                        .getInformacionRENAPO().getNombre());
                renapoOrigen.setApellidoPaterno(requestReadEvent.getData()
                        .getInformacionRENAPO().getApellidoPaterno());
                renapoOrigen.setApellidoMaterno(requestReadEvent.getData()
                        .getInformacionRENAPO().getApellidoMaterno());
                renapoOrigen.setCurp(requestReadEvent.getData()
                        .getInformacionRENAPO().getCurp());
                renapoOrigen.setSexo(requestReadEvent.getData()
                        .getInformacionRENAPO().getSexo());
                renapoOrigen.setFechaNacimiento(requestReadEvent.getData()
                        .getInformacionRENAPO().getFechaNacimiento());
                renapoOrigen.setLugarNacimiento(requestReadEvent.getData()
                        .getInformacionRENAPO().getLugarNacimiento());
                renapoOrigen.setIdlugarNacimiento(lugarNacimiento);
                renapoOrigen.setNacionalidad(requestReadEvent.getData().getInformacionRENAPO().getNacionalidad());
                renapoOrigen.setCurpsHistoricas(requestReadEvent.getData().getInformacionRENAPO().getCurpsHistoricas());
                renapoOrigen.setDatosDocumentoProbatorio(requestReadEvent.getData().getInformacionRENAPO().getDatosDocumentoProbatorio());
                correcion.setRenapo(renapoOrigen);
                
            }

            return new ReadEvent<CorreccionDatos>(requestReadEvent.getKey(),
                    correcion);

        } catch (Exception e) {
            return ReadEvent.notFound(requestReadEvent.getKey());
        }

    }

    

}
