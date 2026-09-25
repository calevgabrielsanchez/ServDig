package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.web.controller;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Acta;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.ActaUnionCivil;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.ActaTerminoUnionCivil;

import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CartillaMilitar;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CedulaProfesional;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DoctoReqTramite;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Documento;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorioCaptura;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.FileUploadVB;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Pasaporte;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoDocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRegistroDerechohabiente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/fileupload")
//nesesario para tener en session el Bean
@SessionAttributes({"fileUploadSVB"})
public class FileUploadController extends AbstractController{
	
	private static final Integer REQUERIDO=new Integer(1);
	private static final String SESSION_BEAN=FileUploadVB.SES_NAME;
	private static final String KEY_DOCUMENTOS_A_ELIMINAR = "documentosANoMostrarSession";
	private static final String DOC_DIG_ERROR = "Es necesario capturar el documento";
	private static final String RES_OK="true";
	private static final String KEY_ID_TRAMITE = "tramiteDocumentosProbatoriosSesion";
	private static final String KEY_ID_TIPO_TRAMITE = "tipoTramiteSesionDocumentos";
	
	@Autowired DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusinessRemote;
	@Autowired SolicitudBusinessRemote solicitudBusinessRemote;
	
	@RequestMapping("/wizard/{idTramite}/{idTipoTramite}/{curp}/{idUmf}/{quitarDocumentos}")
	public String iniciarWizard(HttpSession session, HttpServletRequest request, Model model,
			@PathVariable Long idTramite, @PathVariable Long idTipoTramite, @PathVariable String curp,
			@PathVariable Long idUmf, @PathVariable String quitarDocumentos) {
		
	
		// ------------------------------------------------------------------
		// Necesitamos la razón de registro para saber si es recien nacido 
		// ------------------------------------------------------------------
		Long idRazonRegistro = null;
		
		try {
			Solicitud solicitud = solicitudBusinessRemote.consultarPorIdTramite(idTramite);
			if(solicitud != null) {
				for(Tramite tram: solicitud.getTramites()) {
					if(tram.getTramiteId().equals(idTramite) && tram  instanceof TramiteRegistroDerechohabiente) {
						TramiteRegistroDerechohabiente registro = (TramiteRegistroDerechohabiente)tram;
						idRazonRegistro = registro.getRazonRegistro().getIdRazonRegistro();
						break;
					}
				}
			}
		} catch (SolicitudNoEncontradaException e) {
			e.printStackTrace();
		}
		
		
		List<Long> documentosAEliminar = null;
		session.setAttribute(KEY_ID_TRAMITE, idTramite);
		session.setAttribute(KEY_ID_TIPO_TRAMITE, idTipoTramite);
		
		model.addAttribute("idTramite", idTramite);
		model.addAttribute("idTipoTramite", idTipoTramite);
		model.addAttribute("curp", curp.equals("0") ? "" : curp);
		model.addAttribute("idUmf", idUmf);
		model.addAttribute("idRazonRegistro", idRazonRegistro);
		
		if(!quitarDocumentos.equals("0")) {
			String[] documentosNoMostrar = quitarDocumentos.split("_");
			documentosAEliminar = new ArrayList<Long>();
			for(String docto :  documentosNoMostrar) {
				documentosAEliminar.add(new Long(docto));
			}
		}
		session.setAttribute(KEY_DOCUMENTOS_A_ELIMINAR, documentosAEliminar);
		
		return "initWizardCapturaDocumentos";
	}
	
