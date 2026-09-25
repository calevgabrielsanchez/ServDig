package mx.gob.imss.distss.delta.rtt.service.business;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.documento.probatorio.DocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.exception.riesgosTrabajo.RiesgosTrabajoException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.dto.DatosBoveda;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudTramiteBusinessRemote;
import mx.gob.imss.ctirss.delta.model.escritoDesacuerdo.CausaDesacuerdo;
import mx.gob.imss.ctirss.delta.model.escritoDesacuerdo.DomicilioEscritoDesacuerdo;
import mx.gob.imss.ctirss.delta.model.escritoDesacuerdo.MotivosDesacuerdo;
import mx.gob.imss.ctirss.delta.model.escritoDesacuerdo.TramiteEscritoDesacuerdo;
import mx.gob.imss.ctirss.delta.model.firma.RespuestaFirmadoSimple;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipo;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.PatronRiesgosTrabajo;
import mx.gob.imss.distss.delta.rtt.service.entity.EscritoDesacuerdoEntityLocal;
import mx.gob.imss.distss.delta.rtt.service.entity.GenerarReportesRiesgosTrabajoLocal;
import mx.gob.imss.distss.delta.rtt.service.interfaces.EscritoDesacuerdoBusinessRemote;

@Stateless(name = "escritoDesacuerdoBusiness", mappedName = "escritoDesacuerdoBusiness")
public class EscritoDesacuerdoBusiness extends AbstractServiceBusiness implements EscritoDesacuerdoBusinessRemote {
	
	@EJB(mappedName = "solicitudBusiness")
	private SolicitudBusinessRemote solicitudBusinessRemote;
	@EJB(mappedName = "firmaDigitalBusiness")
	private FirmaDigitalBusinessRemote firmaDigitalBusinessRemote;
	@EJB(mappedName = "solicitudTramiteBusiness")
	private SolicitudTramiteBusinessRemote solicitudTramiteBusinessRemote;
	@EJB
	private EscritoDesacuerdoEntityLocal escritoDesacuerdoEntityLocal;
	@EJB
	private GenerarReportesRiesgosTrabajoLocal generarReportesRiesgosTrabajo;
	@EJB(mappedName = "documentoProbatorioServiceBusiness")
	private DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusinessRemote;
	

	/**
	 * Metodo para crear la solicitud y en su caso finalizarla
	 * @param solicitud - Datos con las que se creara la solicitud de escrito de desacuerdo
	 * @param finalizar - Si se quiere finalizar la solicicitud al mismo tiempo que se crea
	 * @return Solicitud creada
	 * @throws SolicitudNoValidaException - Si algun dato esta mal  o no se pudo crear o no se pudo generar el folio de rececpcion si se finaliza
	 * @throws SolicitudNoEncontradaException - Si no se pueden actualizar los estados de la solicitud cuando se marca que se va a finalizar la solicitud
	 */
	@Override
	public Solicitud crearSolicitudEscrito(Solicitud solicitud,Boolean finalizar) throws SolicitudNoValidaException, SolicitudNoEncontradaException {
		
		//si la bandera de finalizar es nula la ponemos en false
		finalizar = finalizar == null ? false : finalizar;
		//llenamos la solicitud de escrito de desacuerdo
		Solicitud solicitudEscrito = this.llenarSolicitudEscrito(solicitud, finalizar);
		//Creamos la solicitud de escrito de desacuerdo
		solicitudEscrito = solicitudBusinessRemote.crear(solicitudEscrito);
		//validamos si tenemos que finalizar la solicitud
		if(finalizar) {
			solicitudEscrito = this.finalizarEscritoInterno(solicitudEscrito, true);
		}
		
		return solicitudEscrito;
	}

	@Override
	public Solicitud finalizarSolicitudEscrito(Solicitud solicitud) throws SolicitudNoValidaException, SolicitudNoEncontradaException {
		
		solicitud = this.finalizarEscritoInterno(solicitud, false);

		return solicitud;
	}

