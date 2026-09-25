/**
*
*
**/
package mx.gob.imss.ctirss.delta.gestion.patronal.service.business.rule;

import gob.imss.webservice.sat.rfc.implementacion.ClienteWebserviceRfc;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.gestion.patronal.PatronExistenteMunicipioFraccionModalidadException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clasificacion.ClasificacionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.business.afiliacion.ServiciosExternosAfiliacionBusinessLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.clasificacion.ClasificacionServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.rep.legal.RepresentanteLegalServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.baja.RegistroPatronalServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rule.RuleServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.socios.SocioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.ParametrosServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.SystemKeyParameters;
import mx.gob.imss.ctirss.delta.model.domicilio.MunicipioIMSS;
import mx.gob.imss.ctirss.delta.model.enums.CausaBajaPatronEnum;
import mx.gob.imss.ctirss.delta.model.enums.FraccionEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoAmbitoEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoConvenioEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Regimen;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RegistroPatronal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Socio;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote;

import org.apache.commons.lang.StringUtils;

/**
 *
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Hugo Armando Martï¿½nez Chamï¿½nica
 *  @Proyecto: delta
 *  @Archivo: RuleServiceBusiness.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.patronal.service.business.rule
 *  @Fecha: 11:39:46
 */
@Stateless(name="ruleServiceBusiness" ,mappedName="ruleServiceBusiness")
public class RuleServiceBusiness extends AbstractServiceBusiness implements RuleServiceBusinessLocal, RuleServiceBusinessRemote{

	@EJB
	private ClasificacionServiceEntityLocal claseEntity;

	@EJB
	private RepresentanteLegalServiceEntityLocal representanteLegalServiceEntityLocal;

	@EJB
	private SujetoObligadoServiceBusinessRemote sujetoObligadoService;

	@EJB
	private ServiciosExternosAfiliacionBusinessLocal serviciosExternosAfiliacionBusiness;

	@EJB
	private DomicilioServiceBusinessRemote domicilioService;

	@EJB
	private RegistroPatronalServiceBusinessRemote registroPatronalService;

	@EJB
	private ClasificacionServiceBusinessRemote clasificacionService;

	@EJB
	private ParametrosServiceBusinessRemote parametrosServiceBusiness;

	@EJB
	private SocioServiceBusinessRemote socioBusiness;

	@EJB
	private PersonaMoralBusinessRemote personaMoralServiceBusiness;

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.business.rule.RuleServiceBusinessLocal#validarRPC_AP_MOD_MAC(java.lang.String, mx.gob.imss.ctirss.delta.model.gestion.patronal.Clase)
	 */
	@Override
	public void validarRPC_AP_MOD_MAC(String rfc, Long clase, String numeroRegistroPatronal) throws GestionPatronalBusinessException{

		if(numeroRegistroPatronal.length()<10 || numeroRegistroPatronal.length()>11){
			throw new GestionPatronalBusinessException("error.registro.patronal.invalido");
		}
		Clasificacion clasificacionDeRP = claseEntity.consultarClasificacionPorRegistroPatronal(numeroRegistroPatronal);
		if(clasificacionDeRP!=null && clasificacionDeRP.getIndRegPatClase().intValue() == 1){
			Long claseActual = clasificacionDeRP.getFraccion().getClase().getClave();
			List<Clasificacion> clasificaciones = claseEntity.consultarClasesExistentesPorRFC(rfc, obtenerTipoPersonaPorRFC(rfc), numeroRegistroPatronal);
			if(claseActual==null)
				claseActual=0L;
			for(Clasificacion claseObj:clasificaciones){
	//			Long numDivision = Long.valueOf(claseObj.getFraccion().getGrupo().getDivision().getNumDivision());
	//			if(numDivision <= 9L)
	//				throw new GestionPatronalBusinessException("error.business.clase.catalogo.anterior");
				Long claseEncontrada = claseObj.getFraccion().getClase().getClave();
				System.err.println("Clase encontrada: "+ claseEncontrada);
				System.err.println("Clase nueva: "+clase);
				System.err.println("Clase actual: "+claseActual);

				if (claseEncontrada!=null && clase!=null && claseEncontrada.equals(clase) && !claseEncontrada.equals(claseActual))
					throw new GestionPatronalBusinessException("error.business.clase.existente");
			}
		}else{
			super.log.debug("La regla RPC no aplica para este RP");
		}

	}

	@Override
	public void validarRPC_AP_MOD_MAC(String rfc, Long clase) throws GestionPatronalBusinessException {
		List<Clasificacion> clasificaciones = claseEntity.consultarClasesExistentesPorRFC(rfc, obtenerTipoPersonaPorRFC(rfc));

		for(Clasificacion claseObj:clasificaciones){
			Long claseEncontrada = claseObj.getFraccion().getClase().getClave();
			System.err.println("Clase encontrada: "+ claseEncontrada);
			System.err.println("Clase nueva: "+clase);

			if (claseEncontrada!=null && clase!=null && claseEncontrada.equals(clase))
				throw new GestionPatronalBusinessException("error.business.clase.existente");
		}
	}



	/**
	 *
	 * @author Hugo Armando Martï¿½nez Chamï¿½nica
	 * @param rfc
	 * @return TipoPersonaFiscal
	 */
	private TipoPersonaFiscal obtenerTipoPersonaPorRFC(String rfc){
		if(rfc.length()==13)
			return TipoPersonaFiscal.FISICA;
		else if(rfc.length()==12)
			return TipoPersonaFiscal.MORAL;
		return null;
	}


	@Override
	public void validarLimiteMinRepresentanteLegal(
			RepresentanteLegal representanteLegal, String tipoPersonaFiscal)
			throws GestionPatronalBusinessException {

		SujetoObligado sujetoObligado = new SujetoObligado();
		//List<DitRepresentanteLegal> ditRepresentanteLegalList = null;
		int numRegistros = 0;

		sujetoObligado.setCveIdSujetoObligado(representanteLegal.getCveIdPatronSujetoObligado());
		if (tipoPersonaFiscal != null && tipoPersonaFiscal.length() > 0){
			if (tipoPersonaFiscal.equalsIgnoreCase("FISICA")){
				return;
			}
		} else {
			throw new GestionPatronalBusinessException("Error al procesar el parï¿½metro \'tipoPersonaFiscal\' en la validaciÃ³n del lÃ­mite mÃ­nimo de representantes legales para el Sujeto Obligado: " + representanteLegal.getCveIdPatronSujetoObligado()+  ", verifique.");
		}

		try {
			//ditRepresentanteLegalList = this.representanteLegalServiceEntityLocal.consultaPorPatronSujetoObligado(representanteLegal);
			numRegistros = this.representanteLegalServiceEntityLocal.consultarNumRegistros(representanteLegal.getCveIdPatronSujetoObligado());
		} catch (Exception e) {
			throw new GestionPatronalBusinessException(e.getMessage());
		}

		if (numRegistros < 2){
			throw new GestionPatronalBusinessException("No se permite seguir dando de baja a ningun representante legal, debe contar al menos con un registro.");
		}
	}

