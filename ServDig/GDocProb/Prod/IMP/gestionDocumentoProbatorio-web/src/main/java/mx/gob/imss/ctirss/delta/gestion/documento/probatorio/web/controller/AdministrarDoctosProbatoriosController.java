package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.documento.probatorio.DocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.PersonaSinDocumentosException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.web.beans.DocumentoProbatorioFormWrapper;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.enums.DocumentosEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoAdministracionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CURP;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Documento;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipo;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Nacimiento;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoDocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoDocumentoProbatorioRenapoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.web.validator.AdministracionDoctosProbatoriosValidator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/documentos/probatorios/administrar")
public class AdministrarDoctosProbatoriosController extends AbstractController {

	@EJB
	private DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusiness;

	
	@RequestMapping(method = RequestMethod.GET)
	public String initAdmonDocumentosProbatorios(final HttpSession session) {

		return "initAdmonDocumentosProbatorios";
	}
	
	@RequestMapping(value = "/{idPersona}", method = RequestMethod.GET)
	public String obtenerDocumentosProbatoriosPersona(final HttpSession session,
			@PathVariable Long idPersona) {
		
		session.removeAttribute("doctosProbatorios");

		Persona persona = new Persona();
		persona.setIdPersona(idPersona);
				
		try {
			List<DocumentoProbatorio> doctosProbatorios = this.documentoProbatorioServiceBusiness
					.consultarDocumentosDePersona(persona);
						
			session.setAttribute("doctosProbatorios", doctosProbatorios);
		} catch (PersonaSinDocumentosException e) {
			session.setAttribute("doctosProbatorios", new ArrayList<Domicilio>());
			log.error(e);
		}

		return "initAdmonDocumentosProbatorios";
	}
	
