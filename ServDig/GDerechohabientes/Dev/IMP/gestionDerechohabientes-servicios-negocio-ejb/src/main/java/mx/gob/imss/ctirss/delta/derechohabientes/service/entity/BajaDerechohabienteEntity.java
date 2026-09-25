package mx.gob.imss.ctirss.delta.derechohabientes.service.entity;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;

import mx.gob.imss.ctirss.delta.derechohabientes.service.parser.BajaDerechohabienteParserServiceLocal;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.derechohabiente.TipoBajaDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.BajaDerechohabienteDto;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteBajaDerechohabiente;
import mx.gob.imss.ctirss.delta.persistence.DitBajaDerechohabiente;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

@Stateless(name = "bajaDerechohabienteEntity", mappedName = "bajaDerechohabienteEntity")
public class BajaDerechohabienteEntity extends AbstractServiceEntity implements BajaDerechohabienteEntityLocal{

	@EJB BajaDerechohabienteParserServiceLocal bajaDerechohabienteParserServiceLocal;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BajaDerechohabienteDto> getBajaDerechohabiente(Long idAsignasionNss, List<Long> idPersonas, List<Long> tiposBaja, Boolean activa) {
		List<BajaDerechohabienteDto> bajas = null;
		
		try {

			int numRegistros = 0;
			int contador = 0;
			StringBuffer sSql = new StringBuffer();
			sSql.append("SELECT b FROM DitBajaDerechohabiente b " +
						  " WHERE b.cveIdAsignacionNSS = " + idAsignasionNss + " ");
			if(idPersonas != null) {
				if(idPersonas.size() == 1) {
					sSql.append(" and b.cveIdPersonaIntegrante = " + idPersonas.get(0) + " ");
				} else {
					numRegistros = idPersonas.size();
					String inPersona = new String();
					for(Long persona:  idPersonas){
						contador +=1;
						inPersona += persona;
						if(contador < numRegistros){
							inPersona +=", "; 
						}
						
					}
					sSql.append(" and b.cveIdPersonaIntegrante in (" +inPersona + ") ");
				}
			}
			
			if(tiposBaja != null) {
				log.debug("Entro con los tipos de bajas");
				if(tiposBaja.size() == 1 )
					sSql.append(" and b.dicTipoBajaDerechohabiente.cveIdTipoBajaDer = " +tiposBaja.get(0) +" ");
				else {
					
					numRegistros = tiposBaja.size();
					contador = 0;
					String inBaja = new String();
					for(Long baja:  tiposBaja){
						contador +=1;
						inBaja += baja;
						if(contador < numRegistros){
							inBaja +=", "; 
						}
						
					}
					
					sSql.append(" and b.dicTipoBajaDerechohabiente.cveIdTipoBajaDer in (" + inBaja +") ");
				}
			}
			
			if(activa != null) {
				
				if( activa )
					sSql.append(" and b.indBajaActiva = 1");
				else
					sSql.append(" and b.indBajaActiva != 1");
				
			}
			
			
			
			Query query = this.em.createQuery(sSql.toString());
			List<DitBajaDerechohabiente> ditBajas = (List<DitBajaDerechohabiente>) query.getResultList();
				
			if(ditBajas != null && !ditBajas.isEmpty()) {
				bajas = new ArrayList<BajaDerechohabienteDto>();
				
				for(DitBajaDerechohabiente ditBaja : ditBajas) {
					BajaDerechohabienteDto baja = bajaDerechohabienteParserServiceLocal.convertirEntityToBajaDto(ditBaja);
					bajas.add(baja);
				}
			}
		} catch (Exception e) {
			
		}
		return bajas;
	}
	
	@Override
	public BajaDerechohabienteDto getBajaDerechohabiente( Long cveIdBaja ) throws Exception {
		BajaDerechohabienteDto item = null;
		
		try{
			DitBajaDerechohabiente entity = new DitBajaDerechohabiente();
			
			entity = em.find(DitBajaDerechohabiente.class, cveIdBaja);
			if( entity != null ){
				item = bajaDerechohabienteParserServiceLocal.convertirEntityToBajaDto(entity);
				
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return item;
	}
	
	

	@Override
	public void insert(DitBajaDerechohabiente entity) throws Exception {
		em.merge(entity);
	}



	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public BajaDerechohabienteDto insertFromTramiteBaja(
			TramiteBajaDerechohabiente tramiteBaja) {
		BajaDerechohabienteDto item = null;
		
		try{
			DitBajaDerechohabiente entity = bajaDerechohabienteParserServiceLocal.convertirTramiteBajaToEntity(tramiteBaja,1L);
			em.persist(entity);
			item = bajaDerechohabienteParserServiceLocal.convertirEntityToBajaDto(entity);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return item;
	}
	
	@Override
	public void saveOrUpdate(BajaDerechohabienteDto bajaDto) throws IllegalArgumentException, TransactionRequiredException   {
		DitBajaDerechohabiente ditBajaDerechohabiente = bajaDerechohabienteParserServiceLocal.convertirBajadtoToEntity(bajaDto);
		em.merge(ditBajaDerechohabiente);
	}


	
	@SuppressWarnings("unchecked")
	@Override
	public boolean tieneBajaAdministrativaActiva(Long idAsignasionNss, Long idPersona) {
		
		try {
		
			StringBuffer sSql = new StringBuffer();
			sSql.append("SELECT b FROM DitBajaDerechohabiente b  WHERE b.cveIdAsignacionNSS = " + idAsignasionNss + " ");
			sSql.append(" and b.cveIdPersonaIntegrante = " + idPersona + " ");
			sSql.append(" and b.dicTipoBajaDerechohabiente.cveIdTipoBajaDer = " +TipoBajaDerechohabienteEnum.ADMINISTRATIVA.getId() +" ");
			sSql.append(" and b.indBajaActiva = 1");
			
			
			Query query = this.em.createQuery(sSql.toString());
			List<DitBajaDerechohabiente> ditBajas = (List<DitBajaDerechohabiente>)query.getResultList();
				
			if(ditBajas != null && !ditBajas.isEmpty()) 
				return true;
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public int tieneBajaPorSolicitud(Long idAsignasionNss, Long idPersonas, int tipoBaja) {

		int numero = 0;

		try{
			StringBuffer sSql = new StringBuffer();
			sSql.append("select cve_id_baja from dit_baja_derechohabiente b ");
			sSql.append("left join dit_tramite t on t.cve_id_tramite = b.cve_id_tramite ");
			sSql.append("left join dit_solicitud s on s.cve_id_solicitud = t.cve_id_solicitud ");
			sSql.append("where b.cve_id_asignacion_nss = "+idAsignasionNss+ " ");
			sSql.append("and b.cve_id_persona_integrante = "+idPersonas+" ");
			sSql.append("and b.cve_id_tipo_baja_der = "+tipoBaja+" and t.cve_id_estado_tramite = '2' ");
			sSql.append("and s.cve_id_estado_solicitud = '2' and b.fec_registro_baja is not null and b.ind_baja_activa = 0");
			Session session = this.getSession();
			String query = sSql.toString();
			SQLQuery queryNSS = session.createSQLQuery(query);
			@SuppressWarnings("unchecked")
			List<Object[]> resultado = (List<Object[]>)queryNSS.list();
			numero = resultado.size();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
        }


		return numero;
	}

	}

