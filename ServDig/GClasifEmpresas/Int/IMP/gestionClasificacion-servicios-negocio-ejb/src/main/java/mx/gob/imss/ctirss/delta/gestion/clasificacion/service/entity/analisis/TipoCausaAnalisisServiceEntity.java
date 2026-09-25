/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Héctor Lara Andrés
 *  @Proyecto: delta
 *  @Archivo:TipoCausaAnalisisServiceEntity.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.analisis
 *  @Fecha:04/10/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.analisis;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.clem.DatosClemServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.clasificacion.AnalisisClasificacionEmpresas;
import mx.gob.imss.ctirss.delta.model.clasificacion.HistoricoDatosClem;
import mx.gob.imss.ctirss.delta.model.clasificacion.TipoCausaAnalisis;
import mx.gob.imss.ctirss.delta.persistence.DicTipoCausaAnalisis;
import mx.gob.imss.ctirss.delta.persistence.DitAnalisisCe;
import mx.gob.imss.ctirss.delta.persistence.DitHistDatosClem;
import mx.gob.imss.ctirss.delta.persistence.DitHistTipoCausa;

@Stateless
public class TipoCausaAnalisisServiceEntity extends AbstractServiceEntity implements TipoCausaAnalisisServiceEntityLocal{

	@EJB
	DatosClemServiceUtilityLocal datosClemUtility;
	
	@Override
	public TipoCausaAnalisis consultaTipoCausa(Long tipoTramite, int tipoProceso){
		TipoCausaAnalisis tipoCausaAnalisis=new TipoCausaAnalisis();
		Query query=null;
		DicTipoCausaAnalisis entity=null;
		String sql="from DicTipoCausaAnalisis tc where tc.dicTipoTramite.cveIdTipoTramite= :tipoTramite and tc.tipoProceso= :tipoProceso";
		try {
			query=em.createQuery(sql);
			query.setParameter("tipoTramite", tipoTramite);
			query.setParameter("tipoProceso", tipoProceso);
			entity=(DicTipoCausaAnalisis)query.getSingleResult();
			if(entity!=null){
				tipoCausaAnalisis.setCveIdTipoCausa(entity.getCveIdTipoCausa());
				tipoCausaAnalisis.setDesCausa(entity.getDesCausa());
				tipoCausaAnalisis.setCveIdTipoTramite(entity.getDicTipoTramite().getCveIdTipoTramite());
				tipoCausaAnalisis.setStmpFechaActualizado(new Timestamp(entity.getDicTipoTramite().getFecRegistroActualizado().getTime()));
			}
		}catch (Exception e){
			log.error("Error al realizar la consulta: " + e.getMessage());
		}
		return tipoCausaAnalisis;
	}
	
	@Override
	public List<HistoricoDatosClem> consultaHistoricoTipoCausaPorAnalisis(Long cveIdAnalisis){
		List<DitHistDatosClem> listaEntity = new ArrayList<DitHistDatosClem>();
		List<HistoricoDatosClem> listaModel = new ArrayList<HistoricoDatosClem>();

		Query query = null;
		String sql = 
			"from DitHistDatosClem hdc " +
			"where hdc.ditAnalisisCe.cveIdAnalisis = :id " +
			"order by hdc.stpHistDatosClem asc";
		try {
			query = em.createQuery(sql);
			query.setParameter("id", cveIdAnalisis);
			listaEntity = (List<DitHistDatosClem>)query.getResultList();
			
			if(!listaEntity.isEmpty()){
				for (DitHistDatosClem entity : listaEntity) {
					listaModel.add(datosClemUtility.convertirEntityToModel(entity));
				}
			}
			
		}catch (Exception e){
			log.error("Error al realizar la consulta: " + e.getMessage());
		}
		
		return listaModel;
	}

	@Override
	public TipoCausaAnalisis consultaTipoTramite(Long tipoCausa) {
		TipoCausaAnalisis tipoCausaAnalisis=new TipoCausaAnalisis();
		Query query=null;
		DicTipoCausaAnalisis entity=null;
		String sql="from DicTipoCausaAnalisis tc where tc.cveIdTipoCausa= :tipoCausa";
		try {
			query=em.createQuery(sql);
			query.setParameter("tipoCausa", tipoCausa);
			entity=(DicTipoCausaAnalisis)query.getSingleResult();
			if(entity!=null){
				tipoCausaAnalisis.setCveIdTipoCausa(entity.getCveIdTipoCausa());
				tipoCausaAnalisis.setDesCausa(entity.getDesCausa());
				tipoCausaAnalisis.setCveIdTipoTramite(entity.getDicTipoTramite().getCveIdTipoTramite());
			}
		}catch (Exception e){
			log.error("Error al realizar la consulta: " + e.getMessage());
		}
		return tipoCausaAnalisis;
	}

	@Override
	public void registraCausa(AnalisisClasificacionEmpresas analisisClasifEmp){
		
		this.log.debug("registrando causa ::::::" 
				 + analisisClasifEmp);
		
		DitHistTipoCausa entity=new DitHistTipoCausa();
		DitAnalisisCe ditAnalisisCe=new DitAnalisisCe();
		ditAnalisisCe.setCveIdAnalisis(analisisClasifEmp.getCveIdAnalisis());
		
		
		DicTipoCausaAnalisis dicTipoCausaAnalisis = (DicTipoCausaAnalisis) em.find(
				DicTipoCausaAnalisis.class,
				Long.parseLong(analisisClasifEmp.getTipoCausaAnalisis()));
		
		this.log.debug("dicTipoCausaAnalisis ..." + dicTipoCausaAnalisis.getCveIdTipoCausa());
		entity.setDitAnalisisCe(ditAnalisisCe);
		entity.setDicTipoCausaAnalisis(dicTipoCausaAnalisis);
		entity.setStmpFechaActualizado(new Timestamp(new Date().getTime()));
		em.persist(entity);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TipoCausaAnalisis> consultaHistoricoTipoCausa(Long cveIdAnalisis){
		List<TipoCausaAnalisis> lstTipoCausaAnalisis=new ArrayList<TipoCausaAnalisis>();
		List<DitHistTipoCausa> lstDitHistTipoCausa=new ArrayList<DitHistTipoCausa>();
		TipoCausaAnalisis tipoCausaAnalisis;
		
		Query query=null;
		String sql="from DitHistTipoCausa htc where htc.ditAnalisisCe.cveIdAnalisis= :cveIdAnalisis order by htc.stmpFechaActualizado asc";
		try {
			query=em.createQuery(sql);
			query.setParameter("cveIdAnalisis", cveIdAnalisis);
			lstDitHistTipoCausa=(List<DitHistTipoCausa>)query.getResultList();
			if(!lstDitHistTipoCausa.isEmpty()){
				for(DitHistTipoCausa dhtc:lstDitHistTipoCausa){
					tipoCausaAnalisis=new TipoCausaAnalisis();
					tipoCausaAnalisis.setCveIdHistTipoCausa(dhtc.getCveIdHistTipoCausa());
					tipoCausaAnalisis.setCveIdTipoCausa(dhtc.getDicTipoCausaAnalisis().getCveIdTipoCausa());
					tipoCausaAnalisis.setDesCausa(dhtc.getDicTipoCausaAnalisis().getDesCausa());
					tipoCausaAnalisis.setStmpFechaActualizado(new Timestamp(dhtc.getStmpFechaActualizado().getTime()));
					lstTipoCausaAnalisis.add(tipoCausaAnalisis);
				}
			}
		}catch (Exception e){
			log.error("Error al realizar la consulta: " + e.getMessage());
		}
		return lstTipoCausaAnalisis;
	}
}