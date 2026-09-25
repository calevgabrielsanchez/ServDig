package mx.gob.imss.ctirss.delta.gestion.individuo.service.utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.framework.exceptions.TransformacionException;
import mx.gob.imss.ctirss.delta.gestion.individuo.util.DateUtils;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.enums.CambioComparacionEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoAdministracionEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoDomicilioEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.MDMDatosEntrada;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Notificacion;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EscrituraConstitutiva;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modulo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RegistroSindicato;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCambioInformacionPersona;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteMoral;
import mx.gob.imss.ctirss.delta.persistence.DicModulo;
import mx.gob.imss.ctirss.delta.persistence.DitNotificacion;
import mx.gob.imss.ctirss.delta.persistence.DitTramite;
import mx.gob.imss.distss.digital.jaxb.util.JaxbUtil;

import org.apache.commons.lang.BooleanUtils;

@Stateless
public class NotificacionServiceUtility extends AbstractServiceUtility
		implements NotificacionServiceUtilityLocal {

	@Override
	public Notificacion transformarNotificacion(DitNotificacion entity)
			throws TransformacionException {

		if (entity == null) {
			throw new TransformacionException();
		}

		Notificacion model = new Notificacion();

		if(entity.getCveIdNotificacion() != 0){
			model.setIdNotificacion(entity.getCveIdNotificacion());
		}
				
		if(entity.getDicModuloOrigen() != null){
			Modulo moduloOrigen = new Modulo();
			moduloOrigen.setIdModulo(entity.getDicModuloOrigen().getCveIdModulo());
			moduloOrigen.setDescripcion(entity.getDicModuloOrigen().getDesModulo());
			model.setModuloOrigen(moduloOrigen);
		}else{
			log.warn("La notificación requiere de un módulo de origen");
			throw new TransformacionException();
		}
		
		if(entity.getDicModuloNotificar() != null){
			Modulo moduloNotificar = new Modulo();
			moduloNotificar.setIdModulo(entity.getDicModuloNotificar().getCveIdModulo());
			moduloNotificar.setDescripcion(entity.getDicModuloNotificar().getDesModulo());
			model.setModuloNotificar(moduloNotificar);
		}else{
			log.warn("La notificación requiere de un módulo a notificar");
			throw new TransformacionException();
		}
		
		
		if(entity.getDitTramite() != null){
			TramiteCambioInformacionPersona tramite = new TramiteCambioInformacionPersona();
			
			if (entity.getDitTramite().getCveIdTramite() != null) {
				tramite.setTramiteId(entity.getDitTramite().getCveIdTramite());
			}
			
			if (entity.getDitTramite().getDicTipoTramite() != null) {
				TipoTramite tipoTramite = new TipoTramite();
				tipoTramite.setDescripcion(entity.getDitTramite().getDicTipoTramite().getDesTipoTramite());
				tipoTramite.setIdTipoTramite(entity.getDitTramite().getDicTipoTramite().getCveIdTipoTramite().intValue());
				
				tramite.setTipoTramite(tipoTramite);
			}
			
			if (entity.getDitTramite().getFecPresentacion() != null) {
				tramite.setFechaPresentacion(entity.getDitTramite().getFecPresentacion());
				
				tramite.setFechaPresentacionParse(DateUtils
						.dateToStringConFormato(entity.getDitTramite()
								.getFecPresentacion(), "dd/MM/yyyy HH:mm:ss"));
			} else {
				log.warn("El trámite no cuenta con fecha de presentación");
			}
			
			if (entity.getDitTramite().getFecConclusion() != null) {
				tramite.setFechaConclusion(entity.getDitTramite().getFecConclusion());
				
				tramite.setFechaConclusionParse(DateUtils
						.dateToStringConFormato(entity.getDitTramite()
								.getFecConclusion(), "dd/MM/yyyy HH:mm:ss"));
			} else {
				log.warn("El trámite no cuenta con fecha de conclusión");
			}
			
			
			
			Object object = JaxbUtil.xmlToObject(entity.getDitTramite().getDitDetalleTramite().getRefDatosTramiteXml());
			
			if(object instanceof TramiteFisica){
				TramiteFisica tramiteFisica = (TramiteFisica) object;
			
				if (tramiteFisica.getDatosICA() != null) {
					tramite.setDatosICA(tramiteFisica.getDatosICA());
				} else if (tramiteFisica.getDatosMDM() != null) {
					tramite.setDatosModifManual(tramiteFisica.getDatosMDM());
				} else {
					this.log.warn("No se tiene detalle del tramite, por lo tanto no se podrá mostrar el detalle de los cambios realizados a la persona.");
				}
			} else if (object instanceof TramiteMoral) {
				TramiteMoral tramiteMoral = (TramiteMoral) object;
				if (tramiteMoral.getDatosICA() != null) {
					tramite.setDatosICA(tramiteMoral.getDatosICA());
				} else if (tramiteMoral.getDatosMDM() != null) {
					tramite.setDatosModifManual(tramiteMoral.getDatosMDM());
				} else {
					this.log.warn("No se tiene detalle del tramite, por lo tanto no se podrá mostrar el detalle de los cambios realizados a la persona.");
				}
			} else {
				this.log.warn("No se tiene detalle del tramite, por lo tanto no se podrá mostrar el detalle de los cambios realizados a la persona.");
			}
			
			model.setTramite(tramite);
		} else {
			log.warn("La notificación requiere de un trámite asociado");
			throw new TransformacionException();
		}
		
		return model;
	}

	@Override
	public DitNotificacion transformarNotificacion(Notificacion model)
			throws TransformacionException {

		if (model == null) {
			throw new TransformacionException();
		}

		DitNotificacion entity = new DitNotificacion();
		
		if(model.getIdNotificacion() != null){
			entity.setCveIdNotificacion(model.getIdNotificacion());
		}
				
		if(model.getModuloOrigen() != null){
			DicModulo dicModuloOrigen = new DicModulo();
			dicModuloOrigen.setCveIdModulo(model.getModuloOrigen().getIdModulo());
			entity.setDicModuloOrigen(dicModuloOrigen);
		}else {
			log.warn("La notificación requiere de un módulo de origen");
			throw new TransformacionException();
		}
		
		if(model.getModuloNotificar() != null){
			DicModulo dicModuloNotificar = new DicModulo();
			dicModuloNotificar.setCveIdModulo(model.getModuloNotificar().getIdModulo());
			entity.setDicModuloNotificar(dicModuloNotificar);
		}else {
			log.warn("La notificación requiere de un módulo a notificar");
			throw new TransformacionException();
		}
		
		
		if(model.getTramite() != null){
			DitTramite ditTramite = new DitTramite(); 
			ditTramite.setCveIdTramite(model.getTramite().getTramiteId());
			entity.setDitTramite(ditTramite);
		}else {
			log.warn("La notificación requiere de un trámite asociado");
			throw new TransformacionException();
		}
		
		if(model.getFechaRegistro() != null){
			entity.setFecRegistroAlta(model.getFechaRegistro());
		} else {
			entity.setFecRegistroAlta(new Date());
		}
		
		return entity;
	}
	
	@Override
	public void generarDetalleCambiosICA(TramiteCambioInformacionPersona tramite, StringBuffer htmlDetalle) {
		
		if (tramite.getDatosICA().getCambios() != null && tramite.getDatosICA().getCambios().size() > 0) {
			Map<String, CambioComparacionEnum> cambios = tramite.getDatosICA().getCambios(); 
			
			htmlDetalle.append("<table id=\"tblCambios\" class=\"tblDetalleCambios\" border=\"0\">");
			htmlDetalle.append("<tr>");
			
			// Se checa si hubo cambios en los datos RENAPO
			if (tramite.getDatosICA().getTraza().containsKey("MSG01-RENAPO")) {
				
				htmlDetalle.append("<td style=\"vertical-align: top !important; padding-right: 5px; width: 50%;\">");
				
				htmlDetalle.append("<table id=\"tblCambiosRENAPO\" class=\"tblDetalleCambios\" border=\"0\">");
				
				htmlDetalle.append("<tr>");
				htmlDetalle.append("<td colspan=\"2\" style=\"text-align: center;\">");
				htmlDetalle.append("DATOS RENAPO");
				htmlDetalle.append("</td>");
				htmlDetalle.append("</tr>");
				
				if(esCambio(cambios.get("nombre"))){
					generarFilaDetalleParticular("Nombre", cambios.get("nombre"), htmlDetalle, false);
				}
				
				if(esCambio(cambios.get("primerApellido"))){
					generarFilaDetalleParticular("Primer Apellido", cambios.get("primerApellido"), htmlDetalle, false);
				}
				
				if(esCambio(cambios.get("segundoApellido"))){
					generarFilaDetalleParticular("Segundo apellido", cambios.get("segundoApellido"), htmlDetalle, false);
				}
				
				if(esCambio(cambios.get("curp"))){
					generarFilaDetalleParticular("CURP", cambios.get("curp"), htmlDetalle, false);
				}
				
				if(esCambio(cambios.get("sexo"))){
					generarFilaDetalleParticular("Sexo", cambios.get("sexo"), htmlDetalle, false);
				}
				
				if(esCambio(cambios.get("fechaNacimiento"))){
					generarFilaDetalleParticular("Fecha de Nacimiento", cambios.get("fechaNacimiento"), htmlDetalle, false);
				}
				
				if(esCambio(cambios.get("lugarNacimiento"))){
					generarFilaDetalleParticular("Lugar de Nacimiento", cambios.get("lugarNacimiento"), htmlDetalle, false);
				}
				
				if(esCambio(cambios.get("nacionalidad"))){
					generarFilaDetalleParticular("Nacionalidad", cambios.get("nacionalidad"), htmlDetalle, false);
				}
				
				if(esCambio(cambios.get("actaNacimiento"))){
					htmlDetalle.append("<tr>");
					htmlDetalle.append("<td>");
					htmlDetalle.append("<div class=\"detalle-cambio-cerrado\"></div>");
					htmlDetalle.append("Acta de Nacimiento:");
					htmlDetalle.append("<div id=\"divDetalleCambios\" style=\"display:none\">");
					htmlDetalle.append("<div class=\"contenedor\">");
					
					if(esCambio(cambios.get("actaNacimiento.anio"))){
						generarFilaDetalleParticular("A&ntilde;o de Registro", cambios.get("actaNacimiento.anio"), htmlDetalle, true);
					}
					
					if(esCambio(cambios.get("actaNacimiento.acta"))){
						generarFilaDetalleParticular("No. Acta", cambios.get("actaNacimiento.acta"), htmlDetalle, true);
					}
					
					if(esCambio(cambios.get("actaNacimiento.foja"))){
						generarFilaDetalleParticular("No. Foja", cambios.get("actaNacimiento.foja"), htmlDetalle, true);
					}
					
					if(esCambio(cambios.get("actaNacimiento.libro"))){
						generarFilaDetalleParticular("No. Libro", cambios.get("actaNacimiento.libro"), htmlDetalle, true);
					}
					
					if(esCambio(cambios.get("actaNacimiento.tomo"))){
						generarFilaDetalleParticular("Tomo", cambios.get("actaNacimiento.tomo"), htmlDetalle, true);
					}
					
					if(esCambio(cambios.get("actaNacimiento.crip"))){
						generarFilaDetalleParticular("CRIP", cambios.get("actaNacimiento.crip"), htmlDetalle, true);
					}
												
					if(esCambio(cambios.get("actaNacimiento.entidad"))){
						generarFilaDetalleParticular("Entidad de Registro", cambios.get("actaNacimiento.entidad"), htmlDetalle, true);
					}

					if(esCambio(cambios.get("actaNacimiento.municipio"))){
						generarFilaDetalleParticular("Municipio de Registro", cambios.get("actaNacimiento.municipio"), htmlDetalle, true);
					}
					
					htmlDetalle.append("</div>");
					htmlDetalle.append("</div>");
					htmlDetalle.append("</td>");
					htmlDetalle.append("<td>");
					generarMensajeCambio(cambios.get("actaNacimiento"), htmlDetalle);
					htmlDetalle.append("</td>");
					htmlDetalle.append("</tr>");
				} else if(esCambio(cambios.get("documentoMigratorio"))){
					htmlDetalle.append("<tr>");
					htmlDetalle.append("<td>");
					htmlDetalle.append("<div class=\"detalle-cambio-cerrado\"></div>");
					htmlDetalle.append("Documento Migratorio:");
					htmlDetalle.append("<div id=\"divDetalleCambios\" style=\"display:none\">");
					htmlDetalle.append("<div class=\"contenedor\">");
					
					if(esCambio(cambios.get("documentoMigratorio.numRegExtranjeros"))){
						generarFilaDetalleParticular("N&uacute;mero del Registro Nacional de Extranjeros", cambios.get("documentoMigratorio.numRegExtranjeros"), htmlDetalle, true);
					}
					
					if(esCambio(cambios.get("documentoMigratorio.numExpediente"))){
						generarFilaDetalleParticular("N&uacute;mero de Expediente del Documento Migratorio", cambios.get("documentoMigratorio.numExpediente"), htmlDetalle, true);
					}
					
					htmlDetalle.append("</div>");
					htmlDetalle.append("</div>");
					htmlDetalle.append("</td>");
					htmlDetalle.append("</tr>");
				} else if(esCambio(cambios.get("cartaNaturalizacion"))){
					htmlDetalle.append("<tr>");
					htmlDetalle.append("<td>");
					htmlDetalle.append("<div class=\"detalle-cambio-cerrado\"></div>");
					htmlDetalle.append("Carta de Naturalizaci&oacute;n:");
					htmlDetalle.append("<div id=\"divDetalleCambios\" style=\"display:none\">");
					htmlDetalle.append("<div class=\"contenedor\">");
					
					if(esCambio(cambios.get("cartaNaturalizacion.anio"))){
						generarFilaDetalleParticular("A&ntilde;o de Registro", cambios.get("cartaNaturalizacion.anio"), htmlDetalle, true);
					}
					
					if(esCambio(cambios.get("cartaNaturalizacion.folio"))){
						generarFilaDetalleParticular("Folio de la Carta", cambios.get("cartaNaturalizacion.folio"), htmlDetalle, true);
					}
					
					htmlDetalle.append("</div>");
					htmlDetalle.append("</div>");
					htmlDetalle.append("</td>");
					htmlDetalle.append("</tr>");
				} else if(esCambio(cambios.get("numUnicoExtranjero"))){
					htmlDetalle.append("<tr>");
					htmlDetalle.append("<td>");
					htmlDetalle.append("<div class=\"detalle-cambio-cerrado\"></div>");
					htmlDetalle.append("N&uacute;mero &Uacute;nico de Extranjero:");
					htmlDetalle.append("<div id=\"divDetalleCambios\" style=\"display:none\">");
					htmlDetalle.append("<div class=\"contenedor\">");
					
					if(esCambio(cambios.get("numUnicoExtranjero.folio"))){
						generarFilaDetalleParticular("N&uacute;mero de folio", cambios.get("numUnicoExtranjero.folio"), htmlDetalle, true);
					}
					
					htmlDetalle.append("</div>");
					htmlDetalle.append("</div>");
					htmlDetalle.append("</td>");
					htmlDetalle.append("</tr>");
				} else if(esCambio(cambios.get("certificadoNacionalidad"))){
					htmlDetalle.append("<tr>");
					htmlDetalle.append("<td>");
					htmlDetalle.append("<div class=\"detalle-cambio-cerrado\"></div>");
					htmlDetalle.append("Certificado de Nacionalidad Mexicana:");
					htmlDetalle.append("<div id=\"divDetalleCambios\" style=\"display:none\">");
					htmlDetalle.append("<div class=\"contenedor\">");
					
					if(esCambio(cambios.get("certificadoNacionalidad.anio"))){
						generarFilaDetalleParticular("A&ntilde;o de Registro", cambios.get("certificadoNacionalidad.anio"), htmlDetalle, true);
					}
					
					if(esCambio(cambios.get("certificadoNacionalidad.folio"))){
						generarFilaDetalleParticular("Folio", cambios.get("certificadoNacionalidad.folio"), htmlDetalle, true);
					}
					
					htmlDetalle.append("</div>");
					htmlDetalle.append("</div>");
					htmlDetalle.append("</td>");
					htmlDetalle.append("</tr>");
				} else if(esCambio(cambios.get("oficioRefugiado"))){
					htmlDetalle.append("<tr>");
					htmlDetalle.append("<td>");
					htmlDetalle.append("<div class=\"detalle-cambio-cerrado\"></div>");
					htmlDetalle.append("Oficio Solicitante de Refugiado:");
					htmlDetalle.append("<div id=\"divDetalleCambios\" style=\"display:none\">");
					htmlDetalle.append("<div class=\"contenedor\">");
					
					if(esCambio(cambios.get("oficioRefugiado.folio"))){
						generarFilaDetalleParticular("Folio", cambios.get("oficioRefugiado.folio"), htmlDetalle, true);
					}
					
					htmlDetalle.append("</div>");
					htmlDetalle.append("</div>");
					htmlDetalle.append("</td>");
					htmlDetalle.append("</tr>");
				} else if(esCambio(cambios.get("formaMigratoria"))){
					htmlDetalle.append("<tr>");
					htmlDetalle.append("<td>");
					htmlDetalle.append("<div class=\"detalle-cambio-cerrado\"></div>");
					htmlDetalle.append("Forma Migratoria Turista:");
					htmlDetalle.append("<div id=\"divDetalleCambios\" style=\"display:none\">");
					htmlDetalle.append("<div class=\"contenedor\">");
					
					if(esCambio(cambios.get("formaMigratoria.folio"))){
						generarFilaDetalleParticular("Folio", cambios.get("formaMigratoria.folio"), htmlDetalle, true);
					}
					
					htmlDetalle.append("</div>");
					htmlDetalle.append("</div>");
					htmlDetalle.append("</td>");
					htmlDetalle.append("</tr>");
				}
				
				htmlDetalle.append("</table>");
				htmlDetalle.append("</td>");
			}
			
			// Se checa si hubo cambios en los datos SAT
			if (tramite.getDatosICA().getTraza().containsKey("MSG02-SAT")) {
				
				htmlDetalle.append("<td style=\"vertical-align: top !important; padding-left: 5px; width: 50%;\">");
				
				htmlDetalle.append("<table id=\"tblCambiosSAT\" class=\"tblDetalleCambios\" border=\"0\">");
				
				htmlDetalle.append("<tr>");
				htmlDetalle.append("<td colspan=\"2\" style=\"text-align: center;\">");
				htmlDetalle.append("DATOS SAT");
				htmlDetalle.append("</td>");
				htmlDetalle.append("</tr>");
				
				if(esCambio(cambios.get("nombreRazonSocial"))){
					generarFilaDetalleParticular("Nombre &oacute; Raz&oacute;n Social", cambios.get("nombreRazonSocial"), htmlDetalle, false);
				}
				
				if(esCambio(cambios.get("fechaConsitutucion"))){
					generarFilaDetalleParticular("Fecha de Constituci&oacute;n", cambios.get("fechaConsitutucion"), htmlDetalle, false);
				}
				
				if(esCambio(cambios.get("tipoSociedad"))){
					generarFilaDetalleParticular("Tipo de Sociedad", cambios.get("tipoSociedad"), htmlDetalle, false);
				}
				
				if(esCambio(cambios.get("rfc"))){
					generarFilaDetalleParticular("RFC", cambios.get("rfc"), htmlDetalle, false);
				}
				
				if(esCambio(cambios.get("fechaConstitucion"))){
					generarFilaDetalleParticular("Fecha Constituci&oacute;n", cambios.get("fechaConstitucion"), htmlDetalle, false);
				}
				
				if(esCambio(cambios.get("domicilioFiscal"))){
					htmlDetalle.append("<tr>");
					htmlDetalle.append("<td>");
					htmlDetalle.append("<div class=\"detalle-cambio-cerrado\"></div>");
					htmlDetalle.append("Domicilio Fiscal:");
					htmlDetalle.append("<div id=\"divDetalleCambios\" style=\"display:none\">");
					htmlDetalle.append("<div class=\"contenedor\">");
					
					if(esCambio(cambios.get("codigoPostal"))){
						generarFilaDetalleParticular("C&oacute;digo Postal", cambios.get("codigoPostal"), htmlDetalle, true);
					}
					
					if(esCambio(cambios.get("calle"))){
						generarFilaDetalleParticular("Calle", cambios.get("calle"), htmlDetalle, true);
					}
					
					if(esCambio(cambios.get("colonia"))){
						generarFilaDetalleParticular("Colonia", cambios.get("colonia"), htmlDetalle, true);
					}
					
					if(esCambio(cambios.get("entidad"))){
						generarFilaDetalleParticular("Entidad Federativa", cambios.get("entidad"), htmlDetalle, true);
					}
					
					if(esCambio(cambios.get("localidad"))){
						generarFilaDetalleParticular("Localidad", cambios.get("localidad"), htmlDetalle, true);
					}
					
					if(esCambio(cambios.get("municipio"))){
						generarFilaDetalleParticular("Municipio", cambios.get("municipio"), htmlDetalle, true);
					}
											
					if(esCambio(cambios.get("entreCalle1"))){
						generarFilaDetalleParticular("Entre Calle 1", cambios.get("entreCalle1"), htmlDetalle, true);
					}
					
					if(esCambio(cambios.get("entreCalle2"))){
						generarFilaDetalleParticular("Entre Calle 2", cambios.get("entreCalle2"), htmlDetalle, true);
					}
					
					if(esCambio(cambios.get("inmueble"))){
						generarFilaDetalleParticular("Inmueble", cambios.get("inmueble"), htmlDetalle, true);
					}
					
					if(esCambio(cambios.get("referencia"))){
						generarFilaDetalleParticular("Referencia", cambios.get("referencia"), htmlDetalle, true);
					}
					
					if(esCambio(cambios.get("vialidad"))){
						generarFilaDetalleParticular("Vialidad", cambios.get("vialidad"), htmlDetalle, true);
					}
					
					if(esCambio(cambios.get("numeInt"))){
						generarFilaDetalleParticular("N&uacute;mero Interior", cambios.get("numeInt"), htmlDetalle, true);
					}
					
					if(esCambio(cambios.get("numeExt"))){
						generarFilaDetalleParticular("N&uacute;mero Exterior", cambios.get("numeExt"), htmlDetalle, true);
					}
					
					htmlDetalle.append("</div>");
					htmlDetalle.append("</div>");
					htmlDetalle.append("</td>");
					htmlDetalle.append("<td>");
					generarMensajeCambio(cambios.get("domicilioFiscal"), htmlDetalle);
					htmlDetalle.append("</td>");
					htmlDetalle.append("</tr>");
				}
				
				if(esCambio(cambios.get("correoElectronico"))){
					generarFilaDetalleParticular("Correo Electr&oacute;nico Fiscal", cambios.get("correoElectronico"), htmlDetalle, false);
				}
				
				if(esCambio(cambios.get("telefonoFijo"))){
					generarFilaDetalleParticular("Tel&eacute;fono Fijo Fiscal", cambios.get("telefonoFijo"), htmlDetalle, false);
				}
				
				if(esCambio(cambios.get("telefonoMovil"))){
					generarFilaDetalleParticular("Tel&eacute;fono M&oacute;vil Fiscal", cambios.get("telefonoMovil"), htmlDetalle, false);
				}
				
				if(esCambio(cambios.get("situacionSAT"))){
					generarFilaDetalleParticular("Situaci&oacute;n SAT", cambios.get("situacionSAT"), htmlDetalle, false);
				}
				
				htmlDetalle.append("</table>");
				htmlDetalle.append("</td>");
			} 				
			
			htmlDetalle.append("</tr>");
			htmlDetalle.append("</table>");				
		} else {
			htmlDetalle.append("SIN CAMBIOS");
		}
	}
	
	@Override
	public void generarDetalleCambiosModificacionManual(
			TramiteCambioInformacionPersona tramite, StringBuffer htmlDetalle) {
		
		Map<String, CambioComparacionEnum> cambios = tramite.getDatosModifManual().getCambios();
		
		if (cambios != null && cambios.size() > 0) {
			
			MDMDatosEntrada datosEntrada = tramite.getDatosModifManual();
			
			htmlDetalle.append("<table id=\"tblCambios\" class=\"tblDetalleCambios\" border=\"0\">");
			htmlDetalle.append("<tr>");
			
			// DATOS RENAPO
			if (datosEntrada.getIndCapturaDatosRENAPO()) {
				
				htmlDetalle.append("<td style=\"vertical-align: top !important; padding-right: 5px; width: 50%;\">");
				
				htmlDetalle.append("<table id=\"tblCambiosRENAPO\" class=\"tblDetalleCambios\" border=\"0\">");
				
				htmlDetalle.append("<tr>");
				htmlDetalle.append("<td colspan=\"2\" style=\"text-align: center;\">");
				htmlDetalle.append("DATOS RENAPO");
				htmlDetalle.append("</td>");
				htmlDetalle.append("</tr>");
				
				if (esCambio(cambios.get("nombre"))
						&& BooleanUtils.isTrue(datosEntrada.getIndCapturaNombre())) {
					generarFilaDetalleParticular("Nombre", cambios.get("nombre"), htmlDetalle, false);
				}
				
				if (esCambio(cambios.get("primerApellido"))
						&& BooleanUtils.isTrue(datosEntrada.getIndCapturaNombre())) {
					generarFilaDetalleParticular("Primer Apellido", cambios.get("primerApellido"), htmlDetalle, false);
				}
				
				if (esCambio(cambios.get("segundoApellido"))
						&& BooleanUtils.isTrue(datosEntrada.getIndCapturaNombre())) {
					generarFilaDetalleParticular("Segundo apellido", cambios.get("segundoApellido"), htmlDetalle, false);
				}
	
				if (esCambio(cambios.get("curp"))
						&& BooleanUtils.isTrue(datosEntrada.getIndCapturaCURP())) {
					generarFilaDetalleParticular("CURP", cambios.get("curp"), htmlDetalle, false);
				}
				
				if (esCambio(cambios.get("sexo"))
						&& BooleanUtils.isTrue(datosEntrada.getIndCapturaSexo())) {
					generarFilaDetalleParticular("Sexo", cambios.get("sexo"), htmlDetalle, false);
				}
				
				if (esCambio(cambios.get("fechaNacimiento"))
						&& BooleanUtils.isTrue(datosEntrada.getIndCapturaFechaNacimiento())) {
					generarFilaDetalleParticular("Fecha de Nacimiento", cambios.get("fechaNacimiento"), htmlDetalle, false);
				}
				
				if (esCambio(cambios.get("lugarNacimiento"))
						&& BooleanUtils.isTrue(datosEntrada.getIndCapturaLugarNacimiento())) {
					generarFilaDetalleParticular("Lugar de Nacimiento", cambios.get("lugarNacimiento"), htmlDetalle, false);
				}
				
				if (esCambio(cambios.get("nacionalidad"))
						&& BooleanUtils.isTrue(datosEntrada.getIndCapturaLugarNacimiento())) {
					generarFilaDetalleParticular("Nacionalidad", cambios.get("nacionalidad"), htmlDetalle, false);
				}
				
				if (BooleanUtils.isTrue(datosEntrada.getIndCapturaDocumentoProbatorio())) {
					if(esCambio(cambios.get("actaNacimiento"))){
						htmlDetalle.append("<tr>");
						htmlDetalle.append("<td>");
						htmlDetalle.append("<div class=\"detalle-cambio-cerrado\"></div>");
						htmlDetalle.append("Acta de Nacimiento:");
						htmlDetalle.append("<div id=\"divDetalleCambios\" style=\"display:none\">");
						htmlDetalle.append("<div class=\"contenedor\">");
						
						if(esCambio(cambios.get("actaNacimiento.anio"))){
							generarFilaDetalleParticular("A&ntilde;o de Registro", cambios.get("actaNacimiento.anio"), htmlDetalle, true);
						}
						
						if(esCambio(cambios.get("actaNacimiento.acta"))){
							generarFilaDetalleParticular("No. Acta", cambios.get("actaNacimiento.acta"), htmlDetalle, true);
						}
						
						if(esCambio(cambios.get("actaNacimiento.foja"))){
							generarFilaDetalleParticular("No. Foja", cambios.get("actaNacimiento.foja"), htmlDetalle, true);
						}
						
						if(esCambio(cambios.get("actaNacimiento.libro"))){
							generarFilaDetalleParticular("No. Libro", cambios.get("actaNacimiento.libro"), htmlDetalle, true);
						}
						
						if(esCambio(cambios.get("actaNacimiento.tomo"))){
							generarFilaDetalleParticular("Tomo", cambios.get("actaNacimiento.tomo"), htmlDetalle, true);
						}
						
						if(esCambio(cambios.get("actaNacimiento.crip"))){
							generarFilaDetalleParticular("CRIP", cambios.get("actaNacimiento.crip"), htmlDetalle, true);
						}
													
						if(esCambio(cambios.get("actaNacimiento.entidad"))){
							generarFilaDetalleParticular("Entidad de Registro", cambios.get("actaNacimiento.entidad"), htmlDetalle, true);
						}

						if(esCambio(cambios.get("actaNacimiento.municipio"))){
							generarFilaDetalleParticular("Municipio de Registro", cambios.get("actaNacimiento.municipio"), htmlDetalle, true);
						}
						
						htmlDetalle.append("</div>");
						htmlDetalle.append("</div>");
						htmlDetalle.append("</td>");
						htmlDetalle.append("<td>");
						generarMensajeCambio(cambios.get("actaNacimiento"), htmlDetalle);
						htmlDetalle.append("</td>");
						htmlDetalle.append("</tr>");
					} 
					
					if(esCambio(cambios.get("documentoMigratorio"))){
						htmlDetalle.append("<tr>");
						htmlDetalle.append("<td>");
						htmlDetalle.append("<div class=\"detalle-cambio-cerrado\"></div>");
						htmlDetalle.append("Documento Migratorio:");
						htmlDetalle.append("<div id=\"divDetalleCambios\" style=\"display:none\">");
						htmlDetalle.append("<div class=\"contenedor\">");
						
						if(esCambio(cambios.get("documentoMigratorio.numRegExtranjeros"))){
							generarFilaDetalleParticular("N&uacute;mero del Registro Nacional de Extranjeros", cambios.get("documentoMigratorio.numRegExtranjeros"), htmlDetalle, true);
						}
						
						if(esCambio(cambios.get("documentoMigratorio.numExpediente"))){
							generarFilaDetalleParticular("N&uacute;mero de Expediente del Documento Migratorio", cambios.get("documentoMigratorio.numExpediente"), htmlDetalle, true);
						}
						
						htmlDetalle.append("</div>");
						htmlDetalle.append("</div>");
						htmlDetalle.append("</td>");
						htmlDetalle.append("</tr>");
					} 
					
					if(esCambio(cambios.get("cartaNaturalizacion"))){
						htmlDetalle.append("<tr>");
						htmlDetalle.append("<td>");
						htmlDetalle.append("<div class=\"detalle-cambio-cerrado\"></div>");
						htmlDetalle.append("Carta de Naturalizaci&oacute;n:");
						htmlDetalle.append("<div id=\"divDetalleCambios\" style=\"display:none\">");
						htmlDetalle.append("<div class=\"contenedor\">");
						
						if(esCambio(cambios.get("cartaNaturalizacion.anio"))){
							generarFilaDetalleParticular("A&ntilde;o de Registro", cambios.get("cartaNaturalizacion.anio"), htmlDetalle, true);
						}
						
						if(esCambio(cambios.get("cartaNaturalizacion.folio"))){
							generarFilaDetalleParticular("Folio de la Carta", cambios.get("cartaNaturalizacion.folio"), htmlDetalle, true);
						}
						
						htmlDetalle.append("</div>");
						htmlDetalle.append("</div>");
						htmlDetalle.append("</td>");
						htmlDetalle.append("</tr>");
					} 
					
					if(esCambio(cambios.get("numUnicoExtranjero"))){
						htmlDetalle.append("<tr>");
						htmlDetalle.append("<td>");
						htmlDetalle.append("<div class=\"detalle-cambio-cerrado\"></div>");
						htmlDetalle.append("N&uacute;mero &Uacute;nico de Extranjero:");
						htmlDetalle.append("<div id=\"divDetalleCambios\" style=\"display:none\">");
						htmlDetalle.append("<div class=\"contenedor\">");
						
						if(esCambio(cambios.get("numUnicoExtranjero.folio"))){
							generarFilaDetalleParticular("N&uacute;mero de folio", cambios.get("numUnicoExtranjero.folio"), htmlDetalle, true);
						}
						
						htmlDetalle.append("</div>");
						htmlDetalle.append("</div>");
						htmlDetalle.append("</td>");
						htmlDetalle.append("</tr>");
					} 
					
					if(esCambio(cambios.get("certificadoNacionalidad"))){
						htmlDetalle.append("<tr>");
						htmlDetalle.append("<td>");
						htmlDetalle.append("<div class=\"detalle-cambio-cerrado\"></div>");
						htmlDetalle.append("Certificado de Nacionalidad Mexicana:");
						htmlDetalle.append("<div id=\"divDetalleCambios\" style=\"display:none\">");
						htmlDetalle.append("<div class=\"contenedor\">");
						
						if(esCambio(cambios.get("certificadoNacionalidad.anio"))){
							generarFilaDetalleParticular("A&ntilde;o de Registro", cambios.get("certificadoNacionalidad.anio"), htmlDetalle, true);
						}
						
						if(esCambio(cambios.get("certificadoNacionalidad.folio"))){
							generarFilaDetalleParticular("Folio", cambios.get("certificadoNacionalidad.folio"), htmlDetalle, true);
						}
						
						htmlDetalle.append("</div>");
						htmlDetalle.append("</div>");
						htmlDetalle.append("</td>");
						htmlDetalle.append("</tr>");
					} 
					
					if(esCambio(cambios.get("oficioRefugiado"))){
						htmlDetalle.append("<tr>");
						htmlDetalle.append("<td>");
						htmlDetalle.append("<div class=\"detalle-cambio-cerrado\"></div>");
						htmlDetalle.append("Oficio Solicitante de Refugiado:");
						htmlDetalle.append("<div id=\"divDetalleCambios\" style=\"display:none\">");
						htmlDetalle.append("<div class=\"contenedor\">");
						
						if(esCambio(cambios.get("oficioRefugiado.folio"))){
							generarFilaDetalleParticular("Folio", cambios.get("oficioRefugiado.folio"), htmlDetalle, true);
						}
						
						htmlDetalle.append("</div>");
						htmlDetalle.append("</div>");
						htmlDetalle.append("</td>");
						htmlDetalle.append("</tr>");
					} 
					
					if(esCambio(cambios.get("formaMigratoria"))){
						htmlDetalle.append("<tr>");
						htmlDetalle.append("<td>");
						htmlDetalle.append("<div class=\"detalle-cambio-cerrado\"></div>");
						htmlDetalle.append("Forma Migratoria Turista:");
						htmlDetalle.append("<div id=\"divDetalleCambios\" style=\"display:none\">");
						htmlDetalle.append("<div class=\"contenedor\">");
						
						if(esCambio(cambios.get("formaMigratoria.folio"))){
							generarFilaDetalleParticular("Folio", cambios.get("formaMigratoria.folio"), htmlDetalle, true);
						}
						
						htmlDetalle.append("</div>");
						htmlDetalle.append("</div>");
						htmlDetalle.append("</td>");
						htmlDetalle.append("</tr>");
					}
				}
				
				htmlDetalle.append("</table>");
				htmlDetalle.append("</td>");
			} 
			
			// DATOS SAT
			if (datosEntrada.getIndCapturaDatosSAT()){
				
				htmlDetalle.append("<td style=\"vertical-align: top !important; padding: 0px 5px; width: 50%;\">");
				
				htmlDetalle.append("<table id=\"tblCambiosSAT\" class=\"tblDetalleCambios\" border=\"0\">");
				
				htmlDetalle.append("<tr>");
				htmlDetalle.append("<td colspan=\"2\" style=\"text-align: center;\">");
				htmlDetalle.append("DATOS SAT");
				htmlDetalle.append("</td>");
				htmlDetalle.append("</tr>");
				
				if (esCambio(cambios.get("rfc"))
						&& (BooleanUtils.isTrue(datosEntrada.getIndCapturaRFC()) 
								|| BooleanUtils.isTrue(datosEntrada.getIndCapturaDomicilioFiscal()) 
								|| BooleanUtils.isTrue(datosEntrada.getIndCapturaMediosContactoFiscales()))) {
					generarMensajeCambio(cambios.get("rfc"), htmlDetalle);
				}
				
				if (esCambio(cambios.get("domicilioFiscal"))
						&& BooleanUtils.isTrue(datosEntrada.getIndCapturaDomicilioFiscal())) {
					htmlDetalle.append("<tr>");
					htmlDetalle.append("<td>");
					htmlDetalle.append("<div class=\"detalle-cambio-cerrado\"></div>");
					htmlDetalle.append("Domicilio Fiscal:");
					htmlDetalle.append("<div id=\"divDetalleCambios\" style=\"display:none\">");
					htmlDetalle.append("<div class=\"contenedor\">");
					
					if(esCambio(cambios.get("codigoPostal"))){
						generarFilaDetalleParticular("C&oacute;digo Postal", cambios.get("codigoPostal"), htmlDetalle, true);
					}
					
					if(esCambio(cambios.get("calle"))){
						generarFilaDetalleParticular("Calle", cambios.get("calle"), htmlDetalle, true);
					}
					
					if(esCambio(cambios.get("colonia"))){
						generarFilaDetalleParticular("Colonia", cambios.get("colonia"), htmlDetalle, true);
					}
					
					if(esCambio(cambios.get("entidad"))){
						generarFilaDetalleParticular("Entidad Federativa", cambios.get("entidad"), htmlDetalle, true);
					}
					
					if(esCambio(cambios.get("localidad"))){
						generarFilaDetalleParticular("Localidad", cambios.get("localidad"), htmlDetalle, true);
					}
					
					if(esCambio(cambios.get("municipio"))){
						generarFilaDetalleParticular("Municipio", cambios.get("municipio"), htmlDetalle, true);
					}
											
					if(esCambio(cambios.get("entreCalle1"))){
						generarFilaDetalleParticular("Entre Calle 1", cambios.get("entreCalle1"), htmlDetalle, true);
					}
					
					if(esCambio(cambios.get("entreCalle2"))){
						generarFilaDetalleParticular("Entre Calle 2", cambios.get("entreCalle2"), htmlDetalle, true);
					}
					
					if(esCambio(cambios.get("inmueble"))){
						generarFilaDetalleParticular("Inmueble", cambios.get("inmueble"), htmlDetalle, true);
					}
					
					if(esCambio(cambios.get("referencia"))){
						generarFilaDetalleParticular("Referencia", cambios.get("referencia"), htmlDetalle, true);
					}
					
					if(esCambio(cambios.get("vialidad"))){
						generarFilaDetalleParticular("Vialidad", cambios.get("vialidad"), htmlDetalle, true);
					}
					
					if(esCambio(cambios.get("numeInt"))){
						generarFilaDetalleParticular("N&uacute;mero Interior", cambios.get("numeInt"), htmlDetalle, true);
					}
					
					if(esCambio(cambios.get("numeExt"))){
						generarFilaDetalleParticular("N&uacute;mero Exterior", cambios.get("numeExt"), htmlDetalle, true);
					}
					
					htmlDetalle.append("</div>");
					htmlDetalle.append("</div>");
					htmlDetalle.append("</td>");
					htmlDetalle.append("<td>");
					generarMensajeCambio(cambios.get("domicilioFiscal"), htmlDetalle);
					htmlDetalle.append("</td>");
					htmlDetalle.append("</tr>");
				}
				
				// Medios fiscales
				if (esCambio(cambios.get("correoElectronico")) 
						&& BooleanUtils.isTrue(datosEntrada.getIndCapturaMediosContactoFiscales())) {
					generarFilaDetalleParticular("Correo Electr&oacute;nico Fiscal", cambios.get("correoElectronico"), htmlDetalle, false);
				}
				
				if (esCambio(cambios.get("telefonoFijo")) && 
						BooleanUtils.isTrue(datosEntrada.getIndCapturaMediosContactoFiscales())) {
					generarFilaDetalleParticular("Tel&eacute;fono Fijo Fiscal", cambios.get("telefonoFijo"), htmlDetalle, false);
				}
				
				if (esCambio(cambios.get("telefonoMovil"))
						&& BooleanUtils.isTrue(datosEntrada.getIndCapturaMediosContactoFiscales())) {
					generarFilaDetalleParticular("Tel&eacute;fono M&oacute;vil Fiscal", cambios.get("telefonoMovil"), htmlDetalle, false);
				}
				
				
				if (esCambio(cambios.get("nombreRazonSocial"))
						&& BooleanUtils.isTrue(datosEntrada.getIndCapturaRazonSocial())) {
					generarFilaDetalleParticular("Nombre &oacute; Raz&oacute;n Social", cambios.get("nombreRazonSocial"), htmlDetalle, false);
				}
				
				if (esCambio(cambios.get("fechaConstitucion")) 
						&& BooleanUtils.isTrue(datosEntrada.getIndCapturaFechaConstitucion())) {
					generarFilaDetalleParticular("Fecha de Constituci&oacute;n", cambios.get("fechaConsitutucion"), htmlDetalle, false);
				}
				
				if (esCambio(cambios.get("fechaInicioOperaciones")) 
						&& BooleanUtils.isTrue(datosEntrada.getIndCapturaFechaConstitucion())) {
					generarFilaDetalleParticular("Fecha de Inicio de Operaciones", cambios.get("fechaInicioOperaciones"), htmlDetalle, false);
				}
				
				if (esCambio(cambios.get("tipoSociedad"))
						&& BooleanUtils.isTrue(datosEntrada.getIndCapturaTipoSociedad())) {
					generarFilaDetalleParticular("Tipo de Sociedad", cambios.get("tipoSociedad"), htmlDetalle, false);
				}
				
				htmlDetalle.append("</table>");
				htmlDetalle.append("</td>");
			}
					
			// DATOS COMPELEMENTARIOS
			if (BooleanUtils.isTrue(datosEntrada.getIndCapturaDatosComplementarios())) {
				
				htmlDetalle.append("<td style=\"vertical-align: top !important; padding-left: 5px;\">");
				
				htmlDetalle.append("<table id=\"tblCambiosDatosComplementarios\" class=\"tblDetalleCambios\" border=\"0\">");
				
				htmlDetalle.append("<tr>");
				htmlDetalle.append("<td colspan=\"2\" style=\"text-align: center;\">");
				htmlDetalle.append("DATOS COMPLEMENTARIOS");
				htmlDetalle.append("</td>");
				htmlDetalle.append("</tr>");
				
				if(BooleanUtils.isTrue(datosEntrada.getIndCapturaDomicilioParticular())){
					List<Domicilio> domiciliosParticulares = datosEntrada.getPersonaFisica().getDomicilios();
					
					// Se checa si hubo cambio en los domicilios particulares
					List<Domicilio> domiciliosCambios = new ArrayList<Domicilio>();
					for (Domicilio domicilio : domiciliosParticulares) {
						if(domicilio.getEstadoAdministracionDomicilio() != null && 
								(domicilio.getEstadoAdministracionDomicilio().getClave() == EstadoAdministracionEnum.NUEVO.getClave() ||
								domicilio.getEstadoAdministracionDomicilio().getClave() == EstadoAdministracionEnum.MODIFICADO.getClave())){
							domiciliosCambios.add(domicilio);
						}
					}
					
					if (!domiciliosCambios.isEmpty()){
						htmlDetalle.append("<tr>");
						htmlDetalle.append("<td>");
						htmlDetalle.append("<div class=\"detalle-cambio-cerrado\"></div>");
						htmlDetalle.append("Domicilios Particulares:");
						htmlDetalle.append("<div id=\"divDetalleCambios\" style=\"display:none\">");
						htmlDetalle.append("<div class=\"contenedor\">");
						
						CambioComparacionEnum cambio = null;
						for (Domicilio domicilio : domiciliosCambios) {
							htmlDetalle.append("<div class=\"row\">");
							htmlDetalle.append("<div class=\"cell\" style\"width: 65%;\">");
							
							if (domicilio.getDicTipoDomicilio() != null){
								long tipoDom = domicilio.getDicTipoDomicilio().getClave().longValue();
								
								if (tipoDom == TipoDomicilioEnum.PARTICULAR.getId()) {
									htmlDetalle.append("Domicilio Particular");
								} else if (tipoDom == TipoDomicilioEnum.FISCAL.getId()) {
									htmlDetalle.append("Domicilio Fiscal");
								} else if (tipoDom == TipoDomicilioEnum.CENTRO_TRABAJO.getId()) {
									htmlDetalle.append("Centro de Trabajo");
								} else if (tipoDom == TipoDomicilioEnum.RECIBIR_NOTIFICACIONES.getId()) {
									htmlDetalle.append("Domicilio para Escuchar y Recibir Notificaciones");
								}
								
							} else {
								htmlDetalle.append("Domicilio");
							}
							htmlDetalle.append("</div>");
							htmlDetalle.append("<div class=\"cell\" style=\"float: right;\">");
							
							if (domicilio.getEstadoAdministracionDomicilio().getClave() == EstadoAdministracionEnum.NUEVO.getClave()) {
								cambio = CambioComparacionEnum.NUEVO;
							} else {
								cambio = CambioComparacionEnum.CAMBIO;
							}
							
							generarMensajeCambio(cambio, htmlDetalle);
							htmlDetalle.append("</div>");
							htmlDetalle.append("</div>");
						}
						
						htmlDetalle.append("</div>");
						htmlDetalle.append("</div>");
						htmlDetalle.append("</td>");
						htmlDetalle.append("<td>");
						cambio = CambioComparacionEnum.CAMBIO;
						generarMensajeCambio(cambio, htmlDetalle);
						htmlDetalle.append("</td>");
						htmlDetalle.append("</tr>");
					}
				}
				
				if(BooleanUtils.isTrue(datosEntrada.getIndCapturaMediosContactoParticular())){
					List<MedioContacto> mediosParticulares = datosEntrada.getPersonaFisica().getMediosContacto();
					
					if (!mediosParticulares.isEmpty()){
						htmlDetalle.append("<tr>");
						htmlDetalle.append("<td>");
						htmlDetalle.append("<div class=\"detalle-cambio-cerrado\"></div>");
						htmlDetalle.append("Medios Particulares:");
						htmlDetalle.append("<div id=\"divDetalleCambios\" style=\"display:none\">");
						htmlDetalle.append("<div class=\"contenedor\">");
						
						CambioComparacionEnum cambio = null;
						for (MedioContacto medio : mediosParticulares) {
							if (medio.getEstadoAdministracionMedioContacto() != null) {
								htmlDetalle.append("<div class=\"row\">");
								htmlDetalle.append("<div class=\"cell\" style\"width: 65%;\">");
								htmlDetalle.append(medio.getTipoMedioContacto().getDescripcion());
								htmlDetalle.append("</div>");
								htmlDetalle.append("<div class=\"cell\" style=\"float: right;\">");
							
								if (medio.getEstadoAdministracionMedioContacto().getClave() == EstadoAdministracionEnum.NUEVO.getClave()) {
									cambio = CambioComparacionEnum.NUEVO;
								} else if (medio.getEstadoAdministracionMedioContacto().getClave() == EstadoAdministracionEnum.MODIFICADO.getClave()) {
									cambio = CambioComparacionEnum.CAMBIO;
								} else if (medio.getEstadoAdministracionMedioContacto().getClave() == EstadoAdministracionEnum.ELIMINADO.getClave()) {
									cambio = CambioComparacionEnum.ELIMINADO;
								}
							
								generarMensajeCambio(cambio, htmlDetalle);
								htmlDetalle.append("</div>");
								htmlDetalle.append("</div>");
							}
						}
						
						htmlDetalle.append("</div>");
						htmlDetalle.append("</div>");
						htmlDetalle.append("</td>");
						htmlDetalle.append("<td>");
						cambio = CambioComparacionEnum.CAMBIO;
						generarMensajeCambio(cambio, htmlDetalle);
						htmlDetalle.append("</td>");
						htmlDetalle.append("</tr>");
					}
				}
				
				if(BooleanUtils.isTrue(datosEntrada.getIndCapturaActaConstitutiva())){
					EscrituraConstitutiva escrituraConstitutiva = datosEntrada.getPersonaMoral().getEscrituraConstitutiva();
					
					if (escrituraConstitutiva != null) {
						
						CambioComparacionEnum cambio = null;
						
						if (escrituraConstitutiva.getCveEscrituraConstitutiva() != null) {
							cambio = CambioComparacionEnum.CAMBIO;
						} else {
							cambio = CambioComparacionEnum.NUEVO;
						}
						
						generarFilaDetalleParticular("Escritura Constitutiva", cambio, htmlDetalle, false);
					}
				}
				
				if(BooleanUtils.isTrue(datosEntrada.getIndCapturaRegistroSindicato())){
					RegistroSindicato registroSindicato = datosEntrada.getPersonaMoral().getRegistroSindicato();
					
					if (registroSindicato != null) {
						
						CambioComparacionEnum cambio = null;
						
						if (registroSindicato.getCveRegistroSindicato() != null) {
							cambio = CambioComparacionEnum.CAMBIO;
						} else {
							cambio = CambioComparacionEnum.NUEVO;
						}
						
						generarFilaDetalleParticular("Registro Sindicato", cambio, htmlDetalle, false);
					}
				}
				htmlDetalle.append("</td>");
			} 
			
			htmlDetalle.append("</tr>");
			htmlDetalle.append("</table>");
			
		} else {
			htmlDetalle.append("SIN CAMBIOS");
		}
	}
	
	private boolean esCambio(CambioComparacionEnum cambio) {

		boolean esCambio = false;
		
		if(cambio != null){
			if (cambio.getId().equals(CambioComparacionEnum.CAMBIO.getId())
					|| cambio.getId().equals(CambioComparacionEnum.NUEVO.getId())) {
				esCambio = true;
			}
		}
		
		return esCambio;
		
	}
	
	private void generarFilaDetalleParticular(String label,
			CambioComparacionEnum cambio, StringBuffer htmlDetalle,
			boolean esDiv) {
		
		if (!esDiv) {
			htmlDetalle.append("<tr>");
			htmlDetalle.append("<td>");
			htmlDetalle.append(label).append(":");
			htmlDetalle.append("</td>");
			htmlDetalle.append("<td>");
			
			generarMensajeCambio(cambio, htmlDetalle);
			
			htmlDetalle.append("</td>");
			htmlDetalle.append("</tr>");
		} else {
			htmlDetalle.append("<div class=\"row\">");
			htmlDetalle.append("<div class=\"cell\" style=\"width: 65%;\">");
			htmlDetalle.append(label);
			htmlDetalle.append("</div>");
			htmlDetalle.append("<div class=\"cell\" style=\"float: right;\">");
			generarMensajeCambio(cambio, htmlDetalle);
			htmlDetalle.append("</div>");
			htmlDetalle.append("</div>");
		}		
	}
	
	private void generarMensajeCambio (CambioComparacionEnum cambio, StringBuffer htmlDetalle){
		
		if (cambio.getId().equals(CambioComparacionEnum.CAMBIO.getId())) {
			htmlDetalle.append("<span class=\"label label-warning\" style=\"font-size: 8px;\">CAMBIO</span>");
		} else if (cambio.getId().equals(CambioComparacionEnum.NUEVO.getId())) {
			htmlDetalle.append("<span class=\"label label-info\" style=\"font-size: 8px;\">NUEVO</span>");
		} else if (cambio.getId().equals(CambioComparacionEnum.ELIMINADO.getId())) {
			htmlDetalle.append("<span class=\"label label-important\" style=\"font-size: 8px;\">ELIMINADO</span>");
		}
		
	}
	
	

}
