package mx.gob.imss.ctirss.delta.gestion.solicitud.service.utility;

import java.util.Map;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.AcuseVentanilla;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;

@Local
public interface AcuseTramitesVentanillaUtilityLocal {

	AcuseVentanilla prepararDatosAcusePorTramite(Solicitud solicitud, Tramite tramite, Long idTipoTramite);
	
	AcuseVentanilla complementarDatosFirma(AcuseVentanilla acuseVentanilla,
		FirmaElectronica firmaElectronica);
	
	Map<String, Object> generarParametrosReporte(AcuseVentanilla acuseVentanilla);
	
	String generarCadenaOriginalIMSS(Solicitud solicitud, AcuseVentanilla acuse);
	
}
