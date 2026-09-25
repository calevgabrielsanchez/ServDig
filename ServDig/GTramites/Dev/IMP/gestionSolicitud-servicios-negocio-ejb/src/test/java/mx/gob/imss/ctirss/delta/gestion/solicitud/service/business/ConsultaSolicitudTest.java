package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.imss.ctirss.delta.framework.base.model.AttachmentContent;
import mx.gob.imss.ctirss.delta.framework.base.model.EmailPayloadType;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.clasificacion.actividad.economica.ActividadEcServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.integration.EMailProducer;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.ConcluirAltaPatronalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.IDSEQueueProducerRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.test.EjbLocator;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.utility.FechaUtils;
import mx.gob.imss.ctirss.delta.global.model.CalificacionTO;
import mx.gob.imss.ctirss.delta.global.model.MensajeTO;
import mx.gob.imss.ctirss.delta.global.model.PersonaTO;
import mx.gob.imss.ctirss.delta.global.model.SolicitudTO;
import mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FiltroSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAsegurado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRiss;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaGlobalBusinessRemote;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsultaSolicitudTest {
	private static final Logger LOG;
	private transient String folioSolicitud;
	private transient final SolicitudBusinessRemote solicitudBusiness = EjbLocator.getSolicitudBusiness();
	private transient final ConcluirAltaPatronalBusinessRemote concluirAltaPatronalBusinessRemote = EjbLocator.getConcluirAltaPatronalBusiness();
	private transient final ActividadEcServiceRemote clasificacionActividadEconomicaServiceBusinessRemote = EjbLocator.getClasificacionActividadEconomicaServiceBusinessRemote();

	static {
		LOG = LoggerFactory.getLogger(ConsultaSolicitudTest.class);
	}

	@Before
	public void setUp() {
		//folioSolicitud = "13958766445199427";
		//folioSolicitud = "1394210933626574754";
//		folioSolicitud = "1395339303412712964";
		//folioSolicitud = "1395395977417720838";
		//folioSolicitud = "13975988017931009680";
	//	folioSolicitud = "1397572097754999766";
		
//		folioSolicitud = "1394743966355646976";
//		folioSolicitud = "1395969383662801119";
//		folioSolicitud = "13981944693441056589";
//		folioSolicitud = "13981954986171056992";
		
		folioSolicitud = "13988136794549727";
		
		
	}
	
	
	@Test
	public void consultarDatosGenerales(){
		try {
			SolicitudTO sol = EjbLocator.getDeltaSolicitudBusiness().consultarDatosGenerales(folioSolicitud);
			System.err.println(sol);
		} catch (SolicitudNoEncontradaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void consultarFolioTest() {
		Solicitud solicitud = new Solicitud();
		solicitud.setNoFolioSolicitud(folioSolicitud);

		try {
			Solicitud solicitudFound = solicitudBusiness
					.consultarFolio(solicitud);
			LOG.debug(solicitudFound.toString());
		} catch (SolicitudNoEncontradaException e) {
			LOG.error(e.getMessage());
		}
	}
	
	@Test
	public void consultaVisorSolicitudesTest() {
		try {
			Date fechaSistema = new Date();
			Date fechaInicioPresentacion = FechaUtils.sumaHoras(fechaSistema, -24);
			Date fechaFinPresentacion = FechaUtils.sumaHoras(fechaSistema, 24);
			System.out.println("Fecha a partir de la cual se realizar el corte : \t" + fechaInicioPresentacion);

			// Se determina filtro de consulta
			FiltroSolicitud filtros = new FiltroSolicitud();
			filtros.setTramiteId(TipoTramiteEnum.ASIGNACION_NSS.getCodigo().longValue());
			filtros.setIdEstadoSolicitud(EstadoSolicitudEnum.ATENDIDA.getCodigo().longValue());
			filtros.setIndFechaPresentacionExacta(true);
			//filtros.setIndFechaConclusionExacta(true);
			filtros.setFechaInicioPresentacion(fechaInicioPresentacion);
			filtros.setFechaFinPresentacion(fechaFinPresentacion);
			filtros.setFechaInicioConclusion(fechaInicioPresentacion);
			filtros.setFechaFinConclusion(fechaFinPresentacion);
			filtros.setRp("");

			// Se indica consulta ultima solicitud
			DatosEntradaPaginador<Solicitud> input = new DatosEntradaPaginador<Solicitud>();
			input.setiDisplayStart(0);
			input.setiDisplayLength(1);

			DatosSalidaPaginador<Solicitud> outputSolicitudes = solicitudBusiness
					.listarSolicitudesPorFiltro(input, filtros, false);
			if (outputSolicitudes != null) {
				Solicitud ultimaSolicitud = outputSolicitudes.getAaData().get(0);
				int totalSolicitudes = outputSolicitudes.getiTotalRecords();

				System.out.println("Ultima Solicitud" + ultimaSolicitud.getNoFolioSolicitud());
				System.out.println("Solicitudes encontradas" + totalSolicitudes);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
	}
	
	@Test
	public void generaEMailMessage(){
		
		Long idSolicitud = 5892L;//5364l se puede usar esta tambien
		TipoDocumentoTramiteEnum tipoDocumento = TipoDocumentoTramiteEnum.COMPROBANTE;
		EMailProducer service = EjbLocator.getEMailQProducer();
		EmailPayloadType data = new EmailPayloadType();
		data.setTo("ignacio.espinosav@imss.gob.mx");
		data.setContent("Notificación Trámite conlcuído (Comprobantes)");
		data.setSubject("Portal digital IMSS - Solicitud Atendida");
		data.setContentType("text/plain");
		
		
		Map<String,String> parametros = new HashMap<String, String>();
		parametros.put("nombre", "HUGO");
		parametros.put("primerApellido", "MARTINEZ");
		parametros.put("segundoApellido", "CHAMONICA");
		data.setParameters(parametros);
		
		
		service.agendarCorreoElectronico(data);
		
	}
	
	@Test
	public void ejecutaICA(){
		PersonaGlobalBusinessRemote service = EjbLocator.getPersonaGlobalHandler();
		PersonaTO persona = new PersonaTO();
		persona.setIdPersona(37490878l);
		persona.setTipoPersona(new TipoPersona());
		persona.getTipoPersona().setIdTipoPersona(1l);
		persona.setRfc("MACH801112CEA");
		persona.setCurp("MACH801112HDFRHG01");
		persona=service.evaluarPersonaConInstanciaExternas(persona);
		System.err.println("Persona: "+persona);
		for(MensajeTO mensaje:persona.getMensajes().getMensaje()){
			System.err.println("Mensaje: "+mensaje.getCodigo()+" "+mensaje.getDescripcion());
		}
		for(CalificacionTO calificacion:persona.getCalificaciones().getCalificacion()){
			System.err.println("Calificacion: "+calificacion.getIdCalificacion()+" "+calificacion.getDescripcion()+" "+calificacion.getFechaCalificacion());
		}
	}
	
	
	@Test
	public void generarMensajeIDSE(){
		try {
			SolicitudTO sol = EjbLocator.getDeltaSolicitudBusiness().consultarDatosGenerales(folioSolicitud);
			System.err.println(sol);
			String claveSerial = sol.getCertificado().getClaveSerial();
			String nombreCompleto=sol.getCertificado().getNombreCompleto();
			Integer estatusFiel=sol.getCertificado().getEstatusFiel();
			String correoElectronico = sol.getCertificado().getCorreoElectronico();
			String curpFiel = sol.getCertificado().getCurpFiel();
			String nombreUsuario = sol.getCertificado().getRfcAsociado();
			String rfcAsociado = sol.getCertificado().getRfcAsociado();
			Date fechaValidaInicio = sol.getCertificado().getFechaValidaInicio(); //YYYY-MM-DDTHH:MM:SS
			Date fechaValidaFin = sol.getCertificado().getFechaValidaFin();
			String telefono = sol.getCertificado().getTelefono();
			String idRol = sol.getCertificado().getIdRol().toString();
			String claveDelegacion = sol.getRegistroPatronal().getSubdelegacion().getDelegacion().getClave();
			String claveSubdelegacion = sol.getRegistroPatronal().getSubdelegacion().getClave();
			String nrp = sol.getRegistroPatronal().getNumeroRegistro().substring(0, 10);
			String digVer = sol.getRegistroPatronal().getNumeroRegistro().substring(10, 11);
			String razonSocial = sol.getPersona().getRazonSocial();
			String cveMunicipio = sol.getRegistroPatronal().getCveMunicipioImss();
			String cveSector = sol.getRegistroPatronal().getSector().toString();
			String rfc = sol.getPersona().getRfc();
			String domicilioCompleto= sol.getPersona().getDomicilioFiscalCompleto();
			String localidad = sol.getPersona().getEntidadFederativa();
			String actividad = sol.getRegistroPatronal().getClasificacion().getFraccion().getDescripcionDetallada().substring(0, 40);
			String fraccion = sol.getRegistroPatronal().getClasificacion().getFraccion().getGrupo().getDivision().getNumDivision()+
					sol.getRegistroPatronal().getClasificacion().getFraccion().getGrupo().getNumGrupo() +
					sol.getRegistroPatronal().getClasificacion().getFraccion().getNumFraccion();
			Long clase = sol.getRegistroPatronal().getClasificacion().getFraccion().getClase().getClave();
			String email=sol.getPersona().getCorreoDeNotificaciones();
			String representanteLegal = sol.getPersona().getRazonSocial();
			Date fechaRecepcion = Calendar.getInstance().getTime();
			Date fechaActivacion = fechaRecepcion;
			String tipoPersona = sol.getPersona().getTipoPersona().getIdTipoPersona().toString();
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd'T'HH:MM:ss");
			StringBuffer msgIdse = new StringBuffer();
			msgIdse.append("<mx:requestIdse xmlns:mx='http://mx.gob.imss.delta.global.service/'>").append("\n");
			msgIdse.append("		<mx:pkcs7Bean>").append("\n");
			msgIdse.append("			<mx:ClaveSerial>").append(claveSerial).append("</mx:ClaveSerial>").append("\n");
			msgIdse.append("			<mx:NombreCompleto>").append(nombreCompleto).append("</mx:NombreCompleto>").append("\n");
			msgIdse.append("			<mx:EstatusFiel>").append(estatusFiel).append("</mx:EstatusFiel>").append("\n");
			msgIdse.append("			<mx:CorreoElectronico>").append(correoElectronico).append("</mx:CorreoElectronico>").append("\n");
			msgIdse.append("			<mx:CurpFiel>").append(curpFiel).append("</mx:CurpFiel>").append("\n");
			msgIdse.append("			<mx:NombreUsuario>").append(nombreUsuario).append("</mx:NombreUsuario>").append("\n");
			msgIdse.append("			<mx:RfcAsociado>").append(rfcAsociado).append("</mx:RfcAsociado>").append("\n");
			msgIdse.append("			<mx:FechaValidaInicio>").append(sdf.format(fechaValidaInicio)).append("</mx:FechaValidaInicio>").append("\n");
			msgIdse.append("			<mx:FechaValidaFin>").append(sdf.format(fechaValidaFin)).append("</mx:FechaValidaFin>").append("\n");
			msgIdse.append("			<mx:Telefono>").append(telefono).append("</mx:Telefono>").append("\n");
			msgIdse.append("			<mx:IdRol>").append(idRol).append("</mx:IdRol>").append("\n");
			msgIdse.append("		</mx:pkcs7Bean>").append("\n");
			msgIdse.append("	<mx:ClaveDelegacion>").append(claveDelegacion).append("</mx:ClaveDelegacion>").append("\n");
			msgIdse.append("	<mx:ClaveSubdelegacion>").append(claveSubdelegacion).append("</mx:ClaveSubdelegacion>").append("\n");
			msgIdse.append("	<mx:RegistroPatronal>").append(nrp).append("</mx:RegistroPatronal>").append("\n");
			msgIdse.append("	<mx:DigitoVerificador>").append(digVer).append("</mx:DigitoVerificador>").append("\n");
			msgIdse.append("	<mx:RazonSocial>").append(razonSocial).append("</mx:RazonSocial>").append("\n");
			msgIdse.append("	<mx:ClaveMunicipio>").append(cveMunicipio).append("</mx:ClaveMunicipio>").append("\n");
			msgIdse.append("	<mx:ClaveSector>").append(cveSector).append("</mx:ClaveSector>").append("\n");
    		msgIdse.append("	<mx:RFC>").append(rfc).append("</mx:RFC>").append("\n");
    		msgIdse.append("	<mx:DomicilioCompleto>").append(domicilioCompleto).append("</mx:DomicilioCompleto>").append("\n");
    		msgIdse.append("	<mx:Localidad>").append(localidad).append("</mx:Localidad>").append("\n");
    		msgIdse.append("	<mx:Actividad>").append(actividad).append("</mx:Actividad>").append("\n");
    		msgIdse.append("	<mx:Fraccion>").append(fraccion).append("</mx:Fraccion>").append("\n");
    		msgIdse.append("	<mx:Clase>").append(clase).append("</mx:Clase>").append("\n");
    		msgIdse.append("	<mx:EMail>").append(email).append("</mx:EMail>").append("\n");
    		msgIdse.append("	<mx:RepresentanteLegal>").append(representanteLegal).append("</mx:RepresentanteLegal>").append("\n");
    		msgIdse.append("	<mx:FechaRecepcion>").append(sdf.format(fechaRecepcion)).append("</mx:FechaRecepcion>").append("\n");
    		msgIdse.append("	<mx:FechaActivacion>").append(sdf.format(fechaActivacion)).append("</mx:FechaActivacion>").append("\n");
    		msgIdse.append("	<mx:TipoPersona>").append(tipoPersona).append("</mx:TipoPersona>").append("\n");
    		msgIdse.append("</mx:requestIdse>");
					
			System.err.println("XML IDSE: "+msgIdse.toString());
			
		} catch (SolicitudNoEncontradaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void encloarMensajeIDSE(){
		List<String> folios = new ArrayList<String>();
		folios.add("13981944693441056589");
		for(String folio:folios){
			//IDSEQueueProducerRemote idseService = EjbLocator.getIDSEProducer();
			//idseService.encolarMensajeIDSE(folio);			
		}
	}
	
	@Test
	public void generarTramaAltaPatronal(){
		List<String> folios = new ArrayList<String>();
		
		folios.add("14090106297442987736");
		folios.add("14091989523513053276");
		folios.add("1396977852416928554");
		folios.add("14093384077203098065");
		folios.add("14093362059823096211");
		folios.add("14093433672263101010");
		folios.add("14093436685693101163");
		folios.add("14094630585913114702");
		folios.add("14092781124743080268");

		
		Solicitud solicitud = null;
		TramiteSujetoObligado tramiteSujetoObligado = null;
		String xml = null;
		SujetoObligado sujetoObligado = null;
		
		for (int i = 0; i < folios.size(); i++) {
			solicitud = new Solicitud();
			solicitud.setNoFolioSolicitud(folios.get(i));
			try {
				solicitud = this.solicitudBusiness.consultarFolio(solicitud);

				for (Tramite tramite : solicitud.getTramites()) {
					if (tramite instanceof TramiteSujetoObligado) {
						tramiteSujetoObligado = (TramiteSujetoObligado) tramite;
						System.out.println("Se encontro tramiteAsegurado con id -> "
								+ tramiteSujetoObligado.getTramiteId());
						
						sujetoObligado = tramiteSujetoObligado.getSujetoObligado();
					}
				}

				concluirAltaPatronalBusinessRemote.concluirAltaPatronal(sujetoObligado.getNumeroRegistroPatronal(), solicitud);
				
				System.out.println("=============Movimiento=============");
				System.out.println(xml);

			} catch (SolicitudNoEncontradaException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	@Test
	public void generarTramaModificacionSRT(){
		List<String> folios = new ArrayList<String>();
		
		folios.add("13988177268001141854");
		folios.add("1395259781722700816");
		folios.add("1394567392433615299");

		
		Solicitud solicitud = null;
		TramiteSujetoObligado tramiteSujetoObligado = null;
		String xml = null;
		SujetoObligado sujetoObligado = null;
		
		for (int i = 0; i < folios.size(); i++) {
			solicitud = new Solicitud();
			solicitud.setNoFolioSolicitud(folios.get(i));
			try {
				solicitud = this.solicitudBusiness.consultarFolio(solicitud);

				for (Tramite tramite : solicitud.getTramites()) {
					if (tramite instanceof TramiteSujetoObligado) {
						tramiteSujetoObligado = (TramiteSujetoObligado) tramite;
						//if(tramiteSujetoObligado.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.RECTIFICACION_DE_LA_CLASIFICACION_POR_PROCESO_DE_ANALISIS.getCodigo().intValue())){}
						System.out.println("Se encontro tramiteAsegurado con id -> "
								+ tramiteSujetoObligado.getTramiteId());
						
						sujetoObligado = tramiteSujetoObligado.getSujetoObligado();
						
						 String folio = buildNumeroFolio(sujetoObligado.getSubdelegacion().getClave());
						clasificacionActividadEconomicaServiceBusinessRemote.ejecutarProcesoSincronizacionSINDO(folio, sujetoObligado, new Date(),
		                        30L, 1, 6, 6);
					}
				}

				
				
				System.out.println("=============Movimiento=============");
				System.out.println(xml);

			} catch (SolicitudNoEncontradaException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	 private String buildNumeroFolio(String claveSubdel) {
	        Calendar calendar=Calendar.getInstance();
	        StringBuilder juliano=new StringBuilder();
	        juliano.append(calendar.get(Calendar.DAY_OF_YEAR));
	        String folio = claveSubdel
	            + (juliano.length()==1?"00"+juliano:juliano.length()==2?"0"+juliano:juliano);
	        folio = claveSubdel + "411";
	        return folio;
	    }
	 
	 private Date calcularFechaSurteEfecto(String fechaSurteEfecto) {
	        Calendar cal = Calendar.getInstance();
	        int month = Integer.parseInt(fechaSurteEfecto.replaceAll("\\d{2}\\D(\\d{2}).*", "$1").replaceAll("^0", ""));
	        int year = Integer.parseInt(fechaSurteEfecto.replaceAll("(?:\\d{2}\\D){2}(\\d{4})", "$1"));
	        cal.set(Calendar.DATE, 1);
	        cal.set(Calendar.MONTH, month - 1);
	        cal.set(Calendar.YEAR, year);
	        return cal.getTime();
	    }
}
