/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:DomicilioServiceEntity.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.domicilio.service.entity.domicilio
 *  @Fecha:02/03/2012
 */
package mx.gob.imss.ctirss.delta.gestion.domicilio.service.entity.domicilio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import mx.gob.imss.ctirss.delta.exception.domicilio.AsentamientoNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.AsociarDomicilioException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.LocalidadNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.MunicipioImssNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.SubDelegacionNoLocalizadaException;
import mx.gob.imss.ctirss.delta.exception.domicilio.UmfNoLocalizadaException;
import mx.gob.imss.ctirss.delta.exception.domicilio.VialidadesNoLocalizadasException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.framework.exceptions.TransformacionException;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.utility.DomicilioServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.utility.UMFUtilityLocal;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioFiscal;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.domicilio.MunicipioIMSS;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoAmbito;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilioEnum;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoVialidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Vialidad;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.persistence.DgAsentamiento;
import mx.gob.imss.ctirss.delta.persistence.DgCatEstado;
import mx.gob.imss.ctirss.delta.persistence.DgCatLocalidad;
import mx.gob.imss.ctirss.delta.persistence.DgCodigosPostale;
import mx.gob.imss.ctirss.delta.persistence.DgDomicilioGeografico;
import mx.gob.imss.ctirss.delta.persistence.DgDomiciliosCamino;
import mx.gob.imss.ctirss.delta.persistence.DgDomiciliosCarretera;
import mx.gob.imss.ctirss.delta.persistence.DgVialidad;
import mx.gob.imss.ctirss.delta.persistence.DicDelegacion;
import mx.gob.imss.ctirss.delta.persistence.DicMunicipioImss;
import mx.gob.imss.ctirss.delta.persistence.DicSubdelegacion;
import mx.gob.imss.ctirss.delta.persistence.DicTipoDomicilio;
import mx.gob.imss.ctirss.delta.persistence.DicUmf;
import mx.gob.imss.ctirss.delta.persistence.DitDomicilioSat;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaFDomFiscal;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaFisica;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaMDomFiscal;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaMoral;
import mx.gob.imss.ctirss.delta.persistence.DitPersonafDom;
import mx.gob.imss.ctirss.delta.persistence.DitPersonamDom;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;

/**
 * @author Lucio Duran Silva
 *
 */
