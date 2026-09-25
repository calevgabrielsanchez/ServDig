package mx.gob.imss.ctirss.delta.derechohabientes.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorioCaptura;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.FileUploadVB;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Nacimiento;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Componente para guardar los documentos asincronos
 * ya que en caso del acta de nacimiento se consulta renapo y el ws podria tardar en responder
 * @author Mario
 *
 */
@Component
public class GuardaDocumentosAsincrono extends AbstractController {
	
	@Autowired
	private PersonaBusinessRemote personaBusinessRemote;
	@Autowired
	private DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusinessRemote;
	
	/**
	 * Metodo asincrono para guardar el acta de nacimietno que vengga dentro de un objeto de tipo fisica
	 * @param Fisica - la persona que contiene el acta de nacimiento, debe traer al menos el id de la persona y el acta de nacimiento
	 * @param Long - el id del tramite al que se le acosiara el acta de nacimiento
	 * @return void
	 */
	/*@Async
	@Transactional*/
	public void guardaActaDeNacimientoSinConsultaARenapo(Fisica fisica, Long idTramite) {
		
		try {
			if(fisica != null && fisica.getActaNacimiento() != null){
				List<DocumentoProbatorio> documentosProbatorios=new ArrayList<DocumentoProbatorio>();
				documentosProbatorios.add(fisica.getActaNacimiento());
				documentoProbatorioServiceBusinessRemote.procesaDocumentosGD(idTramite,fisica.getIdPersona(),documentosProbatorios);
	
			}
		} catch(Exception e) {
			log.error("Ocurrio un error al guardar el acta de nacimiento",e);
		}
	}
	
	/**
	 * Metodo asincrono para guardar el acta de nacimiento consultando a renapo
	 * @param fisica - El objeto fisica que debe contener al menos el curp y el id de la persona a la que se asociara el acta de nacimiento
	 * @param idTramite - El id del tramite
	 */
	/*@Async
	@Transactional*/
	public void guardaActaDeNacimiento(Fisica fisica, Long idTramite) {
		log.debug("inicio el guardado del acta de nacimiento a las " + (new Date()));
		//guardamos el acta de nacimiento
		try {
			if(fisica != null && fisica.getCurp()!= null && !fisica.getCurp().trim().equals("")){
				Fisica fisicaR = personaBusinessRemote.buscarPersonaFisicaPorCurpEnRenapo(fisica.getCurp().trim());
				if(fisicaR != null && fisicaR.getActaNacimiento() != null){
					List<DocumentoProbatorio> documentosProbatorios=new ArrayList<DocumentoProbatorio>();
				     Nacimiento paso = fisicaR.getActaNacimiento();
				    documentosProbatorios.add(paso);
					documentoProbatorioServiceBusinessRemote.procesaDocumentosGD(idTramite,fisica.getIdPersona(),documentosProbatorios);
					log.debug("Termino el guardado del acta a las  " + (new Date()));
				}
			}
		} catch(Exception e) {
			log.error("Ocurrio un error al guardar el acta de nacimiento",e);
		}
	}
	
	//@Async
	@Transactional
	public void salvaDocumentosProbatorios(HttpSession session, Long idTramite, Long idPersona) {
		this.salvaDocumentosPrivate(session, idTramite, idPersona);
	}
	
	public void salvaDocumentosProbatoriosSincrono(HttpSession session, Long idTramite, Long idPersona) {
		log.debug("Entro al metodo sincrono de guardado de documentos");
		this.salvaDocumentosPrivate(session, idTramite, idPersona);
		log.debug("Termino de guardar los documentos probatorios desde el metodo sincrono");
	}
	
	private void salvaDocumentosPrivate(HttpSession session, Long idTramite, Long idPersona) {
		if(session.getAttribute(FileUploadVB.SES_NAME) != null) {

			if(idTramite != null) {
				((FileUploadVB)session.getAttribute(FileUploadVB.SES_NAME)).setIdTramite(idTramite);
			}

			
			FileUploadVB cargadorDocumentosVB=(FileUploadVB) session.getAttribute(FileUploadVB.SES_NAME);

			if(cargadorDocumentosVB != null && cargadorDocumentosVB.getDocumenProbatorioCapturaList() != null
					&& !cargadorDocumentosVB.getDocumenProbatorioCapturaList().isEmpty()) {
				
				try {
					for(DocumentoProbatorioCaptura dPC:cargadorDocumentosVB.getDocumenProbatorioCapturaList()){
						
						
						//encapsular los documentos de captura 
						if(dPC!=null){
							List<DocumentoProbatorio> documentosProbatorios=new ArrayList<DocumentoProbatorio>();
							
							DocumentoProbatorio documentoProbatorio=(DocumentoProbatorio) dPC.getCaptura();
							documentoProbatorio.setCifrado(dPC.getDocumentoProbatorio().getCifrado());
							documentoProbatorio.setDigitalizacion(dPC.getDocumentoProbatorio().getDigitalizacion());
							documentoProbatorio.setDocumentoPorTipo(dPC.getDocumentoProbatorio().getDocumentoPorTipo());
							//se agrega a la lista
							documentosProbatorios.add(documentoProbatorio);
							documentoProbatorioServiceBusinessRemote.procesaDocumentosGD(cargadorDocumentosVB.getIdTramite(),idPersona,documentosProbatorios);
						}
						
					}
				} catch(Exception e) {
					log.error("Ocurrio un error al guardar los documentos",e);
				}

				//se remueve la session
				session.removeAttribute(FileUploadVB.SES_NAME);
			}
		}
	}
}
