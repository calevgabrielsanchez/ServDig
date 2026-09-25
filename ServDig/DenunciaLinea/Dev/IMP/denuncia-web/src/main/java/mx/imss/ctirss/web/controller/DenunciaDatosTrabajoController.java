package mx.imss.ctirss.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.imss.ctirss.catalogos.base.model.AbstractDlcTipoconcepto;
import mx.imss.ctirss.catalogos.model.DlcMotivodenuncia;
import mx.imss.ctirss.catalogos.model.DlcTipoconcepto;
import mx.imss.ctirss.catalogos.model.DlcTiposformapago;
import mx.imss.ctirss.framework.base.controller.AbstractController;
import mx.imss.ctirss.framework.base.model.AbstractModel;
import mx.imss.ctirss.login.service.interfaces.IDenunciaService;
import mx.imss.ctirss.model.DltDenuncia;
import mx.imss.ctirss.model.DltFormapago;
import mx.imss.ctirss.model.DltFormapagoPK;
import mx.imss.ctirss.model.DltInfotrabajo;
import mx.imss.ctirss.model.DltMotivodenuncia;

import mx.imss.ctirss.service.interfaces.ICatalogoService;
import mx.imss.ctirss.session.UserSession;
import mx.imss.ctirss.web.bean.DenunciaDTO;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@Controller
@RequestMapping(value="/denuncia/datosTrabajoMain")
@SessionAttributes("denunciaDTO")
@JsonIgnoreProperties(ignoreUnknown=true)
public class DenunciaDatosTrabajoController extends AbstractController{
	ArrayList<String> FPList;
	
	@Autowired
	private ICatalogoService<AbstractModel> catalogoServiceBean;
	
	@Autowired
	private IDenunciaService<AbstractModel> denunciaServiceBean;
	
	private static final String SESSION_BEAN=DenunciaDTO.SES_NAME;
	
	/**
	 * Logger.
	 */
	private final static Logger logger = Logger.getLogger(DenunciaDatosTrabajoController.class);
	
	@RequestMapping(method=RequestMethod.GET)
	//public String getCreateForm(Model model, HttpServletRequest request) {
	public ModelAndView getCreateForm(Model model, HttpSession ses, HttpServletRequest request) {
		DenunciaDTO denunciaDTO = null;		 
		DenunciaDTO denunciaDTOSes = (DenunciaDTO)request.getSession().getAttribute("denunciaDTO");
		 if(denunciaDTOSes != null){
			 denunciaDTO = denunciaDTOSes;
			 ses.setAttribute(SESSION_BEAN, denunciaDTOSes);			 			 
		 }else{
			 denunciaDTO = new DenunciaDTO();
			 ses.setAttribute(SESSION_BEAN, denunciaDTO);
		 }
		 		 		 
		 ModelAndView mw = new ModelAndView("denuncia/datosTrabajoMain");
		 mw.addObject("denunciaDTO", denunciaDTO);
		
		 return mw;
		
	}
	
