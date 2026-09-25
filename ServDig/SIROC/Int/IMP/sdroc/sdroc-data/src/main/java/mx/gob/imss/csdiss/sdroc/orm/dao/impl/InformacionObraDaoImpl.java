package mx.gob.imss.csdiss.sdroc.orm.dao.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.imss.csdiss.sdroc.dto.EstatusObraDTO;
import mx.gob.imss.csdiss.sdroc.dto.InformacionObraDTO;
import mx.gob.imss.csdiss.sdroc.entity.RotInformacionObra;
import mx.gob.imss.csdiss.sdroc.orm.dao.InformacionObraDao;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

/**
 * 
 * Clase que implementa la interface ParametroDao para obtener los parametros
 * del sistema de la BD, mediante la utilizacion del patron DAO (Data Access
 * Object).
 * 
 * @author Brian Hernandez Garcia
 * 
 */
@Repository("InformacionObraDao")
@Transactional
public class InformacionObraDaoImpl extends AbstractDaoImpl<RotInformacionObra, Long> implements InformacionObraDao {
	
	public SujetoObligado getInfoPatron(String rp) {
		SujetoObligado so = null;
		String queryPatron = "select so.CVE_ID_PATRON_SUJETO_OBLIGADO, " +
		"persona.CVE_ID_PERSONA, persona.NOM_NOMBRE, persona.NOM_PRIMER_APELLIDO, persona.NOM_SEGUNDO_APELLIDO, fisica.RFC RFC_FISICA, " +
		"moral.CVE_ID_PERSONA_MORAL,moral.DENOMINACION_RAZON_SOCIAL, moral.RFC RFC_MORAL, subdel.CVE_ID_SUBDELEGACION, subdel.DES_SUBDELEGACION, " +
		"deleg.CVE_ID_DELEGACION, deleg.DES_DELEG, clasi.IND_REG_PAT_CLASE from dit_llave_patron llave " +
		"inner join DIT_PATRON_SUJETO_OBLIGADO so on so.CVE_ID_PATRON_SUJETO_OBLIGADO = llave.CVE_ID_PATRON_SUJETO_OBLIGADO " +
		"left outer join DIT_PERSONA_FISICA fisica on so.CVE_ID_PERSONA_FISICA = fisica.CVE_ID_PERSONA_FISICA " +
		"left outer join DIT_PERSONA persona on persona.CVE_ID_PERSONA = fisica.CVE_ID_PERSONA " +
		"left outer join DIT_PERSONA_MORAL moral on so.CVE_ID_PERSONA_MORAL = moral.CVE_ID_PERSONA_MORAL " +
		"inner join DIT_DELSUB_PAT_SUJ_OBLIG del_pat on so.CVE_ID_PATRON_SUJETO_OBLIGADO = del_pat.CVE_ID_PATRON_SUJETO_OBLIGADO " +
		"inner join DIC_SUBDELEGACION subdel on subdel.CVE_ID_SUBDELEGACION = del_pat.CVE_ID_SUBDELEGACION " +
		"inner join DIC_DELEGACION deleg on subdel.CVE_ID_DELEGACION = deleg.CVE_ID_DELEGACION " +
		"inner join DIT_CLASIFICACION clasi on so.CVE_ID_PATRON_SUJETO_OBLIGADO = clasi.CVE_ID_PATRON_SUJETO_OBLIGADO " +
		"where so.FEC_REGISTRO_BAJA is null and llave.REF_BUSCA='"+(rp.length() > 10 ? rp.substring(0, 10) : rp)+"' order by clasi.FEC_REGISTRO_ALTA desc";
		List<Object[]> soEntity = this.getSession().createSQLQuery(queryPatron).list();
		
		if(soEntity != null && !soEntity.isEmpty()){
			Object[] patron = soEntity.get(0);
			so = new SujetoObligado();
			so.setNumeroRegistroPatronal(rp);
			so.setCveIdSujetoObligado(((BigDecimal) patron[0]).longValue());
			boolean isFisica = patron[1]  != null;
			if(isFisica) {
				so.setFisica(new Fisica());
				so.getFisica().setIdPersona(((BigDecimal) patron[1]).longValue());
				so.getFisica().setNombre(getString(patron[2]));
				so.getFisica().setPrimerApellido(getString(patron[3]));
				so.getFisica().setSegundoApellido(getString(patron[4]));
				so.getFisica().setRfc(getString(patron[5]));
			} else {
				so.setMoral(new Moral());
				so.getMoral().setIdPersona(((BigDecimal) patron[6]).longValue());
				so.getMoral().setRazonSocial(getString(patron[7]));
				so.getMoral().setRfc(getString(patron[8]));
			}
			
			so.setSubdelegacion(new Subdelegacion());
			so.getSubdelegacion().setDelegacion(new Delegacion());
			
			so.getSubdelegacion().setId(((BigDecimal) patron[9]).longValue());
			so.getSubdelegacion().setDescripcion(getString(patron[10]));
			so.getSubdelegacion().getDelegacion().setId(((BigDecimal) patron[11]).longValue());
			so.getSubdelegacion().getDelegacion().setDescripcion(getString(patron[12]));
			
			so.setClasificacion(new Clasificacion());
			Integer isRPC = patron[13] != null ? ((BigDecimal) patron[13]).intValue() : 0;
			so.getClasificacion().setIndRegPatClase(isRPC);
			
		}
		
		return so;
	}
	
