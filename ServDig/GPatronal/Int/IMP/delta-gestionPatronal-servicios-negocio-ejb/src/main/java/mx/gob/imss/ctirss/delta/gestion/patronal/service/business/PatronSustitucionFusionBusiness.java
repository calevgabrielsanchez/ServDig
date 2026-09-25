package mx.gob.imss.ctirss.delta.gestion.patronal.service.business;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.fusionsust.PatronFusionEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.PatronSustitucionFusionBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

@Stateless(name = "patronSustitucionFusionBusiness" , mappedName = "patronSustitucionFusionBusiness")
public class PatronSustitucionFusionBusiness extends AbstractServiceBusiness implements 
		PatronSustitucionFusionBusinessRemote,PatronSustitucionFusionBusinessLocal {
	
	@EJB
	PatronFusionEntityLocal patronFusionEntityLocal;
	
	@Override
	public Boolean validarExistenciaFusion(Long idPatronSO, Long idPatronSOFusionado) {
		
		Boolean resultado = false;
		if(idPatronSO != null && idPatronSOFusionado != null) {
			idPatronSO = patronFusionEntityLocal.getCveIdPatronGeneralPorIdSujetoObligado(idPatronSO);
			idPatronSOFusionado = patronFusionEntityLocal.getCveIdPatronGeneralPorIdSujetoObligado(idPatronSOFusionado);
			
			
			resultado = patronFusionEntityLocal.existeFusion(idPatronSO, idPatronSOFusionado);
			log.debug("el id del patron general es " + idPatronSO);
			log.debug("el id del patron general fusionado es " + idPatronSOFusionado);
		}
		return resultado;
	}

	@Override
	public void insertarPatronFusionado(SujetoObligado sujeto) {
		Long idPatronGeneral = null;
		if(sujeto != null && sujeto.getSujetosObligados() != null) {
			idPatronGeneral = patronFusionEntityLocal.getCveIdPatronGeneralPorIdSujetoObligado(sujeto.getCveIdSujetoObligado());
			for(SujetoObligado sujetoFusionado: sujeto.getSujetosObligados()) {
				Long idPatronGeneralFusionado = patronFusionEntityLocal.getCveIdPatronGeneralPorIdSujetoObligado(sujetoFusionado.getCveIdSujetoObligado());
				patronFusionEntityLocal.insertarFusion(idPatronGeneral, idPatronGeneralFusionado);
			}
		}
		
	}

	@Override
	public Boolean validarPatronExisteComoFusionado(Long idPatronAValidar) {
		Boolean existeComoFusionado = false;
		Long idPatronGeneral = null;
		if(idPatronAValidar != null) {
			idPatronGeneral = patronFusionEntityLocal.getCveIdPatronGeneralPorIdSujetoObligado(idPatronAValidar);
			existeComoFusionado = patronFusionEntityLocal.validarPatronExisteComoFusionado(idPatronGeneral);
		}
		
		return existeComoFusionado;
	}

}