@Stateless
public class DomicilioServiceEntity extends AbstractServiceEntity implements
		DomicilioServiceEntityLocal {
	
	
	
	@EJB
	private DomicilioServiceUtilityLocal domicilioServiceUtilityLocal;
	@EJB
	private UMFUtilityLocal umfUtility;
	
	
	@Override
	public List<Asentamiento> getAsentamientoPorCodigoPosta(CodigoPostal codigo)
			throws DomicilioNoLocalizadoException {
		
		this.log.debug("consultando los asentamientos por codigo postal [" + codigo +"]");
		
		List<Asentamiento> asentamientos = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("select new mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento( asent.id.cveAsen,  asent.nomAsen , asent.id.cveEnt, asent.id.cveMun, estado.nomEnt,mun.nomMun )");
		sql.append(" from DgCodigosPostale code join code.dgAsentamiento as asent "
				+ " join asent.dgCatMunicipio as mun"
				+ " join mun.dgCatEstado as estado");
		sql.append(" where code.id.codigo = :codigo");
		sql.append(" order by asent.nomAsen");
		
		Query query = this.getSession().createQuery(sql.toString());
		query.setParameter("codigo", codigo.getCodigoPostal().toString());
		asentamientos = query.list();
		return asentamientos;
	}
	
	
	

	@Override
	public List<Asentamiento> getAsentamientoPorMunicipio(Municipio municipio)
			throws DomicilioNoLocalizadoException {
		this.log.debug("consultando los asentamientos por municipio  [" + municipio +"]");
		List<Asentamiento> asentamientos = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("select new mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento( asent.id.cveAsen, asent.nomAsen , asent.id.cveEnt, asent.id.cveMun )");
		sql.append(" from DgAsentamiento asent");
		sql.append(" where asent.id.cveMun = :municipio");
		sql.append(" and asent.id.cveEnt = :entidad");
		sql.append(" order by asent.nomAsen");
		
		Query query = this.getSession().createQuery(sql.toString());
		query.setParameter("municipio", municipio.getClave().toString());
		query.setParameter("entidad", municipio.getEntidadFederativa().getClave().toString());
		asentamientos = query.list();
		
		
		/*Se debe de obtener el codigo postal del asentamiento*/
		
		
		return asentamientos;
		
	}
	
	
	@Override
	public List<Localidad> getLocalidadesPorMunicipio(Municipio municipio)
			throws DomicilioNoLocalizadoException {
		this.log.debug("consultando las localidades por municipio  [" + municipio +"]");
		List<Localidad> localidades = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("select new mx.gob.imss.ctirss.delta.model.domicilio.Localidad( local.id.cveLoc, local.nomLoc , local.id.cveEnt, local.id.cveMun )");
		sql.append(" from DgCatLocalidad local");
		sql.append(" where local.id.cveMun = :municipio");
		sql.append(" and local.id.cveEnt = :entidad");
		sql.append(" order by local.nomLoc");
		
		Query query = this.getSession().createQuery(sql.toString());
		query.setParameter("municipio", municipio.getClave().toString());
		query.setParameter("entidad", municipio.getEntidadFederativa().getClave().toString());
		localidades = query.list();
		
		
		return localidades;
		
	}

	
	

	@Override
	public Asentamiento getAsentamiento(Asentamiento asentamiento)
			throws AsentamientoNoLocalizadoException {
		
		Asentamiento response = null;
		
		this.log.debug("Clave del asentamiento[" + asentamiento.getClave()+"]");
		this.log.debug("Clave del municipio [" + asentamiento.getLocalidad().getMunicipio().getClave()+"]");
		
		StringBuffer bfr = new StringBuffer();
		bfr.append(" from DgAsentamiento asent ");
		bfr.append(" where asent.id.cveAsen  = :cveAsen ");
		bfr.append(" and asent.id.cveEnt  = :cveEnt ");
		bfr.append(" and asent.id.cveMun  = :cveMun ");
		
		Query query = this.getSession().createQuery(bfr.toString());
		query.setParameter("cveAsen", asentamiento.getClave() );
		
		query.setParameter("cveMun", asentamiento.getLocalidad().getMunicipio().getClave());
		query.setParameter("cveEnt", asentamiento.getLocalidad().getMunicipio().getEntidadFederativa().getClave());
		
		
		DgAsentamiento dgAsentamiento =(DgAsentamiento) query.uniqueResult();
		
		try {
			response = domicilioServiceUtilityLocal
					.transformarAsentamiento(dgAsentamiento);
		} catch (TransformacionException e) {
			throw new AsentamientoNoLocalizadoException();
		}
		return response;
	}
	
	
	@Override
	public Localidad getLocalidad(Localidad localidad)
			throws LocalidadNoLocalizadoException {
		
		
		this.log.debug("Clave del localidad[" + localidad.getClave()+"]");
		this.log.debug("Clave del municipio [" + localidad.getMunicipio().getClave()+"]");
		
		Localidad response = null;
		DgCatLocalidad localidadRecuperada = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append(" from DgCatLocalidad local");
		sql.append(" where local.id.cveMun = :municipio");
		sql.append(" and local.id.cveEnt = :entidad");
		sql.append(" and local.id.cveLoc =:cveLocal");
		
		
		Query query = this.getSession().createQuery(sql.toString());
		query.setParameter("municipio", localidad.getMunicipio().getClave().toString());
		query.setParameter("entidad", localidad.getMunicipio().getEntidadFederativa().getClave().toString());
		query.setParameter("cveLocal", localidad.getClave().toString());
		
		localidadRecuperada = (DgCatLocalidad) query.uniqueResult();
		try {
			response = domicilioServiceUtilityLocal
					.transformarLocalidad(localidadRecuperada);
		} catch (TransformacionException e) {
			throw new LocalidadNoLocalizadoException();
		}
		
		return response;
	}
	
	
	
	


	/**
	 * 
	 * @param asentamiento
	 * @return
	 * @throws DomicilioNoLocalizadoException
	 */
	public CodigoPostal getCodigoPostalDeAsentamiento(Asentamiento asentamiento) throws DomicilioNoLocalizadoException{
		
		
		List <DgCodigosPostale> lstCodigo = null;
		CodigoPostal codigoPostal = new CodigoPostal();
		
		
		StringBuffer bfr = new StringBuffer();
		bfr.append(" from DgCodigosPostale code ");
		bfr.append(" where code.id.cveAsen = :cveAsen ");
		bfr.append(" and code.id.cveEnt = :cveEnt ");
		bfr.append(" and code.id.cveMun = :cveMun ");
		
		Query query = this.getSession().createQuery(bfr.toString());
		query.setParameter("cveAsen", asentamiento.getClave());
		query.setParameter("cveEnt", asentamiento.getLocalidad().getMunicipio().getEntidadFederativa().getClave());
		query.setParameter("cveMun", asentamiento.getLocalidad().getMunicipio().getClave());
		
/*TODO se cambio al forma de recuperar el codigo postal
 * requerie validar si se regresa el primer resultado, o se baja una lista de codigos postales a ubicar **/
				// DgCodigoPostale dgCodigo = query.uniqueResult()
				lstCodigo  = query.list();
		try {
			
			if(lstCodigo != null && lstCodigo.size()>0){
				//this.log.debug("voy por el codigo unico");
				codigoPostal = this.domicilioServiceUtilityLocal.transformarCodigoPostal(lstCodigo.get(0));	
			}
			
			
		} catch (TransformacionException e) {
			this.log.error(e.getMessage(), e);
			throw new DomicilioNoLocalizadoException();
			
		}
		
		
		return codigoPostal;
	}




	@Override
	public Domicilio guardarDomicilio(Domicilio domicilio)
			throws DomicilioNoValidoException {
		this.log.debug("guardarDomicilio [" + domicilio + "]");
		try {
			DgDomicilioGeografico dgDomicilio  =  this.domicilioServiceUtilityLocal.transformarDomicilio(domicilio);
			if(dgDomicilio == null){
				this.log.debug("Domicilio no se guardara ...");
			}else{
				
				Date fechaActual = new Date();
				dgDomicilio.setFechaHoraAlta(fechaActual);
				dgDomicilio.setFecRegistroAlta(fechaActual);
				
				DgDomiciliosCamino camino = dgDomicilio.getDgDomiciliosCamino();
				DgDomiciliosCarretera carretera = dgDomicilio.getDgDomiciliosCarretera();
				
				//se hace nula la referencia hacia los objetos de persistencia para salvarlos por separado
				dgDomicilio.setDgDomiciliosCamino(null);
				dgDomicilio.setDgDomiciliosCarretera(null);
				this.em.persist(dgDomicilio);
				this.em.flush();
				
				if(camino != null){
					camino.setDomicilioId(dgDomicilio.getDomicilioId());
					this.em.persist(camino);
				}
				if(carretera != null){
					carretera.setDomicilioId(dgDomicilio.getDomicilioId());
					this.em.persist(carretera);
				}
				
				this.em.flush();
				this.em.clear();
				domicilio.setClave(Long.valueOf(dgDomicilio.getDomicilioId()).intValue());
			}
			
		} catch (TransformacionException e) {
			log.debug("Error en guardarDomicilio(Domicilio domicilio): "+e.getMessage());
			throw new DomicilioNoValidoException(e.getMessage());
		}catch (RuntimeException e) {
			this.log.error(e.getMessage(), e);
			throw new DomicilioNoValidoException(e.getMessage());
		}
		
		return domicilio;
	}




	@Override
	public Domicilio consultarDomicilio(Domicilio domicilio)
			throws DomicilioNoLocalizadoException {
		
		Long domicilioId = domicilio.getClave().longValue();
		
		StringBuffer bfr = new StringBuffer();
		bfr.append(" from DgDomicilioGeografico dom ");
		bfr.append(" where dom.domicilioId = :domicilioId");
		
		
		Query query = this.getSession().createQuery(bfr.toString());
		query.setParameter("domicilioId", domicilioId);
		
		DgDomicilioGeografico dgDomicilio = (DgDomicilioGeografico) query.uniqueResult();
		
		try {
			domicilio = this.domicilioServiceUtilityLocal.transformarDomicilio(dgDomicilio);
		} catch (TransformacionException e) {
			throw new DomicilioNoLocalizadoException();
		}
		
		return domicilio;
	}




	@Override
	/**
	 * 
	 */
	public List<Vialidad> getVialidades(Localidad localidad)
			throws VialidadesNoLocalizadasException {
		
		
		this.log.debug("consultando las vialidades por localidad  [" + localidad +"]");
		List<Vialidad> vialidades = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("select new mx.gob.imss.ctirss.delta.model.domicilio.Vialidad(vialidad.cveVia , vialidad.nomVia, vialidad.dgCatVialidad.cveTipoVial, vialidad.dgCatVialidad.descripcion )");
		sql.append(" from DgVialidad vialidad");
		sql.append(" where vialidad.dgCatLocalidad.id.cveEnt = :cveEnt");
		sql.append(" and vialidad.dgCatLocalidad.id.cveMun = :cveMun");
		sql.append(" and vialidad.dgCatLocalidad.id.cveLoc = :cveLoc");
		sql.append(" order by vialidad.nomVia");
		
		Query query = this.getSession().createQuery(sql.toString());
		
		query.setParameter("cveEnt", localidad.getMunicipio().getEntidadFederativa().getClave());
		query.setParameter("cveMun", localidad.getMunicipio().getClave());
		query.setParameter("cveLoc", localidad.getClave());
		
		vialidades = query.list();
		
		return vialidades;
		
	}
	
	
	
	/**
	 * Obtiene las vialidades de una localidad y por el tipo especificado.
	 * @param localidad
	 * @param tipoVialidad
	 * @return
	 * @throws VialidadesNoLocalizadasException
	 */
	public List<Vialidad> getVialidadesPorTipoVialidad(Localidad localidad, TipoVialidad tipoVialidad)
			throws VialidadesNoLocalizadasException {
		
		
		this.log.debug("consultando las vialidades por localidad y por tipo [" + localidad +"] y [" + tipoVialidad +"]" );
		List<Vialidad> vialidades = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("select new mx.gob.imss.ctirss.delta.model.domicilio.Vialidad(vialidad.cveVia , vialidad.nomVia, vialidad.dgCatVialidad.cveTipoVial, vialidad.dgCatVialidad.descripcion )");
		sql.append(" from DgVialidad vialidad");
		sql.append(" where vialidad.dgCatLocalidad.id.cveEnt = :cveEnt");
		sql.append(" and vialidad.dgCatLocalidad.id.cveMun = :cveMun");
		sql.append(" and vialidad.dgCatLocalidad.id.cveLoc = :cveLoc");
		sql.append(" and vialidad.dgCatVialidad.cveTipoVial = :cveTipoVial");
		sql.append(" order by vialidad.nomVia");
		
		Query query = this.getSession().createQuery(sql.toString());
		
		query.setParameter("cveEnt", localidad.getMunicipio().getEntidadFederativa().getClave());
		query.setParameter("cveMun", localidad.getMunicipio().getClave());
		query.setParameter("cveLoc", localidad.getClave());
		query.setParameter("cveTipoVial", tipoVialidad.getClave());
		
		vialidades = query.list();
		
		return vialidades;
		
	}
	
	
	/**
	 * 
	 * @param vialidad
	 * @return
	 * @throws VialidadesNoLocalizadasException
	 */
	public Vialidad getVialidad( Vialidad vialidad )throws VialidadesNoLocalizadasException{
		
		StringBuffer sql = new StringBuffer();
		sql.append("select new mx.gob.imss.ctirss.delta.model.domicilio.Vialidad(vialidad.cveVia , vialidad.nomVia, vialidad.dgCatVialidad.cveTipoVial, vialidad.dgCatVialidad.descripcion )");
		sql.append(" from DgVialidad vialidad");
		sql.append(" where vialidad.cveVia = :cveVia");
		
		Query query = this.getSession().createQuery(sql.toString());
		this.log.debug("Clave de la vialidad a localizar : " + vialidad.getClave());
		query.setParameter("cveVia", vialidad.getClave());
		
		
		
		vialidad  = (Vialidad)query.uniqueResult();
		
		this.log.debug("Vialidad encontrada ::" + vialidad);
		
		return vialidad;
	}

	
	
	@Override
	public Localidad getLocalidadByVialidad(Vialidad vialidad)
			throws DomicilioNoValidoException {
		Localidad localidad = null;

		if (vialidad.getClave() != null && vialidad.getClave() > 0) {
			try {
				DgVialidad dgVialidad = em.find(DgVialidad.class, vialidad.getClave());

				localidad = domicilioServiceUtilityLocal
						.transformarLocalidad(dgVialidad.getDgCatLocalidad());
			} catch (TransformacionException e) {
				log.debug("Error en getLocalidadByVialidad(Vialidad vialidad): "+e.getMessage());
				throw new DomicilioNoValidoException(e.getMessage());
			} catch (RuntimeException e) {
				this.log.error(e.getMessage(), e);
				throw new DomicilioNoValidoException(e.getMessage());
			}
		} else {
			throw new DomicilioNoValidoException("Para consultar el Municpio y Localidad es necesaria su clave");
		}

		return localidad;
	}




	@Override
	public DomicilioFiscal guardarDomicilioFiscal(
			DomicilioFiscal domicilioFiscal) throws DomicilioNoValidoException {

		this.log.debug("guardarDomicilio [" + domicilioFiscal + "]");
		try {
			DitDomicilioSat ditDomicilioSat = this.domicilioServiceUtilityLocal
					.transformarDomicilioFiscal(domicilioFiscal);
			
			if (ditDomicilioSat == null) {
				this.log.debug("Domicilio no se guardara ...");
			} else {
				
				ditDomicilioSat.setFecRegistroAlta(new Date());
				
				this.em.persist(ditDomicilioSat);
				this.em.flush();
				this.log.debug("la clave asignada es: [" +ditDomicilioSat.getCveIdDomicilio()+ "]");
				this.log.debug("la clave asignada to LONG es: [" + Long.valueOf(
						ditDomicilioSat.getCveIdDomicilio()).intValue()+ "]");
				
				domicilioFiscal.setClave(Long.valueOf(
						ditDomicilioSat.getCveIdDomicilio()).intValue());
			}
		} catch (TransformacionException e) {
			throw new DomicilioNoValidoException(e.getMessage());
		} catch (RuntimeException e) {
			this.log.error(e.getMessage(), e);
			throw new DomicilioNoValidoException(e.getMessage());
		}
		this.log.debug("al salir del mentodo la clave es [" +domicilioFiscal.getClave()+"]" );
		return domicilioFiscal;
	}

	@Override
	public DomicilioFiscal consultarDomicilioFiscal(
			DomicilioFiscal domicilioFiscal)
			throws DomicilioNoLocalizadoException {

		Long domicilioId = domicilioFiscal.getClave().longValue();

		StringBuffer bfr = new StringBuffer();
		bfr.append(" from DitDomicilioSat dom ");
		bfr.append(" where dom.cveIdDomicilio = :cveIdDomicilio");

		Query query = this.getSession().createQuery(bfr.toString());
		query.setParameter("cveIdDomicilio", domicilioId);

		DitDomicilioSat domicilioSat = (DitDomicilioSat) query.uniqueResult();

		try {
			domicilioFiscal = this.domicilioServiceUtilityLocal
					.transformarDomicilioFiscal(domicilioSat);
		} catch (TransformacionException e) {
			log.debug("Error en consultarDomicilioFiscal(DomicilioFiscal domicilioFiscal): "+e.getMessage());
			throw new DomicilioNoLocalizadoException("El domicilio fiscal no ha sido localizado");
		}

		return domicilioFiscal;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DomicilioFiscal consultarDomicilioFiscalPersona(Persona persona)
			throws DomicilioNoLocalizadoException {
		
		DomicilioFiscal domicilioFiscal = null;
		Long idPersona = persona.getIdPersona();
		StringBuffer jpaQuery = new StringBuffer();
		
		if(persona instanceof Moral) {
			jpaQuery.append("select domSat.ditDomicilioSat ");
			jpaQuery.append("from DitPersonaMDomFiscal domSat ");
			jpaQuery.append("where domSat.ditPersonaMoral.cveIdPersonaMoral = :idPersona");
		}else {
			jpaQuery.append("select domSat.ditDomicilioSat ");
			jpaQuery.append("from DitPersonaFDomFiscal domSat ");
			jpaQuery.append("where domSat.ditPersonaFisica.ditPersona.cveIdPersona = :idPersona");
		}
		
		javax.persistence.Query query = this.em.createQuery(jpaQuery.toString());
		query.setParameter("idPersona", idPersona);
		
		List<DitDomicilioSat> domicilioSat = query.getResultList();
		
		if (domicilioSat != null && !domicilioSat.isEmpty()) {
			if (domicilioSat.size() > 1) {
				this.log.error("La persona " +  idPersona + " cuenta con m�s de un domicilio fiscal!!!!!!");
			}
			
			try {
				domicilioFiscal = this.domicilioServiceUtilityLocal
						.transformarDomicilioFiscal(domicilioSat.get(0));
			} catch (TransformacionException e) {
				log.debug("Error en consultarDomicilioFiscalPersona(Persona persona): "+e.getMessage());
				throw new DomicilioNoLocalizadoException("El domicilio fiscal no ha sido localizado");
			}
		} else {
			throw new DomicilioNoLocalizadoException("El domicilio fiscal no ha sido localizado");
		}

		return domicilioFiscal;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Domicilio> consultarDomiciliosPersonaFisica(Persona persona)
			throws DomicilioNoLocalizadoException {

		List<DitPersonafDom> domiciliosPersonaFisica = null;
		List<Domicilio> domiciliosPersona = null;

		StringBuffer jpaQuery = new StringBuffer();
		jpaQuery.append("select new DitPersonafDom(domicilios.cveIdPersonafDom, domicilios.dgDomicilioGeografico, ");
		jpaQuery.append("domicilios.dicTipoDomicilio) ");
		jpaQuery.append("from DitPersonafDom domicilios ");
		jpaQuery.append("where domicilios.ditPersonaView.cveIdPersona = :idPersona ");

		Query query = this.getSession().createQuery(jpaQuery.toString());
		query.setParameter("idPersona", persona.getIdPersona());

		domiciliosPersonaFisica = query.list();

		if (!domiciliosPersonaFisica.isEmpty()) {
			domiciliosPersona = new ArrayList<Domicilio>();
			Domicilio domicilio = null;
			for (DitPersonafDom ditPersonafDom : domiciliosPersonaFisica) {
				try {
					domicilio = this.domicilioServiceUtilityLocal
							.transformarDomicilio(ditPersonafDom
									.getDgDomicilioGeografico());

					if (ditPersonafDom.getDicTipoDomicilio() != null) {
						TipoDomicilio tipoDomicilio = new TipoDomicilio();
						tipoDomicilio.setClave(ditPersonafDom
								.getDicTipoDomicilio().getCveIdTipoDomicilio()
								.intValue());
						tipoDomicilio.setDescripcion(ditPersonafDom
								.getDicTipoDomicilio().getDesTipoDomicilio());
						domicilio.setDicTipoDomicilio(tipoDomicilio);
						domicilio.setCveIdPersonafDom( ditPersonafDom.getCveIdPersonafDom() );
					}

					domiciliosPersona.add(domicilio);
				} catch (TransformacionException e) {
					log.error("Error al transformar el domicilio "
							+ ditPersonafDom.getDgDomicilioGeografico()
									.getDomicilioId() + ": ", e);
				}
			}
		} else {
			throw new DomicilioNoLocalizadoException("La persona "
					+ persona.getIdPersona() + "no cuenta con domicilios");
		}

		return domiciliosPersona;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Domicilio> consultarDomiciliosPersonaMoral(Persona persona)
			throws DomicilioNoLocalizadoException {

		List<DitPersonamDom> domiciliosPersonaMoral = null;
		List<Domicilio> domiciliosPersona = null;

		StringBuffer jpaQuery = new StringBuffer();

		jpaQuery.append("select new DitPersonamDom(domicilios.dgDomicilioGeografico, ");
		jpaQuery.append("domicilios.dicTipoDomicilio) ");
		jpaQuery.append("from DitPersonamDom domicilios ");
		jpaQuery.append("where domicilios.ditPersonaMoral.cveIdPersonaMoral = :idPersona ");

		Query query = this.getSession().createQuery(jpaQuery.toString());
		query.setParameter("idPersona", persona.getIdPersona());

		domiciliosPersonaMoral = query.list();

		if (!domiciliosPersonaMoral.isEmpty()) {
			domiciliosPersona = new ArrayList<Domicilio>();
			Domicilio domicilio = null;
			for (DitPersonamDom ditPersonamDom : domiciliosPersonaMoral) {
				try {
					domicilio = this.domicilioServiceUtilityLocal
							.transformarDomicilio(ditPersonamDom
									.getDgDomicilioGeografico());

					if (ditPersonamDom.getDicTipoDomicilio() != null) {
						TipoDomicilio tipoDomicilio = new TipoDomicilio();
						tipoDomicilio.setClave(ditPersonamDom
								.getDicTipoDomicilio().getCveIdTipoDomicilio()
								.intValue());
						tipoDomicilio.setDescripcion(ditPersonamDom
								.getDicTipoDomicilio().getDesTipoDomicilio());
						domicilio.setTipoDomicilio(tipoDomicilio);
					}

					domiciliosPersona.add(domicilio);
				} catch (TransformacionException e) {
					log.error("Error al transformar el domicilio "
							+ ditPersonamDom.getDgDomicilioGeografico()
									.getDomicilioId() + ": ", e);
				}
			}
		} else {
			throw new DomicilioNoLocalizadoException("La persona "
					+ persona.getIdPersona() + "no cuenta con domicilios");
		}

		return domiciliosPersona;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Domicilio> consultarDomiciliosModificablesPersonaFisica(
			Persona persona) throws DomicilioNoLocalizadoException {
		
		List<DitPersonafDom> domiciliosPersonaFisica = null;
		List<Domicilio> domiciliosPersona = null;

		StringBuffer jpaQuery = new StringBuffer();
		jpaQuery.append("select new DitPersonafDom(domicilios.dgDomicilioGeografico, ");
		jpaQuery.append("domicilios.dicTipoDomicilio) ");
		jpaQuery.append("from DitPersonafDom domicilios ");
		jpaQuery.append("where domicilios.ditPersonaView.cveIdPersona = :idPersona ");
		jpaQuery.append("and domicilios.dicTipoDomicilio.cveIdTipoDomicilio in (2,4)");

		Query query = this.getSession().createQuery(jpaQuery.toString());
		query.setParameter("idPersona", persona.getIdPersona());

		domiciliosPersonaFisica = query.list();

		if (!domiciliosPersonaFisica.isEmpty()) {
			domiciliosPersona = new ArrayList<Domicilio>();
			Domicilio domicilio = null;
			for (DitPersonafDom ditPersonafDom : domiciliosPersonaFisica) {
				try {
					domicilio = this.domicilioServiceUtilityLocal
							.transformarDomicilio(ditPersonafDom
									.getDgDomicilioGeografico());

					if (ditPersonafDom.getDicTipoDomicilio() != null) {
						TipoDomicilio tipoDomicilio = new TipoDomicilio();
						tipoDomicilio.setClave(ditPersonafDom
								.getDicTipoDomicilio().getCveIdTipoDomicilio()
								.intValue());
						tipoDomicilio.setDescripcion(ditPersonafDom
								.getDicTipoDomicilio().getDesTipoDomicilio());
						domicilio.setDicTipoDomicilio(tipoDomicilio);
					}

					domiciliosPersona.add(domicilio);
				} catch (TransformacionException e) {
					log.error("Error al transformar el domicilio "
							+ ditPersonafDom.getDgDomicilioGeografico()
									.getDomicilioId() + ": ", e);
				}
			}
		} else {
			throw new DomicilioNoLocalizadoException("La persona "
					+ persona.getIdPersona() + "no cuenta con domicilios");
		}

		return domiciliosPersona;
	}

	@Override
	public void modificarDomicilioFiscal(
			DomicilioFiscal domicilioFiscal) throws DomicilioNoValidoException {
		
		this.log.debug("Se va a modificar el domicilio fiscal [" + domicilioFiscal.getClave() + "]");
		
		if(domicilioFiscal.getClave() != null && domicilioFiscal.getClave() > 0){

			try {
				
				DitDomicilioSat ditDomicilioSat = this.em.find(DitDomicilioSat.class, domicilioFiscal.getClave().longValue());
				
				/*
				 * Dado que la modificaci�n de un domicilio fiscal, en realidad
				 * es la creaci�n de uno nuevo con el id del anterior, por lo tanto, se limpia
				 * entity encontrado antes de settearle los valores
				 */
				ditDomicilioSat.setCodigo(null);
				ditDomicilioSat.setCalle(null);
				ditDomicilioSat.setColonia(null);
				ditDomicilioSat.setEntidadFederativa(null);
				ditDomicilioSat.setLocalidad(null);
				ditDomicilioSat.setMunicipio(null);
				ditDomicilioSat.setEntreCalle1(null);
				ditDomicilioSat.setEntreCalle2(null);
				ditDomicilioSat.setInmueble(null);
				ditDomicilioSat.setReferencia(null);
				ditDomicilioSat.setVialidad(null);
				ditDomicilioSat.setNumInterior(null);
				ditDomicilioSat.setNumExterior(null);
								
				DitDomicilioSat ditDomicilioSatModificar = this.domicilioServiceUtilityLocal
						.transformarDomicilioFiscal(domicilioFiscal);
				
				ditDomicilioSat.setCodigo(ditDomicilioSatModificar.getCodigo());
				ditDomicilioSat.setCalle(ditDomicilioSatModificar.getCalle());
				ditDomicilioSat.setColonia(ditDomicilioSatModificar.getColonia());
				ditDomicilioSat.setEntidadFederativa(ditDomicilioSatModificar.getEntidadFederativa());
				ditDomicilioSat.setLocalidad(ditDomicilioSatModificar.getLocalidad());
				ditDomicilioSat.setMunicipio(ditDomicilioSatModificar.getMunicipio());
				ditDomicilioSat.setEntreCalle1(ditDomicilioSatModificar.getEntreCalle1());
				ditDomicilioSat.setEntreCalle2(ditDomicilioSatModificar.getEntreCalle2());
				ditDomicilioSat.setInmueble(ditDomicilioSatModificar.getInmueble());
				ditDomicilioSat.setReferencia(ditDomicilioSatModificar.getReferencia());
				ditDomicilioSat.setVialidad(ditDomicilioSatModificar.getVialidad());
				ditDomicilioSat.setNumInterior(ditDomicilioSatModificar.getNumInterior());
				ditDomicilioSat.setNumExterior(ditDomicilioSatModificar.getNumExterior());

				ditDomicilioSat.setFecRegistroActualizado(new Date());
				
			} catch (TransformacionException e) {
				throw new DomicilioNoValidoException(e.getMessage());
			} catch (RuntimeException e) {
				this.log.error(e.getMessage(), e);
				throw new DomicilioNoValidoException(e.getMessage());
			}
		}else{
			throw new DomicilioNoValidoException("Para modificar un domicilio fiscal es necesaria su clave");
		}
	}
	
	@Override
	public Long asociarDomicilioPersona(Domicilio domicilio, Long cvePersona)
			throws DomicilioNoValidoException {
		
		this.log.debug("Se va a asociar el domicilio geogr�fico ["
				+ domicilio.getClave() + "] a la persona ["
				+ cvePersona + "]");
		
		if(domicilio != null && domicilio.getClave() != null && cvePersona != null){
			DgDomicilioGeografico dom = new DgDomicilioGeografico();
			dom.setDomicilioId(domicilio.getClave().longValue());
			
			DitPersona ditPersona = new DitPersona();
			ditPersona.setCveIdPersona(cvePersona);
			
			DicTipoDomicilio dicTipoDomicilio = new DicTipoDomicilio();
			if (domicilio.getDicTipoDomicilio() != null) {
				dicTipoDomicilio.setCveIdTipoDomicilio(domicilio.getDicTipoDomicilio().getClave().longValue());
			} else {
				this.log.warn("No se recibi� tipo de domicilio, se settea el default para la relaci�n con la persona");
				dicTipoDomicilio.setCveIdTipoDomicilio(TipoDomicilioEnum.PARTICULAR.getCodigo());
			}
			
			DitPersonafDom ditPersonafDom = new DitPersonafDom();
			ditPersonafDom.setDgDomicilioGeografico(dom);
			ditPersonafDom.setDitPersona(ditPersona);
			ditPersonafDom.setDicTipoDomicilio(dicTipoDomicilio);
			ditPersonafDom.setFecRegistroAlta(new Date());
			
			this.em.persist(ditPersonafDom);
			return ditPersonafDom.getCveIdPersonafDom();
		}else{
			throw new DomicilioNoValidoException(
					"No se puede guardar la relaci�n domicilio - persona ya que no se cuenta con las claves de ambas entidades");
		}
	}

	@Override
	public void asociarDomicilioPersonaMoral(Domicilio domicilio, Long cveMoral)
			throws DomicilioNoValidoException {
		
		this.log.debug("Se va a asociar el domicilio geogr�fico ["
				+ domicilio.getClave() + "] a la persona moral ["
				+ cveMoral + "]");
		
		if(domicilio != null && domicilio.getClave() != null && cveMoral != null){
			DgDomicilioGeografico dom = new DgDomicilioGeografico();
			dom.setDomicilioId(domicilio.getClave().longValue());
			
			DitPersonaMoral ditPersonaMoral = new DitPersonaMoral();
			ditPersonaMoral.setCveIdPersonaMoral(cveMoral);
			
			DicTipoDomicilio dicTipoDomicilio = new DicTipoDomicilio();
			if (domicilio.getDicTipoDomicilio() != null) {
				dicTipoDomicilio.setCveIdTipoDomicilio(domicilio.getDicTipoDomicilio().getClave().longValue());
			} else {
				this.log.warn("No se recibi� tipo de domicilio, se settea el default para la relaci�n con la persona");
				dicTipoDomicilio.setCveIdTipoDomicilio(TipoDomicilioEnum.PARTICULAR.getCodigo());
			}
			
			DitPersonamDom ditPersonamDom = new DitPersonamDom();
			ditPersonamDom.setDgDomicilioGeografico(dom);
			ditPersonamDom.setDitPersonaMoral(ditPersonaMoral);
			ditPersonamDom.setDicTipoDomicilio(dicTipoDomicilio);
			ditPersonamDom.setFecRegistroAlta(new Date());
			
			this.em.persist(ditPersonamDom);
		}else{
			throw new DomicilioNoValidoException(
					"No se puede guardar la relaci�n domicilio - persona moral ya que no se cuenta con las claves de ambas entidades");
		}
		
	}
	
	@Override
	public long asociarDomicilioFiscalPersonaFisica(Integer cveDomicilioFiscal,
			Long cveFisica) throws AsociarDomicilioException {
		
		long cveIdPfdomFiscal;

		this.log.debug("Se va a asociar el domicilio fiscal ["
				+ cveDomicilioFiscal + "] a la persona fisica ["
				+ cveFisica + "]");
		
		if(cveDomicilioFiscal != null && cveFisica != null){
			DitDomicilioSat ditDomicilioSat = new DitDomicilioSat();
			ditDomicilioSat.setCveIdDomicilio(cveDomicilioFiscal.longValue());
			
			DitPersonaFisica ditPersonaFisica = new DitPersonaFisica();
			ditPersonaFisica.setCveIdPersonaFisica(cveFisica);
			
			DitPersonaFDomFiscal ditPersonaFDomFiscal = new DitPersonaFDomFiscal();
			ditPersonaFDomFiscal.setDitDomicilioSat(ditDomicilioSat);
			ditPersonaFDomFiscal.setDitPersonaFisica(ditPersonaFisica);
			ditPersonaFDomFiscal.setFecRegistroAlta(new Date());
			
			this.em.persist(ditPersonaFDomFiscal);
			this.em.flush();
			cveIdPfdomFiscal = ditPersonaFDomFiscal.getCveIdPfdomFiscal();
			return cveIdPfdomFiscal;
		}else{
			throw new AsociarDomicilioException();
		}
		
	}
	
	@Override
	public long asociarDomicilioFiscalPersonaMoral(Integer cveDomicilioFiscal,
			Long cveMoral) throws AsociarDomicilioException {
		
		long cveIdPmdomFiscal;

		this.log.debug("Se va a asociar el domicilio fiscal ["
				+ cveDomicilioFiscal + "] a la persona moral ["
				+ cveMoral + "]");
		
		if(cveDomicilioFiscal != null && cveMoral != null){
			DitDomicilioSat ditDomicilioSat = new DitDomicilioSat();
			ditDomicilioSat.setCveIdDomicilio(cveDomicilioFiscal.longValue());
			
			DitPersonaMoral ditPersonaMoral = new DitPersonaMoral();
			ditPersonaMoral.setCveIdPersonaMoral(cveMoral);
			
			DitPersonaMDomFiscal ditPersonaMDomFiscal = new DitPersonaMDomFiscal();
			ditPersonaMDomFiscal.setDitDomicilioSat(ditDomicilioSat);
			ditPersonaMDomFiscal.setDitPersonaMoral(ditPersonaMoral);
			ditPersonaMDomFiscal.setFecRegistroAlta(new Date());
			
			this.em.persist(ditPersonaMDomFiscal);
			cveIdPmdomFiscal = ditPersonaMDomFiscal.getCveIdPmdomFiscal();
			return cveIdPmdomFiscal;
		}else{
			throw new AsociarDomicilioException();
		}
	}
	
	@Override
	public void modificarDomicilio(Domicilio domicilio) throws TransformacionException{
		
		this.log.debug("Se va a modificar el domicilio geogr�fico [" + domicilio.getClave() + "]");
		
		DgDomicilioGeografico dgDomicilioGeografico = this.em.find(
				DgDomicilioGeografico.class, domicilio.getClave().longValue());
		
		if(domicilio.getTipoBusquedaVialidad() != null) {
			log.debug("Se verifica el tipo de busqueda : " + domicilio.getTipoBusquedaVialidad());
			if(!domicilio.getTipoBusquedaVialidad().equals(2)) {
				domicilio.setCalle(null);
			}
		}
		
		DgDomicilioGeografico domicilioModificado = this.domicilioServiceUtilityLocal.transformarDomicilio(domicilio);
		
		//se eliminan los auxiliares de domicilio y se vuevle a insertar si es que bien alguno
		DgDomiciliosCarretera carretera = dgDomicilioGeografico.getDgDomiciliosCarretera();
		if(carretera != null){
			this.em.remove(carretera);
			dgDomicilioGeografico.setDgDomiciliosCarretera(null);
		}
		DgDomiciliosCamino camino = dgDomicilioGeografico.getDgDomiciliosCamino();
		if(camino != null){
			this.em.remove(camino);
			dgDomicilioGeografico.setDgDomiciliosCamino(null);
		}
		
		this.em.flush();
		
		
		dgDomicilioGeografico.setDgAsentamiento(domicilioModificado.getDgAsentamiento());
		dgDomicilioGeografico.setDgCatLocalidad(domicilioModificado.getDgCatLocalidad());
		dgDomicilioGeografico.setDgCatTipoDom(domicilioModificado.getDgCatTipoDom());
		dgDomicilioGeografico.setDgCodigosPostale(domicilioModificado.getDgCodigosPostale());

		dgDomicilioGeografico.setNomvial(domicilioModificado.getNomvial());
		
		dgDomicilioGeografico.setNumextnum(domicilioModificado.getNumextnum());
		dgDomicilioGeografico.setNumextalf(domicilioModificado.getNumextalf());
		dgDomicilioGeografico.setNumextAnt(domicilioModificado.getNumextAnt());
		dgDomicilioGeografico.setNumintnum(domicilioModificado.getNumintnum());
		dgDomicilioGeografico.setNumintalf(domicilioModificado.getNumintalf());
		dgDomicilioGeografico.setRefLatitud(domicilioModificado.getRefLatitud());
		dgDomicilioGeografico.setRefLongitud(domicilioModificado.getRefLongitud());

		dgDomicilioGeografico.setDgVialidadByCveViaPrin(domicilioModificado.getDgVialidadByCveViaPrin());
		dgDomicilioGeografico.setDgVialidadByCveViaRef1(domicilioModificado.getDgVialidadByCveViaRef1());
		dgDomicilioGeografico.setDgVialidadByCveViaRef2(domicilioModificado.getDgVialidadByCveViaRef2());
		dgDomicilioGeografico.setDgVialidadByCveViaRef3(domicilioModificado.getDgVialidadByCveViaRef3());
		dgDomicilioGeografico.setDescripc(domicilioModificado.getDescripc());
		dgDomicilioGeografico.setDomGeog(domicilioModificado.getDomGeog());
		
		dgDomicilioGeografico.setFecRegistroActualizado(new Date());
		
		
		//se insertan alguno de los componentes de domicilio
		DgDomiciliosCarretera carreteraNueva = domicilioModificado.getDgDomiciliosCarretera();
		if(carreteraNueva != null){
			carreteraNueva.setDomicilioId(dgDomicilioGeografico.getDomicilioId());
			this.em.persist(carreteraNueva);
		}
		DgDomiciliosCamino caminoNuevo = domicilioModificado.getDgDomiciliosCamino();
		if(caminoNuevo != null){
			caminoNuevo.setDomicilioId(dgDomicilioGeografico.getDomicilioId());
			this.em.persist(caminoNuevo);
		}
					
	}
	
	@Override
	public void desasociarEliminarDomicilioPersona(Long cveDomicilio,
			Long cvePersona) throws AsociarDomicilioException {
		
		this.log.debug("Se va a eliminar el domicilio geogr�fico [" + cveDomicilio
				+ "] y desasociarlo de la persona [" + cvePersona + "]");
		
		if(cveDomicilio != null && cvePersona != null){
			
			StringBuffer jpaQuery = new StringBuffer();
			jpaQuery.append("from DitPersonafDom pd ");
			jpaQuery.append("where pd.dgDomicilioGeografico.domicilioId = :cveDomicilio ");
			jpaQuery.append("and pd.ditPersona.cveIdPersona = :cvePersona");
			
			javax.persistence.Query query = this.em.createQuery(jpaQuery.toString());
			query.setParameter("cveDomicilio", cveDomicilio);
			query.setParameter("cvePersona", cvePersona);
			
			DitPersonafDom ditPersonafDom = (DitPersonafDom) query.getSingleResult();
			
			this.em.remove(ditPersonafDom);
			
		}else{
			throw new AsociarDomicilioException("No se puede eliminar la relaci�n domicilio - persona ya que no se cuenta con las claves de ambas entidades");
		}
	}
	
	@Override
	public void desasociarEliminarDomicilioPersonaMoral(Long cveDomicilio,
			Long cvePersonaMoral) throws AsociarDomicilioException {
		
		this.log.debug("Se va a eliminar el domicilio geogr�fico [" + cveDomicilio
				+ "] y desasociarlo de la persona moral [" + cvePersonaMoral + "]");
		
		if(cveDomicilio != null && cvePersonaMoral != null){
			
			StringBuffer jpaQuery = new StringBuffer();
			jpaQuery.append("from DitPersonamDom pd ");
			jpaQuery.append("where pd.dgDomicilioGeografico.domicilioId = :cveDomicilio ");
			jpaQuery.append("and pd.ditPersonaMoral.cveIdPersonaMoral = :cvePersonaMoral");
			
			javax.persistence.Query query = this.em.createQuery(jpaQuery.toString());
			query.setParameter("cveDomicilio", cveDomicilio);
			query.setParameter("cvePersonaMoral", cvePersonaMoral);
			
			DitPersonamDom ditPersonamDom = (DitPersonamDom) query.getSingleResult();
			
			this.em.remove(ditPersonamDom);
			
		}else{
			throw new AsociarDomicilioException("No se puede eliminar la relaci�n domicilio - persona moral ya que no se cuenta con las claves de ambas entidades");
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Domicilio> consultarDomiciliosPersonaFisicaPorTipo(Persona persona,
			List<Long> tiposDomicilio)
			throws DomicilioNoLocalizadoException {
		
		List<DitPersonafDom> domiciliosPersonaFisica = null;
		List<Domicilio> domiciliosPersona = null;

		StringBuffer jpaQuery = new StringBuffer();
		jpaQuery.append("select new DitPersonafDom(domicilios.dgDomicilioGeografico, ");
		jpaQuery.append("domicilios.dicTipoDomicilio) ");
		jpaQuery.append("from DitPersonafDom domicilios ");
		jpaQuery.append("where domicilios.ditPersonaView.cveIdPersona = :idPersona ");
		jpaQuery.append("and domicilios.dicTipoDomicilio.cveIdTipoDomicilio in :tiposDomicilio ");
		jpaQuery.append("order by domicilios.cveIdPersonafDom desc ");

		Query query = this.getSession().createQuery(jpaQuery.toString());
		query.setParameter("idPersona", persona.getIdPersona());
		query.setParameterList("tiposDomicilio", tiposDomicilio);

		domiciliosPersonaFisica = query.list();

		if (!domiciliosPersonaFisica.isEmpty()) {
			domiciliosPersona = new ArrayList<Domicilio>();
			Domicilio domicilio = null;
			for (DitPersonafDom ditPersonafDom : domiciliosPersonaFisica) {
				try {
					domicilio = this.domicilioServiceUtilityLocal
							.transformarDomicilio(ditPersonafDom
									.getDgDomicilioGeografico());

					if (ditPersonafDom.getDicTipoDomicilio() != null) {
						TipoDomicilio tipoDomicilio = new TipoDomicilio();
						tipoDomicilio.setClave(ditPersonafDom
								.getDicTipoDomicilio().getCveIdTipoDomicilio()
								.intValue());
						tipoDomicilio.setDescripcion(ditPersonafDom
								.getDicTipoDomicilio().getDesTipoDomicilio());
						domicilio.setDicTipoDomicilio(tipoDomicilio);
					}

					domiciliosPersona.add(domicilio);
				} catch (TransformacionException e) {
					log.error("Error al transformar el domicilio "
							+ ditPersonafDom.getDgDomicilioGeografico()
									.getDomicilioId() + ": ", e);
				}
			}
		} else {
			throw new DomicilioNoLocalizadoException("La persona "
					+ persona.getIdPersona() + "no cuenta con domicilios del tipo solicitado");
		}

		return domiciliosPersona;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Domicilio> consultarDomiciliosPersonaMoralPorTipo(Persona persona,
			List<Long> tiposDomicilio)
			throws DomicilioNoLocalizadoException {
		
		List<DitPersonamDom> domiciliosPersonaMoral = null;
		List<Domicilio> domiciliosPersona = null;

		StringBuffer jpaQuery = new StringBuffer();
		jpaQuery.append("select new DitPersonamDom(domicilios.dgDomicilioGeografico, ");
		jpaQuery.append("domicilios.dicTipoDomicilio) ");
		jpaQuery.append("from DitPersonamDom domicilios ");
		jpaQuery.append("where domicilios.ditPersonaMoral.cveIdPersonaMoral = :idPersona ");
		jpaQuery.append("and domicilios.dicTipoDomicilio.cveIdTipoDomicilio in :tiposDomicilio");

		Query query = this.getSession().createQuery(jpaQuery.toString());
		query.setParameter("idPersona", persona.getIdPersona());
		query.setParameterList("tiposDomicilio", tiposDomicilio);

		domiciliosPersonaMoral = query.list();

		if (!domiciliosPersonaMoral.isEmpty()) {
			domiciliosPersona = new ArrayList<Domicilio>();
			Domicilio domicilio = null;
			for (DitPersonamDom ditPersonafDom : domiciliosPersonaMoral) {
				try {
					domicilio = this.domicilioServiceUtilityLocal
							.transformarDomicilio(ditPersonafDom
									.getDgDomicilioGeografico());

					if (ditPersonafDom.getDicTipoDomicilio() != null) {
						TipoDomicilio tipoDomicilio = new TipoDomicilio();
						tipoDomicilio.setClave(ditPersonafDom
								.getDicTipoDomicilio().getCveIdTipoDomicilio()
								.intValue());
						tipoDomicilio.setDescripcion(ditPersonafDom
								.getDicTipoDomicilio().getDesTipoDomicilio());
						domicilio.setDicTipoDomicilio(tipoDomicilio);
					}

					domiciliosPersona.add(domicilio);
				} catch (TransformacionException e) {
					log.error("Error al transformar el domicilio "
							+ ditPersonafDom.getDgDomicilioGeografico()
									.getDomicilioId() + ": ", e);
				}
			}
		} else {
			throw new DomicilioNoLocalizadoException("La persona "
					+ persona.getIdPersona() + "no cuenta con domicilios del tipo solicitado");
		}

		return domiciliosPersona;
	}
	
	
	/**
	 * Consulta y recupera un listado de municipios IMSS asociados a un codigo postal filtrando por entidad federativa y municipio inegi
	 * @param objMunicipio
	 * @param codigoPostal
	 * @return
	 * @throws MunicipioImssNoLocalizadoException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MunicipioIMSS> getMunicipioIMSSbyEstadoMunCP(Municipio objMunicipio, 
			String codigoPostal) throws MunicipioImssNoLocalizadoException{
		List<MunicipioIMSS> lstMunicipioIMSS =  new ArrayList<MunicipioIMSS>();
		List<DicMunicipioImss> lstjMunImss;	
		StringBuffer strQuery = new StringBuffer();
		
		strQuery.append("SELECT DISTINCT muni ");
		strQuery.append("FROM  DicMunicipioImss muni, DitMunicipioImssInegi ine, DgCodigosPostale cod ");
		// DicMunicipioImss - DitMunicipioImssInegi
		strQuery.append("WHERE muni.cveIdMunicipioImss = ine.dicMunicipioImss.cveIdMunicipioImss ");
		strQuery.append("  AND muni.dgCatEstado.cveEnt = ine.dgCatMunicipio.id.cveEnt ");
		// DicMunicipioImss - DgCodigosPostale
		strQuery.append("  AND muni.dgCatEstado.cveEnt      = cod.id.cveEnt ");
		// DitMunicipioImssInegi - DgCodigosPostale
		strQuery.append("  AND ine.dgCatMunicipio.id.cveEnt = cod.id.cveEnt ");
		strQuery.append("  AND ine.dgCatMunicipio.id.cveMun = cod.id.cveMun ");
		
		strQuery.append("  AND muni.cveMunicipio <> :cveMunLosAngeles ");
		strQuery.append("  AND cod.id.codigo = :codigoPostal ");
		strQuery.append("  AND muni.fecRegistroBaja IS NULL ");
		strQuery.append("  AND muni.fecBajaVigencia IS NULL ");
		strQuery.append("  AND ine.fecRegistroBaja  IS NULL ");
		
		if(objMunicipio!=null){
			if(StringUtils.isNotEmpty(objMunicipio.getClave()) 
					&& StringUtils.isNotBlank(objMunicipio.getClave())){
				strQuery.append("	AND cod.id.cveMun = :cveMunicipio ");
			}			
			if(objMunicipio.getEntidadFederativa() != null 
					&& StringUtils.isNotEmpty(objMunicipio.getEntidadFederativa().getClave()) 
					&& StringUtils.isNotBlank(objMunicipio.getEntidadFederativa().getClave())){
				strQuery.append("	AND cod.id.cveEnt = :cveEntidadFederativa ");	
			}
		}
		log.debug("el query a ejecutar es " + strQuery.toString());
		
		Query query = this.getSession().createQuery(strQuery.toString());
		query.setParameter("cveMunLosAngeles", "Z28");
		query.setParameter("codigoPostal", codigoPostal);
		if(objMunicipio!=null){
			if(StringUtils.isNotEmpty(objMunicipio.getClave()) 
					&& StringUtils.isNotBlank(objMunicipio.getClave())){
				query.setParameter("cveMunicipio", objMunicipio.getClave());
			}
			if(objMunicipio.getEntidadFederativa() != null 
					&& StringUtils.isNotEmpty(objMunicipio.getEntidadFederativa().getClave()) 
					&& StringUtils.isNotBlank(objMunicipio.getEntidadFederativa().getClave())){
				query.setParameter("cveEntidadFederativa", objMunicipio.getEntidadFederativa().getClave());	
			}				
		}			
		try{
			lstjMunImss = query.list();
			if (!lstjMunImss.isEmpty()){
				for (DicMunicipioImss objMunicipioImss : lstjMunImss) {					
					MunicipioIMSS objBeanMun = new MunicipioIMSS();					
					objBeanMun.setIdMunicipio(objMunicipioImss.getCveIdMunicipioImss()+"");
					objBeanMun.setCvecMunicipioSINDO(objMunicipioImss.getCveMunicipio());
					objBeanMun.setDescMunicipio(objMunicipioImss.getNomMunicipioImss());
					Subdelegacion objSubdelegacion = new Subdelegacion();
					Delegacion objDelegacion = new Delegacion();
					DicSubdelegacion dicSubdelegacion = objMunicipioImss.getDitMunicipioSubdelegacions().get(0).getDicSubdelegacion();
					DicDelegacion dicDelegacion = dicSubdelegacion.getDicDelegacion();
					objSubdelegacion.setId(dicSubdelegacion.getCveIdSubdelegacion());
					objSubdelegacion.setDescripcion(dicSubdelegacion.getDesSubdelegacion());
					objSubdelegacion.setClave(dicSubdelegacion.getClaveSubdelegacion());
					objDelegacion.setClave(dicDelegacion.getClaveDelegacion());
					objDelegacion.setId(dicDelegacion.getCveIdDelegacion());
					objDelegacion.setDescripcion(dicDelegacion.getDesDeleg());
					objDelegacion.setCiz(dicDelegacion.getCveCiz());
					objSubdelegacion.setDelegacion(objDelegacion);
					objBeanMun.setSubdelegacion(objSubdelegacion);
					lstMunicipioIMSS.add(objBeanMun);
				}
			}else{
				throw new MunicipioImssNoLocalizadoException();
			}
		}catch(Exception e){
			this.log.error("ocurrio un error inesperado al tratar de consutar el municipio ", e);
			throw new MunicipioImssNoLocalizadoException();
		}
		return lstMunicipioIMSS;
	}	
	
	/**
	 * Metodo encargado de recuperar una umf a partir de un asentamiento del domicilio
	 * @param asentamiento
	 * @return UnidadMedicaFamiliar con una lista de umf con subdelegacion y delegacion
	 * @throws UmfNoLocalizadaException
	 */
	@Override
	public  List<UnidadMedicaFamiliar> getUmfByAsentamientoDomicilio(Asentamiento asentamiento) throws UmfNoLocalizadaException{
		
		List<UnidadMedicaFamiliar> lstUmf =  new ArrayList();
		
		this.log.debug("el objeto trae "+  asentamiento);
		List<DicUmf> lstUmfCat;
		
		StringBuffer strQuery = new StringBuffer();
		strQuery.append("select distinct umf ");
		strQuery.append(" from  DitUmfCodPo umfCod, DicUmf umf ");
		strQuery.append(" where umfCod.dicUmf.cveIdUmf = umf.cveIdUmf ");
		strQuery.append(" 	and umfCod.dgCodigosPostale.id.cveEnt = :cveEnt ");
		strQuery.append(" 	and umfCod.dgCodigosPostale.id.cveMun = :cveMun ");
		strQuery.append(" 	and umfCod.dgCodigosPostale.id.cveAsen = :cveAsen ");
		strQuery.append(" 	and umfCod.dgCodigosPostale.id.codigo = :codigo ");
		
		
		Query query = this.getSession().createQuery(strQuery.toString());
		
		
		query.setParameter("cveEnt", asentamiento.getLocalidad().getMunicipio().getEntidadFederativa().getClave());
		query.setParameter("cveMun", asentamiento.getLocalidad().getMunicipio().getClave());
		query.setParameter("cveAsen", asentamiento.getClave());
		query.setParameter("codigo", asentamiento.getCodigoPostal().getCodigoPostal());
		
		
		try{
			lstUmfCat = query.list();
			if(!lstUmfCat.isEmpty()){
				for (DicUmf objDicUmf :lstUmfCat) {
					//se crean objetos de negocio para su llenado
					UnidadMedicaFamiliar unidad = new UnidadMedicaFamiliar();
					Subdelegacion subdelegacion = new Subdelegacion();
					Delegacion delegacion = new Delegacion();
					
					//se crean objetos de persistencia para no realizar mas consultas en bd
					DicSubdelegacion dicSubdelegacion = objDicUmf.getDicSubdelegacion();  
					DicDelegacion dicDelegacion = dicSubdelegacion.getDicDelegacion();
							
					delegacion.setCiz(dicDelegacion.getCveCiz());
					delegacion.setId(dicDelegacion.getCveIdDelegacion());
					delegacion.setClave(dicDelegacion.getClaveDelegacion());
					delegacion.setDescripcion(dicDelegacion.getDesDeleg());
					
					subdelegacion.setClave(dicSubdelegacion.getClaveSubdelegacion());
					subdelegacion.setId(dicSubdelegacion.getCveIdSubdelegacion());
					subdelegacion.setDescripcion(dicSubdelegacion.getDesSubdelegacion());
					subdelegacion.setDelegacion(delegacion);
					
					unidad.setSubdelegacion(subdelegacion);
					unidad.setDescripcion(objDicUmf.getNomUnidad());
					unidad.setDesDireccion(this.domicilioServiceUtilityLocal.persisToModelDesDireccion(
								objDicUmf.getDgDomicilioGeografico()));
					unidad.setIdUMF(objDicUmf.getCveIdUmf());
					unidad.setNombreCorto(objDicUmf.getNomCorto());
					unidad.setNoEconomico(objDicUmf.getNumEconom());
					
					lstUmf.add(unidad);
					
				}
				
			}else{
				throw new UmfNoLocalizadaException();
			}
			
		}catch(Exception e){
			this.log.error("Ocurrio un error al tratar de recuperar la UMF" ,e);
			throw new UmfNoLocalizadaException();
			
		}
		
		
		
		return lstUmf;
	}
	
	/**
	 * Metodo encargado de recuperar una umf a partir de un codigo postal
	 * @param String codigoPostal
	 * @return UnidadMedicaFamiliar con una lista de umf con subdelegacion y delegacion
	 * @throws UmfNoLocalizadaException
	 */
	@Override
	public  List<UnidadMedicaFamiliar> getUmfByCodigoPostal(String codigoPostal) throws UmfNoLocalizadaException{
		
		List<UnidadMedicaFamiliar> lstUmf =  new ArrayList();
		
		this.log.debug("el objeto trae "+  codigoPostal);
		List<DicUmf> lstUmfCat;
		
		StringBuffer strQuery = new StringBuffer();
		strQuery.append("select distinct umf ");
		strQuery.append(" from  DicUmfCodigoPostal umfCod, DicUmf umf ");
		strQuery.append(" where umfCod.dicUmf.cveIdUmf = umf.cveIdUmf ");
		strQuery.append(" 	and umfCod.numCodigoPostal= :codigo ");
		strQuery.append(" 	and umf.fecRegistroBaja is null ");
		
		Query query = this.getSession().createQuery(strQuery.toString());
		
		
		query.setParameter("codigo", codigoPostal);
		
		try{
			lstUmfCat = query.list();
			if(!lstUmfCat.isEmpty()){
				for (DicUmf objDicUmf :lstUmfCat) {
					//se crean objetos de negocio para su llenado
					UnidadMedicaFamiliar unidad = new UnidadMedicaFamiliar();
					Subdelegacion subdelegacion = new Subdelegacion();
					Delegacion delegacion = new Delegacion();
					
					//se crean objetos de persistencia para no realizar mas consultas en bd
					DicSubdelegacion dicSubdelegacion = objDicUmf.getDicSubdelegacion();  
					DicDelegacion dicDelegacion = dicSubdelegacion.getDicDelegacion();
							
					delegacion.setCiz(dicDelegacion.getCveCiz());
					delegacion.setId(dicDelegacion.getCveIdDelegacion());
					delegacion.setClave(dicDelegacion.getClaveDelegacion());
					delegacion.setDescripcion(dicDelegacion.getDesDeleg());
					
					subdelegacion.setClave(dicSubdelegacion.getClaveSubdelegacion());
					subdelegacion.setId(dicSubdelegacion.getCveIdSubdelegacion());
					subdelegacion.setDescripcion(dicSubdelegacion.getDesSubdelegacion());
					subdelegacion.setDelegacion(delegacion);
					
					unidad.setSubdelegacion(subdelegacion);
					unidad.setDescripcion(objDicUmf.getNomUnidad());
					unidad.setDesDireccion(this.domicilioServiceUtilityLocal.persisToModelDesDireccion(
								objDicUmf.getDgDomicilioGeografico()));
					unidad.setIdUMF(objDicUmf.getCveIdUmf());
					unidad.setNombreCorto(objDicUmf.getNomCorto());
					unidad.setNoEconomico(objDicUmf.getNumEconom());
					unidad.setClavePresupuestal(umfUtility.convertEntityToModelClavePresupuestal(objDicUmf.getDicClavePresupuestal()));
					
					lstUmf.add(unidad);
					
				}
				
			}else{
				throw new UmfNoLocalizadaException();
			}
			
		}catch(Exception e){
			this.log.error("Ocurrio un error al tratar de recuperar la UMF: " + e.getMessage());
			throw new UmfNoLocalizadaException();
			
		}
		
		
		
		return lstUmf;
	}

	
	
	/**
	 */
	public MunicipioIMSS getMunicipioIMSSPorClave(String clave){
		MunicipioIMSS municipio = null;
		
		StringBuffer strQuery = new StringBuffer();
		strQuery.append("select mun ");
		strQuery.append(" from  DicMunicipioImss mun ");
		strQuery.append(" where mun.cveMunicipio = :claveMun ");
		strQuery.append(" 	and mun.fecRegistroBaja is null ");
		strQuery.append(" 	and mun.fecBajaVigencia is null ");
		
		Query query = this.getSession().createQuery(strQuery.toString());
		query.setParameter("claveMun", clave);
		
		try {
		DicMunicipioImss dicMunicipioImss = (DicMunicipioImss) query.uniqueResult();
		municipio = new MunicipioIMSS();
		municipio.setIdMunicipio(String.valueOf(dicMunicipioImss.getCveIdMunicipioImss()));
		municipio.setCvecMunicipioSINDO(dicMunicipioImss.getCveMunicipio());
		municipio.setDescMunicipio(dicMunicipioImss.getNomMunicipioImss());
		municipio.setFechaInicioOperacionesServiciosCampo(dicMunicipioImss.getFecInicioServicioCamp());
		municipio.setFechaInicioOperacionesServiciosUrbanos(dicMunicipioImss.getFecInicioServicioUrb());
		municipio.setIdentificadorConvenio(dicMunicipioImss.getIndConvenio());
		
		if(dicMunicipioImss.getDicTipoAmbito()!=null){
			TipoAmbito ambito = new TipoAmbito();
			ambito.setClave(dicMunicipioImss.getDicTipoAmbito().getCveIdTipoAmbito());
			ambito.setDescripcion(dicMunicipioImss.getDicTipoAmbito().getDesTipoAmbito());
			municipio.setTipoAmbito(ambito);
		}
		} catch (NoResultException nre) {
			super.log.debug("NO SE ENCONTRO NINGU Municipio IMSS con esta clave ");
		}
		return municipio;
	}
	
	@Override
	public Delegacion obtenerDelegacionPorId(Long idDelegacion) {
		DicDelegacion dicDeleg = this.em.find(DicDelegacion.class, idDelegacion);
		
		return domicilioServiceUtilityLocal.convertirEntityToModelDelegacion(dicDeleg);
	}
	
	@Override
	public Subdelegacion obtenerSubdelegacionPorId(Long idSubdelegacion) {
		DicSubdelegacion dicSubdel = this.em.find(DicSubdelegacion.class, idSubdelegacion);
		return domicilioServiceUtilityLocal.convertirEntityToModelSubdelegacion(dicSubdel);
	}
	
	/**
	 * Servicio de devuelve el catalogo de delegaciones  activas que contiene el IMSS 
	 * @return
	 */
	@Override
	public List<Delegacion> findDelegacionesActivas() {
		List<Delegacion> delegaciones = new ArrayList<Delegacion>();
		Criteria queryDel = this.getSession().createCriteria(DicDelegacion.class);
		queryDel.add(Restrictions.isNull("fecRegistroBaja"));
		
		List<DicDelegacion> dicDelegaciones = queryDel.list();
		for(DicDelegacion dicDelegacion: dicDelegaciones) {
			Delegacion delegacion =  domicilioServiceUtilityLocal.convertirEntityToModelDelegacion(dicDelegacion);
			delegaciones.add(delegacion);
		}
		
		return delegaciones;
	}
	
	
	 /**
		 * Servicio de devuelve el catalogo de subdelegaciones  activas que contiene una delegacion IMSS
		 * @param idDelegacion
		 * @return
	*/
	@Override
	public List<Subdelegacion> findSubDelegacionesActivas(Long idDelegacion) {
		List<Subdelegacion> subdelegaciones = new ArrayList<Subdelegacion>();
		Criteria querySubDel = this.getSession().createCriteria(DicSubdelegacion.class);
		querySubDel.createAlias("dicDelegacion", "delegacion");
		querySubDel.add(Restrictions.eq("delegacion.cveIdDelegacion", idDelegacion));
		querySubDel.add(Restrictions.isNull("fecRegistroBaja"));
		List<DicSubdelegacion> dicSubDelegaciones = querySubDel.list();
		
		for(DicSubdelegacion dicSubDelegacion: dicSubDelegaciones) {
			Subdelegacion subdelegacion = domicilioServiceUtilityLocal.convertirEntityToModelSubdelegacion(dicSubDelegacion);
			subdelegaciones.add(subdelegacion);
		}
		
		return subdelegaciones;
	}
	
	
	@Override
	public EntidadFederativa getEstado(String cveEnt) {
		
		EntidadFederativa estado = null;
		DgCatEstado dgCatEstado = (DgCatEstado) this.getSession().get(DgCatEstado.class, cveEnt);
		
		if (dgCatEstado != null) {
			estado = new EntidadFederativa();
			
			estado.setClave(dgCatEstado.getCveEnt());
			estado.setNombre(dgCatEstado.getNomEnt());
		}
		
		return estado;
	}
	
	/* Metodo que crea un objeto UMF defaul 000 con la delegacion y subdelagacion seteada a partir del codigo postal
	 * @param codigoPostal
	 * @return
	 * @throws UmfNoLocalizadaException
	 */
	
	@Override
	public UnidadMedicaFamiliar getUMFDefaultPorCP(String codigoPostal) throws UmfNoLocalizadaException{
			
			
			UnidadMedicaFamiliar unidad; 

			List<DicSubdelegacion> lstSubdelegacion;
			
			StringBuffer strQuery = new StringBuffer();
			strQuery.append("select distinct sub ");
			strQuery.append("from  DicMunicipioImss muni, DitMunicipioImssInegi ine, DgCodigosPostale cod,  " +
					" DitMunicipioSubdelegacion musu, DicSubdelegacion sub  ");
			strQuery.append(" where sub.cveIdSubdelegacion = musu.dicSubdelegacion.cveIdSubdelegacion"); 
			strQuery.append("	and sub.fecRegistroBaja is null");
			strQuery.append("	and musu.dicMunicipioImss.cveIdMunicipioImss = muni.cveIdMunicipioImss");
			strQuery.append("	and muni.cveIdMunicipioImss = ine.dicMunicipioImss.cveIdMunicipioImss ");
			strQuery.append("	and muni.dgCatEstado.cveEnt = ine.dgCatMunicipio.id.cveEnt ");
			strQuery.append("	and ine.dgCatMunicipio.id.cveMun = cod.id.cveMun ");
			strQuery.append("	and ine.dgCatMunicipio.id.cveEnt = cod.id.cveEnt ");
			strQuery.append("	and cod.id.codigo = :codigoPostal ");
			strQuery.append("	and muni.cveMunicipio <> :cveMunLosAng'"); // TODO Revisar caso Los Angeles
			log.debug("el query a ejecutar es " + strQuery.toString());
			
			Query query = this.getSession().createQuery(strQuery.toString());
			query.setParameter("codigoPostal", codigoPostal);
			query.setParameter("cveMunLosAng", "Z28");
			try{
				lstSubdelegacion = query.list();
				if (!lstSubdelegacion.isEmpty()){
					DicSubdelegacion objSubdelegacionImss = (DicSubdelegacion)lstSubdelegacion.get(0);
						
							
							unidad = new UnidadMedicaFamiliar();
							Subdelegacion subdelegacion = new Subdelegacion();
							Delegacion delegacion = new Delegacion();
							//se crean objetos de persistencia para no realizar mas consultas en bd
							  
							DicDelegacion dicDelegacion = objSubdelegacionImss.getDicDelegacion();
									
							delegacion.setCiz(dicDelegacion.getCveCiz());
							delegacion.setId(dicDelegacion.getCveIdDelegacion());
							delegacion.setClave(dicDelegacion.getClaveDelegacion());
							delegacion.setDescripcion(dicDelegacion.getDesDeleg());
							
							subdelegacion.setClave(objSubdelegacionImss.getClaveSubdelegacion());
							subdelegacion.setId(objSubdelegacionImss.getCveIdSubdelegacion());
							subdelegacion.setDescripcion(objSubdelegacionImss.getDesSubdelegacion());
							subdelegacion.setDelegacion(delegacion);
							
							unidad.setSubdelegacion(subdelegacion);
							unidad.setDescripcion("UMF NO LOCALIZADA");
							unidad.setDesDireccion("SIN DIRECCION");
							unidad.setIdUMF(new Long(0));
							unidad.setNombreCorto("000");
							unidad.setNoEconomico(BigDecimal.ZERO);
					
				}else{
					throw new UmfNoLocalizadaException();
				}
				
			}catch(Exception e){
				this.log.error("Ocurrio un error al tratar de recuperar la UMF: " + e.getMessage());
				throw new UmfNoLocalizadaException();
				
			}
				
			return unidad;
		}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Vialidad> obtenerVialidadesAutocompletar(Localidad localidad,
			int periodo, String nomVialidad)
			throws VialidadesNoLocalizadasException {

		StringBuffer sqlQuery = new StringBuffer();

		sqlQuery.append("select * from (");
		sqlQuery.append("select tv.cve_tipo_vial, tv.descripcion, v.nom_via ");
		sqlQuery.append("from dg_vialidad v, dg_cat_vialidad tv ");
		sqlQuery.append("where v.cve_tipo_vial = tv.cve_tipo_vial ");
		sqlQuery.append("and v.cve_ent = :cveEntidad ");
		sqlQuery.append("and v.cve_mun = :cveMunicipio ");
		sqlQuery.append("and v.cve_periodo = :periodo ");
		sqlQuery.append("and v.nom_via like :nomVialidad ");
		sqlQuery.append("group by tv.cve_tipo_vial, tv.descripcion, v.nom_via ");
		sqlQuery.append("order by 3 asc");
		sqlQuery.append(") where rownum <= :rownum");

		javax.persistence.Query query = this.em.createNativeQuery(sqlQuery.toString());
		query.setParameter("cveEntidad", localidad.getMunicipio()
				.getEntidadFederativa().getClave());
		query.setParameter("cveMunicipio", localidad.getMunicipio().getClave());
		query.setParameter("periodo", periodo);
		query.setParameter("nomVialidad", "%" + nomVialidad + "%");
		query.setParameter("rownum", 10);
		
		
		List<Object[]> resultados = query.getResultList();
		List<Vialidad> vialidades = null;

		if (resultados != null && !resultados.isEmpty()) {
			vialidades = new ArrayList<Vialidad>(resultados.size());
			Vialidad vialidad = null;
			
			for (Object[] resultado : resultados) {
				vialidad = new Vialidad(null, resultado[2].toString(),
						((BigDecimal)resultado[0]).intValue(), resultado[1].toString());
				vialidades.add(vialidad);
			}
		} else {
			throw new VialidadesNoLocalizadasException();
		}
		
		return vialidades;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Domicilio obtenerVialidadElegida(Localidad localidad,
			int periodo, Vialidad vialidad)
			throws VialidadesNoLocalizadasException {

		Domicilio domicilio = null;
		
		StringBuffer sqlQuery = new StringBuffer();

		sqlQuery.append("select vial.CVE_VIA, vial.NOM_VIA, vial.CVE_TIPO_VIAL, tv.DESCRIPCION, vial.CVE_LOC, local.NOM_LOC "); 
		sqlQuery.append("from DG_VIALIDAD vial, DG_CAT_VIALIDAD tv, DG_CAT_LOCALIDAD local ");
		sqlQuery.append("where vial.CVE_TIPO_VIAL = tv.CVE_TIPO_VIAL and vial.CVE_LOC = local.CVE_LOC ");
		sqlQuery.append("and vial.CVE_MUN = local.CVE_MUN and vial.CVE_ENT = local.CVE_ENT ");
		sqlQuery.append("and vial.CVE_ENT = :cveEntidad ");
		sqlQuery.append("and vial.CVE_MUN = :cveMunicipio ");
		sqlQuery.append("and vial.CVE_PERIODO = :periodo ");
		sqlQuery.append("and vial.NOM_VIA = :nomVialidad ");
		sqlQuery.append("and vial.CVE_TIPO_VIAL = :cveTipoVialidad");
		
		if(vialidad.getNombre().equalsIgnoreCase("NINGUNO")) {
			sqlQuery.append(" and vial.CVE_VIA > 20000000");
		}

		javax.persistence.Query query = this.em.createNativeQuery(sqlQuery.toString());
		query.setParameter("cveEntidad", localidad.getMunicipio()
				.getEntidadFederativa().getClave());
		query.setParameter("cveMunicipio", localidad.getMunicipio().getClave());
		query.setParameter("periodo", periodo);
		query.setParameter("nomVialidad", vialidad.getNombre());
		query.setParameter("cveTipoVialidad", vialidad.getTipoVialidad().getClave());
		
		List<Object[]> resultados = query.getResultList();
		if (resultados != null && !resultados.isEmpty()) {
			// Siempre se toma la primera que se encuentre
			Object[] resultado = resultados.get(0);
			vialidad = new Vialidad(((BigDecimal)resultado[0]).intValue(), resultado[1].toString(),
					((BigDecimal) resultado[2]).intValue(), resultado[3].toString());
			localidad.setClave(resultado[4].toString());
			localidad.setNombre(resultado[5].toString());
			
			domicilio = new Domicilio();
			domicilio.setVialidadPrimaria(vialidad);
			domicilio.setLocalidad(localidad);
			
		} else {
			throw new VialidadesNoLocalizadasException();
		}
		
		return domicilio;
	}

    @Override
    public List<Domicilio> consultarDomiciliosPersonaFisicaPorTipoOrdenadoPorFecha(Persona persona, List<Long> tiposDomicilio, String orderType) throws DomicilioNoLocalizadoException {
        List<DitPersonafDom> domiciliosPersonaFisica;
        List<Domicilio> domiciliosPersona;

        StringBuilder jpaQuery = new StringBuilder();
        jpaQuery.append("select new DitPersonafDom(domicilios.dgDomicilioGeografico, ");
        jpaQuery.append("domicilios.dicTipoDomicilio) ");
        jpaQuery.append("from DitPersonafDom domicilios ");
        jpaQuery.append("where domicilios.ditPersonaView.cveIdPersona = :idPersona ");
        jpaQuery.append("and domicilios.dicTipoDomicilio.cveIdTipoDomicilio in :tiposDomicilio ");
        if(orderType!=null){
            if(orderType.toUpperCase().equals("ASC")){
                jpaQuery.append("order by domicilios.fecRegistroAlta asc ");
            }else if(orderType.toUpperCase().equals("DESC")){
                jpaQuery.append("order by domicilios.fecRegistroAlta desc ");
            }
        }

        Query query = this.getSession().createQuery(jpaQuery.toString());
        query.setParameter("idPersona", persona.getIdPersona());
        query.setParameterList("tiposDomicilio", tiposDomicilio);

        domiciliosPersonaFisica = query.list();

        if (!domiciliosPersonaFisica.isEmpty()) {
            domiciliosPersona = new ArrayList<Domicilio>();
            Domicilio domicilio;
            for (DitPersonafDom ditPersonafDom : domiciliosPersonaFisica) {
                try {
                    domicilio = this.domicilioServiceUtilityLocal
                            .transformarDomicilio(ditPersonafDom
                                    .getDgDomicilioGeografico());

                    if (ditPersonafDom.getDicTipoDomicilio() != null) {
                        TipoDomicilio tipoDomicilio = new TipoDomicilio();
                        tipoDomicilio.setClave(ditPersonafDom
                                .getDicTipoDomicilio().getCveIdTipoDomicilio()
                                .intValue());
                        tipoDomicilio.setDescripcion(ditPersonafDom
                                .getDicTipoDomicilio().getDesTipoDomicilio());
                        domicilio.setDicTipoDomicilio(tipoDomicilio);
                    }

                    domiciliosPersona.add(domicilio);
                } catch (TransformacionException e) {
                    log.error("Error al transformar el domicilio "
                            + ditPersonafDom.getDgDomicilioGeografico()
                            .getDomicilioId() + ": ", e);
                }
            }
        } else {
            throw new DomicilioNoLocalizadoException("La persona "
                    + persona.getIdPersona() + "no cuenta con domicilios del tipo solicitado");
        }

        return domiciliosPersona;
    }

    @Override
    public Subdelegacion getSubDelegacionPorCP(String codigoPostal) throws SubDelegacionNoLocalizadaException {
        List<DicSubdelegacion> lstSubdelegacion;

        StringBuffer strQuery = new StringBuffer();
        strQuery.append("select distinct sub ");
        strQuery.append("from  DicMunicipioImss muni, DitMunicipioImssInegi ine, DgCodigosPostale cod,  "
                + " DitMunicipioSubdelegacion musu, DicSubdelegacion sub  ");
        strQuery.append(" where sub.cveIdSubdelegacion = musu.dicSubdelegacion.cveIdSubdelegacion");
        strQuery.append("	and sub.fecRegistroBaja is null");
        strQuery.append("	and musu.dicMunicipioImss.cveIdMunicipioImss = muni.cveIdMunicipioImss");
        strQuery.append("	and muni.cveIdMunicipioImss = ine.dicMunicipioImss.cveIdMunicipioImss ");
        strQuery.append("	and muni.dgCatEstado.cveEnt = ine.dgCatMunicipio.id.cveEnt ");
        strQuery.append("	and ine.dgCatMunicipio.id.cveMun = cod.id.cveMun ");
        strQuery.append("	and ine.dgCatMunicipio.id.cveEnt = cod.id.cveEnt ");
        strQuery.append("	and cod.id.codigo = :codigoPostal ");
        strQuery.append("	and muni.cveMunicipio <> :cveMunLosAng'"); // TODO Revisar caso Los Angeles
        log.debug("el query a ejecutar es " + strQuery.toString());

        Query query = this.getSession().createQuery(strQuery.toString());
        query.setParameter("codigoPostal", codigoPostal);
        query.setParameter("cveMunLosAng", "Z28");
        lstSubdelegacion = query.list();
        if (!lstSubdelegacion.isEmpty()) {
            DicSubdelegacion objSubdelegacionImss = (DicSubdelegacion) lstSubdelegacion.get(0);

            Subdelegacion subdelegacion = new Subdelegacion();
            Delegacion delegacion = new Delegacion();
            //se crean objetos de persistencia para no realizar mas consultas en bd

            DicDelegacion dicDelegacion = objSubdelegacionImss.getDicDelegacion();

            delegacion.setCiz(dicDelegacion.getCveCiz());
            delegacion.setId(dicDelegacion.getCveIdDelegacion());
            delegacion.setClave(dicDelegacion.getClaveDelegacion());
            delegacion.setDescripcion(dicDelegacion.getDesDeleg());

            subdelegacion.setClave(objSubdelegacionImss.getClaveSubdelegacion());
            subdelegacion.setId(objSubdelegacionImss.getCveIdSubdelegacion());
            subdelegacion.setDescripcion(objSubdelegacionImss.getDesSubdelegacion());
            subdelegacion.setDelegacion(delegacion);
            return subdelegacion;
        } else {
            throw new SubDelegacionNoLocalizadaException();
        }
    }
    
    @Override
    public List<Subdelegacion> getSubDelegacionesPorCP(String codigoPostal) throws SubDelegacionNoLocalizadaException {
        List<DicSubdelegacion> lstSubdelegacion;
        List<Subdelegacion> subdelegaciones = new ArrayList<Subdelegacion>();
        Subdelegacion subdelegacion;
        StringBuffer strQuery = new StringBuffer();
        strQuery.append("select distinct sub ");
        strQuery.append("from  DicMunicipioImss muni, DitMunicipioImssInegi ine, DgCodigosPostale cod,  "
                + " DitMunicipioSubdelegacion musu, DicSubdelegacion sub  ");
        strQuery.append(" where sub.cveIdSubdelegacion = musu.dicSubdelegacion.cveIdSubdelegacion");
        strQuery.append("	and sub.fecRegistroBaja is null");
        strQuery.append("	and musu.dicMunicipioImss.cveIdMunicipioImss = muni.cveIdMunicipioImss");
        strQuery.append("	and muni.cveIdMunicipioImss = ine.dicMunicipioImss.cveIdMunicipioImss ");
        strQuery.append("	and muni.dgCatEstado.cveEnt = ine.dgCatMunicipio.id.cveEnt ");
        strQuery.append("	and ine.dgCatMunicipio.id.cveMun = cod.id.cveMun ");
        strQuery.append("	and ine.dgCatMunicipio.id.cveEnt = cod.id.cveEnt ");
        strQuery.append("	and cod.id.codigo = :codigoPostal ");
        strQuery.append("	and muni.cveMunicipio <> :cveMunLosAng'");
        log.debug("el query a ejecutar es " + strQuery.toString());

        Query query = this.getSession().createQuery(strQuery.toString());
        query.setParameter("codigoPostal", codigoPostal);
        query.setParameter("cveMunLosAng", "Z28");
        lstSubdelegacion = query.list();
        if (!lstSubdelegacion.isEmpty()) {
            for (DicSubdelegacion dicSub : lstSubdelegacion) {
                subdelegacion = new Subdelegacion();
                Delegacion delegacion = new Delegacion();
                DicDelegacion dicDelegacion = dicSub.getDicDelegacion();

                delegacion.setCiz(dicDelegacion.getCveCiz());
                delegacion.setId(dicDelegacion.getCveIdDelegacion());
                delegacion.setClave(dicDelegacion.getClaveDelegacion());
                delegacion.setDescripcion(dicDelegacion.getDesDeleg());

                subdelegacion.setClave(dicSub.getClaveSubdelegacion());
                subdelegacion.setId(dicSub.getCveIdSubdelegacion());
                subdelegacion.setDescripcion(dicSub.getDesSubdelegacion());
                subdelegacion.setDelegacion(delegacion);
                subdelegaciones.add(subdelegacion);
            }
            return subdelegaciones;
        } else {
            throw new SubDelegacionNoLocalizadaException();
        }
    }
	
	@Override
    public List<DicMunicipioImss> getMunicipioImssPorMunicipioIMSS(List<String> municipioIMSS){
		
		Criteria dicMun = this.getSession().createCriteria(DicMunicipioImss.class);
        dicMun.add(Restrictions.in("cveMunicipio", municipioIMSS));
        dicMun.add(Restrictions.isNull("fecRegistroBaja")); 
        List<DicMunicipioImss> mun = dicMun.list();
		return mun;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Domicilio> consultarDomiciliosPersonaFisicaAcceder(Persona persona)
			throws DomicilioNoLocalizadoException {

		List<DitPersonafDom> domiciliosPersonaFisica = null;
		List<Domicilio> domiciliosPersona = null;

		StringBuffer sql = new StringBuffer(50);
		
		sql.append("SELECT cve_id_personaf_dom FROM DIT_GRUPO_FAMILIAR ");
		sql.append("WHERE CVE_ID_CALIDAD_PARENTESCO in (5,6) and cve_id_asignacion_nss=:idAsignacionNss ");
		javax.persistence.Query query1 = this.em.createNativeQuery(sql.toString());
		query1.setParameter("idAsignacionNss", persona.getRfc());
		
		String cveIdPersonafDom;
		
		try{
			List<Object> lista = query1.getResultList();
			cveIdPersonafDom =  lista.get(0).toString();
			if(cveIdPersonafDom ==null){
				log.error("No se encontro cveIdPersonafDom en DIT_GRUPO_FAMILIAR ");	
				throw new DomicilioNoLocalizadoException();
			}
		}catch (NullPointerException e){
			log.error("No se encontro cveIdPersonafDom en DIT_GRUPO_FAMILIAR ");
			throw new DomicilioNoLocalizadoException();
		}
		
		
		
		
		StringBuffer jpaQuery = new StringBuffer();
		jpaQuery.append("select new DitPersonafDom(domicilios.cveIdPersonafDom, domicilios.dgDomicilioGeografico, ");
		jpaQuery.append("domicilios.dicTipoDomicilio) ");
		jpaQuery.append("from DitPersonafDom domicilios ");
		jpaQuery.append("where domicilios.ditPersonaView.cveIdPersona = :idPersona ");
		jpaQuery.append("and domicilios.cveIdPersonafDom = :cveIdPersonafDom ");

		Query query = this.getSession().createQuery(jpaQuery.toString());
		query.setParameter("idPersona", persona.getIdPersona());
		query.setParameter("cveIdPersonafDom", cveIdPersonafDom);

		domiciliosPersonaFisica = query.list();

		if (!domiciliosPersonaFisica.isEmpty()) {
			domiciliosPersona = new ArrayList<Domicilio>();
			Domicilio domicilio = null;
			for (DitPersonafDom ditPersonafDom : domiciliosPersonaFisica) {
				try {
					domicilio = this.domicilioServiceUtilityLocal
							.transformarDomicilio(ditPersonafDom
									.getDgDomicilioGeografico());

					if (ditPersonafDom.getDicTipoDomicilio() != null) {
						TipoDomicilio tipoDomicilio = new TipoDomicilio();
						tipoDomicilio.setClave(ditPersonafDom
								.getDicTipoDomicilio().getCveIdTipoDomicilio()
								.intValue());
						tipoDomicilio.setDescripcion(ditPersonafDom
								.getDicTipoDomicilio().getDesTipoDomicilio());
						domicilio.setDicTipoDomicilio(tipoDomicilio);
						domicilio.setCveIdPersonafDom( ditPersonafDom.getCveIdPersonafDom() );
					}

					domiciliosPersona.add(domicilio);
				} catch (TransformacionException e) {
					log.error("Error al transformar el domicilio "
							+ ditPersonafDom.getDgDomicilioGeografico()
									.getDomicilioId() + ": ", e);
				}
			}
		} else {
			throw new DomicilioNoLocalizadoException("La persona "
					+ persona.getIdPersona() + "no cuenta con domicilios");
		}

		return domiciliosPersona;
	}
	
    
}

