package mx.gob.imss.ctirss.delta.derechohabientes.web.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.RegistroDerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.RequisitosMinimosServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.select.bean.SelectBean;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.enums.EstadoCivilEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.enums.RazonRegistroEnum;
import mx.gob.imss.ctirss.delta.model.enums.SexoEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.util.Constants;
import mx.gob.imss.ctirss.delta.service.interfaces.ISelectService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Juan Manuel Lopez Lozano
 * @since  08/10/2011
 *
 */
@Controller
@RequestMapping(value="/combo")
public class SelectController extends AbstractController {

	
	@Autowired
	private ISelectService componentComboService;	
	@Autowired
	private RegistroDerechohabienteServiceRemote registroDerechohabienteService;
	@Autowired
	private RequisitosMinimosServiceRemote requisitosMinimosServiceRemote;
	
	/**
	 * 
	 * @param clazEntityName : NOmbre de la clase Entity a filtrar
	 * @return
	 */
	@RequestMapping(value="/simple", method=RequestMethod.GET )
    public @ResponseBody List<SelectBean> getSelectOptions(@RequestParam String clazEntityName,@RequestParam boolean mostrarSoloActivos,
			@RequestParam(required = false) String campoVigencia){
			
		this.log.debug("getSelectOptions ----");
		
	    List<SelectBean> selectOpts = null;
		try {			
			if (mostrarSoloActivos && StringUtils.isBlank(campoVigencia)) {
				selectOpts = this.componentComboService
						.getActiveOptions(clazEntityName);
			} else if (mostrarSoloActivos
					&& StringUtils.isNotBlank(campoVigencia)) {
				selectOpts = this.componentComboService.getActiveOptions(
						clazEntityName, campoVigencia);
			} else {
				selectOpts = this.componentComboService
						.getOptions(clazEntityName);
			}		
		} catch (Exception e) {
			log.error("Error inesperado", e);
		}
		return selectOpts;
    }
	
