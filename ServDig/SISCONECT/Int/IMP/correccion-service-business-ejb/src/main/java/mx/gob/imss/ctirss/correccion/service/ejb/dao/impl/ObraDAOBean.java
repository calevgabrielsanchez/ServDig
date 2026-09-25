package mx.gob.imss.ctirss.correccion.service.ejb.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.model.SatObra;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.promocion.generico.SegEOIncidenciasObraVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.promocion.generico.SegEOPeriodosPresentadosObraVO;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.base.repository.AbstractRespository;
import mx.gob.imss.ctirss.correccion.model.SacMunicipio;
import mx.gob.imss.ctirss.correccion.model.SatUbicacion;
import mx.gob.imss.ctirss.correccion.seguimiento.promocion.model.ConsultasEstatusObra;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.ObraDAOLocal;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

@Stateless
public class ObraDAOBean <T extends AbstractModel> extends AbstractRespository implements ObraDAOLocal<T>{

	
	public SatObra validaObra(String numRegObra) {
		Criteria criteria = this.getSession().createCriteria(SatObra.class).add(Restrictions.eq("cveNroregobra", new Long(numRegObra).longValue()))
		                                                           		   .addOrder(Order.desc("indHistorico"));
		
		List l = criteria.list();
		if(l!=null&&l.size()>0)
		{
			SatObra obra = (SatObra)l.get(0);
			Criteria criteria2 = this.getSession().createCriteria(SatUbicacion.class).add(Restrictions.eq("cvePK", obra.getCveFkUbicacion().intValue()));
			List l2 = criteria2.list();
			if(l2!=null&&l2.size()>0)
			{
				SatUbicacion ubicacion = (SatUbicacion)l2.get(0);
				ubicacion.setMunicipio(getMunicipioById(ubicacion.getFkMunicipio()));
				obra.setUbicacion(ubicacion);
			}
			return obra;
		}
		return null;
	}
	
	public SacMunicipio getMunicipioById(Integer cveMunicipio) {
		Criteria criteria = this.getSession().createCriteria(SacMunicipio.class).add(Restrictions.eq("cvePK", cveMunicipio));
		List l = criteria.list();
		if(l!=null&&l.size()>0)
		{
			return (SacMunicipio)l.get(0);
		}
		return null;
	}


	@SuppressWarnings("unchecked")
	public DatosSalidaPaginador<SegEOIncidenciasObraVO> buscaIncidencias(DatosEntradaPaginador<SegEOIncidenciasObraVO> params, Long numRegObra) {
	
		String stringQuery = ConsultasEstatusObra.CONSULTA_INCIDENCIAS_DE_OBRA;
		SQLQuery query = this.getSession().createSQLQuery(stringQuery);

		query.setParameter("numRegObra", numRegObra);
		
		List<Object[]> resultadoQuey=null;
		query.setFirstResult(params.getiDisplayStart());
		query.setMaxResults(params.getiDisplayLength());
		
		List<SegEOIncidenciasObraVO> incidencias=new ArrayList<SegEOIncidenciasObraVO>();
		DatosSalidaPaginador<SegEOIncidenciasObraVO> resultado = new DatosSalidaPaginador<SegEOIncidenciasObraVO>();
		 
		try{
			resultadoQuey= query.list();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		/*Se debe de obtener el numero total de registros en la base de datos*/
		
		/*Se debe de obtener el numero total de registros en la base de datos*/
        query.setFirstResult(0);
        query.setMaxResults(-1);
        
        final List temp = query.list();
        logger.debug("temp.size() :: " + temp.size());
        resultado.setiTotalRecords(temp.size());
        resultado.setiTotalDisplayRecords(temp.size());
		
		SegEOIncidenciasObraVO incidencia=null;
		for(Object[] o: resultadoQuey){
			incidencia = new SegEOIncidenciasObraVO(o);
			incidencias.add(incidencia);
		}
		resultado.setAaData(incidencias);
		return resultado;
	}


	@SuppressWarnings("unchecked")
	public DatosSalidaPaginador<SegEOPeriodosPresentadosObraVO> buscaPeriodosPresentados(
			DatosEntradaPaginador<SegEOPeriodosPresentadosObraVO> params,
			Long numRegObra) {
		//String stringQuery = ConsultasEstatusObra.CONSULTA_RELACIONES_DE_TRABAJADORES;
		String stringQuery = ConsultasEstatusObra.CONSULTA_RELACIONES_DE_TRABAJADORES_NUEVO;
		
		SQLQuery query = this.getSession().createSQLQuery(stringQuery);

		query.setParameter("numRegObra", numRegObra);
		
		List<Object[]> resultadoQuey=null;
		query.setFirstResult(params.getiDisplayStart());
		query.setMaxResults(params.getiDisplayLength());
		
		List<SegEOPeriodosPresentadosObraVO> periodos=new ArrayList<SegEOPeriodosPresentadosObraVO>();
		DatosSalidaPaginador<SegEOPeriodosPresentadosObraVO> resultado = new DatosSalidaPaginador<SegEOPeriodosPresentadosObraVO>();
		 
		try{
			resultadoQuey= query.list();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		int iTotalRecords = 0;
		/*Se debe de obtener el numero total de registros en la base de datos*/
		if(resultadoQuey!=null){
			iTotalRecords = resultadoQuey.size();
		}
		/*Se debe de obtener el numero total de registros en la base de datos*/
        query.setFirstResult(0);
        query.setMaxResults(-1);
        
        final List temp = query.list();
        logger.debug("temp.size() periodos :: " + temp.size());
        resultado.setiTotalRecords(temp.size());
        resultado.setiTotalDisplayRecords(temp.size());
		
        SegEOPeriodosPresentadosObraVO periodo=null;
		for(Object[] o: resultadoQuey){
			periodo = new SegEOPeriodosPresentadosObraVO(o);
			periodos.add(periodo);
		}
		
		
		resultado.setAaData(periodos);
		return resultado;
	}


}
