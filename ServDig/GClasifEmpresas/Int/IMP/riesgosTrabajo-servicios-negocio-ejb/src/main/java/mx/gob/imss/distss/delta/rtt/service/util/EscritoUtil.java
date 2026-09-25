package mx.gob.imss.distss.delta.rtt.service.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.gob.imss.ctirss.delta.model.derechohabiente.DiasFestivos;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.escritoDesacuerdo.*;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.PatronRiesgosTrabajo;
import mx.gob.imss.ctirss.delta.model.escritoDesacuerdo.MotivosDesacuerdo;
import mx.gob.imss.ctirss.delta.persistence.*;
import mx.gob.imss.distss.digital.jaxb.util.JaxbUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EscritoUtil {

	private static final Logger LOG;

	static {
		LOG = LoggerFactory.getLogger(JaxbUtil.class);
	}
	
	public static DitEscritoDesacuerdo escritoModelToEntity(TramiteEscritoDesacuerdo tramite) {
		DitEscritoDesacuerdo ditEscritoDesacuerdo = null;
		
		if(tramite != null) {
			ditEscritoDesacuerdo = new DitEscritoDesacuerdo();
			ditEscritoDesacuerdo.setDicCausaDesacuerdo(EscritoUtil.causaModelToEntity(tramite.getCausaDesacuerdo()));
			ditEscritoDesacuerdo.setDitTramite(new DitTramite(tramite.getTramiteId()));
			ditEscritoDesacuerdo.setDitPatronSujetoObligado(new DitPatronSujetoObligado());
			ditEscritoDesacuerdo.getDitPatronSujetoObligado().setCveIdPatronSujetoObligado(tramite.getPatron().getIdPatronSujetoObligado());
			ditEscritoDesacuerdo.setDicMotivoDesacuerdo(EscritoUtil.motivoModelToEntity(tramite.getMotivosDesacuerdo()).getCveIdMotivoDescacuerdo());
			ditEscritoDesacuerdo.setRefFolioInpugnado(tramite.getFolioImpugnado());
			ditEscritoDesacuerdo.setRefFolioRecepcion(tramite.getFolioRecepcion());
			ditEscritoDesacuerdo.setIndRegistroProcesado(tramite.getRegistroProcesado() == null ? 0 : tramite.getRegistroProcesado());
			ditEscritoDesacuerdo.setIndAnVigencia(tramite.getAnVigencia());
			ditEscritoDesacuerdo.setRefClaseAnterior(tramite.getClaseAnterior());
			ditEscritoDesacuerdo.setRefFraccAnterior(tramite.getFracAnterior());
			ditEscritoDesacuerdo.setRefPrimaAnterior(tramite.getPrimAnterior());
			ditEscritoDesacuerdo.setRefTrabPromedio(tramite.getTrabajadorProm());
			String mask = "dd/MM/yyyy";
			Date date = null;
			try {
				date = new SimpleDateFormat(mask).parse(tramite.getFechNotRes());
			} catch (ParseException e) {
				LOG.error("Parsing date", e);
			}
			ditEscritoDesacuerdo.setFecNotificaResol(date);
			ditEscritoDesacuerdo.setRefMotivos(tramite.getMotivoDesacuerdo());
			ditEscritoDesacuerdo.setRefMotivos1(tramite.getMotivoDesacuerdo1());
			ditEscritoDesacuerdo.setRefMotivos2(tramite.getMotivoDesacuerdo2());
			ditEscritoDesacuerdo.setRefMotivos3(tramite.getMotivoDesacuerdo3());
			ditEscritoDesacuerdo.setRefMotivos4(tramite.getMotivoDesacuerdo4());
			ditEscritoDesacuerdo.setRefMotivos5(tramite.getMotivoDesacuerdo5());
			ditEscritoDesacuerdo.setRefMotivos6(tramite.getMotivoDesacuerdo6());
			ditEscritoDesacuerdo.setRefMotivos7(tramite.getMotivoDesacuerdo7());
			ditEscritoDesacuerdo.setRefMotivos8(tramite.getMotivoDesacuerdo8());
			ditEscritoDesacuerdo.setRefMotivos9(tramite.getMotivoDesacuerdo9());
		}
		
		return ditEscritoDesacuerdo;
	}

	public static TramiteEscritoDesacuerdo escritoEntityToModel(DitEscritoDesacuerdo ditEscritoDesacuerdo) {
		TramiteEscritoDesacuerdo tramite = null;

		if(ditEscritoDesacuerdo != null) {
			tramite = new TramiteEscritoDesacuerdo();
			tramite.setIdEscrito(ditEscritoDesacuerdo.getCveIdDesacuerdo());
			tramite.setTramiteId(ditEscritoDesacuerdo.getDitTramite().getCveIdTramite());
			DitPatronSujetoObligado ditPatron = ditEscritoDesacuerdo.getDitPatronSujetoObligado();
			PatronRiesgosTrabajo patron = EscritoUtil.patronEntityToModel(ditPatron);
			tramite.setPatron(patron);
			tramite.setFolioRecepcion(ditEscritoDesacuerdo.getRefFolioRecepcion());
			tramite.setFolioImpugnado(ditEscritoDesacuerdo.getRefFolioInpugnado());
			tramite.setMotivosDesacuerdo(EscritoUtil.motivoEntityToModal(ditEscritoDesacuerdo.getDicMotivoDesacuerdo()));
			tramite.setMotivoDesacuerdo(ditEscritoDesacuerdo.getRefMotivos());
			tramite.setRegistroProcesado(ditEscritoDesacuerdo.getIndRegistroProcesado());
		}

		return tramite;
	}

	public static PatronRiesgosTrabajo patronEntityToModel(DitPatronSujetoObligado ditPatronSujetoObligado) {
		PatronRiesgosTrabajo patron = null;
		
		if(ditPatronSujetoObligado != null) {
			patron = new PatronRiesgosTrabajo(ditPatronSujetoObligado.getCveIdPatronSujetoObligado());
		}
		
		return patron;
	}
	
	public static DicCausaDesacuerdo causaModelToEntity(CausaDesacuerdo causaDesacuerdo) {
		DicCausaDesacuerdo dicCausaDesacuerdo = null;

		if(causaDesacuerdo != null) {
			dicCausaDesacuerdo = new DicCausaDesacuerdo(causaDesacuerdo.getIdCausaDes());
			if(causaDesacuerdo.getDescCausaDes() != null) {
				dicCausaDesacuerdo.setDesCausaDesacuerdo(causaDesacuerdo.getDescCausaDes());
			}
		}
		
		return dicCausaDesacuerdo;
		
	}

	public static DicMotivoDesacuerdo motivoModelToEntity(MotivosDesacuerdo motivoDesacuerdo) {
		DicMotivoDesacuerdo dicMotivoDesacuerdo = null;

		if(motivoDesacuerdo != null) {
			dicMotivoDesacuerdo = new DicMotivoDesacuerdo(motivoDesacuerdo.getIdMotivoDes());
			if(motivoDesacuerdo.getDescMotivoDes() != null) {
				dicMotivoDesacuerdo.setDesMotivoDesacuerdo(motivoDesacuerdo.getDescMotivoDes());
			}
		}

		return dicMotivoDesacuerdo;

	}
	
	public static CausaDesacuerdo causaEntityToModal(DicCausaDesacuerdo dicCausa){
		CausaDesacuerdo causa = null;
		
		if(dicCausa != null) {
			causa = new CausaDesacuerdo();
			causa.setIdCausaDes(dicCausa.getCveIdCausaDescacuerdo());
			causa.setDescCausaDes(dicCausa.getDesCausaDesacuerdo());
			causa.setMateriaDesacuerdo(EscritoUtil.materiaEntityToModel(dicCausa.getDicMateriaDesacuerdo()));
		}
		
		return causa;
	}

	public static MotivosDesacuerdo motivoEntityToModal(long dicMotivo) {
		MotivosDesacuerdo motivo = new MotivosDesacuerdo();
			motivo.setIdMotivoDes(dicMotivo);
		return motivo;
	}

	public static MateriaDesacuerdo materiaEntityToModel(DicMateriaDesacuerdo dicMateria) {
		MateriaDesacuerdo materia = null;
		
		if(dicMateria != null) {
			materia = new MateriaDesacuerdo();
			materia.setIdMateria(dicMateria.getCveIdMateriaDesacuerdo());
			materia.setDescMateria(dicMateria.getDesMateriaDesacuerdo());
		}
		
		return materia;
	}
	
	public static DicMateriaDesacuerdo materiaModelToEntity(MateriaDesacuerdo materia) {
		
		DicMateriaDesacuerdo dicMateriaDesacuerdo = null;
		
		if(materia != null) {
			dicMateriaDesacuerdo = new DicMateriaDesacuerdo(materia.getIdMateria());
			if(materia.getDescMateria() != null) {
				dicMateriaDesacuerdo.setDesMateriaDesacuerdo(materia.getDescMateria());
			}
			
		}
		
		return dicMateriaDesacuerdo;
		
	}
	
	public static List<TramiteEscritoDesacuerdo> escritoEntityToModel(
			List<DitEscritoDesacuerdo> listaditEscritoDesacuerdo) {

		List<TramiteEscritoDesacuerdo> listaEscrito = new ArrayList<TramiteEscritoDesacuerdo>();

		for (DitEscritoDesacuerdo ditEscrito : listaditEscritoDesacuerdo) {

			TramiteEscritoDesacuerdo tramite = escritoEntityToModel(ditEscrito);
			listaEscrito.add(tramite);
		}

		return listaEscrito;
	}
	
	/**
	 * Devuelve un objeto {link=TramiteEscritoDesacuerdo} con los siguientes datos
	 * <li>Registro Patronal </li>
	 * <li>Razon Social</li>
	 * <li>Fecha de Alta</li>
	 * <li>Delegaci%oacute;n descripci%oacute;n</li>
	 * <li>Subdelegaci%oacute;n descripci%oacute;n</li>
	 * <li>N%uacute; de folio de recepci%oacute;n</li>
	 * <li>Materia de Determinación de Prima</li>
	 * <li>Clasificación de Empresa</li>
	 * <li>Motivos(s) del Desacuerdo (agravios)</li>
	 * <li>Documentos Adjuntos</li>
	 * @param desacuerdos
	 * @return
	 */
	public static List<TramiteEscritoDesacuerdo> escritoObjectToModel(List<Object[]> desacuerdos) {
		
		List<TramiteEscritoDesacuerdo> tramites = new ArrayList<TramiteEscritoDesacuerdo>();
		
		for(Object[] escrito : desacuerdos){
			
			int i=9;
			TramiteEscritoDesacuerdo tramite = buildTramiteComun(escrito);
			
			tramite.setCausaDesacuerdo(new CausaDesacuerdo());
			tramite.getCausaDesacuerdo().setMateriaDesacuerdo(new MateriaDesacuerdo());
			
			tramite.setIdEscrito( parseLong(escrito[i++]) );
			tramite.getCausaDesacuerdo().setIdCausaDes( parseLong(escrito[i++]) );
			tramite.getCausaDesacuerdo().setDescCausaDes( parseString(escrito[i++]) );
			tramite.getCausaDesacuerdo().getMateriaDesacuerdo().setIdMateria( parseLong(escrito[i++]) );
			tramite.getCausaDesacuerdo().getMateriaDesacuerdo().setDescMateria( parseString(escrito[i++]) );
		
			tramite.getPatron().setIdPersona(escrito[14] != null?  parseLong(escrito[14]) : parseLong(escrito[16]));				
			tramite.getPatron().setRazonSocial(escrito[14] != null? parseString(escrito[15]) : parseString(escrito[17]));		
			tramite.getPatron().setTipoPersona(escrito[14] != null? 2L : 1L);									
			tramite.setMotivoDesacuerdo(parseString(escrito[18]));

			tramite.setFolioImpugnado(parseString(escrito[21]));

			if(escrito[22] != null){
				tramite.setAnVigencia(parseLong(escrito[22]));
				tramite.setMotivosDesacuerdo(new MotivosDesacuerdo());
				tramite.getMotivosDesacuerdo().setIdMotivoDes(parseLong(escrito[23]));

				tramite.setTrabajadorProm(parseString(escrito[24]));
				tramite.setClaseAnterior(parseString(escrito[25]));
				tramite.setFracAnterior(parseString(escrito[26]));
				tramite.setPrimAnterior(parseString(escrito[27]));
				tramite.setFechNotRes(dateToString(escrito[28]));
			}

			tramite.setMotivoDesacuerdo1(parseString(escrito[29]));
			tramite.setMotivoDesacuerdo2(parseString(escrito[30]));
			tramite.setMotivoDesacuerdo3(parseString(escrito[31]));
			tramite.setMotivoDesacuerdo4(parseString(escrito[32]));
			tramite.setMotivoDesacuerdo5(parseString(escrito[33]));
			tramite.setMotivoDesacuerdo6(parseString(escrito[34]));
			tramite.setMotivoDesacuerdo7(parseString(escrito[35]));
			tramite.setMotivoDesacuerdo8(parseString(escrito[36]));
			tramite.setMotivoDesacuerdo9(parseString(escrito[37]));

			//String rfc = parseString(escrito[i++]);
			tramites.add(tramite);
		}
		
		return tramites;
	}
	
	public static TramiteEscritoDesacuerdo escritoObjectToModel(Object[] escrito) {
		
		TramiteEscritoDesacuerdo tramite = null;
		
		if(escrito != null){
			tramite = new TramiteEscritoDesacuerdo();
			
			tramite.setFolioRecepcion( parseString(escrito[0]) );
			tramite.setTramiteId( parseLong(escrito[1]) );
			tramite.setFechaTramite(parseDate(escrito[2]));
			tramite.setIdEscrito( parseLong(escrito[3]) );
			
			tramite.setCausaDesacuerdo(new CausaDesacuerdo());
			tramite.getCausaDesacuerdo().setMateriaDesacuerdo(new MateriaDesacuerdo());
			tramite.getCausaDesacuerdo().setIdCausaDes( parseLong(escrito[4]) );
			tramite.getCausaDesacuerdo().setDescCausaDes( parseString(escrito[5]) );
			tramite.getCausaDesacuerdo().getMateriaDesacuerdo().setIdMateria( parseLong(escrito[6]) );
			tramite.getCausaDesacuerdo().getMateriaDesacuerdo().setDescMateria( parseString(escrito[7]) );
								
			tramite.setMotivoDesacuerdo(parseString(escrito[8]));

		}
		
		return tramite;
	}

	public static List<TramiteEscritoDesacuerdo> escritoObjectDetalleToModel(List<Object[]> desacuerdosPorReg) {
		
		List<TramiteEscritoDesacuerdo> desacuerdos = new ArrayList<TramiteEscritoDesacuerdo>();
		
		for(Object[] escrito : desacuerdosPorReg){				
							
			TramiteEscritoDesacuerdo tramite = buildTramiteComun(escrito);
			
			desacuerdos.add(tramite);			
		}
		
		return desacuerdos;
	}
	
	private static TramiteEscritoDesacuerdo buildTramiteComun(Object[] escrito){
		
		int i =0;
		TramiteEscritoDesacuerdo tramite = new TramiteEscritoDesacuerdo();
		
		PatronRiesgosTrabajo patron=new PatronRiesgosTrabajo();									
		patron.setSubdelegacion(new Subdelegacion());
		patron.getSubdelegacion().setDelegacion(new Delegacion());

		tramite.setFolioRecepcion( parseString(escrito[i++]) );
		patron.setIdPatronSujetoObligado( parseLong(escrito[i++]) );
		patron.setNrp( parseString(escrito[i++]) );
		tramite.setTramiteId( parseLong(escrito[i++]) );
		tramite.setFechaTramite(parseDate(escrito[i++]));

		patron.getSubdelegacion().setId( parseLong(escrito[i++]) );				
		patron.getSubdelegacion().setDescripcion( parseString(escrito[i++]) );
		long idDelegacion = parseLong(escrito[i++]) ;
		patron.setDelegacion(idDelegacion);
		patron.getSubdelegacion().getDelegacion().setId( idDelegacion);				
		patron.getSubdelegacion().getDelegacion().setDescripcion( parseString(escrito[i++]) );

		tramite.setPatron(patron);
		return tramite;
	}

	private static String parseString (Object obj){
		return (String)obj;
	}
	
	private static Long parseLong(Object obj){
		return ((BigDecimal)obj).longValue() ;
	}

	private static int parseInt(Object obj) {
		Object objNumExt = obj;
		java.math.BigDecimal bigDecimalObj= (java.math.BigDecimal) objNumExt;
		return bigDecimalObj.intValue();
	}
	
	private static Date parseDate(Object obj){
		 return new Date ( ((Timestamp)obj).getTime() );
	}

	private static String dateToString(Object obj){
		Date dat = parseDate(obj);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String strDate = dateFormat.format(dat);
		return strDate;
	}

	public static DomicilioEscritoDesacuerdo domEscritoEntityToModel(DitDomicilioEscDes ditDomicilioEscDes) {
		DomicilioEscritoDesacuerdo tramite = null;

		if(ditDomicilioEscDes != null) {
			tramite = new DomicilioEscritoDesacuerdo();
			tramite.setIdEscrito(ditDomicilioEscDes.getCveIdDesacuerdo());
			tramite.setFolioRecepcion(ditDomicilioEscDes.getRefFolioRecepcion());
			tramite.setDesDomicilio(ditDomicilioEscDes.getDesDomCalle());
			tramite.setDomNumExterior(ditDomicilioEscDes.getDomNumExterior());
			tramite.setDomNumInterior(ditDomicilioEscDes.getDomNumInterior());
			tramite.setRefCodPostal(ditDomicilioEscDes.getRefCodPostal());
			tramite.setDesCiudad(ditDomicilioEscDes.getDesCiudad());
			tramite.setDesEstado(ditDomicilioEscDes.getDesEstado());
			tramite.setCveIdTipoDomicilio(ditDomicilioEscDes.getCveIdTipoDomicilio());
		}
		return tramite;
	}

	public static List<MotivosDesacuerdo> motivoObjectToModal(List<Object[]>  objMotivos){
		List<MotivosDesacuerdo> motivos = new ArrayList<MotivosDesacuerdo>();

		for(Object[] motivo : objMotivos){
			MotivosDesacuerdo tramite = new MotivosDesacuerdo();

			tramite.setIdMotivoDes(parseLong(motivo[0]));
			tramite.setDescMotivoDes(parseString(motivo[1]));

			motivos.add(tramite);
		}
			return motivos;
	}

	public static List<MotivosDesacuerdo> fraccClaseObjectToModal(List<Object[]>  objMotivos){
		List<MotivosDesacuerdo> motivos = new ArrayList<MotivosDesacuerdo>();

		for(Object[] motivo : objMotivos){
			MotivosDesacuerdo fraccion = new MotivosDesacuerdo();
			String value;
			value = parseString(motivo[0])+parseString(motivo[1])+parseString(motivo[2]);

			fraccion.setIdMotivoDes(Long.parseLong(value));
			fraccion.setDescMotivoDes(value);
			motivos.add(fraccion);
		}
		return motivos;
	}

	public static DomicilioEscritoDesacuerdo domEscritoObjectToModel(Object[] domEscrito) {

		DomicilioEscritoDesacuerdo tramite = null;

		if(domEscrito != null){
			tramite = new DomicilioEscritoDesacuerdo();

			tramite.setIdDomEscrito(parseLong(domEscrito[0]));
			tramite.setIdEscrito(parseLong(domEscrito[1]));
			tramite.setFolioRecepcion(parseString(domEscrito[2]));
			tramite.setDesDomicilio(parseString(domEscrito[3]));
			tramite.setDomNumExterior(parseInt(domEscrito[4]));
			tramite.setDomNumInterior(parseInt(domEscrito[5]));
			tramite.setRefCodPostal(parseString(domEscrito[6]));
			tramite.setDesCiudad(parseString(domEscrito[7]));
			tramite.setDesEstado(parseString(domEscrito[8]));
			tramite.setCveIdTipoDomicilio(parseLong(domEscrito[9]));
		}

		return tramite;
	}

	/**
	 * Metodo para convetir una lista de RttRegistro a una lista de
	 * RiesgoTrabajoDTO
	 *
	 * @param diasFestivos
	 * @return
	 */
	public static List<DiasFestivos> getDataDiasFestivos(List<DicDiasFestivo> diasFestivos) {
		List<DiasFestivos> festivos = new ArrayList<DiasFestivos>();

		for (DicDiasFestivo festivo : diasFestivos) {
			DiasFestivos dia = new DiasFestivos();

			//Se seta el dato individual de la consulta
			dia.setFecha(festivo.getFecDiaFestivo());
			dia.setDescripcion(String.valueOf(festivo.getNumDiaSemana()));

			//Se agrega el día a la lista de Dias Festivos
			festivos.add(dia);
		}
		return festivos;
	}


	/**
	 * Devuelve un objeto {link=TramiteEscritoDesacuerdo} con los siguientes datos
	 * <li>Registro Patronal </li>
	 * <li>Razon Social</li>
	 * <li>Fecha de Alta</li>
	 * <li>Delegaci%oacute;n descripci%oacute;n</li>
	 * <li>Subdelegaci%oacute;n descripci%oacute;n</li>
	 * <li>N%uacute; de folio de recepci%oacute;n</li>
	 * <li>Materia de Determinación de Prima</li>
	 * <li>Clasificación de Empresa</li>
	 * <li>Motivos(s) del Desacuerdo (agravios)</li>
	 * <li>Documentos Adjuntos</li>
	 * @param desacuerdo
	 * @return
	 */
	public static TramiteEscritoDesacuerdo escritoDupObjectToModel(Object[] desacuerdo) {

			TramiteEscritoDesacuerdo tramite = new TramiteEscritoDesacuerdo();
			tramite.setIdEscrito(parseLong(desacuerdo[0]));
			tramite.setFolioRecepcion(parseString(desacuerdo[1]));

		return tramite;
	}

	public static final String [] COLUMNAS_REPORTE = {"Folio(s) Escrito Desacuerdo", "Registro Patronal","Nombre o Raz\u00F3n Social","Delegaci\u00F3n", "SubDelegaci\u00F3n", "Materia", "Fecha de Presentaci\u00F3n", "Materia de Prima", "Motivo"};

	public static final StringBuffer QUERY_DESACUERDO_POR_FOLIO = new StringBuffer();

	public static final StringBuffer QUERY_DESACUERDO_SIMPLE = new StringBuffer();

	public static final StringBuffer QUERY_DESACUERDO_DETALLE = new StringBuffer();

	public static final StringBuffer QUERY_DOM_DESACUERDO = new StringBuffer();

	public static final StringBuffer UPDATE_DOM_DESACUERDO = new StringBuffer();

	public static final StringBuffer FIND_DOM_DESACUERDO = new StringBuffer();

	public static final StringBuffer QUERY_DESACUERDO_DUPLICIDAD = new StringBuffer();

	public static final StringBuffer FIND_MOTIVOS_DESACUERDO = new StringBuffer();

	public static final StringBuffer FIND_FRACCION_CLASE = new StringBuffer();

	static {
		QUERY_DESACUERDO_POR_FOLIO
				.append(" select desa.ref_folio_recepcion d0, suje.cve_id_patron_sujeto_obligado d1, llave.ref_busca d2, desa.cve_id_tramite d3 , desa.fec_registro_alta d4, ")
				.append(" subdel.cve_id_subdelegacion d5, subdel.des_subdelegacion d6, dele.cve_id_delegacion d7, dele.des_deleg d8, ")
				.append(" desa.cve_id_escrito_desacuerdo d9, causa.CVE_ID_CAUSA_DESACUERDO d10,  causa.des_causa_desacuerdo d11, mat.cve_id_materia_desacuerdo d12, mat.des_materia_desacuerdo d13,")
				.append(" moral.cve_id_persona_moral d14, moral.denominacion_razon_social d15,fisica.CVE_ID_PERSONA_FISICA d16, persona.nom_nombre || ' ' || persona.NOM_PRIMER_APELLIDO || ' ' || persona.NOM_SEGUNDO_APELLIDO d17,")
				.append(" desa.ref_motivo_desacuerdo d18, moral.rfc d19, fisica.RFC d20,")
				.append(" desa.ref_folio_inpugnado d21, desa.ind_an_vigencia d22, desa.cve_id_motivo_desacuerdo d23, desa.ref_trabajador_promedio d24, ")
				.append(" desa.ref_clase_anterior d25, desa.ref_fraccion_anterior d26, desa.ref_prima_anterior d27, desa.fec_notificacion_resol d28, ")
				.append(" desa.ref_motivo_desacuerdo1 d29, desa.ref_motivo_desacuerdo2 d30, desa.ref_motivo_desacuerdo3 d31, desa.ref_motivo_desacuerdo4 d32, desa.ref_motivo_desacuerdo5 d33, ")
				.append(" desa.ref_motivo_desacuerdo6 d34, desa.ref_motivo_desacuerdo7 d35, desa.ref_motivo_desacuerdo8 d36, desa.ref_motivo_desacuerdo9 d37 ")
				.append(" from DIT_ESCRITO_DESACUERDO desa inner join DIT_PATRON_SUJETO_OBLIGADO suje on desa.cve_id_patron_sujeto_obligado = suje.cve_id_patron_sujeto_obligado")
				.append(" inner join DIT_DELSUB_PAT_SUJ_OBLIG sujedel on suje.cve_id_patron_sujeto_obligado = sujedel.cve_id_patron_sujeto_obligado")
				.append(" inner join DIC_SUBDELEGACION subdel on sujedel.cve_id_subdelegacion = subdel.cve_id_subdelegacion")
				.append(" inner join DIC_DELEGACION dele on subdel.cve_id_delegacion = dele.cve_id_delegacion ")
				.append(" inner join DIT_LLAVE_PATRON llave on llave.cve_id_patron_sujeto_obligado = desa.cve_id_patron_sujeto_obligado")
				.append(" inner join DIC_CAUSA_DESACUERDO causa on causa.cve_id_causa_desacuerdo = desa.cve_id_causa_desacuerdo")
				.append(" inner join DIC_MATERIA_DESACUERDO mat on causa.cve_id_materia_desacuerdo= mat.cve_id_materia_desacuerdo")
				.append(" left outer join DIT_PERSONA_MORAL moral on suje.cve_id_persona_moral = moral.cve_id_persona_moral ")
				.append(" left outer join DIT_PERSONA_FISICA fisica on suje.CVE_ID_PERSONA_FISICA = fisica.CVE_ID_PERSONA_FISICA")
				.append(" left outer join DIT_PERSONA persona on fisica.cve_id_persona = persona.cve_id_persona");

		QUERY_DESACUERDO_SIMPLE
				.append(" select desa.ref_folio_recepcion d0, desa.cve_id_tramite d1 , desa.fec_registro_alta d2, ")
				.append(" desa.cve_id_escrito_desacuerdo d3, causa.CVE_ID_CAUSA_DESACUERDO d4,  causa.des_causa_desacuerdo d5,")
				.append(" mat.cve_id_materia_desacuerdo d6, mat.des_materia_desacuerdo d7,desa.ref_motivo_desacuerdo d88")
				.append(" from DIT_ESCRITO_DESACUERDO desa")
				.append(" inner join DIC_CAUSA_DESACUERDO causa on causa.cve_id_causa_desacuerdo = desa.cve_id_causa_desacuerdo")
				.append(" inner join DIC_MATERIA_DESACUERDO mat on causa.cve_id_materia_desacuerdo= mat.cve_id_materia_desacuerdo")
				.append(" where desa.ref_folio_recepcion = :folioRecepcion");

		QUERY_DESACUERDO_DUPLICIDAD
				.append("select desa.cve_id_escrito_desacuerdo d0, desa.ref_folio_recepcion d1 ")
				.append("from DIT_ESCRITO_DESACUERDO desa ")
				.append("where desa.ind_an_vigencia = :adnVigencia and desa.ref_folio_inpugnado = :folImpugnado ");

		QUERY_DESACUERDO_DETALLE
				.append(" select desa.ref_folio_recepcion , suje.cve_id_patron_sujeto_obligado, llave.ref_busca, desa.cve_id_tramite , desa.fec_registro_alta, ")
				.append(" subdel.cve_id_subdelegacion, subdel.des_subdelegacion, dele.cve_id_delegacion, dele.des_deleg ")
				.append(" from DIT_ESCRITO_DESACUERDO desa, DIT_PATRON_SUJETO_OBLIGADO  suje, ")
				.append(" DIT_DELSUB_PAT_SUJ_OBLIG sujedel, DIC_SUBDELEGACION subdel, DIC_DELEGACION dele, ")
				.append(" DIT_LLAVE_PATRON llave where desa.cve_id_patron_sujeto_obligado = suje.cve_id_patron_sujeto_obligado ")
				.append(" and suje.cve_id_patron_sujeto_obligado = sujedel.cve_id_patron_sujeto_obligado ")
				.append(" and sujedel.cve_id_subdelegacion = subdel.cve_id_subdelegacion ")
				.append(" and subdel.cve_id_delegacion = dele.cve_id_delegacion ")
				.append(" and llave.cve_id_patron_sujeto_obligado = desa.cve_id_patron_sujeto_obligado");

		QUERY_DOM_DESACUERDO
				.append("INSERT INTO DIT_DOM_ESCRITO_DESACUERDO (CVE_ID_DOM_ESCRITO_DESACUERDO, CVE_ID_ESCRITO_DESACUERDO, REF_FOLIO_RECEPCION, DES_DOM_CALLE, ")
				.append("DOM_NUM_EXTERIOR, DOM_NUM_INTERIOR, REF_CODIGO_POSTAL, DES_CIUDAD, DES_ESTADO, CVE_ID_TIPO_DOMICILIO, FEC_REGISTRO_ALTA, FEC_REGISTRO_ACTUALIZADO)")
				.append("VALUES (:idDomEscrito, :idEscrito, :folioRecepcion, :desDomicilio, :domNumExterior, :domNumInterior, :refCodPostal, :desCiudad, :desEstado, :cveIdTipoDomicilio, ")
				.append(":fechAlta, :fecactualiza)");

		UPDATE_DOM_DESACUERDO
				.append("UPDATE DIT_DOM_ESCRITO_DESACUERDO SET CVE_ID_ESCRITO_DESACUERDO = :idEscrito, REF_FOLIO_RECEPCION = :folioRecepcion, ")
				.append("DES_DOM_CALLE = :desDomicilio, DOM_NUM_EXTERIOR = :domNumExterior, DOM_NUM_INTERIOR = :domNumInterior, REF_CODIGO_POSTAL = :refCodPostal, DES_CIUDAD = :desCiudad, ")
				.append("DES_ESTADO = :desEstado, CVE_ID_TIPO_DOMICILIO = :cveIdTipoDomicilio, FEC_REGISTRO_ACTUALIZADO = :fecactualiza ")
				.append("where CVE_ID_DOM_ESCRITO_DESACUERDO = :idDomEscrito");

		FIND_DOM_DESACUERDO
				.append("select dom.CVE_ID_DOM_ESCRITO_DESACUERDO, dom.CVE_ID_ESCRITO_DESACUERDO, dom.REF_FOLIO_RECEPCION, dom.DES_DOM_CALLE, ")
				.append("dom.DOM_NUM_EXTERIOR, dom.DOM_NUM_INTERIOR, dom.REF_CODIGO_POSTAL, dom.DES_CIUDAD, dom.DES_ESTADO, dom.CVE_ID_TIPO_DOMICILIO ")
				.append("from DIT_DOM_ESCRITO_DESACUERDO dom where dom.CVE_ID_DOM_ESCRITO_DESACUERDO = :idtramDomEsc");

		FIND_MOTIVOS_DESACUERDO
				.append("select CVE_ID_MOTIVO_DESACUERDO, DES_MOTIVO_DESACUERDO from DIC_MOTIVO_DESACUERDO where FEC_REGISTRO_BAJA IS NULL ");

		FIND_FRACCION_CLASE
				.append("select d.num_division, g.num_grupo, f.num_fraccion, dc.CVE_ID_CLASE ")
				.append("from dic_division d ")
				.append("join dic_grupo g on d.cve_id_division = g.cve_id_division ")
				.append("join dic_fraccion f on g.cve_id_grupo = f.cve_id_grupo ")
				.append("join dic_fraccion_clase fc on f.cve_id_fraccion = fc.cve_id_fraccion ")
				.append("join dic_clase dc on fc.cve_id_clase = dc.cve_id_clase ")
				.append("where dc.CVE_ID_CLASE = :clase AND fc.FEC_FIN IS NULL ORDER BY d.num_division, g.num_grupo, f.num_fraccion ");

	}

}
