package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import mx.gob.imss.consulta.tramites.business.FinalizaSolicitudWSClientRemote;
import mx.gob.imss.consulta.tramites.business.Mensaje;
import mx.gob.imss.consulta.tramites.business.Respuesta;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.GrupoFamiliarDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.SolicitudDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.FinalizaSolicitudServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ImpactaAlmacenesWSException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.Movimiento06CorreccionBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Derechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.BajaDerechohabienteDto;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.asegurado.integracion.sindo.MovCorreccionesDatosAseguradoType;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteBajaDerechohabiente;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteProrroga;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRegistroDerechohabiente;
import mx.gob.imss.ctirss.gestionpersonas.servicios.utility.AfectarDatosPersonaUtilityRemote;

@Stateless(name = "finalizaSolicitud", mappedName = "finalizaSolicitud")
public class FinalizaSolicitudService extends AbstractServiceBusiness implements FinalizaSolicitudServiceRemote, FinalizaSolicitudServiceLocal {

	@EJB
	GrupoFamiliarDaoLocal grupoFamiliarDao;
	
	@EJB
	FinalizaSolicitudWSClientRemote finalizarSolicitudWSCliente;
	
	@EJB
	SolicitudDaoLocal solicitudDao;
	
	@EJB
	AfectarDatosPersonaUtilityRemote afectarDatosPersona;
	
	@EJB
	Movimiento06CorreccionBusinessRemote movimiento06Correccion;
	
	private enum IdentificadoresFuente {
		DIT_BAJA_BDTU, 
		DIT_PRORROGA_BDTU, 
		DIT_DERECHOHABIENTE_BDTU,
		DIT_DERECHOHABIENTE_CL3_CLON
	}
	
	private enum IdentificadoresOperacion{
		A, M
	}
	
	private final String PIPE = "|";
	private final String FORMATO_FECHA = "yyyy-MM-dd";
	private final String FORMATO_FECHA_ddMMYYYY ="dd/MM/yyyy";

	@Override
	public void finalizaSolicitud(Solicitud solicitud) throws IllegalArgumentException, ImpactaAlmacenesWSException, Exception {
		if (solicitud == null ) {
			throw new IllegalArgumentException("solicitud is null");
		}else if(solicitud.getTramites() == null || solicitud.getTramites().size() == 0){
			throw new IllegalArgumentException("Tramites is null");
		}
		String trama;
		Mensaje mensaje;
		for(Tramite tramite : solicitud.getTramites()){
			trama = null;
			mensaje = null;
			if(tramite instanceof TramiteProrroga){
				trama = generarMensajeProrroga((TramiteProrroga) tramite, solicitud.getFechaSolicitud());	
				mensaje = new Mensaje();
				mensaje.setIdentificadorFuente(IdentificadoresFuente.DIT_PRORROGA_BDTU.toString());
				mensaje.setRegistroAInsertar(trama);
				mensaje.setIdentificadorOperacion(IdentificadoresOperacion.A.toString());
				finalizarSolicitud(mensaje);
			}
		}		
	}
	
	private void finalizarSolicitud(Mensaje mensaje) throws ImpactaAlmacenesWSException{
		log.debug("******Se va a enviar el mensaje al WS de finalizar solcitiud a los almacenes******" );
		log.debug("La N trama que se envia el WS es : " + mensaje.getRegistroAInsertar() );
		log.debug("El Identificador de operacion: " + mensaje.getIdentificadorOperacion() + ", El indicador fuente: " + mensaje.getIdentificadorFuente());
		Respuesta respuesta = null;
		try{
			respuesta = finalizarSolicitudWSCliente.finalizarSolicitud(mensaje);	
		}catch(Exception e){
			log.debug("Error al consumir el WS de finalizar solicitud: " + e.getMessage());
			e.printStackTrace();
			throw new ImpactaAlmacenesWSException(e.getMessage());
		}
		if(respuesta == null){
			throw new ImpactaAlmacenesWSException("Ocurrio un problema al consumir el WS respuesta = null");
		}else if(respuesta.getCodigoError() != 0){
			log.debug("error al impactar el ws de vigencia con codigo de error: "+ respuesta.getCodigoError() + " tratando de usar la trama: " + mensaje.getRegistroAInsertar() + " con tipo de operacion: " + mensaje.getIdentificadorOperacion());;
			throw new ImpactaAlmacenesWSException("Ocurrio un error al impactar en el WS Codigo Error: " + respuesta.getCodigoError() + " Mensaje Error: " + respuesta.getMensajeError());
		}
		
		log.debug("Respuesta del WS de finalizar Codigo:"+respuesta.getCodigoError()+", Mensaje: "+respuesta.getMensajeError());
		log.debug("******Finaliza consumo de WS de finalizar solcitiud a los almacenes******" );
	}


	private String generarMensajeProrroga(TramiteProrroga tramiteProrroga, Date fechaSolicitud) throws IllegalArgumentException, Exception{
		validarTramiteProrroga(tramiteProrroga);
		StringBuilder st = new StringBuilder();
		GrupoFamiliar grupoFamiliar = tramiteProrroga.getGrupoFamiliar();
		st.append(grupoFamiliar.getAsignacionNSS().getIdAsignacionNSS() + PIPE);
		st.append(grupoFamiliar.getDerechohabiente().getIdPersona() + PIPE);
		st.append(grupoFamiliar.getAsignacionNSS().getNss() + PIPE);
		st.append(grupoFamiliar.getParentesco().getIdParentesco() + PIPE);
		st.append(tramiteProrroga.getTramiteId()+ PIPE);  
		st.append(tramiteProrroga.getCaracter().getIdCaracter() + PIPE);
		st.append(tramiteProrroga.getEstadoProrroga().getIdEstadoProrroga() + PIPE);
		st.append(tramiteProrroga.getIdTipoProrroga() + PIPE);
		st.append(formatoFechaYYYYMMDD(tramiteProrroga.getFechaInicioProrroga()) + PIPE);
		st.append(formatoFechaYYYYMMDD(tramiteProrroga.getFechaFinProrroga()) + PIPE);
		st.append(formatoFechaYYYYMMDD(fechaSolicitud));
		return st.toString();
	}

