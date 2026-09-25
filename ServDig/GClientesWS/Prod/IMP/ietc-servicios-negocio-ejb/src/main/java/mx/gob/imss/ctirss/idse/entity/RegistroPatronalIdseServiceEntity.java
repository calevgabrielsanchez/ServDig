package mx.gob.imss.ctirss.idse.entity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import mx.gob.imss.ctirss.idse.model.RegistroPatronal;
import mx.gob.imss.ctirss.idse.model.enums.AccionMovimientosEnum;
import mx.gob.imss.ctirss.idse.model.enums.EstadoFielEnum;
import mx.gob.imss.ctirss.idse.model.enums.EstadoRegistroPatronalEnum;
import mx.gob.imss.ctirss.idse.model.enums.EstadoRelacionEnum;
import mx.gob.imss.ctirss.idse.model.enums.OrigenMovimientoEnum;
import mx.gob.imss.ctirss.idse.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.idse.persistencia.IdcAccionMovimiento;
import mx.gob.imss.ctirss.idse.persistencia.IdcEstatusRegPat;
import mx.gob.imss.ctirss.idse.persistencia.IdcEstatusRelacion;
import mx.gob.imss.ctirss.idse.persistencia.IdrBloqueRegistro;
import mx.gob.imss.ctirss.idse.persistencia.IdrPersonasFisica;
import mx.gob.imss.ctirss.idse.persistencia.IdrPersonasMorale;
import mx.gob.imss.ctirss.idse.persistencia.IdtCartasEmpresa;
import mx.gob.imss.ctirss.idse.persistencia.IdtHistoricoMovimiento;
import mx.gob.imss.ctirss.idse.persistencia.IdtRegistrosPatronale;
import mx.gob.imss.ctirss.idse.persistencia.IdtRepresentado;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

