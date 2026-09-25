package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;



import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.math.BigInteger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.transaction.RollbackException;

import mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness.IdeeServiceLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.entity.AcuerdoDerechohabienteEntityLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.entity.EstadoDerechohabienteEntityLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.parser.GrupoFamiliarParserServiceLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.parser.MedicoEnTurnoParserServiceLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.parser.PersonaDomicilioParserServiceLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.util.DeltaUtils;
import mx.gob.imss.ctirss.delta.derechohabientes.util.GrupoFamiliarUtil;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.AsignacionNSSParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.ParentescoParser;
import mx.gob.imss.ctirss.delta.derechohabientes.ws.bussiness.DerechohabienteWSClientRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesWebSserviceException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.AsignacionNSSNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaConNSSException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.ActualizaCorreoIn;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.asegurado.TipoMovtoAsegurado;
import mx.gob.imss.ctirss.delta.model.derechohabiente.ActualizaCorreo;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliarTE;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Consultorio;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Derechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.EstadoDerechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Parentesco;
import mx.gob.imss.ctirss.delta.model.derechohabiente.SubEstadoDerechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.TipoBajaDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Turno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.vigenciaderechos.BeneficiarioDTO;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.vigenciaderechos.ComprobanteVigenciaDerechosDTO;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilioEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaInteresadaSolEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.EstadoCivil;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaDomicilio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.RazonResultadoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAcuerdoDh;
import mx.gob.imss.ctirss.delta.persistence.DgDomicilioGeografico;
import mx.gob.imss.ctirss.delta.persistence.DicCalidadParentesco;
import mx.gob.imss.ctirss.delta.persistence.DitAsignacionNss;
import mx.gob.imss.ctirss.delta.persistence.DitAsignacionNssCL3;
import mx.gob.imss.ctirss.delta.persistence.DitBajaDerechohabiente;
import mx.gob.imss.ctirss.delta.persistence.DitCircunscripcionForanea;
import mx.gob.imss.ctirss.delta.persistence.DitGrupoFamiliar;
import mx.gob.imss.ctirss.delta.persistence.DitGrupoFamiliarCL3;
import mx.gob.imss.ctirss.delta.persistence.DitGrupoFamiliarPK;
import mx.gob.imss.ctirss.delta.persistence.DitPersonafDom;
import mx.gob.imss.ctirss.delta.persistence.DitTramitePersonaFisica;
import mx.gob.imss.ctirss.delta.persistence.DitUmfConsTurnoMedico;
import mx.gob.imss.vigenciaderechos.VigenciaDerechosWSClientRemote;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;

