/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Hï¿½ctor Lara Andrï¿½s
 *  @Proyecto: delta
 *  @Archivo: DatosClemServiceBusiness.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.clem
 *  @Fecha:17/08/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.clem;

import static mx.gob.imss.ctirss.delta.gestion.clasificacion.util.Constantes.CLAVE_DEL_VERACRUZ_SUR;
import static mx.gob.imss.ctirss.delta.gestion.clasificacion.util.Constantes.CLAVE_SUBDEL_COATZACOALCOS;
import static mx.gob.imss.ctirss.delta.gestion.clasificacion.util.Constantes.CLEM_POSFIJO_DELEGACIONAL;
import static mx.gob.imss.ctirss.delta.gestion.clasificacion.util.Constantes.CLEM_POSFIJO_SUBDELEGACIONAL;
import static mx.gob.imss.ctirss.delta.gestion.clasificacion.util.Constantes.PATH_REPORTES;
import static mx.gob.imss.ctirss.delta.gestion.clasificacion.util.Constantes.PDF_DELEGACIONAL_INICIAL;
import static mx.gob.imss.ctirss.delta.gestion.clasificacion.util.Constantes.PDF_DELEGACIONAL_MODIFICACION;
import static mx.gob.imss.ctirss.delta.gestion.clasificacion.util.Constantes.PDF_SUBDELEGACIONAL_INICIAL;
import static mx.gob.imss.ctirss.delta.gestion.clasificacion.util.Constantes.PDF_SUBDELEGACIONAL_MODIFICACION;
import static mx.gob.imss.ctirss.delta.model.clasificacion.TipoCausaAnalisisEnum.RECTIFICACION_DE_LA_CLASIFICACION_INICIAL;
import static mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum.RECTIFICACION_DE_LA_CLASIFICACION_POR_PROCESO_DE_ANALISIS;
import static mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum.CERRADO;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.clasificacion.AnalisisNoEncontradoException;
import mx.gob.imss.ctirss.delta.exception.clasificacion.ClemCaracterException;
import mx.gob.imss.ctirss.delta.exception.clasificacion.DatosClemException;
import mx.gob.imss.ctirss.delta.exception.clasificacion.PatronNoEncontradoException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.ClemVO;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.DictamenClasificacion;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.FirmaClemDTO;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.ResolucionVO;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.analisis.AnalisisServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.analisis.TipoCausaAnalisisServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.articulo.ArticuloServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.bitacora.BitacoraServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.clasificacion.ClasifPropDictServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.clem.DatosClemServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.domicilio.DelegacionServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.domicilio.DomicilioMigrServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.domicilio.SubDelegacionServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis.AnalisisServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis.TipoCausaAnalisisServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.articulo.ArticuloServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clasificacion.ClasificacionPropuestaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clem.Articulo155ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clem.DatosClemServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.bitacora.BitacoraServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.clem.DatosClemServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.util.Constantes;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.clasificacion.actividad.economica.ActividadEcServiceRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.clasificacion.AnalisisClasificacionEmpresas;
import mx.gob.imss.ctirss.delta.model.clasificacion.Articulo155;
import mx.gob.imss.ctirss.delta.model.clasificacion.ArticuloModel;
import mx.gob.imss.ctirss.delta.model.clasificacion.DatosClem;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisEnum;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisModel;
import mx.gob.imss.ctirss.delta.model.clasificacion.ReporteClemBean;
import mx.gob.imss.ctirss.delta.model.clasificacion.SubdelegacionRimss;
import mx.gob.imss.ctirss.delta.model.clasificacion.TipoDatosClemEnum;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CentroTrabajo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

@Stateless(name = "datosClemServiceBusiness", mappedName = "datosClemServiceBusiness")

public class DatosClemServiceBusiness extends AbstractServiceBusiness  implements DatosClemServiceBusinessRemote {

	final Logger logger = LoggerFactory.getLogger(DatosClemServiceBusiness.class);
	
	@EJB
	private DatosClemServiceEntityLocal datosClemEntity;
	
	@EJB
	private DatosClemServiceUtilityLocal datosClemUtility;	
	
	@EJB
	private ArticuloServiceBusinessRemote articuloBusiness;
	
	@EJB
	private ArticuloServiceEntityLocal articuloService;
	
	@EJB
	private DelegacionServiceEntityLocal delegacionService;
	
	@EJB
	private SubDelegacionServiceEntityLocal subDelegacionService;
	
	@EJB
	private AnalisisServiceEntityLocal analisisServiceEntity;
	
	@EJB
	private AnalisisServiceBusinessRemote analisisBusiness;
	
	@EJB
	private ClasificacionPropuestaServiceBusinessRemote clasificacionPropuestaService;
	
	@EJB
	private Articulo155ServiceBusinessRemote articulo155ServiceBusiness;
	
	@EJB
	private TipoCausaAnalisisServiceBusinessRemote tipoCausaAnalisisServiceBusiness;
	
	@EJB
	private SolicitudServiceBusinessRemote solicitudServiceBusiness;
	
	@EJB
	private mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote solicitudPatronalService;
	
	@EJB
	private TipoCausaAnalisisServiceEntityLocal tipoCausaServiceEntity;
	
	@EJB
	private DomicilioMigrServiceEntityLocal domicilioMigrServiceEntity;
	
	@EJB
	private ActividadEcServiceRemote clasificacionActividadEconomicaBusiness;

	@EJB
	private BitacoraServiceEntityLocal bitacoraServiceEntity;
	
	@EJB
	ClasifPropDictServiceEntityLocal clasifPropDictEntity;

	@EJB
	private BitacoraServiceUtilityLocal bitacoraUtility;



	@Override
	public DatosClem insertaClem(DatosClem datosClem, ReporteClemBean reporteClemBean, Boolean insert) throws DatosClemException{
		DatosClem response = null;
		ArticuloModel articulo= new ArticuloModel();
		List<ArticuloModel> lstArticuloModel=new ArrayList<ArticuloModel>();
		Boolean existeClem=false;
		
		try{
			Delegacion delegacion = new Delegacion();
			delegacion.setId(new Long(datosClem.getCveDelegacion().longValue()));
			delegacion = delegacionService.consultaPorId(delegacion);
			datosClem.setCveDesDelegacion(delegacion.getClave());
			Subdelegacion subdelegacion = new Subdelegacion();
			subdelegacion.setDelegacion(delegacion);
			subdelegacion.setId(new Long(datosClem.getCveSubdelegacion().longValue()));
			subdelegacion = subDelegacionService.consultaPorId(subdelegacion);
			datosClem.setCveDesSubdelegacion(subdelegacion.getClave());
		}catch(Exception e){
			log.error("Problema al obtener la clave de la delegacion / subdelegacion");
			e.printStackTrace();
		}
		
		response=datosClemEntity.consultaPorClave(datosClem);
		if(response==null){
			existeClem=true;
		}else{
			existeClem=false;
		}

		if(existeClem){
			try{
				if(!(datosClem.getFolioResolucion()!=null && datosClem.getFolioResolucion().trim().length()>0)){
					/**
					 * Esta validaciï¿½n: if(insert)
					 * 	Es para evitar generar un Folio en caso de una Modificaciï¿½n, sï¿½lo se debe generar cuando es nuevo CLEM
					 */
					if(insert){
						datosClem.setFolioResolucion(datosClemUtility.generaFolioClem(datosClem));
					}
					articulo = obtieneArticulo(datosClem, 155);//Obteniendo la descripcion del artï¿½culo
					if(articulo==null){
						throw new Exception("No se encontro el artículo 155"); 
					}else{
						datosClem.setCveArticulo155(articulo.getCveIdArticulo());
					}
				}
				if(TipoDatosClemEnum.DELEGACIONAL.getClave()==datosClem.getCveTipoClem().intValue()){
					datosClem.setCveTipoDoc(new BigDecimal(TipoDatosClemEnum.DELEGACIONAL.getClave()));	
				}else{
					datosClem.setCveTipoDoc(new BigDecimal(TipoDatosClemEnum.SUBDELEGACIONAL.getClave()));
				}
				
				datosClem.setIndActivo(BigDecimal.ONE);
				datosClem.setUltFechaActualizacion(new Date());
				
				datosClem.setDesTitular(datosClem.getDesTitular()!=null?datosClem.getDesTitular():null);
				datosClem.setDesSuplente(datosClem.getDesSuplente()!=null?datosClem.getDesSuplente():null);
				datosClem.setPuesto(datosClem.getPuesto()!=null?datosClem.getPuesto():null);
				//Paso Folio generado al bean del reporte
				reporteClemBean.setFolio(datosClem.getFolioResolucion());
				
				response = datosClemEntity.crear(datosClem, insert);
				response=datosClemEntity.consultaPorClave(datosClem);
				response.setCveSubdelegacion(datosClem.getCveSubdelegacion());
				datosClemEntity.generaHistDatosClem(response, reporteClemBean.getCveIdTipoCausa());//Inserta en Histï¿½rico de CLEM
				
				response.setCveDelegacion(datosClem.getCveDelegacion());
				response.setCveSubdelegacion(datosClem.getCveSubdelegacion());
				response.setCveArticulo20(datosClem.getCveArticulo20());
				response.setCveArticulo26(datosClem.getCveArticulo26());
				response.setCveArticulo28(datosClem.getCveArticulo28());
				response.setCveArticulo155(datosClem.getCveArticulo155());
				response.setIncisoArticulo155(datosClem.getIncisoArticulo155());
				response.setDescFraccion115(datosClem.getDescFraccion115());
				if(insert){
					datosClem.setArticulos(articuloService.crearArticulos(response));
				}else{
					lstArticuloModel=articuloService.buscarPorIdClem(response.getCveIdClem().longValue());
					articuloService.actualizarArticulos(lstArticuloModel, response);
				}
			}catch (Exception e){
				logger.error("ERROR- " + e.getMessage());
				e.printStackTrace();
				throw new DatosClemException();
			}
			if(response!=null)
				response.setCveTipoClem(datosClem.getCveTipoClem());
		} else {
			try{
				/**
				 * Esta validaciï¿½n: if(insert)
				 * 	Es para evitar generar un Folio en caso de una Modificaciï¿½n, sï¿½lo se debe generar cuando es nuevo CLEM
				 */
				if(insert){
					datosClem.setFolioResolucion(datosClemUtility.generaFolioClem(datosClem));
				}
				datosClem.setCveAnalisis(response.getCveAnalisis());
				datosClem.setCveIdClem(response.getCveIdClem());
				reporteClemBean.setFolio(datosClem.getFolioResolucion());
				
				//datosClem.setRefDocumento(generaReporteClem(reporteClemBean, PATH_REPORTES + nomReporte).toByteArray());
				
				response=datosClemEntity.modificaClem(datosClem);
				response.setCveSubdelegacion(datosClem.getCveSubdelegacion());
				datosClemEntity.generaHistDatosClem(response, reporteClemBean.getCveIdTipoCausa());//Inserta en Histï¿½rico de CLEM
				/**
				 * Las siguientes dos lï¿½neas actualiza artï¿½culos
				 */
				lstArticuloModel=articuloService.buscarPorIdClem(datosClem.getCveIdClem().longValue());
				articuloService.actualizarArticulos(lstArticuloModel, datosClem);
				
				
			}catch(Exception exc){
				log.error("Error al actualizar la clem:" + exc.getMessage());
				throw new DatosClemException();
			}
		}
		return response;
	}
			
