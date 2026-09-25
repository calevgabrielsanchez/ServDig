package mx.gob.imss.ctirss.correccion.web.controller.cedulascontruccion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.read.biff.BiffException;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import mx.gob.imss.ctirss.correccion.prorroga.service.interfaces.ProrrogaService;
import mx.gob.imss.ctirss.correccion.session.UserSession;
import mx.gob.imss.ctirss.correccion.web.controller.monitor.ConstantesCedulas;
import mx.gob.imss.ctirss.correccion.web.controller.monitor.EstatusCorrecciones;
import mx.gob.imss.ctirss.correccion.web.utils.ReadZipFiles;
import mx.gob.imss.ctirss.correccion.correccion.service.interfaces.SolicitudService;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.framework.utils.ConstantesBusiness;
import mx.gob.imss.ctirss.correccion.model.CrtAnexosolcorrpat;
import mx.gob.imss.ctirss.correccion.model.CrtControlFlujoCedula;
import mx.gob.imss.ctirss.correccion.model.CrtControlFlujoCedulaPK;
import mx.gob.imss.ctirss.correccion.model.CrtEstatusFlujoCedula;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.monitor.service.interfaces.MonitorService;
import mx.gob.imss.ctirss.correccion.web.utils.ValidaAnexo;



@Controller
@RequestMapping(value="/cedulasCorreccion/carga")
public class CedulasConstruccionCargaController extends AbstractController{
	/**
	 * Logger
	 */
	private final static Logger logger = Logger.getLogger(CedulasConstruccionCargaController.class);
	
	@Autowired
	private ProrrogaService<CrtAnexosolcorrpat> prorrogaServiceBean;
	
	@Autowired
	private SolicitudService<CrtSolicitudcorr> solicitudServiceBean;
	
	@Autowired
	private MonitorService<CrtControlFlujoCedula> monitorServiceBean;
	
	@RequestMapping(method=RequestMethod.GET)
	public String getUploadForm(Model model,HttpServletRequest request) {
		model.addAttribute(new UploadItem());
		return determinaURL(request, "/cedulasCorreccion/carga/cargaMain", "/cedulasCorreccion/carga/cargaMain/patron");
	}
	
	
	
