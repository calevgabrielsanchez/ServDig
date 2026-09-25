package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.NoResultException;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;

import mx.gob.imss.ctirss.delta.derechohabientes.service.parser.MedicoEnTurnoParserServiceLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.AsentamientoParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.CodigoPostalParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.MedicoFamiliarParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.TurnoParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.UnidadMedicaFamiliarParser;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Consultorio;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Turno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabientes.ConsultorioDTO;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.persistence.DicMedico;
import mx.gob.imss.ctirss.delta.persistence.DicSubdelegacion;
import mx.gob.imss.ctirss.delta.persistence.DicTurno;
import mx.gob.imss.ctirss.delta.persistence.DicUmf;
import mx.gob.imss.ctirss.delta.persistence.DitUmfCodPo;
import mx.gob.imss.ctirss.delta.persistence.DitUmfConsTurnoMedico;
import mx.gob.imss.ctirss.delta.persistence.DitUmfConsultorioTurno;

@Stateless(name = "subDelegacionDAO", mappedName = "subDelegacionDAO")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UmfDao extends AbstractServiceEntity implements UmfDaoLocal  {
	
	@EJB MedicoEnTurnoParserServiceLocal medicoEnTurnoParserServiceLocal;
	
	@Override
	public DicUmf findUmfbySubDelagacionDelegacion(Long idSubDelegacion) throws Exception {
		DicUmf dicUmf=null;
		try {
			dicUmf= this.findUmfbySubDelagacion(idSubDelegacion).get(0);
		} catch (Exception e) {
			log.error("Error - findUmfbySubDelagacionDelegacion", e);
			throw e;
		}
        
        
		return dicUmf;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UnidadMedicaFamiliar> findUnidadesBySubdelegacionSinUmf(
			Long idSubdelegacion, Long idUmf) throws DerechohabientesBusinessException,Exception {
		
		List <DicUmf> dicUmfsList=null;
		
		Criteria queryUmf = this.getSession().createCriteria(DicUmf.class);
		
		queryUmf.createAlias("dicSubdelegacion", "subDel");
		queryUmf.add(Restrictions.eq("subDel.cveIdSubdelegacion", idSubdelegacion));
		if(idUmf != null) {
			queryUmf.add(Restrictions.ne("cveIdUmf", idUmf));
		}
		queryUmf.add(Restrictions.isNull("fecRegistroBaja"));
		
		
		/*CriteriaBuilder cb = em.getCriteriaBuilder(); //Step 1 
		CriteriaQuery<DicUmf> cqry= cb.createQuery(DicUmf.class);  //Aqui se pone que tipo esperamos recivir
		Root<DicUmf> root = cqry.from(DicUmf.class); //Step 2 //se crea el from
	       
        cqry.select(root); //Step 3 se agrega al select //tipo count
		Predicate conjunction = cb.conjunction();
		log.debug("idSubdelegacion: " + idSubdelegacion + " IdUmf: " +idUmf);
		conjunction.getExpressions().add(cb.equal(root.get("dicSubdelegacion").get("cveIdSubdelegacion").as(Integer.class), idSubdelegacion)); 
		conjunction.getExpressions().add(cb.notEqual(root.get("cveIdUmf").as(Integer.class), idUmf));
        conjunction.getExpressions().add(cb.isNull(root.get("fecRegistroBaja")));
        
        cqry.where(conjunction); //Step 5 se agrega el predicado
        //TODO definir como encontrar la umf del usuario tramitador
*/        try {
        	//dicUmfsList= em.createQuery(cqry).getResultList();
			dicUmfsList = queryUmf.list();
        } catch (NoResultException e) {
        	log.error("No existe ningun resultado",e);
        }
		return UnidadMedicaFamiliarParser.persisToModelList(dicUmfsList);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List <DicUmf> findUmfbySubDelagacion(Long idSubDelegacion) throws Exception {
		List <DicUmf> dicUmfsList=null;
		try {
			
			Criteria queryUmf = this.getSession().createCriteria(DicUmf.class);
			
			queryUmf.createAlias("dicSubdelegacion", "subDel");
			queryUmf.add(Restrictions.eq("subDel.cveIdSubdelegacion", idSubDelegacion));
			queryUmf.add(Restrictions.isNull("fecRegistroBaja"));
			
			/*
			CriteriaBuilder cb = em.getCriteriaBuilder(); //Step 1 
			CriteriaQuery<DicUmf> cqry= cb.createQuery(DicUmf.class);  //Aqui se pone que tipo esperamos recivir
			Root<DicUmf> root = cqry.from(DicUmf.class); //Step 2 //se crea el from
		       
	        cqry.select(root); //Step 3 se agrega al select //tipo count
			Predicate conjunction = cb.conjunction();//se crea unoa conjuntion para poder hacer and
			//TODO idDelegacion
			//conjunction.getExpressions().add(cb.equal(root.get("dicDelegacion").get("cveIdDelegacion").as(Integer.class), idSubDelegacion)); 
			//idSubdelegacion
			System.out.println("****************idDeLaSubdelegacion" + idSubDelegacion);
			conjunction.getExpressions().add(cb.equal(root.get("dicSubdelegacion").get("cveIdSubdelegacion").as(Integer.class), idSubDelegacion)); 
			   //fecha de baja sea Null
	        conjunction.getExpressions().add(cb.isNull(root.get("fecRegistroBaja")));
	        
	        cqry.where(conjunction); //Step 5 se agrega el predicado
	        //TODO definir como encontrar la umf del usuario tramitador
	        dicUmfsList= em.createQuery(cqry).getResultList();*/
			dicUmfsList = queryUmf.list();
		} catch (Exception e) {
			log.error("Error - findUmfbySubDelagacion", e);
			throw e;
		}
		
        
		return dicUmfsList;
	}
	
	/**
	 * Metodo para obtener el consultorio con menor poblacion en una umf
	 * y un turno, recibien los siguientes parametros
	 * @param idUmf - El id de la umf
	 * @param idTurno - El id del turno
	 * @return Consultorio
	 */
	@Override
	public Consultorio getConsultorioConMenorPoblacion(Long idUmf, Long idTurno, Boolean mostrarVirtuales)
			throws DerechohabientesBusinessException {
		
		Consultorio salida = null;
		mostrarVirtuales = mostrarVirtuales == null ? true : mostrarVirtuales;
		
		try {
			StringBuilder consulta = new StringBuilder("select * from (");
			consulta.append("select dicconsult2_.CVE_NUM_CONSULTORIO \"idConsultorio\",  ");
			consulta.append("dicconsult2_.DES_CONSULTORIO \"descripcion\" , coalesce(poblacion.NUM_POBLACION , 0)  \"poblacion\" ,");
			consulta.append("ditumfct_.CVE_ID_UMF_CONS_TURNO_MED \"idUmfConsultorioTurnoMed\"");
			consulta.append("from DIT_UMF_CONSULTORIO_TURNO this_ ");
			consulta.append("inner join DIT_UMF_CONS_TURNO_MEDICO ditumfct_ on this_.CVE_ID_UMF_CONS_TURNO = ditumfct_.CVE_ID_UMF_CONS_TURNO ");
			consulta.append("inner join DIC_CONSULTORIO_UMF dicconsult2_ on this_.CVE_ID_UMF_CONSULTORIO=dicconsult2_.CVE_ID_UMF_CONSULTORIO ");
			consulta.append("inner join DIC_UMF dicumf3_ on dicconsult2_.CVE_ID_UMF=dicumf3_.CVE_ID_UMF ");
			consulta.append("inner join DIC_TURNO turno1_ on this_.CVE_ID_TURNO=turno1_.CVE_ID_TURNO ");
			consulta.append("left join DIV_POBLACION_UMF_TURNO_CONS poblacion on this_.CVE_ID_UMF_CONS_TURNO = poblacion.CVE_ID_UMF_CONS_TURNO ");
			consulta.append("where  turno1_.CVE_ID_TURNO=:idTurno and dicumf3_.CVE_ID_UMF=:idUmf ");
			if(!mostrarVirtuales) {
				consulta.append("and dicconsult2_.IND_VIRTUAL=0 ");
			}
			consulta.append("order by poblacion.NUM_POBLACION asc) where rownum = 1");

			Query query = this.getSession().createSQLQuery(consulta.toString())
					.setLong("idUmf", idUmf).setLong("idTurno", idTurno);

			ConsultorioDTO c = (ConsultorioDTO) query.setResultTransformer(new AliasToBeanResultTransformer(ConsultorioDTO.class)).uniqueResult();
			if(c != null) {
				salida = new Consultorio(c.getIdConsultorio().longValue(), c.getDescripcion(), c.getPoblacion().longValue(),c.getIdUmfConsultorioTurnoMed().longValue());
			}
		} catch(Exception e) {
			log.error("Ocurrio un error al consultar el consultorio con menor poblacion", e);
		}
		
		return salida;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Consultorio> findConsultoriosByUmfTurno(Long idUmf, Long idTurno, Boolean mostrarVirtuales) throws DerechohabientesBusinessException,Exception {

		List<Consultorio> salida= new ArrayList<Consultorio>();
		mostrarVirtuales = mostrarVirtuales == null ? true : mostrarVirtuales;
		try {
			
			
			StringBuilder consulta = new StringBuilder("select dicconsult2_.CVE_NUM_CONSULTORIO \"idConsultorio\",  dicconsult2_.DES_CONSULTORIO \"descripcion\", coalesce(poblacion.NUM_POBLACION , 0)  \"poblacion\" ");
			consulta.append("from DIT_UMF_CONSULTORIO_TURNO this_ ");
			consulta.append("inner join DIC_CONSULTORIO_UMF dicconsult2_ on this_.CVE_ID_UMF_CONSULTORIO=dicconsult2_.CVE_ID_UMF_CONSULTORIO ");
			consulta.append("inner join DIC_UMF dicumf3_ on dicconsult2_.CVE_ID_UMF=dicumf3_.CVE_ID_UMF ");
			consulta.append("inner join DIC_TURNO turno1_ on this_.CVE_ID_TURNO=turno1_.CVE_ID_TURNO ");
			consulta.append("left join DIV_POBLACION_UMF_TURNO_CONS poblacion on this_.CVE_ID_UMF_CONS_TURNO = poblacion.CVE_ID_UMF_CONS_TURNO ");
			consulta.append("where  turno1_.CVE_ID_TURNO=:idTurno and dicumf3_.CVE_ID_UMF=:idUmf ");
			if(!mostrarVirtuales) {
				consulta.append("and dicconsult2_.IND_VIRTUAL=0 ");
			}
			consulta.append("order by dicconsult2_.CVE_NUM_CONSULTORIO");
     		
			Query query = this.getSession().createSQLQuery(consulta.toString())
					.setLong("idUmf", idUmf).setLong("idTurno", idTurno);
			List<ConsultorioDTO> consultorios = query.setResultTransformer(new AliasToBeanResultTransformer(ConsultorioDTO.class)).list();
			
			for(ConsultorioDTO c : consultorios ){
				salida.add(new Consultorio(c.getIdConsultorio().longValue(), c.getDescripcion(), c.getPoblacion().longValue()));
			}
			
			
			
		} catch (Exception e) {
			log.error("Error - findConsultoriosByUmfTurno", e);
			throw e;
		}
		
		
		return salida;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Consultorio> findConsultoriosByUmfTurnoMedicoEsp(Long idUmf,
			Long idTurno, Long idMedicoEspecialidad) throws DerechohabientesBusinessException,Exception{
		
		List<DitUmfConsTurnoMedico> entrada=null;
		List<Consultorio> salida= new ArrayList<Consultorio>();
		try {
			Criteria queryConsTur = this.getSession().createCriteria(DitUmfConsTurnoMedico.class);
			Criteria queryUCT = queryConsTur.createCriteria("ditUmfConsultorioTurno");
			Criteria queryTurno = queryUCT.createCriteria("dicTurno");
			queryTurno.add(Restrictions.eq("cveIdTurno", idTurno));
			Criteria queryUmf = queryUCT.createCriteria("dicConsultorioUmf").createCriteria("dicUmf");
			queryUmf.add(Restrictions.eq("cveIdUmf", idUmf));
			Criteria queryEsp = queryConsTur.createCriteria("ditMedicoEspecialidad").createCriteria("dicEspecialidadMedico");
			queryEsp.add(Restrictions.eq("cveEspecialidad", idMedicoEspecialidad));
			entrada = queryConsTur.list();
			/*
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<DitUmfConsTurnoMedico> query= cb.createQuery(DitUmfConsTurnoMedico.class);
			Root<DitUmfConsTurnoMedico> root = query.from(DitUmfConsTurnoMedico.class); 
			query.select(root);
			
			Pre8dicate conjunction = cb.conjunction();
			
			conjunction.getExpressions().add(cb.equal(root.get("ditUmfConsultorioTurno").get("dicConsultorioUmf").get("dicUmf").get("cveIdUmf").as(Integer.class), idUmf));
			conjunction.getExpressions().add(cb.equal(root.get("ditUmfConsultorioTurno").get("dicTurno").get("cveIdTurno").as(Integer.class), idTurno));
			conjunction.getExpressions().add(cb.equal(root.get("ditMedicoEspecialidad").get("dicEspecialidadMedico").get("cveEspecialidad").as(Integer.class), idMedicoEspecialidad));
			query.where(conjunction);			
			entrada=(List<DitUmfConsTurnoMedico>) em.createQuery(query).getResultList();*/
			
			for(DitUmfConsTurnoMedico ditMedico: entrada) {
				Consultorio consul = new Consultorio();
				consul.setIdConsultorio(ditMedico.getDitUmfConsultorioTurno().getDicConsultorioUmf().getCveNumConsultorio().longValue());
				consul.setDescripcion(ditMedico.getDitUmfConsultorioTurno().getDicConsultorioUmf().getDesConsultorio());
				salida.add(consul);
			}
		} catch (Exception e) {
			log.error("Error - findConsultoriosByUmfTurnoMedicoEsp", e);
			throw e;
		}
		
		
		return salida;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MedicoEnTurno> findMedicosByUmfTurnoConsultorio(Long idUmf,
			Long idTurno, Long idConsultorio) throws DerechohabientesBusinessException,Exception {
		List<DitUmfConsTurnoMedico> entrada=null;
		List<MedicoEnTurno> salida=null;
		try {
			
			Criteria queryMedico = this.getSession().createCriteria(DitUmfConsTurnoMedico.class);
			Criteria queryUmfC = queryMedico.createCriteria("ditUmfConsultorioTurno");
			queryUmfC.createCriteria("dicTurno").add(Restrictions.eq("cveIdTurno", idTurno));
			Criteria queryU = queryUmfC.createCriteria("dicConsultorioUmf");
			queryU.createCriteria("dicUmf").add(Restrictions.eq("cveIdUmf", idUmf));
			queryU.add(Restrictions.eq("cveNumConsultorio", new BigDecimal(idConsultorio)));
		
			entrada = queryMedico.list();
			
			/*
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<DitUmfConsTurnoMedico> query= cb.createQuery(DitUmfConsTurnoMedico.class);
			Root<DitUmfConsTurnoMedico> root = query.from(DitUmfConsTurnoMedico.class); 
			query.select(root);
			
			Predicate conjunction = cb.conjunction();
			
			conjunction.getExpressions().add(cb.equal(root.get("ditUmfConsultorioTurno").get("dicConsultorioUmf").get("dicUmf").get("cveIdUmf").as(Integer.class), idUmf));
			conjunction.getExpressions().add(cb.equal(root.get("ditUmfConsultorioTurno").get("dicTurno").get("cveIdTurno").as(Integer.class), idTurno));
			conjunction.getExpressions().add(cb.equal(root.get("ditUmfConsultorioTurno").get("dicConsultorioUmf").get("cveNumConsultorio").as(Integer.class), idConsultorio));
			query.where(conjunction);
			
			entrada=(List<DitUmfConsTurnoMedico>) em.createQuery(query).getResultList();*/
			
		} catch (Exception e) {
			log.error("Error - findMedicosByUmfTurnoConsultorio", e);
			throw e;
		}
		
		salida = medicoEnTurnoParserServiceLocal.persisToModelList(entrada);
		return salida;
	}
	
	 @SuppressWarnings("unchecked")
	public List<MedicoEnTurno> findMedicosByUmfTurno(Long idUmf, Long idTurno) throws DerechohabientesBusinessException,Exception{
			List<DitUmfConsTurnoMedico> entrada=null;
			List<MedicoEnTurno> salida=null;
			try {
				
				Criteria queryMedico = this.getSession().createCriteria(DitUmfConsTurnoMedico.class);
				Criteria queryUCT = queryMedico.createCriteria("ditUmfConsultorioTurno");
				queryUCT .createCriteria("dicConsultorioUmf").createCriteria("dicUmf").add(Restrictions.eq("cveIdUmf", idUmf));
				queryUCT .createCriteria("dicTurno").add(Restrictions.eq("cveIdTurno", idTurno));
				
				entrada = queryMedico.list();
				/*CriteriaBuilder cb = em.getCriteriaBuilder();
				CriteriaQuery<DitUmfConsTurnoMedico> query= cb.createQuery(DitUmfConsTurnoMedico.class);
				Root<DitUmfConsTurnoMedico> root = query.from(DitUmfConsTurnoMedico.class); 
				query.select(root);
				
				Predicate conjunction = cb.conjunction();
				
				conjunction.getExpressions().add(cb.equal(root.get("ditUmfConsultorioTurno").get("dicConsultorioUmf").get("dicUmf").get("cveIdUmf").as(Integer.class), idUmf));
				conjunction.getExpressions().add(cb.equal(root.get("ditUmfConsultorioTurno").get("dicTurno").get("cveIdTurno").as(Integer.class), idTurno));
				query.where(conjunction);
				
				entrada=(List<DitUmfConsTurnoMedico>) em.createQuery(query).getResultList();*/
				
			} catch (Exception e) {
				log.error("Error - findMedicosByUmfTurnoConsultorio", e);
				throw e;
			}
			
				salida =medicoEnTurnoParserServiceLocal.persisToModelList(entrada);
			return salida;
		}
	 
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MedicoEnTurno> findMedicosByUmfTurnoConsultorioMedicoEsp(
			Long idUmf, Long idTurno, Long idConsultorio,
			Long idMedicoEspecialidad) throws DerechohabientesBusinessException,Exception{
		
		List<DitUmfConsTurnoMedico> entrada=null;
		List<MedicoEnTurno> salida=null;
		try {
			
			Criteria queryMedico = this.getSession().createCriteria(DitUmfConsTurnoMedico.class);
			Criteria queryUCT = queryMedico.createCriteria("ditUmfConsultorioTurno");
			Criteria queryCU = queryUCT.createCriteria("dicConsultorioUmf");
			queryCU.createCriteria("dicUmf").add(Restrictions.eq("cveIdUmf", idUmf));
			queryUCT.createCriteria("dicTurno").add(Restrictions.eq("cveIdTurno", idTurno));
			queryCU.add(Restrictions.eq("cveNumConsultorio", new BigDecimal(idConsultorio)));
			queryMedico.createCriteria("ditMedicoEspecialidad").createCriteria("dicEspecialidadMedico").add(Restrictions.eq("cveEspecialidad", idMedicoEspecialidad));
			entrada = queryMedico.list();
			
			/*
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<DitUmfConsTurnoMedico> query= cb.createQuery(DitUmfConsTurnoMedico.class);
			Root<DitUmfConsTurnoMedico> root = query.from(DitUmfConsTurnoMedico.class); 
			query.select(root);
			
			Predicate conjunction = cb.conjunction();
			
			conjunction.getExpressions().add(cb.equal(root.get("ditUmfConsultorioTurno").get("dicConsultorioUmf").get("dicUmf").get("cveIdUmf").as(Integer.class), idUmf));
			conjunction.getExpressions().add(cb.equal(root.get("ditUmfConsultorioTurno").get("dicTurno").get("cveIdTurno").as(Integer.class), idTurno));
			conjunction.getExpressions().add(cb.equal(root.get("ditUmfConsultorioTurno").get("dicConsultorioUmf").get("cveNumConsultorio").as(Integer.class), idConsultorio));
			conjunction.getExpressions().add(cb.equal(root.get("ditMedicoEspecialidad").get("dicEspecialidadMedico").get("cveEspecialidad").as(Integer.class), idMedicoEspecialidad));
			query.where(conjunction);
			
			entrada=(List<DitUmfConsTurnoMedico>) em.createQuery(query).getResultList();*/
			
		} catch (Exception e) {
			log.error("Error - findMedicosByUmfTurnoConsultorio", e);
			throw e;
		}
		
		salida =medicoEnTurnoParserServiceLocal.persisToModelList(entrada);
		
		return salida;
	}

	@SuppressWarnings("unchecked")
	public List<MedicoEnTurno> getMedicosByUMF(Long idUmf) throws DerechohabientesBusinessException,Exception{
		List<DitUmfConsTurnoMedico> entrada=null;
		List<MedicoEnTurno> salida=null;
		try {
			
			Criteria queryMedico = this.getSession().createCriteria(DitUmfConsTurnoMedico.class);
			queryMedico.createCriteria("ditUmfConsultorioTurno").createCriteria("dicConsultorioUmf").createCriteria("dicUmf").add(Restrictions.eq("cveIdUmf", idUmf));
			entrada = queryMedico.list();
			/*
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<DitUmfConsTurnoMedico> query= cb.createQuery(DitUmfConsTurnoMedico.class);
			Root<DitUmfConsTurnoMedico> root = query.from(DitUmfConsTurnoMedico.class); 
			query.select(root);
			
			Predicate conjunction = cb.conjunction();
			
			conjunction.getExpressions().add(cb.equal(root.get("ditUmfConsultorioTurno").get("dicConsultorioUmf").get("dicUmf").get("cveIdUmf").as(Integer.class), idUmf)); 
			query.where(conjunction);
			
			entrada=(List<DitUmfConsTurnoMedico>) em.createQuery(query).getResultList();*/
		} catch (Exception e) {
			log.error("Error - getMedicosByUMF", e);
			throw e;
		}		
		
		salida =medicoEnTurnoParserServiceLocal.persisToModelList(entrada);
		
		return salida;
	}
	
	
	
	/**
	 * Metodo para obtener los turnos disponibles en uns UMF
	 * @param idUmf - La umf de donde se quieren obtener las umfs disponibles
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Turno> findTurnosDisponiblesPorUmf(Long idUmf)
			throws DerechohabientesBusinessException {
		List<Turno> turnos = null;
		
		try {
			
			Criteria queryTurno = this.getSession().createCriteria(DitUmfConsultorioTurno.class);
			queryTurno.createCriteria("dicConsultorioUmf").createCriteria("dicUmf").add(Restrictions.eq("cveIdUmf", idUmf));
			queryTurno.setProjection(Projections.distinct(Projections.property("dicTurno")));
			
			List<DicTurno> dicTurnos = queryTurno.list();
			
			if(dicTurnos != null && !dicTurnos.isEmpty()){
				turnos = new ArrayList<Turno>();
				
				for(DicTurno dicTurno : dicTurnos) {
					Turno turno = TurnoParser.persisToModel(dicTurno);
					turnos.add(turno);
				}
			}
			
			
		} catch (Exception e) {
			log.error("Error - getMedicosByUMF", e);
			DerechohabientesBusinessException.throwException("No fue posible consultar los turnos");
		}		
		
		return turnos;
	}


	public MedicoFamiliar getMedico(Long idMedico) throws DerechohabientesBusinessException,Exception{
		DicMedico medico = null;
		try {
			medico = em.find(DicMedico.class, idMedico);
		} catch (NoResultException e){
			medico = null;
		}catch (Exception e) {
			log.error("Error - getMedico", e);
			throw e;
		}
		
		MedicoFamiliar medicoFamiliar = MedicoFamiliarParser.persisToModel(medico);	
		
		return medicoFamiliar;
	}
	
	@Override
	public List<UnidadMedicaFamiliar> findUnidadesBySubdelegacion(Long idSubdelegacion) throws DerechohabientesBusinessException,Exception{
		DicSubdelegacion  entrada = null;
		try {
			entrada = em.find(DicSubdelegacion.class, idSubdelegacion);
		} catch(NoResultException e){
			entrada = null;
		} catch (Exception e) {
			log.error("Error - findUnidadesBySubdelegacion", e);
			throw e;
		}
		
		List<UnidadMedicaFamiliar> datos=UnidadMedicaFamiliarParser.persisToModelList(entrada.getDicUmfs());				
		return datos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CodigoPostal> findCodigosPostalesByUmf(Long idUmf) throws DerechohabientesBusinessException,Exception {
		// TODO Auto-generated method stub
		/*CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<DitUmfCodPo> query= cb.createQuery(DitUmfCodPo.class);
		Root<DitUmfCodPo> root = query.from(DitUmfCodPo.class); 
		query.select(root);
		
		Predicate conjunction = cb.conjunction();
		
		conjunction.getExpressions().add(cb.equal(root.get("dicUmf").get("cveIdUmf").as(Integer.class), idUmf)); 
		conjunction.getExpressions().add(cb.isNull(root.get("fecRegistroBaja")));
		query.where(conjunction);*/
		
		Criteria queryUmfCp = this.getSession().createCriteria(DitUmfCodPo.class);
		queryUmfCp.createAlias("dicUmf", "umf");
		queryUmfCp.add(Restrictions.eq("umf.cveIdUmf", idUmf));
		queryUmfCp.add(Restrictions.isNull("fecRegistroBaja"));
		
		
		List<CodigoPostal> salida = new ArrayList<CodigoPostal>();
		
		try {
			//List<DitUmfCodPo> entrada = em.createQuery(query).getResultList();
			
			List<DitUmfCodPo> entrada = queryUmfCp.list();
			for(DitUmfCodPo codigo: entrada) {
				salida.add(CodigoPostalParser.persistToModel(codigo.getDgCodigosPostale()));
			}
		} catch (Exception e) {
			log.error("Error - findCodigosPostalesByUmf", e);
			throw e;
		}
		
		
		return salida;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Asentamiento> findAsentamientosByUmf(Long idUmf) throws DerechohabientesBusinessException,Exception {
		// TODO Auto-generated method stub
		/*CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<DitUmfCodPo> query= cb.createQuery(DitUmfCodPo.class);
		Root<DitUmfCodPo> root = query.from(DitUmfCodPo.class); 
		query.select(root);
		
		Predicate conjunction = cb.conjunction();*/
		List<Asentamiento> salida = new ArrayList<Asentamiento>();
		try {
			/*conjunction.getExpressions().add(cb.equal(root.get("dicUmf").get("cveIdUmf").as(Integer.class), idUmf)); 
			conjunction.getExpressions().add(cb.isNull(root.get("fecRegistroBaja")));
			query.where(conjunction);*/
			Criteria queryUmfCp = this.getSession().createCriteria(DitUmfCodPo.class);
			queryUmfCp.createAlias("dicUmf", "umf");
			queryUmfCp.add(Restrictions.eq("umf.cveIdUmf", idUmf));
			queryUmfCp.add(Restrictions.isNull("fecRegistroBaja"));
			
			//List<DitUmfCodPo> entrada = em.createQuery(query).getResultList();
			List<DitUmfCodPo> entrada = queryUmfCp.list();
			
			for(DitUmfCodPo codigo: entrada) {
				//salida.add(CodigoPostalParser.persistToModel(codigo.getDgCodigosPostale()));
				Asentamiento encontrado = AsentamientoParser.persisToModel(codigo.getDgCodigosPostale().getDgAsentamiento());
				encontrado.setCodigoPostal(new CodigoPostal());
				encontrado.getCodigoPostal().setCodigoPostal(codigo.getDgCodigosPostale().getId().getCodigo());
				salida.add(encontrado);
			}
		} catch (Exception e) {
			log.error("Error - findAsentamientosByUmf", e);
			throw e;
		}
		
		
		return salida;
	}


	/**metodo que consulta las UMF asociadas a una subdelegacion filtrando el nivel de atencion en caso de ser nulo no se concidera como filtro**
	 * 
	 * @param idSubdelegacion
	 * @param nivelAtencion
	 * @return
	 * @throws DerechohabientesBusinessException
	 * @throws Exception
	 */
	@Override
	public List<UnidadMedicaFamiliar> findUnidadesBySubdelegacionNivelAtencion(Long idSubdelegacion, Long nivelAtencion)
			throws DerechohabientesBusinessException, Exception {
	try {
			
			Criteria queryUmf = this.getSession().createCriteria(DicUmf.class);
			
			queryUmf.createAlias("dicSubdelegacion", "subDel");
			queryUmf.add(Restrictions.eq("subDel.cveIdSubdelegacion", idSubdelegacion));
			queryUmf.add(Restrictions.isNull("fecRegistroBaja"));
			if(nivelAtencion != null) {
				queryUmf.createAlias("accNivelAtencion", "nivel");
				queryUmf.add(Restrictions.eq("nivel.cveIdNivelAtencion", nivelAtencion));
			}
			List<DicUmf>  lstUmf = queryUmf.list();

			return UnidadMedicaFamiliarParser.persisToModelList(lstUmf);
				
			
		} catch (Exception e) {
			log.error("Ocurrio unn Error al querer consultar las UMF - findUnidadesBySubdelegacionNivelAtencio", e);
			throw e;
		}

		
	}
	
	
	
	
}