	@Override
	public boolean validarRPCPorModificacionSRT(Long idSolicitud, String rfc,
			Long clase, String numeroRegistroPatronal){

		super.log.debug("La regla RPC fallÃ³ se registra el indicador de falla de rpc");
		Solicitud solicitud = claseEntity.consultarReintentoRPC(idSolicitud);
		boolean existeReintento = solicitud == null ? false : true;
		if(!existeReintento){
			//Se inicializa el objeto con valores por defecto
			solicitud = new Solicitud();
			solicitud.setSolicitudId(idSolicitud);
			solicitud.setIndReintentoRpc(false);
			solicitud.setIndRpcInvalido(false);
		}

		try{
			validarRPC_AP_MOD_MAC(rfc, clase, numeroRegistroPatronal);
		}catch (GestionPatronalBusinessException gpbe) {
			super.log.debug("La regla RPC fallÃ³ se registra el indicador de falla de rpc");
			if(solicitud.isIndRpcInvalido())
				solicitud.setIndReintentoRpc(true);
			else
				solicitud.setIndRpcInvalido(true);
			claseEntity.crearActualizarReintentoRPC(solicitud);
			return false;
		}

	if(solicitud.isIndRpcInvalido())
		solicitud.setIndReintentoRpc(true);
	claseEntity.crearActualizarReintentoRPC(solicitud);

	return true;
	}

	/**
	 *
	 * @author Hugo Martinez
	 * @Date 22/02/2013
	 * @param numeroRegistroPatronal
	 * @return
	 */
	@Override
	public SujetoObligado validarNumeroDeRegistroPatronal(String numeroRegistroPatronal) throws GestionPatronalBusinessException{

		if(numeroRegistroPatronal==null || (numeroRegistroPatronal!=null && numeroRegistroPatronal.equals("")))
			throw new GestionPatronalBusinessException("error.nrp.requerido");

		if(numeroRegistroPatronal.length()!=10 && numeroRegistroPatronal.length()!=11)
			throw new GestionPatronalBusinessException("error.nrp.formato");

		SujetoObligado patron = sujetoObligadoService.consultarPorNumeroRegistroPatronal(numeroRegistroPatronal);

		if(patron==null)
			throw new GestionPatronalBusinessException("error.nrp.inexistente");

		return patron;
	}

	@Override
	public void validarClasificacionPorPatronYMunicipio(Long cveIdPersona,
			Long tipoPersona, Long idMunicipioIMSS, Long idFraccion, Long idRegistroPatronalActual)
			throws GestionPatronalBusinessException {
		Integer numeroDeRPConMismaFraccionYMunicipio = sujetoObligadoService.obtenerNumeroDeRPEnMunicipioIMSSPorFraccion(cveIdPersona, tipoPersona, idMunicipioIMSS, idFraccion, idRegistroPatronalActual);
		log.error("Se encontraron los siguientes RP dentro del municipio con la misma fracciÃ³n: "+numeroDeRPConMismaFraccionYMunicipio);
		if(numeroDeRPConMismaFraccionYMunicipio>0)
			throw new GestionPatronalBusinessException("error.clasificacion.municipio.invalida");
	}

	@Override
	public void validarClasificacionPorPatronYMunicipio(String rfc,
			Long tipoPersona, Long idMunicipioIMSS, Long idFraccion, Long idRegistroPatronalActual)
			throws GestionPatronalBusinessException {

		/*
		 * if(tipoPersona.equals(TipoPersona.MORAL)){ Fraccion fraccion =
		 * claseEntity.consultarFraccionPorId(idFraccion); StringBuffer
		 * fraccionAEvaluar=new StringBuffer();
		 * fraccionAEvaluar.append(fraccion.getGrupo().getDivision().getNumDivision());
		 * fraccionAEvaluar.append(fraccion.getGrupo().getNumGrupo());
		 * fraccionAEvaluar.append(fraccion.getNumFraccion());
		 *
		 * if(fraccionAEvaluar.toString().equals(FraccionEnum.CONSTRUCCION.getCodigo()))
		 * return;// se elimina esta reglpara personas morales con fraccion 411 }
		 * SujetoObligado registroPatronal =
		 * sujetoObligadoService.obtenerRegistroPatronalEnMunicipioIMSSPorFraccion(rfc,
		 * tipoPersona, idMunicipioIMSS, idFraccion, idRegistroPatronalActual);
		 *
		 * if(registroPatronal!=null){ log.
		 * error("Se encontraron los siguientes RP dentro del municipio con la misma fraccion: "
		 * +registroPatronal); StringBuffer mensaje= new StringBuffer();
		 * mensaje.append("Actualmente cuenta con el registro patronal ");
		 * mensaje.append(registroPatronal.getNumeroRegistroPatronal());
		 * mensaje.append(registroPatronal.getModalidad()!=null ?
		 * registroPatronal.getModalidad().getNumModalidad() : "");
		 * mensaje.append(registroPatronal.getDigVerificador()!=null ?
		 * registroPatronal.getDigVerificador() : "");
		 * mensaje.append(" con la misma actividad seleccionada asociado al municipio "
		 * ); mensaje.append(registroPatronal.getMunicipioIMSS().getDescMunicipio() +
		 * "."); //mensaje.append(" por favor seleccione una nueva actividad.");
		 *
		 * throw new GestionPatronalBusinessException(mensaje.toString(),800); }
		 */
	}