	@RequestMapping(value="/special", method=RequestMethod.GET )
    public @ResponseBody List<SelectBean> getSelectOptionSpecial(@RequestParam String clazEntityName, String regEspecial, 
    		@RequestParam String valorCombo, HttpSession session){
						    
		CabezaGrupoFamiliar cabeza = (CabezaGrupoFamiliar) session.getAttribute(Constants.CABEZA_GRUPO_FAM_SESSION);
		boolean isPensionado = false;
		if(cabeza != null) {
			isPensionado = cabeza.getCalidadParentesco().getIdParentesco().equals(ParentescoEnum.PENSIONADO.getId());
		}
		
	    List<SelectBean> selectOpts = null;	    
		try {	
			if(clazEntityName.equals("mx.gob.imss.ctirss.delta.persistence.DicRazonRegistro2")){									
				clazEntityName = "mx.gob.imss.ctirss.delta.persistence.DicRazonRegistro";		
			}	
				
			if(clazEntityName.equals("mx.gob.imss.ctirss.delta.persistence.DgCatEstado")){
				selectOpts = this.componentComboService.getOptionsEstado(clazEntityName);	
			}else{
				selectOpts = this.componentComboService.getOptions(clazEntityName);
			}
			if(clazEntityName.equals("mx.gob.imss.ctirss.delta.persistence.DicRazonRegistro")){																
				Iterator<SelectBean> it =  selectOpts.iterator();
				SelectBean hasta_16 = null;
				SelectBean hasta_25 = null;
				SelectBean recien_nacido = null;
				SelectBean mayor_a_25 = null;
				
				while(it.hasNext()){ 
					SelectBean o = it.next();
					if(o.getId().equals(String.valueOf(RazonRegistroEnum.HASTA_16.getId()))) 
						hasta_16 = o;						
					 if(o.getId().equals(String.valueOf(RazonRegistroEnum.HASTA_25.getId())))
						 hasta_25 = o;
					 if(o.getId().equals(String.valueOf(RazonRegistroEnum.MAYOR_A_25.getId())))
						 mayor_a_25 = o;
					 if(o.getId().equals(String.valueOf(RazonRegistroEnum.RECIEN_NACIDO.getId())))
						 recien_nacido = o;	
					 	
				}					
				selectOpts.removeAll(selectOpts);						
				selectOpts.add(hasta_16);
				selectOpts.add(hasta_25);
				selectOpts.add(mayor_a_25);
				selectOpts.add(recien_nacido);						
			}else
			if((regEspecial.equals(Constants.SIN_ASEGURADO_EXTERNO) || regEspecial.equals(Constants.SIN_ASEGURADO_INTERNO)) 
					&& clazEntityName.equals("mx.gob.imss.ctirss.delta.persistence.DicCalidadParentesco")){				
				selectOpts.removeAll(selectOpts);					
				selectOpts.add(llenaCombo(String.valueOf(ParentescoEnum.ASEGURADO.getId()),ParentescoEnum.ASEGURADO.toString()));				
			}else
			if((regEspecial.equals(Constants.SIN_PENSIONADO_EXTERNO) || regEspecial.equals(Constants.SIN_PENSIONADO_INTERNO)) 
					&& clazEntityName.equals("mx.gob.imss.ctirss.delta.persistence.DicCalidadParentesco")){				
				selectOpts.removeAll(selectOpts);							
				selectOpts.add(llenaCombo(String.valueOf(ParentescoEnum.PENSIONADO.getId()),ParentescoEnum.PENSIONADO.toString()));					
			}else				
			if((regEspecial.equals(Constants.CON_ASEGURADO_EXTERNO) || regEspecial.equals(Constants.CON_ASEGURADO_INTERNO))
					&& clazEntityName.equals("mx.gob.imss.ctirss.delta.persistence.DicCalidadParentesco")){
				SujetoObligado so = (SujetoObligado) session.getAttribute(Constants.PATRON_SUJETO);
				Modalidad mod = so.getModalidad();
				if(isPensionado) {
					mod = new Modalidad();
					mod.setIdModalidad(1L);
				}
				
				AsignacionNSS an = (AsignacionNSS) session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
				List<SelectBean> selectOpts2 = new ArrayList<SelectBean>();
				selectOpts2.addAll(selectOpts);
			
				for(SelectBean sb : selectOpts2){
					if(sb.getId().equals(String.valueOf(ParentescoEnum.HIJOS.getId()))){
						if(!registroDerechohabienteService.maximoParentesco(an.getIdAsignacionNSS(), ParentescoEnum.HIJOS.getId())){						
							selectOpts.remove(sb);
						}else
						if(!registroDerechohabienteService.modalidadParentesco(mod.getIdModalidad(), ParentescoEnum.HIJOS.getId())){
							selectOpts.remove(sb);
						}
					}else
					if(sb.getId().equals(String.valueOf(ParentescoEnum.PADRES.getId()))){		
						boolean padre = true;
						boolean madre = true;
						
						if(!registroDerechohabienteService.maximoParentesco(an.getIdAsignacionNSS(), ParentescoEnum.PADRES.getId())){
							selectOpts.remove(sb);
						}else
						if(!registroDerechohabienteService.modalidadParentesco(mod.getIdModalidad(), ParentescoEnum.PADRES.getId())){
							selectOpts.remove(sb);
						}else{
							selectOpts.remove(sb);
							List<GrupoFamiliar> integrantes = null;							
							integrantes = registroDerechohabienteService.integrantesParentesco(an.getIdAsignacionNSS(), ParentescoEnum.PADRES.getId());
							
							if(integrantes.size() > 0){
								for(GrupoFamiliar gf : integrantes){
									if(gf.getDerechohabiente().getSexo().getIdSexo().longValue() == SexoEnum.HOMBRE.getId()){
										padre = false;
									}
									if(gf.getDerechohabiente().getSexo().getIdSexo().longValue() == SexoEnum.MUJER.getId()){
										madre = false;
									}
								}
							}
							if(padre)
								selectOpts.add(llenaCombo(String.valueOf(ParentescoEnum.PADRES.getId()),"PADRE"));
							if(madre)
								selectOpts.add(llenaCombo(String.valueOf(ParentescoEnum.MADRE.getId()),"MADRE"));
						}
						
					}else
					if(sb.getId().equals(String.valueOf(ParentescoEnum.CONCUBINARIO.getId()))){
						if(!registroDerechohabienteService.maximoParentesco(an.getIdAsignacionNSS(), ParentescoEnum.CONCUBINARIO.getId())){
							selectOpts.remove(sb);
						}else
						if(!registroDerechohabienteService.modalidadParentesco(mod.getIdModalidad(), ParentescoEnum.CONCUBINARIO.getId())){
							selectOpts.remove(sb);
						}					
					}else
					if(sb.getId().equals(String.valueOf(ParentescoEnum.CONYUGE.getId()))){
						if(!registroDerechohabienteService.maximoParentesco(an.getIdAsignacionNSS(), ParentescoEnum.CONYUGE.getId())){
							selectOpts.remove(sb);
						}else
						if(!registroDerechohabienteService.modalidadParentesco(mod.getIdModalidad(), ParentescoEnum.CONYUGE.getId())){
							selectOpts.remove(sb);
						}
					}else
					if(sb.getId().equals(String.valueOf(ParentescoEnum.ASEGURADO.getId()))){
						selectOpts.remove(sb);
					}else
					if(sb.getId().equals(String.valueOf(ParentescoEnum.PENSIONADO.getId()))){
						selectOpts.remove(sb);
					}
				}						
			}else				
				if(regEspecial.equals(Constants.CON_ASEGURADO_PARENTESCO) && clazEntityName.equals("mx.gob.imss.ctirss.delta.persistence.DicCalidadParentesco")){
					
					log.debug("Entro al combo de parentesco");
					
					SujetoObligado so = (SujetoObligado) session.getAttribute(Constants.PATRON_SUJETO);
					Modalidad mod = so.getModalidad();
					if(isPensionado) {
						mod = new Modalidad();
						mod.setIdModalidad(1L);
					}
					AsignacionNSS an = (AsignacionNSS) session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
					List<SelectBean> selectOpts2 = new ArrayList<SelectBean>();
					selectOpts2.addAll(selectOpts);
					for(SelectBean sb : selectOpts2){
						if(sb.getId().equals(String.valueOf(ParentescoEnum.HIJOS.getId()))){
							if(!registroDerechohabienteService.maximoParentesco(an.getIdAsignacionNSS(), ParentescoEnum.HIJOS.getId())){						
								selectOpts.remove(sb);
							}else
							if(!this.validarModalidadParentesco(session, TipoTramiteEnum.REGISTRO_HIJOS.getCodigo().longValue())){
								selectOpts.remove(sb);
							}
						}else
						if(sb.getId().equals(String.valueOf(ParentescoEnum.PADRES.getId()))){		
							
							if(!registroDerechohabienteService.maximoParentesco(an.getIdAsignacionNSS(), ParentescoEnum.PADRES.getId()) && !valorCombo.equals(""+ParentescoEnum.PADRES.getId()) && !valorCombo.equals(""+ParentescoEnum.MADRE.getId())){
								selectOpts.remove(sb);
							}else if(!this.validarModalidadParentesco(session, TipoTramiteEnum.REGISTRO_PADRES.getCodigo().longValue()) && !valorCombo.equals(""+ParentescoEnum.PADRES.getId()) && !valorCombo.equals(""+ParentescoEnum.MADRE.getId())){
								selectOpts.remove(sb);
							}else{
								
								selectOpts.remove(sb);
								
								// --------------------------------------------------------
								// Ya puede tener dos padres del mismo sexo
								// --------------------------------------------------------
								selectOpts.add(llenaCombo(String.valueOf(ParentescoEnum.PADRES.getId()), ParentescoEnum.PADRES.toString()));
								
								
							}
							
						}else
						if(sb.getId().equals(String.valueOf(ParentescoEnum.CONCUBINARIO.getId()))){
							if(!registroDerechohabienteService.maximoParentesco(an.getIdAsignacionNSS(), ParentescoEnum.CONCUBINARIO.getId())
									&& !valorCombo.equals(""+ParentescoEnum.CONCUBINARIO.getId())){
								selectOpts.remove(sb);
							}else
							if(!this.validarModalidadParentesco(session, TipoTramiteEnum.REGISTRO_CONCUBINA_RIO.getCodigo().longValue())
									&& !valorCombo.equals(""+ParentescoEnum.CONCUBINARIO.getId())){
								selectOpts.remove(sb);
							}					
						}else
						if(sb.getId().equals(String.valueOf(ParentescoEnum.CONYUGE.getId()))){
							if(!registroDerechohabienteService.maximoParentesco(an.getIdAsignacionNSS(), ParentescoEnum.CONYUGE.getId())
									&& !valorCombo.equals(""+ParentescoEnum.CONYUGE.getId())){
								selectOpts.remove(sb);
							}else
							if(!this.validarModalidadParentesco(session, TipoTramiteEnum.REGISTRO_CONYUGE.getCodigo().longValue())
									&& !valorCombo.equals(""+ParentescoEnum.CONYUGE.getId())){
								selectOpts.remove(sb);
							}
						}else
						if(sb.getId().equals(String.valueOf(ParentescoEnum.ASEGURADO.getId()))){
							selectOpts.remove(sb);
						}else
						if(sb.getId().equals(String.valueOf(ParentescoEnum.PENSIONADO.getId()))){
							selectOpts.remove(sb);
						}
					}						
				}			
			
		} catch (Exception e) {			
			log.error("Error inesperado", e);
		}
		return selectOpts;
    }
	
