package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.socios;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Socio;
import mx.gob.imss.ctirss.delta.persistence.DgCatEstado;

@Local
public interface SocioServiceEntityLocal {
	
	/**
	 * Obtener socios por IdPersonaMoralPatron
	 *  
	 * @param Socio
	 * @return List<Socio>
	 */
	List<Socio> obtenerSociosPorIdPersonaMoralPatron(Socio socio);
	
	/**
	 * Da de alta un socio (Fisico o Moral) asociado al patron.
	 * @param socio
	 * @return socio
	 */
	Socio altaSocio(Socio socio);
	
	/**
	 * Da de baja un socio.
	 * @param socio
	 */
	void bajaSocio(Socio socio);
	
	/**
	 * Obtiene el tipo de socio (Tipo persona: fisica, moral o fideicomiso) para la gestion de socios
	 * @param IdTipoPersona
	 * @return
	 */
	TipoPersona getTipoSocio(Long idTipoPersona);
	
	
	DgCatEstado getEstado(String cveEnt);
	
	/**
	 * Metodo para eliminar un Socio.
	 * @param socio
	 */
	@Deprecated
	void eliminarSocio(Socio socio);

	/**
	 * Metodo para agregar un socio fisico
	 */
	@Deprecated
	Socio agregarSocioFisico(Socio model);

	/**
	 * Metodo para agregar un socio Moral.
	 */
	@Deprecated
	Socio agregarSocioMoral(Socio model);
	
	/**
	 * Metodo para agregar un socio Fideicomiso.
	 * @param socio
	 * @return
	 */
	@Deprecated
	Socio agregarSocioFideicomiso(Socio socio);
	
	/**
	 * Modifica un socio fisico, moral o fideicomiso
	 * @param socio
	 */
	@Deprecated
	void modificarSocio(Socio socio);

	/**
	 * Metodo para modificar la informacion de un socio fisico.
	 * @param oForm
	 * @return
	 */
	@Deprecated
	Socio modificarSocioFisico(Socio oForm);

	/**
	 * Metodo para modificar la informacion de un socio moral.
	 * @param oForm
	 * @return
	 */
	@Deprecated
	Socio modificarSocioMoral(Socio oForm);
	
	@Deprecated
	Socio getSocio(Socio socio);

	
	/**
	 * MEtodo para obtener un socio fisico.
	 * @param socio
	 * @return
	 */
	@Deprecated
	Socio getSocioFisico(Socio socio);
	
	
	/**
	 * Metodo para obtener un socio moral.
	 * @param socio
	 * @return
	 */
	@Deprecated
	Socio getSocioMoral(Socio socio);
	
	/**
	 * Verifica si existe un socio fisico con la informacion dada
	 * @param socio
	 * @throws Exception
	 */
	@Deprecated
	void validaExisteSocioFisico(Socio socio) throws Exception;
	
	/**
	 * Verifica si existe un socio moral con la informacion dada
	 * @param socio
	 * @throws Exception
	 */
	@Deprecated
	void validaExisteSocioMoral(Socio socio) throws Exception;

	@Deprecated
	List<Socio> consultarSociosPorSujetoObligado(Socio model);
	
	/**
	 * 
	 * @param socio
	 * @return
	 */
	@Deprecated
	List<Socio> consultarSociosPorPerosna(Socio socio);
	
	/**
	 * Agrega un socio que puede ser Fisico, Moral o Fideicomiso 
	 * @param socio Engloba a un socio de distinto tipo (Moral, Fisico, Fideicomiso) dentro de todas sus variantes
	 * @return
	 */
	@Deprecated
	Socio agregarSocio(Socio socio);
	
	/**
	 * Verifica si este socio está relacionado a otro patrón de acuerdo a la
	 * regla de negocio RN-008-00-2: Un socio solo puede estar relacionado a un Patrón Persona Moral
	 * @param socio
	 * @return boolean
	 */
	@Deprecated
	boolean existenAsignacionesAnterioresSocioPatron(Socio socio);
	
	/**
	 * Inserta un socio extranjero con residencia en el extranjero
	 * @author Hugo Martinez
	 * @Date 07/05/2013
	 * @param socio información a insertar
	 * @return Socio
	 */
	@Deprecated
	Socio agregarSocioExtranjero(Socio socio);


		
}