	/**
	 * Metodo que valida que no exista un patron imss con los mismos datos de nombre, fraccion prima modalidad en el mismo municipio,
	 * si encuentra un registros con estas caracteristicas arroja excepcion de negocio
	 * @throws PatronExistenteMunicipioFraccionModalidadException
	 */
	@Override
	public void validaPatronExistentePorMunicipioFraccionModalidad(SujetoObligado sujetoObligado) throws PatronExistenteMunicipioFraccionModalidadException{
		//se realiza la llamada al metodo que consulta a los patrones en bdtu

			Long idTipoPersona = null;
			Long idMunicipioIMSS = null;
			Long idFraccion=null;
			String rfc=null;
			if(sujetoObligado.getTipoPersonaFiscal()!=null && sujetoObligado.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
				rfc = sujetoObligado.getFisica().getRfc();
				idTipoPersona = TipoPersona.FISICA.longValue();
			}else{

				String fraccionSeleccionada = formarFraccionCompleta(sujetoObligado.getClasificacion().getFraccion());
				if(fraccionSeleccionada.equals(FraccionEnum.CONSTRUCCION.getCodigo())) {
					this.log.debug("::: La fraccion seleccionada es de construccion no se aplica regla");
					return;
				}

				idTipoPersona = TipoPersona.MORAL.longValue();
				rfc = sujetoObligado.getMoral().getRfc();
			}

			idMunicipioIMSS = Long.valueOf(sujetoObligado.getMunicipioIMSS().getIdMunicipio());
			idFraccion = sujetoObligado.getClasificacion().getFraccion().getId();

			try {
				validarClasificacionPorPatronYMunicipio(rfc, idTipoPersona, idMunicipioIMSS, idFraccion,sujetoObligado.getCveIdSujetoObligado());
			} catch (GestionPatronalBusinessException e) {
				throw new PatronExistenteMunicipioFraccionModalidadException(e.getSituacion());
			}

//			SujetoObligado registroPatronalExistente = sujetoObligadoService.obtenerRegistroPatronalEnMunicipioIMSSPorFraccion(rfc, idTipoPersona, idMunicipioIMSS, idFraccion,sujetoObligado.getCveIdSujetoObligado());
//			if (registroPatronalExistente != null){
//				this.log.debug("se encontro un patron con estos datos en bdtu");
//				throw new PatronExistenteMunicipioFraccionModalidadException("error.clasificacion.municipio.invalida");
//			}
//

			boolean existePatronReing = serviciosExternosAfiliacionBusiness.consultaPatronPorNombreModalidadPrimaMunicipioIMSSenReing(sujetoObligado);

			this.log.debug("el resultado de la consulta es: "+ existePatronReing );
			if (existePatronReing){
				this.log.debug("se encontro un patron con estos datos en REING");
				throw new PatronExistenteMunicipioFraccionModalidadException("Actualmente cuenta con un registro patronal con la misma actividad seleccionada, por favor seleccione una nueva actividad.");
			}


	}

	/**
	 * Valida si el municipio asignado cuenta con el tipo de servicio (servicios urbanos o de campo) asociado a la modalidad en base a la
	 * siguiente tabla:
	 *
	 * Modalidad	Servicios urbanos	Servicios campo
	 * 10					X
	 * 13										X
	 * 14										X
	 * 17					X
	 * 30										X
	 * 32					X
	 * 33					X
	 * 35					X					X
	 * 36					X
	 * 38					X
	 * 40					X
	 * 42					X
	 * 43										X
	 * 44					X
	 * 46					X					X
	 *
	 * Esto se valida en base a la fecha de inicio de operaciï¿½n de servicios (de campo o urbano segï¿½un
	 * corresponda) o bien, en caso de no existir fecha de inicio de operaciones se valida
	 * en base al identificador de convenio.
	 *
	 * @param cveMunicipio Clave de municipio de 3 posiciones alfanï¿½mericas
	 * @param numModalidad Nï¿½mero de modalidad a dos digitos
	 * @param fechaDeMovimiento fecha de movimiento
	 */
	@Override
	public void validarTipoAmbitoPorMunicipio(String cveMunicipio,
			String numModalidad, Date fechaDeMovimiento) throws GestionPatronalBusinessException{
		MunicipioIMSS municipioIMSS = domicilioService.getMunicipioIMSSPorClave(cveMunicipio);

		if(municipioIMSS.getFechaInicioOperacionesServiciosCampo()==null
				&& municipioIMSS.getFechaInicioOperacionesServiciosUrbanos()==null
				&& municipioIMSS.getIdentificadorConvenio()==null)
			throw new GestionPatronalBusinessException("error.municipio.imss.configuration.incompleta");

		Date inicioOperacionesServiciosUrbanos = municipioIMSS.getFechaInicioOperacionesServiciosUrbanos();
		Date inicioOperacionesServiciosCampo = municipioIMSS.getFechaInicioOperacionesServiciosCampo();
		Integer idConvenio = municipioIMSS.getIdentificadorConvenio();
		Calendar calendarioPivote = Calendar.getInstance();
		calendarioPivote.set(1, 1, 1, 0 , 0, 0);
		Date fechaPivote = calendarioPivote.getTime();
		TipoAmbitoEnum tipoAmbito = obtenerTipoAmbitoPorModalidad(numModalidad);
		TipoConvenioEnum tipoConvenio = null;
		if(tipoAmbito.equals(TipoAmbitoEnum.URBANO)){
			log.error("::::Tipo de servicio urbano::::");
			if(inicioOperacionesServiciosUrbanos!=null && inicioOperacionesServiciosUrbanos.compareTo(fechaPivote)>0){
				log.error("::::Cuenta con fecha de Inicio de operaciones de servicios urbanos::::");
				if(fechaDeMovimiento.compareTo(inicioOperacionesServiciosUrbanos)>=0)
					return;
				else
					throw new GestionPatronalBusinessException("error.ambito.urbano.ejercicio.invalido");
			}else if(idConvenio!=null){
				log.error("::::Cuenta con convenio::::");
				tipoConvenio = TipoConvenioEnum.obternerEnumById(idConvenio);
				log.error("::::Tipo de convenio - "+ tipoConvenio+"::::");
				if(tipoConvenio.equals(TipoConvenioEnum.MIXTO) || tipoConvenio.equals(TipoConvenioEnum.URBANO))
					return;
				else
					throw new GestionPatronalBusinessException("error.convenio.servicios.urbanos.invalido");
			}else{
				log.error("::::No cuenta con fecha de inicio de operaciones ni convenio::::");
				throw new GestionPatronalBusinessException("error.ambito.urbano.invalido");
			}
		}else if(tipoAmbito.equals(TipoAmbitoEnum.CAMPO)){
			log.error("::::Tipo de servicio campo::::");
			if(inicioOperacionesServiciosCampo!=null && inicioOperacionesServiciosCampo.compareTo(fechaPivote)>0){
				log.error("::::Cuenta con fecha de Inicio de operaciones de servicios de campo::::");
				if(fechaDeMovimiento.compareTo(inicioOperacionesServiciosCampo)>=0)
					return;
				else
					throw new GestionPatronalBusinessException("error.ambito.campo.ejercicio.invalido");
			}else if(idConvenio!=null){
				log.error("::::Cuenta con convenio::::");
				tipoConvenio = TipoConvenioEnum.obternerEnumById(idConvenio);
				log.error("::::Tipo de convenio - "+ tipoConvenio+"::::");
				if(tipoConvenio.equals(TipoConvenioEnum.MIXTO) || tipoConvenio.equals(TipoConvenioEnum.CAMPO))
					return;
				else
					throw new GestionPatronalBusinessException("error.convenio.servicios.campo.invalido");
			}else{
				log.error("::::No cuenta con fecha de inicio de operaciones ni convenio::::");
				throw new GestionPatronalBusinessException("error.ambito.campo.invalido");
			}
		}else if(tipoAmbito.equals(TipoAmbitoEnum.MIXTO)){
			log.error("::::Ambito de Servicios Mixtos::::");
			if((inicioOperacionesServiciosCampo!=null && inicioOperacionesServiciosCampo.compareTo(fechaPivote)>0 )
					|| (inicioOperacionesServiciosUrbanos!=null && inicioOperacionesServiciosUrbanos.compareTo(fechaPivote)>0)){
				boolean operacionesDeCampoValidas=false;
				boolean operacionesUrbanasValidas=false;
				if(inicioOperacionesServiciosCampo!=null && inicioOperacionesServiciosCampo.compareTo(fechaPivote)>0){
					log.error("::::Cuenta con fecha de Inicio de operaciones de servicios de campo::::");
					if(fechaDeMovimiento.compareTo(inicioOperacionesServiciosCampo)>=0)
						operacionesDeCampoValidas = true;
				}
				if(inicioOperacionesServiciosUrbanos!=null){
					log.error("::::Cuenta con fecha de Inicio de operaciones de servicios urbanos::::");
					if(fechaDeMovimiento.compareTo(inicioOperacionesServiciosUrbanos)>=0)
						operacionesUrbanasValidas=true;
				}
				if(operacionesDeCampoValidas || operacionesUrbanasValidas)
					return;
				else
					throw new GestionPatronalBusinessException("error.ambito.servicios.invalidos");
			}else if(idConvenio!=null){
				log.error("::::Cuenta con convenio::::");
				tipoConvenio = TipoConvenioEnum.obternerEnumById(idConvenio);
				log.error("::::Tipo de convenio - "+ tipoConvenio+"::::");
				if(tipoConvenio.equals(TipoConvenioEnum.CAMPO) || tipoConvenio.equals(TipoConvenioEnum.URBANO) || tipoConvenio.equals(TipoConvenioEnum.MIXTO))
					return;
				else
					throw new GestionPatronalBusinessException("error.convenio.servicios.invalidos");
			}

		}else{
			log.error("No hay regla a aplicar para esta modalidad: "+numModalidad);
		}
	}

