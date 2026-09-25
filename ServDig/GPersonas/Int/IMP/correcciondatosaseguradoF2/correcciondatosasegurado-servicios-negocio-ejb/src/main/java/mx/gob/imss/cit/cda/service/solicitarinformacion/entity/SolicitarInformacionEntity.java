package mx.gob.imss.cit.cda.service.solicitarinformacion.entity;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;

@Stateless(name = "solicitarInformacionEntity", mappedName = "solicitarInformacionEntity")
public class SolicitarInformacionEntity  extends AbstractServiceEntity implements SolicitarInformacionEntityLocal {
    
    /**
     *Método para guardar la persona que solicitó la información
     * **/
    public int actualizarUsuarioSolicitarInformacion(Long idTramite, int tipoUsr) {

        StringBuffer sql = new StringBuffer();

        sql.append("update DIT_CORRECCION_DATOS_ASEG set IND_TIPO_SOLICITUD_INFO = :cveTipoUsr where CVE_ID_TRAMITE = :cveIdTramite");

        javax.persistence.Query query = em.createNativeQuery(sql.toString());

        query.setParameter("cveIdTramite", idTramite);
        query.setParameter("cveTipoUsr", tipoUsr);

        query.executeUpdate();

        int numSolicitudesActualizadas = query.executeUpdate();
        log.error("Se actualizaron los sig numero de solicitudes: "+ numSolicitudesActualizadas);
        return numSolicitudesActualizadas;

    }

}