	private String generarMensajeBaja(TramiteBajaDerechohabiente tramiteBajaDerechohabiente, Date fechaSolicitud, BajaDerechohabienteDto baja) throws IllegalArgumentException, Exception{
		validarTramiteBaja(tramiteBajaDerechohabiente, baja);
		StringBuilder st = new StringBuilder();
		Derechohabiente der = (Derechohabiente) tramiteBajaDerechohabiente.getPersona();
		st.append(der.getIdPersona() + PIPE);
		if(tramiteBajaDerechohabiente.getTramiteId() != null){
			st.append(tramiteBajaDerechohabiente.getTramiteId() + PIPE);
		}else{
			st.append(""+ PIPE);
		}
		st.append(der.getAsignacionNSS().getIdAsignacionNSS()+ PIPE);
		st.append(baja.getCveIdTipoBajaDer() + PIPE);
		st.append(baja.getIndBajaActiva() + PIPE); 
		st.append(formatoFechaYYYYMMDD(fechaSolicitud) + PIPE);
		if(tramiteBajaDerechohabiente.getFechaDefuncion() != null){
			log.debug("la fecha de baja no es nula [" +tramiteBajaDerechohabiente.getFechaDefuncion()+"]");
			st.append(formatoFechaYYYYMMDD(tramiteBajaDerechohabiente.getFechaDefuncion()));
		}
		
		
		return st.toString();
	}

	private String generarMensajeRegistroWeb(TramiteRegistroDerechohabiente tramite, GrupoFamiliar grupoFamiliar, Usuario usuario) {
		List<GrupoFamiliar> grupoFamiliarList = new ArrayList<GrupoFamiliar>();
		grupoFamiliarList.add(grupoFamiliar);
		validarTramiteRegistro(tramite, grupoFamiliarList);
		StringBuilder st = new StringBuilder();
		st.append(tramite.getDatosAsegurado().getNss()+ PIPE);
		st.append(tramite.getDatosAsegurado().getIdAsignacionNSS() + PIPE);
		st.append(tramite.getFisica().getIdPersona() + PIPE);
		st.append(tramite.getFisica().getPrimerApellido() + PIPE);
		st.append(tramite.getFisica().getSegundoApellido() != null ? tramite.getFisica().getSegundoApellido() : "");
		st.append(PIPE);
		st.append(tramite.getFisica().getNombre() + PIPE);
		st.append(tramite.getFisica().getCurp() != null ? tramite.getFisica().getCurp() : "" );
		st.append(PIPE);
		st.append(tramite.getFisica().getLugarNacimiento() != null ? tramite.getFisica().getLugarNacimiento().getClave() :"");
		st.append(PIPE);
		st.append(tramite.getFisica().getSexo().getIdSexo() + PIPE);
		st.append(formatoFechaDDMMYYYY(tramite.getFisica().getFechaNacimiento()) + PIPE);
		st.append(tramite.getFisica().getFechaDefuncion()!= null ? formatoFechaDDMMYYYY(tramite.getFisica().getFechaDefuncion()) : "");
		st.append(PIPE);
		st.append(grupoFamiliar.getAgregadoMedico() + PIPE);
		st.append(grupoFamiliar.getAgregadoAfiliacion() + PIPE);
		st.append(getAnioNacimiento(tramite.getFisica())); 
		st.append(PIPE);
		st.append((grupoFamiliar.getDerechohabiente().getExpedienteElectronico() != null ? grupoFamiliar.getDerechohabiente().getExpedienteElectronico() : "" ) + PIPE);
		st.append(grupoFamiliar.getCalidad() + PIPE);
		st.append(grupoFamiliar.getParentesco().getIdParentesco() + PIPE);
		st.append(formatoFechaYYYYMMDD(grupoFamiliar.getFechaRegistroAlta()) + PIPE);
		st.append(grupoFamiliar.getCvePersonaDomicilio() != null ? grupoFamiliar.getCvePersonaDomicilio() : "");
		st.append(PIPE);
		st.append(grupoFamiliar.getMedicoEnTurno().getIdMedicoContultorioTurno()+ PIPE);
		st.append(grupoFamiliar.getFechaRegistroBaja() != null ? formatoFechaYYYYMMDD(grupoFamiliar.getFechaRegistroBaja()) : "");
		st.append(PIPE);
		st.append(grupoFamiliar.getIndRecienNacido() != null ? grupoFamiliar.getIndRecienNacido() : "");
		return st.toString();
	}
	


	private void validarTramiteProrroga(TramiteProrroga tramiteProrroga) throws IllegalArgumentException {
		if(tramiteProrroga.getGrupoFamiliar() == null){
			throw new IllegalArgumentException("tramiteProrroga.getGrupoFamiliar() null");
		}
		if(tramiteProrroga.getGrupoFamiliar().getAsignacionNSS() == null){
			throw new IllegalArgumentException("tramiteProrroga.getGrupoFamiliar().getAsignacionNSS() null");
		}
		if(tramiteProrroga.getGrupoFamiliar() == null){
			throw new IllegalArgumentException("tramiteProrroga.getFisica() null");
		}
		if(tramiteProrroga.getGrupoFamiliar().getDerechohabiente().getIdPersona() == null){
			throw new IllegalArgumentException("tramiteProrroga.getGrupoFamiliar().getDerechohabiente().getIdPersona() null");
		}
		if(tramiteProrroga.getGrupoFamiliar().getAsignacionNSS().getNss() == null){
			throw new IllegalArgumentException("tramiteProrroga.getGrupoFamiliar().getAsignacionNSS().getNss()  null");
		}
		if(tramiteProrroga.getGrupoFamiliar().getParentesco()  == null){
			throw new IllegalArgumentException("tramiteProrroga.getGrupoFamiliar().getParentesco()  null");
		}
		if(tramiteProrroga.getTramiteId() == null){
			throw new IllegalArgumentException("ramiteProrroga.getTramiteId()  null");
		}
		if(tramiteProrroga.getCaracter() == null){
			throw new IllegalArgumentException("tramiteProrroga.getCaracter()  null");
		}
		if(tramiteProrroga.getEstadoProrroga() == null){
			throw new IllegalArgumentException("tramiteProrroga.getEstadoProrroga()  null");
		}
		if(tramiteProrroga.getIdTipoProrroga() == null){
			throw new IllegalArgumentException("tramiteProrroga.getIdTipoProrroga()  null");
		}
		if(tramiteProrroga.getFechaInicioProrroga() == null){
			throw new IllegalArgumentException("tramiteProrroga.getFechaInicioProrroga()  null");
		}
		if(tramiteProrroga.getFechaFinProrroga() == null){
			throw new IllegalArgumentException("tramiteProrroga.getFechaFinProrroga()  null");
		}
	}

	private void validarTramiteBaja(TramiteBajaDerechohabiente tramiteBajaDerechohabiente, BajaDerechohabienteDto baja) throws IllegalArgumentException {
		Derechohabiente der = (Derechohabiente) tramiteBajaDerechohabiente.getPersona();
		if(der == null){
			throw new IllegalArgumentException("tramiteBajaDerechohabiente.getPersona null");
		}
		
		if(der.getAsignacionNSS().getNss() == null){
			throw new IllegalArgumentException("tramiteBajaDerechohabiente.getFisica().getNss() null");
		}
		if(baja.getCveIdTipoBajaDer() == null){
			throw new IllegalArgumentException("tramiteBajaDerechohabiente.getTipoTramite().getIdTipoTramite() null");
		}
	}