	@RequestMapping( value = "/wizard/limpiar-session")
	public @ResponseBody Map<String, Object> limpiarSession(HttpSession session) {
		
		session.removeAttribute(SESSION_BEAN);
		session.removeAttribute(KEY_ID_TRAMITE);
		session.removeAttribute(KEY_ID_TIPO_TRAMITE);
		session.removeAttribute(KEY_DOCUMENTOS_A_ELIMINAR);
		
		return null;
	}
	  @RequestMapping("/init") 
	  public @ResponseBody Map<String, ? extends Object>  cargaComponente(@RequestParam(value="cveIdTramite",required=false)Long idTramite,
			  @RequestParam(value="cveIdTipoTramite",required=true)Long idTipoTramite,
			  @RequestParam(value="nuevosDocsList",required=false)String nuevosDocsList,
			  HttpSession ses) { 
		  
		  List<String> listaNuevosDocRequeridos =  new ArrayList<String>();
		  Map<String, Object> objMapaData = new HashMap<String, Object>();
		  List<Long> documentosNoMostrar = (List<Long>) ses.getAttribute(KEY_DOCUMENTOS_A_ELIMINAR);
		  
		  if(idTipoTramite == 47L){
			  documentosNoMostrar = null;
		  }
		  
		  FileUploadVB documentosVB = new FileUploadVB();
		  //se elimina el objeto siempre de la session para que al momento de entrar al metodo refresque toda la lista de objetos.
		  //FileUploadVB documentosVB=(FileUploadVB) ses.getAttribute(SESSION_BEAN);//para ver si la session ya existia de lo contrario se creara un nuevo bean
		  ses.removeAttribute(SESSION_BEAN);
		  //if(!(documentosVB!=null&&documentosVB.getIdTipoTramite().equals(idTipoTramite))){//si existe la session y es igual al tipotramite en el que se esta actualmente no hacer esto
			  documentosVB=new FileUploadVB();
			  	
			  	//Se Obtiene la lista de documentos por tipo de tramite
				documentosVB.setDoctoReqTramiteList(this.documentoProbatorioServiceBusinessRemote.getDocumentosRequeridosPorTipoTramite(idTipoTramite));
				
				//Obtinene la lista de los ids de los nuevos documentoos obligatorios
				
				if(nuevosDocsList != null && nuevosDocsList.length() > 0){
					String[] idDocs = nuevosDocsList.split(",");
					if(idDocs.length > 0){
						listaNuevosDocRequeridos = Arrays.asList(idDocs);
					}
				}
				//obtiene los tipos de documento probatorios obligatorios y para mostrar
				//documentosVB.setTipoDocumentoProbatorioList(this.getTipoDocumentoProbatorioPorTramite(documentosVB.getDoctoReqTramiteList()));
				
			  	documentosVB  = this.llenaListasTiposDocProbatorios(documentosVB, listaNuevosDocRequeridos, documentosNoMostrar);
				
				
				documentosVB.setIdTipoTramite(""+idTipoTramite);
				
				if(idTramite!=null){
					documentosVB.setIdTramite(new Long(idTramite));
				}
				 //meter en session
				 documentosVB.setLoadedBytes(false);
				 documentosVB.setLoadedDocform(false);
				 System.out.println("Tamaño de la lista: " + documentosVB.getDoctoReqTramiteList().size());
				 //
				 documentosVB.setNecesarios(this.documentosRequeridos(documentosVB.getDoctoReqTramiteList()));
		  //}

		 ses.setAttribute(SESSION_BEAN, documentosVB);
		 
		 objMapaData.put("tipoDocumentoProbatorioList",documentosVB.getTipoDocumentoProbatorioList());
		 objMapaData.put("tipoDocumentoProbatorioReqList",documentosVB.getTipoDocumentoProbatorioReqList());
		 return objMapaData;
	  } 
	  /**
	   * obtiene la lista de tipoDocumentoProbatorio nesesarios para el tramite sin repetir
	   * @param entradaList
	   * @return
	   */
	  @SuppressWarnings("unused")
	private List<TipoDocumentoProbatorio> getTipoDocumentoProbatorioPorTramite(List<DoctoReqTramite> entradaList){
		  List<TipoDocumentoProbatorio> salidaList=new ArrayList<TipoDocumentoProbatorio>();
		  Integer idTipoDocumentoProbatorio=null;
		  
		  for(DoctoReqTramite doctoReqTramite : entradaList){
			  
			  idTipoDocumentoProbatorio = doctoReqTramite.getDocumentoPorTipo().getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio();
			  
			  //Antes de verificar si el tipo ya se encuentra en la lista valida si el documento es opcional
			  if(doctoReqTramite.getRefDocumentoOpcional() != 1 && !existeTipoDocumentoProbatorio(salidaList, idTipoDocumentoProbatorio)){
				  salidaList.add(doctoReqTramite.getDocumentoPorTipo().getTipoDocumentoProbatorio());
			  } 
		  }
		  
		  return salidaList;
	  }
	  
	  
	  