	@Override
	public DatosClem consultaClem(DatosClem model) throws DatosClemException{
		DatosClem response =null;
		int auxFolio;
		String auxTipoClem=null;
		try{
			response = datosClemEntity.consultaPorClave(model);
			if(response!= null){
				if(response.getFolioResolucion()!=null){
					auxFolio = response.getFolioResolucion().length();
					auxTipoClem =  response.getFolioResolucion().substring(auxFolio-2, auxFolio);
					log.debug("Tipo de clem: "+ auxTipoClem);
					if(CLEM_POSFIJO_DELEGACIONAL.compareTo(auxTipoClem)==0){
						response.setCveTipoClem(new BigDecimal(TipoDatosClemEnum.DELEGACIONAL.getClave()));
					}else if(CLEM_POSFIJO_SUBDELEGACIONAL.compareTo(auxTipoClem)==0){
						response.setCveTipoClem(new BigDecimal(TipoDatosClemEnum.SUBDELEGACIONAL.getClave()));					
					}
				}
			}
		}catch(Exception e){
			logger.error("ERROR- " + e.getMessage());
			e.printStackTrace();
			throw new DatosClemException("No se encontr\u00F3 el registro del clem.");
		}
		return response;
	}
	
	@Override
	public DatosClem insertaActualizacionClem(DatosClem model, ReporteClemBean reporteClemBean) throws DatosClemException{
		DatosClem response = null;
		String auxTipoClem = "";
		int auxFolio= 0;
		try{
			//Se asigna el estado de no activo
			model.setIndActivo(BigDecimal.ZERO);
			DatosClem actualizaciones = datosClemEntity.consultaPorClave(model);//.actualizaEstadosClem(model);			
			log.debug("Registros modificado:"+ actualizaciones.getCveIdClem() +", activo:"+actualizaciones.getIndActivo());
			model.setFolioResolucion(actualizaciones.getFolioResolucion());
			//Obteniendo tipo de clem
			auxFolio = actualizaciones.getFolioResolucion().length();
			auxTipoClem =  actualizaciones.getFolioResolucion().substring(auxFolio-2, auxFolio);
			log.debug("Tipo de clem a generar: "+ auxTipoClem);
			if(Constantes.CLEM_POSFIJO_DELEGACIONAL.compareTo(auxTipoClem)==0){
				model.setCveTipoClem(new BigDecimal(TipoDatosClemEnum.DELEGACIONAL.getClave()));	
			}else if(Constantes.CLEM_POSFIJO_SUBDELEGACIONAL.compareTo(auxTipoClem)==0){
				model.setCveTipoClem(new BigDecimal(TipoDatosClemEnum.SUBDELEGACIONAL.getClave()));					
			}
			model.setCveIdClem(actualizaciones.getCveIdClem());
			
			Long idTipoCausa=null;
			
			if (model.getCveArticulo28().trim().equals("I|31")){
				idTipoCausa=31L;
				model.setCveArticulo28("I");
			}else if (model.getCveArticulo28().trim().equals("I|37")){
				idTipoCausa=37L;
				model.setCveArticulo28("I");
			}else if (model.getCveArticulo28().trim().equals("I|33")){
				idTipoCausa=33L;
				model.setCveArticulo28("I");
			}else if (model.getCveArticulo28().trim().equals("I|39")){
				idTipoCausa=39L;
				model.setCveArticulo28("I");
			}else if (model.getCveArticulo28().trim().equals("I|40")){
				idTipoCausa=40L;
				model.setCveArticulo28("I");
			}else if (model.getCveArticulo28().trim().equals("I|38")){
				idTipoCausa=38L;
				model.setCveArticulo28("I");
			}else if (model.getCveArticulo28().trim().equals("I|41")){
				idTipoCausa=41L;
				model.setCveArticulo28("I");
			}else if (model.getCveArticulo28().trim().equals("II")){
				idTipoCausa=42L;
			}else if (model.getCveArticulo28().trim().equals("III")){
				idTipoCausa=35L;
			}else if (model.getCveArticulo28().trim().equals("IV")){
				idTipoCausa=36L;
			}else if (model.getCveArticulo28().trim().equals("V")){
				idTipoCausa=34L;
			}
			else if (model.getCveArticulo28().trim().equals("VI")){
				idTipoCausa=46L;
			
			}
			
			
			if(idTipoCausa!=null){
				analisisServiceEntity.actualizaTipoCausa(model.getCveAnalisis().longValue(), idTipoCausa);
			}
			reporteClemBean.setCveIdTipoCausa(idTipoCausa);
			response=this.insertaClem(model, reporteClemBean, false);
		}catch(Exception exc){
			log.error("Error al actualizar la clem:" + exc.getMessage());
			exc.printStackTrace();
			throw new DatosClemException();
		}
		return response;
	}
	
	private ArticuloModel obtieneArticulo(DatosClem datosClem, int numArticulo) throws DatosClemException {
		AnalisisClasificacionEmpresas analisisBean = new AnalisisClasificacionEmpresas();
		analisisBean.setCveIdAnalisis(new Long(datosClem.getCveAnalisis().longValue()));
		ArticuloModel articulo = null;
		try{
			articulo = new ArticuloModel(); 
			articulo.setNumArticulo(new BigDecimal(numArticulo));
			articulo.setCveIdDelegacion(BigInteger.valueOf(datosClem.getCveDelegacion().longValue()));
			articulo.setCveIdSubdelegacion(BigInteger.valueOf(datosClem.getCveSubdelegacion().longValue()));
			log.debug("Datos para busqueda del articulo, Delegacion:"+articulo.getCveIdDelegacion()+" Subdelegacion:"+articulo.getCveIdSubdelegacion());
			articulo = articuloBusiness.buscarPorDelegacionSubdelegacion(articulo);
		}catch (Exception e) {
			log.error("Se present\u00F3 un error al obtener los datos del patr\u00F3n: "+ e.getMessage());
			e.printStackTrace();
			throw new DatosClemException("Se present\u00F3 un error al obtener los datos del art\u00EDculo:"+ e.getMessage());
		}
			return articulo;
	}
	
