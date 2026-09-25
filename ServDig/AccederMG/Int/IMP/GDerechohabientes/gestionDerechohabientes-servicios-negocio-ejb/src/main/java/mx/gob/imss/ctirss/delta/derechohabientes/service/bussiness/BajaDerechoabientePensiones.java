package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.lang.StringUtils;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.BajaDerechoabientePensionesRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ImpactaAlmacenesWSException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.TipoBajaDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.BajaDerechohabienteDto;
import mx.gob.imss.ctirss.delta.model.enums.EstadoDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteBajaDerechohabiente;

@Stateless(name = "bajaDerechoabientePensiones", mappedName = "bajaDerechoabientePensiones")
public class BajaDerechoabientePensiones extends AbstractServiceBusiness implements BajaDerechoabientePensionesRemote {
	
	@EJB
	private BajaDerechohabienteServiceLocal bajaDerechohabienteServiceLocal;
	@EJB
	private GrupoFamiliarServiceLocal grupoFamiliarServiceLocal;
	@EJB
	private TramiteServiceLocal tramiteServiceLocal;

	/**
	 * Metodo para obtener las bajas activas de un beneficiario
	 * @param idAsignacionNSS - La cve del nss
	 * @param idIntegrante - El id del beneficiario
	 */
	@Override
	public List<Long> findBajasActivasBeneficiario(Long idAsignacionNSS,
			Long idIntegrante) throws DerechohabientesBusinessException {
		
		return this.findBajasPorDerechohabiente(idAsignacionNSS, idIntegrante, null);
	}
	
	@Override
	public GrupoFamiliar validarBajaDerechohabientes(Long idAsignacionNSS, Long idIntegrante, Long tipoBaja) throws DerechohabientesBusinessException {
		GrupoFamiliar candidato = null;
		String codigo = null;
		String error = null;
		Long idParentesco = null;
		
		if(idAsignacionNSS == null || idIntegrante == null || tipoBaja == null) {
			DerechohabientesBusinessException.throwException("Los datos para la validacion estan incompletos", "001");
		}
		
		if(tipoBaja.intValue() <= 0 || tipoBaja.intValue() > 4) {
			DerechohabientesBusinessException.throwException("No existe el tipo de baja solicitado", "002");
		}
		
		try {
			candidato = grupoFamiliarServiceLocal.getIntegranteGrupoFamiliarPorIdPersona(idAsignacionNSS, idIntegrante);
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
		} 
		
		if(candidato == null){
			codigo = "003";
			error = "La persona no existe dentro del grupo familiar";
			//Verificamos que la persona tenga un estado de vigencia
		} else if(candidato.getEstadoDerechohabiente() != null && candidato.getEstadoDerechohabiente().getIdEstadoDerechohabiente() != null) {
			//obtenemos el parentesco para verificar si se tiene el parentesco
			idParentesco = candidato.getParentesco().getIdParentesco();
			//Verificamos que el candidato no este dado de baja
			if(!candidato.getEstadoDerechohabiente().getIdEstadoDerechohabiente().equals(EstadoDerechohabienteEnum.BAJA.getId())) {
				codigo = "005";
				error = "La persona no cuenta con el estado de vigencia adecuado.";
			}else if(((tipoBaja.equals(TipoBajaDerechohabienteEnum.DIVORCIO.getId()) && !idParentesco.equals(ParentescoEnum.CONYUGE.getId()))
				|| (tipoBaja.equals(TipoBajaDerechohabienteEnum.TERMINO_CONCUBINATO.getId()) && !idParentesco.equals(ParentescoEnum.CONCUBINARIO.getId()))
				|| (tipoBaja.equals(TipoBajaDerechohabienteEnum.TERMINO_CONVIVENCIA.getId()) && !idParentesco.equals(ParentescoEnum.PADRES.getId())))) {
				//verificamos que el tipo de baja sea al adecuado dependiaendo del parentesco que tenga la persona dentro del grupo familiar
				codigo = "006";
				error = "El tipo de baja proporcionado no es aplicable al candidato.";
			} else {
				List<Long> tiposBaja = new ArrayList<Long>();
				
				//Siempre verificaremos si se tiene una baja por defuncion
				tiposBaja.add(TipoBajaDerechohabienteEnum.DEFUNCION.getId());
				//Dependiendo del tipo de baja verificaremos si ya se tiene una del mismo tipo o una de defuncion
				if(tipoBaja.equals(TipoBajaDerechohabienteEnum.DIVORCIO.getId())){
					tiposBaja.add(TipoBajaDerechohabienteEnum.DIVORCIO.getId());
				} else if(tipoBaja.equals(TipoBajaDerechohabienteEnum.TERMINO_CONVIVENCIA.getId())) {
					tiposBaja.add(TipoBajaDerechohabienteEnum.TERMINO_CONVIVENCIA.getId());
				} else if(tipoBaja.equals(TipoBajaDerechohabienteEnum.TERMINO_CONCUBINATO.getId())) {
					tiposBaja.add(TipoBajaDerechohabienteEnum.TERMINO_CONCUBINATO.getId());
				}
				
				//Verificamos si tenemos alguna baja de derechohabiente
				List<Long> bajasActivas = this.findBajasPorDerechohabiente(idAsignacionNSS, idIntegrante, tiposBaja);
				
				//retornamos error indicando que se encontro una baja activa para el candidato
				if(bajasActivas != null && !bajasActivas.isEmpty()) {
					codigo = "007";
					error = "La persona ya cuenta con una baja activa (";
					for(Long baja: bajasActivas) {
						error += " "+baja+" ";
					}
					error += ")";
				}
			}
		} else {
			codigo = "004";
			error = "No fue posible localizar la vigencia del candidato.";
		}
		
		if(codigo != null) {
			DerechohabientesBusinessException.throwException(error, codigo);
		}
		
		return candidato;
	}

