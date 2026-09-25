package mx.gob.imss.ctirss.delta.gestion.beneficio.service.utility;

import java.awt.image.BufferedImage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.beneficio.model.MovimientoRissType;
import mx.gob.imss.ctirss.delta.exception.beneficio.BeneficioRissException;
import mx.gob.imss.ctirss.delta.exception.beneficio.PersonaNoValidaBeneficioRissException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.framework.exceptions.TransformacionException;
import mx.gob.imss.ctirss.delta.framework.util.DateUtils;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.beneficio.Beneficio;
import mx.gob.imss.ctirss.delta.model.beneficio.CancelacionBeneficio;
import mx.gob.imss.ctirss.delta.model.beneficio.DescuentoBeneficio;
import mx.gob.imss.ctirss.delta.model.beneficio.EstadoBeneficio;
import mx.gob.imss.ctirss.delta.model.beneficio.MotivoCancelacionBeneficio;
import mx.gob.imss.ctirss.delta.model.beneficio.ReglasCancelacionBeneficio;
import mx.gob.imss.ctirss.delta.model.beneficio.RespuestaRifSat;
import mx.gob.imss.ctirss.delta.model.beneficio.TipoBeneficio;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.enums.ApartadoPersonaBeneficioEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoBeneficioEnum;
import mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum;
import mx.gob.imss.ctirss.delta.model.enums.MotivoCancelacionBeneficioEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoBeneficioEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRiss;
import mx.gob.imss.ctirss.delta.persistence.DicDescuentoRiss;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoBeneficio;
import mx.gob.imss.ctirss.delta.persistence.DicTipoBeneficio;
import mx.gob.imss.ctirss.delta.persistence.DitBeneficio;
import mx.gob.imss.ctirss.delta.persistence.DitBeneficioRiss;
import mx.gob.imss.ctirss.delta.persistence.DitDescuentoBeneficio;
import mx.gob.imss.ctirss.delta.persistence.DitPatSujObligBeneficio;
import mx.gob.imss.ctirss.delta.persistence.DitPatronGeneral;
import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaBeneficio;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.CollectionUtils;