	private String getString(Object object) {
		return object != null ? (String) object : null;
	}
	
	
	public Map<String, Object> findObrasByRfcAndRp(String rfc, String rp, Long inicio, Long fin) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<InformacionObraDTO> obras = null;
		String queryPaginada = "";
		String queryTotal = "";
		Long total=0L;
		if(inicio != null) {
			if(inicio.intValue() != 0 && (fin != null && fin.intValue() != 0)) {
				queryPaginada+="select * from ("
						+ "select resultados_.*,"
						+ "rownum rownum_ from (";
			}else if(fin != null && fin.intValue() != 0){
				queryPaginada +="select * from (";
			}
		}
		String query = "select info_obra.CVE_ID_INFORMACION_OBRA rs0, info_obra.CVE_ID_TRAMITE rs1, info_obra.CVE_REGISTRO_OBRA rs2, "+
        "info_obra.CVE_ID_INFORMACION_OBRA_PRIN rs3, info_obra.FEC_FIN_CONTRATO rs4, info_obra.FEC_FIN_OBRA rs5, info_obra.FEC_INI_CONTRATO rs6, "+
        "info_obra.FEC_INI_OBRA rs7, info_obra.IMP_CONTRATADO rs8, info_obra.IMP_EJERCIDO rs9, info_obra.IMP_OBRA rs10, info_obra.num_actualiza rs11, "+
        "info_obra.NUM_LICITACION rs12, info_obra.NUM_PROCEDIMIENTO rs13, info_obra.CVE_ID_AVISO_OBRA rs14, info_obra.REF_OBSERVACION rs15, "+
        "info_obra.REF_SUP_CONSTRUCCION rs16, info_obra.CVE_ID_ESTATUS_OBRA rs17, info_obra.CVE_ID_OBJETO_CONTRATO rs18, info_obra.CVE_ID_SUBDELEGACION rs19, "+
                "info_obra.CVE_ID_TIPO_OBRA rs20, info_obra.CVE_ID_INFORMACION_PATRON rs21, info_obra.CVE_ID_UBICACION_OBRA rs22, estatus.DES_ESTATUS_OBRA rs23, info_patron.CVE_ID_TIPO_PATRON rs24 ";
		
        String where = "from ROT_INFORMACION_OBRA info_obra, ROT_INFORMACION_PATRON info_patron, ROC_ESTATUS_OBRA estatus "+
        "where info_obra.CVE_ID_INFORMACION_PATRON=info_patron.CVE_ID_INFORMACION_PATRON "+
        "and estatus.CVE_ID_ESTATUS_OBRA = info_obra.CVE_ID_ESTATUS_OBRA "+
        "and info_patron.ROT_RFC='"+rfc+"' and info_patron.ROT_REG_PATRONAL='"+rp+"' "+
        "and info_obra.CVE_ID_ESTATUS_OBRA not in  ( 3 , 5, 6)";
		
		queryPaginada += "" + query + " " + where;
		queryTotal = "select count(*) " + where;
		
