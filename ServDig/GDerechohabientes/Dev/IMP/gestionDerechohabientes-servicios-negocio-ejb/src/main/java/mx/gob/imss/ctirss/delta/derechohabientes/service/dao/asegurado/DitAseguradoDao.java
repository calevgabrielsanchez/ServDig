package mx.gob.imss.ctirss.delta.derechohabientes.service.dao.asegurado;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.ServiciosParser;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.derechohabientes.ReporteSav011;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Servicio;
import mx.gob.imss.ctirss.delta.persistence.DicServicio;
import mx.gob.imss.ctirss.delta.persistence.DitAsegurado;
import mx.gob.imss.ctirss.delta.persistence.DitModSegPrestServ;
import mx.gob.imss.ctirss.delta.persistence.DitModalidadLeySeguro;
import mx.gob.imss.ctirss.delta.persistence.DitModalidadSeguroPrest;

import org.apache.log4j.Logger;

@Stateless(name = "ditAseguradoDao", mappedName = "ditAseguradoDao")
public class DitAseguradoDao extends AbstractServiceEntity implements DitAseguradoDaoLocal {
	
	private static final Logger logger = Logger.getLogger(DitAseguradoDao.class);
	
	/**
	@PersistenceContext()
	private EntityManager em;
	**/

	@Override
	public ReporteSav011 getAsegurado(Integer idAsegurado) {

		//DitAsegurado asegurado = em.find(DitAsegurado.class, idAsegurado);
		
		ReporteSav011 reporte = new ReporteSav011();
			return reporte;
	}
	
	
	@Override
	public List<Servicio> getServiciosByAsegurado(Long idNss) throws Exception{
		
		List<DitAsegurado> ditAsegurados=null;
		List<Servicio> resultado =null;
		List<DitModalidadLeySeguro> modalidadLeySeguro;		
		List<DicServicio> serviciosByAsegurado=new ArrayList<DicServicio>();
		boolean servicioExistente=false;
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<DitAsegurado> query = cb.createQuery(DitAsegurado.class);//resulado
		Root<DitAsegurado> root = query.from(DitAsegurado.class);//from
		query.select(root);//select
		Predicate conj=cb.conjunction();
		
		conj.getExpressions().add(cb.equal(root.get("ditAsignacionNss").get("cveIdAsignacionNss").as(Integer.class), idNss));
		conj.getExpressions().add(cb.isNull(root.get("fecRegistroBaja").as(Date.class)));
		query.where(conj);
		
		try{
			ditAsegurados=em.createQuery(query).getResultList();
		}catch (NoResultException e) {
			return null;
		}
		if(ditAsegurados.size() > 0){
			try {
				for (DitAsegurado asegurado : ditAsegurados) {
					modalidadLeySeguro=asegurado.getDitPatronSujetoObligado().getDicModalidad().getDitModalidadLeySeguros();
					for (DitModalidadLeySeguro ditModalidadLeySeguro : modalidadLeySeguro) {
						for(DitModalidadSeguroPrest seguroPrest :ditModalidadLeySeguro.getDitModalidadSeguroPrests()){
							for(DitModSegPrestServ servicios :seguroPrest.getDitModSegPrestServs()){
								for (DicServicio servicio : serviciosByAsegurado) {
								     if(servicio.getCveIdServicio()==servicios.getDicServicio().getCveIdServicio())	{
								    	 servicioExistente=true;
								    	 break;
								     }
								}
								if(!servicioExistente){
									serviciosByAsegurado.add(servicios.getDicServicio());
								}else
									servicioExistente=false;
								
							}
						}
						
					}
					
				}
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_CONSULTA_SERVICIOS, e);
				throw e;
			}
			
		}
		
		resultado = ServiciosParser.persisToModelList(serviciosByAsegurado);
		
		return resultado;
		
	}
	

}