	@Override
	public Solicitud validarTramiteExistente(Long idPatronSO) {
		
		List<Long> idTramite = new ArrayList<Long>();
		idTramite.add(TipoTramiteEnum.REGISTRO_ESCRITO_DESACUERDO.getCodigo().longValue());
		log.debug("El id del patron al que le buscaremos tramites abiertos de escrito es " + idPatronSO);
		Solicitud solicitudEscrito = null;;
		try {
			solicitudEscrito = solicitudTramiteBusinessRemote.getUltimaSolicitudPatronPorTipoEstado(idPatronSO, idTramite, EstadoTramiteEnum.INICIADO.getValor().longValue());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return solicitudEscrito;
	}

	@Override
	public TramiteEscritoDesacuerdo getTramiteEscrito(Long idEscritoDesacuerdo) {
		
		return null;
	}
	
	 @Override
	 public byte[] generarAcuseEscritoDesacuerdoPDF(Solicitud solicitud) throws RiesgosTrabajoException {

		 log.debug("Generamos reporte");
		 byte[] reporte = null;

		 TramiteEscritoDesacuerdo tramitee = null;
		 for(Tramite tramite: solicitud.getTramites()) {
			 if(tramite instanceof TramiteEscritoDesacuerdo) {
				 tramitee = (TramiteEscritoDesacuerdo) tramite;
				 break;
			 }
		 }
		 //Generamos el PDF
		 reporte = generarReportesRiesgosTrabajo.generarAcuseEscritoDesacuerdoPDF(solicitud);
		 DocumentoProbatorio docto = new DocumentoProbatorio();
		 docto.setDigitalizacion(reporte);
		 docto.setNomNombreDocumento("acuseRecepcionEscritoDesacuerdo.pdf");
		 docto.setDocumentoPorTipo(new DocumentoPorTipo());
		 docto.getDocumentoPorTipo().setIdDocumentoPorTipo(165L);
		 
		 DatosBoveda datosBoveda = new DatosBoveda();
		 datosBoveda.setFolio(solicitud.getNoFolioSolicitud());
		 datosBoveda.setIdTramite(tramitee.getTramiteId());
		 datosBoveda.setTipoTramite(tramitee.getTipoTramite().getIdTipoTramite());
		 datosBoveda.setTipoDocumentos("pdf");
		 datosBoveda.setTipoDocumental("D:RTT:escrito_desacuerdo");
		 datosBoveda.setRutaBoveda("/rtt");
		 
		 try {
			documentoProbatorioServiceBusinessRemote.guardarDocumentoProbatorioBoveda(datosBoveda, docto);
		} catch (DocumentoProbatorioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RiesgosTrabajoException("No fue posible guardar el acuse de recepcion");
		}

		 log.debug("Enviamos reporte");
		 return reporte;
	 }
	
	/**
	 * 
	 * @param solicitud
	 * @param estadosAfectados
	 * @return
	 * @throws SolicitudNoValidaException
	 * @throws SolicitudNoEncontradaException
	 */
	private Solicitud finalizarEscritoInterno(Solicitud solicitud, Boolean estadosAfectados) throws SolicitudNoValidaException, SolicitudNoEncontradaException {
		TramiteEscritoDesacuerdo tramiteEscrito = (TramiteEscritoDesacuerdo) solicitud.getTramites().get(0);
		PatronRiesgosTrabajo patron = tramiteEscrito.getPatron();
		FirmaElectronica firmaElectronica = solicitud.getFirmaElectronica();
		//generamos el folio de recepcion
		String folioRecepcion = escritoDesacuerdoEntityLocal.generarFolioRecepcion(patron);
		log.debug("El folio de recepcion generado es el: " + folioRecepcion);
		tramiteEscrito.setFolioRecepcion(folioRecepcion);
		//insertamos el escrito en BD
		tramiteEscrito = escritoDesacuerdoEntityLocal.guardarEscrito(tramiteEscrito);
		//actualizamos el detalle del tramite para incluir el id del escrito y el folio de recepcion
		try {
			solicitudBusinessRemote.actualizarXmlTramite(tramiteEscrito);
		} catch (TramiteNoEncontradoException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		
		//agregamos los ultimos cambios a la solicitud
		solicitud.setTramites(new ArrayList<Tramite>());
		tramiteEscrito.setFechaConclusion(new Date());
		solicitud.getTramites().add(tramiteEscrito);
		solicitud.setFechaConclusion(new Date());
		
		//verificamos si tenemos que generar los datos de firma electronica
		//esto va a pasar cuando la solicitud sea por ventanilla, ya que ahi no se utiliza la FIEL
		if(firmaElectronica == null){
			String cadenaOriginal = generarCadenaOriginal(tramiteEscrito, solicitud.getNoFolioSolicitud());
			RespuestaFirmadoSimple firmado = firmaDigitalBusinessRemote.getSelloDigital(cadenaOriginal, null, null);
			firmaElectronica = firmaDigitalBusinessRemote.convertirRespuestaFirmadoSimple(cadenaOriginal, firmado);
		}
		//insertamos los datos de firma y los relacionamos a la solicitud
		firmaDigitalBusinessRemote.insertarSolicitudFirmaDigital(solicitud, firmaElectronica);
		
		solicitud.setFirmaElectronica(firmaElectronica);
		if(!estadosAfectados) {
			try {
				solicitudBusinessRemote.actualizaAConcluida(solicitud);
			} catch (SolicitudNoEncontradaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return solicitud;
	}
	
	private String generarCadenaOriginal(TramiteEscritoDesacuerdo tramite, String folioSolicitud) {
		StringBuffer contenidoAFirmar = new StringBuffer();

		// Inicio
		contenidoAFirmar.append("||");
		contenidoAFirmar.append("Invocante:portalimssdigital|");
		contenidoAFirmar.append("Tipo de tramite: ESCRITO DE DESACUERDO").append("|");
		contenidoAFirmar.append("Fecha del tramite : ").append((new SimpleDateFormat("dd 'de' MMMM yyyy',' HH:mm:ss", new Locale("es", "MX"))).format(tramite.getFechaConclusion())).append("|");
		contenidoAFirmar.append("Folio: ").append(folioSolicitud).append("|");
		contenidoAFirmar.append("Nombre o Razon Social: ").append(tramite.getPatron().getRazonSocial()).append("|");
		contenidoAFirmar.append("Numero Registro Patronal: ").append(tramite.getPatron().getNrp()).append("||");
		return contenidoAFirmar.toString();
	} 

	private Solicitud llenarSolicitudEscrito(Solicitud solicitud,Boolean finalizar) throws SolicitudNoValidaException{
		
		Date fechaActual = new Date();
		//Obtenemos el tramite de escrito 
		TramiteEscritoDesacuerdo tramiteEscritoDesacuerdo = null;
		//obtenemos el tramite de escrito de desacuerdo dentro de la solicitud
		for(Tramite tramite: solicitud.getTramites()) {
			if(tramite instanceof TramiteEscritoDesacuerdo) {
				tramiteEscritoDesacuerdo = (TramiteEscritoDesacuerdo) tramite;
				break;
			}
		}
		//Si no existe ni un tramite de escrito mandamos una excepcion
		if(tramiteEscritoDesacuerdo == null) {
			throw new SolicitudNoValidaException("La solicitud no contiene tramites de escrito");
		}
		//verificamos el origen de la solicitud, si no viene la ponemos como ventanilla
		OrigenSolicitud origenSolicitud = solicitud.getOrigenSolicitud();
		if(origenSolicitud == null) {
			origenSolicitud = new OrigenSolicitud();
			origenSolicitud.setIdOrigenSolicitud(OrigenSolicitudEnum.INTERNET.getId());
			solicitud.setOrigenSolicitud(origenSolicitud);
		}
		
		solicitud.setFechaPresentacion(fechaActual);
		solicitud.setFechaSolicitud(fechaActual);
		solicitud.setTipoSolicitud(new TipoSolicitud(TipoSolicitudEnum.ESCRITO_DESACUERDO.getValor().longValue()));
		
		Integer estadoSolicitud = finalizar ? EstadoSolicitudEnum.ATENDIDA.getValor() : EstadoSolicitudEnum.REGISTRADA.getValor();
		solicitud.setEstadoSolicitud(new EstadoSolicitud(estadoSolicitud));
		
		Integer estadoTramite = finalizar ? EstadoTramiteEnum.CERRADO.getValor() : EstadoTramiteEnum.INICIADO.getValor();
		tramiteEscritoDesacuerdo.setEstadoTramite(new EstadoTramite());
		tramiteEscritoDesacuerdo.getEstadoTramite().setIdEstadoTramitePersona(estadoTramite);
		tramiteEscritoDesacuerdo.setTipoTramite(new TipoTramite(TipoTramiteEnum.REGISTRO_ESCRITO_DESACUERDO.getCodigo()));
	
		Date fechaConclusion = finalizar ? new Date() : null;
		solicitud.setFechaConclusion(fechaConclusion);
		tramiteEscritoDesacuerdo.setFechaPresentacion(fechaActual);
		tramiteEscritoDesacuerdo.setFechaTramite(fechaActual);
		tramiteEscritoDesacuerdo.setFechaConclusion(fechaConclusion);
		
		solicitud.setTramites(new ArrayList<Tramite>());
		solicitud.getTramites().add(tramiteEscritoDesacuerdo);
		
		return solicitud;
		
	}

	@Override
	public List<CausaDesacuerdo> getCausasDesacuerdo(Long idMateria) {
		return escritoDesacuerdoEntityLocal.getCausasDesacuerdo(idMateria);
	}

}
