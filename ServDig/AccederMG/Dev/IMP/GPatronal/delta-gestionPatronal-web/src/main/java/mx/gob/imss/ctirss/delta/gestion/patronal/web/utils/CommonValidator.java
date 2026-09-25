package mx.gob.imss.ctirss.delta.gestion.patronal.web.utils;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.firma.RespuestaFirmadoSimple;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;

public class CommonValidator implements Serializable {
	
	Logger log = Logger.getLogger(CommonValidator.class);

	private static final long serialVersionUID = 1L;
	
	protected static final String KEY_ORIGEN_CONTEXT = "ORIGEN_APP";	
	protected static final String KEY_USUARIO_SSO = "usuarioSSO";
	
	public boolean validarSolicitudMismoOrigen(Solicitud solicitud, Long origenSolicitud){
		boolean solicitudMismoOrigen = false;
		if(solicitud.getOrigenSolicitud().getIdTipoSolicitud().equals(origenSolicitud)){
			solicitudMismoOrigen = true;
		}
		return solicitudMismoOrigen;		
	}
	
	public boolean esSolicitudInternet(Long origenSolicitud){
		boolean esInternet=false;
		OrigenSolicitudEnum origen = OrigenSolicitudEnum.getById(origenSolicitud);
		if(origen.equals(OrigenSolicitudEnum.INTERNET)){
			esInternet = true;
		}
		return esInternet;		
	}

	public Usuario getUsuarioSession(HttpSession session){
		Usuario usuario = new Usuario();
		UsuarioSSO sso = (UsuarioSSO)session.getAttribute(KEY_USUARIO_SSO);
		if(sso!=null && sso.getNombre()!=null){
			if(sso.getNombre()!=null)
				usuario.setUsuario(sso.getNombre().toUpperCase());
			
			if(sso.getSubdelegacion()!=null){
				usuario.setUsuarioFuncionario(new UsuarioFuncionario());
				usuario.getUsuarioFuncionario().setSubdelegacion(new Subdelegacion());				
				usuario.getUsuarioFuncionario()
					.getSubdelegacion().setId(sso.getSubdelegacion().longValue());
			}
		}		
		return usuario;	
	}
	
	public Long getOrigenContext(HttpServletRequest request){
		String origenContext = (String)request.getSession()
			.getServletContext().getInitParameter(KEY_ORIGEN_CONTEXT);
		OrigenSolicitudEnum origenSolicitudEnum = OrigenSolicitudEnum.INTERNET;
		if(StringUtils.isNotEmpty(origenContext) && StringUtils.isNotBlank(origenContext)){
			origenSolicitudEnum = OrigenSolicitudEnum.getById(new Long(origenContext));
		}
		return origenSolicitudEnum.getId();
	}

	public boolean valorNoVacio(String valor){
		if(StringUtils.isNotEmpty(valor) && StringUtils.isNotBlank(valor)){
			return true;
		}else{
			return false;
		}
	}
	
	public static void getCadenaOriginal(Solicitud solicitud, Persona persona, 
			String numeroRegistroPatronal) {
		Locale locMEX = new Locale("es", "MX");
		FirmaElectronica datosEntradaFirma = new FirmaElectronica();
		DateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM yyyy, HH:mm:ss", locMEX);
		StringBuffer contenidoAFirmar = new StringBuffer();

		// Inicio
		contenidoAFirmar.append("||");
		contenidoAFirmar.append("Invocante:movPatimssdigital|");

		// Denominacion del Tramite o servicio
		contenidoAFirmar.append("Solicitud:");
		contenidoAFirmar.append("MODIFICACION AL SRT").append("|");
		contenidoAFirmar.append("Tramite:");
		contenidoAFirmar.append(solicitud.getTramites().get(0).getTipoTramite().getDescripcion()).append("|");
		
		// Fecha Electronica
		String strFechaElectronica = dateFormat.format(Calendar.getInstance().getTime());
		contenidoAFirmar.append("Fecha:");
		contenidoAFirmar.append(strFechaElectronica).append("|");
		datosEntradaFirma.setFechaElectronicaFormateada(strFechaElectronica);
		datosEntradaFirma.setFechaElectronica(Calendar.getInstance().getTime());

		// Folio
		contenidoAFirmar.append("Folio:");
		contenidoAFirmar.append(solicitud.getNoFolioSolicitud()).append("|");

		// RFC
		contenidoAFirmar.append("RFC:");
		contenidoAFirmar.append(persona.getRfc()).append("|");
		datosEntradaFirma.setRfc(persona.getRfc());

		// Nombre, denominacion o razon social del interesado (y en su caso el de su representante o persona autorizada)
		StringBuffer sbnombre = new StringBuffer();
		if (persona instanceof Fisica) {
			sbnombre.append(((Fisica)persona).getNombre().trim()).append(" ");
			if(StringUtils.isNotBlank(((Fisica)persona).getPrimerApellido())) {
				sbnombre.append(((Fisica)persona).getPrimerApellido()).append(" ");
			}
			if (StringUtils.isNotBlank(((Fisica) persona).getSegundoApellido())) {
				sbnombre.append(((Fisica) persona).getSegundoApellido());
			}
		} else {
			sbnombre.append(((Moral)persona).getRazonSocial());
		}

        String nombreRazonSocial = sbnombre.toString();

        contenidoAFirmar.append("Nombre o Razon Social:");
		/*
		 * Se sustituyen las comillas con el caracter especial HTML &quot; para
		 * que el valor en el input hidden sea correcto y no se despu�s el
		 * applet de la firma funcione correctamente
		 */
        
        datosEntradaFirma.setNombreCompleto(nombreRazonSocial.toString());
        if (persona instanceof Fisica) {
			// CURP
			if(((Fisica)persona).getCurp() != null) {
				contenidoAFirmar.append("CURP:");
				contenidoAFirmar.append(((Fisica)persona).getCurp()).append("|");
				datosEntradaFirma.setCurp(((Fisica)persona).getCurp());
			}
		}

		if(numeroRegistroPatronal != null && numeroRegistroPatronal.trim().length() > 0){
			// Registro Patronal
			contenidoAFirmar.append("Registro Patronal:");
			contenidoAFirmar.append(numeroRegistroPatronal).append("|");
		}

		contenidoAFirmar.append("|");
		solicitud.setCadenaOriginal(contenidoAFirmar.toString());
		solicitud.setFirmaElectronica(datosEntradaFirma);
	}
	
	
}
