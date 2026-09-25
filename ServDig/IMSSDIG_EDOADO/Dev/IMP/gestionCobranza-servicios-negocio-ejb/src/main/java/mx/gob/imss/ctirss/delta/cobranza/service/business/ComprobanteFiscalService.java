package mx.gob.imss.ctirss.delta.cobranza.service.business;

import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.cobranza.exception.EstadoAdeudoException;
import mx.gob.imss.ctirss.delta.cobranza.service.interfaces.ComprobanteFiscalServiceRemote;
import mx.gob.imss.ctirss.delta.cobranza.service.utility.ComprobanteFiscalServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractService;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceComprobanteFiscalException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.GenerarCodigoQRServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.enums.TipoDescargaArchivo;
import mx.gob.imss.ctirss.delta.model.firma.RespuestaFirmadoSimple;
import mx.gob.imss.ctirss.delta.model.gestion.cobranza.cfdi.Pago;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

@Stateless(name = "comprobanteFiscalService", mappedName = "comprobanteFiscalService")
public class ComprobanteFiscalService extends AbstractService implements
		ComprobanteFiscalServiceRemote {
	private static final String DESC_IMPRESION_COMPROBANTE_FISCAL = "Impresion de Comprobante Fiscal";
	private final String ERROR_SELLADO = "Ocurri&oacute; un error al intentar sellar el documento.";

	@EJB(name = "solicitudBusiness", mappedName = "solicitudBusiness")
	private SolicitudBusinessRemote solicitudBusinessRemote;

	@EJB(name = "firmaDigitalBusiness", mappedName = "firmaDigitalBusiness")
	private FirmaDigitalBusinessRemote firmaDigitalBusinessRemote;

	@EJB(name = "sujetoObligadoServiceBusiness", mappedName = "sujetoObligadoServiceBusiness")
	private SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusinessRemote;

	@EJB
	private GenerarCodigoQRServiceBusinessRemote generarCodigoQRServiceBusiness;

	@EJB
	private ComprobanteFiscalServiceUtilityLocal comprobanteFiscalServiceUtility;

	@Override
	public List<Pago> obtenerPagosporPeriodo(Pago pagoFiscal)
			throws ClienteWebserviceComprobanteFiscalException {
		return obtenerPagosCDFI(pagoFiscal);		
	}
	

	@Override
	public Solicitud crearSolicitudDescargaCompFiscal(Pago pagoFiscal, Date fechaActual, TipoDescargaArchivo tipoDescarga)
			throws EstadoAdeudoException {
		SujetoObligado sujetoObligado = null;

		try {
			sujetoObligado = sujetoObligadoServiceBusinessRemote
					.consultarPorNumeroRegistroPatronal(pagoFiscal.getNumeroRegistroPatronal());
		} catch (Exception e) {
			log.error(e);
		}

		Solicitud solicitud = new Solicitud();
		Calendar cal = Calendar.getInstance();
		Date fechaConclusion = cal.getTime();

		solicitud.setFechaSolicitud(fechaActual);
		solicitud.setFechaPresentacion(fechaActual);
		solicitud.setFechaConclusion(fechaConclusion);
		solicitud.setTipoSolicitud(new TipoSolicitud());
		solicitud.getTipoSolicitud().setIdTipoSolicitud(
				TipoSolicitudEnum.IMPRESION_COMPROBANTE_FISCAL.getValor().longValue());
		solicitud.setEstadoSolicitud(new EstadoSolicitud());
		solicitud.getEstadoSolicitud().setIdEstadoSolicitud(
				EstadoSolicitudEnum.ATENDIDA.getCodigo());
		solicitud.setTramites(new ArrayList<Tramite>());

		Tramite tramite;
		if (sujetoObligado != null) {
			solicitud.setSubdelegacion(sujetoObligado.getSubdelegacion());

			TramiteSujetoObligado tramiteSO = new TramiteSujetoObligado();
			tramiteSO.setSujetoObligado(sujetoObligado);
			tramite = tramiteSO;
		} else {
			tramite = new Tramite();
		}

		tramite.setEstadoTramite(new EstadoTramite());
		tramite.getEstadoTramite().setIdEstadoTramitePersona(
				EstadoTramiteEnum.CERRADO.getCodigo());
		tramite.getEstadoTramite().setDescripcion(
				EstadoTramiteEnum.CERRADO.getDescripcion());
		tramite.setTipoTramite(new TipoTramite());
		tramite.getTipoTramite().setIdTipoTramite(
				TipoTramiteEnum.IMPRESION_COMPROBANTE_FISCAL_CFDI.getCodigo());
		tramite.setFechaTramite(fechaActual);
		tramite.setFechaPresentacion(fechaActual);
		tramite.setFechaConclusion(fechaConclusion);
		tramite.setIndRatificado(false);

		solicitud.getTramites().add(tramite);
		try {
			solicitud = solicitudBusinessRemote.crear(solicitud);

			FirmaElectronica firma = obtenerDatosSellado(pagoFiscal);
			if (firma != null) {
				firmaDigitalBusinessRemote.insertarSolicitudFirmaDigital(solicitud, firma);

				// Se obtiene contenido del reporte en PDF
				if (tipoDescarga.equals(TipoDescargaArchivo.PDF)) {
					Map<String, Object> mapaFacturaElectronica = descargarFacturaElectronica(pagoFiscal);
					byte[] reporte = (byte[]) mapaFacturaElectronica.get("facturaElectronica");
					firmaDigitalBusinessRemote.guardarArchivoFirmado(
							firma.getSecuenciaNotaria(), "CFDI_" + pagoFiscal.getRfc() + ".pdf",
							reporte);
				} else if (tipoDescarga.equals(TipoDescargaArchivo.XML)) {
					String xmlComprobante = getCadenaComprobanteFiscal(pagoFiscal);

					firmaDigitalBusinessRemote.guardarArchivoFirmado(
							firma.getSecuenciaNotaria(), "CFDI_" + pagoFiscal.getRfc() + ".xml",
							xmlComprobante.getBytes());
				}
			}
		} catch (SolicitudNoValidaException e) {
			log.error("Error al guardar la solicitud", e);
			throw new EstadoAdeudoException(
					"No fue posible guardar la solicitud de impresion de Comprobante Fiscal");
		}

		return solicitud;
	}
	
	@Override
	public String getCadenaComprobanteFiscal(Pago pagoFiscal) {
		String xmlPagoFiscal;
		try {
			Pago pago = recuperarPagoSeleccionado(pagoFiscal, obtenerPagosCDFI(pagoFiscal));
			xmlPagoFiscal = pago.getXmlComprobante();
		} catch (ClienteWebserviceComprobanteFiscalException e) {
			log.error(e);
			xmlPagoFiscal = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		}
		return xmlPagoFiscal;
	}

	@Override
	public Map<String, Object> descargarFacturaElectronica(Pago pagoFiscal) {
		Map<String, Object> reporte = null;
		try {			
			Pago pago = recuperarPagoSeleccionado(pagoFiscal, obtenerPagosCDFI(pagoFiscal));
			BufferedImage imagenCodeQR = generarCodigoQRServiceBusiness
					.generadorCodigoQrCadena(comprobanteFiscalServiceUtility.generarCadenaCodigoQR(pago), 160, 150);
			reporte = comprobanteFiscalServiceUtility.descargarFacturaElectronica(pago, imagenCodeQR);
		} catch (Exception e) {
			log.error(e);
		}
		return reporte;
	}

	
	

	
	/**
	 * 
	 * @param pagoFiscal
	 * @return Pago
	 * @throws ClienteWebserviceComprobanteFiscalException
	 */
	private List<Pago> obtenerPagosCDFI(Pago pagoFiscal) throws ClienteWebserviceComprobanteFiscalException{
		List<Pago> listaPagos = new ArrayList<Pago>();
		try {
			listaPagos = comprobanteFiscalServiceUtility.obtenerPagos(pagoFiscal);			
		} catch (ClienteWebserviceComprobanteFiscalException e) {
			throw new ClienteWebserviceComprobanteFiscalException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ClienteWebserviceComprobanteFiscalException();
		}
		return listaPagos;
	}
	
	
	private FirmaElectronica obtenerDatosSellado(Pago pagoFiscal) throws EstadoAdeudoException{
		FirmaElectronica firmaElectronica = null;
		Locale locMEX = new Locale("es", "MX");
		Date fechaDelReporte = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMM yyyy, HH:mm:ss",locMEX);
		StringBuffer sbCadenaOriginal = new StringBuffer();

		sbCadenaOriginal.append("||Invocante:portalimssdigital")
				.append("|Tramite:").append(DESC_IMPRESION_COMPROBANTE_FISCAL)
				.append("|Fecha:").append(sdf.format(fechaDelReporte))
				.append("|Numero de registro patronal:").append(pagoFiscal.getNumeroRegistroPatronal())
				.append("|Nombre o razón social:");

		if (StringUtils.isNotBlank(pagoFiscal.getRfc())) {
			sbCadenaOriginal.append("|RFC:").append(pagoFiscal.getRfc());
		}
		sbCadenaOriginal.append("||");

		RespuestaFirmadoSimple selloDigital = firmaDigitalBusinessRemote
				.getSelloDigital(sbCadenaOriginal.toString(), null, pagoFiscal.getRfc());
		if (selloDigital != null) {
			if (StringUtils.isBlank(selloDigital.getSello())) {
				throw new EstadoAdeudoException(ERROR_SELLADO);
			}

			//Se crea el objeto de firma digital
            firmaElectronica = new FirmaElectronica();
            firmaElectronica.setCadenaOriginal(sbCadenaOriginal.toString());
            firmaElectronica.setReciboNotarial(selloDigital.getTramite());
            firmaElectronica.setSecuenciaNotaria(selloDigital.getTramite());
            firmaElectronica.setSerialCertificado(selloDigital.getNoSerie());
            firmaElectronica.setRecibo(selloDigital.getSello());
            firmaElectronica.setUrlAcuseFirma("");
            firmaElectronica.setIniciaVigenciaCertificado(new Date());
            firmaElectronica.setFinVigenciaCertificado(new Date());
		} else {
			throw new EstadoAdeudoException(ERROR_SELLADO);
		}

		return firmaElectronica;
	}
	
	private Pago recuperarPagoSeleccionado(Pago pago, List<Pago> listaPagos){
		Pago pagoSelect = null;
		if(!CollectionUtils.isEmpty(listaPagos)){
			for(Pago p : listaPagos){
				if(p.getFolioSua().endsWith(pago.getFolioSua()) && p.getTotal().equals(pago.getTotal())){
					pagoSelect=p;
					break;
				}
			}
		}
		return pagoSelect;
	}
	
}
