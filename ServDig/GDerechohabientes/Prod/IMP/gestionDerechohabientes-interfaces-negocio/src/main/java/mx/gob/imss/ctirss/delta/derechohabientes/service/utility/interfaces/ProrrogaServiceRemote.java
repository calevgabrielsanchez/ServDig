/**
 * 
 */
package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Acta;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Acuerdo;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.ConstanciaEstudio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DictamenIntegranteIncapacitado;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Obstetrico;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.VigenciaTemporal;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;

/**
 * @author ghdolores
 *
 */


@Remote
public interface ProrrogaServiceRemote {
	
	public Solicitud saveProrrogaEstudios(ConstanciaEstudio constancia,GrupoFamiliar grupoFamiliar, Usuario usuario, Long idOrigenSolicitud) throws DerechohabientesBusinessException, Exception;
	public Solicitud saveProrrogaAcuerdos(Acuerdo acuerdo,GrupoFamiliar grupoFamiliar, Usuario usuario, Long idOrigenSolicitud) throws DerechohabientesBusinessException, Exception;
	public Solicitud saveProrrogaLaudos(Acuerdo acuerdo,GrupoFamiliar grupoFamiliar, Usuario usuario, Long idOrigenSolicitud) throws DerechohabientesBusinessException, Exception;
	public Solicitud saveProrrogaVigenciaTemporal(VigenciaTemporal pension,GrupoFamiliar grupoFamiliar, Usuario usuario, Long idOrigenSolicitud) throws DerechohabientesBusinessException, Exception;
	public Solicitud saveProrrogaServiciosObstetricos(Obstetrico prorroga,GrupoFamiliar grupoFamiliar, Usuario usuario, Long idOrigenSolicitud) throws DerechohabientesBusinessException, Exception;
	public Solicitud saveProrrogaVigenciaPermanente(Acta acta,GrupoFamiliar grupoFamiliar, Usuario usuario, Long idOrigenSolicitud) throws DerechohabientesBusinessException, Exception;
	public Solicitud saveProrrogaEnfermedad(DictamenIntegranteIncapacitado prorroga,GrupoFamiliar grupoFamiliar, Usuario usuario, Long idOrigenSolicitud) throws DerechohabientesBusinessException, Exception;

    /**
     * Valida si el integrante enviado aplica para una prorroga por estudios para el derechohabiente dado
     * @param integrante Integrante a validad
     * @param idAsignacioNSS dato unico del derechohabiente
     * @param usuario Usuario quién realiza el trámite
     * @return
     * @throws DerechohabientesBusinessException
     * @throws Exception
     */
    public Map<String,Object> validateProrrogaEstudios(GrupoFamiliar integrante, Long idAsignacioNSS, Usuario usuario)throws DerechohabientesBusinessException, Exception;

    public List<GrupoFamiliar> getCandidatosProrroga(CabezaGrupoFamiliar cabeza, GrupoFamiliar grupoFamiliar, Long tipoProrroga, Long idAsignacioNSS, Usuario usuario, boolean saltaSolicitudes) throws DerechohabientesBusinessException, Exception;
	public GrupoFamiliar getIntegranteProrroga(Long TipoProrroga, Long idPersona, Long idUMFTramite, AsignacionNSS idAsignacionNss, Boolean autorizacion) throws DerechohabientesBusinessException, Exception;
	public Tramite validaTramite(Solicitud solicitud, AsignacionNSS nss, Usuario usuario) throws DerechohabientesBusinessException, Exception;
	public void actalizaProrroga(Solicitud prorroga) throws Exception;
	
	//metodo que obtiene la cabeza grupo familiar
	public CabezaGrupoFamiliar getCabezaGrupoFamiliar(Long idAsignacioNSS) throws DerechohabientesBusinessException;
	
	/*
	 * Metodo para el registro de tramite de prorroga desde el portal
	 */
	/**
	 * Este metodo es usado para crear un tramite y solictud de tipo prorroga.
	 * Actualmente es usado desde WizardProrrogaDerechohabienteController 
	 * @author juan.osorioal
	 * @param idDerechohabiente
	 * @param usuario
	 * @param aseguradoPensionado
	 * @param origen
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	public Solicitud guardaTramiteProrroga(Long idDerechohabiente,Usuario usuario, AsignacionNSS aseguradoPensionado, OrigenSolicitudEnum origen, 
			mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum tipoProrroga) throws DerechohabientesBusinessException;
	
	/**
	 * Metodo para finalizar una solicitud de prorroga
	 * @param solicitud - Solicitud , el objeto debe contener al menos el id de la solicitud
	 * @throws SolicitudNoValidaException
	 * @throws SolicitudNoEncontradaException
	 * @throws SolicitudException
	 */
	public Solicitud finalizarSolicitudProrroga(Solicitud solicitud)
			throws SolicitudNoValidaException, SolicitudNoEncontradaException, SolicitudException;
	
	 /**
	  * Metodo encargado de validar la prorroga por estudios y las regla de negocio relacionadas con prorrogas anteriores y vigencia
	  * @param integrante
	  * @param idAsignacioNSS
	  * @return
	  * @throws DerechohabientesBusinessException
	  * @throws Exception
	  */
	 String validaIntegrantePrrogaEstudiosTSPI(GrupoFamiliar integrante, Long idAsignacioNSS) throws DerechohabientesBusinessException, Exception;
}
