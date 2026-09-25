package mx.gob.imss.ctirss.delta.gestion.patronal.service.business;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.gestion.patronal.service.EjbLocator;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.test.EJBLocator;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoVialidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Vialidad;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Bien;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CentroTrabajo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EquipoTransporte;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MaquinariaEquipo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MateriaPrima;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
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


public class CreaSolicitudTest {
	
	
	private static final Logger log;

	static {
		log = LoggerFactory.getLogger(CreaSolicitudTest.class);
	}
	
	@Test
	public void obtenerDetalleRP(){
		SujetoObligado so = new SujetoObligado();
		String nrp = "Z3241639106";
		// REING Z3241639106, E2934137100
		// BDTU E3528545104
		so.setNumeroRegistroPatronal(nrp); 
//		so.setNumeroRegistroPatronal(nrp.substring(0,11)); 
//		Modalidad mod = new Modalidad();
//		mod.setNumModalidad(nrp.substring(9,10));
//		so.setModalidad(mod);
//		so.setDigVerificador(nrp.substring(11,11));
		so.setTipoPersonaFiscal(TipoPersonaFiscal.FISICA);
		System.out.println("::: Obteniendo EJB");
		so = EJBLocator.getSujetoServiceBusiness().obtenerDetalleSujetoObligadoActividadEconomica(so);
		System.out.println("::: Resultado");
		System.err.println(so);
	}
	
	
	
//	@Test
	public void creaSolicitudTest(){
		String[] solicutudesADuplicar = { "Y4625044104|" +  TipoSolicitudEnum.ACTUALIZACION_DE_CLASIFICACION.getValor().intValue()
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
			
			creaSolicitud(val[0], val[1], val[2], tipoPersonaFiscal);
		}
		log.debug("::: Termino recorrido ");
	}
		
