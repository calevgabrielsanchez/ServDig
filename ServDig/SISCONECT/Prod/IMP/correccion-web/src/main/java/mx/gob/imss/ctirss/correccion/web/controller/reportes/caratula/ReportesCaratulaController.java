package mx.gob.imss.ctirss.correccion.web.controller.reportes.caratula;


import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.ctirss.correccion.constantes.ArchivosXLSCaratula;
import mx.gob.imss.ctirss.correccion.constantes.QuerysCaratula;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.service.interfaces.ICatalogoService;
import mx.gob.imss.ctirss.correccion.session.UserSession;
import mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula.ReporteCaratulaFormVO;
import mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula.ItemVO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/reportes/caratula")
public class ReportesCaratulaController extends AbstractController{
	
	@Autowired
	private ICatalogoService<AbstractModel> catalogoServiceBean;
	
	
	
	
	
	
	/**
	 * Logger.
	 */
	private final static Logger logger = Logger.getLogger(ReportesCaratulaController.class);

	
	@RequestMapping(method=RequestMethod.GET)
	public String getCreateForm(Model model,HttpServletRequest request) {
		ReporteCaratulaFormVO form = new ReporteCaratulaFormVO();
		UserSession usrSession = getUsuarioFirmado(request);
		loadCommonCollections(form);
	
		form.setDelegacion(usrSession.getIdDelegacion()!=null ? usrSession.getIdDelegacion().intValue():0);
		form.setSubdelegacion(usrSession.getIdSubDelegacion()!=null ? usrSession.getIdSubDelegacion().intValue():0);
	
		model.addAttribute("modeloReporte",form);		
		 return "reportes/caratula/reportesMain";
	}
	
	@RequestMapping(value="/caratulaRetun" , method=RequestMethod.POST)
	public String getCreateForm2(Model model,HttpServletRequest request) {
		
		ReporteCaratulaFormVO form = new ReporteCaratulaFormVO();
		UserSession usrSession = getUsuarioFirmado(request);
		loadCommonCollections(form);
		form.setDelegacion(usrSession.getIdDelegacion()!=null ? usrSession.getIdDelegacion().intValue():0);
		form.setSubdelegacion(usrSession.getIdSubDelegacion()!=null ?usrSession.getIdSubDelegacion().intValue():0);
		model.addAttribute("modeloReporte",form);
		
		 return "reportes/caratula/reportesMain";
	}
	
	@RequestMapping(value="/reportWindow" , method=RequestMethod.GET)
	public String showReportWindow(Model model,HttpServletRequest request) {
		ReporteCaratulaFormVO form= new ReporteCaratulaFormVO();
		form.setTipoReporte(new Integer(request.getParameter("tipoReporte")).intValue());		
		form.setDelegacion(new Integer(request.getParameter("delegaParam")).intValue());
		form.setSubdelegacion(new Integer(request.getParameter("subdelegaParam")).intValue());				
		
		form.setNombreDelegacion(recuperaNombreDelegacion(form.getDelegacion()));
		form.setNombreSubdelegacion(recuperaNombreSubDelegacion(form.getSubdelegacion()));		
		
		loadCommonCollections(form);
		model.addAttribute("modeloReporte",form);		
		 return "reportes/caratula/reportWindow";
	}
	
