/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: AnalisisServiceUtility.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.analisis
 *  @Fecha: 04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.analisis;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.clasificacion.AnalisisClasificacionEmpresas;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Solicitud;
import mx.gob.imss.ctirss.delta.persistence.DitAnalisisCe;
import mx.gob.imss.ctirss.delta.persistence.DitDatosClem;

@Stateless
public class AnalisisServiceUtility extends AbstractServiceUtility implements AnalisisServiceUtilityLocal  {

	@Override
	public AnalisisClasificacionEmpresas convertirEntityToModel(DitAnalisisCe entity) throws Exception {
		AnalisisClasificacionEmpresas model = new AnalisisClasificacionEmpresas();
		model.setCveIdAnalisis(new Long(entity.getCveIdAnalisis()));
		model.setFechaRegistro(entity.getFecAnalisis());
		model.setClaveUsuarioAsignado(entity.getCveIdUsuarioSso() == null ? null : entity.getCveIdUsuarioSso());		
		if(entity.getDicEstatusAnalisisCe() != null ){
			model.setCveIdEstatus(new Long(entity.getDicEstatusAnalisisCe().getCveIdEstatusAnalisis()));
			model.setDescEstatus(entity.getDicEstatusAnalisisCe().getDesCausasAnalisis());
		}
		//Solo se agrega la clave de la solicitud JJGV 31/01/2012
		if(entity.getCveIdSolicitud()!=null){
			Solicitud solicitud = new Solicitud();
			solicitud.setId(entity.getCveIdSolicitud().longValue());
			model.setSolicitud(solicitud);
		}

		model.setIndModAut(new Long(entity.getIndModAut().longValue()));
		
		if(entity.getDicTipoCausaAnalisi()!=null){
			model.setTipoCausaAnalisis(String.valueOf(entity.getDicTipoCausaAnalisi().getCveIdTipoCausa()));
		}		
		if(entity.getDicGrupoAnalisisCe()!=null){
			model.setCveIdGrupoAnalisisCe(entity.getDicGrupoAnalisisCe().getCveIdGrupoAnalisisCe());
		}
		
		//Aquí va la asignación de la bandera a model
		model.setIndRegistraCausa(entity.getIndRegistraCausa());
		
		// se recupera url del documento en caso de que ya este firmada
		if(entity.getDitDatosClems() != null){
			System.out.println("Si vienen datos de clem + " + entity.getDitDatosClems().size());
		}else{
			System.out.println("NO vienen datos de clem");
		}
		
		model.setUrlClemFirma(null);
		for(int x=0; x<entity.getDitDatosClems().size();x++){
			DitDatosClem cl = entity.getDitDatosClems().get(x);
			System.out.println("Baja: " + cl.getFecRegistroBaja() + ", firma: " + cl.getFirma());
			if(cl.getFecRegistroBaja() == null && cl.getAcuse() != null){
				System.out.println("seteo el valor del acuse");
				model.setUrlClemFirma(cl.getAcuse());
			}
		}
		
		return model;
	}
		
	@Override
	public AnalisisClasificacionEmpresas convertirEntityToModelDetalle(DitAnalisisCe entity) throws Exception {
		AnalisisClasificacionEmpresas model = new AnalisisClasificacionEmpresas();
		model.setCveIdAnalisis(new Long(entity.getCveIdAnalisis()));
		model.setFechaRegistro(entity.getFecAnalisis());
		if(entity.getCveIdUsuarioSso() != null){
			model.setClaveUsuarioAsignado(entity.getCveIdUsuarioSso());
		}
		if(entity.getDicEstatusAnalisisCe() != null ){
			model.setCveIdEstatus(new Long(entity.getDicEstatusAnalisisCe().getCveIdEstatusAnalisis()));
			model.setDescEstatus(entity.getDicEstatusAnalisisCe().getDesCausasAnalisis());
		}
		if(entity.getDicTipoCausaAnalisi() != null){
			model.setTipoAnalisis(entity.getDicTipoCausaAnalisi().getDesCausa());
		}
		model.setIndModAut(new Long(entity.getIndModAut().longValue()));
		model.setIndActivo(entity.getIndActivo());
		return model;
	}
	
}