	private SelectBean llenaCombo(String id, String descripcion){
		SelectBean opcion = new SelectBean();
		opcion.setId(id);
		opcion.setDescripcion(descripcion);
		return opcion;
	}
	/**
	 * 
	 * @param clazEntityName : NOmbre de la clase Entity a filtrar
	 * @param entityParentName : NOmbre del atributo del FK padre
	 * @param valueParent : Valor del FK padre
	 * @return
	 */
	
	@RequestMapping(value="/dependiente", method=RequestMethod.GET )
    public @ResponseBody List<SelectBean> getSelectOptions(	@RequestParam String clazEntityName, 
    														@RequestParam String entityParentName, 
    														@RequestParam String valueParent,
    														@RequestParam boolean mostrarSoloActivos,
    														@RequestParam(required = false) String campoVigencia){
	    List<SelectBean> selectOpts = null;
		try {
			if (mostrarSoloActivos && StringUtils.isBlank(campoVigencia)) {
				selectOpts = this.componentComboService.getActiveOptions(
						clazEntityName, entityParentName, valueParent);
			} else if (mostrarSoloActivos
					&& StringUtils.isNotBlank(campoVigencia)) {
				selectOpts = this.componentComboService.getActiveOptions(
						clazEntityName, entityParentName, valueParent,
						campoVigencia);
			} else {
				selectOpts = this.componentComboService.getOptions(
						clazEntityName, entityParentName, valueParent);
			}
		} catch (Exception e) {
			log.error("Error inesperado", e);
		}
		return selectOpts;
		
    }	
	
