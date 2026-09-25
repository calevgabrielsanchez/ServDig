package mx.gob.imss.ctirss.gestionpersonas.servicios.entity;

import mx.gob.imss.ctirss.delta.gestion.individuo.util.Utilerias;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Tramite;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoTramite;
import mx.gob.imss.ctirss.delta.persistence.DicRazonResultado;
import mx.gob.imss.ctirss.delta.persistence.DicTipoTramite;
import mx.gob.imss.ctirss.delta.persistence.DitTramite;

public class TramiteConversor {

    public static DitTramite fromModelToEntity(final Tramite tramite) {
        DitTramite ditTramite = null; // NOPMD
        if (tramite != null) {
            ditTramite = new DitTramite();
            ditTramite.setFecTramite(tramite.getFechaTramite());
            
            if (tramite.getIdTramite() != null){
            	ditTramite.setCveIdTramite(tramite.getIdTramite());	
            }
            
            final DicEstadoTramite dicEstadoTramite = new DicEstadoTramite();
            dicEstadoTramite.setCveIdEstadoTramite(tramite.getIdEstadoTramite());
            dicEstadoTramite.setDesEstadoTramite(tramite.getDesEstadoTramite());
            ditTramite.setDicEstadoTramite(dicEstadoTramite);
            final DicTipoTramite dicTipoTramite = new DicTipoTramite();
            dicTipoTramite.setCveIdTipoTramite(tramite.getTipoTramite().getIdTipoTramite());
            ditTramite.setDicTipoTramite(dicTipoTramite);
            if (Utilerias.isNotBlank(tramite.getIdRazonResultado())) {
                final DicRazonResultado dicRazonResultado = new DicRazonResultado();
                dicRazonResultado.setCveIdRazonResultado(tramite.getIdRazonResultado());
                dicRazonResultado.setDesRazonResultado(tramite.getDesRazonResultado());
                ditTramite.setDicRazonResultado(dicRazonResultado);
            }
        }
        return ditTramite;
    }
}
