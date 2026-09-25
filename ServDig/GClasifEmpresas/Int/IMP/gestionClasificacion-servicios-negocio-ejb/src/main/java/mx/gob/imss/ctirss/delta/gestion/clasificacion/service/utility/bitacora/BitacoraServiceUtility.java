/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: BitacoraServiceUtility.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.bitacora
 *  @Fecha: 04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.bitacora;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.clasificacion.actividad.economica.ActividadEcServiceRemote;
import mx.gob.imss.ctirss.delta.model.clasificacion.AnalisisClasificacionEmpresas;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisModel;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.persistence.DicEstatusAnalisisCe;
import mx.gob.imss.ctirss.delta.persistence.DicFraccion;
import mx.gob.imss.ctirss.delta.persistence.DicSubdelegacion;
import mx.gob.imss.ctirss.delta.persistence.DitAnalisisCe;
import mx.gob.imss.ctirss.delta.persistence.DitHistEstatusAnalisis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class BitacoraServiceUtility extends AbstractServiceUtility 
		implements BitacoraServiceUtilityLocal {

    private static final Logger log = LoggerFactory.getLogger(BitacoraServiceUtility.class);

    @EJB
    private ActividadEcServiceRemote clasificacionActividadEconomicaService;

	@Override
	public DitHistEstatusAnalisis convertirModelToEntity(EstatusAnalisisModel model) 
			throws Exception {
		DitHistEstatusAnalisis entity = new DitHistEstatusAnalisis();
		
		try {			
			if (null == model.getCveUsuario()) {
//				entity.setDitUsuario(null);
				entity.setCveIdUsuarioSso(null);
			} else {
//				final DitUsuario ditUsuario = new DitUsuario();
//				ditUsuario.setCveIdUsuario(Long.valueOf(model.getCveUsuario()));
//				entity.setDitUsuario(ditUsuario);
				entity.setCveIdUsuarioSso(model.getCveUsuario());
				
			}
			DicEstatusAnalisisCe dicEstatusAnalisisCe = 
				new DicEstatusAnalisisCe();
			dicEstatusAnalisisCe.setCveIdEstatusAnalisis(
				model.getCveIdEstatus().longValue());
			entity.setDicEstatusAnalisisCe(dicEstatusAnalisisCe);
			
			DitAnalisisCe ditAnalisisCe = new DitAnalisisCe();
			ditAnalisisCe.setCveIdAnalisis(
				model.getCveIdAnalisis().longValue());
			entity.setDitAnalisisCe(ditAnalisisCe);	
			entity.setDesComentario((model.getComentario() == null) ? null
					: model.getComentario().toUpperCase());
			
			DicSubdelegacion dicSubdelegacion = new DicSubdelegacion();
			dicSubdelegacion.setCveIdSubdelegacion(model.getCveIdSubdelegacion().longValue());
			entity.setDicSubdelegacion(dicSubdelegacion);
			
			if(model.getFraccionActual() != null && model.getFraccionActual().getId()!=null && model.getFraccionActual().getPrimaSRT()!=null){
				DicFraccion dicFraccionAct = new DicFraccion();
				dicFraccionAct.setCveIdFraccion(model.getFraccionActual().getId());
				entity.setDicFraccionDec(dicFraccionAct);
				entity.setNumPrimaDec(model.getFraccionActual().getPrimaSRT());
			}
			
			if(model.getFraccionPropuesta() != null && model.getFraccionPropuesta().getId() != null && model.getFraccionPropuesta().getPrimaSRT()!=null){
				DicFraccion dicFraccionPro = new DicFraccion();
				dicFraccionPro.setCveIdFraccion(model.getFraccionPropuesta().getId());
				entity.setDicFraccionPro(dicFraccionPro);
				

				
				entity.setNumPrimaPro(model.getFraccionPropuesta().getPrimaSRT());
			}

			if(model.getFraccionAnterior() != null && model.getFraccionAnterior().getId() != null && model.getFraccionAnterior().getPrimaSRT()!=null){
				DicFraccion dicFraccionAnt = new DicFraccion();
				dicFraccionAnt.setCveIdFraccion(model.getFraccionAnterior().getId());
				entity.setDicFraccionAnt(dicFraccionAnt);
				entity.setNumPrimaAnt(model.getFraccionAnterior().getPrimaSRT());
			}

		}  catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return entity;
	}

	public EstatusAnalisisModel armaBitacora(AnalisisClasificacionEmpresas model, 
			String comentario) throws Exception{
		EstatusAnalisisModel estatusAnalisisModel = new EstatusAnalisisModel();
		
		estatusAnalisisModel.setComentario(comentario);
		estatusAnalisisModel.setCveIdEstatus(model.getCveIdEstatus());
		estatusAnalisisModel.setCveUsuario(model.getClaveUsuarioAsignado());
		estatusAnalisisModel.setCveIdAnalisis(model.getCveIdAnalisis());
		estatusAnalisisModel.setCveIdDelegacion(model.getCveIdDelegacion());
		estatusAnalisisModel.setCveIdSubdelegacion(model.getCveIdSubdelegacion());
		
		if(model.getClasificacionActual() != null && model.getClasificacionActual().getFraccion() != null){
			Fraccion fraccionAct = new Fraccion();
			fraccionAct = model.getClasificacionActual().getFraccion();
			estatusAnalisisModel.setFraccionActual(fraccionAct);
		}
		if(model.getClasificacionPropuesta() != null && model.getClasificacionPropuesta().getFraccion() != null){
			Fraccion fraccionPro = new Fraccion();
			fraccionPro = model.getClasificacionPropuesta().getFraccion();
			
			if(model.getClasificacionPropuesta().getPrimaSugerida() != null) {
				fraccionPro.setPrimaSRT(model.getClasificacionPropuesta().getPrimaSugerida());
			}
			estatusAnalisisModel.setFraccionPropuesta(fraccionPro);
		}
		if(model.getClasificacionAnterior() != null && model.getClasificacionAnterior().getFraccion() != null){
			Fraccion fraccionAnt = new Fraccion();
			fraccionAnt = model.getClasificacionAnterior().getFraccion();
			estatusAnalisisModel.setFraccionAnterior(fraccionAnt);
		}

		return estatusAnalisisModel;
	}
	
	public EstatusAnalisisModel convertirEntityToModel(DitHistEstatusAnalisis entity) throws Exception {
		EstatusAnalisisModel model = new EstatusAnalisisModel();
		
		model.setComentario(entity.getDesComentario());
		model.setCveIdEstatus(new Long(entity.getCveHistEstatusAnalisis()));
		model.setCveIdAnalisis(new Long(entity.getDitAnalisisCe().getCveIdAnalisis()));
		model.setCveIdDelegacion(new Long(entity.getDicSubdelegacion().getDicDelegacion().getCveIdDelegacion()));
		model.setCveIdSubdelegacion(new Long(entity.getDicSubdelegacion().getCveIdSubdelegacion()));
		model.setCveHistEstatus(entity.getStpHistEstatusAnalisis());
		model.setPrimaDec(entity.getNumPrimaDec());
       	model.setPrimaAnt(entity.getNumPrimaAnt());
		
        model.setFraccionActual(obtenerFraccionClaseActiva(entity.getDicFraccionDec()));
        model.setFraccionAnterior(obtenerFraccionClaseActiva(entity.getDicFraccionAnt()));
        model.setFraccionPropuesta(obtenerFraccionClaseActiva(entity.getDicFraccionPro()));
				
		return model;
	}
	public String obtenerFraccion(DicFraccion dicFraccion) {
		Fraccion fraccion = obtenerFraccionClaseActiva(dicFraccion);
		String fraccionPropuesto = fraccion.getGrupo().getDivision().getNumDivision() + fraccion.getGrupo().getNumGrupo() + fraccion.getNumFraccion();		
		return fraccionPropuesto;
	}

    private Fraccion obtenerFraccionClaseActiva(DicFraccion dicFraccion) {
        Fraccion fraccion = null;
        if (dicFraccion != null) {
            try {
                fraccion = clasificacionActividadEconomicaService.obtenerFraccionClaseActiva(dicFraccion.getCveIdFraccion());
            }
            catch (GestionPatronalBusinessException gpb) {
                log.warn("Excepcion Codigo: {}", gpb.getMessage(), gpb);
            }
        }
        return fraccion;
    }

	@Override
	public EstatusAnalisisModel armaBitacoraCambioClem(
			String idAnalisis, Fraccion fraccion, String del, String subDel, String usuario)
			throws Exception {
		
		EstatusAnalisisModel estatusAnalisisModel = new EstatusAnalisisModel();

		//El cambio a los datos de la clem no se considera como un cambio de estatus
		//se pone el estatus a 23 ya que este es un registro especial en la tabla de estatus solo para identificar los cambios a la clem
		estatusAnalisisModel.setCveIdEstatus(new Long(23));

		estatusAnalisisModel.setComentario("MODIFICACIÓN A LA CLEM");

		estatusAnalisisModel.setCveUsuario(usuario);
		estatusAnalisisModel.setCveIdAnalisis(new Long(idAnalisis));
		estatusAnalisisModel.setCveIdDelegacion(new Long(del));
		estatusAnalisisModel.setCveIdSubdelegacion(new Long(subDel));		

		return estatusAnalisisModel;
	}
}