		if(inicio != null) {
			if(inicio.intValue() != 0 && (fin != null && fin.intValue() != 0)) {
				queryPaginada+=" order by info_obra.CVE_ID_INFORMACION_OBRA asc ) resultados_ "
						+ "where rownum <=" + (inicio + fin ) +""
								+ " ) where rownum_ > " + inicio; 
			} else if(fin != null && fin.intValue() != 0){
				queryPaginada+=" order by info_obra.CVE_ID_INFORMACION_OBRA asc) where rownum <= " + fin +  " ";
			}
		} else {
			queryPaginada +=  " order by info_obra.CVE_ID_INFORMACION_OBRA asc";
		}
		List<Object[]> obrasEntity = this.getSession().createSQLQuery(queryPaginada).list();
		if(obrasEntity != null && !obrasEntity.isEmpty()){
			obras = new ArrayList<InformacionObraDTO>();
			for(Object[] obraObject: obrasEntity) {
				InformacionObraDTO infoObra = new InformacionObraDTO();
				infoObra.setCveInformacionObra(((BigDecimal) obraObject[0]).longValue());
				infoObra.setCveIdTramite(obraObject[1] != null ? ((BigDecimal) obraObject[1]).longValue() : null);
				infoObra.setCveRegistroObra((String)obraObject[2]);
				infoObra.setCveRegistroObraPrincipal(obraObject[3] != null ? ((BigDecimal) obraObject[3]).longValue() : null);
				infoObra.setFecFinContrato(this.timeStampToDate(obraObject[4]));
				infoObra.setFecFinObra(this.timeStampToDate(obraObject[5]));
				infoObra.setFecIniContrato(this.timeStampToDate(obraObject[6]));
				infoObra.setFecIniObra(this.timeStampToDate(obraObject[7]));
				infoObra.setImpContratado(obraObject[8] != null ? ((BigDecimal) obraObject[8]).doubleValue() : null);
				infoObra.setImpEjercido(obraObject[9] != null ? ((BigDecimal) obraObject[9]).doubleValue() : null);
				infoObra.setImpObra(obraObject[10] != null ? ((BigDecimal) obraObject[10]).doubleValue() : null);
				infoObra.setNumActualiza(obraObject[11] != null ? ((BigDecimal) obraObject[11]).intValue() : null);
				infoObra.setNumLicitacion((String)obraObject[12]);
				infoObra.setNumProcedimiento((String)obraObject[13]);
				infoObra.setRefObservacion((String)obraObject[15]);
				infoObra.setRefSupConstruccion(obraObject[16] != null ? ((BigDecimal) obraObject[16]).doubleValue() : null);
				infoObra.setEstatusObraDTO(new EstatusObraDTO(obraObject[17] != null ? ((BigDecimal) obraObject[17]).longValue() : null, (String)obraObject[23]));
                infoObra.setCveTipoPatron(((BigDecimal) obraObject[24]).longValue());
				obras.add(infoObra);
			}
		}
		
		Object totalResult = (Object)this.getSession().createSQLQuery(queryTotal).uniqueResult();
		if(totalResult != null) {
			total = ((BigDecimal) totalResult).longValue();
		}
		
