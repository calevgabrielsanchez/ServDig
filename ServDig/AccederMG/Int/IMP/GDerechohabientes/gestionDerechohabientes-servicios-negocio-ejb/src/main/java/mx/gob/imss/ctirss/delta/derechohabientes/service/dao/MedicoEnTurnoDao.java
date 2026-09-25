package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.parser.MedicoEnTurnoParserServiceLocal;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Consultorio;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoFamiliar;
import mx.gob.imss.ctirss.delta.persistence.DitGrupoFamiliar;
import mx.gob.imss.ctirss.delta.persistence.DitUmfConsTurnoMedico;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

@Stateless(name = "medicoEnTurnoDao", mappedName = "medicoEnTurnoDao")
public class MedicoEnTurnoDao extends AbstractServiceEntity implements MedicoEnTurnoDaoLocal{
	
	@EJB
	private MedicoEnTurnoParserServiceLocal medicoEnTurnoParserServiceLocal;
	
	@Override
	public MedicoEnTurno getMedicoEnTurnoById(Long idMedicoEnTurno){
		
		DitUmfConsTurnoMedico ditUmfConsTurno = this.em.find(DitUmfConsTurnoMedico.class, idMedicoEnTurno);
		
		try {
			if(ditUmfConsTurno != null) {
				return medicoEnTurnoParserServiceLocal.persisToModel(ditUmfConsTurno);
			}
		} catch(DerechohabientesBusinessException e) {
			log.error("Ocurrio un error al consultar la relacion del medico en turno", e);
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DitUmfConsTurnoMedico> getMedicosEnTurnobyUmf(Long idUmf) throws Exception{
		
		List<DitUmfConsTurnoMedico> ditMedicoEnTurnos=null;
		try {
			Criteria queryMedicos = this.getSession().createCriteria(DitUmfConsTurnoMedico.class);
			queryMedicos.add(Restrictions.isNull("fecRegistroBaja"));
			
			Criteria queryUCT = queryMedicos.createCriteria("ditUmfConsultorioTurno");
			Criteria queryCU = queryUCT.createCriteria("dicConsultorioUmf");
			queryCU.createAlias("dicUmf", "umf");
			queryCU.add(Restrictions.eq("umf.cveIdUmf", idUmf));
			
			ditMedicoEnTurnos = queryMedicos.list();
			/*CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<DitUmfConsTurnoMedico> cQuery = cb.createQuery(DitUmfConsTurnoMedico.class);//resulado
			Root<DitUmfConsTurnoMedico> root = cQuery.from(DitUmfConsTurnoMedico.class);//from
			cQuery.select(root);//select
			Predicate conj=cb.conjunction();
			
			//delegacion
			conj.getExpressions().add(cb.equal(root.get("ditUmfConsultorioTurno").get("dicConsultorioUmf").get("dicUmf").get("cveIdUmf").as(Integer.class), idUmf));
			//fecha baja
			conj.getExpressions().add(cb.isNull(root.get("fecRegistroBaja")));
			cQuery.where(conj);
			
			ditMedicoEnTurnos=em.createQuery(cQuery).getResultList();*/
		} catch (Exception e) {
			log.error("Error - getMedicosEnTurnobyUmf",e);
			throw e;
		}
		
		return ditMedicoEnTurnos;
		
	}

	@Override
	public Long getPoblacionByIdConsturnoMedico(Long idUmfConsTurno){
		Long poblacion = 0L;
		Criteria query = this.getSession().createCriteria(DitGrupoFamiliar.class);
		query.setProjection(Projections.rowCount());
		query.createAlias("ditUmfConsTurnoMedico", "medico");
		query.add(Restrictions.eq("medico.cveIdUmfConsTurnoMed", idUmfConsTurno));
		
		poblacion = (Long) query.uniqueResult();
		
		return poblacion;
	}
	
	@Override
	public List<MedicoEnTurno> getMedicosPoblacionByUmfTurno(Long idUmf,
			Long idTurno) throws Exception {
		List<MedicoEnTurno> medicos = new ArrayList<MedicoEnTurno>();
		Session session = this.getSession();
		String query="select ctmumf.CVE_ID_UMF_CONS_TURNO_MED cve_relacion, med.NOM_NOMBRE nom, med.nom_primer_apellido apep," +
				"med.nom_segundo_apellido apem, cumf.DES_CONSULTORIO cons," +
				" tur.cve_id_turno cvetur,tur.DES_DESCRIPCION destur, count(fam.CVE_ID_PERSONA_INTEGRANTE) poblacion " +
				"from DIC_UMF umf , DIC_CONSULTORIO_UMF cumf, DIT_UMF_CONSULTORIO_TURNO ctumf,DIC_MEDICO med, DIT_MEDICO_ESPECIALIDAD emed," +
				"DIT_UMF_CONS_TURNO_MEDICO ctmumf, DIT_GRUPO_FAMILIAR fam, DIC_TURNO tur " +
				"where umf.CVE_ID_UMF = "+idUmf+" and umf.cve_id_umf = cumf.CVE_ID_UMF and tur.CVE_ID_TURNO = "+idTurno+ " and tur.CVE_ID_TURNO = ctumf.CVE_ID_TURNO " +
				"and cumf.CVE_ID_UMF_CONSULTORIO = ctumf.CVE_ID_UMF_CONSULTORIO and med.CVE_ID_MEDICO = emed.CVE_ID_MEDICO " +
				"and ctumf.CVE_ID_UMF_CONS_TURNO = ctmumf.CVE_ID_UMF_CONS_TURNO and emed.CVE_ID_MEDICO_ESPECIALIDAD = ctmumf.CVE_ID_MEDICO_ESPECIALIDAD " +
				"and ctmumf.CVE_ID_UMF_CONS_TURNO_MED = fam.CVE_ID_UMF_CONS_TURNO_MED(+) " +
				"group by ctmumf.CVE_ID_UMF_CONS_TURNO_MED, med.NOM_NOMBRE,med.nom_primer_apellido,med.nom_segundo_apellido, " +
				"cumf.DES_CONSULTORIO, tur.cve_id_turno,tur.DES_DESCRIPCION order by cumf.DES_CONSULTORIO";
		
		SQLQuery queryMedico = session.createSQLQuery(query);
		@SuppressWarnings("unchecked")
		List<Object[]> resultado = (List<Object[]>)queryMedico.list();
		for(Object[] fila: resultado) {
			MedicoEnTurno medico = new MedicoEnTurno();
			log.info("Clave de la relacion " + (BigDecimal)fila[0]);
			medico.setIdMedicoContultorioTurno(((BigDecimal)fila[0]).longValue());
			medico.setConsultorio(new Consultorio());
			medico.getConsultorio().setDescripcion((String)fila[4]);
			medico.setMedicoFamiliar(new MedicoFamiliar());
			medico.getMedicoFamiliar().setNombre((String)fila[1]);
			medico.getMedicoFamiliar().setPrimerApellido((String)fila[2]);
			medico.getMedicoFamiliar().setSegundoApellido((String)fila[3]);
			medico.setPoblacion(((BigDecimal)fila[7]).longValue());
			medicos.add(medico);
		}
		
		return medicos;
	}
	
	
}
