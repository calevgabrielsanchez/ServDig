package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.enums.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.CitaSolicitud;

import org.hibernate.SQLQuery;
import org.hibernate.Session;


@Stateless(mappedName = "solicitudCitaEntity")
public class SolicitudCitaEntity extends AbstractServiceEntity implements SolicitudCitaEntityLocal {
	
	@Override
	public Long updateCitaSolicitudesPorCambioMasivoClinica(
			Asentamiento asentamiento,MedicoEnTurno medicoEnTurno,Date fechaCita){
		Long numColumnasAfectadas=null;
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String truncatedConclusionInitDate = sdf.format(fechaCita);
		
			String query="Update DIT_SOLICITUD sol set sol.CVE_ID_UMF=" + medicoEnTurno.getUnidadMedicaFamiliar().getIdUMF().intValue() +
			",sol.CVE_ID_TURNO="+ medicoEnTurno.getTurno().getIdTurno().intValue() +
			",sol.FEC_CITA =to_date("+truncatedConclusionInitDate + ",'yyyyMMdd')"+ 
			" where exists ("+
							" select sol.rowid from  DIT_GRUPO_FAMILIAR gru,  dit_personaf_dom pfdom, DG_DOMICILIO_GEOGRAFICO dom, DIT_PERSONA_INTERESADA_SOL perIntSol"+
							" where perIntSol.CVE_ID_SOLICITUD =sol.CVE_ID_SOLICITUD "+
							" and sol.FEC_REGISTRO_BAJA IS NULL"+
							" and sol.CVE_ID_ESTADO_SOLICITUD= 1"+
							//la fecha de cita sea mayor al dia de hoy
							" and to_date(to_char(sol.FEC_CITA,'yyyyMMdd'),'yyyyMMdd')> to_date(to_char(sysdate,'yyyyMMdd'),'yyyyMMdd')"+
							" and gru.CVE_ID_PERSONA_INTEGRANTE= perIntSol.CVE_ID_PERSONA"+
							" and gru.cve_id_personaf_dom = pfdom.cve_id_personaf_dom and pfdom.domicilio_id = dom.DOMICILIO_ID"+
							" and dom.CVE_MUN = "+ asentamiento.getLocalidad().getMunicipio().getClave() +
							" and dom.CVE_LOC = "+	asentamiento.getLocalidad().getClave() +
							" and dom.CVE_ASEN = "+  asentamiento.getClave() +
							" and dom.CVE_ENT = "+ asentamiento.getLocalidad().getMunicipio().getEntidadFederativa().getClave() +
							") ";
			Session session = this.getSession();
			SQLQuery q = session.createSQLQuery(query);
			numColumnasAfectadas=new Long(q.executeUpdate());
			return numColumnasAfectadas;
	}
	
	@Override
	public Long countSolicitudesFechaTurnoUmf(CitaSolicitud cita) throws Exception {
		em.flush();
		SimpleDateFormat ojbFormat = new SimpleDateFormat("yyyyMMdd");
		Long resultado = null;
		try {
			String queryS="SELECT count(s.cveIdSolicitud) FROM DitSolicitud s WHERE  to_char(s.fecCita, 'yyyyMMdd') =:fechaConsulta " +
			"AND s.ditUmfTurno.id.cveIdTurno=:cveIdTurno" +
			" AND s.dicEstadoSolicitud.cveIdEstadoSolicitud=:cveIdEstadoSolicitud"+
			"  AND s.ditUmfTurno.id.cveIdUmf=:cveIdUmf";

		      final TypedQuery<Long> query = em.createQuery(queryS, 
		    		  	 Long.class).setParameter("fechaConsulta", ojbFormat.format(cita.getFechaHora()) )
		    		  	.setParameter("cveIdTurno", cita.getTurno().getIdTurno())
		    		  	.setParameter("cveIdUmf" , cita.getUmf().getIdUMF())
		    		  	.setParameter("cveIdEstadoSolicitud", EstadoSolicitudEnum.REGISTRADA.getId());
		      			resultado = query.getSingleResult();
		} catch(NoResultException e){
			resultado = null;
		} catch (Exception e) {
			log.error("Error -countSolicitudesFechaTurnoUmf", e);
			throw e;
		}
	      return  resultado;
  		
	}
	
	
	

}
