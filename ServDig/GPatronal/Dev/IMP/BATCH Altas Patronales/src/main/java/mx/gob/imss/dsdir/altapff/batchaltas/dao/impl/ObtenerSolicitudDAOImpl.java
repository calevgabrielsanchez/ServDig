package mx.gob.imss.dsdir.altapff.batchaltas.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import mx.gob.imss.dsdir.altapff.batchaltas.dao.ObtenerSolicitudDAO;


@Component
public class ObtenerSolicitudDAOImpl implements ObtenerSolicitudDAO{

	private Logger LOG = LogManager.getLogger(ObtenerSolicitudDAOImpl.class);
	
	// Estado solicitud 3 cancelada
	// Estado tramite 7 cancelado
	
	// Estado solicitud 2 atendida
	// Estado tramite  2 cerrado
	
	private static final String SQL_SELECT_CVE_ID_SOLICITUD_EN_PROCESO = "SELECT S.CVE_ID_SOLICITUD "
			+"FROM MGPBDTU9X.DIT_SOLICITUD S "
			+"JOIN MGPBDTU9X.DIT_TRAMITE T "
			+"ON S.CVE_ID_SOLICITUD=T.CVE_ID_SOLICITUD "
			+"LEFT JOIN MGPBDTU9X.DIT_DOCTO_RESULTANTE_TRAMITE DDRT " 
			+"ON T.CVE_ID_TRAMITE = DDRT.CVE_ID_TRAMITE "
			+" WHERE "
			+"S.CVE_ID_ESTADO_SOLICITUD=5 AND "
			+"S.CVE_ID_TIPO_SOLICITUD IN (15,6) AND "
			+"T.CVE_ID_TIPO_TRAMITE IN (1,120,9,8,2,90) AND "
			+"T.CVE_ID_ESTADO_TRAMITE=1 AND "
			+"T.FEC_REGISTRO_ACTUALIZADO <= SYSDATE-3 AND "
//			+"TRUNC(T.FEC_REGISTRO_ACTUALIZADO) = TRUNC(SYSDATE - INTERVAL '30' DAY) AND "
			+"T.FEC_REGISTRO_ACTUALIZADO > SYSDATE - 365 AND "
			+"DDRT.REF_DOCUMENTO_RESULTANTE IS NULL";

	private static final String SQL_SELECT_CVE_ID_SOLICITUD_EN_PROCESO_CON_DOCS = "SELECT S.CVE_ID_SOLICITUD "
			+"FROM MGPBDTU9X.DIT_SOLICITUD S "
			+"JOIN MGPBDTU9X.DIT_TRAMITE T "
			+"ON S.CVE_ID_SOLICITUD=T.CVE_ID_SOLICITUD "
			+"LEFT JOIN MGPBDTU9X.DIT_DOCTO_RESULTANTE_TRAMITE DDRT " 
			+"ON T.CVE_ID_TRAMITE = DDRT.CVE_ID_TRAMITE "
			+" WHERE "
			+"S.CVE_ID_ESTADO_SOLICITUD=5 AND "
			+"S.CVE_ID_TIPO_SOLICITUD IN (15,6) AND "
			+"T.CVE_ID_TIPO_TRAMITE IN (1,120,9,8,2,90) AND "
			+"T.CVE_ID_ESTADO_TRAMITE=1 AND "
			+"T.FEC_REGISTRO_ACTUALIZADO <= SYSDATE-3 AND "
//			+"TRUNC(T.FEC_REGISTRO_ACTUALIZADO) = TRUNC(SYSDATE - INTERVAL '30' DAY) AND "
			+"T.FEC_REGISTRO_ACTUALIZADO > SYSDATE - 365 AND "			
			+"DDRT.REF_DOCUMENTO_RESULTANTE IS NOT NULL";
	

			
	private static final String SQL_UPDATE_ESTADO_SOLICITUD = "UPDATE MGPBDTU9X.DIT_SOLICITUD "
			+"SET CVE_ID_ESTADO_SOLICITUD = ?, FEC_REGISTRO_ACTUALIZADO = SYSDATE "
			+"WHERE CVE_ID_SOLICITUD IN (?) AND CVE_ID_TIPO_SOLICITUD IN (15,6) AND CVE_ID_ESTADO_SOLICITUD=5 ";
	
	
	private static final String SQL_UPDATE_ESTADO_TRAMITE = "UPDATE MGPBDTU9X.DIT_TRAMITE "
			+"SET CVE_ID_ESTADO_TRAMITE = ?, FEC_REGISTRO_ACTUALIZADO = SYSDATE " 
			+"WHERE CVE_ID_SOLICITUD IN (?) AND CVE_ID_TIPO_TRAMITE IN (1,120,9,8,2,90) AND CVE_ID_ESTADO_TRAMITE=1 ";	
			
	
	@Qualifier("secondaryJdbcTemplateObject")
	@Autowired
	public JdbcTemplate secondaryJdbcTemplate;
	

