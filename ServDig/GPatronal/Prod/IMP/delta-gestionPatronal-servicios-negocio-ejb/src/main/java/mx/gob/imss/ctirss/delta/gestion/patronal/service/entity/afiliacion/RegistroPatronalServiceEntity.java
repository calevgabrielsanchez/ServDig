package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.afiliacion;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;




import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.SujetoObligadoUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.clasificacion.actividad.economica.ClasificacionActividadEconomicaServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.enums.CausaBajaPatronEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoContactoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RegistroPatronal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.persistence.DicFraccionClase;
import mx.gob.imss.ctirss.delta.persistence.DicModalidad;
import mx.gob.imss.ctirss.delta.persistence.DicMunicipioImss;
import mx.gob.imss.ctirss.delta.persistence.DicRegPatConvencional;
import mx.gob.imss.ctirss.delta.persistence.DicTipoPersona;
import mx.gob.imss.ctirss.delta.persistence.DitCentroTrabajoContacto;
import mx.gob.imss.ctirss.delta.persistence.DitClasificacion;
import mx.gob.imss.ctirss.delta.persistence.DitLlavePatron;
import mx.gob.imss.ctirss.delta.persistence.DitMunicipioPatSujOblig;
import mx.gob.imss.ctirss.delta.persistence.DitPatronGeneral;
import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DitDtsExtraPatron;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaFContactoFiscal;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaFisica;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaMContactoFiscal;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaMoral;
import mx.gob.imss.ctirss.delta.persistence.DitRegPatConvencional;
import mx.gob.imss.ctirss.delta.model.domicilio.MunicipioIMSS;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
/**
 * 
 * @author Hugo Martinez
 * @Projecto: delta-gestionPatronal-servicios-negocio-ejb
 * @Package: mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.afiliacion
 * @Archivo: RegistroPatronalServiceEntity.java
 * @Fecha: 10/01/2013 09:52:56
 */
