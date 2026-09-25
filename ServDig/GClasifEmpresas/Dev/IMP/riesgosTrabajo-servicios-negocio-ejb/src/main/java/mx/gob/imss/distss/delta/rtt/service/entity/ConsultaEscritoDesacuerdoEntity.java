package mx.gob.imss.distss.delta.rtt.service.entity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.riesgosTrabajo.RiesgosTrabajoException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.derechohabiente.DiasFestivos;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.escritoDesacuerdo.DomicilioEscritoDesacuerdo;
import mx.gob.imss.ctirss.delta.model.escritoDesacuerdo.MotivosDesacuerdo;
import mx.gob.imss.ctirss.delta.model.escritoDesacuerdo.TramiteEscritoDesacuerdo;
import mx.gob.imss.ctirss.delta.model.clasificacion.CodigoRolClasificacion;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;

import mx.gob.imss.ctirss.delta.persistence.DicDiasFestivo;
import mx.gob.imss.distss.delta.rtt.service.util.EscritoUtil;
import mx.gob.imss.distss.delta.rtt.service.util.ExcelPOIUtils;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;


@Stateless(name = "consultaEscritoDesacuerdoEntity", mappedName = "consultaEscritoDesacuerdoEntity")
public class ConsultaEscritoDesacuerdoEntity extends AbstractServiceEntity
		implements ConsultaEscritoDesacuerdoLocal {

	@Override
	public TramiteEscritoDesacuerdo getEscritoSimple(String folioRecepcion) {
		Query query = this.getSession().createSQLQuery(
				EscritoUtil.QUERY_DESACUERDO_SIMPLE.toString());
		query.setString("folioRecepcion", folioRecepcion);

		Object[] desacuerdo = (Object[]) query.uniqueResult();

		if (desacuerdo != null) {
			return EscritoUtil.escritoObjectToModel(desacuerdo);
		} 
		
		return null;
	}

	@Override
	public TramiteEscritoDesacuerdo findEscritoDesacuerdoFolio(
			String folio) throws RiesgosTrabajoException {
		StringBuffer queryBuffer = new StringBuffer();
		queryBuffer.append(EscritoUtil.QUERY_DESACUERDO_POR_FOLIO.toString());
		queryBuffer.append(" where desa.ref_folio_recepcion = :folioRecepcion ");

		Query query = this.getSession().createSQLQuery(queryBuffer.toString());
		query.setString("folioRecepcion", folio);

		List<Object[]> desacuerdos = (List<Object[]>) query.list();
		if (desacuerdos != null && !desacuerdos.isEmpty()) {
			return EscritoUtil.escritoObjectToModel(desacuerdos).get(0);
		} else {
			throw new RiesgosTrabajoException(
					"No existen Escritos de Desacuerdo");
		}
	}

	@Override
	public DomicilioEscritoDesacuerdo findDomEscritoDesacuerdo(Long idtramDomEsc) throws RiesgosTrabajoException {
			StringBuffer queryBuffer = new StringBuffer();
			queryBuffer.append(EscritoUtil.FIND_DOM_DESACUERDO.toString());
		Query queryDetalle = this.getSession().createSQLQuery(queryBuffer.toString());
		queryDetalle.setLong("idtramDomEsc", idtramDomEsc);

		Object[] ditDomicilioEscDes = (Object[]) queryDetalle.uniqueResult();
		log.debug("Resultado de la busqueda de domicilio "+ditDomicilioEscDes);

		if (ditDomicilioEscDes != null) {
			return EscritoUtil.domEscritoObjectToModel(ditDomicilioEscDes);
		} else {
			return null;
		}
	}

	@Override
	public List<MotivosDesacuerdo> getMotivosDesacuerdoList(int confirma) throws RiesgosTrabajoException {
		StringBuffer queryBuffer = new StringBuffer();
		queryBuffer.append(EscritoUtil.FIND_MOTIVOS_DESACUERDO.toString());
		if(confirma !=99){
			queryBuffer.append(" AND CVE_ID_MOTIVO_DESACUERDO = :cveIdMotivo");
		}

		Query queryDetalle = this.getSession().createSQLQuery(queryBuffer.toString());
		if(confirma !=99){
			queryDetalle.setLong("cveIdMotivo", Long.valueOf(confirma));
		}

		List<Object[]> objMotivos = (List<Object[]>) queryDetalle.list();
		if (objMotivos != null && !objMotivos.isEmpty()) {
			return EscritoUtil.motivoObjectToModal(objMotivos);
		} else {
			throw new RiesgosTrabajoException("No se logro consultar los motivos");
		}
	}

	@Override
	public List<MotivosDesacuerdo> getFraccionClaseList(String clase) throws RiesgosTrabajoException {
		StringBuffer queryBuffer = new StringBuffer();
		queryBuffer.append(EscritoUtil.FIND_FRACCION_CLASE.toString());

		Query query = this.getSession().createSQLQuery(queryBuffer.toString());
		query.setString("clase", clase);
		List<Object[]> fracClaseList = (List<Object[]>) query.list();
		if (fracClaseList != null && !fracClaseList.isEmpty()) {
			return EscritoUtil.fraccClaseObjectToModal(fracClaseList);
		} else {
			throw new RiesgosTrabajoException("No se logro consultar las Fracciones Clase");
		}
	}

	@Override
	public List<TramiteEscritoDesacuerdo> findEscritoDesacuerdoRegPatronPeriodo(
			String regPatron, Date fechaInicio, Date fechaFin, Delegacion delegacion,
			Subdelegacion subdelegacion)
			throws RiesgosTrabajoException {
		
		StringBuffer queryBuffer = new StringBuffer();
		queryBuffer.append(EscritoUtil.QUERY_DESACUERDO_DETALLE.toString());

		boolean existeRegpatronal =  regPatron!=null && !"".equals(regPatron);
		if(fechaInicio != null) {
			Calendar calendarInicio = Calendar.getInstance();
			calendarInicio.setTime(fechaInicio);
			calendarInicio.set(Calendar.HOUR_OF_DAY, 0);
			calendarInicio.set(Calendar.MINUTE, 0);
			calendarInicio.set(Calendar.SECOND, 0);

			fechaInicio = calendarInicio.getTime();
		}
		
		if(fechaFin != null) {
			Calendar calendarInicio = Calendar.getInstance();
			calendarInicio.setTime(fechaFin);
			calendarInicio.set(Calendar.HOUR, 23);
			calendarInicio.set(Calendar.MINUTE, 59);
			calendarInicio.set(Calendar.SECOND, 59);
			
			fechaFin = calendarInicio.getTime();
		}
		
		if(fechaInicio!=null && fechaFin!=null){
			queryBuffer.append(" and desa.fec_registro_alta between :fechaInicio and :fechaFin ");
		} else if(fechaInicio != null) {
			queryBuffer.append(" and desa.fec_registro_alta between :fechaInicio and sysdate");
		}
		
		if(existeRegpatronal){
			queryBuffer.append(" and llave.ref_busca = :regPatronal ");
		}

		Query query = buildQueryDelSubDel(queryBuffer, delegacion,  subdelegacion);
		
		if(existeRegpatronal){
			query.setString("regPatronal", regPatron);
		}
		
		if(fechaInicio!=null && fechaFin!=null){
			query.setDate("fechaFin", fechaFin);
			query.setDate("fechaInicio", fechaInicio);
		}else if(fechaInicio != null) {
			query.setDate("fechaInicio", fechaInicio);
		}

		List<Object[]> desacuerdosPorRegPatronPeriodo = (List<Object[]>) query.list();
		if (desacuerdosPorRegPatronPeriodo != null && !desacuerdosPorRegPatronPeriodo.isEmpty()) {
			return EscritoUtil.
					escritoObjectDetalleToModel(desacuerdosPorRegPatronPeriodo);
		} else {
			throw new RiesgosTrabajoException(
					"No existen Escritos de Desacuerdo");
		}
	}

	private Query buildQueryDelSubDel(StringBuffer queryBuffer,
			Delegacion delegacion, Subdelegacion subdelegacion){
		
		boolean existeDelegacion = delegacion != null
				&& delegacion.getId() != null;
		boolean existeSubdel = subdelegacion != null
				&& subdelegacion.getId() != null;
		
		if (existeDelegacion) {
			queryBuffer.append(" and dele.cve_id_delegacion = :idDelegacion ");
		}

		if (existeSubdel) {
			queryBuffer.append(" and subdel.cve_id_subdelegacion = :idSubDelegacion ");
		}

		queryBuffer.append(" order by desa.ref_folio_recepcion");
		
		Query query = this.getSession().createSQLQuery(queryBuffer.toString());
		if (existeDelegacion) {
			query.setBigDecimal("idDelegacion", BigDecimal.valueOf(delegacion.getId()));
		}

		if (existeSubdel) {
			query.setBigDecimal("idSubDelegacion", BigDecimal.valueOf(subdelegacion.getId()));
		}
		return query;
	}


	@Override
	public List<TramiteEscritoDesacuerdo> findEscritoDesacuerdoRegPatGralPeriodo(
			String regPatron, Date fechaInicio, Date fechaFin, Delegacion delegacion, Subdelegacion subdelegacion, PerfilUsuario perfilUsuario)
			throws RiesgosTrabajoException {

		StringBuffer queryBuffer = new StringBuffer();
		queryBuffer.append(EscritoUtil.QUERY_DESACUERDO_DETALLE.toString());

		boolean existeRegpatronal =  regPatron!=null && !"".equals(regPatron);
		if(fechaInicio != null) {
			Calendar calendarInicio = Calendar.getInstance();
			calendarInicio.setTime(fechaInicio);
			calendarInicio.set(Calendar.HOUR_OF_DAY, 0);
			calendarInicio.set(Calendar.MINUTE, 0);
			calendarInicio.set(Calendar.SECOND, 0);

			fechaInicio = calendarInicio.getTime();
		}

		if(fechaFin != null) {
			Calendar calendarInicio = Calendar.getInstance();
			calendarInicio.setTime(fechaFin);
			calendarInicio.set(Calendar.HOUR, 23);
			calendarInicio.set(Calendar.MINUTE, 59);
			calendarInicio.set(Calendar.SECOND, 59);

			fechaFin = calendarInicio.getTime();
		}

		if(fechaInicio!=null && fechaFin!=null){
			queryBuffer.append(" and desa.fec_registro_alta between :fechaInicio and :fechaFin ");
		} else if(fechaInicio != null) {
			queryBuffer.append(" and desa.fec_registro_alta between :fechaInicio and sysdate");
		}

		if(existeRegpatronal){
			queryBuffer.append(" and llave.ref_busca = :regPatronal ");
		}

		Query query=null;

	if (perfilUsuario.getIdPerfilUsuario() != null && perfilUsuario.getIdPerfilUsuario().intValue() == CodigoRolClasificacion.NORMATIVO_CENTRAL.getCodigo().intValue()){
		queryBuffer.append(" order by desa.ref_folio_recepcion");
		query = this.getSession().createSQLQuery(queryBuffer.toString());

	} else if (perfilUsuario.getIdPerfilUsuario() !=null && perfilUsuario.getIdPerfilUsuario().intValue() == CodigoRolClasificacion.NORMATIVO_DEL.getCodigo().intValue()){
		queryBuffer.append(" and dele.cve_id_delegacion = :idDelegacion ");
		queryBuffer.append(" order by desa.ref_folio_recepcion");

		query = this.getSession().createSQLQuery(queryBuffer.toString());
		query.setBigDecimal("idDelegacion", BigDecimal.valueOf(delegacion.getId()));
	
	}else if (perfilUsuario.getIdPerfilUsuario() != null && perfilUsuario.getIdPerfilUsuario().intValue() == CodigoRolClasificacion.NORMATIVO_SUBDEL.getCodigo().intValue()){
		queryBuffer.append(" and subdel.cve_id_subdelegacion = :idSubDelegacion ");
		queryBuffer.append(" order by desa.ref_folio_recepcion");

		query = this.getSession().createSQLQuery(queryBuffer.toString());
		query.setBigDecimal("idSubDelegacion", BigDecimal.valueOf(subdelegacion.getId()));
		
	}else {
		query = buildQueryDelSubDel(queryBuffer, delegacion,  subdelegacion);
	}
		if(existeRegpatronal){
			query.setString("regPatronal", regPatron);
		}

		if(fechaInicio!=null && fechaFin!=null){
			query.setDate("fechaFin", fechaFin);
			query.setDate("fechaInicio", fechaInicio);
		}else if(fechaInicio != null) {
			query.setDate("fechaInicio", fechaInicio);
		}

		List<Object[]> desacuerdosPorRegPatronGral = (List<Object[]>) query.list();
		if (desacuerdosPorRegPatronGral != null && !desacuerdosPorRegPatronGral.isEmpty()) {
			return EscritoUtil.
					escritoObjectDetalleToModel(desacuerdosPorRegPatronGral);
		} else {
			throw new RiesgosTrabajoException(
					"No existen Escritos de Desacuerdo");
		}
	}

	@Override
	public byte[] generaReporteEscritoDesacuerdo(List<TramiteEscritoDesacuerdo> findFoliosExcel) throws RiesgosTrabajoException {

		List<TramiteEscritoDesacuerdo> listaEscrito = findEscritoDesacuerdoFolioExcel(findFoliosExcel);

		ByteArrayOutputStream byteArrayWorkbook = new ByteArrayOutputStream();
		Map<Integer, Object[]> data = new TreeMap<Integer, Object[]>();
		Integer intRow = 1;
		data.put(0, EscritoUtil.COLUMNAS_REPORTE);
		for (TramiteEscritoDesacuerdo ed : listaEscrito) {
			data.put(intRow, new Object[] { ed.getFolioRecepcion(), ed.getPatron().getNrp(), ed.getPatron().getRazonSocial(), ed.getPatron().getSubdelegacion().getDelegacion().getDescripcion(),
					ed.getPatron().getSubdelegacion().getDescripcion(),ed.getCausaDesacuerdo().getMateriaDesacuerdo().getDescMateria(),ed.getFechaTramite(),ed.getCausaDesacuerdo().getDescCausaDes(),
					ed.getMotivoDesacuerdo()});
			intRow++;
		}

		XSSFWorkbook workbook = new XSSFWorkbook();
		ExcelPOIUtils.createSheet(workbook,data,"escritosDesacuerdo");
		try {
			try {
				workbook.write(byteArrayWorkbook);
			} finally {
				byteArrayWorkbook.close();
			}
		} catch (IOException e) {
			throw new RiesgosTrabajoException("No existen Escritos de Desacuerdo");
		}
		return byteArrayWorkbook.toByteArray();
	}

	public List<TramiteEscritoDesacuerdo> findEscritoDesacuerdoFolioExcel(
			List<TramiteEscritoDesacuerdo> escritosDesacuerdo) throws RiesgosTrabajoException {
		String[] listaEscritos= new String[escritosDesacuerdo.size()];
		for (int i = 0; i < escritosDesacuerdo.size(); i++) {
			listaEscritos[i] = escritosDesacuerdo.get(i).getFolioRecepcion();
		}

		StringBuffer queryBuffer = new StringBuffer();
		queryBuffer.append(EscritoUtil.QUERY_DESACUERDO_POR_FOLIO.toString());
		queryBuffer.append(" where desa.ref_folio_recepcion in (:folioRecepcion) ");
		Query query = this.getSession().createSQLQuery(queryBuffer.toString());
		query.setParameterList("folioRecepcion", listaEscritos);

		List<Object[]> desacuerdosList = (List<Object[]>) query.list();
		if (desacuerdosList != null && !desacuerdosList.isEmpty()) {
			return EscritoUtil.
					escritoObjectToModel(desacuerdosList);
		} else {
			throw new RiesgosTrabajoException(
					"No existen Escritos de Desacuerdo");
		}
	}

	public boolean saveDomEscritoDes(DomicilioEscritoDesacuerdo domEscrDes, int querAct) throws RiesgosTrabajoException {
		boolean bExito = false;
		StringBuffer queryBuffer = new StringBuffer();
		if(querAct == 1){
			queryBuffer.append(EscritoUtil.QUERY_DOM_DESACUERDO.toString());
		}else if(querAct == 2){
			queryBuffer.append(EscritoUtil.UPDATE_DOM_DESACUERDO.toString());
		}

		log.debug("Inicia registro de domiciio");
		Query query = this.getSession().createSQLQuery(queryBuffer.toString());
		query.setParameter("idDomEscrito", domEscrDes.getIdDomEscrito());
		query.setParameter("idEscrito", domEscrDes.getIdEscrito());
		query.setParameter("folioRecepcion", domEscrDes.getFolioRecepcion());
		query.setParameter("desDomicilio", domEscrDes.getDesDomicilio());
		query.setParameter("domNumExterior", domEscrDes.getDomNumExterior());
		query.setParameter("domNumInterior", domEscrDes.getDomNumInterior());
		query.setParameter("refCodPostal", domEscrDes.getRefCodPostal());
		query.setParameter("desCiudad", domEscrDes.getDesCiudad());
		query.setParameter("desEstado", domEscrDes.getDesEstado());
		query.setParameter("cveIdTipoDomicilio", domEscrDes.getCveIdTipoDomicilio());
		query.setParameter("fecactualiza", domEscrDes.getFecactualiza());

		if(querAct == 1){
			query.setParameter("fechAlta", domEscrDes.getFechAlta());
		}

		bExito = query.executeUpdate() > 0 ? true : false;

		if (bExito) {
			return bExito;
		} else {
			throw new RiesgosTrabajoException(
					"No fue posible guardar el domicilio");
		}
	}

	@Override
	public TramiteEscritoDesacuerdo getTramoDuplicado(String folioImpugnado, long anVigencia) {
		Query query = this.getSession().createSQLQuery(
				EscritoUtil.QUERY_DESACUERDO_DUPLICIDAD.toString());
		query.setParameter("adnVigencia", anVigencia);
		query.setParameter("folImpugnado", folioImpugnado);

		Object[] desacuerdo = (Object[]) query.uniqueResult();
		log.debug("Resultado de la busqueda de tramte duplicado");

		if (desacuerdo != null) {
			return EscritoUtil.escritoDupObjectToModel(desacuerdo);
		} else {
			return null;
		}
	}

	@Override
	public List<DiasFestivos> findDiasFestivos() throws RiesgosTrabajoException{
		log.debug("Consultando los dias festivos");
		List<DicDiasFestivo> result = null;
		List<DiasFestivos> diasFestivos = new ArrayList<DiasFestivos>();

		Locale locMEX = new Locale("es", "MX");
		Calendar fechaActual = Calendar.getInstance(locMEX);

		fechaActual.set(fechaActual.get(Calendar.YEAR), Calendar.JANUARY, 01, 00, 00, 00);
		Date fechaInicio = fechaActual.getTime();
		fechaActual.set(fechaActual.get(Calendar.YEAR), Calendar.DECEMBER, 31, 23, 59, 59);
		Date fechaFin = fechaActual.getTime();

		//Se realiza la consulta
		Criteria criteria = this.getSession().createCriteria(DicDiasFestivo.class);
		criteria.add(Restrictions.between("fecDiaFestivo",fechaInicio, fechaFin));
		criteria.addOrder(Order.asc("fecDiaFestivo"));
		result = criteria.list();

		//Se convierten los datos de la consulta a objetos Dias Festivos
		if (result != null && !result.isEmpty()) {
			diasFestivos = EscritoUtil.getDataDiasFestivos(result);
		} else {
			throw new RiesgosTrabajoException("No existen dias festivos para este periodo");
		}

		log.debug("Se finaliza la consulta");
		return diasFestivos;
	}
}
