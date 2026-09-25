package mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.actividad.economica;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.MateriaPrima;
import mx.gob.imss.ctirss.delta.persistence.DitMateriaPrimaMaterial;

@Local
public interface MateriaPrimaServiceUtilityLocal {
	
	/**
	 * Transforma una entidad MateriaMaterial a un model DitMateriaPrimaMaterial.
	 * @param model Modelo a transformar.
	 * @return Entidad del tipo SmtMateriaPrimaMaterial.
	 */
	DitMateriaPrimaMaterial convertirModelToEntity(MateriaPrima model);
	
	/**
	 * Transforma una entidad SmtMateriaPrimaMaterial a un model MateriaMaterial.
	 * @param entity Entidad a transformar.
	 * @return Modelo del tipo MateriaMaterial.
	 */
	MateriaPrima convertirEntityToModel(DitMateriaPrimaMaterial entity) throws Exception;
	
	
	/**
	 * Transforma una lista de Entities de tipo SmtMateriaPrimaMaterial a una lista de Models de tipo MateriaMaterial
	 * @param origen Lista del tipo List <SmtMateriaPrimaMaterial> a transformar.
	 * @return una lista del tipo List <MateriaMaterial>.
	 * @throws Exception En caso de error.
	 */
	List <MateriaPrima> convertListOfEntitiesToListOfModel(List <DitMateriaPrimaMaterial> origen) throws Exception;
	
	/**
	 * Metodo que valida el limite maximo de registros capturados para Materia Prima
	 * @param materiaPrima Objeto Materia Prima.
	 * @throws MateriaPrimaLimiteMaxRegExcedidoException En caso de sobrepasar el limite maximo de registros permitidos.
	 */
	void validaLimMaxRegMateriaPrima(MateriaPrima materiaPrima) throws Exception;
	
	
	/**
	 * Método que valida si el registro a insertar ya existeo es invalido.
	 * @param materiaPrima Objeto materia prima a insertar.
	 * @return Objeto materia prima validado.
	 * @throws MateriaPrimaInvalidoException Excepcion si el objeto es invalido.
	 * @throws MateriaPrimaYaExisteException Excepcion si el objeto ya existe.
	 */
	MateriaPrima validaAgregarMateriaPrima(MateriaPrima materiaPrima) throws Exception;

	

	/**
	 * Método que realiza la validación del objeto materia prima.
	 * @param materiaPrima Objeto materia prima a borrar.
	 * @throws MateriaPrimaLimiteMinRegExcedidoException Lanzada en caso de no tener registros a borrar.
	 */
	void validaBorrarMateriaPrima(MateriaPrima materiaPrima) throws Exception;
	
	
	/**
	 * Convierte la lista de entidades de base de datos en una lista de entidades de modelo
	 * @author Hugo Martinez
	 * @Date 18/06/2012
	 * @param models
	 * @return List<DitMateriaPrimaMaterial>
	 */
	List<DitMateriaPrimaMaterial> convertirListOfModelToListOfEntities(List<MateriaPrima> models);

}
