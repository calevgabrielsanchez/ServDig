/**
 * 
 */
package mx.gob.imss.ctirss.delta.cobranza.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import mx.gob.imss.ctirss.delta.cobranza.exception.ErrorAlConsultarTimbradosException;
import mx.gob.imss.ctirss.delta.cobranza.exception.NoExistenDatosParaCancelarException;
import mx.gob.imss.ctirss.delta.cobranza.model.ProcOdiCompFisc;
import mx.gob.imss.ctirss.delta.cobranza.model.RegistroCFDI;
import mx.gob.imss.ctirss.delta.cobranza.service.utility.ICancelarTimbradoUtilityRemote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author vanderluk
 * 
 */
@Stateless
public class CancelacionServiceEntity implements ICancelacionServiceLocal {

	private static Logger LOG = null;

	static {
		LOG = LoggerFactory.getLogger(CancelacionServiceEntity.class);

	}

	@PersistenceContext(unitName = "portalPersistenceUnit")
	protected EntityManager em;
	private SimpleDateFormat sdf;

	@Override
	public List<ProcOdiCompFisc> obtenerRegistrosParaCancelar(
			String fechaProceso) throws NoExistenDatosParaCancelarException,
			ErrorAlConsultarTimbradosException {
		// Obtenemos los registros de timbre a cancelar a partir de la fecha de
		// proceso.

		StringBuffer string_query = new StringBuffer();
		string_query.append("select p from ProcOdiCompFisc p");
		string_query.append(" where p.cfdiXml is not null ");
		string_query.append(" and trunc(p.fecInicio) = :fechaProceso");
		string_query.append(" and p.cveCodigoRespuesta = 901 ");

		Query query = this.em.createQuery(string_query.toString());		
		sdf = new SimpleDateFormat("dd-MM-yyyy");
		
		Date dateFechaProceso = null;
		
		try {
			
			dateFechaProceso = sdf.parse(fechaProceso);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		LOG.info("Fecha de processo  " +   dateFechaProceso);
		query.setParameter("fechaProceso", dateFechaProceso);

		List<ProcOdiCompFisc> timbrados = query.getResultList();

		Iterator<ProcOdiCompFisc> it = timbrados.iterator();

		if (timbrados.isEmpty()) {
			throw new NoExistenDatosParaCancelarException(
					"No existen registros para cancelar.");
		}

		return timbrados;

	}

	@Override
	public long getNumeroRegistrosTimbrados(String fechaProceso)
			throws ErrorAlConsultarTimbradosException {
		
		
		LOG.debug("Obteniendo el numero de registros por fecha de proceso:");

		Long numeroRegistros = 0L;

		//
		sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date dateFechaProceso = null;
		try {
			dateFechaProceso = sdf.parse(fechaProceso);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		StringBuffer string_query = new StringBuffer();
		string_query.append("select count(p) from ProcOdiCompFisc p");
		string_query.append(" where p.cfdiXml is not null ");
		string_query.append(" and trunc(p.fecInicio) = :fechaProceso");
		string_query.append(" and p.cveCodigoRespuesta is null ");

		Query query = this.em.createQuery(string_query.toString());
		query.setParameter("fechaProceso", dateFechaProceso);
		numeroRegistros =(Long) query.getSingleResult();
		return numeroRegistros; 
	}

	@Override
	public List<ProcOdiCompFisc> obtenerRegistrosParaCancelarPorLote(
			String fechaProceso, int numeroLote)
			throws NoExistenDatosParaCancelarException,
			ErrorAlConsultarTimbradosException {

		LOG.debug("Iniciando la consulta de registros paginados..."
				+ numeroLote);

		List<ProcOdiCompFisc> registros = null;
		sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date dateFechaProceso = null;
		try {
			dateFechaProceso = sdf.parse(fechaProceso);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		StringBuffer string_query = new StringBuffer();
		string_query.append("select p from ProcOdiCompFisc p");
		string_query.append(" where p.cfdiXml is not null ");
		string_query.append(" and trunc(p.fecInicio) = :fechaProceso");
		string_query.append(" and p.cveCodigoRespuesta is null ");

		Query query = this.em.createQuery(string_query.toString());
		query.setParameter("fechaProceso", dateFechaProceso);
		query.setMaxResults(ICancelarTimbradoUtilityRemote.NUMERO_DE_REGISTROS_POR_LOTE.intValue());
		query.setFirstResult(numeroLote*ICancelarTimbradoUtilityRemote.NUMERO_DE_REGISTROS_POR_LOTE.intValue());
		
		registros = query.getResultList();

		if (registros.isEmpty()) {
			throw new NoExistenDatosParaCancelarException(
					"No existen registros de CFDI timbrados.");
		}

		return registros;
	}
	
	
	/**
	 * Metodo que actualiza el Campo UUID obtenido del XMl timbrado
	 * @param registroCFDI
	 * @param 
	 * @return
	 * @throws ErrorAlConsultarTimbradosException
	 */		
	@Override	
	public void actualizaFolios(RegistroCFDI[] registroCFDI) throws ErrorAlConsultarTimbradosException{
		LOG.info("Numero de Registros  :: " + registroCFDI.length);
		for(int i = 0; i < registroCFDI.length; i++){					
			ProcOdiCompFisc procOdiCompFisc = this.em.find(ProcOdiCompFisc.class, new Long(registroCFDI[i].getCveRegistro()));
			LOG.info("Obtiene objeto  :: " + procOdiCompFisc.getCveRegistro());
		    procOdiCompFisc.setUuid(registroCFDI[i].getUuid());
		    procOdiCompFisc.setCveCodigoRespuesta("901");
		    try{	    		    	
		    	this.em.merge(procOdiCompFisc);
		    	this.em.flush();
		      }catch(Exception ex){
		    	  ex.printStackTrace();
		    	  throw new ErrorAlConsultarTimbradosException("error en la actualizacion de folios:. "+ ex.getMessage());	    	  
		      }
		}
		
	}
	
	
	/**
	 * Metodo que obtiene los UUID de la tabla PROC_ODI_COMP_FISC para crear los lotes de folios
	 * @param registroCFDI
	 * @param 
	 * @return
	 * @throws ErrorAlConsultarTimbradosException
	 */	
	@Override
	public List<ProcOdiCompFisc> obtenerFoliosParaCancelarPorUUID(String fechaInicio) throws NoExistenDatosParaCancelarException,ErrorAlConsultarTimbradosException {
		StringBuffer string_query = new StringBuffer();
		string_query.append("select p from ProcOdiCompFisc p");
		string_query.append(" where p.cveCodigoRespuesta = 901");
		string_query.append("   and p.fecInicio between :fechaInicio and :fechaFin");

		Query query = this.em.createQuery(string_query.toString());
		sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		
		Date dateFechaInicio = null;
		Date dateFechaFin = null;
		
		try {
			dateFechaInicio = sdf.parse(fechaInicio + " 00:00:00");
			dateFechaFin = sdf.parse(fechaInicio + " 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		LOG.info("Fecha de processo para la obtencion de folios a cancelar: "+dateFechaInicio);
		query.setParameter("fechaInicio", dateFechaInicio);
		query.setParameter("fechaFin", dateFechaFin);
		
		List<ProcOdiCompFisc> folios = query.getResultList();
		
		if (folios.isEmpty()) {
			LOG.info("No existen folios para cancelar.");
			throw new NoExistenDatosParaCancelarException("No existen registros para cancelar.");
		}
		return folios;
	}
	
	/**
	 * Metodo que actualiza los datos cancelados
	 * @param registroCFDI
	 * @param 
	 * @return
	 * @throws ErrorAlConsultarTimbradosException
	 */		
	@Override	
	public void actualizaRegistroCFDICancelado(RegistroCFDI registroCFDI) throws ErrorAlConsultarTimbradosException {
//		LOG.info("Se busca el registro con CLAVE:"+registroCFDI.getCveRegistro());
//		LOG.info("Se busca el registro con UUID:"+registroCFDI.getUuid()+ " con estatus: "+registroCFDI.getEstatus());
//		ProcOdiCompFisc procOdiCompFisc = this.em.find(ProcOdiCompFisc.class, new Long(registroCFDI.getCveRegistro()));
		
		ProcOdiCompFisc procOdiCompFisc = null;
		
		StringBuffer string_query = new StringBuffer();
		string_query.append("select p from ProcOdiCompFisc p");
		string_query.append(" where upper(p.uuid) = upper(:uuid)");
		
		Query query = this.em.createQuery(string_query.toString());
		
		query.setParameter("uuid", registroCFDI.getUuid());
		
		if (registroCFDI.getEstatus() != null) {
		   procOdiCompFisc = (ProcOdiCompFisc) query.getSingleResult();
		   LOG.info("Registro recuperado con CLAVE: "+procOdiCompFisc.getCveRegistro());
		   procOdiCompFisc.setCveCodigoRespuesta(registroCFDI.getEstatus());
		   try {
			   this.em.merge(procOdiCompFisc);
			   this.em.flush();
		   } catch (Exception ex) {
			   ex.printStackTrace();
			   throw new ErrorAlConsultarTimbradosException("error en la actualizacion de registros CFDI:. "+ ex.getMessage());
		   }
	   }
	}
	
}
