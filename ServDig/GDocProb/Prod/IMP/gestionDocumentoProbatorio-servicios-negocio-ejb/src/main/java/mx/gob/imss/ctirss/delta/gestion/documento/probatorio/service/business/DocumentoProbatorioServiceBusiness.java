package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.CambioMasivoClinicaServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GestionDocumentalServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.DocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.PersonaSinDocumentosException;
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.RegistrarDocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.dto.DatosBoveda;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.dao.DocumentacionTramiteDAOLocal;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.dao.DocumentoProbatorioDaoLocal;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.entity.DocumentoProbatorioServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.utility.BovedaServiceUtilLocal;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.PdfUtils;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser.DocumentoProbatorioParser;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoFamiliar;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Acta;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DetalleNivelEducativo;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DoctoReqTramite;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DoctoReqTramiteOrigenSol;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Documento;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipo;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoDocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoNivelEducativo;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.persistence.DitDoctosPersona;
import mx.gob.imss.ctirss.delta.persistence.DitDoctosPersonaPK;
import mx.gob.imss.ctirss.delta.persistence.DitDocumentacionTramite;
import mx.gob.imss.ctirss.delta.persistence.DitDocumentacionTramitePK;
import mx.gob.imss.ctirss.delta.persistence.DitDocumentoProbatorio;

