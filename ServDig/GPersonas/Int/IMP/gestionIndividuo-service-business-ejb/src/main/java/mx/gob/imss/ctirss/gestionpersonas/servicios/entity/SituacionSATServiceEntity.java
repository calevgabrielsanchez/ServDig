package mx.gob.imss.ctirss.gestionpersonas.servicios.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.individuo.situacionSAT.SituacionSATNoValidaException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.framework.exceptions.TransformacionException;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.SituacionSAT;
import mx.gob.imss.ctirss.delta.persistence.DitSituacionSat;
import mx.gob.imss.ctirss.gestionpersonas.servicios.utility.SituacionSatServiceUtilityLocal;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

@Stateless(mappedName = "situacionSATServiceEntity")
public class SituacionSATServiceEntity extends AbstractServiceEntity implements
		SituacionSATServiceEntityLocal {

	@EJB
	private SituacionSatServiceUtilityLocal situacionSatServiceUtility;

	@Override
	public SituacionSAT guardar(SituacionSAT situacionSAT)
			throws SituacionSATNoValidaException {

		this.log.debug("Guardando la situacionSAT");
		
		try {
			DitSituacionSat entity = this.situacionSatServiceUtility
					.transformarSituacionSat(situacionSAT);
			
			Date fechaActual = new Date();
			entity.setFecRegistroAlta(fechaActual);
			entity.setFecRegistroActualizado(fechaActual);
			
			this.em.persist(entity);

			situacionSAT.setIdSituacionSAT(entity.getCveSituacionSat());
		} catch (TransformacionException e) {
			this.log.warn(e);
			throw new SituacionSATNoValidaException(
					"No se puede guardar la situación debido a: "
							+ e.getMessage());
		}

		return situacionSAT;
	}

	@Override
	public void expirar(SituacionSAT situacionSAT)
			throws SituacionSATNoValidaException {
		
		this.log.debug("Expirando la situacionSAT [cveSituacionSAT="
				+ situacionSAT.getCveSituacionSAT() + "]");
		
		DitSituacionSat entity = this.em.find(DitSituacionSat.class,
				situacionSAT.getIdSituacionSAT().longValue());
		
		entity.setFecRegistroBaja(new Date());
	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SituacionSAT> obtenerSituacionesPersona(Persona persona,
			boolean obtenerActivas) throws SituacionSATNoValidaException {

		Long cvePersona = null;
		boolean isFisica = true;
		
		if (persona != null) {
			if (persona instanceof Fisica
					|| (persona.getTipoPersona() != null && persona
							.getTipoPersona().getIdTipoPersona().longValue() == TipoPersonaEnum.FISICA
							.getId())) {
				cvePersona = ((Fisica) persona).getCveFisica();
			} else if (persona instanceof Moral
					|| (persona.getTipoPersona() != null && persona.getTipoPersona()
							.getIdTipoPersona().longValue() == TipoPersonaEnum.MORAL
							.getId())) {
				cvePersona = ((Moral) persona).getCveMoral();
				isFisica = false;
			}
		} else {
			throw new SituacionSATNoValidaException(
					"No se puede obtener la lista de situaciones SAT, ya que la persona es requerida");
		}

		if(cvePersona == null){
			throw new SituacionSATNoValidaException(
					"No se puede obtener la lista de situaciones SAT, ya que la persona es requerida");
		}
		
		this.log.debug("Obteniendo las situaciones SAT de la persona [cvePersona=" + cvePersona + "]");
		
		List<SituacionSAT> situaciones = null;
		
		Criteria criteria = this.getSession().createCriteria(
				DitSituacionSat.class);
		
		if (isFisica) {
			criteria.add(Restrictions.eq("ditPersonaFisica.cveIdPersonaFisica",
					cvePersona));
		} else {
			criteria.add(Restrictions.eq("ditPersonaMoral.cveIdPersonaMoral",
					cvePersona));
		}
		
		if(obtenerActivas){
			criteria.add(Restrictions.isNull("fecRegistroBaja"));
		}
		
		List<DitSituacionSat> situacionesEntity = criteria.list();
		
		if(!situacionesEntity.isEmpty()){
			situaciones = new ArrayList<SituacionSAT>();
			for(DitSituacionSat entity : situacionesEntity){
				try {
					situaciones.add(this.situacionSatServiceUtility
							.transformarSituacionSat(entity));
				} catch (TransformacionException e) {
					this.log.warn(e);
					throw new SituacionSATNoValidaException(
							"No se puede obtener la lista de situaciones debido a: "
									+ e.getMessage());
				}
			}
		}

		return situaciones;
	}
}
