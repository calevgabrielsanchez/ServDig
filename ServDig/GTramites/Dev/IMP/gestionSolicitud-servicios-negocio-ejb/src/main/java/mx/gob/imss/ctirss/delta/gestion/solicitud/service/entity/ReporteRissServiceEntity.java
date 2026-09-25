package mx.gob.imss.ctirss.delta.gestion.solicitud.service.entity;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.gestion.integracion.common.ReporteRissWrapper;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;

@Stateless
public class ReporteRissServiceEntity extends AbstractServiceEntity implements
		ReporteRissServiceEntityLocal {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ReporteRissWrapper> consultarSolicitudesRiss(Date fechaInicio,
			Date fechaFin) {
		
		StringBuffer consulta = new StringBuffer();
		consulta.append("SELECT SOL.CVE_ID_SOLICITUD AS \"idSolicitud\", ");
		consulta.append("SOL.REF_FOLIO AS \"folio\", ");
		consulta.append("DECODE(SOL.REF_OBSERVACION, NULL, ' ', SOL.REF_OBSERVACION) AS \"observaciones\", ");
		consulta.append("SOL.FEC_SOLICITUD AS \"fecha\", ");
		consulta.append("OSOL.DES_ORIGEN_SOLICITUD AS \"origen\", ");
		consulta.append("ESOL.DES_ESTADO_SOLICITUD AS \"estado\", ");
		consulta.append("DECODE(xmltype(DT.REF_DATOS_TRAMITE_XML).extract('//./respuestaRifSat/tipoApartadoBeneficio/text()').getStringVal(), NULL, ' ', ");
		consulta.append("xmltype(DT.REF_DATOS_TRAMITE_XML).extract('//./respuestaRifSat/tipoApartadoBeneficio/text()').getStringVal() ||  ");
		consulta.append("DECODE(xmltype(DT.REF_DATOS_TRAMITE_XML).extract('//./respuestaRifSat/indicadorC/text()').getStringVal(), NULL, ' ', ");
		consulta.append("DECODE (xmltype(DT.REF_DATOS_TRAMITE_XML).extract('//./respuestaRifSat/indicadorC/text()').getStringVal(), 'true', '-C', ' ' ))) ");
		consulta.append("AS \"apartado\", ");
		consulta.append("xmltype(DT.REF_DATOS_TRAMITE_XML).extract('//./respuestaRifSat/indicadorC/text()').getStringVal() ");
		consulta.append("as \"esApartadoC\", ");
		consulta.append("TPF.CVE_ID_PERSONA AS \"idPersona\", ");
		consulta.append("P.RFC AS \"rfcPersona\",  ");
		consulta.append("NSS.NUM_NSS AS \"nss\", ");
		consulta.append("TPSO.CVE_ID_PATRON_SUJETO_OBLIGADO AS \"idSujOblig\", ");
		consulta.append("PF.RFC AS \"rfcPersonaFisica\", ");
		consulta.append("PGRAL.REG_PATRON || M.NUM_MODALIDAD || PGRAL.DIG_VER AS \"nrp\", ");
		consulta.append("xmltype(DT.REF_DATOS_TRAMITE_XML).extract('//./fisica/rfc/text()').getStringVal() AS \"rfcXML\", ");
		consulta.append("xmltype(DT.REF_DATOS_TRAMITE_XML).extract('//./fisica/@nss').getStringVal() as \"nssXML\" ");
		consulta.append("FROM DIT_SOLICITUD SOL ");
		consulta.append("INNER JOIN DIC_ORIGEN_SOLICITUD OSOL ");
		consulta.append("ON SOL.CVE_ID_ORIGEN_SOLICITUD = OSOL.CVE_ID_ORIGEN_SOLICITUD ");
		consulta.append("INNER JOIN DIC_ESTADO_SOLICITUD ESOL ");
		consulta.append("ON SOL.CVE_ID_ESTADO_SOLICITUD = ESOL.CVE_ID_ESTADO_SOLICITUD ");
		consulta.append("INNER JOIN DIT_TRAMITE T ");
		consulta.append("ON SOL.CVE_ID_SOLICITUD = T.CVE_ID_SOLICITUD ");
		consulta.append("INNER JOIN DIT_DETALLE_TRAMITE DT ");
		consulta.append("ON T.CVE_ID_TRAMITE = DT.CVE_ID_TRAMITE ");
		consulta.append("LEFT JOIN DIT_TRAMITE_PERSONA_FISICA TPF ");
		consulta.append("ON T.CVE_ID_TRAMITE = TPF.CVE_ID_TRAMITE ");
		consulta.append("LEFT JOIN DIT_PERSONA P ");
		consulta.append("ON TPF.CVE_ID_PERSONA = P.CVE_ID_PERSONA ");
		consulta.append("LEFT JOIN DIT_ASIGNACION_NSS  NSS ");
		consulta.append("ON P.CVE_ID_PERSONA = NSS.CVE_ID_PERSONA ");
		consulta.append("LEFT JOIN DIT_TRAMITE_PAT_SUJ_OBLIGADO TPSO ");
		consulta.append("ON T.CVE_ID_TRAMITE = TPSO.CVE_ID_TRAMITE ");
		consulta.append("LEFT JOIN DIT_PATRON_SUJETO_OBLIGADO PSO ");
		consulta.append("ON TPSO.CVE_ID_PATRON_SUJETO_OBLIGADO = PSO.CVE_ID_PATRON_SUJETO_OBLIGADO ");
		consulta.append("LEFT JOIN DIT_PERSONA_FISICA PF ");
		consulta.append("ON (P.CVE_ID_PERSONA = PF.CVE_ID_PERSONA_FISICA ");
		consulta.append("OR PSO.CVE_ID_PERSONA_FISICA = PF.CVE_ID_PERSONA_FISICA) ");
		consulta.append("LEFT JOIN DIT_PATRON_GENERAL PGRAL ");
		consulta.append("ON PSO.CVE_ID_PATRON_SUJETO_OBLIGADO = PGRAL.CVE_ID_PATRON_SUJETO_OBLIGADO ");
		consulta.append("LEFT JOIN DIC_MODALIDAD M ");
		consulta.append("ON PSO.CVE_ID_MODALIDAD = M.CVE_ID_MODALIDAD ");
		consulta.append("WHERE SOL.CVE_ID_TIPO_SOLICITUD = :tipoSolRiss ");
		consulta.append("AND TRUNC(SOL.FEC_REGISTRO_ALTA) BETWEEN TRUNC(:fechaInicio) AND TRUNC(:fechaFin) ");
		consulta.append("AND SOL.CVE_ID_ESTADO_SOLICITUD != :estadoEnProceso ");
		consulta.append("ORDER BY SOL.CVE_ID_SOLICITUD DESC");

		Query query = this.getSession().createSQLQuery(consulta.toString())
				.setResultTransformer(Transformers.aliasToBean(ReporteRissWrapper.class));
		query.setParameter("tipoSolRiss",
				TipoSolicitudEnum.INCORPORACION_BENEFICIO.getValor());
		query.setParameter("fechaInicio", fechaInicio);
		query.setParameter("fechaFin", fechaFin);
		query.setParameter("estadoEnProceso", EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo());

		List<ReporteRissWrapper> resultados = query.list();

		return resultados;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ReporteRissWrapper> consultarMediosContactoNssRiss() {
		
		StringBuffer consulta = new StringBuffer();
		consulta.append("select nss.NUM_NSS  as \"nss\", ");
		consulta.append("pb.FEC_REGISTRO_ALTA as \"fecha\", ");
		consulta.append("edob.DES_ESTADO_BENEFICIO AS \"estado\", ");
		consulta.append("trim(replace(fc.DES_FORMA_CONTACTO,'|','')) as \"descMedioContacto\", ");
		consulta.append("tc.DES_TIPO_CONTACTO AS \"tipoMedioContacto\" ");
		consulta.append("from DIT_PERSONA_BENEFICIO pb ");
		consulta.append("inner join DIT_ASIGNACION_NSS nss ");
		consulta.append("on pb.CVE_ID_PERSONA = nss.CVE_ID_PERSONA ");
		consulta.append("inner join DIC_ESTADO_BENEFICIO edob ");
		consulta.append("on pb.CVE_ID_ESTADO_BENEFICIO = edob.CVE_ID_ESTADO_BENEFICIO ");
		consulta.append("left join DIT_PERSONAF_CONTACTO pcon ");
		consulta.append("on pb.CVE_ID_PERSONA = pcon.CVE_ID_PERSONA ");
		consulta.append("left join DIT_PERSONA_FISICA pf ");
		consulta.append("on pb.CVE_ID_PERSONA = pf.CVE_ID_PERSONA ");
		consulta.append("left join DIT_PERSONAF_CONTACTO_FISCAL pconf ");
		consulta.append("on pf.CVE_ID_PERSONA_FISICA = pconf.CVE_ID_PERSONA_FISICA ");
		consulta.append("left join DIT_FORMA_CONTACTO fc ");
		consulta.append("on pcon.CVE_ID_FORMA_CONTACTO = fc.CVE_ID_FORMA_CONTACTO or ");
		consulta.append("pconf.CVE_ID_FORMA_CONTACTO = fc.CVE_ID_FORMA_CONTACTO ");
		consulta.append("left join DIT_TIPO_CONTACTO tc ");
		consulta.append("on fc.CVE_ID_TIPO_CONTACTO = tc.CVE_ID_TIPO_CONTACTO ");
		consulta.append("where fc.DES_FORMA_CONTACTO is not null ");
		consulta.append("and trunc(pb.FEC_REGISTRO_ALTA) <= trunc(sysdate - 1) ");
		consulta.append("group by nss.NUM_NSS,  ");
		consulta.append("pb.FEC_REGISTRO_ALTA, edob.DES_ESTADO_BENEFICIO, ");
		consulta.append("fc.DES_FORMA_CONTACTO, tc.DES_TIPO_CONTACTO ");
		consulta.append("order by 1 ");
		
		Query query = this.getSession().createSQLQuery(consulta.toString())
				.setResultTransformer(Transformers.aliasToBean(ReporteRissWrapper.class));
		
		List<ReporteRissWrapper> resultados = query.list();
		
		return resultados;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ReporteRissWrapper> consultarMediosDomicilioNrpRiss() {
		
		StringBuffer consulta = new StringBuffer();
		consulta.append("select pso.cve_id_patron_sujeto_obligado as \"idSujOblig\", ");
		consulta.append("pgral.reg_patron || mod.num_modalidad || pgral.dig_ver as \"nrp\", ");
		consulta.append("trim(replace(fc.DES_FORMA_CONTACTO,'|','')) as \"descMedioContacto\", ");
		consulta.append("tc.DES_TIPO_CONTACTO AS \"tipoMedioContacto\" ");
		consulta.append("from dit_tramite_pat_suj_obligado tpso ");
		consulta.append("inner join dit_tramite t ");
		consulta.append("on tpso.cve_id_tramite = t.cve_id_tramite ");
		consulta.append("inner join dit_patron_sujeto_obligado pso ");
		consulta.append("on tpso.cve_id_patron_sujeto_obligado = pso.cve_id_patron_sujeto_obligado ");
		consulta.append("inner join dit_patron_general pgral ");
		consulta.append("on pso.cve_id_patron_sujeto_obligado = pgral.cve_id_patron_sujeto_obligado ");
		consulta.append("inner join dic_modalidad mod ");
		consulta.append("on pso.cve_id_modalidad = mod.cve_id_modalidad ");
		consulta.append("left join dit_centro_trabajo_contacto ctc ");
		consulta.append("on pso.cve_id_patron_sujeto_obligado = ctc.cve_id_patron_sujeto_obligado ");
		consulta.append("left join DIT_FORMA_CONTACTO fc ");
		consulta.append("on  ctc.cve_id_forma_contacto = fc.CVE_ID_FORMA_CONTACTO ");
		consulta.append("left join DIT_TIPO_CONTACTO tc ");
		consulta.append("on fc.CVE_ID_TIPO_CONTACTO = tc.CVE_ID_TIPO_CONTACTO ");
		consulta.append("where t.cve_id_tipo_tramite = 91 ");
		consulta.append("and trim(replace(fc.DES_FORMA_CONTACTO,'|','')) is not null ");
		consulta.append("group by pso.cve_id_patron_sujeto_obligado, ");
		consulta.append("pgral.reg_patron || mod.num_modalidad || pgral.dig_ver, "); 
		consulta.append("fc.DES_FORMA_CONTACTO, tc.DES_TIPO_CONTACTO ");
		consulta.append("order by 2");
		
		Query query = this.getSession().createSQLQuery(consulta.toString())
				.setResultTransformer(Transformers.aliasToBean(ReporteRissWrapper.class));
		
		List<ReporteRissWrapper> resultados = query.list();
		
		return resultados;
		
	}
	
}
