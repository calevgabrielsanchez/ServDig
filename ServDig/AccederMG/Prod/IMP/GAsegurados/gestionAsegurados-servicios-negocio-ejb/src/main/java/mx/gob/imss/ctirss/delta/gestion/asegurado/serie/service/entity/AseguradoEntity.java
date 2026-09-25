package mx.gob.imss.ctirss.delta.gestion.asegurado.serie.service.entity;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.asegurado.serie.service.utility.AseguradoUtilityLocal;
import mx.gob.imss.ctirss.delta.model.asegurado.Asegurado;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.enums.OrigenConsultaNssEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Identificador;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo;
import mx.gob.imss.ctirss.delta.persistence.DitAsegurado;
import mx.gob.imss.ctirss.delta.persistence.DitAsignacionNss;

@Stateless
public class AseguradoEntity extends AbstractServiceEntity implements
		AseguradoEntityLocal{
	@EJB
	private transient AseguradoUtilityLocal aseguradoUtility;

	@Override
	public Asegurado altaAsegurado(Asegurado asegurado) {
		DitAsegurado ditAsegurado = aseguradoUtility
				.convertirModelToEntity(asegurado);

		if (ditAsegurado != null) {
			em.persist(ditAsegurado);
			em.flush();
		}

		return aseguradoUtility.convertirEntityToModel(ditAsegurado);
	}

	@Override
	public AsignacionNSS consultarAsignacionNSS(String nss) {

		//Correccion de fix - Se elimina validacion por indActivo
		BigDecimal indActivo = BigDecimal.ONE;

		Criteria criteria = this.getSession().createCriteria(
				DitAsignacionNss.class);

		criteria.add(Restrictions.eq("numNss", nss));
		criteria.add(Restrictions.isNull("fecRegistroBaja"));
		criteria.add(Restrictions.or(Restrictions.eq("indActivo", indActivo),
				Restrictions.isNull("indActivo")));
		DitAsignacionNss ditAsignacionNss = (DitAsignacionNss) criteria
				.uniqueResult();

		return aseguradoUtility
				.convertirAsignacionNSSEntityToModel(ditAsignacionNss);
	}
	
	@Override 
	public AsignacionNSS consultarAsignacionNSSEnBajaLogica(String numNss) {
		
		AsignacionNSS asignacion =null;
		Session session = this.getSession();
		String query ="select nss.CVE_ID_ASIGNACION_NSS idNss, nss.NUM_NSS numNss, persona.CVE_ID_PERSONA idPersona,"+
				"persona.NOM_NOMBRE nombre, persona.NOM_PRIMER_APELLIDO primerApe, persona.NOM_SEGUNDO_APELLIDO segundoApe,"+
				"persona.CURP from dit_asignacion_nss nss"+
				" inner join dit_persona persona on nss.cve_id_persona = persona.cve_id_persona"+
				" where nss.num_nss = '"+ numNss+"'"+" and nss.FEC_REGISTRO_BAJA is not null";
		
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
			String curp = (String)nss[6];

			asignacion = new AsignacionNSS();
			asignacion.setIdAsignacionNSS(idAsignacion);
			asignacion.setNss(numNSS);
			asignacion.setNssStr(numNSS);
			asignacion.setIdPersona(idPersonaIntegrante);
			asignacion.setNombre(nombre);
			asignacion.setPrimerApellido(primerApellido);
			asignacion.setSegundoApellido(segundoApellido);
			asignacion.setCurp(curp);

		}

		return asignacion;
	}

	@Override
	public Asegurado getAseguradoByAsegurado(Asegurado asegurado) {
		Criteria criteria = this.getSession()
				.createCriteria(DitAsegurado.class);

		criteria.createAlias("ditPatronSujetoObligado", "sujetoObligado");
		criteria.add(Restrictions.eq("sujetoObligado.cveIdPatronSujetoObligado",
				asegurado.getSujetoObligado().getCveIdSujetoObligado()));

		criteria.createAlias("ditAsignacionNss", "asignacionNss");
		criteria.add(Restrictions.eq("asignacionNss.cveIdAsignacionNss",
				asegurado.getAsignacionNSS().getIdAsignacionNSS()));

		DitAsegurado ditAsegurado = (DitAsegurado) criteria.uniqueResult();

		return aseguradoUtility.convertirEntityToModel(ditAsegurado);
	}
	
	@Override
	public DitAsignacionNss consultarDitAsignacionNss(String nss) {
		Criteria criteria = this.getSession().createCriteria(
				DitAsignacionNss.class);

		criteria.add(Restrictions.eq("numNss", nss));
		DitAsignacionNss ditAsignacionNss = (DitAsignacionNss) criteria
				.uniqueResult();

		return ditAsignacionNss;
	}

	@Override
	public AsignacionNSS getAsignacionNSSParaTarjeta(String numnss) {
		AsignacionNSS asignacion =null;
		Session session = this.getSession();
		String query ="select nss.CVE_ID_ASIGNACION_NSS idNss, nss.NUM_NSS numNss, persona.CVE_ID_PERSONA idPersona,"+
				"persona.NOM_NOMBRE nombre, persona.NOM_PRIMER_APELLIDO primerApe, persona.NOM_SEGUNDO_APELLIDO segundoApe,"+
				"persona.CURP from dit_asignacion_nss nss"+
				" inner join dit_persona persona on nss.cve_id_persona = persona.cve_id_persona"+
				" where nss.num_nss = '"+ numnss+"'";
		
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
			String curp = (String)nss[6];

			asignacion = new AsignacionNSS();
			asignacion.setIdAsignacionNSS(idAsignacion);
			asignacion.setNss(numNSS);
			asignacion.setNssStr(numNSS);
			asignacion.setIdPersona(idPersonaIntegrante);
			asignacion.setNombre(nombre);
			asignacion.setPrimerApellido(primerApellido);
			asignacion.setSegundoApellido(segundoApellido);
			asignacion.setCurp(curp);

		}

		return asignacion;
	}

	/**
	 * Metodo encargado de buscar el NSS en BDTU  en las tablas de legados
	 * @param nss String con el NSS a 11 posicioens
	 * @return Lista de personas con datos basicos y el NSS se seteara el origen donde se encontrÃ³ el NSS en los indicadores
	 */
	@Override
	public List<Fisica> getAseguradoByNSSLegados(String nss) {
		List<Fisica> lstFisicaNSS = new ArrayList<Fisica>();
		lstFisicaNSS.addAll(this.getAseguradoByNSSHistoricoCentral(nss));
		lstFisicaNSS.addAll(this.getAseguradoByNSSSindo(nss));
		lstFisicaNSS.addAll(this.getAseguradoByNSSCanase(nss));
		return lstFisicaNSS;
		
	
	}
	
	@SuppressWarnings("unchecked")
	private List<Fisica> getAseguradoByNSSHistoricoCentral( String nss){
		List<Fisica> lstFisicaNSS = new ArrayList<Fisica>();
	
		StringBuffer jpaQuery = new StringBuffer();
		Query query = null;
		List<Object[]> resultList = null;
		this.log.debug("Se busca NSS (" + nss
				+ ") en tabla D_SINDO_SSAS_ASEG_HIST_CENTRAL");
		
		jpaQuery.append("select  NSS, nvl(CURP,' ') as CURP,  nvl(AP_PATERNO,' ') as AP_PATERNO,   nvl(AP_MATERNO,' ') as AP_MATERNO, ");  
		jpaQuery.append("nvl(NOMBRE,' ')  as  NOMBRE,     nvl(DES_SEXO,' ') as  DES_SEXO,  nvl(NOM_ENT,' ') as NOM_ENT, 4 as CVE_CIZ, nvl(MES_NAC,0) as MES_NAC, NVL(LUGAR_NAC, 0 ) as LUGAR_NAC ");
		jpaQuery.append("from (select ac.*, sex.DES_SEXO  ");
		jpaQuery.append("from D_SINDO_SSAS_ASEG_HIST_CENTRAL ac, DIC_SEXO sex ");
		jpaQuery.append("where ac.nss = :nss ");
		jpaQuery.append("and ac.sexo= sex.cve_id_sexo(+) ");
		jpaQuery.append(")  asex, dg_cat_estado edo ");
		jpaQuery.append("where asex.lugar_nac =  to_number(edo.cve_ent(+)) ");
		
		this.log.debug("el query a ejecutar es [" + jpaQuery.toString() +"]");
		query = this.em.createNativeQuery(jpaQuery.toString());
		query.setParameter("nss", nss);
		 resultList = 	query.getResultList();
		 for (Object[] result : resultList) {
			 lstFisicaNSS.add(this.parseNSSFuentesHistoricostoFisica(result));
		   }
		return lstFisicaNSS;
	
	}
		
	@SuppressWarnings("unchecked")
    private List<Fisica> getAseguradoByNSSSindo( String nss){
                   List<Fisica> lstFisicaNSS = new ArrayList<Fisica>();
                  
                   StringBuffer jpaQuery = new StringBuffer();
                   Query query = null;
                   List<Object[]> resultList = null;
                   this.log.debug("Se busca NSS (" + nss
                                                   + ") en tabla ASEGURADOS_TEMP_UNIF");
                   jpaQuery.append("select  CVE_NSS, nvl(CURP,' ') as CURP,     nvl(AP_PATERNO,' ') as AP_PATERNO,   nvl(AP_MATERNO,' ') as AP_MATERNO, "); 
                   jpaQuery.append("nvl(NOMBRE,' ')  as  NOMBRE,     nvl(DES_SEXO,' ') as  DES_SEXO,  nvl(NOM_ENT,' ') as NOM_ENT,  CVE_CIZ, nvl(MES_NAC,0) as MES_NAC, NVL(LUGAR_NAC, 0 ) as LUGAR_NAC  ");
                   jpaQuery.append("from (select ac.*, sex.DES_SEXO  ");
                   jpaQuery.append("from ASEGURADOS_TEMP_UNIF ac, DIC_SEXO sex ");
                   jpaQuery.append("where ac.CVE_NSS = :nss ");
                   jpaQuery.append("and ac.sexo= sex.cve_id_sexo(+) ");
                   jpaQuery.append(")  asex, dg_cat_estado edo ");
                   jpaQuery.append("where asex.lugar_nac =  to_number(edo.cve_ent(+)) ");
                  
                   this.log.debug("el query a ejecutar es [" + jpaQuery.toString() +"]");
                   query = this.em.createNativeQuery(jpaQuery.toString());
                   query.setParameter("nss", nss);
                   resultList =        query.getResultList();
                   for (Object[] result : resultList) {
                                   lstFisicaNSS.add(this.parseNSSFuentesHistoricostoFisica(result));
                      }
                   return lstFisicaNSS;

    }
	
	@SuppressWarnings("unchecked")
	private List<Fisica> getAseguradoByNSSCanase( String nss){
		List<Fisica> lstFisicaNSS = new ArrayList<Fisica>();
		
		StringBuffer jpaQuery = new StringBuffer();
		Query query = null;
		List<Object[]> resultList = null;
		this.log.debug("Se busca NSS (" + nss
				+ ") en tabla D_CANASE_PPCANA01");
		jpaQuery.append("select  NSS, nvl(CURP,' ') as CURP,     nvl(AP_PATERNO,' ') as AP_PATERNO,   nvl(AP_MATERNO,' ') as AP_MATERNO, ");  
		jpaQuery.append("nvl(NOMBRE,' ')  as  NOMBRE,     nvl(DES_SEXO,' ') as  DES_SEXO,  nvl(NOM_ENT,' ') as NOM_ENT, 5 as CVE_CIZ, nvl(MES_NAC,0) as MES_NAC, NVL(LUGAR_NAC, 0 ) as LUGAR_NAC ");
		jpaQuery.append("from (select ac.*, sex.DES_SEXO  ");
		jpaQuery.append("from D_CANASE_PPCANA01 ac, DIC_SEXO sex ");
		jpaQuery.append("where ac.nss = :nss ");
		jpaQuery.append("and ac.sexo= sex.cve_id_sexo(+) ");
		jpaQuery.append(")  asex, dg_cat_estado edo ");
		jpaQuery.append("where asex.lugar_nac =  to_number(edo.cve_ent(+)) ");
		
		this.log.debug("el query a ejecutar es [" + jpaQuery.toString() +"]");
		query = this.em.createNativeQuery(jpaQuery.toString());
		query.setParameter("nss", nss);
		 resultList = 	query.getResultList();
		 for (Object[] result : resultList) {
			 lstFisicaNSS.add(this.parseNSSFuentesHistoricostoFisica(result));
		   }
		return lstFisicaNSS;

	}
	
	private Fisica parseNSSFuentesHistoricostoFisica(Object[] result){
		Fisica fisica = new Fisica();
		fisica.setNss(result[0].toString());
		fisica.setCurp(result[1].toString());
		fisica.setPrimerApellido(result[2].toString());
		fisica.setSegundoApellido(result[3].toString());
		fisica.setNombre(result[4].toString());
		fisica.setSexo(new Sexo(result[5].toString()));
		EntidadFederativa entidad = new EntidadFederativa();
		entidad.setNombre(result[6].toString());
		entidad.setClave(result[9].toString());
		fisica.setLugarNacimiento(entidad);
		//seteo del origen de la consulta
		
		OrigenConsultaNssEnum enumOrigen = null;
		if (result[7] instanceof Character) {
			enumOrigen= OrigenConsultaNssEnum.obtenerEnumById(Character.getNumericValue((Character)result[7]));
		}else{
			enumOrigen= OrigenConsultaNssEnum.obtenerEnumById(((BigDecimal)result[7]).intValue());
		}
		
		if(result[8] != null && result[8] instanceof Number && ((Number)result[8]).intValue()>0){
			Calendar fecha = Calendar.getInstance();
			fecha.set(9999, ((Number)result[8]).intValue()-1, 1);			
			fisica.setFechaNacimiento(fecha.getTime());
		}
		
		Identificador identificaOrigan = new Identificador();
		identificaOrigan.setIdIdentificador(enumOrigen.getClave());
		identificaOrigan.setIdentificadora(enumOrigen.getDescripcion());
		List<Identificador> lstIndentificadores = new ArrayList<Identificador>();
		lstIndentificadores.add(identificaOrigan);
		fisica.setIdentificadores(lstIndentificadores);
		return fisica;
	}
	
	@Override
    public String obtenerEstadoPendienteConfirmar(String nss){
                   
        String estadoPendiente = "";
                  
	   StringBuffer jpaQuery = new StringBuffer();
	   Query query = null;

	   this.log.debug("Se busca NSS (" + nss
									   + ") en tabla ASEGURADOS_TEMP_UNIF");
	   jpaQuery.append("select  ac.ID_CONFIRM ");
	   jpaQuery.append("from  ASEGURADOS_TEMP_UNIF ac ");
	   jpaQuery.append("where ac.CVE_NSS = :nss ");
	  
	   this.log.debug("el query a ejecutar es [" + jpaQuery.toString() +"]");
	   query = this.em.createNativeQuery(jpaQuery.toString());
	   query.setParameter("nss", nss);
	   try{
			estadoPendiente = Character.toString((Character)query.getSingleResult());
	   }catch(Exception ex){
		   this.log.error("Error al obtener pendiente de confirmar" +  ex.getMessage());
	   }
	   return estadoPendiente;

    }
	
}

