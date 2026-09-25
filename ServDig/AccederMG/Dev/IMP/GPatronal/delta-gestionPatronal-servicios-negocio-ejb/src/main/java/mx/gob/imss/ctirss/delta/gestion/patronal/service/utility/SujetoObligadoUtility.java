package mx.gob.imss.ctirss.delta.gestion.patronal.service.utility;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.SujetoObligadoServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.clasificacion.actividad.economica.ClasificacionActividadEconomicaServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.escritura.constitutiva.EscrituraConstitutivaServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.registro.sindicato.RegistroSindicatoServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioFiscal;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.domicilio.MunicipioIMSS;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.enums.CausaBajaPatronEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CentroTrabajo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EscrituraConstitutiva;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RegistroSindicato;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSociedad;
import mx.gob.imss.ctirss.delta.persistence.DicDelegacion;
import mx.gob.imss.ctirss.delta.persistence.DicModalidad;
import mx.gob.imss.ctirss.delta.persistence.DicMunicipioImss;
import mx.gob.imss.ctirss.delta.persistence.DicSubdelegacion;
import mx.gob.imss.ctirss.delta.persistence.DicTipoRegPatron;
import mx.gob.imss.ctirss.delta.persistence.DicTipoSociedad;
import mx.gob.imss.ctirss.delta.persistence.DitClasificacion;
import mx.gob.imss.ctirss.delta.persistence.DitDtsExtraPatron;
import mx.gob.imss.ctirss.delta.persistence.DitFormaContacto;
import mx.gob.imss.ctirss.delta.persistence.DitMunicipioPatSujOblig;
import mx.gob.imss.ctirss.delta.persistence.DitPatronGeneral;
import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaFContactoFiscal;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaFisica;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaMContactoFiscal;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaMoral;
import mx.gob.imss.ctirss.delta.persistence.DitPersonafContacto;
import mx.gob.imss.ctirss.delta.persistence.DitPersonamContacto;
import mx.gob.imss.ctirss.delta.persistence.DitTipoContacto;
import mx.gob.imss.ctirss.delta.persistence.PatronesTempInc;

/**
 * 
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Hugo Armando Mart�nez Cham�nica
 *  @Proyecto: delta
 *  @Archivo: SujetoObligadoUtility.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.patronal.service.utility
 *  @Fecha: 12:49:48
 */