@Stateless(mappedName = "registroPatronalIdseServiceEntity", name = "registroPatronalIdseServiceEntity")
public class RegistroPatronalIdseServiceEntity 
	extends IETCAbstractEntity
	implements RegistroPatronalIdseServiceEntityLocal {

	protected final Log log = LogFactory.getLog(getClass());

	public IdrPersonasFisica obtenerPersonaFisica(String rfc, String serial){
		StringBuffer jpaQuery = new StringBuffer();
		jpaQuery.append("SELECT t from IdrPersonasFisica t ");
		jpaQuery.append("WHERE t.idtDatosCertificado.id.refRfcAsociado = :rfc ");
		if(serial!=null)
			jpaQuery.append("AND t.idtDatosCertificado.id.cveSerialFiel = :serial ");
		
		jpaQuery.append("AND t.idtDatosCertificado.idcEstatusFiel.cveEstatusFiel = :fielActiva ");		
		Query query = this.em.createQuery(jpaQuery.toString());
		query.setParameter("rfc", rfc);
		query.setParameter("fielActiva", EstadoFielEnum.ACTIVA.getId());
		if(serial!=null)
			query.setParameter("serial", serial);
				
		try{
			IdrPersonasFisica personaFisica = (IdrPersonasFisica)query.getSingleResult();
			return personaFisica;
		}catch(NoResultException e){
			return null;
		}
	}
	
	public IdrPersonasMorale obtenerPersonaMoral(String rfc, String serial){
		StringBuffer jpaQuery = new StringBuffer();
		jpaQuery.append("SELECT t from IdrPersonasMorale t ");
		jpaQuery.append("WHERE t.idtDatosCertificado.id.refRfcAsociado = :rfc ");
		if(serial!=null)
			jpaQuery.append("AND t.idtDatosCertificado.id.cveSerialFiel = :serial ");
		
		jpaQuery.append("AND t.idtDatosCertificado.idcEstatusFiel.cveEstatusFiel = :fielActiva ");		
		Query query = this.em.createQuery(jpaQuery.toString());
		query.setParameter("rfc", rfc);
		query.setParameter("fielActiva", EstadoFielEnum.ACTIVA.getId());
		if(serial!=null)
			query.setParameter("serial", serial);
								
		try{
			IdrPersonasMorale personaMoral = (IdrPersonasMorale)query.getSingleResult();
			return personaMoral;
		}catch(NoResultException e){
			return null;
		}
		
	}
	
	public IdtRegistrosPatronale obtenerRegistroPatronal(String nrp, 
			TipoPersonaEnum tipoPersonaPatron, Long cvePatron){
		StringBuffer jpaQuery = new StringBuffer();
		jpaQuery.append("SELECT t from IdtRegistrosPatronale t ");
		jpaQuery.append("WHERE t.refRegistroPatronal = :nrp ");
		if(tipoPersonaPatron.equals(TipoPersonaEnum.FISICA)){
			jpaQuery.append("AND t.idrPersonasFisica.cvePersonaFisica = :cvePatron ");
		}else{
			jpaQuery.append("AND t.idrPersonasMorale.cvePersonaMoral = :cvePatron ");
		}
		jpaQuery.append("AND t.idcEstatusRegPat.cveEstatusRegPat = :rpActivo ");
		
		Query query = this.em.createQuery(jpaQuery.toString());
		query.setParameter("nrp", nrp);
		query.setParameter("cvePatron", cvePatron);
		query.setParameter("rpActivo", EstadoRegistroPatronalEnum.ACTIVADO.getId());
		try{
			IdtRegistrosPatronale rp = (IdtRegistrosPatronale)query.getSingleResult();
			return rp;
		}catch(NoResultException e){
			return null;
		}
	}
	
	public IdtRepresentado obtenerRepresentanteLegal(Long cvePersonaFisica, 
			TipoPersonaEnum tipoPersonaPatron, Long cvePatron){
		StringBuffer jpaQuery = new StringBuffer();
		jpaQuery.append("SELECT t from IdtRepresentado t ");
		jpaQuery.append("WHERE t.representanteLegal.cvePersonaFisica = :cveRepresentante ");
		if(tipoPersonaPatron.equals(TipoPersonaEnum.FISICA)){
			jpaQuery.append("AND t.patronFisica.cvePersonaFisica = :cvePatron ");
		}else{
			jpaQuery.append("AND t.patronMoral.cvePersonaMoral = :cvePatron ");
		}
		jpaQuery.append("AND t.idcEstatusRelacion.cveEstatusRelacion = :relacionActiva ");
		
		Query query = this.em.createQuery(jpaQuery.toString());
		query.setParameter("cveRepresentante", cvePersonaFisica);
		query.setParameter("cvePatron", cvePatron);
		query.setParameter("relacionActiva", EstadoRelacionEnum.ACTIVA.getId());
		try{
			IdtRepresentado rl = (IdtRepresentado)query.getSingleResult();
			return rl;
		}catch(NoResultException e){
			return null;
		}		
	}
	
	@SuppressWarnings("unchecked")
	public List<IdtRepresentado> obtenerRepresentantesLegalesAsociados(
			TipoPersonaEnum tipoPersonaPatron, Long cvePatron, Long cvePFRepresentante){
		StringBuffer jpaQuery = new StringBuffer();
		jpaQuery.append("SELECT t from IdtRepresentado t  WHERE ");
		if(tipoPersonaPatron.equals(TipoPersonaEnum.FISICA)){
			jpaQuery.append("t.patronFisica.cvePersonaFisica = :cvePatron ");
		}else{
			jpaQuery.append("t.patronMoral.cvePersonaMoral = :cvePatron ");
		}
		jpaQuery.append("AND t.idcEstatusRelacion.cveEstatusRelacion = :relacionActiva ");
		if(cvePFRepresentante!=null)
			jpaQuery.append("AND t.representanteLegal.cvePersonaFisica <> :cveRepresentante ");
		
		Query query = this.em.createQuery(jpaQuery.toString());
		query.setParameter("cvePatron", cvePatron);
		query.setParameter("relacionActiva", EstadoRelacionEnum.ACTIVA.getId());
		if(cvePFRepresentante!=null)
			query.setParameter("cveRepresentante", cvePFRepresentante);
		
		List<IdtRepresentado> listaRepresentantes = query.getResultList();
		return listaRepresentantes;		
	}
	
	@SuppressWarnings("unchecked")
	public IdrBloqueRegistro obtenerBloqueRegistro(Long cveRegistroPatronal, Long cveRepresentado){
		StringBuffer jpaQuery = new StringBuffer();
		jpaQuery.append("SELECT t from IdrBloqueRegistro t ");
		jpaQuery.append("WHERE t.idtRegistrosPatronale.cveRegistroPatronal = :cveRegistroPatronal ");
		jpaQuery.append("AND t.idtRepresentado.cveRepresentados = :cveRepresentado ");
		jpaQuery.append("AND t.idcEstatusRelacion.cveEstatusRelacion = :relacionActiva ");
		
		Query query = this.em.createQuery(jpaQuery.toString());
		query.setParameter("cveRegistroPatronal", cveRegistroPatronal);
		query.setParameter("cveRepresentado", cveRepresentado);
		query.setParameter("relacionActiva", EstadoRelacionEnum.ACTIVA.getId());
		List<IdrBloqueRegistro> bloques = query.getResultList();
		if(!CollectionUtils.isEmpty(bloques)){
			return bloques.get(0);
		}		
		return null;
	}
	
	public IdrPersonasFisica guardarPersonaFisicia(IdrPersonasFisica idrPersonasFisica){
		this.em.persist(idrPersonasFisica);
		return idrPersonasFisica;
	}
			
	public IdrPersonasMorale guardarPersonaMoral(IdrPersonasMorale idrPersonasMoral){
		this.em.persist(idrPersonasMoral);
		return idrPersonasMoral;
	}
	
	public IdtRegistrosPatronale guardarRegistroPatronal(IdtRegistrosPatronale idtRegistroPatronal){
		IdtCartasEmpresa cartaEmpresa = null;
		if(idtRegistroPatronal.getIdtCartasEmpresa()!=null){
			cartaEmpresa = (IdtCartasEmpresa)idtRegistroPatronal.getIdtCartasEmpresa().clone();			
		}
		idtRegistroPatronal.setIdtCartasEmpresa(null);
		//Guardar Registro Patronal
		this.em.persist(idtRegistroPatronal);
		
		//Se requiere de cveRegistroPatronal para almacenar carta.
		if(cartaEmpresa!=null){
			cartaEmpresa.setCveRegistroPatronal(idtRegistroPatronal.getCveRegistroPatronal());
			guardarCartasEmpresa(cartaEmpresa);
		}
		
		return idtRegistroPatronal;
	}
	
	public IdtRepresentado guardarRepresentado(IdtRepresentado idtRepresentado){
		this.em.persist(idtRepresentado);
		return idtRepresentado;
	}
	
	public IdrBloqueRegistro guardarBloqueRegistro(IdrBloqueRegistro idrBloqueRegistro){
		this.em.persist(idrBloqueRegistro);
		return idrBloqueRegistro;
	}

	public IdtHistoricoMovimiento guardarHistoricoMovimiento(IdtHistoricoMovimiento idtHistoricoMovimiento){
		idtHistoricoMovimiento.setRefUsuarioAfecta("PDigIMSS");
		this.em.persist(idtHistoricoMovimiento);
		return idtHistoricoMovimiento;
	}
	
	public IdtCartasEmpresa guardarCartasEmpresa(IdtCartasEmpresa idtCartasEmpresa){
		this.em.persist(idtCartasEmpresa);
		return idtCartasEmpresa;
	}
	
	public void desasociarRegistroPatronal(RegistroPatronal registroPatronal){
		TipoPersonaEnum tipoPersonaPatron = TipoPersonaEnum
			.parse(registroPatronal.getPatron().getTipoPersona());
		IdtRegistrosPatronale idtRegistroPatronal = obtenerRegistroPatronal(registroPatronal.getNrp(), 
			tipoPersonaPatron, registroPatronal.getPatron().getCvePersona());
		OrigenMovimientoEnum origen = OrigenMovimientoEnum
			.parse(registroPatronal.getIdOrigenSolicitud());
		if(idtRegistroPatronal!=null){
			//Desactivar RP
			idtRegistroPatronal.setIdcEstatusRegPat(new IdcEstatusRegPat());
			idtRegistroPatronal.getIdcEstatusRegPat().setCveEstatusRegPat(EstadoRegistroPatronalEnum.CANCELADO.getId());
			idtRegistroPatronal.getIdrBloqueRegistros();
			//Desactivar relacion RL-RP si existe
			if(!CollectionUtils.isEmpty(idtRegistroPatronal.getIdrBloqueRegistros())){
				for(IdrBloqueRegistro bloqueRegistro : idtRegistroPatronal.getIdrBloqueRegistros()){
					bloqueRegistro.setIdcEstatusRelacion(new IdcEstatusRelacion());
					bloqueRegistro.getIdcEstatusRelacion().setCveEstatusRelacion(EstadoRelacionEnum.INACTIVA.getId());
				}
			}
			AccionMovimientosEnum movimiento = null;
			if(origen.equals(OrigenMovimientoEnum.INTERNET)){
				movimiento = AccionMovimientosEnum.DISOSIACION_REGISTRO_PATRONAL;				
			}else{
				movimiento = AccionMovimientosEnum.DISOSIACION_REGISTRO_PATRONAL_VENTANILLA;
			}
			guardarHistoricoMovimiento(getHistoricoMovimiento(idtRegistroPatronal.getCveRegistroPatronal(), 
					registroPatronal.getPatron().getRfc(), movimiento.getId()));
		}else{
			log.info("No se encontro el registro patronal, no se procesa desasociación de RP.");
		}
	}
	
	@SuppressWarnings("unchecked")
	public void desasociarRepresentanteLegal(Long cvePersonaFisica, TipoPersonaEnum tipoPersonaPatron, 
			Long cvePatron, String usuarioAfecta, OrigenMovimientoEnum origen){
		StringBuffer jpaQuery = new StringBuffer();
		jpaQuery.append("SELECT t from IdtRepresentado t ");
		jpaQuery.append("WHERE t.representanteLegal.cvePersonaFisica = :cveRepresentante ");
		if(tipoPersonaPatron.equals(TipoPersonaEnum.FISICA)){
			jpaQuery.append("AND t.patronFisica.cvePersonaFisica = :cvePatron ");
		}else{
			jpaQuery.append("AND t.patronMoral.cvePersonaMoral = :cvePatron ");
		}
		jpaQuery.append("AND t.idcEstatusRelacion.cveEstatusRelacion = :relacionActiva ");
		
		Query query = this.em.createQuery(jpaQuery.toString());
		query.setParameter("cveRepresentante", cvePersonaFisica);
		query.setParameter("cvePatron", cvePatron);
		query.setParameter("relacionActiva", EstadoRelacionEnum.ACTIVA.getId());
		List<IdtRepresentado> representados = query.getResultList();
		if(!CollectionUtils.isEmpty(representados)){
			for(IdtRepresentado idtRepresentado : representados){
				idtRepresentado.setIdcEstatusRelacion(new IdcEstatusRelacion());	
				idtRepresentado.getIdcEstatusRelacion().setCveEstatusRelacion(EstadoRelacionEnum.INACTIVA.getId());	
				idtRepresentado.getIdrBloqueRegistros();
				if(!CollectionUtils.isEmpty(idtRepresentado.getIdrBloqueRegistros())){
					for(IdrBloqueRegistro idrBloqueRegistro : idtRepresentado.getIdrBloqueRegistros()){
						//Procesar desasosiacion de bloque solo para activos
						if(idrBloqueRegistro.getIdcEstatusRelacion()!=null && idrBloqueRegistro.getIdcEstatusRelacion()
								.getCveEstatusRelacion()==EstadoRelacionEnum.ACTIVA.getId()){
							idrBloqueRegistro.setIdcEstatusRelacion(new IdcEstatusRelacion());
							idrBloqueRegistro.getIdcEstatusRelacion().setCveEstatusRelacion(EstadoRelacionEnum.INACTIVA.getId());
							idrBloqueRegistro.getIdtRegistrosPatronale();
							Long cveRP = idrBloqueRegistro.getIdtRegistrosPatronale().getCveRegistroPatronal();
							AccionMovimientosEnum movimiento = null;
							if(origen.equals(OrigenMovimientoEnum.INTERNET)){
								movimiento = AccionMovimientosEnum.DISOSIACION_REPRESENTANTE_LEGAL;
							}else{
								movimiento = AccionMovimientosEnum.DISOSIACION_REPRESENTANTE_LEGAL_VENTANILLA;								
							}
							guardarHistoricoMovimiento(getHistoricoMovimiento(cveRP, usuarioAfecta,movimiento.getId()));
						}
					}
				}else{
					log.info("No se encontro relación de bloques o estan inactivos, sólo se inactiva representado");
				}
			}
		}else{
			log.info("No se encontro el representado, no se procesa desasociación de RL.");
		}
	}

	@SuppressWarnings("unchecked")
	public void asociarRepresentanteLegal(TipoPersonaEnum tipoPersonaPatron, Long cvePatron, 
			Long cveRepresentado, String usuarioAfecta, OrigenMovimientoEnum origen){
		StringBuffer jpaQuery = new StringBuffer();
		jpaQuery.append("SELECT t from IdtRegistrosPatronale t ");
		jpaQuery.append("WHERE ");
		if(tipoPersonaPatron.equals(TipoPersonaEnum.FISICA)){
			jpaQuery.append(" t.idrPersonasFisica.cvePersonaFisica = :cvePatron ");
		}else{
			jpaQuery.append(" t.idrPersonasMorale.cvePersonaMoral = :cvePatron ");
		}
		jpaQuery.append("AND t.idcEstatusRegPat.cveEstatusRegPat = :rpActivo ");
		
		Query query = this.em.createQuery(jpaQuery.toString());
		query.setParameter("rpActivo", EstadoRegistroPatronalEnum.ACTIVADO.getId());
		query.setParameter("cvePatron", cvePatron);		
		List<IdtRegistrosPatronale> registrosPatronales = query.getResultList();
		if(!CollectionUtils.isEmpty(registrosPatronales)){
			for(IdtRegistrosPatronale registrosPatronal : registrosPatronales){	
				//Procesar sino existe o no esta activa la relacion bloque (representado-rp).
				if(obtenerBloqueRegistro(registrosPatronal.getCveRegistroPatronal(), cveRepresentado)==null){					
					AccionMovimientosEnum movimiento = null;
					if(origen.equals(OrigenMovimientoEnum.INTERNET)){
						movimiento = AccionMovimientosEnum.ADHESION_REPRESENTANTE_LEGAL;
					}else{
						movimiento = AccionMovimientosEnum.ADHESION_REPRESENTANTE_LEGAL_VENTANILLA;						
					}					
					guardarHistoricoMovimiento(getHistoricoMovimiento(
						registrosPatronal.getCveRegistroPatronal(), usuarioAfecta,movimiento.getId()));
					try{
						guardarBloqueRegistro(getBloque(registrosPatronal.getCveRegistroPatronal(), cveRepresentado));
					}catch(Exception e){
						log.error(e.getMessage());
					}
				}else{
					log.info("Ya existe la relacion del bloque Representado-RP. No se procesa información.");
				}
			}
		}else{
			log.info("No se encontro registro patronal para procesar asociasión de RL.");
		}
	}
	
	private IdrBloqueRegistro getBloque(Long cveRegistroPatronal, Long cveRepresentado){
		IdrBloqueRegistro bloque = new IdrBloqueRegistro();
		bloque.setIdcEstatusRelacion(new IdcEstatusRelacion());
		bloque.getIdcEstatusRelacion().setCveEstatusRelacion(EstadoRelacionEnum.ACTIVA.getId());
		bloque.setIdtRegistrosPatronale(new IdtRegistrosPatronale());
		bloque.getIdtRegistrosPatronale().setCveRegistroPatronal(cveRegistroPatronal);
		bloque.setIdtRepresentado(new IdtRepresentado());
		bloque.getIdtRepresentado().setCveRepresentados(cveRepresentado);
		return bloque;
	}
	
	private IdtHistoricoMovimiento getHistoricoMovimiento(Long cveRegistroPatronal, 
			String usuarioAfecta, long idAccionMovimiento){
		IdtHistoricoMovimiento movimiento = new IdtHistoricoMovimiento();
		movimiento.setRefUsuarioAfecta("PDigIMSS");
		movimiento.setStpFechaMovimiento(new Timestamp(new Date().getTime()));
		movimiento.setIdcAccionMovimiento(new IdcAccionMovimiento());
		movimiento.setIdtRegistrosPatronale(new IdtRegistrosPatronale());
		movimiento.getIdtRegistrosPatronale().setCveRegistroPatronal(cveRegistroPatronal);
		movimiento.getIdcAccionMovimiento().setCveAccionMovimiento(idAccionMovimiento);
		return movimiento;
	}
	
}
