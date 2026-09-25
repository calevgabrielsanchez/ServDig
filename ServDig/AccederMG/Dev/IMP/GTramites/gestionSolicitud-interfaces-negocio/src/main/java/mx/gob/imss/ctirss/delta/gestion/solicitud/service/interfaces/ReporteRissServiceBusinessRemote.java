package mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces;

import javax.ejb.Remote;

@Remote
public interface ReporteRissServiceBusinessRemote {

	void obtenerTxtMovimientosRiss();

	void generarExcelMovimientosRiss(boolean isCorteDiaAnterior,
			boolean mostrarDatosNrp, boolean enviarCorreo);

}