	@RequestMapping(value="/guardarInfoTrabajo", method=RequestMethod.POST)
	public ModelAndView guardarInfoTrabajo(@ModelAttribute DenunciaDTO denunciaDTO, BindingResult denunciaDTOBr, HttpServletResponse response, HttpServletRequest request ){
	
		request.getSession().setAttribute("denunciaDTO", denunciaDTO);		
		DltDenuncia dltDenuncia = denunciaDTO.getDltDenuncia();	
		denunciaDTO.getDltInfotrabajo().setCveFoliodenuncia(dltDenuncia.getCveFoliodenuncia());	    
		DltInfotrabajo dltInfotrabajo = (DltInfotrabajo)catalogoServiceBean.agregar(denunciaDTO.getDltInfotrabajo());		
	    denunciaDTO.setDltInfotrabajo(dltInfotrabajo);
				
	    //guarda periodo pago en dlt_formapago
	    DlcTipoconcepto dlcTipoconcepto = new DlcTipoconcepto();
		dlcTipoconcepto.setCveConcepto(new Long(1));
		    
		DltFormapagoPK id = new DltFormapagoPK();
		id.setCveConcepto(dlcTipoconcepto.getCveConcepto());
		id.setCveFormapago(denunciaDTO.getDltFormapagoPP().getDlcTiposformapago().getCveFormapago());
		id.setCveInfotrabajo(denunciaDTO.getDltInfotrabajo().getCveInfotrabajo());
		denunciaDTO.getDltFormapagoPP().setId(id);
		
		denunciaDTO.getDltFormapagoPP().setDltInfotrabajo(denunciaDTO.getDltInfotrabajo());
		denunciaDTO.getDltFormapagoPP().setDlcTipoconcepto(dlcTipoconcepto);
		denunciaDTO.getDltFormapagoPP().setDlcTiposformapago(denunciaDTO.getDltFormapagoPP().getDlcTiposformapago());
		denunciaDTO.getDltFormapagoPP().setDesEspecifique(denunciaDTO.getDltFormapagoPP().getDesEspecifique());
		//denunciaDTO.getDltFormapagoPP().setRefDocumento("cualquierlugarComprobantePeriodo");
		catalogoServiceBean.agregar(denunciaDTO.getDltFormapagoPP());
	
		//guarda comprobante pago en dlt_formapago
		DlcTipoconcepto dlcTipoconceptoCP = new DlcTipoconcepto();
		dlcTipoconceptoCP.setCveConcepto(new Long(2));
		    
		DltFormapagoPK idCP = new DltFormapagoPK();
		idCP.setCveConcepto(dlcTipoconceptoCP.getCveConcepto());
		idCP.setCveFormapago(denunciaDTO.getDltFormapagoCP().getDlcTiposformapago().getCveFormapago());
		idCP.setCveInfotrabajo(denunciaDTO.getDltInfotrabajo().getCveInfotrabajo());
		denunciaDTO.getDltFormapagoCP().setId(idCP);
	
		denunciaDTO.getDltFormapagoCP().setDltInfotrabajo(denunciaDTO.getDltInfotrabajo());
		denunciaDTO.getDltFormapagoCP().setDlcTipoconcepto(dlcTipoconceptoCP);
		denunciaDTO.getDltFormapagoCP().setDlcTiposformapago(denunciaDTO.getDltFormapagoCP().getDlcTiposformapago());
		//denunciaDTO.getDltFormapagoCP().setDesEspecifique("especifiqueComprobante");
		denunciaDTO.getDltFormapagoCP().setDesEspecifique(denunciaDTO.getDltFormapagoCP().getDesEspecifique());
		//denunciaDTO.getDltFormapagoCP().setRefDocumento("cualquierlugarComprobante");
		catalogoServiceBean.agregar(denunciaDTO.getDltFormapagoCP());
	
		//guarda forma pago en dlt_formapago
		
		for(String fp : FPList){
			DlcTipoconcepto dlcTipoconceptoFP = new DlcTipoconcepto();
			dlcTipoconceptoFP.setCveConcepto( new Long(3));
		    
			DlcTiposformapago dlcTiposformapago = new DlcTiposformapago();
			dlcTiposformapago.setCveFormapago((Long.parseLong(fp)));
			
			DltFormapagoPK idFP = new DltFormapagoPK();
			idFP.setCveConcepto(dlcTipoconceptoFP.getCveConcepto());
			//idFP.setCveFormapago(denunciaDTO.getDltFormapagoFP().getDlcTiposformapago().getCveFormapago());
			idFP.setCveFormapago(dlcTiposformapago.getCveFormapago());
			idFP.setCveInfotrabajo(denunciaDTO.getDltInfotrabajo().getCveInfotrabajo());
			denunciaDTO.getDltFormapagoFP().setId(idFP);
		
			denunciaDTO.getDltFormapagoFP().setDltInfotrabajo(denunciaDTO.getDltInfotrabajo());
			denunciaDTO.getDltFormapagoFP().setDlcTipoconcepto(dlcTipoconceptoFP);
			//denunciaDTO.getDltFormapagoFP().setDlcTiposformapago(denunciaDTO.getDltFormapagoFP().getDlcTiposformapago());
			denunciaDTO.getDltFormapagoFP().setDlcTiposformapago(dlcTiposformapago);
			//denunciaDTO.getDltFormapagoFP().setDesEspecifique("especifiqueForma");
			denunciaDTO.getDltFormapagoFP().setDesEspecifique(denunciaDTO.getDltFormapagoFP().getDesEspecifique());
			//denunciaDTO.getDltFormapagoFP().setRefDocumento("cualquierlugarForma2");
			catalogoServiceBean.agregar(denunciaDTO.getDltFormapagoFP());
		}
		logger.debug("ingresa guardarInfoTrabajo");
	
		return new ModelAndView("denuncia/datosPatronMain");
	}