	  /**
	   * obtiene la lista de tipoDocumentoProbatorio nesesarios para el tramite reuqeridos y para mostrar sin repetir
	   * @param entradaList
	   * @return
	   */
	  private FileUploadVB llenaListasTiposDocProbatorios(FileUploadVB fileUploadVB, List<String> listaNuevosDocsRequeridos, List<Long> documentosNoMostrados){
		  
		  List<TipoDocumentoProbatorio> tipoDocsMostrar = new ArrayList<TipoDocumentoProbatorio>();
		  List<TipoDocumentoProbatorio> tipoDocsRequeridos =new ArrayList<TipoDocumentoProbatorio>();
		  
		  Integer idTipoDocumentoProbatorio=null;
		  
		  if(documentosNoMostrados != null && !documentosNoMostrados.isEmpty()) {
			  List<DoctoReqTramite> doctosReqAux = new ArrayList<DoctoReqTramite>();

			  for(DoctoReqTramite doctoReqTramite : fileUploadVB.getDoctoReqTramiteList()) {
				  Boolean eliminar= false;
				  
				  for(Long idDocEliminar:  documentosNoMostrados) {
					  if(doctoReqTramite.getDocumentoPorTipo().getDocumento().getCveIdDocumento().equals(idDocEliminar)) {
						  eliminar = true;
						  break;
					  }
				  }
				  
				  if(!eliminar) {
					  doctosReqAux.add(doctoReqTramite);
				  }
			  }
			  
			  fileUploadVB.setDoctoReqTramiteList(doctosReqAux);
		  }
		  
		  if(fileUploadVB.getDoctoReqTramiteList() != null && !fileUploadVB.getDoctoReqTramiteList().isEmpty()) {
			  for(DoctoReqTramite doctoReqTramite : fileUploadVB.getDoctoReqTramiteList()){

				  //Verifica si se trata de alguno  los nuevos documentos reuqeridos
				  for(String idDocumento : listaNuevosDocsRequeridos){

					  if(doctoReqTramite.getDocumentoPorTipo().getDocumento().getCveIdDocumento().equals(new Long(idDocumento))){
						  //Se marca como no opcional
						  doctoReqTramite.setRefDocumentoOpcional(0);

						  break;
					  }
				  }


				  idTipoDocumentoProbatorio = doctoReqTramite.getDocumentoPorTipo().getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio();

				  //LLena la lista de los documentos para mostrar
				  if(!existeTipoDocumentoProbatorio(tipoDocsMostrar, idTipoDocumentoProbatorio)){
					  tipoDocsMostrar.add(doctoReqTramite.getDocumentoPorTipo().getTipoDocumentoProbatorio());
				  }

				  //LLena la lista de los documentos requeridos
				  //Antes de verificar si el tipo ya se encuentra en la lista valida si el documento es opcional
				  if(doctoReqTramite.getRefDocumentoOpcional() != 1 && !existeTipoDocumentoProbatorio(tipoDocsRequeridos, idTipoDocumentoProbatorio)){
					tipoDocsRequeridos.add(doctoReqTramite.getDocumentoPorTipo().getTipoDocumentoProbatorio());
				  }
			  }
		  }
		  
		  
		  
		  //Agrega las listas al bean
		  fileUploadVB.setTipoDocumentoProbatorioList(tipoDocsMostrar);
		  fileUploadVB.setTipoDocumentoProbatorioReqList(tipoDocsRequeridos);
		  
		  return fileUploadVB;
	  }
	  
	  
	  /**
	   * pregunta si ya existe el id de documento probatorio
	   * @param entradaList
	   * @param idTipoDocumentoProbatorio
	   * @return
	   */
	  private boolean existeTipoDocumentoProbatorio(List<TipoDocumentoProbatorio> entradaList,Integer idTipoDocumentoProbatorio){
		  boolean salida=false;
		  for(TipoDocumentoProbatorio entrada:entradaList){
			  if(entrada.getIdTipoDocumentoProbatorio().equals(idTipoDocumentoProbatorio)){
				  return true;
			  }
		  }
		  return salida;
	  }
	  