	@RequestMapping(value="/reportes" , method=RequestMethod.POST)
	public String generarReporte(ReporteCaratulaFormVO form, Model model, HttpServletRequest request, HttpServletResponse response) {
		
		logger.info("Generando Reporte [Caratula]-> Correcciones Presentadas");
		//UserSession usrSession = getUsuarioFirmado(request);
		
		UserSession usrSession = new UserSession();
		usrSession.setIdDelegacion(Long.valueOf(form.getDelegacion()));
		usrSession.setIdSubDelegacion(Long.valueOf(form.getSubdelegacion()));
		usrSession.setNombreDelegacion(form.getNombreDelegacion());
		usrSession.setNombreSubDelegacion(form.getNombreSubdelegacion());

		String query="SELECT FEC_FIN_REPORTES FROM dual";
		List<AbstractModel> list=catalogoServiceBean.consultaSQL(query);
	
		if(!list.isEmpty()){
			Iterator<?> iter = list.iterator();
			Date currentObj= null;		
			while(iter.hasNext()){				
				currentObj = (Date)iter.next();
				AbstractReportesCaratula.setFechaFinReporte(currentObj);
			}
		}
		
		try{
			switch(form.getTipoReporte()){
			
		    //control_correcciones_presentadas.xls
			case ArchivosXLSCaratula.CASE_CCP:
				logger.info("Reporte: CCP-> Inicio Consulta");
		
				DescargaCCP descargaCCP = new DescargaCCP(
									getInfoReporte(QuerysCaratula.CCP,usrSession.getIdSubDelegacion().toString()), 
									ArchivosXLSCaratula.CCP_XLS,
									ArchivosXLSCaratula.CCP_XLS,
									request, response);
				
				
				form.setMensaje(descargaCCP.obtenerReporte(usrSession.getNombreDelegacion(),
														   usrSession.getNombreSubDelegacion()));
				
				logger.info("Reporte: CCP-> "+ form.getMensaje());
				break;
				
			//"folios_promocion_pendientes_conclusion.xls"
			case ArchivosXLSCaratula.CASE_RFPPC:
				logger.info("Reporte: RFPPC-> Inicio Consulta");
								
				DescargaRFPPC descargaRFPPC = new DescargaRFPPC(
						       getInfoReporte(QuerysCaratula.RFPPC,usrSession.getIdSubDelegacion().toString()), 
									ArchivosXLSCaratula.RFPPC_XLS,
									ArchivosXLSCaratula.RFPPC_XLS,
									request, response);
				
				form.setMensaje(descargaRFPPC.obtenerReporte(usrSession.getNombreDelegacion(),
						   usrSession.getNombreSubDelegacion()));
				
				logger.info("Reporte: RFPPC-> "+ form.getMensaje());
				break;

			//folios_correccion_pendientes_conclusion.xls
			case ArchivosXLSCaratula.CASE_RFCPC:
				logger.info("Reporte: RFCPC-> Inicio Consulta");
				
				DescargaRFCPC descargaRFCPC = new DescargaRFCPC(
					       getInfoReporte(QuerysCaratula.RFCPC,usrSession.getIdSubDelegacion().toString()), 
									ArchivosXLSCaratula.RFCPC_XLS,
									ArchivosXLSCaratula.RFCPC_XLS,
									request, response);
				
				form.setMensaje(descargaRFCPC.obtenerReporte(usrSession.getNombreDelegacion(),
						   usrSession.getNombreSubDelegacion()));
				logger.info("Reporte: RFCPC-> "+ form.getMensaje());
				break;
			
			//folios_correccion_proc_autodet.xls	
			case ArchivosXLSCaratula.CASE_RFCPA:
				logger.info("Reporte: RFCPA-> Inicio Consulta");
				
				DescargaRFCPA descargaRFCPA = new DescargaRFCPA(
					       getInfoReporte(QuerysCaratula.RFCPA,usrSession.getIdSubDelegacion().toString()), 
									ArchivosXLSCaratula.RFCPA_XLS,
									ArchivosXLSCaratula.RFCPA_XLS,
									request, response);
				
				form.setMensaje(descargaRFCPA.obtenerReporte(usrSession.getNombreDelegacion(),
						   usrSession.getNombreSubDelegacion()));
				
				logger.info("Reporte: RFCPA-> "+ form.getMensaje());
				break;
				
			case ArchivosXLSCaratula.CASE_RESUMEN_DE_RESULTADOS:
				logger.info("Reporte: Resumen Resultados-> Inicio Consulta");
			
				
				DescargaResumenResultados descargaRR = new DescargaResumenResultados(null, 
									ArchivosXLSCaratula.RESUMEN_DE_RESULTADOS,
									ArchivosXLSCaratula.RESUMEN_DE_RESULTADOS,
									request, response);
				
				descargaRR.setListaCOP(getInfoReporteResumen(QuerysCaratula.RESUMEN_RESULTADOS_COP,usrSession.getIdSubDelegacion().toString()));
				descargaRR.setListaRCV(getInfoReporteResumen(QuerysCaratula.RESUMEN_RESULTADOS_RCV,usrSession.getIdSubDelegacion().toString()));
				descargaRR.setListaTrabajadores(getInfoReporteResumen(QuerysCaratula.RESUMEN_RESULTADOS_NUM_TRABAJADORES,usrSession.getIdSubDelegacion().toString()));
				
				form.setMensaje(descargaRR.obtenerReporte(usrSession.getNombreDelegacion(),
						   usrSession.getNombreSubDelegacion()));
				logger.info("Reporte: RR-> "+ form.getMensaje());
				break;	
			
			//ctrl_solicitudes_correccion.xls
			case ArchivosXLSCaratula.CASE_CSC:
				logger.info("Reporte: CSC-> Inicio Consulta");
								
				DescargaCSC descargaCSC = new DescargaCSC(
									getInfoReporte(QuerysCaratula.CSC,usrSession.getIdSubDelegacion().toString()), 
									ArchivosXLSCaratula.CSC_XLS,
									ArchivosXLSCaratula.CSC_XLS,
									request, response);
				
				form.setMensaje(descargaCSC.obtenerReporte(usrSession.getNombreDelegacion(),
						   usrSession.getNombreSubDelegacion()));
				logger.info("Reporte: CSC-> "+ form.getMensaje());
				break;
			
			//ctrl_prorroga.xls
			case ArchivosXLSCaratula.CASE_CP:
				logger.info("Reporte: CP-> Inicio Consulta");
				
				DescargaCP descargaCP = new DescargaCP(
									getInfoReporte(QuerysCaratula.CP,usrSession.getIdSubDelegacion().toString()), 
									ArchivosXLSCaratula.CP_XLS,
									ArchivosXLSCaratula.CP_XLS,
									request, response);
				
				form.setMensaje(descargaCP.obtenerReporte(usrSession.getNombreDelegacion(),
								usrSession.getNombreSubDelegacion()));
				
				logger.info("Reporte: CP-> "+ form.getMensaje());
				break;
			
			//ctrl_folios_corr_espo_ord.xls
			case ArchivosXLSCaratula.CASE_CFCE:
				logger.info("Reporte: CFCE-> Inicio Consulta");
				
				DescargaCFCE descargaCFCE = new DescargaCFCE(
									getInfoReporte(QuerysCaratula.CFCE,usrSession.getIdSubDelegacion().toString()), 
									ArchivosXLSCaratula.CFCE_XLS,
									ArchivosXLSCaratula.CFCE_XLS,
									request, response);
				
				form.setMensaje(descargaCFCE.obtenerReporte(usrSession.getNombreDelegacion(),
						   usrSession.getNombreSubDelegacion()));
				
				logger.info("Reporte: CFCE-> "+ form.getMensaje());
				break;
			
			//ctrl_folios_corr_espo_contruccion.xls
			case ArchivosXLSCaratula.CASE_CFCCE:
				logger.info("Reporte: CFCCE-> Inicio Consulta");
								
				DescargaCFCCE descargaCFCCE = new DescargaCFCCE(
									getInfoReporte(QuerysCaratula.CFCCE,usrSession.getIdSubDelegacion().toString()), 
									ArchivosXLSCaratula.CFCCE_XLS,
									ArchivosXLSCaratula.CFCCE_XLS,
									request, response);
				
				form.setMensaje(descargaCFCCE.obtenerReporte(usrSession.getNombreDelegacion(),
						   usrSession.getNombreSubDelegacion()));
				logger.info("Reporte: CFCCE-> "+ form.getMensaje());
				break;
			
			//ctrl_folios_correccion_construc_invitacion.xls
				
			case ArchivosXLSCaratula.CASE_CFCI:
				logger.info("Reporte: CFCI-> Inicio Consulta");
				
				DescargaCFCI descargaCFCI = new DescargaCFCI(
									getInfoReporte(QuerysCaratula.CFCI,usrSession.getIdSubDelegacion().toString()), 
									ArchivosXLSCaratula.CFCI_XLS,
									ArchivosXLSCaratula.CFCI_XLS,
									request, response);
				
				form.setMensaje(descargaCFCI.obtenerReporte(usrSession.getNombreDelegacion(),
						   usrSession.getNombreSubDelegacion()));
				logger.info("Reporte: CFCI-> "+ form.getMensaje());
				break;
			
			//ctrl_folios_promocion_ordinario.xls	
			case ArchivosXLSCaratula.CASE_CFPO:
				logger.info("Reporte: CFPO-> Inicio Consulta");
				
				DescargaCFPO descargaCFPO = new DescargaCFPO(
									getInfoReporte(QuerysCaratula.CFPO,usrSession.getIdSubDelegacion().toString()), 
									ArchivosXLSCaratula.CFPO_XLS,
									ArchivosXLSCaratula.CFPO_XLS,
									request, response);
				
				form.setMensaje(descargaCFPO.obtenerReporte(usrSession.getNombreDelegacion(),
						   usrSession.getNombreSubDelegacion()));
				logger.info("Reporte: CFPO-> "+ form.getMensaje());
				break;
			
			//ctrl_folios_SATICA.xls	
			case ArchivosXLSCaratula.CASE_CFSATICA:
				logger.info("Reporte: CFSATICA-> Inicio Consulta");
				
				DescargaCFSATICA descargaCFSATICA = new DescargaCFSATICA(
									getInfoReporte(QuerysCaratula.CFSATICA,usrSession.getIdSubDelegacion().toString()), 
									ArchivosXLSCaratula.CFSATICA_XLS,
									ArchivosXLSCaratula.CFSATICA_XLS,
									request, response);
				
				form.setMensaje(descargaCFSATICA.obtenerReporte(usrSession.getNombreDelegacion(),
						   usrSession.getNombreSubDelegacion()));
				logger.info("Reporte: CFSATICA-> "+ form.getMensaje());
				break;
			
			//ctrl_folios_promocion_contruccion.xls	
			case ArchivosXLSCaratula.CASE_CFPC:
				logger.info("Reporte: CFPC-> Inicio Consulta");
				
				DescargaCFPC descargaCFPC = new DescargaCFPC(
									getInfoReporte(QuerysCaratula.CFPC,usrSession.getIdSubDelegacion().toString()), 
									ArchivosXLSCaratula.CFPC_XLS,
									ArchivosXLSCaratula.CFPC_XLS,
									request, response);
				
				form.setMensaje(descargaCFPC.obtenerReporte(usrSession.getNombreDelegacion(),
						   usrSession.getNombreSubDelegacion()));
				logger.info("Reporte: CFPC-> "+ form.getMensaje());
				break;
			
			//pagos_registrados.xls
			case ArchivosXLSCaratula.CASE_PD:
				logger.info("Reporte: PD -> Inicio Consulta");
				
				DescargaPD descargaPD = new DescargaPD(
									getInfoReporte(QuerysCaratula.PD,usrSession.getIdSubDelegacion().toString()), 
									ArchivosXLSCaratula.PD_XLS,
									ArchivosXLSCaratula.PD_XLS,
									request, response);
				
				form.setMensaje(descargaPD.obtenerReporte(usrSession.getNombreDelegacion(),
						   usrSession.getNombreSubDelegacion()));
				
				logger.info("Reporte: PD-> "+ form.getMensaje());
				break;
			
			//reporte_trabajadores.xls
			case ArchivosXLSCaratula.CASE_TD:
				logger.info("Reporte: TD -> Inicio Consulta");
				
				DescargaTD descargaTD = new DescargaTD(
									getInfoReporte(QuerysCaratula.TD,usrSession.getIdSubDelegacion().toString()),
									ArchivosXLSCaratula.TD_XLS,
									ArchivosXLSCaratula.TD_XLS,
									request, response);
				
				form.setMensaje(descargaTD.obtenerReporte(usrSession.getNombreDelegacion(),
						   usrSession.getNombreSubDelegacion()));
				
				logger.info("Reporte: TD-> "+ form.getMensaje());
				break;
				
			// correccion_detalle.xls
			case ArchivosXLSCaratula.CASE_CORRECCION_DETALLE:
				logger.info("Reporte: CORRECCION_DETALLE -> Inicio Consulta");
				DescargaCTCPCD descargaCORRECCION_DETALLE = new DescargaCTCPCD(
						            getInfoReporte(QuerysCaratula.CORRECCION_DETALLE,usrSession.getIdSubDelegacion().toString()),
									ArchivosXLSCaratula.CORRECCION_DETALLE_XLS,
									ArchivosXLSCaratula.CORRECCION_DETALLE_XLS,
									request, response);
				
				form.setMensaje(descargaCORRECCION_DETALLE.obtenerReporte(usrSession.getNombreDelegacion(),
						   usrSession.getNombreSubDelegacion()));
				logger.info("Reporte: CORRECCION_DETALLE-> "+ form.getMensaje());
				break;
				
	
			case ArchivosXLSCaratula.CASE_CORRECCION_RESUMEN:
				logger.info("Reporte: CORRECCION_RESUMEN -> Inicio Consulta");
				DescargaCorreccionResumen descargaCORRECCION_RESUMEN = new DescargaCorreccionResumen(
			            getInfoReporte(QuerysCaratula.CORRECCION_RESUMEN,usrSession.getIdSubDelegacion().toString()),
						ArchivosXLSCaratula.CORRECCION_RESUMEN_XLS,
						ArchivosXLSCaratula.CORRECCION_RESUMEN_XLS,
						request, response);

				form.setMensaje(descargaCORRECCION_RESUMEN.obtenerReporte(usrSession.getNombreDelegacion(),
						   usrSession.getNombreSubDelegacion()));
				logger.info("Reporte: CORRECCION_RESUMEN-> "+ form.getMensaje());
				break;
				
	
			case ArchivosXLSCaratula.CASE_PROMOCION_DETALLE:
				logger.info("Reporte: PROMOCION_DETALLE -> Inicio Consulta");
				DescargaPromocionDetalle descargaPROMOCION_DETALLE = new DescargaPromocionDetalle(
									getInfoReporte(QuerysCaratula.PROMOCION_DETALLE,usrSession.getIdSubDelegacion().toString()),
									ArchivosXLSCaratula.PROMOCION_DETALLE_XLS,
									ArchivosXLSCaratula.PROMOCION_DETALLE_XLS,
									request, response);
				
				form.setMensaje(descargaPROMOCION_DETALLE.obtenerReporte(usrSession.getNombreDelegacion(),
						   usrSession.getNombreSubDelegacion()));
				logger.info("Reporte: PROMOCION_DETALLE-> "+ form.getMensaje());
				break;
						
				
				
			case ArchivosXLSCaratula.CASE_PROMOCION_RESUMEN:
				logger.info("Reporte: PROMOCION_RESUMEN -> Inicio Consulta");
				DescargaPromocionResumen descargaPROMOCION_RESUMEN = new DescargaPromocionResumen(
						            getInfoReporte(QuerysCaratula.PROMOCION_RESUMEN,usrSession.getIdSubDelegacion().toString()),
									ArchivosXLSCaratula.PROMOCION_RESUMEN_XLS,
									ArchivosXLSCaratula.PROMOCION_RESUMEN_XLS,
									request, response);
				
				form.setMensaje(descargaPROMOCION_RESUMEN.obtenerReporte(usrSession.getNombreDelegacion(),
						   usrSession.getNombreSubDelegacion()));
				logger.info("Reporte: PROMOCION_RESUMEN-> "+ form.getMensaje());
				break;
				
			default:
				form.setMensaje(ArchivosXLSCaratula.MENSAJE_REPORTE_NO_ENCONTRADO);
				
		}

			
		}catch(Exception e){
			e.printStackTrace();
			form.setMensaje(e.getMessage());
			
		}
				
				
		logger.info("Generando Reporte [Caratula]-> Correcciones Presentadas Terminado");
		
		loadCommonCollections(form);
		model.addAttribute("modeloReporte",form);
						
		return "reportes/caratula/reportResponse";
	}
	