	@RequestMapping(value = "/guardaCentroTrabajo/{idDenuncia}", method = RequestMethod.POST)
	public @ResponseBody DltInfotrabajo guardaCentroTrabajo(@RequestBody DltInfotrabajo dltInfotrabajo,@PathVariable("idDenuncia") String idDenuncia,
			HttpServletResponse response, HttpServletRequest request) {
		
		DltDenuncia dltDenuncia = null;
		
		if(idDenuncia != null && !idDenuncia.trim().equalsIgnoreCase("")){
			
			ArrayList lista = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from DltDenuncia denuncia where denuncia.cveFoliodenuncia = '"+ idDenuncia +"'");
			if(lista.size() ==1){
				dltDenuncia = (DltDenuncia)lista.get(0);
			}			
			dltInfotrabajo.setFecFechareg(new Date());
			dltInfotrabajo.setCveFoliodenuncia(Long.parseLong(idDenuncia));
			dltInfotrabajo.setDltDenuncia(dltDenuncia);
			dltInfotrabajo = (DltInfotrabajo)catalogoServiceBean.agregar(dltInfotrabajo);
		}		
		return dltInfotrabajo;
	}
	
	@RequestMapping(value = "/guardaFormaPago/{idDenuncia}", method = RequestMethod.POST)
	public @ResponseBody DltFormapago guardaFormaPago(@RequestBody DltFormapago dltFormapago, @PathVariable("idDenuncia") String idDenuncia,
			HttpServletResponse response, HttpServletRequest request) {
		
		DltInfotrabajo dltInfotrabajo = null;

		
		if (idDenuncia != null && !idDenuncia.trim().equalsIgnoreCase("")) {
			  
			ArrayList trabajoList = (ArrayList) catalogoServiceBean.consultaLibrePorClave(Long.parseLong(idDenuncia),
							"from DltInfotrabajo d where d.cveFoliodenuncia ="+ idDenuncia);

			if (trabajoList.size() == 1) {
				
				dltInfotrabajo = (DltInfotrabajo) trabajoList.get(0);

				// guarda periodo pago en dlt_formapago
				DlcTipoconcepto dlcTipoconceptoPP = new DlcTipoconcepto();
				dlcTipoconceptoPP.setCveConcepto(new Long(1));

				ArrayList catalogoD = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from DlcTipoconcepto dlcTipoconcepto where dlcTipoconcepto.cveConcepto = 1"); // 1- periodos pago
				if(catalogoD.size() == 1){
					dlcTipoconceptoPP = (DlcTipoconcepto)catalogoD.get(0);
				}
				
				DlcTiposformapago dlcTiposformapagoPP = new DlcTiposformapago();
				dlcTiposformapagoPP.setCveFormapago(new Long(dltFormapago.getPeriodoPago()));
				
				if(dltFormapago.getPeriodoPago() != -1){
					ArrayList catalogoDD = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from DlcTiposformapago dlcTiposformapago where dlcTiposformapago.cveFormapago = "+ new Long(dltFormapago.getPeriodoPago()) );	
					if(catalogoDD.size() == 1){
						dlcTiposformapagoPP = (DlcTiposformapago)catalogoDD.get(0);
					}
	
					DltFormapagoPK id = new DltFormapagoPK();
					id.setCveConcepto(dlcTipoconceptoPP.getCveConcepto());
					id.setCveFormapago( new Long(dltFormapago.getPeriodoPago()));
					id.setCveInfotrabajo(dltInfotrabajo.getCveInfotrabajo());
					
					DltFormapago dltFormapagoPP = new DltFormapago();
					dltFormapagoPP.setId(id);
									
					dltFormapagoPP.setDltInfotrabajo(dltInfotrabajo);
					dltFormapagoPP.setDlcTipoconcepto(dlcTipoconceptoPP);
					dltFormapagoPP.setDlcTiposformapago(dlcTiposformapagoPP);
					dltFormapagoPP.setDesEspecifique(dltFormapago.getDesEspecifiquePP());
					//dltFormapagoPP.setRefDocumento("");
					
					dltFormapagoPP = (DltFormapago)catalogoServiceBean.agregar(dltFormapagoPP);
				}
				// guarda comprobante pago en dlt_formapago
				DlcTipoconcepto dlcTipoconceptoCP = new DlcTipoconcepto();
				dlcTipoconceptoCP.setCveConcepto(new Long(2));

				ArrayList list2 = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from DlcTipoconcepto dlcTipoconcepto where dlcTipoconcepto.cveConcepto = 2"); // 1- tipos compromabte pago
				if(list2.size() == 1){
					dlcTipoconceptoCP = (DlcTipoconcepto)list2.get(0);
				}
				
				DlcTiposformapago dlcTiposformapagoCP = new DlcTiposformapago();
				dlcTiposformapagoCP.setCveFormapago( new Long(dltFormapago.getComprobantePago()));
				
				if(dltFormapago.getComprobantePago() != -1){
					ArrayList lista3 = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from DlcTiposformapago dlcTiposformapago where dlcTiposformapago.cveFormapago = "+ new Long(dltFormapago.getComprobantePago()) );	
					if(lista3.size() == 1){
						dlcTiposformapagoCP = (DlcTiposformapago)lista3.get(0);
					}
					
					DltFormapagoPK idCP = new DltFormapagoPK();
					idCP.setCveConcepto(dlcTipoconceptoCP.getCveConcepto());
					idCP.setCveFormapago(new Long(dltFormapago.getComprobantePago()));
					idCP.setCveInfotrabajo(dltInfotrabajo.getCveInfotrabajo());
					
					DltFormapago dltFormapagoCP = new DltFormapago();
					dltFormapagoCP.setId(idCP);
					dltFormapagoCP.setDltInfotrabajo(dltInfotrabajo);
					dltFormapagoCP.setDlcTipoconcepto(dlcTipoconceptoCP);
					dltFormapagoCP.setDlcTiposformapago(dlcTiposformapagoCP);
					dltFormapagoCP.setDesEspecifique(dltFormapago.getDesEspecifiqueCP());
				//	dltFormapagoCP.setRefDocumento("");
					
					catalogoServiceBean.agregar(dltFormapagoCP);
				}
				// guarda forma pago en dlt_formapago

				for (String fp : dltFormapago.getFormaPago()) {
					
					DlcTipoconcepto dlcTipoconceptoFP = new DlcTipoconcepto();
					dlcTipoconceptoFP.setCveConcepto(new Long(3));
					
					ArrayList list4 = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from DlcTipoconcepto dlcTipoconcepto where dlcTipoconcepto.cveConcepto = 3"); // 1- tipos forma pago
					if(list4.size() == 1){
						dlcTipoconceptoFP = (DlcTipoconcepto)list4.get(0);
					}
					

					
					DlcTiposformapago dlcTiposformapagoFP = new DlcTiposformapago();
					dlcTiposformapagoFP.setCveFormapago((Long.valueOf(fp)));
					
					ArrayList list5 = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from DlcTiposformapago dlcTiposformapago where dlcTiposformapago.cveFormapago =  "+ Long.valueOf(fp) );	
					if(list5.size() == 1){
						dlcTiposformapagoFP = (DlcTiposformapago)list5.get(0);
					}

					DltFormapagoPK idFP = new DltFormapagoPK();
					idFP.setCveConcepto(dlcTipoconceptoFP.getCveConcepto());
					idFP.setCveFormapago(Long.valueOf(fp));
					idFP.setCveInfotrabajo(dltInfotrabajo.getCveInfotrabajo());
					
					DltFormapago dltFormapagoFP = new DltFormapago();
					dltFormapagoFP.setId(idFP);

					dltFormapagoFP.setDltInfotrabajo(dltInfotrabajo);
					dltFormapagoFP.setDlcTipoconcepto(dlcTipoconceptoFP);
					dltFormapagoFP.setDlcTiposformapago(dlcTiposformapagoFP);

					if (fp.trim().equals("16")) {
						dltFormapagoFP.setDesEspecifique(dltFormapago.getDesEspecifiqueFP());
					}

					//dltFormapagoFP.setRefDocumento("");
					catalogoServiceBean.agregar(dltFormapagoFP);
				}
			}// if lista
		}// if denuncia
		return dltFormapago;
	}
	
