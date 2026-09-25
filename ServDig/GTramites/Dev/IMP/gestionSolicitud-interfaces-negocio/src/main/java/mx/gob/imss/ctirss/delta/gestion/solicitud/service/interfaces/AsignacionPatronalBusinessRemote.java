package mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces;

import javax.ejb.Remote;
import mx.gob.imss.ctirss.delta.model.gestion.integracion.asignacion.MovimientoAsignacionType;

@Remote
public interface AsignacionPatronalBusinessRemote {
	
	/**
	 * Genera el XML y encola al OSB para escribir en el archivo de CANASE
	 * 
	 * @param movimientoAsignacionType
	 */
    void encolarMovimientoAsignacion(MovimientoAsignacionType movimientoAsignacionType);

    /**
     * Solo genera el XML para escribir el archivo en CANASE
     * 
     * @param movimientoAsignacionType
     * @return xml
     */
    String generarXmlMovimientoAsignacion(
			MovimientoAsignacionType movimientoAsignacionType);
}
