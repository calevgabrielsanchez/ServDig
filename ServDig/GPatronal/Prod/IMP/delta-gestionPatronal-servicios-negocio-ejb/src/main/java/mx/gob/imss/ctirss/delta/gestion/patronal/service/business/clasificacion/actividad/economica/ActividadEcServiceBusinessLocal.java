package mx.gob.imss.ctirss.delta.gestion.patronal.service.business.clasificacion.actividad.economica;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Documento;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

@Local
public interface ActividadEcServiceBusinessLocal {
	
	Clasificacion obtenerClasificacionPorSujetoObligado(Long cveIdSujetoObligado) throws GestionPatronalBusinessException;
	void asignarNuevaClasificacion(Clasificacion clasificacion, Long cveCausa) throws GestionPatronalBusinessException;
	/**
	 * Obtiene la solicitud asociada al tr�mite de de tipo clasificacion
	 * @author Hugo Martinez
	 * @Date 14/06/2012
	 * @param cveIdPatronSujetoObligado
	 * @return
	 */
	SujetoObligado obtenerClasificacionEnTramitePorPatron(Long cveIdPatronSujetoObligado);
	
	/**
	 * Se almacena la clasificaci�n anterior en el detalle del tr�mite y guarda la nueva clasificaci�n y procesos, personal,
	 * bienes, productos, equipo, transporte, materia prima material, procesos, actividades complementarias.
	 * @author Hugo Martinez
	 * @Date 18/06/2012
	 * @param SujetoObligado
	 * @param idSolicitud
	 * @return SujetoObligado
	 */
	SujetoObligado afectarClasificacionActividadEconomica(SujetoObligado so, Solicitud solicitud)throws GestionPatronalBusinessException;
	
	/**
	 * Se almacena la clasificaci�n anterior en el detalle del tr�mite y guarda la nueva clasificaci�n y procesos, personal,
	 * bienes, productos, equipo, transporte, materia prima material, procesos, actividades complementarias.
	 * @author Hugo Martinez
	 * @Date 18/06/2012
	 * @param SujetoObligado
	 * @param idSolicitud
	 * @return SujetoObligado
	 */
	SujetoObligado afectarClasificacionActividadEconomica(SujetoObligado so, Solicitud solicitud, SujetoObligado soActual)throws GestionPatronalBusinessException;
	
	/**
	 * Se almacena la clasificaci�n anterior en el detalle del tr�mite y guarda la nueva clasificaci�n y procesos, personal,
	 * bienes, productos, equipo, transporte, materia prima material, procesos, actividades complementarias.
	 * @author Hugo Martinez
	 * @Date 18/06/2012
	 * @param SujetoObligado
	 * @param idSolicitud
	 * @return SujetoObligado
	 */
	SujetoObligado concluirClasificacionActividadEconomica(SujetoObligado so, Solicitud solicitud)throws GestionPatronalBusinessException;
	
	/**
	 * Afecta la clasificaci�n (fracci�n asociada al patr�n) asi como la bit�cora
	 * de clasificaci�n
	 * @author Hugo Martinez
	 * @Date 07/03/2013
	 * @param clasificacion
	 */
	void actualizarClasificacion(Clasificacion clasificacion, Integer cveCausa, Integer tpoMovimiento)throws GestionPatronalBusinessException;
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 08/03/2013
	 * @param noFolio
	 * @param so
	 * @param cveCausa
	 * @param cveAplicacion
	 * @param tipoMovto
	 * @param origenMvto
	 */
	void ejecutarProcesoSincronizacionSINDO(String noFolio, SujetoObligado so, Date fechaEfecto, Long cveCausa, Integer cveAplicacion, Integer tipoMovto, Integer origenMvto);
	
	/**
	 * Metodo que genera los documentos de acuso y  ASMRT para los tramites de calasifiacion de empresas
	 * consulta si ya existe el registro en base de datos para recuperarlo y si no lo genera
	 * @param solicitud
	 * @return
	 * @throws GestionPatronalBusinessException
	 */
	byte[] obtenerDoctosResultantesModificacionSRTVentanilla(Solicitud solicitud) throws GestionPatronalBusinessException;
	
}