	private TipoAmbitoEnum obtenerTipoAmbitoPorModalidad(String numModalidad)throws GestionPatronalBusinessException{
		if(numModalidad.equals("10") || numModalidad.equals("17") ||numModalidad.equals("32") ||
			numModalidad.equals("33") ||numModalidad.equals("36") ||numModalidad.equals("38") ||
			numModalidad.equals("40") ||numModalidad.equals("42") ||numModalidad.equals("44")){
			return TipoAmbitoEnum.URBANO;
		}else if(numModalidad.equals("13") || numModalidad.equals("14") ||
				numModalidad.equals("30") || numModalidad.equals("43") ){
			return TipoAmbitoEnum.CAMPO;
		}else if(numModalidad.equals("35") || numModalidad.equals("46") ){
			return TipoAmbitoEnum.MIXTO;
		}
		throw new GestionPatronalBusinessException("error.modalidad.servicio.invalido");
	}

	@Override
	public void validarModalidad(Long cveIdPatronSujetoObligado,
			Clasificacion nuevaClasificacion) throws GestionPatronalBusinessException {
		// TODO Auto-generated method stub
		RegistroPatronal regPatron = registroPatronalService.obtenerDatosRegistroPatronal(cveIdPatronSujetoObligado);
		log.error("Modalidad actual del patron: "+regPatron.getModalidad().getNumModalidad());
		log.error("Division de la nueva clasificacion: "+nuevaClasificacion.getFraccion().getGrupo().getDivision().getNumDivision());
		if(regPatron.getModalidad().getNumModalidad().equals("13")){
			if(!nuevaClasificacion.getFraccion().getGrupo().getDivision().getNumDivision().equals("0"))
				throw new GestionPatronalBusinessException("error.modalidad.invalida");
		}else if(regPatron.getModalidad().getNumModalidad().equals("10")){
			if(nuevaClasificacion.getFraccion().getGrupo().getDivision().getNumDivision().equals("0"))
				throw new GestionPatronalBusinessException("error.modalidad.invalida");
		}else if(regPatron.getModalidad().getNumModalidad().equals("30")){
			StringBuffer fraccionCompleta = new StringBuffer().append(
					nuevaClasificacion.getFraccion().getGrupo().getDivision().getNumDivision()
					).append(nuevaClasificacion.getFraccion().getGrupo().getNumGrupo()
					).append(nuevaClasificacion.getFraccion().getNumFraccion());
			if(!fraccionCompleta.equals(FraccionEnum.AGRICULTURA.getCodigo())){
				throw new GestionPatronalBusinessException("error.modalidad.invalida");
			}
		}
	}
	