@Stateless
public class RegistroPatronalServiceEntity extends AbstractServiceEntity
	implements RegistroPatronalServiceEntityLocal{

    private static final Logger log = LoggerFactory.getLogger(RegistroPatronalServiceEntity.class);
	
	@EJB
	private SujetoObligadoUtilityLocal sujetoObligadoUtility;
	@EJB
	private ClasificacionActividadEconomicaServiceUtilityLocal clasificacionUtility;
	
	@Override
	public DatosSalidaPaginador<SujetoObligado> consultarRegistrosPatronalesPersonaFisica(
			DatosEntradaPaginador<SujetoObligado> input) {
		StringBuffer bfrPersona = new StringBuffer();
		bfrPersona.append("select sujetoObligado from DitPatronSujetoObligado sujetoObligado ");
		bfrPersona.append("join sujetoObligado.ditPersonaFisica fisica ");
		bfrPersona.append("join fisica.ditPersona persona ");
		bfrPersona.append("where persona.rfc = '"+input.getModelo().getFisica().getRfc()+"' ");
		bfrPersona.append("and sujetoObligado.fecRegistroBaja is null");
        
		Query queryTotal = this.em.createQuery(bfrPersona.toString());
		@SuppressWarnings("unchecked")
		List<DitPatronSujetoObligado> ditSujetosObligados = queryTotal.getResultList();
		int totalResult=ditSujetosObligados.size();
		
		
		Query query = this.em.createQuery(bfrPersona.toString());
		query.setFirstResult(input.getiDisplayStart());
		query.setMaxResults(input.getiDisplayLength());
		
		
		
		@SuppressWarnings("unchecked")
		List<DitPatronSujetoObligado> ditSujetosObligadosDePagina = query.getResultList();
		List<SujetoObligado> sObligados = new ArrayList<SujetoObligado>();
		if (ditSujetosObligadosDePagina.size() > 0) {
			for (DitPatronSujetoObligado ditSujetoObligado : ditSujetosObligadosDePagina) {
				SujetoObligado sujetoObligado = sujetoObligadoUtility.convertirEntityToModel(
						ditSujetoObligado,
						TipoPersonaFiscal.FISICA);
				sObligados.add(sujetoObligado);
			}
		} else
			return null;
		
		DatosSalidaPaginador<SujetoObligado> output = new DatosSalidaPaginador<SujetoObligado>();
		output.setAaData(sObligados);
		output.setiTotalRecords(totalResult);
		output.setiTotalDisplayRecords(totalResult);
		
		return output;
	}

	@Override
	public DatosSalidaPaginador<SujetoObligado> consultarRegistrosPatronalesPersonaMoral(
			DatosEntradaPaginador<SujetoObligado> input) {
		StringBuffer bfrPersona = new StringBuffer();
		bfrPersona.append("select sujetoObligado from DitPatronSujetoObligado sujetoObligado ");
		bfrPersona.append("join sujetoObligado.ditPersonaMoral moral ");
		bfrPersona.append("where moral.rfc = '"+input.getModelo().getMoral().getRfc()+"' ");
		bfrPersona.append("and sujetoObligado.fecRegistroBaja is null");
        
		
		Query queryTotal = this.em.createQuery(bfrPersona.toString());
		@SuppressWarnings("unchecked")
		List<DitPatronSujetoObligado> ditSujetosObligados = queryTotal.getResultList();
		int totalResult=ditSujetosObligados.size();
		
		Query query = this.em.createQuery(bfrPersona.toString());
		query.setFirstResult(input.getiDisplayStart());
		query.setMaxResults(input.getiDisplayLength());
		
		@SuppressWarnings("unchecked")
		List<DitPatronSujetoObligado> ditSujetosObligadosDePagina = query.getResultList();
		List<SujetoObligado> sObligados = new ArrayList<SujetoObligado>();
		if (ditSujetosObligadosDePagina.size() > 0) {
			for (DitPatronSujetoObligado ditSujetoObligado : ditSujetosObligadosDePagina) {
				SujetoObligado sujetoObligado = sujetoObligadoUtility.convertirEntityToModel(
						ditSujetoObligado,
						TipoPersonaFiscal.MORAL);
				sObligados.add(sujetoObligado);
			}
		} else
			return null;
		
		
		DatosSalidaPaginador<SujetoObligado> output = new DatosSalidaPaginador<SujetoObligado>();
		output.setAaData(sObligados);
		output.setiTotalRecords(totalResult);
		output.setiTotalDisplayRecords(totalResult);
		
		return output;
	}

	@Override
	public void darDeBajaRPs(List<String> registrosPatronales) throws Exception {
		
		DitPatronGeneral ditPatronGeneral = null;
		
		for (String registroPatronal : registrosPatronales) {
			System.out.println("Registro Patronal a consultar: "+registroPatronal);
			System.err.println("Registro Patronal a consultar: "+registroPatronal);
			super.log.debug("Registro Patronal a consultar: "+registroPatronal);
			
			String regPatronal = registroPatronal.substring(0,8);
			log.debug("Registro Patronal a 8 posiciones: "+regPatronal);
			
			try{
				Criteria c = this.getSession().createCriteria(DitPatronGeneral.class);
				c.add(Restrictions.eq("regPatron", regPatronal));
				
				if(registroPatronal.length()>8){
					String modalidad = registroPatronal.substring(8,10);
					log.debug("modalidad: "+modalidad);
					c.createAlias("ditPatronSujetoObligado", "pso");
					c.createAlias("pso.dicModalidad", "modalidad");
					c.add(Restrictions.eq("modalidad.numModalidad", modalidad));
				}
				if(registroPatronal.length()==11){
					log.debug("Digito verificador: "+registroPatronal.length());
					c.add(Restrictions.eq("digVer", registroPatronal.substring(registroPatronal.length()-1)));
				}
				
				ditPatronGeneral = (DitPatronGeneral) c.uniqueResult();			
					
				if (ditPatronGeneral.getDitPatronSujetoObligado() != null){
					ditPatronGeneral.getDitPatronSujetoObligado().setFecRegistroBaja(new Date());
				}
			} catch (NoResultException nre) {
				super.log.debug("NO SE ENCONTRO NINGUN REGISTRO PATRONAL CON EL IDENTIFICADOR: " + registroPatronal);
			} catch(Exception e){
				super.log.debug("ERROR AL EJECUTAR CONSULTA CON EL IDENTIFICADOR " + registroPatronal + ", DETALLE: " + e.getMessage());
				throw e;
			}
		}
	}

    public List<SujetoObligado> listaRPRelacionadosPersonaFisica(String rp) {
        return listaRPRelacionados(rp, TipoPersonaFiscal.FISICA);
    }

    public List<SujetoObligado> listaRPRelacionadosPersonaMoral(String rp) {
        return listaRPRelacionados(rp, TipoPersonaFiscal.MORAL);
    }

	@Override
	public RegistroPatronal registrarRegistroPatronal(RegistroPatronal registroPatronal)
			throws Exception {
		DicModalidad dicModalidad = this.em.find(DicModalidad.class, registroPatronal.getModalidad().getIdModalidad());
		
		DitPatronSujetoObligado ditPatSujOblig = new DitPatronSujetoObligado();
		ditPatSujOblig.setDicModalidad(dicModalidad);
		if(registroPatronal.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA)){
			DitPersona individuo = this.em.find(DitPersona.class, registroPatronal.getIdPatron());
			DitPersonaFisica personaFisica = individuo.getDitPersonaFisicas().get(0);
			ditPatSujOblig.setDitPersonaFisica(personaFisica);
		}else{
			DitPersonaMoral personaMoral = this.em.find(DitPersonaMoral.class, registroPatronal.getIdPatron());
			ditPatSujOblig.setDitPersonaMoral(personaMoral);
		}
		ditPatSujOblig.setFecRegistroAlta(Calendar.getInstance().getTime());
		ditPatSujOblig.setFecRegistroActualizado(Calendar.getInstance().getTime());
		ditPatSujOblig.setNombreComercial(registroPatronal.getNombreComercial());
		ditPatSujOblig.setIndPatronConfirmado(1);//Se agrega para determinar que el RP esta asociado con una persona y no puede ser recuperado en una operación posterior
		this.em.persist(ditPatSujOblig);
		
		//Se inserta el patron general
		DitPatronGeneral patronGeneral = new DitPatronGeneral();
		patronGeneral.setDitPatronSujetoObligado(ditPatSujOblig);
		patronGeneral.setDigVer(registroPatronal.getDigVerificador());
		patronGeneral.setRegPatron(registroPatronal.getNoRegPatronal());
		patronGeneral.setFecRegistroActualizado(Calendar.getInstance().getTime());
		this.em.persist(patronGeneral);
		
		//Se insertan datos extra del patron
		log.error("Inicia persistencia de datos extra de patron");
		DitDtsExtraPatron ditDtsExtraPatron = new DitDtsExtraPatron();
		ditDtsExtraPatron.setCveIdPatronGeneral(patronGeneral.getCveIdPatronGeneral());
		ditDtsExtraPatron.setRefSecNotif("000");
		ditDtsExtraPatron.setFecMovto(registroPatronal.getFechaMovimiento());
		ditDtsExtraPatron.setCveTipoMovto(new BigDecimal(1));
		ditDtsExtraPatron.setFecIniHuelga(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/0001"));
		log.error("di"+ditDtsExtraPatron.getFecIniHuelga());
		//ditDtsExtraPatron.setFecIniHuelga(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/1800"));
		log.error("di"+ditDtsExtraPatron.getFecIniHuelga());
		ditDtsExtraPatron.setCanTrabVigEve(0);
		ditDtsExtraPatron.setCanTrabVigPer(0);
		ditDtsExtraPatron.setCanTrabVigCons(0);
		ditDtsExtraPatron.setCanTrabVigMexExtran(0);
		ditDtsExtraPatron.setRefAdicPens("000000");
		ditDtsExtraPatron.setFecRegistroAlta(new Date());
		this.em.persist(ditDtsExtraPatron);
		log.error("Finaliza persistencia datos extra patron");
		
		ditPatSujOblig.setDitPatronGenerals(new ArrayList<DitPatronGeneral>());
		ditPatSujOblig.getDitPatronGenerals().add(patronGeneral);
		
		//Se inserta la clasificacion
		DitClasificacion ditClasificacion = new DitClasificacion();
		DicFraccionClase dicFraccionClaseConsulta = obtenerFraccionActivaPorIdentificador(registroPatronal.getClasificacion().getFraccion().getId());
		DicFraccionClase dicFraccionClase = this.em.find(DicFraccionClase.class, dicFraccionClaseConsulta.getCveIdFraccionClase());
		ditClasificacion.setDitPatronSujetoObligado(ditPatSujOblig);
		ditClasificacion.setDicFraccionClase(dicFraccionClase);
		ditClasificacion.setFecRegistroAlta(Calendar.getInstance().getTime());
		ditClasificacion.setFecRegistroActualizado(Calendar.getInstance().getTime());
		ditClasificacion.setIndDistribucionEntrega(BigDecimal.ZERO);
		ditClasificacion.setIndEvaluada(BigDecimal.ZERO);		
		ditClasificacion.setIndTransporteAjeno(BigDecimal.ZERO);
		ditClasificacion.setIndTransportePropio(BigDecimal.ZERO);				
		ditClasificacion.setIndPrestaServicioPersonal(BigDecimal.ZERO);
		ditClasificacion.setIndRegPatClase(BigDecimal.ZERO);
		ditClasificacion.setIndServicioOtrasPersonas(BigDecimal.ZERO);
		ditClasificacion.setNumCentrosTraba(BigDecimal.ZERO);
		
		if(registroPatronal.getClasificacion()!=null){
			if(registroPatronal.getClasificacion().getIndPrestaServicioPersonal()!=null){
				ditClasificacion.setIndPrestaServicioPersonal(
					new BigDecimal(registroPatronal.getClasificacion().getIndPrestaServicioPersonal()));
			}				
			if(registroPatronal.getClasificacion().getIndRegPatClase()!=null 
					&& registroPatronal.getClasificacion().getIndRegPatClase().intValue() == 1){
				ditClasificacion.setIndRegPatClase(new BigDecimal(registroPatronal.getClasificacion().getIndRegPatClase()));
			}			
			if(registroPatronal.getClasificacion().getIndServiciosATerceros()!=null){
				ditClasificacion.setIndServicioOtrasPersonas(
					new BigDecimal(registroPatronal.getClasificacion().getIndServiciosATerceros()));
			}
			if(registroPatronal.getClasificacion().getNumCentrosTraba()!=null){
				ditClasificacion.setNumCentrosTraba(
					registroPatronal.getClasificacion().getNumCentrosTraba());
			}
			
		}		
		this.em.persist(ditClasificacion);
		
		try{
			if(registroPatronal.getMunicipioIMSS()!=null){
				Long idMunicipioIMSS = Long.valueOf(registroPatronal.getMunicipioIMSS().getIdMunicipio());
				DicMunicipioImss municipioIMSS = this.em.find(DicMunicipioImss.class, idMunicipioIMSS);
				log.error("municipio: "+municipioIMSS.getCveMunicipio());
				DitMunicipioPatSujOblig patronMunicipio= new DitMunicipioPatSujOblig();
				patronMunicipio.setCveIdPatronSujetoObligado(ditPatSujOblig.getCveIdPatronSujetoObligado());
				patronMunicipio.setFecRegistroAlta(Calendar.getInstance().getTime());
				patronMunicipio.setFecRegistroActualizado(Calendar.getInstance().getTime());
				patronMunicipio.setIndMigrMunic(BigDecimal.ZERO);
				patronMunicipio.setDicMunicipioImss(municipioIMSS);
				patronMunicipio.setDitPatronSujetoObligado(ditPatSujOblig);
				this.em.persist(patronMunicipio);
			}else{
				log.error("No existe municipiImss asignado...");
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error("Error al guardar municipio");
		}

		// Se inserta llaves del Patron/Sujeto Obligado
		DitLlavePatron ditLlavePatron = new DitLlavePatron();
		ditLlavePatron.setDitPatronSujetoObligado(ditPatSujOblig);
		ditLlavePatron.setDitPatronGeneral(patronGeneral);
		
		DicTipoPersona dicTipoPersona = new DicTipoPersona();
		if (registroPatronal.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA)) {
			DitPersona individuo = this.em.find(DitPersona.class, registroPatronal.getIdPatron());
			dicTipoPersona.setCveIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);

			ditLlavePatron.setDitPersonaFisica(ditPatSujOblig.getDitPersonaFisica());
			ditLlavePatron.setDitPersona(individuo);
		} else {
			dicTipoPersona.setCveIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);

			ditLlavePatron.setDitPersonaMoral(ditPatSujOblig.getDitPersonaMoral());
		}
		ditLlavePatron.setDicTipoPersona(dicTipoPersona);

		String numeroRPCompleto = registroPatronal.getNoRegPatronal();
		numeroRPCompleto += registroPatronal.getModalidad().getNumModalidad();
		ditLlavePatron.setRefBusca(numeroRPCompleto);

		this.em.persist(ditLlavePatron);

		registroPatronal.setId(patronGeneral.getCveIdPatronGeneral());
		registroPatronal.setIdRegistroPatronal(ditPatSujOblig.getCveIdPatronSujetoObligado());
		registroPatronal.getClasificacion().setId(ditClasificacion.getCveIdClasificacion());

		return registroPatronal;
	}

    private List<SujetoObligado> listaRPRelacionados(String rp, TipoPersonaFiscal tipoPersonaFiscal) {
        log.debug("rp: {} TipoPersonaFiscal: {}", rp, tipoPersonaFiscal);

        Session session = getSession();
        org.hibernate.Query query = session.createQuery(new StringBuilder("select ditSujetoObligado from\n")
                .append("DitPatronGeneral patronGeneral\n")
                .append("join patronGeneral.ditPatronGeneral ditPatronGeneral\n")
                .append("join ditPatronGeneral.ditPatronSujetoObligado ditSujetoObligado\n")
                .append("where patronGeneral.regPatron = :regPatron\n")
                .toString());

        query.setParameter("regPatron", rp);
        @SuppressWarnings("unchecked")
		List<DitPatronSujetoObligado> listDitPatronSO = query.list();
        log.debug("listDitPatronSO: {}", listDitPatronSO);
        List<SujetoObligado> sujetoObligadoList = new ArrayList<SujetoObligado>();
        for (DitPatronSujetoObligado ditPatronSujetoObligado:listDitPatronSO) {
            SujetoObligado so = sujetoObligadoUtility.convertirEntityToModel(ditPatronSujetoObligado, tipoPersonaFiscal);
            sujetoObligadoList.add(so);
        }
        return sujetoObligadoList;
    }
    
    
    private DicFraccionClase obtenerFraccionActivaPorIdentificador(Long cveIdFraccion){
		StringBuffer bfr = new StringBuffer();		
		
		bfr.append("select fc from DicFraccionClase fc where ");
		bfr.append(" fc.fecFin is null");
		bfr.append(" and fc.dicFraccion.cveIdFraccion = :idFraccion");
		
		Query query = this.em.createQuery(bfr.toString());
		query.setParameter("idFraccion", cveIdFraccion);
		DicFraccionClase fraccionDisponible = null;
		try{
			fraccionDisponible = (DicFraccionClase) query.getSingleResult();
		}catch(NoResultException nre){
			fraccionDisponible = null;
		}
		return fraccionDisponible;
	}
    
    @Override
    public Modalidad obtenerModalidadPorClave(String cveModalidad){
    	StringBuffer bfr = new StringBuffer();		
		
		bfr.append("select modalidad from DicModalidad modalidad where ");
		bfr.append(" modalidad.numModalidad = :numModalidad");
		
		Query query = this.em.createQuery(bfr.toString());
		query.setParameter("numModalidad", cveModalidad);
		DicModalidad dicModalidad = null;
		Modalidad modalidad=null;
		try{
			dicModalidad = (DicModalidad) query.getSingleResult();
			modalidad  = sujetoObligadoUtility.convertirEntityToModelModalidad(dicModalidad);
		}catch(NoResultException nre){
			modalidad = null;
		}
		return modalidad;    	
    }

	@Override
	public RegistroPatronal obtenerDatosGenerales(Long cveIdSujetoObligado) {
		
		DitPatronSujetoObligado pso = em.find(DitPatronSujetoObligado.class, cveIdSujetoObligado);
		DicModalidad dicModalidad=pso.getDicModalidad();
		DitPatronGeneral ditPatronGeneral = pso.getDitPatronGenerals().get(0);
		DitClasificacion ditClasificacion=pso.getDitClasificacions().get(0);
		
		Clasificacion clasificacion=clasificacionUtility.convertirEntityToModelClasificacion(ditClasificacion);
		Modalidad modalidad = sujetoObligadoUtility.convertirEntityToModelModalidad(dicModalidad);
		TipoPersona tipoPersona = new TipoPersona();
		RegistroPatronal registroPatronal = new RegistroPatronal();
		registroPatronal.setId(ditPatronGeneral.getCveIdPatronGeneral());
		registroPatronal.setNoRegPatronal(ditPatronGeneral.getRegPatron());
		registroPatronal.setDigVerificador(ditPatronGeneral.getDigVer());
		registroPatronal.setIdRegistroPatronal(cveIdSujetoObligado);
		
		if(pso.getDitPersonaFisica()!=null){
			tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
			registroPatronal.setTipoPersona(tipoPersona);
			registroPatronal.setIdPatron(pso.getDitPersonaFisica().getCveIdPersonaFisica());
		}else{
			tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
			registroPatronal.setTipoPersona(tipoPersona);
			registroPatronal.setIdPatron(pso.getDitPersonaMoral().getCveIdPersonaMoral());
		}
		registroPatronal.setClasificacion(clasificacion);
		registroPatronal.setModalidad(modalidad);
		return registroPatronal;
	}

	@Override
	public String obtenerCorreoPorIdentificador(Long id) {
		String correo=null;
		StringBuffer mailQuery = new StringBuffer();
		mailQuery.append("select contacto from DitCentroTrabajoContacto contacto ");
		mailQuery.append("join contacto.ditFormaContacto formaContacto ");
		mailQuery.append("join formaContacto.ditTipoContacto tipoContacto ");
		mailQuery.append("where tipoContacto.cveIdTipoContacto =:idTipoContacto and ");
		mailQuery.append("contacto.ditPatronSujetoObligado.cveIdPatronSujetoObligado =:idPatron ");
		Query query = this.em.createQuery(mailQuery.toString());
		query.setParameter("idTipoContacto", TipoContactoEnum.CORREO_ELECTRONICO.getCodigo().longValue());
		query.setParameter("idPatron", id);
		List<DitCentroTrabajoContacto> correos =  query.getResultList();
		if(correos!=null && correos.size()>0)
			correo = correos.get(0).getDitFormaContacto().getDesFormaContacto();
		return correo;
	}

	@Override
	public String obtenerCorreoPorNumeroDeRegistroPatronal(String nrp, String modalidad, String digitoVerificador) {
		String correo=null;
		StringBuffer mailQuery = new StringBuffer();
		mailQuery.append("select contacto from DitCentroTrabajoContacto contacto ");
		mailQuery.append("join contacto.ditFormaContacto formaContacto ");
		mailQuery.append("join contacto.ditPatronSujetoObligado patron ");
		mailQuery.append("join patron.ditPatronGenerals pg ");
		mailQuery.append("where contacto.ditFormaContacto.ditTipoContacto.cveIdTipoContacto =:idTipoContacto ");
		mailQuery.append("and pg.regPatron =:nrp ");
		mailQuery.append("and patron.dicModalidad.numModalidad =:numModalidad ");
		mailQuery.append("and pg.digVer =:digitoVerificador ");
		Query query = this.em.createQuery(mailQuery.toString());
		query.setParameter("idTipoContacto", TipoContactoEnum.CORREO_ELECTRONICO.getCodigo().longValue());
		query.setParameter("nrp", nrp);
		query.setParameter("numModalidad", modalidad);
		query.setParameter("digitoVerificador", digitoVerificador);
		
		List<DitCentroTrabajoContacto> correos =  query.getResultList();
		if(correos!=null && correos.size()>0)
			correo = correos.get(0).getDitFormaContacto().getDesFormaContacto();
		return correo;
	}

	@Override
	public String obtenerCorreoFiscal(Long idPersona, Long idTipoPersona) {
		String correo=null;
		StringBuffer mailQuery = new StringBuffer();
		
		if(idTipoPersona.equals(TipoPersona.TIPO_PERSONA_FISICA)){
			mailQuery.append("select contacto from DitPersonaFContactoFiscal contacto ");
			mailQuery.append("join contacto.ditFormaContacto formaContacto ");
			mailQuery.append("join contacto.ditPersonaFisica fisica ");
			mailQuery.append("where formaContacto.ditTipoContacto.cveIdTipoContacto =:idTipoContacto ");
			mailQuery.append("and fisica.ditPersona.cveIdPersona =:idPersona ");
			
			Query query = this.em.createQuery(mailQuery.toString());
			query.setParameter("idTipoContacto", TipoContactoEnum.CORREO_ELECTRONICO.getCodigo().longValue());
			query.setParameter("idPersona", idPersona);
			
			List<DitPersonaFContactoFiscal> correos =  query.getResultList();
			if(correos!=null && correos.size()>0)
				correo = correos.get(0).getDitFormaContacto().getDesFormaContacto();
			
		
		}else{
			mailQuery.append("select contacto from DitPersonaMContactoFiscal contacto ");
			mailQuery.append("join contacto.ditFormaContacto formaContacto ");
			mailQuery.append("where formaContacto.ditTipoContacto.cveIdTipoContacto =:idTipoContacto ");
			mailQuery.append("and contacto.ditPersonaMoral.cveIdPersonaMoral =:idPersona ");
			
			Query query = this.em.createQuery(mailQuery.toString());
			query.setParameter("idTipoContacto", TipoContactoEnum.CORREO_ELECTRONICO.getCodigo().longValue());
			query.setParameter("idPersona", idPersona);
			
			List<DitPersonaMContactoFiscal> correos =  query.getResultList();
			if(correos!=null && correos.size()>0)
				correo = correos.get(0).getDitFormaContacto().getDesFormaContacto();
			
		}
		
		return correo;
	}
    
	@SuppressWarnings("unchecked")
	@Override
	public SujetoObligado obtenerNrpModalidad34PersonaFisica(String rfc, String cveMunicipioImss) { 
		StringBuffer queryConsulta = new StringBuffer();
		
		queryConsulta.append("SELECT pso FROM DitMunicipioPatSujOblig pm ");
		queryConsulta.append(" JOIN pm.ditPatronSujetoObligado pso");
		queryConsulta.append(" JOIN pm.dicMunicipioImss mi ");
		queryConsulta.append(" JOIN pso.ditPersonaFisica fisica join fisica.ditPersona persona ");
		queryConsulta.append(" JOIN pso.dicModalidad modalidad ");
		queryConsulta.append(" JOIN pso.ditPatronGenerals pg ");
		queryConsulta.append(" left outer join pg.ditDtsExtraPatron dep ");
		
		queryConsulta.append(" WHERE persona.rfc = :rfcFiltro ");
		queryConsulta.append(" AND mi.cveMunicipio = :cveMunicipioImss ");
		queryConsulta.append(" AND modalidad.numModalidad = :modalidad34 ");
		queryConsulta.append(" AND pso.fecRegistroBaja is null ");
		queryConsulta.append(" AND ( dep.cveTipoMovto IS NULL OR dep.cveTipoMovto <> :claveBaja ) ");			
		
		Query query = this.em.createQuery(queryConsulta.toString());
		query.setParameter("rfcFiltro", rfc);
		query.setParameter("cveMunicipioImss", cveMunicipioImss);
		query.setParameter("modalidad34", 34);
		query.setParameter("claveBaja", new BigDecimal(CausaBajaPatronEnum.BAJA.getClave()));
				
		try{			
			List<DitPatronSujetoObligado> lstRP = query.getResultList();			
			if(!CollectionUtils.isEmpty(lstRP)){
				 DitPatronSujetoObligado entity = lstRP.get(0);
				 return sujetoObligadoUtility.convertirEntityToModel(entity, null);				 
			}
		}catch (NoResultException e) {
			return null;
		}
		
		return null;
	}

	@Override
	public String obtenerNrpConvencionalPorMunicipio(String cveMunImss,
			String numModalidad) throws GestionPatronalBusinessException{
		StringBuffer query = new StringBuffer();
		query.append("select nrpConvencional from DitRegPatConvencional nrpConvencional ");
		query.append("join nrpConvencional.ditPatronSujetoObligado patron ");
		query.append("join nrpConvencional.dicModalidad modalidad ");
		query.append("join nrpConvencional.dicMunicipioImss munImss ");
		query.append("where munImss.cveMunicipio =:cveMunImss ");
		query.append("and modalidad.numModalidad =:numModalidad ");
		query.append("and nrpConvencional.fecRegistroBaja is null ");
		
		Query emQuery = em.createQuery(query.toString());
		
		emQuery.setParameter("cveMunImss", cveMunImss);
		emQuery.setParameter("numModalidad", numModalidad);
		DitRegPatConvencional psoConvencional = null;
		try{
			psoConvencional = (DitRegPatConvencional)emQuery.getSingleResult();
		}catch(NoResultException nre){
			throw new GestionPatronalBusinessException("No se encontró ningún Registro patronal convencional para el municipio Imss "+cveMunImss);
		}
		StringBuffer nrp = new StringBuffer();
		nrp.append(psoConvencional.getDitPatronSujetoObligado().getDitPatronGenerals().get(0).getRegPatron()
				).append(psoConvencional.getDitPatronSujetoObligado().getDicModalidad().getNumModalidad()
				).append(psoConvencional.getDitPatronSujetoObligado().getDitPatronGenerals().get(0).getDigVer());
		
		return nrp.toString();
	}
    
	
	@Override
	public String obtenerNrpConvencionalPorSubdelegacionDelegacion(Long idDelegacion, Long idSubdelegacion,
			String numModalidad) throws GestionPatronalBusinessException{
		StringBuffer query = new StringBuffer();
		query.append("select nrpConvencional from DicRegPatConvencional nrpConvencional ");
		query.append("join nrpConvencional.ditPatronSujetoObligado patron ");
		query.append("join nrpConvencional.dicModalidad modalidad ");
		query.append("join nrpConvencional.dicSubdelegacion subdel ");
		query.append("join nrpConvencional.dicDelegacion del ");
		query.append("where subdel.cveIdSubdelegacion =:idSubdelegacion ");
		query.append("and del.cveIdDelegacion =:idDelegacion ");
		query.append("and modalidad.numModalidad =:numModalidad ");
		query.append("and nrpConvencional.fecRegistroBaja is null ");
		
		Query emQuery = em.createQuery(query.toString());
		

		emQuery.setParameter("idDelegacion", idDelegacion);
		emQuery.setParameter("idSubdelegacion", idSubdelegacion);
		emQuery.setParameter("numModalidad", numModalidad);
		DicRegPatConvencional psoConvencional = null;
		try{
			psoConvencional = (DicRegPatConvencional)emQuery.getSingleResult();
		}catch(NoResultException nre){
			throw new GestionPatronalBusinessException("No se encontró ningún Registro patronal convencional para la subdelegacion "+idSubdelegacion);
		}
		StringBuffer nrp = new StringBuffer();
		nrp.append(psoConvencional.getDitPatronSujetoObligado().getDitPatronGenerals().get(0).getRegPatron()
				).append(psoConvencional.getDitPatronSujetoObligado().getDicModalidad().getNumModalidad()
				).append(psoConvencional.getDitPatronSujetoObligado().getDitPatronGenerals().get(0).getDigVer());
		
		return nrp.toString();
	}
	
	@Override
    public Date obtenerEstadoHuelga(String regPatronal){


            log.debug("Busca estado de Huelga para el regPatronal:" + regPatronal);
            Criteria criteria = this.getSession().createCriteria(DitPatronGeneral.class);

            criteria.add(Restrictions.eq("regPatron", regPatronal));

            DitPatronGeneral resp = (DitPatronGeneral) criteria.uniqueResult();
            Criteria extraPatron = this.getSession().createCriteria(DitDtsExtraPatron.class);
            extraPatron.add(Restrictions.eq("cveIdPatronGeneral", resp.getCveIdPatronGeneral()));

            log.debug("Despues de la Consulta:" + resp);
           
                Date fechaRespuesta = resp.getDitDtsExtraPatron().getFecIniHuelga();
                log.debug(
                        "Fecha de huelga encontrada!: Fin Busca estado de Huelga para el regPatronal:" + regPatronal);
                return fechaRespuesta;
            
        
    }
	
	@Override
	public List<RegistroPatronal> obtenerPatronesConMunicipiosImss(List<String> registrosPatronales){
		
		List<RegistroPatronal> registrosConMunicipio = new ArrayList<RegistroPatronal>();
		StringBuffer query = new StringBuffer();
		query.append("select patronGral.regPatron, patronGral.ditPatronSujetoObligado.ditMunicipioPatSujOblig.dicMunicipioImss.dgCatEstado.nomEnt from DitPatronGeneral patronGral ");
		query.append("where patronGral.regPatron in (:registrosPatronales) ");
		
		Query emQuery = em.createQuery(query.toString());
		emQuery.setParameter("registrosPatronales", registrosPatronales);

		
			final List<Object[]> list = emQuery.getResultList();
			RegistroPatronal registroPatronal = null;
			MunicipioIMSS municipioIMSS = null;
			for (Object[] row : list) {
				registroPatronal = new RegistroPatronal();
				registroPatronal.setNoRegPatronal((String)row[0]);
				municipioIMSS = new MunicipioIMSS();
				municipioIMSS.setDescEntidad((String)row[1]);
				registroPatronal.setMunicipioIMSS(municipioIMSS);
				
				registrosConMunicipio.add(registroPatronal);
			}
		
		
		return registrosConMunicipio;
	}
}
