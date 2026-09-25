package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import mx.gob.imss.ctirss.delta.derechohabientes.service.parser.MedicoEnTurnoParserServiceLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.AsentamientoParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.EntidadFederativaParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.EstadoCivilParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.ParentescoParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.SexoParser;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Parentesco;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoVialidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Vialidad;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.EstadoCivil;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo;
import mx.gob.imss.ctirss.delta.persistence.DgAsentamiento;
import mx.gob.imss.ctirss.delta.persistence.DgAsentamientoPK;
import mx.gob.imss.ctirss.delta.persistence.DgCatEstado;
import mx.gob.imss.ctirss.delta.persistence.DgVialidad;
import mx.gob.imss.ctirss.delta.persistence.DicCalidadParentesco;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoCivil;
import mx.gob.imss.ctirss.delta.persistence.DicSexo;
import mx.gob.imss.ctirss.delta.persistence.DitUmfConsTurnoMedico;

@Stateless( name = "descripcionesDao", mappedName = "descripcionesDao")
public class DescripcionesDao extends AbstractServiceEntity implements DescripcionesDaoLocal {
	
	@EJB MedicoEnTurnoParserServiceLocal medicoEnTurnoParserServiceLocal;
	
	@Override
	public Sexo getSexo(Long idSexo)  throws DerechohabientesBusinessException{
		DicSexo sexo = null;
		try {
			sexo = em.find(DicSexo.class, idSexo);
		} catch (NoResultException e) {
			log.debug("No fue localizada la descripcion del sexo", e);
		} catch (Exception e) {
			log.error("No fue posible recuperar la descripcion del sexo", e);
			DerechohabientesBusinessException.throwException(e.getCause().getMessage(),"error.buscar.personales");
		}
		
		return SexoParser.persisToModel(sexo);
	}

	
	@Override
	public Parentesco getParentesco(Long idParentesco)
			throws DerechohabientesBusinessException {
		DicCalidadParentesco parentesco = null;
		try {
			parentesco = em.find(DicCalidadParentesco.class, idParentesco);
		} catch(NoResultException e) {
			log.debug("No fue localizada la descripcion del parentesco", e);
		} catch (Exception e) {
			log.error("No fue localizada la descripcion del parentesco", e);
			DerechohabientesBusinessException.throwException(e.getCause().getMessage(), "error.buscar.personales");
		}
		
		return ParentescoParser.persisToModel(parentesco);
	}


	@Override
	public EstadoCivil getEstadoCivil(Long idEstadoCivil)  throws DerechohabientesBusinessException{
		DicEstadoCivil estadoCivil = null;
		try {
			estadoCivil = em.find(DicEstadoCivil.class, idEstadoCivil);
		} catch(NoResultException e) {
			log.debug("No fue localizada la descripcion del estado civil", e);
		} catch (Exception e) {
			log.error("No fue localizada la descripcion del estado civil", e);
			DerechohabientesBusinessException.throwException(e.getCause().getMessage(), "error.buscar.personales");
		}
		
		return EstadoCivilParser.persisToModel(estadoCivil);
	}

	@Override
	public EntidadFederativa getEntidadFederativa(String idEntidadFederativa)  throws DerechohabientesBusinessException{
		DgCatEstado estado = null;
		try{
			estado = em.find(DgCatEstado.class, idEntidadFederativa);
		} catch(NoResultException e) {
			log.debug("No fue localizada la descripcion dela entidad federativa", e);
		} catch (Exception e) {
			log.error("No fue localizada la descripcion dela entidad federativa", e);
			DerechohabientesBusinessException.throwException(e.getCause().getMessage(), "error.buscar.asentamiento");
		}
		return EntidadFederativaParser.persisToModel(estado);
	}

	@Override
	public Asentamiento getAsentamiento(Asentamiento asentamiento)  throws DerechohabientesBusinessException{
		DgAsentamientoPK pk = new DgAsentamientoPK();
		pk.setCveAsen(asentamiento.getClave());
		pk.setCveEnt(asentamiento.getLocalidad().getMunicipio().getEntidadFederativa().getClave());
		//pk.setCveLoc(asentamiento.getLocalidad().getClave());
		pk.setCveMun(asentamiento.getLocalidad().getMunicipio().getClave());
		//pk.setCvePeriodo(1);
		DgAsentamiento asenta = null;
		try {
			asenta = em.find(DgAsentamiento.class, pk);
		} catch(NoResultException e) {
			log.debug("No se encontro el asentamiento", e);
		} catch(Exception e) {
			log.error("No se pudo recuperar el asentamiento", e);
			DerechohabientesBusinessException.throwException(e.getCause().getMessage(), "error.buscar.asentamiento");
		}
		
		return AsentamientoParser.persisToModel(asenta);
	}

	@Override
	public Vialidad getVialidad(Integer idVialidad) throws DerechohabientesBusinessException {
		DgVialidad vialidad = null;
		
		if(idVialidad != null) {
			try {
				vialidad = em.find(DgVialidad.class, idVialidad);
			} catch(NoResultException e) {
				log.debug("No se encontro la vialidad", e);
			} catch(Exception e) {
				log.error("No se pudo recuperar la vialidad", e);
				DerechohabientesBusinessException.throwException(e.getCause().getMessage(), "error.buscar.asentamiento");
			}
		}
		Vialidad calle = null;
		
		if(vialidad != null){
			calle= new Vialidad();
			calle.setClave(vialidad.getCveVia());
			calle.setNombre(vialidad.getNomVia());
			calle.setTipoVialidad(new TipoVialidad());
			calle.getTipoVialidad().setClave(vialidad.getDgCatVialidad().getCveTipoVial());
			calle.getTipoVialidad().setDescripcion(vialidad.getDgCatVialidad().getDescripcion());
		}
		return calle;
	}

	@Override
	public MedicoEnTurno getMedicoEnTurno(MedicoEnTurno medico) throws DerechohabientesBusinessException {
		
		DitUmfConsTurnoMedico medicoe = null;
		try {
			medicoe = em.find(DitUmfConsTurnoMedico.class, medico.getIdMedicoContultorioTurno());
		} catch(NoResultException e) {
			log.debug("No se encontro el medico en turno", e);
		} catch(Exception e) {
			log.error("No se encontro el medico en turno", e);
			DerechohabientesBusinessException.throwException(e.getCause().getMessage(), "error.buscar.asentamiento");
		}
		return medicoEnTurnoParserServiceLocal.persisToModel(medicoe);
	}
	
	
	
}