	@Override
	public void validarModalidadClasificacion(Long cveIdPatronSujetoObligado,
			Clasificacion nuevaClasificacion) throws GestionPatronalBusinessException {
		log.debug("::: Se buscara el sujeto obligado " + cveIdPatronSujetoObligado);
		RegistroPatronal regPatron = registroPatronalService.obtenerDatosRegistroPatronal(cveIdPatronSujetoObligado);
		log.debug("::: Registro patronal "+ regPatron.getNoRegPatronal()+". Modalidad actual del patron: "+regPatron.getModalidad().getNumModalidad());
		StringBuffer fraccionCompleta = new StringBuffer().append(
				nuevaClasificacion.getFraccion().getGrupo().getDivision().getNumDivision().trim()
				).append(nuevaClasificacion.getFraccion().getGrupo().getNumGrupo().trim()
				).append(nuevaClasificacion.getFraccion().getNumFraccion().trim());
		log.debug("::: Fraccion a comparar: " + fraccionCompleta.toString());		
		if(regPatron.getModalidad().getNumModalidad().equals("13")){
			if(!fraccionCompleta.toString().equals(FraccionEnum.AGRICULTURA.getCodigo())){
				log.debug("::: Se mandara error ya que la modalidad es 13 y la fraccion NO es AGRICULTURA 011");
				throw new GestionPatronalBusinessException("error.modalidad.invalida");
			}
		}else if(regPatron.getModalidad().getNumModalidad().equals("10")){
			log.debug("::: La  modalidad es 10, no se valida fraccion");
		}else if(regPatron.getModalidad().getNumModalidad().equals("30")){
			if(!fraccionCompleta.toString().equals(FraccionEnum.AGRICULTURA.getCodigo())){
				log.debug("::: Se mandara error ya que la modalidad es 30 y la fraccion NO es AGRICULTURA 011");
				throw new GestionPatronalBusinessException("error.modalidad.invalida");
			}
		}
	}		

	@Override
	public void validarNuevoRegistroPatronal(String numeroRegistroPatronal)
			throws GestionPatronalBusinessException {

		//Se modifica el cï¿½digo para valorar los RP a 8 posiciones y no a 10 u 11 como se realizaba por solicitud del usuario e ignacio espinosa
		SujetoObligado registroPatronal = sujetoObligadoService.consultarPorNumeroRegistroPatronal(numeroRegistroPatronal.substring(0, 8));
		if(registroPatronal!=null)
			throw new GestionPatronalBusinessException("El nÃºmero de registro patronal proporcionado ya existe.");

	}

	@Override
	public BigDecimal calcularPrima(Integer cveCausa, Long idPatron, Clasificacion clasificacionNueva){
		log.error("Evaluando prima");

		RegistroPatronal regPatron = registroPatronalService.obtenerDatosRegistroPatronal(idPatron);
		Clasificacion clasificacionActual = regPatron.getClasificacion();
		BigDecimal numPrimaPago = null;
		BigDecimal primaActual = clasificacionActual.getPrimaSRTActual();
		BigDecimal nuevaPrimaMedia = clasificacionNueva.getFraccion().getPrimaSRT();
		Long idFraccionActual=clasificacionActual.getFraccion().getId();
		Long idFraccionNueva=clasificacionNueva.getFraccion().getId();

		Long idClaseActual = clasificacionActual.getFraccion().getClase().getClave();
		Long idClaseNuevaFraccion = clasificacionNueva.getFraccion().getClase().getClave();

		if(cveCausa == TipoTramiteEnum.ALTA_SRT.getCodigo() ||
				cveCausa == TipoTramiteEnum.ALTA_SRT_PM.getCodigo()){
			log.error("Se asigna Prima media para el alta patronal: "+nuevaPrimaMedia);
			return nuevaPrimaMedia;
		}

		if(cveCausa != TipoTramiteEnum.DISPOSICION_DE_LEY.getCodigo()){
			if(idFraccionActual.equals(idFraccionNueva)){
				log.error("Misma fracciÃ³n, se conserva la prima: "+primaActual);
				numPrimaPago = primaActual;
			}else {//Distinta FracciÃ³n
				log.error("Distinta fraccion");
				if(idClaseActual.equals(idClaseNuevaFraccion)){ //Misma clase conserva la prima
					log.error("Misma clase, se conserva la prima: "+primaActual);
					numPrimaPago = primaActual;
				}else{
					log.error("Distinta clase, se asigna la prima media de la nueva clase: "+nuevaPrimaMedia);
					numPrimaPago = nuevaPrimaMedia;
				}
			}
			System.err.println("Se actualiza la prima por no ser cambio por disposicion de ley");
		}else{
			log.error("Cambio por disposiciï¿½n de ley, se conserva la prima: "+primaActual);
			numPrimaPago = primaActual;//Se conserva la prima anterior en cambio por disposiciï¿½n de ley
		}

		if(numPrimaPago.longValue() < primaActual.longValue()){
			log.error("Se marcarï¿½ registro para ser verificado en MAC");
			//TODO Marcar registro, se marca en un proceso anterior poniendo una nota a la solicitud en ref observaciones
		}
		return numPrimaPago;
	}

	@Override
	public Clasificacion obtenerClasificacionEquivalenteValida(
			Clasificacion inputObject, Integer idTipoTramite)
			throws GestionPatronalBusinessException {
		inputObject=clasificacionService.obtenerClasificacionEquivalente(inputObject);
		inputObject=validaReglasClasificacion(inputObject, idTipoTramite);
		return inputObject;
	}