	@Override
	public DatosClem actualizaEstadoClem(DatosClem model) throws DatosClemException {
		try{
			//Se asigna el estado de no activo
			model.setIndActivo(BigDecimal.ZERO);
			model = datosClemEntity.actualizaEstadosClem(model);
			
		}catch(Exception exc){
			log.error("Error al actualiza la clem:" + exc.getMessage());
			throw new DatosClemException();
		}
		return model;
	}

	@Override
	public ByteArrayOutputStream generaReporteClem(ReporteClemBean reporteClemBean, String nomReporte, String imgPath)throws DatosClemException{		

		Map<String, Object> parametros=llenaMapa();
		ResolucionVO resolucionVO = llenaResolucion(reporteClemBean);
		System.out.println("::::::::::::::::resolucionVO:::::::::::::::::::::::::");
		System.out.println(resolucionVO.toString());
		
		ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
		try {
			System.out.println(".......--->"+nomReporte);
			JasperReport report = (JasperReport) JRLoader.loadObject(Thread.currentThread().getContextClassLoader().getResourceAsStream(nomReporte));
			System.out.println("...report....--->"+report);
			System.out.println("...parametros....--->"+parametros);
			
			JasperPrint print=JasperFillManager.fillReport(report, parametros, new JRBeanArrayDataSource(new Object[] {resolucionVO}));
			
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);
			exporter.exportReport();
			
			
		}catch(JRException e){
			e.printStackTrace();
			System.out.println(".....Error....." + e.getMessage());
			log.debug("err:: generaReporteClem - ");
		} 	
		System.out.println(".....regreso....byteArrayOutputStream");
		return byteArrayOutputStream;
	}

	public ResolucionVO llenaResolucion(ReporteClemBean reporteClemBean){
		ResolucionVO resVO = new ResolucionVO();
		ClemVO clemVO = new ClemVO();
		String ruta =  new ClassPathResource("reportes/asimss-clem.jpg").getPath();
		this.log.debug("Ruta de las imagenes de los reportes ..." + ruta);
		
		clemVO.setFolioClem(reporteClemBean.getFolio());
		clemVO.setDelegacion((reporteClemBean.getDelegacion().toUpperCase().startsWith("DELEGACIÓ“N ") ||
				reporteClemBean.getDelegacion().toUpperCase().startsWith("DELEGACION "))?
						reporteClemBean.getDelegacion().toUpperCase().substring("DELEGACION ".length()):
						reporteClemBean.getDelegacion().toUpperCase());
		clemVO.setSubdelegacion((reporteClemBean.getSubdelegacion().toUpperCase().startsWith("SUBDELEGACIÓ“N ") ||
				reporteClemBean.getSubdelegacion().toUpperCase().startsWith("SUBDELEGACION "))?
						reporteClemBean.getSubdelegacion().toUpperCase().substring("SUBDELEGACION ".length()):
						reporteClemBean.getSubdelegacion().toUpperCase());
		clemVO.setRazonSocial(covertirHTMLaString(reporteClemBean.getRazonSocial()));
		clemVO.setDomicilio(reporteClemBean.getDomicilio());
		if(clemVO.getDomicilio() == null || clemVO.getDomicilio().trim().length() == 0){
			clemVO.setMunicipioDelegacion(" ");
		}else{
			clemVO.setMunicipioDelegacion(reporteClemBean.getMunicipioDelegacion());
		}
		clemVO.setRegPatronal(reporteClemBean.getRegPatronal());
		clemVO.setFechaAviso(modificaFormatoFecha(reporteClemBean.getFechaAviso()).toString());
		clemVO.setIdDivisionPatron(reporteClemBean.getIdDivisionPatron());
		clemVO.setIdGrupoPatron(clemVO.getIdDivisionPatron() + reporteClemBean.getIdGrupoPatron());
		clemVO.setIdFraccionPatron(clemVO.getIdGrupoPatron() + reporteClemBean.getIdFraccionPatron());
		clemVO.setDenominacionFraccion(reporteClemBean.getDenominacionFraccion());
		clemVO.setClase(reporteClemBean.getClase());
		clemVO.setPrima(reporteClemBean.getPrima());
		clemVO.setFechaTramite(modificaFormatoFecha(reporteClemBean.getFechaTramite()).toString());
		
		//Sustituimos saltos de lï¿½nea por el salto de lï¿½nea en HTML para que la plantilla lo pueda leer
		String motivos = reporteClemBean.getMotivos().trim().toUpperCase();
		log.debug("::::::::::: Reemplazando saltos de linea solo en documento");		
		motivos= motivos.replaceAll("\\n", "&#13;");
		clemVO.setMotivos(motivos);
		
		//clemVO.setMotivos(reporteClemBean.getMotivos().trim().toUpperCase());
		
		clemVO.setIdDivisionPropuesta(reporteClemBean.getIdDivisionPropuesta());
		clemVO.setIdGrupoPropuesta(clemVO.getIdDivisionPropuesta() + reporteClemBean.getIdGrupoPropuesta());
		clemVO.setIdFraccionPropuesta(clemVO.getIdGrupoPropuesta() + reporteClemBean.getIdFraccionPropuesta());
		clemVO.setDivisionPropuesta(reporteClemBean.getDivisionPropuesta());
		clemVO.setGrupoPropuesta(reporteClemBean.getGrupoPropuesta());
		clemVO.setClasePropuesta(reporteClemBean.getClasePropuesta());
		if(reporteClemBean.getPrimaSugerida() != null) {
			
			clemVO.setPrimaPropuesta(reporteClemBean.getPrimaSugerida());
		}else {
			
			clemVO.setPrimaPropuesta(reporteClemBean.getPrimaPropuesta());
		}
		
		clemVO.setFraccionPropuesta(reporteClemBean.getFraccionPropuesta());
		clemVO.setFraccionArticulo26(reporteClemBean.getFraccionArticulo26());
		clemVO.setFraccion115(reporteClemBean.getFraccion115());
		clemVO.setIncisio115(reporteClemBean.getIncisio115());
		clemVO.setTitular(reporteClemBean.getTitular().toUpperCase());
		if(reporteClemBean.getSuplente() != null && reporteClemBean.getSuplente().trim().length() > 0 &&  !reporteClemBean.getSuplente().equals("null")){
			clemVO.setSuplente(reporteClemBean.getSuplente());
			
		
		}else{
			
			clemVO.setSuplente(null);
		}
		clemVO.setPuesto(reporteClemBean.getPuesto());
		clemVO.setLugarFechaExpedicion(reporteClemBean.getLugarFechaExpedicion().trim().toUpperCase());

//AQUI REVISAR VALOR DE PSP A 2		
		
		clemVO.setPspArt15A(reporteClemBean.getPspArt15A().trim().equals("1") ? "15-A, " : "");
		clemVO.setPspArt19(reporteClemBean.getPspArt15A().trim().equals("1") ? "19, " : "");
		
		
		
		clemVO.setFraccionArticulo20(reporteClemBean.getFraccionArticulo20().trim().equals("1")?"20, ":"");
		clemVO.setFraccionArticulo26("fracción " + reporteClemBean.getFraccionArticulo26() + ", ");
		clemVO.setFraccionArticulo28("fracción " + reporteClemBean.getFraccionArticulo28() + ", ");
		clemVO.setFechaSurteEfecto(modificaFormatoFecha(reporteClemBean.getFechaSurteEfecto()).toString());
		clemVO.setTipoPersona(reporteClemBean.getTipoPersona());
		
		//Buscamos la descripcion RIMSS de la subdelegacion
	    SubdelegacionRimss model = new SubdelegacionRimss();
        model.setCveDelegacion(null);
        model.setCveSubdelegacion(null);
       
        if(reporteClemBean.getCveIdDelegacion() != null) {
            model.setCveDelegacion(new Long(reporteClemBean.getCveIdDelegacion()));
        }
        if(reporteClemBean.getCveIdSubdelegacion() != null) {
            model.setCveSubdelegacion(new Long(reporteClemBean.getCveIdSubdelegacion()));
        }
       
        SubdelegacionRimss subDelRimss =  datosClemEntity.consultaSubdelegacionRIMSS(model);
        if(subDelRimss != null && subDelRimss.getDescDelegacion() != null
                && subDelRimss.getTipo() != null &&  subDelRimss.getDescSubDelegacion() != null){
           
            clemVO.setDelegacion(subDelRimss.getTipo() + " " + subDelRimss.getDescDelegacion());
            clemVO.setSubdelegacion(subDelRimss.getDescSubDelegacion()+ " ");
        }
       
		resVO.setClemVO(clemVO);
		
		return resVO;
	}
	
	public Map<String, Object> llenaMapa(){
		Map<String, Object> parametros=new HashMap<String, Object>();
		String ruta =  new ClassPathResource("reportes/asimss-clem.jpg").getPath();
		parametros.put("ruta",ruta);
		parametros.put("IMAGENES_DIR", new ClassPathResource("/reportes/").getPath());
		return parametros;
	}
	