	  @RequestMapping(value="/uploadify")    
	  public @ResponseBody void uploadBytes(@RequestParam(value="file") MultipartFile[] file,
			  								
			  								HttpSession ses) throws IOException{
		  
		  

		  FileUploadVB cargadorDocumentosVB=(FileUploadVB) ses.getAttribute(SESSION_BEAN);

		  DocumentoProbatorio dp=cargadorDocumentosVB.getDocumentoProbatorio();
		  
			dp.setDigitalizacion(file[0].getBytes());
			
			StringTokenizer st=new StringTokenizer(file[0].getOriginalFilename(), ".");
			String ext=null;
			while(st.hasMoreTokens()){
				ext=st.nextToken();
			}
			//se pone la extencion temporalmente en el cifrado
			dp.setCifrado(ext); 
			
			cargadorDocumentosVB.setDocumentoProbatorio(dp);
			cargadorDocumentosVB.setLoadedBytes(true);
			ses.setAttribute(SESSION_BEAN, cargadorDocumentosVB);
			
	  }
	  
/**
 * carga el documentosDoctoReqTramite seleccionado
 * @param cveTipoDoctoProb
 * @param cveDocto
 * @param ses
 * @return
 */
	  
	  @RequestMapping(value="/infoDocSeleccionado")    
	  public @ResponseBody  String infoDocSeleccionado(@RequestParam(value="cveTipoDoctoProb",required=true) String cveTipoDoctoProb,
			  @RequestParam(value="cveDocto",required=true) String cveDocto,
			  HttpSession ses
			  ){
		  FileUploadVB cargadorDocumentosVB=(FileUploadVB) ses.getAttribute(SESSION_BEAN);
		  
		  //se le pasa el tipodocProb y el documento que corresponde
		  DoctoReqTramite documentosDoctoReqTramite=this.getDocumentoSelect(new Long(cveDocto),new Integer(cveTipoDoctoProb), cargadorDocumentosVB.getDoctoReqTramiteList());
		  cargadorDocumentosVB.setDoctoReqTramite(documentosDoctoReqTramite);
		  
		  //borrar documentoCargado
		  cargadorDocumentosVB.setLoadedDocform(false);
		  cargadorDocumentosVB.setCaptura(null);
		  //borra documento digitalizado
		  cargadorDocumentosVB.setDocumentoProbatorio(new DocumentoProbatorio());
		  cargadorDocumentosVB.setLoadedBytes(false);		  
		  
		  ses.setAttribute(SESSION_BEAN, cargadorDocumentosVB);
		  
		  return RES_OK;
		  
	  }
	  
	  
	  
 
	  /**
	   * Carga la lista de documentos subidos y elimina de la lista de documentos por subir.
	   * @param cveDoctoReqTramite
	   * @param cveDocto
	   * @param ses
	   * @return
	   */
	  