	@RequestMapping(value = "/init-agregar", method = RequestMethod.GET)
	public String initAgregarDocumentoProbatorio(Model model) {

		DocumentoProbatorioFormWrapper documentoProbatorio = new DocumentoProbatorioFormWrapper();
				
		model.addAttribute("documentoProbatorioFormWrapper", documentoProbatorio);
		
		return "admon.doctosProbatorios.captura";
	}

	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/agregar", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> agregarDocumentoProbatorio(Model model,
			final HttpSession session, @RequestBody DocumentoProbatorioFormWrapper form, 
			HttpServletResponse response) {

		Map<String, Object> result = new HashMap<String, Object>();
    	Errors errors = new BindException(form, "model");
    	
		List<DocumentoProbatorio> doctosProbatorios = (List<DocumentoProbatorio>) session
				.getAttribute("doctosProbatorios");
		
		//Se valida que la persona no cuente con documentos repetidos
		if (doctosProbatorios != null) {
			for(DocumentoProbatorio documento : doctosProbatorios){
				if (documento.getDocumentoPorTipo().getDocumento()
						.getCveIdDocumento().intValue() == form
						.getTipoDocumentoProbatorio()
						.getIdTipoDocumentoProbatorio().intValue() && (documento.getEstadoAdministracionDocto() != null &&
						documento.getEstadoAdministracionDocto().getClave() != EstadoAdministracionEnum.ELIMINADO.getClave())) {
					DocumentoProbatorioException e = new DocumentoProbatorioException(
							"La persona ya cuenta con el documento probatorio que se est\u00E1 registrando.");
					log.error(e);
					form = new DocumentoProbatorioFormWrapper();
					form.setErrorFormGeneral(e.getMessage());
					this.procesarErrorDeNegocio(e, result, response);
					
					return result;
				}
			}
		} else {
			//Se ignora la validacion si no se selecciono ningun documento
			return result;
		}
		
		//Se valida el medio de contacto capturado		
		new AdministracionDoctosProbatoriosValidator().validate(form, errors);
		if( errors.hasErrors()){
			this.procesaErroresDeCaptura(errors, result, response);
			return result;
		}
		
		DocumentoProbatorio documentoProbatorio = null;
		DocumentoPorTipo documentoPorTipo = null;
		Documento documento = null; 
		Long idDocumento = null;
		String desDocumento = null;
		Long idDocumentoPorTipo = null;
		
		if (form.getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio().intValue() == DocumentosEnum.ACTA_NACIMIENTO.getId().intValue()) {
			documentoProbatorio = form.getActaNacimiento();
			idDocumento = DocumentosEnum.ACTA_NACIMIENTO.getId();
			desDocumento = DocumentosEnum.ACTA_NACIMIENTO.getDescripcion();
			idDocumentoPorTipo = DocumentoPorTipoEnum.ACTA_NACIMIENTO.getId();
		} else {
			documentoProbatorio = form.getDocProbRENAPO();

			if (form.getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio().intValue() == DocumentosEnum.DOCUMENTO_MIGRATORIO.getId().intValue()) {
				idDocumento = DocumentosEnum.DOCUMENTO_MIGRATORIO.getId();
				desDocumento = DocumentosEnum.DOCUMENTO_MIGRATORIO.getDescripcion();
				idDocumentoPorTipo = DocumentoPorTipoEnum.DOCUMENTO_MIGRATORIO.getId();
				((CURP)documentoProbatorio).setNumTipoDocumento(TipoDocumentoProbatorioRenapoEnum.DOCUMENTO_MIGRATORIO.getValor().longValue());
			} else if (form.getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio().intValue() == DocumentosEnum.CARTA_NATURALIZACION.getId().intValue()) {
				idDocumento = DocumentosEnum.CARTA_NATURALIZACION.getId();
				desDocumento = DocumentosEnum.CARTA_NATURALIZACION.getDescripcion();
				idDocumentoPorTipo = DocumentoPorTipoEnum.CARTA_NATURALIZACION.getId();
				((CURP)documentoProbatorio).setNumTipoDocumento(TipoDocumentoProbatorioRenapoEnum.CARTA_NATURALIZACION.getValor().longValue());
			} else if (form.getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio().intValue() == DocumentosEnum.NUMERO_UNICO_DE_EXTRANJERO.getId().intValue()) {
				idDocumento = DocumentosEnum.NUMERO_UNICO_DE_EXTRANJERO.getId();
				desDocumento = DocumentosEnum.NUMERO_UNICO_DE_EXTRANJERO.getDescripcion();
				idDocumentoPorTipo = DocumentoPorTipoEnum.NUMERO_UNICO_EXTRANJERO.getId();
				((CURP)documentoProbatorio).setNumTipoDocumento(TipoDocumentoProbatorioRenapoEnum.NUMERO_UNICO_DE_EXTRANJERO.getValor().longValue());
			} else if (form.getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio().intValue() == DocumentosEnum.CERTIFICADO_DE_NACIONALIDAD_MEXICANA.getId().intValue()) {
				idDocumento = DocumentosEnum.CERTIFICADO_DE_NACIONALIDAD_MEXICANA.getId();
				desDocumento = DocumentosEnum.CERTIFICADO_DE_NACIONALIDAD_MEXICANA.getDescripcion();
				idDocumentoPorTipo = DocumentoPorTipoEnum.CERTIFICADO_NACIONALIDAD_MEXICANA.getId();
				((CURP)documentoProbatorio).setNumTipoDocumento(TipoDocumentoProbatorioRenapoEnum.CERTIFICADO_DE_NACIONALIDAD_MEXICANA.getValor().longValue());
			} else if (form.getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio().intValue() == DocumentosEnum.OFICIO_SOLICITANTE_DE_REFUGIADO.getId().intValue()) {
				idDocumento = DocumentosEnum.OFICIO_SOLICITANTE_DE_REFUGIADO.getId();
				desDocumento = DocumentosEnum.OFICIO_SOLICITANTE_DE_REFUGIADO.getDescripcion();
				idDocumentoPorTipo = DocumentoPorTipoEnum.OFICIO_SOLICITANTE_REFUGIADO.getId();
				((CURP)documentoProbatorio).setNumTipoDocumento(TipoDocumentoProbatorioRenapoEnum.OFICIO_SOLICITANTE_DE_REFUGIADO.getValor().longValue());
			} else if (form.getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio().intValue() == DocumentosEnum.FORMA_MIGRATORIA_TURISTA.getId().intValue()) {
				idDocumento = DocumentosEnum.FORMA_MIGRATORIA_TURISTA.getId();
				desDocumento = DocumentosEnum.FORMA_MIGRATORIA_TURISTA.getDescripcion();
				idDocumentoPorTipo = DocumentoPorTipoEnum.FORMA_MIGRATORIA_TURISTA.getId();
				((CURP)documentoProbatorio).setNumTipoDocumento(TipoDocumentoProbatorioRenapoEnum.FORMA_MIGRATORIA_TURISTA.getValor().longValue());
			}
		}
						
		documento = new Documento();
		documento.setCveIdDocumento(idDocumento.longValue());
		documento.setDesDocumento(desDocumento);
		
		documentoPorTipo = new DocumentoPorTipo();
		documentoPorTipo.setDocumento(documento);
		documentoPorTipo.setIdDocumentoPorTipo(idDocumentoPorTipo);
		
		documentoProbatorio.setDocumentoPorTipo(documentoPorTipo);
		
		documentoProbatorio.setEstadoAdministracionDocto(EstadoAdministracionEnum.NUEVO);
		
		doctosProbatorios.add(documentoProbatorio);
		
		result.put("docProbatorioFormWrapper", form);
    	
		return result;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/modificar/{indexDocProbatorio}", method = RequestMethod.GET)
	public String modificiarDocumentoProbatorio(final HttpSession session,
			HttpServletRequest request, @PathVariable Integer indexDocProbatorio,
			Model model) {

		List<DocumentoProbatorio> doctosProbatorios = (List<DocumentoProbatorio>) session
				.getAttribute("doctosProbatorios");
		
		DocumentoProbatorio documentoProbatorio = doctosProbatorios.get(indexDocProbatorio);
		DocumentoProbatorioFormWrapper documentoProbatorioFormWrapper = new DocumentoProbatorioFormWrapper();
		
		if(documentoProbatorio instanceof Nacimiento){
			documentoProbatorioFormWrapper.setActaNacimiento((Nacimiento) documentoProbatorio);
		} else if (documentoProbatorio instanceof CURP){
			documentoProbatorioFormWrapper.setDocProbRENAPO((CURP) documentoProbatorio);
		}
		
		TipoDocumentoProbatorio tipoDocumentoProbatorio = new TipoDocumentoProbatorio();
		tipoDocumentoProbatorio.setIdTipoDocumentoProbatorio(documentoProbatorio.getDocumentoPorTipo().getDocumento().getCveIdDocumento().intValue());
		documentoProbatorioFormWrapper.setTipoDocumentoProbatorio(tipoDocumentoProbatorio);
		
		request.setAttribute("indexDocProbatorio", indexDocProbatorio);
		model.addAttribute("documentoProbatorioFormWrapper", documentoProbatorioFormWrapper);

		return "admon.doctosProbatorios.captura";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/confirmar-modificacion/{indexDocProbatorio}", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> confirmarModificacion(final HttpSession session,
			@RequestBody DocumentoProbatorioFormWrapper form, HttpServletResponse response,
			@PathVariable Integer indexDocProbatorio) {

		Map<String, Object> result = new HashMap<String, Object>();
    	Errors errors = new BindException(form, "model");
    	
		List<DocumentoProbatorio> doctosProbatorios = (List<DocumentoProbatorio>) session
				.getAttribute("doctosProbatorios");
				
		//Se valida el medio de contacto capturado		
		new AdministracionDoctosProbatoriosValidator().validate(form, errors);
		if( errors.hasErrors()){
			this.procesaErroresDeCaptura(errors, result, response);
			return result;
		}
		
		DocumentoProbatorio documentoProbatorio = null;
		DocumentoPorTipo documentoPorTipo = null;
		Documento documento = null; 
		Long idDocumento = null;
		String desDocumento = null;
		Long idDocumentoPorTipo = null;
		
		if (form.getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio().intValue() == DocumentosEnum.ACTA_NACIMIENTO.getId().intValue()) {
			Nacimiento nacimiento =  (Nacimiento) doctosProbatorios.get(indexDocProbatorio);

			// Se realiza el merge de la entidad modificada con la original
			nacimiento.setAnio(form.getActaNacimiento().getAnio());
			nacimiento.setTomo(form.getActaNacimiento().getTomo());
			nacimiento.setCrip(form.getActaNacimiento().getCrip());
			nacimiento.setNoFoja(form.getActaNacimiento().getNoFoja());
			nacimiento.setNoLibro(form.getActaNacimiento().getNoLibro());
			nacimiento.setNoActa(form.getActaNacimiento().getNoActa());
			nacimiento.setMunicipio(form.getActaNacimiento().getMunicipio());
					
			documentoProbatorio = nacimiento;
			idDocumento = DocumentosEnum.ACTA_NACIMIENTO.getId();
			desDocumento = DocumentosEnum.ACTA_NACIMIENTO.getDescripcion();
			idDocumentoPorTipo = DocumentoPorTipoEnum.ACTA_NACIMIENTO.getId();
		} else {
			documentoProbatorio = form.getDocProbRENAPO();

			if (form.getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio().intValue() == DocumentosEnum.DOCUMENTO_MIGRATORIO.getId().intValue()) {
				idDocumento = DocumentosEnum.DOCUMENTO_MIGRATORIO.getId();
				desDocumento = DocumentosEnum.DOCUMENTO_MIGRATORIO.getDescripcion();
				idDocumentoPorTipo = DocumentoPorTipoEnum.DOCUMENTO_MIGRATORIO.getId();
			} else if (form.getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio().intValue() == DocumentosEnum.CARTA_NATURALIZACION.getId().intValue()) {
				idDocumento = DocumentosEnum.CARTA_NATURALIZACION.getId();
				desDocumento = DocumentosEnum.CARTA_NATURALIZACION.getDescripcion();
				idDocumentoPorTipo = DocumentoPorTipoEnum.CARTA_NATURALIZACION.getId();
			} else if (form.getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio().intValue() == DocumentosEnum.NUMERO_UNICO_DE_EXTRANJERO.getId().intValue()) {
				idDocumento = DocumentosEnum.NUMERO_UNICO_DE_EXTRANJERO.getId();
				desDocumento = DocumentosEnum.NUMERO_UNICO_DE_EXTRANJERO.getDescripcion();
				idDocumentoPorTipo = DocumentoPorTipoEnum.NUMERO_UNICO_EXTRANJERO.getId();
			} else if (form.getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio().intValue() == DocumentosEnum.CERTIFICADO_DE_NACIONALIDAD_MEXICANA.getId().intValue()) {
				idDocumento = DocumentosEnum.CERTIFICADO_DE_NACIONALIDAD_MEXICANA.getId();
				desDocumento = DocumentosEnum.CERTIFICADO_DE_NACIONALIDAD_MEXICANA.getDescripcion();
				idDocumentoPorTipo = DocumentoPorTipoEnum.CERTIFICADO_NACIONALIDAD_MEXICANA.getId();
			} else if (form.getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio().intValue() == DocumentosEnum.OFICIO_SOLICITANTE_DE_REFUGIADO.getId().intValue()) {
				idDocumento = DocumentosEnum.OFICIO_SOLICITANTE_DE_REFUGIADO.getId();
				desDocumento = DocumentosEnum.OFICIO_SOLICITANTE_DE_REFUGIADO.getDescripcion();
				idDocumentoPorTipo = DocumentoPorTipoEnum.OFICIO_SOLICITANTE_REFUGIADO.getId();
			} else if (form.getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio().intValue() == DocumentosEnum.FORMA_MIGRATORIA_TURISTA.getId().intValue()) {
				idDocumento = DocumentosEnum.FORMA_MIGRATORIA_TURISTA.getId();
				desDocumento = DocumentosEnum.FORMA_MIGRATORIA_TURISTA.getDescripcion();
				idDocumentoPorTipo = DocumentoPorTipoEnum.FORMA_MIGRATORIA_TURISTA.getId();
			}
		}
						
		documento = new Documento();
		documento.setCveIdDocumento(idDocumento.longValue());
		documento.setDesDocumento(desDocumento);
				
		documentoPorTipo = new DocumentoPorTipo();
		documentoPorTipo.setDocumento(documento);
		documentoPorTipo.setIdDocumentoPorTipo(idDocumentoPorTipo);
		
		documentoProbatorio.setDocumentoPorTipo(documentoPorTipo);
		
		documentoProbatorio.setEstadoAdministracionDocto(EstadoAdministracionEnum.MODIFICADO);
		doctosProbatorios.set(indexDocProbatorio.intValue(), documentoProbatorio);
		
		result.put("docProbatorioFormWrapper", form);

		return result;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/eliminar/{indexDocProbatorio}", method = RequestMethod.GET)
	public String eliminarDocumentoProbatorio(final HttpSession session,
			@PathVariable Integer indexDocProbatorio, Model model) {

		List<DocumentoProbatorio> doctosProbatorios = (List<DocumentoProbatorio>) session
				.getAttribute("doctosProbatorios");

		DocumentoProbatorio doctoProbatorio = doctosProbatorios.get(indexDocProbatorio.intValue());
		
		doctoProbatorio.setEstadoAdministracionAnteriorDocto(doctoProbatorio.getEstadoAdministracionDocto());
		doctoProbatorio.setEstadoAdministracionDocto(EstadoAdministracionEnum.ELIMINADO);

		return "initAdmonDocumentosProbatorios";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/obtener-documentos-probatorios", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> obtenerListDoctosProbatoriosAdministrados(
			final HttpSession session) {

		List<DocumentoProbatorio> doctosProbatorios = (List<DocumentoProbatorio>) session
				.getAttribute("doctosProbatorios");
		
		
		/*
		 * La lista de documentos probatorios se pasa a un mapa, para poder
		 * settearlos en la propiedad correspondiente.
		 */
		Map<String, Object> documentos = null;
		
		if(doctosProbatorios != null){
			ListIterator<DocumentoProbatorio> itDocs = doctosProbatorios.listIterator();
			
			if(itDocs != null){
				documentos = new HashMap<String, Object>();
				DocumentoProbatorio documentoProbatorio = null;
				
				while(itDocs.hasNext()){
					documentoProbatorio = itDocs.next();
					if(documentoProbatorio instanceof Nacimiento){										
						documentos.put("ACTA_NACIMIENTO", documentoProbatorio);
					}else if(documentoProbatorio instanceof CURP){
						CURP doctoAux = (CURP) documentoProbatorio;
						
						if ((doctoAux.getNumTipoDocumento() != null && 
								doctoAux.getNumTipoDocumento().intValue() == TipoDocumentoProbatorioRenapoEnum.ACTA_NACIMIENTO.getValor().intValue())
								|| (doctoAux.getDocumentoPorTipo() != null && 
										doctoAux.getDocumentoPorTipo().getIdDocumentoPorTipo().equals(DocumentoPorTipoEnum.ACTA_NACIMIENTO.getId()))) {

							documentos.put("ACTA_NACIMIENTO", documentoProbatorio);
						
						} else if ((doctoAux.getNumTipoDocumento() != null && 
								doctoAux.getNumTipoDocumento().intValue() == TipoDocumentoProbatorioRenapoEnum.DOCUMENTO_MIGRATORIO.getValor().intValue())
								|| (doctoAux.getDocumentoPorTipo() != null && 
										doctoAux.getDocumentoPorTipo().getIdDocumentoPorTipo().equals(DocumentoPorTipoEnum.DOCUMENTO_MIGRATORIO.getId()))) {

							documentos.put("DOCUMENTO_MIGRATORIO", documentoProbatorio);
							
						} else if ((doctoAux.getNumTipoDocumento() != null && 
								doctoAux.getNumTipoDocumento().intValue() == TipoDocumentoProbatorioRenapoEnum.CARTA_NATURALIZACION.getValor().intValue())
								|| (doctoAux.getDocumentoPorTipo() != null && 
										doctoAux.getDocumentoPorTipo().getIdDocumentoPorTipo().equals(DocumentoPorTipoEnum.CARTA_NATURALIZACION.getId()))) {
							
							documentos.put("CARTA_NATURALIZACION", documentoProbatorio);
							
						} else if ((doctoAux.getNumTipoDocumento() != null && 
								doctoAux.getNumTipoDocumento().intValue() == TipoDocumentoProbatorioRenapoEnum.NUMERO_UNICO_DE_EXTRANJERO.getValor().intValue())
								|| (doctoAux.getDocumentoPorTipo() != null && 
										doctoAux.getDocumentoPorTipo().getIdDocumentoPorTipo().equals(DocumentoPorTipoEnum.NUMERO_UNICO_EXTRANJERO.getId()))) {

							documentos.put("NUMERO_UNICO_EXTRANJERO", documentoProbatorio);
							
						} else if ((doctoAux.getNumTipoDocumento() != null && 
								doctoAux.getNumTipoDocumento().intValue() == TipoDocumentoProbatorioRenapoEnum.CERTIFICADO_DE_NACIONALIDAD_MEXICANA.getValor().intValue())
								|| (doctoAux.getDocumentoPorTipo() != null && 
										doctoAux.getDocumentoPorTipo().getIdDocumentoPorTipo().equals(DocumentoPorTipoEnum.CERTIFICADO_NACIONALIDAD_MEXICANA.getId()))) {

							documentos.put("CERTIFICADO_NACIONALIDAD_MEXICANA", documentoProbatorio);
							
						} else if ((doctoAux.getNumTipoDocumento() != null && 
								doctoAux.getNumTipoDocumento().intValue() == TipoDocumentoProbatorioRenapoEnum.OFICIO_SOLICITANTE_DE_REFUGIADO.getValor().intValue())
								|| (doctoAux.getDocumentoPorTipo() != null && 
										doctoAux.getDocumentoPorTipo().getIdDocumentoPorTipo().equals(DocumentoPorTipoEnum.OFICIO_SOLICITANTE_REFUGIADO.getId()))) {
							
							documentos.put("OFICIO_SOLICITANTE_REFUGIADO", documentoProbatorio);
							
						} else if ((doctoAux.getNumTipoDocumento() != null && 
								doctoAux.getNumTipoDocumento().intValue() == TipoDocumentoProbatorioRenapoEnum.FORMA_MIGRATORIA_TURISTA.getValor().intValue())
								|| (doctoAux.getDocumentoPorTipo() != null && 
										doctoAux.getDocumentoPorTipo().getIdDocumentoPorTipo().equals(DocumentoPorTipoEnum.FORMA_MIGRATORIA_TURISTA.getId()))) {

							documentos.put("FORMA_MIGRATORIA_TURISTA", documentoProbatorio);
							
						}
					}
				}
			}
		}

		return documentos;
	}
	
	@RequestMapping(value = "/limpiar-documentos-probatorios", method = RequestMethod.POST)
	public @ResponseBody DocumentoProbatorio limpiarListaDocumentosAdministrados(
			final HttpSession session) {
	
		session.removeAttribute("doctosProbatorios");
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deshacer-eliminar/{indexDocProbatorio}", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> deshacerEliminarDocumentoProbatorio(final HttpSession session,
			@PathVariable Integer indexDocProbatorio, HttpServletResponse response) {

		Map<String, Object> result = new HashMap<String, Object>();
		DocumentoProbatorioFormWrapper form = null;
    	
		List<DocumentoProbatorio> doctosProbatorios = (List<DocumentoProbatorio>) session
				.getAttribute("doctosProbatorios");
		DocumentoProbatorio doctoProbatorio = null;
		
		//Se valida que la persona no cuente con documentos repetidos
		if (doctosProbatorios != null) {
			
			doctoProbatorio = doctosProbatorios.get(indexDocProbatorio.intValue());
			
			for(DocumentoProbatorio documento : doctosProbatorios){
				if (documento.getDocumentoPorTipo().getDocumento().getCveIdDocumento().intValue() == 
						doctoProbatorio.getDocumentoPorTipo().getDocumento().getCveIdDocumento().intValue()
						&& (documento.getEstadoAdministracionDocto() == null || (documento.getEstadoAdministracionDocto() != null && 
							documento.getEstadoAdministracionDocto().getClave() != EstadoAdministracionEnum.ELIMINADO.getClave()))) {
					DocumentoProbatorioException e = new DocumentoProbatorioException(
							"La persona ya cuenta con el documento probatorio que se est\u00E1 restaurando.");
					log.error(e);
					form = new DocumentoProbatorioFormWrapper();
					form.setErrorFormGeneral(e.getMessage());
					this.procesarErrorDeNegocio(e, result, response);
					
					return result;
				}
			}
		} else {
			//Se ignora la validacion si no se selecciono ningun documento
			return result;
		}

		doctoProbatorio.setEstadoAdministracionDocto(doctoProbatorio.getEstadoAdministracionAnteriorDocto());

		return result;
	}
}