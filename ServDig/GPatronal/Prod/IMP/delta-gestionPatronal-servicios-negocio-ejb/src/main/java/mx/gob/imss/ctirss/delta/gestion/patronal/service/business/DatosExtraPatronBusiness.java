package mx.gob.imss.ctirss.delta.gestion.patronal.service.business;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractService;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.clasificacion.actividad.economica.DatosExtrasPatronEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.fusionsust.PatronFusionEntityLocal;
import mx.gob.imss.ctirss.delta.model.enums.TipoMovtoPatSujObligEnum;

@Stateless(name = "datosExtraPatronBusiness", mappedName = "datosExtraPatronBusiness")
public class DatosExtraPatronBusiness extends AbstractService implements DatosExtraPatronBusinessLocal {
	
	@EJB
	PatronFusionEntityLocal patronFusionEntityLocal;
	@EJB
	DatosExtrasPatronEntityLocal datosExtrasPatronEntityLocal;
	
	@Override
	public void ejecutarReanudacionActividades(Long cveIdPatronSujetoObligado) {
		Long cveIdPatronGenel = patronFusionEntityLocal.getCveIdPatronGeneralPorIdSujetoObligado(cveIdPatronSujetoObligado);
		
		if(cveIdPatronGenel != null) {
			datosExtrasPatronEntityLocal.setTipoMovimientoPatron(cveIdPatronGenel, TipoMovtoPatSujObligEnum.RESTABLECIMIENTO_PATRONAL.getId());
		}
		
	}

}
