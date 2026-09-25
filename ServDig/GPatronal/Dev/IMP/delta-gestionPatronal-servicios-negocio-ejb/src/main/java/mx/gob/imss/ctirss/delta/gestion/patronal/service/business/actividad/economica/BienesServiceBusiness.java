package mx.gob.imss.ctirss.delta.gestion.patronal.service.business.actividad.economica;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.actividad.economica.BienesServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.actividad.economica.BienesServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Bien;

@Stateless(name="bienesServiceBusiness", mappedName="bienesServiceBusiness")
public class BienesServiceBusiness extends AbstractServiceBusiness implements
		BienesServiceBusinessLocal, BienesServiceBusinessRemote {
	
	@EJB
	private BienesServiceEntityLocal bienesServiceEntity;

	@Override
	public Bien agregarBien(Bien bien) throws Exception {
		validaBienExistente(bien);
		this.bienesServiceEntity.agregar(bien);
		return bien;
	}

	@Override
	public void eliminarBien(Bien bien) throws Exception {
		this.bienesServiceEntity.eliminar(bien);
		
	}

	@Override
	public Bien getBien(Bien bien) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bien modificarBien(Bien bien) throws Exception {
		validaBienExistente(bien);
		return this.bienesServiceEntity.actualizar(bien);
	}

	@Override
	public DatosSalidaPaginador<Bien> paginarBien(
			DatosEntradaPaginador<Bien> datatablein) {
		return this.bienesServiceEntity.paginar(datatablein);
	}
	
	
	private void validaBienExistente(Bien bien) throws GestionPatronalBusinessException{
		Bien cBien = bienesServiceEntity.findBienByDescription(bien);
		
		if(cBien!=null){
			//Para registros a insertar
			if(bien.getId()==null){
				throw new GestionPatronalBusinessException("Actualmente se tiene registrado un bien con la descripción proporcionada");
			}else if(!cBien.getId().equals(bien.getId())){
				//Para actualizar registros
				throw new GestionPatronalBusinessException("Actualmente se tiene un bien con la misma descripción con la cual solcita actualizar el registro");
			}
		}
	}

}