	/**
	 * 
	 * @param clazEntityName : NOmbre de la clase Entity a filtrar
	 * @return
	 */
	@RequestMapping(value="/dep2", method=RequestMethod.GET )
    public @ResponseBody List<SelectBean> getSelectOptions2(@RequestParam String clazEntityName,
    														@RequestParam String valueParent,    														
    														@RequestParam String regEspecial,
    														@RequestParam String actor){				
		this.log.debug("getSelectOptions ----");
		
	    List<SelectBean> selectOpts = null;	    
//	    List<SelectBean> nuevoSelectOpts = new ArrayList<SelectBean>();
		try {
			
			selectOpts = this.componentComboService.getOptions(clazEntityName);
			
			if(clazEntityName.equals("mx.gob.imss.ctirss.delta.persistence.DicRazonRegistro")){
				Iterator<SelectBean> it =  selectOpts.iterator();
				if(valueParent.equals(String.valueOf(ParentescoEnum.HIJOS.getId()))){																	
					SelectBean laudo = null;
					SelectBean acuerdo = null;
					SelectBean amparo = null;					
					
					while(it.hasNext()){ 
						SelectBean o = it.next();
						if(o.getId().equals(String.valueOf(RazonRegistroEnum.POR_LAUDO.getId()))) 
							laudo = o;						
						 if(o.getId().equals(String.valueOf(RazonRegistroEnum.POR_ACUERDO.getId())))
							 acuerdo = o;	
						 if(o.getId().equals(String.valueOf(RazonRegistroEnum.POR_AMPARO.getId())))
							 amparo = o;							  
					}
					
					selectOpts.removeAll(selectOpts);
					selectOpts.add(laudo);
					selectOpts.add(acuerdo);
					selectOpts.add(amparo);	
				}else{					
					SelectBean normal = null;
					SelectBean laudo = null;
					SelectBean acuerdo = null;
					SelectBean amparo = null;
					
					while(it.hasNext()){ 
						SelectBean o = it.next();
						if(o.getId().equals(String.valueOf(RazonRegistroEnum.NORMAL.getId()))) 
							normal = o;						
						 if(o.getId().equals(String.valueOf(RazonRegistroEnum.POR_LAUDO.getId())))
							 laudo = o;	
						 if(o.getId().equals(String.valueOf(RazonRegistroEnum.POR_ACUERDO.getId())))
							 acuerdo = o;	
						  if(o.getId().equals(String.valueOf(RazonRegistroEnum.POR_AMPARO.getId())))
							 amparo = o;
					}
					
					selectOpts.removeAll(selectOpts);
					if(Integer.parseInt(regEspecial) > Integer.parseInt(Constants.CON_ASEGURADO_INTERNO)							
							|| actor.equals(Constants.EXTERNO)){
						selectOpts.add(normal);
					}else{
						selectOpts.add(normal);
						selectOpts.add(laudo);
						selectOpts.add(acuerdo);
						selectOpts.add(amparo);
					}	
				}				
			}else{
				if(clazEntityName.equals("mx.gob.imss.ctirss.delta.persistence.DicEstadoCivil")){
					estadoCivilIdent(selectOpts, valueParent);
				}
			}
		} catch (Exception e) {
			log.error("Error inesperado", e);
		}
		return selectOpts;
    }
	
