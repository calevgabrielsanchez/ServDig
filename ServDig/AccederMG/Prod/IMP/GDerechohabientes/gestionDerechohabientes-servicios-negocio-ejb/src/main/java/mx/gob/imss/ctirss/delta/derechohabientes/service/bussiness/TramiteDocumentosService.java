package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.DocumentosServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.TramiteDocumentosServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesWebSserviceException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.DocumentoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.entity.SolicitudEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.TipoPerInteresadaSol;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Turno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.derechohabientes.PropiedadesDocumento;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaInteresadaSolEnum;
import mx.gob.imss.ctirss.delta.model.firma.RespuestaFirmadoSimple;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.CitaSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.PersonaInteresadaSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAsegurado;
import mx.gob.imss.vigenciaderechos.VigenciaDerechosWSClientRemote;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang.StringUtils;

@Stateless(name = "tramiteDocumentosService", mappedName = "tramiteDocumentosService")
public class TramiteDocumentosService extends AbstractServiceBusiness implements
		TramiteDocumentosServiceRemote, TramiteDocumentosServiceLocal{

	@EJB(mappedName = "solicitudBusiness")
	private SolicitudBusinessRemote solicitudBusiness;

	@EJB(mappedName = "firmaDigitalBusiness")
	private FirmaDigitalBusinessRemote firmaDigitalBusiness;

	@EJB
	private DocumentosServiceRemote documentosService;
	
	@EJB
	private EMailServiceLocal eMailService;
	
	@EJB
    private transient SolicitudEntityLocal solicitudEntity;
	
	@EJB
	private VigenciaDerechosWSClientRemote vigenciaDerechosWS;
	
	@Override
	public Map<String, Object> generaTramiteDocumentoReporteVigencia(
			AsignacionNSS asignacionNSS, Usuario usuario,
			Map<String, Integer> identificadoresMap, boolean enviaMail) throws DocumentoException {
		
		Map<String, Object> resultado = new HashMap<String, Object>();
		String nss = asignacionNSS.getNssStr();
		byte[] documentByteArray = null;

		try {
			// Si usuario == null indica que la llama fue externa
			// en caso contrario indica que se hizo desde el portal
			if (usuario == null) {
				usuario = new Usuario();
				usuario.setUsuario(asignacionNSS.getCurp());
			}

			asignacionNSS.setNss(nss);
			

			// 1. Se debe crear un tramite y solicitud.
			Integer origenSolicitud = identificadoresMap.get("origenSolicitud");
			if(origenSolicitud==null) {
				origenSolicitud = OrigenSolicitudEnum.INTERNET.getId().intValue();
			}

			// asignacionNSS es de tipo AsignacionNSS que extiende de Fisica
			// por lo que puede pasarse como parametro para crear la solicitud
			Solicitud solicitud = this.crearTramiteSolicitud(asignacionNSS,
					origenSolicitud.longValue(), usuario, identificadoresMap);

			// 2. Obtener sello digital
			// Se obtiene el sello digital
			// Se genera cadena original
			asignacionNSS.setNss(nss);
			FirmaElectronica firmaElectronica = this.generaFirmaElectronica(
					asignacionNSS, solicitud, "COMPROBANTE DE VIGENCIA DE DERECHOHABIENTES");

			// firmaElectronica.setCadenaOriginal(
			// this.generaCadenaOriginal(asignacionNSS, solicitud) );
			this.log.debug("Cadena original: "
					+ firmaElectronica.getCadenaOriginal());
			this.log.debug("Sello digital: " + firmaElectronica.getRecibo());
			this.log.debug("Secuencia notarial: "
					+ firmaElectronica.getSecuenciaNotaria());
			this.log.debug("Recibo notarial: "
					+ firmaElectronica.getReciboNotarial());
			this.log.debug("Numero de serie: "
					+ firmaElectronica.getSerialCertificado());

			// 3. Generacion del reporte con informacion del sello digital
			documentByteArray = (byte[]) documentosService
					.getComprobanteVigenciaDerechos(asignacionNSS,
							firmaElectronica, usuario);

			// 4. Se guarda el reporte
			firmaDigitalBusiness.guardarArchivoFirmado(
					firmaElectronica.getReciboNotarial(),
					"reporte_consulta_vigencia", documentByteArray);
			
			Long tramiteId = solicitud.getTramites().get(0).getTramiteId();
			
			solicitudEntity.actualizarDocumentosTramite(tramiteId, 
					DocumentoPorTipoEnum.COMPROBANTE_VIGENCIA_DERECHOS.getId(), documentByteArray);
			
			resultado.put("documento", documentByteArray);
			resultado.put("tramiteId", tramiteId);

		} catch (SolicitudNoValidaException e) {
			throw new DocumentoException(e.getMessage());
		} catch (SolicitudException e) {
			throw new DocumentoException(e.getMessage());
		} catch (DerechohabientesBusinessException e) {
			throw new DocumentoException(e.getMessage());
		} catch (Exception e) {
			throw new DocumentoException(e.getMessage());
		}
		return resultado;
	}

	@Override
	public Object generaDocumentoConSelloDigital(AsignacionNSS asignacionNSS,
			Solicitud solicitud, Usuario usuario, Integer identificadorReporte, PropiedadesDocumento propiedades, Map<String, Integer> identificadoresMap)
			throws DocumentoException {

		String nombreReporte = "";
		byte[] documentByteArray = null;
		List<ByteArrayOutputStream> listByteArray = new ArrayList<ByteArrayOutputStream>();
		Boolean guardarCompleto = true;
		try {
			// Si usuario == null indica que la llama fue externa
			// en caso contrario indica que se hizo desde el portal
			if (usuario == null) {
				usuario = new Usuario();
				usuario.setUsuario(asignacionNSS.getCurp());
			}

			asignacionNSS.setNss(asignacionNSS.getNssStr());

			// asignacionNSS es de tipo AsignacionNSS que extiende de Fisica
			// por lo que puede pasarse como parametro para crear la solicitud
			if(identificadoresMap != null){
				// 1. Se debe crear un tramite y solicitud.
				Integer origenSolicitud = identificadoresMap.get("origenSolicitud");
				if(origenSolicitud==null) {
					origenSolicitud = OrigenSolicitudEnum.VENTANILLA.getId().intValue();
				}
				
				solicitud = this.crearTramiteSolicitud(asignacionNSS,
						origenSolicitud.longValue(), usuario, identificadoresMap);
			}

			// asignacionNSS es de tipo AsignacionNSS que extiende de Fisica
			// por lo que puede pasarse como parametro para crear la solicitud

			// 2. Obtener sello digital
			// Se obtiene el sello digital
			// Se genera cadena original
			FirmaElectronica firmaElectronica = null;
			String reciboNotarial = null;
			
//			if(solicitud != null){
//				firmaElectronica = this.generaFirmaElectronica(
//						asignacionNSS, solicitud, "");
//			}

			
			// 3. Generacion del reporte con informacion del sello digital en
			// base a identificador
			//Los tramites deben generar de forma individual su sello digital, utilizando como principal la FirmaDigital del primer sello generado
			//este se ira pasando a los reportes subsecuentes, al final se concatenaran los arreglos de bytes en uno solo para que este sea guardado
			ByteArrayOutputStream stream = null;
			ByteArrayOutputStream outputStream = null;
			
			TipoTramiteEnum tipoTramiteEnum = TipoTramiteEnum.obternerEnumById(identificadorReporte);

			switch (tipoTramiteEnum) {
			case REIMPRESION_SAV002://SAV002
				firmaElectronica = this.generaFirmaElectronica(
						asignacionNSS, solicitud, "SAV002");
				
				reciboNotarial = firmaElectronica.getReciboNotarial();
				
				documentByteArray = (byte[]) documentosService.generaSav002v2(
						asignacionNSS, firmaElectronica, usuario, null, OrigenSolicitudEnum.VENTANILLA.getId(), true);
				stream = new ByteArrayOutputStream(documentByteArray.length);
				stream.write(documentByteArray, 0, documentByteArray.length);
				listByteArray.add( stream );
				
				outputStream = (ByteArrayOutputStream) documentosService.concatenarByteStream(listByteArray, true);
				documentByteArray = outputStream.toByteArray();
				nombreReporte = "SAV002.pdf";
				break;
//			case 2://SAV005
//				documentByteArray = (byte[]) documentosService.getDocumentoSav005(asignacionNSS, firmaElectronica, 
//						propiedades.getIdDerechohabiente(), propiedades.getIdEstadoTramite());
//				nombreReporte = "SAV005.pdf";
//				break;
//			case 3://SAV007
//				documentByteArray = (byte[]) documentosService.getDocumentoSav007(asignacionNSS, firmaElectronica, propiedades.getIdDerechohabiente());
//				nombreReporte = "SAV007.pdf";
//				break;
//			case 4://SAV011
//				documentByteArray = (byte[]) documentosService.getDocumentoSav011(asignacionNSS, firmaElectronica, usuario, propiedades.getIdDerechohabiente());
//				nombreReporte = "SAV011.pdf";
//				break;
			case CARTILLA_NACIONAL_DE_SALUD://cartilla
				firmaElectronica = this.generaFirmaElectronica(
						asignacionNSS, solicitud, "Cartilla nacional de salud");
				reciboNotarial = firmaElectronica.getReciboNotarial();
				
				documentByteArray = (byte[]) documentosService.getCartillaNacionalSalud(propiedades.getIdDerechohabiente(), asignacionNSS, firmaElectronica);
				stream = new ByteArrayOutputStream(documentByteArray.length);
				stream.write(documentByteArray, 0, documentByteArray.length);
				listByteArray.add( stream );
				
				outputStream = (ByteArrayOutputStream) documentosService.concatenarByteStream(listByteArray, true);
				documentByteArray = outputStream.toByteArray();
				
				nombreReporte = "Cartilla.pdf";
				break;
			case TARJETA_DE_ADSCRIPCION_430A5://4305a
				firmaElectronica = this.generaFirmaElectronica(
						asignacionNSS, solicitud, "Trajeta de adscripcion 430A5");
				reciboNotarial = firmaElectronica.getReciboNotarial();
				
				documentByteArray = (byte[]) documentosService.getDocumentoReporte4305A(asignacionNSS, firmaElectronica, usuario, OrigenSolicitudEnum.VENTANILLA.getId());
				stream = new ByteArrayOutputStream(documentByteArray.length);
				stream.write(documentByteArray, 0, documentByteArray.length);
				listByteArray.add( stream );
				
				outputStream = (ByteArrayOutputStream) documentosService.concatenarByteStream(listByteArray, true);
				documentByteArray = outputStream.toByteArray();
				
				nombreReporte = "4305a.pdf";
				break;
			case COMPROBANTE_DE_VIGENCIA_DE_DERECHOHAB:
				guardarCompleto = false;
				firmaElectronica = this.generaFirmaElectronica(
						asignacionNSS, solicitud, "COMPROBANTE DE VIGENCIA DE DERECHOHABIENTES");
				reciboNotarial = firmaElectronica.getReciboNotarial();
				
				documentByteArray = (byte[]) documentosService.getComprobanteVigenciaDerechos(asignacionNSS,firmaElectronica, usuario);
				
				firmaDigitalBusiness.guardarArchivoFirmado(reciboNotarial,"comprobanteVigenciaDerechos.pdf", documentByteArray);
				
				stream = new ByteArrayOutputStream(documentByteArray.length);
				stream.write(documentByteArray, 0, documentByteArray.length);
				listByteArray.add( stream );
				
				outputStream = (ByteArrayOutputStream) documentosService.concatenarByteStream(listByteArray, true);
				documentByteArray = outputStream.toByteArray();
				
				nombreReporte = "Reporte_vigencia.pdf";
				break;
				//Reportes anidados
				//Todos los subreportes tienen que ser sellados individualmente
			case REGISTRO_DE_DERECHOHABIENTE://este tramite genera Cartilla, 4305a, SAV002
			case REGISTRO_ASEGURADO:
			case REGISTRO_PENSIONADO:
			case REGISTRO_CONCUBINA_RIO:
			case REGISTRO_CONYUGUE:
			case REGISTRO_HIJOS:
			case REGISTRO_PADRES:
//				documentByteArray = (byte[]) documentosService.getDocumentoRegistroDerechohabientes(asignacionNSS, firmaElectronica, 
//						propiedades.getIdTramite(), propiedades.getTitulo(), usuario);
				
				Tramite tram = null;
				for(Tramite tramite: solicitud.getTramites()) {
					if(tramite.getTramiteId().equals(propiedades.getIdTramite())) {
						tram = tramite;
					}
				}
				
				firmaElectronica = this.generaFirmaElectronica(
						asignacionNSS, solicitud, tram.getTipoTramite().getDescripcion());
				reciboNotarial = firmaElectronica.getSecuenciaNotaria();
				
				solicitud.setSolicitante(usuario);
				solicitud.setCadenaOriginal(firmaElectronica.getCadenaOriginal());
				solicitud.setSecuenciaDeNotaria(firmaElectronica.getSecuenciaNotaria());
				solicitud.setSelloDigital(firmaElectronica.getRecibo());
				solicitud.setNumeroSerieCertificado(firmaElectronica.getSerialCertificado());
				solicitud.setOrigenSolicitud(new OrigenSolicitud());
				solicitud.getOrigenSolicitud().setIdTipoSolicitud(OrigenSolicitudEnum.VENTANILLA.getId());
				
				documentByteArray = (byte[]) solicitudBusiness.obtenerDocumentoResultante(solicitud, propiedades.getIdTramite(), DocumentoPorTipoEnum.CARTILLA.getId().intValue());
				//documentByteArray = (byte[]) documentosService.getCartillaNacionalSalud(propiedades.getIdDerechohabiente(), asignacionNSS, firmaElectronica);
				
				if( documentByteArray != null ){
					stream = new ByteArrayOutputStream(documentByteArray.length);
					stream.write(documentByteArray, 0, documentByteArray.length);
					listByteArray.add( stream );
				}
				
				//firmaElectronica = this.generaFirmaElectronica(asignacionNSS, solicitud, "Trajeta de adscripcion 430A5");
				documentByteArray = (byte[]) solicitudBusiness.obtenerDocumentoResultante(solicitud, propiedades.getIdTramite(),DocumentoPorTipoEnum.D_4305A.getId().intValue());
				//documentByteArray = (byte[]) documentosService.getDocumentoReporte4305A(asignacionNSS, firmaElectronica, usuario, OrigenSolicitudEnum.VENTANILLA.getId());
				stream = new ByteArrayOutputStream(documentByteArray.length);
				stream.write(documentByteArray, 0, documentByteArray.length);
				listByteArray.add( stream );
				
				//firmaElectronica = this.generaFirmaElectronica(asignacionNSS, solicitud, "SAV002");
				documentByteArray = (byte[]) solicitudBusiness.obtenerDocumentoResultante(solicitud, propiedades.getIdTramite(),DocumentoPorTipoEnum.SAV002.getId().intValue());
				//documentByteArray = (byte[]) documentosService.generaSav002(asignacionNSS, firmaElectronica, usuario, propiedades.getIdTramite(),OrigenSolicitudEnum.VENTANILLA.getId(),true);
				stream = new ByteArrayOutputStream(documentByteArray.length);
				stream.write(documentByteArray, 0, documentByteArray.length);
				listByteArray.add( stream );
				
				outputStream = (ByteArrayOutputStream) documentosService.concatenarByteStream(listByteArray, true);
				documentByteArray = outputStream.toByteArray();
				guardarCompleto = false;
				nombreReporte = "DocumentosRegistroDerechohabientes.pdf";
				break;
			case ASIGNACION_CONSULTORIO_TURNO_MEDICO://este tramite genera Cartilla, 4305a
//				documentByteArray = (byte[]) documentosService.getDocumentosProrroga(asignacionNSS, firmaElectronica, 
//						propiedades.getIdTramite(), propiedades.getIdDerechohabiente());
				firmaElectronica = this.generaFirmaElectronica(
						asignacionNSS, solicitud, "Cartilla nacional de salud");
				reciboNotarial = firmaElectronica.getReciboNotarial();
				
				documentByteArray = (byte[]) documentosService.getCartillaNacionalSalud(propiedades.getIdDerechohabiente(), asignacionNSS, firmaElectronica);
				stream = new ByteArrayOutputStream(documentByteArray.length);
				stream.write(documentByteArray, 0, documentByteArray.length);
				listByteArray.add( stream );
				
				firmaElectronica = this.generaFirmaElectronica(
						asignacionNSS, solicitud, "Trajeta de adscripcion 430A5");
				documentByteArray = (byte[]) documentosService.getDocumentoReporte4305A(asignacionNSS, firmaElectronica, usuario, OrigenSolicitudEnum.VENTANILLA.getId());
				stream = new ByteArrayOutputStream(documentByteArray.length);
				stream.write(documentByteArray, 0, documentByteArray.length);
				listByteArray.add( stream );
				
				outputStream = (ByteArrayOutputStream) documentosService.concatenarByteStream(listByteArray, true);
				documentByteArray = outputStream.toByteArray();
				
				nombreReporte = "DocumentosProrroga.pdf";
				break;
			case MODIFICACION_DE_DERECHOHABIENTE://este tramite genera Cartilla, 4305a, SAV002
//				documentByteArray = (byte[]) documentosService.getDocumentosCambioDatos(asignacionNSS, firmaElectronica, 
//						propiedades.getIdTramite(), propiedades.getTitulo(), usuario);
				firmaElectronica = this.generaFirmaElectronica(
						asignacionNSS, solicitud, "Cartilla nacional de salud");
				reciboNotarial = firmaElectronica.getReciboNotarial();
				
				documentByteArray = (byte[]) documentosService.getCartillaNacionalSalud(propiedades.getIdDerechohabiente(), asignacionNSS, firmaElectronica);
				stream = new ByteArrayOutputStream(documentByteArray.length);
				stream.write(documentByteArray, 0, documentByteArray.length);
				listByteArray.add( stream );
				
				firmaElectronica = this.generaFirmaElectronica(
						asignacionNSS, solicitud, "Trajeta de adscripcion 430A5");
				documentByteArray = (byte[]) documentosService.getDocumentoReporte4305A(asignacionNSS, firmaElectronica, usuario, OrigenSolicitudEnum.VENTANILLA.getId());
				
				firmaElectronica = this.generaFirmaElectronica(
						asignacionNSS, solicitud, "SAV002");
				documentByteArray = (byte[]) documentosService.generaSav002(asignacionNSS, firmaElectronica, usuario, propiedades.getIdTramite(),OrigenSolicitudEnum.VENTANILLA.getId(),true);
				stream = new ByteArrayOutputStream(documentByteArray.length);
				stream.write(documentByteArray, 0, documentByteArray.length);
				listByteArray.add( stream );
				
//				outputStream = (ByteArrayOutputStream) documentosService.concatenarByteStream(listByteArray, true);
//				stream = new ByteArrayOutputStream(documentByteArray.length);
//				stream.write(documentByteArray, 0, documentByteArray.length);
//				listByteArray.add( stream );
				
				outputStream = (ByteArrayOutputStream) documentosService.concatenarByteStream(listByteArray, true);
				documentByteArray = outputStream.toByteArray();
				
				nombreReporte = "DocmentosCambioDatos.pdf";
				break;
			case CAMBIO_CLINICA://Este tramite genera Cartilla, 4305a, SAV002, SAV005, SAV006
//				documentByteArray = (byte[]) documentosService.getDocumentosCambioClinica(asignacionNSS, firmaElectronica, 
//						propiedades.getIdTramite(), usuario, propiedades.getTitulo());
				guardarCompleto = false;
				
				if (propiedades.getIdEstadoTramite() == EstadoTramiteEnum.INICIADO.getCodigo().longValue() )
					{
					log.debug("llegue saav05" + propiedades.getIdEstadoTramite().byteValue());
					firmaElectronica = this.generaFirmaElectronica(asignacionNSS, solicitud, "SAV005");
					
					reciboNotarial = firmaElectronica.getReciboNotarial();
					
					documentByteArray = (byte[]) documentosService.getDocumentoSav005(asignacionNSS, firmaElectronica, propiedades.getIdDerechohabiente(), propiedades.getIdEstadoTramite(), OrigenSolicitudEnum.VENTANILLA.getId(),usuario);
					
					// 3. Se guarda el reporte
					firmaDigitalBusiness.guardarArchivoFirmado(
							reciboNotarial,
							"sav005.pdf", documentByteArray);
					stream = new ByteArrayOutputStream(documentByteArray.length);
					stream.write(documentByteArray, 0, documentByteArray.length);
					listByteArray.add( stream );
				}else{
						log.debug("llegue por finalizar con estado " + propiedades.getIdEstadoTramite().byteValue());
						firmaElectronica = this.generaFirmaElectronica(asignacionNSS, solicitud, "Cartilla nacional de salud");
						reciboNotarial = firmaElectronica.getReciboNotarial();
						
						
						try{
							// -------------------------------------------
							// CARTILLA
							// -------------------------------------------
							documentByteArray = (byte[]) documentosService.getCartillaNacionalSalud(propiedades.getIdDerechohabiente(), asignacionNSS, firmaElectronica);
							firmaDigitalBusiness.guardarArchivoFirmado(
									reciboNotarial,
									"cartilla.pdf", documentByteArray);
							
						}catch(Exception e){
							log.debug("GENERAR CARTILLA");
							log.debug(e);
						}
						
						/*stream = new ByteArrayOutputStream(documentByteArray.length);
						stream.write(documentByteArray, 0, documentByteArray.length);
						listByteArray.add( stream );*/
						
						try{
							
							// -----------------------------------------------------------
							// REPORTE 4305A
							// -----------------------------------------------------------
							documentByteArray = (byte[]) documentosService.getDocumentoReporte4305A(asignacionNSS, firmaElectronica, usuario, OrigenSolicitudEnum.VENTANILLA.getId());
							firmaDigitalBusiness.guardarArchivoFirmado(
									reciboNotarial,
									"D_4305A.pdf", documentByteArray);
							
						}catch(Exception e){
							log.debug("D_4305A");
							log.debug(e);
						}
						
						
						/*stream = new ByteArrayOutputStream(documentByteArray.length);
						stream.write(documentByteArray, 0, documentByteArray.length);
						listByteArray.add( stream );*/
						
						try{
							
							// ---------------------------------------
							// SAV002
							// ---------------------------------------
							documentByteArray = (byte[]) documentosService.generaSav002(asignacionNSS, firmaElectronica, usuario, propiedades.getIdTramite(),OrigenSolicitudEnum.VENTANILLA.getId(),true);
							firmaDigitalBusiness.guardarArchivoFirmado(
									reciboNotarial,
									"sav002.pdf", documentByteArray);
							
						}catch(Exception e){
							log.debug("sav002");
							log.debug(e);
						}
						
						/*stream = new ByteArrayOutputStream(documentByteArray.length);
						stream.write(documentByteArray, 0, documentByteArray.length);
						listByteArray.add( stream );*/
						
						try{
							// -----------------------------
							// SAVO005
							// -----------------------------
							documentByteArray = (byte[]) documentosService.getDocumentoSav005(asignacionNSS, firmaElectronica, propiedades.getIdDerechohabiente(), propiedades.getIdEstadoTramite(), OrigenSolicitudEnum.VENTANILLA.getId(), usuario);
							firmaDigitalBusiness.guardarArchivoFirmado(
									reciboNotarial,
									"sav005.pdf", documentByteArray);
							
						}catch(Exception e){
							log.debug("SAV005");
							log.debug(e);
						}
						
						/*
						stream = new ByteArrayOutputStream(documentByteArray.length);
						stream.write(documentByteArray, 0, documentByteArray.length);
						listByteArray.add( stream );*/
						
						/*firmaElectronica = this.generaFirmaElectronica(
								asignacionNSS, solicitud, "SAV006");
						documentByteArray = (byte[]) documentosService.getDocumentoSav006(asignacionNSS, firmaElectronica, usuario, propiedades.getIdTramite(), TipoTramiteEnum.CAMBIO_CLINICA.getCodigo());
						firmaDigitalBusiness.guardarArchivoFirmado(
								reciboNotarial,
								"sav006.pdf", documentByteArray);*/
						/*
						stream = new ByteArrayOutputStream(documentByteArray.length);
						stream.write(documentByteArray, 0, documentByteArray.length);
						listByteArray.add( stream );*/
				}
				outputStream = (ByteArrayOutputStream) documentosService.concatenarByteStream(listByteArray, true);
				documentByteArray = outputStream.toByteArray();
				
				nombreReporte = "DocumentosCambioClinica.pdf";
				break;
			case AUTORIZACION_SERVICIOS_CIRCUNSCRIPCION_FORANEA://Este tramite genera Cartilla, 4305a, SAV002, SAV017, SAV006
//				documentByteArray = (byte[]) documentosService.getDocumentosCambioConsultorio(asignacionNSS, firmaElectronica, 
//						propiedades.getIdTramite(), usuario, propiedades.getTitulo());
				firmaElectronica = this.generaFirmaElectronica(
						asignacionNSS, solicitud, "Cartilla nacional de salud");
				reciboNotarial = firmaElectronica.getReciboNotarial();
				
				documentByteArray = (byte[]) documentosService.getCartillaNacionalSalud(propiedades.getIdDerechohabiente(), asignacionNSS, firmaElectronica);
				stream = new ByteArrayOutputStream(documentByteArray.length);
				stream.write(documentByteArray, 0, documentByteArray.length);
				listByteArray.add( stream );
				
				firmaElectronica = this.generaFirmaElectronica(
						asignacionNSS, solicitud, "Trajeta de adscripcion 430A5");
				documentByteArray = (byte[]) documentosService.getDocumentoReporte4305A(asignacionNSS, firmaElectronica, usuario, OrigenSolicitudEnum.VENTANILLA.getId());
				stream = new ByteArrayOutputStream(documentByteArray.length);
				stream.write(documentByteArray, 0, documentByteArray.length);
				listByteArray.add( stream );
				
				firmaElectronica = this.generaFirmaElectronica(
						asignacionNSS, solicitud, "SAV002");
				documentByteArray = (byte[]) documentosService.generaSav002(asignacionNSS, firmaElectronica, usuario, propiedades.getIdTramite(),OrigenSolicitudEnum.VENTANILLA.getId(),true);
				stream = new ByteArrayOutputStream(documentByteArray.length);
				stream.write(documentByteArray, 0, documentByteArray.length);
				listByteArray.add( stream );
				
				firmaElectronica = this.generaFirmaElectronica(
						asignacionNSS, solicitud, "SAV017");
				documentByteArray = (byte[]) documentosService.getDocumentoSav017(propiedades.getIdDerechohabiente(), asignacionNSS, firmaElectronica, propiedades.getAutorizacion(), null, OrigenSolicitudEnum.VENTANILLA.getId());
				stream = new ByteArrayOutputStream(documentByteArray.length);
				stream.write(documentByteArray, 0, documentByteArray.length);
				listByteArray.add( stream );
				
				firmaElectronica = this.generaFirmaElectronica(
						asignacionNSS, solicitud, "SAV006");
				documentByteArray = (byte[]) documentosService.getDocumentoSav006(asignacionNSS, firmaElectronica, usuario, propiedades.getIdTramite(), TipoTramiteEnum.AUTORIZACION_SERVICIOS_CIRCUNSCRIPCION_FORANEA.getCodigo());
				stream = new ByteArrayOutputStream(documentByteArray.length);
				stream.write(documentByteArray, 0, documentByteArray.length);
				listByteArray.add( stream );
				
				outputStream = (ByteArrayOutputStream) documentosService.concatenarByteStream(listByteArray, true);
				documentByteArray = outputStream.toByteArray();
				
				nombreReporte = "DocumentosCambioConsultorio.pdf";
				break;
			case SUSPENSION_SERVICIOS_CIRCUNSCRIPCION_FORANEA://Este tramite genera SAV017, SAV006
//				documentByteArray = (byte[]) documentosService.getDocumentosCircunscripcion(asignacionNSS, firmaElectronica, 
//						propiedades.getIdTramite(), propiedades.getAutorizacion(), propiedades.getTitulo(), usuario);
				firmaElectronica = this.generaFirmaElectronica(
						asignacionNSS, solicitud, "SAV017");
				reciboNotarial = firmaElectronica.getReciboNotarial();
				
				documentByteArray = (byte[]) documentosService.getDocumentoSav017(propiedades.getIdDerechohabiente(), asignacionNSS, firmaElectronica, propiedades.getAutorizacion(), null, OrigenSolicitudEnum.VENTANILLA.getId());
				stream = new ByteArrayOutputStream(documentByteArray.length);
				stream.write(documentByteArray, 0, documentByteArray.length);
				listByteArray.add( stream );
				
				firmaElectronica = this.generaFirmaElectronica(
						asignacionNSS, solicitud, "SAV006");
				documentByteArray = (byte[]) documentosService.getDocumentoSav006(asignacionNSS, firmaElectronica, usuario, propiedades.getIdDerechohabiente(), TipoTramiteEnum.SUSPENSION_SERVICIOS_CIRCUNSCRIPCION_FORANEA.getCodigo());
				stream = new ByteArrayOutputStream(documentByteArray.length);
				stream.write(documentByteArray, 0, documentByteArray.length);
				listByteArray.add( stream );
				
				outputStream = (ByteArrayOutputStream) documentosService.concatenarByteStream(listByteArray, true);
				documentByteArray = outputStream.toByteArray();
				
				nombreReporte = "DocumentosCircunscipcion.pdf";
				break;
			case CAMBIO_CONSULTORIO_TURNO://Este tramite genera Cartilla, 4305a
//				documentByteArray = (byte[]) documentosService.getDocumentoRegistroDerechohabientesDep(asignacionNSS, firmaElectronica, propiedades.getIdTramite(), 
//						propiedades.getPersonas(), propiedades.getTipoTramite(), propiedades.getTitulo(), usuario);
				firmaElectronica = this.generaFirmaElectronica(
						asignacionNSS, solicitud, "Cartilla nacional de salud");
				reciboNotarial = firmaElectronica.getReciboNotarial();
				
				documentByteArray = (byte[]) documentosService.getCartillaNacionalSalud(propiedades.getIdDerechohabiente(), asignacionNSS, firmaElectronica);
				stream = new ByteArrayOutputStream(documentByteArray.length);
				stream.write(documentByteArray, 0, documentByteArray.length);
				listByteArray.add( stream );
				
				firmaElectronica = this.generaFirmaElectronica(
						asignacionNSS, solicitud, "Trajeta de adscripcion 430A5");
				documentByteArray = (byte[]) documentosService.getDocumentoReporte4305A(asignacionNSS, firmaElectronica, usuario, OrigenSolicitudEnum.VENTANILLA.getId());
				stream = new ByteArrayOutputStream(documentByteArray.length);
				stream.write(documentByteArray, 0, documentByteArray.length);
				listByteArray.add( stream );
				
				outputStream = (ByteArrayOutputStream) documentosService.concatenarByteStream(listByteArray, true);
				documentByteArray = outputStream.toByteArray();
				
				nombreReporte = "DocumentosDerechohabientesDep.pdf";
				break;
			case BAJA_DE_DERECHOHABIENTE_POR_DEFUNCION://Este tramite genera SAV002
			case BAJA_DE_DERECHOHABIENTE_POR_TERMINO_DE_CONCUBINATO://Este tramite genera SAV002
			case BAJA_DE_DERECHOHABIENTE_POR_DIVORCIO://Este tramite genera SAV002
			case BAJA_DE_DERECHOHABIENTE_POR_TERMINO_DE_CONVIVENCIA://Este tramite genera SAV002
				firmaElectronica = this.generaFirmaElectronica(
						asignacionNSS, solicitud, solicitud.getTramites().get(0).getTipoTramite().getDescripcion());
				reciboNotarial = firmaElectronica.getReciboNotarial();
				
				solicitud.setSolicitante(usuario);
				solicitud.setCadenaOriginal(firmaElectronica.getCadenaOriginal());
				solicitud.setSecuenciaDeNotaria(firmaElectronica.getSecuenciaNotaria());
				solicitud.setSelloDigital(firmaElectronica.getRecibo());
				solicitud.setNumeroSerieCertificado(firmaElectronica.getSerialCertificado());
				solicitud.setOrigenSolicitud(new OrigenSolicitud());
				solicitud.getOrigenSolicitud().setIdTipoSolicitud(OrigenSolicitudEnum.VENTANILLA.getId());
				
				Long idTramiteBaja = solicitud.getTramites().get(0).getTramiteId();
				
				documentByteArray = solicitudBusiness.obtenerDocumentoResultante(solicitud, idTramiteBaja, DocumentoPorTipoEnum.SAV002.getId().intValue());
				stream = new ByteArrayOutputStream(documentByteArray.length);
				stream.write(documentByteArray, 0, documentByteArray.length);
				listByteArray.add( stream );
				
				/*
				documentByteArray = (byte[]) documentosService.generaSav002(asignacionNSS, firmaElectronica, usuario, propiedades.getIdTramite(),OrigenSolicitudEnum.VENTANILLA.getId(),false);
				stream = new ByteArrayOutputStream(documentByteArray.length);
				stream.write(documentByteArray, 0, documentByteArray.length);
				listByteArray.add( stream );*/
				
				outputStream = (ByteArrayOutputStream) documentosService.concatenarByteStream(listByteArray, true);
				documentByteArray = outputStream.toByteArray();
				
				nombreReporte = "DocumentosBajaDerechohabiente.pdf";
				
				guardarCompleto = false;
				break;
			case BAJA_DE_DERECHOHABIENTE_POR_AUTORIDAD_NORMATIVA:
				firmaElectronica = this.generaFirmaElectronica(
						asignacionNSS, solicitud, "BAJA DE DERECHOHABIENTE POR AUTORIDAD NORMATIVA");
				
				solicitud.setSolicitante(usuario);
				solicitud.setCadenaOriginal(firmaElectronica.getCadenaOriginal());
				solicitud.setSecuenciaDeNotaria(firmaElectronica.getSecuenciaNotaria());
				solicitud.setSelloDigital(firmaElectronica.getRecibo());
				solicitud.setNumeroSerieCertificado(firmaElectronica.getSerialCertificado());
				
				Long idTramite = solicitud.getTramites().get(0).getTramiteId();
				
				documentByteArray = solicitudBusiness.obtenerDocumentoResultante(solicitud, idTramite, DocumentoPorTipoEnum.SAV002.getId().intValue());
				stream = new ByteArrayOutputStream(documentByteArray.length);
				stream.write(documentByteArray, 0, documentByteArray.length);
				listByteArray.add( stream );
				
				documentByteArray = solicitudBusiness.obtenerDocumentoResultante(solicitud, idTramite, DocumentoPorTipoEnum.COMPROBANTE_VIGENCIA_DERECHOS.getId().intValue());
				stream = new ByteArrayOutputStream(documentByteArray.length);
				stream.write(documentByteArray, 0, documentByteArray.length);
				listByteArray.add( stream );
				
				/*
				documentByteArray = (byte[]) documentosService.generaSav002(asignacionNSS, firmaElectronica, usuario, propiedades.getIdTramite(),OrigenSolicitudEnum.VENTANILLA.getId(),true);
				stream = new ByteArrayOutputStream(documentByteArray.length);
				stream.write(documentByteArray, 0, documentByteArray.length);
				listByteArray.add( stream );
				
				firmaDigitalBusiness.guardarArchivoFirmado(firmaElectronica.getSecuenciaNotaria(), "SAV_002.pdf", documentByteArray);
				solicitudBusiness.actualizarDocumentosTramite(propiedades.getIdTramite(), DocumentoPorTipoEnum.SAV002.getId(), documentByteArray);

				documentByteArray = (byte[]) documentosService.getComprobanteVigenciaDerechos(asignacionNSS,
						firmaElectronica, usuario);
		
				firmaDigitalBusiness.guardarArchivoFirmado(firmaElectronica.getSecuenciaNotaria(), "COMPROBANTE_VIGENCIA_DERECHOS.pdf", documentByteArray);
				solicitudBusiness.actualizarDocumentosTramite(propiedades.getIdTramite(), DocumentoPorTipoEnum.COMPROBANTE_VIGENCIA_DERECHOS.getId(), documentByteArray);

				
				stream = new ByteArrayOutputStream(documentByteArray.length);
				stream.write(documentByteArray, 0, documentByteArray.length);
				listByteArray.add( stream );
				*/
				outputStream = (ByteArrayOutputStream) documentosService.concatenarByteStream(listByteArray, true);
				documentByteArray = outputStream.toByteArray();
			
				nombreReporte = "documentosBajaNormativa.pdf";
				
				guardarCompleto = false;
				break;
			case PRORROGA_POR_ESTUDIOS://Este tramite genera SAV007
			case PRORROGA_POR_ENFERMEDAD_CRONICA_PSIQUICA_FISICA:
			case PRORROGA_POR_VIGENCIA_PERMANENTE://Prórroga para padres por fallecimiento del asegurado o pensionado:
			case PRORROGA_POR_VIGENCIA_TEMPORAL://Prórroga por trámite de pensión:
			case PRORROGA_POR_ACUERDOS_HCCD_HCT://Este tramite genera SAV007
			case PRORROGA_POR_SERVICIOS_OBSTETRICOS://Este tramite genera SAV007
			case PRORROGA_POR_LAUDO://Este tramite genera SAV007
				
				//La llamada se hace a getDocumentosProrroga
				firmaElectronica = this.generaFirmaElectronica(
						asignacionNSS, solicitud, solicitud.getTramites().get(0).getTipoTramite().getDescripcion());
				
				solicitud.setSolicitante(usuario);
				solicitud.setCadenaOriginal(firmaElectronica.getCadenaOriginal());
				solicitud.setSecuenciaDeNotaria(firmaElectronica.getSecuenciaNotaria());
				solicitud.setSelloDigital(firmaElectronica.getRecibo());
				solicitud.setNumeroSerieCertificado(firmaElectronica.getSerialCertificado());
				
				reciboNotarial = firmaElectronica.getReciboNotarial();
				
				Long idTramiteProrroga = solicitud.getTramites().get(0).getTramiteId();
				
				documentByteArray = solicitudBusiness.obtenerDocumentoResultante(solicitud, idTramiteProrroga, DocumentoPorTipoEnum.SAV007.getId().intValue());
				stream = new ByteArrayOutputStream(documentByteArray.length);
				stream.write(documentByteArray, 0, documentByteArray.length);
				listByteArray.add( stream );
				/*
				documentByteArray = (byte[]) documentosService.getDocumentoSav007(asignacionNSS, firmaElectronica, propiedades.getIdDerechohabiente());
				stream = new ByteArrayOutputStream(documentByteArray.length);
				stream.write(documentByteArray, 0, documentByteArray.length);
				listByteArray.add( stream );*/
				
				outputStream = (ByteArrayOutputStream) documentosService.concatenarByteStream(listByteArray, true);
				documentByteArray = outputStream.toByteArray();
				
				nombreReporte = "DocumentosProrroga.pdf";
				guardarCompleto = false;
				
				break;
			default:
				break;
			}
			
			if(guardarCompleto) {
	
				// 3. Se guarda el reporte
				firmaDigitalBusiness.guardarArchivoFirmado(
						reciboNotarial,
						nombreReporte, documentByteArray);
			}
		} catch (SolicitudNoValidaException e) {
			e.printStackTrace();
			throw new DocumentoException(e.getMessage());
		} catch (SolicitudException e) {
			e.printStackTrace();
			throw new DocumentoException(e.getMessage());
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
			throw new DocumentoException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DocumentoException(e.getMessage());
		}
		return documentByteArray;

	}
	
	
	@Override
	public void insertarFirmaDigital(Solicitud solicitud) {
		firmaDigitalBusiness.insertarSolicitudFirmaDigital(solicitud, solicitud.getFirmaElectronica());
	}

	@Override
	public FirmaElectronica generaFirmaElectronica(AsignacionNSS asignacionNSS,
			Solicitud solicitud, String nombreTramite) throws DocumentoException {

		FirmaElectronica firmaElectronica = null;
		
		firmaElectronica = firmaDigitalBusiness.getFirmaElectronica(solicitud);
		
		if(firmaElectronica == null) {
			//Solo si la solicitud no tiene firma, se debe generar una
			if( solicitud.getFirmaElectronica() == null ){
				String cadenaOriginal = this.generaCadenaOriginal(asignacionNSS,
						solicitud, nombreTramite);
	
				RespuestaFirmadoSimple firmadoSimple = firmaDigitalBusiness
						.getSelloDigital(cadenaOriginal, null, null);
	
				this.log.debug("Tramite: " + firmadoSimple.getTramite());
	
				firmaElectronica = firmaDigitalBusiness
						.convertirRespuestaFirmadoSimple(cadenaOriginal, firmadoSimple);
			}else{
				firmaElectronica = solicitud.getFirmaElectronica();
			}
	
			firmaDigitalBusiness.insertarSolicitudFirmaDigital(solicitud,
					firmaElectronica);
		}
		return firmaElectronica;
	}
	
	@Override
	public void enviaCorreo(AsignacionNSS asignacionNSS, byte[] atachDocto, String url) throws Exception{
		eMailService.enviaCorreoReporteVigenciaDerechos(asignacionNSS, atachDocto, null);
	}
	
	@Override
	public Object getDocumentoPorTipoIdTramite(Long idTramite, Long idDocumentoPorTipo){
		return solicitudEntity.getDocumentoPorTipoIdTramite(
				idTramite, DocumentoPorTipoEnum.COMPROBANTE_VIGENCIA_DERECHOS.getId());
	}

	/**
	 * Este metodo de utileria sirve para la generacion de un tramite y
	 * solicitud
	 * 
	 * @param fisica
	 * @param origenAsignacion
	 * @param usuario
	 * @return
	 * @throws SolicitudNoValidaException
	 * @throws SolicitudException
	 */
	@Override
	public Solicitud crearTramiteSolicitud(Fisica fisica,
			Long idOrigenSolicitud, Usuario usuario,
			Map<String, Integer> identificadoresMap)
			throws SolicitudNoValidaException, SolicitudException {

		this.log.info("Se va a crear un tramite de tipo: "
				+ identificadoresMap.get("tramite") + ", solicitud de tipo: "
				+ identificadoresMap.get("solicitud")
				+ " para la persona  [idPersona:" + fisica.getIdPersona()
				+ ", curp:" + fisica.getCurp() + "] de NSS con origen "
				+ idOrigenSolicitud);

		TramiteAsegurado tramiteAsegurado = new TramiteAsegurado();
		Solicitud solicitud = new Solicitud();
		AsignacionNSS asignacionNSS = new AsignacionNSS();
		List<Tramite> tramites = new ArrayList<Tramite>();
		Date fechaActual = new Date();

		try {
			ConvertUtils.register(new DateConverter(null), Date.class);
			BeanUtils.copyProperties(asignacionNSS, fisica);
			tramiteAsegurado.setFisica(asignacionNSS);

			if (fisica.getIdPersona() == null) {
				asignacionNSS.setIdPersona(null);
			}

			this.log.debug("Copia de parametros para solicitud exitosa");

		} catch (IllegalAccessException e) {
			this.log.error(e.getStackTrace());
		} catch (InvocationTargetException e) {
			this.log.error(e.getStackTrace());
		}

		TipoSolicitud tipoSolicitud = new TipoSolicitud();

		tipoSolicitud.setIdTipoSolicitud(identificadoresMap.get("solicitud")
				.longValue());
		solicitud.setTipoSolicitud(tipoSolicitud);

		EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
		estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.ATENDIDA
				.getCodigo());
		solicitud.setEstadoSolicitud(estadoSolicitud);

		solicitud.setFechaSolicitud(fechaActual);
		solicitud.setFechaPresentacion(fechaActual);
		solicitud.setFechaConclusion(fechaActual);

		// Se settea el origen de la solicitud
		OrigenSolicitud origenSolicitud = new OrigenSolicitud();
		origenSolicitud.setIdTipoSolicitud(idOrigenSolicitud);
		solicitud.setOrigenSolicitud(origenSolicitud);

		tramiteAsegurado.setFechaTramite(fechaActual);
		tramiteAsegurado.setFechaPresentacion(fechaActual);
		tramiteAsegurado.setFechaConclusion(fechaActual);

		TipoTramite tipoTramite = new TipoTramite();
		tipoTramite.setIdTipoTramite(identificadoresMap.get("tramite"));
		tramiteAsegurado.setTipoTramite(tipoTramite);

		EstadoTramite estadoTramite = new EstadoTramite();
		estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO
				.getCodigo());
		estadoTramite
				.setDescripcion(EstadoTramiteEnum.CERRADO.getDescripcion());
		tramiteAsegurado.setEstadoTramite(estadoTramite);

		tramites.add(tramiteAsegurado);

		solicitud.setTramites(tramites);
		
		if(fisica != null && fisica.getIdPersona() != null) {
			PersonaInteresadaSolicitud personaIntSol = new PersonaInteresadaSolicitud();
			personaIntSol.setPersona(fisica);
			TipoPerInteresadaSol tipoPersona = new TipoPerInteresadaSol();
			tipoPersona.setCveTipoInteresadaSol(TipoPersonaInteresadaSolEnum.ASEGURADO_PENSIONADO.getId());
			personaIntSol.setTipoPersonaInteresadaSol(tipoPersona);
			
			solicitud.setPersonaInteresadaSolicitud(personaIntSol);

		}
		/*
		 * se setea el usuario siempre para saber quien fue el que realizo el
		 * tramite
		 */
		// if (origenAsignacion.getId() ==
		// OrigenSolicitudEnum.VENTANILLA.getId()
		if (usuario != null) {

			UsuarioFuncionario uf = usuario.getUsuarioFuncionario();

			if (uf != null && uf.getSubdelegacion() != null
					&& uf.getSubdelegacion().getId() != null
					&& !uf.getSubdelegacion().getId().equals(-1)) {

				/*
				 * Debido a que a nivel base de datos, sólo se puede relacionar
				 * una solicitud a nivel subdelegacional, no se toma en cuenta
				 * el nivel delegacional
				 */
				solicitud.setSubdelegacion(uf.getSubdelegacion());

				this.log.debug("El usuario tiene nivel subdelegacional");
			}
			this.log.debug("el usuario es" + usuario.getUsuario());
			solicitud.setSolicitante(usuario);
			Long idUmfSol = null;
			
			if(usuario.getIdUmf()!= null) {
				idUmfSol = usuario.getIdUmf();
			} else if(usuario.getUsuarioFuncionario() != null && usuario.getUsuarioFuncionario().getUnidadMedicaFamiliar() != null) {
				idUmfSol = usuario.getUsuarioFuncionario().getUnidadMedicaFamiliar().getIdUMF();
			}
			
			if(idUmfSol != null) {
				CitaSolicitud cita = new CitaSolicitud();
				cita.setUmf(new UnidadMedicaFamiliar());
				cita.setTurno(new Turno());
				cita.getUmf().setIdUMF(idUmfSol);
				cita.getTurno().setIdTurno(1L);
				
				solicitud.setCitaSolicitud(cita);
			}
		//se cambio el seteo de la propiedad del usuario por si no llega se setan los datos de la persona 
			//que realia el ramite validando si tiene CURP
		}else if (StringUtils.isNotEmpty(fisica.getCurp()) ){
			Usuario usuarioPF = new Usuario();
			usuarioPF.setUsuario(fisica.getCurp());
			solicitud.setSolicitante(usuarioPF);
			
		}

		// Se crea la solicitud en base de datos
		solicitud = this.solicitudBusiness.crear(solicitud);

		this.log.debug("Se creo exitosamente la solicitud de consulta de reporte de vigencia");

		return solicitud;
	}

	/**
	 * Metodo de utileria para la generacion de cadenas originales delos
	 * documentos generados en este servicio
	 */
	private String generaCadenaOriginal(Fisica fisica, Solicitud solicitud, String nombreTramite) {
		Locale locMEX = new Locale("es", "MX");

		String nombre = fisica.getNombre() != null ? fisica.getNombre().trim()
				: "";
		String apellidoP = fisica.getPrimerApellido() != null ? fisica
				.getPrimerApellido().trim() : "";
		String apellidoM = fisica.getSegundoApellido() != null ? fisica
				.getSegundoApellido().trim() : "";

		String nombreCompleto = nombre + " " + apellidoP + " " + apellidoM;

		SimpleDateFormat sdf = new SimpleDateFormat(
				"dd 'de' MMMM yyyy, HH:mm:ss", locMEX);

		StringBuilder cadenaOriginal = new StringBuilder(
				"||Invocante:portalimssdigital");
		cadenaOriginal.append("|Tipo de trámite:" + nombreTramite);
		cadenaOriginal.append("|Fecha:" + sdf.format(new Date()));
		cadenaOriginal.append("|Folio:" + solicitud.getNoFolioSolicitud());
		if(!StringUtils.isBlank(fisica.getRfc())) {
			cadenaOriginal.append("|RFC:"+ (fisica.getRfc() != null ? fisica.getRfc() : ""));
		}
		
		cadenaOriginal.append("|Nombre o Razón Social:" + nombreCompleto);
		cadenaOriginal.append("|Curp:"
				+ (fisica.getCurp() != null ? fisica.getCurp() : ""));
		cadenaOriginal.append("|Número de Seguridad Social:" + fisica.getNss()
				+ "||");

		
		
		return cadenaOriginal.toString();
	}

	@Override
	public void generarSolicitudRechazo(Long idOrigenSolicitud,String curp,String observaciones) {
		Solicitud solicitud = new Solicitud();
		Date fecha = new Date();
		TipoSolicitud tipoSolicitud = new TipoSolicitud();

		tipoSolicitud.setIdTipoSolicitud(TipoSolicitudEnum.COMPROBANTE_DE_VIGENCIA_DE_DERECHOS.getValor().longValue());
		solicitud.setTipoSolicitud(tipoSolicitud);

		EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
		estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.RECHAZADA.getCodigo());
		solicitud.setEstadoSolicitud(estadoSolicitud);

		solicitud.setFechaSolicitud(fecha);
		solicitud.setFechaPresentacion(fecha);
		solicitud.setFechaConclusion(fecha);
		solicitud.setSolicitante(new Usuario());
		solicitud.getSolicitante().setCveIdUsuario(curp);
		solicitud.getSolicitante().setUsuario(curp);
		String obs = observaciones != null ? (observaciones.length() > 2050 ? observaciones.substring(0, 2050) : observaciones) : "";
		solicitud.setObservacion(obs);
		
		Tramite tramite = new Tramite();
		tramite.setFechaTramite(fecha);
		tramite.setFechaPresentacion(fecha);
		tramite.setFechaConclusion(fecha);

		TipoTramite tipoTramite = new TipoTramite();
		tipoTramite.setIdTipoTramite(TipoTramiteEnum.COMPROBANTE_DE_VIGENCIA_DE_DERECHOHAB
				.getCodigo());
		tramite.setTipoTramite(tipoTramite);

		EstadoTramite estadoTramite = new EstadoTramite();
		estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.CANCELADO.getCodigo());
		tramite.setEstadoTramite(estadoTramite);
		String observacionesTramites = obs.length() > 250 ? obs.substring(0,250) : obs;
		tramite.setObservacion(observacionesTramites);
		// Se settea el origen de la solicitud
		OrigenSolicitud origenSolicitud = new OrigenSolicitud();
		origenSolicitud.setIdTipoSolicitud(idOrigenSolicitud);
		solicitud.setOrigenSolicitud(origenSolicitud);
		solicitud.setTramites(new ArrayList<Tramite>());
		solicitud.getTramites().add(tramite);
		// Se crea la solicitud en base de datos
		try {
			solicitud = this.solicitudBusiness.crear(solicitud);
		} catch (SolicitudNoValidaException e) {
			log.error("Error al crear la solicitud de rechazo", e);
		}

	}

	@Override
	public Map<String, Object> generaTramiteDocumentoReporteConstanciaVigencia(AsignacionNSS asignacionNSS, Usuario usuario,
			Map<String, Integer> identificadoresMap, boolean enviaMail)
			throws DocumentoException {
		
		return this.generaConstancia(asignacionNSS, usuario, identificadoresMap, enviaMail,false);

			
	}
	
	@Override
	public Map<String, Object> generaTramiteDocumentoReporteConstanciaVigenciaWS(AsignacionNSS asignacionNSS, Usuario usuario, Map<String, Integer> identificadoresMap, boolean enviaMail) throws DocumentoException {

		if(asignacionNSS.getIdAsignacionNSS() == null || asignacionNSS.getIdAsignacionNSS().intValue() == 0){
			try {
				GrupoFamiliar grupoFamiliar =  vigenciaDerechosWS.getInfoAsegurado(asignacionNSS.getNss());
				asignacionNSS = grupoFamiliar.getAsignacionNSS();
			} catch (DerechohabientesWebSserviceException e) {
				throw new DocumentoException(e.getMessage());
			}
		}
		
		return this.generaConstancia(asignacionNSS, usuario, identificadoresMap, enviaMail, true);
			
	}
	
	private Map<String, Object> generaConstancia(AsignacionNSS asignacionNSS, Usuario usuario,
			Map<String, Integer> identificadoresMap, boolean enviaMail, boolean infoWS) throws DocumentoException {
		
		byte[] documentByteArray = null;
		Map<String, Object> resultado = new HashMap<String, Object>();
		String nss = asignacionNSS.getNssStr();
		asignacionNSS.setNss(nss);
		
		try {

			// 1. Se debe crear un tramite y solicitud.
			Integer origenSolicitud = identificadoresMap.get("origenSolicitud");
			if(origenSolicitud==null) {
				origenSolicitud = OrigenSolicitudEnum.INTERNET.getId().intValue();
			}
	
			log.debug("voy a crear la solicitud");
			// asignacionNSS es de tipo AsignacionNSS que extiende de Fisica
			// por lo que puede pasarse como parametro para crear la solicitud
			Solicitud solicitud = this.crearTramiteSolicitud(asignacionNSS,
					origenSolicitud.longValue(), usuario, identificadoresMap);
	
			log.debug("Termino de generar la solicitud");
			// 2. Obtener sello digital
			// Se obtiene el sello digital
			// Se genera cadena original
			asignacionNSS.setNss(nss);
			log.debug("voy a guardar en notaria");
			FirmaElectronica firmaElectronica = this.generaFirmaElectronica(
					asignacionNSS, solicitud, "COMPROBANTE DE VIGENCIA DE DERECHOHABIENTES");
			log.debug("termino de guardar en notaria");
		
			log.debug("Voy a generar el pdf");
			
			if(infoWS) {
				// 3. Generacion del reporte con informacion del sello digital
				documentByteArray = (byte[]) documentosService.getConstanciaVigenciaWS(asignacionNSS, firmaElectronica, usuario);
			} else {
				// 3. Generacion del reporte con informacion del sello digital
				documentByteArray = (byte[]) documentosService.getConstanciaVigenciaInternetRecortado(asignacionNSS,firmaElectronica, usuario);
			}
			log.debug("Termino de generar el pdf");
			
			log.debug("Voy a giardar en notaria");
			// 4. Se guarda el reporte
			firmaDigitalBusiness.guardarArchivoFirmado(
					firmaElectronica.getReciboNotarial(),
					"reporteVigencia"+nss+".pdf", documentByteArray);
			log.debug("Termino de guardar en notaria");
			
			Long tramiteId = solicitud.getTramites().get(0).getTramiteId();
			
			/*solicitudEntity.actualizarDocumentosTramite(tramiteId, 
					DocumentoPorTipoEnum.COMPROBANTE_VIGENCIA_DERECHOS.getId(), documentByteArray);*/
			
			resultado.put("documento", documentByteArray);
			resultado.put("tramiteId", tramiteId);
			resultado.put("folio", solicitud.getNoFolioSolicitud());
			resultado.put("idSolicitud", solicitud.getSolicitudId().toString());
			resultado.put("secuenciaNotarial",firmaElectronica.getSecuenciaNotaria());
	
	
		} catch (SolicitudNoValidaException e) {
			throw new DocumentoException(e.getMessage());
		} catch (SolicitudException e) {
			throw new DocumentoException(e.getMessage());
		} catch (DerechohabientesBusinessException e) {
			throw new DocumentoException(e.getMessage());
		} catch (Exception e) {
			throw new DocumentoException(e.getMessage());
		}
		return resultado;
	}
}