@Stateless(mappedName = "beneficioServiceUtility", name = "beneficioServiceUtility")
public class BeneficioServiceUtility extends AbstractServiceUtility implements
		BeneficioServiceUtilityLocal {

	@Override
	public Beneficio convertirEntityToModel(DitBeneficio entity)
			throws TransformacionException {		
		Beneficio model = new Beneficio();
		
		model.setIdBeneficio((entity.getCveIdBeneficio() != null 
			? entity.getCveIdBeneficio().intValue():0));
		model.setInicioVigencia(entity.getFecInicioVigencia());
		model.setFinVigencia(entity.getFecFinVigencia());
		
		//Obtener historial de descuentos (obtener descuento actual)
		List<DitDescuentoBeneficio> listaDescuentosBeneficios = entity.getDitDescuentoBeneficios();
		if(!CollectionUtils.isEmpty(listaDescuentosBeneficios)){
			List<DescuentoBeneficio> listaDescuentosBeneficio = new ArrayList<DescuentoBeneficio>();
			for(DitDescuentoBeneficio descuentoBeneficio : listaDescuentosBeneficios){				
				DescuentoBeneficio descuento = new DescuentoBeneficio();
				descuento.setIdDescuentoBeneficio(descuentoBeneficio.getCveIdDescuentoBeneficio());
				descuento.setPorcentajeDescuento(descuentoBeneficio.getNumPorcentaje());
				descuento.setFecInicio(descuentoBeneficio.getFecInicio());
				descuento.setFechaFin(descuentoBeneficio.getFecFin());
				descuento.setAnioFiscal(descuentoBeneficio.getAnioFiscal());				
				if(esDescuentoBeneficioActual(descuentoBeneficio.getFecInicio(), 
					descuentoBeneficio.getFecFin())){
						model.setDescuentoActual(descuento);
				}			
				listaDescuentosBeneficio.add(descuento);
			}
			model.setListaDescuentosBeneficio(listaDescuentosBeneficio);
		}		
		//TipoBeneficio
		if (entity.getDicTipoBeneficio() != null
				&& entity.getDicTipoBeneficio().getCveIdTipoBeneficio() != null) {
			TipoBeneficio tipoBeneficio = new TipoBeneficio();
			tipoBeneficio.setIdTipoBeneficio(entity.getDicTipoBeneficio()
					.getCveIdTipoBeneficio().intValue());
			tipoBeneficio.setDescripcion(TipoBeneficioEnum.RIF.getDesc());
			model.setTipoBeneficio(tipoBeneficio);
		}
		//MotivoCancelacionBeneficio
		if (entity.getDicBeneficioCancelacion() != null
				&& entity.getDicBeneficioCancelacion()
						.getCveIdBeneficioCancelacion() > 0) {
			MotivoCancelacionBeneficio motivoCancelacion = new MotivoCancelacionBeneficio();
			motivoCancelacion.setIdMotivoCancelacionBeneficio(Long.valueOf(
					entity.getDicBeneficioCancelacion()
							.getCveIdBeneficioCancelacion()).intValue());
			motivoCancelacion.setDescripcion(entity
					.getDicBeneficioCancelacion().getDesBeneficioCancelacion());
			model.setMotivoCancelacion(motivoCancelacion);
		}
		//PersonaIndependiente o Patron
		EstadoBeneficio estadoBeneficio = new EstadoBeneficio();
		if(!CollectionUtils.isEmpty(entity.getDitPersonaBeneficios())){
			DitPersonaBeneficio persona = entity.getDitPersonaBeneficios().get(0);
			if(persona!=null && persona.getDicEstadoBeneficio()!=null){
				estadoBeneficio.setIdEstadoBeneficio(persona.getDicEstadoBeneficio()
						.getCveIdEstadoBeneficio().intValue());
				estadoBeneficio.setDescripcion(persona.getDicEstadoBeneficio()
						.getDesEstadoBeneficio());
				model.setEstadoBeneficio(estadoBeneficio);				
			}
			if(persona!=null && persona.getDitPersona()!=null){
				model.setFechaBaja(persona.getFecRegistroBaja());
				model.setRfc(persona.getDitPersona().getRfc());
				model.setCurp(persona.getDitPersona().getCurp());
				
				model.setFisica(new Fisica());
				model.getFisica().setRfc(persona.getDitPersona().getRfc());
				model.getFisica().setCurp(persona.getDitPersona().getCurp());				
				model.getFisica().setIdPersona(persona.getDitPersona().getCveIdPersona());
				model.getFisica().setNombre(persona.getDitPersona().getNomNombre());
				model.getFisica().setPrimerApellido(persona.getDitPersona().getNomPrimerApellido());
				model.getFisica().setSegundoApellido(persona.getDitPersona().getNomSegundoApellido());
			}
		}else if(!CollectionUtils.isEmpty(entity.getDitPatSujObligBeneficios())){
			List<SujetoObligado> listaSujetosObligados = new ArrayList<SujetoObligado>();			
			for(DitPatSujObligBeneficio patron : entity.getDitPatSujObligBeneficios()){
				SujetoObligado so = new SujetoObligado();
				so.setCveIdSujetoObligado(patron.getDitPatronSujetoObligado()
					.getCveIdPatronSujetoObligado());
				if(!CollectionUtils.isEmpty(patron.getDitPatronSujetoObligado().getDitPatronGenerals())){
					DitPatronGeneral patrongeneral = patron.getDitPatronSujetoObligado()
						.getDitPatronGenerals().get(0);
					so.setNumeroRegistroPatronal(patrongeneral.getRegPatron());
					so.setDigVerificador(patrongeneral.getDigVer());
				}
				if(patron.getDitPatronSujetoObligado().getDicModalidad()!=null){
					so.setModalidad(new Modalidad());
					so.getModalidad().setIdModalidad(patron.getDitPatronSujetoObligado()
						.getDicModalidad().getCveIdModalidad());
					so.getModalidad().setNumModalidad(patron.getDitPatronSujetoObligado()
						.getDicModalidad().getNumModalidad());
					so.getModalidad().setDescripcion(patron.getDitPatronSujetoObligado()
						.getDicModalidad().getDesModalidad());
				}
				if(patron.getDitPatronSujetoObligado().getDitPersonaFisica()!=null &&
						patron.getDitPatronSujetoObligado().getDitPersonaFisica().getDitPersona()!=null){
					so.setFisica(new Fisica());
					so.getFisica().setRfc(patron.getDitPatronSujetoObligado()
						.getDitPersonaFisica().getDitPersona().getRfc());
					so.getFisica().setCurp(patron.getDitPatronSujetoObligado()
						.getDitPersonaFisica().getDitPersona().getCurp());				
					so.getFisica().setIdPersona(patron.getDitPatronSujetoObligado()
						.getDitPersonaFisica().getDitPersona().getCveIdPersona());
					so.getFisica().setNombre(patron.getDitPatronSujetoObligado()
							.getDitPersonaFisica().getDitPersona().getNomNombre());
					so.getFisica().setPrimerApellido(patron.getDitPatronSujetoObligado()
							.getDitPersonaFisica().getDitPersona().getNomPrimerApellido());
					so.getFisica().setSegundoApellido(patron.getDitPatronSujetoObligado()
							.getDitPersonaFisica().getDitPersona().getNomSegundoApellido());
				}
				listaSujetosObligados.add(so);
			}
			model.setListaSujetosObligados(listaSujetosObligados);			
			
			DitPatSujObligBeneficio patron = entity.getDitPatSujObligBeneficios().get(0);
			if(patron!=null && patron.getDicEstadoBeneficio()!=null){				
				estadoBeneficio.setIdEstadoBeneficio(patron.getDicEstadoBeneficio()
						.getCveIdEstadoBeneficio().intValue());
				estadoBeneficio.setDescripcion(patron.getDicEstadoBeneficio()
						.getDesEstadoBeneficio());
				model.setEstadoBeneficio(estadoBeneficio);
				model.setFechaBaja(patron.getFecRegistroBaja());
			}			
			if(patron.getDitPatronSujetoObligado().getDitPersonaFisica()!=null){
				model.setRfc(patron.getDitPatronSujetoObligado().getDitPersonaFisica().getRfc());
			}
		}else{
			log.warn("El beneficio obtenido [cveIdBeneficio: "
					+entity.getCveIdBeneficio()+"] no tiene relaci�n");
		}

		return model;
	}

	@Override
	public DitBeneficio prepararBeneficioRiss(Beneficio model, List<DicDescuentoRiss> listaDescuentosRiss, 
			Date fechaActual, Date fechaSatRif, Date fechaInicioRifImss) throws BeneficioRissException {
		fechaSatRif = inicializarFechaInicio(fechaSatRif);
		Date fechaMaximaRif = fechaFinRif10Anios(fechaSatRif);	
		fechaInicioRifImss = inicializarFechaInicio(fechaInicioRifImss);
		Date fechaMaximaRifImss = fechaFinRif10Anios(fechaInicioRifImss);

		DitBeneficio entity = new DitBeneficio();		
		//Set Fechas Beneficio
		entity.setFecInicioVigencia(fechaActual);
		entity.setFecRegistroAlta(fechaActual);		
		//Set TipoBeneficio
		DicTipoBeneficio tipoBeneficio = new DicTipoBeneficio();
		tipoBeneficio.setCveIdTipoBeneficio((long)TipoBeneficioEnum.RIF.getClave());
		entity.setDicTipoBeneficio(tipoBeneficio);		
		//Set DitBeneficioRiss
		prepararDitBeneficioRiss(entity, fechaActual, fechaSatRif, fechaMaximaRif);		
		//Set DitDescuentoBeneficio

		if (fechaSatRif.before(fechaInicioRifImss)) {
			prepararDitDescuentoBeneficio(entity, listaDescuentosRiss, fechaActual, fechaInicioRifImss, fechaMaximaRifImss);
			entity.setFecFinVigencia(fechaMaximaRifImss);
		} else {
			prepararDitDescuentoBeneficio(entity, listaDescuentosRiss, fechaActual, fechaSatRif, fechaMaximaRif);
			entity.setFecFinVigencia(fechaMaximaRif);
		}

		//Set Relacion Perasona-Beneficio (DitPatSujObligBeneficio  o  DitPersonaBeneficio)
		DicEstadoBeneficio dicEstadoBeneficio = new DicEstadoBeneficio();
		dicEstadoBeneficio.setCveIdEstadoBeneficio((long)EstadoBeneficioEnum.ACTIVO.getClave());
		if(!CollectionUtils.isEmpty(model.getListaSujetosObligados())){
			List<DitPatSujObligBeneficio> relacion = new ArrayList<DitPatSujObligBeneficio>();			
			for(SujetoObligado so : model.getListaSujetosObligados()){
				//Set DitPatronSujetoObligado
				DitPatronSujetoObligado ditPatronSujetoObligado = new DitPatronSujetoObligado();
				ditPatronSujetoObligado.setCveIdPatronSujetoObligado(so.getCveIdSujetoObligado());
				//Set DitPatSujObligBeneficio
				DitPatSujObligBeneficio ditPatSujObligBeneficio = new DitPatSujObligBeneficio();
				ditPatSujObligBeneficio.setDitPatronSujetoObligado(ditPatronSujetoObligado);
				ditPatSujObligBeneficio.setFecRegistroAlta(fechaActual);
				ditPatSujObligBeneficio.setDicEstadoBeneficio(dicEstadoBeneficio);
				ditPatSujObligBeneficio.setDitBeneficio(entity);				
				//Set List<DitPatSujObligBeneficio> to DitBeneficio				
				relacion.add(ditPatSujObligBeneficio);
			}
			entity.setDitPatSujObligBeneficios(relacion);
			
		}else if (model.getFisica() != null
				&& model.getFisica().getIdPersona() != null) {			
			//Set DitPersona
			DitPersona ditPersona = new DitPersona();
			ditPersona.setCveIdPersona(model.getFisica().getIdPersona());
			//Set DitPersonaBeneficio
			DitPersonaBeneficio ditPersonaBeneficio = new DitPersonaBeneficio();
			ditPersonaBeneficio.setDitPersona(ditPersona);
			ditPersonaBeneficio.setFecRegistroAlta(fechaActual);
			ditPersonaBeneficio.setDicEstadoBeneficio(dicEstadoBeneficio);
			ditPersonaBeneficio.setDitBeneficio(entity);
			//Set List<DitPersonaBeneficio> to DitBeneficio
			entity.setDitPersonaBeneficios(new ArrayList<DitPersonaBeneficio>());
			entity.getDitPersonaBeneficios().add(ditPersonaBeneficio);
		
		} else{
			//lanzarErrorBeneficioRiss("beneficioRiss.general.sinPersonaBeneficio");
			lanzarErrorBeneficioRiss("El beneficio no tiene relaci\u00f3n con " +
				"una Persona F\u00edsica o Sujeto Obligado.");
		}		
		return entity;
	}
	
	@Override
	public List<DescuentoBeneficio> obtenerDescuentoBeneficio(List<DicDescuentoRiss> listaDescuentosRiss, 
			Date fechaActual, Date fechaSatRif, Date fechaMaximaRif){		
		DitBeneficio entity = new DitBeneficio();
		List<DescuentoBeneficio> listaDescuentoBeneficio = new ArrayList<DescuentoBeneficio>();
		 
		prepararDitDescuentoBeneficio(entity, listaDescuentosRiss, fechaActual, fechaSatRif, fechaMaximaRif);
		if(!CollectionUtils.isEmpty(entity.getDitDescuentoBeneficios())){
			for(DitDescuentoBeneficio entityDitDB : entity.getDitDescuentoBeneficios() ){
				DescuentoBeneficio descuento = new DescuentoBeneficio();
				descuento.setFecInicio(entityDitDB.getFecInicio());
				descuento.setFechaFin(entityDitDB.getFecFin());
				descuento.setPorcentajeDescuento(entityDitDB.getNumPorcentaje());
				descuento.setAnioFiscal(entityDitDB.getAnioFiscal());
				listaDescuentoBeneficio.add(descuento);
			}
		}
		return listaDescuentoBeneficio;
	}
		
	@Override
	public TramiteRiss obtenerTramitePersonaFisica(Solicitud solicitud){
		if(!CollectionUtils.isEmpty(solicitud.getTramites())){
			for (Tramite tramite : solicitud.getTramites()) {								
				if(((TramiteRiss)tramite).getFisica()!=null 
					&& esTramiteActivo((TramiteRiss)tramite)){
					return ((TramiteRiss)tramite);
				}
			}
		}				
		return null;
	}
	
	@Override
	public TramiteRiss obtenerTramitePatron(Solicitud solicitud){
		if(!CollectionUtils.isEmpty(solicitud.getTramites())){
			for (Tramite tramite : solicitud.getTramites()) {
				if(!CollectionUtils.isEmpty(((TramiteRiss)tramite).getListaCveIdSujetosObligados())
					&& esTramiteActivo((TramiteRiss)tramite)){
					return ((TramiteRiss)tramite);
				}
			}
		}				
		return null;
	}
	
	@Override
	public Solicitud crearSolicitudRiss(Beneficio beneficio, Usuario usuario,
			Long idOrigenSolicitud){
		Solicitud solicitud = new Solicitud();
		Date fechaActual = new Date();		
		
		TipoTramite tipoTramite = new TipoTramite();
		tipoTramite.setIdTipoTramite(TipoTramiteEnum.ALTA_RIF.getCodigo());
		EstadoTramite estadoTramite = new EstadoTramite();
		estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.INICIADO.getCodigo());
		estadoTramite.setDescripcion(EstadoTramiteEnum.INICIADO.getDescripcion());
		
		TipoSolicitud tipoSolicitud = new TipoSolicitud();
		tipoSolicitud.setIdTipoSolicitud(TipoSolicitudEnum.INCORPORACION_BENEFICIO.getValor().longValue());
		EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
		estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.REGISTRADA.getCodigo());
						
		OrigenSolicitudEnum origen = OrigenSolicitudEnum.getById(idOrigenSolicitud);
		if(origen!=null){
			OrigenSolicitud origenSolicitud = new OrigenSolicitud();
			origenSolicitud.setIdTipoSolicitud(origen.getId());
			origenSolicitud.setDescripcion(origen.getDesc());
			solicitud.setOrigenSolicitud(origenSolicitud);
		}		
		
		solicitud.setTipoSolicitud(tipoSolicitud);
		solicitud.setEstadoSolicitud(estadoSolicitud);
		solicitud.setFechaSolicitud(fechaActual);
		solicitud.setFechaPresentacion(fechaActual);	

		//Prepara tramite riss (PF y/o SOs)
		List<Tramite> listaTramiteRiss = new ArrayList<Tramite>();		
		if(beneficio.getFisica()!=null && beneficio.getFisica().getIdPersona()!=null){
			TramiteRiss tramiteRissPF = prepararTramiteRelacionPersona(tipoTramite, fechaActual, 
				estadoTramite, beneficio, true);
			tramiteRissPF.setListaCveIdSujetosObligados(null);
			listaTramiteRiss.add(tramiteRissPF);
		}
		if(!CollectionUtils.isEmpty(beneficio.getListaSujetosObligados())){
			TramiteRiss tramiteRissPatron = prepararTramiteRelacionPersona(tipoTramite, fechaActual,
				estadoTramite, beneficio, false);
			tramiteRissPatron.setFisica(null);
			tramiteRissPatron.setIdBeneficioPatronExistente(beneficio.getIdBeneficioPatronExistente());
			listaTramiteRiss.add(tramiteRissPatron);
		}		
		solicitud.setTramites(listaTramiteRiss);
		
		if (usuario != null) {
			UsuarioFuncionario uf = usuario.getUsuarioFuncionario();
			if (uf != null && uf.getSubdelegacion() != null
					&& uf.getSubdelegacion().getId() != null
					&& !uf.getSubdelegacion().getId().equals(-1)) {
				/*
				 * Debido a que a nivel base de datos, s�lo se puede relacionar
				 * una solicitud a nivel subdelegacional, no se toma en cuenta
				 * el nivel delegacional
				 */
				solicitud.setSubdelegacion(uf.getSubdelegacion());
			}
			log.debug("El usuario para la solicitud del RIF es:" + usuario.getUsuario());
			solicitud.setSolicitante(usuario);
		}
		
		return solicitud;		
	}
	
	@Override
	public Solicitud crearSolicitudRechazoRif(Fisica fisica, Usuario usuario,
			Long idOrigenSolicitud, String motivoRechazo){
		Solicitud solicitud = new Solicitud();
		Date fechaActual = new Date();		
		TipoTramite tipoTramite = new TipoTramite();
		tipoTramite.setIdTipoTramite(TipoTramiteEnum.ALTA_RIF.getCodigo());
		EstadoTramite estadoTramite = new EstadoTramite();
		estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getCodigo());
		estadoTramite.setDescripcion(EstadoTramiteEnum.CERRADO.getDescripcion());
		TipoSolicitud tipoSolicitud = new TipoSolicitud();
		tipoSolicitud.setIdTipoSolicitud(TipoSolicitudEnum.INCORPORACION_BENEFICIO.getValor().longValue());
		EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
		estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.RECHAZADA.getCodigo());
		OrigenSolicitudEnum origen = OrigenSolicitudEnum.getById(idOrigenSolicitud);
		if(origen!=null){
			OrigenSolicitud origenSolicitud = new OrigenSolicitud();
			origenSolicitud.setIdTipoSolicitud(origen.getId());
			origenSolicitud.setDescripcion(origen.getDesc());
			solicitud.setOrigenSolicitud(origenSolicitud);
		}
		if (usuario != null) {
			UsuarioFuncionario uf = usuario.getUsuarioFuncionario();
			if (uf != null && uf.getSubdelegacion() != null
					&& uf.getSubdelegacion().getId() != null
					&& !uf.getSubdelegacion().getId().equals(-1)) {
				solicitud.setSubdelegacion(uf.getSubdelegacion());
			}
			solicitud.setSolicitante(usuario);
		}		
		List<Tramite> listaTramiteRiss = new ArrayList<Tramite>();
		TramiteRiss tramiteRiss = new TramiteRiss();		
		tramiteRiss.setTipoTramite(tipoTramite);
		tramiteRiss.setFechaTramite(fechaActual);
		tramiteRiss.setFechaPresentacion(fechaActual);
		tramiteRiss.setEstadoTramite(estadoTramite);		
		tramiteRiss.setFisica(fisica);
		tramiteRiss.setRfcSolicitud(fisica.getRfc()); 
		tramiteRiss.setListaCveIdSujetosObligados(null);
		tramiteRiss.setRespuestaRifSat(new RespuestaRifSat());
		tramiteRiss.getRespuestaRifSat().setDescripcion(motivoRechazo);
		tramiteRiss.getRespuestaRifSat().setMotivoDeRechazo(motivoRechazo);
		listaTramiteRiss.add(tramiteRiss);
		solicitud.setTipoSolicitud(tipoSolicitud);
		solicitud.setEstadoSolicitud(estadoSolicitud);
		solicitud.setFechaSolicitud(fechaActual);
		solicitud.setFechaPresentacion(fechaActual);
		solicitud.setTramites(listaTramiteRiss);
		solicitud.setObservacion(motivoRechazo);
		
		return solicitud;		
	}
	
	@Override
	public List<Integer> getBeneficioEstadoActivo(){
		List<Integer> estadosBeneficios = new ArrayList<Integer>();
		estadosBeneficios.add(EstadoBeneficioEnum.ACTIVO.getClave());
		return estadosBeneficios;
	}
	
	@Override
	public List<Integer> getTipoBeneficioRif(){
		List<Integer> estadosBeneficios = new ArrayList<Integer>();
		estadosBeneficios.add(TipoBeneficioEnum.RIF.getClave());
		return estadosBeneficios;
	}
	
	@Override
	public Date fechaFinRif10Anios(Date fechaSatRif){
		Calendar calendarFinRif = new GregorianCalendar();
		calendarFinRif.setTime(fechaSatRif);
		calendarFinRif.set(Calendar.HOUR, 11);
		calendarFinRif.set(Calendar.MINUTE, 59);
		calendarFinRif.set(Calendar.SECOND, 59);
		calendarFinRif.add(Calendar.YEAR, getMaximoAniosRif());
		return calendarFinRif.getTime();		
	}
	
	@Override
	public List<Long> getIdsBeneficios(List<Beneficio> listaBeneficios){
		List<Long> idsBeneficios = new ArrayList<Long>();
		for(Beneficio beneficio : listaBeneficios){
			idsBeneficios.add(beneficio.getIdBeneficio().longValue());
		}
		return idsBeneficios;
	}
	
	@Override
	public List<Long> getIdsPersonasFisicas(List<Fisica> listaFisicas){
		List<Long> idsFisicas = new ArrayList<Long>();
		for(Fisica fisica : listaFisicas){
			idsFisicas.add(fisica.getIdPersona());
		}
		return idsFisicas;
	}
	
	@Override
	public List<Long> getIdsSujetosObligados(List<SujetoObligado> listaSujetosObligados){
		
		Set<Long> setIdsSOs = new HashSet<Long>();
		for(SujetoObligado so : listaSujetosObligados){
			setIdsSOs.add(so.getCveIdSujetoObligado());
		}
		return (new ArrayList<Long>(setIdsSOs));
	}
	
	@Override
	public List<Long> getIdsModalidades10y13(){
		List<Long> idsModalidades = new ArrayList<Long>(2);
		idsModalidades.add(ModalidadEnum.DIEZ.getId());
		idsModalidades.add(ModalidadEnum.TRECE.getId());		
		return idsModalidades;
	}
		
	@Override
	public void lanzarErrorBeneficioRiss(String mensaje) 
			throws BeneficioRissException{
		if(mensaje!=null){
			throw new BeneficioRissException(mensaje);
		}
		throw new BeneficioRissException();	
	}
	
	@Override
	public void lanzarErrorPersonaNoValidaBeneficioRissException(String mensaje) 
			throws PersonaNoValidaBeneficioRissException{
		if(mensaje!=null){
			throw new PersonaNoValidaBeneficioRissException(mensaje);
		}
		throw new PersonaNoValidaBeneficioRissException();	
	}
	
	@Override
	public void validarBeneficiosExistentes(List<Beneficio> listaBeneficios) 
			throws BeneficioRissException{
		if(!CollectionUtils.isEmpty(listaBeneficios)){
			for (Beneficio beneficio : listaBeneficios) {
				if(beneficio.getEstadoBeneficio().getIdEstadoBeneficio().equals(EstadoBeneficioEnum.CANCELADO.getClave())){
					lanzarErrorBeneficioRiss("Debido a cancelaci\u00f3n, ya no cuentas con los beneficios del r\u00e9gimen de " +
							"incorporaci\u00f3n fiscal dentro del Instituto Mexicano del Seguro Social.");
				}
			}

			lanzarErrorBeneficioRiss("Ya cuentas con los beneficios del r\u00e9gimen de " +
				"incorporaci\u00f3n fiscal dentro del Instituto Mexicano del Seguro Social.");
		}
	}	
	
	@Override
	public Beneficio validarBeneficioPorPeriodo(List<Beneficio> listaBeneficios,
			Date fechaInicio, Date fechaFin) throws BeneficioRissException{		
		if(!CollectionUtils.isEmpty(listaBeneficios)){
			List<DescuentoBeneficio> listaDescuentosBeneficio = new ArrayList<DescuentoBeneficio>();
			Beneficio beneficio = listaBeneficios.get(0);
			if(!CollectionUtils.isEmpty(beneficio.getListaDescuentosBeneficio())){
				for(DescuentoBeneficio descuento : beneficio.getListaDescuentosBeneficio()){					
					if( !(((((Date)descuento.getFecInicio()).compareTo(fechaInicio)<0)	
						&&(((Date)descuento.getFechaFin()).compareTo(fechaInicio)<0)
						&& (((Date)descuento.getFecInicio()).compareTo(fechaFin)<0)	
						&&(((Date)descuento.getFechaFin()).compareTo(fechaFin)<0)
						||
						(((Date)descuento.getFecInicio()).compareTo(fechaInicio)>0)	
						&&(((Date)descuento.getFechaFin()).compareTo(fechaInicio)>0)
						&& (((Date)descuento.getFecInicio()).compareTo(fechaFin)>0)	
						&&(((Date)descuento.getFechaFin()).compareTo(fechaFin)>0))) ){
							listaDescuentosBeneficio.add(descuento);
					}
				}
			}
			beneficio.setListaDescuentosBeneficio(listaDescuentosBeneficio);
			if(CollectionUtils.isEmpty(beneficio.getListaDescuentosBeneficio())){
				//lanzarErrorBeneficioRiss("beneficioRiss.general.sinBeneficiosPorPeriodo");	
				lanzarErrorBeneficioRiss("No cuenta con beneficios para el periodo proporcionado.");
			}
			return beneficio;
		}else{
			//lanzarErrorBeneficioRiss("beneficioRiss.general.sinBeneficiosAsociados");
			lanzarErrorBeneficioRiss("No cuentas con los beneficios del r\u00e9gimen de " +
				"incorporaci\u00f3n fiscal dentro del Instituto Mexicano del Seguro Social.");
			
		}
		return null;
	}
	
	@Override
	public void validarRegimenRif(boolean esRegimenRif) throws BeneficioRissException{
		if(!esRegimenRif){
			//lanzarErrorBeneficioRiss("beneficioRiss.general.sinRegimenRif");
			lanzarErrorBeneficioRiss("El SAT reporta que no se encuentra bajo el " +
				"R\u00e9gimen de Incorporaci\u00f3n Fiscal.");
		}		
	}
	
	@Override
	public void validarFiltrosBeneficios(String valor, Date fechaInicio, Date fechaFin) 
			throws BeneficioRissException{
		if(valor==null  || fechaInicio==null || fechaFin==null){
			//lanzarErrorBeneficioRiss("beneficioRiss.general.datosIncompletos");	
			lanzarErrorBeneficioRiss("Datos incompletos para obtener informaci\u00f3n.");
		}
	}
	
	@Override
	public void validarPersonaFisica(Fisica fisica) 
			throws BeneficioRissException{
		if (fisica==null || fisica.getIdPersona() == null) {
			//lanzarErrorBeneficioRiss("beneficioRiss.pf.errorConsultaBeneficio");
			lanzarErrorBeneficioRiss("La persona f\u00edsica es requerida para consultar los beneficios.");
		}
	}
		
	@Override
	public void validarSujetoObligado(SujetoObligado sujetoObligado) 
			throws BeneficioRissException{
		if (sujetoObligado==null || sujetoObligado.getCveIdSujetoObligado() == null) {
			//lanzarErrorBeneficioRiss("beneficioRiss.so.errorConsultaBeneficio");	
			lanzarErrorBeneficioRiss("El sujeto sbligado es requerido para consultar los beneficios.");
		}
	}
	
	@Override
	public void validarRespuestaSat(boolean respuesta) throws BeneficioRissException{
		if(!respuesta){
			lanzarErrorBeneficioRiss("El SAT reporta que no tiene derecho para obtener el beneficio.");
		}		
	}
	
	@Override
	public void validarRespuestaInfonavit(boolean respuesta) throws BeneficioRissException{
		if(!respuesta){
			lanzarErrorBeneficioRiss("El INFONAVIT reporta que no tiene derecho para obtener el beneficio.");
		}		
	}
	
	@Override
	public void validarVigenciaBeneficioRiss(Date fechaAltaRif, 
			Date FechaInicioRifImss) throws BeneficioRissException{
		if(fechaAltaRif==null){
			lanzarErrorBeneficioRiss("El SAT no reporto fecha de alta del RIF.");
		}else{
			Date fechaActual = inicializarFechaInicio(new Date());
			fechaAltaRif = inicializarFechaInicioRif(fechaAltaRif, FechaInicioRifImss);
			Date fechaMaximaRif = fechaFinRif10Anios(fechaAltaRif);			
			if( (fechaMaximaRif.compareTo(fechaActual)<0) ){
				SimpleDateFormat sDF=new SimpleDateFormat(BeneficiosConstants.FORMAT_DATE_DIAGONAL_dd_MM_yyyy);
				lanzarErrorBeneficioRiss("La vigencia del beneficio ya expir\u00f3, finaliz\u00f3 el "
					+sDF.format(fechaMaximaRif)+
					". La fecha de alta del RIF que reporta el SAT corresponde al "+sDF.format(fechaAltaRif));
			}
		}
	}
	
	@Override
	public void validarCalificacionSat(boolean tieneCalificacionSat) throws BeneficioRissException{
		if(!tieneCalificacionSat){
		    lanzarErrorBeneficioRiss("Debe ingresar su RFC para ser evaluado como sujeto al R\u00e9gimen de Incorporaci\u00f3n a la Seguridad Social.");
		}
	}
	
	@Override
	public CancelacionBeneficio obtenerTipoMotivoCancelacion(String rfc, String nrp, 
			String nss,	int indicadorInstitucion){
		CancelacionBeneficio cancelacion =  new CancelacionBeneficio();
		cancelacion.setCveOperacion(null);
		cancelacion.setDescripcionOperacion(null);
		
		MotivoCancelacionBeneficioEnum motivoCancelacion = 
			MotivoCancelacionBeneficioEnum.obtenerEnumById(indicadorInstitucion);
		
		if(motivoCancelacion!=null){
			//SAT
			if(indicadorInstitucion==MotivoCancelacionBeneficioEnum.POR_SAT.getClave()){
				if(rfc != null && rfc.length() == 13){
					//RN1 Cancelar beneficios como persona y patron por RFC
					cancelacion.setMotivoCancelacion(indicadorInstitucion);
					cancelacion.setIdRNCancelacion(ReglasCancelacionBeneficio
						.RN_CANCELA_BENEFICIO_TODO_SAT_RFC.getClave());
				}else{
					//Error: SAT no proporciono RFC
					cancelacion.setCveOperacion(01);
					cancelacion.setDescripcionOperacion("Contribuyente no localizado: " +
						"Es requerido un RFC de 13 posiciones");
				}				
			//INFONAVIT	
			}else if(indicadorInstitucion==MotivoCancelacionBeneficioEnum.POR_INFONAVIT.getClave()){
				if (nrp != null && nrp.length() >= 10){
					//RN2 Cancelar beneficios como persona y patron por NRP
					cancelacion.setMotivoCancelacion(indicadorInstitucion);
					cancelacion.setIdRNCancelacion(ReglasCancelacionBeneficio
						.RN_CANCELA_BENEFICIO_TODO_INFONAVIT_NRP.getClave());
				}else if (nss!=null){
					//RN3 Cancelar beneficios como persona por NSS
					cancelacion.setMotivoCancelacion(indicadorInstitucion);
					cancelacion.setIdRNCancelacion(ReglasCancelacionBeneficio
						.RN_CANCELA_BENEFICIO_PERSONA_INFONAVIT_NSS.getClave());
				}else{
					//Error: INFONAVIT no proporciono NRP o NSS (no aplica RFC)
					cancelacion.setCveOperacion(01);
					cancelacion.setDescripcionOperacion("Contribuyente no localizado: " +
						"Es requerido un NRP de 10 u 11 posiciones o NSS");
				}				
			//IMSS		
			}else {
				if (nrp != null && nrp.length() >= 10){
					//RN4 Cancelar beneficios como persona y patron por NRP
					cancelacion.setMotivoCancelacion(indicadorInstitucion);
					cancelacion.setIdRNCancelacion(ReglasCancelacionBeneficio
						.RN_CANCELA_BENEFICIO_TODO_IMSS_NRP.getClave());
				}else if (nss!=null){
					//RN5 Cancelar beneficios como persona por NSS
					cancelacion.setMotivoCancelacion(indicadorInstitucion);
					cancelacion.setIdRNCancelacion(ReglasCancelacionBeneficio
						.RN_CANCELA_BENEFICIO_PERSONA_IMSS_NSS.getClave());
				}else{
					//Error: IMSS no proporciono NRP o NSS (no aplica RFC)
					cancelacion.setCveOperacion(01);
					cancelacion.setDescripcionOperacion("Contribuyente no localizado: " +
						"Es requerido un NRP de 10 u 11 posiciones o NSS");
				}
			}
		}else{
			cancelacion.setCveOperacion(03);
			cancelacion.setDescripcionOperacion("Indicador de instituci�n incorrecto");
		}		
		return cancelacion;
	}
	
	@Override
	public List<MovimientoRissType> prepararLayoutMovimientoAltaRiss(List<DescuentoBeneficio> listaDescuentosBeneficio, 
			List<SujetoObligado> listaSujetosObligados, boolean incluirMovimientoPF, Fisica fisicaMovimiento, 
			Date fechaActual, RespuestaRifSat respuestaRifSat, String patronGeneral){

		Date fechaAltaRif = ((respuestaRifSat!=null && respuestaRifSat.getFechaAltaRif()!=null) 
			? respuestaRifSat.getFechaAltaRif() : new Date());
				
		List<MovimientoRissType> listaMovimientoRissType = new ArrayList<MovimientoRissType>();		
		if(!CollectionUtils.isEmpty(listaSujetosObligados)){
			//Generar lista de movimiento "por cada descuento existente de cada sujeto obligado"
			listaMovimientoRissType = movimientosAltaPatrones(listaDescuentosBeneficio, 
				listaSujetosObligados, fisicaMovimiento, fechaActual, fechaAltaRif, respuestaRifSat);			
			if(incluirMovimientoPF){
				//Considerar movimientos del patron pero como persona fisica independiente.
				listaMovimientoRissType.addAll(movimientosAltaPersonaFisica(listaDescuentosBeneficio, 
					fisicaMovimiento, obtenerTipoApartadoBeneficio(respuestaRifSat, false), 
					fechaActual, fechaAltaRif, patronGeneral));
			}
		}else{
			//Es una persona fisica independiente, generar los movimientos de descuento de la persona.
			listaMovimientoRissType = movimientosAltaPersonaFisica(listaDescuentosBeneficio, 
				fisicaMovimiento, obtenerTipoApartadoBeneficio(respuestaRifSat, false), 
				fechaActual, fechaAltaRif, patronGeneral);
		}
		return listaMovimientoRissType;
	}
	
	@Override
	public List<MovimientoRissType> movimientosAltaPatrones(List<DescuentoBeneficio> listaDescuentosBeneficio, 
			List<SujetoObligado> listaSujetosObligados, Fisica fisicaMovimiento, Date fechaActual, 
			Date fechaAltaRif, RespuestaRifSat respuestaRifSat){		
		String tipoPersona = obtenerTipoApartadoBeneficio(respuestaRifSat, true);		
		List<MovimientoRissType> listaMovimientoRissType = new ArrayList<MovimientoRissType>();
		if(!CollectionUtils.isEmpty(listaSujetosObligados)){
			for(SujetoObligado so : listaSujetosObligados){
				MovimientoRissType layoutBase = new MovimientoRissType();	
				datosGeneralesLayout(layoutBase, tipoPersona, fechaActual, fechaAltaRif);
				datosPatronLayout(layoutBase, so);
				datosPersonaFisicaLayout(layoutBase, fisicaMovimiento);
				agregarMovimientosRiss(layoutBase, listaMovimientoRissType, listaDescuentosBeneficio, fechaAltaRif);
			}
		}
		return listaMovimientoRissType;
	}
	
	@Override
	public List<MovimientoRissType> prepararLayoutMovimientoBajaBeneficio(List<DitBeneficio> listaDitBeneficio,
			DitPersona personaMovimiento, String nss, int motivoBaja, Date fechaBaja, String patronGeneral) 
			throws BeneficioRissException{
		List<MovimientoRissType> listaMovimientoRissType = new ArrayList<MovimientoRissType>();
		try{
			for(DitBeneficio beneficio : listaDitBeneficio){
				List<DitPatSujObligBeneficio> listaPatrones = beneficio.getDitPatSujObligBeneficios();
				MovimientoRissType layoutBase = new MovimientoRissType();
				layoutBase.setRfc(personaMovimiento.getRfc());
				layoutBase.setNss(nss);
				layoutBase.setCurp(personaMovimiento.getCurp());
				layoutBase.setFecBajaRiss(fechaBaja);
				layoutBase.setMotBaja(motivoBaja);
				layoutBase.setFecMovto(new Date());
				
				if(!CollectionUtils.isEmpty(listaPatrones)){
					//Generar los movimientos de baja por patron.
					for(DitPatSujObligBeneficio patron : listaPatrones){
						MovimientoRissType layoutPatrones = (MovimientoRissType)layoutBase.clone();
						
						if(patron!=null && patron.getDitPatronSujetoObligado()!=null
							&& (!CollectionUtils.isEmpty(patron.getDitPatronSujetoObligado()
								.getDitPatronGenerals()))){
							
							DitPatronGeneral patronGral = patron.getDitPatronSujetoObligado()
								.getDitPatronGenerals().get(0);
							layoutPatrones.setRegPatAlf(patronGral.getRegPatron());
							layoutPatrones.setRegPatDv(patronGral.getDigVer());
							if(patron.getDitPatronSujetoObligado().getDicModalidad()!=null && patron
								.getDitPatronSujetoObligado().getDicModalidad().getNumModalidad()!=null){
								String nModalidad = patron.getDitPatronSujetoObligado()
									.getDicModalidad().getNumModalidad();							
								layoutPatrones.setRegPatMod(Integer.parseInt(nModalidad));
							}
							listaMovimientoRissType.add(layoutPatrones);
						}
					}
				}else if(!CollectionUtils.isEmpty(beneficio.getDitPersonaBeneficios())){
					//Generar movimiento de baja por personas independientes (fisicas)
					datosPatronGenericoLayout(layoutBase, patronGeneral);
					listaMovimientoRissType.add(layoutBase);
				}
			}
		}catch(Exception e){
			lanzarErrorBeneficioRiss("Error al procesar movimiento de baja.");
		}
		return listaMovimientoRissType;
	}
	
	@Override
	public String generarCadenaOriginal(Solicitud solicitud, Persona persona){
		Locale locMEX = new Locale(BeneficiosConstants.LOCALE_es, BeneficiosConstants.LOCALE_MX);		
		DateFormat dateFormat = new SimpleDateFormat(BeneficiosConstants.FORMAT_DATE_FORMATO_LARGO_1, locMEX);
		StringBuffer contenidoAFirmar = new StringBuffer();		// Inicio
		contenidoAFirmar.append("||");
		contenidoAFirmar.append("Invocante:portalimssdigital|");
		// Denominacion del Tramite o servicio
		contenidoAFirmar.append("Tramite:");
		contenidoAFirmar.append(TipoBeneficioEnum.RIF.getDesc()).append("|");
		// Fecha Electronica
		String strFechaElectronica = dateFormat.format(obtenerFechaSolicitud(solicitud));
		contenidoAFirmar.append("Fecha:");
		contenidoAFirmar.append(strFechaElectronica).append("|");		
		// Folio
		contenidoAFirmar.append("Folio:");
		contenidoAFirmar.append(solicitud.getNoFolioSolicitud()).append("|");		
		// RFC
		contenidoAFirmar.append("RFC:");
		contenidoAFirmar.append(persona.getRfc()).append("|");		
		//Datos de la persona fisica
		if (persona instanceof Fisica) {
			// Nombre
			contenidoAFirmar.append("Nombre:");
			contenidoAFirmar.append(((Fisica)persona).getNombreCompleto()).append("|");			
			// CURP
			contenidoAFirmar.append("CURP:");
			contenidoAFirmar.append(((Fisica)persona).getCurp()).append("|");
			// Registro Patronal(No aplica)
			//contenidoAFirmar.append("Registro Patronal:|");			
			// NSS
			if (StringUtils.isNotEmpty(((Fisica)persona).getNss())					
					 && StringUtils.isNotBlank(((Fisica)persona).getNss())) {
				contenidoAFirmar.append("NSS:");
				contenidoAFirmar.append(((Fisica)persona).getNss()).append("|");
			}			
		}
		contenidoAFirmar.append("|");		
		return contenidoAFirmar.toString();		
	}
	
	@Override
	public Map<String, Object> generarParametrosReporteRiss(Fisica persona, FirmaElectronica firmaElectronica, 
			RespuestaRifSat respuestaRifSat, boolean tramiteFisica, boolean tramitePatron, Date fechaAltaBeneficio,
			List<SujetoObligado> listaSujetosObligados, Solicitud solicitud, BufferedImage imagenCodeQR){		
		OrigenSolicitudEnum origen = OrigenSolicitudEnum.getById(solicitud
			.getOrigenSolicitud().getIdTipoSolicitud());
		String mostrarNrps = BeneficiosConstants.CONSTANTE_SI;
		//Trabajador independientes 35, 43 y 44 (Para la modalidad 35 NO mostrar NRPs)
		if(tramiteFisica){
			listaSujetosObligados=null;
			mostrarNrps = BeneficiosConstants.CONSTANTE_NO;
		}		
		if(!CollectionUtils.isEmpty(listaSujetosObligados)){
			for(SujetoObligado so : listaSujetosObligados){
				StringBuffer nrp = new StringBuffer();
				nrp.append(so.getNumeroRegistroPatronal());
				nrp.append(so.getModalidad().getNumModalidad());
				nrp.append(so.getDigVerificador());
				so.setNumeroRegistroPatronal(nrp.toString());
			}
		}
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(BeneficiosConstants.REPORTES_IMAGENES_DIR, 
			new ClassPathResource(BeneficiosConstants.REPORTES_RISS_CLASS_PATH).getPath());
		parameters.put(BeneficiosConstants.REPORTES_SUBREPORT_DIR, 
			new ClassPathResource(BeneficiosConstants.REPORTES_RISS_CLASS_PATH).getPath());
		parameters.put(BeneficiosConstants.REPORTES_PARAM_NOMBRE_COMPLETO, persona.getNombreCompleto());
		parameters.put(BeneficiosConstants.REPORTES_PARAM_NSS, persona.getNss());
		parameters.put(BeneficiosConstants.REPORTES_PARAM_RFC, persona.getRfc());
		parameters.put(BeneficiosConstants.REPORTES_PARAM_CURP, persona.getCurp());
		parameters.put(BeneficiosConstants.REPORTES_PARAM_NRPS, listaSujetosObligados);
		parameters.put(BeneficiosConstants.REPORTES_PARAM_MOSTRAR_NRPS, mostrarNrps);
		parameters.put(BeneficiosConstants.REPORTES_PARAM_FOLIO_SOLICITUD, solicitud.getNoFolioSolicitud());
		parameters.put(BeneficiosConstants.REPORTES_PARAM_FECHA_REPORTE, formatearFechaLarga(obtenerFechaSolicitud(solicitud)));
		parameters.put(BeneficiosConstants.REPORTES_PARAM_MOSTRAR_FIRMA_AUTOGRAFA, origen.equals(OrigenSolicitudEnum.VENTANILLA) 
			? BeneficiosConstants.CONSTANTE_SI : BeneficiosConstants.CONSTANTE_NO);		
		parameters.put(BeneficiosConstants.REPORTES_PARAM_CADENA_ORIGINAL, firmaElectronica.getCadenaOriginal());
		parameters.put(BeneficiosConstants.REPORTES_PARAM_SECUENCIA_NOTARIAL, firmaElectronica.getSecuenciaNotaria());
		parameters.put(BeneficiosConstants.REPORTES_PARAM_SELLO_DIGITAL, firmaElectronica.getRecibo());
		parameters.put(BeneficiosConstants.REPORTES_PARAM_NUMERO_SERIE, firmaElectronica.getSerialCertificado());
		parameters.put(BeneficiosConstants.REPORTES_PARAM_IMAGEN_QR, imagenCodeQR);		
		return parameters;
	}

	@Override
	public boolean esTramiteActivo(TramiteRiss tramiteRiss){
		boolean tramiteActivo=true;
		if(tramiteRiss != null && tramiteRiss.getEstadoTramite() != null &&
			tramiteRiss.getEstadoTramite().getIdEstadoTramitePersona() != null &&	
			(tramiteRiss.getEstadoTramite().getIdEstadoTramitePersona().intValue()
			== (EstadoTramiteEnum.CANCELADO.getCodigo().intValue())) ){
				tramiteActivo=false;
		}
		return tramiteActivo;
	}
	
	@Override
	public String obtenerTipoApartadoBeneficio(TramiteRiss tramiteRiss){
		if(tramiteRiss != null){
			if(tramiteRiss.getFisica()!=null){
				return obtenerTipoApartadoBeneficio(tramiteRiss.getRespuestaRifSat(), false);
			}else if(!CollectionUtils.isEmpty(tramiteRiss.getListaCveIdSujetosObligados())){
				return obtenerTipoApartadoBeneficio(tramiteRiss.getRespuestaRifSat(), true);
			}
		}
		return "";
	}
	
	@Override
	public String obtenerTipoApartadoBeneficio(RespuestaRifSat respuestaRifSat, boolean esPatron){
		String tipoApartadoBeneficio="";
		if(respuestaRifSat !=null){
			if(respuestaRifSat.getIndicadorC()){
				tipoApartadoBeneficio = ApartadoPersonaBeneficioEnum.APARTADO_C.getClave();
			}else{
				if(esPatron){
					tipoApartadoBeneficio = ApartadoPersonaBeneficioEnum.APARTADO_B.getClave();
				}else{
					tipoApartadoBeneficio = ApartadoPersonaBeneficioEnum.APARTADO_A.getClave();
				}
			}
		}else{
			if(esPatron){
				tipoApartadoBeneficio = ApartadoPersonaBeneficioEnum.APARTADO_B.getClave();
			}else{
				tipoApartadoBeneficio = ApartadoPersonaBeneficioEnum.APARTADO_A.getClave();
			}
		}		
		return tipoApartadoBeneficio;
	}

	@Override
	public Beneficio prepararBeneficioPatronSinValidaciones(SujetoObligado so, 
			RespuestaRifSat respuestaRifSat){
		Beneficio beneficio = new Beneficio();
		beneficio.setTipoBeneficio(new TipoBeneficio());
		beneficio.getTipoBeneficio().setIdTipoBeneficio(TipoBeneficioEnum.RIF.getClave());
		beneficio.getTipoBeneficio().setDescripcion(TipoBeneficioEnum.RIF.getDesc());
		beneficio.setTipoApartado(ApartadoPersonaBeneficioEnum.APARTADO_B.getClave());
		beneficio.setEsPatron(true);		
		beneficio.setRespuestaRifSat(respuestaRifSat);
		beneficio.setRfc(so.getFisica().getRfc());
		beneficio.setCurp(so.getFisica().getCurp());
		beneficio.setListaSujetosObligados(new ArrayList<SujetoObligado>());
		beneficio.getListaSujetosObligados().add(so);	
		return beneficio;
	}
	
	@Override
	public boolean habilitarRissPortal(Long idOrigenSolicitud, Long parametroRissPortal){
		boolean habilitaRiss=false;
		if(parametroRissPortal.equals(0L)){
			//Habilitar para TODOS los portales
			habilitaRiss=true;
		}else if(parametroRissPortal.equals(99L) 
				&& !idOrigenSolicitud.equals(OrigenSolicitudEnum.VENTANILLA.getId())){
			//Habilitar para PORTALES INTERNET (Fiel, Ciudadano, Ventanilla Unica, etc.)
			habilitaRiss=true;
		}else{
			OrigenSolicitudEnum origen = OrigenSolicitudEnum.getById(idOrigenSolicitud);
			OrigenSolicitudEnum parametro = OrigenSolicitudEnum.getById(parametroRissPortal);
			if(origen != null && parametro != null && origen.equals(parametro)){
				//Habilitar para Portal Especifico (Fiel, Ventanilla, Ciudadano, etc.)
				habilitaRiss=true;
			}
		}
		
		log.debug("VALOR ORIGEN SOLICITUD: " + idOrigenSolicitud);
		log.debug("VALOR HABILITA_RISS_PORTAL: " + parametroRissPortal);
		log.debug("PARAMETRO HABILITA RISS PORTAL: " + habilitaRiss);		
		return habilitaRiss;
	}
	
	//Metodos privados
	private List<MovimientoRissType> movimientosAltaPersonaFisica(List<DescuentoBeneficio> listaDescuentosBeneficio, 
			Fisica fisica, String tipoPersona, Date fechaActual, Date fechaAltaRif, String patronGeneral){	
		List<MovimientoRissType> listaMovimientoRissType = new ArrayList<MovimientoRissType>();
		MovimientoRissType layoutBase = new MovimientoRissType();
		datosGeneralesLayout(layoutBase, tipoPersona, fechaActual, fechaAltaRif);
		datosPersonaFisicaLayout(layoutBase, fisica);
		datosPatronGenericoLayout(layoutBase, patronGeneral);
		agregarMovimientosRiss(layoutBase, listaMovimientoRissType, listaDescuentosBeneficio, fechaAltaRif);
		return listaMovimientoRissType;
	}

	private TramiteRiss prepararTramiteRelacionPersona(TipoTramite tipoTramite,
			Date fechaActual, EstadoTramite estadoTramite, Beneficio beneficio, boolean esFisica){
		
		TramiteRiss tramiteRiss = new TramiteRiss();		
		tramiteRiss.setTipoTramite(tipoTramite);
		tramiteRiss.setFechaTramite(fechaActual);
		tramiteRiss.setFechaPresentacion(fechaActual);
		tramiteRiss.setEstadoTramite(estadoTramite);		
		//Determina si es TramitePF o TramiteSO
		if(esFisica){
			tramiteRiss.setFisica(beneficio.getFisica());
		}else{
			tramiteRiss.setListaCveIdSujetosObligados(
				getIdsSujetosObligados(beneficio.getListaSujetosObligados()));			
		}
		tramiteRiss.setListaDescuentosBeneficio(beneficio.getListaDescuentosBeneficio());
		
		if (beneficio.getFisica() != null && (StringUtils.isNotEmpty(beneficio.getFisica().getRfc())					
				 && StringUtils.isNotBlank(beneficio.getFisica().getRfc())) ) {
			tramiteRiss.setRfcSolicitud(beneficio.getFisica().getRfc()); 
		}else{
			tramiteRiss.setRfcSolicitud(beneficio.getRfc()); 
		} 
		//Almacenar respuestaRifSat y tipoApartadoCandidato si ya existen
		if(beneficio.getRespuestaRifSat()!=null){
			tramiteRiss.setRespuestaRifSat(beneficio.getRespuestaRifSat());
			tramiteRiss.setTipoApartadoCandidato(obtenerTipoApartadoBeneficio(tramiteRiss));
		}
		return tramiteRiss;
	}
	
	private boolean esDescuentoBeneficioActual(Date fechaInicio, Date fechaFin){
		Calendar calendarActual = new GregorianCalendar();
		calendarActual.setTime(new Date());
		Date fechaActual = calendarActual.getTime();
		fechaInicio = inicializarFechaInicio(fechaInicio);
		fechaFin = inicializarFechaFinal(fechaFin);
		if( (fechaInicio.compareTo(fechaActual)<=0) 
			&& (fechaFin.compareTo(fechaActual)>=0) ){
			return true;
		}		
		return false;
	}
	
	private void prepararDitDescuentoBeneficio(DitBeneficio entity, 
			List<DicDescuentoRiss> listaDescuentosRiss, Date fechaActual, 
			Date fechaSatRif, Date fechaMaximaRif){
		
		if(!CollectionUtils.isEmpty(listaDescuentosRiss)){
			List<DitDescuentoBeneficio> relacionDB = new ArrayList<DitDescuentoBeneficio>();
			//Generar TODOS los rangos de decuentos-beneficios (como maximo 10)
			Map<Integer, DitDescuentoBeneficio> mapaDescuentoBeneficio 
				= generarRangosDescuentoBeneficio(fechaActual, fechaSatRif, fechaMaximaRif);
			log.debug("Fecha Solicitud "+fechaActual);
			log.debug("Fecha Alta RIF ["+fechaSatRif+"]");
			log.debug("Fecha Maxima RIF ["+fechaMaximaRif+"]");			
			
			for (Map.Entry<Integer, DitDescuentoBeneficio> mapaEntry : mapaDescuentoBeneficio.entrySet()) {
				Integer anioMapa = mapaEntry.getKey();
				DitDescuentoBeneficio descuentoBeneficio = mapaEntry.getValue();
				//Match de los decuentos-beneficios generados VS descuentos-catalogo
				//para determinar su % de decusnto acorde al periodo/anio
				for(DicDescuentoRiss cDescuento : listaDescuentosRiss){
					if(anioMapa==cDescuento.getNumAnio()){
						//PROCESAR "descuento-beneficio" actual y todos los futuros posibles.
						if( ((descuentoBeneficio.getFecInicio().compareTo(fechaActual)<=0) && 
							(descuentoBeneficio.getFecFin().compareTo(fechaActual)>=0))
							|| (descuentoBeneficio.getFecInicio().compareTo(fechaActual)>0) &&
							(descuentoBeneficio.getFecFin().compareTo(fechaActual)>0) ){
							log.debug("PROCESANDO DescuentoBeneficio actual o futuro");
							descuentoBeneficio.setDitBeneficio(entity);
							descuentoBeneficio.setFecRegistroAlta(fechaActual);														
							descuentoBeneficio.setNumPorcentaje(cDescuento.getNumPorcentaje());		
							descuentoBeneficio.setAnioFiscal(cDescuento.getNumAnio());
							relacionDB.add(descuentoBeneficio);							
						}						
					}
				}
			}
			//Se asegura que el primer periodo inicie con fecha solicitud del beneficio.
			if(!CollectionUtils.isEmpty(relacionDB)){
				relacionDB.get(0).setFecInicio(fechaActual);
			}
			
			//Set List<DitDescuentoBeneficio> to DitBeneficio
			entity.setDitDescuentoBeneficios(relacionDB);
		}
	}
	
	private Map<Integer, DitDescuentoBeneficio> generarRangosDescuentoBeneficio(Date fechaActual, 
			Date fechaSatRif, Date fechaMaximaRif){		
		
		Date fechaInicial = fechaSatRif;			
		Map<Integer, DitDescuentoBeneficio> fechasDescuentoBeneficio 
			= new HashMap<Integer, DitDescuentoBeneficio>();		
		//Generar todos los rangos de descuento (iniciando desde fechaSatRif a fechaMaximaRif)
		for(int i=1; i<=getMaximoAniosRif(); i++){			
			//Calcula fecha final		
			Calendar calendarFinal = new GregorianCalendar();
			calendarFinal.setTime(inicializarFechaFinal(fechaInicial));
			calendarFinal.set(Calendar.MONTH, Calendar.DECEMBER);
			calendarFinal.set(Calendar.DAY_OF_MONTH, calendarFinal.getActualMaximum(Calendar.DATE));
						
			DitDescuentoBeneficio descuento = new DitDescuentoBeneficio();
			descuento.setFecInicio(fechaInicial);
			descuento.setFecFin(calendarFinal.getTime());
			fechasDescuentoBeneficio.put(i, descuento);
			
			//Prepara siguiente fecha inicial (fecha final + 1 dia)
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(inicializarFechaInicio(calendarFinal.getTime()));
			calendar.add(Calendar.DATE, 1);
			fechaInicial = calendar.getTime();
		}
		return fechasDescuentoBeneficio;
	}
	
	private Date inicializarFechaFinal(Date fechaFin){
		Calendar calendarFinal = new GregorianCalendar();
		calendarFinal.setTime(fechaFin);
		calendarFinal.set(Calendar.HOUR, 11);
		calendarFinal.set(Calendar.MINUTE, 59);
		calendarFinal.set(Calendar.SECOND, 59);
		return calendarFinal.getTime();
	}
		
	private Date inicializarFechaInicio(Date fechaInicio){
		Calendar calendarInicial = new GregorianCalendar();
		calendarInicial.setTime(fechaInicio);
		calendarInicial.set(Calendar.HOUR_OF_DAY, 0);
		calendarInicial.set(Calendar.MINUTE, 0);
		calendarInicial.set(Calendar.SECOND, 0);
		return calendarInicial.getTime();
	}
		
	private Date inicializarFechaInicioRif(Date fechaSatRif, 
			Date FechaInicioRifImss){
		//Si la fechaSatRif < FechaInicioRifImss (dic_parametros - FECHA_INICIO_RIF_IMSS)
		// tomar fecha parametrizable como fechaRif.
		Calendar fechaInicioRif = new GregorianCalendar();
		if(fechaSatRif.compareTo(FechaInicioRifImss) < 0){
			fechaSatRif = FechaInicioRifImss;
		}
		fechaInicioRif.setTime(fechaSatRif);		
		fechaInicioRif.set(Calendar.HOUR_OF_DAY, 0);
		fechaInicioRif.set(Calendar.MINUTE, 0);
		fechaInicioRif.set(Calendar.SECOND, 0);
		return fechaInicioRif.getTime();
	}
	
	private void prepararDitBeneficioRiss(DitBeneficio entity,
		Date fechaActual, Date fechaSatRif, Date fechaMaximaRif){
			
		DitBeneficioRiss beneficiosRiss = new DitBeneficioRiss();
		beneficiosRiss.setDitBeneficio(entity);
		beneficiosRiss.setFecInicioRif(fechaSatRif);
		beneficiosRiss.setFecFinRif(fechaMaximaRif);
		beneficiosRiss.setFecRegistroAlta(fechaActual);
			
		List<DitBeneficioRiss> relacionBR = new ArrayList<DitBeneficioRiss>();
		relacionBR.add(beneficiosRiss);
		entity.setDitBeneficiosRiss(relacionBR);
	}
	
	private int getMaximoAniosRif(){
		return 10;
	}

	private void datosGeneralesLayout(MovimientoRissType layoutBase, String tipoPersona, 
			Date fechaActual, Date fechaAltaRif){
		layoutBase.setTipPatPerFis(tipoPersona);
		layoutBase.setFecIniRif(fechaAltaRif!=null?fechaAltaRif:new Date());		
		layoutBase.setFecMovto(fechaActual);
	}
	
	private void datosPatronLayout(MovimientoRissType layoutBase, SujetoObligado so){
		layoutBase.setRegPatAlf(so.getNumeroRegistroPatronal());		
		layoutBase.setRegPatDv(so.getDigVerificador());
		if(so.getModalidad()!=null && so.getModalidad().getNumModalidad()!=null){
			layoutBase.setRegPatMod(Integer.parseInt(so
				.getModalidad().getNumModalidad()));
		}
	}
	
	private void datosPatronGenericoLayout(MovimientoRissType layoutBase, String patronGeneral){
		if(StringUtils.isNotEmpty(patronGeneral) &&
			StringUtils.isNotBlank(patronGeneral)){			
			layoutBase.setRegPatAlf(patronGeneral.substring(0, 8));			
			layoutBase.setRegPatMod(Integer.parseInt(patronGeneral.substring(8, 10)));
			layoutBase.setRegPatDv(patronGeneral.substring(10));			
		}
	}
	
	private void datosPersonaFisicaLayout(MovimientoRissType layoutBase, Fisica fisica){
		if(fisica!=null){
			layoutBase.setRfc(fisica.getRfc()!=null ? fisica.getRfc():"");
			layoutBase.setCurp(fisica.getCurp()!=null ? fisica.getCurp():"");
			layoutBase.setNss(fisica.getNss()!=null ? fisica.getNss():"");			
		}
	}
	
	private void agregarMovimientosRiss(MovimientoRissType layoutBase, 
			List<MovimientoRissType> listaMovimientoRissType, 
			List<DescuentoBeneficio> listaDescuentosBeneficio, Date fechaAltaRif){
		Date fechaMaximaRif = fechaFinRif10Anios(fechaAltaRif);
		layoutBase.setFecBajaRif(fechaMaximaRif);
		int i = 1;
		for(DescuentoBeneficio descuentoBeneficio : listaDescuentosBeneficio){
			MovimientoRissType layout = (MovimientoRissType)layoutBase.clone();			
			datosDescuentoBeneficioLayout(layout, descuentoBeneficio, i);
			i++;
			listaMovimientoRissType.add(layout);
		}
	}
	
	private void datosDescuentoBeneficioLayout(MovimientoRissType layoutBase, 
			DescuentoBeneficio descuentoBeneficio, int consecutivo){		
		layoutBase.setConsecutivo(consecutivo);
		layoutBase.setFecIniRiss(descuentoBeneficio.getFecInicio());
		layoutBase.setFecBajaRiss(descuentoBeneficio.getFechaFin());		
		layoutBase.setPorDescAnioFiscal(descuentoBeneficio
			.getPorcentajeDescuento().intValue());
	}
	
	private String formatearFechaLarga(Date fecha){
		String sFecha = DateUtils.dateToStringConFormato(fecha, "dd '          ' MM '          ' yyyy "); 
		if(sFecha!=null && StringUtils.isNotBlank(sFecha)){
			//Fecha Larga (Diciembre 31 de 2001, 20:30:11) y se remplaza primer caracter a mayusculas
			char letraInicial = sFecha.trim().charAt(0);
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