	  @RequestMapping(value="/loadDoc")    
	  public @ResponseBody   Map<String, ? extends Object>  cargaDocumento(@RequestParam(value="cveTipoDoctoProb",required=true) String cveTipoDoctoProb,
			  @RequestParam(value="cveDocto",required=true) String cveDocto,
			  HttpSession ses
			  ){
		  FileUploadVB cargadorDocumentosVB=(FileUploadVB) ses.getAttribute(SESSION_BEAN);
		  Map<String, Object> objMapaData = new HashMap<String, Object>();
		  
		  DocumentoProbatorioCaptura probatorioCaptura=null;
		  TipoDocumentoProbatorio tipoDocumentoProbatorio=null;

		  //se valida si la carga o captura es obligatoria y si se ha cargado
		  if(this.isDocumentacionObligatoriaCargada(cargadorDocumentosVB.getDoctoReqTramite(), cargadorDocumentosVB)){

			  //se borran los errores
			  cargadorDocumentosVB.setError(null);
			  
			  
			  //se llena con la informacion subida
			  probatorioCaptura=new DocumentoProbatorioCaptura();
			  //documento Capturado
			  probatorioCaptura.setCaptura(cargadorDocumentosVB.getCaptura());
			  //documento ditalizado
			  probatorioCaptura.setDocumentoProbatorio(cargadorDocumentosVB.getDocumentoProbatorio());
			
			  //el documento por tipo
			  
			  //TODO el tipoDocumentoProbatorio es el tipo generico se requiere el documento por tipo
			  probatorioCaptura.getDocumentoProbatorio().setDocumentoPorTipo(cargadorDocumentosVB.getDoctoReqTramite().getDocumentoPorTipo());
			
			  //la clave del documentocapturado
			  probatorioCaptura.setIdDocumentoCaptura(new Long(cveDocto));
			  //se agrega a la lista
			  cargadorDocumentosVB.getDocumenProbatorioCapturaList().add(probatorioCaptura);
			  
			  Iterator<TipoDocumentoProbatorio> iter = cargadorDocumentosVB.getTipoDocumentoProbatorioList().iterator();

			  //se quita de la lista de tipoDocumentoprobatorio por subir
			  while(iter.hasNext()){
				  tipoDocumentoProbatorio=iter.next();
				  if(tipoDocumentoProbatorio.getIdTipoDocumentoProbatorio().equals(new Integer(cveTipoDoctoProb))){
					  iter.remove();
				  } 
			  }
			  
			  //Se quita el elemento de la lista de tipo docs requeridos
			  Iterator<TipoDocumentoProbatorio> iterTipoDocReq = cargadorDocumentosVB.getTipoDocumentoProbatorioReqList().iterator();

			  //se quita de la lista de tipoDocumentoprobatorio por subir
			  while(iterTipoDocReq.hasNext()){
				  tipoDocumentoProbatorio=iterTipoDocReq.next();
				  if(tipoDocumentoProbatorio.getIdTipoDocumentoProbatorio().equals(new Integer(cveTipoDoctoProb))){
					  iterTipoDocReq.remove();
				  } 
			  }

			  //se resetean los campos temporales
			  cargadorDocumentosVB.setLoadedBytes(false);
			  cargadorDocumentosVB.setLoadedDocform(false);
			  cargadorDocumentosVB.setCaptura(null);
			  cargadorDocumentosVB.setDocumentoProbatorio(null);
			  ses.setAttribute(SESSION_BEAN, cargadorDocumentosVB);
	
		  }else{
			  objMapaData.put("error", DOC_DIG_ERROR);
			  cargadorDocumentosVB.setError(DOC_DIG_ERROR);
		  }
		 
		  objMapaData.put("tipoDocumentoProbatorioList",cargadorDocumentosVB.getTipoDocumentoProbatorioList());
		  objMapaData.put("tipoDocumentoProbatorioReqList",cargadorDocumentosVB.getTipoDocumentoProbatorioReqList());
		  objMapaData.put("documenProbatorioCapturaList", cargadorDocumentosVB.getDocumenProbatorioCapturaList());
		  return objMapaData;    
	  } 
	  
