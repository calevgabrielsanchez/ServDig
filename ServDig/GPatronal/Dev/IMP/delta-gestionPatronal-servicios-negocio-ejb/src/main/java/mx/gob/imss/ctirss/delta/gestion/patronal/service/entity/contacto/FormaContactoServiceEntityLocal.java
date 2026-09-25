/**
 * @author Hugo Martinez
 */
package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.contacto;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;

@Local
public interface FormaContactoServiceEntityLocal {
	
	/**
	 * Asocia la lista de medios de contacto proporcionada al patron con el
	 * identificador recibido como parametro
	 * @author Hugo Martinez
	 * @Date 24/09/2012
	 * @param mediosContacto
	 * @param idPatronSujetoObligado
	 */
	void asociarMediosContactoACentroTrabajo(List<MedioContacto> mediosContacto, Long idPatronSujetoObligado);
	
	/**
	 * Elimina todos los medios de contacto de un patron sujeto obligado
	 * @author Hugo Martinez
	 * @Date 24/09/2012
	 * @param idPatronSujetoObligado
	 */
	void eliminarMediosContactoCentroTrabajo(Long idPatronSujetoObligado);
}