@Stateless
public class SujetoObligadoUtility extends AbstractServiceUtility implements
		SujetoObligadoUtilityLocal {

    private static final Logger log = LoggerFactory.getLogger(SujetoObligadoUtility.class);
	
	@EJB
	RegistroSindicatoServiceUtilityLocal registroSindicatoServiceUtility;
	
	@EJB
	EscrituraConstitutivaServiceUtilityLocal escrituraConstitutivaServiceUtilityLocal;
	
	@EJB
	ClasificacionActividadEconomicaServiceUtilityLocal clasificacionUtility;
	
	@EJB
	SujetoObligadoServiceEntityLocal sujetoObligadoServiceEntity;
	
	/**
	 * @Autor: Hugo Armando Mart�nez Cham�nica
	 */
	@Override
	public SujetoObligado convertirEntityToModel(
			DitPatronSujetoObligado entity, TipoPersonaFiscal tipoPersonaFiscal) {
        tipoPersonaFiscal = selectTipoPersonaFiscal(entity, tipoPersonaFiscal);
        SujetoObligado model = initSujetoObligado(entity, tipoPersonaFiscal);
		log.info("Se consulta la lista de patron general");
        initDatosPatronGeneral(entity, model);
        if (tipoPersonaFiscal == TipoPersonaFiscal.FISICA) {
			this.log.debug("Convirtiendo persona fisica: ");
			model.setFisica(convertirEntityToModelPersonaFisica(entity
					.getDitPersonaFisica()));
			this.log.debug("Finaliza convirtiendo persona fisica: ");
		} else {
			model.setMoral(convertirEntityToModelPersonaMoral(entity
					.getDitPersonaMoral()));
			this.log.debug("Finaliza convirtiendo persona moral: ");
		}
		
		DitClasificacion ditClasificacion = entity.getDitClasificacions()!=null && entity.getDitClasificacions().size() > 0 ? entity.getDitClasificacions().get(0) : null;
		
		model.setMunicipioIMSS(convertiyEntityToModelMunicipioImss(entity.getDitMunicipioPatSujOblig()));
		
		if(ditClasificacion!=null)
			model.setClasificacion(clasificacionUtility.convertirEntityToModelClasificacion(ditClasificacion));
		
		if(entity.getDitSubdelPatSujOblig()!=null)
			model.setSubdelegacion(convertirEntityToModelSubdelegacion(entity.getDitSubdelPatSujOblig().getDicSubdelegacion()));
		return model;
		
	}
	
	@Override
	public SujetoObligado convertirEntityToModelDictamen(
			DitPatronSujetoObligado entity, TipoPersonaFiscal tipoPersonaFiscal) {
        tipoPersonaFiscal = selectTipoPersonaFiscal(entity, tipoPersonaFiscal);
        SujetoObligado model = initSujetoObligado(entity, tipoPersonaFiscal);
		log.info("Se consulta la lista de patron general");
        initDatosPatronGeneral(entity, model);
        if(tipoPersonaFiscal == TipoPersonaFiscal.FISICA && entity
				.getDitPersonaFisica() != null && entity
						.getDitPersonaFisica().getCveIdPersonaFisica()!=0){
			
			this.log.debug("CONVIRTIENDO PERSONA FISICA YA VIENE COMO FISICA");
			
			model.setFisica(convertirEntityToModelPersonaFisicaBasic(entity
					.getDitPersonaFisica()));
			
		} 
//		Si ya esta definido como persona moral
		else if(tipoPersonaFiscal == TipoPersonaFiscal.MORAL && entity
				.getDitPersonaMoral() != null && entity
				.getDitPersonaMoral().getCveIdPersonaMoral()!=0) {
			this.log.debug("CONVIRTIENDO PERSONA MORAL YA VIENE COMO MORAL");

			model.setMoral(convertirEntityToModelPersonaMoral(entity
					.getDitPersonaMoral()));
		} 
		else if(entity
				.getDitPersonaFisica() != null && entity
				.getDitPersonaFisica().getCveIdPersonaFisica()!=0) {
			this.log.debug("CONVIRTIENDO PERSONA FISICA NO VIENE COMO FISICA");

			tipoPersonaFiscal = TipoPersonaFiscal.FISICA;
			model.setTipoPersonaFiscal(tipoPersonaFiscal);
			   initDatosPatronGeneral(entity, model);
			model.setFisica(convertirEntityToModelPersonaFisicaBasic(entity
					.getDitPersonaFisica()));
		}
		else if (entity
				.getDitPersonaMoral() != null && entity
				.getDitPersonaMoral().getCveIdPersonaMoral()!=0) {
			this.log.debug("CONVIRTIENDO PERSONA MORAL NO VIENE COMO MORAL");

			tipoPersonaFiscal = TipoPersonaFiscal.MORAL;
			model.setTipoPersonaFiscal(tipoPersonaFiscal);
			   initDatosPatronGeneral(entity, model);
			model.setMoral(convertirEntityToModelPersonaMoral(entity
					.getDitPersonaMoral()));
		} else {
			return null;
		}
		
		DitClasificacion ditClasificacion = entity.getDitClasificacions()!=null && entity.getDitClasificacions().size() > 0 ? entity.getDitClasificacions().get(0) : null;
		
		model.setMunicipioIMSS(convertiyEntityToModelMunicipioImss(entity.getDitMunicipioPatSujOblig()));
		
		if(ditClasificacion!=null)
			model.setClasificacion(clasificacionUtility.convertirEntityToModelClasificacion(ditClasificacion));
		
		if(entity.getDitSubdelPatSujOblig()!=null)
			model.setSubdelegacion(convertirEntityToModelSubdelegacion(entity.getDitSubdelPatSujOblig().getDicSubdelegacion()));
		return model;
		
	}


    public SujetoObligado convertirEntityToModelBasic(DitPatronSujetoObligado entity, TipoPersonaFiscal tipoPersonaFiscal) {
        tipoPersonaFiscal = selectTipoPersonaFiscal(entity, tipoPersonaFiscal);
        SujetoObligado model = initSujetoObligado(entity, tipoPersonaFiscal);
		log.info("Se consulta la lista de patron general");
        initDatosPatronGeneral(entity, model);
		if (tipoPersonaFiscal == TipoPersonaFiscal.FISICA) {
			this.log.debug("Convirtiendo persona fisica: ");
			model.setFisica(convertirEntityToModelPersonaFisicaBasic(entity
					.getDitPersonaFisica()));
			this.log.debug("Finaliza convirtiendo persona fisica basico ");
		} else {
			model.setMoral(convertirEntityToModelPersonaMoral(entity
					.getDitPersonaMoral()));
		}
		log.info("termina conversion SujetoObligado");
        return model;
    }
    
    

    /* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.SujetoObligadoUtilityLocal#convertirEntityToModelBasicDictamen(mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado, mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal)
	 */
	@Override
	public SujetoObligado convertirEntityToModelBasicDictamen(DitPatronSujetoObligado entity,
			TipoPersonaFiscal tipoPersonaFiscal) {
	
		tipoPersonaFiscal = selectTipoPersonaFiscal(entity, tipoPersonaFiscal);
		SujetoObligado model = initSujetoObligado(entity, tipoPersonaFiscal);
		log.debug("SE CONSULTA LA LISTA DE PATRON GENERAL:: ");
		initDatosPatronGeneral(entity, model);
		
//		Si ya esta definido como persona fisica
		if(tipoPersonaFiscal == TipoPersonaFiscal.FISICA && entity
				.getDitPersonaFisica() != null && entity
						.getDitPersonaFisica().getCveIdPersonaFisica()!=0){
			
			this.log.debug("CONVIRTIENDO PERSONA FISICA YA VIENE COMO FISICA");
		
			model.setFisica(convertirEntityToModelPersonaFisicaBasic(entity
					.getDitPersonaFisica()));
			
		} 
//		Si ya esta definido como persona moral
		else if(tipoPersonaFiscal == TipoPersonaFiscal.MORAL && entity
				.getDitPersonaMoral() != null && entity
				.getDitPersonaMoral().getCveIdPersonaMoral()!=0) {
			this.log.debug("CONVIRTIENDO PERSONA MORAL YA VIENE COMO MORAL");
	
			model.setMoral(convertirEntityToModelPersonaMoral(entity
					.getDitPersonaMoral()));
		} 
		else if(entity
				.getDitPersonaFisica() != null && entity
				.getDitPersonaFisica().getCveIdPersonaFisica()!=0) {
			this.log.debug("CONVIRTIENDO PERSONA FISICA NO VIENE COMO FISICA");
			tipoPersonaFiscal = TipoPersonaFiscal.FISICA;
			model.setTipoPersonaFiscal(tipoPersonaFiscal);
			initDatosPatronGeneral(entity, model);
			model.setFisica(convertirEntityToModelPersonaFisicaBasic(entity
					.getDitPersonaFisica()));
		}
		else if (entity
				.getDitPersonaMoral() != null && entity
				.getDitPersonaMoral().getCveIdPersonaMoral()!=0) {
			this.log.debug("CONVIRTIENDO PERSONA MORAL NO VIENE COMO MORAL");
			tipoPersonaFiscal = TipoPersonaFiscal.MORAL;
			model.setTipoPersonaFiscal(tipoPersonaFiscal);
			initDatosPatronGeneral(entity, model);
			model.setMoral(convertirEntityToModelPersonaMoral(entity
					.getDitPersonaMoral()));
		} else {
			return null;
		}
		
		return model;
	}

    private SujetoObligado initSujetoObligado(DitPatronSujetoObligado entity, TipoPersonaFiscal tipoPersonaFiscal) {
        SujetoObligado model = new SujetoObligado();
		model.setCveIdSujetoObligado(entity.getCveIdPatronSujetoObligado());
		model.setIndPatronConfirmado(entity.getIndPatronConfirmado());
		model.setTipoPersonaFiscal(tipoPersonaFiscal);
		model.setDesUsosBienes(entity.getDesUsosBienes());
		model.setDesAfectacion(entity.getDesAfectacion());
		model.setNombreComercial(entity.getNombreComercial());
		model.setModalidad(convertirEntityToModelModalidad(entity.getDicModalidad()));
		
        return model;
    }

    private void initDatosPatronGeneral(DitPatronSujetoObligado entity, SujetoObligado model) {
        log.info("datos patron general");
        
		List<DitPatronGeneral> patronGrals = entity.getDitPatronGenerals();
			
		
		super.log.error("Fin consulta la lista de patron general: "+patronGrals);
		if(patronGrals==null)
			return;//TODO Validaci�n pata consulta de ARP probar y corregir si produce errores en alg�n tr�mite
		
//		if(patronGrals!=null){
			super.log.error("Patron general encontrado en convertirEntityToModel: "+patronGrals.get(0));
			DitPatronGeneral patrongeneral = patronGrals.get(0);
			model.setNumeroRegistroPatronal(patrongeneral.getRegPatron());
			model.setDigVerificador(patrongeneral.getDigVer());
			
			model.setIdTipoRegPatron(patrongeneral.getDicTipoRegPatron()!=null? patrongeneral.getDicTipoRegPatron().getCveIdTipoRegPatron() : 1);//1 --> No tiene asociaci�n
			
			DitDtsExtraPatron datosExtra = patrongeneral.getDitDtsExtraPatron(); 
			if(datosExtra != null){
				SimpleDateFormat fechaBase = new SimpleDateFormat("yyyy/MM/dd");
				if(datosExtra.getCveTipoMovto().intValue() == CausaBajaPatronEnum.BAJA.getClave()){ 
					model.setDescSituacionBaja(CausaBajaPatronEnum.BAJA.getDescripcion());
				    DateFormat FORMATO_FECHA_DD_MM_YYYY = new SimpleDateFormat("dd/MM/yyyy");
					String strDate = FORMATO_FECHA_DD_MM_YYYY.format(datosExtra.getFecMovto());
					model.setFechaBaja(strDate);
					super.log.debug("::: tiene baja - " + datosExtra.getCveTipoMovto().intValue() + " - " + datosExtra.getFecMovto());
				}else if (!fechaBase.format(datosExtra.getFecIniHuelga()).equalsIgnoreCase(CausaBajaPatronEnum.FECHA_DE_HUELGA.getDescripcion())){
					model.setDescSituacionBaja(CausaBajaPatronEnum.HUELGA.getDescripcion());
				}
				Modalidad modalidad = convertirEntityToModelModalidad(entity.getDicModalidad());
				DitPatronGeneral ditPatronGeneral = entity.getDitPatronGenerals().get(0);
				
				Integer numTrabPermanentes = 0;
				Integer numTrabEventuales = 0;
				Integer numTrabContadores = 0;
				Integer numTrabMexExtr = 0;
				
				PatronesTempInc patronesTempInc = sujetoObligadoServiceEntity.getPatronesTempInc(
						ditPatronGeneral.getRegPatron(), 
						String.valueOf(modalidad.getNumModalidad()), 
						ditPatronGeneral.getDigVer());

				if(patronesTempInc!=null){
					numTrabPermanentes = patronesTempInc.getNumTraVigPerm()!=null ? patronesTempInc.getNumTraVigPerm().intValue() : 0;
					numTrabEventuales = patronesTempInc.getNumTraVigEven()!=null ? patronesTempInc.getNumTraVigEven().intValue() : 0;
					numTrabContadores = patronesTempInc.getNumTraVigCons()!=null ? patronesTempInc.getNumTraVigCons().intValue() : 0;
					numTrabMexExtr = patronesTempInc.getNumTraMexExtr()!=null ? patronesTempInc.getNumTraMexExtr().intValue() : 0;
				}
				
				Integer numTrabajadores = numTrabPermanentes + numTrabEventuales + numTrabContadores + numTrabMexExtr;

				model.setNumeroTrabajadores(numTrabajadores);

			}
//		}
    }
	
    private TipoPersonaFiscal selectTipoPersonaFiscal(DitPatronSujetoObligado entity, TipoPersonaFiscal tipoPersonaFiscal) {
        TipoPersonaFiscal tipoPersona = tipoPersonaFiscal;
        if(tipoPersonaFiscal==null) {
            if(entity.getDitPersonaFisica()!=null) {
                    tipoPersona=TipoPersonaFiscal.FISICA;
            }
            else if(entity.getDitPersonaMoral()!=null) {
                tipoPersona=TipoPersonaFiscal.MORAL;
            }
        }
        log.debug("Tipo Persona en consultar registro patronal: "+tipoPersonaFiscal);

        return tipoPersona;
    }
	
    @Override 
    public Fisica convertirDitPersonaToModelFisica(List<DitPersona> ditPersonaList) {
    	Fisica fisica = new Fisica();
    	for (Iterator<DitPersona> iterator = ditPersonaList.iterator(); iterator.hasNext();) {
			DitPersona entity = iterator.next();
	    	fisica.setCurp(entity.getCurp());
	    	if(fisica.getNombre() == null && entity.getNomNombre() != null && entity.getNomNombre().trim().length() > 0){
	    		fisica.setNombre(entity.getNomNombre());
	    	}
	    	if(fisica.getPrimerApellido() == null && entity.getNomPrimerApellido() != null && entity.getNomPrimerApellido().trim().length() > 0){
	    		fisica.setPrimerApellido(entity.getNomPrimerApellido());
	    	}
	    	if(fisica.getSegundoApellido() == null && entity.getNomSegundoApellido() != null && entity.getNomSegundoApellido().trim().length() > 0){
	    		fisica.setSegundoApellido(entity.getNomSegundoApellido());
	    	}			
		}
    	return fisica;
    }
	
	@Override
	public SujetoObligado convertirEntityToModelDatosPersona(
			DitPatronSujetoObligado entity, TipoPersonaFiscal tipoPersonaFiscal) {
		SujetoObligado obligado = new SujetoObligado();
		
		if(tipoPersonaFiscal==null) {
			if(entity.getDitPersonaFisica()!=null){
				tipoPersonaFiscal=TipoPersonaFiscal.FISICA;
			} else{
				tipoPersonaFiscal=TipoPersonaFiscal.MORAL;
			}
		}
		
		obligado.setCveIdSujetoObligado(entity.getCveIdPatronSujetoObligado());
		obligado.setNombreComercial(entity.getNombreComercial());
		obligado.setTipoPersonaFiscal(tipoPersonaFiscal);
		
		if(tipoPersonaFiscal.getCodigo().equals(TipoPersonaFiscal.FISICA.getCodigo())) {
			obligado.setFisica(new Fisica());
			DitPersonaFisica ditFisica = entity.getDitPersonaFisica();
			obligado.getFisica().setCveFisica(ditFisica.getCveIdPersonaFisica());
			obligado.getFisica().setRfc(ditFisica.getRfc());
			
			DitPersona ditPersona = ditFisica.getDitPersona();
			obligado.getFisica().setIdPersona(ditPersona.getCveIdPersona());
			obligado.getFisica().setNombre(ditPersona.getNomNombre());
			obligado.getFisica().setPrimerApellido(ditPersona.getNomPrimerApellido());
			obligado.getFisica().setSegundoApellido(ditPersona.getNomSegundoApellido());
			obligado.getFisica().setCurp(ditPersona.getCurp());
		} else {
			DitPersonaMoral ditMoral = entity.getDitPersonaMoral();
			
			obligado.setMoral(new Moral());
			obligado.getMoral().setCveMoral(ditMoral.getCveIdPersonaMoral());
			obligado.getMoral().setIdPersona(ditMoral.getCveIdPersonaMoral());
			obligado.getMoral().setRazonSocial(ditMoral.getDenominacionRazonSocial());
			obligado.getMoral().setRfc(ditMoral.getRfc());
		}
		
		return obligado;
	}



	@Override
	public SujetoObligado convertirEntityToModelSinPersona(
			DitPatronSujetoObligado entity, PatronesTempInc patronesTempInc) {
		SujetoObligado obligado = new SujetoObligado();
		
		obligado.setCveIdSujetoObligado(entity.getCveIdPatronSujetoObligado());
		
		DitPatronGeneral ditPG = entity.getDitPatronGenerals().get(0);
		obligado.setNumeroRegistroPatronal(ditPG.getRegPatron());
		obligado.setDigVerificador(ditPG.getDigVer());
		obligado.setModalidad(convertirEntityToModelModalidad(entity.getDicModalidad()));
		obligado.setNombreComercial(entity.getNombreComercial());
		obligado.setFechaAlta(entity.getFecRegistroAlta());
		obligado.setSubdelegacion(convertirEntityToModelSubdelegacion(entity.getDitSubdelPatSujOblig()!=null 
				? entity.getDitSubdelPatSujOblig().getDicSubdelegacion() : null));
		
		
		DicTipoRegPatron dicTipoRegPatron = ditPG.getDicTipoRegPatron();
		obligado.setIdTipoRegPatron(dicTipoRegPatron!=null? dicTipoRegPatron.getCveIdTipoRegPatron() : 1);//1 --> No tiene asociaci�n
		
		
		if(entity.getDitClasificacions()!=null && entity.getDitClasificacions().size()>0){
			obligado.setClasificacion(new Clasificacion());
			if(entity.getDitClasificacions().get(0).getIndRegPatClase()!=null 
					&& entity.getDitClasificacions().get(0).getIndRegPatClase().intValue()==1){
				obligado.getClasificacion().setIndRegPatClase(entity.getDitClasificacions().get(0).getIndRegPatClase().intValue());
			}else{
				obligado.getClasificacion().setIndRegPatClase(0);
			}	
		}
		//seteo de datos de baja
		DitDtsExtraPatron datosExtra  = ditPG.getDitDtsExtraPatron(); 
		if(datosExtra != null){
			SimpleDateFormat fechaBase = new SimpleDateFormat("yyyy/MM/dd");
			if(datosExtra.getCveTipoMovto().intValue() == CausaBajaPatronEnum.BAJA.getClave()){ 
				obligado.setDescSituacionBaja(CausaBajaPatronEnum.BAJA.getDescripcion());
				super.log.debug("tiene baja");
			}else if (!fechaBase.format(datosExtra.getFecIniHuelga()).equalsIgnoreCase(CausaBajaPatronEnum.FECHA_DE_HUELGA.getDescripcion())){
				obligado.setDescSituacionBaja(CausaBajaPatronEnum.HUELGA.getDescripcion());
			}
			
			Integer numTrabPermanentes = 0;
			Integer numTrabEventuales = 0;
			Integer numTrabContadores = 0;
			Integer numTrabMexExtr = 0;
			
			if(patronesTempInc != null){
				numTrabPermanentes = patronesTempInc.getNumTraVigPerm()!=null ? patronesTempInc.getNumTraVigPerm().intValue() : 0;
				numTrabEventuales = patronesTempInc.getNumTraVigEven()!=null ? patronesTempInc.getNumTraVigEven().intValue() : 0;
				numTrabContadores = patronesTempInc.getNumTraVigCons()!=null ? patronesTempInc.getNumTraVigCons().intValue() : 0;
				numTrabMexExtr = patronesTempInc.getNumTraMexExtr()!=null ? patronesTempInc.getNumTraMexExtr().intValue() : 0;
			}
			
			Integer numTrabajadores = numTrabPermanentes + numTrabEventuales + numTrabContadores + numTrabMexExtr;
			
			obligado.setNumeroTrabajadores(numTrabajadores);
			
		}else{
			//Se iniciliza numTrabajadores a cero
			obligado.setNumeroTrabajadores(0);
		}
		
		
		
		return obligado;
	}


	/**
	 * Popula la siguiente informaci�n de la persona f�sica:
	 * IdPersona, Nombre, Primer Apellido, Segundo Apellido
	 * RFC y Nombre Comercial
	 * @author Hugo Armando Mart�nez Cham�nica
	 * @param personaFisica
	 * @return Fisica
	 */
	@Override
	public Fisica convertirEntityToModelPersonaFisica(DitPersonaFisica personaFisica) {
		this.log.debug("Asignando persona ");
		Fisica pFisica = convertirEntityToModelPersonaFisicaBasic(personaFisica);
		pFisica.setMediosContactoFiscales(convertirEntitesToModel(personaFisica.getDitPersonaFContactoFiscales()));
		System.err.println("Lista de medios de la persona: "+personaFisica.getDitPersona().getDitPersonafContactos());
		pFisica.setMediosContacto(convertirEntitesDPCToModel(personaFisica.getDitPersona().getDitPersonafContactos()));
		return pFisica;
	}

    public Fisica convertirEntityToModelPersonaFisicaBasic(DitPersonaFisica personaFisica) {
        log.debug("convertirEntityToModelBasico sin relaciones");
		DitPersona persona = personaFisica.getDitPersona();
        Fisica pFisica = new Fisica();
		this.log.debug("Asignando valores ");
		pFisica.setIdPersona(persona.getCveIdPersona());
		this.log.debug("Asignando cveFisica: "+personaFisica.getCveIdPersonaFisica());
		pFisica.setCveFisica(personaFisica.getCveIdPersonaFisica());
		this.log.debug("Asignando nombre: "+persona.getNomNombre());
		pFisica.setNombre(persona.getNomNombre());
		this.log.debug("Asignando apellido "+persona.getNomPrimerApellido());
		pFisica.setPrimerApellido(persona.getNomPrimerApellido());
		this.log.debug("Asignando apellido materno: "+persona.getNomSegundoApellido());
		pFisica.setSegundoApellido(persona.getNomSegundoApellido());
		String rfc = personaFisica.getRfc()!=null ? personaFisica.getRfc() : persona.getRfc();
		this.log.debug("Asignando rfc "+personaFisica.getRfc());
		pFisica.setRfc(rfc);
		this.log.debug("Asignando curp "+persona.getCurp());
		pFisica.setCurp(persona.getCurp());
		TipoPersona tipoPersona = new TipoPersona();
		tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
		pFisica.setTipoPersona(tipoPersona);
        return pFisica;
    }
	
	private List<MedioContacto> convertirEntitesDPCToModel(List<DitPersonafContacto> entities){
		List<MedioContacto> medios = null;
		if(entities!=null){
			log.error("Si hay medios de contacto personales:::");
			medios = new ArrayList<MedioContacto>();
			for(DitPersonafContacto mcFiscal:entities){
				log.error("Agregando medio de contacto personal:::"+mcFiscal.getDitFormaContacto());
				medios.add(convertirEntityToModel(mcFiscal.getDitFormaContacto()));
			}
			log.error("Se agregaron "+medios.size()+" medios personales");
		}else{
			log.error("No hay medios de contacto personales:::");
		}
		
		return medios;
	}
	
	
	private List<MedioContacto> convertirEntitesToModel(List<DitPersonaFContactoFiscal> entities){
		List<MedioContacto> medios = null;
		if(entities!=null){
			medios = new ArrayList<MedioContacto>();
			for(DitPersonaFContactoFiscal mcFiscal:entities){
				medios.add(convertirEntityToModel(mcFiscal.getDitFormaContacto()));
			}
		}
		
		return medios;
	}
	
	private List<MedioContacto> convertirEntitesDFMToModel(List<DitPersonaMContactoFiscal> entities){
		List<MedioContacto> medios = null;
		if(entities!=null){
			medios = new ArrayList<MedioContacto>();
			for(DitPersonaMContactoFiscal mcFiscal:entities){
				medios.add(convertirEntityToModel(mcFiscal.getDitFormaContacto()));
			}
		}
		
		return medios;
	}
	
	public MedioContacto convertirEntityToModel(DitFormaContacto entity){
		MedioContacto medioContacto = new MedioContacto();
		medioContacto.setClave(entity.getCveIdFormaContacto());
		medioContacto.setDesFormaContacto(entity.getDesFormaContacto());
		medioContacto.setTipoMedioContacto(convertirEntityToModel(entity.getDitTipoContacto()));
		
		return medioContacto;
	}
	
	public TipoMedioContacto convertirEntityToModel(DitTipoContacto entity){
		TipoMedioContacto tipoMedioContacto = new TipoMedioContacto();
		if(entity==null)
			return tipoMedioContacto;
		
		tipoMedioContacto.setIdTipoMedioContacto(entity.getCveIdTipoContacto());
		tipoMedioContacto.setDescripcion(entity.getDesTipoContacto());
		
		return tipoMedioContacto;
	}
	
	/**
	 * Popula la siguiente informaci�n de la persona Moral:
	 * IdPersonaMoral, Nombre Comercial, RFC, Raz�n Social
	 * y Tipo de Sociedad
	 * @author Hugo Armando Mart�nez Cham�nica
	 * @param personaMoral
	 * @return Moral
	 */
	@Override
	public Moral convertirEntityToModelPersonaMoral(
			DitPersonaMoral personaMoral) {
		Moral pMoral = new Moral();
		TipoPersona tipoPersona = new TipoPersona();
		tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
		pMoral.setCveMoral(personaMoral.getCveIdPersonaMoral());
		pMoral.setIdPersona(personaMoral.getCveIdPersonaMoral());
		pMoral.setRfc(personaMoral.getRfc());
		pMoral.setRazonSocial(personaMoral.getDenominacionRazonSocial());
		pMoral.setTipoSociedad(convertirEntityToModelTipoSociedad(personaMoral.getDicTipoSociedad()));
		pMoral.setTipoPersona(tipoPersona);
		
		if(personaMoral.getDitSindicatos()!= null && personaMoral.getDitSindicatos().size() >0){
			pMoral.setRegistroSindicato(registroSindicatoServiceUtility.convertirEntityToModel(personaMoral.getDitSindicatos().get(0)));
		}else{
			pMoral.setRegistroSindicato(new RegistroSindicato());
		}
			
		if(personaMoral.getDitActaConstitutivas()!= null && personaMoral.getDitActaConstitutivas().size() >0){
			pMoral.setEscrituraConstitutiva(escrituraConstitutivaServiceUtilityLocal.convertirEntityToModel(personaMoral.getDitActaConstitutivas().get(0)));
		}else{
			EscrituraConstitutiva escritura = new EscrituraConstitutiva();
			Municipio mun = new Municipio();
			EntidadFederativa entidad = new EntidadFederativa();
			mun.setEntidadFederativa(entidad);
			escritura.setLugarExpedicion(mun);
			pMoral.setEscrituraConstitutiva(escritura);
		}
		
		pMoral.setMediosContactoFiscales(convertirEntitesDFMToModel(personaMoral.getDitPersonaMContactoFiscales()));
		pMoral.setMediosContacto(convertirDatosContactoPersonalesPM(personaMoral.getDitPersonamContactos()));
		return pMoral;
	}
	
	
	private List<MedioContacto> convertirDatosContactoPersonalesPM(List<DitPersonamContacto> contactosPersona){
		List<MedioContacto> contactos = new ArrayList<MedioContacto>();
		
		if(contactosPersona!=null)
			for(DitPersonamContacto contactoPersona:contactosPersona){
				MedioContacto medioContacto = convertirEntityToModel(contactoPersona.getDitFormaContacto());
				contactos.add(medioContacto);
			}
		
		return contactos;
	}
	
	/**
	 * 
	 * @author Hugo Armando Mart�nez Cham�nica
	 * @param entity
	 * @return TipoSociedad
	 */
	@Override
	public TipoSociedad convertirEntityToModelTipoSociedad(DicTipoSociedad entity){
		if(entity==null){
			log.debug("No existe Tipo de sociedad para la persona moral");
			return new TipoSociedad();
		}
		TipoSociedad tipoSociedad = new TipoSociedad();
		tipoSociedad.setIdTipoSociedad(Long.valueOf(entity.getCveIdTipoSociedad()));
		tipoSociedad.setDescripcion(entity.getDesTipoSociedad());
		tipoSociedad.setDescripcionAbreviada(entity.getDesTipoSociedadAbrev());
		return tipoSociedad;
	}

	@Override
	public SujetoObligado llenarDatosPersonaMoral(SujetoObligado sujetoObligado, EscrituraConstitutiva escrituraConstitutiva, RegistroSindicato registroSindicato) {
		
		TipoPersonaFiscal tipoPersonaFiscal = sujetoObligado.getTipoPersonaFiscal();
		
		
//		SujetoObligado model = sujetoObligado;
//		model.setCveIdSujetoObligado(sujetoObligado.getCveIdSujetoObligado());
		sujetoObligado.setTipoPersonaFiscal(tipoPersonaFiscal);
		
		if (tipoPersonaFiscal == TipoPersonaFiscal.FISICA) {
//			model.setFisica(sujetoObligado.getFisica());
			System.out.println("Es fisica no hago nada....");
		} else {
			System.out.println("Es Moral meto escritura y sindicato....");
			sujetoObligado.setEscrituraConstitutiva(escrituraConstitutiva);
			sujetoObligado.setRegistroSindicato(registroSindicato);
//			sujetoObligado.setMoral(sujetoObligado.getMoral());
		}
		return sujetoObligado;
		
	}
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.SujetoObligadoUtilityLocal#convertDomicilioToDomicilioFiscal(mx.gob.imss.ctirss.delta.model.domicilio.Domicilio)
	 */
	@Override
	public DomicilioFiscal convertDomicilioToDomicilioFiscal(Domicilio domicilio) {
		if(domicilio==null)
			return null;
		
		DomicilioFiscal domFiscal= new DomicilioFiscal();
		try {
			super.copyBeans(domicilio, domFiscal);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return domFiscal;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.SujetoObligadoUtilityLocal#construirPersonaFisicaContactoEntity(mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto, mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona)
	 */
	@Override
	public DitPersonafContacto construirPersonaFisicaContactoEntity(
			MedioContacto medioContacto, Persona persona) {
		DitPersonafContacto entity = new DitPersonafContacto();
		entity.setFecRegistroAlta(Calendar.getInstance().getTime());
		DitFormaContacto fc = new DitFormaContacto();
		fc.setCveIdFormaContacto(medioContacto.getClave());
		entity.setDitFormaContacto(fc);
		DitPersona ditPersona = new DitPersona();
		ditPersona.setCveIdPersona(persona.getIdPersona());
		entity.setDitPersona(ditPersona);
		
		return entity;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.SujetoObligadoUtilityLocal#construirPersonaMoralContactoEntity(mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto, mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona)
	 */
	@Override
	public DitPersonamContacto construirPersonaMoralContactoEntity(
			MedioContacto medioContacto, Persona persona) {
		DitPersonamContacto entity = new DitPersonamContacto();
		entity.setFecRegistroAlta(Calendar.getInstance().getTime());
		DitFormaContacto fc = new DitFormaContacto();
		fc.setCveIdFormaContacto(medioContacto.getClave());
		entity.setDitFormaContacto(fc);
		DitPersonaMoral ditPersona = new DitPersonaMoral();
		ditPersona.setCveIdPersonaMoral(persona.getIdPersona());
		entity.setDitPersonaMoral(ditPersona);

		return entity;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.SujetoObligadoUtilityLocal#convertirEntityToModelDelegacion(mx.gob.imss.ctirss.delta.persistence.DicDelegacion)
	 */
	@Override
	public Delegacion convertirEntityToModelDelegacion(DicDelegacion entity) {
		Delegacion delegacion = new Delegacion();
		delegacion.setClave(entity.getClaveDelegacion());
		delegacion.setId(entity.getCveIdDelegacion());
		delegacion.setDescripcion(entity.getDesDeleg());
		Integer ciz = entity.getCveCiz() == null ? 1 : entity.getCveCiz();
		delegacion.setCiz(ciz);
		return delegacion;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.SujetoObligadoUtilityLocal#convertirEntityToModelSubdelegacion(mx.gob.imss.ctirss.delta.persistence.DicSubdelegacion)
	 */
	@Override
	public Subdelegacion convertirEntityToModelSubdelegacion(
			DicSubdelegacion entity) {
		if(entity==null)
			return null;
		Subdelegacion model = new Subdelegacion();
		model.setClave(entity.getClaveSubdelegacion());
		model.setId(entity.getCveIdSubdelegacion());
		model.setDescripcion(entity.getDesSubdelegacion());
		model.setDelegacion(convertirEntityToModelDelegacion(entity.getDicDelegacion()));
		return model;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.SujetoObligadoUtilityLocal#convertirEntityToModelPersona(mx.gob.imss.ctirss.delta.persistence.DitPersona)
	 */
	@Override
	public Persona convertirEntityToModelPersona(DitPersona entity) {
		Persona model = new Persona();
		model.setIdPersona(entity.getCveIdPersona());
		model.setRfc(entity.getRfc());
		return model;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.SujetoObligadoUtilityLocal#convertirDomicilioACentroTrabajo(mx.gob.imss.ctirss.delta.model.domicilio.Domicilio)
	 */
	@Override
	public CentroTrabajo convertirDomicilioACentroTrabajo(Domicilio domicilio) {
		CentroTrabajo cTrabajo = new CentroTrabajo();
		try {
			super.copyBeans(domicilio, cTrabajo);
			validarCamposNumericosCentroTrabajo(domicilio, cTrabajo);
			StringBuffer domicilioCompleto = new StringBuffer();			
			if(domicilio.getVialidadPrimaria() == null){
				domicilioCompleto.append(domicilio.getCalle()!=null?domicilio.getCalle():"");
			}else{
				domicilioCompleto.append(domicilio.getVialidadPrimaria().getNombre());
			}
			domicilioCompleto.append(domicilio.getNumExterior1()!=null && domicilio.getNumExterior1()!=0 
					? (" #"+domicilio.getNumExterior1()) 
					: " " + (domicilio.getNumExteriorAlf()!=null ? " " + domicilio.getNumExteriorAlf() : "") );
			if(domicilio.getNumInterior()!=null && domicilio.getNumInterior()!=0 )
				domicilioCompleto.append(", interior "+domicilio.getNumInterior()+(domicilio.getNumInteriorAlf()!=null ? " "+domicilio.getNumInteriorAlf() : "" ) );
			domicilioCompleto.append(", COLONIA "+domicilio.getAsentamiento().getNombre());
			if(domicilio.getAsentamiento().getMunicipio()!=null){
				domicilioCompleto.append(", " +domicilio.getAsentamiento().getMunicipio().getNombre());
				if(domicilio.getAsentamiento().getMunicipio().getEntidadFederativa()!=null)
				domicilioCompleto.append(", " +domicilio.getAsentamiento().getMunicipio().getEntidadFederativa().getNombre());
			}
			domicilioCompleto.append(", CP "+domicilio.getCodigoPostal().getCodigoPostal());
			cTrabajo.setDescripcion(domicilioCompleto.toString());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return cTrabajo;
	}
	
	@Override
	public Modalidad convertirEntityToModelModalidad(DicModalidad entity){
		if(entity != null){
			Modalidad model = new Modalidad();
			model.setIdModalidad(entity.getCveIdModalidad());
			model.setNumModalidad(entity.getNumModalidad());
			model.setDescripcion(entity.getDesModalidad());
			model.setSiglaAgregadoMedico(entity.getSiglaAgregadoMedico());
			model.setDesCorta(entity.getDesNomModalidadCorto());
			
			return model;
		}
		return null;
	}
	
	@Override
	public MunicipioIMSS convertiyEntityToModelMunicipioImss(DitMunicipioPatSujOblig dicMunicipio){
		return dicMunicipio==null ? null : convertiyEntityToModelMunicipioImss(dicMunicipio.getDicMunicipioImss());
	}
	
	private MunicipioIMSS convertiyEntityToModelMunicipioImss(DicMunicipioImss dicMunicipio){
		if(dicMunicipio==null)
			return null;
		MunicipioIMSS model = new MunicipioIMSS();
		
		model.setCvecMunicipioSINDO(dicMunicipio.getCveMunicipio());
		model.setDescMunicipio(dicMunicipio.getNomMunicipioImss());
		model.setIdMunicipio(String.valueOf(dicMunicipio.getCveIdMunicipioImss()));
		model.setFechaInicioOperacionesServiciosCampo(dicMunicipio.getFecInicioServicioCamp());
		model.setFechaInicioOperacionesServiciosUrbanos(dicMunicipio.getFecInicioServicioUrb());
		
		
		return model;

	}
	
	@Override
	public MedioContacto convertirEntityToModelContacto(DitFormaContacto entity){
		if(entity.getCveIdFormaContacto()==null)
			return null;
		MedioContacto medio = new MedioContacto();
		medio.setClave(entity.getCveIdFormaContacto());
		medio.setDesFormaContacto(entity.getDesFormaContacto());
		medio.setTipoMedioContacto(convertirEntityToModel(entity.getDitTipoContacto()));
		
		return medio;
	}

	private void validarCamposNumericosCentroTrabajo(Domicilio domicilio,
			CentroTrabajo cTrabajo) {
		if (domicilio.getClave() == null) {
			cTrabajo.setClave(null);
		}
		if (domicilio.getNumExterior1() == null) {
			cTrabajo.setNumExterior1(null);
		}
		if (domicilio.getNumExterior2() == null) {
			cTrabajo.setNumExterior2(null);
		}
		if (domicilio.getNumInterior() == null) {
			cTrabajo.setNumInterior(null);
		}
		if (domicilio.getTipoBusquedaVialidad() == null) {
			cTrabajo.setTipoBusquedaVialidad(null);
		}
		if (domicilio.getCveIdPersonafDom() == null) {
			cTrabajo.setCveIdPersonafDom(null);
		}
	}
	
	/**
	 * @param personaFisica
	 * @return Fisica
	 */
	@Override
	public Fisica convertirEntityToModelPFDatosBasicos(DitPersonaFisica personaFisica) {
		this.log.debug("Asignando persona fisica datos basicos");
		Fisica pFisica = convertirEntityToModelPersonaFisicaBasic(personaFisica);
		return pFisica;
	}	

	/**
	 * @param personaMoral
	 * @return Moral
	 */
	@Override
	public Moral convertirEntityToModelPMDatosBasicos(
			DitPersonaMoral personaMoral) {
		this.log.debug("Asignando persona moral datos basicos");
		Moral pMoral = new Moral();
		TipoPersona tipoPersona = new TipoPersona();
		tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
		pMoral.setCveMoral(personaMoral.getCveIdPersonaMoral());
		pMoral.setIdPersona(personaMoral.getCveIdPersonaMoral());
		pMoral.setRfc(personaMoral.getRfc());
		pMoral.setRazonSocial(personaMoral.getDenominacionRazonSocial());
		pMoral.setTipoSociedad(convertirEntityToModelTipoSociedad(personaMoral.getDicTipoSociedad()));
		pMoral.setTipoPersona(tipoPersona);
		return pMoral;
	}

}
