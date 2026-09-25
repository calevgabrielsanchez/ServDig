package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.contacto;

import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateless;
import org.hibernate.criterion.Restrictions;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.persistence.DitCentroTrabajoContacto;
import mx.gob.imss.ctirss.delta.persistence.DitCentroTrabajoContactoPK;

@Stateless
public class FormaContactoServiceEntity extends AbstractServiceEntity 
									implements FormaContactoServiceEntityLocal{

	@Override
	public void asociarMediosContactoACentroTrabajo(
			List<MedioContacto> mediosContacto, Long idPatronSujetoObligado) {
		for (MedioContacto mc : mediosContacto) {
				DitCentroTrabajoContacto ctc = new DitCentroTrabajoContacto();
				DitCentroTrabajoContactoPK id = new DitCentroTrabajoContactoPK();
				id.setCveIdFormaContacto(mc.getClave());
				id.setCveIdPatronSujetoObligado(idPatronSujetoObligado);
				ctc.setId(id);
				ctc.setFecRegistroAlta(Calendar.getInstance().getTime());
				ctc.setFecRegistroActualizado(Calendar.getInstance().getTime());
				this.getSession().save(ctc);
			}
	}

	@Override
	public void eliminarMediosContactoCentroTrabajo(Long idPatronSujetoObligado) {
		@SuppressWarnings("unchecked")
		List<DitCentroTrabajoContacto> ditCentroTrabajoContactoList=this.getSession().createCriteria(DitCentroTrabajoContacto.class)			 
		 .add(Restrictions.eq("ditPatronSujetoObligado.cveIdPatronSujetoObligado",idPatronSujetoObligado)).list();
		for (DitCentroTrabajoContacto ditCentroTrabajoContactoListTmp : ditCentroTrabajoContactoList){
			this.getSession().delete(ditCentroTrabajoContactoListTmp);
		}		
	}
	
}