@Stateless(name = "documentoProbatorioServiceBusiness",mappedName = "documentoProbatorioServiceBusiness")
public class DocumentoProbatorioServiceBusiness extends AbstractServiceBusiness
		implements DocumentoProbatorioServiceBusinessRemote {

	private static final boolean  DEPLOY_OLD_MODE=false;
	private static final String TEXTO_EN_DOCUMENTO="COPYA IMSS";
	@EJB
	DocumentoProbatorioServiceEntityLocal entity;
	@EJB
	DocumentoProbatorioDaoLocal documentoProbatorioDao;
	@EJB
	DocumentacionTramiteDAOLocal documentacionTramiteDao;
	@EJB(name = "solicitudBusiness", mappedName = "solicitudBusiness")
	SolicitudBusinessRemote solicitudBusinessRemote;
	@EJB
	GestionDocumentalServiceRemote gestionDocumentos;
	@EJB
	CambioMasivoClinicaServiceRemote cambioClinica;
	@EJB
	BovedaServiceUtilLocal bovedaServiceUtilLocal;

	private byte[] waterMark=null;	
	
	@Override
	public Boolean requiereDocumentos(Long idTipoTramite) {
		
		Long numeroDocumentosExistentes = null;
		Boolean requiereDocumentos = false;
		
		numeroDocumentosExistentes = entity.getNumeroDocumentosProbatoriosActivos(idTipoTramite, null);
		
		if(numeroDocumentosExistentes != null && numeroDocumentosExistentes > 0) {
			requiereDocumentos = true;
		}
		
		return requiereDocumentos;
	}

	/**
	 * Guarda los documentos
	 */
	@Override
	public List<DocumentoProbatorio> registrarDocumentos(List<DocumentoProbatorio> documentos) throws RegistrarDocumentoProbatorioException {
		List<DocumentoProbatorio> documentoProbatorios = null;
		if(DEPLOY_OLD_MODE){//como se hacia en persona
			documentoProbatorios = this.entity.registrarDocumentos(documentos);
		}else{//nueva implementacion para todos los documentos
			documentoProbatorios=this.registrarDocumentosNew(documentos);
		}
		return documentoProbatorios;
	}
	
	@Override
	public List<MedicoFamiliar> getAllMedicos() throws Exception {
		return gestionDocumentos.getAllMedicos();
//		return entity.findAllMedicos();
	}

	@Override
	public List<TipoNivelEducativo> getAllTipoNivelEducativos()
			throws Exception {
		return gestionDocumentos.getAllTipoNivelEducativos();
//		return this.entity.findAllTipoNivelEducativo();
	}

	@Override
	public DetalleNivelEducativo getDetalleNivelEducativo(Long idTipoNivel,
			Long idNivel) throws Exception {
		return gestionDocumentos.getDetalleNivelEducativo(idTipoNivel, idNivel);
//		return entity.getDetalleNivelEducativo(idTipoNivel, idNivel);
	}
	
	

	@Override
	public List<MedicoEnTurno> medicoEnTurnos(Long idUmf) throws Exception {
		return cambioClinica.medicoEnTurnos(idUmf);
//		return this.entity.getMedicosEnTurnobyUmf(idUmf);
	}

	@Override
	public List<DocumentoProbatorio> consultarDocumentosDePersona(
			Persona persona) throws PersonaSinDocumentosException {

		List<DocumentoProbatorio> documentos = entity.consultarDocumentosDePersona(persona); 
		
		// Si la lista de documentos no contiene actas de naciemiento
		if(documentos.isEmpty()){
			throw new PersonaSinDocumentosException();
		}
		
		return documentos;
	}
	
	
	@Override
	public void asociaDocumentosPersona(Long idPersona, Long idTramite) {
		// TODO Auto-generated method stub
		List<DocumentoProbatorio> documentosProbatorios = this.listaDocumentosProbatoriosTramite(idTramite);
		this.saveDocumentoPersona(idPersona, documentosProbatorios);
	}


	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void procesaDocumentos(Long idTramite, List<DocumentoProbatorio> documentoProbatorios) throws DocumentoProbatorioException{
		
		Long idPersonaTramite = documentacionTramiteDao.getIdPersona(idTramite);
		this.procedaDocumentosTramite(idTramite, idPersonaTramite, documentoProbatorios);
		
	}
	
	/**
	 * guarda los documentos y los asocia a su tramite
	 * @throws DerechohabientesBusinessException 
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void procesaDocumentosGD(Long idTramite, Long idPersona, List<DocumentoProbatorio> documentoProbatorios) throws DocumentoProbatorioException{
		//llamamos al metodo generico
		this.procedaDocumentosTramite(idTramite, idPersona, documentoProbatorios);
	}
	
	/**
	 * Metodo para guardar los documentos probatorios del tramite y asociarlos a la persona
	 * @param idTramite
	 * @param idPersona
	 * @param documentosProbatorios
	 * @throws DocumentoProbatorioException
	 */
	private void procedaDocumentosTramite(Long idTramite, Long idPersona, List<DocumentoProbatorio> documentosProbatorios) throws DocumentoProbatorioException{
		//los documentos que contendran ya los ids
		List<DocumentoProbatorio> documentosGuardados = new ArrayList<DocumentoProbatorio>();
		if(documentosProbatorios != null && !documentosProbatorios.isEmpty()) {
			for(DocumentoProbatorio dP: documentosProbatorios){
				dP = this.guardarDocumentoTramite(idTramite, dP);
				//se agrega el id del documento guardado para no volverlos a consultar
				documentosGuardados.add(dP);
			}
			//si el id de la persona no viene nula
			if(idPersona != null) {
				//se relacionan los documentos contra la persona
				this.saveDocumentoPersona(idPersona, documentosGuardados);
			}
		}
	}
	
	@Override
	public List<DocumentoProbatorio> listaDocumentosProbatoriosTramite (
			Long cveIdTramite) {
		List<DocumentoProbatorio> documentoProbatorios=null;
		documentoProbatorios = this.documentacionTramiteDao.findDocumentosProbatorios(cveIdTramite);
		return documentoProbatorios;
	}
	
	@Override
	public List<DocumentoProbatorio> listaDocumentosProbatoriosActivosTramite(Long cveIdTramite) {
		return this.documentacionTramiteDao.findDocumentosProbatoriosActivos(cveIdTramite);
	}
	
	
	
	@Override
	public DocumentoProbatorio getDocumentoProbatorio(Long idDocProvatorio) throws DocumentoProbatorioException{
		
		DocumentoProbatorio dP;
		dP=this.documentoProbatorioDao.getDocumentoProvatorio(idDocProvatorio);
		this.checkValDocDig(dP);
		return dP;
	}
	
	@Override
	public DocumentoProbatorio getDocumentoProbatorioBytes(Long idDocProvatorio){
		
		DocumentoProbatorio dP;
		dP=this.documentoProbatorioDao.getDocumentoProvatorioBytes(idDocProvatorio);
		this.checkValDocDig(dP);
		return dP;
	}
	
	
	
	
	
	
	public List<DocumentoProbatorio> registrarDocumentosNew(
			List<DocumentoProbatorio> documentos) throws RegistrarDocumentoProbatorioException{
		try{
			for(DocumentoProbatorio dP:documentos ){
				this.registraDocumento(dP);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new RegistrarDocumentoProbatorioException();
		}
		return documentos;
	}
		
	/**
	 * Guarda un documentoProbatorio marca de agua y captura
	 * @param dP
	 * @return
	 * @throws DerechohabientesBusinessException 
	 */
	@Override
	public DocumentoProbatorio registraDocumento(DocumentoProbatorio dP) throws DocumentoProbatorioException{
		//marca de agua y Cifrado
		this.addWaterMarkAndCifrado(dP);
		//se guarda el documento capturado
		dP=this.documentoProbatorioDao.saveDocumentoProbatorio(dP);
		this.guardaDocumentoCaptura(dP);
		return dP;
	}
	/** 
	 * Ageraga marca de agua y cifrado por referencia al documento digitalizado
	 * @param dP
	 */
		private void addWaterMarkAndCifrado(DocumentoProbatorio dP){
			if(dP.getDigitalizacion()!=null){
				//se transforma de imagen a PDF
				byte[] temp=PdfUtils.analizeTypeToTransformImgtoPdf(dP.getCifrado(),dP.getDigitalizacion());
				//agregar marca de agua;
			
				temp=PdfUtils.addWatermark(temp,this.getWaterMark(),TEXTO_EN_DOCUMENTO);
			
				dP.setDigitalizacion(temp);
				//Cifrado MD5
		
				dP.setCifrado(PdfUtils.getProductoMD5(dP.getDigitalizacion()));
			}
		}
		
		/**
		 * 
		 * @param documentoProbatorio
		 * @throws DocumentoProbatorioException
		 */
		private void  guardaDocumentoCaptura(DocumentoProbatorio documentoProbatorio) throws DocumentoProbatorioException{
			Object documentoCapturado=null;
			log.debug("el documento probatorio es: " + documentoProbatorio );
			
			if( documentoProbatorio instanceof Acta ){
				documentoCapturado=DocumentoProbatorioParser.modelToModelActa(documentoProbatorio);
				if(documentoCapturado != null) {
					log.debug("Se guardara un acta");
					this.guardaDocCapt(documentoCapturado);
				}
			}
			//guarda cualquier otro documento
			documentoCapturado=DocumentoProbatorioParser.modelToModelCaptura(documentoProbatorio);
			if(documentoCapturado != null) {
				log.debug("el documento capturado es: " + documentoCapturado );
				log.debug("Se guarda documento");
				this.guardaDocCapt(documentoCapturado);
			}	
		}
		
		/**
		 * 
		 * @param documentoCapturado
		 */
		private void guardaDocCapt(Object documentoCapturado){
			if(documentoCapturado!=null){
				this.documentoProbatorioDao.saveDocumentoCapturado(documentoCapturado);
			}
		}



	


	/**
	 * obtiene los bytes de la marka de agua
	 * @return
	 */
	
	public byte[] getWaterMark() {
		if(this.waterMark==null){
			this.waterMark=documentoProbatorioDao.findWaterMark().getRefDocumentoDigitalizado();
		}
		return this.waterMark;
	}
	/**
	 * Si el documento es invalida pone null en el cifrado y la digitalizacion
	 * @param documentoProbatorio
	 */
	private void checkValDocDig(DocumentoProbatorio documentoProbatorio){
		if((documentoProbatorio!=null)&&(documentoProbatorio.getDigitalizacion()!=null)
				&& !PdfUtils.compareMD5Names(documentoProbatorio.getDigitalizacion(),documentoProbatorio.getCifrado())){
			documentoProbatorio.setDigitalizacion(null);//podria metersele una imagen que asi lo indique
			documentoProbatorio.setCifrado("");
		}
	}

	/**
	 * Metodo que relaciona el documento a la persona
	 * @param idPersona
	 * @param lista
	 */
	private void saveDocumentoPersona(Long idPersona,List<DocumentoProbatorio> lista) {
		//Se hace una lista ya que para el acta de nacimiento se guarda en dos lados en (DIT_ACTA y DIT_NACIMIENTO)
		//y ambas comparten el id de documento probatorio
		List<Long> idsDocumentosYaGuardados = new ArrayList<Long>();
		for(DocumentoProbatorio doc: lista) {
			Long idDocumentoProbatorio = doc.getIdDocumentoProbatorio().longValue();
			//Solo si el id del documento probatorio no se encuentra dentro de los ya guardados se vuelve a guardad
			if(!idsDocumentosYaGuardados.contains(idDocumentoProbatorio)) {
				DitDoctosPersona docto = new DitDoctosPersona();
				DitDoctosPersonaPK llave = new DitDoctosPersonaPK();
				//se setean las claves
				llave.setCveIdPersona(idPersona);
				llave.setCveIdDocumentoProbatorio(idDocumentoProbatorio);
				docto.setId(llave);
				//Se persiste la relacion persona-documento
				documentacionTramiteDao.saveDocumentacionPersona(docto);
				//una vez que ya fue guardado el documento se agrega a la lista de guardados
				idsDocumentosYaGuardados.add(idDocumentoProbatorio);
			}
			
			
		}
	}
	
	@Override
	public DocumentoProbatorio registrarAsociarDocumentoProbatorioPersona(
			DocumentoProbatorio documento, Long cvePersona)
			throws DocumentoProbatorioException{

		// Se registra el documento probatorio
		documento = this.registraDocumento(documento);

		// Se asocia el documento probatorio a la persona
		this.entity.asociarDocumentoAPersona(documento, cvePersona);

		return documento;
	}
	
	@Override
	public void modificarDocumentoProbatorio(
			DocumentoProbatorio documentoProbatorio) throws DocumentoProbatorioException{

		this.entity.modificarDocumentoProbatorio(documentoProbatorio);
	}
	
	@Override
	public void eliminarDesasociarDocumentoProbatorioPersona(
			DocumentoProbatorio documentoProbatorio, Long cvePersona) {

		this.entity.eliminarDesasociarDocumentoProbatorioPersona(documentoProbatorio, cvePersona);
	}	

	@Override
	public List<DoctoReqTramite> getDocumentosRequeridosPorTipoTramite(
			Long cveIdTipoTramite) {
		List<DoctoReqTramite> doctoReqTramites = null;
		
		
		doctoReqTramites = entity.obtenerListaDeDocumentos(cveIdTipoTramite, false);
		
		
		return doctoReqTramites;
	}

	@Override
	public List<Documento> getDocumentosPorTramites(List<Long> idTiposTramite) {
		List<Documento> documentos = null;
		
		try {
			documentos = entity.obtenerListaDeDocumentos(idTiposTramite,false);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return documentos;
	}

	
	@Override
	public List<Documento> getDocumentosPorTramitesMovPatInternet(List<Long> idTiposTramite) {
		List<Documento> documentos = null;
		try {
			documentos = entity.obtenerDocumentosMovPatInternet(idTiposTramite, false);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return documentos;
	}
	
	public List<Map<String,Object>> getDocumentosClasificadosPorTipoTramite(
			List<Long> idTiposTramite) {
		List<DocumentoPorTipo> documentos = null;
		List<TipoDocumentoProbatorio> tiposDocumento = null;
		List<Map<String,Object>> clasificados = null;
		
		tiposDocumento = entity.getTiposDocumentoProbatorios();
		
		if(tiposDocumento != null) {
			documentos = entity.obtenerDocumentosPorTipoByTipoTramite(idTiposTramite);
			
			if(documentos != null) {
				clasificados = new ArrayList<Map<String,Object>>();
				Map<String,Object> clasificacion = null;
				
				for(TipoDocumentoProbatorio tipo: tiposDocumento) {
					clasificacion = getDocumentosPorTipo(documentos, tipo);
					if(clasificacion != null) {
						clasificados.add(clasificacion);
					}
				}
			}
		}
		
		return clasificados;
	}
	
	private Map<String,Object> getDocumentosPorTipo(List<DocumentoPorTipo> documentos, TipoDocumentoProbatorio tipo) {
		Map<String, Object> mapa = null;
		List<Documento> doctos = new ArrayList<Documento>();
		Map<Long,Object> datos = new HashMap<Long, Object>();
		for(DocumentoPorTipo docto: documentos) {
			if(docto.getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio().equals(tipo.getIdTipoDocumentoProbatorio())) {
				datos.put(docto.getDocumento().getCveIdDocumento(), docto.getDocumento());
			}
		}
		if(!datos.isEmpty()) {
			Iterator<Long> iter = datos.keySet().iterator();
			while(iter.hasNext()) {
				doctos.add((Documento)datos.get(iter.next()));
			}
		}
		
		if(!doctos.isEmpty()) {
			mapa = new HashMap<String, Object>();
			mapa.put("titulo", tipo.getDescripcion());
			mapa.put("documentos", doctos);
		}
		
		return mapa;
	}

	@Override
	public void guardarDocumentosCapturados(Long idSolicitud)
			throws SolicitudNoEncontradaException,
			TramiteNoEncontradoException, DocumentoProbatorioException {
		
		Solicitud solicitud = new Solicitud(idSolicitud);
		
		solicitud = solicitudBusinessRemote.consultar(solicitud);
		
		this.guardarDocumentosCapturados(solicitud);
		
	}

	@Override
	public void guardarDocumentosCapturados(Solicitud solicitud)
			throws TramiteNoEncontradoException, DocumentoProbatorioException {
		
		if(solicitud.getTramites().isEmpty()) {
			throw new TramiteNoEncontradoException(null);
		} else {
			for(Tramite tramite: solicitud.getTramites()) {
				if(tramite.getDocumentosProbatorios() != null && !tramite.getDocumentosProbatorios().isEmpty()) {
					this.procesaDocumentos(tramite.getTramiteId(), tramite.getDocumentosProbatorios());
				}
			}
		}
	}
	
	@Override
	public Long getIdPersonaTramite(Long idTramite) {
	 Long idPersona = documentacionTramiteDao.getIdPersona(idTramite);
	 return idPersona;
	}
	
	@Override
	public void eliminarDocumentosTramite(Long cveIdTramite) throws DocumentoProbatorioException {
		List<DitDocumentoProbatorio> documentos = null;
		
		try {
			documentos = documentacionTramiteDao.findDitDocumentosProbatorios(cveIdTramite);
		} catch (Exception e) {
			DocumentoProbatorioException.throwException(">> CDA >> Ocurrio un error al buscar los documentos asociados al tramite " + cveIdTramite);
		}
		
		if (documentos != null && !documentos.isEmpty()) {
			for(DitDocumentoProbatorio documento : documentos) {
				documento.setFecRegistroBaja(new Date());
				documentoProbatorioDao.updateDocumentoProbatorio(documento);
			}
		} else {
			log.debug(">> CDA >> No existen documentos asociados al tramite con identificador: " + cveIdTramite);
		}
		
	}

	@Override
	public void eliminarDocumentoProbatorio(DocumentoProbatorio documentoProbatorio) {
		this.entity.eliminarDocumentoProbatorio(documentoProbatorio);
	}

	@Override
	public void eliminarDocumentoProbatorioBoveda(DocumentoProbatorio documentoProbatorio) throws DocumentoProbatorioException {
		
		bovedaServiceUtilLocal.eliminarDocumentoBoveda(documentoProbatorio);
		this.eliminarDocumentoProbatorio(documentoProbatorio);
	}

	@Override
	public DocumentoProbatorio guardarDocumentoProbatorioBoveda(DatosBoveda datosBoveda, DocumentoProbatorio documentoProbatorio)  throws DocumentoProbatorioException{

		documentoProbatorio = bovedaServiceUtilLocal.guardarDocumentoBoveda(documentoProbatorio, datosBoveda);
		//eliminamos los bytes una vez que ya se mando a boveda
		documentoProbatorio.setDigitalizacion(null);
		documentoProbatorio = this.guardarDocumentoTramite(datosBoveda.getIdTramite(), documentoProbatorio);
		
		return documentoProbatorio;
	}
	
	
	@Override
	public DocumentoProbatorio getDocumentoBoveda(String idDocumento, DatosBoveda datosBoveda) throws DocumentoProbatorioException {
		DocumentoProbatorio docto = null;
		
		docto = bovedaServiceUtilLocal.getDocumento(idDocumento, datosBoveda);
		
		return docto;
	}

	private DocumentoProbatorio guardarDocumentoTramite(Long idTramite, DocumentoProbatorio documento) throws DocumentoProbatorioException {
		//guardado y tratamiento del documento Probatorio
		documento = this.registraDocumento(documento);
		//Creamos los objetos que persistiremos
		DitDocumentacionTramite ditDocumentacionTramite = null; 
		//guardado de La relacion Documento Tramite
		ditDocumentacionTramite = new DitDocumentacionTramite();
		//id Tramite
		ditDocumentacionTramite.setId(new DitDocumentacionTramitePK());
		ditDocumentacionTramite.getId().setCveIdTramite(idTramite);
		ditDocumentacionTramite.getId().setCveIdDocumentoProbatorio(documento.getIdDocumentoProbatorio().longValue());
		//se asocia el documento al tramite
		this.documentacionTramiteDao.saveDocumentacionTramite(ditDocumentacionTramite);
		
		return documento;
	}
	
	@Override
	public List<DoctoReqTramiteOrigenSol> getDocumentosRequeridosPorTipoTramiteOrigenSol (
			Long cveIdTipoTramite, Long cveIdOrigenSolicituid) throws DocumentoProbatorioException {
		List<DoctoReqTramiteOrigenSol> doctoReqTramites = null;
		doctoReqTramites = entity.getDocumentosReqTramiteOrigenSol(cveIdTipoTramite, false, cveIdOrigenSolicituid );
		return doctoReqTramites;
	}
	
}
