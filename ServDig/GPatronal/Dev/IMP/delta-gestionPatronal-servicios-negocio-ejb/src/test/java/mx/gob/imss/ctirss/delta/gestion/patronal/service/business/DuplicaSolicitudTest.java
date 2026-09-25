package mx.gob.imss.ctirss.delta.gestion.patronal.service.business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.gestion.patronal.service.EjbLocator;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Bien;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EquipoTransporte;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MaquinariaEquipo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MateriaPrima;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Personal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Proceso;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Producto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSociedad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;


public class DuplicaSolicitudTest {
	
	
	private static final Logger log;

	static {
		log = LoggerFactory.getLogger(DuplicaSolicitudTest.class);
	}
	
	@Test
	public void duplicaSolicitudClasTest(){

		String[] solicutudesADuplicar = { "74885510|" + TipoTramiteEnum.SUSTITUCION_PATRONAL.getCodigo().intValue()
				+ "|" + TipoTramiteEnum.SUSTITUCION_PATRONAL_SUBCONTRATACION.getCodigo().intValue() + "|0" 
		};
		
		log.debug("::: Inicia recorrido ");
		
		for (int i = 0; i < solicutudesADuplicar.length; i++) {
			String[] val = solicutudesADuplicar[i].split("\\|");
			log.debug("::: Posicion: " + (i+1) + " de " + solicutudesADuplicar.length);
			TipoPersonaFiscal tipoPersonaFiscal = null;
			if(val[3].equals("0")) {
				tipoPersonaFiscal = TipoPersonaFiscal.MORAL; 
			}else {
				tipoPersonaFiscal = TipoPersonaFiscal.FISICA;
			}
			clonaSolicitudClasificacion(val[0], val[1], val[2], tipoPersonaFiscal);
		}

		log.debug("::: Termino recorrido ");
		
	}
		
