package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.web.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.web.utils.Constants;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorioCaptura;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.FileReadVB;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.FileUploadVB;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;



@Controller
@RequestMapping("/fileread")
@SessionAttributes({"fileReadVB"})
public class FileReadController {
	
	private static final Logger logger = Logger.getLogger(FileReadController.class);
	private static final String FILE_READ_VB="fileReadVB";
	private static final String MUESTRA="Muestra";
	@Autowired
	DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusinessRemote;
	
	@RequestMapping(value = "/muestraDigitalizacion", method = RequestMethod.GET)
	public @ResponseBody String generarDigitalizacion(
									@RequestParam(value="idDocumentoProbatorio",required=true) Long id	,		
									HttpServletResponse sresponse,
									HttpSession ses) {
		
		DocumentoProbatorio dP=this.documentoProbatorioServiceBusinessRemote.getDocumentoProbatorioBytes(id);
		

		byte[] res =dP.getDigitalizacion();
		sresponse.setContentType("application/pdf");
		sresponse.setContentLength(res.length);
		sresponse.setHeader("Content-Disposition","filename = " + dP.getCifrado()+".pdf");
		try {
			sresponse.getOutputStream().write(res);
			sresponse.getOutputStream().flush();
			sresponse.getOutputStream().close();
		} catch (IOException e) {
			System.out.println("Exception " + e);
		}
		return "welcome";
	}	
	
	
	
	
	
	@RequestMapping(value = "/initMuestraDocumentosTramite", method = RequestMethod.GET)
	public String direccionaMuestraDocumentosTramite(){
		return "muestraDocTram";
	}
	

	@RequestMapping(value = "/listaDocTramiteSession")
	public @ResponseBody List<DocumentoProbatorio> listaDocumentosTramiteSession(HttpSession ses){
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DocumentoProbatorio documentoProbatorio=null;
		FileUploadVB cargadorDocumentosVB=(FileUploadVB) ses.getAttribute(FileUploadVB.SES_NAME);
		
		List<DocumentoProbatorio> DocumentosProbatorios=new ArrayList<DocumentoProbatorio>();
		
		for(DocumentoProbatorioCaptura dPC:cargadorDocumentosVB.getDocumenProbatorioCapturaList()){
			//encapsular los documentos de captura 
			if(dPC!=null){
				documentoProbatorio=this.documentoProbatorioCapturaListaParser(dPC);
				
			}
			//se agrega a la lista
			DocumentosProbatorios.add(documentoProbatorio);
		}
		return DocumentosProbatorios;
	}
	
	
	
	@RequestMapping(value = "/listaDocTramite")
	public @ResponseBody List<DocumentoProbatorio> listaDocumentosTramite(@RequestParam(value="idTramite",required=true) String idTramite){
		
		
		
		List <DocumentoProbatorio> dPList=null;
	
		dPList=documentoProbatorioServiceBusinessRemote.listaDocumentosProbatoriosTramite(new Long(idTramite));
		//se regresa a Json
		return dPList;
	}
	
