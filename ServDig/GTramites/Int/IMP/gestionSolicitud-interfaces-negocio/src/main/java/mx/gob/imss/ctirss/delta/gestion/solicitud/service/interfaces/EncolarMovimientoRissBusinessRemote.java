package mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.beneficio.model.MovimientoRissType;

@Remote
public interface EncolarMovimientoRissBusinessRemote {

	void encolarMovimientoRiss(MovimientoRissType movimientoRissType);
	
	void encolarMovimientosRiss(List<MovimientoRissType> listaMovimientosRissType);
}
