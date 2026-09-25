package mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rep.legal;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.gestion.patronal.RepresentanteLegalInvalidoException;
import mx.gob.imss.ctirss.delta.exception.gestion.patronal.RepresentanteLegalYaExisteException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;

@Remote
public interface RepresentanteLegalServiceBusinessRemote {
	
	/**
	 * Metodo para saber si una persona esta registrada como representante legal
	 * @param idPersona
	 * @return
	 */
	Boolean isRepresentanteLegal(Long idPersona);
	
	DatosSalidaPaginador<RepresentanteLegal> paginarRepresentanteLegal( DatosEntradaPaginador<RepresentanteLegal> datatablein);
	
	DatosSalidaPaginador<RepresentanteLegal> paginarRepresentanteLegalParaManejoDeTramite( DatosEntradaPaginador<RepresentanteLegal> datatablein);
	
	RepresentanteLegal getRepresentanteLegal(RepresentanteLegal representanteLegal);
	
	void agregarRepresentanteLegal(RepresentanteLegal representanteLegal) throws RepresentanteLegalInvalidoException, RepresentanteLegalYaExisteException;
	
	void eliminarRepresentanteLegal(RepresentanteLegal representanteLegal, String tipoPersonaFiscal) throws GestionPatronalBusinessException;
	
	void modificarRepresentanteLegal(RepresentanteLegal representanteLegal)  throws RepresentanteLegalInvalidoException , RepresentanteLegalYaExisteException;
	
	/**
	 * Se obtienen el representante legales asociados a un id_patron_sujeto_obligado
	 * @author Hugo Armando Martínez Chamónica
	 * @return List<RepresentanteLegal>
	 */
	List<RepresentanteLegal> obtenerRepresentanteLegalPorSujetoObligado(Long idPatronSujetoObligado);
	
	/**
	 * Se obtiene personas por CURP
	 * @return Fisica
	 */	
	Fisica getPersonaByCurp(String curp);
	
	/**
	 * Se obtienen el representante legales asociados a un RFC
	 * @return List<RepresentanteLegal>
	 */	
	List<RepresentanteLegal> obtenerRepresentantesPorRFCMoral(String rfc);
	
	
	/**
	 * segmento para validaciones
	 */
	void validaExisteRepresentanteLegal(RepresentanteLegal representanteLegal) throws RepresentanteLegalInvalidoException, RepresentanteLegalYaExisteException;
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 26/06/2012
	 * @param representantes
	 */
	void actualizarRepresentantesLegales(Long cveIdSolicitud, List<RepresentanteLegal> representantes,
		OrigenSolicitudEnum origenSolicitud);
	
	
	//METODOS ACTUALES... SE DEBEN ELIMINAR LOS METODOS ANTERIORES
	
	/**
	 * Gestiona la actualización del trámite de representantes legales agregando, o eliminando registros
	 * de la lista de representantes legales en el tramite.
	 * @author Hugo Martinez
	 * @Date 30/07/2012
	 * @param representante
	 * @param tipoOperacion
	 */
	String gestionarTramiteRepresentanteLegal(Long idSolicitud, RepresentanteLegal representante, Usuario usuario) throws GestionPatronalBusinessException;
	
	/**
	 * Elimina un movimiento de representante legal
	 * en base al identificador de persona del representante
	 * @author Hugo Martinez
	 * @Date 31/07/2012
	 * @param representante
	 */
	void eliminarMovimientosDelTramite(Long idSolicitud, RepresentanteLegal representante, Usuario usuario);
	
	/**
	 * Lista los movimientos del tramite de representante legal
	 * @author Hugo Martinez
	 * @Date 02/08/2012
	 * @return List<RepresentanteLegal>
	 */
	DatosSalidaPaginador<RepresentanteLegal> obtenerMovimientosDeTramite( DatosEntradaPaginador<RepresentanteLegal> datatablein, Usuario usuario, Long idSolicitud);
	
	RepresentanteLegal obtenerRLporIdPersonaFisica(Long cveIdPersona, TipoPersonaEnum tipoPersona) throws Exception;
	
