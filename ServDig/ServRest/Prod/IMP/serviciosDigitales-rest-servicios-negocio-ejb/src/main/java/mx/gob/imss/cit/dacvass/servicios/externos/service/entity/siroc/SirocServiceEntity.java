package mx.gob.imss.cit.dacvass.servicios.externos.service.entity.siroc;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.criterion.MatchMode;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.Page;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.ConsultaModel;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.ActualizarObraInputSiroc;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.AvisoUbicacionObra;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.AvisoUbicacionObraDetalle;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.ConsultaObraDetalle;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.CumplimientOutput;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.DatosPatronOutput;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.DetalleRegistroObra;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.IncidenciaOutput;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.ObraGeneralExtPrto;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.ObraSirocInput;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.ObraSirocOutput;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.ObradetalleOutput;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.Obraoutput;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.ObrasSimilaresInputSiroc;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.RegistroObra;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.RegistroObraDetalle;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.SirocOutput;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.UbicacionOutput;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.util.PersistenceUnitSISCOBServiceEntity;
import mx.gob.imss.cit.dacvass.servicios.externos.service.util.AproximacionSirocUtil;

@Local(value = ISirocServiceEntityLocal.class)
@Stateless
public class SirocServiceEntity extends PersistenceUnitSISCOBServiceEntity implements ISirocServiceEntityLocal {
	
