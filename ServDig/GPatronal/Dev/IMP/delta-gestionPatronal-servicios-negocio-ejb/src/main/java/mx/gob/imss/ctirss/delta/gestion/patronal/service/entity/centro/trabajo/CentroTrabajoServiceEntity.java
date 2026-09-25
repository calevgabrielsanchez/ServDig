package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.centro.trabajo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.ServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.SujetoObligadoUtilityLocal;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.domicilio.MunicipioIMSS;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoAmbito;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoAsentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoVialidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Vialidad;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CentroTrabajo;
import mx.gob.imss.ctirss.delta.persistence.DgAsentamiento;
import mx.gob.imss.ctirss.delta.persistence.DgCatAmbito;
import mx.gob.imss.ctirss.delta.persistence.DgCatEstado;
import mx.gob.imss.ctirss.delta.persistence.DgCatLocalidad;
import mx.gob.imss.ctirss.delta.persistence.DgCatMunicipio;
import mx.gob.imss.ctirss.delta.persistence.DgCatTipoAsen;
import mx.gob.imss.ctirss.delta.persistence.DgCatVialidad;
import mx.gob.imss.ctirss.delta.persistence.DgCodigosPostale;
import mx.gob.imss.ctirss.delta.persistence.DgDomicilioGeografico;
import mx.gob.imss.ctirss.delta.persistence.DgVialidad;
import mx.gob.imss.ctirss.delta.persistence.DicModalidad;
import mx.gob.imss.ctirss.delta.persistence.DicMunicipioImss;
import mx.gob.imss.ctirss.delta.persistence.DicSubdelegacion;
import mx.gob.imss.ctirss.delta.persistence.DicTipoDomicilio;
import mx.gob.imss.ctirss.delta.persistence.DicTipoPersona;
import mx.gob.imss.ctirss.delta.persistence.DitCentroTrabajoContacto;
import mx.gob.imss.ctirss.delta.persistence.DitLlavePatron;
import mx.gob.imss.ctirss.delta.persistence.DitPatSujObligDomicilio;
import mx.gob.imss.ctirss.delta.persistence.DitPatronGeneral;
import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DitSubdelPatSujOblig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Stateless
public class CentroTrabajoServiceEntity extends ServiceEntity implements
		CentroTrabajoServiceEntityLocal {

    private static final Logger log = LoggerFactory.getLogger(CentroTrabajoServiceEntity.class);
	
	@EJB
	SujetoObligadoUtilityLocal sujetoObligadoUtility;
	
	@Override
	public boolean validarSubDelOrigenSubDelDestino(long idSubdelegacionOrigen, long idSubdelegacionDestino){
		// TODO Auto-generated method stub				
		return false;
	}

	@Override
	public CentroTrabajo actualizarCentroTrabajo(
			CentroTrabajo centroTrabajo)
			throws GestionPatronalBusinessException {
		
		DitPatSujObligDomicilio ditPatSujObligDomicilio = null;
		Long tipoDomicilioCentroTrabajo=3l;
				
		try {	
			
			StringBuffer qDomicilioCt = new StringBuffer();
			qDomicilioCt.append("select centroTrabajoAsociado from DitPatSujObligDomicilio centroTrabajoAsociado where ");
			qDomicilioCt.append(" centroTrabajoAsociado.ditPatronSujetoObligado.cveIdPatronSujetoObligado =:idPatron and  ");
			qDomicilioCt.append(" centroTrabajoAsociado.dicTipoDomicilio.cveIdTipoDomicilio =:idTipoDomicilio ");
			
			Query qDomicilio = this.em.createQuery(qDomicilioCt.toString());
			qDomicilio.setParameter("idPatron", centroTrabajo.getCveIdPatronSujetoObligado());
			qDomicilio.setParameter("idTipoDomicilio", tipoDomicilioCentroTrabajo);
			
//			//Se obtiene el centro de trabajo actual del sujeto obligado
//			ditPatSujObligDomicilio = (DitPatSujObligDomicilio)this.getSession().createCriteria(DitPatSujObligDomicilio.class)			 
//			 .add(Restrictions.and
//					 (Restrictions.eq("ditPatronSujetoObligado.cveIdPatronSujetoObligado",centroTrabajo.getCveIdPatronSujetoObligado())
//							 ,Restrictions.eq("dicTipoDomicilio.cveIdTipoDomicilio", tipoDomicilioCentroTrabajo))).uniqueResult();
			try{
				ditPatSujObligDomicilio = (DitPatSujObligDomicilio)qDomicilio.getSingleResult();
				if (ditPatSujObligDomicilio!=null){
					DgDomicilioGeografico domGeografico = this.em.find(DgDomicilioGeografico.class, centroTrabajo.getClave().longValue());
					ditPatSujObligDomicilio.setDgDomicilioGeografico(domGeografico);
					//Se borra el registro anterior del centro de trabajo del sujeto obligado
//					this.em.remove(ditPatSujObligDomicilio);
//					getSession().delete(ditPatSujObligDomicilio);
					//Se registra el nuevo centro de trabajo al sujeto obligado
//					ditPatSujObligDomicilio=generarNuevoCentroTrabajo(ditPatSujObligDomicilio,centroTrabajo);
//					this.em.persist(ditPatSujObligDomicilio);
//					getSession().save(ditPatSujObligDomicilio);
				}
			}catch(NoResultException nre){
				ditPatSujObligDomicilio = new DitPatSujObligDomicilio();
				DgDomicilioGeografico domGeografico = this.em.find(DgDomicilioGeografico.class, centroTrabajo.getClave().longValue());
				ditPatSujObligDomicilio.setDgDomicilioGeografico(domGeografico);
				DicTipoDomicilio dicTipoDomicilio = this.em.find(DicTipoDomicilio.class, tipoDomicilioCentroTrabajo);
				ditPatSujObligDomicilio.setDicTipoDomicilio(dicTipoDomicilio);
				DitPatronSujetoObligado pso = this.em.find(DitPatronSujetoObligado.class, centroTrabajo.getCveIdPatronSujetoObligado());
				ditPatSujObligDomicilio.setDitPatronSujetoObligado(pso);
				this.em.persist(ditPatSujObligDomicilio);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			throw new GestionPatronalBusinessException();
		}
		return centroTrabajo;
	}

    public Domicilio findDomicilio(Long idDomicilio) {
        Domicilio domicilio = new Domicilio();
        em.flush();
        em.clear();
        DgDomicilioGeografico dgDomicilioGeografico = em.find(DgDomicilioGeografico.class, idDomicilio);
        fillDomicilio(dgDomicilioGeografico, domicilio);
        return domicilio;
    }

    private void fillDomicilio(DgDomicilioGeografico entity, Domicilio domicilio) {
        domicilio.setLongitud(entity.getRefLongitud());
        domicilio.setLatitud(entity.getRefLatitud());
        domicilio.setCalle(entity.getNomvial());
        DgAsentamiento dgAsentamiento = entity.getDgAsentamiento();
        Asentamiento asentamiento = new Asentamiento();
        asentamiento.setClave(dgAsentamiento.getId().getCveAsen());
        asentamiento.setNombre(dgAsentamiento.getNomAsen());
        domicilio.setAsentamiento(asentamiento);
        DgCatTipoAsen catTipoAsen = dgAsentamiento.getDgCatTipoAsen();
        TipoAsentamiento tipoAsentamiento = new TipoAsentamiento();
        tipoAsentamiento.setClave(Long.valueOf(catTipoAsen.getCveTipoAsen()));
        tipoAsentamiento.setDescripcion(catTipoAsen.getNombre());
        asentamiento.setTipoAsentamiento(tipoAsentamiento);
        DgCatLocalidad dgLocalidad = entity.getDgCatLocalidad();
        DgCatAmbito dgAmbito = dgLocalidad.getDgCatAmbito();
        if(dgAmbito != null) {
            TipoAmbito ambito = new TipoAmbito();
            ambito.setClave(Long.valueOf(dgAmbito.getAmbito()));
            ambito.setDescripcion(dgAmbito.getNombre());
        }
        Localidad localidad = new Localidad();
        localidad.setClave(dgLocalidad.getId().getCveLoc());
        localidad.setNombre(dgLocalidad.getNomLoc());
        asentamiento.setLocalidad(localidad);
        DgCatMunicipio dgMunicipio = dgLocalidad.getDgCatMunicipio();
        Municipio municipio = new Municipio();
        municipio.setClave(dgMunicipio.getId().getCveMun());
        municipio.setNombre(dgMunicipio.getNomMun());
        localidad.setMunicipio(municipio);
        DgCatEstado dgEstado = dgMunicipio.getDgCatEstado();

        EntidadFederativa entidadFederativa = new EntidadFederativa();
        entidadFederativa.setClave(dgEstado.getCveEnt());
        entidadFederativa.setNombre(dgEstado.getNomEnt());
        municipio.setEntidadFederativa(entidadFederativa);
        DgCodigosPostale dgCodigo = entity.getDgCodigosPostale();
        CodigoPostal codigo = new CodigoPostal();
        codigo.setCodigoPostal(dgCodigo.getId().getCodigo());
        domicilio.setClave(Integer.valueOf((new Long(entity.getDomicilioId().longValue())).intValue()));
        domicilio.setCodigoPostal(codigo);
        asentamiento.setCodigoPostal(codigo);
        Vialidad vialidadPrimaria = transformaVialidad(entity.getDgVialidadByCveViaPrin());
        domicilio.setVialidadPrimaria(vialidadPrimaria);
        Vialidad vialidadRefPrimaria = transformaVialidad(entity.getDgVialidadByCveViaRef1());
        domicilio.setVialidadReferenciaPrimaria(vialidadRefPrimaria);
        Vialidad vialidadRefSecundaria = transformaVialidad(entity.getDgVialidadByCveViaRef2());
        domicilio.setVialidadReferenciaSecundaria(vialidadRefSecundaria);
        Vialidad vialidadRefPosterior = transformaVialidad(entity.getDgVialidadByCveViaRef3());
        domicilio.setVialidadReferenciaPosterior(vialidadRefPosterior);
        domicilio.setNumExterior1(entity.getNumextnum());
        String num = entity.getNumextAnt();
        if(num != null && Pattern.matches("\\d+", num)) {
            domicilio.setNumExterior2(new Integer(entity.getNumextAnt()));
        }
        if(entity.getNumintnum() != null) {
            domicilio.setNumInterior(entity.getNumintnum());
        }
        if(entity.getNumintalf() != null) {
            domicilio.setNumInteriorAlf(entity.getNumintalf().toString());
        }
        if(entity.getNumextalf() != null) {
            domicilio.setNumExteriorAlf(entity.getNumextalf().toString());
        }
        domicilio.setDescripcion(entity.getDescripc());
        if(entity.getDgCatTipoDom() != null) {
            TipoDomicilio tipoDomicilio = new TipoDomicilio();
            tipoDomicilio.setClave(entity.getDgCatTipoDom().getCveTipoDom());
            tipoDomicilio.setDescripcion(entity.getDgCatTipoDom().getDescripcion());
            domicilio.setTipoDomicilio(tipoDomicilio);
        }

    }

    private Vialidad transformaVialidad(DgVialidad entity) {
        Vialidad vialidad = new Vialidad();
        if(entity != null) {
            vialidad.setClave(Integer.valueOf(entity.getCveVia().intValue()));
            vialidad.setNombre(entity.getNomVia());
            DgCatVialidad dgCatVialidad = entity.getDgCatVialidad();
            TipoVialidad tipoVialidad = new TipoVialidad();
            tipoVialidad.setClave(Integer.valueOf(dgCatVialidad.getCveTipoVial().intValue()));
            tipoVialidad.setDescripcion(dgCatVialidad.getDescripcion());
            vialidad.setTipoVialidad(tipoVialidad);
        }
        return vialidad;
    }

	@Override
	public List<Subdelegacion> obtenerSubdelegacionesCompatibles(
			Long cveIdSubdelegacion) {
		
		
		StringBuffer qBuffer = new StringBuffer();
		qBuffer.append(" select dicSubCompat.dicSubdelegacionDestino from DicSubdelCompatible dicSubCompat");
		qBuffer.append(" where  dicSubCompat.dicSubdelegacionOrigen.cveIdSubdelegacion =:idSubdelegacionOrigen ");
		
		Query query = em.createQuery(qBuffer.toString());
		query.setParameter("idSubdelegacionOrigen", cveIdSubdelegacion);
		
		@SuppressWarnings("unchecked")
		List<DicSubdelegacion> subdelCompatibles = query.getResultList();
		List<Subdelegacion> subdelegaciones = new ArrayList<Subdelegacion>();
		if(subdelCompatibles!=null){
			
			for(DicSubdelegacion subdelegacion : subdelCompatibles){
				Subdelegacion subdelcompat = sujetoObligadoUtility.convertirEntityToModelSubdelegacion(subdelegacion);
				subdelegaciones.add(subdelcompat);
			}
		}
		
		return subdelegaciones;
	}

	@Override
	public void actualizarSubdelegacionDelRegistroPatronal(
			Long cveIdPatronSujetoObligado, Long idSubdelegacion) {
		DitSubdelPatSujOblig ditSubdelPatSujOblig = this.em.find(DitSubdelPatSujOblig.class, cveIdPatronSujetoObligado);
		DicSubdelegacion dicSubdel = this.em.find(DicSubdelegacion.class, idSubdelegacion);
		if(ditSubdelPatSujOblig!= null){
			ditSubdelPatSujOblig.setDicSubdelegacion(dicSubdel);
			ditSubdelPatSujOblig.setFecRegistroActualizado(Calendar.getInstance().getTime());
		}else{
			DitPatronSujetoObligado ditpatron = this.em.find(DitPatronSujetoObligado.class, cveIdPatronSujetoObligado);
			ditSubdelPatSujOblig = new DitSubdelPatSujOblig();
			ditSubdelPatSujOblig.setCveIdPatronSujetoObligado(cveIdPatronSujetoObligado);
			ditSubdelPatSujOblig.setDitPatronSujetoObligado(ditpatron);
			ditSubdelPatSujOblig.setDicSubdelegacion(dicSubdel);
			ditSubdelPatSujOblig.setFecRegistroAlta(Calendar.getInstance().getTime());
			this.em.persist(ditSubdelPatSujOblig);
		}
		
//		this.em.merge(ditSubdelPatSujOblig);
	}
	
	
	@Override
	public void actualizarMunicipioIMSSDelRegistroPatronal(
			Long cveIdPatronSujetoObligado, String idMunicipioIMSS) {
		Long idMunicipio = Long.valueOf(idMunicipioIMSS);
		DicMunicipioImss municipioIMSS = this.em.find(DicMunicipioImss.class, idMunicipio);
		DitPatronSujetoObligado ditPatronSujetoObligado = this.em.find(DitPatronSujetoObligado.class, cveIdPatronSujetoObligado);
		log.error("municipio: "+municipioIMSS.getCveMunicipio());
		if(ditPatronSujetoObligado!=null && ditPatronSujetoObligado.getDitMunicipioPatSujOblig()!=null){
			ditPatronSujetoObligado.getDitMunicipioPatSujOblig().setDicMunicipioImss(municipioIMSS);
			ditPatronSujetoObligado.setFecRegistroActualizado(Calendar.getInstance().getTime());

			// Se actualiza la llave Patronal
			updateLlavePatron(ditPatronSujetoObligado);
		}
	}

	@Override
	public MunicipioIMSS consultarMunicipioIMSSPorRegistroPatronal(
			Long cveIdPatron) {
		DitPatronSujetoObligado ditPatronSujetoObligado = this.em.find(DitPatronSujetoObligado.class, cveIdPatron);
		if(ditPatronSujetoObligado!=null 
				&& ditPatronSujetoObligado.getDitMunicipioPatSujOblig()!=null 
				&& ditPatronSujetoObligado.getDitMunicipioPatSujOblig().getDicMunicipioImss()!=null){
			MunicipioIMSS municipio = sujetoObligadoUtility.convertiyEntityToModelMunicipioImss(ditPatronSujetoObligado.getDitMunicipioPatSujOblig());
			return municipio;
		}
		
		
		return null;
	}

	private void updateLlavePatron(DitPatronSujetoObligado ditPatronSujetoObligado) {
		// Se actualiza la llave Patronal
		List<DitPatronGeneral> patronGrals = ditPatronSujetoObligado.getDitPatronGenerals();
		DitPatronGeneral patrongeneral = patronGrals.get(0);
		String nrp = patrongeneral.getRegPatron();
		DicModalidad dicModalidad = ditPatronSujetoObligado.getDicModalidad();
		nrp = nrp + dicModalidad.getNumModalidad();

		DitLlavePatron ditLlavePatron = em.find(DitLlavePatron.class, nrp);
		if (ditLlavePatron == null) {
			ditLlavePatron = new DitLlavePatron();
			ditLlavePatron.setRefBusca(nrp);
		}
		ditLlavePatron.setDitPatronSujetoObligado(ditPatronSujetoObligado);
		ditLlavePatron.setDitPatronGeneral(patrongeneral);

		DicTipoPersona dicTipoPersona = new DicTipoPersona();
		if (ditPatronSujetoObligado.getDitPersonaFisica() != null) {
			dicTipoPersona.setCveIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);

			ditLlavePatron.setDitPersonaFisica(ditPatronSujetoObligado.getDitPersonaFisica());
			ditLlavePatron.setDitPersona(ditPatronSujetoObligado.getDitPersonaFisica().getDitPersona());
			ditLlavePatron.setDitPersonaMoral(null);
		} else {
			dicTipoPersona.setCveIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);

			ditLlavePatron.setDitPersonaFisica(null);
			ditLlavePatron.setDitPersona(null);
			ditLlavePatron.setDitPersonaMoral(ditPatronSujetoObligado.getDitPersonaMoral());
		}
		ditLlavePatron.setDicTipoPersona(dicTipoPersona);

		em.merge(ditLlavePatron);
	}
	
	@Override
	public List<MedioContacto> consultarMediosContactoPorIdPatron(Long idPatron){
		List<MedioContacto> medios = new ArrayList<MedioContacto>();
		StringBuffer query = new StringBuffer();
		query.append("Select ctContacto from DitCentroTrabajoContacto ctContacto where  ctContacto.id.cveIdPatronSujetoObligado =:idPatron");
		Query consulta=em.createQuery(query.toString());
		consulta.setParameter("idPatron", idPatron);
		List<DitCentroTrabajoContacto> mediosCt = consulta.getResultList();
		if(mediosCt!=null && mediosCt.size()>0)
			for(DitCentroTrabajoContacto medioCt : mediosCt)
				if(medioCt.getDitFormaContacto()!=null && medioCt.getDitFormaContacto().getCveIdFormaContacto()!=null)
					medios.add(sujetoObligadoUtility.convertirEntityToModelContacto(medioCt.getDitFormaContacto()));
		
		return medios;
	}
}
