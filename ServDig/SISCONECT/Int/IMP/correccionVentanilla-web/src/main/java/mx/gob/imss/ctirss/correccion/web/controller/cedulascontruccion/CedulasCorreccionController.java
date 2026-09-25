package mx.gob.imss.ctirss.correccion.web.controller.cedulascontruccion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import mx.gob.imss.ctirss.correccion.catalogos.model.CrcGastos;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcGrupoCategoria;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcMes;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcPercepciones;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcTrabajadores;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrtEjertrabajador;
import mx.gob.imss.ctirss.correccion.prorroga.service.interfaces.ProrrogaService;
import mx.gob.imss.ctirss.correccion.service.interfaces.ICatalogoService;
import mx.gob.imss.ctirss.correccion.service.interfaces.IPatronesService;
import mx.gob.imss.ctirss.correccion.session.UserSession;
import mx.gob.imss.ctirss.correccion.utils.Functions;
import mx.gob.imss.ctirss.correccion.catalogos.service.interfaces.GastosService;
import mx.gob.imss.ctirss.correccion.catalogos.service.interfaces.PercepcionesService;
import mx.gob.imss.ctirss.correccion.catalogos.service.interfaces.TrabajadoresService;
import mx.gob.imss.ctirss.correccion.constantes.CedulasCorreccion;
import mx.gob.imss.ctirss.correccion.correccion.service.interfaces.SolicitudService;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.model.CrtAnexosolcorrpat;
import mx.gob.imss.ctirss.correccion.model.CrtControlFlujoCedula;
import mx.gob.imss.ctirss.correccion.model.CrtControlFlujoCedulaPK;
import mx.gob.imss.ctirss.correccion.model.CrtEstatusFlujoCedula;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.model.SatPatron;
import mx.gob.imss.ctirss.correccion.monitor.service.interfaces.MonitorService;
import mx.gob.imss.ctirss.correccion.web.controller.monitor.EstatusCorrecciones;
import mx.gob.imss.ctirss.correccion.web.controller.utils.ValidaSolicitudCorreccion;
import mx.gob.imss.ctirss.correccion.web.desgargas.cedulascorreccion.DescargaCOP;
import mx.gob.imss.ctirss.correccion.web.desgargas.cedulascorreccion.DescargaCedulaA;
import mx.gob.imss.ctirss.correccion.web.desgargas.cedulascorreccion.DescargaCedulaG;
import mx.gob.imss.ctirss.correccion.web.desgargas.cedulascorreccion.DescargaCedulaH;
import mx.gob.imss.ctirss.correccion.web.desgargas.cedulascorreccion.DescargaCedulaI;
import mx.gob.imss.ctirss.correccion.web.desgargas.cedulascorreccion.DescargaCedulaO;
import mx.gob.imss.ctirss.correccion.web.desgargas.cedulascorreccion.DescargaCedulaQ;
import mx.gob.imss.ctirss.correccion.web.desgargas.cedulascorreccion.DescargaDetalleTrabajadores;
import mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula.ReporteCaratulaFormVO;

@Controller
@RequestMapping(value="/cedulasCorreccion/descarga")
public class CedulasCorreccionController extends AbstractController{

	/**
	 * Logger
	 */
	private final static Logger logger = Logger.getLogger(CedulasCorreccionController.class);
	
	@Autowired
	private PercepcionesService<CrcPercepciones> persepcionesServiceBean;

	@Autowired
	private GastosService<CrcGastos> gastosServiceBean;

	@Autowired
	private TrabajadoresService<CrtEjertrabajador> trabajadoresServiceBean;

	@Autowired
	private SolicitudService<CrtAnexosolcorrpat> solicitudServiceBean;
	
	@Autowired
	private ICatalogoService<CrcMes> catalogoServiceBean;
	
	
	@Autowired
	private ICatalogoService<CrcGrupoCategoria> catalogoServiceCategoria;
	
	@Autowired
	private ICatalogoService<AbstractModel> catalogoServiceBeanPeriodo;
	
	@Autowired
	private IPatronesService<SatPatron>  patronesServiceBean;
	
	@Autowired
	private MonitorService<CrtControlFlujoCedula> monitorServiceBean;
	
	@Autowired
	private ProrrogaService<CrtAnexosolcorrpat> prorrogaServiceBean;
	
	@Autowired
	private ValidaSolicitudCorreccion validaSolicitudService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String getCreateForm(Model model,HttpServletRequest request) {
		UserSession user = getUsuarioFirmado(request);
		//crcTrabajadores.setCveSubdelegcionOficial(Functions.getCveSubdelegacionOficial(user));
		
		model.addAttribute("valCveSubdelCedula",Functions.getCveSubdelegacionOficial(user));
		
		
		model.addAttribute("descargaCedula",new DescargaCedula());
				
		return determinaURL(request, "cedulasCorreccion/descarga/descargaMain", "cedulasCorreccion/descarga/descargaMainPatron");
	}
	
	//
	@RequestMapping(value="/getPeriodosCorreccion" , method=RequestMethod.POST)
	public @ResponseBody List<?> getPeriodosCorreccion(@RequestBody DescargaCedula descargaCedula,HttpServletRequest request) {
		
		
		String SQL = AbstractCedulasSQL.OBTENER_PERIODOS_CORRECCION.replace("{1}",descargaCedula.getFolioCorreccion());
		
		List<?> ls = catalogoServiceBeanPeriodo.consultaSQL(SQL);
				
		return ls;
	}
	//
	
