package mx.gob.imss.cit.cda.service.solicitarinformacion.entity;

import javax.ejb.Local;

@Local
public interface SolicitarInformacionEntityLocal {
    
    int actualizarUsuarioSolicitarInformacion(Long idTramite, int tipoUsr);

}