	@RequestMapping(value = "/muestraDocumentoProbatorio")
	public String muestraDocumentoProbatorio(@RequestParam(value="idDocumentoProbatorio",required=false)Long idDocumentoProbatorio,
												//@RequestParam(value="idDocumentoProbatorio",required=false)DocumentoProbatorio documentoProbatorio,
																HttpServletRequest request){
		Object documentoProbatorio=null;
		FileReadVB bean=new FileReadVB();
		String fordward=null;
	
		if(documentoProbatorio==null){
			try {
				documentoProbatorio=documentoProbatorioServiceBusinessRemote.getDocumentoProbatorio(idDocumentoProbatorio);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
			}
		}
		bean.setDocumentoProbatorio(documentoProbatorio);
		//seleccionar el view
		fordward=this.getViewMuestraDocumentoProbatorio(bean);
		request.setAttribute(FILE_READ_VB,bean );
		
		return fordward;
	}
	/**
	 * Muestra documento en session
	 * @param idDocumentoProbatorio en relaidad es idDocumentoPorTipo
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/muestraDocumentoProbatorioSession")
	public String muestraDocumentoProbatorio(@RequestParam(value="idDocumentoProbatorio",required=true)Long idDocumentoPorTipo,
											HttpSession ses,																				
											HttpServletRequest request	){
		DocumentoProbatorio documentoProbatorio=null;
		String fordward=null;
		//obtienen bean de la session
		FileUploadVB cargadorDocumentosVB=(FileUploadVB) ses.getAttribute(FileUploadVB.SES_NAME);
		
		//obtiene el documento probatorio de session
		documentoProbatorio=this.buscaDocumentoEnSession(idDocumentoPorTipo, cargadorDocumentosVB.getDocumenProbatorioCapturaList());
		FileReadVB bean=new FileReadVB();

		
		bean.setDocumentoProbatorio(documentoProbatorio);
		//seleccionar el view
		
		fordward=this.getViewMuestraDocumentoProbatorio(bean);
		request.setAttribute(FILE_READ_VB,bean );
		
		return fordward;
	}
	
	private String getViewMuestraDocumentoProbatorio(FileReadVB bean){
		String fordward=null;
		String tituloComp=null;
		DocumentoProbatorio dP=(DocumentoProbatorio)bean.getDocumentoProbatorio();

		switch(dP.getDocumentoPorTipo().getDocumento().getCveIdDocumento().intValue()){		
		case Constants.TIPO_DOC_ACTA_NACIMIENTO:
			fordward=Constants.FORDWARD_ACTA_NACIMIENTO + MUESTRA ;
			break;
		case Constants.TIPO_DOC_ACTA_ADOPCION:
			tituloComp=Constants.TITLE_ACTA_ADOPCION;
			fordward=Constants.FORDWARD_ACTA_COMUN + MUESTRA ;
			break;	
		case Constants.TIPO_DOC_ACTA_DEFUNCION:
			tituloComp=Constants.TITLE_ACTA_DEFUNCION;
			fordward=Constants.FORDWARD_ACTA_COMUN + MUESTRA ;
			break;	
		case Constants.TIPO_DOC_ACTA_DIVORCIO:
			tituloComp=Constants.TITLE_ACTA_DIVORCIO;
			fordward=Constants.FORDWARD_ACTA_COMUN + MUESTRA ;
			break;	
		case Constants.TIPO_DOC_ACTA_MATRIMONIO:
			tituloComp=Constants.TITLE_ACTA_MATRIMONIO;
			fordward=Constants.FORDWARD_ACTA_COMUN + MUESTRA ;
			break;	
		case Constants.TIPO_DOC_ACTA_MATRIMONIO_DICTAMEN_DIS:
			tituloComp=Constants.TITLE_ACTA_MATRIMONIO_DIC_DIS;
			fordward=Constants.FORDWARD_ACTA_COMUN + MUESTRA ;
			break;	
		case Constants.TIPO_DOC_ACTA_RECONOCIMIENTO:
			tituloComp=Constants.TITLE_ACTA_RECONOCIMIENTO;
			fordward=Constants.FORDWARD_ACTA_COMUN + MUESTRA ;
			break;
		case Constants.TIPO_DOC_ACTA_PACTO_CIVIL:
			tituloComp= "ACTA PACTO SOLIDARIDAD CIVIL";
			fordward=Constants.FORDWARD_ACTA_PACTO_CIVIL + MUESTRA ;
			break;	
		case Constants.TIPO_DOC_ACUERDO:
			tituloComp=Constants.TITLE_ACUERDO;
			fordward=Constants.FORDWARD_ACUERDO + MUESTRA ;
			break;	
		case Constants.TIPO_DOC_LAUDO:
			tituloComp=Constants.TITLE_LAUDO;
			fordward=Constants.FORDWARD_ACUERDO + MUESTRA ;
			break;
		case Constants.TIPO_DOC_CARTILLA_MILITAR:
			fordward=Constants.FORDWARD_CARTILLA_MILITAR + MUESTRA ;
			break;
		case Constants.TIPO_DOC_CEDULA_PROFESIONAL:
			fordward=Constants.FORDWARD_CEDULA_PROFESIONAL + MUESTRA ;
			break;
		case Constants.TIPO_DOC_CERTIFICADO_NACIMIENTO:
			fordward=Constants.FORDWARD_CERTIFICADO_NACIMIENTO + MUESTRA ;
			break;
		case Constants.TIPO_DOC_CONSTANCIA_ESTUDIOS:
			fordward=Constants.FORDWARD_CONSTANCIA_ESTUDIOS + MUESTRA ;
			break;
		case Constants.TIPO_DOC_CREDENCIAL_ELECTOR:
			fordward=Constants.FORDWARD_CREDENCIAL_ELECTOR + MUESTRA ;
			break;
		case Constants.TIPO_DOC_CURP:
			fordward=Constants.FORDWARD_CURP + MUESTRA ;
			break;
		case Constants.TIPO_DOC_DICTAMEN_INCAPACITADO:
			fordward=Constants.FORDWARD_DICTAMEN_INCAPACITADO + MUESTRA;
			break;
		case Constants.TIPO_DOC_OBSTETRICO:
			fordward=Constants.FORDWARD_OBSTETRICO + MUESTRA;
			break;
		case Constants.TIPO_DOC_PASAPORTE:
			fordward=Constants.FORDWARD_PASAPORTE + MUESTRA;
			break;
		case Constants.TIPO_DOC_PENSION:

		case Constants.TIPO_DOC_VIGENCIA_TEMPORAL:
			fordward=Constants.FORDWARD_VIGENCIA_TEMPORAL + MUESTRA;
			break;
		case Constants.TIPO_CERTIFICADO_SIT_CRITICA:
			fordward=Constants.FORDWARD_CERTIFICADO_SIT_CRITICA + MUESTRA;
			break;
		case Constants.TIPO_DOC_ESTADO_CUENTA_BANCARIO:
		case Constants.TIPO_DOC_CONTRATO_ARRENDAMIENTO:
		case Constants.TIPO_DOC_RECIBO_TELEVISION:
		case Constants.TIPO_DOC_PAGO_TENENCIA_VEHICULAR:
		case Constants.TIPO_DOC_RECIBO_GAS:
		case Constants.TIPO_DOC_RECIBO_LUZ:
		case Constants.TIPO_DOC_RECIBO_TELEFONO:
		case Constants.TIPO_DOC_ESCRITURA_PROPIEDAD_INMOBILIARIA:
		case Constants.TIPO_DOC_RECIBO_AGUA:
		case Constants.TIPO_DOC_PAGO_PREDIAL:
			fordward = Constants.FORDWARD_COMPBANTE_DOMICILIO + MUESTRA;
			break;
		case Constants.TIPO_DOC_ADIMSS:
			tituloComp = "title.capturaDocumentos.adimss";
			fordward = Constants.FORDWARD_ADIMSS + MUESTRA;
			break;
		case Constants.TIPO_DOC_ACTA_UNION_CIVIL:
			fordward = Constants.FORDWARD_ACTA_UNION_CIVIL + MUESTRA;
			break;
		case Constants.TIPO_DOC_ACTA_TERMINO_UNION_CIVIL:
			fordward = Constants.FORDWARD_ACTA_TERMINO_UNION_CIVIL + MUESTRA;
			break;
		}

		bean.setTituloComp(tituloComp);
		return fordward;
	}
	
	private DocumentoProbatorio buscaDocumentoEnSession(Long id,List<DocumentoProbatorioCaptura> entrada){
		DocumentoProbatorio salida=null;
		
		for(DocumentoProbatorioCaptura dPC: entrada){
			if(dPC.getDocumentoProbatorio().getDocumentoPorTipo().getIdDocumentoPorTipo().equals(id)){
				salida=this.documentoProbatorioCapturaParser(dPC);
			}
		}
		return salida;
	}

	
	private DocumentoProbatorio documentoProbatorioCapturaParser(DocumentoProbatorioCaptura dPC){
		DocumentoProbatorio documentoProbatorio=null;
		if(dPC!=null){
			documentoProbatorio=(DocumentoProbatorio) dPC.getCaptura();
			documentoProbatorio.setCifrado(dPC.getDocumentoProbatorio().getCifrado());
			documentoProbatorio.setDigitalizacion(dPC.getDocumentoProbatorio().getDigitalizacion());
			documentoProbatorio.setDocumentoPorTipo(dPC.getDocumentoProbatorio().getDocumentoPorTipo());
		}
		return documentoProbatorio;
	}
	
	private DocumentoProbatorio documentoProbatorioCapturaListaParser(DocumentoProbatorioCaptura dPC){
		DocumentoProbatorio documentoProbatorio=null;
		if(dPC!=null){
			documentoProbatorio=new DocumentoProbatorio();
			documentoProbatorio.setDocumentoPorTipo(dPC.getDocumentoProbatorio().getDocumentoPorTipo());
			documentoProbatorio.setIdDocumentoProbatorio(dPC.getDocumentoProbatorio().getDocumentoPorTipo().getIdDocumentoPorTipo().intValue());
		}
		return documentoProbatorio;
	}
}