	@RequestMapping(value="/archivo", method=RequestMethod.POST)
	public String create(HttpServletRequest request, HttpServletResponse response) 
	{
//		String folioCorrecion = uploadItem.getFolioCorreccion().toString().replaceAll("/", "_");
//		String folioValidar = uploadItem.getFolioCorreccion().toString();
//		String tipoCarga = uploadItem.getIdArchivoCarga();
		
		
		//ejercicio
		
		
		String folioCorrecion = request.getParameter("numeroFolio").toString().replaceAll("/", "_");;
		String folioValidar = request.getParameter("numeroFolio").toString();
		String tipoCarga = request.getParameter("idCedula");
		String ejercicioPantalla = request.getParameter("ejercicio");
		
		
		MultipartHttpServletRequest multipartRequest =(MultipartHttpServletRequest) request;
		MultipartFile adjuntoArchivo = multipartRequest.getFile("Filedata");
		
		
		
		String nombreCedula = adjuntoArchivo.getOriginalFilename().toString();
	
		String resultadoValidaFolio = validaFolioCorreccion(folioValidar);
		
		if(resultadoValidaFolio.equals("")){
			File fi = new File(nombreCedula);
			

			switch (new Integer(request.getParameter("idCedula"))) {
				case 1:
					tipoCarga = "CEDULA_A";
					break;
				case 2:
					tipoCarga = "CEDULA_G";
					break;
				case 3:
					tipoCarga = "CEDULA_H";
					break;
				case 4:
					tipoCarga = "CEDULA_I";
					break;
				case 5:
					tipoCarga = "CEDULA_O";
					break;
				case 6:
					tipoCarga = "CEDULA_Q";
					break;
				case 7:
					tipoCarga = "DETALLE_TRABAJADORES";
					break;
				case 8:
					tipoCarga = "COP_PAGADAS";
					break;
	
				default:
					tipoCarga="";
					break;
			}
			

			if(new EvaluaExtension().accept(fi, ".txt")){
				

//				String prePath = request.getSession().getServletContext().getRealPath(File.separator).endsWith(File.separator) 
//									? request.getSession().getServletContext().getRealPath(File.separator) 
//									: request.getSession().getServletContext().getRealPath(File.separator)+File.separator;
//				logger.debug("prePath :: [" + prePath + "]");
//				String filePath = prePath +"resources"+File.separator+"archivos"+File.separator+
//						"cedulasConstruccion"+File.separator+"pendientesProceso"+ File.separator+ folioCorrecion + File.separator;
//				logger.debug("filePath :: [" + filePath + "]");
//				String filePathTemp =prePath +"resources"+File.separator+"archivos"+File.separator+
//						"cedulasConstruccion"+File.separator+"temp"+File.separator+folioCorrecion + File.separator;
//
//
//				String fileName = folioCorrecion+ "_"+tipoCarga + ".zip";
//
//				File fileToCreate = new File(filePath, fileName);
//				File fileToCreate_temp = new File(filePathTemp, fileName);
//
//				File mkdir = new File(filePath);
//				File mkdir_temp = new File(filePathTemp);
//
//				if(!mkdir_temp.isDirectory())
//					mkdir_temp.mkdirs();
//
//				if(fileToCreate_temp.exists()){
//					fileToCreate_temp.delete();
//				}
//
//				InputStream is = null;
//				ReadZipFiles rZips = null;
				try {
					
//					FileOutputStream fileOutStream = new FileOutputStream(fileToCreate_temp);
//					fileOutStream.write(uploadItem.getFileData().getBytes());
//					fileOutStream.flush();
//					fileOutStream.close();
//
//					rZips = new ReadZipFiles(fileToCreate_temp);
//					is = rZips.getUniqueFile();
//					ValidaAnexo validaAnexo = new ValidaAnexo(t, folioValidar, new Integer(uploadItem.getIdArchivoCarga()));  
//
//					validaAnexo.validaByFolioAvisoIdAnexo();
//
//					if(!mkdir.isDirectory())
//						mkdir.mkdirs();
//
//					if(fileToCreate.exists()){
//						fileToCreate.delete();
//					}
//
//					logger.debug("Procesando Archivo");
//					FileOutputStream fileOut = new FileOutputStream(fileToCreate);
//					fileOut.write(uploadItem.getFileData().getBytes());
//					fileOut.flush();
//					fileOut.close();     
//
//					is.close();
//					rZips.closeZipFile();
//					fileToCreate_temp.delete();
					
					InputStream archivo=adjuntoArchivo.getInputStream();
					InputStreamReader read=new InputStreamReader(archivo);
					BufferedReader br = new BufferedReader (read);
					//Integer cveEjercicio=Integer.parseInt(Desencriptar(br.readLine()));
					String datos=Desencriptar(br.readLine());
					Integer cveEjercicio=Integer.parseInt((datos).split("\\|")[0]);
					
					System.out.println("Linea del archivo "+cveEjercicio);
					
					UserSession user = getUsuarioFirmado(request);	
					CrtSolicitudcorr crtSolicitudcorr  = new CrtSolicitudcorr();
					crtSolicitudcorr.setNuFolio(folioValidar);
					crtSolicitudcorr = solicitudServiceBean.consultarFolio(crtSolicitudcorr);
					CrtControlFlujoCedulaPK idPk = new CrtControlFlujoCedulaPK();
					idPk.setCveCedula(new Long(request.getParameter("idCedula")));
					idPk.setCveSolicitudcorr(crtSolicitudcorr.getCveSolicitudCorr());
					//idPk.setCveEjercicio(validaAnexo.getCveEjercicio());
					idPk.setCveEjercicio(Integer.valueOf(ejercicioPantalla));
					if(validaProcesamientoCedula(idPk)){
						// Estatus Cargado
						CrtEstatusFlujoCedula estatusFlujoCedula = new CrtEstatusFlujoCedula();
						estatusFlujoCedula.setCveEstatus(EstatusCorrecciones.ESTATUS_CARGADO.longValue());
						
						CrtControlFlujoCedula model = new CrtControlFlujoCedula();
						model.setPorcentajeAvance(0.0f);
						model.setFecFechareg(Calendar.getInstance().getTime());
						model.setId(idPk);
						if(user.getCurpUsuario()!=null){
							model.setCveUsuario(user.getCurpUsuario().toString());	
						}
						
						model.setCrtEstatusFlujoCedula(estatusFlujoCedula);
						if(comparaFolios((datos).split("\\|")[1],folioValidar)){
							monitorServiceBean.agregar(model);						
							request.setAttribute("mensaje", "El archivo sera procesado");								
							FtpService ftpService=new FtpService();						
							ftpService.subirArchivo(adjuntoArchivo.getInputStream(),folioCorrecion+"-"+tipoCarga+".txt");
							logger.info("Subida de archivo finalizada "+folioCorrecion+"-"+tipoCarga+".txt");
						}else{
							request.setAttribute("mensaje", "El archivo no pertenece al folio ingresado");
							CrtEstatusFlujoCedula estatusError = new CrtEstatusFlujoCedula();
							estatusError.setCveEstatus(EstatusCorrecciones.ESTATUS_ERROR_CARGA.longValue());							
							model.setCrtEstatusFlujoCedula(estatusError);
							monitorServiceBean.agregar(model);
							
						}
						
					}else{
						 request.setAttribute("mensaje","El archivo esta siendo procesado");
	                     request.setAttribute("folioCorreccion",folioValidar);
					}
					
					
					
					
					
				}catch (Exception e) {
//						 if(is!=null)
//							try {
//								is.close();
//							} catch (IOException e1) {
//								// TODO Auto-generated catch block
//								e1.printStackTrace();
//							}
//	                     rZips.closeZipFile();
//	                     fileToCreate_temp.delete();
	                     request.setAttribute("mensaje",e.getMessage());
	                     request.setAttribute("folioCorreccion",folioValidar);
						e.printStackTrace();
					} 
				}else{
					System.out.println("El archivo que se desea subir es invalido, unicamente se aceptan archivos con extension zip");
					request.setAttribute("mensaje", "El archivo que se desea subir es invalido, unicamente se aceptan archivos con extension txt");
					request.setAttribute("folioCorreccion",folioValidar);
				}

		}else request.setAttribute("mensaje",resultadoValidaFolio);
		
		request.setAttribute("folioCorreccion",folioValidar);
		
			 
		return determinaURL(request, "/cedulasCorreccion/carga/cargaMain", "/cedulasCorreccion/carga/cargaMain/patron");
	}
	