	public void creaSolicitud(String nrp, String tipoSol, String tipoTram, TipoPersonaFiscal tipoPersonaFiscal){
		
		SolicitudServiceBusinessRemote solicitudService = EjbLocator.getSolicitudServiceBusiness();
		SujetoObligadoServiceBusinessRemote sujetoObligadoService = EjbLocator.getSujetoObligadoServiceBusiness();
		
		try {
			log.debug(":::: Creando solicitud tipoSol: " + tipoSol + ", con tipo de tramite " + tipoTram);
			log.debug(":: Numero Registro Patronal para generar solicitud: " + nrp);
			
			Usuario usuario = new Usuario() ;
			//Creamos la nueva solicitud del tipo de tramite destino
			TipoSolicitudEnum tipoSolicitud = TipoSolicitudEnum.obtenerEnumById(new Integer(tipoSol));
			TipoTramiteEnum tipoTramite = TipoTramiteEnum.obternerEnumById(new Integer(tipoTram));						
			SujetoObligado sujetoTramite = new SujetoObligado();
			sujetoTramite.setNumeroRegistroPatronal(nrp);
			sujetoTramite.setDigVerificador(nrp.substring(nrp.length()));
			Modalidad m = new Modalidad();
			m.setNumModalidad(nrp.substring(9,10));
			sujetoTramite.setModalidad(m);
			sujetoTramite.setTipoPersonaFiscal(tipoPersonaFiscal);
			
			sujetoTramite = sujetoObligadoService.obtenerDetalleSujetoObligadoActividadEconomica(sujetoTramite);
			sujetoTramite.getClasificacion().setSujetoObligado(sujetoTramite);			
			sujetoTramite = inicializaInformacionActividadEconomica(sujetoTramite);
			
			log.debug("::: Buscando si el patron tiene tramites pendientes, sujetoTramite.getCveIdSujetoObligado(): "
					+ sujetoTramite.getCveIdSujetoObligado());
			
			boolean tramiteClasifExistente = solicitudService.existeTramitesClasificacionActivos(
					sujetoTramite.getCveIdSujetoObligado(),true);
			
			if(tramiteClasifExistente){
				System.err.println("::: EL patron " + nrp + ", ya tiene un tramite son terminar o cancelar");
			}else {
				log.debug("::: El patron NO tiene tramites pendientes, sujetoTramite.getCveIdSujetoObligado(): "
						+ sujetoTramite.getCveIdSujetoObligado());
											
				//Creamos SO auxiliar
				SujetoObligado soAux = new SujetoObligado();
				soAux.setClasificacion(new Clasificacion());
				soAux.setCuentaConTransporte(sujetoTramite.getCuentaConTransporte());
				soAux.setCveIdSujetoObligado(sujetoTramite.getCveIdSujetoObligado());
				soAux.setFiltroPorIdPersona(sujetoTramite.getFiltroPorIdPersona());
				soAux.setObtenerRPsConBaja(sujetoTramite.getObtenerRPsConBaja());
				soAux.setProceso(new Proceso());
				
				//Adecuamos el sujeto obligado para crear nueva solicitud conservando los datos necesarios
				sujetoTramite.setCntroTrabajo(null);
				sujetoTramite.getClasificacion().setAuditoria(false);
				sujetoTramite.getClasificacion().setSujetoObligado(soAux);
				
				List<MaquinariaEquipo> lme = sujetoTramite.getEquipos();
				for (Iterator<MaquinariaEquipo> iterator = lme.iterator(); iterator.hasNext();) {
					MaquinariaEquipo maquinariaEquipo = iterator.next();
					maquinariaEquipo.setSujetoObligado(soAux);
				}			
				
				if(tipoPersonaFiscal.getCodigo().intValue() == TipoPersona.TIPO_PERSONA_MORAL.intValue()) {
					log.debug("::: Set de persona MORAL");
					final Moral moralAux = sujetoTramite.getMoral();
					sujetoTramite.setMoral(new Moral());
					sujetoTramite.getMoral().setIdPersona(moralAux.getIdPersona());
					sujetoTramite.getMoral().setRfc(moralAux.getRfc());
					sujetoTramite.getMoral().setTipoSociedad(new TipoSociedad());
				}else{
					log.debug("::: Set de persona FISICA");
					final Fisica fisAux = sujetoTramite.getFisica();
					sujetoTramite.setFisica(new Fisica());
					sujetoTramite.getFisica().setIdPersona(fisAux.getIdPersona());
					sujetoTramite.getFisica().setRfc(fisAux.getRfc());
					sujetoTramite.getFisica().setLugarNacimiento(new EntidadFederativa());
					sujetoTramite.getFisica().setSexo(new Sexo());					
				}
				
				List<MateriaPrima> lmp = sujetoTramite.getMateriaPrimaMateriales();
				for (Iterator<MateriaPrima> iterator = lmp.iterator(); iterator.hasNext();) {
					MateriaPrima materiaPrima = iterator.next();
					materiaPrima.setSujetoObligado(soAux);				
				}			
				
				List<Personal> lpe = sujetoTramite.getPersonal();
				for (Iterator<Personal> iterator = lpe.iterator(); iterator.hasNext();) {
					 Personal personal = iterator.next();
					 personal.setSujetoObligado(soAux);
				}
				
				sujetoTramite.getProceso().setSujetoObligado(soAux);
							
				List<Producto> lpr = sujetoTramite.getProductos();
				for (Iterator<Producto> iterator = lpr.iterator(); iterator.hasNext();) {
					Producto producto = iterator.next();
					producto.setSujetoObligado(soAux);
				}								
				
				Solicitud sol = solicitudService.generarSolicitud(
						tipoSolicitud,
						EstadoSolicitudEnum.REGISTRADA, usuario,
						tipoTramite,
						EstadoTramiteEnum.INICIADO,
						sujetoTramite, false, false);
				log.debug("::: Se ha creado la solicitud: " + sol.getSolicitudId() + ", clave: " + sol.getSolicitudId()
						+ ", folio: " + sol.getNoFolioSolicitud());				
				log.debug("::: Se consulta solicitud para comprobar que se creo con exito");
				Solicitud solicitud = solicitudService.consultarSolicitudPorId(sol.getSolicitudId());
				log.debug("::: Se imprime la solicitud");
				log.debug(solicitud.toString());	
				log.debug("::: Actualizando solicitud");
				
//				solicitudService.actualizarSolicitud(solicitud, EstadoTramiteEnum.INICIADO, sujetoTramite);
//				log.debug("::: Solicitud actualizada");
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		log.debug("::: TERMINE");
	}		
	
	private SujetoObligado inicializaInformacionActividadEconomica(SujetoObligado sujetoTramite) {
		sujetoTramite.setBienes(new ArrayList<Bien>());
		sujetoTramite.setEquipos(new ArrayList<MaquinariaEquipo>());
		sujetoTramite.setEquiposTransporte(new ArrayList<EquipoTransporte>());
		sujetoTramite.setMateriaPrimaMateriales(new ArrayList<MateriaPrima>());
		sujetoTramite.setPersonal(new ArrayList<Personal>());
		sujetoTramite.setProductos(new ArrayList<Producto>());
		sujetoTramite.setCntroTrabajo(crearCentroTrabajoVacio());
		return sujetoTramite;
	}	
	
	private CentroTrabajo crearCentroTrabajoVacio(){
		CentroTrabajo ct=new CentroTrabajo();
		ct.setAsentamiento(crearAsentamientoVacio());
		ct.setCodigoPostal(new CodigoPostal());
		ct.setVialidadPrimaria(crearVialidadVacia());
		ct.setVialidadReferenciaPrimaria(crearVialidadVacia());
		ct.setVialidadReferenciaSecundaria(crearVialidadVacia());
		ct.setVialidadReferenciaPosterior(crearVialidadVacia());
		return ct;
	}
	
	private Asentamiento crearAsentamientoVacio(){
		Asentamiento asent = new Asentamiento();
		asent.setLocalidad(new Localidad());
		asent.getLocalidad().setMunicipio(new Municipio());
		asent.getLocalidad().getMunicipio().setEntidadFederativa(new EntidadFederativa());
		return asent;
	}
	
	private Vialidad crearVialidadVacia(){
		Vialidad vialidad = new Vialidad();
		vialidad.setTipoVialidad(new TipoVialidad());
		return vialidad;
	}	
	

}