	  /**
	   * busca los id descripciones y restricciones del tipodocumentoProbatorio  y documento seleccionado del tramite
	   * @param cveDocto
	   * @param cveTipoDocProb
	   * @param entradaList
	   * @return
	   */
	  private DoctoReqTramite getDocumentoSelect(Long cveDocto,Integer cveTipoDocProb,List<DoctoReqTramite> entradaList){
		  
		  for(DoctoReqTramite entrada:entradaList){
			  if(entrada.getDocumentoPorTipo().getDocumento().getCveIdDocumento().equals(cveDocto)&&entrada.getDocumentoPorTipo().getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio().equals(cveTipoDocProb)){
				  return entrada;
			  }
		  }
		  
		  return null;
	  }
	/**
	 * Si la documentacion es requerida debe estar cargada tanto captura como carga
	 * @param documentosDoctoReqTramite
	 * @param fileUploadVB
	 * @return
	 */
	  private boolean isDocumentacionObligatoriaCargada(DoctoReqTramite documentosDoctoReqTramite,
			  FileUploadVB fileUploadVB){

		 // if(isDocumentoCargado(documentosDoctoReqTramite.getRefDocumentoCaptura(), fileUploadVB.isLoadedDocform())
				 // &&isDocumentoCargado(documentosDoctoReqTramite.getRefDocumentoObligatorio(), fileUploadVB.isLoadedBytes())){
		 try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  if(fileUploadVB.isLoadedDocform()
				  && isDocumentoCargado(documentosDoctoReqTramite.getRefCargaDocumentoObigatorio(), 
						  fileUploadVB.isLoadedBytes())){
			  
			  return true;
		  }
		  

		  return false;
	  }
	  /**
	   * Valida que si el documento es obligatorio se cargue
	   * @param obligatorio
	   * @param loaded
	   * @return
	   */
	  private boolean isDocumentoCargado(Integer obligatorio,boolean loaded){

		  if(obligatorio != null && obligatorio.equals(REQUERIDO)){
			return loaded;
		  }
		  
		  return true;
	  }
	  
	  /**
	   * Busca la lista de documentos para el TipoDocumentoProbatorio
	   * @param cveIdTipoDocProb
	   * @param ses
	   * @return
	   */
	  @RequestMapping(value="/cargaDocumentosDelTipo")    
	  public @ResponseBody  List<Documento> cargaDocumentosDelTipo(@RequestParam(value="cveIdTipoDocProb",required=true) String cveIdTipoDocProb,
			  													HttpSession ses
	  																){
		
		  List<Documento> documentos=new ArrayList<Documento>();
		  FileUploadVB fileUploadVB=(FileUploadVB) ses.getAttribute(FileUploadVB.SES_NAME);
		
		  
		  for(DoctoReqTramite docReqTramite:fileUploadVB.getDoctoReqTramiteList()){
			  if(docReqTramite.getDocumentoPorTipo().getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio().equals(new Integer(cveIdTipoDocProb))){
				  documentos.add(docReqTramite.getDocumentoPorTipo().getDocumento());
			  }
		  }
		  
		  //cargarlos en session
		  
		 //ses.setAttribute(FileUploadVB.SES_NAME, fileUploadVB);
	
		  return documentos;
		  
	  }
	  
	  
	  