	 @RequestMapping(value="/consultaPeriodoPago", method=RequestMethod.POST) 
     public @ResponseBody ArrayList consultaPeriodoPago(HttpServletResponse response,HttpServletRequest request){
            ArrayList listaOrigenes = (ArrayList) catalogoServiceBean.consultaLibrePorClave(1L, "from DlcTiposformapago d where d.cveConcepto = 1");
            return listaOrigenes; 
     } 
	 
	 @RequestMapping(value="/consultaActividades", method=RequestMethod.POST) 
     public @ResponseBody ArrayList consultaActividades(HttpServletResponse response,HttpServletRequest request){
            
			 ArrayList listaOrigenes = (ArrayList) catalogoServiceBean.consultaLibrePorClave(1L, "from DlcActEconomica where CVE_GRUPO="+request.getParameter("sector")+" order by TX_ACTIVIDAD");
            return listaOrigenes; 
     } 
	 
	 @RequestMapping(value="/consultaGrupos", method=RequestMethod.POST) 
     public @ResponseBody ArrayList consultaGrupos(HttpServletResponse response,HttpServletRequest request){
            ArrayList listaOrigenes = (ArrayList) catalogoServiceBean.consultaLibrePorClave(1L, "from DlcGrupo  order by TX_GRUPO");
            return listaOrigenes; 
     } 
	 