	@RequestMapping(value="/descargaReturn" , method=RequestMethod.POST)
	public String getCreateForm2(Model model,HttpServletRequest request) {
		//logger.info("getCreateForm2");
		UserSession user = getUsuarioFirmado(request);
		model.addAttribute("valCveSubdelCedula",Functions.getCveSubdelegacionOficial(user));
		
		
		DescargaCedula form = new DescargaCedula();
		model.addAttribute("descargaCedula",form);
		
		return determinaURL(request, "cedulasCorreccion/descarga/descargaMain", "cedulasCorreccion/descarga/descargaMainPatron");
	}
	
	@RequestMapping(value="/descargaWindow" , method=RequestMethod.GET)
	public String showDescargaWindow(Model model,HttpServletRequest request) {
		
		DescargaCedula form= new DescargaCedula();
		form.setIdArchivoDescarga(new Integer(request.getParameter("idArchivoDescarga")).intValue());
		form.setFolioCorreccion(String.valueOf(request.getParameter("folioCorreccion")));
		form.setPeriodo(String.valueOf(request.getParameter("periodo")));
		logger.info("IDCedula  "+form.getIdArchivoDescarga());
		logger.info("FolioCorreccion "+form.getFolioCorreccion());
		logger.info("Periodo "+form.getPeriodo());
		model.addAttribute("descargaCedula",form);		
		 return "cedulasCorreccion/descarga/descargaWindow";
	}
	
	
	@RequestMapping(value="/validaArchivo" , method=RequestMethod.POST)
	public @ResponseBody DescargaCedula  validarArchivoDescarga(@RequestBody DescargaCedula cedula, HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println("Validando archivo "+cedula.getFolioCorreccion());
		UserSession user = getUsuarioFirmado(request);
		
		CrtSolicitudcorr par=new CrtSolicitudcorr();
		par.setNuFolio(cedula.getFolioCorreccion());
		CrtSolicitudcorr sol=solicitudServiceBean.consultarFolio(par);
		if(sol==null){
			cedula.setMsg("El folio no existe");
			return cedula;
		}
		
		if(!(sol.getCveSubdelegacion().intValue()==user.getIdSubDelegacion().intValue())){
			cedula.setMsg("El folio no pertenece a la subdelegacion");
			return cedula;
		}
		
		CrtAnexosolcorrpat anexo = new CrtAnexosolcorrpat();
		anexo.setNuFolio(cedula.getFolioCorreccion());
		CrtAnexosolcorrpat resultado  = this.prorrogaServiceBean.consultarPorFolio(anexo);		
		
		
		if(resultado==null) cedula.setMsg("La Solicitud de la Correcion No existe");
		else if(resultado!=null && resultado.getEstadoFolioCorr()==1) cedula.setMsg("La Solicitud de la Correcion No ha sido aceptada");
		else if(resultado!=null && resultado.getEstadoFolioCorr()==4) cedula.setMsg("La Solicitud de la Correcion ya ha sido Presentada");
		else if(resultado!=null && resultado.getEstadoFolioCorr()==5) cedula.setMsg("La Solicitud de la Correcion se ha derivado a dictamen");
		else if(resultado!=null && resultado.getEstadoFolioCorr()==6) cedula.setMsg("La Solicitud de la Correcion ha sido cancelado");
		else{
			cedula.setMsg("true");
		}
		return cedula;
	}