	private void validarTramiteRegistro(TramiteRegistroDerechohabiente tramiteRegistroDerechohabiente, List<GrupoFamiliar> grupoFamiliarList) throws IllegalArgumentException {
		if(tramiteRegistroDerechohabiente.getDatosAsegurado() == null){
			throw new IllegalArgumentException("tramiteRegistroDerechohabiente.getDatosAsegurado() null");
		}
		if(tramiteRegistroDerechohabiente.getDatosAsegurado().getNss() == null){
			throw new IllegalArgumentException("tramiteRegistroDerechohabiente.getDatosAsegurado().getNss() null");
		}
		if(tramiteRegistroDerechohabiente.getDatosAsegurado().getIdAsignacionNSS()  == null){
			throw new IllegalArgumentException("tramiteRegistroDerechohabiente.getDatosAsegurado().getIdAsignacionNSS()  null");
		}
		if(tramiteRegistroDerechohabiente.getFisica() == null){
			throw new IllegalArgumentException("tramiteRegistroDerechohabiente.getFisica() null");
		}
		if(tramiteRegistroDerechohabiente.getFisica().getIdPersona() == null){
			throw new IllegalArgumentException("tramiteRegistroDerechohabiente.getFisica().getIdPersona() null");
		}
		if(tramiteRegistroDerechohabiente.getFisica().getPrimerApellido() == null){
			throw new IllegalArgumentException("tramiteRegistroDerechohabiente.getFisica().getPrimerApellido() null");
		}
		if(tramiteRegistroDerechohabiente.getFisica().getNombre() == null){
			throw new IllegalArgumentException("tramiteRegistroDerechohabiente.getFisica().getNombre() null");
		}
		if(tramiteRegistroDerechohabiente.getFisica().getSexo() == null){
			throw new IllegalArgumentException("tramiteRegistroDerechohabiente.getFisica().getSexo() null");
		}
		if(tramiteRegistroDerechohabiente.getFisica().getSexo().getIdSexo() == null){
			throw new IllegalArgumentException("tramiteRegistroDerechohabiente.getFisica().getSexo().getIdSexo() null");
		}
		if (grupoFamiliarList == null || grupoFamiliarList.size() == 0) {
			throw new IllegalArgumentException("grupoFamiliar = null");
		}
		GrupoFamiliar grupoFamiliar = grupoFamiliarList.get(0);
		
		if(grupoFamiliar.getAgregadoAfiliacion()== null){
			throw new IllegalArgumentException("grupoFamiliar.getAgregadoAfiliacion() null");
		}
		if(grupoFamiliar.getDerechohabiente()== null){
			throw new IllegalArgumentException("grupoFamiliar.getDerechohabiente() null");
		}
		if(grupoFamiliar.getDerechohabiente().getExpedienteElectronico() == null){
			//throw new IllegalArgumentException("grupoFamiliar.getDerechohabiente().getExpedienteElectronico() null");
		}
		if(grupoFamiliar.getCalidad() == null){
			throw new IllegalArgumentException("grupoFamiliar.getCalidad() null");
		}
		if(grupoFamiliar.getParentesco() == null){
			throw new IllegalArgumentException("grupoFamiliar.getParentesco() null");
		}
		if(grupoFamiliar.getParentesco().getIdParentesco() == null){
			throw new IllegalArgumentException("grupoFamiliar.getParentesco().getIdParentesco() null");
		}
		if(grupoFamiliar.getFechaRegistroAlta() == null){
			throw new IllegalArgumentException("grupoFamiliar.getFechaRegistroAlta() null");
		}
		if(grupoFamiliar.getCvePersonaDomicilio() == null){
//			throw new IllegalArgumentException("grupoFamiliar.getCvePersonaDomicilio() null");
		}
		if(grupoFamiliar.getMedicoEnTurno() == null){
			throw new IllegalArgumentException("grupoFamiliar.getMedicoEnTurno() null");
		}
		if(grupoFamiliar.getMedicoEnTurno().getConsultorio() == null){
			throw new IllegalArgumentException("grupoFamiliar.getMedicoEnTurno().getConsultorio() null");
		}
		if(grupoFamiliar.getMedicoEnTurno().getConsultorio().getIdConsultorio() == null){
			throw new IllegalArgumentException("grupoFamiliar.getMedicoEnTurno().getConsultorio().getIdConsultorio() null");
		}
	}
	