		result.put("total", total);
		result.put("obras", obras);
		return result;
	}
	
	private Date timeStampToDate(Object timeS) {
		
		Timestamp ts = (Timestamp) timeS;
		Calendar calendar = Calendar.getInstance();
		Date fecha = null;
		
		if(ts != null) {
			fecha = new Date(ts.getTime());
		}

		return fecha;		
	}

	public List<RotInformacionObra> findByRfcAndRp(String cveRfc, String cvRegPatronal) {

		List<RotInformacionObra> listRotInformacionObra = null;

		try {
			String sql = "SELECT informacionObra FROM RotInformacionObra informacionObra, RotInformacionPatron informacionPatron WHERE "
					+ "informacionObra.rotInformacionPatron.cveInformacionPatron = informacionPatron.cveInformacionPatron AND "
					+ "informacionPatron.cveRfc = :cveRfc AND informacionPatron.cveRegPatronal = :cvRegPatronal"
					+ " AND informacionObra.rocEstatusObra.cveEstatusObra not in (3,5)";

			Query query = getSession().createQuery(sql).setParameter("cveRfc", cveRfc).setParameter("cvRegPatronal",
					cvRegPatronal);

			listRotInformacionObra = this.findMany(query);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listRotInformacionObra;
	}

	public RotInformacionObra findByCveRegistroObra(String cveRegistroObra) {

		RotInformacionObra rotInformacionObra = null;
		try {
			String sql = "SELECT informacionObra FROM RotInformacionObra informacionObra WHERE "
					+ "informacionObra.cveRegistroObra = :cveRegistroObra ";

			Query query = getSession().createQuery(sql).setParameter("cveRegistroObra", cveRegistroObra);

			rotInformacionObra = this.findOne(query);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rotInformacionObra;
	}

	public void updateInformacionObran(Long cveInformacionObra, Long cveEstatusObra, String cadImpEjercido) {
		try {
			String sql = "UPDATE RotInformacionObra set rocEstatusObra.cveEstatusObra = :cveEstatusObra "
					+ cadImpEjercido + " WHERE cveInformacionObra = :cveInformacionObra";
			Query query = getSession().createQuery(sql).setParameter("cveEstatusObra", cveEstatusObra)
					.setParameter("cveInformacionObra", cveInformacionObra);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<RotInformacionObra> findByCveRfc(String cveRfc) {
		List<RotInformacionObra> listRotInformacionObra = null;

		try {
			String sql = "SELECT informacionObra FROM RotInformacionObra informacionObra, RotInformacionPatron informacionPatron WHERE "
					+ "informacionObra.rotInformacionPatron.cveInformacionPatron = informacionPatron.cveInformacionPatron AND "
					+ "informacionPatron.cveRfc = :cveRfc ";

			Query query = getSession().createQuery(sql).setParameter("cveRfc", cveRfc);

			listRotInformacionObra = this.findMany(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listRotInformacionObra;
	}

	public List<RotInformacionObra> findByCvRegPatronal(String cvRegPatronal) {
		List<RotInformacionObra> listRotInformacionObra = null;
		try {
			String sql = "SELECT informacionObra FROM RotInformacionObra informacionObra, RotInformacionPatron informacionPatron WHERE "
					+ "informacionObra.rotInformacionPatron.cveInformacionPatron = informacionPatron.cveInformacionPatron AND "
					+ "informacionPatron.cveRegPatronal = :cvRegPatronal";

			Query query = getSession().createQuery(sql).setParameter("cvRegPatronal", cvRegPatronal);

			listRotInformacionObra = this.findMany(query);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listRotInformacionObra;
	}

	public List<RotInformacionObra> findAllByCveRegistroObraPrincipal(Long cveRegistroObraPrincipal) {

		List<RotInformacionObra> listRotInformacionObra = null;
		try {
			String sql = "SELECT informacionObra FROM RotInformacionObra informacionObra WHERE informacionObra.cveRegistroObraPrincipal = :cveRegistroObraPrincipal ";

			Query query = getSession().createQuery(sql).setParameter("cveRegistroObraPrincipal",
					cveRegistroObraPrincipal);

			listRotInformacionObra = this.findMany(query);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listRotInformacionObra;

	}

	public int numInformacionObraByCveRfcAndAnio(String cveRfc, String anio) {

		Long numObrasPorAcveRfcAnio = null;

		try {
			String sql = "SELECT count(*) FROM RotInformacionObra informacionObra WHERE "
					+ "informacionObra.rotInformacionPatron.cveRfc = :cveRfc AND to_char(informacionObra.fecRegistroAlta,'YYYY') = :anio";

			numObrasPorAcveRfcAnio = (Long) this.getSession().createQuery(sql).setParameter("cveRfc", cveRfc)
					.setParameter("anio", anio).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return numObrasPorAcveRfcAnio.intValue();
	}

	public List<RotInformacionObra> findInformacionObraByCveRfcAndAnio(String cveRfc, String anio) {

		List<RotInformacionObra> listRotInformacionObra = null;

		try {
			String sql = "SELECT informacionObra FROM RotInformacionObra informacionObra WHERE "
					+ "informacionObra.rotInformacionPatron.cveRfc = :cveRfc AND to_char(informacionObra.fecRegistroAlta,'YYYY') = :anio";

			Query query = this.getSession().createQuery(sql).setParameter("cveRfc", cveRfc).setParameter("anio", anio);

			listRotInformacionObra = this.findMany(query);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listRotInformacionObra;
	}

	public List<Object[]> findAllByCveRfcGroupByCveRegPatronalAndSubdelegacionAndDelegacion(String cveRfc) {

		List<Object[]> listInformacionObra = null;

		try {
			String sql = "SELECT informacionObra.rotInformacionPatron.cveRegPatronal, informacionObra.rocSubdelegacion.rocDelegacion.nomDelegacion, "
					+ "informacionObra.rocSubdelegacion.nomSubdelegacion, count(informacionObra.rotInformacionPatron.cveRegPatronal) "
					+ " FROM RotInformacionObra informacionObra WHERE "
					+ "informacionObra.rotInformacionPatron.cveRfc = :cveRfc "
					+ " GROUP BY informacionObra.rotInformacionPatron.cveRegPatronal, informacionObra.rocSubdelegacion.rocDelegacion.nomDelegacion,"
					+ " informacionObra.rocSubdelegacion.nomSubdelegacion";

			Query query = getSession().createQuery(sql).setParameter("cveRfc", cveRfc);

			listInformacionObra = (List<Object[]>) this.findManyObject(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listInformacionObra;
	}

	public RotInformacionObra findByCveInformacionObra(Long cveInformacionObra) {
		RotInformacionObra rotInformacionObra = null;
		try {
			String sql = "SELECT informacionObra FROM RotInformacionObra informacionObra  WHERE "
					+ "informacionObra.cveInformacionObra = :cveInformacionObra";

			Query query = getSession().createQuery(sql).setParameter("cveInformacionObra", cveInformacionObra);

			rotInformacionObra = this.findOne(query);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rotInformacionObra;
	}

	public List<Object[]> findAllInformacionObraForReport(String cveRfc, String anio) {

		List<Object[]> resultadoConsulta = null;

		try {
			String sql = "SELECT patron.ROT_REG_PATRONAL, "
							  + "obra.CVE_REGISTRO_OBRA, "
							  + "(CASE WHEN obra.CVE_ID_INFORMACION_OBRA_PRIN IS NULL THEN (select CVE_REGISTRO_AVISO_OBRA from rot_aviso_obra where CVE_ID_AVISO_OBRA = obra.CVE_ID_AVISO_OBRA) ELSE  (SELECT obraPrincipal.CVE_REGISTRO_OBRA FROM ROT_INFORMACION_OBRA obraPrincipal WHERE obraPrincipal.CVE_ID_INFORMACION_OBRA = obra.CVE_ID_INFORMACION_OBRA_PRIN) END) ANTECEDENTE, "
							  + "clasificacion.DES_CLASIFICACION_OBRA, "
							  + "tipoPatron.DES_TIPO_PATRON, "
							  + "ubicacion.CALLE, "
							  + "ubicacion.NUM_EXTERIOR, "
							  + "ubicacion.NUM_EXTERIOR_ALF, "
							  + "ubicacion.NUM_INTERIOR, "
							  + "ubicacion.NUM_INTERIOR_ALF, "
							  + "ubicacion.REF_COLONIA, "
							  + "ubicacion.REF_MUNICIPIO, "
							  + "ubicacion.REF_ENTIDAD, "
							  + "ubicacion.CODIGO_POSTAL, "
							  + "tipoPatron.CVE_ID_TIPO_PATRON, "
							  + "obra.FEC_INI_OBRA, "
							  + "obra.FEC_FIN_OBRA, "
							  + "obra.FEC_INI_CONTRATO, "
							  + "obra.FEC_FIN_CONTRATO, "
							  + "NVL(OBRA.IMP_OBRA,0)IMP_OBRA "
							+ "FROM ROT_INFORMACION_OBRA obra , "
							  + "ROT_INFORMACION_PATRON patron, "
							  + "ROC_TIPO_OBRA tipoObra, "
							  + "ROC_CLASIFICACION_OBRA clasificacion, "
							  + "ROC_TIPO_PATRON tipoPatron, "
							  + "ROT_UBICACION_OBRA ubicacion "
							+ "WHERE obra.CVE_ID_TIPO_OBRA                 = tipoObra.CVE_ID_TIPO_OBRA "
							+ "AND tipoObra.CVE_ID_CLASIFICACION_OBRA      = clasificacion.CVE_ID_CLASIFICACION_OBRA "
							+ "AND obra.CVE_ID_INFORMACION_PATRON          = patron.CVE_ID_INFORMACION_PATRON "
							+ "AND patron.CVE_ID_TIPO_PATRON               = tipoPatron.CVE_ID_TIPO_PATRON "
							+ "AND obra.CVE_ID_UBICACION_OBRA              = ubicacion.CVE_ID_UBICACION_OBRA "
							+ "AND patron.ROT_RFC                          = :cveRfc "
							+ "AND TO_CHAR( obra.FEC_REGISTRO_ALTA,'YYYY') = :anio ";

			Query query = getSession().createSQLQuery(sql).setParameter("cveRfc", cveRfc).setParameter("anio", anio);

			resultadoConsulta = (List<Object[]>) this.findManyObject(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultadoConsulta;
	}

	public List<Object[]> findAllInformacionObraForReportByCvRegPatronal(String cvRegPatronal) {

		List<Object[]> resultadoConsulta = null;

		try {

			String sql = "SELECT obra.cve_registro_obra, "					
					+ "(case when obra.cve_id_informacion_obra_prin is null "
					+ " then (select CVE_REGISTRO_AVISO_OBRA  from ROT_AVISO_OBRA  where CVE_ID_AVISO_OBRA = obra.CVE_ID_AVISO_OBRA) "
					+ " else (SELECT obraprincipal.cve_registro_obra FROM rot_informacion_obra obraprincipal WHERE obraprincipal.cve_id_informacion_obra = obra.cve_id_informacion_obra_prin) end) antecedente, "
					+ "  clasificacion.des_clasificacion_obra, "
					+ "    tipopatron.des_tipo_patron, "
					+ "    ubicacion.calle, "
					+ "    ubicacion.num_exterior, "
					+ "    ubicacion.num_exterior_alf, "
					+ "    ubicacion.num_interior, "
					+ "    ubicacion.num_interior_alf, "
					+ "    ubicacion.ref_colonia, "
					+ "    ubicacion.ref_municipio, "
					+ "    ubicacion.ref_entidad, "
					+ "    ubicacion.codigo_postal, "
					+ "    tipopatron.cve_id_tipo_patron, "
					+ "    obra.fec_ini_obra, "
					+ "    obra.fec_fin_obra, "
					+ "    obra.fec_ini_contrato, "
					+ "    obra.fec_fin_contrato, "
					+ "    nvl(obra.imp_obra,0) imp_obra "
					+ "FROM "
					+ "    rot_informacion_obra obra, "
					+ "    rot_informacion_patron patron, "
					+ "    roc_tipo_obra tipoobra, "
					+ "    roc_clasificacion_obra clasificacion, "
					+ "    roc_tipo_patron tipopatron, "
					+ "    rot_ubicacion_obra ubicacion "
					+ "WHERE obra.cve_id_tipo_obra = tipoobra.cve_id_tipo_obra "
					+ "    AND tipoobra.cve_id_clasificacion_obra = clasificacion.cve_id_clasificacion_obra "
					+ "    AND obra.cve_id_informacion_patron = patron.cve_id_informacion_patron "
					+ "    AND patron.cve_id_tipo_patron = tipopatron.cve_id_tipo_patron "
					+ "    AND obra.cve_id_ubicacion_obra = ubicacion.cve_id_ubicacion_obra "
					+ "    AND patron.rot_reg_patronal = :cvRegPatronal ";

			Query query = getSession().createSQLQuery(sql).setParameter("cvRegPatronal", cvRegPatronal);

			resultadoConsulta = (List<Object[]>) this.findManyObject(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultadoConsulta;
	}

	public String getCveRegistroObra() {

		String secuencia = null;

		try {
			String sql = "SELECT 'C'||LPad(SEQ_CVEREGISTROOBRA.NEXTVAL, 7, '0') FROM dual";

			Query query = getSession().createSQLQuery(sql);

			secuencia = (String) this.findOneObjectBySqlQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return secuencia;
	}

	public RotInformacionObra consultaBloqueoRegistroObra(String cveRegistroObra) {
		RotInformacionObra rotInformacionObra = null;
		try {
			String sql = "SELECT informacionObra FROM RotInformacionObra informacionObra WHERE "
					+ "informacionObra.cveRegistroObra = '" + cveRegistroObra + "'";
			Query query = getSession().createQuery(sql);
			rotInformacionObra = this.findOne(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rotInformacionObra;
	}

	public void updateBloqueo(String cveIdUsuarioBloqueo, String cveIdInformacionObra) {

		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

			String fecha = format.format(Calendar.getInstance().getTime());
			String strFecha = " , obra.fecBloqueoObra=TO_DATE('" + fecha + "', 'yyyy/mm/dd hh24:mi:ss')";

			String sql = "UPDATE RotInformacionObra obra set obra.cveIdUsuarioBloqueo = "
					+ (cveIdUsuarioBloqueo == null ? null : "'" + cveIdUsuarioBloqueo + "'") + " "
					+ (cveIdUsuarioBloqueo == null ? ", obra.fecBloqueoObra=null " : strFecha)
					+ " WHERE obra.cveInformacionObra = " + cveIdInformacionObra + "";

			Query query = getSession().createQuery(sql);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void liberaObras(String cveIdUsuarioBloqueo) {
		try {
			String sql = "UPDATE RotInformacionObra obra set obra.cveIdUsuarioBloqueo = null, obra.fecBloqueoObra=null "
					+ " WHERE obra.cveIdUsuarioBloqueo = '" + cveIdUsuarioBloqueo + "'";

			Query query = getSession().createQuery(sql);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