	private static final Logger log = LoggerFactory.getLogger(SirocServiceEntity.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AvisoUbicacionObra> consultaAvisoRegistroObraByDelegSubDel(Long cveIdDelegacion,
			Long cveIdSubDelegacion) throws Exception {
		log.debug("llegando a la consulta consultaAvisoRegistroObraByDelegSubDel subDel {cveIdSubDelegacion}" , cveIdSubDelegacion );
		StringBuffer strQueryAviso = new StringBuffer();
		strQueryAviso.append(" SELECT D.DES_DELEG as \"descDelegacionImss\", S.DES_SUBDELEGACION as \"descSubDelegacionImss\" , ");
		strQueryAviso.append(" AO.CVE_REGISTRO_AVISO_OBRA  as \"cveRegistroAvisoObra\", UO.CODIGO_POSTAL as \"codigoPostal\" , ");
		strQueryAviso.append(" UO.CALLE as \"calle\", UO.NUM_EXTERIOR as \"numeroExterior\", UO.NUM_EXTERIOR_ALF as \"numeroExteriorAlfa\" , "); 
		strQueryAviso.append(" UO.REF_COLONIA as \"colonia\", UO.REF_MUNICIPIO as \"municipioAlcaldia\" , TRR.FEC_REGISTRO_ALTA AS \"fechaRegistroAlta\" ");				
		strQueryAviso.append(" FROM ROT_AVISO_OBRA AO  ");
		strQueryAviso.append(" LEFT OUTER JOIN DIC_SUBDELEGACION  S  ON AO.CVE_ID_SUBDELEGACION  = S.CVE_ID_SUBDELEGACION ");				
		strQueryAviso.append(" LEFT OUTER JOIN DIC_DELEGACION  D   ON S.CVE_ID_DELEGACION  = D.CVE_ID_DELEGACION ");
		strQueryAviso.append(" LEFT OUTER JOIN ROT_UBICACION_OBRA  UO  ON AO.CVE_ID_UBICACION_OBRA  = UO.CVE_ID_UBICACION_OBRA ");				
		strQueryAviso.append(" LEFT OUTER JOIN DIT_TRAMITE  TRR  ON TRR.CVE_ID_TRAMITE =AO.CVE_ID_TRAMITE ");
		strQueryAviso.append(" where  s.cve_id_delegacion= :cveIdDelegacion ");
		strQueryAviso.append(" and   s.cve_id_subdelegacion = :cveIdSubDelegacion");  
		strQueryAviso.append(" order by TRR.FEC_REGISTRO_ALTA  desc ");
		try {
			log.debug("el query a ejecutar de avisosDeObra es " + strQueryAviso );
			SQLQuery sqlQuery = getSession().createSQLQuery(strQueryAviso.toString());
			sqlQuery.setLong("cveIdDelegacion", cveIdDelegacion);
			sqlQuery.setLong("cveIdSubDelegacion", cveIdSubDelegacion);
			List<AvisoUbicacionObra> listAvisos =
					sqlQuery.setResultTransformer(Transformers.aliasToBean(AvisoUbicacionObra.class)).list();
			if(listAvisos!= null && !listAvisos.isEmpty())
				log.debug(" la cosulta de aviso si trae registros "+ listAvisos.size());
			
			return listAvisos;
		}catch(Exception e) {
			log.error("ocurio un error al consultar el avisos de registor de obra " + cveIdSubDelegacion, e );
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public AvisoUbicacionObraDetalle getAvisoRegistroObra(String cveRegistroAvisoObra) throws Exception {
		log.debug("llegando a la consulta getAvisoRegistroObra idAViso {cveRegistroAvisoObra}" , cveRegistroAvisoObra );
		StringBuffer strQueryAviso = new StringBuffer();
		log.debug(" la cosulta de aviso si trae registros ");
		strQueryAviso.append(" SELECT AO.CVE_REGISTRO_AVISO_OBRA as \"cveRegistroAvisoObra\", ");  															
		strQueryAviso.append(" UO.CALLE as \"calle\", UO.NUM_EXTERIOR as \"numeroExterior\", UO.NUM_EXTERIOR_ALF as \"numeroExteriorAlfa\", "); 
		strQueryAviso.append(" UO.NUM_EXTERIOR_DOS as \"numeroExterior2\",  UO.NUM_INTERIOR as \"numeroInterior\", UO.NUM_INTERIOR_ALF as \"numeroInteriorAlfa\", RO.FEC_REGISTRO_ALTA as \"fechaRegistroAlta\", "); 
		strQueryAviso.append(" UO.REF_COLONIA as \"colonia\",  UO.CODIGO_POSTAL as \"codigoPostal\", UO.REF_MUNICIPIO as \"municipioAlcaldia\", "); 
		strQueryAviso.append(" UO.REF_ENTIDAD as \"entidadFederativa\",  UO.REF_OBSERVACION as \"descripcionUbicacion\"  ");									
		strQueryAviso.append(" FROM MGPBDTU9X.ROT_AVISO_OBRA AO  ");									
		strQueryAviso.append(" LEFT OUTER JOIN MGPBDTU9X.ROT_UBICACION_OBRA  UO  ON AO.CVE_ID_UBICACION_OBRA  = UO.CVE_ID_UBICACION_OBRA ");	
		strQueryAviso.append(" LEFT OUTER JOIN MGPBDTU9X.DIT_TRAMITE  RO ON RO.CVE_ID_TRAMITE  = AO.CVE_ID_TRAMITE ");									
		strQueryAviso.append(" where AO.CVE_REGISTRO_AVISO_OBRA = :cveRegistropAvisoObra ");   									

		try {
			log.debug("el query a ejecutar de avisosDeObra es " + strQueryAviso );
			SQLQuery sqlQuery = getSession().createSQLQuery(strQueryAviso.toString());
			sqlQuery.setParameter("cveRegistropAvisoObra", cveRegistroAvisoObra);
			
			List<AvisoUbicacionObraDetalle> listAvisosDetalle =
					sqlQuery.setResultTransformer(Transformers.aliasToBean(AvisoUbicacionObraDetalle.class)).list();
			if(listAvisosDetalle!= null && !listAvisosDetalle.isEmpty()) {
				log.debug(" la cosulta de aviso si trae registros "+ listAvisosDetalle.size());
				return listAvisosDetalle.get(0);
			}
			return null;
		}catch(Exception e) {
			log.error("ocurio un error al consultar el avisos de registor de obra por ID" + cveRegistroAvisoObra, e );
			throw e;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RegistroObra> consultaRegistroObraByCPColonia(String codigoPostal, String colonia) throws Exception {
		log.debug("llegando a la consulta consultaRegistroObraByCPColonia CODIGO POSTAL {codigoPostal}" , codigoPostal );
		StringBuffer strQueryRegistro = new StringBuffer();
		
		strQueryRegistro.append(" SELECT RO.CVE_REGISTRO_OBRA as \"numRegistroObra\", UO.CODIGO_POSTAL as \"codigoPostal\", UO.CALLE as \"calle\", "); 
		strQueryRegistro.append(" UO.REF_COLONIA as \"colonia\", UO.REF_MUNICIPIO as \"municipioAlcaldia\",	TR.FEC_REGISTRO_ALTA  as \"fechaRegistro\"  ");				
		strQueryRegistro.append(" FROM MGPBDTU9X.ROT_INFORMACION_OBRA RO ");
		strQueryRegistro.append(" LEFT OUTER JOIN MGPBDTU9X.ROT_UBICACION_OBRA UO  ON RO.CVE_ID_UBICACION_OBRA  = UO.CVE_ID_UBICACION_OBRA ");					
		strQueryRegistro.append(" LEFT OUTER JOIN MGPBDTU9X.DIT_TRAMITE  TR  ON TR.CVE_ID_TRAMITE = RO.CVE_ID_TRAMITE ");
		strQueryRegistro.append(" where UO.CODIGO_POSTAL= :codigoPostal ");
		strQueryRegistro.append(" AND UPPER(UO.REF_COLONIA) LIKE (:colonia )");     					
		//strQueryRegistro.append(" Order by  ROT_AVISO_OBRA desc ");
	
		try {
			log.debug("el query a ejecutar de avisosDeObra es " + strQueryRegistro );
			SQLQuery sqlQuery = getSession().createSQLQuery(strQueryRegistro.toString());
			sqlQuery.setParameter("codigoPostal", codigoPostal);
			sqlQuery.setParameter("colonia", "%"+colonia.toUpperCase()+"%");
			
			List<RegistroObra> listRegistroObra =
					sqlQuery.setResultTransformer(Transformers.aliasToBean(RegistroObra.class)).list();
			if(listRegistroObra!= null && !listRegistroObra.isEmpty()) {
				log.debug(" la cosulta de regisro de obra si trae registros "+ listRegistroObra.size());
			}
			return listRegistroObra;
		}catch(Exception e) {
			log.error("ocurio un error al consultar el registros de obra por CP " + codigoPostal, e );
			throw e;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public DetalleRegistroObra getRegistroObraByNumRegistro(String cveRegistroObra) throws Exception {
		log.debug("llegando a la consulta getRegistroObraByNumRegistro {cveRegistroObra}" , cveRegistroObra);
		StringBuffer strQueryRegistro = new StringBuffer();
		strQueryRegistro.append(" SELECT D.DES_DELEG as \"descDelegacionImss\", S.DES_SUBDELEGACION as \"descSubDelegacionImss\", ");
		strQueryRegistro.append(" IP.ROT_RAZON_SOCIAL as \"nombreRazonSocial\",  IP.ROT_RFC as \"rfc\", IP.ROT_REG_PATRONAL as \"registroPatronal\", "); 
		strQueryRegistro.append(" RO.CVE_REGISTRO_OBRA as \"numRegistroObra\", RO.FEC_REGISTRO_ALTA as \"fecRegistroObra\", RO.FEC_REGISTRO_ALTA as \"fechaRegistroAlta\", "); 
		strQueryRegistro.append(" UO.CALLE as \"calle\", UO.NUM_EXTERIOR as \"numeroExterior\", UO.NUM_EXTERIOR_ALF as \"numeroExteriorAlfa\", "); 
		strQueryRegistro.append(" UO.NUM_INTERIOR as \"numeroInterior\", UO.NUM_INTERIOR_ALF as \"numeroInteriorAlfa\", UO.REF_COLONIA as \"colonia\", "); 
		strQueryRegistro.append(" UO.REF_MUNICIPIO as \"municipioAlcaldia\",  UO.CODIGO_POSTAL as \"codigoPostal\",  UO.REF_ENTIDAD as \"entidadFederativa\", ");  
		strQueryRegistro.append(" UO.REF_OBSERVACION  as \"descripcionUbicacion\", TP.DES_TIPO_PATRON as \"tipoPatron\", ");
		strQueryRegistro.append(" RO.REF_SUP_CONSTRUCCION as \"superficie\", RO.IMP_OBRA as \"montoObra\", RO.IMP_EJERCIDO  as \"importeEjercido\", ");							
		strQueryRegistro.append(" TOO.DES_TIPO_OBRA as \"tiooObra\", CO.DES_CLASIFICACION_OBRA as \"claseObra\", ");
		strQueryRegistro.append(" CASE  "); 							
		strQueryRegistro.append(" WHEN RO.CVE_ID_INFORMACION_OBRA_PRIN IS NULL THEN "); 							
		strQueryRegistro.append(" NULL ");
		strQueryRegistro.append(" ELSE ");					
		strQueryRegistro.append(" ( ");					
		strQueryRegistro.append(" SELECT RFO.CVE_REGISTRO_OBRA ");							
		strQueryRegistro.append(" FROM MGPBDTU9X.ROT_INFORMACION_OBRA RFO ");							
		strQueryRegistro.append(" WHERE RFO.CVE_ID_INFORMACION_OBRA = RO.CVE_ID_INFORMACION_OBRA_PRIN ");							
		strQueryRegistro.append(" ) ");
		strQueryRegistro.append(" END as \"numObraContratante\", "); 	
		strQueryRegistro.append(" RO.FEC_INI_OBRA as \"fecInicioObra\", RO.FEC_FIN_OBRA as \"fecFinObra\", ");   												
		strQueryRegistro.append(" (SELECT MAX(NUM_ANIO || CVE_ID_CALENDARIO_REP)  ");
		strQueryRegistro.append(" FROM MGPBDTU9X.ROT_INFORMACION_INCIDENCIA II ");					
		strQueryRegistro.append(" WHERE II.CVE_ID_INFORMACION_OBRA = RO.CVE_ID_INFORMACION_OBRA ");							
		strQueryRegistro.append(" and II.CVE_ID_MOTIVO_INCIDENCIA = 14 ");
		strQueryRegistro.append(" and II.CVE_ID_TIPO_REGISTRO < 3  ");					
		strQueryRegistro.append(" ) as  \"ultimoBimestrePresentado\",  ");   														
		strQueryRegistro.append(" RO.REF_OBSERVACION as \"desRegistro\" ");
		strQueryRegistro.append(" FROM MGPBDTU9X.ROT_INFORMACION_OBRA RO ");				
		strQueryRegistro.append(" LEFT OUTER JOIN MGPBDTU9X.DIC_SUBDELEGACION       S   ON RO.CVE_ID_SUBDELEGACION          = S.CVE_ID_SUBDELEGACION ");							
		strQueryRegistro.append(" LEFT OUTER JOIN MGPBDTU9X.DIC_DELEGACION          D   ON S.CVE_ID_DELEGACION              = D.CVE_ID_DELEGACION	");
		strQueryRegistro.append(" LEFT OUTER JOIN MGPBDTU9X.ROT_INFORMACION_PATRON  IP  ON RO.CVE_ID_INFORMACION_PATRON     = IP.CVE_ID_INFORMACION_PATRON ");							
		strQueryRegistro.append(" LEFT OUTER JOIN MGPBDTU9X.ROT_UBICACION_OBRA      UO  ON RO.CVE_ID_UBICACION_OBRA         = UO.CVE_ID_UBICACION_OBRA ");
		strQueryRegistro.append(" LEFT OUTER JOIN MGPBDTU9X.ROC_TIPO_OBRA           TOO  ON RO.CVE_ID_TIPO_OBRA              = TOO.CVE_ID_TIPO_OBRA ");
		strQueryRegistro.append(" LEFT OUTER JOIN MGPBDTU9X.ROC_CLASIFICACION_OBRA  CO  ON TOO.CVE_ID_CLASIFICACION_OBRA     = CO.CVE_ID_CLASIFICACION_OBRA ");
		strQueryRegistro.append(" LEFT OUTER JOIN MGPBDTU9X.ROC_TIPO_PATRON         TP  ON IP.CVE_ID_TIPO_PATRON            = TP.CVE_ID_TIPO_PATRON ");
		strQueryRegistro.append(" LEFT OUTER JOIN MGPBDTU9X.ROT_INFORMACION_OBRA    RO2 ON RO.CVE_ID_INFORMACION_OBRA_PRIN  = RO.CVE_ID_INFORMACION_OBRA ");
		strQueryRegistro.append(" WHERE RO.CVE_REGISTRO_OBRA = :cveRegistroObra ");
		try {
			log.debug("el query a ejecutar de registroDeObra es " + strQueryRegistro );
			SQLQuery sqlQuery = getSession().createSQLQuery(strQueryRegistro.toString());
			sqlQuery.setParameter("cveRegistroObra", cveRegistroObra);
			List<DetalleRegistroObra> listDetalleRegistroObra =
					sqlQuery.setResultTransformer(Transformers.aliasToBean(DetalleRegistroObra.class)).list();
			if(listDetalleRegistroObra!= null && !listDetalleRegistroObra.isEmpty()) {
				log.debug(" la cosulta de detalle de regisro de obra si trae registros "+ listDetalleRegistroObra.size());
				return listDetalleRegistroObra.get(0);
			}
			return null;
		}catch(Exception e) {
			log.error("ocurio un error al consultar el  getRegistroObraByNumRegistro " + cveRegistroObra, e );
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Page<ObraGeneralExtPrto> obrasRegistradasPRTO(
			ObrasSimilaresInputSiroc<ObraGeneralExtPrto> input, Date fecha)
			throws Exception {
		Page<ObraGeneralExtPrto> pagina = new Page<ObraGeneralExtPrto>();
		log.debug(
				"llegando a la consulta obrasRegistradasPRTO idDelegacion: {} , idMunicipio: {}",
				((ObraGeneralExtPrto) input.getModel()).getIdDelegacion(),
				((ObraGeneralExtPrto) input.getModel()).getIdSubDelegacion());
		StringBuilder strQueryRegistro = AproximacionSirocUtil
				.obtenerConsulta();
		
		if(input.getOrder() != null && input.getDesc() != null) {
			strQueryRegistro.append(" order by ");			
			if(input.getOrder().equals("numRegistroObra")) {
				strQueryRegistro.append("CVE_REGISTRO_OBRA");
			}
			else if (input.getOrder().equals("fecFinObra")) {
				strQueryRegistro.append("fec_fin_obra");
			}
			strQueryRegistro.append(" ");
			if (input.getDesc().equals("true")) {
				strQueryRegistro.append(" desc ");
			}
			else if(input.getDesc().equals("false")) {
				strQueryRegistro.append(" asc ");
			}
			
		}

		try {
			log.debug("el query a ejecutar de registroDeObra es "
					+ strQueryRegistro);
			SQLQuery sqlQuery = getSession().createSQLQuery(
					strQueryRegistro.toString());
			sqlQuery.setParameter(0, ((ObraGeneralExtPrto) input.getModel())
					.getIdSubDelegacion());
			sqlQuery.setParameter(1,
					((ObraGeneralExtPrto) input.getModel()).getIdDelegacion());
			sqlQuery.setParameter(2, fecha);
			sqlQuery.setParameter(3,
					((ObraGeneralExtPrto) input.getModel()).getIdSubDelegacion());
			sqlQuery.setParameter(4, ((ObraGeneralExtPrto) input.getModel())
					.getIdDelegacion());
			sqlQuery.setParameter(5, fecha);
			sqlQuery.setFirstResult((input.getPage() - 1) * input.getPageSize());
			sqlQuery.setMaxResults(input.getPageSize());
			List<ObraGeneralExtPrto> listDetalleRegistroObra = sqlQuery
					.setResultTransformer(
							Transformers.aliasToBean(ObraGeneralExtPrto.class))
					.list();
			if (listDetalleRegistroObra != null
					&& !listDetalleRegistroObra.isEmpty()) {
				ScrollableResults scroll = getSession()
						.createSQLQuery(strQueryRegistro.toString())
						.setParameter(
								0,
								((ObraGeneralExtPrto) input.getModel())
										.getIdSubDelegacion())
						.setParameter(
								1,
								((ObraGeneralExtPrto) input.getModel())
										.getIdDelegacion())
						.setParameter(2, fecha)
						.setParameter(
								3,
								((ObraGeneralExtPrto) input.getModel())
										.getIdSubDelegacion())
						.setParameter(
								4,
								((ObraGeneralExtPrto) input.getModel())
										.getIdDelegacion())
						.setParameter(5, fecha)
						.scroll(ScrollMode.SCROLL_SENSITIVE);
				scroll.last();
				log.debug(" la cosulta de detalle de regisro de obra si trae registros "
						+ listDetalleRegistroObra.size());
				pagina.setContent(listDetalleRegistroObra);
				pagina.setNumberOfElements(listDetalleRegistroObra.size());
				pagina.setSize(input.getPageSize());
				pagina.setNumber(input.getPage() - 1);
				pagina.setTotalElements(scroll.getRowNumber() + 1);
				pagina.setTotalPages(Double.valueOf(
						Math.ceil((scroll.getRowNumber() + 1)
								/ input.getPageSize())).intValue());
			}
			return pagina;
		} catch (Exception e) {
			log.error(
					"ocurio un error al consultar el  obrasRegistradasPRTO {}",
					((ObraGeneralExtPrto) input.getModel()).getIdDelegacion(),
					e);
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Page<ObraGeneralExtPrto> obrasRegistradasPRTOAproximacion(
			ObrasSimilaresInputSiroc<ObraGeneralExtPrto> input, Date fecha)
			throws Exception {
		Page<ObraGeneralExtPrto> pagina = new Page<ObraGeneralExtPrto>();
		log.debug(
				"llegando a la consulta obrasRegistradasPRTO idDelegacion: {} , idMunicipio: {}",
				((ObraGeneralExtPrto) input.getModel()).getIdDelegacion(),
				((ObraGeneralExtPrto) input.getModel()).getIdSubDelegacion());
		StringBuilder strQueryRegistro = AproximacionSirocUtil.consultaAproximacion(input, fecha);
		if(input.getOrder() != null && input.getDesc() != null) {
			strQueryRegistro.append(" order by ");			
			if(input.getOrder().equals("numRegistroObra")) {
				strQueryRegistro.append("CVE_REGISTRO_OBRA");
			}
			else if (input.getOrder().equals("fecFinObra")) {
				strQueryRegistro.append("fec_fin_obra");
			}
			strQueryRegistro.append(" ");
			if (input.getDesc().equals("true")) {
				strQueryRegistro.append(" desc ");
			}
			else if(input.getDesc().equals("false")) {
				strQueryRegistro.append(" asc ");
			}
		}
		try {
			log.debug("el query a ejecutar de registroDeObra es "
					+ strQueryRegistro);
			SQLQuery sqlQuery = getSession().createSQLQuery(
					strQueryRegistro.toString());			
			sqlQuery.setFirstResult((input.getPage() - 1) * input.getPageSize());
			sqlQuery.setMaxResults(input.getPageSize());
			List<ObraGeneralExtPrto> listDetalleRegistroObra = sqlQuery
					.setResultTransformer(
							Transformers.aliasToBean(ObraGeneralExtPrto.class))
					.list();
			if (listDetalleRegistroObra != null
					&& !listDetalleRegistroObra.isEmpty()) {
				ScrollableResults scroll = getSession()
						.createSQLQuery(strQueryRegistro.toString())						
						.scroll(ScrollMode.SCROLL_SENSITIVE);
				scroll.last();
				log.debug(" la cosulta de detalle de regisro de obra si trae registros "
						+ listDetalleRegistroObra.size());
				pagina.setContent(listDetalleRegistroObra);
				pagina.setNumberOfElements(listDetalleRegistroObra.size());
				pagina.setSize(input.getPageSize());
				pagina.setNumber(input.getPage() - 1);
				pagina.setTotalElements(scroll.getRowNumber() + 1);
				pagina.setTotalPages(Double.valueOf(
						Math.ceil((scroll.getRowNumber() + 1)
								/ input.getPageSize())).intValue());
			}
			return pagina;
		} catch (Exception e) {
			log.error(
					"ocurio un error al consultar el  obrasRegistradasPRTO {}",
					((ObraGeneralExtPrto) input.getModel()).getIdDelegacion(),
					e);
			throw e;
		}
	}
		
	@Override
	public ObraGeneralExtPrto actualizarObra(ActualizarObraInputSiroc input)
			throws Exception {
		log.debug(
				"llegando a la consulta actualizarObra input.getNumeroRegistroObra: {}",
				input.getNumeroRegistroObra());
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ObraGeneralExtPrto detalleObraPRTO(String numObra) throws Exception {
		log.debug("llegando a la consulta obrasRegistradasPRTO numObra: {}",
				numObra);
		StringBuffer strQueryAviso = new StringBuffer();
		strQueryAviso.append("SELECT ");
		strQueryAviso.append("        UO.CALLE              AS \"calle\", ");
		strQueryAviso.append("        CO.DES_CLASIFICACION_OBRA  AS \"claseObra\", ");
		strQueryAviso.append("        UO.CODIGO_POSTAL           AS \"codigoPostal\", ");
		strQueryAviso.append("        UO.REF_COLONIA             AS \"colonia\", ");
		strQueryAviso.append("        AO.CVE_REGISTRO_AVISO_OBRA AS \"cveRegistroAvisoObra\", ");
		strQueryAviso.append("        D.DES_DELEG                AS \"descDelegacionImss\", ");
		strQueryAviso.append("        S.DES_SUBDELEGACION        AS \"descSubDelegacionImss\", ");
		strQueryAviso.append("        UO.REF_ENTIDAD             AS \"entidadFederativa\", ");
		strQueryAviso.append("        ro.fec_fin_obra            AS \"fecFinObra\", ");
		strQueryAviso.append("        RO.FEC_REGISTRO_ALTA       AS \"fechaRegistroAlta\", ");
		strQueryAviso.append("        RO.FEC_REGISTRO_ALTA       AS \"fecRegistroObra\", ");
		strQueryAviso.append("        ro.fec_ini_obra            AS \"fecInicioObra\", ");
		strQueryAviso.append("        RO.IMP_EJERCIDO            AS \"importeEjercido\", ");
		strQueryAviso.append("        RO.IMP_OBRA                AS \"montoObra\", ");
		strQueryAviso.append("        UO.REF_MUNICIPIO           AS \"municipioAlcaldia\", ");
		strQueryAviso.append("        PAT.ROT_RAZON_SOCIAL       AS \"nombreRazonSocial\", ");
		strQueryAviso.append("        UO.NUM_EXTERIOR            AS \"numeroExterior\", ");
		strQueryAviso.append("        UO.NUM_EXTERIOR_DOS        AS \"numeroExterior2\", ");
		strQueryAviso.append("        UO.NUM_EXTERIOR_ALF        AS \"numeroExteriorAlfa\", ");
		strQueryAviso.append("        UO.NUM_INTERIOR            AS \"numeroInterior\", ");
		strQueryAviso.append("        UO.NUM_INTERIOR_ALF        AS \"numeroInteriorAlfa\", ");
		strQueryAviso.append("        RO.CVE_REGISTRO_OBRA       AS \"numRegistroObra\", ");
		strQueryAviso.append("        PAT.ROT_REG_PATRONAL       AS \"registroPatronal\", ");
		strQueryAviso.append("        PAT.ROT_RFC                AS \"rfc\", ");
		strQueryAviso.append("        RO.REF_SUP_CONSTRUCCION    AS \"superficie\", ");
		strQueryAviso.append("        TOO.DES_TIPO_OBRA          AS \"tiooObra\", ");
		strQueryAviso.append("        TP.DES_TIPO_PATRON         AS \"tipoPatron\", ");
		strQueryAviso.append("        MGPBDTU9X.ULTIMO_BIMESTRE_PRESENTADO(RO.CVE_ID_INFORMACION_OBRA) AS \"ultimoBimestrePresentado\", ");
		strQueryAviso.append("        (   CASE ");
		strQueryAviso.append("            WHEN RII.STP_REG_INCIDENCIA IS NULL     THEN 'NO' ");
		strQueryAviso.append("            ELSE 'SI' ");
		strQueryAviso.append("        END)                     AS \"terminacionPorIncidencia\", ");
		strQueryAviso.append("        RTI.DES_TIPO_INCIDENCIA  AS \"descTipoIncidencia\", ");
		strQueryAviso.append("        RII.STP_REG_INCIDENCIA   AS \"fechaTerminoIncidencia\", ");
		strQueryAviso.append("        RII.REF_SUP_CONSTRUCCION AS \"superficieIncidencia\", ");
		strQueryAviso.append("        RII.IMP_EJERCIDO         AS \"montoObraIncidencia\", ");
		strQueryAviso.append("        RO.CVE_ID_INFORMACION_OBRA AS \"cveInformacionObra\", ");
		strQueryAviso.append("        MPRTO.REF_MARCA_PRTO AS \"marcaPrto\" ");
		strQueryAviso.append("    FROM ");
		strQueryAviso.append("        MGPBDTU9X.ROT_INFORMACION_OBRA RO ");
		strQueryAviso.append("    LEFT OUTER JOIN ");
		strQueryAviso.append("        MGPBDTU9X.ROT_AVISO_OBRA AO ");
		strQueryAviso.append("            ON AO.CVE_REGISTRO_AVISO_OBRA = ro.cve_id_aviso_obra ");
		strQueryAviso.append("    LEFT OUTER JOIN ");
		strQueryAviso.append("        MGPBDTU9X.DIC_SUBDELEGACION S ");
		strQueryAviso.append("            ON RO.CVE_ID_SUBDELEGACION = S.CVE_ID_SUBDELEGACION ");
		strQueryAviso.append("    LEFT OUTER JOIN ");
		strQueryAviso.append("        MGPBDTU9X.DIC_DELEGACION D ");
		strQueryAviso.append("            ON D.CVE_ID_DELEGACION = S.CVE_ID_DELEGACION ");
		strQueryAviso.append("    LEFT OUTER JOIN ");
		strQueryAviso.append("        MGPBDTU9X.ROT_UBICACION_OBRA UO ");
		strQueryAviso.append("            ON RO.CVE_ID_UBICACION_OBRA = UO.CVE_ID_UBICACION_OBRA ");
		strQueryAviso.append("    LEFT OUTER JOIN ");
		strQueryAviso.append("        MGPBDTU9X.ROC_TIPO_OBRA TOO ");
		strQueryAviso.append("            ON RO.CVE_ID_TIPO_OBRA = TOO.CVE_ID_TIPO_OBRA ");
		strQueryAviso.append("    LEFT OUTER JOIN ");
		strQueryAviso.append("        MGPBDTU9X.ROC_CLASIFICACION_OBRA CO ");
		strQueryAviso.append("            ON TOO.CVE_ID_CLASIFICACION_OBRA = CO.CVE_ID_CLASIFICACION_OBRA ");
		strQueryAviso.append("    LEFT OUTER JOIN ");
		strQueryAviso.append("        MGPBDTU9X.ROT_INFORMACION_OBRA RO2 ");
		strQueryAviso.append("            ON RO.CVE_ID_INFORMACION_OBRA_PRIN = RO.CVE_ID_INFORMACION_OBRA ");
		strQueryAviso.append("    LEFT OUTER JOIN ");
		strQueryAviso.append("        MGPBDTU9X.ROC_ESTATUS_OBRA EO ");
		strQueryAviso.append("            ON RO.CVE_ID_ESTATUS_OBRA = EO.CVE_ID_ESTATUS_OBRA ");
		strQueryAviso.append("    LEFT OUTER JOIN ");
		strQueryAviso.append("        MGPBDTU9X.ROT_INFORMACION_PATRON PAT ");
		strQueryAviso.append("            ON PAT.CVE_ID_INFORMACION_PATRON = RO.CVE_ID_INFORMACION_PATRON ");
		strQueryAviso.append("    LEFT OUTER JOIN ");
		strQueryAviso.append("        MGPBDTU9X.ROC_TIPO_PATRON TP ");
		strQueryAviso.append("            ON tp.cve_id_tipo_patron = pat.cve_id_tipo_patron ");
		strQueryAviso.append("    LEFT OUTER JOIN ");
		strQueryAviso.append("        MGPBDTU9X.ROT_INFORMACION_INCIDENCIA RII ");
		strQueryAviso.append("            ON RO.CVE_ID_INFORMACION_OBRA = RII.CVE_ID_INFORMACION_OBRA ");
		strQueryAviso.append("    LEFT OUTER JOIN ");
		strQueryAviso.append("        MGPBDTU9X.ROC_MOTIVO_INCIDENCIA RMI ");
		strQueryAviso.append("            ON RMI.CVE_ID_MOTIVO_INCIDENCIA = RII.CVE_ID_MOTIVO_INCIDENCIA ");
		strQueryAviso.append("    LEFT OUTER JOIN ");
		strQueryAviso.append("        MGPBDTU9X.ROC_TIPO_INCIDENCIA RTI ");
		strQueryAviso.append("            ON RMI.CVE_ID_TIPO_INCIDENCIA = RTI.CVE_ID_TIPO_INCIDENCIA ");
		strQueryAviso.append("    LEFT OUTER JOIN ");
		strQueryAviso.append("        MGPBDTU9X.ROT_INF_OBRA_MARCA_PRTO MPRTO ");
		strQueryAviso.append("            ON RO.CVE_ID_INFORMACION_OBRA =  MPRTO.CVE_ID_INFORMACION_OBRA ");
		strQueryAviso.append("    WHERE ");
		strQueryAviso.append("        RO.CVE_REGISTRO_OBRA = ? ");
		strQueryAviso.append("    ORDER BY rii.cve_id_informacion_incidencia DESC ");
		try {
			log.debug("el query a ejecutar de registroDeObra es {}",
					strQueryAviso);
			SQLQuery sqlQuery = getSession().createSQLQuery(
					strQueryAviso.toString());
			sqlQuery.setParameter(0, numObra);
			List<ObraGeneralExtPrto> listDetalleRegistroObra = sqlQuery
					.setResultTransformer(
							Transformers.aliasToBean(ObraGeneralExtPrto.class))
					.list();
			if (listDetalleRegistroObra != null
					&& !listDetalleRegistroObra.isEmpty()) {
				log.debug(" la cosulta de detalle de regisro de obra si trae registros "
						+ listDetalleRegistroObra.size());
				return listDetalleRegistroObra.get(0);
			}
			return null;
		} catch (Exception e) {
			log.error(
					"ocurio un error al consultar el  obrasRegistradasPRTO {}",
					numObra, e);
			throw e;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Page<ObraGeneralExtPrto> obrasSimilaresCP(
			ObrasSimilaresInputSiroc<ObraGeneralExtPrto> input)
			throws Exception {
		Page<ObraGeneralExtPrto> pagina = new Page<ObraGeneralExtPrto>();
		log.debug("llegando a la consulta obrasSimilares cp: {}",
				((ObraGeneralExtPrto) input.getModel()).getCodigoPostal());
		StringBuffer strQueryRegistro = new StringBuffer();
		strQueryRegistro.append("SELECT UO.CALLE \"calle\", ");
		strQueryRegistro.append("  UO.REF_ENTIDAD            AS \"entidadFederativa\", ");
		strQueryRegistro.append("  UO.CODIGO_POSTAL          AS \"codigoPostal\", ");
		strQueryRegistro.append("  UO.REF_COLONIA            AS \"colonia\", ");
		strQueryRegistro.append("  RO.FEC_REGISTRO_ALTA      AS \"fechaRegistroAlta\", ");
		strQueryRegistro.append("  UO.REF_MUNICIPIO          AS \"municipioAlcaldia\", ");
		strQueryRegistro.append("  CO.DES_CLASIFICACION_OBRA AS \"claseObra\", ");
		strQueryRegistro.append("  UO.NUM_INTERIOR           AS \"numeroInterior\", ");
		strQueryRegistro.append("  UO.NUM_INTERIOR_ALF       AS \"numeroInteriorAlfa\", ");
		strQueryRegistro.append("  UO.NUM_EXTERIOR           AS \"numeroExterior\", ");
		strQueryRegistro.append("  UO.NUM_EXTERIOR_ALF       AS \"numeroExteriorAlfa\", ");
		strQueryRegistro.append("  RO.CVE_REGISTRO_OBRA      AS \"numRegistroObra\", ");
		strQueryRegistro.append("  TOO.DES_TIPO_OBRA         AS \"tiooObra\", ");
		strQueryRegistro.append("  RO.IMP_OBRA               AS \"montoObra\", ");
		strQueryRegistro.append("  RIP.ROT_RAZON_SOCIAL      AS \"nombreRazonSocial\", ");
		strQueryRegistro.append("  RIP.ROT_RFC               AS \"rfc\", ");		
		strQueryRegistro.append("  ( ");
		strQueryRegistro.append("  CASE ");
		strQueryRegistro.append("    WHEN RII.STP_REG_INCIDENCIA = NULL ");
		strQueryRegistro.append("    THEN 'NO' ");
		strQueryRegistro.append("    ELSE 'SI' ");
		strQueryRegistro.append("  END)                     AS \"terminacionPorIncidencia\", ");
		strQueryRegistro.append("  RTI.DES_TIPO_INCIDENCIA  AS \"descTipoIncidencia\", ");
		strQueryRegistro.append("  RII.STP_REG_INCIDENCIA   AS \"fechaTerminoIncidencia\", ");
		strQueryRegistro.append("  RII.REF_SUP_CONSTRUCCION AS \"superficieIncidencia\", ");
		strQueryRegistro.append("  RII.IMP_EJERCIDO         AS \"montoObraIncidencia\" ");
		strQueryRegistro.append("FROM MGPBDTU9X.ROT_INFORMACION_OBRA RO ");
		strQueryRegistro.append("JOIN ROT_INFORMACION_PATRON RIP ");
		strQueryRegistro.append("ON RO.CVE_ID_INFORMACION_PATRON = RIP.CVE_ID_INFORMACION_PATRON ");
		strQueryRegistro.append("JOIN MGPBDTU9X.ROT_UBICACION_OBRA UO ");
		strQueryRegistro.append("ON RO.CVE_ID_UBICACION_OBRA = UO.CVE_ID_UBICACION_OBRA ");
		strQueryRegistro.append("AND UO.CODIGO_POSTAL = ?");
		strQueryRegistro.append("JOIN MGPBDTU9X.ROC_TIPO_OBRA TOO ");
		strQueryRegistro.append("ON RO.CVE_ID_TIPO_OBRA = TOO.CVE_ID_TIPO_OBRA ");
		strQueryRegistro.append("JOIN MGPBDTU9X.ROC_CLASIFICACION_OBRA CO ");
		strQueryRegistro.append("ON TOO.CVE_ID_CLASIFICACION_OBRA = CO.CVE_ID_CLASIFICACION_OBRA ");
		strQueryRegistro.append("JOIN MGPBDTU9X.ROT_INFORMACION_INCIDENCIA RII ");
		strQueryRegistro.append("ON RO.CVE_ID_INFORMACION_OBRA = RII.CVE_ID_INFORMACION_OBRA ");
		strQueryRegistro.append("JOIN MGPBDTU9X.ROC_MOTIVO_INCIDENCIA RMI ");
		strQueryRegistro.append("ON RMI.CVE_ID_MOTIVO_INCIDENCIA = RII.CVE_ID_MOTIVO_INCIDENCIA ");
		strQueryRegistro.append("JOIN MGPBDTU9X.ROC_TIPO_INCIDENCIA RTI ");
		strQueryRegistro.append("ON RMI.CVE_ID_TIPO_INCIDENCIA = RTI.CVE_ID_TIPO_INCIDENCIA ");
		if(input.getOrder() != null && input.getDesc() != null) {
			strQueryRegistro.append(" order by ");			
			if(input.getOrder().equals("numRegistroObra")) {
				strQueryRegistro.append("RO.CVE_REGISTRO_OBRA");
			}
			else if(input.getOrder().equals("codigoPostal"))  {
				strQueryRegistro.append("UO.CODIGO_POSTAL");
			}
			else if(input.getOrder().equals("calle"))  {
				strQueryRegistro.append("UO.CALLE");
			}
			else if(input.getOrder().equals("numeroExterior"))  {
				strQueryRegistro.append("UO.NUM_EXTERIOR");
			}
			else if(input.getOrder().equals("colonia"))  {
				strQueryRegistro.append("UO.REF_COLONIA");
			}
			else if(input.getOrder().equals("municipioAlcaldia"))  {
				strQueryRegistro.append("UO.REF_MUNICIPIO");
			}
			else if(input.getOrder().equals("fechaRegistroAlta"))  {
				strQueryRegistro.append("RO.FEC_REGISTRO_ALTA");
			}
			else if(input.getOrder().equals("claseObra"))  {
				strQueryRegistro.append("CO.DES_CLASIFICACION_OBRA");
			}
			else if(input.getOrder().equals("tipoObra"))  {
				strQueryRegistro.append("TOO.DES_TIPO_OBRA");
			}			
			strQueryRegistro.append(" ");
			if (input.getDesc().equals("true")) {
				strQueryRegistro.append(" desc ");
			}
			else if(input.getDesc().equals("false")) {
				strQueryRegistro.append(" asc ");
			}
		}
		try {
			log.debug("el query a ejecutar de registroDeObra es "
					+ strQueryRegistro);
			SQLQuery sqlQuery = getSession().createSQLQuery(
					strQueryRegistro.toString());
			sqlQuery.setParameter(0,
					((ObraGeneralExtPrto) input.getModel()).getCodigoPostal());
			sqlQuery.setFirstResult((input.getPage() - 1) * input.getPageSize());
			sqlQuery.setMaxResults(input.getPageSize());
			List<ObraGeneralExtPrto> listDetalleRegistroObra = sqlQuery
					.setResultTransformer(
							Transformers.aliasToBean(ObraGeneralExtPrto.class))
					.list();
			if (listDetalleRegistroObra != null
					&& !listDetalleRegistroObra.isEmpty()) {
				ScrollableResults scroll = getSession()
						.createSQLQuery(strQueryRegistro.toString())
						.setParameter(
								0,
								((ObraGeneralExtPrto) input.getModel())
										.getCodigoPostal())
						.scroll(ScrollMode.SCROLL_SENSITIVE);
				scroll.last();
				log.debug(" la cosulta de detalle de regisro de obra si trae registros "
						+ listDetalleRegistroObra.size());
				pagina.setContent(listDetalleRegistroObra);
				pagina.setNumberOfElements(listDetalleRegistroObra.size());
				pagina.setSize(input.getPageSize());
				pagina.setNumber(input.getPage() - 1);
				pagina.setTotalElements(scroll.getRowNumber() + 1);
				pagina.setTotalPages(Double.valueOf(
						Math.ceil((scroll.getRowNumber() + 1)
								/ input.getPageSize())).intValue());
			}
			return pagina;
		} catch (Exception e) {
			log.error(
					"ocurio un error al consultar el  obrasRegistradasPRTO {}",
					((ObraGeneralExtPrto) input.getModel()).getCodigoPostal(),
					e);
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Page<ObraGeneralExtPrto> obrasSimilaresCPColonia(
			ObrasSimilaresInputSiroc<ObraGeneralExtPrto> input)
			throws Exception {
		Page<ObraGeneralExtPrto> pagina = new Page<ObraGeneralExtPrto>();
		log.debug("llegando a la consulta obrasSimilares cp: {}, colonia {}",
				((ObraGeneralExtPrto) input.getModel()).getCodigoPostal(),
				((ObraGeneralExtPrto) input.getModel()).getColonia());
		StringBuffer strQueryRegistro = new StringBuffer();
		strQueryRegistro.append("SELECT UO.CALLE \"calle\", ");
		strQueryRegistro.append("  UO.REF_ENTIDAD            AS \"entidadFederativa\", ");
		strQueryRegistro.append("  UO.CODIGO_POSTAL          AS \"codigoPostal\", ");
		strQueryRegistro.append("  UO.REF_COLONIA            AS \"colonia\", ");
		strQueryRegistro.append("  RO.FEC_REGISTRO_ALTA      AS \"fechaRegistroAlta\", ");
		strQueryRegistro.append("  UO.REF_MUNICIPIO          AS \"municipioAlcaldia\", ");
		strQueryRegistro.append("  CO.DES_CLASIFICACION_OBRA AS \"claseObra\", ");
		strQueryRegistro.append("  UO.NUM_INTERIOR           AS \"numeroInterior\", ");
		strQueryRegistro.append("  UO.NUM_INTERIOR_ALF       AS \"numeroInteriorAlfa\", ");
		strQueryRegistro.append("  UO.NUM_EXTERIOR           AS \"numeroExterior\", ");
		strQueryRegistro.append("  UO.NUM_EXTERIOR_ALF       AS \"numeroExteriorAlfa\", ");
		strQueryRegistro.append("  RO.CVE_REGISTRO_OBRA      AS \"numRegistroObra\", ");
		strQueryRegistro.append("  TOO.DES_TIPO_OBRA         AS \"tiooObra\", ");
		strQueryRegistro.append("  RO.IMP_OBRA               AS \"montoObra\" ");
		strQueryRegistro.append("FROM MGPBDTU9X.ROT_INFORMACION_OBRA RO ");
		strQueryRegistro.append("LEFT OUTER JOIN MGPBDTU9X.DIC_SUBDELEGACION S ");
		strQueryRegistro.append("ON RO.CVE_ID_SUBDELEGACION = S.CVE_ID_SUBDELEGACION ");
		strQueryRegistro.append("JOIN MGPBDTU9X.ROT_UBICACION_OBRA UO ");
		strQueryRegistro.append("ON RO.CVE_ID_UBICACION_OBRA = UO.CVE_ID_UBICACION_OBRA ");
		strQueryRegistro.append("AND UO.CODIGO_POSTAL = ? ");
		strQueryRegistro.append("AND UPPER(UO.REF_COLONIA) like UPPER(?) ");
		strQueryRegistro.append("LEFT OUTER JOIN MGPBDTU9X.ROC_TIPO_OBRA TOO ");
		strQueryRegistro.append("ON RO.CVE_ID_TIPO_OBRA = TOO.CVE_ID_TIPO_OBRA ");
		strQueryRegistro.append("LEFT OUTER JOIN MGPBDTU9X.ROC_CLASIFICACION_OBRA CO ");
		strQueryRegistro.append("ON TOO.CVE_ID_CLASIFICACION_OBRA = CO.CVE_ID_CLASIFICACION_OBRA ");
		strQueryRegistro.append("LEFT OUTER JOIN MGPBDTU9X.ROT_INFORMACION_OBRA RO2 ");
		strQueryRegistro.append("ON RO.CVE_ID_INFORMACION_OBRA_PRIN = RO.CVE_ID_INFORMACION_OBRA ");
		strQueryRegistro.append("LEFT OUTER JOIN MGPBDTU9X.ROC_ESTATUS_OBRA EO ");
		strQueryRegistro.append("ON RO.CVE_ID_ESTATUS_OBRA = EO.CVE_ID_ESTATUS_OBRA ");
		if(input.getOrder() != null && input.getDesc() != null) {
			strQueryRegistro.append(" order by ");			
			if(input.getOrder().equals("numRegistroObra")) {
				strQueryRegistro.append("RO.CVE_REGISTRO_OBRA");
			}
			else if(input.getOrder().equals("codigoPostal"))  {
				strQueryRegistro.append("UO.CODIGO_POSTAL");
			}
			else if(input.getOrder().equals("calle"))  {
				strQueryRegistro.append("UO.CALLE");
			}
			else if(input.getOrder().equals("numeroExterior"))  {
				strQueryRegistro.append("UO.NUM_EXTERIOR");
			}
			else if(input.getOrder().equals("colonia"))  {
				strQueryRegistro.append("UO.REF_COLONIA");
			}
			else if(input.getOrder().equals("municipioAlcaldia"))  {
				strQueryRegistro.append("UO.REF_MUNICIPIO");
			}
			else if(input.getOrder().equals("fechaRegistroAlta"))  {
				strQueryRegistro.append("RO.FEC_REGISTRO_ALTA");
			}
			else if(input.getOrder().equals("claseObra"))  {
				strQueryRegistro.append("CO.DES_CLASIFICACION_OBRA");
			}
			else if(input.getOrder().equals("tipoObra"))  {
				strQueryRegistro.append("TOO.DES_TIPO_OBRA");
			}			
			strQueryRegistro.append(" ");
			if (input.getDesc().equals("true")) {
				strQueryRegistro.append(" desc ");
			}
			else if(input.getDesc().equals("false")) {
				strQueryRegistro.append(" asc ");
			}
		}
		try {
			log.debug("el query a ejecutar de registroDeObra es "
					+ strQueryRegistro);
			SQLQuery sqlQuery = getSession().createSQLQuery(
					strQueryRegistro.toString());
			sqlQuery.setParameter(0,
					((ObraGeneralExtPrto) input.getModel()).getCodigoPostal());
			sqlQuery.setParameter(1, MatchMode.ANYWHERE
					.toMatchString(((ObraGeneralExtPrto) input.getModel())
							.getColonia()));
			sqlQuery.setFirstResult((input.getPage() - 1) * input.getPageSize());
			sqlQuery.setMaxResults(input.getPageSize());
			List<ObraGeneralExtPrto> listDetalleRegistroObra = sqlQuery
					.setResultTransformer(
							Transformers.aliasToBean(ObraGeneralExtPrto.class))
					.list();
			if (listDetalleRegistroObra != null
					&& !listDetalleRegistroObra.isEmpty()) {
				ScrollableResults scroll = getSession()
						.createSQLQuery(strQueryRegistro.toString())
						.setParameter(
								0,
								((ObraGeneralExtPrto) input.getModel())
										.getCodigoPostal())
						.setParameter(
								1,
								MatchMode.ANYWHERE
										.toMatchString(((ObraGeneralExtPrto) input
												.getModel()).getColonia()))
						.scroll(ScrollMode.SCROLL_SENSITIVE);
				scroll.last();
				log.debug(" la cosulta de detalle de regisro de obra si trae registros "
						+ listDetalleRegistroObra.size());
				pagina.setContent(listDetalleRegistroObra);
				pagina.setNumberOfElements(listDetalleRegistroObra.size());
				pagina.setSize(input.getPageSize());
				pagina.setNumber(input.getPage() - 1);
				pagina.setTotalElements(scroll.getRowNumber() + 1);
				pagina.setTotalPages(Double.valueOf(
						Math.ceil((scroll.getRowNumber() + 1)
								/ input.getPageSize())).intValue());
			}
			return pagina;
		} catch (Exception e) {
			log.error(
					"ocurio un error al consultar el  obrasRegistradasPRTO {}",
					((ObraGeneralExtPrto) input.getModel()).getCodigoPostal(),
					e);
			throw e;
		}
	}
	
	@Override
	public boolean existeMarcaPRTO(String numObra) throws Exception {
		log.debug("llegando a la consulta obrasSimilares numObra: {}", numObra);
		StringBuffer strQueryRegistro = new StringBuffer();
		strQueryRegistro
				.append("select count(1) from MGPBDTU9X.ROT_INF_OBRA_MARCA_PRTO where CVE_ID_INFORMACION_OBRA = ?");
		try {
			ObraGeneralExtPrto obra = detalleObraPRTO(numObra);
			log.debug("el query a ejecutar de registroDeObra es "
					+ strQueryRegistro);
			SQLQuery sqlQuery = getSession().createSQLQuery(
					strQueryRegistro.toString());
			sqlQuery.setParameter(0, obra.getCveInformacionObra());
			BigDecimal marca = (BigDecimal) sqlQuery.uniqueResult();
			return marca != null && marca.intValue() > 0;
		} catch (Exception e) {
			log.error(
					"ocurio un error al consultar el  obrasRegistradasPRTO {}",
					numObra, e);
			throw e;
		}
	}
	
	@Override
	public ObraGeneralExtPrto insertaMarcaPRTO(String numObra,
			String marcaPRTO) throws Exception {
		log.debug("llegando a la consulta obrasSimilares numObra: {}", numObra);
		StringBuffer strQueryRegistro = new StringBuffer();
		strQueryRegistro
				.append("insert into MGPBDTU9X.ROT_INF_OBRA_MARCA_PRTO (CVE_ID_MARCA_PRTO, CVE_ID_INFORMACION_OBRA, REF_MARCA_PRTO) values (MGPBDTU9X.SEQ_ROTINFOBRAMARCAPRTO.nextval, ?, ?)");
		try {
			ObraGeneralExtPrto obra = detalleObraPRTO(numObra);
			log.debug("el query a ejecutar de registroDeObra es "
					+ strQueryRegistro);
			SQLQuery sqlQuery = getSession().createSQLQuery(
					strQueryRegistro.toString());
			sqlQuery.setParameter(0, obra.getCveInformacionObra());
			sqlQuery.setParameter(1, marcaPRTO);
			int a = sqlQuery.executeUpdate();
			return a > 0 ? detalleObraPRTO(numObra) : null;
		} catch (Exception e) {
			log.error(
					"ocurio un error al consultar el  obrasRegistradasPRTO {}",
					numObra, e);
			throw e;
		}
	}
	
	@Override
	public ObraGeneralExtPrto actualizaMarcaPRTO(String numObra, String marcaPRTO)
			throws Exception {
		log.debug("llegando a la consulta obrasSimilares numObra: {}", numObra);
		StringBuffer strQueryRegistro = new StringBuffer();
		strQueryRegistro
				.append("update MGPBDTU9X.ROT_INF_OBRA_MARCA_PRTO set REF_MARCA_PRTO = ?, FEC_REGISTRO_ACTUALIZADO = SYSDATE where CVE_ID_INFORMACION_OBRA = ?");
		try {
			ObraGeneralExtPrto obra = detalleObraPRTO(numObra);
			log.debug("el query a ejecutar de registroDeObra es "
					+ strQueryRegistro);
			SQLQuery sqlQuery = getSession().createSQLQuery(
					strQueryRegistro.toString());
			sqlQuery.setParameter(0, marcaPRTO);
			sqlQuery.setParameter(1, obra.getCveInformacionObra());
			int a = sqlQuery.executeUpdate();
			return a > 0 ? detalleObraPRTO(numObra) : null;
		} catch (Exception e) {
			log.error(
					"ocurio un error al consultar el  obrasRegistradasPRTO {}",
					numObra, e);
			throw e;
		}
	}

		
			//Consulta para AVISO DE OBRAS  Aviso de Ubicación de Obras
			@SuppressWarnings("unchecked")
			@Override
			public Page<RegistroObraDetalle> consultaUbicacionObra(ObrasSimilaresInputSiroc<ObraSirocInput> input) throws Exception {
				log.debug("llegando a la consulta BD consultaUbicacionObra ");
				boolean idDelegacionCheck = false, idSubdelegacionCheck = false, idClaseObraCheck =false,idTipoPatronCheck =false, idTipoIncidenciaCheck =false, estatusObraCheck = false ;
				Page<RegistroObraDetalle> pagina = new Page<RegistroObraDetalle>();
				StringBuffer strQueryRegistro = new StringBuffer();
				strQueryRegistro.append("SELECT ");
				strQueryRegistro.append("  AO.CVE_ID_TRAMITE AS \"idTramite\",");
				strQueryRegistro.append("  AO.CVE_ID_AVISO_OBRA AS \"idAvisObra\",");
				strQueryRegistro.append("  D.DES_DELEG AS \"delegacion\",");
				strQueryRegistro.append("  S.DES_SUBDELEGACION AS \"subDelegacion\",");
				strQueryRegistro.append("  ao.CVE_REGISTRO_AVISO_OBRA AS \"registroAvisoObra\", ");
				//--Información del patrón
				strQueryRegistro.append("  TP.DES_TIPO_PATRON AS \"tipoPatron\", ");
				strQueryRegistro.append("  IP.ROT_RFC AS  \"rfc\", ");
				strQueryRegistro.append("  IP.ROT_RAZON_SOCIAL AS  \"nombreRazonSocial\", ");
				strQueryRegistro.append("  IP.ROT_REG_PATRONAL AS \"rp\", ");
				//--Información del aviso
				strQueryRegistro.append("  AO.CVE_RFC_PATRON AS \"rfcPatron\", ");
				strQueryRegistro.append("  TO_CHAR(AO.fec_ini_obra, 'DD/MM/YYYY') AS \"fecIniObra\", "); 
				strQueryRegistro.append("  TO_CHAR(AO.fec_fin_obra, 'DD/MM/YYYY') AS \"fecFinObra\", "); 
				strQueryRegistro.append("  AO.imp_obra AS  \"impObra\", ");
				//--Ubicación de la obra
				strQueryRegistro.append("  uo.calle  AS  \"calle\", "); 
				strQueryRegistro.append("  uo.num_exterior AS  \"numExterior\", "); 
				strQueryRegistro.append("  uo.num_exterior_alf AS  \"numExteriorALF\", "); 
				strQueryRegistro.append("  uo.num_exterior_dos AS \"numExteriorDos\", "); 
				strQueryRegistro.append("  uo.num_interior AS  \"numInterior\", "); 
				strQueryRegistro.append("  uo.num_interior_alf AS \"numInteriorALF\", "); 
				strQueryRegistro.append("  uo.codigo_postal AS  \"codigoPostal\", "); 
				strQueryRegistro.append("  uo.ref_entidad AS  \"entidad\", "); 
				strQueryRegistro.append("  uo.ref_municipio AS  \"municipioAlcaldia\", "); 
				strQueryRegistro.append("  uo.ref_colonia AS  \"colonia\", ");
				strQueryRegistro.append("  ( ");
				strQueryRegistro.append("  CASE ");
				strQueryRegistro.append("    WHEN AO.REF_ESTADO_REG = 0 THEN 'CERRADO' ");
				strQueryRegistro.append("    WHEN AO.REF_ESTADO_REG = 1 THEN 'ABIERTO' ");
				strQueryRegistro.append("  END)    AS \"estatus\", ");
				strQueryRegistro.append("  RIO.CVE_REGISTRO_OBRA AS  \"idregistrObra\", ");///modificar
				strQueryRegistro.append(" CASE");
				strQueryRegistro.append(" WHEN  RIO.CVE_REGISTRO_OBRA IS  NULL THEN ");
				strQueryRegistro.append(" 		 NULL	");
				strQueryRegistro.append("  ELSE ");
				strQueryRegistro.append(" (  SELECT DTR.FEC_REGISTRO_ALTA ");
				strQueryRegistro.append("  FROM MGPBDTU9X.ROT_INFORMACION_OBRA RO ");
				strQueryRegistro.append("  INNER JOIN MGPBDTU9X.DIT_TRAMITE DTR ON DTR.CVE_ID_TRAMITE=RO.CVE_ID_TRAMITE ");
				strQueryRegistro.append("  WHERE RO.CVE_REGISTRO_OBRA=RIO.CVE_REGISTRO_OBRA ");
				strQueryRegistro.append("  )");
				strQueryRegistro.append("  END AS  \"fechaBloqueo\", ");
				strQueryRegistro.append("  CASE ");
				strQueryRegistro.append("  WHEN RIO.CVE_REGISTRO_OBRA IS  NULL THEN ");
				strQueryRegistro.append("  	  NULL ");
				strQueryRegistro.append("  ELSE ");
				strQueryRegistro.append("   ( ");
				strQueryRegistro.append("   SELECT IP.ROT_RFC ");
				strQueryRegistro.append("  FROM MGPBDTU9X.ROT_INFORMACION_OBRA RO ");
				strQueryRegistro.append("  LEFT OUTER JOIN MGPBDTU9X.ROT_INFORMACION_PATRON  IFP  ON RO.CVE_ID_INFORMACION_PATRON     = IFP.CVE_ID_INFORMACION_PATRON ");
				strQueryRegistro.append("  WHERE RO.CVE_REGISTRO_OBRA=RIO.CVE_REGISTRO_OBRA ");
				strQueryRegistro.append("  ) ");
				strQueryRegistro.append("  END AS \"rfcBloquea\", ");
				strQueryRegistro.append("  TRR.FEC_REGISTRO_ALTA  AS  \"fecRegistroAlta\", ");
				strQueryRegistro.append("  co.des_clasificacion_obra AS \"claseObra\", ");  
				strQueryRegistro.append("  TO_CHAR(ao.fec_registro_alta, 'DD/MM/YYYY') AS  \"fechaRegistro\", "); 
				strQueryRegistro.append("  rio.cve_registro_obra AS  \"numeroRegistroObra\" ");
				strQueryRegistro.append(" FROM MGPBDTU9X.ROT_INFORMACION_OBRA RIO ");
				strQueryRegistro.append(" LEFT OUTER JOIN MGPBDTU9X.ROT_AVISO_OBRA AO ON rio.cve_id_aviso_obra = ao.cve_id_aviso_obra");
				strQueryRegistro.append(" LEFT OUTER JOIN MGPBDTU9X.ROT_INFORMACION_PATRON  IP  ON AO.CVE_ID_INFORMACION_PATRON = IP.CVE_ID_INFORMACION_PATRON");
				strQueryRegistro.append(" LEFT OUTER JOIN MGPBDTU9X.ROT_UBICACION_OBRA      UO  ON AO.CVE_ID_UBICACION_OBRA     = UO.CVE_ID_UBICACION_OBRA");
				strQueryRegistro.append(" LEFT OUTER JOIN MGPBDTU9X.ROC_TIPO_PATRON         TP  ON IP.CVE_ID_TIPO_PATRON        = TP.CVE_ID_TIPO_PATRON");
				strQueryRegistro.append(" LEFT OUTER JOIN MGPBDTU9X.ROC_TIPO_OBRA           TOO  ON RIO.CVE_ID_TIPO_OBRA              = TOO.CVE_ID_TIPO_OBRA");
				strQueryRegistro.append(" LEFT OUTER JOIN MGPBDTU9X.ROC_CLASIFICACION_OBRA  CO  ON TOO.CVE_ID_CLASIFICACION_OBRA     = CO.CVE_ID_CLASIFICACION_OBRA");
				strQueryRegistro.append(" LEFT OUTER JOIN MGPBDTU9X.DIT_TRAMITE             TRR ON TRR.CVE_ID_TRAMITE      = RIO.CVE_ID_TRAMITE");
				strQueryRegistro.append(" INNER JOIN MGPBDTU9X.DIC_SUBDELEGACION       S   ON AO.CVE_ID_SUBDELEGACION      = S.CVE_ID_SUBDELEGACION");
				strQueryRegistro.append(" INNER JOIN MGPBDTU9X.DIC_DELEGACION          D   ON S.CVE_ID_DELEGACION          = D.CVE_ID_DELEGACION");
				strQueryRegistro.append(" WHERE EXTRACT(YEAR FROM TRR.FEC_REGISTRO_ALTA) = :fecha");
				if (input.getModel().getIdDelegacion()!=null && !input.getModel().getIdDelegacion().isEmpty()){ 
					strQueryRegistro.append(" AND d.cve_id_delegacion 	   = :idDelegacion ");
					idDelegacionCheck = true;
				}
				if (input.getModel().getIdSubdelegacion()!=null && !input.getModel().getIdSubdelegacion().isEmpty()){
					strQueryRegistro.append(" AND s.cve_id_subdelegacion 	   = :idSubdelegacion");
					idSubdelegacionCheck = true;
				}
				if (input.getModel().getIdClaseObra()!=null && !input.getModel().getIdClaseObra().isEmpty()){
					strQueryRegistro.append(" AND co.cve_id_clasificacion_obra = :idClaseObra");
					idClaseObraCheck= true;
				}
				if (input.getModel().getIdTipoPatron()!=null && !input.getModel().getIdTipoPatron().isEmpty()){
					strQueryRegistro.append(" AND ip.cve_id_tipo_patron = :idTipoPatron");
					idTipoPatronCheck = true;
				}
				if (input.getModel().getCampoAproximacion() != null) {
					strQueryRegistro.append(" AND (AO.CVE_REGISTRO_AVISO_OBRA LIKE '%" + input.getModel().getCampoAproximacion()+ "%'");
					strQueryRegistro.append("OR IP.ROT_REG_PATRONAL LIKE  '%" + input.getModel().getCampoAproximacion()+ "%'");
					strQueryRegistro.append("OR IP.ROT_RFC LIKE  '%" + input.getModel().getCampoAproximacion()+ "%'");
					strQueryRegistro.append("OR co.des_clasificacion_obra LIKE '%" + input.getModel().getCampoAproximacion()+ "%'");
					strQueryRegistro.append("OR TP.DES_TIPO_PATRON LIKE '%" + input.getModel().getCampoAproximacion()+ "%'");
					strQueryRegistro.append("OR (   CASE ");
					strQueryRegistro.append("WHEN AO.REF_ESTADO_REG = 0 THEN 'CERRADO' ");
					strQueryRegistro.append("WHEN AO.REF_ESTADO_REG = 1 THEN 'ABIERTO' ");
					strQueryRegistro.append("END)  LIKE  '%" + input.getModel().getCampoAproximacion()+ "%'");
					strQueryRegistro.append("OR  TO_CHAR(ao.fec_registro_alta, 'DD/MM/YYYY') LIKE '%" + input.getModel().getCampoAproximacion()+ "%')");
				}
				
				if (input.getOrder() == null || input.getDesc() == null) {
					strQueryRegistro.append(" Order by  TRR.FEC_REGISTRO_ALTA DESC ");
				}
				else if(input.getOrder().equals("registroAvisoObra")) {
					strQueryRegistro.append(" Order by  ao.CVE_REGISTRO_AVISO_OBRA  ");
					strQueryRegistro.append(input.getDesc().equals("true") ? " DESC " : " ASC ");
				}
				else if(input.getOrder().equals("rp")) {
					strQueryRegistro.append(" Order by  IP.ROT_REG_PATRONAL ");
					strQueryRegistro.append(input.getDesc().equals("true") ? " DESC " : " ASC ");
				}
				else if(input.getOrder().equals("rfcPatron")) {
					strQueryRegistro.append(" Order by  IP.ROT_RFC ");
					strQueryRegistro.append(input.getDesc().equals("true") ? " DESC " : " ASC ");
				}
				else if(input.getOrder().equals("claseObra")) {
					strQueryRegistro.append(" Order by  co.DES_CLASIFICACION_OBRA ");
					strQueryRegistro.append(input.getDesc().equals("true") ? " DESC " : " ASC ");
				}
				else if(input.getOrder().equals("tipoPatron")) {
					strQueryRegistro.append(" Order by  TP.DES_TIPO_PATRON ");
					strQueryRegistro.append(input.getDesc().equals("true") ? " DESC " : " ASC ");
				}
				else if(input.getOrder().equals("estatus")) {
					strQueryRegistro.append(" Order by  AO.REF_ESTADO_REG");
					strQueryRegistro.append(input.getDesc().equals("true") ? " DESC " : " ASC ");
				}
				else if(input.getOrder().equals("fechaRegistro")) {
					strQueryRegistro.append(" Order by  TRR.FEC_REGISTRO_ALTA ");
					strQueryRegistro.append(input.getDesc().equals("true") ? " DESC " : " ASC ");
				}
				else {
					strQueryRegistro.append(" Order by  TRR.FEC_REGISTRO_ALTA DESC ");
				}
				try {
					log.debug("el query a ejecutar de consultaUbicacionObra es " + strQueryRegistro );
					SQLQuery sqlQuery = getSession().createSQLQuery(strQueryRegistro.toString());
					sqlQuery.setParameter("fecha",input.getModel().getAnioFiscal());
					if (idDelegacionCheck) 
						sqlQuery.setParameter("idDelegacion",Long.valueOf(input.getModel().getIdDelegacion()).longValue());
					
					if (idSubdelegacionCheck)
						sqlQuery.setParameter("idSubdelegacion", Long.valueOf(input.getModel().getIdSubdelegacion()).longValue() );
					
					if (idClaseObraCheck) 
						sqlQuery.setParameter("idClaseObra",Long.valueOf(input.getModel().getIdClaseObra()).longValue());
					
					if (idTipoPatronCheck) 
						sqlQuery.setParameter("idTipoPatron", Long.valueOf(input.getModel().getIdTipoPatron()).longValue());
					
					sqlQuery.setFirstResult((input.getPage() - 1) * input.getPageSize());
					sqlQuery.setMaxResults(input.getPageSize());
					List<RegistroObraDetalle> listRegistroObra =
							sqlQuery.setResultTransformer(Transformers.aliasToBean(RegistroObraDetalle.class)).list();
					
					if (listRegistroObra != null
							&& !listRegistroObra.isEmpty()) {
						 Query query = getSession().createSQLQuery(strQueryRegistro.toString()).setParameter("fecha",input.getModel().getAnioFiscal());
						 
						 if (idDelegacionCheck)
							 query.setParameter("idDelegacion",Long.valueOf(input.getModel().getIdDelegacion()).longValue());
							
						 if (idSubdelegacionCheck)
							 query.setParameter("idSubdelegacion",Long.valueOf(input.getModel().getIdSubdelegacion()).longValue()  );
							
						 if (idClaseObraCheck)
							 query.setParameter("idClaseObra",Long.valueOf(input.getModel().getIdClaseObra()).longValue() );
								
						 if (idTipoPatronCheck) 
							 query.setParameter("idTipoPatron",Long.valueOf(input.getModel().getIdTipoPatron()).longValue()  );
						
						 ScrollableResults scroll = query.scroll(ScrollMode.SCROLL_SENSITIVE);
						scroll.last();
						log.debug(" la cosulta de detalle de regisro de obra si trae registros "
								+ listRegistroObra.size());
						pagina.setContent(listRegistroObra);
						pagina.setNumberOfElements(listRegistroObra.size());
						pagina.setSize(input.getPageSize());
						pagina.setNumber(input.getPage() - 1);
						pagina.setTotalElements(scroll.getRowNumber() + 1);
						pagina.setTotalPages(Double.valueOf(
								Math.ceil((scroll.getRowNumber() + 1)
										/ input.getPageSize())).intValue());
					}
					return pagina;
				}catch(Exception e) {
					log.error("ocurio un error al consultar consultaUbicacionObra " +  e );
					throw e;
				}
			}

			@Override
			public Page<ConsultaObraDetalle> getConsultaObra(ObrasSimilaresInputSiroc<ObraSirocInput> datObra) throws Exception {
				log.debug("llegando a la consulta BD getConsultaObra ");
				Page<ConsultaObraDetalle> pagina = new Page<ConsultaObraDetalle>();
				StringBuffer strQueryRegistro = new StringBuffer();
				boolean idDelegacionCheck = false, idSubdelegacionCheck = false, idClaseObraCheck =false,idTipoPatronCheck =false, idTipoIncidenciaCheck =false, estatusObraCheck = false ;
				
				strQueryRegistro.append("SELECT  RO.CVE_ID_INFORMACION_OBRA AS \"idInformacionObra\",");
				strQueryRegistro.append("  RO.CVE_REGISTRO_OBRA  AS \"idNumRegistroObra\", ");
				strQueryRegistro.append("  D.DES_DELEG  AS \"delegacion\", ");
				strQueryRegistro.append("  S.DES_SUBDELEGACION  AS \"subDelegacion\", ");
				strQueryRegistro.append("  ip.rot_rfc  AS \"rfc\", ");
				strQueryRegistro.append("  IP.ROT_RAZON_SOCIAL  AS \"nombreRazonSocial\", ");  
				strQueryRegistro.append("  IP.ROT_REG_PATRONAL AS \"rp\", "); 
				strQueryRegistro.append("  uo.calle AS \"calle\", "); 
				strQueryRegistro.append("  uo.num_exterior AS \"numExterior\", "); 
				strQueryRegistro.append("  uo.num_exterior_alf AS \"numExteriorALF\", "); 
				strQueryRegistro.append("  uo.num_exterior_dos AS \"numExteriorDos\", "); 
				strQueryRegistro.append("  uo.num_interior AS \"numInterior\", "); 
				strQueryRegistro.append("  uo.num_interior_alf AS \"numInteriorALF\", "); 
				strQueryRegistro.append("  uo.codigo_postal AS \"codigoPostal\", "); 
				strQueryRegistro.append("  uo.ref_entidad AS \"refEntidad\", "); 
				strQueryRegistro.append("  uo.ref_municipio AS \"refMunicipio\", "); 
				strQueryRegistro.append("  uo.ref_colonia AS \"refColonia\", "); 
				strQueryRegistro.append("  ro.fec_ini_obra AS \"fecIniObra\", "); 
				strQueryRegistro.append("  ro.fec_fin_obra AS \"fecFinObra\", "); 
				strQueryRegistro.append("  ro.fec_ini_contrato AS \"fecIniContrato\", "); 
				strQueryRegistro.append("  ro.fec_fin_contrato AS \"fecFinContrato\", "); 
				strQueryRegistro.append("  ro.imp_obra AS \"impObra\", "); 
				strQueryRegistro.append("  ro.imp_ejercido AS \"impEjercido\", "); 
				strQueryRegistro.append("  ro.ref_sup_construccion AS \"refSupConstruccion\", "); 
				strQueryRegistro.append("  too.des_tipo_obra AS \"desTipoObra\", "); 
				strQueryRegistro.append("  CO.DES_CLASIFICACION_OBRA AS \"desClasifiObra\", "); 
				strQueryRegistro.append("  TP.DES_TIPO_PATRON AS  \"tipoPatron\", ");
				strQueryRegistro.append("  ro.num_procedimiento AS  \"numProcedimiento\", ");
				strQueryRegistro.append("  ro.ref_observacion AS  \"refObservacion\", ");
				strQueryRegistro.append("  ro.ref_otro_objeto_contrato AS  \"refOtroObjetoContrato\", ");
				strQueryRegistro.append("  EO.DES_ESTATUS_OBRA AS  \"estatusObra\", "); 
				strQueryRegistro.append("  TO_CHAR(RO.FEC_REGISTRO_ALTA,'dd/MM/yyyy') AS \"fechaRegistro\", "); 
				strQueryRegistro.append("  ro.num_actualiza AS \"numActualiza\", ");
				strQueryRegistro.append("  ro.fec_bloqueo_obra AS \"fechaBloqueo\", ");
				strQueryRegistro.append("  ro.num_aprox_trabajadores AS \"numAproxTrabajadores\", ");
				strQueryRegistro.append("  ro.num_reg_stps AS \"numRegStps\", ");
				strQueryRegistro.append("  ro.ref_objcont_subesp AS \"refObjcontSubesp\", ");
				strQueryRegistro.append("  d.cve_id_delegacion AS \"cveIdDelegacion\", "); 
				strQueryRegistro.append("  S.cve_id_subdelegacion AS \"cveIdSubdelegacion\", "); 
				strQueryRegistro.append("  mti.cve_id_tipo_incidencia AS \"cveIdTipoIncidencia\", "); 
				strQueryRegistro.append("  RO.CVE_REGISTRO_OBRA AS \"id\" ");
				strQueryRegistro.append(" FROM MGPBDTU9X.ROT_INFORMACION_OBRA RO ");
				strQueryRegistro.append(" LEFT OUTER JOIN MGPBDTU9X.DIC_SUBDELEGACION       S   ON RO.CVE_ID_SUBDELEGACION          = S.CVE_ID_SUBDELEGACION");
				strQueryRegistro.append(" LEFT OUTER JOIN MGPBDTU9X.DIC_DELEGACION          D   ON S.CVE_ID_DELEGACION              = D.CVE_ID_DELEGACION");
				strQueryRegistro.append(" LEFT OUTER JOIN MGPBDTU9X.ROT_INFORMACION_PATRON  IP  ON RO.CVE_ID_INFORMACION_PATRON     = IP.CVE_ID_INFORMACION_PATRON");
				strQueryRegistro.append(" LEFT OUTER JOIN MGPBDTU9X.ROT_UBICACION_OBRA      UO  ON RO.CVE_ID_UBICACION_OBRA         = UO.CVE_ID_UBICACION_OBRA");
				strQueryRegistro.append(" LEFT OUTER JOIN MGPBDTU9X.ROC_TIPO_OBRA           TOO  ON RO.CVE_ID_TIPO_OBRA              = TOO.CVE_ID_TIPO_OBRA");
				strQueryRegistro.append(" LEFT OUTER JOIN MGPBDTU9X.ROC_CLASIFICACION_OBRA  CO  ON TOO.CVE_ID_CLASIFICACION_OBRA     = CO.CVE_ID_CLASIFICACION_OBRA");
				strQueryRegistro.append(" LEFT OUTER JOIN MGPBDTU9X.ROC_TIPO_PATRON         TP  ON IP.CVE_ID_TIPO_PATRON            = TP.CVE_ID_TIPO_PATRON");
				strQueryRegistro.append(" LEFT OUTER JOIN MGPBDTU9X.ROC_ESTATUS_OBRA        EO  ON RO.CVE_ID_ESTATUS_OBRA           = EO.CVE_ID_ESTATUS_OBRA");
				strQueryRegistro.append(" LEFT OUTER JOIN MGPBDTU9X.DIT_TRAMITE             TR  ON TR.CVE_ID_TRAMITE                =RO.CVE_ID_TRAMITE");
				strQueryRegistro.append(" LEFT OUTER JOIN MGPBDTU9X.ROT_AVISO_OBRA          RAOB ON RAOB.CVE_ID_AVISO_OBRA          = RO.CVE_ID_AVISO_OBRA");
				strQueryRegistro.append(" LEFT OUTER JOIN MGPBDTU9X.rot_informacion_incidencia IC ON RO.cve_id_informacion_obra     = ic.cve_id_informacion_obra");
				strQueryRegistro.append(" LEFT OUTER JOIN MGPBDTU9X.roc_motivo_incidencia MTI ON ic.cve_id_motivo_incidencia = mti.cve_id_motivo_incidencia");
				strQueryRegistro.append(" LEFT OUTER JOIN MGPBDTU9X.roc_tipo_incidencia TPI ON mti.cve_id_tipo_incidencia = tpi.cve_id_tipo_incidencia");
				strQueryRegistro.append(" WHERE EXTRACT(YEAR FROM RO.FEC_REGISTRO_ALTA) = :fecha ");
				strQueryRegistro.append(" AND ic.cve_id_informacion_incidencia IN( ");
				strQueryRegistro.append("         SELECT  ");
				strQueryRegistro.append(" 		            MAX(nir.cve_id_informacion_incidencia) AS cve_id_informacion_incidencia ");
				strQueryRegistro.append(" FROM rot_informacion_incidencia nir ");
				strQueryRegistro.append(" WHERE nir.cve_id_informacion_obra = ic.cve_id_informacion_obra ");
				strQueryRegistro.append("         )  ");
				if (datObra.getModel().getIdDelegacion()!=null && !datObra.getModel().getIdDelegacion().isEmpty()){ 
					strQueryRegistro.append(" AND d.cve_id_delegacion    	   = :idDelegacion");
					idDelegacionCheck = true;
				}
				if (datObra.getModel().getIdSubdelegacion()!=null && !datObra.getModel().getIdSubdelegacion().isEmpty()){
					strQueryRegistro.append(" AND s.cve_id_subdelegacion 	   = :idSubdelegacion");
					idSubdelegacionCheck = true;
				}
				if (datObra.getModel().getIdClaseObra()!=null && !datObra.getModel().getIdClaseObra().isEmpty()){
					strQueryRegistro.append(" AND CO.CVE_ID_CLASIFICACION_OBRA = :idClaseObra");
					idClaseObraCheck= true;
				}
				if (datObra.getModel().getIdTipoPatron()!=null && !datObra.getModel().getIdTipoPatron().isEmpty()){
					strQueryRegistro.append(" AND ip.cve_id_tipo_patron = :idTipoPatron");
					idTipoPatronCheck = true;
				}
				if (datObra.getModel().getEstatusObra()!=null && !datObra.getModel().getEstatusObra().isEmpty()){
					strQueryRegistro.append(" AND ro.CVE_ID_ESTATUS_OBRA = :estatusObra");
					estatusObraCheck = true;
				}
				if (datObra.getModel().getIdTipoIncidencia()!=null && !datObra.getModel().getIdTipoIncidencia().isEmpty()){
					strQueryRegistro.append(" AND mti.cve_id_tipo_incidencia = :idTipoIncidencia");
					idTipoIncidenciaCheck =true;
				}
				if (datObra.getModel().getCampoAproximacion() != null) {
					strQueryRegistro.append("  AND (to_char(RO.CVE_REGISTRO_OBRA) LIKE '%" +datObra.getModel().getCampoAproximacion()+ "%'");
					strQueryRegistro.append("  OR IP.ROT_RAZON_SOCIAL LIKE '%" +datObra.getModel().getCampoAproximacion()+ "%'");
					strQueryRegistro.append("  OR IP.ROT_REG_PATRONAL LIKE '%" +datObra.getModel().getCampoAproximacion()+ "%'");
					strQueryRegistro.append("  OR co.des_clasificacion_obra LIKE '%" +datObra.getModel().getCampoAproximacion()+ "%'");
					strQueryRegistro.append("  OR TP.DES_TIPO_PATRON LIKE '%" +datObra.getModel().getCampoAproximacion()+ "%'");
					strQueryRegistro.append("  OR EO.DES_ESTATUS_OBRA  LIKE '%" +datObra.getModel().getCampoAproximacion()+ "%'"); 
					strQueryRegistro.append("  OR TO_CHAR(RO.FEC_REGISTRO_ALTA, 'dd/MM/yyyy') LIKE '%" +datObra.getModel().getCampoAproximacion()+ "%')");
				}
				if (datObra.getOrder() == null || datObra.getDesc() == null) {
					strQueryRegistro.append(" Order by  RO.FEC_REGISTRO_ALTA DESC ");
				}
				else if(datObra.getOrder().equals("idNumRegistroObra")) {
					strQueryRegistro.append(" Order by  RO.CVE_REGISTRO_OBRA  ");
					strQueryRegistro.append(datObra.getDesc().equals("true") ? " DESC " : " ASC ");
				}
				else if(datObra.getOrder().equals("nombreRazonSocial")) {
					strQueryRegistro.append(" Order by  IP.ROT_RAZON_SOCIAL ");
					strQueryRegistro.append(datObra.getDesc().equals("true") ? " DESC " : " ASC ");
				}
				else if(datObra.getOrder().equals("desClasifiObra")) {
					strQueryRegistro.append(" Order by  CO.DES_CLASIFICACION_OBRA ");
					strQueryRegistro.append(datObra.getDesc().equals("true") ? " DESC " : " ASC ");
				}
				else if(datObra.getOrder().equals("rp")) {
					strQueryRegistro.append(" Order by  IP.ROT_REG_PATRONAL ");
					strQueryRegistro.append(datObra.getDesc().equals("true") ? " DESC " : " ASC ");
				}
				else if(datObra.getOrder().equals("rp")) {
					strQueryRegistro.append(" Order by  IP.ROT_REG_PATRONAL ");
					strQueryRegistro.append(datObra.getDesc().equals("true") ? " DESC " : " ASC ");
				}
				else if(datObra.getOrder().equals("estatusObra")) {
					strQueryRegistro.append(" Order by  eo.DES_ESTATUS_OBRA");
					strQueryRegistro.append(datObra.getDesc().equals("true") ? " DESC " : " ASC ");
				}
				else if(datObra.getOrder().equals("fechaRegistro")) {
					strQueryRegistro.append(" Order by  RO.FEC_REGISTRO_ALTA ");
					strQueryRegistro.append(datObra.getDesc().equals("true") ? " DESC " : " ASC ");
				}
				else {
					strQueryRegistro.append(" Order by  RO.FEC_REGISTRO_ALTA DESC ");
				}
				try {
					log.debug("el query a ejecutar de getConsultaObra es " + strQueryRegistro );
					SQLQuery sqlQuery = getSession().createSQLQuery(strQueryRegistro.toString());
					sqlQuery.setParameter("fecha", datObra.getModel().getAnioFiscal());
					if (idDelegacionCheck) 
						sqlQuery.setParameter("idDelegacion",Long.valueOf(datObra.getModel().getIdDelegacion()).longValue());
					
					if (idSubdelegacionCheck)
						sqlQuery.setParameter("idSubdelegacion",Long.valueOf(datObra.getModel().getIdSubdelegacion()).longValue());
					
					if (idClaseObraCheck) 
						sqlQuery.setParameter("idClaseObra",Long.valueOf(datObra.getModel().getIdClaseObra()).longValue());
					
					if (idTipoPatronCheck) 
						sqlQuery.setParameter("idTipoPatron",Long.valueOf(datObra.getModel().getIdTipoPatron()).longValue());
					
					if (estatusObraCheck) 
						sqlQuery.setParameter("estatusObra",Long.valueOf(datObra.getModel().getEstatusObra()).longValue());
					
					if (idTipoIncidenciaCheck) 
						sqlQuery.setParameter("idTipoIncidencia",Long.valueOf(datObra.getModel().getIdTipoIncidencia()).longValue());
					
					sqlQuery.setFirstResult((datObra.getPage() - 1) * datObra.getPageSize());
					sqlQuery.setMaxResults(datObra.getPageSize());
					List<ConsultaObraDetalle> listRegistroObra =sqlQuery.setResultTransformer(Transformers.aliasToBean(ConsultaObraDetalle.class)).list();
					
					if (listRegistroObra != null
							&& !listRegistroObra.isEmpty()) {
//						ScrollableResults scroll = getSession().createSQLQuery(strQueryRegistro.toString())
						Query query = getSession().createSQLQuery(strQueryRegistro.toString()).setParameter("fecha",datObra.getModel().getAnioFiscal());
								
						if (idDelegacionCheck)
							query.setParameter("idDelegacion",Long.valueOf(datObra.getModel().getIdDelegacion()).longValue());
						
						if (idSubdelegacionCheck)
							query.setParameter("idSubdelegacion",Long.valueOf(datObra.getModel().getIdSubdelegacion()).longValue());
						
						if (idClaseObraCheck)
							query.setParameter("idClaseObra",Long.valueOf(datObra.getModel().getIdClaseObra()).longValue());
							
						if (idTipoPatronCheck) 
							query.setParameter("idTipoPatron",Long.valueOf(datObra.getModel().getIdTipoPatron()).longValue());
							
						if (estatusObraCheck) 	
							query.setParameter("estatusObra", Long.valueOf(datObra.getModel().getEstatusObra()).longValue());
						
						if (idTipoIncidenciaCheck)
							query.setParameter("idTipoIncidencia", Long.valueOf(datObra.getModel().getIdTipoIncidencia()).longValue());
						
						ScrollableResults scroll = query.scroll(ScrollMode.SCROLL_SENSITIVE);
						scroll.last();
						
						log.debug(" la cosulta de detalle de regisro de obra si trae registros "
								+ listRegistroObra.size());
						pagina.setContent(listRegistroObra);
						pagina.setNumberOfElements(listRegistroObra.size());
						pagina.setSize(datObra.getPageSize());
						pagina.setNumber(datObra.getPage() - 1);
						pagina.setTotalElements(scroll.getRowNumber() + 1);
						pagina.setTotalPages(Double.valueOf(
								Math.ceil((scroll.getRowNumber() + 1)
										/ datObra.getPageSize())).intValue());
					}
					return pagina;
				}catch(Exception e) {
					log.error("ocurio un error al consultar getConsultaObra " +  e );
					throw new Exception(e);
				}
			}
 
			@Override
			public List<ConsultaModel> tipoPatron() throws Exception {
				log.debug("llegando a la consulta tipoPatron");
				StringBuffer strQueryAviso = new StringBuffer();
				strQueryAviso.append(" SELECT tp.cve_id_tipo_patron AS \"id\" , ");
				strQueryAviso.append(" tp.des_tipo_patron AS \"descripcion\" ");
				strQueryAviso.append(" FROM roc_tipo_patron tp ");
				strQueryAviso.append(" WHERE  tp.fec_registro_baja IS NULL");
					log.debug("el query a ejecutar de tipoPatron es " + strQueryAviso );
					SQLQuery sqlQuery = getSession().createSQLQuery(strQueryAviso.toString());
					List<ConsultaModel> listTipoPatron =
							sqlQuery.setResultTransformer(Transformers.aliasToBean(ConsultaModel.class)).list();
					return listTipoPatron;
			}
			
			@Override
			public List<ConsultaModel> estatusObra() throws Exception {
				log.debug("llegando a la consulta estatusObra");
				StringBuffer strQueryAviso = new StringBuffer();
				strQueryAviso.append(" SELECT eo.cve_id_estatus_obra AS \"id\" , ");
				strQueryAviso.append(" eo.des_estatus_obra AS  \"descripcion\" ");
				strQueryAviso.append(" FROM roc_estatus_obra EO ");
				strQueryAviso.append(" WHERE  EO.fec_registro_baja IS NULL");
					log.debug("el query a ejecutar de estatusObra es " + strQueryAviso );
					SQLQuery sqlQuery = getSession().createSQLQuery(strQueryAviso.toString());
					List<ConsultaModel> listestatusObra =
							sqlQuery.setResultTransformer(Transformers.aliasToBean(ConsultaModel.class)).list();
					return listestatusObra;
			}
			
			@Override
			public List<ConsultaModel> rocTipoIncidencia() throws Exception {
				log.debug("llegando a la consulta rocTipoIncidencia");
				StringBuffer strQueryAviso = new StringBuffer();
				strQueryAviso.append(" SELECT ti.CVE_ID_TIPO_INCIDENCIA AS \"id\" , ");
				strQueryAviso.append(" ti.DES_TIPO_INCIDENCIA AS \"descripcion\" ");
				strQueryAviso.append(" FROM roc_tipo_incidencia ti ");
				strQueryAviso.append(" WHERE  ti.fec_registro_baja IS NULL");
					log.debug("el query a ejecutar de rocTipoIncidencia es " + strQueryAviso );
					SQLQuery sqlQuery = getSession().createSQLQuery(strQueryAviso.toString());
					List<ConsultaModel> listTipoIncidencia = sqlQuery.setResultTransformer(Transformers.aliasToBean(ConsultaModel.class)).list();
					return listTipoIncidencia;
			}
			
			
			
			@SuppressWarnings("unchecked")
			@Override
			public SirocOutput rocDetalleObra(String numObra) throws Exception {
				log.info("llegando a la consulta rocDetalleObra numObra: {}",numObra);
				StringBuffer strQueryAviso = new StringBuffer();
				strQueryAviso.append("SELECT ");
				strQueryAviso.append("        RO.CVE_ID_INFORMACION_OBRA      AS \"idObra\", ");
				strQueryAviso.append("        RO.CVE_REGISTRO_OBRA       AS \"numeroRegistroObra\", ");
				strQueryAviso.append("        D.DES_DELEG     AS \"delegacion\", ");
				strQueryAviso.append("        S.DES_SUBDELEGACION        AS \"subDelegacion\", ");
				strQueryAviso.append("        CO.DES_CLASIFICACION_OBRA  AS \"claseObra\", ");
				strQueryAviso.append("        TP.DES_TIPO_PATRON		 AS \"tipoPatron\", ");
				strQueryAviso.append("        TOO.DES_TIPO_OBRA                AS \"tipoObra\", ");
				strQueryAviso.append("        TO_CHAR(RO.FEC_REGISTRO_ALTA, 'DD/MM/YYYY')      AS \"fechaRegistroObra\", ");
				strQueryAviso.append("        RO.IMP_OBRA            		 			 AS \"montoObra\", ");
				strQueryAviso.append("        RO.REF_SUP_CONSTRUCCION            		 AS \"superficie\", ");
				strQueryAviso.append("        TO_CHAR(RO.FEC_INI_OBRA, 'DD/MM/YYYY')      AS \"fechaInicioObra\", ");
				strQueryAviso.append("        TO_CHAR(RO.FEC_FIN_OBRA, 'DD/MM/YYYY')      AS \"fechaFinObra\", ");
				strQueryAviso.append("        TO_CHAR(RO.FEC_INI_CONTRATO, 'DD/MM/YYYY')  AS \"fechaInicioContrato\", ");
				strQueryAviso.append("        TO_CHAR(RO.FEC_FIN_CONTRATO, 'DD/MM/YYYY')  AS \"fechaFinContrato\", ");
				strQueryAviso.append("        RO.NUM_PROCEDIMIENTO         				 AS \"numProcedimiento\", ");
				strQueryAviso.append("        RO.NUM_ACTUALIZA   	 AS \"actualizaciones\", ");
				strQueryAviso.append("        RO.num_reg_stps        					 AS \"numeroRepse\", ");
				strQueryAviso.append("        RO.ref_otro_objeto_contrato 				 AS \"objetoContrato\", ");
				strQueryAviso.append("        RO.num_aprox_trabajadores     	         AS \"numAproxTrabajadores\", ");
				strQueryAviso.append("        RAOB.cve_registro_aviso_obra          AS \"avisoBloqueado\", ");
				strQueryAviso.append("        case ");
				strQueryAviso.append("        when  RAOB.CVE_ID_AVISO_OBRA  is null then");
				strQueryAviso.append("        		null");
				strQueryAviso.append("       else");
				strQueryAviso.append("        TO_CHAR(TR.FEC_REGISTRO_ALTA, 'DD/MM/YYYY') ");
				strQueryAviso.append("        end AS \"fechaAvisoBloqueado\",");
				strQueryAviso.append("        RAOB.CVE_ID_AVISO_OBRA      AS \"idBloqueaAviso\", ");
				strQueryAviso.append("        		CASE ");
				strQueryAviso.append("         WHEN RO.CVE_ID_INFORMACION_OBRA_PRIN IS NULL THEN");
				strQueryAviso.append("        	NULL");
				strQueryAviso.append("        ELSE");
				strQueryAviso.append("        (");
				strQueryAviso.append("        	SELECT RFO.CVE_REGISTRO_OBRA");
				strQueryAviso.append("          FROM MGPBDTU9X.ROT_INFORMACION_OBRA RFO ");
				strQueryAviso.append("        	WHERE RFO.CVE_ID_INFORMACION_OBRA = RO.CVE_ID_INFORMACION_OBRA_PRIN");
				strQueryAviso.append("        )");
				strQueryAviso.append("        END AS \"numeroObraContratante\",");
				strQueryAviso.append("        RO.REF_OBSERVACION AS \"observacionObra\",");
				strQueryAviso.append("        RO.IMP_EJERCIDO AS \"montoEjercido\",");
				strQueryAviso.append("        RO.num_seq_notaria AS \"seqNotaria\",");
				//--Datos de la ubicacion
				strQueryAviso.append("        UO.CALLE 					AS \"calle\", ");
				strQueryAviso.append("    	  UO.NUM_EXTERIOR 			AS \"numExt\",");
				strQueryAviso.append("        UO.NUM_EXTERIOR_ALF 		AS \"numExtALF\",");
				strQueryAviso.append("    	  UO.NUM_EXTERIOR_DOS		AS \"numExtDos\",");
				strQueryAviso.append("        UO.NUM_INTERIOR 			AS \"numInterior\",");
				strQueryAviso.append("        UO.NUM_INTERIOR_ALF		AS \"numInteriorALF\",");
				strQueryAviso.append("        UO.REF_COLONIA 			AS \"colonia\",");
				strQueryAviso.append("    	  UO.REF_MUNICIPIO 			AS \"municipioAlcaldia\",");
				strQueryAviso.append("        UO.CODIGO_POSTAL 			AS \"codigoPostal\",");
				strQueryAviso.append("        UO.REF_ENTIDAD 			AS \"entidadFederativa\",");
				strQueryAviso.append("    	  UO.REF_OBSERVACION AS \"observacionUbicacion\",");
				//--Datos Patrón
				strQueryAviso.append("       IP.ROT_REG_PATRONAL 							AS \"rp\",");
				strQueryAviso.append("       IP.ROT_RAZON_SOCIAL		AS \"razonSocial\",");
				strQueryAviso.append("   	 IP.ROT_RFC 								AS \"rfc\",");
				strQueryAviso.append("       ip.fec_registro_alta	AS \"fechaRegistroPatron\",");
				//--Cumplimiento de la obra
				strQueryAviso.append("       EO.DES_ESTATUS_OBRA 				AS \"estatusObra\",");
				//-- Datos de la incidencia
				strQueryAviso.append("     	 rti.cve_id_tipo_incidencia											AS \"idTipoIncidencia\",");
				strQueryAviso.append("       rti.des_tipo_incidencia											AS \"desTipoIncidencia\", ");
				strQueryAviso.append("       TO_CHAR(rii.fec_registro_alta, 'DD/MM/YYYY') 					AS \"fechaIncidencia\",");
				strQueryAviso.append("    	 TO_CHAR(rii.fec_registro_actualizado, 'DD/MM/YYYY') 		AS \"fechaRegistroActualizado\",");
				strQueryAviso.append("       TO_CHAR(rii.fec_suspension, 'DD/MM/YYYY')					AS \"fecSuspension\", ");
				strQueryAviso.append("       TO_CHAR(rii.fec_reanudacion, 'DD/MM/YYYY')					AS \"fecReanudacion\"");
				strQueryAviso.append(" FROM MGPBDTU9X.ROT_INFORMACION_OBRA RO");
				strQueryAviso.append(" LEFT OUTER JOIN MGPBDTU9X.DIC_SUBDELEGACION       S   ON RO.CVE_ID_SUBDELEGACION          = S.CVE_ID_SUBDELEGACION");
				strQueryAviso.append(" LEFT OUTER JOIN MGPBDTU9X.DIC_DELEGACION          D   ON S.CVE_ID_DELEGACION              = D.CVE_ID_DELEGACION");
				strQueryAviso.append(" LEFT OUTER JOIN MGPBDTU9X.ROT_INFORMACION_PATRON  IP  ON RO.CVE_ID_INFORMACION_PATRON     = IP.CVE_ID_INFORMACION_PATRON");
				strQueryAviso.append(" LEFT OUTER JOIN MGPBDTU9X.ROT_UBICACION_OBRA      UO  ON RO.CVE_ID_UBICACION_OBRA         = UO.CVE_ID_UBICACION_OBRA");
				strQueryAviso.append(" LEFT OUTER JOIN MGPBDTU9X.ROC_TIPO_OBRA           TOO  ON RO.CVE_ID_TIPO_OBRA              = TOO.CVE_ID_TIPO_OBRA");
				strQueryAviso.append(" LEFT OUTER JOIN MGPBDTU9X.ROC_CLASIFICACION_OBRA  CO  ON TOO.CVE_ID_CLASIFICACION_OBRA     = CO.CVE_ID_CLASIFICACION_OBRA");
				strQueryAviso.append(" LEFT OUTER JOIN MGPBDTU9X.ROC_TIPO_PATRON         TP  ON IP.CVE_ID_TIPO_PATRON            = TP.CVE_ID_TIPO_PATRON");
				strQueryAviso.append(" LEFT OUTER JOIN MGPBDTU9X.ROT_INFORMACION_OBRA    RO2 ON RO.CVE_ID_INFORMACION_OBRA_PRIN  = RO.CVE_ID_INFORMACION_OBRA");
				strQueryAviso.append(" LEFT OUTER JOIN MGPBDTU9X.ROC_ESTATUS_OBRA        EO  ON RO.CVE_ID_ESTATUS_OBRA           = EO.CVE_ID_ESTATUS_OBRA");
				strQueryAviso.append(" LEFT OUTER JOIN MGPBDTU9X.DIT_TRAMITE             TR  ON TR.CVE_ID_TRAMITE                =RO.CVE_ID_TRAMITE");
				strQueryAviso.append(" LEFT OUTER JOIN MGPBDTU9X.ROT_AVISO_OBRA          RAOB ON RAOB.CVE_ID_AVISO_OBRA          = RO.CVE_ID_AVISO_OBRA");
				strQueryAviso.append(" LEFT OUTER JOIN MGPBDTU9X.rot_informacion_incidencia rii ON ro.cve_id_informacion_obra = rii.cve_id_informacion_obra");
				strQueryAviso.append(" LEFT OUTER JOIN MGPBDTU9X.roc_motivo_incidencia rmi ON rii.cve_id_motivo_incidencia = rmi.cve_id_motivo_incidencia");
				strQueryAviso.append(" LEFT OUTER JOIN MGPBDTU9X.roc_tipo_incidencia rti ON rmi.cve_id_tipo_incidencia = rti.cve_id_tipo_incidencia");
				strQueryAviso.append("    WHERE ");
				strQueryAviso.append("        RO.cve_registro_obra = ? ");
				strQueryAviso.append("    ORDER BY rii.fec_registro_alta ASC");
				try {
					DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
					log.debug("el query a ejecutar de rocDetalleObra es {}",
							strQueryAviso);
					SQLQuery sqlQuery = getSession().createSQLQuery(
							strQueryAviso.toString());
					sqlQuery.setParameter(0, numObra);
					List<ObradetalleOutput> detalleObra =  sqlQuery.setResultTransformer(Transformers.aliasToBean(ObradetalleOutput.class)).list();
					SirocOutput sirocO = new SirocOutput();
					List<IncidenciaOutput> lst = new ArrayList<IncidenciaOutput>();
					if (detalleObra != null&& !detalleObra.isEmpty()) {
						log.info(" la cosulta rocDetalleObra de obra si trae registros "+ detalleObra.size());
						IncidenciaOutput incFinal = new IncidenciaOutput();
						for (ObradetalleOutput d : detalleObra) {
							IncidenciaOutput inc = new IncidenciaOutput();
							if (d.getDesTipoIncidencia().equals("2")) {
								inc.setFechaIncidencia(d.getFecSuspension());
							} else if (d.getDesTipoIncidencia().equals("5")) {
								inc.setFechaIncidencia(d.getFecReanudacion());
							} else {
								inc.setFechaIncidencia(d.getFechaIncidencia());
							}
							inc.setTipoIncidencia(d.getDesTipoIncidencia());
							lst.add(inc);
						}
						lst.add(incFinal);
						sirocO.setIncidencias(lst);
						ObradetalleOutput obra = detalleObra.get(0);
						ObradetalleOutput o2 = detalleObra.get(detalleObra.size() -1);
						Obraoutput ob1 = new Obraoutput();
								
						setDatos(ob1, obra);
						//obj2
						UbicacionOutput ub1 =new UbicacionOutput();
						setDatos(ub1, obra);
						
						DatosPatronOutput dt1 = new DatosPatronOutput();
						setDatos(dt1, obra);
				
						//obj3
						CumplimientOutput ct = new CumplimientOutput();
//						setDatos(ct, obra);
						ct.setEstatusObra(obra.getEstatusObra());
						ct.setFechaRegistroInc(o2.getFechaIncidencia());
						
						sirocO.setObra(ob1);
						sirocO.setUbicacion(ub1);
						sirocO.setDatosPatron(dt1);
						sirocO.setCumplimiento(ct);
						
						return sirocO;
					}else {
						return null;
					}
					
				} catch (Exception e) {
					
					log.error("ocurio un error al consultar el  rocDetalleObra {}",numObra, e);
					throw e;
				
				}
			}

			@Override
			public List<ObraSirocOutput> getObrasHijasByRegObra(List<String> numObra) throws Exception {
				log.debug("llegando a la consulta getObrasHijasByRegObra++++");
				StringBuffer strQueryAviso = new StringBuffer();
				strQueryAviso.append(" SELECT * FROM (");
				strQueryAviso.append(" 		SELECT ");
				strQueryAviso.append(" 			ro.cve_registro_obra AS \"numeroRegistroObra\", ");
				strQueryAviso.append(" 			tp.des_tipo_patron AS \"tipoPatron\",");
				strQueryAviso.append("			ip.rot_razon_social AS \"nombreRazonSocial\",");
				strQueryAviso.append("			ro.fec_registro_baja AS \"fecRegBaja\",");
				strQueryAviso.append(" 			CASE");
				strQueryAviso.append(" 			  WHEN RO.CVE_ID_INFORMACION_OBRA_PRIN IS NULL THEN ");
				strQueryAviso.append(" 				NULL ");
				strQueryAviso.append("			  ELSE");
				strQueryAviso.append("  			(");
				strQueryAviso.append("  			 SELECT RFO.CVE_REGISTRO_OBRA");
				strQueryAviso.append(" 				 FROM MGPBDTU9X.ROT_INFORMACION_OBRA RFO");
				strQueryAviso.append(" 				 WHERE RFO.CVE_ID_INFORMACION_OBRA = RO.CVE_ID_INFORMACION_OBRA_PRIN");
				strQueryAviso.append(" 				 ) ");
				strQueryAviso.append(" 			  END \"numeroObraContratante\"");
				strQueryAviso.append(" 		FROM MGPBDTU9X.ROT_INFORMACION_OBRA RO");
				strQueryAviso.append(" 		LEFT OUTER JOIN MGPBDTU9X.ROT_INFORMACION_OBRA    RO2 ON RO.CVE_ID_INFORMACION_OBRA_PRIN  = RO.CVE_ID_INFORMACION_OBRA");
				strQueryAviso.append(" 		LEFT OUTER JOIN MGPBDTU9X.rot_informacion_patron IP ON  ro.cve_id_informacion_patron = ip.cve_id_informacion_patron");
				strQueryAviso.append(" 		INNER JOIN MGPBDTU9X.roc_tipo_patron TP ON ip.cve_id_tipo_patron = tp.cve_id_tipo_patron) T");
				strQueryAviso.append(" 		WHERE \"numeroObraContratante\" IN(:numObra)");
				strQueryAviso.append(" AND \"fecRegBaja\" IS NULL");					
				log.debug("el query a ejecutar de rocTipoIncidencia es " + strQueryAviso );
					SQLQuery sqlQuery = getSession().createSQLQuery(strQueryAviso.toString());
					sqlQuery.setParameterList("numObra", numObra);
					 
					List<ObraSirocOutput > listObrasHijasByRegObra = sqlQuery.setResultTransformer(Transformers.aliasToBean(ObraSirocOutput.class)).list();
					return listObrasHijasByRegObra;
				
			}
			
			private void setDatos(Object obj1, Object obj2 ){
				
				Field []flds = obj1.getClass().getDeclaredFields();
				for(Field f: flds) {
					try {
						String name = f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1, f.getName().length());
						if (!name.equals("SerialVersionUID")) {
						Object valor = obj2.getClass().getMethod("get" + name ).invoke(obj2);
						if (valor instanceof String) {
							obj1.getClass().getDeclaredMethod("set" + name , String.class).invoke(obj1,valor);
						}else if (valor instanceof BigDecimal) {
							obj1.getClass().getDeclaredMethod("set" + name , BigDecimal.class).invoke(obj1,valor);
						}else if (valor instanceof Date) {
							obj1.getClass().getDeclaredMethod("set" + name , Date.class).invoke(obj1,valor);
						}						
						}
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
}