	@Override
	public void finalizaSolicitudRegistroVentanilla(Solicitud solicitud, GrupoFamiliar grupoFamiliar)throws IllegalArgumentException, ImpactaAlmacenesWSException, Exception{
		Mensaje mensaje = new Mensaje();
		TramiteRegistroDerechohabiente tramiteRegistroDerechohabiente = (TramiteRegistroDerechohabiente)solicitud.getTramites().get(0);
			
		List<GrupoFamiliar> grupoFamiliarList = new ArrayList<GrupoFamiliar>();
		grupoFamiliarList.add(grupoFamiliar);
		
		validarTramiteRegistro(tramiteRegistroDerechohabiente,grupoFamiliarList);
		
		StringBuffer st = new StringBuffer();
		st.append(tramiteRegistroDerechohabiente.getDatosAsegurado().getNss() + PIPE);
		st.append(tramiteRegistroDerechohabiente.getDatosAsegurado().getIdAsignacionNSS() + PIPE);
		st.append(tramiteRegistroDerechohabiente.getFisica().getIdPersona() + PIPE);
		st.append(tramiteRegistroDerechohabiente.getFisica().getPrimerApellido() + PIPE);
		st.append(tramiteRegistroDerechohabiente.getFisica().getSegundoApellido() != null ? tramiteRegistroDerechohabiente.getFisica().getSegundoApellido() : "" );
		st.append(PIPE);
		st.append(tramiteRegistroDerechohabiente.getFisica().getNombre() + PIPE);
		st.append(tramiteRegistroDerechohabiente.getFisica().getCurp() != null ? tramiteRegistroDerechohabiente.getFisica().getCurp() : "");
		st.append(PIPE);
		st.append(tramiteRegistroDerechohabiente.getFisica().getLugarNacimiento() != null ? tramiteRegistroDerechohabiente.getFisica().getLugarNacimiento().getClave() : "");
		st.append(PIPE);
		st.append(tramiteRegistroDerechohabiente.getFisica().getSexo().getIdSexo() + PIPE);
		st.append(formatoFechaDDMMYYYY(tramiteRegistroDerechohabiente.getFisica().getFechaNacimiento()) + PIPE);
		st.append(tramiteRegistroDerechohabiente.getFisica().getFechaDefuncion()!= null ? formatoFechaDDMMYYYY(tramiteRegistroDerechohabiente.getFisica().getFechaDefuncion()) : "");
		st.append(PIPE);
		st.append(grupoFamiliar.getAgregadoMedico() + PIPE);
		st.append(grupoFamiliar.getAgregadoAfiliacion() + PIPE);
		st.append(getAnioNacimiento(tramiteRegistroDerechohabiente.getFisica()));
		st.append(PIPE);
		st.append((grupoFamiliar.getDerechohabiente().getExpedienteElectronico() != null? grupoFamiliar.getDerechohabiente().getExpedienteElectronico() : "" ) + PIPE);
		st.append(grupoFamiliar.getCalidad() + PIPE);
		st.append(grupoFamiliar.getParentesco().getIdParentesco() + PIPE);
		st.append(formatoFechaYYYYMMDD(grupoFamiliar.getFechaRegistroAlta()) + PIPE);
		st.append(grupoFamiliar.getCvePersonaDomicilio() != null ? grupoFamiliar.getCvePersonaDomicilio() : "");
		st.append(PIPE);
		st.append(grupoFamiliar.getMedicoEnTurno().getIdMedicoContultorioTurno()+ PIPE);
		st.append(grupoFamiliar.getFechaRegistroBaja() != null ? formatoFechaYYYYMMDD(grupoFamiliar.getFechaRegistroBaja()) : "");
		st.append(PIPE);
		st.append(grupoFamiliar.getIndRecienNacido() != null ? grupoFamiliar.getIndRecienNacido() : "");
		
		mensaje.setIdentificadorFuente(IdentificadoresFuente.DIT_DERECHOHABIENTE_BDTU.toString());
		mensaje.setRegistroAInsertar(st.toString().replace('#', 'Ñ'));
		mensaje.setIdentificadorOperacion(IdentificadoresOperacion.A.toString());
		
		finalizarSolicitud(mensaje);
		if(grupoFamiliar.getParentesco().getIdParentesco().longValue() == ParentescoEnum.ASEGURADO.getId() || grupoFamiliar.getParentesco().getIdParentesco().longValue() == ParentescoEnum.PENSIONADO.getId()){
			enviarArchivo(grupoFamiliar);
		} 
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public void finalizaSolicitudRegistroWeb(Solicitud solicitud,GrupoFamiliar grupoFamiliar) throws IllegalArgumentException, ImpactaAlmacenesWSException, Exception {
		if (solicitud == null) {
			throw new IllegalArgumentException("solicitud is null");
		} else if (solicitud.getTramites() == null || solicitud.getTramites().size() == 0) {
			throw new IllegalArgumentException("Tramites is null");
		} else if (grupoFamiliar == null) {
			throw new IllegalArgumentException("grupoFamiliar is null");
		}
		String trama;
		Mensaje mensaje;
		for (Tramite tramite : solicitud.getTramites()) {
			if (tramite instanceof TramiteRegistroDerechohabiente) {
				trama = generarMensajeRegistroWeb((TramiteRegistroDerechohabiente) tramite,grupoFamiliar,solicitud.getSolicitante());
				mensaje = new Mensaje();
				mensaje.setIdentificadorFuente(IdentificadoresFuente.DIT_DERECHOHABIENTE_BDTU.toString());
				mensaje.setRegistroAInsertar(trama.replace('#', 'Ñ'));
				mensaje.setIdentificadorOperacion(IdentificadoresOperacion.A.toString());
				finalizarSolicitud(mensaje);
				if(grupoFamiliar.getParentesco().getIdParentesco().longValue() == ParentescoEnum.ASEGURADO.getId() || grupoFamiliar.getParentesco().getIdParentesco().longValue() == ParentescoEnum.PENSIONADO.getId()){
					enviarArchivo(grupoFamiliar);
				} 
			}
		}
	}

	

	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public void finalizaSolicitudBajaDerechohabiente(Solicitud solicitud, BajaDerechohabienteDto baja, Boolean isNuevabaja) throws IllegalArgumentException, ImpactaAlmacenesWSException, Exception {
		if (solicitud == null) {
			throw new IllegalArgumentException("solicitud is null");
		} else if (solicitud.getTramites() == null || solicitud.getTramites().size() == 0) {
			throw new IllegalArgumentException("Tramites is null");
		} else if (baja == null) {
			throw new IllegalArgumentException("BAJA is null");
		}
		String trama;
		Mensaje mensaje;
		for (Tramite tramite : solicitud.getTramites()) {
			if (tramite instanceof TramiteBajaDerechohabiente) {
				trama = generarMensajeBaja( (TramiteBajaDerechohabiente) tramite, solicitud.getFechaSolicitud(), baja);
				mensaje = new Mensaje();
				mensaje.setIdentificadorFuente(IdentificadoresFuente.DIT_BAJA_BDTU.toString());
				mensaje.setRegistroAInsertar(trama);
				if(isNuevabaja){
					mensaje.setIdentificadorOperacion(IdentificadoresOperacion.A.toString());	
				}else{
					mensaje.setIdentificadorOperacion(IdentificadoresOperacion.M.toString());	
				}				
				finalizarSolicitud(mensaje);
			}
		}
	}

	
	/* SECCION MODIFICACION DERECHOHABIENTE*/
	
	@Override
	public void modificarCambioTurnoMedioConsultorio(Solicitud solicitud, AsignacionNSS asignacionNSS) throws IllegalArgumentException, ImpactaAlmacenesWSException, Exception {
		TramiteCorreccionDerechohabiente correcion = (TramiteCorreccionDerechohabiente) solicitud.getTramites().get(0);
		GrupoFamiliar grupoFamiliar = consultaGrupoFamiliarBDTU(correcion.getIdPersona(), asignacionNSS.getIdAsignacionNSS());
		String trama = generarMensajeModificacionDerechohabiente(grupoFamiliar);
		finalizarMoodificacion(trama);
	}
	
	

	@Override
	public void modificarCambioUMF(String idUsuario, List<Long> candidatos, AsignacionNSS asignacionNSS) throws IllegalArgumentException, ImpactaAlmacenesWSException, Exception {
		for(Long candidato : candidatos){
			GrupoFamiliar grupoFamiliar = consultaGrupoFamiliarBDTU(candidato, asignacionNSS.getIdAsignacionNSS());
			String trama = generarMensajeModificacionDerechohabiente(grupoFamiliar);
			finalizarMoodificacion(trama);
		}
		
	}
	
	@Override
	public void modificarCambioDerechohabiente(String idUsuario, TramiteCorreccionDerechohabiente derechohabiente, AsignacionNSS asignacionNSS) throws IllegalArgumentException, ImpactaAlmacenesWSException, Exception {
		GrupoFamiliar grupoFamiliar = consultaGrupoFamiliarBDTU(derechohabiente.getIdPersona(), asignacionNSS.getIdAsignacionNSS());
		String trama = generarMensajeModificacionDerechohabiente(grupoFamiliar);
		finalizarMoodificacion(trama);
	}
	
	@Override
	public void modificarSuspensionCircunscripcion(Solicitud solicitud, String idUsuario, AsignacionNSS asignacionNSS) throws IllegalArgumentException, ImpactaAlmacenesWSException, Exception {
		GrupoFamiliar grupoFamiliar = consultaGrupoFamiliarBDTU(solicitud.getPersonaInteresadaSolicitud().getPersona().getIdPersona(), asignacionNSS.getIdAsignacionNSS());
		String trama = generarMensajeModificacionDerechohabiente(grupoFamiliar);
		finalizarMoodificacion(trama);
	}
	
	@Override
	public void finalizaCorreccionDatosDerechohabienteInternet(GrupoFamiliar grupoFamiliar) throws IllegalArgumentException, ImpactaAlmacenesWSException,Exception {
		String trama = generarMensajeModificacionDerechohabiente(grupoFamiliar);
		finalizarMoodificacion(trama);
	}
	
	@Override
	public void finalizaActualizacionDatosDerechohabienteAsincrono(GrupoFamiliar grupoFamiliar) throws IllegalArgumentException, ImpactaAlmacenesWSException,Exception {
		String trama = generarMensajeModificacionDerechohabienteAsincrono(grupoFamiliar);
		finalizarMoodificacion(trama);
	}
	
	@Override
	public void modificarDerechohabientePortal(Solicitud solicitud) throws IllegalArgumentException, ImpactaAlmacenesWSException, Exception {
		for(Tramite tramite : solicitud.getTramites()){
			if(tramite instanceof TramiteCorreccionDerechohabiente){
				GrupoFamiliar grupoFamiliar = consultaGrupoFamiliarBDTU(solicitud.getPersonaInteresadaSolicitud().getPersona().getIdPersona(), ((TramiteCorreccionDerechohabiente) tramite).getDatosAsegurado().getIdAsignacionNSS());
				String trama = generarMensajeModificacionDerechohabiente(grupoFamiliar);
				finalizarMoodificacion(trama);
			}
		}
		
	}
	
	
	
	@Override
	public void mandaMovimientoWS(GrupoFamiliar grupoFamiliar)
			throws IllegalArgumentException, ImpactaAlmacenesWSException,
			Exception {
		try {
			String trama = generarMensajeModificacionDerechohabiente(grupoFamiliar);
			finalizarMoodificacion(trama);
		} catch(IllegalArgumentException e) {
			throw new IllegalArgumentException("Los datos para el movimiento de la persona " + grupoFamiliar.getDerechohabiente().getNombreCompleto() + " se encuentran incompletos,"
					+ " por lo tanto no es posible concluir la solicitud. Es necesario que realice una correcci&oacute;n de datos personales para la persona antes mencionada y una vez que "
					+ "realice el tr&aacute;mite ya ser&aacute; posible continuar con este.");
		}
	}

	@Override
	public void mandaMovimientosWS(List<GrupoFamiliar> candidatosCambio,
			Boolean cambioClinica) throws IllegalArgumentException,
			ImpactaAlmacenesWSException, Exception {
		
		for(GrupoFamiliar grupoFamiliar : candidatosCambio){
			try {
			String trama = generarMensajeModificacionDerechohabiente(grupoFamiliar);
			finalizarMoodificacion(trama);
			
			if(cambioClinica) {
				Long idParentesco = grupoFamiliar.getParentesco().getIdParentesco();
				
				if(idParentesco.equals(ParentescoEnum.ASEGURADO.getId()) || idParentesco.equals(ParentescoEnum.PENSIONADO.getId())) {
					enviarArchivo(grupoFamiliar);
				}
			}
			} catch(IllegalArgumentException e) {
				throw new IllegalArgumentException("Los datos para el movimiento de la persona " + grupoFamiliar.getDerechohabiente().getNombreCompleto() + " se encuentran incompletos,"
						+ " por lo tanto no es posible concluir la solicitud. Es necesario que realice una correcci&oacute;n de datos personales para la persona antes mencionada y una vez que "
						+ "realice el tr&aacute;mite ya ser&aacute; posible continuar con este.");
			}
		}
	}

	@Override
	public void modificarCambioClinicaPortal(List<GrupoFamiliar> candidatosCambio, Boolean cambioClinica) throws IllegalArgumentException, ImpactaAlmacenesWSException, Exception {
		for(GrupoFamiliar grupoFamiliar : candidatosCambio){
			String trama = generarMensajeModificacionDerechohabiente(grupoFamiliar);
			finalizarMoodificacion(trama);
			
			if(cambioClinica) {
				Long idParentesco = grupoFamiliar.getParentesco().getIdParentesco();
				
				if(idParentesco.equals(ParentescoEnum.ASEGURADO.getId()) || idParentesco.equals(ParentescoEnum.PENSIONADO.getId())) {
					enviarArchivo(grupoFamiliar);
				}
			}
		}
	}

	private void finalizarMoodificacion(String trama) throws ImpactaAlmacenesWSException, Exception{
		Mensaje mensaje = new Mensaje();
		mensaje.setIdentificadorFuente(IdentificadoresFuente.DIT_DERECHOHABIENTE_BDTU.toString());
		mensaje.setRegistroAInsertar(trama.replace('#', 'Ñ'));
		mensaje.setIdentificadorOperacion(IdentificadoresOperacion.M.toString());
		finalizarSolicitud(mensaje);
	}
	
	private void finalizarRegistro(String trama) throws ImpactaAlmacenesWSException, Exception{
		Mensaje mensaje = new Mensaje();
		mensaje.setIdentificadorFuente(IdentificadoresFuente.DIT_DERECHOHABIENTE_BDTU.toString());
		mensaje.setRegistroAInsertar(trama.replace('#', 'Ñ'));
		mensaje.setIdentificadorOperacion(IdentificadoresOperacion.A.toString());
		finalizarSolicitud(mensaje);
	}
	
	private void finalizarMoodificacionEstudiante(String trama) throws ImpactaAlmacenesWSException, Exception{
		Mensaje mensaje = new Mensaje();
		mensaje.setIdentificadorFuente(IdentificadoresFuente.DIT_DERECHOHABIENTE_CL3_CLON.toString());
		mensaje.setRegistroAInsertar(trama.replace('#', 'Ñ'));
		mensaje.setIdentificadorOperacion(IdentificadoresOperacion.M.toString());
		finalizarSolicitud(mensaje);
	}
	
	private void finalizarRegistroEstudiante(String trama) throws ImpactaAlmacenesWSException, Exception{
		Mensaje mensaje = new Mensaje();
		mensaje.setIdentificadorFuente(IdentificadoresFuente.DIT_DERECHOHABIENTE_CL3_CLON.toString());
		mensaje.setRegistroAInsertar(trama.replace('#', 'Ñ'));
		mensaje.setIdentificadorOperacion(IdentificadoresOperacion.A.toString());
		finalizarSolicitud(mensaje);
	}
	
	private String generarMensajeModificacionDerechohabiente(GrupoFamiliar grupoFamiliar) throws IllegalArgumentException{
		validarGrupoFamiliar(grupoFamiliar);
		Derechohabiente derechohabiente = grupoFamiliar.getDerechohabiente();
		StringBuilder st = new StringBuilder();
		st.append(derechohabiente.getAsignacionNSS().getNss() + PIPE);
		st.append(derechohabiente.getAsignacionNSS().getIdAsignacionNSS() + PIPE);
		st.append(derechohabiente.getIdPersona() + PIPE);
		st.append(derechohabiente.getPrimerApellido() + PIPE);
		st.append(derechohabiente.getSegundoApellido() != null ? derechohabiente.getSegundoApellido() : "");
		st.append(PIPE);
		st.append(derechohabiente.getNombre() + PIPE);
		st.append(derechohabiente.getCurp() != null ? derechohabiente.getCurp() : "");
		st.append(PIPE);
		st.append(derechohabiente.getLugarNacimiento() != null ? derechohabiente.getLugarNacimiento().getClave() : "");
		st.append(PIPE);
		st.append(derechohabiente.getSexo().getIdSexo() + PIPE);
		st.append(formatoFechaDDMMYYYY(derechohabiente.getFechaNacimiento()) + PIPE);
		st.append(derechohabiente.getFechaDefuncion() != null ? formatoFechaDDMMYYYY(derechohabiente.getFechaDefuncion()) : "");
		st.append(PIPE);
		st.append(grupoFamiliar.getAgregadoMedico() + PIPE);
		st.append(grupoFamiliar.getAgregadoAfiliacion() + PIPE);
		st.append(getAnioNacimiento(derechohabiente));
		st.append(PIPE);
		st.append((derechohabiente.getExpedienteElectronico() != null ? derechohabiente.getExpedienteElectronico(): "") + PIPE);
		st.append(grupoFamiliar.getCalidad() + PIPE);
		st.append(grupoFamiliar.getParentesco().getIdParentesco() + PIPE);
		st.append(formatoFechaYYYYMMDD(grupoFamiliar.getFechaRegistroAlta()) + PIPE);
		st.append(grupoFamiliar.getCvePersonaDomicilio() != null ? grupoFamiliar.getCvePersonaDomicilio() : "");
		st.append(PIPE);
		st.append(grupoFamiliar.getMedicoEnTurno().getIdMedicoContultorioTurno() + PIPE);
		st.append(grupoFamiliar.getFechaRegistroBaja() != null ? formatoFechaYYYYMMDD(grupoFamiliar.getFechaRegistroBaja()) : "");
		st.append(PIPE);
		st.append(grupoFamiliar.getIndRecienNacido() != null ? grupoFamiliar.getIndRecienNacido() : "");
		return st.toString();
	}

//	private GrupoFamiliar consultaGrupoFamiliar(Long idPersona, Long idAsignacionNSS) throws Exception {
//		GrupoFamiliar grupoFamiliar = grupoFamiliarDao.getIntegranteGrupoFamiliar(idAsignacionNSS, idPersona);
//		return grupoFamiliar;
//	}
	
	private String generarMensajeModificacionDerechohabienteAsincrono(GrupoFamiliar grupoFamiliar) throws IllegalArgumentException{
		validarGrupoFamiliar(grupoFamiliar);
		Derechohabiente derechohabiente = grupoFamiliar.getDerechohabiente();
		StringBuilder st = new StringBuilder();
		st.append(derechohabiente.getAsignacionNSS().getNss() + PIPE);
		st.append(derechohabiente.getAsignacionNSS().getIdAsignacionNSS() + PIPE);
		st.append(derechohabiente.getIdPersona() + PIPE);
		st.append(derechohabiente.getPrimerApellido() + PIPE);
		st.append(derechohabiente.getSegundoApellido() != null ? derechohabiente.getSegundoApellido() : "");
		st.append(PIPE);
		st.append(derechohabiente.getNombre() + PIPE);
		st.append(derechohabiente.getCurp() != null ? derechohabiente.getCurp() : "");
		st.append(PIPE);
		st.append(derechohabiente.getLugarNacimiento() != null ? derechohabiente.getLugarNacimiento().getClave() : "");
		st.append(PIPE);
		st.append(derechohabiente.getSexo().getIdSexo() + PIPE);
		st.append(formatoFechaDDMMYYYY(derechohabiente.getFechaNacimiento()) + PIPE);
		st.append(derechohabiente.getFechaDefuncion() != null ? formatoFechaDDMMYYYY(derechohabiente.getFechaDefuncion()) : "");
		st.append(PIPE);
		st.append(grupoFamiliar.getAgregadoMedico() + PIPE);
		st.append(grupoFamiliar.getAgregadoAfiliacion() + PIPE);
		st.append(getAnioNacimiento(derechohabiente));
		st.append(PIPE);
		st.append((derechohabiente.getExpedienteElectronico() != null ? derechohabiente.getExpedienteElectronico(): "") + PIPE);
		st.append(grupoFamiliar.getCalidad() + PIPE);
		st.append(grupoFamiliar.getParentesco().getIdParentesco() + PIPE);
		st.append(formatoFechaYYYYMMDD(grupoFamiliar.getFechaRegistroAlta()) + PIPE);
		st.append(grupoFamiliar.getCvePersonaDomicilio() != null ? grupoFamiliar.getCvePersonaDomicilio() : "");
		st.append(PIPE);
		st.append(grupoFamiliar.getMedicoEnTurno().getIdMedicoContultorioTurno() + PIPE);
		st.append(grupoFamiliar.getIndSimilarCalDifGpoFam() != null ? formatoFechaYYYYMMDD(grupoFamiliar.getIndSimilarCalDifGpoFam()) : "");
		st.append(PIPE);
		st.append(grupoFamiliar.getIndRecienNacido() != null ? grupoFamiliar.getIndRecienNacido() : "");
		return st.toString();
	}

	private void validarGrupoFamiliar(GrupoFamiliar grupoFamiliar) throws IllegalArgumentException {
		if(grupoFamiliar == null){
			throw new IllegalArgumentException("GrupoFamiliar is null");
		}
		if(grupoFamiliar.getDerechohabiente() == null){
			throw new IllegalArgumentException("Derechohabiente is null");
		}
		Derechohabiente derechohabiente = grupoFamiliar.getDerechohabiente();
		if(derechohabiente.getAsignacionNSS() == null){
			throw new IllegalArgumentException("derechohabiente.getAsignacionNSS() is null");
		}
		if(derechohabiente.getAsignacionNSS().getNss() == null){
			throw new IllegalArgumentException("derechohabiente.getAsignacionNSS().getNss() is null");
		}
		if(derechohabiente.getAsignacionNSS().getIdAsignacionNSS() == null){
			throw new IllegalArgumentException("derechohabiente.getAsignacionNSS().getIdAsignacionNSS() is null");
		}
		if(derechohabiente.getIdPersona() == null){
			throw new IllegalArgumentException("derechohabiente.getIdPersona() is null");
		}
		if(derechohabiente.getPrimerApellido() == null){
			throw new IllegalArgumentException("derechohabiente.getPrimerApellido() is null");
		}
		if(derechohabiente.getNombre() == null){
			throw new IllegalArgumentException("derechohabiente.getNombre()  is null");
		}
		if(derechohabiente.getSexo() == null){
			throw new IllegalArgumentException("derechohabiente.getSexo()  is null");
		}
		if(derechohabiente.getSexo().getIdSexo() == null){
			throw new IllegalArgumentException("derechohabiente.getSexo().getIdSexo()  is null");
		}
		if(grupoFamiliar.getAgregadoMedico() == null){
			//throw new IllegalArgumentException("grupoFamiliar.getAgregadoMedico()  is null");
		}
		if(grupoFamiliar.getAgregadoAfiliacion() == null){
			//throw new IllegalArgumentException("grupoFamiliar.getAgregadoAfiliacion()  is null");
		}
		if(derechohabiente.getExpedienteElectronico() == null){
			//throw new IllegalArgumentException("derechohabiente.getExpedienteElectronico()  is null");
		}
		if(grupoFamiliar.getCalidad() == null){
			throw new IllegalArgumentException("grupoFamiliar.getCalidad()  is null");
		}
		if(grupoFamiliar.getParentesco() == null){
			throw new IllegalArgumentException("grupoFamiliar.getParentesco()  is null");
		}
		if(grupoFamiliar.getParentesco().getIdParentesco() == null){
			throw new IllegalArgumentException("grupoFamiliar.getParentesco().getIdParentesco()  is null");
		}
		if(grupoFamiliar.getFechaRegistroAlta() == null){
			throw new IllegalArgumentException("grupoFamiliar.getFechaRegistroAlta()  is null");
		}
		if(grupoFamiliar.getCvePersonaDomicilio() == null){
//			throw new IllegalArgumentException("grupoFamiliar.getCvePersonaDomicilio()  is null");
		}
		if(grupoFamiliar.getMedicoEnTurno() == null){
			throw new IllegalArgumentException("grupoFamiliar.getMedicoEnTurno()  is null");
		}
		if(grupoFamiliar.getMedicoEnTurno().getConsultorio() == null){
			throw new IllegalArgumentException("grupoFamiliar.getMedicoEnTurno().getConsultorio()  is null");
		}
		if(grupoFamiliar.getMedicoEnTurno().getConsultorio().getIdConsultorio() == null){
			throw new IllegalArgumentException("grupoFamiliar.getMedicoEnTurno().getConsultorio().getIdConsultorio()  is null");
		}
	}
	
	
	private String formatoFechaDDMMYYYY(Date fecha){
		if(fecha == null){
			return "";
		}
			SimpleDateFormat sdf = new  SimpleDateFormat(FORMATO_FECHA_ddMMYYYY);
		return sdf.format(fecha);
	}
	
	private String formatoFechaYYYYMMDD(Date fecha) {
		if(fecha == null){
			return "";
		}
		SimpleDateFormat sdf = new  SimpleDateFormat(FORMATO_FECHA);
		return sdf.format(fecha);
	}

	/**
	 * Método para finalizar una solicitud con todos sus trámites y personas interesadas
	 */
	@Override
	public void finalizarSolicitudTramites(Solicitud solicitud, String idUsuario, AsignacionNSS asignacionNSS) throws IllegalArgumentException, ImpactaAlmacenesWSException, Exception {
		List<Tramite> tramites = solicitud.getTramites();
		if(tramites != null){
			for (Tramite tramite : tramites) {
				Boolean isRegistro = null;
				TramiteRegistroDerechohabiente tramiteRegistro = null;
				TramiteCorreccionDerechohabiente tramiteCorrecion = null;
				if(tramite instanceof TramiteRegistroDerechohabiente){
					tramiteRegistro = (TramiteRegistroDerechohabiente) tramite;
					isRegistro = true;						
				}	
				if(tramite instanceof TramiteCorreccionDerechohabiente){
					tramiteCorrecion = (TramiteCorreccionDerechohabiente) tramite;
					isRegistro = false;
				}
				log.debug("El tipo de tramite es: " + tramite.getTipoTramite().getIdTipoTramite() + ", descripcion: " + tramite.getTipoTramite().getDescripcion());
				if(tramite.getPersonas() != null){					
					for (Fisica fisica : tramite.getPersonas()) {
//						Derechohabiente derechohabiente = null;
//						if(fisica instanceof Derechohabiente){
//							derechohabiente = (Derechohabiente) fisica;
//						}
						if(isRegistro != null && isRegistro == true /*&& derechohabiente != null*/){
							finalizarSolicitudPersonaRegistro(fisica, tramiteRegistro, idUsuario, asignacionNSS);
						}else if(isRegistro != null && isRegistro == false /*&& derechohabiente != null*/){
							finalizarSolicitudPersonaModificacion(fisica, tramiteCorrecion, idUsuario, asignacionNSS);
						}
					}
				}else if(tramite.getPersona() != null){
					if(isRegistro != null && isRegistro == true /*&& derechohabiente != null*/){
						finalizarSolicitudPersonaRegistro(tramite.getPersona(), tramiteRegistro, idUsuario, asignacionNSS);
					}else if(isRegistro != null && isRegistro == false /*&& derechohabiente != null*/){
						finalizarSolicitudPersonaModificacion(tramite.getPersona(), tramiteCorrecion, idUsuario, asignacionNSS);
					}
				}
			}	
		}
	}
	
	
	/**
	 * Método para finalizar una solicitud con todos sus trámites y personas interesadas
	 */
	@Override
	public void finalizarSolicitudTramitesConGF(Solicitud solicitud, List<GrupoFamiliar> listaGrupoFamiliar) throws IllegalArgumentException, ImpactaAlmacenesWSException, Exception {
		List<Tramite> tramites = solicitud.getTramites();
		if(tramites != null){
			for (Tramite tramite : tramites) {
				Boolean isRegistro = null;
				TramiteRegistroDerechohabiente tramiteRegistro = null;
				TramiteCorreccionDerechohabiente tramiteCorrecion = null;
				if(tramite instanceof TramiteRegistroDerechohabiente){
					tramiteRegistro = (TramiteRegistroDerechohabiente) tramite;
					isRegistro = true;						
				}	
				if(tramite instanceof TramiteCorreccionDerechohabiente){
					tramiteCorrecion = (TramiteCorreccionDerechohabiente) tramite;
					isRegistro = false;
				}
				log.debug("El tipo de tramite es: " + tramite.getTipoTramite().getIdTipoTramite() + ", descripcion: " + tramite.getTipoTramite().getDescripcion());
				if(listaGrupoFamiliar != null && listaGrupoFamiliar.size() > 0){					
					for (GrupoFamiliar grupoFamiliar : listaGrupoFamiliar) {
						if(isRegistro != null && isRegistro == true){
							finalizarSolicitudPersonaRegistroGF(grupoFamiliar);
						}else if(isRegistro != null && isRegistro == false){
							finalizarSolicitudPersonaModificacionGF(grupoFamiliar, tramite);
						}
					}
				}
			}	
		}
	}

	
	
	private void finalizarSolicitudPersonaModificacionGF(GrupoFamiliar grupoFamiliar, Tramite tramite) throws ImpactaAlmacenesWSException, Exception {
		String trama = generarMensajeModificacionDerechohabiente(grupoFamiliar);
		finalizarMoodificacion(trama);
		if(tramite.getTipoTramite().getIdTipoTramite().intValue() == TipoTramiteEnum.CAMBIO_CLINICA.getCodigo().intValue()
				&& (grupoFamiliar.getParentesco().getIdParentesco().longValue() == ParentescoEnum.ASEGURADO.getId() || grupoFamiliar.getParentesco().getIdParentesco().longValue() == ParentescoEnum.PENSIONADO.getId())){
			enviarArchivo(grupoFamiliar);							
		}
	}

	private void finalizarSolicitudPersonaRegistroGF(GrupoFamiliar grupoFamiliar) throws ImpactaAlmacenesWSException, Exception{
		String trama = generarMensajeModificacionDerechohabiente(grupoFamiliar);
		finalizarRegistro(trama);
		if(grupoFamiliar.getParentesco().getIdParentesco().longValue() == ParentescoEnum.ASEGURADO.getId() || grupoFamiliar.getParentesco().getIdParentesco().longValue() == ParentescoEnum.PENSIONADO.getId()){
			enviarArchivo(grupoFamiliar);
		} 
	}

	private void finalizarSolicitudPersonaRegistro(Fisica fisica, TramiteRegistroDerechohabiente tramite, String idUsuario, AsignacionNSS asignacionNSS) throws Exception {
//		GrupoFamiliar grupoFamiliar = consultaGrupoFamiliarBDTU(derechohabiente.getIdPersona(), derechohabiente.getAsignacionNSS().getIdAsignacionNSS());
		GrupoFamiliar grupoFamiliar = consultaGrupoFamiliarBDTU(fisica.getIdPersona(), asignacionNSS.getIdAsignacionNSS());
		String trama = generarMensajeModificacionDerechohabiente(grupoFamiliar);
		finalizarRegistro(trama);
		if(grupoFamiliar.getParentesco().getIdParentesco().longValue() == ParentescoEnum.ASEGURADO.getId() || grupoFamiliar.getParentesco().getIdParentesco().longValue() == ParentescoEnum.PENSIONADO.getId()){
			enviarArchivo(grupoFamiliar);
		} 
		
	}

	private void finalizarSolicitudPersonaModificacion(Fisica fisica, TramiteCorreccionDerechohabiente tramite, String idUsuario, AsignacionNSS asignacionNSS) throws Exception {
//		GrupoFamiliar grupoFamiliar = consultaGrupoFamiliarBDTU(derechohabiente.getIdPersona(), derechohabiente.getAsignacionNSS().getIdAsignacionNSS());
		GrupoFamiliar grupoFamiliar = consultaGrupoFamiliarBDTU(fisica.getIdPersona(), asignacionNSS.getIdAsignacionNSS());
		String trama = generarMensajeModificacionDerechohabiente(grupoFamiliar);
		finalizarMoodificacion(trama);
		if(tramite.getTipoTramite().getIdTipoTramite().intValue() == TipoTramiteEnum.CAMBIO_CLINICA.getCodigo().intValue()
				&& (grupoFamiliar.getParentesco().getIdParentesco().longValue() == ParentescoEnum.ASEGURADO.getId() || grupoFamiliar.getParentesco().getIdParentesco().longValue() == ParentescoEnum.PENSIONADO.getId())){
			enviarArchivo(grupoFamiliar);							
		}
	}
	
	private void enviarArchivo(GrupoFamiliar grupoFamiliar) {
		try{
			log.debug("Se va a generar movimiento de correccion para ***SINDO***" );
			MovCorreccionesDatosAseguradoType movCorrecion = afectarDatosPersona.generarMovimientoActualizacionRegistroAsegurado(grupoFamiliar);
			log.debug("Se genero movimeinto para ***SINDO*** y se va a encolar");
			movimiento06Correccion.encolarMovimiento06CorrecconAsegurado(movCorrecion);
			log.debug("Se encolo el movimiento correctamente a ***SINDO***");
		}catch(Exception e){
			e.printStackTrace();
			log.debug("ocurrio un errro al generar el movimiento y encolar en ***SINDO*** error: " + e.getMessage());
		}
		
	}
	
	
	private GrupoFamiliar consultaGrupoFamiliarBDTU(Long idPersona, Long idAsignacionNSS) throws Exception {
		return grupoFamiliarDao.getIntegranteComplementado( new GrupoFamiliar(new AsignacionNSS(idAsignacionNSS), new Derechohabiente(idPersona)) );
	}
	
	private String getAnioNacimiento(Fisica fisica){
		String anio = "";
		if(fisica.getFechaNacimiento() != null){
			Calendar cal = Calendar.getInstance();
			cal.setTime(fisica.getFechaNacimiento());
			anio = Integer.toString(cal.get(Calendar.YEAR)); 
			anio = anio.substring(2, 4);
		}else if(fisica.getAnioRegistroNac() != null){
			anio = (fisica.getAnioRegistroNac()).toString();
		}
		return anio;
	}

	@Override
	public void finalizaRegistroEstudiante(GrupoFamiliar grupoFamiliar, Boolean esNuevoRegistro) throws ImpactaAlmacenesWSException, Exception {
		String trama = generarMensajeModificacionDerechohabiente(grupoFamiliar);
		if(esNuevoRegistro){
			finalizarRegistroEstudiante(trama);	
		}else{
			finalizarMoodificacionEstudiante(trama);
		}
		enviarArchivo(grupoFamiliar);
	}
}