	private void loadCommonCollections(ReporteCaratulaFormVO form){
		
		//Tipos de Reportes
		
		ItemVO currentTR = new ItemVO();
		
		currentTR.setId(String.valueOf(ArchivosXLSCaratula.CASE_CCP));
		currentTR.setLabel("Control de Correcciones Presentadas");
		form.getLsTipoReporte().add(currentTR);
		
		form.getLsDelegaciones().add(currentTR);
		form.getLsSubDelegaciones().add(currentTR);
		
		currentTR = new ItemVO();
		currentTR.setId(String.valueOf(ArchivosXLSCaratula.CASE_RFPPC));
		currentTR.setLabel("Reporte de Folios de Promoción Pendientes Conclusión");
		form.getLsTipoReporte().add(currentTR);
		
		
		currentTR = new ItemVO();
		currentTR.setId(String.valueOf(ArchivosXLSCaratula.CASE_RFCPC));
		currentTR.setLabel("Reporte de Folios de Corrección Pendientes Conclusión");
		form.getLsTipoReporte().add(currentTR);
		
		currentTR = new ItemVO();
		currentTR.setId(String.valueOf(ArchivosXLSCaratula.CASE_RFCPA));
		currentTR.setLabel("Reporte de Folios de Corrección en Proceso de Autodeterminación");
		form.getLsTipoReporte().add(currentTR);
		/*
		currentTR = new TipoReporteVO();
		currentTR.setId(String.valueOf(ArchivosXLSCaratula.CASE_RR));
		currentTR.setLabel("Co");
		form.getLsTipoReporte().add(currentTR);
		*/
		currentTR = new ItemVO();
		currentTR.setId(String.valueOf(ArchivosXLSCaratula.CASE_CSC));
		currentTR.setLabel("Control de Solicitudes de Corrección");
		form.getLsTipoReporte().add(currentTR);
		
		currentTR = new ItemVO();
		currentTR.setId(String.valueOf(ArchivosXLSCaratula.CASE_CP));
		currentTR.setLabel("Control de Prorrogas");
		form.getLsTipoReporte().add(currentTR);
		
		currentTR = new ItemVO();
		currentTR.setId(String.valueOf(ArchivosXLSCaratula.CASE_CFCE));
		currentTR.setLabel("Control de Folios de Corrección Espontánea Ordinario");
		form.getLsTipoReporte().add(currentTR);
		
		currentTR = new ItemVO();
		currentTR.setId(String.valueOf(ArchivosXLSCaratula.CASE_CFCCE));
		currentTR.setLabel("Control de Folios de Corrección Espontánea de Contrucción");
		form.getLsTipoReporte().add(currentTR);
		
		currentTR = new ItemVO();
		currentTR.setId(String.valueOf(ArchivosXLSCaratula.CASE_CFCI));
		currentTR.setLabel("Control de Folios de Corrección de Construcción por Invitación");
		form.getLsTipoReporte().add(currentTR);
		
		currentTR = new ItemVO();
		currentTR.setId(String.valueOf(ArchivosXLSCaratula.CASE_CFPO));
		currentTR.setLabel("Control de Folios de Promoción Ordinario");
		form.getLsTipoReporte().add(currentTR);
		
		currentTR = new ItemVO();
		currentTR.setId(String.valueOf(ArchivosXLSCaratula.CASE_CFSATICA));
		currentTR.setLabel("Control de Folios SATICA");
		form.getLsTipoReporte().add(currentTR);
		
		currentTR = new ItemVO();
		currentTR.setId(String.valueOf(ArchivosXLSCaratula.CASE_CFPC));
		currentTR.setLabel("Control de Folios de Promoción Construcción");
		form.getLsTipoReporte().add(currentTR);
		
		currentTR = new ItemVO();
		currentTR.setId(String.valueOf(ArchivosXLSCaratula.CASE_PD));
		currentTR.setLabel("Reportes de Pagos Registrados");
		form.getLsTipoReporte().add(currentTR);
		
		currentTR = new ItemVO();
		currentTR.setId(String.valueOf(ArchivosXLSCaratula.CASE_TD));
		currentTR.setLabel("Reporte de Trabajadores");
		form.getLsTipoReporte().add(currentTR);		

		currentTR = new ItemVO();
		currentTR.setId(String.valueOf(ArchivosXLSCaratula.CASE_CORRECCION_DETALLE));
		currentTR.setLabel("Control de Trámite de Corrección Patronal (Detalle)");
		form.getLsTipoReporte().add(currentTR);

		currentTR = new ItemVO();
		currentTR.setId(String.valueOf(ArchivosXLSCaratula.CASE_CORRECCION_RESUMEN));
		currentTR.setLabel(" Control de Trámite de Corrección Patronal (Resumen)");
		form.getLsTipoReporte().add(currentTR);

		currentTR = new ItemVO();
		currentTR.setId(String.valueOf(ArchivosXLSCaratula.CASE_PROMOCION_DETALLE));
		currentTR.setLabel("Control de Promociones (Exhorto)(Detalle)");
		form.getLsTipoReporte().add(currentTR);

		currentTR = new ItemVO();
		currentTR.setId(String.valueOf(ArchivosXLSCaratula.CASE_PROMOCION_RESUMEN));
		currentTR.setLabel("Control de Promociones (Exhorto)(Resumen)");		
		form.getLsTipoReporte().add(currentTR);
		
		currentTR = new ItemVO();
		currentTR.setId(String.valueOf(ArchivosXLSCaratula.CASE_RESUMEN_DE_RESULTADOS));
		currentTR.setLabel("Resumen de Resultados");		
		form.getLsTipoReporte().add(currentTR);
		
		Calendar calendar = Calendar.getInstance();
		Integer anioActual = calendar.get(Calendar.YEAR);

		currentTR = new ItemVO();
		currentTR.setId("0");
		currentTR.setLabel("Seleccione periodo");
		form.getLsAniosFiscales().add(currentTR);
		
		for(int i=anioActual;i>anioActual-QuerysCaratula.NUMERO_ANIOS_FISCALES;i--){
			currentTR = new ItemVO();
			currentTR.setId(i + "");
			currentTR.setLabel(i + "");
			form.getLsAniosFiscales().add(currentTR);
		}
		
	}
	