	/**
	 * Valida reglas de modalidad y clasificaciï¿½n por municipio
	 * @param inputObject
	 * @throws GestionPatronalBusinessException
	 */
	private Clasificacion validaReglasClasificacion(Clasificacion inputObject, Integer idTipoTramite) throws GestionPatronalBusinessException{
		boolean validarActividadPorMunicipio=true;
		Long idPersona = null;
		Long idTipoPersona = null;
		Long idMunicipioIMSS = null;
		String rfc =null;
		String nrp=null;
		Long idPatronSujetoObligado = inputObject.getSujetoObligado()!=null ? inputObject.getSujetoObligado().getCveIdSujetoObligado() : null;//Este dato solo esta presenta en una modificaciï¿½n de SRT o centro de trabajo
		if(inputObject.getSujetoObligado()!=null &&
				inputObject.getSujetoObligado().getMunicipioIMSS()!=null &&
				StringUtils.isNotBlank(inputObject.getSujetoObligado().getMunicipioIMSS().getIdMunicipio())){
			idMunicipioIMSS = Long.valueOf(inputObject.getSujetoObligado().getMunicipioIMSS().getIdMunicipio());
			nrp = inputObject.getSujetoObligado().getNumeroRegistroPatronal();
			if(inputObject.getSujetoObligado().getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
				idPersona = inputObject.getSujetoObligado().getFisica().getCveFisica();
				idTipoPersona = TipoPersona.FISICA.longValue();
				rfc = inputObject.getSujetoObligado().getFisica().getRfc();
			}else{
				idPersona = inputObject.getSujetoObligado().getMoral().getIdPersona();
				idTipoPersona = TipoPersona.MORAL.longValue();
				rfc = inputObject.getSujetoObligado().getMoral().getRfc();
			}
		}else{
			log.error("No se tiene municipio asignado no se validara la regla de municipio");
			validarActividadPorMunicipio=false;
		}

		if(inputObject!=null && inputObject.getFraccion()!=null && inputObject.getFraccion().getId()!=null){
			Long idFraccion = inputObject.getFraccion().getId();
			log.error("Se validara la regla fraccion municipio");
			log.error("idPersona: ["+idPersona+"]");
			log.error("idTipoPersona: ["+idTipoPersona+"]");
			log.error("idMunicipio: ["+idMunicipioIMSS+"]");
			log.error("idFraccion: ["+idFraccion+"]");
			log.error("idRegistroPatronal: ["+idPatronSujetoObligado+"]");

			if(inputObject.getSujetoObligado()!=null && inputObject.getSujetoObligado().getCveIdSujetoObligado()!=null){
				log.error("se valida la regla de modalidad y se calcula la prima de pago");
				if( idTipoTramite.equals(TipoTramiteEnum.SUSTITUCION_PATRONAL_SUBCONTRATACION.getCodigo()) 
						|| idTipoTramite.equals(TipoTramiteEnum.SUSTITUCION_PATRONAL.getCodigo())
						|| idTipoTramite.equals(TipoTramiteEnum.FUSION.getCodigo()) 
						|| idTipoTramite.equals(TipoTramiteEnum.CAMBIO_DE_DOMICILIO_DIFERENTE_MUNICIPIO.getCodigo())
				){
					log.debug(":::: El tramite es "+idTipoTramite+", se aplica regla de modalidad para clasificacion");
					validarModalidadClasificacion(inputObject.getSujetoObligado().getCveIdSujetoObligado(), inputObject);
				}else{
					log.debug(":::: Se aplica regla de modalidad");
					validarModalidad(inputObject.getSujetoObligado().getCveIdSujetoObligado(), inputObject);
				}
				BigDecimal nuevaAsignada = calcularPrima(idTipoTramite, inputObject.getSujetoObligado().getCveIdSujetoObligado(), inputObject);
				inputObject.setPrimaSRTActual(nuevaAsignada);
			}

			if(validarActividadPorMunicipio)
				validarClasificacionPorPatronYMunicipio(rfc, idTipoPersona, idMunicipioIMSS, idFraccion, idPatronSujetoObligado);

			validarRPC_AP_MOD_MAC(rfc, inputObject.getFraccion().getClase().getClave(), nrp);
		}

		return inputObject;
	}

	@Override
	public boolean estaParametroRIFHabilitado() {
		String cadenaFechaInicioRif=parametrosServiceBusiness.obtenerParametroDeConfiguracion(SystemKeyParameters.KEY_FECHA_INICIO_RIF);
		Date fechaActual = Calendar.getInstance().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date fechaInicioRIF = sdf.parse(cadenaFechaInicioRif);
			if(fechaActual.after(fechaInicioRIF))
				return true;

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean isRegimenRIF(List<Regimen> regimenes) {
		if(regimenes==null)
			return false;
		for(Regimen regimen:regimenes){
			String cadenaRegimenRif=parametrosServiceBusiness.obtenerParametroDeConfiguracion(SystemKeyParameters.KEY_TIPO_REGIMEN_RIF);
			if(regimen.getClaveRegimen().equalsIgnoreCase(cadenaRegimenRif))
				return true;
		}
		return false;
	}

	@Override
	public boolean personaConRegimenRIF(Fisica persona) {
		ClienteWebserviceRfc clienteSat = new ClienteWebserviceRfc();
		Fisica fisicaSat=null;
		try {
			fisicaSat = clienteSat.buscarPersonaFisicaPorRfcEnSat(persona.getRfc());
			if(fisicaSat!=null){
				List<Regimen> regimenes = fisicaSat.getRegimenes();
				if(regimenes==null)
					return false;
				persona.setRegimenes(regimenes);
				for(Regimen regimen:regimenes){
					String cadenaRegimenRif=parametrosServiceBusiness.obtenerParametroDeConfiguracion(SystemKeyParameters.KEY_TIPO_REGIMEN_RIF);
					if(regimen.getClaveRegimen().equalsIgnoreCase(cadenaRegimenRif)){
						persona.setIndRIF(true);
						return true;
					}
				}
			}
		} catch (ClienteWebserviceSatRfcException e) {
			System.err.println("Error en consulta SAT");
			e.printStackTrace();
		}


		return false;
	}

	@Override
	public void validarFraccionObligatoria(Clasificacion clasif)throws GestionPatronalBusinessException{

		if(clasif.getSujetoObligado().getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA))
			return;

		String fraccionSeleccionada = formarFraccionCompleta(clasif.getFraccion());
		if(fraccionSeleccionada.equals(FraccionEnum.CONSTRUCCION.getCodigo()))
			return;

		String rfcPatron = clasif.getSujetoObligado().getMoral().getRfc();
		Fraccion frOblig=calcularFraccionObligatoriaParaPatron(rfcPatron);
		if(frOblig!=null)
			System.err.println("idFraccionOblig: "+frOblig!=null ? frOblig.getId() : "no hay");
		System.err.println("idFraccionSeleccionada: "+clasif.getFraccion()!=null ? clasif.getFraccion().getId() : "no hay");
		if(frOblig!=null)
			if(!clasif.getFraccion().getId().equals(frOblig.getId()))
				throw new GestionPatronalBusinessException("Su registro patronal debe estar asociado a alguna de las siguientes Fracciones: ["+formarFraccionCompleta(frOblig) +" o "+FraccionEnum.CONSTRUCCION.getCodigo()+"]", 801);

	}

	private Fraccion calcularFraccionObligatoriaParaPatron(String rfc){
		List<Fraccion> fraccionesActuales = claseEntity.obtenerFraccionesPreviasPorRfcPatron(rfc);

		for(Fraccion fr:fraccionesActuales){
			String fraccionCompleta = formarFraccionCompleta(fr);
			System.err.println("Fracciones encontradas para PM: "+fraccionCompleta);
			if(!fraccionCompleta.equalsIgnoreCase(FraccionEnum.CONSTRUCCION.getCodigo()))
				return fr;
		}
		return null;
	}