	public boolean comparaFolios(String folioArchivo,String folioPantalla){
		if(folioArchivo.equals(folioPantalla)){
			return true;
		}else{
			return false;
		}
		
	}
	
	
	public boolean validaProcesamientoCedula(CrtControlFlujoCedulaPK idPk){
		logger.info("Busqueda "+idPk.getCveCedula()+" ejer "+idPk.getCveEjercicio()+" cveSol "+idPk.getCveSolicitudcorr());
		CrtControlFlujoCedula flujo=new CrtControlFlujoCedula();
		flujo.setId(idPk);
		List<CrtControlFlujoCedula> lista=monitorServiceBean.consultar(flujo);
		for(CrtControlFlujoCedula flu:lista){
			System.out.println(flu.getId().getCveCedula()+" "+flu.getId().getCveEjercicio());
			if(flu.getId().getCveCedula()==idPk.getCveCedula() && flu.getId().getCveEjercicio().intValue()==idPk.getCveEjercicio().intValue()){
				if(flu.getCrtEstatusFlujoCedula().getCveEstatus()==ConstantesBusiness.ESTATUS_EN_PROCESO){
					return false;
				}
			}
		}
		
	return true;
	}
	
	
	public String Desencriptar(String DataValue) throws Exception{
		String temp = "";
		String hexByte;
		for(int i = 0; i < DataValue.length(); i+=2){
			hexByte = DataValue.substring(i, i+2);
			int decimal = Integer.parseInt(hexByte, 16);
			temp = temp + (char)decimal;
		}
		return temp;
	}
	
	public boolean validaFolio(String folio, Integer IdCedula, File zipFile) throws BiffException, IOException, Exception{
		
		 logger.debug("Folio a Validar: " + folio);
		
		InputStream is = null;
		ReadZipFiles rZips = null;
		
		 rZips = new ReadZipFiles(zipFile);
         is = rZips.getUniqueFile();
          ValidaAnexo validaAnexo = new ValidaAnexo(is,
        		  folio,IdCedula);       
	
	    is.close();
        rZips.closeZipFile();
        
        return  validaAnexo.validaByFolioAvisoIdAnexo();
	}
	
	
	class EvaluaExtension implements FilenameFilter{
		public boolean accept(File dir, String extension){
			return dir.getName().endsWith(extension);
		}
	}
	
	
	@RequestMapping(value="/validar" , method=RequestMethod.POST)
	public @ResponseBody CrtSolicitudcorr consultar(@RequestBody UploadItem uploadItem) {
		
		 logger.debug("FOlio de COrreccion A BUSCAR:"+ uploadItem.getFolioCorreccion());
		
		CrtSolicitudcorr patrones = new CrtSolicitudcorr();
		patrones.setNuFolio(uploadItem.getFolioCorreccion());
		return  solicitudServiceBean.consultarFolio(patrones);		
	}
	
	private String validaFolioCorreccion(String folioCorreccion){
		
		CrtAnexosolcorrpat anexo = new CrtAnexosolcorrpat();
		anexo.setNuFolio(folioCorreccion);
		
		CrtAnexosolcorrpat resultado  = this.prorrogaServiceBean.consultarPorFolio(anexo);
		
		if(resultado!=null && resultado.getEstadoFolioCorr()==1)return "La Solicitud de la Correcion No ha sido aceptada";
		else if(resultado!=null && resultado.getEstadoFolioCorr()==4) return "La Solicitud de la Correcion ya ha sido Presentada";
		
		return "";
	}
	
}
