package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces;

import javax.ejb.Remote;

@Remote
public interface TramiteServiceRemote {

	public Boolean tramitePosiblePorModalidadParenteso(Long idModalidad,Long idParentesco, Long idTipoTramite);
}