	/**
	 * Obtiene los datos del representante legal firmado en base al patrón proporcionado
	 * @author Hugo Martinez
	 * @Fecha: 17/01/2013 11:18:40
	 */
	RepresentanteLegal obtenerRLporTipoPatronYIdPersonaRepresentante(Long cveIdPersonaRepresentada, TipoPersonaEnum tipoPersona, Long idPersonaRepresentante) throws Exception;
	
	/**
	 * Verifica si el representante representa a la persona proporcionada
	 * @author Hugo Martinez
	 * @Date 30/01/2013
	 * @param cveIdPersonaRepresentante
	 * @param cveIdPersonaRepresentanda
	 * @param tipoPersonaRepresentanda
	 * @return
	 */
	boolean esRepresentanteDePersona(Long cveIdPersonaRepresentante, Long cveIdPersonaRepresentanda, TipoPersonaEnum tipoPersonaRepresentanda);
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 02/10/2012
	 * @param cveIdPersona
	 * @param tipoPersona
	 * @return 
	 */
	List<RepresentanteLegal> obtenerRepresentantesLegalesPorPersona(Long cveIdPersona, TipoPersonaEnum tipoPersona);

	/**
	 * Obtiene los representantes legales asociados al patrón que poseen actos de administración y dominio
	 * @author Hugo Martinez
	 * @Date 06/02/2013
	 * @param cveIdPersona
	 * @param tipoPersona
	 * @return
	 */
	List<RepresentanteLegal> obtenerRepresentantesLegalesConActosAdmonPorPersona(
			Long cveIdPersona, TipoPersonaEnum tipoPersona);
	
	/**
	 * Obtiene la lista de personas representadas por el representante legal proporcionado
	 * La lista contiene información b&aacute;sica de cada persona: nombres, apellidos,
	 * denominaci&oacute;n raz&oacute;n social, nombre comercial, etc.
	 * @param cveIdPersona Identificador de persona del representante legal
	 * @return List<Persona>
	 */
	List<Persona> obtenerPersonasRepresentadasPorRepresentanteLegal(Long cveIdPersona);
	
	/**
	 * Obtiene la lista de Representados por el id de a persona que es el representante
	 * @param idPersona
	 * @return
	 */
	List<RepresentanteLegal> obtenerRepresentadosPorIdPersona(Long idPersona); 
	
	/**
	 * Finaliza la solicitud asumiendo que la información almacenada actualmente en la solicitud es la que requiere ser 
	 * impactada en la base de datos
	 * @param idSolcitud Identificador de la solicitud
	 * @throws SolicitudNoEncontradaException
	 * @throws TramiteNoEncontradoException
	 * @throws SolicitudException
	 */
	void concluirSolicitudBajaRepresentanteLegal(Long idSolcitud) throws SolicitudNoEncontradaException, TramiteNoEncontradoException, SolicitudException;
	
	void concluirSolicitudBajaRepresentanteLegal(Solicitud solicitud) throws SolicitudNoEncontradaException, TramiteNoEncontradoException, SolicitudException;
	
	/**
	 * Actualiza la base de datos delta con la información contenida en el tramite de baja de representantes legales
	 * @param tramite (TramiteFisica, TramiteMoral)
	 * @throws GestionPatronalBusinessException
	 */
	void afectarTramiteBajaRepresentantesLegales(Tramite tramite) throws GestionPatronalBusinessException;
	
	void agregarRepresentantesLegalesARegistroPatronal(List<RepresentanteLegal> representantesLegales);
	
	void agregarRepresentanteLegalARegistroPatronal(RepresentanteLegal representantLegal);
	
	void altaRepresentanteLegal(RepresentanteLegal representanteLegal) throws GestionPatronalBusinessException;
	void existeRelacionRepresentanteLegalPorIdentificadores(RepresentanteLegal representanteLegal) throws RepresentanteLegalYaExisteException;
	Fisica localizarRL(Fisica rl)  throws GestionPatronalBusinessException;
	
	List<Persona> obtenerPersonasRepresentadasPorRepresentanteLegalDIC(Long cveIdPersona);
}
