package mx.gob.imss.ctirss.delta.gestion.patronal.service.business.actividad.economica;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.actividad.economica.MateriaPrimaServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.actividad.economica.MateriaPrimaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.actividad.economica.MateriaPrimaServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MateriaPrima;

@Stateless(name="materiaPrimaServiceBusiness", mappedName="materiaPrimaServiceBusiness")
public class MateriaPrimaServiceBusiness extends AbstractServiceBusiness
		implements MateriaPrimaServiceBusinessRemote,
		MateriaPrimaServiceBusinessLocal {
	
	@EJB
	private MateriaPrimaServiceEntityLocal materiaPrimaServiceEntity;
	
	@EJB
	private MateriaPrimaServiceUtilityLocal materiaPrimaServiceUtility;

	@Override
	public void agregarMateriaPrima(MateriaPrima materiaPrima) throws Exception {
		// 1. validar si no excede el limite maximo de registros (10 en este
		// caso)
		this.materiaPrimaServiceUtility
				.validaLimMaxRegMateriaPrima(materiaPrima);

		// 2. Validamos si se puede agregar la materia prima
		this.materiaPrimaServiceUtility.validaAgregarMateriaPrima(materiaPrima);

		// 3. Agregamos la materia prima
		this.materiaPrimaServiceEntity.agrega(materiaPrima);
		
	}

	@Override
	public void eliminarMateriaPrima(MateriaPrima materiaPrima)
			throws Exception {
		this.materiaPrimaServiceEntity.elimina(materiaPrima);
	}

	@Override
	public void modificarMateriaPrima(MateriaPrima materiaPrima)
			throws Exception {
		
		this.materiaPrimaServiceUtility.validaAgregarMateriaPrima(materiaPrima);

		this.materiaPrimaServiceEntity.actualiza(materiaPrima);
		
	}

	@Override
	public MateriaPrima getMateriaPrima(MateriaPrima materiaPrima) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DatosSalidaPaginador<MateriaPrima> paginarMateriasPrimas(
			DatosEntradaPaginador<MateriaPrima> datatablein) {
		DatosSalidaPaginador<MateriaPrima> out = new DatosSalidaPaginador<MateriaPrima>();
		out = this.materiaPrimaServiceEntity.consultarMateriaPrima(datatablein);
		return out;
	}


}