@Stateless(name = "grupoFamiliarDao", mappedName = "grupoFamiliarDao")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class GrupoFamiliarDao extends AbstractServiceEntity implements GrupoFamiliarDaoLocal {
	
	@EJB 
	private GrupoFamiliarParserServiceLocal grupoFamiliarParserServiceLocal;
	@EJB 
	private TramitePersonaFisicaDaoLocal tramite;
	@EJB 
	private EstadoDerechohabienteEntityLocal estadoDerechohabienteEntityLocal;
	@EJB
	private MedicoEnTurnoParserServiceLocal medicoEnTurnoParserServiceLocal;
	@EJB
	private PersonaDomicilioParserServiceLocal personaDomicilioParserServiceLocal;
	@EJB
	private CatalogosDaoLocal catalogosDao;
	@EJB 
	private MovimientoAseguradoDaoLocal movimientoAseguradoDaoLocal;
	@EJB
	private IdeeServiceLocal ideeServiceLocal;
	@EJB
	private ServiceBusinessRemote serviceBusinessRemote;
	@EJB
	private VigenciaDerechosWSClientRemote vigenciaDerechosWS;
	@EJB
	private AcuerdoDerechohabienteEntityLocal acuerdoDerechohabienteEntityLocal;
	
	
	@EJB(name="derechohabienteWSClientService" ,mappedName="derechohabienteWSClientService") 
	private DerechohabienteWSClientRemote wsClient;//derechohabienteWSClientService
	@EJB(name="sujetoObligadoServiceBusiness" ,mappedName="sujetoObligadoServiceBusiness") 
	private SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusinessRemote;
	private Map<Long, EstadoDerechohabiente> estados = new HashMap<Long, EstadoDerechohabiente>();
	private Map<Long, SubEstadoDerechohabiente> subestados = new HashMap<Long, SubEstadoDerechohabiente>();
	
	private final String NUM_MODALIDAD_35 = "35";
	private final String NUM_MODALIDAD_00 = "00";
	private final String NUM_MODALIDAD_37 = "37";
	//private final String NUM_MODALIDAD_34 = "34";
	
	@Override
	public List<GrupoFamiliar> findDatosBasicosIntegrantesGrupoByIdAsignacionNss(
			Long idAsignacionNSS, Boolean conVigencia, Boolean mostrarAsegurado,
			Boolean incluirDatosUmf,Integer paginarInicio, Integer paginarFin)  throws DerechohabientesBusinessException, IllegalArgumentException {
		List<GrupoFamiliar> encontrados = this.findDatosBasicosintegrantes(idAsignacionNSS, null, mostrarAsegurado, incluirDatosUmf,paginarInicio,paginarFin);
		
		if(conVigencia) {
			encontrados = this.complementarVigencia(encontrados);
		}
		
		return encontrados;
	}

	@Override
	public List<GrupoFamiliar> findDatosBasicosIntegrantesGrupoByNumNss(
			String numNSS, Boolean conVigencia, Boolean mostrarAsegurado, Boolean incluirDatosUmf,Integer paginarInicio, Integer paginarFin) throws DerechohabientesBusinessException, IllegalArgumentException {
		
		List<GrupoFamiliar> encontrados = this.findDatosBasicosintegrantes(null, numNSS, mostrarAsegurado, incluirDatosUmf,paginarInicio,paginarFin);
		
		if(conVigencia) {
			encontrados = this.complementarVigencia(encontrados);
		}
		
		return encontrados;
	}

	private AsignacionNSS getAsignacionNSS(Long idAsignacionNSS, String numNss) throws IllegalArgumentException{
		AsignacionNSS asignacion =null;
		Session session = this.getSession();
		String query ="select nss.CVE_ID_ASIGNACION_NSS idNss, nss.NUM_NSS numNss, persona.CVE_ID_PERSONA idPersona,"+
				"persona.NOM_NOMBRE nombre, persona.NOM_PRIMER_APELLIDO primerApe, persona.NOM_SEGUNDO_APELLIDO segundoApe,"+
				"persona.FEC_NACIMIENTO nacimiento, persona.NUM_ANIO_NAC_REG, persona.NUM_MES_NAC_REG, persona.CURP, "+
				"sexo.CVE_ID_SEXO idSexo, sexo.DES_SEXO deSexo from dit_asignacion_nss nss"+
				" inner join dit_persona persona on nss.cve_id_persona = persona.cve_id_persona"+
				" left outer join dic_sexo sexo on persona.CVE_ID_SEXO = sexo.CVE_ID_SEXO"+
				" where ";

		if(idAsignacionNSS != null) {
			query +="nss.cve_id_asignacion_nss ="+ idAsignacionNSS;
		} else if(!StringUtils.isBlank(numNss)) {
			query +="nss.num_nss = '"+ numNss+"'";
		} else {
			throw new IllegalArgumentException("Es necesario el nss o el id del nss");
		}

		SQLQuery queryNSS = session.createSQLQuery(query);
		@SuppressWarnings("unchecked")
		List<Object[]> resultado = (List<Object[]>)queryNSS.list();

		if(!resultado.isEmpty()) {

			Object[] nss = resultado.get(0);

			Long idAsignacion = ((BigDecimal)nss[0]).longValue();
			String numNSS = (String) nss[1];
			Long idPersonaIntegrante = ((BigDecimal)nss[2]).longValue();
			String nombre = (String) nss[3];
			String primerApellido = (String) nss[4];
			String segundoApellido = (String) nss[5];
			Date fechaNacimiento = (Date) nss[6];
			Integer anioNacimiento = nss[7] != null ? ((BigDecimal)nss[7]).intValue() : null;
			Integer mesNacimiento =	nss[8] != null ? ((BigDecimal)nss[8]).intValue() : null;
			String curp = (String)nss[9];
			Integer idSexo = nss[10] != null ? ((BigDecimal)nss[10]).intValue() : null;
			String deSexo = (String) nss[11];

			asignacion = new AsignacionNSS();
			asignacion.setIdAsignacionNSS(idAsignacion);
			asignacion.setNss(numNSS);
			asignacion.setNssStr(numNSS);
			asignacion.setIdPersona(idPersonaIntegrante);
			asignacion.setNombre(nombre);
			asignacion.setPrimerApellido(primerApellido);
			asignacion.setSegundoApellido(segundoApellido);
			asignacion.setFechaNacimiento(fechaNacimiento);
			asignacion.setAnioRegistroNac(anioNacimiento);
			asignacion.setMesRegistroNac(mesNacimiento);
			asignacion.setCurp(curp);
			asignacion.setSexo(new Sexo(idSexo));
			asignacion.getSexo().setDescripcion(deSexo);

		}

		return asignacion;
	}
	
	private List<GrupoFamiliar> findDatosBasicosintegrantes(Long idAsignacionNSS, String numNss, Boolean mostrarAsegurado, Boolean incluirDatosUmf,
			Integer paginarInicio, Integer paginarFin)
	throws IllegalArgumentException{
		List<GrupoFamiliar> grupo = null;
		AsignacionNSS asignacion = this.getAsignacionNSS(idAsignacionNSS, numNss);
		
		if(asignacion != null) {
			Session session = this.getSession();
			String query = "";
			
			if(paginarInicio != null) {
				if(paginarInicio.intValue() != 0 && (paginarFin != null && paginarFin.intValue() != 0)) {
					query+="select * from ("
							+ "select resultados_.*,"
							+ "rownum rownum_ from (";
				}else if(paginarFin != null && paginarFin.intValue() != 0){
					query+="select * from (";
				}
			}
			
			query+="select nss.CVE_ID_ASIGNACION_NSS id_nss, nss.NUM_NSS num_nss, grupo.NUM_CALIDAD numCalidad, der.CVE_ID_PERSONA id_persona,"+
					"der.NOM_NOMBRE nombre, der.NOM_PRIMER_APELLIDO primerAp, der.NOM_SEGUNDO_APELLIDO segundoApe,"+
					"der.FEC_NACIMIENTO fec_nac, der.NUM_MES_NAC_REG mes, der.NUM_ANIO_NAC_REG anio, der.CURP,"+
					"sexo.CVE_ID_SEXO idSexo, sexo.DES_SEXO deSexo, parentesco.CVE_ID_CALIDAD_PARENTESCO idPar,"+
					"parentesco.DES_PARENTESCO desPar";
			
			if(incluirDatosUmf != null && incluirDatosUmf) {
				query += ",umfconsturmed.CVE_ID_UMF_CONS_TURNO_MED,umf.CVE_ID_UMF, umf.NOM_CORTO,umf.NOM_UNIDAD,subdel.CVE_ID_SUBDELEGACION, subdel.DES_SUBDELEGACION"+
						",del.CVE_ID_DELEGACION, del.DES_DELEG,turno.CVE_ID_TURNO, turno.DES_DESCRIPCION"+
						", umfcons.CVE_NUM_CONSULTORIO, umfcons.DES_CONSULTORIO";
			}
			
			query+=	" from dit_asignacion_nss nss "+
					"inner join dit_grupo_familiar grupo on nss.CVE_ID_ASIGNACION_NSS = grupo.CVE_ID_ASIGNACION_NSS "+
					"inner join dit_persona der on grupo.CVE_ID_PERSONA_INTEGRANTE = der.CVE_ID_PERSONA "+
					"inner join DIC_CALIDAD_PARENTESCO parentesco on grupo.CVE_ID_CALIDAD_PARENTESCO = parentesco.CVE_ID_CALIDAD_PARENTESCO "+
					"inner join DIC_SEXO sexo on der.CVE_ID_SEXO = sexo.CVE_ID_SEXO ";
			
			if(incluirDatosUmf != null && incluirDatosUmf) {
				query += "inner join DIT_UMF_CONS_TURNO_MEDICO umfconsturmed on grupo.CVE_ID_UMF_CONS_TURNO_MED = umfconsturmed.CVE_ID_UMF_CONS_TURNO_MED "+
						"inner join DIT_UMF_CONSULTORIO_TURNO umfconstur on umfconsturmed.CVE_ID_UMF_CONS_TURNO = umfconstur.CVE_ID_UMF_CONS_TURNO "+
						"inner join DIC_TURNO turno on umfconstur.CVE_ID_TURNO = turno.CVE_ID_TURNO "+
						"inner join DIC_CONSULTORIO_UMF umfcons on umfconstur.CVE_ID_UMF_CONSULTORIO = umfcons.CVE_ID_UMF_CONSULTORIO "+
						"inner join DIC_UMF umf on umfcons.CVE_ID_UMF = umf.CVE_ID_UMF "+
						"inner join DIC_SUBDELEGACION subdel on umf.CVE_ID_SUBDELEGACION = subdel.CVE_ID_SUBDELEGACION "+
						"inner join DIC_DELEGACION del on subdel.CVE_ID_DELEGACION = del.CVE_ID_DELEGACION ";
			}
			
			query += "where grupo.FEC_REGISTRO_BAJA is null and ";
			
			if(mostrarAsegurado != null && !mostrarAsegurado ) {
				query+=" grupo.NUM_CALIDAD != 1 and ";
			}
			if(idAsignacionNSS!= null) {
				query+=	"nss.CVE_ID_ASIGNACION_NSS = " + idAsignacionNSS;
			} else if(StringUtils.isNotBlank(numNss)) {
				query+=	"nss.num_nss = '" + numNss+"'";
			} else {
				throw new IllegalArgumentException("Es necesario el nss o el id del nss");
			}

			
			
			if(paginarInicio != null) {
				if(paginarInicio.intValue() != 0 && (paginarFin != null && paginarFin.intValue() != 0)) {
					query+=" order by grupo.NUM_CALIDAD asc ) resultados_ "
							+ "where rownum <=" + (paginarInicio + paginarFin ) +""
									+ " ) where rownum_ > " + paginarInicio; 
				} else if(paginarFin != null && paginarFin.intValue() != 0){
					query+=" order by grupo.NUM_CALIDAD asc) where rownum <= " + paginarFin +  " ";
				}
			} else {
				query+=  " order by grupo.NUM_CALIDAD asc";
			}
			
			SQLQuery queryGrupos = session.createSQLQuery(query);
			@SuppressWarnings("unchecked")
			List<Object[]> resultado = (List<Object[]>)queryGrupos.list();

			if(!resultado.isEmpty()) {
				grupo = new ArrayList<GrupoFamiliar>();
				for(Object[] integrante: resultado) {
					MedicoEnTurno medicoEnTurno = null;
					
					BigDecimal numCalidad = (BigDecimal) integrante[2];
					Long idPersonaIntegrante = ((BigDecimal)integrante[3]).longValue();
					String nombre = (String) integrante[4];
					String primerApellido = (String) integrante[5];
					String segundoApellido = (String) integrante[6];
					Date fechaNacimiento = (Date) integrante[7];
					Integer anioNacimiento = integrante[8] != null ? ((BigDecimal)integrante[8]).intValue() : null;
					Integer mesNacimiento =	integrante[9] != null ? ((BigDecimal)integrante[9]).intValue() : null;
					String curp = (String) integrante[10];
					Integer idSexo = integrante[11] != null ? ((BigDecimal)integrante[11]).intValue() : null;
					String deSexo = (String) integrante[12];
					Long idParentesco = integrante[13] != null ? ((BigDecimal)integrante[13]).longValue() : null;
					String desParentesco = (String) integrante[14];
					
					if(incluirDatosUmf != null && incluirDatosUmf) {
						medicoEnTurno = new MedicoEnTurno();
						medicoEnTurno.setUnidadMedicaFamiliar(new UnidadMedicaFamiliar());
						
						Long idUmfConsturno = integrante[15] != null ? ((BigDecimal)integrante[15]).longValue() : null;
						Long idUmf = integrante[16] != null ? ((BigDecimal)integrante[16]).longValue() : null;
						String nomCorto = (String) integrante[17];
						String nomUnidad = (String) integrante[18];
						Long idSubDele= integrante[19] != null ? ((BigDecimal)integrante[19]).longValue() : null;
						String subdel = (String)integrante[20];
						Long idDele= integrante[21] != null ? ((BigDecimal)integrante[21]).longValue() : null;
						String del = (String)integrante[22];
						Long idTurno= integrante[23] != null ? ((BigDecimal)integrante[23]).longValue() : null;
						String turno = (String)integrante[24];
						Long idConsultorio= integrante[25] != null ? ((BigDecimal)integrante[25]).longValue() : null;
						String consultorio = (String)integrante[26];
						
						medicoEnTurno.setIdMedicoContultorioTurno(idUmfConsturno);
						medicoEnTurno.getUnidadMedicaFamiliar().setIdUMF(idUmf);
						medicoEnTurno.getUnidadMedicaFamiliar().setNombreCorto(nomCorto);
						medicoEnTurno.getUnidadMedicaFamiliar().setDescripcion(nomUnidad);
						medicoEnTurno.getUnidadMedicaFamiliar().setSubdelegacion(new Subdelegacion());
						medicoEnTurno.getUnidadMedicaFamiliar().getSubdelegacion().setId(idSubDele);
						medicoEnTurno.getUnidadMedicaFamiliar().getSubdelegacion().setDescripcion(subdel);
						medicoEnTurno.getUnidadMedicaFamiliar().getSubdelegacion().setDelegacion(new Delegacion());
						medicoEnTurno.getUnidadMedicaFamiliar().getSubdelegacion().getDelegacion().setId(idDele);
						medicoEnTurno.getUnidadMedicaFamiliar().getSubdelegacion().getDelegacion().setDescripcion(del);
						medicoEnTurno.setTurno(new Turno(idTurno, turno));
						medicoEnTurno.setConsultorio(new Consultorio(idConsultorio, consultorio));
					}

					GrupoFamiliar integranteN = new GrupoFamiliar();
					integranteN.setAsignacionNSS(asignacion);
					integranteN.setDerechohabiente(new Derechohabiente());
					integranteN.setCalidad(numCalidad);
					integranteN.getDerechohabiente().setIdPersona(idPersonaIntegrante);
					integranteN.getDerechohabiente().setCurp(curp);
					integranteN.getDerechohabiente().setNombre(nombre);
					integranteN.getDerechohabiente().setPrimerApellido(primerApellido);
					integranteN.getDerechohabiente().setSegundoApellido(segundoApellido);
					integranteN.getDerechohabiente().setFechaNacimiento(fechaNacimiento);
					integranteN.getDerechohabiente().setMesRegistroNac(mesNacimiento);
					integranteN.getDerechohabiente().setAnioRegistroNac(anioNacimiento);
					integranteN.getDerechohabiente().setSexo(new Sexo(idSexo));
					integranteN.getDerechohabiente().getSexo().setDescripcion(deSexo);
					integranteN.setParentesco(new Parentesco());
					integranteN.getParentesco().setIdParentesco(idParentesco);
					integranteN.getParentesco().setDescripcion(desParentesco);
					integranteN.setMedicoEnTurno(medicoEnTurno);

					grupo.add(integranteN);
				}
			} else {
				log.debug("No se encontraron ideess");
			}
		}
		return grupo;
	}

	/**
	 * Metodo para obtener a los integrantes dados de baja por el o los tipos de baja que se pasan como parametro
	 * entre la fecha de hoy y hace X anios
	 * @param idAsignacionNss - Long idAsignacionnss
	 * @param idParentesco - List<Long> , Lista de ids de parentescos a buscar
	 * @param numeroDeAniosAtras - Integer , El numero de a�os atras desde que se buscara
	 * @param tiposDeBaja - Long[] , Los tipos de baja que se buscaran entre la fecha de hoy y los anios 
	 * @return List<GrupoFamiliar> - Los integrantes del grupo que tengan un tipo de baja y el parnetesco pasados como parametros entre hoy y X anios atras
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<GrupoFamiliar> getIntegrantesEnBajaDesdeHaceXAnios(Long idAsignacionNss,
			List<Long> idParentesco, Integer numeroDeAniosAtras, Long[] tiposDeBaja) {
		//integrantes encontrados
		List<GrupoFamiliar> integrantes = null;
		
		try {
			//Fecha de hoy
			Date fechaFin = new Date();
			Calendar calen= Calendar.getInstance();
			calen.setTime(fechaFin);
			
			//fecha desde la que se buscara
			Calendar fechaAnterior = Calendar.getInstance();
			fechaAnterior.set(Calendar.DATE, calen.get(Calendar.DATE));
			fechaAnterior.set(Calendar.MONTH, calen.get(Calendar.MONTH));
			fechaAnterior.set(Calendar.YEAR, numeroDeAniosAtras != null ? calen.get(Calendar.YEAR) - numeroDeAniosAtras : calen.get(Calendar.YEAR));
			//una vez seteada la fecha de inicio la transformamos en un date
			Date fechaInicio = fechaAnterior.getTime();
			//anadimos un dia
			calen.add(Calendar.DAY_OF_YEAR, 1);
			fechaFin = calen.getTime();
			log.debug("Se buscara bajas de conyuges, concubinas o en union civil entre " + fechaInicio + " y " + fechaFin);
			//creamos el query a partir de ditgrupofamilia
			Criteria queryGrupoFamiliar = this.getSession().createCriteria(DitGrupoFamiliar.class);
			queryGrupoFamiliar.createAlias("ditAsignacionNss","nss");
			queryGrupoFamiliar.add(Restrictions.eq("nss.cveIdAsignacionNss", idAsignacionNss));
			queryGrupoFamiliar.createAlias("ditPersona", "der");
			//verificamos si los ids son nulos o vacios
			if(idParentesco != null && !idParentesco.isEmpty()) {
				//Creamos la relacion con parentesco
				queryGrupoFamiliar.createAlias("dicCalidadParentesco", "parentesco");
				//Dependiento del tama�o de la lista agregamos la restricion del parentesco
				if(idParentesco.size() > 1) {
					queryGrupoFamiliar.add(Restrictions.in("parentesco.cveIdCalidadParentesco", idParentesco));
				} else {
					queryGrupoFamiliar.add(Restrictions.eq("parentesco.cveIdCalidadParentesco", idParentesco.get(0)));
				}
			}
			//relacionaremos contra la tabla de bajas
			DetachedCriteria queryBaja = DetachedCriteria.forClass(DitBajaDerechohabiente.class);
			queryBaja.add(Restrictions.eq("cveIdAsignacionNSS", idAsignacionNss));
			if(tiposDeBaja != null && tiposDeBaja.length > 0) {
				queryBaja.createAlias("dicTipoBajaDerechohabiente", "tipoBaja");
				queryBaja.add(Restrictions.in("tipoBaja.cveIdTipoBajaDer", tiposDeBaja));
			}
			queryBaja.add(Restrictions.ge("fecRegistroAlta", fechaInicio));
			queryBaja.add(Restrictions.lt("fecRegistroAlta", fechaFin));
			queryBaja.add(Restrictions.eq("indBajaActiva",1L));
			queryBaja.setProjection(Projections.property("cveIdPersonaIntegrante"));
			//hacemos un subquery
			queryGrupoFamiliar.add(Subqueries.propertyIn("der.cveIdPersona",queryBaja));
			
			//ejecutamos el query
			List<DitGrupoFamiliar> ditGrupo = queryGrupoFamiliar.list();
			//Verificamos si encontramos resultados
			if(ditGrupo != null) {
				integrantes = grupoFamiliarParserServiceLocal.persistToModelList(ditGrupo);
			} 
			
		}catch (NoResultException e) {				
			integrantes = null;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return integrantes;
	}

	@Override
	/**
	 * Metodo para obtenener el numero de hijos recien nacidos en el grupo familiar
	 * @param idAsignacionNss
	 * @return
	 */
	public Long getNumeroRecienNacidos(Long idAsignacionNss){
		
		Long numeroIntegrantes = 0L;
		
		Criteria query = this.getSession().createCriteria(DitGrupoFamiliar.class);
		query.setProjection(Projections.rowCount());
		query.add(Restrictions.eq("indRecienNacido", 1));
		query.createAlias("ditAsignacionNss","nss");
		query.add(Restrictions.eq("nss.cveIdAsignacionNss", idAsignacionNss));
		try{
			numeroIntegrantes = (Long) query.uniqueResult();
		}catch(Exception e) {
			log.error("No se pudo consultar la calidad mas alta",e);
		}
		
		return numeroIntegrantes;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	/**
	 * Metodo para obtener a los integrantes hijos recien nacidos dentro del grpo familiar
	 * @param idAsignacionNss
	 * @return
	 */
	public List<GrupoFamiliar> getIntegrantesRecienNacidos(Long idAsignacionNss) {
		List<GrupoFamiliar> integrantes = null;
		
		try {
			Criteria criteria = this.getSession().createCriteria(DitGrupoFamiliar.class);

			criteria.add(Restrictions.eq("indRecienNacido", 1));
			criteria.createAlias("ditAsignacionNss", "nss");
			criteria.add(Restrictions.eq("nss.cveIdAsignacionNss", idAsignacionNss));
			
			List<DitGrupoFamiliar> ditGrupo = criteria.list();

			if(ditGrupo != null) {
				integrantes = grupoFamiliarParserServiceLocal.persistToModelList(ditGrupo);
				
			} 
		}catch (NoResultException e) {				
			integrantes = null;
		}catch(Exception e) {
			log.error("Ocurrio un error al consultar la lista de recien nacidos");
		}

		return integrantes;
	}

	@Override
	/**
	 * Metodo para verificar si una persona ya esta registrada dentro del grupo familiar
	 * @param idAsignacionNss
	 * @param idPersona
	 * @return
	 */
	public Boolean existeIntegranteRegistrado(Long idAsignacionNss,Long idPersona) {
		
		Criteria queryGrupo = this.getSession().createCriteria(DitGrupoFamiliar.class);
		queryGrupo.createAlias("ditAsignacionNss", "nss");
		queryGrupo.add(Restrictions.eq("nss.cveIdAsignacionNss", idAsignacionNss));
		queryGrupo.createAlias("ditPersona", "der");
		queryGrupo.add(Restrictions.eq("der.cveIdPersona", idPersona));
		
		DitGrupoFamiliar grupo = (DitGrupoFamiliar) queryGrupo.uniqueResult();
		if(grupo != null) {
			return true;
		}
		
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * Metodo para obtener a los integrantes del grupo familiar que no tienen un domicilio asignado
	 * @param idAsignacionNss
	 * @param personasExcluir
	 * @return
	 * @throws Exception
	 */
	public List<GrupoFamiliar> findIntegrantesSinDomicilio(Long idAsignacionNss, List<Long> personasExcluir) throws Exception {
		List<GrupoFamiliar> grupos = null;
		Criteria queryGrupoFamiliar =  this.getSession().createCriteria(DitGrupoFamiliar.class);
		queryGrupoFamiliar.createAlias("ditAsignacionNss","nss");
		queryGrupoFamiliar.add(Restrictions.eq("nss.cveIdAsignacionNss", idAsignacionNss));
		queryGrupoFamiliar.createAlias("ditPersona", "der");
		
		if(personasExcluir != null && !personasExcluir.isEmpty()){
			queryGrupoFamiliar.add(Restrictions.not(Restrictions.in("der.cveIdPersona", personasExcluir)));
		}
		
		queryGrupoFamiliar.add(Restrictions.isNull("ditPersonafDom"));
		
		List<DitGrupoFamiliar> ditGrupos = queryGrupoFamiliar.list();
		
		if(ditGrupos != null && !ditGrupos.isEmpty()) {
			grupos = grupoFamiliarParserServiceLocal.persistToModelList(ditGrupos);
		}
		
		return grupos;
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * Metodo para obtener a los integrantes que fueron registrados mediante el origen que se pase como parametro
	 * @param idAsignacionNss
	 * @param idOrigenSolicitud
	 * @return
	 * @throws Exception
	 */
	public List<GrupoFamiliar> findIntegrantesRegistradosByOrigen(
			Long idAsignacionNss, Long idOrigenSolicitud) throws Exception {
		List<GrupoFamiliar> grupoFamiliar = null;
		
		
	
		
		// ----------------------------
		// TRAMITE PERSONA FISICA
		// ----------------------------
		DetachedCriteria queryPersonas  = DetachedCriteria.forClass(DitTramitePersonaFisica.class);
		queryPersonas.createAlias("ditPersona", "persona");
		queryPersonas.setProjection(Projections.distinct(Projections.property("persona.cveIdPersona")));
		
		
		// -----------------------
		// TRAMITE
		// -----------------------
		List<Long> tiposTramiteRegistro = new ArrayList<Long>();
		tiposTramiteRegistro.add(TipoTramiteEnum.REGISTRO_HIJOS.getCodigo().longValue());
		tiposTramiteRegistro.add(TipoTramiteEnum.REGISTRO_CONYUGUE.getCodigo().longValue());
		
		
		DetachedCriteria queryTramite = queryPersonas.createCriteria("ditTramite");
		queryTramite.createAlias("dicTipoTramite", "tipoTramite");
		queryTramite.add(Restrictions.in("tipoTramite.cveIdTipoTramite", tiposTramiteRegistro));
		
		
		// --------------------
		// SOLICITUD
		// --------------------
		DetachedCriteria querySolicitud = queryTramite.createCriteria("ditSolicitud");
		querySolicitud.createAlias("dicOrigenSolicitud", "origen");
		querySolicitud.add(Restrictions.eq("origen.cveIdOrigenSolicitud", idOrigenSolicitud));
		querySolicitud.createAlias("dicTipoSolicitud", "tipoSol");
		querySolicitud.add(Restrictions.eq("tipoSol.cveIdTipoSolicitud", TipoSolicitudEnum.REGISTRO_DE_DERECHOHABIENTES.getValor().longValue()));
		
		
		// --------------------------
		// PERSONA INTERESADA
		// --------------------------
		DetachedCriteria personaInteresada = querySolicitud.createCriteria("ditPersonaInteresadaSols");
		DetachedCriteria personaSol = personaInteresada.createCriteria("ditPersona");
		
		// -----------------------------
		// ASIGNACION NSS
		// -----------------------------
		DetachedCriteria asignacionNssSol = personaSol.createCriteria("ditAsignacionNsses");
		asignacionNssSol.add(Restrictions.eq("cveIdAsignacionNss", idAsignacionNss));
		
		
		// ------------------------------
		// GRUPO FAMILIAR
		// ------------------------------
		Criteria queryGrupoFamiliar = this.getSession().createCriteria(DitGrupoFamiliar.class);
		queryGrupoFamiliar.createAlias("ditAsignacionNss","nss");
		queryGrupoFamiliar.add(Restrictions.eq("nss.cveIdAsignacionNss", idAsignacionNss));
		queryGrupoFamiliar.createAlias("ditPersona", "der");
		queryGrupoFamiliar.add(Subqueries.propertyIn("der.cveIdPersona", queryPersonas));
		
		List<DitGrupoFamiliar> ditGrupoFamiliar  = queryGrupoFamiliar.list();
		
		
		if(ditGrupoFamiliar != null && !ditGrupoFamiliar.isEmpty()) {
			grupoFamiliar = grupoFamiliarParserServiceLocal.persistToModelList(ditGrupoFamiliar);
			this.complementarVigencia(grupoFamiliar);
		}
		
		return grupoFamiliar;
		
	}

	@Override
	/**
	 * Metodo que obtiene los datos de adscripcion den un integrante dentro del grupo familiar
	 * @param idAsignacionNss
	 * @param idPersona
	 * @return
	 * @throws Exception
	 */
	public MedicoEnTurno getMedicoEnTurnoPorIntegrante(Long idAsignacionNss,Long idPersona) throws Exception{

		MedicoEnTurno encontrado = null;
		
		Criteria query = this.getSession().createCriteria(DitGrupoFamiliar.class);
		query.createAlias("ditAsignacionNss","nss");
		query.add(Restrictions.eq("nss.cveIdAsignacionNss", idAsignacionNss));
		query.createAlias("ditPersona", "per");
		query.add(Restrictions.eq("per.cveIdPersona", idPersona));
		query.setProjection(Projections.property("ditUmfConsTurnoMedico"));

		DitUmfConsTurnoMedico ditUmfCT = (DitUmfConsTurnoMedico)query.uniqueResult();
		if(ditUmfCT != null) {
			encontrado = medicoEnTurnoParserServiceLocal.persisToModel(ditUmfCT);
		}

		return encontrado;
	}

	/**
	 * Obtiene cuantos integrantes estan registrados con el parentesco pasado como parametro
	 * el parentesco puede ser nulo, en caso de ser asi se consultara el numero de integrantes
	 * registrados en el grupo familiar
	 */
	@Override
	public Long getNumeroDeIntegrantesPorParentesco(Long idAsignacionNss,
			Long idParentesco) {
		
		Long numeroIntegrantes = 0L;
		
		Criteria query = this.getSession().createCriteria(DitGrupoFamiliar.class);
		query.setProjection(Projections.rowCount());
		query.createAlias("ditAsignacionNss","nss");
		
		query.add(Restrictions.eq("nss.cveIdAsignacionNss", idAsignacionNss));
		query.add(Restrictions.isNull("fecRegistroBaja"));
		
		if(idParentesco != null) {
			query.createAlias("dicCalidadParentesco", "parentesco");
			query.add(Restrictions.eq("parentesco.cveIdCalidadParentesco", idParentesco));
		}
		
		try{
			numeroIntegrantes = (Long) query.uniqueResult();
		}catch(Exception e) {
			log.error("No se pudo consultar la calidad mas alta",e);
		}
		
		return numeroIntegrantes;
	}

	@Override
	/**
	 * Obtiene cuantos integrantes estan registrados con el parentesco pasado como parametro
	 * el parentesco puede ser nulo, en caso de ser asi se consultara el numero de integrantes
	 * registrados en el grupo familiar
	 * @param idAsignacionNss
	 * @param idParentesco
	 * @return
	 */
	public Long getNumeroDeIntegrantesPorListParentesco(Long idAsignacionNss,List<Long> idParentesco) {
		
		Long numeroIntegrantes = 0L;
		
		Criteria query = this.getSession().createCriteria(DitGrupoFamiliar.class);
		//agregamos projection para que nos de el numero de registros
		query.setProjection(Projections.rowCount());
		//agregamos la restriccion del nss
		query.createAlias("ditAsignacionNss","nss");
		query.add(Restrictions.eq("nss.cveIdAsignacionNss", idAsignacionNss));
		//verificamos si los ids son nulos o vacios
		if(idParentesco != null && !idParentesco.isEmpty()) {
			//Creamos la relacion con parentesco
			query.createAlias("dicCalidadParentesco", "parentesco");
			//Dependiento del tama�o de la lista agregamos la restricion del parentesco
			if(idParentesco.size() > 1) {
				query.add(Restrictions.in("parentesco.cveIdCalidadParentesco", idParentesco));
			} else {
				query.add(Restrictions.eq("parentesco.cveIdCalidadParentesco", idParentesco.get(0)));
			}
		}
		//obtenemos el numero de registros que concuerdan
		try{
			numeroIntegrantes = (Long) query.uniqueResult();
		}catch(Exception e) {
			log.error("No se pudo consultar la calidad mas alta",e);
		}
		
		return numeroIntegrantes;
	}

	@Override
	/**
	 * Metodo para obtener la calidad mas alta registrada por parentesco
	 * @param idAsignacionNss
	 * @param idParentesco
	 * @return
	 */
	public Long getCalidadMasAltaRegistradaPorParentesco(
			Long idAsignacionNss, Long idParentesco) {
		
		Long calidadAlta = null;
		
		Criteria query = this.getSession().createCriteria(DitGrupoFamiliar.class);
		//agregamos la restricion del maximo
		query.setProjection(Projections.max("numCalidad"));
		//agregamos la restricion del nss
		query.createAlias("ditAsignacionNss","nss");
		query.add(Restrictions.eq("nss.cveIdAsignacionNss", idAsignacionNss));
		//agregamos la restricion del parentesco
		query.createAlias("dicCalidadParentesco", "parentesco");
		query.add(Restrictions.eq("parentesco.cveIdCalidadParentesco", idParentesco));
		//obtenemos la calidad mas alta
		try{
			calidadAlta = (Long) query.uniqueResult();
		}catch(Exception e) {
			log.error("No se pudo consultar la calidad mas alta",e);
		}
		
		return calidadAlta;
	}

	/**
	 * M�todo que regresa un grupo de derechohabientes que pertenecen a un
	 * grupo familiar.
	 * 
	 * @param nss
	 * @return Grupo familiar de un asegurado
	 */
	@Override
	public List<GrupoFamiliar> findGrupoFamiliar(Long idAsignacionNss) throws DerechohabientesBusinessException,Exception {

		List<GrupoFamiliar> encontrados = this.findGrupoFamiliarByNss(idAsignacionNss);

		return encontrados;
	}

	/**
	 * M�todo que regresa un grupo familiar de acuerdo al parentesco, solo la informacion del ws
	 * 
	 * @param nss
	 * @return Grupo familiar de un asegurado
	 */
	@Override
	public List<GrupoFamiliar> findGrupoFamiliarByParentescoWs(
			Long idAsignacionNss, Long parentesco) throws DerechohabientesBusinessException,Exception{

		List<GrupoFamiliar> integrantesAux = new ArrayList<GrupoFamiliar>();
		//log.debug("Se buscara el parentesco: " +  parentesco +  " para el id asignacion: " + idAsignacionNss + " solo en el ws");
		try {
			integrantesAux = wsClient.getGrupoFamiliarPorParentesco(idAsignacionNss, parentesco.intValue());
		} catch(DerechohabientesWebSserviceException e) {
			lanzarExcepcionWsVigencia(e);
		}
		
		return integrantesAux;
	}
	
	/**
	 * M�todo que regresa un grupo familiar de acuerdo al parentesco .
	 * 
	 * @param nss
	 * @return Grupo familiar de un asegurado
	 */
	@Override
	public List<GrupoFamiliar> findGrupoFamiliarByParentesco(
			Long idAsignacionNss, Long parentesco) throws DerechohabientesBusinessException,Exception{

		//log.debug("Se buscara el parentesco : " + parentesco + " para el id asignacion: " + idAsignacionNss + " en el ws y en ventanilla");
		List<GrupoFamiliar> integrantes = new ArrayList<GrupoFamiliar>();
		List<GrupoFamiliar> integrantesAux = new ArrayList<GrupoFamiliar>();

		try {
			integrantesAux = wsClient.getGrupoFamiliarPorParentesco(idAsignacionNss, parentesco.intValue());
		} catch(DerechohabientesWebSserviceException e) {
			lanzarExcepcionWsVigencia(e);
		}

		if(integrantesAux != null && !integrantesAux.isEmpty()){
			integrantes = this.getListaComplementada(integrantesAux);
		}

		return integrantes;
	}

	/**
	 * Metodo para recuperar la cabeza de grupo familiar con un nss
	 * @throws Exception 
	 */
	@Override
	public GrupoFamiliar getCabezaGrupoFamiliar(Long idAsignacionNss,Long parentesco) throws DerechohabientesBusinessException,Exception {
		GrupoFamiliar miGrupoFamiliar = null;

		//log.debug("Se buscara el parentesco : " + parentesco + " para el id asignacion: " + idAsignacionNss + " en el ws y en ventanilla" +
		//		"pero solo se retornara al primero");
		
		List<GrupoFamiliar> integrantes = new ArrayList<GrupoFamiliar>();
		List<GrupoFamiliar> integrantesAux = new ArrayList<GrupoFamiliar>();

		try{
			integrantesAux = wsClient.getGrupoFamiliarPorParentesco(idAsignacionNss, parentesco.intValue());
		} catch(DerechohabientesWebSserviceException e) {
			lanzarExcepcionWsVigencia(e);
		}
		if(integrantesAux != null && !integrantesAux.isEmpty()){
			integrantes = this.getListaComplementada(integrantesAux);
			miGrupoFamiliar = integrantes.get(0);
		}

		return miGrupoFamiliar;
	}

	/**
	 * M�todo que regresa un derechohabientes que pertenecen a un grupo
	 * familiar.
	 * 
	 * @param idDerechohabiente
	 * @return GrupoFamiliar
	 * @throws DerechohabientesBusinessException 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<GrupoFamiliar> getIntegranteGrupoFamiliarEstado(Long idPersona,
			Long idEstadoDerechohabiente) throws DerechohabientesBusinessException,Exception {

		List<DitGrupoFamiliar> ditIntegrantes = null;
		List<GrupoFamiliar> integrantes = null;
		try {
			Criteria query = this.getSession().createCriteria(DitGrupoFamiliar.class);
			query.createAlias("ditPersona", "per");
			query.add(Restrictions.eq("per.cveIdPersona", idPersona));
			
			ditIntegrantes = (List<DitGrupoFamiliar>) query.list();

			if(ditIntegrantes != null){
				integrantes = grupoFamiliarParserServiceLocal.persistToModelList(ditIntegrantes);
				this.complementarVigencia(integrantes);
				
				if(idEstadoDerechohabiente != null) {
					List<Long> estados = new ArrayList<Long>();
					estados.add(idEstadoDerechohabiente);
					
					integrantes = this.obtenerIntegrantesConEstado(integrantes, estados);
				}
			}
		} catch (Exception e) {
			log.error("getIntegranteGrupoFamiliar", e);
			throw e;
		}


		return integrantes;
	}

	@Override
	/**
	 * 
	 * @param idAsignacionNss
	 * @param parentesco
	 * @param estado
	 * @return
	 * @throws DerechohabientesBusinessException
	 * @throws Exception
	 */
	public List<GrupoFamiliar> findGrupoFamiliarParentescoEstado(
			Long idAsignacionNss, Long parentesco, Long estado) throws DerechohabientesBusinessException,Exception {
		List<GrupoFamiliar> integrantes = new ArrayList<GrupoFamiliar>();
		List<GrupoFamiliar> integrantesAux = null;

		try{
			integrantesAux = wsClient.getGrupoFamiliarPorParentescoYEstado(idAsignacionNss, parentesco.intValue(),
					estado.intValue());
		} catch(DerechohabientesWebSserviceException e) {
			lanzarExcepcionWsVigencia(e);
		}

		if(integrantesAux != null && !integrantesAux.isEmpty()){
			integrantes = this.getListaComplementada(integrantesAux);
		}

		return integrantes;
	}
	
	@Override
	/**
	 * 
	 * @param idAsignacionNss
	 * @param parentesco
	 * @param estado
	 * @param idSubestado
	 * @return
	 * @throws DerechohabientesBusinessException
	 * @throws Exception
	 */
	public List<GrupoFamiliar> findGrupoFamiliarEstadoSubestado(
			Long idAsignacionNss, Long parentesco, Long estado,Long idSubestado) throws DerechohabientesBusinessException,Exception {
		List<GrupoFamiliar> integrantes = new ArrayList<GrupoFamiliar>();
		List<GrupoFamiliar> integrantesAux = null;

		integrantesAux = wsClient.getGrupoFamiliarPorParentescoYEstado(idAsignacionNss,
				parentesco.intValue(), estado.intValue());

		if(integrantesAux != null && !integrantesAux.isEmpty()){
			if(idSubestado != null) {
				integrantesAux = this.getIntegrantesPorSubestado(integrantesAux, idSubestado);

				if(integrantesAux.isEmpty()) {
					return integrantes;
				}
			}

			integrantes = this.getListaComplementada(integrantesAux);
			integrantes = GrupoFamiliarUtil.ordenarPorCalidad(integrantes);
		}

		return integrantes;
	}

	/**
	 * Obtiene a los integrantes con el subestado pasado como parametro
	 * @param integrantes
	 * @param idSubEstado
	 * @return
	 */
	private List<GrupoFamiliar> getIntegrantesPorSubestado(List<GrupoFamiliar> integrantes, Long idSubEstado) {

		List<GrupoFamiliar> integrantesS = new ArrayList<GrupoFamiliar>();

		for(GrupoFamiliar integrante: integrantes) {
			if(integrante.getSubEstadoDerechohabiente().getIdSubEstadoDerechohabiente().equals(idSubEstado)) {
				integrantesS.add(integrante);
			}
		}

		return integrantesS;
	}

	@Override
	public List<GrupoFamiliar> findGrupoFamiliarPorEstado(Long idAsignacionNss,
			Long estado) throws DerechohabientesBusinessException,Exception {


		List<GrupoFamiliar> integrantesAux = new ArrayList<GrupoFamiliar>();
		List<GrupoFamiliar> integrantes = new ArrayList<GrupoFamiliar>();
		
		//log.debug("Se buscara por estado.\n idAsignacion: " + idAsignacionNss + ". idEstado: " + estado);
		try{
			integrantesAux = wsClient.getGrupoFamiliarPorEstado(idAsignacionNss, estado.intValue());
		} catch(DerechohabientesWebSserviceException e) {
			lanzarExcepcionWsVigencia(e);
		}

		if(integrantesAux != null && !integrantesAux.isEmpty()) {

			integrantes = this.getListaComplementada(integrantesAux);
		}

		return integrantes;
	}
	
	/**
	 * Metodo para complementar una lista de grupos familiares obtenidos del webservice con la informacion almacenada en bdtu
	 * @param grupoWebService
	 * @return
	 * @throws Exception
	 */
	private List<GrupoFamiliar> getListaComplementada(List<GrupoFamiliar> grupoWebService) throws Exception {
		List<GrupoFamiliar> grupoComplementado = new ArrayList<GrupoFamiliar>();
		Map<Long, GrupoFamiliar> distintos = new HashMap<Long, GrupoFamiliar>();
		//Quitamos los diferentes
		for(GrupoFamiliar integranteWebs: grupoWebService) {
			distintos.put(integranteWebs.getDerechohabiente().getIdPersona(), integranteWebs);
		}
		
		grupoWebService = new ArrayList<GrupoFamiliar>(distintos.values());
		
		for(GrupoFamiliar integranteWebs: grupoWebService) {
			GrupoFamiliar integranteComplementado = this.getIntegranteComplementado(integranteWebs);

			if(integranteComplementado != null) {
				grupoComplementado.add(integranteComplementado);
			}
		}

		return grupoComplementado;
	}

	
	
	@Override
	public GrupoFamiliar getIntegranteSinVigencia(Long idAsignacionNss, Long idPersona) throws Exception {
		GrupoFamiliar integrante = null;

		DitGrupoFamiliar ditGrupo = null;

			try {
				Criteria criteria = this.getSession().createCriteria(DitGrupoFamiliar.class);
				
				log.debug("Se buscara el siguiente integrante en la BDTU: \n  - IdAsignacionNss: " + idAsignacionNss +
						"\n  - IdPersona: " + idPersona);
				criteria.createAlias("ditAsignacionNss", "nss");
				criteria.add(Restrictions.eq("nss.cveIdAsignacionNss", idAsignacionNss));
				criteria.createAlias("ditPersona", "der");
				criteria.add(Restrictions.eq("der.cveIdPersona", idPersona));

				ditGrupo = (DitGrupoFamiliar) criteria.uniqueResult();

				if(ditGrupo != null) {
					integrante = grupoFamiliarParserServiceLocal.persistToModel(ditGrupo);
					
				} 
			}catch (NoResultException e) {				
				integrante = null;
			}catch (Exception e) {				
				log.error("getIntegranteGrupoFamiliarByEstados", e);
				throw e;				
			}

		

		return integrante;
	}

	/**
	 * Metodo para complementar con la informacion almacenada en bdtu un objeto GrupoFamiliar recuperado del webservice
	 * @param grupoWebService
	 * @return
	 * @throws Exception
	 */
	@Override
	public GrupoFamiliar getIntegranteComplementado(GrupoFamiliar grupoWebService) throws DerechohabientesBusinessException {
		GrupoFamiliar integrante = null;

		DitGrupoFamiliar ditGrupo = null;

		if(grupoWebService != null) {

			try {
				Criteria criteria = this.getSession().createCriteria(DitGrupoFamiliar.class);
				
				log.debug("Se buscara el siguiente integrante con los datos del ws: \n  - IdAsignacionNss: " + grupoWebService.getAsignacionNSS().getIdAsignacionNSS() +
						"\n  - IdPersona: " + grupoWebService.getDerechohabiente().getIdPersona());
				criteria.createAlias("ditAsignacionNss", "nss");
				criteria.add(Restrictions.eq("nss.cveIdAsignacionNss", grupoWebService.getAsignacionNSS().getIdAsignacionNSS()));
				criteria.createAlias("ditPersona", "der");
				criteria.add(Restrictions.eq("der.cveIdPersona", grupoWebService.getDerechohabiente().getIdPersona()));

				ditGrupo = (DitGrupoFamiliar) criteria.uniqueResult();

				if(ditGrupo != null) {
					integrante = grupoFamiliarParserServiceLocal.persistToModel(ditGrupo);
					
					if( grupoWebService.getEstadoDerechohabiente() != null ){
						EstadoDerechohabiente estado = estadoDerechohabienteEntityLocal.getEstadoDerechohabiente(grupoWebService.getEstadoDerechohabiente().getIdEstadoDerechohabiente());
						integrante.setEstadoDerechohabiente(estado);
					}	
					
					if( grupoWebService.getSubEstadoDerechohabiente() != null ){
						SubEstadoDerechohabiente subEstado = estadoDerechohabienteEntityLocal.getSubEstadoDerechohabiente(grupoWebService.getSubEstadoDerechohabiente().getIdSubEstadoDerechohabiente());
						integrante.setSubEstadoDerechohabiente(subEstado);
					}
					
					if( grupoWebService.getFechaInicioVigencia() != null )
						integrante.setFechaInicioVigencia(grupoWebService.getFechaInicioVigencia());
					
					if( grupoWebService.getFechaFinVigencia() != null )
						integrante.setFechaFinVigencia(grupoWebService.getFechaFinVigencia());
					
					
					//TODO SE SETEA EL PARENTESCO DEL SERVICIO POR PROBLEMAS DE SINCRONIZACION
					if(grupoWebService.getParentesco()!= null){
						Parentesco parentesco = catalogosDao.getCatalogoParentesco(grupoWebService.getParentesco().getIdParentesco());
						integrante.setParentesco(parentesco);
					}
					
					if(grupoWebService.getAgregadoMedico() != null){
						integrante.setAgregadoMedico(grupoWebService.getAgregadoMedico());
					}
					
					
				} else {
					log.warn("Existen diferencias entre el ws de vigencia y la bdtu, no se encontro al integrante : " + grupoWebService.getDerechohabiente().getIdPersona() + 
							" con id de NSS: " + grupoWebService.getAsignacionNSS().getIdAsignacionNSS() + " en la BDTU",
							new DerechohabientesBusinessException("Existen diferencias entre el ws y BDTU", "Existen diferencias entre el ws y BDTU"));
				}
//				integrante.setAgregadoMedico(vigenciaWS.getAgregadoMedico(integrante.getAsignacionNSS().getNss(), integrante.getDerechohabiente().getIdPersona()));
			}catch (NoResultException e) {				
				integrante = null;
			}catch (Exception e) {				
				log.error("getIntegranteGrupoFamiliarByEstados", e);
				DerechohabientesBusinessException.throwException("Error en getIntegranteComplementado(GrupoFamiliar grupoWebService): "+e);
			}

		}

		return integrante;
	}
	

	
	@Override
	public List<GrupoFamiliar> findGrupoFamiliarByNssWS(Long idAsignacionNss)
			throws DerechohabientesBusinessException, Exception {
		List<GrupoFamiliar> encontrados = new ArrayList<GrupoFamiliar>();
		log.debug("Se buscara el grupo familiar para el id asignacion: " + idAsignacionNss + " solo en el WS");
		log.debug("Los estados en los que se buscara el grupo familiar son: " + this.getEstadosDerechohabiente());
		try{
			encontrados = wsClient.getGrupoFamiliarPorEstados(idAsignacionNss,this.getEstadosDerechohabiente());
		} catch(DerechohabientesWebSserviceException e) {
			lanzarExcepcionWsVigencia(e);
		}

		return encontrados;
	}

	@Override
	public List<GrupoFamiliar> findGrupoFamiliarByNss(Long idAsignacionNss) throws DerechohabientesBusinessException,Exception{

		List<GrupoFamiliar> encontradosAux = null;
		List<GrupoFamiliar> encontrados = new ArrayList<GrupoFamiliar>();
		log.debug("Se buscara el grupo familiar para el siguiente id asignacion: " + idAsignacionNss);
		log.debug("Los estados en los que se buscara el grupo familiar son: " + this.getEstadosDerechohabiente());
		try{
			encontradosAux = wsClient.getGrupoFamiliarPorEstados(idAsignacionNss,this.getEstadosDerechohabiente());
		} catch(DerechohabientesWebSserviceException e) {
			lanzarExcepcionWsVigencia(e);
		}

		if(encontradosAux != null && !encontradosAux.isEmpty()) {
			try {
				encontrados = this.getListaComplementada(encontradosAux);
				encontrados = GrupoFamiliarUtil.ordenarPorCalidad(encontrados);
			} catch (Exception e) {
				log.error("findGrupoFamiliarByNss", e);
				throw e;
			}
		}

		return encontrados;
	}
	

	private List<Integer> getEstadosDerechohabiente() {
		List<Integer> estados = new ArrayList<Integer>();
		estados.add(Integer.valueOf(""+EstadoDerechohabienteEnum.BAJA.getId()));
		estados.add(Integer.valueOf(""+EstadoDerechohabienteEnum.VIGENTE.getId()));
		estados.add(Integer.valueOf(""+EstadoDerechohabienteEnum.CON_DERECHO.getId()));
		estados.add(Integer.valueOf(""+EstadoDerechohabienteEnum.CONSERVACION_DERECHOS.getId()));
		estados.add(Integer.valueOf(""+EstadoDerechohabienteEnum.FALLECIDO.getId()));
		estados.add(Integer.valueOf(""+EstadoDerechohabienteEnum.PENSION_TRAMITE.getId()));
		estados.add(Integer.valueOf(""+EstadoDerechohabienteEnum.VIGENTE_POR_PRORRGA.getId()));

		return estados;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<GrupoFamiliar> findGrupoFamiliarSinMedico(Long idAsignacionNss,
			Long idPersonaExcluir, Boolean conVigencia) throws DerechohabientesBusinessException,
			Exception {
		List<GrupoFamiliar> grupoFamiliar = new ArrayList<GrupoFamiliar>();
		List<DitGrupoFamiliar> ditGrupoFamiliar = new ArrayList<DitGrupoFamiliar>();
		
		conVigencia = conVigencia != null ? conVigencia : false;
		
		Criteria queryGrupo = this.getSession().createCriteria(DitGrupoFamiliar.class);
		queryGrupo.createAlias("ditAsignacionNss", "nss");
		queryGrupo.add(Restrictions.eq("nss.cveIdAsignacionNss", idAsignacionNss));
		queryGrupo.add(Restrictions.isNull("ditUmfConsTurnoMedico"));
		
		if(idPersonaExcluir != null) {
			queryGrupo.createAlias("ditPersona", "integrante");
			queryGrupo.add(Restrictions.ne("integrante.cveIdPersona", idPersonaExcluir));
		}
		
		ditGrupoFamiliar = queryGrupo.list();
		
		if(ditGrupoFamiliar != null && !ditGrupoFamiliar.isEmpty()){
			grupoFamiliar = grupoFamiliarParserServiceLocal.persistToModelList(ditGrupoFamiliar);
			
			if(conVigencia) {
				this.complementarVigencia(grupoFamiliar);
			}
		}
		
		
		
		return grupoFamiliar;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GrupoFamiliar> findGrupoFamiliarSinMedico(String nss) throws DerechohabientesBusinessException,Exception {
		List<GrupoFamiliar> encontrados = new ArrayList<GrupoFamiliar>();
		List<DitGrupoFamiliar> grupoFamiliar = new ArrayList<DitGrupoFamiliar>();
		try {
			Query query = em.createQuery("select g from DitGrupoFamiliar g " +
			"where g.ditAsignacionNss.numNss=:nss and g.ditUmfConsTurnoMedico.cveIdUmfConsTurnoMed IS NULL");
			query.setParameter("nss",nss);

			grupoFamiliar = query.getResultList();
			encontrados = grupoFamiliarParserServiceLocal.persistToModelList(grupoFamiliar);
			this.complementarVigencia(encontrados);
		} catch (Exception e) {
			log.error("findGrupoFamiliarSinMedico", e);
			throw e;
		}

		return encontrados;
	}

	@Override
	public List<GrupoFamiliar> findGrupoFamiliarByParentescoNss(Long idAsignacionNss,
			Long parentesco) throws DerechohabientesBusinessException,Exception{
		List<GrupoFamiliar> integrantesAux = null;
		List<GrupoFamiliar> integrantes = null;

		log.debug("Se buscara por parentesco para el siguiente idAsignacion: " + idAsignacionNss + ", idParentesco: " + parentesco);
		try{
			integrantesAux = wsClient.getGrupoFamiliarPorParentesco(idAsignacionNss, parentesco.intValue());
		} catch(DerechohabientesWebSserviceException e) {
			lanzarExcepcionWsVigencia(e);
		}

		if(integrantesAux != null && !integrantesAux.isEmpty()) {
			integrantes = this.getListaComplementada(integrantesAux);
		}

		return integrantes;
	}


	/**
	 * Metodo para obtener todos los integrantes del grupo familiar
	 * @param DatosEntradaPaginador<AsignacionNSS> filtros para la busqueda
	 * @param idEstado El tipo de estado que buscamos en los integrantes del grupo familiar
	 * es decir vigente o baja, en caso de ser nulo se buscaran a los integrantes del grupo cualquiera
	 * que sea su estado
	 * @throws DerechohabientesBusinessException 
	 */
	@Override
	public DatosSalidaPaginador<GrupoFamiliar> paginarGrupoFamiliar(
			DatosEntradaPaginador<AsignacionNSS> entrada, Long idEstado) throws DerechohabientesBusinessException,Exception {

		Criteria grupoFamiliarC = this.getSession().createCriteria(DitGrupoFamiliar.class);

		AsignacionNSS nss = entrada.getModelo();
		DatosSalidaPaginador<GrupoFamiliar> salida = new DatosSalidaPaginador<GrupoFamiliar>();
		List<GrupoFamiliar> grupoFamiliar = new ArrayList<GrupoFamiliar>();
		Long encontrados = new Long(3);

		try {

			if(nss.getIdAsignacionNSS() != null){
				grupoFamiliarC.createAlias("ditAsignacionNss", "nss");
				grupoFamiliarC.add(Restrictions.eq("nss.cveIdAsignacionNss", nss.getIdAsignacionNSS()));
			}
			
			grupoFamiliarC.add(Restrictions.isNull("fecRegistroBaja"));
			grupoFamiliarC.add(Restrictions.ne("numCalidad", 1L));
			grupoFamiliarC.addOrder(Order.asc("numCalidad"));

			encontrados = (Long) grupoFamiliarC.setProjection(Projections.rowCount()).uniqueResult();
			//si encontramos personas las verificaremos
			if(encontrados > 0) {
				log.debug("el inicio es: " + entrada.getiDisplayStart());
				log.debug("Y se modtraran: " + entrada.getiDisplayLength());
				
				grupoFamiliar = this.findDatosBasicosIntegrantesGrupoByIdAsignacionNss(nss.getIdAsignacionNSS(), true, false, true, entrada.getiDisplayStart(), entrada.getiDisplayLength());
				if(idEstado != null && idEstado.longValue() != 0) {
					List<Long> estados = new ArrayList<Long>();
					estados.add(idEstado);
					grupoFamiliar = this.obtenerIntegrantesConEstado(grupoFamiliar, estados);
				}
				/*grupoFamiliarC.setProjection(null);
				grupoFamiliarC.setResultTransformer(Criteria.ROOT_ENTITY);
	
				grupoFamiliarC.setFirstResult(entrada.getiDisplayStart());
				grupoFamiliarC.setMaxResults(entrada.getiDisplayLength());
	
	
				log.debug("DatosSalidaPaginador");	
				ditGrupoFamiliar = grupoFamiliarC.list();
				
				if(ditGrupoFamiliar!= null && !ditGrupoFamiliar.isEmpty()) {
					grupoFamiliar = grupoFamiliarParserServiceLocal.persistToModelList(ditGrupoFamiliar);
					
					grupoFamiliar = complementarVigencia(grupoFamiliar);
		
					if(idEstado != null && idEstado.longValue() != 0) {
						List<Long> estados = new ArrayList<Long>();
						estados.add(idEstado);
						grupoFamiliar = this.obtenerIntegrantesConEstado(grupoFamiliar, estados);
					}
				}*/
			}
			salida.setAaData(grupoFamiliar);
			salida.setiTotalDisplayRecords(encontrados.intValue());
			salida.setiTotalRecords(encontrados.intValue());
		} catch (Exception e) {
			log.error("paginarGrupoFamiliar", e);
			throw e;
		}


		return salida;
	}

	/**
	 * Metodo para complementar a una lista de grupo familiar con los datos de la vigencia obtenida del webservice
	 * @param grupoBdtu - List<GrupoFamiliar>
	 * @throws DerechohabientesBusinessException
	 */
	private List<GrupoFamiliar> complementarVigencia(List<GrupoFamiliar> grupoBdtu) throws DerechohabientesBusinessException{
		List<GrupoFamiliar> listaAux = new ArrayList<GrupoFamiliar>();
		//verificamos que la lista no venga nula y que no este vacia
		if(grupoBdtu != null && !grupoBdtu.isEmpty()) {
			
			
			//para cada grupo complemetamos la vigencia
			for(GrupoFamiliar integranteBdtu: grupoBdtu) {
				GrupoFamiliar integranteAux = integranteBdtu;
				try{
					integranteAux = complementarVigencia(integranteBdtu);
				} catch (Exception e) {
					log.error("No fue posible localizar al integrante en el ws de vigencia",e);
				}
				
				listaAux.add(integranteAux);
			}
			
		}
		
		return listaAux;
	}
	
	/**
	 * Metodo para complementar a una lista de grupo familiar con los datos de la vigencia obtenida del webservice arroja una excepcion 
	 * si ocurre algun error al consultar la vigencia de algun integrante de la lsita
	 * @param grupoBdtu - List<GrupoFamiliar>
	 * @throws DerechohabientesBusinessException
	 */
	private List<GrupoFamiliar> complementarVigenciaConExcepcion(List<GrupoFamiliar> grupoBdtu) throws DerechohabientesBusinessException{
		List<GrupoFamiliar> listaAux = new ArrayList<GrupoFamiliar>();
		//verificamos que la lista no venga nula y que no este vacia
		if(grupoBdtu != null && !grupoBdtu.isEmpty()) {
			
			try{
			//para cada grupo complemetamos la vigencia
			for(GrupoFamiliar integranteBdtu: grupoBdtu) {
				GrupoFamiliar integranteAux = integranteBdtu;
				integranteAux = complementarVigencia(integranteBdtu);
				listaAux.add(integranteAux);
				}
			}catch (DerechohabientesBusinessException e){
				log.error("No fue posible localizar al integrante en el ws de vigencia",e);
				throw e;
			} catch (Exception e) {
				log.error("Error desconocido al complementar a los integrantes",e);
				throw new DerechohabientesBusinessException(e.getMessage());
			}
			
			
		}
		
		return listaAux;
	}

	/**
	 * Metodo para ocmplementar con los datos de vigencia a un integrante del grupo familiar
	 * @param integranteBdtu - GrupoFamiliar
	 * @throws DerechohabientesBusinessException
	 */
	private GrupoFamiliar complementarVigencia(GrupoFamiliar integranteBdtu) throws DerechohabientesBusinessException, RollbackException{

		GrupoFamiliar integranteWS = null ;

		//invocamos al cliente del webservice con los datos de integrante para obtener su vigencia
		try {
			log.debug("Se buscara el siguiente integrante con los datos que se obtuvieron de la bdtu: \n  - IdAsignacionNss: " + integranteBdtu.getAsignacionNSS().getIdAsignacionNSS() +
					"\n  - IdPersona: " + integranteBdtu.getDerechohabiente().getIdPersona());
			
			integranteWS = wsClient.getGrupoFamiliarPorDerechohabiente(integranteBdtu.getAsignacionNSS().getIdAsignacionNSS(),
					integranteBdtu.getDerechohabiente().getIdPersona().intValue());
		} catch( DerechohabientesWebSserviceException e) {
			lanzarExcepcionWsVigencia(e);
		}
		//En caso de que no aya ocurrido ningun error o que el integrante no sea nulo
		if(integranteWS != null) {
			//Consultamos los estados en bdtu
			EstadoDerechohabiente estado = null;
			SubEstadoDerechohabiente subEstado = null;
			
			Long idEstado = integranteWS.getEstadoDerechohabiente().getIdEstadoDerechohabiente();
			Long idSubestado = integranteWS.getSubEstadoDerechohabiente().getIdSubEstadoDerechohabiente();
			
			if(estados.containsKey(idEstado)){
				estado = estados.get(integranteWS.getEstadoDerechohabiente().getIdEstadoDerechohabiente());
			} else {
				estado = estadoDerechohabienteEntityLocal.getEstadoDerechohabiente(idEstado);
				estados.put(estado.getIdEstadoDerechohabiente(), estado);
			}
			
			if(subestados.containsKey(idSubestado)) {
				subEstado = subestados.get(idSubestado);
			} else {
				subEstado = estadoDerechohabienteEntityLocal.getSubEstadoDerechohabiente(idSubestado);
				subestados.put(idSubestado, subEstado);
			}
			//Estabelcemos estados y fechas
			integranteBdtu.setEstadoDerechohabiente(estado);
			integranteBdtu.setSubEstadoDerechohabiente(subEstado);
			integranteBdtu.setFechaInicioVigencia(integranteWS.getFechaInicioVigencia());
			integranteBdtu.setFechaFinVigencia(integranteWS.getFechaFinVigencia());
		}

		return integranteBdtu;
	}

	@Override
	public AsignacionNSS getAsignacionNss(String nss, long idPersona) throws DerechohabientesBusinessException,Exception {		 
		DitAsignacionNss ditAsignacion = null;
		try {
			Query query = em.createNamedQuery("buscaAsignacionXnss");
			query.setParameter("numNss", nss);
			query.setParameter("idPersona", idPersona);
			ditAsignacion = (DitAsignacionNss) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}catch (NonUniqueResultException e) {
			log.debug("getAsignacionNss regresa mes de un resultado", e);
			throw e;
		}catch (Exception e) {
			log.debug("getAsignacionNss", e);
			throw e;
		}

		return AsignacionNSSParser.persisToModel(ditAsignacion);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AsignacionNSS> getAsignacionNss(Long idPersona) throws DerechohabientesBusinessException {		 
		List<DitAsignacionNss> ditAsignaciones = null;
		//List<DicTipoPension> pensiones = null;
		List<AsignacionNSS> nsss = null;
		try {
			
			StringBuffer jpaQuery = new StringBuffer();
			jpaQuery.append("select asig  "); 
			jpaQuery.append("from DitAsignacionNss asig ");
			jpaQuery.append("where asig.ditPersona.cveIdPersona=:idPersona ");
			jpaQuery.append("and asig.fecRegistroBaja is null ");
			jpaQuery.append("and (asig.indActivo = :indActivo or asig.indActivo is null)");
			
			Query query = this.em.createQuery(jpaQuery.toString());
			query.setParameter("indActivo", BigDecimal.ONE);
			
			query.setParameter("idPersona", idPersona);
			ditAsignaciones = query.getResultList();
	
		}catch (NoResultException e) {
			return null;
		}catch (Exception e) {
			log.debug("getAsignacionNss", e);
			DerechohabientesBusinessException.throwException("Ocurrio un error en getAsignacionNss(Long idPersona): "+e);
		}

		if(ditAsignaciones != null) {
			if(!ditAsignaciones.isEmpty()) {
				nsss = new ArrayList<AsignacionNSS>();
				for(DitAsignacionNss asig: ditAsignaciones) {
					AsignacionNSS asignacionNss= AsignacionNSSParser.persisToModel(asig);
					if(asignacionNss != null) {
						asignacionNss.setPensionado(false);
						nsss.add(asignacionNss);
					}
				}
			}
		}

		return nsss;
	}

	/**
	 * Metodo para obtener la informacion del nss y las pensiones relacionadas a partir del idasignacionNss
	 */
	@Override
	public AsignacionNSS getAsignacionNssByIdAsignacionNss(Long idAsignacionNss)
			throws DerechohabientesBusinessException {
		
		
		DitAsignacionNss ditAsignacion = null;
		//List<DicTipoPension> pensiones = null;
		try {

			ditAsignacion = this.em.find(DitAsignacionNss.class, idAsignacionNss);
			/*
			Criteria pensionesC = this.getSession().createCriteria(DitAseguradoPension.class);
			pensionesC.setProjection(Projections.property("dicTipoPension"));
			Criteria aseguradoC = pensionesC.createCriteria("ditAsegurado");
			aseguradoC.createAlias("ditAsignacionNss", "nss");
			pensionesC.add(Restrictions.eq("nss.cveIdAsignacionNss", idAsignacionNss));
			pensiones = pensionesC.list();
	*/
		}catch (NoResultException e) { 
			return null;
		}catch (NonUniqueResultException e) {
			log.debug("getAsignacionNss regresa mes de un resultado", e);
			DerechohabientesBusinessException.throwException("Se encontraron mas de un nss con el id");
		}catch (Exception e) {
			log.error("getAsignacionNss", e);
			DerechohabientesBusinessException.throwException("Ocurrio un error al consultar el nss");
		}

		AsignacionNSS asignacionNss= AsignacionNSSParser.persisToModel(ditAsignacion);
	/*
		if(asignacionNss != null) {
			if(pensiones != null && !pensiones.isEmpty()) {
				asignacionNss.setPensionado(true);
				asignacionNss.setTipoPension(pensiones.get(0).getRefMarcaPension() + " - " + pensiones.get(0).getDesTipoPension());
			} else {
				asignacionNss.setPensionado(false);
			}
		}*/
		
		return asignacionNss;
	}

	@Override
	public AsignacionNSS getAsignacionNss(String nss)
	throws DerechohabientesBusinessException, AsignacionNSSNoLocalizadoException, Exception {

		Criteria asignacionNssC = this.getSession().createCriteria(DitAsignacionNss.class);
		//Criteria pensionesC = this.getSession().createCriteria(DitAseguradoPension.class);
		DitAsignacionNss ditAsignacion = null;
		//List<DicTipoPension> pensiones = null;
		try {

			System.out.println(" ******************************************************** entra a la consulta de nss: " + nss + " ***************************************");
			asignacionNssC.add(Restrictions.eq("numNss", nss));
			ditAsignacion = (DitAsignacionNss) asignacionNssC.uniqueResult();
			System.out.println(" ******************************************************** Sale de la consulta del nss" + nss + "***************************************************");
			if(ditAsignacion == null){
				String strQueryAseguradoBaja = "select nss.num_nss "
						+ " from dit_asignacion_nss nss where nss.num_nss = '" + nss + "' and nss.fec_registro_baja is not null";
				
				SQLQuery queryAsegurardoBaja = this.getSession().createSQLQuery(strQueryAseguradoBaja);
				List<Object[]> resultadoGrupo = (List<Object[]>)queryAsegurardoBaja.list();
				log.debug("Se verificara el grupo familiar del NSS: " + nss);
				if(!resultadoGrupo.isEmpty()) {
					throw new AsignacionNSSNoLocalizadoException("Asegurado con fecha de  baja", 1);
				}
				return null;
			}
			
			/*
			Query query = em.createNamedQuery("buscaAsignacionXnssSinPersona");
			query.setParameter("numNss", nss);			
			ditAsignacion = (DitAsignacionNss) query.getSingleResult();


			pensionesC.setProjection(Projections.property("dicTipoPension"));
			Criteria aseguradoC = pensionesC.createCriteria("ditAsegurado");
			aseguradoC.createAlias("ditAsignacionNss", "nss");
			pensionesC.add(Restrictions.eq("nss.cveIdAsignacionNss", ditAsignacion.getCveIdAsignacionNss()));

			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<DicTipoPension> queryPension = cb.createQuery(DicTipoPension.class);
			Root<DitAseguradoPension> root = queryPension.from(DitAseguradoPension.class);
			Path<DicTipoPension> path=root.get("dicTipoPension");
			queryPension.select(path);
			List<Predicate> listaFiltros = new ArrayList<Predicate>();
			listaFiltros.add(cb.equal(root.get("ditAsegurado").get("ditAsignacionNss").get("cveIdAsignacionNss").as(Integer.class), ditAsignacion.getCveIdAsignacionNss()));
			queryPension.where(listaFiltros.get(0));

			pensiones= em.createQuery(queryPension).getResultList();

			pensiones = pensionesC.list();*/

		}catch (NoResultException e) { 
			return null;
		}catch (NonUniqueResultException e) {
			log.debug("getAsignacionNss regresa mes de un resultado", e);
			throw e;
		}catch (Exception e) {
			log.error("getAsignacionNss", e);
			throw e;
		}

		AsignacionNSS asignacionNss= AsignacionNSSParser.persisToModel(ditAsignacion);
		/*
		if(asignacionNss != null) {
			if(pensiones != null && !pensiones.isEmpty()) {
				asignacionNss.setPensionado(true);
				asignacionNss.setTipoPension(pensiones.get(0).getRefMarcaPension() + " - " + pensiones.get(0).getDesTipoPension());
			} else {
				asignacionNss.setPensionado(false);
			}
		}*/
		return asignacionNss;
	}

	@Override
	/**
	 * Metodo para obtener a un integrante de un grupo familiar pasandole como parametro
	 * el nss de la cabezadel grupo familiar y el id de la personaabuscar
	 */
	public GrupoFamiliar getIntegranteGrupoFamiliar(Long idAsignacionNss, Long idPersona) throws DerechohabientesBusinessException {

		GrupoFamiliar integranteAux = null;
		GrupoFamiliar integrante = null;

		log.debug("\n Se buscara al integrante con los datos de la bdtu en el ws: \n - IdAsignacionNss:  " + idAsignacionNss + "\n  IdPersona: " + idPersona);
		try {
			integranteAux = wsClient.getGrupoFamiliarPorDerechohabiente(idAsignacionNss,idPersona.intValue());
		} catch(DerechohabientesWebSserviceException e) {
			lanzarExcepcionWsVigencia(e);
		}

		if(integranteAux != null) {
			log.debug("\n El ws regreso los siguientes datos: \n - IdAsignacionNss:  " + integranteAux.getAsignacionNSS().getIdAsignacionNSS()
					+ "\n  IdPersona: " + integranteAux.getDerechohabiente().getIdPersona());
			//TODO se setea el id de la persona no importando lo que regrese el ws, ya que al dia 18/06/2014 en campo de id venis ocupado por el idasignacionnss
			integranteAux.getDerechohabiente().setIdPersona(idPersona);
			integrante = this.getIntegranteComplementado(integranteAux);
		}
		
		integrante.setConDerechoSm(this.getServicioMedicoDerechohabiente(idAsignacionNss, idPersona));
		Boolean tieneAcuerdo = false;
		String numeroAcuerdo = null;
		
		if(integrante.getParentesco().getIdParentesco().longValue() == ParentescoEnum.PADRES.getId()){
			 //tieneAcuerdo  = acuerdoDerechohabienteEntityLocal.tieneAcuerdoVigente(integrante.getAsignacionNSS().getIdAsignacionNSS(), integrante.getDerechohabiente().getIdPersona());
			 TramiteAcuerdoDh acuerdoPadre = this.acuerdoDerechohabienteEntityLocal.getAcuerdoDerechohabiente(
					 integrante.getAsignacionNSS().getIdAsignacionNSS(),
					 integrante.getDerechohabiente().getIdPersona(), 1L);
			 tieneAcuerdo = acuerdoPadre != null;
			 numeroAcuerdo = tieneAcuerdo ? acuerdoPadre.getNumeroAcuerdo() : null;
			
			 if(!tieneAcuerdo && integrante.getEstadoDerechohabiente().getIdEstadoDerechohabiente().longValue() == EstadoDerechohabienteEnum.VIGENTE.getId()){
				CabezaGrupoFamiliar cabezaGrupoFamiliar=  null;
				try {
					cabezaGrupoFamiliar = this.getCabezaGrupoFamiliarWS(idAsignacionNss);
				} catch(Exception e) {
					log.error("ocurrio un eror al consultar la cabeza en el integrante para validar si tiene defunci�n", e);
				}
				if(cabezaGrupoFamiliar !=null){
					if(cabezaGrupoFamiliar.getEstadoDerechohabiente().getIdEstadoDerechohabiente().longValue() ==
							EstadoDerechohabienteEnum.FALLECIDO.getId() )
								tieneAcuerdo = true;
				}
			}
			
		}
		
		integrante.setNumeroAcuerdo(numeroAcuerdo);
		integrante.setIndAcuerdo(tieneAcuerdo ? 1 : 0);
		return integrante;
	}

	@Override
	public GrupoFamiliar getIntegranteGrupoFamiliarByAsignacionNss(
			Long idAsignacionNss, Long idPersona, Long idEstadoDerechohabiente) throws DerechohabientesBusinessException,Exception {
		GrupoFamiliar integranteAux = null;
		GrupoFamiliar integrante = null;

		log.debug("Se buscara la vigencia para la persona: " + idPersona + " dentro del grupo familiar " +
				"con id asignacion: " + idAsignacionNss + " y estado: " + idEstadoDerechohabiente);
		try {
			integranteAux = wsClient.getGrupoFamiliarPorDerechohabiente(idAsignacionNss,idPersona.intValue());
		} catch(DerechohabientesWebSserviceException e) {
			lanzarExcepcionWsVigencia(e);
		}

		if(integranteAux != null) {

			if(integranteAux.getEstadoDerechohabiente().getIdEstadoDerechohabiente().equals(idEstadoDerechohabiente)) {
				integrante = this.getIntegranteComplementado(integranteAux);
			}
		}

		return integrante;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GrupoFamiliar> getIntegrantesGrupoFamiliarByAsignacionNss(
			Long idAsignacionNss, List<Long> idPersona, List<Long> idEstadoDerechohabiente) throws DerechohabientesBusinessException {

		List<DitGrupoFamiliar> ditIntegrantes = null;		
		List<GrupoFamiliar> integrantes = new ArrayList<GrupoFamiliar>();

		try {

			Criteria queryGrupoFamiliar = this.getSession().createCriteria(DitGrupoFamiliar.class);
			queryGrupoFamiliar.createAlias("ditAsignacionNss", "nss");
			queryGrupoFamiliar.add(Restrictions.eq("nss.cveIdAsignacionNss",idAsignacionNss));
			queryGrupoFamiliar.createAlias("ditPersona", "persona");
			
			if(idPersona.size() == 1) {
				queryGrupoFamiliar.add(Restrictions.eq("persona.cveIdPersona", idPersona.get(0)));
			} else {
				queryGrupoFamiliar.add(Restrictions.in("persona.cveIdPersona", idPersona));
			}
			
			ditIntegrantes = queryGrupoFamiliar.list();
			
			if(!ditIntegrantes.isEmpty()) {
				integrantes = grupoFamiliarParserServiceLocal.persistToModelList(ditIntegrantes);
				
				if(idEstadoDerechohabiente != null && !idEstadoDerechohabiente.isEmpty()) {
					this.complementarVigencia(integrantes);
					integrantes = this.obtenerIntegrantesConEstado(integrantes, idEstadoDerechohabiente);
				}
			}
		} catch(Exception e) {
			log.error("ocurrio un error desconocido", e);
			DerechohabientesBusinessException.throwException("Ocurrio un error desconocido", e.getMessage());
		}
		/*
		try {

			Criteria queryG = this.getSession().createCriteria(DitGrupoFamiliar.class);
			queryG.createAlias("ditAsignacionNss", "nss");
			queryG.add(Restrictions.eq("nss.cveIdAsignacionNss", idAsignacionNss));

			if(idEstadoDerechohabiente != null && !idEstadoDerechohabiente.isEmpty()) {
				queryG.createAlias("dicEstadoDerechohabiente", "estado");
				if(idEstadoDerechohabiente.size() == 1) {
					queryG.add(Restrictions.eq("estado.cveEstadoDerechohabiente", idEstadoDerechohabiente));
				} else {
					queryG.add(Restrictions.in("estado.cveEstadoDerechohabiente", idEstadoDerechohabiente));
				}
			}

			queryG.createAlias("ditDerechohabiente", "der");

			if(idPersona.size() > 1) {
				queryG.add(Restrictions.in("der.cveIdPersona", idPersona));
			} else {
				queryG.add(Restrictions.eq("der.cveIdPersona", idPersona.get(0)));
			}

			ditIntegrantes = queryG.list();

		} catch (NoResultException e){
			ditIntegrantes = null;
		} catch (Exception e) {
			log.error("Ocurrio un error", e);		
		}

		if(ditIntegrantes != null && !ditIntegrantes.isEmpty()){
			try {
				integrantes = grupoFamiliarParserServiceLocal.persistToModelList(ditIntegrantes);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/

		return integrantes;
	}

	@Override
	public GrupoFamiliar updateIntegrante(GrupoFamiliar integrante) throws DerechohabientesBusinessException,Exception {
        return updateIntegrante(integrante, false);
	}

    @Override
    public GrupoFamiliar updateIntegrante(GrupoFamiliar integrante, boolean afectarDomicilio) throws DerechohabientesBusinessException,Exception {

        PersonaDomicilio personaDom = null;
        // ------------------------------------------------------------------------------------------------------
        // Se verifica si el integrante trae domicilio para guardar o actualizar la relacion personafdom
        // una vez que se guarda se pone en el integrante, en caso de no venir se actualiza lo demas
        // ------------------------------------------------------------------------------------------------------
        if(integrante.getDomicilio() != null) {

            personaDom = new PersonaDomicilio();

            personaDom.setDomicilio(integrante.getDomicilio());
            personaDom.setTipoDomicilio(new TipoDomicilio());
            personaDom.getTipoDomicilio().setClave(TipoDomicilioEnum.PARTICULAR.getCodigo().intValue());
            personaDom.setPersona(integrante.getDerechohabiente());


            if(StringUtils.isNotBlank(integrante.getDomicilio().getAsentamiento().getClave())){
				personaDom = this.savePersonaDomicilio(personaDom);
                integrante.setCvePersonaDomicilio(personaDom.getCvePersonaDomicilio());
            }else{
                integrante.setCvePersonaDomicilio(null);
            }

        }
        DitGrupoFamiliar ditGrupoFamiliar = grupoFamiliarParserServiceLocal.modelToPersist(integrante, afectarDomicilio);

        try {
            em.merge(ditGrupoFamiliar);
            em.flush();
        } catch (Exception e) {
            log.error("updateIntegrante", e);
            throw e;
        }

        return integrante;

    }

	
	@SuppressWarnings("unchecked")
	@Override
	public PersonaDomicilio getPersonaFDom(Long idPersona) {
		PersonaDomicilio unaPersonaDom = new PersonaDomicilio();
		List<DitPersonafDom> unDitPersonafDom = new ArrayList<DitPersonafDom>();
		try {
			Query query = em.createNamedQuery("getPersonafDom");
			query.setParameter("idPersona", idPersona);
			query.setParameter("tipoDomicilio", TipoDomicilioEnum.PARTICULAR.getCodigo());		
			unDitPersonafDom = query.getResultList();
			if(unDitPersonafDom.size() > 0){
				unaPersonaDom = personaDomicilioParserServiceLocal.persistToModel(unDitPersonafDom.get(0));
			}
		} catch (Exception e) {
			log.error("Error - getPersonaDom", e);
		}
			
		return unaPersonaDom;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PersonaDomicilio savePersonaDomicilio(PersonaDomicilio miPersonaDomicilio) throws DerechohabientesBusinessException,Exception {
		DitPersonafDom unDitPersonafDom = null;
		try {
			if(miPersonaDomicilio.getCvePersonaDomicilio() != null) {
				try {
					unDitPersonafDom = this.em.find(DitPersonafDom.class, miPersonaDomicilio.getCvePersonaDomicilio());
				}catch(NoResultException e) {
					log.debug("No se encontro la relacion de persona domicilio");
				}
			}
			
			if(unDitPersonafDom == null) {
				Criteria query = this.getSession().createCriteria(DitPersonafDom.class);
				query.createAlias("ditPersona", "persona");
				query.add(Restrictions.eq("persona.cveIdPersona", miPersonaDomicilio.getPersona().getIdPersona()));
				query.createAlias("dicTipoDomicilio", "tipoDom");
				query.add(Restrictions.eq("tipoDom.cveIdTipoDomicilio", miPersonaDomicilio.getTipoDomicilio().getClave().longValue()));
				
				List<DitPersonafDom> personasFDom = query.list();
				if(personasFDom != null && !personasFDom.isEmpty()) {
					unDitPersonafDom = personasFDom.get(0);
				}
			}
			
			if(unDitPersonafDom == null) {
				miPersonaDomicilio.setFechaRegistroAlta(new Date());
				unDitPersonafDom = personaDomicilioParserServiceLocal.modelToPersist(miPersonaDomicilio);
				em.persist(unDitPersonafDom);
			} else {
				unDitPersonafDom.setFecRegistroActualizado(new Date());
				unDitPersonafDom.setDgDomicilioGeografico(new DgDomicilioGeografico());
				unDitPersonafDom.getDgDomicilioGeografico().setDomicilioId(miPersonaDomicilio.getDomicilio().getClave().longValue());
				em.merge(unDitPersonafDom);
			}
			
			miPersonaDomicilio.setCvePersonaDomicilio(unDitPersonafDom.getCveIdPersonafDom());
		} catch (Exception e) {
			log.error("Error - savePersonaDomicilio", e);
			throw e;
		}
		
		return miPersonaDomicilio;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getMedioContacto(long idPersona, long tipoContacto)
	throws Exception {
		List<String> contactos = null;
		StringBuffer sb = new StringBuffer();
		SQLQuery q = null;
		try {
			sb.append("SELECT DES_FORMA_CONTACTO FROM DIT_FORMA_CONTACTO WHERE CVE_ID_TIPO_CONTACTO= :tipoContacto");
			sb.append(" AND CVE_ID_FORMA_CONTACTO IN(SELECT CVE_ID_FORMA_CONTACTO FROM DIT_PERSONAF_CONTACTO WHERE CVE_ID_PERSONA= :idPersona)");
			Session session = em.unwrap(Session.class);
			q = session.createSQLQuery(sb.toString());
			q.setParameter("tipoContacto", tipoContacto);
			q.setParameter("idPersona", idPersona);
			contactos = q.list();
		}catch (NoResultException e){
			contactos = null;
		}catch (Exception e) {
			log.error("getMedioContacto", e);
			throw e;
		}

		return contactos;
	}

	@Override
	public List<GrupoFamiliar> findGrupoFamiliarByEstado(Long idAsignacionNss,
			List<Long> estados) throws DerechohabientesBusinessException,Exception {
		List<Integer> idEstados = new ArrayList<Integer>();

		if(estados != null) {
			for(Long idEs : estados ){
				idEstados.add(idEs.intValue());
			}
		}
		List<GrupoFamiliar> integrantesWs = null;
		try {
			log.debug("Se buscaran los integrantes para el idAsignacion: " + idAsignacionNss + " con los siguientes estados " + idEstados);
			
			integrantesWs = wsClient.getGrupoFamiliarPorEstados(idAsignacionNss,idEstados);
		} catch(DerechohabientesWebSserviceException e) {
			lanzarExcepcionWsVigencia(e);
		}
		List<GrupoFamiliar> resultado =new ArrayList<GrupoFamiliar>();

		if(integrantesWs != null && !integrantesWs.isEmpty()) {
			resultado = this.getListaComplementada(integrantesWs);
		} 

		return resultado;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<GrupoFamiliar> findIntegrantesByParentescoEstado(
			Long idPersona, Long idParentesco, Long idEstadoDerechohabiente, Long idAsignacionNSSExcluir)
			throws Exception {

		List<DitGrupoFamiliar> encontrados = null;
		List<GrupoFamiliar> grupo = null;

		try {

			Criteria queryParentescoEstado = this.getSession().createCriteria(DitGrupoFamiliar.class);
			//queryParentescoEstado.createAlias("dicEstadoDerechohabiente", "estado");
			queryParentescoEstado.createAlias("ditPersona", "der");
			queryParentescoEstado.createAlias("dicCalidadParentesco", "parentesco");
			//queryParentescoEstado.add(Restrictions.eq("estado.cveEstadoDerechohabiente", idEstadoDerechohabiente));
			queryParentescoEstado.add(Restrictions.eq("der.cveIdPersona", idPersona));
			queryParentescoEstado.add(Restrictions.eq("parentesco.cveIdCalidadParentesco", idParentesco));

			if(idAsignacionNSSExcluir != null) {
				queryParentescoEstado.createAlias("ditAsignacionNss", "nss");
				queryParentescoEstado.add(Restrictions.ne("nss.cveIdAsignacionNss", idAsignacionNSSExcluir));
			}
			encontrados = queryParentescoEstado.list();
			grupo = grupoFamiliarParserServiceLocal.persistToModelList(encontrados);
			//Se agrega complemento de vigencia con el webservice
			this.complementarVigencia(grupo);
			//Se verifica que que los integrantes tengan el estado proporcionado
			if(idEstadoDerechohabiente != null) {
				List<Long> estados = new ArrayList<Long>();
				estados.add(idEstadoDerechohabiente);
				
				grupo = this.obtenerIntegrantesConEstado(grupo, estados);
			}
		
		} catch(DerechohabientesBusinessException e) {
			throw e;
		} catch (Exception e) {
			log.error("findIntegrantesByParentescoEstado", e);
			throw e;
		}

		return grupo;
	}


	/**
	 * MEtodo para obtener los candidatos a circunscripcion asi como a suspension, ademas de obtener los miembros
	 * que tienenuna circunscripcion activa y los que tienen una suspension
	 * @param nss AsignacionNSS objeto donde obtendremos el nss
	 * @param estadoD EstadoDerechohabienteEnum el estado que estamos buscando
	 * @param candidato boolean si es true traremos a los candidatos que son candidatos aautorizacion si es false traeremos a los que ya tienen una cricunscripcion 
	 * @param circunscripcion boolean si es true traeremos a los integrantes del grupo familiar con una circunscripcion activa si e false traermos a los candidatos con suspension de circunscripcion
	 * @throws DerechohabientesBusinessException 
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public List<GrupoFamiliar> findGrupoFamiliarCircunscripcion(AsignacionNSS nss, EstadoDerechohabienteEnum estadoD, boolean candidato, 
			boolean circunscripcion) throws DerechohabientesBusinessException,Exception {
		List<DitGrupoFamiliar> encontrados = null;
		List<GrupoFamiliar> resultado = null;

		try {

			Criteria queryGrupoCircunscripcion = this.getSession().createCriteria(DitGrupoFamiliar.class);
			queryGrupoCircunscripcion.createAlias("ditAsignacionNss", "nss");
			queryGrupoCircunscripcion.createAlias("ditPersona", "der");
			//queryGrupoCircunscripcion.createAlias("dicEstadoDerechohabiente", "estadoDer");
			queryGrupoCircunscripcion.add(Restrictions.eq("nss.numNss", nss.getNssStr()));
			//queryGrupoCircunscripcion.add(Restrictions.eq("estadoDer.cveEstadoDerechohabiente", estadoD.getId()));

			DetachedCriteria queryCircuncripcion = DetachedCriteria.forClass(DitCircunscripcionForanea.class);
			DetachedCriteria queryTramit = queryCircuncripcion.createCriteria("ditTramite","tram");
			DetachedCriteria queryTramitePf = queryTramit.createCriteria("ditTramitePersonaFisica", "tramPF");
			DetachedCriteria queryPersona = queryTramitePf.createCriteria("ditPersona", "persona");
			queryCircuncripcion.setProjection(Projections.property("persona.cveIdPersona"));

			if(circunscripcion == true) {
				queryCircuncripcion.add(Restrictions.isNull("fecFinCircunscripcion"));
				queryCircuncripcion.add(Restrictions.eq("indCircunscripcionActiva", 1));
			}
			else {
				queryCircuncripcion.add(Restrictions.isNotNull("fecFinCircunscripcion"));
				queryCircuncripcion.add(Restrictions.eq("indCircunscripcionActiva", 0));
			}

			DetachedCriteria querySolicitud = queryTramit.createCriteria("ditSolicitud");
			querySolicitud.createAlias("dicEstadoSolicitud", "estadoSol");
			querySolicitud.add(Restrictions.eq("estadoSol.cveIdEstadoSolicitud", EstadoSolicitudEnum.ATENDIDA.getId()));
			DetachedCriteria queryPerInteresada = querySolicitud.createCriteria("ditPersonaInteresadaSols");
			queryPerInteresada.createAlias("ditPersona", "perInter");
			queryPerInteresada.createAlias("dicTipoPersonaInteresadaSol", "tipoPer");
			queryPerInteresada.add(Restrictions.eq("perInter.cveIdPersona",nss.getIdPersona() ));
			queryPerInteresada.add(Restrictions.eq("tipoPer.cveTipoInteresadaSol",TipoPersonaInteresadaSolEnum.ASEGURADO_PENSIONADO.getId() ));

			if(candidato) {
				queryGrupoCircunscripcion.add(Subqueries.propertyNotIn("der.cveIdPersona", queryCircuncripcion));
			}
			//De lo contrario nos traemos a los integrantes que tienen una circunscripcion 
			else {
				queryGrupoCircunscripcion.add(Subqueries.propertyIn("der.cveIdPersona", queryCircuncripcion));
			}

			encontrados = queryGrupoCircunscripcion.list();
			
		} catch (Exception e) {
			log.error("findGrupoFamiliarCircunscripcion", e);
			throw e;
		}
		
		resultado = grupoFamiliarParserServiceLocal.persistToModelList(encontrados);
		//Se complementan los datos de vigencia
		this.complementarVigencia(resultado);
		
		if(estadoD != null) {
			List<Long> estados = new ArrayList<Long>();
			estados.add(estadoD.getId());
			resultado = this.obtenerIntegrantesConEstado(resultado, estados);
		}
		
		return resultado;
	}


	/**
	 * 
	 */
	@Override
	public GrupoFamiliar getIntegranteGrupoFamiliarByEstados(
			Long idPersona, List<Long> estados, Long idAsignacionNss)
	throws DerechohabientesBusinessException,Exception {

		GrupoFamiliar integranteAux = null;
		GrupoFamiliar integrante = null;

		try {
			log.debug("Se buscara a la persona: " + idPersona + " con los estados: " + estados + " en el grupo familiar con idAsignacion: " + idAsignacionNss);
			integranteAux = wsClient.getGrupoFamiliarPorDerechohabiente(idAsignacionNss,idPersona.intValue());
		} catch(DerechohabientesWebSserviceException e) {
			lanzarExcepcionWsVigencia(e);
		}
		
		log.debug("El integrante es nulo ? : " + integranteAux != null);

		if(integranteAux != null) {

			if(estados != null) {
				if(!estados.contains(integranteAux.getEstadoDerechohabiente().getIdEstadoDerechohabiente())) {
					return null;
				}
			}
			
			log.debug("Se buscara el siguiente integrante con los datos del ws: \n  - IdAsignacionNss: " + integranteAux.getAsignacionNSS().getIdAsignacionNSS() +
					"\n  - IdPersona: " + integranteAux.getDerechohabiente().getIdPersona());
			//TODO se setea el id de la persona porque el ws esta retornando el idasignacionnss en el campo id persona
			integranteAux.getDerechohabiente().setIdPersona(idPersona);
			integrante = this.getIntegranteComplementado(integranteAux);
		}

		return integrante;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Boolean existeIntegranteGF(Long idPersona, List<Long> parentescos, List<Long> estados) throws DerechohabientesBusinessException,Exception {
		boolean existe = false;
		List<GrupoFamiliar> grupoFamiliar = new ArrayList<GrupoFamiliar>();
		try {

			Criteria queryIntegrante = this.getSession().createCriteria(DitGrupoFamiliar.class);

			if(parentescos != null && parentescos.size() > 0){
				queryIntegrante.createAlias("dicCalidadParentesco", "parentesco");
				queryIntegrante.add(Restrictions.in("parentesco.cveIdCalidadParentesco",parentescos));
			}

			queryIntegrante.createAlias("ditPersona", "der");
			queryIntegrante.add(Restrictions.eq("der.cveIdPersona", idPersona));

			List<DitGrupoFamiliar> integrantes = queryIntegrante.list();
			grupoFamiliar = grupoFamiliarParserServiceLocal.persistToModelNssParentescoList(integrantes);
			//Se complementa los datos de vigencia para poder verifficar si la persona esta en los estados
			this.complementarVigencia(grupoFamiliar);
			
			if(estados != null && estados.size() > 0){
				grupoFamiliar = this.obtenerIntegrantesConEstado(grupoFamiliar, estados);
			}
			
			if(grupoFamiliar.size() > 0){
				existe = true;
			}else{
				existe = false;
			}
		} catch (Exception e) {
			log.error("existeIntegranteGF", e);
			throw e;
		}


		return existe;
	}	

	@SuppressWarnings("unchecked")
	@Override
	public List <DitGrupoFamiliar> findGrupoFamiliarbyAsentamiento(Asentamiento asen) throws Exception{
		List <DitGrupoFamiliar> ditGrupoFamiliaresList=null;


		try {

			Criteria queryGrupoAsentamiento = this.getSession().createCriteria(DitGrupoFamiliar.class);
			Criteria queryPersonaf = queryGrupoAsentamiento.createCriteria("ditPersonafDom");
			Criteria queryDomGe = queryPersonaf.createCriteria("dgDomicilioGeografico");
			Criteria queryAsent = queryDomGe.createCriteria("dgAsentamiento");
			queryAsent.add(Restrictions.eq("id.cveAsen", asen.getClave()));
			queryAsent.add(Restrictions.eq("id.cveMun", asen.getLocalidad().getMunicipio().getClave()));
			queryAsent.add(Restrictions.eq("id.cveEnt", asen.getLocalidad().getMunicipio().getEntidadFederativa().getClave()));

			ditGrupoFamiliaresList = queryGrupoAsentamiento.list();

		} catch (Exception e) {
			log.error("findGrupoFamiliarbyAsentamiento", e);
			throw e;
		}

		return ditGrupoFamiliaresList;
	}

	@Override
	public CabezaGrupoFamiliar getCabezaGrupoFamiliarWS(long idAsignacionNSS)
			throws Exception {
		CabezaGrupoFamiliar cabezaGrupoFamiliar=  null;

		log.debug("Se buscara la cabeza de grupo familiar para el id asignacion: " + idAsignacionNSS + " solo en el ws");
		try {
			cabezaGrupoFamiliar = wsClient.obtieneInfoCabGpoFam(idAsignacionNSS, false);
			
			if(cabezaGrupoFamiliar.getPatronSujetoObligado() != null &&
					cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad() != null) {
				log.debug("la cabeza de grupo familiar si tiene modalidad regresadno del servicio ");
				if(cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad().equals(NUM_MODALIDAD_35) ||
						cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad().equals(NUM_MODALIDAD_00)){
					cabezaGrupoFamiliar.getPatronSujetoObligado().setCveIdSujetoObligado(sujetoObligadoServiceBusinessRemote.getCvePatronSujetoObligadoPorRP(cabezaGrupoFamiliar.getPatronSujetoObligado().getNumeroRegistroPatronal()));
				}
				
				if(!cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad().equals(NUM_MODALIDAD_37)){
				cabezaGrupoFamiliar.getPatronSujetoObligado().setModalidad(sujetoObligadoServiceBusinessRemote.getModalidad(
						cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad()));
				}
				/*if(!cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad().equals(NUM_MODALIDAD_34)){
					cabezaGrupoFamiliar.getPatronSujetoObligado().setModalidad(sujetoObligadoServiceBusinessRemote.getModalidad(
							cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad()));
					log.debug("consulta la informacion para la prorroga por obstetricos");
					}*/
			}
			
		} catch(DerechohabientesWebSserviceException e) {
			lanzarExcepcionWsVigencia(e);
		}
		log.debug("Se regresa al grupo familiar para continuar con la prorroga");
		return cabezaGrupoFamiliar;
	}
	
	@Override
	public CabezaGrupoFamiliarTE getCabezaGrupoFamiliarWSTE(long idAsignacionNSS)
			throws Exception {
		CabezaGrupoFamiliarTE cabezaGrupoFamiliar=  null;

		log.debug("Se buscara la cabeza de grupo familiar para el id asignacion: " + idAsignacionNSS + " solo en el ws");
		try {
			cabezaGrupoFamiliar = wsClient.obtieneInfoCabGpoFamTE(idAsignacionNSS);
			
			if(cabezaGrupoFamiliar.getPatronSujetoObligado() != null &&
					cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad() != null) {
				log.debug("la cabeza de grupo familiar si tiene modalidad regresadno del servicio ");
				if(cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad().equals(NUM_MODALIDAD_35) ||
						cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad().equals(NUM_MODALIDAD_00)){
					cabezaGrupoFamiliar.getPatronSujetoObligado().setCveIdSujetoObligado(sujetoObligadoServiceBusinessRemote.getCvePatronSujetoObligadoPorRP(cabezaGrupoFamiliar.getPatronSujetoObligado().getNumeroRegistroPatronal()));
				}
				
				if(!cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad().equals(NUM_MODALIDAD_37)){
				cabezaGrupoFamiliar.getPatronSujetoObligado().setModalidad(sujetoObligadoServiceBusinessRemote.getModalidad(
						cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad()));
				}
				/*if(!cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad().equals(NUM_MODALIDAD_34)){
					cabezaGrupoFamiliar.getPatronSujetoObligado().setModalidad(sujetoObligadoServiceBusinessRemote.getModalidad(
							cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad()));
					log.debug("consulta la informacion para la prorroga por obstetricos");
					}*/
			}
			
		} catch(DerechohabientesWebSserviceException e) {
			lanzarExcepcionWsVigencia(e);
		}
		log.debug("Se regresa al grupo familiar para continuar con la prorroga");
		return cabezaGrupoFamiliar;
	}

	@Override
	public CabezaGrupoFamiliar getCabezaGrupoFamiliar(long idAsignacionNSS)
	throws  Exception {
		CabezaGrupoFamiliar cabezaGrupoFamiliar=  null;
		
		try {
			cabezaGrupoFamiliar = this.getCabezaGrupoFamiliarWS(idAsignacionNSS);
		} catch(DerechohabientesWebSserviceException e) {
			lanzarExcepcionWsVigencia(e);
		}

		if(cabezaGrupoFamiliar != null) {
			log.debug("Se encontro la cabeza del grupo familiar y se procede a consultar estados en BDTU");
			EstadoDerechohabiente estado = estadoDerechohabienteEntityLocal.getEstadoDerechohabiente(cabezaGrupoFamiliar.getEstadoDerechohabiente().getIdEstadoDerechohabiente());
			SubEstadoDerechohabiente subEstado = estadoDerechohabienteEntityLocal.getSubEstadoDerechohabiente(cabezaGrupoFamiliar.getSubEstadoDerechohabiente().getIdSubEstadoDerechohabiente());
		
			if(cabezaGrupoFamiliar.getTipoMovtoAsegurado() !=null ){
				log.debug("Se encontro la cabeza del grupo familiar y se procede a consultar movimientos");
				TipoMovtoAsegurado tipoMovimiento = movimientoAseguradoDaoLocal.getTipoMovimiento(cabezaGrupoFamiliar.getTipoMovtoAsegurado().getIdTipoMvtoAsegurado());
				cabezaGrupoFamiliar.setTipoMovtoAsegurado(tipoMovimiento);
			}
			
			// --------------------------------------------------------------------
			// Si el IdTipoMvtoAsegurado no esta en la base se asignara null
			// --------------------------------------------------------------------
			if( cabezaGrupoFamiliar.getTipoMovtoAsegurado() == null ){
				log.debug("Si el IdTipoMvtoAsegurado no esta en la base se asignara null");
				TipoMovtoAsegurado tipoMovimiento = new TipoMovtoAsegurado();
				tipoMovimiento.setIdTipoMvtoAsegurado(0L);
				tipoMovimiento.setDesTipoMvtoAsegurado("NO DISPONIBLE");
				cabezaGrupoFamiliar.setTipoMovtoAsegurado(tipoMovimiento);
			}

			cabezaGrupoFamiliar.setEstadoDerechohabiente(estado);
			cabezaGrupoFamiliar.setSubEstadoDerechohabiente(subEstado);
		
			if(cabezaGrupoFamiliar.getPatronSujetoObligado() != null) {
				SujetoObligado so = new SujetoObligado();
				if(cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad() != null 
						&& (cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad().equals(NUM_MODALIDAD_35) ||
								cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad().equals(NUM_MODALIDAD_00)||
								cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad().equals(NUM_MODALIDAD_37) 
								//|| cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad().equals(NUM_MODALIDAD_34)
								)){
					//puede no existir el CveIdSujetoObligado en estas modalidades, por lo que si es nulo se setearan los valores  del numero de 
					// registro patronal provenientes del WS de cabeza de grupo familiar y asi obtener el digito verificador,INC296293
					if (cabezaGrupoFamiliar.getPatronSujetoObligado().getCveIdSujetoObligado()!= null ){
					so = sujetoObligadoServiceBusinessRemote.getDatosBasicosPatronPorIdPatronSujetoObligado(cabezaGrupoFamiliar.getPatronSujetoObligado().getCveIdSujetoObligado());
					}else{
						so.setNumeroRegistroPatronal(cabezaGrupoFamiliar.getPatronSujetoObligado().getNumeroRegistroPatronal());
					}
					
					try{
						so.setDigVerificador(DeltaUtils.generaDigitoVerificadorRP(so.getNumeroRegistroPatronal()+cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad())+"");
					}catch(Exception e){
						log.error("error al calcular el DV", e);
						so.setDigVerificador("");
					}
					
					so.setModalidad(cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad());
					
				}else if(cabezaGrupoFamiliar.getPatronSujetoObligado().getCveIdSujetoObligado() != null) {
					so = sujetoObligadoServiceBusinessRemote.getDatosBasicosPatronPorIdPatronGeneral(cabezaGrupoFamiliar.getPatronSujetoObligado().getCveIdSujetoObligado());
				}
				
				cabezaGrupoFamiliar.setPatronSujetoObligado(so);
			} 

		}
		
		return cabezaGrupoFamiliar;
	}
	
	@Override
	public CabezaGrupoFamiliarTE getCabezaGrupoFamiliarTE(long idAsignacionNSS)
	throws  Exception {
		CabezaGrupoFamiliarTE cabezaGrupoFamiliar=  null;
		
		try {
			cabezaGrupoFamiliar = this.getCabezaGrupoFamiliarWSTE(idAsignacionNSS);
		} catch(DerechohabientesWebSserviceException e) {
			lanzarExcepcionWsVigencia(e);
		}

		if(cabezaGrupoFamiliar != null) {
			log.debug("Se encontro la cabeza del grupo familiar y se procede a consultar estados en BDTU");
			EstadoDerechohabiente estado = estadoDerechohabienteEntityLocal.getEstadoDerechohabiente(cabezaGrupoFamiliar.getEstadoDerechohabiente().getIdEstadoDerechohabiente());
			SubEstadoDerechohabiente subEstado = estadoDerechohabienteEntityLocal.getSubEstadoDerechohabiente(cabezaGrupoFamiliar.getSubEstadoDerechohabiente().getIdSubEstadoDerechohabiente());
		
			if(cabezaGrupoFamiliar.getTipoMovtoAsegurado() !=null ){
				log.debug("Se encontro la cabeza del grupo familiar y se procede a consultar movimientos");
				TipoMovtoAsegurado tipoMovimiento = movimientoAseguradoDaoLocal.getTipoMovimiento(cabezaGrupoFamiliar.getTipoMovtoAsegurado().getIdTipoMvtoAsegurado());
				cabezaGrupoFamiliar.setTipoMovtoAsegurado(tipoMovimiento);
			}
			
			// --------------------------------------------------------------------
			// Si el IdTipoMvtoAsegurado no esta en la base se asignara null
			// --------------------------------------------------------------------
			if( cabezaGrupoFamiliar.getTipoMovtoAsegurado() == null ){
				log.debug("Si el IdTipoMvtoAsegurado no esta en la base se asignara null");
				TipoMovtoAsegurado tipoMovimiento = new TipoMovtoAsegurado();
				tipoMovimiento.setIdTipoMvtoAsegurado(0L);
				tipoMovimiento.setDesTipoMvtoAsegurado("NO DISPONIBLE");
				cabezaGrupoFamiliar.setTipoMovtoAsegurado(tipoMovimiento);
			}

			cabezaGrupoFamiliar.setEstadoDerechohabiente(estado);
			cabezaGrupoFamiliar.setSubEstadoDerechohabiente(subEstado);
		
			if(cabezaGrupoFamiliar.getPatronSujetoObligado() != null) {
				SujetoObligado so = new SujetoObligado();
				if(cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad() != null 
						&& (cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad().equals(NUM_MODALIDAD_35) ||
								cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad().equals(NUM_MODALIDAD_00)||
								cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad().equals(NUM_MODALIDAD_37) 
								//|| cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad().equals(NUM_MODALIDAD_34)
								)){
					//puede no existir el CveIdSujetoObligado en estas modalidades, por lo que si es nulo se setearan los valores  del numero de 
					// registro patronal provenientes del WS de cabeza de grupo familiar y asi obtener el digito verificador,INC296293
					if (cabezaGrupoFamiliar.getPatronSujetoObligado().getCveIdSujetoObligado()!= null ){
					so = sujetoObligadoServiceBusinessRemote.getDatosBasicosPatronPorIdPatronSujetoObligado(cabezaGrupoFamiliar.getPatronSujetoObligado().getCveIdSujetoObligado());
					}else{
						so.setNumeroRegistroPatronal(cabezaGrupoFamiliar.getPatronSujetoObligado().getNumeroRegistroPatronal());
					}
					
					try{
						so.setDigVerificador(DeltaUtils.generaDigitoVerificadorRP(so.getNumeroRegistroPatronal()+cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad())+"");
					}catch(Exception e){
						log.error("error al calcular el DV", e);
						so.setDigVerificador("");
					}
					
					so.setModalidad(cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad());
					
				}else if(cabezaGrupoFamiliar.getPatronSujetoObligado().getCveIdSujetoObligado() != null) {
					so = sujetoObligadoServiceBusinessRemote.getDatosBasicosPatronPorIdPatronGeneral(cabezaGrupoFamiliar.getPatronSujetoObligado().getCveIdSujetoObligado());
				}
				
				cabezaGrupoFamiliar.setPatronSujetoObligado(so);
			} 

		}
		
		return cabezaGrupoFamiliar;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<GrupoFamiliar> findIntegrantesEnUmf(Long idAsignacionNss,
			Long idUmf, Long idPersonaExcluir) throws Exception{
		List<DitGrupoFamiliar> ditGrupoFamiliar = null;
		List<GrupoFamiliar> grupoFamiliar = null;
	
		try {

			Criteria queryGrupoFamiliar = this.getSession().createCriteria(DitGrupoFamiliar.class);
			queryGrupoFamiliar.createAlias("ditAsignacionNss", "nss");
			queryGrupoFamiliar.createAlias("ditPersona", "der");
			queryGrupoFamiliar.add(Restrictions.eq("nss.cveIdAsignacionNss", idAsignacionNss));
			
			if(idPersonaExcluir != null) {
				queryGrupoFamiliar.add(Restrictions.ne("der.cveIdPersona", idPersonaExcluir));
			}
	
			Criteria queryUmfCTM = queryGrupoFamiliar.createCriteria("ditUmfConsTurnoMedico");
			Criteria queryUmfCT = queryUmfCTM.createCriteria("ditUmfConsultorioTurno");
			Criteria queryUmfC = queryUmfCT.createCriteria("dicConsultorioUmf");
			queryUmfC.createAlias("dicUmf", "umf");
			queryUmfC.add(Restrictions.eq("umf.cveIdUmf", idUmf));
			
			
			ditGrupoFamiliar = queryGrupoFamiliar.list();
			grupoFamiliar = grupoFamiliarParserServiceLocal.persistToModelList(ditGrupoFamiliar);
		
		} catch(DerechohabientesBusinessException e) {
			throw e;
		} catch (Exception e) {
			log.error("findGrupoFamiliarbyAsentamiento", e);
			throw e;
		}
		
		return grupoFamiliar;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GrupoFamiliar> getMedicoYFechaEnUmf(Long idAsignacionNss,
			Long idUmf, Boolean fechaNula, Integer maxResults) throws Exception{
		List<DitGrupoFamiliar> ditGrupoFamiliar = null;
		List<GrupoFamiliar> grupoFamiliar = null;
		Long[] tiposBaja = {TipoBajaDerechohabienteEnum.DEFUNCION.getId(),
				TipoBajaDerechohabienteEnum.DIVORCIO.getId(),
				TipoBajaDerechohabienteEnum.TERMINO_CONCUBINATO.getId(),
				TipoBajaDerechohabienteEnum.TERMINO_DE_UNION_CIVIL.getId()
				};
		log.debug("getMedicoYFechaEnUmf 1" + tiposBaja);
		try {

			Criteria queryGrupoFamiliar = this.getSession().createCriteria(DitGrupoFamiliar.class);
			queryGrupoFamiliar.createAlias("ditAsignacionNss", "nss");
			queryGrupoFamiliar.createAlias("ditPersona", "der");
			queryGrupoFamiliar.add(Restrictions.eq("nss.cveIdAsignacionNss", idAsignacionNss));
	
			Criteria queryUmfCTM = queryGrupoFamiliar.createCriteria("ditUmfConsTurnoMedico");
			Criteria queryUmfCT = queryUmfCTM.createCriteria("ditUmfConsultorioTurno");
			Criteria queryUmfC = queryUmfCT.createCriteria("dicConsultorioUmf");
			queryUmfC.createAlias("dicUmf", "umf");
			queryUmfC.add(Restrictions.eq("umf.cveIdUmf", idUmf));
			
			if(fechaNula != null) {
				if(fechaNula) {
					queryGrupoFamiliar.add(Restrictions.isNull("fecCambioTurnoConsultorio"));
					log.debug("fecCambioTurnoConsultorio 1" + tiposBaja);

				} else {
					 queryGrupoFamiliar.add(Restrictions.isNotNull("fecCambioTurnoConsultorio"));
				}
			}
			
			DetachedCriteria queryBaja = DetachedCriteria.forClass(DitBajaDerechohabiente.class);
			queryBaja.add(Restrictions.eq("cveIdAsignacionNSS", idAsignacionNss));
			queryBaja.createAlias("dicTipoBajaDerechohabiente", "tipoBaja");
			queryBaja.add(Restrictions.in("tipoBaja.cveIdTipoBajaDer", tiposBaja));
			queryBaja.add(Restrictions.eq("indBajaActiva",1L));
			queryBaja.setProjection(Projections.property("cveIdPersonaIntegrante"));
			
			queryGrupoFamiliar.add(Subqueries.propertyNotIn("der.cveIdPersona",queryBaja));
			
			if(maxResults != null) {
				queryGrupoFamiliar.setMaxResults(maxResults);
			}
			ditGrupoFamiliar = queryGrupoFamiliar.list();
			grupoFamiliar = grupoFamiliarParserServiceLocal.persistToModelList(ditGrupoFamiliar);
		
		} catch(DerechohabientesBusinessException e) {
			throw e;
		} catch (Exception e) {
			log.error("findGrupoFamiliarbyAsentamiento", e);
			throw e;
		}
		
		return grupoFamiliar;
	}

	/**
	 * Metodo para obtener a los integrantes deun grupo familiar que pertenecen a una umf en especifico y con un estado
	 * los parametros que recibe son los siguientes
	 * @param idAsignacionNss - Long el id del nss
	 * @param idUmf - Long: El id de la umf en la que se quiere buscar a los integrantes
	 * @param idEstado - Long: El id del estado que deben tener los integrantes si se pone null la consulta traera
	 * a los integrantes sin importar su estado
	 * @return List<GrupoFamiliar> Una lista con los integrantes encontrados en esa umf
	 * @throws Exception 
	 */
	@Override
	public List<GrupoFamiliar> findIntegrantesPorUmfEstado(
			Long idAsignacionNss, Long idUmf, List<Long> idEstado, Long idPersonaExcluir, List<Long> idsParentescos) throws Exception {
		List <GrupoFamiliar> grupoFamiliar=null;
		List<Long> idsPersonasExcluir = new ArrayList<Long>();
		
		if(idPersonaExcluir != null) {
			idsPersonasExcluir.add(idPersonaExcluir);
		}
		
		grupoFamiliar = this.findIntegrantesPorUmfEstadoIntegrantes(idAsignacionNss, idUmf, idEstado, idsPersonasExcluir, idsParentescos,true);

		return grupoFamiliar;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<GrupoFamiliar> findIntegrantesPorUmfEstadoIntegrantes(
			Long idAsignacionNss, Long idUmf, List<Long> idEstado, List<Long> idPersonasExcluir, List<Long> idsParentescos, Boolean conDomicilio) throws Exception {
		List <GrupoFamiliar> grupoFamiliar=null;
		conDomicilio = conDomicilio == null ? true : conDomicilio;
		try {
			List<DitGrupoFamiliar> ditGrupoFamiliar = null;

			Criteria queryGrupoFamiliar = this.getSession().createCriteria(DitGrupoFamiliar.class);
			queryGrupoFamiliar.createAlias("ditAsignacionNss", "nss");
			queryGrupoFamiliar.add(Restrictions.eq("nss.cveIdAsignacionNss", idAsignacionNss));

			if(!conDomicilio){
				queryGrupoFamiliar.add(Restrictions.isNull("ditPersonafDom"));
			}
			
			Criteria queryUmfCTM = queryGrupoFamiliar.createCriteria("ditUmfConsTurnoMedico");
			Criteria queryUmfCT = queryUmfCTM.createCriteria("ditUmfConsultorioTurno");
			Criteria queryUmfC = queryUmfCT.createCriteria("dicConsultorioUmf");
			queryUmfC.createAlias("dicUmf", "umf");
			queryUmfC.add(Restrictions.eq("umf.cveIdUmf", idUmf));

			if(idPersonasExcluir != null && !idPersonasExcluir.isEmpty()) {
				if(idPersonasExcluir.size() == 1) {
					queryGrupoFamiliar.createAlias("ditPersona", "der");
					queryGrupoFamiliar.add(Restrictions.ne("der.cveIdPersona", idPersonasExcluir.get(0)));
				} else if(idPersonasExcluir.size() > 1){
					queryGrupoFamiliar.createAlias("ditPersona", "der");
					queryGrupoFamiliar.add(Restrictions.not(Restrictions.in("der.cveIdPersona", idPersonasExcluir)));
				}
			}
			
			if(idsParentescos != null && !idsParentescos.isEmpty()){
				queryGrupoFamiliar.createAlias("dicCalidadParentesco", "parentesco");
				if(idsParentescos.size() == 1) {
					queryGrupoFamiliar.add(Restrictions.eq("parentesco.cveIdCalidadParentesco", idsParentescos.get(0)));
				} else {
					queryGrupoFamiliar.add(Restrictions.in("parentesco.cveIdCalidadParentesco", idsParentescos));
				}
			}
			
			ditGrupoFamiliar = queryGrupoFamiliar.list();

			grupoFamiliar = grupoFamiliarParserServiceLocal.persistToModelList(ditGrupoFamiliar);
			
			
			if(idEstado != null && !idEstado.isEmpty()) {
				this.complementarVigencia(grupoFamiliar);
				grupoFamiliar = this.obtenerIntegrantesConEstado(grupoFamiliar, idEstado);
			}

			
		} catch(DerechohabientesBusinessException e) {
			throw e;
		} catch (Exception e) {
			log.error("findGrupoFamiliarbyAsentamiento", e);
			throw e;
		}

		return grupoFamiliar;
	}

	/**
	 * Metodo para obtener a los integrantes de un grupo que tengan alguno de los estados pasados como lista
	 * @param grupo
	 * @param estados
	 * @return
	 */
	private List<GrupoFamiliar> obtenerIntegrantesConEstado(List<GrupoFamiliar> grupo, List<Long> estados) {

		List<GrupoFamiliar> aux = new ArrayList<GrupoFamiliar>();

		if(grupo != null && !grupo.isEmpty() && estados != null && !estados.isEmpty()) {

			for(GrupoFamiliar integrante: grupo){
				if(estados.contains(integrante.getEstadoDerechohabiente().getIdEstadoDerechohabiente())) {
					aux.add(integrante);
				}
			}

		}


		return aux;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GrupoFamiliar> findIntegrantesDuplicados(Long idPersona,
			List<Long> parentescos, Long idEstadoDerechohabiente) throws Exception {
		List<DitGrupoFamiliar> ditGrupoFamiliar = null;		
		List<GrupoFamiliar> encontrados = null;

		try {
			Criteria queryDuplicados = this.getSession().createCriteria(DitGrupoFamiliar.class);
			//Se quito la relacion contra dicEstado en BD
			//queryDuplicados.createAlias("dicEstadoDerechohabiente", "estado");
			queryDuplicados.createAlias("ditPersona", "der");
			queryDuplicados.createAlias("dicCalidadParentesco", "parentesco");
			//queryDuplicados.add(Restrictions.eq("estado.cveEstadoDerechohabiente", idEstadoDerechohabiente));
			queryDuplicados.add(Restrictions.eq("der.cveIdPersona", idPersona));
			queryDuplicados.add(Restrictions.in("parentesco.cveIdCalidadParentesco", parentescos));
			
			// Agregar condicion para `fecRegistroBaja is null`
			queryDuplicados.add(Restrictions.isNull("fecRegistroBaja"));

			ditGrupoFamiliar = queryDuplicados.list();
			encontrados = grupoFamiliarParserServiceLocal.persistToModelList(ditGrupoFamiliar);
			
			log.debug("los integrantes dao son: " + encontrados);

//			if(encontrados != null && !encontrados.isEmpty()) {
//				//Complementamos los estados de vigencia de lso encontrados
//				this.complementarVigencia(encontrados);
//				List<GrupoFamiliar> encontradosAux = new ArrayList<GrupoFamiliar>();
//				//Recorremos la lista y solo ponemos los que esten en el mismo estado que el indicado como parametro
//				for(GrupoFamiliar integrante: encontrados) {
//					if(integrante.getEstadoDerechohabiente() != null ) {
//						if(integrante.getEstadoDerechohabiente().getIdEstadoDerechohabiente().equals(idEstadoDerechohabiente)) {
//							encontradosAux.add(integrante);
//						}
//					}
//				}
//
//				encontrados = encontradosAux;
//				log.debug("los integrantes encontrados final son: " + encontrados);
//			}
		} catch (Exception e) {
			log.error("findIntegrantesDuplicados", e);
			throw e;
		}

		return encontrados;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Parentesco getParentescoIntegrante(String nss, Long idPersona) throws Exception{
		Parentesco encontrado = null;

		try {

			Criteria queryParentesco = this.getSession().createCriteria(DitGrupoFamiliar.class);
			List<DicCalidadParentesco> parentescos = null;

			queryParentesco.setProjection(Projections.property("dicCalidadParentesco"));
			queryParentesco.createAlias("ditAsignacionNss", "nss");
			queryParentesco.createAlias("ditPersona", "der");
			queryParentesco.add(Restrictions.eq("nss.numNss", nss));
			queryParentesco.add(Restrictions.eq("der.cveIdPersona", idPersona));

			parentescos = queryParentesco.list();

			if(parentescos != null && !parentescos.isEmpty())
				encontrado = ParentescoParser.persisToModel(parentescos.get(0));

		} catch (Exception e) {
			log.error("getParentescoIntegrante", e);
			throw e;
		}

		return encontrado;
	}

	/**
	 * Metodo para obtener los grupos familiares en los que se encuentra registrado una persona
	 * @param idPersona
	 * @param String nssActual
	 * @param Boolean incluirActual
	 * @return List<GrupoFamiliar>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<GrupoFamiliar> getGruposFamiliaresPorPersona(Long idPersona,
			String nssActual, Boolean incluirActual) throws DerechohabientesBusinessException, Exception {
		List<DitGrupoFamiliar> ditGrupoFamiliares = null;		
		List<GrupoFamiliar> encontrados = null;

		Criteria queryGrupo = this.getSession().createCriteria(DitGrupoFamiliar.class);
		queryGrupo.createAlias("ditPersona", "der");
		queryGrupo.add(Restrictions.eq("der.cveIdPersona", idPersona));

		//verificamos que el nss actual sea diferente de nulo
		if(nssActual != null) {
			//de ser asi verificamos si es indicador es diferente de nulo
			if(incluirActual != null && !incluirActual) {
				//y si es false, no incluiremos en la lista el grupo familiar con el nss paraso como parametro
				queryGrupo.createAlias("ditAsignacionNss", "nss");
				queryGrupo.add(Restrictions.ne("nss.numNss", nssActual));
			}
		}
		
		queryGrupo.addOrder(Order.asc("numCalidad"));

		//Obtenemos la lista de grupos familiares
		ditGrupoFamiliares = queryGrupo.list();

		//En caso de que la lista no sea nula parseamos la informacion
		if(!ditGrupoFamiliares.isEmpty()) {
			encontrados = grupoFamiliarParserServiceLocal.persistToModelList(ditGrupoFamiliares);
			//complementamos los datos de la vigencia invocando al webservice
			this.complementarVigencia(encontrados);
		}

		return encontrados;
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<GrupoFamiliar> findGrupoFamiliarPorParentescos(
			Long idAsignacionNss, List<Long> idParentescos,
			Boolean consultaVigencia) throws DerechohabientesBusinessException,
			Exception {
		consultaVigencia = consultaVigencia == null ? false : consultaVigencia;
		
		List<DitGrupoFamiliar> ditGrupoFamiliar = null;
		List<GrupoFamiliar> grupoFamiliar = null;
		
		Criteria queryGrupo = this.getSession().createCriteria(DitGrupoFamiliar.class);
		queryGrupo.createAlias("ditAsignacionNss", "nss");
		queryGrupo.add(Restrictions.eq("nss.cveIdAsignacionNss", idAsignacionNss));
		
		if(idParentescos != null && !idParentescos.isEmpty()) {
			queryGrupo.createAlias("dicCalidadParentesco", "parentesco");
			queryGrupo.add(Restrictions.in("parentesco.cveIdCalidadParentesco", idParentescos));
		}
		
		ditGrupoFamiliar = queryGrupo.list();
		
		if(ditGrupoFamiliar != null && !ditGrupoFamiliar.isEmpty()) {
			grupoFamiliar = grupoFamiliarParserServiceLocal.persistToModelList(ditGrupoFamiliar);
			
			if(consultaVigencia) {
				this.complementarVigencia(grupoFamiliar);
			}
		}
		
		return grupoFamiliar;
	}

	
	@Override
	public Boolean validarDomicilioYUmfIntegrante(Long idAsignacionNss, Long idPersona, Boolean validarUmf, Boolean validarDomicilio) {
		validarUmf = validarUmf == null ? false: validarUmf;
		validarDomicilio = validarDomicilio == null ? false : validarDomicilio;
		
		Criteria queryGrupo = this.getSession().createCriteria(DitGrupoFamiliar.class);
		queryGrupo.createAlias("ditAsignacionNss", "nss");
		queryGrupo.add(Restrictions.eq("nss.cveIdAsignacionNss", idAsignacionNss));
		queryGrupo.createAlias("ditPersona", "der");
		queryGrupo.add(Restrictions.eq("der.cveIdPersona", idPersona));
	
		
		if(validarUmf) {
			queryGrupo.add(Restrictions.isNotNull("ditUmfConsTurnoMedico"));
		}
		
		if(validarDomicilio) {
			queryGrupo.add(Restrictions.isNotNull("ditPersonafDom"));
		}
		
		DitGrupoFamiliar ditGrupo = (DitGrupoFamiliar)queryGrupo.uniqueResult();
		
		if(ditGrupo == null) {
			return false;
		} else {
			return true;
		}
	}

	private void lanzarExcepcionWsVigencia(DerechohabientesWebSserviceException e) throws DerechohabientesBusinessException{
		//  log.error("Ocurrio un error al consultar el ws de vigencia", e);
		String situacion = e.getMessage() != null ? e.getMessage() : "Ocurri&oacute; un error al consultar el ws de vigencia";
		DerechohabientesBusinessException.throwException(situacion, situacion );
	}
	
	@Override
	public boolean existeIntegranteGrupoFamiliarPorCurp(
			Long idAsignacionNss, String curp) throws DerechohabientesBusinessException {

		try {

			Criteria queryGrupoFamiliar = this.getSession().createCriteria(DitGrupoFamiliar.class);
			
			queryGrupoFamiliar.createAlias("ditAsignacionNss", "nss");
			queryGrupoFamiliar.add(Restrictions.eq("nss.cveIdAsignacionNss",idAsignacionNss));
			
			queryGrupoFamiliar.createAlias("ditPersona", "persona");
			queryGrupoFamiliar.add(Restrictions.eq("persona.curp", curp));
			
			@SuppressWarnings("unchecked")
			List<DitGrupoFamiliar> ditIntegrantes = queryGrupoFamiliar.list();
			
			if(!ditIntegrantes.isEmpty()) 
				return true;
			
		} catch(Exception e) {
			log.error("ocurrio un error desconocido", e);
			DerechohabientesBusinessException.throwException("Ocurrio un error desconocido", e.getMessage());
		}
	

		return false;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<GrupoFamiliar> findGrupoFamiliarByEstadoVigente(Long idAsignacionNss) throws DerechohabientesBusinessException,Exception {

		List<GrupoFamiliar> integrantesWs = null;

		try {
			log.debug("Se buscara a los integrantes con estados " + this.getEstadosVigenteDerechohabiente() + " en el grupo familiar con " +
					"idAsignacion " + idAsignacionNss);
			integrantesWs = wsClient.getGrupoFamiliarPorEstados(idAsignacionNss,this.getEstadosVigenteDerechohabiente());
		} catch(DerechohabientesWebSserviceException e) {
			lanzarExcepcionWsVigencia(e);
		}
		List<GrupoFamiliar> resultado =new ArrayList<GrupoFamiliar>();

		if(integrantesWs != null && !integrantesWs.isEmpty()) {
			resultado = this.getListaComplementada(integrantesWs);
		} 

		return resultado;
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Integer> getEstadosVigenteDerechohabiente() {
		List<Integer> estados = new ArrayList<Integer>();
		estados.add(Integer.valueOf(""+EstadoDerechohabienteEnum.VIGENTE.getId()));
		estados.add(Integer.valueOf(""+EstadoDerechohabienteEnum.CON_DERECHO.getId()));
		estados.add(Integer.valueOf(""+EstadoDerechohabienteEnum.CONSERVACION_DERECHOS.getId()));
		return estados;
	}
	
	
	
	@Override
	public List<GrupoFamiliar> getIntegranteEnLista(List<Long> idPersonas, Long idAsignacionNss) throws DerechohabientesBusinessException {

		List<GrupoFamiliar> integrantes = null;

		try {

			Criteria queryGrupoFamiliar = this.getSession().createCriteria(DitGrupoFamiliar.class);
			
			queryGrupoFamiliar.createAlias("ditPersona", "der");
			queryGrupoFamiliar.createAlias("ditAsignacionNss", "nss");
			queryGrupoFamiliar.add(Restrictions.eq("nss.cveIdAsignacionNss",idAsignacionNss));
			queryGrupoFamiliar.add(Restrictions.not(Restrictions.in("der.cveIdPersona", idPersonas)));
			
			@SuppressWarnings("unchecked")
			List<DitGrupoFamiliar> ditIntegrantes = queryGrupoFamiliar.list();
			
			if(ditIntegrantes != null && !ditIntegrantes.isEmpty()) {
				integrantes = grupoFamiliarParserServiceLocal.persistToModelList(ditIntegrantes);
			}
			
			
			
		} catch(Exception e) {
			log.error("ocurrio un error desconocido", e);
			DerechohabientesBusinessException.throwException("Ocurrio un error desconocido", e.getMessage());
		}
	

		return integrantes;
	}

	@Override
	public Boolean tienePatronImss(Long idAsignacionNSS)
			throws DerechohabientesBusinessException, Exception {
		Boolean patronImss = false;
		CabezaGrupoFamiliar cabeza = this.getCabezaGrupoFamiliarWS(idAsignacionNSS);
		
		if(cabeza != null) {
			patronImss = cabeza.getPatronImss() != null ? cabeza.getPatronImss().equals(1): false;
		}
		
		return patronImss;
	}

	@Override
	public GrupoFamiliar getIntegranteComplementadoCL3(Long idAsignacionNss, Long idPersona) throws Exception {
		GrupoFamiliar integrante = null;

		DitGrupoFamiliarCL3 ditGrupo = null;


			try {
				Criteria criteria = this.getSession().createCriteria(DitGrupoFamiliarCL3.class);
				
				criteria.createAlias("ditAsignacionNss", "nss");
				criteria.add(Restrictions.eq("nss.cveIdAsignacionNss", idAsignacionNss));
				criteria.createAlias("ditPersona", "der");
				criteria.add(Restrictions.eq("der.cveIdPersona", idPersona));

				ditGrupo = (DitGrupoFamiliarCL3) criteria.uniqueResult();

				if(ditGrupo != null) {
					integrante = grupoFamiliarParserServiceLocal.persistCL3ToModel(ditGrupo);
				} else {
					log.warn("Existen diferencias entre el ws de vigencia y la bdtu, no se encontro al integrante : " + idPersona + 
							" con id de NSS: " + idAsignacionNss + " en la BDTU",
							new DerechohabientesBusinessException("Existen diferencias entre el ws y BDTU", "Existen diferencias entre el ws y BDTU"));
				}
//				integrante.setAgregadoMedico(vigenciaWS.getAgregadoMedico(integrante.getAsignacionNSS().getNss(), integrante.getDerechohabiente().getIdPersona()));
			}catch (NoResultException e) {				
				integrante = null;
			}catch (Exception e) {				
				log.error("getIntegranteGrupoFamiliarByEstados", e);
				throw e;				
			}

		return integrante;
	}
	
	

	@Override
	public void updateIntegranteCL3(GrupoFamiliar integrante) throws DerechohabientesBusinessException,Exception {
		
		PersonaDomicilio personaDom = null;
		// ------------------------------------------------------------------------------------------------------
		// Se verifica si el integrante trae domicilio para guardar o actualizar la relacion personafdom
		// una vez que se guarda se pone en el integrante, en caso de no venir se actualiza lo demas
		// ------------------------------------------------------------------------------------------------------
		if(integrante.getDomicilio() != null) {
		
			personaDom = new PersonaDomicilio();
			
			personaDom.setDomicilio(integrante.getDomicilio());
			personaDom.setTipoDomicilio(new TipoDomicilio());
			personaDom.getTipoDomicilio().setClave(TipoDomicilioEnum.PARTICULAR.getCodigo().intValue());
			personaDom.setPersona(integrante.getDerechohabiente());
			
			personaDom = this.savePersonaDomicilio(personaDom);
			integrante.setCvePersonaDomicilio(personaDom.getCvePersonaDomicilio());
		}
		
		DitGrupoFamiliarCL3 ditGrupoFamiliarCL3 = grupoFamiliarParserServiceLocal.modelToPersistCL3(integrante);
			
		try {
			em.merge(ditGrupoFamiliarCL3);
			em.flush();
		} catch (Exception e) {
			log.error("updateIntegrante", e);
			throw e;
		}
			

	}

	@Override
	public AsignacionNSS getAsignacionNssCL3(String nss) throws DerechohabientesBusinessException, AsignacionNSSNoLocalizadoException, Exception {
		Criteria asignacionNssC = this.getSession().createCriteria(DitAsignacionNssCL3.class);
		DitAsignacionNssCL3 ditAsignacion = null;
		try {
			System.out.println(" ******************************************************** entra a la consulta de nss: " + nss + " ***************************************");
			asignacionNssC.add(Restrictions.eq("numNss", nss));
			ditAsignacion = (DitAsignacionNssCL3) asignacionNssC.uniqueResult();
			System.out.println(" ******************************************************** Sale de la consulta del nss ***************************************************");
			if(ditAsignacion == null){
				String strQueryAseguradoBaja = "select nss.num_nss "
						+ " from dit_asignacion_nss_cl3 nss where nss.num_nss = '" + nss + "' and nss.fec_registro_baja is not null";
				SQLQuery queryAsegurardoBaja = this.getSession().createSQLQuery(strQueryAseguradoBaja);
				List<Object[]> resultadoGrupo = (List<Object[]>)queryAsegurardoBaja.list();
				log.debug("Se verificara el grupo familiar del NSS: " + nss);
				if(!resultadoGrupo.isEmpty()) {
					throw new AsignacionNSSNoLocalizadoException("Asegurado con fecha de  baja", 1);
				}
				return null;
				
				
			}
		}catch (NoResultException e) { 
			return null;
		}catch (NonUniqueResultException e) {
			log.debug("getAsignacionNss regresa mas de un resultado", e);
			throw e;
		}catch (Exception e) {
			log.error("getAsignacionNss", e);
			throw e;
		}
		AsignacionNSS asignacionNss= AsignacionNSSParser.persisCL3ToModel(ditAsignacion);
		
		return asignacionNss;
	}

	@Override
	public Long getNumeroDeIntegrantesPorListParentescoCL3(Long idAsignacionNss, List<Long> idParentesco) {
		Long numeroIntegrantes = 0L;

		Criteria query = this.getSession().createCriteria(DitGrupoFamiliarCL3.class);
		query.setProjection(Projections.rowCount());
		query.createAlias("ditAsignacionNss", "nss");

		query.add(Restrictions.eq("nss.cveIdAsignacionNss", idAsignacionNss));

		if (idParentesco != null && !idParentesco.isEmpty()) {
			query.createAlias("dicCalidadParentesco", "parentesco");
			if (idParentesco.size() > 1) {
				query.add(Restrictions.in("parentesco.cveIdCalidadParentesco",idParentesco));
			} else {
				query.add(Restrictions.eq("parentesco.cveIdCalidadParentesco",idParentesco.get(0)));
			}
		}
		try {
			numeroIntegrantes = (Long) query.uniqueResult();
		} catch (Exception e) {
			log.error("No se pudo consultar la calidad mas alta", e);
		}
		return numeroIntegrantes;
	}
	
	
	@Override
	public GrupoFamiliar getIntegranteSinVigenciaCL3(Long idAsignacionNss, Long idPersona) throws Exception {
		GrupoFamiliar integrante = null;

		DitGrupoFamiliarCL3 ditGrupo = null;

			try {
				Criteria criteria = this.getSession().createCriteria(DitGrupoFamiliarCL3.class);
				
				log.debug("Se buscara el siguiente integrante en la BDTU CL3: \n  - IdAsignacionNss: " + idAsignacionNss +
						"\n  - IdPersona: " + idPersona);
				criteria.createAlias("ditAsignacionNss", "nss");
				criteria.add(Restrictions.eq("nss.cveIdAsignacionNss", idAsignacionNss));
				criteria.createAlias("ditPersona", "der");
				criteria.add(Restrictions.eq("der.cveIdPersona", idPersona));

				ditGrupo = (DitGrupoFamiliarCL3) criteria.uniqueResult();

				if(ditGrupo != null) {
					integrante = grupoFamiliarParserServiceLocal.persistCL3ToModel(ditGrupo);
					
				} 
			}catch (NoResultException e) {				
				integrante = null;
			}catch (Exception e) {				
				log.error("getIntegranteGrupoFamiliarByEstados", e);
				throw e;				
			}

		

		return integrante;
	}
	
	
	
	
	/**
	 * Metodo para obtener a un integrante de un grupo familiar pasandole como parametro
	 * el nss de la cabezadel grupo familiar y el id de la personaabuscar
	 */
	@Override
	public GrupoFamiliar getIntegranteGrupoFamiliarEstudiante(Long idAsignacionNss, Long idPersona) throws DerechohabientesBusinessException , Exception {

		CabezaGrupoFamiliar integranteAux = null;
		GrupoFamiliar integrante = null;

		log.debug("\n Se buscara al estudiante con los datos de la bdtu en el ws: \n - IdAsignacionNss:  " + idAsignacionNss + "\n  IdPersona: " + idPersona);
		try {
			integranteAux = this.getCabezaGrupoFamiliarWS(idAsignacionNss);
		} catch(DerechohabientesWebSserviceException e) {
			lanzarExcepcionWsVigencia(e);
		}

		if(integranteAux != null) {
			integrante = this.getIntegranteComplementadoCL3(idAsignacionNss, idPersona);
			integrante.setEstadoDerechohabiente(integranteAux.getEstadoDerechohabiente());
			integrante.setSubEstadoDerechohabiente(integranteAux.getSubEstadoDerechohabiente());
			integrante.setFechaInicioVigencia(integranteAux.getFechaInicioVigencia());
			integrante.setFechaFinVigencia(integranteAux.getFechaFinVigencia());
			
		}

		return integrante;
	}

	@Override
	public Boolean actualizarIDEE(Integer numeroFilas) {
		
		log.debug("Entro a hacer la consulta de los IDEES");
		Boolean seguirConsultando = true;
		
		Session session = this.getSession();
		String query="SELECT DISTINCT P.NOM_NOMBRE, P.NOM_PRIMER_APELLIDO, P.NOM_SEGUNDO_APELLIDO," +
				" P.FEC_NACIMIENTO, D.CVE_ID_PER_DERECHOHAB, MAX(SUBSTR(A.NUM_NSS, 0,10) || '|' || GF.NUM_CALIDAD)" +
				//" FROM DIT_PERSONA_DERECHOHABIENTE D, DIT_TRAMITE_PERSONA_FISICA PF," +
				" FROM DIT_PERSONA_DERECHOHABIENTE D, " +
				" DIT_ASIGNACION_NSS A, DIT_GRUPO_FAMILIAR GF, DIT_PERSONA P" +
				//" WHERE  D.CVE_ID_PERSONA = PF.CVE_ID_PERSONA" +
				" where P.CVE_ID_PERSONA = D.CVE_ID_PERSONA" +
				" AND A.CVE_ID_ASIGNACION_NSS = GF.CVE_ID_ASIGNACION_NSS" +
				" AND D.CVE_ID_PERSONA = GF.CVE_ID_PERSONA_INTEGRANTE" +
				" AND P.FEC_NACIMIENTO IS NOT NULL" +
				" AND LENGTH(D.CVE_EXPEDIENTE_ELECTRONICO) <= 17" +
				" AND ROWNUM <=" + numeroFilas + 
				" GROUP BY P.NOM_NOMBRE, P.NOM_PRIMER_APELLIDO, P.NOM_SEGUNDO_APELLIDO, P.FEC_NACIMIENTO, D.CVE_ID_PER_DERECHOHAB";
		
		SQLQuery queryIDEES = session.createSQLQuery(query);
		@SuppressWarnings("unchecked")
		List<Object[]> resultado = (List<Object[]>)queryIDEES.list();
		
		if(!resultado.isEmpty()) {
			seguirConsultando = true;
			log.debug("Se encontraron IDEES mal, y se procedera a ejecutarlos");
			Integer numeroRegistros = resultado.size();
			log.debug("Se encontraron " + numeroRegistros + " IDEES y se actualizaran");
			for(Object[] datosIDEE: resultado) {
				
				String nombre = (String) datosIDEE[0];
				String primerApellido = (String) datosIDEE[1];
				String segundoApellido = (String) datosIDEE[2];
				Date fechaNacimiento = (Date) datosIDEE[3];
				Long idPersonaDerechohabiente = ((BigDecimal) datosIDEE[4]).longValue();
				String guia = (String) datosIDEE[5];
				String[] nssCal = guia.split("|"); 
				
				String idee = ideeServiceLocal.generarIDEE(nssCal[0], new Integer(nssCal[1]), nombre, primerApellido, segundoApellido, fechaNacimiento, null, null);
				
				log.debug("Se actualiza el idee " + idee + " para la cve " + idPersonaDerechohabiente + " tiene 18 caracteres? " + (idee.length() == 18));
				try {
					String querySolicitud = "update DIT_PERSONA_DERECHOHABIENTE set CVE_EXPEDIENTE_ELECTRONICO  = '" + idee + "', FEC_REGISTRO_ACTUALIZADO = sysdate" ;
					querySolicitud += " where CVE_ID_PER_DERECHOHAB = " + idPersonaDerechohabiente;
					
					this.getSession().createSQLQuery(querySolicitud).executeUpdate();
				} catch(Exception e) {
					log.error("Ocurrio unu error al actualizar el idee: " + idee + " con la cve: " + idPersonaDerechohabiente);
				}
			}
			log.debug("Se encontraron " + numeroRegistros + " IDEES y se actualizaron");
		} else {
			log.debug("No se encontraron ideess");
			seguirConsultando = false;
		}
		
		return seguirConsultando;
		
	}
	
	

	@Override
	public Boolean actualizarIDEECL3(Integer numeroFilas) {
		log.debug("Entro a hacer la consulta de los IDEES");
		Boolean seguirConsultando = true;
		int errores= 0;
		
		Session session = this.getSession();
		String query="sELECT DISTINCT P.NOM_NOMBRE, P.NOM_PRIMER_APELLIDO, P.NOM_SEGUNDO_APELLIDO, "+
				"P.FEC_NACIMIENTO, p.NUM_ANIO_NAC_REG, p.NUM_MES_NAC_REG, p.cve_id_persona, MAX(SUBSTR(A.NUM_NSS, 0,10) || '|' || GF.NUM_CALIDAD) "+
				"FROM  DIT_ASIGNACION_NSS_CL3 A, DIT_GRUPO_FAMILIAR_CL3 GF, DIT_PERSONA P "+
				"where A.CVE_ID_ASIGNACION_NSS = GF.CVE_ID_ASIGNACION_NSS "+
				"AND P.CVE_ID_PERSONA = GF.CVE_ID_PERSONA_INTEGRANTE "+
				"and (p.FEC_NACIMIENTO is not null or (p.NUM_ANIO_NAC_REG is not null and p.NUM_MES_NAC_REG is not null)) "
				+ "and p.nom_nombre is not null "+
				"AND ROWNUM <= "+ numeroFilas + " " +
				"and not exists ("+
				"select rowid from dit_persona_derechohabiente pd "+
				"where pd.cve_id_persona = p.cve_id_persona "+
				") "+
				"GROUP BY P.NOM_NOMBRE, P.NOM_PRIMER_APELLIDO, P.NOM_SEGUNDO_APELLIDO, "+
				" P.FEC_NACIMIENTO,  p.NUM_ANIO_NAC_REG, p.NUM_MES_NAC_REG, p.cve_id_persona"
				+ " order by p.cve_id_persona asc";
		
		SQLQuery queryIDEES = session.createSQLQuery(query);
		@SuppressWarnings("unchecked")
		List<Object[]> resultado = (List<Object[]>)queryIDEES.list();
		
		if(!resultado.isEmpty()) {
			seguirConsultando = true;
			log.debug("Se encontraron IDEES mal, y se procedera a ejecutarlos");
			Integer numeroRegistros = resultado.size();
			log.debug("Se encontraron " + numeroRegistros + " IDEES y se actualizaran");
			for(Object[] datosIDEE: resultado) {
					
				log.debug("se creara el ide para la persona con id: " + datosIDEE[6] + ", fecha de nacimiento: " + datosIDEE[3] + ", mes de nacimiento " + datosIDEE[5] +
						" y anio " + datosIDEE[4]);
				String nombre = (String) datosIDEE[0];
				String primerApellido = (String) datosIDEE[1];
				String segundoApellido = (String) datosIDEE[2];
				Date fechaNacimiento = (Date) datosIDEE[3];
				Integer anioNacimiento = datosIDEE[4] != null ? ((BigDecimal)datosIDEE[4]).intValue() : null;
				Integer mesNacimiento =	datosIDEE[5] != null ? ((BigDecimal)datosIDEE[5]).intValue() : null;
				Long idPersona= ((BigDecimal) datosIDEE[6]).longValue();
				String guia = (String) datosIDEE[7];
				
				Boolean calcular = nombre != null && primerApellido != null && (fechaNacimiento != null || (anioNacimiento != null && mesNacimiento != null));
				
				try {
					if(calcular) {
						log.debug("se creara el idee para la person " + idPersona + " con fecha de nacimiento " + fechaNacimiento + 
								" com mes " + mesNacimiento +  " y anio " + anioNacimiento + " de nacimiento");
						String[] nssCal = guia.split("|"); 
						
						String idee = ideeServiceLocal.generarIDEE(nssCal[0], new Integer(nssCal[1]), nombre, primerApellido, segundoApellido, fechaNacimiento, mesNacimiento, anioNacimiento);
						
							String querySolicitud = "";
							log.debug("Se inserta el idee " + idee + " para la persona " + idPersona + " tiene 18 caracteres? " + (idee.length() == 18));
							//de lo contrario si no existe la relacion se creara
							querySolicitud += "insert into DIT_PERSONA_DERECHOHABIENTE (CVE_ID_PER_DERECHOHAB,FEC_REGISTRO_ALTA," +
									"CVE_EXPEDIENTE_ELECTRONICO,CVE_ID_PERSONA,IND_REG_ACTIVO) "+ 
									"values (SEQ_DITPERSONADERECHOHABIENTE.nextval,sysdate,'"+idee+"',"+idPersona+",1)";

							this.getSession().createSQLQuery(querySolicitud).executeUpdate();
						
					} else {
						log.error("No fue posible calcular el idee porque algun dato no viene idPersona " + idPersona + ", nombre: "
								+ nombre + ", primer apellido: " + primerApellido + ", fechaNacimiento: " + fechaNacimiento + ", mes NAcimiento: " + mesNacimiento +
								", anio Nacimiento: " + anioNacimiento);
						errores++;  
					}
				} catch(Exception e) {
					errores++;
					e.printStackTrace();
					log.error("Ocurrio unu error al actualizar el idee a la persona " + idPersona);
				}
			}
			log.debug("Se encontraron " + numeroRegistros + " IDEES y se actualizaron " + (numeroRegistros-errores));
		} else {
			log.debug("No se encontraron ideess");
			seguirConsultando = false;
		}
		
		return seguirConsultando;
	}
	
	
	
	@Override
	public Boolean actualizarIDEETablaAux(Integer numeroFilas) {
		log.debug("Entro a hacer la consulta de los IDEES");
		Boolean seguirConsultando = true;
		int errores= 0;
		
		Session session = this.getSession();
		String query ="select NOM_NOMBRE,NOM_PRIMER_APELLIDO,NOM_SEGUNDO_APELLIDO,FEC_NACIMIENTO,NUM_ANIO_NAC_REG,NUM_MES_NAC_REG,"+
		"CVE_ID_PERSONA,IDEE_ACTUAL, MAX(SUBSTR(NUM_NSS, 0,10) || '|' || NUM_CALIDAD) from AUX_PD_IDEEMENOR18_ACTUALIZA where "+
		"IDEE_NUEVO is null and rownum <= "+numeroFilas+" "+
		"group by NOM_NOMBRE,NOM_PRIMER_APELLIDO,NOM_SEGUNDO_APELLIDO,FEC_NACIMIENTO,NUM_ANIO_NAC_REG,NUM_MES_NAC_REG,"+
		"CVE_ID_PERSONA,IDEE_ACTUAL";

		SQLQuery queryIDEES = session.createSQLQuery(query);
		@SuppressWarnings("unchecked")
		List<Object[]> resultado = (List<Object[]>)queryIDEES.list();
		
		if(!resultado.isEmpty()) {
			seguirConsultando = true;
			Integer numeroRegistros = resultado.size();
			log.debug("Se encontraron " + numeroRegistros + " IDEES y se actualizaran");
			for(Object[] datosIDEE: resultado) {
					
				log.debug("se creara el ide para la persona con id: " + datosIDEE[6] + ", fecha de nacimiento: " + datosIDEE[3] + ", mes de nacimiento " + datosIDEE[5] +
						" y anio " + datosIDEE[4]);
				String nombre = (String) datosIDEE[0];
				String primerApellido = (String) datosIDEE[1];
				String segundoApellido = (String) datosIDEE[2];
				Date fechaNacimiento = (Date) datosIDEE[3];
				Integer anioNacimiento = datosIDEE[4] != null ? ((BigDecimal)datosIDEE[4]).intValue() : null;
				Integer mesNacimiento =	datosIDEE[5] != null ? ((BigDecimal)datosIDEE[5]).intValue() : null;
				Long idPersona= ((BigDecimal) datosIDEE[6]).longValue();
				String ideeActual = (String) datosIDEE[7];
				String guia = (String) datosIDEE[8];
				
				Boolean calcular = nombre != null && primerApellido != null && (fechaNacimiento != null || (anioNacimiento != null && mesNacimiento != null));
				
				try {
					if(calcular) {
						
						String[] nssCal = guia.split("|"); 
						
						String idee = ideeServiceLocal.generarIDEE(nssCal[0], new Integer(nssCal[1]), nombre, primerApellido, segundoApellido, fechaNacimiento, mesNacimiento, anioNacimiento);
						
						
							String querySolicitud = "";
							log.debug("Se actualiza el idee " + idee + ", con idee anterior "+ ideeActual+" para la persona " + idPersona + " tiene 18 caracteres? " + (idee.length() == 18));
							//de lo contrario si no existe la relacion se creara
							querySolicitud += "update AUX_PD_IDEEMENOR18_ACTUALIZA set IDEE_NUEVO='"+idee+"' where CVE_ID_PERSONA="+idPersona;

							this.getSession().createSQLQuery(querySolicitud).executeUpdate();
						
					} else {
						log.error("No fue posible calcular el idee porque algun dato no viene idPersona " + idPersona + ", nombre: "
								+ nombre + ", primer apellido: " + primerApellido + ", fechaNacimiento: " + fechaNacimiento + ", mes NAcimiento: " + mesNacimiento +
								", anio Nacimiento: " + anioNacimiento);
						errores++;  
					}
				} catch(Exception e) {
					errores++;
					e.printStackTrace();
					log.error("Ocurrio un error al actualizar el idee a la persona " + idPersona);
				}
			}
			log.debug("Se encontraron " + numeroRegistros + " IDEES y se actualizaron " + (numeroRegistros-errores));
		} else {
			log.debug("No se encontraron ideess");
			seguirConsultando = false;
		}
		
		return seguirConsultando;
	}

	@Override
	public GrupoFamiliar getIntegranteWs(Long idAsignacionNSS, Long idPersona) throws DerechohabientesBusinessException {
		GrupoFamiliar integranteWS = null ;

		//invocamos al cliente del webservice con los datos de integrante para obtener su vigencia
		try {
			log.debug("Se buscara el siguiente integrante con los datos que se obtuvieron de la bdtu: \n  - IdAsignacionNss: " + idAsignacionNSS +
					"\n  - IdPersona: " + idPersona);
			
			integranteWS = wsClient.getGrupoFamiliarPorDerechohabiente(idAsignacionNSS,idPersona.intValue());
		} catch( DerechohabientesWebSserviceException e) {
			lanzarExcepcionWsVigencia(e);
		}
		
		return integranteWS;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GrupoFamiliar> getIntegrantePorCurp(Long idAsignacionNSS, String curp)
			throws DerechohabientesBusinessException {
		List<GrupoFamiliar> grupo = null;
		List<DitGrupoFamiliar> integrantes = null;
		
		Criteria query = this.getSession().createCriteria(DitGrupoFamiliar.class);
		query.createAlias("ditAsignacionNss", "nss");
		query.add(Restrictions.eq("nss.cveIdAsignacionNss", idAsignacionNSS));
		query.createAlias("ditPersona", "der");
		query.add(Restrictions.eq("der.curp", curp));
		
		integrantes = query.list();
		
		if(integrantes != null && !integrantes.isEmpty()) {
			try {
				grupo = grupoFamiliarParserServiceLocal.persistToModelList(integrantes);
				this.complementarVigencia(grupo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return grupo;
	}

	@Override
	public Long generarArchivoConCurp(Integer numeroFilas, Long idMinimo) {
		
		if(idMinimo == null) {
			idMinimo = 0L;
		}
		
		List<String> curps = new ArrayList<String>();
		
		String query = "select nss.CVE_ID_ASIGNACION_NSS id_nss,nss.NUM_NSS n_ss, persona.CURP curp,nss.cve_id_persona idPersona from dit_asignacion_nss nss, dit_persona persona where "+
		"persona.cve_id_persona = nss.cve_id_persona and persona.curp is not null "+
		"and persona.curp != '000000000000000000'  and nss.fec_registro_alta > to_date('30/09/2014') and nss.cve_id_asignacion_nss "+
		"not in (select grupo.cve_id_asignacion_nss from dit_grupo_familiar grupo) " 
		+ " and rownum < " + numeroFilas +" and nss.cve_id_asignacion_nss > " + idMinimo + " order by nss.cve_id_asignacion_nss asc";
		
		SQLQuery queryIDEES = this.getSession().createSQLQuery(query);
		@SuppressWarnings("unchecked")
		List<Object[]> resultado = (List<Object[]>)queryIDEES.list();
		
		if(!resultado.isEmpty()) {
			
			for(Object[] datosCurp: resultado) {
				
				Long idAsignacionNSS = ((BigDecimal) datosCurp[0]).longValue();
				String nss = (String) datosCurp[1];
				String curp = (String) datosCurp[2];
				Long idPersona = ((BigDecimal) datosCurp[3]).longValue();
				String datos = "CURP: " + curp + ", idAsignacionNSS: " + idAsignacionNSS + ", NSS: " + nss + ", idPersona: " + idPersona;
				idMinimo = idAsignacionNSS;
				
				Fisica fisica = new Fisica();
				fisica.setCurp(curp);
				
				try {
					fisica = serviceBusinessRemote.validacionesNSS(fisica, true);
				} catch (PersonaConNSSException e) {
					fisica = e.getFisica();
				}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					continue;
				}
				
				if(fisica.getIdPersona() != null) {
					try {
						List<AsignacionNSS> listaNSS = this.getAsignacionNss(fisica.getIdPersona());
						if(listaNSS != null && listaNSS.size() == 1) {
							AsignacionNSS nss1 = listaNSS.get(0);
							try {
								CabezaGrupoFamiliar cabeza = this.getCabezaGrupoFamiliarWS(nss1.getIdAsignacionNSS());
								if(cabeza.getEstadoDerechohabiente().getIdEstadoDerechohabiente().equals(EstadoDerechohabienteEnum.VIGENTE.getId())) {
									try {
										wsClient.getIDsPatronesActivosPorAsignacionNSS(idAsignacionNSS);
										curps.add(datos);
									} catch(Exception e) {
										log.error("No se localizaron los patrones");
										e.printStackTrace();
									}
								} else if(cabeza.getEstadoDerechohabiente().getIdEstadoDerechohabiente().equals(EstadoDerechohabienteEnum.CON_DERECHO.getId()) ||
										cabeza.getEstadoDerechohabiente().getIdEstadoDerechohabiente().equals(EstadoDerechohabienteEnum.CONSERVACION_DERECHOS.getId())) {
										log.debug("la curp esta como pensionado");
										curps.add(datos);
								}else {
									log.debug("La curp " + curp + " no esta vigente");
									continue;
								}
							} catch (Exception e) {
								continue;
							}
						} else {
							log.debug("La curp " + curp + " tiene mas de un nss");
							continue;
						}
					} catch (DerechohabientesBusinessException e) {
						continue;
					}
				} else {
					continue;
				}
				
			}
		}
		
		FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter("c:/curps.txt",true);
            pw = new PrintWriter(fichero);
 
            for (String dato : curps) {
            	pw.println(dato);
            }
 
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
		log.debug("Me quede en el id " + idMinimo);
		
		return idMinimo;
	}
	  
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean validaPersonaExisteEnGruposFamiliaresPorParentesco(Long idPersona,	List<Long> parentesco)
			throws DerechohabientesBusinessException, Exception{
		List<DitGrupoFamiliar> ditGrupoFamiliares = null;	
		boolean existePersonaParentesco = false;
		Criteria queryGrupo = this.getSession().createCriteria(DitGrupoFamiliar.class);
		queryGrupo.createAlias("ditPersona", "der");
		queryGrupo.add(Restrictions.eq("der.cveIdPersona", idPersona));

		if (parentesco != null && !parentesco.isEmpty()) {
			queryGrupo.createAlias("dicCalidadParentesco", "parentesco");
			if (parentesco.size() > 1) {
				queryGrupo.add(Restrictions.in("parentesco.cveIdCalidadParentesco",parentesco));
			} else {
				queryGrupo.add(Restrictions.eq("parentesco.cveIdCalidadParentesco",parentesco.get(0)));
			}
		}
		
		
		//Obtenemos la lista de grupos familiares
		ditGrupoFamiliares = queryGrupo.list();

		//En caso de que la lista no sea nula parseamos la informacion
		if(ditGrupoFamiliares != null && !ditGrupoFamiliares.isEmpty()) {
			log.debug("Si econtro algun parentesco");
			return true;
		}

		return existePersonaParentesco;
	}

	@Override
	public void actualizarFechaBaja(Long idAsignacionNSS, Long idPersona,
			Date fechaBaja) throws Exception {
		
		DitGrupoFamiliarPK busqueda = new DitGrupoFamiliarPK();
		busqueda.setCveIdAsignacionNss(idAsignacionNSS);
		busqueda.setCveIdPersonaIntegrante(idPersona);
		
		DitGrupoFamiliar ditGrupoFamiliar = this.em.find(DitGrupoFamiliar.class, busqueda);
		
		if(ditGrupoFamiliar != null) {
			ditGrupoFamiliar.setFecRegistroActualizado(new Date());
			ditGrupoFamiliar.setFecRegistroBaja(fechaBaja);
		}
		
	}

	@Override
	public EstadoDerechohabiente getEstadoIntegrante(Long idAsignacionNSS,
			Long idPersona){
		GrupoFamiliar vigencia = new GrupoFamiliar(idAsignacionNSS, idPersona);
		EstadoDerechohabiente estado = null;
		try {
			vigencia = this.complementarVigencia(vigencia);
			estado = vigencia.getEstadoDerechohabiente();
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
		} catch (RollbackException e) {
			e.printStackTrace();
		}
		
		
		return estado;
	}
	
	
	/**
	  * Metodo que busca en bdtu el list de integrantes de grupos familiares por personas y list de parentescos, filtra la vigencia de almacenes
	  * del list de estados de vigencia, el cveIdNss es opcional si es diferente de nulo excluye de la busca las persona de ese grupo familiar
	  * @param lstIdPersona
	  * @param lstIdParentesco
	  * @param idEstadoDerechohabiente
	  * @param idAsignacionNssExcluir
	  * @return
	  * @throws Exception
	  */
	@SuppressWarnings("unchecked")
	@Override
	 public List<GrupoFamiliar> findIntegrantesByParentescoEstado(List<Long> lstIdPersona, 
			 List<Long> lstIdParentesco, List<Long> lstIdEstadoDerechohabiente, Long idAsignacionNssExcluir) throws Exception{
			
			if(lstIdPersona == null || lstIdPersona.isEmpty()) {
				throw new IllegalArgumentException("La lista de personas no puede ser nula");
			}
			
			List<DitGrupoFamiliar> encontrados = null;
			List<GrupoFamiliar> grupo = null;
			try {

				Criteria queryParentescoEstado = this.getSession().createCriteria(DitGrupoFamiliar.class);
				queryParentescoEstado.createAlias("ditPersona", "der");
				queryParentescoEstado.add(Restrictions.isNull("fecRegistroBaja"));
				//queryParentescoEstado.add(Restrictions.eq("estado.cveEstadoDerechohabiente", idEstadoDerechohabiente));
					//Dependiento del tama�o de la lista agregamos la restricion del parentesco
					if(lstIdPersona.size() > 1) {
						queryParentescoEstado.add(Restrictions.in("der.cveIdPersona", lstIdPersona));
					} else {
						queryParentescoEstado.add(Restrictions.eq("der.cveIdPersona", lstIdPersona.get(0)));
					}
				
				
				if(lstIdParentesco != null && !lstIdParentesco.isEmpty()) {
					//Creamos la relacion con parentesco
					queryParentescoEstado.createAlias("dicCalidadParentesco", "parentesco");
					//Dependiento del tama�o de la lista agregamos la restricion del parentesco
					if(lstIdParentesco.size() > 1) {
						queryParentescoEstado.add(Restrictions.in("parentesco.cveIdCalidadParentesco", lstIdParentesco));
					} else {
						queryParentescoEstado.add(Restrictions.eq("parentesco.cveIdCalidadParentesco", lstIdParentesco.get(0)));
					}
				}
			
				if(idAsignacionNssExcluir != null) {
					queryParentescoEstado.createAlias("ditAsignacionNss", "nss");
					queryParentescoEstado.add(Restrictions.ne("nss.cveIdAsignacionNss", idAsignacionNssExcluir));
				}
				
				encontrados = queryParentescoEstado.list();
				grupo = grupoFamiliarParserServiceLocal.persistToModelList(encontrados);
				//Se agrega complemento de vigencia con el webservice
				//se itera la lista temporalmente ya que no estan todos los integrantes
				
				this.complementarVigenciaConExcepcion(grupo);
				//Se verifica que que los integrantes tengan el estado proporcionado
				if(lstIdEstadoDerechohabiente != null &&  grupo != null && !grupo.isEmpty()) {
					grupo = this.obtenerIntegrantesConEstado(grupo, lstIdEstadoDerechohabiente);
				}
			
			} catch(DerechohabientesBusinessException e) {
				throw e;
			} catch (Exception e) {
				log.error("findIntegrantesByParentescoEstado", e);
				throw e;
			}

			return grupo;

		
	}
		

	@Override
	public void getNSSActivosConDomicilio() {
		
		
		//con esta consulta obtenemos a los nss con mas de 3 integrantes con domicilio
		String query = "select asignacion.NUM_NSS,asignacion.cve_id_asignacion_nss, count(*) as integrantes from dit_grupo_familiar grupo "+
		" inner join DIT_ASIGNACION_NSS asignacion on grupo.CVE_ID_ASIGNACION_NSS = asignacion.CVE_ID_ASIGNACION_NSS "+
		" where grupo.CVE_ID_PERSONAF_DOM is not null group by asignacion.num_nss,asignacion.cve_id_asignacion_nss having count(*) > 3";
		
		SQLQuery queryNSS = this.getSession().createSQLQuery(query);
		List<Object[]> resultado = (List<Object[]>)queryNSS.list();
		List<String> nssArchivo = new ArrayList<String>();
		if(!resultado.isEmpty()) {
			forGrupos: for(Object[] datosNSS: resultado) {
				String nss = (String) datosNSS[0];
				Long idAsignacionNSS = ((BigDecimal) datosNSS[1]).longValue();
				Long totalIntegrantes = ((BigDecimal) datosNSS[2]).longValue();
				
				String registroArchivo ="" + nss + ",";
				
				String queryG = "select cve_id_asignacion_nss, CVE_ID_PERSONA_INTEGRANTE, CVE_ID_CALIDAD_PARENTESCO, CVE_ID_PERSONAF_DOM"
						+ " from dit_grupo_familiar where cve_id_asignacion_nss = " + idAsignacionNSS + "";
				
				SQLQuery queryGrupo = this.getSession().createSQLQuery(queryG);
				List<Object[]> resultadoGrupo = (List<Object[]>)queryGrupo.list();
				log.debug("Se verificara el grupo familiar del NSS: " + nss);
				if(!resultadoGrupo.isEmpty()) {
					
					Long asegurado = 0L;
					
					boolean aseguradoConDomicilio = false;
					int beneficiariosActivos = 0;
					int beneficiariosNoVigentes = 0;
					int beneficiariosVigentesConDomicilio = 0;
					int beneficiariosSinVigencia = 0;
					Map<Long,Boolean> beneficiariosMap = new HashMap<Long, Boolean>();
					
					for(Object[] datosGrupo: resultadoGrupo) {
						
						Long idAsignacionNSSG = ((BigDecimal) datosGrupo[0]).longValue();
						Long idPersonaIntegrante = ((BigDecimal) datosGrupo[1]).longValue();
						Long idParentesco = datosGrupo[2] != null ? ((BigDecimal) datosGrupo[2]).longValue() : null;
						Long idDomicilio = datosGrupo[3] != null ?  ((BigDecimal) datosGrupo[3]).longValue() : null;
						
						if(idParentesco != null) {
							if(idParentesco.equals(5L) || idParentesco.equals(6L)) {
								 asegurado = idPersonaIntegrante;
								 aseguradoConDomicilio = idDomicilio != null;
							} else {
								beneficiariosMap.put(idPersonaIntegrante, idDomicilio != null);
							}
						}
						
					}
					
					
					try {
						GrupoFamiliar derechohabiente = wsClient.getGrupoFamiliarPorDerechohabiente(idAsignacionNSS, asegurado.intValue());
						if(derechohabiente.getEstadoDerechohabiente().getIdEstadoDerechohabiente().longValue() == EstadoDerechohabienteEnum.VIGENTE.getId()) {
							for(Long idBeneficiario : beneficiariosMap.keySet()) {
								try {
									GrupoFamiliar beneficiario = wsClient.getGrupoFamiliarPorDerechohabiente(idAsignacionNSS, idBeneficiario.intValue());
									if(beneficiario.getEstadoDerechohabiente().getIdEstadoDerechohabiente().longValue() == EstadoDerechohabienteEnum.VIGENTE.getId()) {
										beneficiariosActivos++;
										if(beneficiariosMap.get(idBeneficiario)) {
											beneficiariosVigentesConDomicilio++;
										}
									} else {
										beneficiariosNoVigentes++;
									}
								} catch (DerechohabientesWebSserviceException e) {
									beneficiariosSinVigencia++;
								}
							}
						} else {
							log.error("El NSS : " + nss + " no esta vigente");
							continue forGrupos;
						}
					} catch (DerechohabientesWebSserviceException e) {
						log.error("El NSS : " + nss + " no fue posible localizar su vigencia");
						continue forGrupos;
					}
					
					registroArchivo += "AseguradoConDomicilio("+aseguradoConDomicilio+"),";
					registroArchivo += "BeneficiariosVigentes("+beneficiariosActivos+"),";
					registroArchivo += "BeneficiariosVigentesConDomicilio("+beneficiariosVigentesConDomicilio+"),";
					registroArchivo += "BeneficiariosNoVigentes("+beneficiariosNoVigentes+"),";
					registroArchivo += "BeneficiariosSinVigencia("+beneficiariosSinVigencia+"),";
					registroArchivo += "TotalIntegrantes("+totalIntegrantes+")";
					
					nssArchivo.add(registroArchivo);
				}
				
			}
		}
		
		FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter("C:/Users/mario.teran/Desktop/nss.txt",true);
            pw = new PrintWriter(fichero);
 
            for (String dato : nssArchivo) {
            	log.debug(dato);
            	pw.println(dato);
            }
 
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
		
	}
	
		@Override
	public List<GrupoFamiliar> findDatosBasicosintegrantes(String numNss) throws DerechohabientesBusinessException, Exception{
		
		List<GrupoFamiliar> grupo = null;
		AsignacionNSS asignacion = this.getAsignacionNss(numNss);
		
		if(asignacion != null) {
			Session session = this.getSession();
			String query = "";
			
			query+="select nss.CVE_ID_ASIGNACION_NSS id_nss, nss.NUM_NSS num_nss, grupo.NUM_CALIDAD numCalidad, der.CVE_ID_PERSONA id_persona,"+//3
					"der.NOM_NOMBRE nombre, der.NOM_PRIMER_APELLIDO primerAp, der.NOM_SEGUNDO_APELLIDO segundoApe,der.FEC_NACIMIENTO fec_nac, "+//7
					"der.CURP , der.rfc , der.CVE_ENT,CATESTADO.NOM_ENT, sexo.CVE_ID_SEXO idSexo,sexo.DES_SEXO deSexo, der.CVE_ID_ESTADO_CIVIL,"+//14 Ultimo elemento
					"CATESTADOCIVIL.DES_ESTADO_CIVIL, parentesco.CVE_ID_CALIDAD_PARENTESCO idPar, parentesco.DES_PARENTESCO desPar,"+//17
					"umfconsturmed.CVE_ID_UMF_CONS_TURNO_MED,umf.CVE_ID_UMF, umf.NOM_CORTO,umf.NOM_UNIDAD,subdel.CVE_ID_SUBDELEGACION, subdel.DES_SUBDELEGACION,"+//23
					"del.CVE_ID_DELEGACION, del.DES_DELEG,turno.CVE_ID_TURNO, turno.DES_DESCRIPCION, umfcons.CVE_NUM_CONSULTORIO, umfcons.DES_CONSULTORIO,nss.fec_registro_baja, "
					+ "der.fec_defuncion  ";
			
			query+=	"from dit_asignacion_nss nss "+
					"inner join dit_grupo_familiar grupo on nss.CVE_ID_ASIGNACION_NSS = grupo.CVE_ID_ASIGNACION_NSS "+
					"inner join dit_persona der on grupo.CVE_ID_PERSONA_INTEGRANTE = der.CVE_ID_PERSONA "+
					"inner join DIC_CALIDAD_PARENTESCO parentesco on grupo.CVE_ID_CALIDAD_PARENTESCO = parentesco.CVE_ID_CALIDAD_PARENTESCO "+
					"inner join DIC_SEXO sexo on der.CVE_ID_SEXO = sexo.CVE_ID_SEXO "+
					"inner join DIT_UMF_CONS_TURNO_MEDICO umfconsturmed on grupo.CVE_ID_UMF_CONS_TURNO_MED = umfconsturmed.CVE_ID_UMF_CONS_TURNO_MED "+
					"inner join DIT_UMF_CONSULTORIO_TURNO umfconstur on umfconsturmed.CVE_ID_UMF_CONS_TURNO = umfconstur.CVE_ID_UMF_CONS_TURNO "+
					"inner join DIC_TURNO turno on umfconstur.CVE_ID_TURNO = turno.CVE_ID_TURNO "+
					"inner join DIC_CONSULTORIO_UMF umfcons on umfconstur.CVE_ID_UMF_CONSULTORIO = umfcons.CVE_ID_UMF_CONSULTORIO "+
					"inner join DIC_UMF umf on umfcons.CVE_ID_UMF = umf.CVE_ID_UMF "+
					"inner join DIC_SUBDELEGACION subdel on umf.CVE_ID_SUBDELEGACION = subdel.CVE_ID_SUBDELEGACION "+
					"inner join DIC_DELEGACION del on subdel.CVE_ID_DELEGACION = del.CVE_ID_DELEGACION "+
					"left join DG_CAT_ESTADO catEstado on der.CVE_ENT = CATESTADO.CVE_ENT "+
					"left join DIC_ESTADO_CIVIL catEstadoCivil on der.CVE_ID_ESTADO_CIVIL = CATESTADOCIVIL.CVE_ID_ESTADO_CIVIL ";
			
			query +="where grupo.FEC_REGISTRO_BAJA is null and nss.num_nss = '" + numNss+"'"+
					" order by grupo.NUM_CALIDAD asc";
			
			System.out.println("query->" + query);
			SQLQuery queryGrupos = session.createSQLQuery(query);
			@SuppressWarnings("unchecked")
			List<Object[]> resultado = (List<Object[]>)queryGrupos.list();
			log.info("Entrando DAO");
			
			GrupoFamiliar integranteN = null;
			grupo = new ArrayList<GrupoFamiliar>();
			
			if(!resultado.isEmpty()) {
				for(Object[] integrante: resultado) {
					
					integranteN = new GrupoFamiliar();
					integranteN.setAsignacionNSS(asignacion);
					integranteN.setDerechohabiente(new Derechohabiente());
					
					integranteN.setCalidad((BigDecimal) integrante[2]);
					integranteN.getDerechohabiente().setIdPersona(((BigDecimal)integrante[3]).longValue());
					integranteN.getDerechohabiente().setNombre((String) integrante[4]);
					integranteN.getDerechohabiente().setPrimerApellido((String) integrante[5]);
					integranteN.getDerechohabiente().setSegundoApellido((String) integrante[6]);
					integranteN.getDerechohabiente().setFechaNacimiento((Date) integrante[7]);
					
					integranteN.getDerechohabiente().setCurp((String) integrante[8]);
					integranteN.getDerechohabiente().setRfc((String) integrante[9]);
					
					integranteN.getDerechohabiente().setLugarNacimiento(new EntidadFederativa());
					integranteN.getDerechohabiente().getLugarNacimiento().setClave((String) integrante[10]);
					integranteN.getDerechohabiente().getLugarNacimiento().setNombre((String) integrante[11]);
					
					integranteN.getDerechohabiente().setSexo(new Sexo(integrante[12] != null ? ((BigDecimal)integrante[12]).intValue() : null));
					integranteN.getDerechohabiente().getSexo().setDescripcion((String) integrante[13]);
					
					integranteN.getDerechohabiente().setEstadoCivil(new EstadoCivil());
					integranteN.getDerechohabiente().getEstadoCivil().setIdEstadoCivil(integrante[14] != null ? ((BigDecimal)integrante[14]).intValue() : null);
					integranteN.getDerechohabiente().getEstadoCivil().setDescripcion((String) integrante[15]);
					
					integranteN.setParentesco(new Parentesco());
					integranteN.getParentesco().setIdParentesco(integrante[16] != null ? ((BigDecimal)integrante[16]).longValue() : null);
					integranteN.getParentesco().setDescripcion((String) integrante[17]);
					integranteN.setFechaRegistroBaja((Date) integrante[30]);
					integranteN.getDerechohabiente().setFechaDefuncion((Date) integrante[31]);
					
					MedicoEnTurno medicoEnTurno = new MedicoEnTurno();
					medicoEnTurno.setUnidadMedicaFamiliar(new UnidadMedicaFamiliar());
					medicoEnTurno.setIdMedicoContultorioTurno(integrante[18] != null ? ((BigDecimal)integrante[18]).longValue() : null);
					medicoEnTurno.getUnidadMedicaFamiliar().setIdUMF(integrante[19] != null ? ((BigDecimal)integrante[19]).longValue() : null);
					medicoEnTurno.getUnidadMedicaFamiliar().setNombreCorto((String) integrante[20]);
					medicoEnTurno.getUnidadMedicaFamiliar().setDescripcion((String) integrante[21]);
					medicoEnTurno.getUnidadMedicaFamiliar().setSubdelegacion(new Subdelegacion());
					medicoEnTurno.getUnidadMedicaFamiliar().getSubdelegacion().setId(integrante[22] != null ? ((BigDecimal)integrante[22]).longValue() : null);
					medicoEnTurno.getUnidadMedicaFamiliar().getSubdelegacion().setDescripcion((String)integrante[23]);
					medicoEnTurno.getUnidadMedicaFamiliar().getSubdelegacion().setDelegacion(new Delegacion());
					medicoEnTurno.getUnidadMedicaFamiliar().getSubdelegacion().getDelegacion().setId(integrante[24] != null ? ((BigDecimal)integrante[24]).longValue() : null);
					medicoEnTurno.getUnidadMedicaFamiliar().getSubdelegacion().getDelegacion().setDescripcion((String)integrante[25]);
					medicoEnTurno.setTurno(new Turno(integrante[26] != null ? ((BigDecimal)integrante[26]).longValue() : null, (String)integrante[27]));
					medicoEnTurno.setConsultorio(new Consultorio(integrante[28] != null ? ((BigDecimal)integrante[28]).longValue() : null, (String)integrante[29]));
					
					integranteN.setMedicoEnTurno(medicoEnTurno);

					grupo.add(integranteN);
				}
			} else {
				integranteN = new GrupoFamiliar();
				integranteN.setAsignacionNSS(asignacion);
				grupo.add(integranteN);
			}
		}
		return grupo;
	}
	
	private String getServicioMedicoDerechohabiente(Long cveIdAsignacionNSS, Long cveIdPesonaDerechohab){
		String strServicioMedicoSiNo = "";
		try{
			String strNSS = this.getAsignacionNssByIdAsignacionNss(cveIdAsignacionNSS).getNss();
			ComprobanteVigenciaDerechosDTO comprobante = vigenciaDerechosWS.getInfo(strNSS.substring(0, 10));
			if(comprobante != null){
				if(comprobante.getIdPersona().longValue() == cveIdPesonaDerechohab.longValue()){
					return strServicioMedicoSiNo = comprobante.getServicioMedico();
				}
				for(BeneficiarioDTO integrante: comprobante.getBeneficiarios()){
					if(integrante.getIdPersona().longValue() == cveIdPesonaDerechohab.longValue()){
						return strServicioMedicoSiNo = integrante.getServicioMedico();
					}
				}
			}
		
		}catch(Exception e){
			log.error("ERROR al consultar el WS completo para recuperar datos del beneficiario", e);
		}
		return strServicioMedicoSiNo;
		
	}
	
	@Override
	public String getServicioMedicoDerechohabiente(String strNSS, Long cveIdPesonaDerechohab){
		String strServicioMedicoSiNo = "";
		try{
		
			ComprobanteVigenciaDerechosDTO comprobante = vigenciaDerechosWS.getInfo(strNSS.substring(0, 10));
			if(comprobante != null){
				if(comprobante.getIdPersona().longValue() == cveIdPesonaDerechohab.longValue()){
					return strServicioMedicoSiNo = comprobante.getServicioMedico();
				}
				for(BeneficiarioDTO integrante: comprobante.getBeneficiarios()){
					if(integrante.getIdPersona().longValue() == cveIdPesonaDerechohab.longValue()){
						return strServicioMedicoSiNo = integrante.getServicioMedico();
					}
				}
			}
		
		}catch(Exception e){
			log.error("ERROR al consultar el WS completo para recuperar datos del beneficiario", e);
		}
		return strServicioMedicoSiNo;
		
	}
	

	/**
	  * Metodo que busca en bdtu si el pensionado tiene una pensión activa
	  * @param numNss = Numero de NSS
	  * @return false = no tiene pensión activa, true = tiene pensión activa
	  * @throws Exception
	  */	
	@Override
	public Boolean findPensionActiva(String numNss) throws Exception{
		
		Boolean respuesta = false;
		
			Session session = this.getSession();
			String query = "";
			
			query+="SELECT LLAV.REF_BUSCA, LLAV.CVE_ID_ASEGURADO_PENSION, APEN.CVE_ID_TIPO_PENSION "+
					"FROM MGPBDTU9X.DIT_LLAVE_ASEGURADO LLAV "+
					"LEFT OUTER JOIN MGPBDTU9X.DIT_ASEGURADO_PENSION APEN ON LLAV.CVE_ID_ASEGURADO_PENSION = APEN.CVE_ID_ASEGURADO_PENSION "+
					"LEFT OUTER JOIN MGPBDTU9X.DIC_TIPO_PENSION TP ON APEN.CVE_ID_TIPO_PENSION = TP.CVE_ID_TIPO_PENSION " +
					"WHERE LLAV.REF_BUSCA = '" + numNss + "'";
			
			System.out.println("query->" + query);
			SQLQuery queryPension = session.createSQLQuery(query);
			@SuppressWarnings("unchecked")
			List<Object[]> resultado = (List<Object[]>)queryPension.list();
			log.info("Entrando DAO");
		
			if(!resultado.isEmpty()) {
				for(Object[] pension: resultado) {
					if (pension[1] != null && pension[2] != null  ) {
						respuesta = true;
					}
				}
			}
		return respuesta;

	}

	@Override
	public DatosSalidaPaginador<ActualizaCorreo> paginarActualizaCorreo(
			DatosEntradaPaginador<ActualizaCorreoIn> entrada) throws Exception {
		
		log.info("llegando a la consulta BD paginarActualizaCorreo......." + entrada.getModelo());
		DatosSalidaPaginador<ActualizaCorreo> salida = new DatosSalidaPaginador<ActualizaCorreo>();
        StringBuilder strQueryRegistro = new StringBuilder();
        boolean nssCheck = false, fechaCheck = false, estadoCheck = false, folioCheck = false;
        Session session = this.getSession();
        
		
        strQueryRegistro.append("SELECT  S.CVE_ID_SOLICITUD     AS \"idSolicitud\", ");
        strQueryRegistro.append(" 		 T.CVE_ID_TRAMITE  	    AS \"idTramite\", ");
        strQueryRegistro.append(" 		 S.REF_FOLIO	   	    AS \"folio\", ");
        strQueryRegistro.append(" 		 TO_CHAR(S.FEC_REGISTRO_ALTA,'DD/MM/YYYY')    AS \"fechaRegistroAlta\", ");
        strQueryRegistro.append(" 		 NSS.NUM_NSS		    AS \"nss\", ");
        strQueryRegistro.append(" 		 P.NOM_NOMBRE		    AS \"nombre\", ");
        strQueryRegistro.append(" 		 P.NOM_PRIMER_APELLIDO  AS \"primerApellido\", ");
        strQueryRegistro.append(" 		 P.NOM_SEGUNDO_APELLIDO AS \"segundoApellido\", ");
        strQueryRegistro.append(" 		 P.CURP				    AS \"curp\", ");
        strQueryRegistro.append(" 		 S.CVE_ID_USUARIO       AS \"idUsuario\", ");
        strQueryRegistro.append(" 		 S.CVE_ID_SUBDELEGACION AS \"idSubdelegacion\", ");
        strQueryRegistro.append(" 		 ET.DES_ESTADO_SOLICITUD AS \"estadoTramite\" ");
        strQueryRegistro.append("FROM DIT_SOLICITUD S ");
        strQueryRegistro.append("INNER JOIN DIT_TRAMITE T				   ON T.CVE_ID_SOLICITUD = S.CVE_ID_SOLICITUD ");
        strQueryRegistro.append("INNER JOIN DIT_PERSONA_INTERESADA_SOL PIS ON PIS.CVE_ID_SOLICITUD = S.CVE_ID_SOLICITUD ");
        strQueryRegistro.append("INNER JOIN DIT_PERSONA P 				   ON P.CVE_ID_PERSONA = PIS.CVE_ID_PERSONA ");
        strQueryRegistro.append("INNER JOIN DIT_ASIGNACION_NSS NSS 		   ON NSS.CVE_ID_PERSONA = P.CVE_ID_PERSONA ");
        strQueryRegistro.append("INNER JOIN DIC_ESTADO_SOLICITUD ET		   ON ET.CVE_ID_ESTADO_SOLICITUD = S.CVE_ID_ESTADO_SOLICITUD ");
        strQueryRegistro.append("WHERE T.CVE_ID_TIPO_TRAMITE = 177 ");
        strQueryRegistro.append("AND S.CVE_ID_ORIGEN_SOLICITUD = 2 ");
        strQueryRegistro.append("AND S.CVE_ID_SUBDELEGACION = " + entrada.getModelo().getIdSubdelegacion());
        
        if (entrada.getModelo().getNss() != null && !entrada.getModelo().getNss().isEmpty()) {
        	log.debug("nss no esta vacio");
        	strQueryRegistro.append("AND NSS.NUM_NSS = " + new BigInteger(entrada.getModelo().getNss()));
        	nssCheck = true;
        }
        if (entrada.getModelo().getFecha() != null) {
        	log.debug("fecha no esta vacio");
            // Convertir la fecha a un formato de cadena SQL v�lido
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Puedes ajustar el formato seg�n tus necesidades
            String fechaSQL = sdf.format(entrada.getModelo().getFecha());
            strQueryRegistro.append("AND TRUNC(S.FEC_REGISTRO_ALTA) = TO_DATE('" + fechaSQL + "', 'YYYY-MM-DD')");
            fechaCheck = true;
        }
        if (entrada.getModelo().getEstado() != null && !entrada.getModelo().getEstado().isEmpty() ) {
        	log.debug("estado no esta vacio");
        	strQueryRegistro.append("AND S.CVE_ID_ESTADO_SOLICITUD = " + new BigInteger(entrada.getModelo().getEstado()));
        	estadoCheck = true;
        }
        if(entrada.getModelo().getNss().isEmpty()&& entrada.getModelo().getFecha()==null&&entrada.getModelo().getEstado().isEmpty()&& entrada.getModelo().getFolio().isEmpty()){
        	strQueryRegistro.append(" AND S.CVE_ID_ESTADO_SOLICITUD = 5 ");
//        	estadoCheck = false;
        }
        if (entrada.getModelo().getFolio() != null && !entrada.getModelo().getFolio().isEmpty()) {
        	log.debug("el folio no esta vacio");
        	strQueryRegistro.append("AND S.REF_FOLIO = " + new BigInteger(entrada.getModelo().getFolio()));
        	folioCheck = true;
        	
        }
        strQueryRegistro.append(" ORDER BY S.FEC_SOLICITUD ");
        
        log.info("el query a ejecutar de getConsultaObra es " + strQueryRegistro);

        SQLQuery sqlQuery = this.getSession().createSQLQuery(strQueryRegistro.toString());
        sqlQuery.addScalar("idSolicitud", StandardBasicTypes.INTEGER);
        sqlQuery.addScalar("idTramite", StandardBasicTypes.INTEGER);
        sqlQuery.addScalar("folio", StandardBasicTypes.STRING);
        sqlQuery.addScalar("fechaRegistroAlta", StandardBasicTypes.STRING);
        sqlQuery.addScalar("nss", StandardBasicTypes.STRING);
        sqlQuery.addScalar("nombre", StandardBasicTypes.STRING);
        sqlQuery.addScalar("primerApellido", StandardBasicTypes.STRING);
        sqlQuery.addScalar("segundoApellido", StandardBasicTypes.STRING);
        sqlQuery.addScalar("curp", StandardBasicTypes.STRING);
        sqlQuery.addScalar("estadoTramite", StandardBasicTypes.STRING);
//        sqlQuery.addScalar("idEstado", StandardBasicTypes.STRING);
//        sqlQuery.addScalar("idUsuario", StandardBasicTypes.INTEGER);
//        sqlQuery.addScalar("idSubdelegacion", StandardBasicTypes.INTEGER);
        
        System.out.println("Valor de el query:......... " +sqlQuery);
//        SQLQuery queryNSS = session.createSQLQuery(strQueryRegistro);
        List<ActualizaCorreo> listDatosCorreo = sqlQuery.setResultTransformer(Transformers.aliasToBean(ActualizaCorreo.class)).list();
        
        if (listDatosCorreo != null && !listDatosCorreo.isEmpty()) {
        	SQLQuery query = this.getSession().createSQLQuery("Select count(*) from (" + strQueryRegistro + ")");
        
//        if (nssCheck){
//        	log.debug("se seteara el parametro nss con el valor: " + Long.valueOf(entrada.getModelo().getNss()));
//            sqlQuery.setParameter("nss", Long.valueOf(entrada.getModelo().getNss()));
//        }
//        if (fechaCheck){
//        	log.debug("se seteara el parametro fecha con el valor: " + entrada.getModelo().getFecha());
//            sqlQuery.setParameter("fecha", entrada.getModelo().getFecha());
//        }
//        if (estadoCheck){
//        	log.debug("se seteara el parametro idEstado con el valor: " + Long.valueOf(entrada.getModelo().getEstado()));
//            sqlQuery.setParameter("idEstado", Long.valueOf(entrada.getModelo().getEstado()));
//        }
//        if (folioCheck){
//        	log.debug("se seteara el parametro nss con el valor: " + Long.valueOf(entrada.getModelo().getNss()));
//            sqlQuery.setParameter("folio", Long.valueOf(entrada.getModelo().getFolio()));
//        }
        
		
        BigDecimal count =(BigDecimal) query.uniqueResult();
		salida.setAaData(listDatosCorreo);
		salida.setiTotalDisplayRecords((int) (Math.ceil((count.intValue() / listDatosCorreo.size()))));
		salida.setiTotalRecords(count.intValue());
        }
        System.out.println("VALOR DE RETORNO...."  + salida.getAaData().get(0).toString() );
	return salida;
	}
        
}