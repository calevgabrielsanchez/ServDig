package mx.gob.imss.ctirss.delta.derechohabientes.web.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.RegistroDerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Parentesco;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Nacimiento;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRegistroDerechohabiente;

@Component
public class ProcesosAsincronosGrupoFamiliar extends AbstractController {

	@Autowired
	private RegistroDerechohabienteServiceRemote registroDerechohabienteServiceRemote;
	@Autowired
	private GuardaMediosContactoAsinc guardaMediosContactoAsinc;
	@Autowired
	private GuardaDocumentosAsincrono guardaDocumentosAsincrono;
	
	
	//@Async("asyncExecutor")
	@Transactional
	public void guardaDocsMediosMarcaParentescoSimilarRegistro(TramiteRegistroDerechohabiente tramiteXml, Boolean nuevaPersona, HttpSession session) {
		log.debug("Entro al metodo de procesos asincronos para el registro" + new Date());
		try {
			
			if(StringUtils.isNotBlank(tramiteXml.getFisica().getCurp())) {
				Nacimiento acta = tramiteXml.getFisica().getActaNacimiento();
				if(acta != null && acta.getDocumentoPorTipo() != null  && acta.getDocumentoPorTipo().getIdDocumentoPorTipo() != null) {
					acta.setFechaSuceso(tramiteXml.getFisica().getFechaNacimiento());
					guardaDocumentosAsincrono.guardaActaDeNacimientoSinConsultaARenapo(tramiteXml.getFisica(),tramiteXml.getTramiteId());
					
				}else{
					guardaDocumentosAsincrono.guardaActaDeNacimiento(tramiteXml.getFisica(),tramiteXml.getTramiteId());
				}
				//guardamos el acta de nacimiento
			}
			guardaDocumentosAsincrono.salvaDocumentosProbatoriosSincrono(session, tramiteXml.getTramiteId(), tramiteXml.getFisica().getIdPersona());

			//se actualizan medios de contacto, solo si no es nueva persona ya que cuando la persona
			//no existe el mismo proceso de registro de persona guarda los medios de contacto
			if(!nuevaPersona) {
				guardaMediosContactoAsinc.guardaMediosContactoPersona(tramiteXml.getFisica());
			}
		} catch(Exception e) {
			log.error("ocurrio un error en el guardado asincrono", e);
		}
		this.setearBanderaDeParentescoSimilar(tramiteXml.getFisica(), tramiteXml.getParentesco());
		log.debug("Sali del metodo de procesos asincronos para el registro" + new Date());
	}
	
	//@Async("asyncExecutor")
	@Transactional
	public void guardaDocsMediosCorreccion(TramiteCorreccionDerechohabiente correccion, TramiteCorreccionDerechohabiente resultado,HttpSession session) {
		log.debug("Entro a los procesos asincronos de correccion");
		try {
			//Guarda acta de renapo si es hijo o conyugue
			if(correccion.getParentesco().getIdParentesco() == ParentescoEnum.HIJOS.getId() || 
					correccion.getParentesco().getIdParentesco() == ParentescoEnum.CONYUGE.getId()){
				//guardaremos el acta siempre y cuando la curp sea valida
				if(correccion.getCurpCap() != null && !correccion.getCurpCap().equals("") && !correccion.getCurpCap().equals("000000000000000000")
						&& correccion.getCurpCap().length() == 18){
					Fisica fisica = new Fisica();
					fisica.setIdPersona(correccion.getIdPersona());
					fisica.setCurp(correccion.getCurpCap());
					guardaDocumentosAsincrono.guardaActaDeNacimiento(fisica, correccion.getTramiteId());
				}
			}
			
			//Se guardan los documentos probatorios
			//this.salvaDocumentos(session, correccion.getTramiteId(),correccion.getIdPersona());//se comenta este ya que ahora se hace asincrono
			guardaDocumentosAsincrono.salvaDocumentosProbatoriosSincrono(session, correccion.getTramiteId(), correccion.getIdPersona());
			
			//se guardan los medios de contacto asincronamente
			guardaMediosContactoAsinc.guardarMediosContactoCorreccion(resultado);
		} catch (Exception e) {
			log.error("Ocurrio un error al realizar los procesos asincronos de correccion",e);
		}
	}
	
	/*@Async
	@Transactional*/
	public void setearBanderaDeParentescoSimilar(Fisica fisica, Parentesco parentesco) {
		log.debug("Se guardara asincronamente la marca de parentesco de grupo ");
		try {
			registroDerechohabienteServiceRemote.setearBanderaDeParentescoSimilar(fisica, parentesco);
		} catch (Exception e) {
			log.error("Ocurrio un error al buscar a la persoan con parenetsco similar en otro grupo familiar");
		}
	}
	
}