	private String formarFraccionCompleta(Fraccion fraccion){
		StringBuffer fraccionAEvaluar=new StringBuffer();
		fraccionAEvaluar.append(fraccion.getGrupo().getDivision().getNumDivision());
		fraccionAEvaluar.append(fraccion.getGrupo().getNumGrupo());
		fraccionAEvaluar.append(fraccion.getNumFraccion());
		return fraccionAEvaluar.toString();
	}


	public void validarFraccionesConsistentesPorPatron(String rfc)throws GestionPatronalBusinessException{
		List<Fraccion> fraccionesActuales = claseEntity.obtenerFraccionesPreviasPorRfcPatron(rfc);

		Map<String,Fraccion> fraccionesDistintasA411 = new TreeMap<String, Fraccion>();
		for(Fraccion fr:fraccionesActuales){
			String fraccionCompleta = formarFraccionCompleta(fr);
			if(!fraccionCompleta.equalsIgnoreCase(FraccionEnum.CONSTRUCCION.getCodigo())){
				fraccionesDistintasA411.put(fraccionCompleta, fr);
			}
		}

		if(fraccionesDistintasA411.size()>1)
			throw new GestionPatronalBusinessException("El tr\u00E1mite no puede realizarse debido a que cuenta con m\u00E1s de una actividad declarada. <br>Por favor regularice sus registros patronales.");

	}

	public void validarFraccionesConsistentesPorPatronV2(String rfc, String cvecMunicipioSINDO, Fraccion fraccion)throws GestionPatronalBusinessException{

		String fraccionSeleccionada = formarFraccionCompleta(fraccion);

		List<Fraccion> fraccionesActuales = claseEntity.obtenerFraccionesPreviasPorRfcPatronMunicipioIMSS(rfc, cvecMunicipioSINDO);
		log.debug("::: Encontre " + fraccionesActuales.size()
				+ " fracciones previas en municipio, rfc: " + rfc
				+ ", municipio: " + cvecMunicipioSINDO);
		Map<String,Fraccion> fraccionesDistintasA411 = new TreeMap<String, Fraccion>();
		for(Fraccion fr:fraccionesActuales){
			String fraccionCompleta = formarFraccionCompleta(fr);
			log.debug("::: Fraccion completa: " + fraccionCompleta);
			if(!fraccionCompleta.equalsIgnoreCase(FraccionEnum.CONSTRUCCION.getCodigo())){
				fraccionesDistintasA411.put(fraccionCompleta, fr);
			}
		}

		if(!fraccionSeleccionada.equals(FraccionEnum.CONSTRUCCION.getCodigo())){
			if(fraccionesDistintasA411.size() > 0){
				log.debug("::: Encontre " + fraccionesDistintasA411.size() + " fracciones distintas en la BD, se arroja GestionPatronalBusinessException");
				throw new GestionPatronalBusinessException(
						"El tr\u00E1mite no puede realizarse debido a que cuenta con m\u00E1s de una actividad declarada. <br>Por favor regularice sus registros patronales.",
						801);
			}
		}

	}

	@Override
	public void validarSociosRequeridos(Long cveIdPersonaMoral)
			throws GestionPatronalBusinessException {
		if(personaMoralServiceBusiness.consultaSindicatoPersonaMoral(cveIdPersonaMoral) <=0){
			Socio socio = new Socio();
			socio.setIdPersonaMoralPatron(cveIdPersonaMoral);
			List<Socio> socios = socioBusiness.sociosPorIdPersonaMoralPatron(socio);
			if(socios==null || (socios!=null && socios.size()==0))
				throw new GestionPatronalBusinessException("Para iniciar el tr\u00E1mite de alta patronal se requiere el registro de al menos un socio", TipoTramiteEnum.ACTUALIZACION_SOCIO.getCodigo());
		}
	}

	@Override
   	public String obtenerNrpPrimaRiesgoMayorPersonaFisica(String rfc) {
       	return claseEntity.obtenerNrpPrimaRiesgoMayorPersonaFisica(rfc);
    }

