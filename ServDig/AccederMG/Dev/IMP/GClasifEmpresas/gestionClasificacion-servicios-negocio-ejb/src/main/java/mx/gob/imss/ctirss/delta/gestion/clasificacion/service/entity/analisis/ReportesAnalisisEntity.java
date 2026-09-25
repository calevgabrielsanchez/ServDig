/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo:ReportesAnalisisEntity.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.analisis
 *  @Fecha:04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.analisis;
 
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.analisis.ReportesAnalisisServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.util.Constantes;
import mx.gob.imss.ctirss.delta.model.clasificacion.FiltrosReportes;
import mx.gob.imss.ctirss.delta.model.clasificacion.ReporteAnalisis;
import mx.gob.imss.ctirss.delta.model.clasificacion.TipoRegistroEnum;
import mx.gob.imss.ctirss.delta.persistence.DivReporteAnalisis;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;


@Stateless
public class ReportesAnalisisEntity extends AbstractServiceEntity implements ReportesAnalisisEntityLocal{
	
	@EJB
	private ReportesAnalisisServiceUtilityLocal reportesAnalisisUtility;	

	/**
	 * {@inheritDoc}
	 */
	public List<ReporteAnalisis> consultarReportesAnalisis(FiltrosReportes filtro) throws Exception{
		
		List<ReporteAnalisis> retVal = null;
		List<DivReporteAnalisis> resultados = null;
		
		try{
			Criteria criteria = this.getSession().createCriteria(DivReporteAnalisis.class);
			criteria.add(Restrictions.isNotNull("regPatron"));
			
			if(filtro.getCveIdGrupoAnalisisCe() != null && !filtro.getCveIdGrupoAnalisisCe().equals("")){

				// filtro por grupo de tramite 1-Altas  2-Modificaciones
				if(!filtro.getCveIdGrupoAnalisisCe().equals("")){
					criteria.add(Restrictions.eq("cveIdGrupoAnalisisCe", new BigInteger(filtro.getCveIdGrupoAnalisisCe())));
				}

				// si la peticion viene de MODIFICACIONES, se agrega filtro por tipo de movimiento
				if ("2".equals(filtro.getCveIdGrupoAnalisisCe())) {
					if (null != filtro.getTipoMovimiento() && !filtro.getTipoMovimiento().equals("-1") && !(filtro.getTipoMovimiento().trim().length() < 1)) {
					    criteria.add(Restrictions.eq("cveIdTipoTramite", new BigDecimal(filtro.getTipoMovimiento())));
					}
				}
				
			}else{
				throw new Exception("Error, No se recibio el grupo de analisis para la busqueda");
			}
			
			criteria.add(Restrictions.isNotNull("cveIdAnalisis"));
			
			if(filtro.getRegistroPatronal() != null && filtro.getRegistroPatronal().length() == 11)
				criteria.add( Restrictions.eq("regPatronCompleto", filtro.getRegistroPatronal().toUpperCase()) );
			
			if (null != filtro.getPeriodoInicio()
					&& null != filtro.getPeriodoFin()) {
				criteria.add(Restrictions.between("fecPresentacion", filtro
						.getPeriodoInicio(), filtro.getPeriodoFin()));
			}
			
			if(filtro.getTipoPersona() != null && !filtro.getTipoPersona().equals("-1") && !filtro.getTipoPersona().equals("")){
				criteria.add(Restrictions.eq("cveIdTipoPersona", new BigInteger(
						filtro.getTipoPersona())));
			}
			
			if(filtro.getTipoRegistro() != null && !filtro.getTipoRegistro().equals("")){
				
				int auxTipoRegistro= Integer.parseInt(filtro.getTipoRegistro());
				
				if(Integer.parseInt(TipoRegistroEnum.ARP.getClave())==auxTipoRegistro){
					Criterion criteria1 = Restrictions.isNull("indPrestaServicioPersonal");
					Criterion criteria2 = Restrictions.eq("indPrestaServicioPersonal", new BigInteger(Constantes.IND_NO_ACTIVO.toString()));
					criteria.add(Restrictions.or(criteria1, criteria2));
					Criterion criteria3 = Restrictions.isNull("indRegPatClase");
					Criterion criteria4 = Restrictions.eq("indRegPatClase", new BigInteger(Constantes.IND_NO_ACTIVO.toString()));
					criteria.add(Restrictions.or(criteria3, criteria4));
				}else if(Integer.parseInt(TipoRegistroEnum.RPC.getClave())==auxTipoRegistro){
					criteria.add(Restrictions.eq("indPrestaServicioPersonal", Constantes.IND_ACTIVO));
					criteria.add(Restrictions.eq("indRegPatClase", Constantes.IND_ACTIVO));								
				}else if(Integer.parseInt(TipoRegistroEnum.PSP.getClave())==auxTipoRegistro){
					criteria.add(Restrictions.eq("indPrestaServicioPersonal", new BigInteger(Constantes.IND_ACTIVO.toString())));
					Criterion criteria1 = Restrictions.isNull("indRegPatClase");
					Criterion criteria2 = Restrictions.eq("indRegPatClase", new BigInteger(Constantes.IND_NO_ACTIVO.toString()));
					criteria.add(Restrictions.or(criteria1, criteria2));
				}
				
			}	
			
			if(filtro.getEstatus() != null && !filtro.getEstatus().equals("")){
				criteria.add(Restrictions.eq("cveIdEstatusAnalisis", new BigInteger(
						filtro.getEstatus())));	
			}		
			
			if(filtro.getDelegacion()  != null && !filtro.getDelegacion().equals("-1") && !filtro.getDelegacion().equals("")){
				criteria.add(Restrictions.eq("cveIdDelegacion", new BigInteger(
						filtro.getDelegacion())));	
			}
			
			if(filtro.getSubDelegacion() != null && !filtro.getSubDelegacion().equals("-1") && !filtro.getSubDelegacion().equals("")){
				criteria.add(Restrictions.eq("cveIdSubdelegacion", new BigInteger(
						filtro.getSubDelegacion())));
			}
			
			if(filtro.getClasePropuesta() != null && !filtro.getClasePropuesta().equals("-1") && !filtro.getClasePropuesta().equals("")){
				log.error("Clase Propuesta:"+ filtro.getClasePropuesta());
				criteria.add(Restrictions.eq("cveIdClaseR",filtro.getClasePropuesta() ));
			}
			if(filtro.getClaseAnterior() != null && !filtro.getClaseAnterior().equals("")){
				log.error("Clase anterior:"+ filtro.getClaseAnterior());
				criteria.add(Restrictions.eq("claseD", filtro.getClaseAnterior()));
			}
			
			criteria.addOrder(Order.asc("regPatronCompleto")); //Inscripción, Modificación
					
			resultados = criteria.list();
		 
			/*Convertimos la lista de los entities a la lista de model*/
			if(resultados!=null)
				retVal = reportesAnalisisUtility.convertListOfEntitiesToListOfModel(resultados);
				
		} catch (Exception e1) {
			log.error("Error al buscar los registros del reporte :"+e1.getMessage());
			e1.printStackTrace();
			throw e1;
		}
		
		return retVal;
	}
	
}