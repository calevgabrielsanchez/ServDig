package mx.gob.imss.ctirss.delta.cobranza.service.entity;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.cobranza.modelo.AdeudoFiscal;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.util.CollectionUtils;

@Stateless(name = "cartaNoAdeudoEntity", mappedName = "cartaNoAdeudoEntity")
public class CartaNoAdeudoEntity 
	extends AbstractEntity implements CartaNoAdeudoEntityLocal{

	@SuppressWarnings("unchecked")
	@Override
	public List<AdeudoFiscal> obtenerAdeudosPorRP(
			SujetoObligado sujetoObligado) {
		
		Query query = this.getSession().createSQLQuery(getConsultaAdeudosPorRP().toString());
		query.setResultTransformer(Transformers.aliasToBean(AdeudoFiscal.class));
		query.setParameter("regPatron", sujetoObligado.getNumeroRegistroPatronal());
		query.setParameter("modalidad", sujetoObligado.getModalidad().getNumModalidad());
		query.setParameter("montoMinCreditos", 100);
		
		List<AdeudoFiscal> adeudos = query.list();
		
		StringBuffer nrp = new StringBuffer();
		nrp.append(sujetoObligado.getNumeroRegistroPatronal());
		
		if (sujetoObligado.getNumeroRegistroPatronal().length() == 8) {
			nrp.append(" ");
			nrp.append(sujetoObligado.getModalidad().getNumModalidad());
			nrp.append(" ");
			nrp.append(sujetoObligado.getDigVerificador());
		} else if (sujetoObligado.getNumeroRegistroPatronal().length() == 10) {
			nrp.append(" ");
			nrp.append(sujetoObligado.getDigVerificador());
		} 
		
		if(!CollectionUtils.isEmpty(adeudos)){
			Subdelegacion subDelegacion = null;
			String desSubdelegacion = null;
			String desDelegacion = null;
			
			if (sujetoObligado.getSubdelegacion() != null
					&& sujetoObligado.getSubdelegacion().getDescripcion() != null) {
				
				subDelegacion = sujetoObligado.getSubdelegacion();
				desSubdelegacion = StringUtils.leftPad(
						subDelegacion.getClave(), 2, '0')
						+ " "
						+ subDelegacion.getDescripcion();
			
				if (subDelegacion.getDelegacion() != null) {
					desDelegacion = StringUtils.leftPad(subDelegacion
							.getDelegacion().getClave(), 2, '0');
				}
			}
			
			for (AdeudoFiscal adeudo : adeudos) {
				adeudo.setNrp(nrp.toString());
				adeudo.setDesSubdelegacion(desSubdelegacion);
				adeudo.setDesDelegacion(desDelegacion);
				adeudo.setNumeroCredito(StringUtils.leftPad(
						adeudo.getNumeroCredito(), 9, '0'));
			}
			
			return  adeudos;
		}		
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SujetoObligado> obtenerPatronesParaCartaNoAdeudo(Persona persona) {
		
		long tipoPersona = persona.getTipoPersona().getIdTipoPersona().longValue();
		
//		String selectQuery = "SELECT PGRAL.REG_PATRON AS \"nrp\", MOD.NUM_MODALIDAD AS \"modalidad\", PGRAL.DIG_VER AS \"digVerif\", "
//				+ "LPAD(SUBD.CVE_ID_DELEGACION, 2, '0' ) AS \"numDelegacion\", LPAD(SUBD.CLAVE_SUBDELEGACION, 2, '0' ) AS \"numSubdelegacion\", "
//				+ "SUBD.DES_SUBDELEGACION AS \"descSubdelegacion\", PTMP.NUM_TRA_VIG_PERM + PTMP.NUM_TRA_VIG_EVEN + PTMP.NUM_TRA_VIG_CONS + "
//				+ "PTMP.NUM_TRA_MEX_EXTR + PTMP.ADIC_PENS AS \"numTrabajadores\", DECODE(DECODE(EXPAT.CVE_TIPO_MOVTO, NULL, 0, EXPAT.CVE_TIPO_MOVTO),"
//				+ " 2 , 1, 0) AS \"indBaja\", DECODE(DECODE(EXPAT.FEC_INI_HUELGA, NULL, '01/01/0001', TO_CHAR(EXPAT.FEC_INI_HUELGA, 'dd/mm/yyyy')), '01/01/0001' , 0, 1)"
//				+ " AS \"indHuelga\", DECODE(EXPAT.CVE_TIPO_MOVTO, NULL, 'INF_INCOMPLETA', TMOV.DES_TIPO_MOVIMIENTO) AS \"desTipoMov\" FROM DIT_PATRON_SUJETO_OBLIGADO PSO ";
		
		String selectQuery = "SELECT DISTINCT PTMP.CVE_PATRON AS \"nrp\", PTMP.CVE_MODALIDAD AS \"modalidad\", PTMP.DIG_VERIFICADOR AS \"digVerif\", "
				+ "LPAD(PTMP.CVE_DELEGACION, 2, '0' ) AS \"numDelegacion\", LPAD(PTMP.CVE_SUBDELEGACION, 2, '0' ) AS \"numSubdelegacion\", "
				+ "SUBD.DES_SUBDELEGACION AS \"descSubdelegacion\", PTMP.NUM_TRA_VIG_PERM + PTMP.NUM_TRA_VIG_EVEN + PTMP.NUM_TRA_VIG_CONS + "
				+ "PTMP.NUM_TRA_MEX_EXTR + PTMP.ADIC_PENS AS \"numTrabajadores\", DECODE(DECODE(PTMP.CVE_TIPO_MOVTO, NULL, 0, PTMP.CVE_TIPO_MOVTO), "
				+ "2 , 1, 0) AS \"indBaja\", DECODE(DECODE(PTMP.FEC_INI_HUELGA, NULL, '01/01/0001', TO_CHAR(PTMP.FEC_INI_HUELGA, 'dd/mm/yyyy')), '01/01/0001' , 0, 1) "
				+ "AS \"indHuelga\", DECODE(PTMP.CVE_TIPO_MOVTO, NULL, 'INF_INCOMPLETA', TMOV.DES_TIPO_MOVIMIENTO) AS \"desTipoMov\" FROM MGCARGA1.PATRONES_TEMP_INC PTMP "; 
	    		
	    
		
//		String joinsQuery = "INNER JOIN DIT_PATRON_GENERAL PGRAL ON PSO.CVE_ID_PATRON_SUJETO_OBLIGADO = PGRAL.CVE_ID_PATRON_SUJETO_OBLIGADO "
//				+ "INNER JOIN DIC_MODALIDAD MOD ON PSO.CVE_ID_MODALIDAD = MOD.CVE_ID_MODALIDAD "
//				+ "LEFT JOIN DIT_DELSUB_PAT_SUJ_OBLIG DSPSO ON PSO.CVE_ID_PATRON_SUJETO_OBLIGADO =  DSPSO.CVE_ID_PATRON_SUJETO_OBLIGADO "
//				+ "LEFT JOIN DIC_SUBDELEGACION SUBD ON DSPSO.CVE_ID_SUBDELEGACION = SUBD.CVE_ID_SUBDELEGACION "
//				+ "LEFT JOIN DIT_DTS_EXTRA_PATRON EXPAT ON PGRAL.CVE_ID_PATRON_GENERAL = EXPAT.CVE_ID_PATRON_GENERAL "
//				+ "LEFT JOIN DIC_TIPO_MOVTO_PAT_SUJOBLIG TMOV ON EXPAT.CVE_TIPO_MOVTO = TMOV.CVE_ID_TIPO_MOVTO_PAT_SUJOBLIG "
//				+ "LEFT JOIN PATRONES_TEMP_INC PTMP ON PGRAL.REG_PATRON = PTMP.CVE_PATRON AND MOD.NUM_MODALIDAD = PTMP.CVE_MODALIDAD ";
		
		String joinsQuery = " INNER JOIN MGPBDTU9X.DIT_LLAVE_PATRON LLAV ON PTMP.CVE_PATRON||PTMP.CVE_MODALIDAD = LLAV.REF_BUSCA"
			    + " LEFT JOIN MGPBDTU9X.DIT_DELSUB_PAT_SUJ_OBLIG DSPSO ON LLAV.CVE_ID_PATRON_SUJETO_OBLIGADO  =  DSPSO.CVE_ID_PATRON_SUJETO_OBLIGADO"
			    + " LEFT JOIN MGPBDTU9X.DIC_SUBDELEGACION SUBD ON DSPSO.CVE_ID_SUBDELEGACION = SUBD.CVE_ID_SUBDELEGACION"
			    + " LEFT JOIN MGPBDTU9X.DIC_TIPO_MOVTO_PAT_SUJOBLIG TMOV ON PTMP.CVE_TIPO_MOVTO = TMOV.CVE_ID_TIPO_MOVTO_PAT_SUJOBLIG ";

//		String orderQuery = "ORDER BY 4,5,1 ";
		
		/*
		StringBuffer sqlFisica = new StringBuffer(selectQuery);
		sqlFisica.append("INNER JOIN DIT_PERSONA_FISICA PF ON PF.CVE_ID_PERSONA_FISICA = PSO.CVE_ID_PERSONA_FISICA ");
		sqlFisica.append(joinsQuery);
		sqlFisica.append("WHERE PF.RFC = :rfc");
		sqlFisica.append(orderQuery);
		
		StringBuffer sqlMoral = new StringBuffer(selectQuery);
		sqlMoral.append("INNER JOIN DIT_PERSONA_MORAL PM ON PM.CVE_ID_PERSONA_MORAL = PSO.CVE_ID_PERSONA_MORAL ");
		sqlMoral.append(joinsQuery);
		sqlMoral.append("WHERE PM.RFC = :rfc");
		sqlMoral.append(orderQuery);*/
		
		StringBuffer sqlUnion = new StringBuffer();
		sqlUnion.append("select * from (");
		sqlUnion.append(selectQuery);
//		sqlUnion.append("INNER JOIN DIT_PERSONA_FISICA PF ON PF.CVE_ID_PERSONA_FISICA = PSO.CVE_ID_PERSONA_FISICA ");
		sqlUnion.append(joinsQuery);
//		sqlUnion.append("WHERE PF.RFC = :rfc ");
//		sqlUnion.append(" UNION ");
//		sqlUnion.append(selectQuery);
//		sqlUnion.append("INNER JOIN DIT_PERSONA_MORAL PM ON PM.CVE_ID_PERSONA_MORAL = PSO.CVE_ID_PERSONA_MORAL ");
//		sqlUnion.append(joinsQuery);
		sqlUnion.append(" WHERE PTMP.RFC = :rfc ");
		sqlUnion.append(") ");
//		sqlUnion.append(orderQuery);
		

				
		Query query = this.getSession().createSQLQuery(sqlUnion.toString())
				.setResultTransformer(Patrones32DResultTransformer.INSTANCE);
		query.setParameter("rfc", persona.getRfc());
		
		List<SujetoObligado> patrones = query.list();
				
		return patrones;
	}
	
	private StringBuilder getConsultaAdeudosPorRP(){
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT ");
		sql.append("REG_PATRONAL AS \"nrp\", ");
		sql.append("MODALIDAD AS \"modalidad\", ");
		sql.append("CREDITO AS \"numeroCredito\", ");
		sql.append("TO_CHAR(TO_DATE(PERIODO, 'yyyymm'),'mm/yyyy') AS \"periodo\", ");
		sql.append("TO_DATE(PERIODO, 'yyyymm') AS \"periodoTmp\", ");
		sql.append("TOTAL_ADEUDO AS \"saldoTotal\", ");
		sql.append("CASE ");
		sql.append("WHEN INC_ACT >= 31 AND INC_ACT <= 43 THEN 'Vigente' ");
		sql.append("WHEN INC_ACT = 9  THEN 'Vigente' ");
		sql.append("WHEN INC_ACT = 12 THEN 'Huelga' ");
		sql.append("END AS \"situacion\", ");
		sql.append("'IMSS' AS \"origen\" ");
		sql.append("FROM H_COP_CREDITOS_TOT ");
		sql.append("WHERE REG_PATRONAL = :regPatron AND MODALIDAD = :modalidad ");
		sql.append("AND (INC_ACT BETWEEN 31 AND 43 ");
		sql.append("OR INC_ACT IN (9,12)) ");
		sql.append("AND TOTAL_ADEUDO > :montoMinCreditos ");
		sql.append("UNION ALL ");
		sql.append("SELECT ");
		sql.append("REG_PATRONAL AS \"nrp\", ");
		sql.append("MODALIDAD AS \"modalidad\", ");
		sql.append("CREDITO AS \"numeroCredito\", ");
		sql.append("TO_CHAR(TO_DATE(PERIODO, 'yyyymm'),'mm/yyyy') AS \"periodo\", ");
		sql.append("TO_DATE(PERIODO, 'yyyymm') AS \"periodoTmp\", ");
		sql.append("TOTAL_ADEUDO AS \"saldoTotal\", ");
		sql.append("CASE ");
		sql.append("WHEN INC_ACT >= 31 AND INC_ACT <= 43 THEN 'Vigente' ");
		sql.append("WHEN INC_ACT = 9  THEN 'Vigente' ");
		sql.append("WHEN INC_ACT = 12 THEN 'Huelga' ");
		sql.append("END AS \"situacion\", ");
		sql.append("'RCV' AS \"origen\" ");
		sql.append("FROM H_RCV_CREDITOS_TOT ");
		sql.append("WHERE REG_PATRONAL = :regPatron AND MODALIDAD = :modalidad ");
		sql.append("AND (INC_ACT BETWEEN 31 AND 43 ");
		sql.append("OR INC_ACT IN (9,12)) ");
		sql.append("AND TOTAL_ADEUDO > :montoMinCreditos ");
		sql.append("UNION ALL ");
		sql.append("SELECT ");
		sql.append("CVE_PATRON AS \"nrp\", ");
		sql.append("TO_CHAR(CVE_MODALIDAD) AS \"modalidad\", ");
		sql.append("TO_CHAR(NUM_CREDITO) AS \"numeroCredito\", ");
		sql.append("TO_CHAR(TO_DATE(NUM_PERIODO, 'yyyymm'),'mm/yyyy') AS \"periodo\", ");
		sql.append("TO_DATE(NUM_PERIODO, 'yyyymm') AS \"periodoTmp\", ");
		sql.append("IMP_SALDO_TOT_CRED AS \"saldoTotal\", ");
		sql.append("'B-251' AS \"situacion\", ");
		sql.append("CASE ");
		sql.append("WHEN TIP_CUOTA_IMSS_RCV = 1  THEN 'IMSS' ");
		sql.append("WHEN TIP_CUOTA_IMSS_RCV = 2 THEN 'RCV' ");
		sql.append("END AS \"origen\" ");
		sql.append("FROM DIT_CREDITOS_CANCELADOS ");
		sql.append("WHERE CVE_PATRON = :regPatron AND CVE_MODALIDAD = :modalidad AND ");
		sql.append("IND_CRED_REGULARIZADO = 0 ");
		sql.append("AND IMP_SALDO_TOT_CRED > :montoMinCreditos ");
		sql.append("ORDER BY 5,3 ");
				
		return sql;
	}

    @SuppressWarnings("unchecked")
    @Override
    public Boolean validaJuicioEnProceso(String rfc) {
        StringBuffer sqlUnion = new StringBuffer();

        sqlUnion.append("SELECT juicio.RFC FROM MGCARGA1.COT_JUICIOPROCESO juicio ");
        sqlUnion.append("WHERE TRIM(RFC) = :rfc AND ROWNUM < 2");

        Boolean respuesta = false;

		String result = (String) this.getSession().createSQLQuery(sqlUnion.toString())
				.setParameter("rfc", rfc).uniqueResult();

		if(result!=null && !result.trim().equals("")){
			respuesta = true;
		}

        return respuesta;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Boolean validaAuditoriaEnProceso(String rfc) {
        StringBuffer sqlUnion = new StringBuffer();

        sqlUnion.append("SELECT auditoria.RFC FROM MGCARGA1.H_VISITAS_AUDITORIA auditoria ");
        sqlUnion.append("WHERE TRIM(RFC) = :rfc AND ROWNUM < 2");

        Boolean respuesta = false;

		String result = (String) this.getSession().createSQLQuery(sqlUnion.toString())
				.setParameter("rfc", rfc).uniqueResult();

		if(result!=null && !result.trim().equals("")){
			respuesta = true;
		}

        return respuesta;
    }
    
    
    @SuppressWarnings("unchecked")
    @Override
    public Boolean validaConvenioEnProceso(String rfc) {
        StringBuffer sqlUnion = new StringBuffer();

        sqlUnion.append("SELECT convenio.RFC FROM MGCARGA1.H_CONV_RFC32D_PATRON convenio ");
        sqlUnion.append("WHERE TRIM(RFC) = :rfc AND ROWNUM < 2");

        Boolean respuesta = false;

		String result = (String) this.getSession().createSQLQuery(sqlUnion.toString())
				.setParameter("rfc", rfc).uniqueResult();

		if(result!=null && !result.trim().equals("")){
			respuesta = true;
		}

        return respuesta;
    }
}