	@Override
	public Solicitud finalizarSolicitudBaja(Long idAsignacionNSS,
			Long idIntegrante, Long tipoBaja, String usuario, String observaciones,
			Date fechaDefuncion) throws DerechohabientesBusinessException {
		
		Solicitud solicitud = null;
		
		if(idAsignacionNSS == null || idIntegrante == null || tipoBaja == null || StringUtils.isBlank(observaciones) || (tipoBaja.equals(TipoBajaDerechohabienteEnum.DEFUNCION.getId()) && fechaDefuncion== null)) {
			DerechohabientesBusinessException.throwException("Los datos para la finalizacion estan incompletos", "001");
		}
		
		if(tipoBaja.equals(TipoBajaDerechohabienteEnum.DEFUNCION.getId()) && fechaDefuncion.getTime() > (new Date()).getTime()){
			DerechohabientesBusinessException.throwException("La fecha de defuncion no puede ser mayor al dia de hoy", "008");
		}
		
		GrupoFamiliar candidato = this.validarBajaDerechohabientes(idAsignacionNSS, idIntegrante, tipoBaja);
		TipoTramiteEnum tipo = null;
		
		if(tipoBaja.equals(TipoBajaDerechohabienteEnum.DEFUNCION.getId())) {
			tipo = TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_DEFUNCION;
		} else if(tipoBaja.equals(TipoBajaDerechohabienteEnum.DIVORCIO.getId())) {
			tipo = TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_DIVORCIO;
		} else if(tipoBaja.equals(TipoBajaDerechohabienteEnum.TERMINO_CONCUBINATO.getId())) {
			tipo = TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_TERMINO_DE_CONCUBINATO;
		} else if(tipoBaja.equals(TipoBajaDerechohabienteEnum.TERMINO_CONVIVENCIA.getId())) {
			tipo = TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_TERMINO_DE_CONVIVENCIA;
		} else {
			DerechohabientesBusinessException.throwException("No existe el tipo de baja solicitado", "002");
		}
		
		Usuario usuarioIMSS = new Usuario();
		usuarioIMSS.setUsuario(usuario);
		
		TramiteBajaDerechohabiente tramiteBaja =  new TramiteBajaDerechohabiente();
		tramiteBaja.setPersona(candidato.getDerechohabiente());
		tramiteBaja.setIdAsignacionNSS(candidato.getAsignacionNSS().getIdAsignacionNSS());
		tramiteBaja.setFechaDefuncion(fechaDefuncion);
		tramiteBaja.setObservacion(observaciones);
		tramiteBaja.setObservaciones(observaciones);
		tramiteBaja.setResultado(true);
		tramiteBaja.setFechaConclusion(new Date());
		
		
		try {
			solicitud = tramiteServiceLocal.guardarSolicitudEstado(candidato, tipo, usuarioIMSS, candidato.getAsignacionNSS(), TipoSolicitudEnum.BAJA_DERECHOHABIENTES, tramiteBaja, OrigenSolicitudEnum.VENTANILLA_TSPI, EstadoSolicitudEnum.ATENDIDA.getCodigo().longValue(), 
					EstadoTramiteEnum.CERRADO.getCodigo().longValue());
		} catch (Exception e) {
			e.printStackTrace();
			DerechohabientesBusinessException.throwException("No fue posible realiza registrar la solicitud de baja.", "009");
		}
		
		try {
			solicitud = bajaDerechohabienteServiceLocal.finalizarBajaSinConsultaSolicitud(solicitud);
		} catch (SolicitudNoValidaException e) {
			e.printStackTrace();
			DerechohabientesBusinessException.throwException("La solicitud no contien datos validos.", "010");
		} catch (SolicitudNoEncontradaException e) {
			e.printStackTrace();
			DerechohabientesBusinessException.throwException("No se encontro la solicitud a finalizar", "011");
		} catch (SolicitudException e) {
			e.printStackTrace();
			DerechohabientesBusinessException.throwException("Ocurrio un error al finalizar la solicitud", "012");
		} catch (ImpactaAlmacenesWSException e) {
			e.printStackTrace();
			DerechohabientesBusinessException.throwException("No fue posible calcular la vigencia.", "013");
		}
		
		return solicitud;
	}
	
	/**
	 * 
	 * @param idAsignacionNSS
	 * @param idPersona
	 * @param idsTipoBaja
	 * @return
	 */
	private List<Long> findBajasPorDerechohabiente(Long idAsignacionNSS, Long idPersona, List<Long> idsTipoBaja) {
		
		List<Long> bajasActivas = null;
		List<Long> idsPersona = new ArrayList<Long>();
		idsPersona.add(idPersona);
		List<BajaDerechohabienteDto> bajasDto =  bajaDerechohabienteServiceLocal.getBajaDerechohabiente(idAsignacionNSS, idsPersona, idsTipoBaja, true);
		
		//retornamos error indicando que se encontro una baja activa para el candidato
		if(bajasDto != null && !bajasDto.isEmpty()) {
			bajasActivas = new ArrayList<Long>();
			for(BajaDerechohabienteDto baja: bajasDto) {
				bajasActivas.add(baja.getCveIdTipoBajaDer());
			}
		}
		
		return bajasActivas;
	}

}