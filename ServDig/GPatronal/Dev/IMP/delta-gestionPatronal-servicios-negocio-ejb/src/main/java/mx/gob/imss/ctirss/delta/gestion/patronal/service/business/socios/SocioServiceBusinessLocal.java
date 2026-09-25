package mx.gob.imss.ctirss.delta.gestion.patronal.service.business.socios;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Socio;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;

@Local
public interface SocioServiceBusinessLocal {
	
	/**
	 * Obtiene la lista de socios a paginar por medio del idPersonaMoralPatron.
	 * 
	 * @param DatosEntradaPaginador<Socio>
	 * @return DatosSalidaPaginador<Socio>
	 */
	DatosSalidaPaginador<Socio> paginarSocios(
			DatosEntradaPaginador<Socio> datatablein);

	/**
	 * Obtiene socios acorde al IdPersonaMoralPatron.
	 * 
	 * @param socio
	 * @return List Socios
	 */
	List<Socio> sociosPorIdPersonaMoralPatron(Socio socio);
	
	/**
	 * Metodo para dar de alta socios.
	 * @param tramite
	 * @param idSolicitud
	 * @throws GestionPatronalBusinessException
	 */
	void afectarTramiteAltaSocios(Tramite tramite, Long idSolicitud) throws GestionPatronalBusinessException;
	
	
	Socio localizarSocioAlta(Socio socio)throws GestionPatronalBusinessException;
	
	/**
	 * Metodo para eliminar un Socio.
	 * @param socio
	 */
	@Deprecated
	void eliminarSocio(Socio socio);

	/**
	 * Metodo para agregar un socio
	 * @param oForm
	 * @return 
	 * @throws Exception 
	 */
	@Deprecated
	Socio agregarSocio(Socio oForm) throws Exception;

	/**
	 * Metodo para modificar la informacion de un socio
	 * @param oForm
	 * @return
	 */
	@Deprecated
	Socio modificarSocio(Socio oForm);


	/**
	 * Metodo para obtener un socio.
	 * @param socio
	 * @return
	 */
	@Deprecated
	Socio getSocio(Socio socio);
	
	/**
	 * 
	 * @author Un wey
	 * @Date 26/06/2012
	 * @param representantes
	 */
	@Deprecated
	void actualizarSocios(List<Socio> socios);

	@Deprecated
	List<Socio> obtenerSociosPorSujetoObligado(Long cveIdSujetoObligado);
	
	/**
	 * Lista los movimientos del tramite de socios
	 * @author Un wey
	 * @return List<Socio>
	 */
	@Deprecated
	DatosSalidaPaginador<Socio> obtenerMovimientosDeTramite( DatosEntradaPaginador<Socio> datatablein, Usuario usuario, Long idSolicitud);
	
	

	
}