//	@Override
//	public ByteArrayOutputStream generaReporteClem(ReporteClemBean reporteClemBean, String nomReporte, String imgPath)throws DatosClemException{
//		Map<String, Object> parametros=llenaMapa(reporteClemBean, imgPath);
//		ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
//		try {
//			System.out.println(".......--->"+nomReporte);
//			JasperReport report = (JasperReport) JRLoader.loadObject(Thread.currentThread().getContextClassLoader().getResourceAsStream(nomReporte));
//			System.out.println(".......--->"+report);
//			System.out.println(".......--->"+parametros);
//			
//			JasperPrint print=JasperFillManager.fillReport(report, parametros, new JREmptyDataSource());
//			JRPdfExporter exporter = new JRPdfExporter();
//			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
//			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);
//			exporter.exportReport();
//		}catch(JRException e){
//			log.debug("err:: generaReporteClem - ");
//			e.printStackTrace();
//		} 		
//		return byteArrayOutputStream;
//	}
//	
//	public Map<String, Object> llenaMapa(ReporteClemBean reporteClemBean, String imgPath){
//		Map<String, Object> parametros=new HashMap<String, Object>();
//		String ruta =  new ClassPathResource("reportes/asimss-clem.jpg").getPath();
//		parametros.put("ruta",ruta);
//		this.log.debug("Ruta de las imagenes de los reportes ..." + ruta);
//		parametros.put("folio", reporteClemBean.getFolio());
//		parametros.put("delegacion", (reporteClemBean.getDelegacion().toUpperCase().startsWith("DELEGACIï¿½N ") ||
//						reporteClemBean.getDelegacion().toUpperCase().startsWith("DELEGACION "))?
//								reporteClemBean.getDelegacion().toUpperCase().substring("DELEGACION ".length()):
//								reporteClemBean.getDelegacion().toUpperCase());
//		parametros.put("subdelegacion", (reporteClemBean.getSubdelegacion().toUpperCase().startsWith("SUBDELEGACIï¿½N ") ||
//						reporteClemBean.getSubdelegacion().toUpperCase().startsWith("SUBDELEGACION "))?
//								reporteClemBean.getSubdelegacion().toUpperCase().substring("SUBDELEGACION ".length()):
//								reporteClemBean.getSubdelegacion().toUpperCase());
//		parametros.put("razonSocial", reporteClemBean.getRazonSocial());
//		parametros.put("domicilio", reporteClemBean.getDomicilio());
//		parametros.put("municipioDelegacion", reporteClemBean.getMunicipioDelegacion());
//		parametros.put("regPatronal", reporteClemBean.getRegPatronal());
//		parametros.put("fechaAviso", modificaFormatoFecha(reporteClemBean.getFechaAviso().toString()));
//		parametros.put("idDivisionPatron", reporteClemBean.getIdDivisionPatron());
//		parametros.put("idGrupoPatron", reporteClemBean.getIdGrupoPatron());
//		parametros.put("idFraccionPatron", reporteClemBean.getIdFraccionPatron());
//		parametros.put("denominacionFraccion", reporteClemBean.getDenominacionFraccion());
//		parametros.put("clase", reporteClemBean.getClase());
//		parametros.put("prima", reporteClemBean.getPrima());
//		parametros.put("fechaTramite", modificaFormatoFecha(reporteClemBean.getFechaTramite()).toString());
//		parametros.put("motivos", reporteClemBean.getMotivos().trim().toUpperCase());
//		parametros.put("idDivisionPropuesta", reporteClemBean.getIdDivisionPropuesta());
//		parametros.put("idFraccionPropuesta", reporteClemBean.getIdFraccionPropuesta());
//		parametros.put("idGrupoPropuesta", reporteClemBean.getIdGrupoPropuesta());
//		parametros.put("divisionPropuesta", reporteClemBean.getDivisionPropuesta());
//		parametros.put("grupoPropuesta", reporteClemBean.getGrupoPropuesta());
//		parametros.put("clasePropuesta", reporteClemBean.getClasePropuesta());
//		parametros.put("primaPropuesta", reporteClemBean.getPrimaPropuesta());
//		parametros.put("fraccionPropuesta", reporteClemBean.getFraccionPropuesta());
//		parametros.put("fraccionArticulo26", reporteClemBean.getFraccionArticulo26());
//		parametros.put("fraccion115", reporteClemBean.getFraccion115());
//		parametros.put("incisio115", reporteClemBean.getIncisio115());
//		parametros.put("titular", reporteClemBean.getTitular());
//		if(reporteClemBean.getSuplente() != null && reporteClemBean.getSuplente().trim().length() > 0)
//			parametros.put("suplente", reporteClemBean.getSuplente());
//		else
//			parametros.put("suplente", null);
//		
//		parametros.put("lugarFechaExpedicion", reporteClemBean.getLugarFechaExpedicion().trim().toUpperCase());
//		
//		parametros.put("pspArt15A", reporteClemBean.getPspArt15A().trim().equals("1") ? "15-A, " : "");
//		parametros.put("pspArt19", reporteClemBean.getPspArt15A().trim().equals("1") ? "19, " : "");
//		parametros.put("fraccionArticulo20", reporteClemBean.getFraccionArticulo20().trim().equals("1")?"20, ":"");
//		
//		parametros.put("fraccionArticulo26", "fracciï¿½n " + reporteClemBean.getFraccionArticulo26() + ", ");
//		parametros.put("fraccionArticulo28", "fracciï¿½n " + reporteClemBean.getFraccionArticulo28() + ", ");
//		parametros.put("fechaSurteEfecto", modificaFormatoFecha(reporteClemBean.getFechaSurteEfecto().toString()));
//		parametros.put("tipoPersona", reporteClemBean.getTipoPersona());
//		parametros
//		.put("IMAGENES_DIR", new ClassPathResource("/reportes/").getPath());
//		return parametros;
//	}
	
	private Object modificaFormatoFecha(String fechaTramite) {
		
		if(fechaTramite == null || fechaTramite.length() < 10) {
			DateFormat formatt = new SimpleDateFormat("dd/MM/yyyy");
			fechaTramite = formatt.format(new Date());
		}
		String dia = fechaTramite.substring(0, 2);
		int mesInt = Integer.parseInt(fechaTramite.substring(3, 5));
		String anio = fechaTramite.substring(6, 10);
		String mes = null;
		
		switch(mesInt){
		case 1:
			mes = "enero";
			break;
		case 2:
			mes = "febrero";
			break;
		case 3:
			mes = "marzo";
			break;
		case 4:
			mes = "abril";
			break;
		case 5:
			mes = "mayo";
			break;
		case 6:
			mes = "junio";
			break;
		case 7:
			mes = "julio";
			break;
		case 8:
			mes = "agosto";
			break;
		case 9:
			mes = "septiembre";
			break;
		case 10:
			mes = "octubre";
			break;
		case 11:
			mes = "noviembre";
			break;
		case 12:
			mes = "diciembre";
			break;
			
		}
		return dia + " de " + mes + " de " + anio;
	}

	public void elimina(Long cveIdClem) throws DatosClemException{
		try{
			datosClemEntity.elimina(cveIdClem);
		}catch(Exception exc){
			log.error("Error al eliminar la clem:" + exc.getMessage());
			throw new DatosClemException();
		}
	}
	
	/**
	 * Consulta Datos para mostrar JSP del CLEM (Generar/Modificar)
	 */
	@Override
	public ReporteClemBean consultaDatosClem(ReporteClemBean reporteClemBean)throws DatosClemException{
		DatosClem datosClem= new DatosClem();
		List<ArticuloModel> lstArticuloModel=null;
		AnalisisClasificacionEmpresas analisis = new AnalisisClasificacionEmpresas();
		SujetoObligado sujeto= null;
		String cveIdTipoCausa;
		datosClem.setCveAnalisis(new BigDecimal(reporteClemBean.getIdAnalisis()));
		analisis.setCveIdAnalisis(Long.parseLong(reporteClemBean.getIdAnalisis()));
		Long cveIdGpoAnalisis=reporteClemBean.getTipoTramite() != null && reporteClemBean.getTipoTramite().equals("167") ? 3L : null;
		analisis.setCveIdGrupoAnalisisCe(cveIdGpoAnalisis);
		try{
			datosClem = consultaClem(datosClem);
			sujeto=analisisBusiness.consultarSujetoObligadoPorAnalisis(analisis, Integer.parseInt(reporteClemBean.getTipoPersona().trim()));
			
			if(datosClem==null){
				datosClem=new DatosClem();
			}
			if(sujeto.getSubdelegacion()!=null && sujeto.getSubdelegacion().getId()!=null &&
					sujeto.getSubdelegacion().getId()==CLAVE_SUBDEL_COATZACOALCOS &&
					sujeto.getSubdelegacion().getDelegacion().getId()==CLAVE_DEL_VERACRUZ_SUR){
				datosClem.setMostrarComboArt155("1");
			}else{
				datosClem.setMostrarComboArt155("0");
			}
			reporteClemBean.setMostrarComboArt155(datosClem.getMostrarComboArt155());
			
			if(datosClem!=null && datosClem.getCveIdClem()!=null){
				reporteClemBean.setCveIdClem(datosClem.getCveIdClem().toString());
				
				if(!reporteClemBean.getInsMod().trim().equals("0")){
					cveIdTipoCausa=analisisBusiness.consultarAnalisisPorId(analisis).getTipoCausaAnalisis();
					reporteClemBean.setCveIdTipoCausa(Long.parseLong(cveIdTipoCausa));
					reporteClemBean.setTipoTramite(
							tipoCausaServiceEntity.consultaTipoTramite(
									Long.parseLong(cveIdTipoCausa)).getCveIdTipoTramite().toString());
				}
				
				lstArticuloModel=articuloService.buscarPorIdClem(datosClem.getCveIdClem().longValue());
				vacio: for(ArticuloModel art:lstArticuloModel){
					if(art.getNumArticulo()==null || art.getNumArticulo().toString().trim().equals("")){
						continue vacio;
					}
					if(art.getNumArticulo().longValue()==28){
						reporteClemBean.setFraccionArticulo28(art.getDesFraccion());
					}else if(art.getNumArticulo().longValue()==20){
						reporteClemBean.setFraccionArticulo20(art.getDesFraccion());
						
					}else if(art.getNumArticulo().longValue()==26){
						reporteClemBean.setFraccionArticulo26(art.getDesFraccion());
					}else if(art.getNumArticulo().longValue()==155){
						if(datosClem.getMostrarComboArt155().trim().equals("1")){
							reporteClemBean.setIncisio115(art.getDesInciso());
						}
					}
				}
				
				if(datosClem.getDesTitular() != null && datosClem.getDesTitular().trim().length() >0){
					reporteClemBean.setTitular(datosClem.getDesTitular());
				} else {
					reporteClemBean.setTitular(null);
				}
				
				
				if(datosClem.getDesSuplente() != null && datosClem.getDesSuplente().trim().length() >0 && !datosClem.getDesSuplente().equals("null")){
					reporteClemBean.setSuplente(datosClem.getDesSuplente());
					
				} else {
					
					reporteClemBean.setSuplente(null);
				}
				
				reporteClemBean.setPuesto(datosClem.getPuesto());
				reporteClemBean.setCveTipoClem(datosClem.getCveTipoClem().toString());
				reporteClemBean.setCveArticulo155(datosClem.getIncisoArticulo155());
				
				//reporteClemBean.setCveSolicitud(datosClem.getCveSolicitud().toString());
				reporteClemBean.setLugarFechaExpedicion(datosClem.getDesLugarFechaExp());
				reporteClemBean.setMotivos(datosClem.getDesMotivos());
				reporteClemBean.setIncisio155(datosClem.getIncisoArticulo155());
				
				reporteClemBean.setFirmaAusencia(datosClem.getFirmaAusencia());
			}
			
		}catch(AnalisisNoEncontradoException e){
			log.error("Error al tratar de consultar Analisis: " + e.getMessage());
		}catch(PatronNoEncontradoException pe){
			log.error("Error al tratar de consultar Patron: " + pe.getMessage());
		}catch(NumberFormatException nfe){
			log.error("Error Parser: " + nfe.getMessage());
		}
		return reporteClemBean;
	}
	
	@SuppressWarnings({ "unused" })
	@Override
	public void generacionClem(ReporteClemBean reporteClemBean, Usuario usuario, String imgPath, Long cveIdPatronDictamen)throws ClemCaracterException, Exception{
		
		this.log.debug("************Generacion de la Clem , datos .." + reporteClemBean);
		this.log.debug("::: Validando datos de captura por caracteres especiales: ");
		if(!this.validaCaracteresPermitidosClem(reporteClemBean)) {
			this.log.debug("************ ERROR, error de caracteres especiales en la CLEM");
			throw new ClemCaracterException();
		}else {
			this.log.debug("::Caracteres en la Clem validos");
		}
		
		AnalisisClasificacionEmpresas analisisClasifEmp=new AnalisisClasificacionEmpresas();
		Clasificacion clasOriginal = null;
		DatosClem clem=new DatosClem();
		SujetoObligado sujetoObligado=new SujetoObligado();
		List<Articulo155> articulo155s=new ArrayList<Articulo155>();
		ArticuloModel articulo= new ArticuloModel();
		final mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitudParam = new mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud();
		Long lTipoCausa=0L, lTipoTramite=0L;
		List<ArticuloModel> lstArticuloModel=new ArrayList<ArticuloModel>();
		boolean cambiaCausa=true;
		boolean comparacionFraccion=false;
		Long cveIdTipoCausa=reporteClemBean.getCveIdTipoCausa();
		String regPatron=reporteClemBean.getRegPatronal();
		String nombreJrxml="";
		
		String tipoTramite = reporteClemBean.getTipoTramite();
		Long cveIdGpoAnalisis=reporteClemBean.getTipoTramite() != null && tipoTramite.equals("167") ? 3L : null;
		analisisClasifEmp.setCveIdAnalisis(Long.parseLong(reporteClemBean.getIdAnalisis().trim()));
		analisisClasifEmp.setCveIdGrupoAnalisisCe(cveIdGpoAnalisis);
		sujetoObligado=analisisBusiness.consultarSujetoObligadoPorAnalisis(analisisClasifEmp,
				Integer.parseInt(reporteClemBean.getTipoPersona().toString().trim()));
		sujetoObligado.setNumeroRegistroPatronal(reporteClemBean.getRegPatronal());
		sujetoObligado.setTipoPersonaFiscal(reporteClemBean.getTipoPersona().equals("2") ? TipoPersonaFiscal.MORAL : TipoPersonaFiscal.FISICA);
		
		log.debug("*********************************************************************************************************************************");
		log.debug("******************************Se obtiene la clasificacion original del SO*********************************************************");
		log.debug("*********************************************************************************************************************************");
		log.debug("Datos del SO "+sujetoObligado.getCveIdSujetoObligado()+", cveIdClas: " + sujetoObligado.getClasificacion().getId());
		final List<DictamenClasificacion> clasSO = clasifPropDictEntity.consultaClasifOriginal(sujetoObligado.getCveIdSujetoObligado());

		//Consulta el registro historico con el que nacio el movimiento patronal
		//para obtener la clasificacion anterior y declarada en el momento en que se registro el movimiento
		//por cuestiones de impresion del documento(CLEM) 
		EstatusAnalisisModel estatusHistorico = bitacoraServiceEntity.buscaClasificacionInicial(Long.parseLong(reporteClemBean.getIdAnalisis().trim()));
		//Se busca fracciÃ³n de ultimo MOV06 a SINDO para compararlo mas adelante
		String fraccionAnterior = bitacoraServiceEntity.fraccionMovAnt(Long.parseLong(reporteClemBean.getIdAnalisis().trim()));
		
		analisisClasifEmp = setClasAnteriorYDeclarada(estatusHistorico, analisisClasifEmp);
		Long cveIdFracClOriginal = analisisClasifEmp.getClasificacionAnterior().getCveIdFraccionClase();

		analisisClasifEmp.setClasificacionPropuesta(clasificacionPropuestaService.consultaPorIdAnalisis(
				Long.parseLong(reporteClemBean.getIdAnalisis())).getClasificacionPropuesta());

		analisisClasifEmp.setCveIdDelegacion(new Long(reporteClemBean.getCveIdDelegacion()));
		analisisClasifEmp.setCveIdSubdelegacion(new Long(reporteClemBean.getCveIdSubdelegacion()));
		analisisClasifEmp.setClaveUsuarioAsignado(usuario.getCveIdUsuario());
		
		articulo155s=articulo155ServiceBusiness.getArticulo155(Long.parseLong(reporteClemBean.getCveIdSubdelegacion()));
		//Inicia proceso de Insercciï¿½n o Actualizaciï¿½n de CLEM
		clem.setCveAnalisis(new BigDecimal(reporteClemBean.getIdAnalisis()));
		clem=datosClemEntity.consultaPorClave(clem);
		
		Long cveIdGrupoAnalisis=analisisBusiness.consultarAnalisisPorId(analisisClasifEmp).getCveIdGrupoAnalisisCe();
		if(clem==null){
			log.debug("No se encontro el analisis");
			clem=new DatosClem();
		}
		clem.setMostrarComboArt155(reporteClemBean.getMostrarComboArt155());
		coatzacoalcos: for (Articulo155 art155 : articulo155s) {
			reporteClemBean.setFraccion115(art155.getDesFraccion());
			reporteClemBean.setIncisio115(art155.getDesInciso());
			clem.setDescFraccion115(art155.getDesFraccion());
			clem.setIncisoArticulo155(art155.getDesInciso());
			clem.setCveArticulo155(new BigDecimal(art155.getCveIdArticulo155()));
			
			if (clem.getMostrarComboArt155().trim().equals("1")) {
				if (clem.getIncisoArticulo155().trim().equals(art155.getDesInciso().trim())) {
					reporteClemBean.setIncisio115(reporteClemBean.getIncisio155() + ")");//art155.getDesInciso() + ")");
					break coatzacoalcos;
				}
			}
		}
		
		//Si Este Trï¿½mite es de Inscripciï¿½n Inicial omite los procesos de Causas
		if(!reporteClemBean.getInsMod().trim().equals("0") && !tipoTramite.equals("167")){
			//Funcionalidad con respecto a TipoCausa (Artï¿½culo 28)
			switch (Integer.parseInt(reporteClemBean.getFraccionArticulo28())){
			case 1:
				analisisClasifEmp.setTipoCausaAnalisis("31");
				reporteClemBean.setFraccionArticulo28("I");
				break;
			case 2:
				analisisClasifEmp.setTipoCausaAnalisis("33");
				reporteClemBean.setFraccionArticulo28("I");
				break;
			case 3:
				analisisClasifEmp.setTipoCausaAnalisis("37");
				reporteClemBean.setFraccionArticulo28("I");
				break;
			case 4:
				analisisClasifEmp.setTipoCausaAnalisis("39");
				reporteClemBean.setFraccionArticulo28("I");
				break;
			case 5:
				analisisClasifEmp.setTipoCausaAnalisis("40");
				reporteClemBean.setFraccionArticulo28("I");
				break;
			case 6:
				analisisClasifEmp.setTipoCausaAnalisis("38");
				reporteClemBean.setFraccionArticulo28("I");
				break;
			case 7:
				analisisClasifEmp.setTipoCausaAnalisis("41");
				reporteClemBean.setFraccionArticulo28("I");
				break;
			case 8:
				analisisClasifEmp.setTipoCausaAnalisis("42");
				reporteClemBean.setFraccionArticulo28("II");
				break;
			case 9:
				analisisClasifEmp.setTipoCausaAnalisis("35");
				reporteClemBean.setFraccionArticulo28("III");
				break;
			case 10:
				analisisClasifEmp.setTipoCausaAnalisis("36");
				reporteClemBean.setFraccionArticulo28("IV");
				break;
			case 11:
				analisisClasifEmp.setTipoCausaAnalisis("34");
				reporteClemBean.setFraccionArticulo28("V");
				break;
			case 13:
				analisisClasifEmp.setTipoCausaAnalisis("46");
				reporteClemBean.setFraccionArticulo28("VI");
//			5134215 / WO1962561 - Cambio de domicilio con diferente municipio
//			Se agrega nuevo valor a las tipos de causas
//			Todvia no se agregar valor correcto en la parte de Fraccion articulo
			case 17:
				analisisClasifEmp.setTipoCausaAnalisis("48");
				reporteClemBean.setFraccionArticulo28("II");
				break;
			}
			lTipoCausa=Long.parseLong(analisisClasifEmp.getTipoCausaAnalisis());
			reporteClemBean.setCveIdTipoCausa(Long.parseLong(analisisClasifEmp.getTipoCausaAnalisis()));
		}else { //Se debe tomar en cuenta Inscripcion inicial Cuando se genera un movimiento 06
            analisisClasifEmp.setTipoCausaAnalisis("30");
            lTipoCausa = 30L;
            reporteClemBean.setFraccionArticulo28("I");
        }
		
		sujetoObligado = solicitudServiceBusiness.obtenerDetalleSolicitud(sujetoObligado);
		
		/**
		 * Funcionalidad adaptada para Tablas incompletas
		 */
		if(sujetoObligado.getCntroTrabajo()==null ||
				sujetoObligado.getCntroTrabajo().getVialidadPrimaria()==null ||
				sujetoObligado.getCntroTrabajo().getVialidadPrimaria().getNombre()==null// ||
				/*sujetoObligado.getCntroTrabajo().getDescripcion()!=null*/){
			CentroTrabajo centroTrabajo=new CentroTrabajo();
			Asentamiento asentamiento=new Asentamiento();
			Localidad localidad=new Localidad();
			Municipio municipio=new Municipio();
			SujetoObligado sujOblig=domicilioMigrServiceEntity.obtenerMunicipioMigr(sujetoObligado.getCveIdSujetoObligado());
			municipio.setNombre(sujOblig.getCntroTrabajo()!=null?sujOblig.getCntroTrabajo().getAsentamiento().getLocalidad().getMunicipio().getNombre():"");
			localidad.setMunicipio(municipio);
			asentamiento.setLocalidad(localidad);
			centroTrabajo.setAsentamiento(asentamiento);
			centroTrabajo.setDescripcion(sujetoObligado.getCntroTrabajo().getDescripcion());
			sujetoObligado.setCntroTrabajo(centroTrabajo);
		}
		
		lTipoTramite=Long.parseLong(reporteClemBean.getTipoTramite());
	
		reporteClemBean = datosClemUtility.convertirModelsToReporteClem(reporteClemBean, analisisClasifEmp, sujetoObligado);
		
		//Si Este Trï¿½mite es de Inscripciï¿½n Inicial omite los procesos de Causas
		if(!reporteClemBean.getInsMod().trim().equals("0")){
			reporteClemBean.setCveIdTipoCausa(Long.parseLong(analisisClasifEmp.getTipoCausaAnalisis()));
		}
		
		if(reporteClemBean.getCveTipoClem().equals(String.valueOf(TipoDatosClemEnum.SUBDELEGACIONAL.getClave()))){
			if(reporteClemBean.getFirmaAusencia().equals("0"))
				reporteClemBean.setSuplente(null);				
		}
				
		//Parï¿½metros que pudieron haber sufrido cambios
		clem.setDesTitular(reporteClemBean.getTitular()!=null?reporteClemBean.getTitular().toUpperCase():null);
		clem.setPuesto(reporteClemBean.getPuesto()!=null?reporteClemBean.getPuesto():null);
		clem.setDesSuplente(reporteClemBean.getSuplente()!=null?reporteClemBean.getSuplente():null);
		clem.setDesMotivos(reporteClemBean.getMotivos());
		clem.setDesLugarFechaExp(reporteClemBean.getLugarFechaExpedicion());
		clem.setCveArticulo20(reporteClemBean.getFraccionArticulo20());
		clem.setCveArticulo26(reporteClemBean.getFraccionArticulo26());
		clem.setCveArticulo28(reporteClemBean.getFraccionArticulo28());
		clem.setIncisoArticulo155(reporteClemBean.getIncisio155());
		clem.setDescFraccion115(reporteClemBean.getFraccion155());
		
		clem.setTipoTramite(reporteClemBean.getTipoTramite());
		clem.setCveTipoClem(new BigDecimal(reporteClemBean.getCveTipoClem()));
		clem.setCveDesDelegacion(sujetoObligado.getSubdelegacion().getId().toString());
		clem.setCveDesSubdelegacion(sujetoObligado.getSubdelegacion().getDelegacion().getId().toString());
		
		switch(cveIdGrupoAnalisis.intValue()){
		case 1:
			if(reporteClemBean.getCveTipoClem().equals(String.valueOf(TipoDatosClemEnum.DELEGACIONAL.getClave()))){
				nombreJrxml=PDF_DELEGACIONAL_INICIAL;
			}else{
				nombreJrxml=PDF_SUBDELEGACIONAL_INICIAL;
			}
			break;
		case 2:
			if(reporteClemBean.getCveTipoClem().equals(String.valueOf(TipoDatosClemEnum.DELEGACIONAL.getClave()))){
				nombreJrxml=PDF_DELEGACIONAL_MODIFICACION;
			}else{
				nombreJrxml=PDF_SUBDELEGACIONAL_MODIFICACION;
			}
			break;
		}
		
		if(clem==null || clem.getCveIdClem()==null){
			log.info("Se insertarï¿½ un Nuevo CLEM");

			// Valida si el anï¿½lisis ha sido modificado por otro usuario.
			analisisBusiness.validaEstatusMovimiento(new Long(reporteClemBean.getIdAnalisis()).longValue(), 
				"" + EstatusAnalisisEnum.RECTIFICADO_PENDIENTE_AUTORIZACION.getClave());

			analisisBusiness.autorizarRectificarSolicitud(analisisClasifEmp);					
			
			clem.setCveAnalisis(new BigDecimal(reporteClemBean.getIdAnalisis()));
			clem.setCveDelegacion(new BigDecimal(reporteClemBean.getCveIdDelegacion()));
			clem.setCveSubdelegacion(new BigDecimal(reporteClemBean.getCveIdSubdelegacion()));
			
			clem.setFecRegistroAlta(new Date());
			
			clem.setFolioResolucion(datosClemUtility.generaFolioClem(clem));
			
			articulo = obtieneArticulo(clem, 155);//Obteniendo la descripcion del artï¿½culo
			if(articulo!=null){
				clem.setCveArticulo155(articulo.getCveIdArticulo()); 
			}
			
			if(TipoDatosClemEnum.DELEGACIONAL.getClave()==Integer.parseInt(reporteClemBean.getCveTipoClem())){
				clem.setCveTipoDoc(new BigDecimal(TipoDatosClemEnum.DELEGACIONAL.getClave()));	
			}else{
				clem.setCveTipoDoc(new BigDecimal(TipoDatosClemEnum.SUBDELEGACIONAL.getClave()));
			}
			
			clem.setIndActivo(BigDecimal.ONE);
			clem.setUltFechaActualizacion(new Date());
			reporteClemBean.setFolio(clem.getFolioResolucion());		
			//se genera Clem del patron enviando como clasificacion actual la del sujeto obligado(BD)	
			clem.setRefDocumento(generaReporteClem(reporteClemBean, PATH_REPORTES
					+ nombreJrxml, imgPath).toByteArray());
			
			clem=datosClemEntity.crear(clem, true);
			clem.setCveDelegacion(new BigDecimal(reporteClemBean.getCveIdDelegacion()));
			clem.setCveSubdelegacion(new BigDecimal(reporteClemBean.getCveIdSubdelegacion()));
			clem.setCveDesDelegacion(reporteClemBean.getCveIdDelegacion());
			clem.setCveDesSubdelegacion(reporteClemBean.getCveIdSubdelegacion());
			clem.setCveArticulo20(reporteClemBean.getFraccionArticulo20());
			clem.setCveArticulo26(reporteClemBean.getFraccionArticulo26());
			clem.setCveArticulo28(reporteClemBean.getFraccionArticulo28());
			clem.setIncisoArticulo155(reporteClemBean.getIncisio155());
			clem.setArticulos(articuloService.crearArticulos(clem));
			
			analisisServiceEntity.actualizaIndCausa(analisisClasifEmp, true);
			
			//Servicio para afectaciï¿½n de Clasificaciï¿½n Declarada por el Patrï¿½n
			log.debug("Actualiza Clasificacion del Patron");
			analisisClasifEmp.getClasificacionPropuesta().setSujetoObligado(sujetoObligado);
			analisisClasifEmp.getClasificacionPropuesta().setId(sujetoObligado.getClasificacion().getId());
			
			//Genera Nuevo Trï¿½mite a una Solicitud existente
			log.debug("Genera Tramite para la Solicitud actual");
			SujetoObligado soPropuesta=sujetoObligado;

			if(cveIdPatronDictamen == null){
				if(analisisClasifEmp.getClasificacionPropuesta().getPrimaSugerida() != null) {
					log.debug("::: Cambiando prima a la sugerida");
					soPropuesta.getClasificacion().getFraccion().setPrimaSRT(analisisClasifEmp.getClasificacionPropuesta().getPrimaSugerida());
					//soPropuesta.getClasificacion().setPrimaSRTActual(analisisClasifEmp.getClasificacionPropuesta().getPrimaSugerida());
				}
				soPropuesta.setClasificacion(analisisClasifEmp.getClasificacionPropuesta());
				analisisClasifEmp.getClasificacionPropuesta().setSujetoObligado(null);
				soPropuesta.getClasificacion().setId(sujetoObligado.getClasificacion().getId());
			}else{
     			log.debug("::: Se agrega registro a la tabla de dictamen con base al SO");
				final Clasificacion clasDtm = sujetoObligado.getClasificacion();
				clasDtm.setFraccion(analisisClasifEmp.getClasificacionPropuesta().getFraccion());
				clasDtm.setPrimaSRTActual(analisisClasifEmp.getClasificacionPropuesta().getPrimaSRTActual());
				if(clasifPropDictEntity.agrega(sujetoObligado.getCveIdSujetoObligado(), clasDtm)){
					log.debug("Clasificacion de dictamen agregada con exito");
				}else{
					log.debug("No se agrego la clasificacion del dictamen");
				}
			}

			log.debug("Agregando tramite");
			TramiteSujetoObligado tramiteSujObliga=solicitudPatronalService.construirTramite(
					RECTIFICACION_DE_LA_CLASIFICACION_POR_PROCESO_DE_ANALISIS, CERRADO, soPropuesta);
			tramiteSujObliga.setFechaPresentacion(new Date());
			tramiteSujObliga.setFechaEfecto(new Date());
			
			solicitudPatronalService.agregarTramiteASolicitud(Long.parseLong(reporteClemBean.getCveSolicitud()),
					tramiteSujObliga);
		}else{
			log.info("Se actualizara el CLEM existente");
			clem.setIndActivo(BigDecimal.ZERO);
			clem.setUltFechaActualizacion(new Date());
			if(reporteClemBean.getCveTipoClem()!= (clem.getCveTipoClem()).toString() ){
			log.debug("Entra a clase cambioFolio");
				clem.setFolioResolucion(datosClemUtility.cambioFolio(clem));
		}
			reporteClemBean.setFolio(clem.getFolioResolucion());
			
			clem.setRefDocumento(generaReporteClem(reporteClemBean, PATH_REPORTES
					+ (nombreJrxml), imgPath).toByteArray());
			
			clem=datosClemEntity.crear(clem, false);
			clem.setCveSubdelegacion(new BigDecimal(reporteClemBean.getCveIdSubdelegacion()));
			
			clem.setCveArticulo20(reporteClemBean.getFraccionArticulo20());
			clem.setCveArticulo26(reporteClemBean.getFraccionArticulo26());
			clem.setCveArticulo28(reporteClemBean.getFraccionArticulo28());
			clem.setIncisoArticulo155(reporteClemBean.getIncisio155());
			
			lstArticuloModel=articuloService.buscarPorIdClem(clem.getCveIdClem().longValue());
			articuloService.actualizarArticulos(lstArticuloModel, clem);
			
			if(!reporteClemBean.getInsMod().trim().equals("0") && (lTipoCausa.intValue()==cveIdTipoCausa.intValue())){
				cambiaCausa=false;
			}
		}
		
		//Actualiza Causa y Crea Histï¿½rico de CLEM
		if(lTipoCausa!=null && lTipoCausa!=0L){
			analisisServiceEntity.actualizaTipoCausa(analisisClasifEmp.getCveIdAnalisis(), lTipoCausa);
			//PENDIENTE DE REVISAR CON EL TOCAYO, QUIZï¿½ LO QUE SE TENGA QUE HACER ES: DESDE LA BASE EL CAMPO CVE_ID_TIPO_CAUSA NO SEA UNA LLAVE
		}
		if(reporteClemBean.getInsMod().trim().equals("0")){
			analisisClasifEmp.setTipoCausaAnalisis(String.valueOf(RECTIFICACION_DE_LA_CLASIFICACION_INICIAL.getClave()));
		}
		
		//si la peticion viene de una modificacion a la CLEM
		if(reporteClemBean.getBotonClem().equals(Constantes.CLEM_MODIFICAR)){
			//genera el historico de la clem
			datosClemEntity.generaHistDatosClem(clem, Long.parseLong(analisisClasifEmp.getTipoCausaAnalisis()));//Inserta en Histï¿½rico de CLEM
			//inserta campo en la tabla de historico de estatus para registrar que hubo un cambio en la Clem		
			EstatusAnalisisModel estatusAnalisisModel = bitacoraUtility.armaBitacoraCambioClem(reporteClemBean.getIdAnalisis().trim(),analisisClasifEmp.getClasificacionPropuesta().getFraccion(),
					reporteClemBean.getCveIdDelegacion(),reporteClemBean.getCveIdSubdelegacion(), usuario.getUsuario() );				
			bitacoraServiceEntity.guardaBitacora(estatusAnalisisModel);
		}
			//Se comparara las fracciones
			
		
			//log.debug("Guarda ultimoSindo " + ultimoEnvioSindo);
			comparacionFraccion = datosClemUtility.comparacionFraccion (fraccionAnterior , reporteClemBean );
			//Aquï¿½ harï¿½s uso del Nuevo Servicio con la Nueva Tabla DIT_HIST_TIPO_CAUSA, en donde siempre se insertarï¿½ la causa
			
			
			if(cambiaCausa || reporteClemBean.getBotonClem()==Constantes.CLEM_MODIFICAR || comparacionFraccion){
						
			analisisClasifEmp.getClasificacionPropuesta().setSujetoObligado(sujetoObligado);
			analisisClasifEmp.getClasificacionPropuesta().setId(sujetoObligado.getClasificacion().getId());
			clasificacionActividadEconomicaBusiness.actualizarClasificacion(analisisClasifEmp.getClasificacionPropuesta(),
					Integer.parseInt(analisisClasifEmp.getTipoCausaAnalisis()), 6);
			analisisClasifEmp.getClasificacionPropuesta().setSujetoObligado(null);
			
			log.debug("Esta Solicitud pertenece a Modificacion Patronal, si registra causa");
			tipoCausaAnalisisServiceBusiness.registraCausa(analisisClasifEmp);
			
			//Envï¿½a a SINDO por cambio de Tipo Causa	
			sujetoObligado.setClasificacion(analisisClasifEmp.getClasificacionPropuesta());
			String folio = analisisBusiness.buildNumeroFolio(sujetoObligado.getSubdelegacion().getClave());
			//Sustituimos para reutilizar la funcion comun
			//String folio = buildNumeroFolio(sujetoObligado.getSubdelegacion().getClave());            
			            
			if(!tipoTramite.equals("167")) {
			clasificacionActividadEconomicaBusiness.ejecutarProcesoSincronizacionSINDO(
					folio,
					sujetoObligado, calcularFechaSurteEfecto(reporteClemBean.getFechaSurteEfecto()),
			        lTipoCausa, Integer.valueOf(1), Integer.valueOf(6), Integer.valueOf(6));
					
			}
            
		}

		if(cveIdPatronDictamen != null){
			log.debug("*************************************Se actualiza la clasificacion a su valor original en caso de dictamen*********************************************");
			log.debug("****************************Registros a buscar***:********"+clasSO.size()+"****************************************************************************");
			log.debug("*********************************************************************************************************************************");
			clasifPropDictEntity.actualizarClasificacion(clasSO);
		}

	}	

