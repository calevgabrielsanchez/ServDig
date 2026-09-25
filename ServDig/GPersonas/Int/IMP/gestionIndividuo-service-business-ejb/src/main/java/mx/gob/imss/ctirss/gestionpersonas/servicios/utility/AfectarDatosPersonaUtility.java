package mx.gob.imss.ctirss.gestionpersonas.servicios.utility;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.Stateless;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import mx.gob.imss.ctirss.delta.exception.individuo.AfectacionDatosPersonaException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.enums.CambioComparacionEnum;
import mx.gob.imss.ctirss.delta.model.enums.SexoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.asegurado.integracion.sindo.MovCorreccionesDatosAseguradoType;
import mx.gob.imss.ctirss.delta.model.gestion.asegurado.integracion.sindo.util.MovCorreccionesDatosAseguradoTypeBuilder;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.AfectarDatosPersonaWrapper;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosRespuesta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.MDMDatosEntrada;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoServicioModificacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCambioInformacionPersona;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

@Stateless(mappedName = "afectarDatosPersonaUtility")
public class AfectarDatosPersonaUtility extends AbstractServiceUtility
		implements AfectarDatosPersonaUtilityLocal, AfectarDatosPersonaUtilityRemote {

	private HashMap<String,String> mapaCaracteres;
	
	{
		mapaCaracteres = new HashMap<String,String>();
		mapaCaracteres.put("[ñÑ]", "#");
		mapaCaracteres.put("[ÁáÄä]", "A");
		mapaCaracteres.put("[ÉéËë]", "E");
		mapaCaracteres.put("[ÍíÏï]", "I");
		mapaCaracteres.put("[ÓóÖö]", "O");
		mapaCaracteres.put("[ÚúÜü]", "U");
		mapaCaracteres.put("[ÚúÜü]", "U");
		mapaCaracteres.put("[\\(\\)]", " ");
		mapaCaracteres.put("[?¿]","");
		mapaCaracteres.put("-"," ");
		mapaCaracteres.put("NULL"," ");
	}
	
	
	@Override
	public AfectarDatosPersonaWrapper crearWrapperDesdeICA(
			TramiteCambioInformacionPersona tramite)
			throws AfectacionDatosPersonaException {
		
		AfectarDatosPersonaWrapper datosPersona = null;
				
		ICADatosRespuesta datosRespuesta = tramite.getDatosICA();
		Map<String, CambioComparacionEnum> diferencias = datosRespuesta.getCambios();
		
		// Se valida que haya diferencias
		if (existenDiferencias(diferencias)) {
			
			datosPersona = new AfectarDatosPersonaWrapper();
			
			// Se valida qué tipo de persona se modificó
			if(datosRespuesta.getPersonaFisicaIMSS() != null){
				datosPersona.setFisica(datosRespuesta.getPersonaFisicaIMSS());
			} else if(datosRespuesta.getPersonaMoralIMSS() != null){
				datosPersona.setMoral(datosRespuesta.getPersonaMoralIMSS());
			} else {
				throw new AfectacionDatosPersonaException(
						"No se puede generar el wrapper, ya que no se cuenta con la información necesaria");
			}
			
			datosPersona.setTipoServicio(TipoServicioModificacionEnum.ICA);
			
			/*
			 * Se settean las banderas dependiendo de las diferencias que se hayan
			 * encontrado en el ICA
			 */
			if (datosRespuesta.getIndicadorConsultaRENAPO()) {
				if ((diferencias.containsKey("nombre") && fueCambio(diferencias.get("nombre")))
						|| (diferencias.containsKey("primerApellido") && fueCambio(diferencias.get("primerApellido")))
						|| (diferencias.containsKey("segundoApellido") && fueCambio(diferencias.get("segundoApellido")))) {
					datosPersona.setModificarNombre(true);
					datosPersona.setModificarDatosRENAPO(true);
				}
		
				if (diferencias.containsKey("curp")
						&& fueCambio(diferencias.get("curp"))) {
					datosPersona.setModificarCURP(true);
					datosPersona.setModificarDatosRENAPO(true);
				}
				
				if (diferencias.containsKey("sexo")
						&& fueCambio(diferencias.get("sexo"))) {
					datosPersona.setModificarSexo(true);
					datosPersona.setModificarDatosRENAPO(true);
				}
				
				if (diferencias.containsKey("fechaNacimiento")
						&& fueCambio(diferencias.get("fechaNacimiento"))) {
					datosPersona.setModificarFechaNacimiento(true);
					datosPersona.setModificarDatosRENAPO(true);
				}
				
				if ((diferencias.containsKey("lugarNacimiento") && fueCambio(diferencias.get("lugarNacimiento"))) 
						|| (diferencias.containsKey("nacionalidad") && fueCambio(diferencias.get("nacionalidad")))) {
					datosPersona.setModificarLugarNacimiento(true);
					datosPersona.setModificarDatosRENAPO(true);
				}
				
				if (diferencias.containsKey("doctoProbatorio")
						&& fueCambio(diferencias.get("doctoProbatorio"))) {
					datosPersona.setModificarDocumentoProbatorio(true);
					datosPersona.setModificarDatosRENAPO(true);
				}
			}
			
			if (datosRespuesta.getIndicadorConsultaSAT()) {
				if (diferencias.containsKey("rfc")
						&& fueCambio(diferencias.get("rfc"))) {
					datosPersona.setModificarRFC(true);
					datosPersona.setModificarDatosSAT(true);
				}
				
				if (diferencias.containsKey("domicilioFiscal")
						&& fueCambio(diferencias.get("domicilioFiscal"))) {
					datosPersona.setModificarDomicilioFiscal(true);
					datosPersona.setModificarDatosSAT(true);
				}
				
				// Medios fiscales
				if ((diferencias.containsKey("correoElectronico") && fueCambio(diferencias.get("correoElectronico")))
						|| (diferencias.containsKey("telefonoFijo") && fueCambio(diferencias.get("telefonoFijo")))
						|| (diferencias.containsKey("telefonoMovil") && fueCambio(diferencias.get("telefonoMovil")))) {
					datosPersona.setModificarMediosContactoFiscales(true);
					datosPersona.setModificarDatosSAT(true);
				}
				
				if (diferencias.containsKey("nombreRazonSocial")
						&& fueCambio(diferencias.get("nombreRazonSocial"))) {
					this.log.info("PASO POR Razon social" + diferencias.get("nombreRazonSocial"));
					datosPersona.setModificarRazonSocial(true);
					datosPersona.setModificarDatosSAT(true);
				}
				
				if ((diferencias.containsKey("fechaConstitucion")
						&& fueCambio(diferencias.get("fechaConstitucion")))
						|| (diferencias.containsKey("fechaInicioOperaciones")
						&& fueCambio(diferencias.get("fechaInicioOperaciones")))) {
					datosPersona.setModificarFechaCreacion(true);
					datosPersona.setModificarDatosSAT(true);
				}
				
				this.log.info("PASO POR Razon social" + diferencias.get("nombreRazonSocial"));
				this.log.info("PARA OBSERVAR QUE VENGA LLENO TIPO DE SOCIEDAD por medio de tipoSociedad" + diferencias.get("tipoSociedad"));
				this.log.info("PARA OBSERVAR QUE VENGA LLENO TIPO DE SOCIEDAD por medio de tipoSociedadNullSat" + diferencias.get("tipoSociedadNullSat"));
				
				
				///Se agrega nuevo cambio para que activa bandera para el cambio de tipoSociedad
//				if (!diferencias.get("tipoSociedad").equals("")
//						|| diferencias.get("tipoSociedad") != null) {
//					this.log.info("ENTRO EN TIPO DE SOCIEDAD PARA TRUE");
//					datosPersona.setModificarTipoSociedad(true);
//					datosPersona.setModificarDatosSAT(true);
//				}else{
//					this.log.info("ENTRO EN TIPO DE SOCIEDAD PARA FALSE");
//					datosPersona.setModificarTipoSociedad(false);
//					datosPersona.setModificarDatosSAT(false);
//					
//				}

				
				if (diferencias.containsKey("tipoSociedad")
						&& fueCambio(diferencias.get("tipoSociedad"))) {
					this.log.info("PASO POR TIPO DE SOCIEDAD");
					datosPersona.setModificarTipoSociedad(true);
					datosPersona.setModificarDatosSAT(true);
				}
				
				if (diferencias.containsKey("situacionSAT")
						&& fueCambio(diferencias.get("situacionSAT"))) {
					datosPersona.setModificarSituacion(true);
					datosPersona.setModificarDatosSAT(true);
				}
				
				
				
			}
			
			if (diferencias.containsKey("datosComplementarios")
					&& fueCambio(diferencias.get("datosComplementarios"))) {
				datosPersona.setModificarDatosComplementarios(true);
				
				if (diferencias.containsKey("actaConstitutiva")
						&& fueCambio(diferencias.get("actaConstitutiva"))) {
					datosPersona.setModificarActaConstitutiva(true);
				}
				if (diferencias.containsKey("registroSindicato")
						&& fueCambio(diferencias.get("registroSindicato"))) {
					datosPersona.setModificarRegistroSindicato(true);
				}
				
				
			}
			
		} else {
			this.log.info("No existen diferencias para afectar desde ICA.");
		}
		
		return datosPersona;

	}

	@Override
	public AfectarDatosPersonaWrapper crearWrapperDesdeModificacionManual(
			TramiteCambioInformacionPersona tramite)
			throws AfectacionDatosPersonaException {

		AfectarDatosPersonaWrapper datosPersona = null;
		
		MDMDatosEntrada datosEntrada = tramite.getDatosModifManual();
		Map<String, CambioComparacionEnum> diferencias = datosEntrada.getCambios();
		
		// Se valida que existan diferencias
		boolean existenDiferencias = existenDiferencias(diferencias);		
		if (!existenDiferencias) {
			/* 
			 * No existen diferencias, pero puede ser que se hayan
			 * modificado los datos complementarios, los cuales
			 * no vienen en el mapa de las diferencias
			 */
			diferencias = new HashMap<String, CambioComparacionEnum>();
		}

		if(existenDiferencias || 
				BooleanUtils.isTrue(datosEntrada.getIndCapturaDatosComplementarios())) {
			
			datosPersona = new AfectarDatosPersonaWrapper();
			
			// Se valida qué tipo de persona se modificó
			if (datosEntrada.getPersonaFisica() != null) {
				datosPersona.setFisica(datosEntrada.getPersonaFisica());
			} else if (datosEntrada.getPersonaMoral() != null) {
				datosPersona.setMoral(datosEntrada.getPersonaMoral());
			} else {
				throw new AfectacionDatosPersonaException(
						"No se puede generar el wrapper, ya que no se cuenta con la información necesaria");
			}
			
			datosPersona.setTipoServicio(TipoServicioModificacionEnum.MDM);
			
			/*
			 * Se settean las banderas dependiendo de las diferencias que se hayan
			 * encontrado en la modificación manual y de los campos que se
			 * capturaron
			 */
			if (((diferencias.containsKey("nombre") && fueCambio(diferencias.get("nombre")))
					|| (diferencias.containsKey("primerApellido") && fueCambio(diferencias.get("primerApellido")))
					|| (diferencias.containsKey("segundoApellido") && fueCambio(diferencias.get("segundoApellido"))))
					&& BooleanUtils.isTrue(datosEntrada.getIndCapturaNombre())) {
				datosPersona.setModificarNombre(true);
				datosPersona.setModificarDatosRENAPO(true);
			}
	
			if (diferencias.containsKey("curp")
					&& fueCambio(diferencias.get("curp"))
					&& BooleanUtils.isTrue(datosEntrada.getIndCapturaCURP())) {
				datosPersona.setModificarCURP(true);
				datosPersona.setModificarDatosRENAPO(true);
			}
			
			if (diferencias.containsKey("sexo")
					&& fueCambio(diferencias.get("sexo"))
					&& BooleanUtils.isTrue(datosEntrada.getIndCapturaSexo())) {
				datosPersona.setModificarSexo(true);
				datosPersona.setModificarDatosRENAPO(true);
			}
			
			if (diferencias.containsKey("fechaNacimiento")
					&& fueCambio(diferencias.get("fechaNacimiento"))
					&& BooleanUtils.isTrue(datosEntrada.getIndCapturaFechaNacimiento())) {
				datosPersona.setModificarFechaNacimiento(true);
				datosPersona.setModificarDatosRENAPO(true);
			}
			
			if (((diferencias.containsKey("lugarNacimiento") && fueCambio(diferencias.get("lugarNacimiento"))) 
					|| (diferencias.containsKey("nacionalidad") && fueCambio(diferencias.get("nacionalidad"))))
					&& BooleanUtils.isTrue(datosEntrada.getIndCapturaLugarNacimiento())) {
				datosPersona.setModificarLugarNacimiento(true);
				datosPersona.setModificarDatosRENAPO(true);
			}
			
			if (diferencias.containsKey("doctoProbatorio")
					&& fueCambio(diferencias.get("doctoProbatorio"))
					|| BooleanUtils.isTrue(datosEntrada.getIndCapturaDocumentoProbatorio())) {
				datosPersona.setModificarDocumentoProbatorio(true);
				datosPersona.setModificarDatosRENAPO(true);
			}
			
			if (diferencias.containsKey("rfc") && fueCambio(diferencias.get("rfc"))
					&& (BooleanUtils.isTrue(datosEntrada.getIndCapturaRFC()) 
							|| BooleanUtils.isTrue(datosEntrada.getIndCapturaDomicilioFiscal()) 
							|| BooleanUtils.isTrue(datosEntrada.getIndCapturaMediosContactoFiscales()))) {
				datosPersona.setModificarRFC(true);
				datosPersona.setModificarDatosSAT(true);
			}
			
			if (diferencias.containsKey("domicilioFiscal")
					&& fueCambio(diferencias.get("domicilioFiscal"))
					&& BooleanUtils.isTrue(datosEntrada.getIndCapturaDomicilioFiscal())) {
				datosPersona.setModificarDomicilioFiscal(true);
				datosPersona.setModificarDatosSAT(true);
			}
			
			// Medios fiscales
			if (((diferencias.containsKey("correoElectronico") && fueCambio(diferencias.get("correoElectronico")))
					|| (diferencias.containsKey("telefonoFijo") && fueCambio(diferencias.get("telefonoFijo")))
					|| (diferencias.containsKey("telefonoMovil") && fueCambio(diferencias.get("telefonoMovil")))) 
					|| BooleanUtils.isTrue(datosEntrada.getIndCapturaMediosContactoFiscales())) {
				datosPersona.setModificarMediosContactoFiscales(true);
				datosPersona.setModificarDatosSAT(true);
			}
			
			if (diferencias.containsKey("nombreRazonSocial")
					&& fueCambio(diferencias.get("nombreRazonSocial"))
					&& BooleanUtils.isTrue(datosEntrada.getIndCapturaRazonSocial())) {
				this.log.info("HAY CAMBIOS PARA LA RAZON SOCIAL");
				datosPersona.setModificarRazonSocial(true);
				datosPersona.setModificarDatosSAT(true);
			}
			
			if (((diferencias.containsKey("fechaConstitucion")
					&& fueCambio(diferencias.get("fechaConstitucion")))
					|| (diferencias.containsKey("fechaInicioOperaciones")
					&& fueCambio(diferencias.get("fechaInicioOperaciones")))) 
					&& BooleanUtils.isTrue(datosEntrada.getIndCapturaFechaConstitucion())) {
				datosPersona.setModificarFechaCreacion(true);
				datosPersona.setModificarDatosSAT(true);
			}
			
			if (diferencias.containsKey("tipoSociedad")
					&& fueCambio(diferencias.get("tipoSociedad"))
					&& BooleanUtils.isTrue(datosEntrada.getIndCapturaTipoSociedad())) {
				this.log.info("HAY CAMBIOS PARA EL TIPO DE SOCIEDAD");
				datosPersona.setModificarTipoSociedad(true);
				datosPersona.setModificarDatosSAT(true);
			}
					
			/*
			 * Dado que sólo en la modificación manual se afectan los medios de
			 * contacto particulares y los domicilios particulares (fisica) y el
			 * acta constitutiva ó registro sindica (moral), se settean las banderas
			 * dependiendo si se pidió capturar y no con el mapa que contiene los
			 * cambios entre entidades
			 */
			if (BooleanUtils.isTrue(datosEntrada.getIndCapturaDatosComplementarios())) {
				datosPersona.setModificarDatosComplementarios(true);
				
				if(datosPersona.getFisica() != null){
					datosPersona.setModificarDomicilioParticular(BooleanUtils.isTrue(datosEntrada.getIndCapturaDomicilioParticular()));
					datosPersona.setModificarMediosContactoParticular(BooleanUtils.isTrue(datosEntrada.getIndCapturaMediosContactoParticular()));
				} else if(datosPersona.getMoral() != null){
					datosPersona.setModificarActaConstitutiva(BooleanUtils.isTrue(datosEntrada.getIndCapturaActaConstitutiva()));
					datosPersona.setModificarRegistroSindicato(BooleanUtils.isTrue(datosEntrada.getIndCapturaRegistroSindicato()));
				}
			}
		} else {
			this.log.info("No existen diferencias para afectar desde Modificación Manual.");
		}
				
		return datosPersona;
	}

	private boolean fueCambio(CambioComparacionEnum cambio) {

		boolean fueCambio = false;

		if (cambio.getId().longValue() == CambioComparacionEnum.CAMBIO.getId()
				.longValue()
				|| cambio.getId().longValue() == CambioComparacionEnum.NUEVO
						.getId().longValue()) {
			fueCambio = true;
		}

		return fueCambio;
	}
	
	@Override
	public boolean existenDiferencias(
			Map<String, CambioComparacionEnum> diferencias) {
		
		boolean existenDiferencias = false;
		
		if(diferencias != null && !diferencias.isEmpty()){
			for (Entry<String, CambioComparacionEnum> entry : diferencias.entrySet()) {
			    if(fueCambio(entry.getValue())) {
			    	existenDiferencias = true;
			    	break;
			    }
			}
		}
		
		return existenDiferencias;
		
	}
	
	/**
	 * Metodo encargado de generar el movimiento 06 a SINDO para asegurados
	 * @param asegurado datos de la persona a actualizara
	 * @param folioSolicitud información para asociar el tramite que realiza la modificacíon puede ser nulo si no se requerie seguimiento
	 * @param aplicativoOrigenMovimiento para identificar el aplicativo que realiza el tramite puede ser nulo recibe un String de longitud 3
	 * @return MovCorreccionesDatosAseguradoType con la estructura requerida para encolar el movimiento en OSB
	 */
	@Override
	public MovCorreccionesDatosAseguradoType generarMovimientoActualizacionAseguradoSINDO(
			Fisica asegurado, String folioSolicitud, String aplicativoOrigenMovimiento) {
		//en caso de que no venga el historico se setena los datos de asegurado con CURP
		

		// Para SINDO HOMBRE = 1, MUJER = 2
		int sexo = asegurado.getSexo().getIdSexo().longValue() == SexoEnum.HOMBRE
				.getId() ? 1 : 2;

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(asegurado.getFechaNacimiento());

		int mesNacimiento = calendar.get(Calendar.MONTH) + 1;

		StringBuffer nombreCompletoActualizado = new StringBuffer();
		nombreCompletoActualizado.append(StringUtils.isNotBlank(asegurado.getPrimerApellido()) ? limpiarCaracteres(asegurado.getPrimerApellido()) : "").append("$");
		nombreCompletoActualizado.append(StringUtils.isNotBlank(asegurado
				.getSegundoApellido()) ? limpiarCaracteres(asegurado.getSegundoApellido()) : "").append("$");
		nombreCompletoActualizado.append(StringUtils.isNotBlank(asegurado.getNombre())?limpiarCaracteres(asegurado.getNombre()):"");
		
		StringBuffer nombreCompletoAnterior = new StringBuffer();
		nombreCompletoAnterior.append(StringUtils.isNotBlank(asegurado.getPrimerApellido()) ? limpiarCaracteres(asegurado.getPrimerApellido()) : "").append("$");
		nombreCompletoAnterior.append(StringUtils.isNotBlank(asegurado
				.getSegundoApellido()) ? limpiarCaracteres(asegurado.getSegundoApellido()) : "").append("$");
		nombreCompletoAnterior.append(StringUtils.isNotBlank(asegurado.getNombre())?limpiarCaracteres(asegurado.getNombre()):"");
		
		// Día juliano a 3 posiciones
		String diaJuliano = String.format("%tj", System.currentTimeMillis());
		StringBuffer folio = new StringBuffer();
		// Como no se conoce el id de la delegacion se manda 00
		folio.append("00");
		folio.append(diaJuliano);
		
		
		
		if(folioSolicitud ==null){
			folioSolicitud = "";
		}else{
			if(aplicativoOrigenMovimiento !=null){
				folioSolicitud = StringUtils.rightPad(folioSolicitud, 47) + aplicativoOrigenMovimiento;
			};
		}
		
		log.debug("el folio de la solicitud quedo como [" +folioSolicitud+"]");
	
		GregorianCalendar gcal = (GregorianCalendar) GregorianCalendar.getInstance();
		XMLGregorianCalendar xFecha = null;
		try{
			 xFecha = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
		}catch(Exception e){
			log.error("Ocurrio un error al generar la instancia de la fecha" , e);
			e.printStackTrace();
		}
		MovCorreccionesDatosAseguradoType movimientoSINDO = new MovCorreccionesDatosAseguradoTypeBuilder()
				.withDelOrig(0)
				.withSubOrig(0)
				.withCveAplic(2)
				.withTpMovto(6)
				.withOrigenMov(6)
				.withNumFolio(folio.toString())
				.withArgumento(0)
				.withFMovto(xFecha)
				.withFRecepMovi(xFecha)
				.withCveUnica(StringUtils.isNotBlank(asegurado.getCurp()) ? asegurado.getCurp() : "")
				.withNumSegSoc(asegurado.getNss().substring(0, 10))
				.withDigVrNss(Integer.valueOf(asegurado.getNss().substring(10,asegurado.getNss().length())))
				.withNomAseg(nombreCompletoAnterior.toString())
				.withSexo(sexo)
				.withMesNac(mesNacimiento)
				.withLugarNac(Integer.valueOf(asegurado.getLugarNacimiento().getClave()))
				.withNomAsegC(nombreCompletoActualizado.toString())
				.withLocMpio(folioSolicitud).build();
		return movimientoSINDO;

	}

	@Override
	public MovCorreccionesDatosAseguradoType generarMovimientoActualizacionRegistroAsegurado(GrupoFamiliar grupofamiliar) throws AfectacionDatosPersonaException{
		try{
			int delOrigen = 0;
			int subOrigen = 0;
			int umf = 0;
			
			
			log.info("--> Comienzo de Validacion de datos de la UMF de origen");

			MedicoEnTurno medicoEnTurno = grupofamiliar.getMedicoEnTurno();
			UnidadMedicaFamiliar unidadMedicaFamiliar = null;
			Subdelegacion subdelegacion = null;
			Delegacion delegacion = null;
			
			if (medicoEnTurno != null) {
				unidadMedicaFamiliar = medicoEnTurno.getUnidadMedicaFamiliar();
				if (unidadMedicaFamiliar != null) {
					if (unidadMedicaFamiliar.getNoEconomico() != null) {
						umf = unidadMedicaFamiliar.getNoEconomico().intValue();
						log.info("--> La UMF de ORIGEN es" + umf);
					}
					subdelegacion = unidadMedicaFamiliar.getSubdelegacion();
					if (subdelegacion != null) {
						subOrigen = Integer.parseInt(subdelegacion.getClave());
						log.info("--> La subdeleacion de ORIGEN es" + subOrigen);
						delegacion = subdelegacion.getDelegacion();

						if (delegacion != null) {
							delOrigen = Integer.parseInt(delegacion.getClave());
							log.info("--> La delegacion de ORIGEN  es: " + delOrigen);
						}
					}
				}
			}
			
			/*
			if(grupofamiliar.getMedicoEnTurno() != null && grupofamiliar.getMedicoEnTurno().getUnidadMedicaFamiliar() != null && grupofamiliar.getMedicoEnTurno().getUnidadMedicaFamiliar().getSubdelegacion() != null && grupofamiliar.getMedicoEnTurno().getUnidadMedicaFamiliar().getSubdelegacion().getDelegacion() != null){
				log.debug("1 " + grupofamiliar.getMedicoEnTurno().getUnidadMedicaFamiliar().getSubdelegacion().getDelegacion().getClave());
				delOrigen = Integer.parseInt(grupofamiliar.getMedicoEnTurno().getUnidadMedicaFamiliar().getSubdelegacion().getDelegacion().getClave());
			}
			if(grupofamiliar.getMedicoEnTurno() != null && grupofamiliar.getMedicoEnTurno().getUnidadMedicaFamiliar() != null && grupofamiliar.getMedicoEnTurno().getUnidadMedicaFamiliar().getSubdelegacion() != null){
				log.debug("La subdelegacion origen es: " + grupofamiliar.getMedicoEnTurno().getUnidadMedicaFamiliar().getSubdelegacion().getClave());
				subOrigen = Integer.parseInt(grupofamiliar.getMedicoEnTurno().getUnidadMedicaFamiliar().getSubdelegacion().getClave());
			}
			if(grupofamiliar.getMedicoEnTurno() != null && grupofamiliar.getMedicoEnTurno().getUnidadMedicaFamiliar() != null ){
				log.debug("La umf origen es : " +  grupofamiliar.getMedicoEnTurno().getUnidadMedicaFamiliar().getNoEconomico().intValue());
				umf = grupofamiliar.getMedicoEnTurno().getUnidadMedicaFamiliar().getNoEconomico().intValue();
			}*/
			
			Fisica asegurado = grupofamiliar.getDerechohabiente();
			GregorianCalendar gcal = (GregorianCalendar) GregorianCalendar.getInstance();
		    XMLGregorianCalendar xFecha = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
			
		    String segundoApellido = StringUtils.isBlank(asegurado.getSegundoApellido()) ? "" : limpiarCaracteres(asegurado.getSegundoApellido());
			MovCorreccionesDatosAseguradoType movimiento = new MovCorreccionesDatosAseguradoTypeBuilder()
			.withDelOrig(delOrigen)
			.withCveAplic(2)
			.withSubOrig(subOrigen)
			.withTpMovto(4)
			.withOrigenMov(0)
			.withNumFolio(subOrigen > 9 ? (subOrigen + "411" ):("0"+ subOrigen + "411" ))
			.withArgumento(0)
			.withRegPatron("          ")
			.withDigVrPat(0)
			.withFMovto(xFecha)
			.withFRecepMovi(xFecha)
			.withCveUnica(grupofamiliar.getAsignacionNSS().getCurp() != null ? grupofamiliar.getAsignacionNSS().getCurp() : "" )
			.withIdSubrServ(0)
			.withIdEventual(0)
			.withNumSegSoc(grupofamiliar.getAsignacionNSS().getNss() != null ?  grupofamiliar.getAsignacionNSS().getNss().substring(0,10):"          ")
			.withDigVrNss(grupofamiliar.getAsignacionNSS().getNss()!= null ?  new Integer(grupofamiliar.getAsignacionNSS().getNss().substring(10,11)):0)
			.withNomAseg(limpiarCaracteres(asegurado.getPrimerApellido()) + "$" + segundoApellido + "$" + limpiarCaracteres(asegurado.getNombre()))
			.withIdExtemp(0)
			.withReducPago(0)
			.withExtODel(0)
			.withSalBase(0)
			.withSalInfonavit(0)
			.withTpSalario(0)
			.withSexo(asegurado.getSexo().getIdSexo())
			.withMesNac(0)
			.withLugarNac(0)
			.withUmf(umf)
			.withAutPerm(0)
			.withDelDest(0)
			.withSubDest(0)
			.withTpDerech(0)
			.withAaNac(0)
			.withSituacion(0)
			.withTsalODel("0")
			.withNombreDh("                                                  ")
			.withMesNacAp(0)
			.withNssCorr(0)
			.withDigVrNssCorr(0)
			.withNomAsegC("                                                  ")
			.withTpPens(0)
			.withAlfGuar("0")
			.withNumGuar(0)
			.withCondicion(0)
			.withLocMpio("                                                  ")
			.withTpProrroga(0)
			.withFecTerProrr(null)
			.withIdPd(0).build();
			
			log.debug("El movimiento que se enviara es: " +movimiento);
			return movimiento;	
		}catch(Exception e){
			log.error("Ocurrio un error al generar la trama par sindo" , e);
			e.printStackTrace();
			//throw e;
			throw new AfectacionDatosPersonaException("Ocurrio un error al generar el objeto" + e.getMessage());
		}
	}
	
	
	private String limpiarCaracteres(String cadena){
		for (Entry<String, String> item : mapaCaracteres.entrySet()) {
			cadena = cadena.replaceAll(item.getKey(), item.getValue());
		}
		return cadena;
	}
	
	
}