	// Se modifica por INC398780 para validar PF y PM con NRP previos en el mismo municipio para el mismo RFC 

//	public void validarRPPrevios(String rfc, String cvecMunicipioSINDO, Fraccion fraccion)throws GestionPatronalBusinessException{
//
//		String fraccionSeleccionada = formarFraccionCompleta(fraccion);
//
//		List<Fraccion> fraccionesActuales = claseEntity.obtenerFraccionesPreviasPorRfcPatronMunicipioIMSSRPC(rfc, cvecMunicipioSINDO);
//
//		log.debug("::: Encontre " + fraccionesActuales.size()
//				+ " fracciones previas en municipio, rfc: " + rfc
//				+ ", municipio: " + cvecMunicipioSINDO);
//
//		Map<String,Fraccion> fraccionesDistintasA411 = new TreeMap<String, Fraccion>();
//
//		for(Fraccion fr:fraccionesActuales){
//			String fraccionCompletaActual = formarFraccionCompleta(fr);
//			log.debug("::: Fraccion completa: " + fraccionCompletaActual);
//			if(!fraccionCompletaActual.equalsIgnoreCase(FraccionEnum.CONSTRUCCION.getCodigo())){
//				if(fr.getIndRPC() == null || fr.getIndRPC().intValue() != 1)
//					fraccionesDistintasA411.put(fraccionCompletaActual, fr);
//			}
//		}
//
//		if(!fraccionSeleccionada.equals(FraccionEnum.CONSTRUCCION.getCodigo())){
//			if(fraccionesDistintasA411.size() > 0){
//				log.debug("::: Encontre " + fraccionesDistintasA411.size() + " fracciones distintas en la BD, se arroja GestionPatronalBusinessException");
//				throw new GestionPatronalBusinessException(
//						"EL PATR\u00D3N TIENE ASIGNADO AL MENOS UN REGISTRO PATRONAL EN EL MUNICIPIO SELECCIONADO Y YA NO ES POSIBLE ASIGNAR OTRO.",
//						801);
//			}
//		}
//
//	}
	
	
	public void validarRPPrevios(SujetoObligado sujetoTramite)throws GestionPatronalBusinessException{

		Long tipoPersona = null;
		String rfc = null;
		
		log.debug("::: Validando regitros patronales previos");
		TipoPersonaFiscal tpFiscal = sujetoTramite.getTipoPersonaFiscal();
		
		if(tpFiscal.equals(TipoPersonaFiscal.FISICA)){
			tipoPersona = new Long(TipoPersona.FISICA);
			rfc = sujetoTramite.getFisica().getRfc();
			log.debug("::: TipoPersona: " + tipoPersona + ", rfc: " + rfc);
		}else if(tpFiscal.equals(TipoPersonaFiscal.MORAL)){
			tipoPersona = new Long(TipoPersona.MORAL);
			rfc = sujetoTramite.getMoral().getRfc();
			log.debug("::: TipoPersona: " + tipoPersona + ", rfc: " + rfc);
			Fraccion fraccion = sujetoTramite.getClasificacion().getFraccion();
			StringBuffer fraccionAEvaluar = new StringBuffer();
			fraccionAEvaluar.append(fraccion.getGrupo().getDivision().getNumDivision());
			fraccionAEvaluar.append(fraccion.getGrupo().getNumGrupo());
			fraccionAEvaluar.append(fraccion.getNumFraccion());
			if (fraccionAEvaluar.toString().equals(FraccionEnum.CONSTRUCCION.getCodigo())) {
				log.debug("::: Fraccion 411 para PM, se omite validacion");
				return;// se elimina esta reglpara personas morales con fraccion 411
			}
		}
			
		Long idMunicipioIMSS = Long.valueOf(sujetoTramite.getMunicipioIMSS().getIdMunicipio());
		Long idRegistroPatronalActual = sujetoTramite.getCveIdSujetoObligado();
		
		List<SujetoObligado> registroPatronalL = sujetoObligadoService.obtenerNRPEnMunicipioIMSSPorRFCyFraccion(rfc,
				tipoPersona, idMunicipioIMSS, sujetoTramite.getClasificacion().getFraccion().getId(), idRegistroPatronalActual);
		
		if(registroPatronalL == null) {
			registroPatronalL = new ArrayList<SujetoObligado>();
		}
		
		log.debug("::: Se encontraron "+registroPatronalL.size()+" registros con el mismo municipio y rfc: " + rfc);
		
		List<SujetoObligado> regPatL = new ArrayList<SujetoObligado>();
		//se omiten registros encontrados con fracicon 411
		if (tipoPersona.equals(new Long(TipoPersona.MORAL))) {
			log.debug("::: Para PM se quitan NRP con fraccion 411");
			for (Iterator<SujetoObligado> iterator = registroPatronalL.iterator(); iterator.hasNext();) {
				SujetoObligado sujetoObligado = iterator.next();
				log.debug("::: " + sujetoObligado.getNumeroRegistroPatronal());
				String fraccionCompletaActual = formarFraccionCompleta(sujetoObligado.getClasificacion().getFraccion());
				log.debug("::: Fraccion completa: " + fraccionCompletaActual);
				if(!fraccionCompletaActual.equalsIgnoreCase(FraccionEnum.CONSTRUCCION.getCodigo())){
					regPatL.add(sujetoObligado);
				}
			}
			log.debug("::: NRP diferentes a 411: " + regPatL.size());
		}else {
			log.debug("::: Para PF se conservan todos los NRP encontrados");
			regPatL = registroPatronalL;
		}
		
		if (regPatL.size() > 0) {
			StringBuffer mensaje = new StringBuffer();
			log.debug("::: Se encontraron los siguientes RP dentro del municipio con mismo RFC: " + regPatL.size());
			if(regPatL.size() > 1) {
				mensaje.append("Actualmente cuenta con los siguientes registros patronales: ");

			}else {
				mensaje.append("Actualmente cuenta con el registro patronal ");
			}
			for (Iterator<SujetoObligado> iterator = regPatL.iterator(); iterator.hasNext();) {
				SujetoObligado sujetoObligado = iterator.next();
				log.debug(sujetoObligado.getNumeroRegistroPatronal() + ", getDescSituacionBaja: " + sujetoObligado.getDescSituacionBaja());
				if(sujetoObligado.getDescSituacionBaja() == null || !sujetoObligado.getDescSituacionBaja().equals(CausaBajaPatronEnum.BAJA.getDescripcion())){
					mensaje.append(sujetoObligado.getNumeroRegistroPatronal());
					mensaje.append(sujetoObligado.getModalidad() != null ? sujetoObligado.getModalidad().getNumModalidad() : "");
					mensaje.append(sujetoObligado.getDigVerificador() != null ? sujetoObligado.getDigVerificador() : "");
					mensaje.append(" con la misma actividad seleccionada asociado al municipio ");
					mensaje.append(sujetoObligado.getMunicipioIMSS().getDescMunicipio() + ". ");					
				}else if(sujetoObligado.getDescSituacionBaja().equals(CausaBajaPatronEnum.BAJA.getDescripcion())){
					mensaje.append(sujetoObligado.getNumeroRegistroPatronal());
					mensaje.append(sujetoObligado.getModalidad() != null ? sujetoObligado.getModalidad().getNumModalidad() : "");
					mensaje.append(sujetoObligado.getDigVerificador() != null ? sujetoObligado.getDigVerificador() : "");
					mensaje.append(" en BAJA con la misma actividad seleccionada asociado al municipio ");
					mensaje.append(sujetoObligado.getMunicipioIMSS().getDescMunicipio() + ", por favor acuda a ventanilla a reanudarlo.");					
				}
			}	
			log.debug("::: Regresando mensaje: " + mensaje.toString());
			throw new GestionPatronalBusinessException(mensaje.toString(), 800);
		}
		
	}
	
	public void validarRPPreviosFisica(String rfc, String cvecMunicipioSINDO)throws GestionPatronalBusinessException{

		List<Fraccion> fraccionesActuales = claseEntity.obtenerFraccionesPreviasPorRfcPatronMunicipioIMSSRPC(rfc, cvecMunicipioSINDO);

		log.debug("::: Encontre " + fraccionesActuales.size()
				+ " fracciones previas en municipio, rfc: " + rfc
				+ ", municipio: " + cvecMunicipioSINDO);
		
		if(fraccionesActuales.size() > 0){
			throw new GestionPatronalBusinessException(
					"EL PATR\u00D3N TIENE ASIGNADO AL MENOS UN REGISTRO PATRONAL EN EL MUNICIPIO SELECCIONADO Y YA NO ES POSIBLE ASIGNAR OTRO.",
					801);
		}
	}	

}
