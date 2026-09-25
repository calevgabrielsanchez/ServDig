package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.clasificacion.actividad.economica;

import java.math.BigDecimal;
import java.util.Date;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.persistence.DitDtsExtraPatron;

@Stateless(name = "datosExtrasPatronEntity", mappedName = "datosExtrasPatronEntity")
public class DatosExtrasPatronEntity extends AbstractServiceEntity implements
		DatosExtrasPatronEntityLocal {

	
	@Override
	public void setTipoMovimientoPatron(Long cveIdPatronGeneral, Integer tipoMovimiento) {
		if(cveIdPatronGeneral != null) {
			DitDtsExtraPatron ditDtsPatron = this.em.find(DitDtsExtraPatron.class, cveIdPatronGeneral);
			if(ditDtsPatron != null) {
				ditDtsPatron.setCveTipoMovto(new BigDecimal(tipoMovimiento));
				ditDtsPatron.setFecRegistroActualizado(new Date());
				ditDtsPatron.setFecMovto(new Date());
			}
			
			this.em.merge(ditDtsPatron);
		}
	}

}
