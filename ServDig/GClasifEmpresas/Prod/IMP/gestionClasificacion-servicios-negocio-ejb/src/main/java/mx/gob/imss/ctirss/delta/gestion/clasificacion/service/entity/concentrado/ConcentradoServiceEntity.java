/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo:BitacoraServiceEntity.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.bitacora
 *  @Fecha:04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.concentrado;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.clasificacion.ElementoConcentrado;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisEnum;
import mx.gob.imss.ctirss.delta.model.clasificacion.FiltrosConcentrado;
import mx.gob.imss.ctirss.delta.model.clasificacion.SubdelegacionesConcentrado;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class ConcentradoServiceEntity extends AbstractServiceEntity implements ConcentradoServiceEntityLocal{

    private static final Logger log = LoggerFactory.getLogger(ConcentradoServiceEntity.class);
	
	private static String SQL_SELECT_BUSQUEDA_CONCENTRADO = null;
	private static String SQL_SELECT_BUSQUEDA_SUBDELEGACIONES = null;
	
	static{
		
		StringBuffer sb = new StringBuffer();
		sb.append("select sc.cveIdDelegacion, sc.cveIdSubdelegacion, sc.cveIdEstatusAnalisis, sc.indRegPatClase, sc.indPrestaServicioPersonal  ");
		sb.append("  from DivSolicitudConcluida sc");
		sb.append(" where sc.regPatron is not null ");	
		
		SQL_SELECT_BUSQUEDA_CONCENTRADO = sb.toString();
		
		StringBuffer sb2 = new StringBuffer();
		sb2.append("select  d.cveIdDelegacion, sd.cveIdSubdelegacion, d.desDeleg, sd.desSubdelegacion ");
		sb2.append("  from DicDelegacion d, DicSubdelegacion sd");
		sb2.append(" where d.cveIdDelegacion = sd.dicDelegacion.cveIdDelegacion ");		
		sb2.append(" and d.fecRegistroBaja is null ");		
		sb2.append(" and sd.fecRegistroBaja is null ");		
		
		SQL_SELECT_BUSQUEDA_SUBDELEGACIONES = sb2.toString();
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ElementoConcentrado> obtieneConcentrado(FiltrosConcentrado filtrosConcentrado)
			throws Exception {
		List<Object[]> response= null;
		List<ElementoConcentrado> lista = new ArrayList<ElementoConcentrado>();
		ElementoConcentrado e = new ElementoConcentrado();

		String strquery  = componeQueryBusqueda(filtrosConcentrado);
		System.out.println("QUERY: " + strquery);
		//Obtendra el nombre del campo clave y el campo descripcion del modelo pasado como parametro
		log.info("Query de consulta="+ strquery);
		Query query = this.getSession().createQuery(strquery.toString());
		
		response = query.list();
		System.out.println("antes de iteraciones para reporte de concentrado");
		
		for (Object[] obj : response) {
			e = new ElementoConcentrado();

			e.setIdDel( ((BigDecimal)obj[0]).intValue() );
			e.setIdSubDel( ((BigDecimal)obj[1]).intValue() );
			if(obj[2] != null)
				e.setEstatus( ((BigDecimal)obj[2]).intValue() );
			else
				e.setEstatus(Integer.valueOf(EstatusAnalisisEnum.PENDIENTE_DE_ANALISIS.getClave()));
			if(obj[3] != null){
				e.setInd_marca_Clase( ((BigDecimal)obj[3]).toString() );
			}else{
				e.setInd_marca_Clase("0");
			}
			
			if(obj[4] != null){
				e.setInd_serv_personal( ((BigDecimal)obj[4]).toString() );
			}else{
				e.setInd_serv_personal("0");
			}

			lista.add(e);
		}
			
		return lista;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<SubdelegacionesConcentrado> obtieneSubdelegaciones(int del)
			throws Exception {
		List<Object[]> response= null;
		ArrayList<SubdelegacionesConcentrado> lista = new ArrayList<SubdelegacionesConcentrado>();
		SubdelegacionesConcentrado e = new SubdelegacionesConcentrado();

		String strquery  = componeQueryBusquedaDel(del);
		//Obtendra el nombre del campo clave y el campo descripcion del modelo pasado como parametro
		Query query = this.getSession().createQuery(strquery.toString());
		
		response = query.list();
		
		for (Object[] obj : response) {
			
			e = new SubdelegacionesConcentrado();

			e.setIdDel( ((Long)obj[0]).intValue() );
			e.setIdSubDel( ((Long)obj[1]).intValue() );
			e.setDel((String)obj[2]);
			e.setSubDel((String)obj[3]);				

			lista.add(e);
				
		}
			
		return lista;
	}
	
	private String componeQueryBusqueda(FiltrosConcentrado filtrosConcentrado){
		String query="";
		
		if(filtrosConcentrado!=null){
			
			query += " and (trunc(sc.fecPresentacion) BETWEEN to_date('"
				  +	filtrosConcentrado.getStrPeriodoInicio() + "','DD/MM/YYYY') and to_date('"
				  + filtrosConcentrado.getStrPeriodoFin() + "','DD/MM/YYYY') ) "
				  + "and sc.cveIdGrupoAnalisisCe = " + filtrosConcentrado.getCveIdGrupoAnalisisCe();

		}
		
		query = SQL_SELECT_BUSQUEDA_CONCENTRADO + query;
		
		return query;
	}
	
	private String componeQueryBusquedaDel(int del){
		String query="";
		
		query = " and d.claveDelegacion = " + del;
		
		query += " order by sd.claveSubdelegacion ";
		
		query = SQL_SELECT_BUSQUEDA_SUBDELEGACIONES + query;
		
		return query;
	}
	
}