	public void clonaSolicitudClasificacion(String idSol, String tramiteOrigen, String tramiteDestino, TipoPersonaFiscal tipoPersonaFiscal){
		
		log.debug(":::: Obteniendo instancia del EJB");
		SolicitudServiceBusinessRemote solicitudService = EjbLocator.getSolicitudServiceBusiness();
		
		try {
			log.debug(":::: Clonando solicitud idSol: " + idSol + " de tramite " + tramiteOrigen
					+ " a nuevo tipo de tramite: " + tramiteDestino);
			//Creamos la nueva solicitud del tipo de tramite destino
			TipoSolicitudEnum tipoSolicitud = TipoSolicitudEnum.ACTUALIZACION_DE_CLASIFICACION;
			Usuario usuario = new Usuario() ;
			TipoTramiteEnum tipoTramite = TipoTramiteEnum.obternerEnumById(new Integer(tramiteDestino));
			
			log.debug(":::: Consultando datos de la solicitud idSol: " + idSol);	
			Solicitud solicitudAlta = solicitudService.consultarSolicitudPorId(new Long(idSol));
			
			//Obtenemos el tramite a clonar
			SujetoObligado sujetoTramiteOrigen = getSO(solicitudAlta, tramiteOrigen);
			//Creamos SO auxiliar
			SujetoObligado soAux = new SujetoObligado();
			soAux.setClasificacion(new Clasificacion());
			soAux.setCuentaConTransporte(sujetoTramiteOrigen.getCuentaConTransporte());
			soAux.setCveIdSujetoObligado(sujetoTramiteOrigen.getCveIdSujetoObligado());
			soAux.setFiltroPorIdPersona(sujetoTramiteOrigen.getFiltroPorIdPersona());
			soAux.setObtenerRPsConBaja(sujetoTramiteOrigen.getObtenerRPsConBaja());
			soAux.setProceso(new Proceso());
			
			//Adecuamos el sujeto obligado para crear nueva solicitud conservando los datos necesarios
			sujetoTramiteOrigen.setCntroTrabajo(null);
			sujetoTramiteOrigen.getClasificacion().setAuditoria(false);
			sujetoTramiteOrigen.getClasificacion().setSujetoObligado(soAux);
			
			sujetoTramiteOrigen.setEquipos(new ArrayList<MaquinariaEquipo>());
			List<MaquinariaEquipo> lme = sujetoTramiteOrigen.getEquipos();
			for (Iterator<MaquinariaEquipo> iterator = lme.iterator(); iterator.hasNext();) {
				MaquinariaEquipo maquinariaEquipo = iterator.next();
				maquinariaEquipo.setSujetoObligado(soAux);
			}			
			
			if(tipoPersonaFiscal.getCodigo().intValue() == TipoPersona.TIPO_PERSONA_MORAL.intValue()) {
				final Moral moralAux = sujetoTramiteOrigen.getMoral();
				sujetoTramiteOrigen.setMoral(new Moral());
				sujetoTramiteOrigen.getMoral().setIdPersona(moralAux.getIdPersona());
				sujetoTramiteOrigen.getMoral().setRfc(moralAux.getRfc());
				sujetoTramiteOrigen.getMoral().setTipoSociedad(new TipoSociedad());
			}else {
				final Fisica fisAux = sujetoTramiteOrigen.getFisica();
				sujetoTramiteOrigen.setFisica(new Fisica());
				sujetoTramiteOrigen.getFisica().setIdPersona(fisAux.getIdPersona());
				sujetoTramiteOrigen.getFisica().setRfc(fisAux.getRfc());
				sujetoTramiteOrigen.getFisica().setLugarNacimiento(new EntidadFederativa());
				sujetoTramiteOrigen.getFisica().setSexo(new Sexo());				
			}
			
			//sujetoTramiteOrigen.setMateriaPrimaMateriales(new ArrayList<MateriaPrima>());
			List<MateriaPrima> lmp = sujetoTramiteOrigen.getMateriaPrimaMateriales();
			for (Iterator<MateriaPrima> iterator = lmp.iterator(); iterator.hasNext();) {
				MateriaPrima materiaPrima = iterator.next();
				materiaPrima.setSujetoObligado(soAux);				
			}			
			
			sujetoTramiteOrigen.setPersonal(new ArrayList<Personal>());
			List<Personal> lpe = sujetoTramiteOrigen.getPersonal();
			for (Iterator<Personal> iterator = lpe.iterator(); iterator.hasNext();) {
				 Personal personal = iterator.next();
				 personal.setSujetoObligado(soAux);
			}
			
			sujetoTramiteOrigen.getProceso().setSujetoObligado(soAux);
						
			List<Producto> lpr = sujetoTramiteOrigen.getProductos();
			for (Iterator<Producto> iterator = lpr.iterator(); iterator.hasNext();) {
				Producto producto = iterator.next();
				producto.setSujetoObligado(soAux);
			}
			
			log.debug("::: Generando nueva solicitud");
			//Generamos la solicitud con el tramite
			Solicitud sol = solicitudService.generarSolicitud(
					tipoSolicitud,
					EstadoSolicitudEnum.REGISTRADA, usuario,
					tipoTramite,
					EstadoTramiteEnum.INICIADO,
					sujetoTramiteOrigen, false, false);
			
			log.debug("::: Se ha creado la solicitud: " + sol.getSolicitudId() + ", con folio: " + sol.getNoFolioSolicitud());
			log.debug("::: Se consulta solicitud para comprobar que se creo con exito");

			Solicitud solicitud = solicitudService.consultarSolicitudPorId(sol.getSolicitudId());
			log.debug("::: Se imprime la solicitud");
			log.debug(solicitud.toString());
			log.debug("::: Actualizando solicitud");
			solicitudService.actualizarSolicitud(solicitud, EstadoTramiteEnum.INICIADO, sujetoTramiteOrigen);			
			log.debug("::: Solicitud actualizada");			
//			inicializa(sujetoTramiteOrigen, sol.getSolicitudId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		log.debug("::: TERMINE");
	}	
	
	private void inicializa(SujetoObligado sujetoObligado, Long idSolicitud) {
		log.debug("ENTRANDO AL METODO INICIO DE CLASIFICACIONCONTROLER");

		SujetoObligadoServiceBusinessRemote sujetoObligadoService = EjbLocator.getSujetoObligadoServiceBusiness();
		SolicitudServiceBusinessRemote solicitudService = EjbLocator.getSolicitudServiceBusiness();


		sujetoObligado = sujetoObligadoService.obtenerDetalleSujetoObligadoActividadEconomica(sujetoObligado);
		SujetoObligado sujetoTramite = new SujetoObligado();

		Solicitud sol = solicitudService.consultarSolicitudPorId(idSolicitud);
		TramiteSujetoObligado tso = (TramiteSujetoObligado) sol.getTramites().get(0);
		sujetoTramite = tso.getSujetoObligado();

		if (sol.getEstadoSolicitud().getIdEstadoSolicitud().intValue()
				== EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo().intValue()) {
			sujetoTramite.getClasificacion().setFecPresentacion(Calendar.getInstance().getTime());
		}

		log.debug("SUJETO TRAMITE: [" + sujetoTramite + "]");
		sujetoTramite = inicializaSujetoObligadoPresentacion(sujetoTramite);

		sujetoTramite.getClasificacion().setFecPresentacion(Calendar.getInstance().getTime());
		
	}		

	private SujetoObligado inicializaSujetoObligadoPresentacion(
			SujetoObligado so) {
		if (so.getBienes() == null) {
			so.setBienes(new ArrayList<Bien>());
		}
		if (so.getEquipos() == null) {
			so.setEquipos(new ArrayList<MaquinariaEquipo>());
		}
		if (so.getEquiposTransporte() == null) {
			so.setEquiposTransporte(new ArrayList<EquipoTransporte>());
		}
		if (so.getMateriaPrimaMateriales() == null) {
			so.setMateriaPrimaMateriales(new ArrayList<MateriaPrima>());
		}
		if (so.getPersonal() == null) {
			so.setPersonal(new ArrayList<Personal>());
		}
		if (so.getProductos() == null) {
			so.setProductos(new ArrayList<Producto>());
		}

		return so;
	}	
	
	private SujetoObligado getSO(Solicitud sol, String tipoTramite){
		Tramite tramite = null;
		
		for(Tramite t : sol.getTramites()){
			if(t.getTipoTramite().getIdTipoTramite().toString().equals(tipoTramite)){
				tramite = t;
				log.debug("Id-Tramite - "
						+ sol.getSolicitudId()
						+ "|"
						+ tramite.getTramiteId()
						+ " - "
						+ sol.getEstadoSolicitud().getIdEstadoSolicitud()
						+ " - "
						+ sol.getEstadoSolicitud().getDescripcion()
						+ " - "
						+ tramite.getEstadoTramite()
								.getIdEstadoTramitePersona() + "-"
						+ tramite.getEstadoTramite().getDescripcion());
				break;
			}
		}
		
		SujetoObligado so = ((TramiteSujetoObligado)tramite).getSujetoObligado();
		
		return so;
	}

}