	@RequestMapping(value="/archivo" , method=RequestMethod.POST)
	public String  descargar(DescargaCedula cedula, HttpServletRequest request, HttpServletResponse response) {
		logger.debug(".--. Descargar Cedula");
		
		
		System.setProperty("file.encoding","UTF-8");
		
		List<CrcGastos> lsGastos = null;
		List<CrcPercepciones> lsPercepciones = null;
		List<CrcTrabajadores> lsTrabajadores = null;
		List<CrtEjertrabajador> lsTrabajadoresPatron = null;
		CrtSolicitudcorr patrones = null;
		CrtEjertrabajador trabajadores =null;
		CrcPercepciones crcPercepciones = null;
		CrcGastos crcGastos = null;
		String nombre = "";
		List<CrcMes> lsMeses = null;
		UserSession user = getUsuarioFirmado(request);	

		CrtAnexosolcorrpat anexo = new CrtAnexosolcorrpat();
		anexo.setNuFolio(cedula.getFolioCorreccion());
		CrtAnexosolcorrpat resultado  = this.prorrogaServiceBean.consultarPorFolio(anexo);
		
		Boolean continuar = false;
		
		if(resultado==null) cedula.setMsg("La Solicitud de la Correcion No existe");
		else if(resultado!=null && resultado.getEstadoFolioCorr()==1) cedula.setMsg("La Solicitud de la Correcion No ha sido aceptada");
		else if(resultado!=null && resultado.getEstadoFolioCorr()==4) cedula.setMsg("La Solicitud de la Correcion ya ha sido Presentada");
		
		
		if(cedula.getMsg().equals("")){
			
			switch(cedula.getIdArchivoDescarga().intValue()){
			case CedulasCorreccion.ID_CEDULA_A:

				patrones = new CrtSolicitudcorr();
				patrones.setNuFolio(cedula.getFolioCorreccion());
				patrones.setPeriodo(new Integer(cedula.getPeriodo()));
				
				patrones = solicitudServiceBean.consultarPatrones(patrones);

				if(patrones==null){cedula.setMsg(CedulasCorreccion.NO_EXISTE_FOLIO_PERIODO);break;}
				
				crcPercepciones = new CrcPercepciones();
				crcPercepciones.setCveSolicitudCorr(patrones.getCveSolicitudCorr());
				lsPercepciones = persepcionesServiceBean.consultar(crcPercepciones);

				if(lsPercepciones==null || lsPercepciones.isEmpty()){cedula.setMsg(CedulasCorreccion.NO_EXISTE_PERCEPCIONES);break;}
				
				crcGastos = new CrcGastos();
				crcGastos.setCveSolicitudCorr(patrones.getCveSolicitudCorr());
				lsGastos = gastosServiceBean.consultar(crcGastos);

				if(lsGastos==null || lsGastos.isEmpty()){cedula.setMsg(CedulasCorreccion.NO_EXISTE_GASTOS);break;}

				DescargaCedulaA cedulaA;
				
				try {
					List<CrtAnexosolcorrpat> patroneFiscal=solicitudServiceBean.consultarAnexos(patrones.getCveSolicitudCorr(), "F");
					SatPatron patron=patronesServiceBean.getById(patroneFiscal.get(0).getCvePatron());
					patroneFiscal.get(0).setRegistroPatronal(patron.getRegistroPatronal());
					cedulaA = new DescargaCedulaA(request, response, 
							cedula.getIdFolioCorreccion(), cedula.getIdArchivoDescarga(), 
							lsPercepciones, lsGastos, patrones.getLstAnexoSolicitudesCorr(),patrones, cedula.getFolioCorreccion(), cedula.getPeriodo(),patroneFiscal.get(0));

					nombre = CedulasCorreccion.CEDULA_A.substring(0, CedulasCorreccion.CEDULA_A.length()-4);

					//Guardamos el estatus para descargado
					 
					CrtControlFlujoCedulaPK idPk = new CrtControlFlujoCedulaPK();
					idPk.setCveCedula(new Long(cedula.getIdArchivoDescarga()));
					idPk.setCveSolicitudcorr(patrones.getCveSolicitudCorr());
					idPk.setCveEjercicio(new Integer(cedula.getPeriodo()));
					// Estatus DesCargado
					CrtEstatusFlujoCedula estatusFlujoCedula = new CrtEstatusFlujoCedula();
					estatusFlujoCedula.setCveEstatus(Long.valueOf(EstatusCorrecciones.ESTATUS_DESCARGADO.toString()));

					CrtControlFlujoCedula model = new CrtControlFlujoCedula();
					model.setFecFechareg(new Date());
					model.setId(idPk);
					if(user.getCveIdUsuario()!=null){
						model.setCveUsuario(user.getCveIdUsuario()!=null ? user.getCveIdUsuario().toString():null);	
					}
					
					model.setCrtEstatusFlujoCedula(estatusFlujoCedula);
					
					monitorServiceBean.agregar(model);


					cedulaA.generarXLS(nombre+"-"+cedula.getFolioCorreccion()+"-"+cedula.getPeriodo()+".xlsm");

				} catch (Exception e) {
					e.printStackTrace();
				}
				break;

			case CedulasCorreccion.ID_CEDULA_G:

				patrones = new CrtSolicitudcorr();
				patrones.setNuFolio(cedula.getFolioCorreccion());
				patrones.setPeriodo(new Integer(cedula.getPeriodo()));
				
				patrones = solicitudServiceBean.consultarPatrones(patrones);	

				DescargaCedulaG cedulaG;

				try {
					List<CrtAnexosolcorrpat> patroneFiscal=solicitudServiceBean.consultarAnexos(patrones.getCveSolicitudCorr(), "F");
					SatPatron patron=patronesServiceBean.getById(patroneFiscal.get(0).getCvePatron());
					patroneFiscal.get(0).setRegistroPatronal(patron.getRegistroPatronal());
					cedulaG = new DescargaCedulaG(request, response, 
							cedula.getIdFolioCorreccion(), cedula.getIdArchivoDescarga(), 
							patrones.getLstAnexoSolicitudesCorr(), patrones,cedula.getFolioCorreccion(), cedula.getPeriodo(),patroneFiscal.get(0));

					nombre = CedulasCorreccion.CEDULA_G.substring(0, CedulasCorreccion.CEDULA_G.length()-4);

					/*Guardamos el estatus para descargado
					 * */
					CrtControlFlujoCedulaPK idPk = new CrtControlFlujoCedulaPK();
					idPk.setCveCedula(new Long(cedula.getIdArchivoDescarga()));
					idPk.setCveSolicitudcorr(patrones.getCveSolicitudCorr());
					idPk.setCveEjercicio(new Integer(cedula.getPeriodo()));
					
					// Estatus DesCargado
					CrtEstatusFlujoCedula estatusFlujoCedula = new CrtEstatusFlujoCedula();
					estatusFlujoCedula.setCveEstatus(Long.valueOf(EstatusCorrecciones.ESTATUS_DESCARGADO.toString()));

					CrtControlFlujoCedula model = new CrtControlFlujoCedula();
					model.setFecFechareg(new Date());
					model.setId(idPk);
					model.setCveUsuario(user.getCveIdUsuario()!=null ? user.getCveIdUsuario().toString():null);
					model.setCrtEstatusFlujoCedula(estatusFlujoCedula);

					monitorServiceBean.agregar(model);


					cedulaG.generarXLS(nombre+"-"+cedula.getFolioCorreccion()+"-"+cedula.getPeriodo()+".xlsm");

				} catch (Exception e) {
					e.printStackTrace();
				}
				break;

			case CedulasCorreccion.ID_CEDULA_H:

				continuar = false;
				patrones = new CrtSolicitudcorr();
				patrones.setNuFolio(cedula.getFolioCorreccion());
				patrones.setPeriodo(new Integer(cedula.getPeriodo()));
				
				patrones = solicitudServiceBean.consultarPatrones(patrones);

				if(patrones.getLstAnexoSolicitudesCorr()!=null && !patrones.getLstAnexoSolicitudesCorr().isEmpty()){
					Iterator<CrtAnexosolcorrpat> iter =  (Iterator<CrtAnexosolcorrpat>) patrones.getLstAnexoSolicitudesCorr().iterator();
					CrtAnexosolcorrpat currentItem = null;
					
					while(iter.hasNext()){
						currentItem = iter.next();

						trabajadores = new CrtEjertrabajador();
						trabajadores.setCveEjercicio(currentItem.getCrcEjercicio().getCveEjercicio());
						trabajadores.setCveAcexoCorrPat(currentItem.getCrcEjercicio().getCveAcexoCorrPat());
						trabajadores.setIndPruebasel(new BigDecimal(1)); // prueba selectiva

						lsTrabajadoresPatron = trabajadoresServiceBean.consultarTrabajadores(trabajadores);


						if(lsTrabajadoresPatron!=null){
							currentItem.setTrabajadores(lsTrabajadoresPatron);
							logger.debug("TAMAÑO TRABAJADORES: " + lsTrabajadoresPatron.size() + " RegistroPatronal: " + currentItem.getRegistroPatronal());
							
							cedula.setMsg("");
							continuar=true;
						}else{
							if(!continuar)
								cedula.setMsg(CedulasCorreccion.NO_EXISTE_TRABAJADORES_DETALLE);
							//break;
						}

					}
				}else{
					cedula.setMsg(CedulasCorreccion.NO_EXISTE_PATRONES_PERIODO);
					break;
				}

				if(cedula.getMsg().equals("")){
					crcPercepciones = new CrcPercepciones();
					crcPercepciones.setCveSolicitudCorr(patrones.getCveSolicitudCorr());
					lsPercepciones = persepcionesServiceBean.consultar(crcPercepciones);

					if(lsPercepciones==null || lsPercepciones.isEmpty()){cedula.setMsg(CedulasCorreccion.NO_EXISTE_PERCEPCIONES);break;}
					
					lsMeses = catalogoServiceBean.consultar(new CrcMes());
					List<AbstractModel> categorias=catalogoServiceBeanPeriodo.consultar(new CrcGrupoCategoria());
					
					
					if(lsMeses==null || lsMeses.isEmpty()){cedula.setMsg(CedulasCorreccion.NO_EXISTE_MESES);break;}
					
					DescargaCedulaH cedulaH;
					try {
						List<CrtAnexosolcorrpat> patroneFiscal=solicitudServiceBean.consultarAnexos(patrones.getCveSolicitudCorr(), "F");
						SatPatron patron=patronesServiceBean.getById(patroneFiscal.get(0).getCvePatron());
						patroneFiscal.get(0).setRegistroPatronal(patron.getRegistroPatronal());
						cedulaH = new DescargaCedulaH(request, response, 
								cedula.getIdFolioCorreccion(), cedula.getIdArchivoDescarga(), 
								lsPercepciones,lsTrabajadores, patrones.getLstAnexoSolicitudesCorr(),patrones, lsMeses, cedula.getFolioCorreccion(), cedula.getPeriodo(),categorias,patroneFiscal.get(0));

						nombre = CedulasCorreccion.CEDULA_H.substring(0, CedulasCorreccion.CEDULA_H.length()-4);

						/*Guardamos el estatus para descargado
						 * */
						CrtControlFlujoCedulaPK idPk = new CrtControlFlujoCedulaPK();
						idPk.setCveCedula(new Long(cedula.getIdArchivoDescarga()));
						idPk.setCveSolicitudcorr(patrones.getCveSolicitudCorr());
						idPk.setCveEjercicio(new Integer(cedula.getPeriodo()));
						
						// Estatus DesCargado
						CrtEstatusFlujoCedula estatusFlujoCedula = new CrtEstatusFlujoCedula();
						estatusFlujoCedula.setCveEstatus(Long.valueOf(EstatusCorrecciones.ESTATUS_DESCARGADO.toString()));

						CrtControlFlujoCedula model = new CrtControlFlujoCedula();
						model.setFecFechareg(new Date());
						model.setId(idPk);
						model.setCveUsuario(user.getCveIdUsuario()!=null ? user.getCveIdUsuario().toString():null);
						model.setCrtEstatusFlujoCedula(estatusFlujoCedula);

						monitorServiceBean.agregar(model);


						cedulaH.generarXLS(nombre+"-"+cedula.getFolioCorreccion()+"-"+cedula.getPeriodo()+".xlsm");

					} catch (Exception e) {
						e.printStackTrace();
					}

				}

				
			break;

			case CedulasCorreccion.ID_CEDULA_I:

				continuar = false;
				patrones = new CrtSolicitudcorr();
				patrones.setNuFolio(cedula.getFolioCorreccion());
				patrones.setPeriodo(new Integer(cedula.getPeriodo()));
				
				patrones = solicitudServiceBean.consultarPatrones(patrones);

				if(patrones.getLstAnexoSolicitudesCorr()!=null && !patrones.getLstAnexoSolicitudesCorr().isEmpty()){
					Iterator<CrtAnexosolcorrpat> iter =  (Iterator<CrtAnexosolcorrpat>) patrones.getLstAnexoSolicitudesCorr().iterator();
					CrtAnexosolcorrpat currentItem = null;

					while(iter.hasNext()){
						currentItem = iter.next();

						trabajadores = new CrtEjertrabajador();
						trabajadores.setCveEjercicio(currentItem.getCrcEjercicio().getCveEjercicio());
						trabajadores.setCveAcexoCorrPat(currentItem.getCrcEjercicio().getCveAcexoCorrPat());
						trabajadores.setIndExcsaltop(new BigDecimal(1));// Salarios Topados

						lsTrabajadoresPatron = trabajadoresServiceBean.consultarTrabajadores(trabajadores);
						if(lsTrabajadoresPatron==null){
							lsTrabajadoresPatron=new ArrayList<CrtEjertrabajador>();
						}

						//if(lsTrabajadoresPatron!=null){
							currentItem.setTrabajadores(lsTrabajadoresPatron);
							logger.debug("TAMAÑO TRABAJADORES: " + lsTrabajadoresPatron.size() + " RegistroPatronal: " + currentItem.getRegistroPatronal());
							
							cedula.setMsg("");
							continuar= true;
//						}
						
//						else{
//							if(!continuar)
//								cedula.setMsg(CedulasCorreccion.NO_EXISTE_TRABAJADORES_DETALLE);
//						}

					}
				}else{
					
					cedula.setMsg(CedulasCorreccion.NO_EXISTE_PATRONES_PERIODO);
					break;
					
				}

				if(cedula.getMsg().equals("")){
					crcPercepciones = new CrcPercepciones();
					crcPercepciones.setCveSolicitudCorr(patrones.getCveSolicitudCorr());
					lsPercepciones = persepcionesServiceBean.consultar(crcPercepciones);

					if(lsPercepciones==null || lsPercepciones.isEmpty()){cedula.setMsg(CedulasCorreccion.NO_EXISTE_PERCEPCIONES);break;}
					
					DescargaCedulaI cedulaI;
					try {
						List<CrtAnexosolcorrpat> patroneFiscal=solicitudServiceBean.consultarAnexos(patrones.getCveSolicitudCorr(), "F");
						SatPatron patron=patronesServiceBean.getById(patroneFiscal.get(0).getCvePatron());
						patroneFiscal.get(0).setRegistroPatronal(patron.getRegistroPatronal());
						cedulaI = new DescargaCedulaI(request, response, 
								cedula.getIdFolioCorreccion(), cedula.getIdArchivoDescarga(), 
								lsPercepciones,lsTrabajadores, patrones.getLstAnexoSolicitudesCorr(),patrones, cedula.getFolioCorreccion(), cedula.getPeriodo(),patroneFiscal.get(0));

						nombre = CedulasCorreccion.CEDULA_I.substring(0, CedulasCorreccion.CEDULA_I.length()-4);

						/*Guardamos el estatus para descargado
						 * */
						CrtControlFlujoCedulaPK idPk = new CrtControlFlujoCedulaPK();
						idPk.setCveCedula(new Long(cedula.getIdArchivoDescarga()));
						idPk.setCveSolicitudcorr(patrones.getCveSolicitudCorr());
						idPk.setCveEjercicio(new Integer(cedula.getPeriodo()));
						// Estatus DesCargado
						CrtEstatusFlujoCedula estatusFlujoCedula = new CrtEstatusFlujoCedula();
						estatusFlujoCedula.setCveEstatus(Long.valueOf(EstatusCorrecciones.ESTATUS_DESCARGADO.toString()));

						CrtControlFlujoCedula model = new CrtControlFlujoCedula();
						model.setFecFechareg(new Date());
						model.setId(idPk);
						model.setCveUsuario(user.getCveIdUsuario()!=null ? user.getCveIdUsuario().toString():null);
						model.setCrtEstatusFlujoCedula(estatusFlujoCedula);

						monitorServiceBean.agregar(model);


						cedulaI.generarXLS(nombre+"-"+cedula.getFolioCorreccion()+"-"+cedula.getPeriodo()+".xlsm");

					} catch (Exception e) {
						e.printStackTrace();
					}

				}
				break;

			case CedulasCorreccion.ID_CEDULA_O:
				continuar= false;
				patrones = new CrtSolicitudcorr();
				patrones.setNuFolio(cedula.getFolioCorreccion());
				patrones.setPeriodo(new Integer(cedula.getPeriodo()));
				
				patrones = solicitudServiceBean.consultarPatrones(patrones);

				if(patrones.getLstAnexoSolicitudesCorr()!=null && !patrones.getLstAnexoSolicitudesCorr().isEmpty()){
					Iterator<CrtAnexosolcorrpat> iter =  (Iterator<CrtAnexosolcorrpat>) patrones.getLstAnexoSolicitudesCorr().iterator();
					CrtAnexosolcorrpat currentItem = null;

					while(iter.hasNext()){
						currentItem = iter.next();

						trabajadores = new CrtEjertrabajador();
						trabajadores.setCveEjercicio(currentItem.getCrcEjercicio().getCveEjercicio());
						trabajadores.setCveAcexoCorrPat(currentItem.getCrcEjercicio().getCveAcexoCorrPat());
						trabajadores.setIndAnatiempext(new BigDecimal(1)); // Indicador de tiempo extra

						lsTrabajadoresPatron = trabajadoresServiceBean.consultarTrabajadores(trabajadores);


						//if(lsTrabajadoresPatron!=null){
							currentItem.setTrabajadores(lsTrabajadoresPatron);
							//logger.debug("TAMAÑO TRABAJADORES: " + lsTrabajadoresPatron.size() + " RegistroPatronal: " + currentItem.getRegistroPatronal());
							continuar= true;
							cedula.setMsg("");
//						}else{
//							if(!continuar)
//								cedula.setMsg(CedulasCorreccion.NO_EXISTE_TRABAJADORES_DETALLE);
//							
//						}


					}
				}else{
					cedula.setMsg(CedulasCorreccion.NO_EXISTE_PATRONES_PERIODO);
					break;
				}
				
				if(cedula.getMsg().equals("")){
					crcPercepciones = new CrcPercepciones();
					crcPercepciones.setCveSolicitudCorr(patrones.getCveSolicitudCorr());
					lsPercepciones = persepcionesServiceBean.consultar(crcPercepciones);

					if(lsPercepciones==null || lsPercepciones.isEmpty()){cedula.setMsg(CedulasCorreccion.NO_EXISTE_PERCEPCIONES);break;}
					
					DescargaCedulaO cedulaO;
					
					try {
						List<CrtAnexosolcorrpat> patroneFiscal=solicitudServiceBean.consultarAnexos(patrones.getCveSolicitudCorr(), "F");
						SatPatron patron=patronesServiceBean.getById(patroneFiscal.get(0).getCvePatron());
						patroneFiscal.get(0).setRegistroPatronal(patron.getRegistroPatronal());
						
						cedulaO = new DescargaCedulaO(request, response, 
								cedula.getIdFolioCorreccion(), cedula.getIdArchivoDescarga(), 
								lsPercepciones,lsTrabajadores, patrones.getLstAnexoSolicitudesCorr(),patrones,cedula.getFolioCorreccion(), cedula.getPeriodo(),patroneFiscal.get(0));

						nombre = CedulasCorreccion.CEDULA_O.substring(0, CedulasCorreccion.CEDULA_O.length()-4);

						/*Guardamos el estatus para descargado
						 * */
						CrtControlFlujoCedulaPK idPk = new CrtControlFlujoCedulaPK();
						idPk.setCveCedula(new Long(cedula.getIdArchivoDescarga()));
						idPk.setCveSolicitudcorr(patrones.getCveSolicitudCorr());
						idPk.setCveEjercicio(new Integer(cedula.getPeriodo()));
						// Estatus DesCargado
						CrtEstatusFlujoCedula estatusFlujoCedula = new CrtEstatusFlujoCedula();
						estatusFlujoCedula.setCveEstatus(Long.valueOf(EstatusCorrecciones.ESTATUS_DESCARGADO.toString()));

						CrtControlFlujoCedula model = new CrtControlFlujoCedula();
						model.setFecFechareg(new Date());
						model.setId(idPk);
						model.setCveUsuario(user.getCveIdUsuario()!=null ? user.getCveIdUsuario().toString():null);
						model.setCrtEstatusFlujoCedula(estatusFlujoCedula);

						monitorServiceBean.agregar(model);


						cedulaO.generarXLS(nombre+"-"+cedula.getFolioCorreccion()+"-"+cedula.getPeriodo()+".xlsm");

					} catch (Exception e) {
						e.printStackTrace();
					}

					
				}

				break;

			case CedulasCorreccion.ID_CEDULA_Q:

				continuar= false;
				patrones = new CrtSolicitudcorr();
				patrones.setNuFolio(cedula.getFolioCorreccion());
				patrones.setPeriodo(new Integer(cedula.getPeriodo()));
				
				patrones = solicitudServiceBean.consultarPatrones(patrones);					

				if(patrones.getLstAnexoSolicitudesCorr()!=null && !patrones.getLstAnexoSolicitudesCorr().isEmpty()){
					Iterator<CrtAnexosolcorrpat> iter =  (Iterator<CrtAnexosolcorrpat>) patrones.getLstAnexoSolicitudesCorr().iterator();
					CrtAnexosolcorrpat currentItem = null;

					while(iter.hasNext()){
						currentItem = iter.next();

						trabajadores = new CrtEjertrabajador();
						trabajadores.setCveEjercicio(currentItem.getCrcEjercicio().getCveEjercicio());
						trabajadores.setCveAcexoCorrPat(currentItem.getCrcEjercicio().getCveAcexoCorrPat());
						trabajadores.setIndAnahon(new BigDecimal(1)); // analisis de honorarios

						lsTrabajadoresPatron = trabajadoresServiceBean.consultarTrabajadores(trabajadores);
						if(lsTrabajadoresPatron==null){
							lsTrabajadoresPatron=new ArrayList<CrtEjertrabajador>();
						}

//						if(lsTrabajadoresPatron!=null){
							currentItem.setTrabajadores(lsTrabajadoresPatron);
							logger.debug("TAMAÑO TRABAJADORES: " + lsTrabajadoresPatron.size() + " RegistroPatronal: " + currentItem.getRegistroPatronal());
							cedula.setMsg("");
							continuar= true;
//						}else{
//							if(!continuar)
//								cedula.setMsg(CedulasCorreccion.NO_EXISTE_TRABAJADORES_DETALLE);
//							
//						}

					}
				}else{
					cedula.setMsg(CedulasCorreccion.NO_EXISTE_PATRONES_PERIODO);
					break;
				}


				if(cedula.getMsg().equals("")){
					crcPercepciones = new CrcPercepciones();
					crcPercepciones.setCveSolicitudCorr(patrones.getCveSolicitudCorr());
					lsPercepciones = persepcionesServiceBean.consultar(crcPercepciones);

					if(lsPercepciones==null || lsPercepciones.isEmpty()){cedula.setMsg(CedulasCorreccion.NO_EXISTE_PERCEPCIONES);break;}
					DescargaCedulaQ cedulaQ;
					try {
						List<CrtAnexosolcorrpat> patroneFiscal=solicitudServiceBean.consultarAnexos(patrones.getCveSolicitudCorr(), "F");
						SatPatron patron=patronesServiceBean.getById(patroneFiscal.get(0).getCvePatron());
						patroneFiscal.get(0).setRegistroPatronal(patron.getRegistroPatronal());
						
						
						List<CrcGrupoCategoria> listaCategor=catalogoServiceCategoria.consultaLibrePorClave(0l, "FROM CrcGrupoCategoria where CVE_SOLICITUDCORR= "+patrones.getCveSolicitudCorr());
						Map<Integer, String> mapaCategoras=new HashMap<Integer, String>();
						for(CrcGrupoCategoria cat:listaCategor){
							System.out.println("Grupo categorrecue "+cat.getCveGrupoCategoria()+" "+ cat.getTxGrupoCategoria());
							mapaCategoras.put(cat.getCveGrupoCategoria(), cat.getTxGrupoCategoria());
						}
						
						
						for(CrtAnexosolcorrpat anexosVo:patrones.getLstAnexoSolicitudesCorr()){							
							for(CrtEjertrabajador  trab:anexosVo.getTrabajadores()){
								String actividad=mapaCategoras.get(Integer.valueOf(String.valueOf(trab.getCveCategoria())));
								System.out.println("aactividad "+actividad);
								trab.setTxActividad(mapaCategoras.get(Integer.valueOf(String.valueOf(trab.getCveCategoria()))));
							}							
						}
						
						
						cedulaQ = new DescargaCedulaQ(request, response, 
								cedula.getIdFolioCorreccion(), cedula.getIdArchivoDescarga(), 
								lsPercepciones,lsTrabajadores, patrones.getLstAnexoSolicitudesCorr(),patrones, cedula.getFolioCorreccion(), cedula.getPeriodo(),patroneFiscal.get(0));

						nombre = CedulasCorreccion.CEDULA_Q.substring(0, CedulasCorreccion.CEDULA_Q.length()-4);

						/*Guardamos el estatus para descargado
						 * */
						CrtControlFlujoCedulaPK idPk = new CrtControlFlujoCedulaPK();
						idPk.setCveCedula(new Long(cedula.getIdArchivoDescarga()));
						idPk.setCveSolicitudcorr(patrones.getCveSolicitudCorr());
						idPk.setCveEjercicio(new Integer(cedula.getPeriodo()));
						// Estatus DesCargado
						CrtEstatusFlujoCedula estatusFlujoCedula = new CrtEstatusFlujoCedula();
						estatusFlujoCedula.setCveEstatus(Long.valueOf(EstatusCorrecciones.ESTATUS_DESCARGADO.toString()));

						CrtControlFlujoCedula model = new CrtControlFlujoCedula();
						model.setFecFechareg(new Date());
						model.setId(idPk);
						model.setCveUsuario(user.getCveIdUsuario()!=null ? user.getCveIdUsuario().toString():null);
						model.setCrtEstatusFlujoCedula(estatusFlujoCedula);

						monitorServiceBean.agregar(model);

						cedulaQ.generarXLS(nombre+"-"+cedula.getFolioCorreccion()+"-"+cedula.getPeriodo()+".xlsm");

					} catch (Exception e) {
						e.printStackTrace();
					}

				}
				break;


			case CedulasCorreccion.ID_DETALLE_TRABAJADORES:

				SatPatron patronPrincipal = null;
				List<CrtAnexosolcorrpat> lstAnexoSol = null;
				List<CrcTrabajadores> empleados;
				List<CrcGrupoCategoria> lstGrupoCategoria = null;
				
				
				patrones = new CrtSolicitudcorr();
				patrones.setNuFolio(cedula.getFolioCorreccion());
				patrones = solicitudServiceBean.consultarFolio(patrones);
				CrcTrabajadores crcTrabajadores = new CrcTrabajadores();
				crcTrabajadores.setCveSolicitudCorr(new Long(patrones.getCveSolicitudCorr()));

				lstAnexoSol =  solicitudServiceBean.consultarAnexoSolicitudes(patrones.getCveSolicitudCorr(),new Long(cedula.getPeriodo()));	
				lstGrupoCategoria=catalogoServiceCategoria.consultaLibrePorClave(0l, "FROM CrcGrupoCategoria where CVE_SOLICITUDCORR= "+patrones.getCveSolicitudCorr());
				
				
				if(lstGrupoCategoria.isEmpty()){
					cedula.setMsg(CedulasCorreccion.NO_EXISTE_CATEGORIAS_RP);
					break;
				}
				
				if(lstAnexoSol!=null&& !lstAnexoSol.isEmpty()){
					Iterator<CrtAnexosolcorrpat> iter =  (Iterator<CrtAnexosolcorrpat>) lstAnexoSol.iterator();
					CrtAnexosolcorrpat currentItem = null;

					while(iter.hasNext()){
						currentItem = iter.next();
						if(currentItem.getCvePatronPr()==null){
							logger.debug("CLAVE PATRON: " + currentItem.getCvePatron());
							patronPrincipal = patronesServiceBean.getById(currentItem.getCvePatron());
							patrones.setRazonSocialPatronPrin(currentItem.getTxRazonSocial());
						}		
					}
				}else{
					cedula.setMsg(CedulasCorreccion.NO_EXISTE_PATRONES_PERIODO);
					break;
				}		
				empleados =  trabajadoresServiceBean.consultarTrabajadoresSinPeriodo(crcTrabajadores);
				
				if(empleados==null || empleados.isEmpty()){cedula.setMsg(CedulasCorreccion.NO_EXISTE_TRABAJADORES);	break;}
				
				patrones.setPeriodo(new Integer(cedula.getPeriodo()));

				DescargaDetalleTrabajadores cedulaTrabajadores;
				
				try {

					
					cedulaTrabajadores = new DescargaDetalleTrabajadores(request, response, cedula.getIdArchivoDescarga(),
							lstAnexoSol, patronPrincipal,patrones,empleados, lstGrupoCategoria, cedula.getFolioCorreccion(), cedula.getPeriodo()) ;

					nombre = CedulasCorreccion.CEDULA_DETALLE_TRABAJADORES.substring(0, CedulasCorreccion.CEDULA_DETALLE_TRABAJADORES.length()-4);

					/*Guardamos el estatus para descargado
					 * */
					CrtControlFlujoCedulaPK idPk = new CrtControlFlujoCedulaPK();
					idPk.setCveCedula(new Long(cedula.getIdArchivoDescarga()));
					idPk.setCveSolicitudcorr(patrones.getCveSolicitudCorr());
					idPk.setCveEjercicio(new Integer(cedula.getPeriodo()));
					// Estatus DesCargado
					CrtEstatusFlujoCedula estatusFlujoCedula = new CrtEstatusFlujoCedula();
					estatusFlujoCedula.setCveEstatus(Long.valueOf(EstatusCorrecciones.ESTATUS_DESCARGADO.toString()));

					CrtControlFlujoCedula model = new CrtControlFlujoCedula();
					model.setFecFechareg(new Date());
					model.setId(idPk);
					model.setCveUsuario(user.getCveIdUsuario()!=null ? user.getCveIdUsuario().toString():null);
					model.setCrtEstatusFlujoCedula(estatusFlujoCedula);

					monitorServiceBean.agregar(model);
					
					cedulaTrabajadores.generarXLS(nombre+"-"+cedula.getFolioCorreccion()+"-"+cedula.getPeriodo()+".xlsm");

				} catch (Exception e) {
					e.printStackTrace();
				}

				break;
				
			case CedulasCorreccion.ID_COP_PAGADA:

				patronPrincipal = null;
				lstAnexoSol = null;
				
				patrones = new CrtSolicitudcorr();
				patrones.setNuFolio(cedula.getFolioCorreccion());
				patrones = solicitudServiceBean.consultarFolio(patrones);
				lstAnexoSol =  solicitudServiceBean.consultarAnexoSolicitudes(patrones.getCveSolicitudCorr(),new Long(cedula.getPeriodo()));

				if(lstAnexoSol!=null&& !lstAnexoSol.isEmpty()){
					Iterator<CrtAnexosolcorrpat> iter =  (Iterator<CrtAnexosolcorrpat>) lstAnexoSol.iterator();
					CrtAnexosolcorrpat currentItem = null;

					while(iter.hasNext()){
						currentItem = iter.next();
						if(currentItem.getCvePatronPr()==null){
							logger.debug("CLAVE PATRON: " + currentItem.getCvePatron());
							patronPrincipal = patronesServiceBean.getById(currentItem.getCvePatron());
							patrones.setRazonSocialPatronPrin(currentItem.getTxRazonSocial());
						}		
					}
				}else{
					cedula.setMsg(CedulasCorreccion.NO_EXISTE_PATRONES_PERIODO);
					break;
				}		
				patrones.setPeriodo(new Integer(cedula.getPeriodo()));

				DescargaCOP cop;
				
				try {

					List<CrtAnexosolcorrpat> patroneFiscal=solicitudServiceBean.consultarAnexos(patrones.getCveSolicitudCorr(), "F");
					SatPatron patron=patronesServiceBean.getById(patroneFiscal.get(0).getCvePatron());
					patroneFiscal.get(0).setRegistroPatronal(patron.getRegistroPatronal());
					cop = new DescargaCOP(request, response, cedula.getIdArchivoDescarga(),
										  lstAnexoSol, patronPrincipal,patrones, cedula.getFolioCorreccion(), cedula.getPeriodo(),patroneFiscal.get(0)) ;

					nombre = CedulasCorreccion.CEDULA_COP_PAGADA.substring(0, CedulasCorreccion.CEDULA_COP_PAGADA.length()-4);

					/*Guardamos el estatus para descargado
					 * */
					CrtControlFlujoCedulaPK idPk = new CrtControlFlujoCedulaPK();
					idPk.setCveCedula(new Long(cedula.getIdArchivoDescarga()));
					idPk.setCveSolicitudcorr(patrones.getCveSolicitudCorr());
					idPk.setCveEjercicio(new Integer(cedula.getPeriodo()));
					// Estatus DesCargado
					CrtEstatusFlujoCedula estatusFlujoCedula = new CrtEstatusFlujoCedula();
					estatusFlujoCedula.setCveEstatus(Long.valueOf(EstatusCorrecciones.ESTATUS_DESCARGADO.toString()));
					
					CrtControlFlujoCedula model = new CrtControlFlujoCedula();
					model.setFecFechareg(new Date());
					model.setId(idPk);
					model.setCveUsuario(user.getCveIdUsuario()!=null ? user.getCveIdUsuario().toString():null);
					model.setCrtEstatusFlujoCedula(estatusFlujoCedula);

					monitorServiceBean.agregar(model);

					cop.generarXLS(nombre+"-"+cedula.getFolioCorreccion()+"-"+cedula.getPeriodo()+".xlsm");

				} catch (Exception e) {
					cedula.setMsg(e.getMessage());
					e.printStackTrace();
				}
			}
			
			logger.debug(".--. Descargada");
		}
		
		
		request.setAttribute("msg", cedula.getMsg());
		
		return "cedulasCorreccion/descarga/descargaResponse";
	}	

	@RequestMapping(value="/validar" , method=RequestMethod.POST)
	public @ResponseBody CrtSolicitudcorr consultar(@RequestBody DescargaCedula cedula) {

		logger.debug("Folio de Corrección A BUSCAR:"+ cedula.getFolioCorreccion());

		CrtSolicitudcorr patrones = new CrtSolicitudcorr();
		patrones.setNuFolio(cedula.getFolioCorreccion());
		patrones.setPeriodo(new Integer(cedula.getPeriodo()));

		if(cedula.getIdArchivoDescarga()==7){
			logger.debug("Se validó Folio de Trabajadores");
			patrones = solicitudServiceBean.consultarFolio(patrones);
			return patrones;
		}else{
			logger.debug("Se validó Otro tipo de cédulas(A,G,H,I,O,Q");
			return  solicitudServiceBean.consultarPatrones(patrones);	
		}

	}

	
}