//    private String buildNumeroFolio(String claveSubdel) {
//        Calendar calendar=Calendar.getInstance();
//        StringBuilder juliano=new StringBuilder();
//        juliano.append(calendar.get(Calendar.DAY_OF_YEAR));
//        String folio = claveSubdel
//            + (juliano.length()==1?"00"+juliano:juliano.length()==2?"0"+juliano:juliano);
//        log.debug(String.format("Folio con dia juliano: %s", folio));
//        folio = claveSubdel + "411";
//        log.debug(String.format("Folio corregido con 411: %s", folio));
//        return folio;
//    }

    private AnalisisClasificacionEmpresas setClasAnteriorYDeclarada(
			EstatusAnalisisModel estatusHistorico, AnalisisClasificacionEmpresas analisisClasifEmp) {
    	Clasificacion clasAnt = new Clasificacion();
    	Clasificacion clasAct = new Clasificacion();
    	clasAnt.setFraccion(estatusHistorico.getFraccionAnterior());
    	clasAct.setFraccion(estatusHistorico.getFraccionActual());
    	clasAnt.setPrimaSRTActual(estatusHistorico.getPrimaAnt());
    	clasAct.setPrimaSRTActual(estatusHistorico.getPrimaDec());
    	
    	clasAnt.getFraccion().setPrimaSRT(estatusHistorico.getPrimaAnt());
    	clasAct.getFraccion().setPrimaSRT(estatusHistorico.getPrimaDec());
    	analisisClasifEmp.setClasificacionAnterior(clasAnt);    	
    	analisisClasifEmp.setClasificacionActual(clasAct);
		return analisisClasifEmp;
	}

	private Date calcularFechaSurteEfecto(String fechaSurteEfecto) {
        Calendar cal = Calendar.getInstance();
        int day = Integer.parseInt(fechaSurteEfecto.replaceAll("\\d{0}\\D(\\d{0}).*", "$1").replaceAll("^0", ""));
        int month = Integer.parseInt(fechaSurteEfecto.replaceAll("\\d{2}\\D(\\d{2}).*", "$1").replaceAll("^0", ""));
        int year = Integer.parseInt(fechaSurteEfecto.replaceAll("(?:\\d{2}\\D){2}(\\d{4})", "$1"));
        cal.set(Calendar.DATE, day);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.YEAR, year);
        return cal.getTime();
    }

	@Override
	public void actualizaDatosFirmaClem(FirmaClemDTO firmaClemDTO) throws DatosClemException {
		try{
			datosClemEntity.actualizaDatosFirmaClem(firmaClemDTO);
		}catch(Exception exc){
			log.error("Error al actualiza la clem:" + exc.getMessage());
			throw new DatosClemException();
		}
	}
	
	private String covertirHTMLaString(String texto) {
		if (texto == null) {
		return null;
		}
		String textoConvertido = "";
		String[][] caracteresHTML = {
		{ "&Ntilde;", "Ñ" }, { "&ntilde;", "ñ" },
		{ "&aacute;", "á" },
		{ "&Aacute;", "Á" }, { "&eacute;", "é" }, { "&Eacute;", "É" }, { "&amp;", "&" }, { "&iacute;", "í" },
		{ "&Iacute;", "Í" }, { "&Oacute;", "Ó“" }, { "&oacute;", "ó" }, { "&Uacute;", "Úš" }, { "&uacute;", "ú" },
		{ "&quot;", "\"" } };
		for (int i = 0; i < caracteresHTML.length; i++) {
		textoConvertido = texto.replace(caracteresHTML[i][0], caracteresHTML[i][1]);
		}
		return textoConvertido;
		}

	@Override
	public boolean validaCaracteresPermitidosClem(ReporteClemBean reporteClemBean) throws Exception {
		log.debug(":::Validando caracteres permitidos a la Clem");		
		boolean errorCaracEsp = true;
		//valida campos obligatorios
		if (reporteClemBean.getMotivos() == null || reporteClemBean.getMotivos().trim().length() == 0 
				|| reporteClemBean.getTitular() == null || reporteClemBean.getTitular().trim().length() == 0 
				|| reporteClemBean.getPuesto() == null  || reporteClemBean.getPuesto().trim().length() == 0 
				|| reporteClemBean.getLugarFechaExpedicion() == null  || reporteClemBean.getLugarFechaExpedicion().trim().length() == 0 ) {
			logger.debug("::: El contenido de los campos obligatorios de la Clem viene vacio");
			return false;
		}				
		if(!datosClemUtility.validaCaracteresPermitidos(reporteClemBean.getMotivos())) {
			logger.debug("::: El contenido del campo de motivos contiene caracteres especiales, se regresara ERROR");
			errorCaracEsp = false;
		}else if(!datosClemUtility.validaCaracteresPermitidos(reporteClemBean.getTitular())) {
			logger.debug("::: El contenido del campo TITULAR contiene caracteres especiales, se regresara ERROR");
			errorCaracEsp = false;	
		}else if(!datosClemUtility.validaCaracteresPermitidos(reporteClemBean.getPuesto())) {
			logger.debug("::: El contenido del campo PUESTO contiene caracteres especiales, se regresara ERROR");
			errorCaracEsp = false;	
		}else if(!datosClemUtility.validaCaracteresPermitidos(reporteClemBean.getLugarFechaExpedicion())) {
			logger.debug("::: El contenido del campo LUGAR Y FECHA contiene caracteres especiales, se regresara ERROR");
			errorCaracEsp = false;	
		}
		
		//si el campo de suplente viene vacio no se valida
		if(reporteClemBean.getSuplente() != null && reporteClemBean.getSuplente().trim().length() > 0 && !datosClemUtility.validaCaracteresPermitidos(reporteClemBean.getSuplente())) {
			logger.debug("::: El contenido del campo SUPLENTE contiene caracteres especiales, se regresara ERROR");
			errorCaracEsp = false;	
		}
		
		return errorCaracEsp;
	}

}
