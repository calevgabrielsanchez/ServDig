package mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces;

import javax.ejb.Remote;

@Remote
public interface PatronSustitucionFusionBusinessRemote {
	
	Boolean validarExistenciaFusion(Long idPatronSO, Long idPatronSOFusionado);
	Boolean validarPatronExisteComoFusionado(Long idPatronAValidar);

}
