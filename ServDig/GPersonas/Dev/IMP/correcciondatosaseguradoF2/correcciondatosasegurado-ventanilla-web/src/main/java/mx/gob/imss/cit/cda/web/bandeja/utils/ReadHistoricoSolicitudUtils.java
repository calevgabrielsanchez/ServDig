package mx.gob.imss.cit.cda.web.bandeja.utils;

import java.util.List;

import mx.gob.imss.cit.cda.web.bandeja.vo.SolicitudBandeja;
import mx.gob.imss.cit.cda.web.common.utils.ReadTramitesCommonUtils;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.TareaBandeja;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.ParticipantesEnum;

import org.springframework.stereotype.Component;

@Component
public class ReadHistoricoSolicitudUtils extends ReadTramitesCommonUtils{

    @Override
    public SolicitudBandeja crearSolicitudBandeja(List<TareaBandeja> tareasPorFolio, String usuario, Integer pantalla, String folioConulta)  {
        getLogger().info("Creando Solicitud Bandeja Historico");
        SolicitudBandeja solicitud = super.crearSolicitudBandeja(tareasPorFolio, usuario,pantalla,folioConulta);
        TareaBandeja bandeja = tareasPorFolio.get(0);
        solicitud.setEsPropietario(usuario.equalsIgnoreCase(bandeja.getInicioTramite().getParticipantes().get(ParticipantesEnum.RESPONSABLE.getDescripcion()))|| usuario.equalsIgnoreCase(bandeja.getInicioTramite()
                .getParticipantes()
                .get(ParticipantesEnum.AUTORIZADOR.getDescripcion())));
        
        
        return solicitud;
    }

}