	 @RequestMapping(value="/consultaSubdelegaciones", method=RequestMethod.POST) 
     public @ResponseBody ArrayList consultaSubdelegaciones(HttpServletResponse response,HttpServletRequest request){
            ArrayList listaOrigenes = (ArrayList) catalogoServiceBean.consultaLibrePorClave(1L, "from DlcSubdelegacion  order by NOM_NOMBRE");
            return listaOrigenes; 
     } 
	 
	 @RequestMapping(value="/consultaEstatus", method=RequestMethod.POST) 
     public @ResponseBody ArrayList consultaEstatus(HttpServletResponse response,HttpServletRequest request){
            ArrayList listaOrigenes = (ArrayList) catalogoServiceBean.consultaLibrePorClave(1L, "from DlcStatus order by ID_STATUS");
            return listaOrigenes; 
     } 
	 
	 @RequestMapping(value="/consultaTipoDocumento", method=RequestMethod.POST) 
     public @ResponseBody ArrayList consultaTipoDocumento(HttpServletResponse response,HttpServletRequest request){
            ArrayList listaOrigenes = (ArrayList) catalogoServiceBean.consultaLibrePorClave(1L, "from DlcTipodocumento  order by DES_DOCUMENTO");
            return listaOrigenes; 
     } 
	 
	 
	 
	 @RequestMapping(value="/consultaTipoComprobante", method=RequestMethod.POST) 
     public @ResponseBody ArrayList consultaTipoComprobante(HttpServletResponse response,HttpServletRequest request){

             UserSession user = getUsuarioFirmado(request); 
             ArrayList listaOrigenes = (ArrayList) catalogoServiceBean.consultaLibrePorClave(2L, "from DlcTiposformapago d where d.cveConcepto = 2");

             return listaOrigenes; 
     }
	 
	 @RequestMapping(value="/consultaFormaPago", method=RequestMethod.POST)
	 public @ResponseBody ArrayList consultaFormaPago(HttpServletResponse response,HttpServletRequest request){
		 UserSession user = getUsuarioFirmado(request); 
         ArrayList listaOrigenes = (ArrayList) catalogoServiceBean.consultaLibrePorClave(3L, "from DlcTiposformapago d where d.cveConcepto = 3");
         return listaOrigenes; 
	 }
	 
	 
	 @RequestMapping(value="/formaPagosCheckBox", method=RequestMethod.POST)
	 public void formaPagosCheckBox(@RequestBody ArrayList<String> FPList, HttpServletResponse response,HttpServletRequest request){
		this.FPList = FPList;  
	 }
	 
	 @RequestMapping(value="/addFile", method=RequestMethod.POST)
	 public @ResponseBody String addFile(@RequestBody String file, HttpServletResponse response,HttpServletRequest request){


		 UserSession user = getUsuarioFirmado(request);

		 
		 FileItemFactory file_factory = new DiskFileItemFactory();
		 ServletFileUpload servlet_up = new ServletFileUpload(file_factory);
		 try {
			List items = servlet_up.parseRequest(request);
			for(int i=0;i<items.size();i++){ 
				 FileItem item = (FileItem) items.get(i); 
				 if (! item.isFormField()){ 
					 String fileToUpload = "c:\\subidos\\" + "katie.jpg";
					 //File archivo_server = new File(item.getName());
					 File archivo_server = new File(fileToUpload);
					 item.write(archivo_server); 
				 }
			}

		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
         return null; 
	 }
	 
	 
}
