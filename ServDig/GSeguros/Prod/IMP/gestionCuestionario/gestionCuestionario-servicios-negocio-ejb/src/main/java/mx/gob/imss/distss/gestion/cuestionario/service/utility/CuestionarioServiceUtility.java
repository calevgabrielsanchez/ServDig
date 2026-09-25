package mx.gob.imss.distss.gestion.cuestionario.service.utility;

import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.persistence.DicCuestionario;
import mx.gob.imss.ctirss.delta.persistence.DicDependenciaOpcPregunta;
import mx.gob.imss.ctirss.delta.persistence.DicOpcionPregunta;
import mx.gob.imss.ctirss.delta.persistence.DicPregunta;
import mx.gob.imss.ctirss.delta.persistence.DicSeccion;
import mx.gob.imss.distss.gestion.cuestionario.modelo.Cuestionario;
import mx.gob.imss.distss.gestion.cuestionario.modelo.Opcion;
import mx.gob.imss.distss.gestion.cuestionario.modelo.OpcionComparator;
import mx.gob.imss.distss.gestion.cuestionario.modelo.Pregunta;
import mx.gob.imss.distss.gestion.cuestionario.modelo.PreguntaComparator;
import mx.gob.imss.distss.gestion.cuestionario.modelo.Seccion;
import mx.gob.imss.distss.gestion.cuestionario.modelo.SeccionComparator;
import mx.gob.imss.distss.gestion.cuestionario.modelo.TipoCuestionario;
import mx.gob.imss.distss.gestion.cuestionario.modelo.TipoRespuesta;
import mx.gob.imss.distss.gestion.cuestionario.service.entity.CuestionarioServiceEntityLocal;

import org.springframework.util.CollectionUtils;

@Stateless(mappedName = "cuestionarioServiceUtility")
public class CuestionarioServiceUtility extends AbstractServiceUtility
		implements CuestionarioServiceUtilityLocal {
	
	@EJB
	private CuestionarioServiceEntityLocal cuestionarioServiceEntity;
	
	@Override
	public Cuestionario transformar(DicCuestionario entity){
		
		List<Integer> lstPregDependientes = this.cuestionarioServiceEntity
				.obtenerIdPreguntasDependientesCuestionario(Long.valueOf(
						entity.getCveIdCuestionario()).intValue());
		
		Cuestionario model = new Cuestionario();
		
		TipoCuestionario tipoCuestionario = new TipoCuestionario();
		tipoCuestionario.setClave(Long.valueOf(entity.getDicTipoCuestionario().getCveIdTipoCuestionario()).intValue());
		tipoCuestionario.setDescripcion(entity.getDicTipoCuestionario().getDesTipoCuestionario());
		
		model.setClave(Long.valueOf(entity.getCveIdCuestionario()).intValue());
		model.setTitulo(entity.getDesTitulo());
		model.setDescripcion(entity.getDesCuestionario());
		model.setTipoCuestionario(tipoCuestionario);
		
		Seccion seccion = null;
		Pregunta pregunta = null;
		
		for (DicSeccion dicSeccion : entity.getDicSecciones()) {
			seccion = new Seccion();
			
			seccion.setClave(Long.valueOf(dicSeccion.getCveIdSeccion()).intValue());
			seccion.setTitulo(dicSeccion.getDesTitulo());
			seccion.setDescripcion(dicSeccion.getDesSeccion());
			
			for (DicPregunta dicPregunta : dicSeccion.getDicPreguntas()) {
				
				if (CollectionUtils.isEmpty(lstPregDependientes) 
						|| !lstPregDependientes.contains(Integer.valueOf(Long.valueOf(
								dicPregunta.getCveIdPregunta()).intValue()))) {
					pregunta = transformar(dicPregunta);
					seccion.getPreguntas().add(pregunta);
				}
			}
			
			Collections.sort(seccion.getPreguntas(), new PreguntaComparator());
			
			model.getSecciones().add(seccion);
		}
		
		Collections.sort(model.getSecciones(), new SeccionComparator());
		
		return model;
	}

	@Override
	public DicCuestionario transformar(Cuestionario model) {

		return null;
	}
	
	private Pregunta transformar (DicPregunta entity) {
		
		Pregunta pregunta = new Pregunta();
		TipoRespuesta tipoRespuesta = new TipoRespuesta();
		Opcion opcion = null;
		
		tipoRespuesta.setClave(Long.valueOf(entity.getDicTipoRespuesta().getCveIdTipoRespuesta()).intValue());
		tipoRespuesta.setDescripcion(entity.getDicTipoRespuesta().getDesTipoRespuesta());
		tipoRespuesta.setElemento(entity.getDicTipoRespuesta().getDesElementoHtml());
		
		pregunta.setClave(Long.valueOf(entity.getCveIdPregunta()).intValue());
		pregunta.setDescripcion(entity.getDesPregunta());
		pregunta.setInciso(entity.getNumPregunta());
		pregunta.setObligatoria(getBooleanFromInt(entity.getIndObligatoria()));
		pregunta.setTipoRespuesta(tipoRespuesta);
		
		for (DicOpcionPregunta dicOpcion : entity.getDicOpcionPreguntas()) {
			opcion = transformar(dicOpcion);
			
			pregunta.getOpciones().add(opcion);
		}
		
		Collections.sort(pregunta.getOpciones(), new OpcionComparator());
		
		return pregunta;
	}
	
	private Opcion transformar (DicOpcionPregunta entity) {
		
		Opcion opcion = new Opcion();
		Pregunta preguntaDep = null;
		
		opcion.setClave(Long.valueOf(entity.getCveIdOpcionPregunta()).intValue());
		opcion.setDescripcion(entity.getDesOpcPregunta());
		opcion.setValor(entity.getNumValor());
		opcion.setHabilitarDependencia(getBooleanFromInt(entity.getIndDependencias()));
		
		for (DicDependenciaOpcPregunta dicPreguntaDep : entity.getDicDependenciaOpcPreguntas()) {
			preguntaDep = transformar(dicPreguntaDep.getDicPregunta());
			opcion.getDependencias().add(preguntaDep);
		}
		
		return opcion;
	}
	
	private boolean getBooleanFromInt(int indicador) {
		
		boolean bandera = false;
		
		if (indicador == 1) {
			bandera = true;
		}
		
		return bandera;
	}
}
