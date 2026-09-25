package mx.gob.imss.ctirss.gestionpersonas.servicios.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.individuo.DatosPersonaSATNoValidosException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.framework.exceptions.TransformacionException;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.DatosPersonaSAT;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.persistence.DitDatosPersonaSat;
import mx.gob.imss.ctirss.gestionpersonas.servicios.utility.DatosPersonaSATServiceUtilityLocal;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

@Stateless(mappedName = "datosPersonaSATServiceEntity")
public class DatosPersonaSATServiceEntity extends AbstractServiceEntity
		implements DatosPersonaSATServiceEntityLocal {

	@EJB
	private DatosPersonaSATServiceUtilityLocal datosPersonaSATServiceUtility;

	@SuppressWarnings("unchecked")
	@Override
	public void guardar(DatosPersonaSAT datosPersonaSAT)
			throws DatosPersonaSATNoValidosException {

		Persona persona = datosPersonaSAT.getPersona();
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
			throw new DatosPersonaSATNoValidosException(
					"No se puede guardar los datos SAT, ya que la persona es requerida");
		}

		if(cvePersona == null){
			throw new DatosPersonaSATNoValidosException(
					"No se puede guardar los datos SAT, ya que la persona es requerida");
		}
		
		this.log.debug("Obteniendo los datos SAT de la persona [cvePersona=" + cvePersona + "]");
		
		Criteria criteria = this.getSession().createCriteria(
				DitDatosPersonaSat.class);
		
		if (isFisica) {
			criteria.add(Restrictions.eq("ditPersonaFisica.cveIdPersonaFisica",
					cvePersona));
		} else {
			criteria.add(Restrictions.eq("ditPersonaMoral.cveIdPersonaMoral",
					cvePersona));
		}
		
		List<DitDatosPersonaSat> ditDatosSAT = criteria.list();
		
		if(!ditDatosSAT.isEmpty()){
			// Se modifican las fechas existentes
			DitDatosPersonaSat ditDatosPersonaSat = ditDatosSAT.get(0);
			
			this.log.debug("Se van a modificar los datos SAT [cveIdDatosSat = " + ditDatosPersonaSat.getCveIdDatosSat() + "]");
			
			ditDatosPersonaSat.setFecConstitucion(datosPersonaSAT.getFechaConstitucion());
			ditDatosPersonaSat.setFecInicioOperaciones(datosPersonaSAT.getFechaInicioOperaciones());
			ditDatosPersonaSat.setFecRegistroActualizado(new Date());
		} else {
			// Se guarda como nuevo
			DitDatosPersonaSat ditDatosPersonaSat;
			
			try {
				ditDatosPersonaSat = this.datosPersonaSATServiceUtility.transformarDatosPersonaSAT(datosPersonaSAT);
				ditDatosPersonaSat.setFecRegistroAlta(new Date());
			} catch (TransformacionException e) {
				this.log.warn(e);
				throw new DatosPersonaSATNoValidosException(
						"No se puede guardar los datos SAT debido a: "
								+ e.getMessage());
			}
			
			this.log.debug("Se va a guardar nuevos datos SAT de la persona [cvePersona = " + cvePersona + "]");
			this.em.persist(ditDatosPersonaSat);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public DatosPersonaSAT obtenerDatosSAT(Persona persona)
			throws DatosPersonaSATNoValidosException {
		
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
			throw new DatosPersonaSATNoValidosException(
					"No se puede obtener los datos SAT, ya que la persona es requerida");
		}

		if(cvePersona == null){
			throw new DatosPersonaSATNoValidosException(
					"No se puede obtener los datos SAT, ya que la persona es requerida");
		}
		
		this.log.debug("Obteniendo  los datos SAT de la persona [cvePersona=" + cvePersona + "]");
		
		List<DatosPersonaSAT> datosSATList = null;
		DatosPersonaSAT datosPersonaSAT = null;
		
		Criteria criteria = this.getSession().createCriteria(
				DitDatosPersonaSat.class);
		
		if (isFisica) {
			criteria.add(Restrictions.eq("ditPersonaFisica.cveIdPersonaFisica",
					cvePersona));
		} else {
			criteria.add(Restrictions.eq("ditPersonaMoral.cveIdPersonaMoral",
					cvePersona));
		}
		
		
		List<DitDatosPersonaSat> ditDatosSAT = criteria.list();
		
		if(!ditDatosSAT.isEmpty()){
			datosSATList = new ArrayList<DatosPersonaSAT>();
			for(DitDatosPersonaSat entity : ditDatosSAT){
				try {
					datosSATList.add(this.datosPersonaSATServiceUtility
							.transformarDatosPersonaSAT(entity));
				} catch (TransformacionException e) {
					this.log.warn(e);
					throw new DatosPersonaSATNoValidosException(
							"No se puede obtener los datos SAT debido a: "
									+ e.getMessage());
				}
			}
			datosPersonaSAT = datosSATList.get(0);
		}
		
		return datosPersonaSAT;
	}

}