	  @RequestMapping(value="/eliminaDoc")    
	  public @ResponseBody Map<String, ? extends Object> eliminaDocumento(@RequestParam(value="cveIdDocumentoPorTipo",required=true) String cveIdDocumentoPorTipo,
			  HttpSession ses){
	 
		  FileUploadVB cargadorDocumentosVB=(FileUploadVB) ses.getAttribute(SESSION_BEAN);
		  Map<String, Object> objMapaData = new HashMap<String, Object>();
		  
		  Iterator<DocumentoProbatorioCaptura> iter= cargadorDocumentosVB.getDocumenProbatorioCapturaList().iterator();
		  
		  while(iter.hasNext()){
			  DocumentoProbatorioCaptura documentoProbatorioCaptura=iter.next();
			 //TODO deve compararse con documento por tipo
			  if(documentoProbatorioCaptura.getDocumentoProbatorio().getDocumentoPorTipo().getIdDocumentoPorTipo().equals(new Long(cveIdDocumentoPorTipo))){
				  //agregar a la lista de documentos para mostrar
				  cargadorDocumentosVB.getTipoDocumentoProbatorioList().add(documentoProbatorioCaptura.getDocumentoProbatorio().getDocumentoPorTipo().getTipoDocumentoProbatorio());
				  //agregar a la lista de documentos requeridos si no son opcionales
				 
				  for(DoctoReqTramite docReq :  cargadorDocumentosVB.getDoctoReqTramiteList()){
					  if(documentoProbatorioCaptura.getDocumentoProbatorio().getDocumentoPorTipo().getDocumento().getCveIdDocumento() == 
						  docReq.getDocumentoPorTipo().getDocumento().getCveIdDocumento() &&
						  docReq.getRefDocumentoOpcional() == 0){
						  cargadorDocumentosVB.getTipoDocumentoProbatorioReqList().add(documentoProbatorioCaptura.getDocumentoProbatorio().getDocumentoPorTipo().getTipoDocumentoProbatorio());
						  break;
					  } 
				  }  
				  //****
				  //eliminar de la lista de cargados
				  iter.remove();
			  }
		  } 
		  cargadorDocumentosVB.setLoadedBytes(false);
		  cargadorDocumentosVB.setLoadedDocform(false);
		  
		  objMapaData.put("tipoDocumentoProbatorioList",cargadorDocumentosVB.getTipoDocumentoProbatorioList());
		  objMapaData.put("tipoDocumentoProbatorioReqList",cargadorDocumentosVB.getTipoDocumentoProbatorioReqList());
		  objMapaData.put("documenProbatorioCapturaList", cargadorDocumentosVB.getDocumenProbatorioCapturaList());	
		  return objMapaData;    
	  }
	  
	  @RequestMapping(value = "/saveDocSinTramite") 
	  public @ResponseBody Map<String, ? extends Object> saveDocSinTramite(HttpSession ses,
			  @RequestParam(value="cveIdTramite",required=true)String idTramite) { 
		  ((FileUploadVB)ses.getAttribute(FileUploadVB.SES_NAME)).setIdTramite(new Long(idTramite));
		  
		  return this.saveDoc(ses);
		  
	  }
	 
	 
	@RequestMapping(value = "/saveDoc") 
	  public @ResponseBody Map<String, ? extends Object> saveDoc(HttpSession ses) { 
		
		Long idTramite = (Long) ses.getAttribute(KEY_ID_TRAMITE);
		
		Solicitud solicitud = null;
		
		DocumentoProbatorio documentoProbatorio=null;
		Map<String, Object> objMapaData = new HashMap<String, Object>();
		FileUploadVB cargadorDocumentosVB=(FileUploadVB) ses.getAttribute(SESSION_BEAN);

		List<DocumentoProbatorio> DocumentosProbatorios=new ArrayList<DocumentoProbatorio>();
		
		for(DocumentoProbatorioCaptura dPC:cargadorDocumentosVB.getDocumenProbatorioCapturaList()){
			//encapsular los documentos de captura 
			if(dPC.getCaptura()!=null){
				documentoProbatorio=(DocumentoProbatorio) dPC.getCaptura();
				documentoProbatorio.setCifrado(dPC.getDocumentoProbatorio().getCifrado());
				documentoProbatorio.setDigitalizacion(dPC.getDocumentoProbatorio().getDigitalizacion());
				documentoProbatorio.setDocumentoPorTipo(dPC.getDocumentoProbatorio().getDocumentoPorTipo());
				
			}else{//si no tiene documento capturado pasa directamente
				documentoProbatorio=dPC.getDocumentoProbatorio();
			}
			
			
			this.agregarDocumentoALista(DocumentosProbatorios, documentoProbatorio);
		}
		
		try {
			solicitud = solicitudBusinessRemote.consultarPorIdTramite(idTramite);
			
			if(solicitud != null) {
				Tramite tramiteDocumentos = null;
				for(Tramite tram: solicitud.getTramites()) {
					if(tram.getTramiteId().equals(idTramite)) {
						tramiteDocumentos = tram;
					}
				}
				
				if(tramiteDocumentos != null) {
					tramiteDocumentos.setDocumentosProbatorios(DocumentosProbatorios);
					solicitudBusinessRemote.actualizarTramites(solicitud);
				}
			}
		
		} catch(Exception e) {
			log.debug("Error al guardar los documentos probatorios", e);
		}
		
		//se remueve la session
		ses.removeAttribute(FileUploadVB.SES_NAME);
		
		objMapaData.put("resultado", RES_OK);
		return objMapaData;
	  }
	
	
	//limpia el objeto en memoria al cancelar su carga
	@RequestMapping(value = "/cancelUploadify") 
	  public @ResponseBody Map<String, ? extends Object> cancelUploadify(
			  									HttpSession ses) { 
		FileUploadVB cargadorDocumentosVB=(FileUploadVB) ses.getAttribute(SESSION_BEAN);
		cargadorDocumentosVB.setLoadedBytes(false);
		Map<String, Object> objMapaData = new HashMap<String, Object>();
		  objMapaData.put("resultado", RES_OK);
		return objMapaData;
		
	}
	
