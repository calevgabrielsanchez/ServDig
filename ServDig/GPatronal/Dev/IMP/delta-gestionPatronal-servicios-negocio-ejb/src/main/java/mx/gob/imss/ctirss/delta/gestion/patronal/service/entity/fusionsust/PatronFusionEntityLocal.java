package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.fusionsust;

import javax.ejb.Local;

@Local
public interface PatronFusionEntityLocal {
	
	Boolean existeFusion(Long idPatron, Long idPatronFusionado);
	Boolean validarPatronExisteComoFusionado(Long idPatronAValidar);
	void insertarFusion(Long idPatron, Long idPatronFusionado);
	Long getCveIdPatronGeneralPorIdSujetoObligado(Long idPatronSujetoObligado);
	
}