	private void estadoCivilIdent(List<SelectBean> selectOpts, String valueParent){
		Iterator<SelectBean> it =  selectOpts.iterator();
		
		SelectBean soltero = null;
		SelectBean casado = null;		
		SelectBean concubinato = null;
		
		while(it.hasNext()){ 
			SelectBean o = it.next();
			if(o.getId().equals(String.valueOf(EstadoCivilEnum.SOLTERO.getId())))
				soltero = o;
			if(o.getId().equals(String.valueOf(EstadoCivilEnum.CASADO.getId())))
				casado = o;						
			if(o.getId().equals(String.valueOf(EstadoCivilEnum.CONCUBINATO.getId())))
				concubinato = o;
		}
		if(valueParent.equals(String.valueOf(ParentescoEnum.HIJOS.getId()))){
			selectOpts.removeAll(selectOpts);
			selectOpts.add(soltero);
		}else
		if(valueParent.equals(String.valueOf(ParentescoEnum.CONCUBINARIO.getId()))){
			selectOpts.removeAll(selectOpts);
			selectOpts.add(concubinato);
		}else
		if(valueParent.equals(String.valueOf(ParentescoEnum.CONYUGE.getId()))){
			selectOpts.removeAll(selectOpts);
			selectOpts.add(casado);
		}		
	}
	
	private boolean validarModalidadParentesco(HttpSession session, Long idTipoTramite){
		CabezaGrupoFamiliar cabeza = (CabezaGrupoFamiliar) session.getAttribute(Constants.CABEZA_GRUPO_FAM_SESSION);
		List<Long> idsModalidadesActivas = this.obtenerModalidadesPatrones(session);
		try {
			Map<String, Object> result = requisitosMinimosServiceRemote.tramitePermitidoParaAseguradoPensionado(cabeza, idTipoTramite, false, idsModalidadesActivas);
			return (Boolean) result.get("correcto");
		} catch (DerechohabientesBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	private List<Long> obtenerModalidadesPatrones(HttpSession session) {
		//obtenemos los patrones de la session
		List<SujetoObligado> sujetos = (List<SujetoObligado>) session.getAttribute("patrones");
		//obtenemos la cabeza de grupo familiar de la session
		CabezaGrupoFamiliar cabeza = (CabezaGrupoFamiliar) session.getAttribute(Constants.CABEZA_GRUPO_FAM_SESSION);
		//creamos la lista de modalidades
		List<Long> modalidades = new ArrayList<Long>();
		
		//si los patrones no son nulos, añadiremos sus modalidades
		if(sujetos != null && !sujetos.isEmpty()) {
			for(SujetoObligado sujeto: sujetos) {
				modalidades.add(sujeto.getModalidad().getIdModalidad());
			}
		} else {
			SujetoObligado sujetoO = cabeza.getPatronSujetoObligado();
			//si los patrones son nulos o vacios verificamos si la cabeza de grupo familiar tiene patron
			if(sujetoO != null && sujetoO.getModalidad() != null && sujetoO.getModalidad().getIdModalidad() != null) {
				//si tiene patron anadimos la modalidad dle patron del ultimo movimiento
				modalidades.add(cabeza.getPatronSujetoObligado().getModalidad().getIdModalidad());
			}
		}
		
		return modalidades;
	}
	
}
