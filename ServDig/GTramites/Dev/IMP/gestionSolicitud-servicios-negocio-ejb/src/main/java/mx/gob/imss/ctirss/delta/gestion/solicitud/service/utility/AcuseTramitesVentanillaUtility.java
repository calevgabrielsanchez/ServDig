package mx.gob.imss.ctirss.delta.gestion.solicitud.service.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.framework.util.DateUtils;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.AfiliacionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.AcuseVentanilla;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteMoral;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSocios;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;

@Stateless(mappedName = "acuseTramitesVentanillaUtility", name = "acuseTramitesVentanillaUtility")
public class AcuseTramitesVentanillaUtility 
	extends AbstractServiceUtility implements AcuseTramitesVentanillaUtilityLocal{

    @EJB
    private AfiliacionServiceBusinessRemote afiliacionServiceBusiness;
    @EJB
    private PersonaMoralBusinessRemote personaMoralBusiness;
    @EJB
    private PersonaBusinessRemote personaBusiness;
    
    
	@Override
	public AcuseVentanilla prepararDatosAcusePorTramite(Solicitud solicitud,
			Tramite tramite, Long idTipoTramite) {
		//Template acuse
		AcuseVentanilla acuseVentanilla = null;
		//Identificador de la persona Fisica o Moral del tramite
		Long idPersonaTramite = 0L;
		//Identificador del Representante Legal
		Long idRepresentanteLegal = null;
		
		if(idTipoTramite.equals(TipoTramiteEnum.ALTA_SRT.getCodigo().longValue()) ||
				idTipoTramite.equals(TipoTramiteEnum.ALTA_SRT_PM.getCodigo().longValue())) {
			//ALTA PATRONAL (PF/PM)
			if(tramite instanceof TramiteSujetoObligado	&& tramite.getAcuseVentanilla()!=null){
				TramiteSujetoObligado t = (TramiteSujetoObligado)tramite;
				//Recuperar información para reporte
				acuseVentanilla = t.getAcuseVentanilla();
				if(t.getSujetoObligado().getFisica()!=null){
					idPersonaTramite = t.getSujetoObligado().getFisica().getIdPersona();
					acuseVentanilla.setEsFisica(true);
				}else{
					idPersonaTramite = t.getSujetoObligado().getMoral().getIdPersona();					
				}
				if(!CollectionUtils.isEmpty(t.getSujetoObligado().getRepresentantesLegales())){
					idRepresentanteLegal = t.getSujetoObligado()
						.getRepresentantesLegales().get(0).getPersonaFisica().getIdPersona();
				}
			}
			
		}else if(idTipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_SOCIO.getCodigo().longValue()) ||
				idTipoTramite.equals(TipoTramiteEnum.BAJA_SOCIO.getCodigo().longValue())) {	
			//ALTA Y BAJA DE SOCIOS
			if(tramite instanceof TramiteSocios && tramite.getAcuseVentanilla()!=null){		
				TramiteSocios t = (TramiteSocios)tramite;
				//Recuperar información para reporte
				acuseVentanilla = t.getAcuseVentanilla();
				idRepresentanteLegal = t.getIdPersonaRL();
				idPersonaTramite = t.getPatron().getIdPersona();				
			}
			
		}else if(idTipoTramite.equals(TipoTramiteEnum.BAJA_REPRESENTANTE_LEGAL.getCodigo().longValue())) {
			//BAJA DE RL y BAJA DE EMPRESA REPRESENTADA
			if(tramite instanceof TramiteFisica && tramite.getAcuseVentanilla()!=null){
				TramiteFisica t = (TramiteFisica)tramite;
				//Recuperar información para reporte
				acuseVentanilla = t.getAcuseVentanilla();
				acuseVentanilla.setEsFisica(true);
				idRepresentanteLegal = t.getIdPersonaRL();
				idPersonaTramite = t.getFisica().getIdPersona();
			}else if(tramite instanceof TramiteMoral && tramite.getAcuseVentanilla()!=null){
				TramiteMoral t = (TramiteMoral)tramite;
				//Recuperar información para reporte
				acuseVentanilla = t.getAcuseVentanilla();
				idRepresentanteLegal = t.getIdPersonaRL();
				idPersonaTramite = t.getMoral().getIdPersona();
			}
			
		}else if(idTipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL.getCodigo().longValue())) {
			//ALTA EMPRESA REPRESENTADA
			if(tramite instanceof TramiteRepresentanteLegal && tramite.getAcuseVentanilla()!=null){
				TramiteRepresentanteLegal t = (TramiteRepresentanteLegal)tramite;
				//Recuperar información para reporte
				acuseVentanilla = t.getAcuseVentanilla();
				idRepresentanteLegal = t.getIdPersonaRL();
				if(t.getFisicaRepresentada()!=null){
					acuseVentanilla.setEsFisica(true);
					idPersonaTramite = t.getFisicaRepresentada().getIdPersona();
				}else {
					idPersonaTramite = t.getMoralRepresentada().getIdPersona();
				}
			}
			
		}else if(idTipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_DATOS_GENERALES.getCodigo().longValue())) {
			//ACTUALIZAR DATOS GENERALES (PF/PM)
			if(tramite instanceof TramiteFisica && tramite.getAcuseVentanilla()!=null){
				TramiteFisica t = (TramiteFisica)tramite;
				//Recuperar información para reporte
				acuseVentanilla = t.getAcuseVentanilla();
				acuseVentanilla.setEsFisica(true);
				idRepresentanteLegal = t.getIdPersonaRL();
				idPersonaTramite = t.getFisica().getIdPersona();
			}else if(tramite instanceof TramiteMoral && tramite.getAcuseVentanilla()!=null){
				TramiteMoral t = (TramiteMoral)tramite;
				//Recuperar información para reporte
				acuseVentanilla = t.getAcuseVentanilla();
				idRepresentanteLegal = t.getIdPersonaRL();
				idPersonaTramite = t.getMoral().getIdPersona();				
			}			
		}
		
		//Procesar reporte
		if(acuseVentanilla!=null){
			acuseVentanilla = generarAcuseVentanilla(acuseVentanilla, solicitud, tramite, 
				idPersonaTramite, idRepresentanteLegal);
		}		
		return acuseVentanilla;
	}
		
	@Override
	public AcuseVentanilla complementarDatosFirma(AcuseVentanilla acuseVentanilla,
			FirmaElectronica firmaElectronica) {
		acuseVentanilla.setCadenaOriginal(firmaElectronica.getCadenaOriginal());
		acuseVentanilla.setSelloDigital(firmaElectronica.getRecibo());
		acuseVentanilla.setSecuenciaNotaria(firmaElectronica.getSecuenciaNotaria());
		acuseVentanilla.setNumeroSerie(firmaElectronica.getSerialCertificado());		
		return acuseVentanilla;
	}	
	
	@Override
	public Map<String, Object> generarParametrosReporte(
			AcuseVentanilla acuseVentanilla) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("IMAGENES_DIR", new ClassPathResource("reportes/").getPath());
		parameters.put("SUBREPORT_DIR", new ClassPathResource("reportes/").getPath());
		parameters.put("fechaReporte", acuseVentanilla.getFechaReporte());
		parameters.put("folioSolicitud", acuseVentanilla.getFolioSolicitud());
		parameters.put("descripcionTramite", acuseVentanilla.getDescripcionTramite());
		parameters.put("nombreRS", acuseVentanilla.getNombreRS());
		parameters.put("rfc", acuseVentanilla.getRfc());
		parameters.put("curp", acuseVentanilla.getCurp());
		parameters.put("nss", acuseVentanilla.getNss());
		parameters.put("usuarioVentanilla", acuseVentanilla.getUsuarioVentanilla());
		parameters.put("subdelegacion", acuseVentanilla.getSubdelegacion());
		parameters.put("rfcFirmante", acuseVentanilla.getRfcFirmante());
		parameters.put("nombreFirmante", acuseVentanilla.getNombreFirmante());
		parameters.put("curpFirmante", acuseVentanilla.getCurpFirmante());
		parameters.put("cadenaOriginal", acuseVentanilla.getCadenaOriginal());
		parameters.put("selloDigital", acuseVentanilla.getSelloDigital());
		parameters.put("secuenciaNotaria", acuseVentanilla.getSecuenciaNotaria());
		parameters.put("numeroSerie", acuseVentanilla.getNumeroSerie());
		parameters.put("esFisica", acuseVentanilla.getEsFisica() ? "1" : "0" );
				
		return parameters;
	}
	
	@Override
	public String generarCadenaOriginalIMSS(Solicitud solicitud, AcuseVentanilla acuse) {
		Locale locMEX = new Locale("es", "MX");		
		DateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM yyyy, HH:mm:ss", locMEX);
		StringBuffer contenidoAFirmar = new StringBuffer();		// Inicio
		contenidoAFirmar.append("||");
		contenidoAFirmar.append("Invocante:ventanillaimssdigital|");
		// Denominacion del Tramite o servicio
		contenidoAFirmar.append("Tramite:");
		contenidoAFirmar.append(acuse.getDescripcionTramite()).append("|");
		// Fecha Electronica
		String strFechaElectronica = dateFormat.format(obtenerFechaSolicitud(solicitud));
		contenidoAFirmar.append("Fecha:");
		contenidoAFirmar.append(strFechaElectronica).append("|");		
		// Folio
		contenidoAFirmar.append("Folio:");
		contenidoAFirmar.append(solicitud.getNoFolioSolicitud()).append("|");		
		// RFC
		contenidoAFirmar.append("RFC:");
		contenidoAFirmar.append(acuse.getRfc()).append("|");		
		//Datos de la persona fisica
		if (acuse.getEsFisica()) {
			// Nombre
			contenidoAFirmar.append("Nombre:");
			contenidoAFirmar.append(acuse.getNombreRS()).append("|");			
			// CURP
			contenidoAFirmar.append("CURP:");
			contenidoAFirmar.append(acuse.getCurp()).append("|");
			// Registro Patronal(No aplica)
			//contenidoAFirmar.append("Registro Patronal:|");			
			// NSS
			if (StringUtils.isNotEmpty(acuse.getNss())					
					 && StringUtils.isNotBlank(acuse.getNss())) {
				contenidoAFirmar.append("NSS:");
				contenidoAFirmar.append(acuse.getNss()).append("|");
			}			
		}
		contenidoAFirmar.append("|");		
		return contenidoAFirmar.toString();		
	}	
	
	private AcuseVentanilla generarAcuseVentanilla(AcuseVentanilla acuseVentanilla, Solicitud solicitud,
			Tramite tramite, Long idPersonaTramite, Long idRepresentanteLegal){
		
		Persona personaTramite = obtenerPersonaTramite(idPersonaTramite, acuseVentanilla.getEsFisica());
		
		cargarDatosGenerales(acuseVentanilla, solicitud, tramite);
		cargarSubdelegacion(acuseVentanilla);
		cargarPersonaTramite(acuseVentanilla, personaTramite);
		cargarRepresentanteLegal(acuseVentanilla, idRepresentanteLegal, personaTramite);
		
		//UpperCase
		acuseVentanilla.setDescripcionTramite(acuseVentanilla.getDescripcionTramite()!=null
			?acuseVentanilla.getDescripcionTramite().toUpperCase():"");
		acuseVentanilla.setNombreRS(acuseVentanilla.getNombreRS()!=null
			?acuseVentanilla.getNombreRS().toUpperCase():"");
		acuseVentanilla.setRfc(acuseVentanilla.getRfc()!=null?acuseVentanilla.getRfc().toUpperCase():"");
		acuseVentanilla.setCurp(acuseVentanilla.getCurp()!=null?acuseVentanilla.getCurp().toUpperCase():"");
		acuseVentanilla.setUsuarioVentanilla(acuseVentanilla.getUsuarioVentanilla()!=null
			?acuseVentanilla.getUsuarioVentanilla().toUpperCase():"");
		acuseVentanilla.setSubdelegacion(acuseVentanilla.getSubdelegacion()!=null
			?acuseVentanilla.getSubdelegacion().toUpperCase():"");		
		acuseVentanilla.setRfcFirmante(acuseVentanilla.getRfcFirmante()!=null
			?acuseVentanilla.getRfcFirmante().toUpperCase():"");
		acuseVentanilla.setNombreFirmante(acuseVentanilla.getNombreFirmante()!=null
			?acuseVentanilla.getNombreFirmante().toUpperCase():"");
		acuseVentanilla.setCurpFirmante(acuseVentanilla.getCurpFirmante()!=null
			?acuseVentanilla.getCurpFirmante().toUpperCase():"");
				
		return acuseVentanilla;
	}
	
	
	private Persona obtenerPersonaTramite(Long idPersona, boolean esFisica){
		Persona persona = null;
		if(esFisica){
			persona = personaBusiness.getPersonaFisica(idPersona);
		}else{
			persona = personaMoralBusiness.getPersonaMoral(idPersona);
		}
		return persona;
	}
	
	private void cargarSubdelegacion(AcuseVentanilla acuseVentanilla){
		if(acuseVentanilla.getIdSubdelegacion()!=null){
			Subdelegacion sd = afiliacionServiceBusiness
				.obtenerSubdelegacion(acuseVentanilla.getIdSubdelegacion());
			if (StringUtils.isNotEmpty(sd.getDescripcion())					
					 && StringUtils.isNotBlank(sd.getDescripcion())) {
				acuseVentanilla.setSubdelegacion(sd.getDescripcion());
			}
		}
	}
	
	private void cargarDatosGenerales(AcuseVentanilla acuseVentanilla, 
			Solicitud solicitud, Tramite tramite){
		Date fecha = obtenerFechaSolicitud(solicitud);
		
		acuseVentanilla.setFechaReporte(formatearFechaLarga(fecha));
		acuseVentanilla.setFolioSolicitud(solicitud.getNoFolioSolicitud());
		acuseVentanilla.setDescripcionTramite(tramite.getTipoTramite().getDescripcion());		
	}

	
	private void cargarPersonaTramite(AcuseVentanilla acuseVentanilla, Persona persona){
		if(persona!=null){
			acuseVentanilla.setRfc(persona.getRfc());	
			if (persona instanceof Fisica) {
				StringBuffer sbnombre = new StringBuffer();
				sbnombre.append(((Fisica)persona).getNombre()).append(" ");
				sbnombre.append(((Fisica)persona).getPrimerApellido()).append(" ");
				if (StringUtils.isNotBlank(((Fisica) persona).getSegundoApellido())) {
					sbnombre.append(((Fisica) persona).getSegundoApellido());
				}
				
				acuseVentanilla.setCurp(((Fisica)persona).getCurp());
				acuseVentanilla.setNombreRS(sbnombre.toString());
				acuseVentanilla.setNss(((Fisica)persona).getNss());
			}else{
				acuseVentanilla.setNombreRS(((Moral)persona).getRazonSocial());
			}
		}
	}
	
	private void cargarRepresentanteLegal(AcuseVentanilla acuseVentanilla, Long idRL, Persona persona){
		Persona rl = null;
		if(idRL != null){
			//Obtener PF representante legal
			rl = personaBusiness.getPersonaFisica(idRL);
		}else{
			//No cuenta con RL, cargar persona tramite.
			rl = persona;
		}
		if(rl != null){
			acuseVentanilla.setRfcFirmante(rl.getRfc());
			if(rl instanceof Fisica){
				StringBuffer sbnombre = new StringBuffer();
				sbnombre.append(((Fisica)rl).getNombre()).append(" ");
				sbnombre.append(((Fisica)rl).getPrimerApellido()).append(" ");
				if (StringUtils.isNotBlank(((Fisica)rl).getSegundoApellido())) {
					sbnombre.append(((Fisica)rl).getSegundoApellido());
				}
				acuseVentanilla.setNombreFirmante(sbnombre.toString());
				acuseVentanilla.setCurpFirmante(((Fisica)rl).getCurp());				
			}else{
				acuseVentanilla.setNombreFirmante(((Moral)persona).getRazonSocial());
			}
		}
	}
	
	private String formatearFechaLarga(Date fecha){
		String sFecha = DateUtils.dateToStringConFormato(fecha, "MMMM d 'de' yyyy',' HH:mm:ss"); 
		if(sFecha!=null && StringUtils.isNotBlank(sFecha)){
			//Fecha Larga (Diciembre 31 de 2001, 20:30:11) y se remplaza primer caracter a mayusculas
			sFecha = sFecha.trim();
			char letraInicial = sFecha.charAt(0);
			String inicialMayuscula = Character.toString(letraInicial).toUpperCase();
			sFecha = inicialMayuscula.concat(sFecha.substring(1));
			return sFecha;
		}
		return "";
	}
	
	private Date obtenerFechaSolicitud(Solicitud solicitud){
		return (solicitud.getFechaSolicitud()==null ? new Date() : solicitud.getFechaSolicitud());
	}
	
}