	private List getInfoReporte(String SQL,String idSubDelegacion) throws Exception{		
				
		SQL = SQL.replace("{1}", idSubDelegacion);		
		logger.debug("REPORTES CONTROL GESTION::"+SQL);
		List myList =catalogoServiceBean.consultaSQL(SQL);
		logger.debug("REPORTES CONTROL, SE OBTUVIERON DATOS PARA REPORTE");
		
		if(myList==null){
			throw new Exception("Se generó un error al generar el reporte::getInfoReporte");
		}		
		return myList; 		
	}
	
	
	private List getInfoReporteResumen(String SQL,String idSubDelegacion) throws Exception{	
		
		DecimalFormat formatSubfolio = new DecimalFormat("00");
		Calendar cal = Calendar.getInstance();		
		Calendar calInici=Calendar.getInstance();
		Calendar calFinal=Calendar.getInstance();
		
		int year= cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int monthActual=0;		
		cal.setTime(Calendar.getInstance().getTime());
	
		
		if(month==0){
			monthActual=11;
			year-=1;
		}else{
			monthActual=month-1;
			month=0;
		}
		calInici.set(Calendar.MONTH, month);
		calInici.set(Calendar.YEAR, year);
		calInici.set(Calendar.DAY_OF_MONTH,1);
		
		
		calFinal.set(Calendar.MONTH, monthActual);
		calFinal.set(Calendar.YEAR, year);
		
		int maxDay = calFinal.getActualMaximum(Calendar.DAY_OF_MONTH);
		calFinal.set(Calendar.DAY_OF_MONTH, maxDay);
		

		
		String strFechaInicio = "to_date('01/"+formatSubfolio.format(calInici.get(Calendar.MONTH)+1)+"/" + calInici.get(Calendar.YEAR) + " 00','dd/mm/yyyy HH24')";
		String strFechaFin = "to_date('"+(calFinal.getActualMaximum(Calendar.DAY_OF_MONTH))+"/"+formatSubfolio.format(calFinal.get(Calendar.MONTH)+1)+"/" + calFinal.get(Calendar.YEAR) + " 23:59:59','dd/mm/yyyy HH24:MI:SS')";

		
		SQL = SQL.replace("{1}", idSubDelegacion);	
		SQL = SQL.replace("{2}", strFechaInicio);	
		SQL = SQL.replace("{3}", strFechaFin);		
		logger.debug("REPORTES CONTROL GESTION::"+SQL);
		List myList =catalogoServiceBean.consultaSQL(SQL);
		logger.debug("REPORTES CONTROL, SE OBTUVIERON DATOS PARA REPORTE");
		
		if(myList==null){
			throw new Exception("Se generó un error al generar el reporte::getInfoReporte");
		}		
		return myList; 	 		
	}
	