	private void agregarDocumentoALista(List<DocumentoProbatorio> lista,DocumentoProbatorio documento) {
		if(documento instanceof Pasaporte) {
			log.debug("el objeto es de tipo pasaporte");
			lista.add((Pasaporte) documento);
		} else if(documento instanceof CartillaMilitar) {
			log.debug("El objeto es de tipo CartillaMilitatar");
			lista.add((CartillaMilitar) documento);
		} else if(documento instanceof CedulaProfesional) {
			log.debug("El objeto es de tipo CedulaProfesional");
			lista.add((CedulaProfesional) documento);
		} else if(documento instanceof Acta) {
			log.debug("El objeto es de tipo Acta");
			lista.add((Acta) documento);
		}else if(documento instanceof ActaUnionCivil) {
			log.debug("El objeto es de tipo Acta union civil");
			lista.add((ActaUnionCivil) documento);
		}else if(documento instanceof ActaTerminoUnionCivil) {
			log.debug("El objeto es de tipo Acta termino union civil");
			lista.add((ActaTerminoUnionCivil) documento);
		}else {
			lista.add(documento);
		}
	}
	
	/*
	//regresa la session en un objeto jsson
	@RequestMapping(value = "/getSessionVBJasson") 
	  public@ResponseBody Map<String, ? extends Object>  getSessionVBJasson(
			  									HttpSession ses,@RequestParam(value="cveIdTipoTramite",required=true)String idTipoTramite) { 
		
		
		FileUploadVB cargadorDocumentosVB=(FileUploadVB) ses.getAttribute(SESSION_BEAN);
		if(!cargadorDocumentosVB.getIdTipoTramite().equals(idTipoTramite)){
			return this.cargaComponente(null, idTipoTramite, null, ses);
		}
		//return cargadorDocumentosVB;
		
	}
	*/
	
	/**
	 * Metodo para saber si todos los documentos son o no necesarios
	 * si todos son opcionales la carga se muestra como finalizada
	 * @param listaDocumentos
	 * @return
	 */
	private Boolean documentosRequeridos(List<DoctoReqTramite> listaDocumentos) {
		
//		for(DoctoReqTramite documento: listaDocumentos) {
//			if(documento.getRefDocumentoCaptura() != 0 && documento.getRefDocumentoObligatorio() != 0)
//				return true;
//		}
		
		for(DoctoReqTramite documento: listaDocumentos) {
			log.info("Clave documento obligartorio: " + documento.getCveIdDoctoReqTramite());
			log.info("documento obligartorio: " + documento.getRefCapturaDocumentoObligatorio());
			log.info("Documento carga obligatorio : " + documento.getRefCargaDocumentoObigatorio());
			if( documento.getRefCapturaDocumentoObligatorio() != 0 && 
				(documento.getRefCargaDocumentoObigatorio() == null ? true : documento.getRefCargaDocumentoObigatorio() != 0 ) )
				return true;
		}
		
		return false;
	}

}