	@Override
	public List<Long> findEnProcesoSolicitudes(){
		System.out.println("finenprocesosolicitudes");
		List<Long> listCveIdSolicitud = new ArrayList<Long>();
		LOG.info("########## SELECT #####");

		listCveIdSolicitud = secondaryJdbcTemplate.query(SQL_SELECT_CVE_ID_SOLICITUD_EN_PROCESO,
				new RowMapper<Long>() {
					@Override
					public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getLong(1);
					}
				});
				
				
		LOG.info("########## SE ENCONTRARON UN TOTAL DE [" + listCveIdSolicitud.size()
				+ "] SOLICITUDES EN PROCESO ##########");
		return listCveIdSolicitud;
	
	}
	
	
	@Override
	public List<Long> findEnProcesoSolicitudesConDocs(){
		List<Long> listCveIdSolicitudConDocs = new ArrayList<Long>();
		listCveIdSolicitudConDocs = secondaryJdbcTemplate.query(SQL_SELECT_CVE_ID_SOLICITUD_EN_PROCESO_CON_DOCS,
				new RowMapper<Long>() {
					@Override
					public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getLong(1);
					}
				});
		LOG.info("########## SE ENCONTRARON UN TOTAL DE [" + listCveIdSolicitudConDocs.size()
				+ "] SOLICITUDES EN PROCESO CON DOCUMENTACION ##########");
		return listCveIdSolicitudConDocs;

		
	}


	@Override
	public boolean updateEstadoSolicitud(List<Long> listaSolicitudes, int estadoSolicitud) {
	int afectedRows[] = secondaryJdbcTemplate.batchUpdate(SQL_UPDATE_ESTADO_SOLICITUD,
				new BatchPreparedStatementSetter() {
					public void setValues(PreparedStatement ps, int i)
						throws SQLException {
						ps.setInt(1, estadoSolicitud);
						ps.setLong(2, listaSolicitudes.get(i));
					}

					@Override
					public int getBatchSize() {
						return listaSolicitudes.size();					
						}
			});
	
		
//		int afectedRows = secondaryJdbcTemplate.update(SQL_UPDATE_ESTADO_SOLICITUD, estadoSolicitud, listaSolicitudes);
		if (afectedRows.length > 0) {
			LOG.info("Estatus actualiza a = " + estadoSolicitud + " de idCargaArchivo: " + listaSolicitudes);
			return true;
		} else {
			return false;
		}

	}


	@Override
	public boolean updateEstadoTramite(List<Long> listaSolicitudes, int estadoTramite) {

//		int afectedRows = secondaryJdbcTemplate.update(SQL_UPDATE_ESTADO_TRAMITE, estadoTramite, listaSolicitudes);
		int afectedRows[] = secondaryJdbcTemplate.batchUpdate(SQL_UPDATE_ESTADO_TRAMITE,
				new BatchPreparedStatementSetter() {
					public void setValues(PreparedStatement ps, int i)
						throws SQLException {
						ps.setInt(1, estadoTramite);
						ps.setLong(2, listaSolicitudes.get(i));
					}

					@Override
					public int getBatchSize() {
						return listaSolicitudes.size();					
						}
			});

		
		if (afectedRows.length > 0) {
			LOG.info("Estatus actualiza a = " + estadoTramite + " de idCargaArchivo: " + listaSolicitudes);
			return true;
		} else {
			return false;
		}

	}	
	
	

}