	private List getInfoReporteConAnio(String SQL,String idSubDelegacion, Integer anio) throws Exception{	
		String strFechaInicio = "to_date('01/01/" + anio + " 00','dd/mm/yyyy HH24')";
		String strFechaFin = "to_date('31/12/" + anio + " 23:59:59','dd/mm/yyyy HH24:MI:SS')";
		
		SQL = SQL.replace("{1}", idSubDelegacion);	
		SQL = SQL.replace("{2}", strFechaInicio);	
		SQL = SQL.replace("{3}", strFechaFin);		
		logger.debug("REPORTES CONTROL GESTION::"+SQL);
		List myList =catalogoServiceBean.consultaSQL(SQL);
		logger.debug("REPORTES CONTROL, SE OBTUVIERON DATOS PARA REPORTE");
		
		if(myList==null){
			throw new Exception("Se generó un error al generar el reporte::getInfoReporte");
		}		
		return myList; 		
	}
	
	
	
	private String recuperaNombreDelegacion(int idDelegacion){
		
		String query="SELECT NOM_NOMBRE FROM SAC_DELEGACION where CVE_PK="+idDelegacion;
		List<AbstractModel> lista=catalogoServiceBean.consultaSQL(query);
		Iterator<?> iter = lista.iterator();
		String nombre="";
		while(iter.hasNext()){			
			nombre=iter.next().toString();
		}
		return nombre;
	}
	
	private String recuperaNombreSubDelegacion(int idSubDelegacion){
		
		String query="SELECT NOM_NOMBRE FROM SAC_SUBDELEGACION where CVE_PK="+idSubDelegacion;
		List<AbstractModel> lista=catalogoServiceBean.consultaSQL(query);
		Iterator<?> iter = lista.iterator();
		String nombre="";
		while(iter.hasNext()){			
			nombre=iter.next().toString();
		}
		return nombre;
	}


}
