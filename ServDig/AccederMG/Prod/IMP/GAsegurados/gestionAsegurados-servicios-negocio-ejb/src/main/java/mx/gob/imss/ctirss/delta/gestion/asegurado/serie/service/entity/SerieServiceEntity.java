/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.asegurado.serie.service.entity;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.SerieNssAgotadaException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.ErrorAlActivarSerieException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.ErrorAsignarSerieException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.ErrorCrearFoliosDeSerieException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.ErrorGuardarSerieException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.NivelDeAsignacionSerieIndefinidoException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.NumeroDeSeriePorAnioRegistroExisteException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.asegurado.serie.service.utility.SerieServiceUtility;
import mx.gob.imss.ctirss.delta.gestion.asegurado.serie.service.utility.SerieServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.nss.AsignacionSerieNSS;
import mx.gob.imss.ctirss.delta.model.gestion.nss.Serie;
import mx.gob.imss.ctirss.delta.model.gestion.nss.TipoSerie;
import mx.gob.imss.ctirss.delta.persistence.DicDelegacion;
import mx.gob.imss.ctirss.delta.persistence.DicFolioNss;
import mx.gob.imss.ctirss.delta.persistence.DicFolioNssPK;
import mx.gob.imss.ctirss.delta.persistence.DicSeriesNss;
import mx.gob.imss.ctirss.delta.persistence.DicSubdelegacion;
import mx.gob.imss.ctirss.delta.persistence.DitAsignacionNss;
import mx.gob.imss.ctirss.delta.persistence.DitAsignacionSerie;
import mx.gob.imss.ctirss.delta.persistence.DitLlaveAsegurado;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;

/**
 * @author Lucio Duran Silva
 * 
 */
@Stateless
public class SerieServiceEntity extends AbstractServiceEntity implements
		SerieServiceEntityLocal {
	private static final Integer MAXIMO_ANIOS_FOLIO = new Integer(100);

	@EJB
	SerieServiceUtilityLocal utility;

	@Override
	public List<AsignacionSerieNSS> consultarSeriesActivas(Long delegacion,
			Long subdelegacion, Long anioRegistro, Integer idTipoSerie) {
		List<AsignacionSerieNSS> listaSeries = null;
		List<DitAsignacionSerie> listaAsignacionSerieInicial = null;

		Criteria criteria = this.getSession().createCriteria(
				DitAsignacionSerie.class);

		criteria.createAlias("dicSeriesNss", "serie");
		if (anioRegistro != null) {
			criteria.add(Restrictions.eq("serie.numAnioRegistro",
					BigDecimal.valueOf(anioRegistro)));
		}
		
		if (idTipoSerie != null) {
			criteria.add(Restrictions.eq("serie.dicTipoSerie.cveIdTipoSerie", idTipoSerie.longValue()));
		}
		
		if(subdelegacion != null){
			// esto es un usuario a nivel subdelegacional
			
			
			criteria.createAlias("dicDelegacion", "delegacion" , Criteria.LEFT_JOIN);
			criteria.createAlias("dicSubdelegacion", "subdelegacion" , Criteria.LEFT_JOIN);
			
			
			
			criteria.add(Restrictions
					.disjunction()
					.add(Restrictions
							.conjunction()
							.add(Restrictions.eq("delegacion.cveIdDelegacion",
									delegacion))
							.add(Restrictions.eq(
									"subdelegacion.cveIdSubdelegacion",
									subdelegacion)))
					.add(Restrictions
							.conjunction()
							.add(Restrictions.eq("delegacion.cveIdDelegacion",
									delegacion))
							.add(Restrictions.isNull("dicSubdelegacion")))
					.add(Restrictions.isNull("dicDelegacion")));
			
			
			
			
	
			
			
		}else if(delegacion != null){
			// esto es un usuario delegacional
			criteria.createAlias("dicDelegacion", "delegacion" , Criteria.LEFT_JOIN);
			criteria.add(Restrictions.disjunction().add(Restrictions.eq("delegacion.cveIdDelegacion",
					delegacion)).add(Restrictions.isNull("dicDelegacion")));
		}
		
		
		
		
		
		
		
		

		criteria.addOrder(Order.asc("serie.dicTipoSerie"));
		listaAsignacionSerieInicial = criteria.list();

		// REGRESAMOS LA LISTA DE SERIES Y CONFIGURAMOS LOS OBJETOS
		if (listaAsignacionSerieInicial != null) {
			for (DitAsignacionSerie ditAsignacionSerie : listaAsignacionSerieInicial) {
				if (listaSeries == null) {
					listaSeries = new LinkedList();
				}

				AsignacionSerieNSS asignacion = this.utility
						.transformarAsignacionNSSModelo(ditAsignacionSerie);
				listaSeries.add(asignacion);
			}
		}

		return listaSeries;

	}

	@Override
	public AsignacionSerieNSS consultarDetalleSerie(Serie serie) {
		List<DitAsignacionSerie> series = null;
		AsignacionSerieNSS sReturn = null;

		if (serie.getIdSerie() != null) {
			Criteria criteria = this.getSession().createCriteria(
					DitAsignacionSerie.class);

			criteria.createAlias("dicSeriesNss", "serie");
			criteria.add(Restrictions.eq("serie.cveIdSerie", serie.getIdSerie()));

			series = criteria.list();
		}

		if (series != null) {
			DitAsignacionSerie ditAsignacionSerie = series.get(0);
			sReturn = this.utility.transformarAsignacionNSSModelo(ditAsignacionSerie);
		}
		this.log.warn("Serie encontrada :" + sReturn);

		return sReturn;
	}

	@Override
	public Serie getFolioDeAnioNacimientoSerie(
			AsignacionSerieNSS asignacionSerie, Long anioNacimiento)
			throws NivelDeAsignacionSerieIndefinidoException {

		this.log.debug("Consultando el folio para el tipo de serie {"
				+ asignacionSerie.getSerie() + "}");

		boolean hasDelegacion = false;
		boolean hasSubDelegacion = false;

		Serie serie = asignacionSerie.getSerie();
		TipoSerie tipoSerie = serie.getTipoSerie();
		Delegacion delegacion = asignacionSerie.getDelegacion();
		Subdelegacion subdelegacion = asignacionSerie.getSubdelegacion();

		// Un folio esta Activo cuando su fecha de baja es igual nulo.
		StringBuffer bfrQuery = new StringBuffer();
		bfrQuery.append("select new mx.gob.imss.ctirss.delta.model.gestion.nss.Serie (series.cveIdSerie, ");
		bfrQuery.append("series.numSerie, series.numAnioRegistro, series.dicTipoSerie.cveIdTipoSerie, ");
		bfrQuery.append("folios.numFolio, folios.id.numAnioNacimiento, "); 
		bfrQuery.append("folios.nomSecuenciaNss)");
		bfrQuery.append("from DitAsignacionSerie as asignacion ");
		bfrQuery.append("join asignacion.dicSeriesNss as series ");
		bfrQuery.append("join series.dicFolioNsses as folios ");
		bfrQuery.append("where series.dicTipoSerie.cveIdTipoSerie = :tipoSerie ");
		bfrQuery.append("and series.numAnioRegistro = :anioRegistro ");
		bfrQuery.append("and folios.id.numAnioNacimiento = :anioNacimiento ");
		bfrQuery.append("and folios.fecRegistroBaja is null ");

		Integer nivelAsignacion = this.utility
				.getNivelAsignacion(asignacionSerie);
		
		// Validamos que sean diferentes de nulos la delegacion
		if (nivelAsignacion.intValue() == SerieServiceUtility.ASIGNACION_SERIE_NIVEL_CENTRAL
				.intValue()) {
			bfrQuery.append("and asignacion.dicDelegacion.cveIdDelegacion is null ");
			bfrQuery.append("and asignacion.dicSubdelegacion.cveIdSubdelegacion is null ");
		} else if (nivelAsignacion.intValue() == SerieServiceUtility.ASIGNACION_SERIE_NIVEL_DELEGACION
				.intValue()) {
			bfrQuery.append("and asignacion.dicDelegacion.cveIdDelegacion = :delegacion ");
			bfrQuery.append("and asignacion.dicSubdelegacion.cveIdSubdelegacion is null ");
			hasDelegacion = true;
		} else if (nivelAsignacion.intValue() == SerieServiceUtility.ASIGNACION_SERIE_NIVEL_SUBDELEGACION
				.intValue()) {
			bfrQuery.append("and asignacion.dicDelegacion.cveIdDelegacion = :delegacion ");
			bfrQuery.append("and asignacion.dicSubdelegacion.cveIdSubdelegacion = :subdelegacion ");
			hasSubDelegacion = true;
		}
		bfrQuery.append("order by series.numSerie asc");
		
		Query query = this.em.createQuery(bfrQuery.toString());
		query.setParameter("tipoSerie", tipoSerie.getIdTipoSerie());
		query.setParameter("anioRegistro", serie.getAnioRegistro());
		query.setParameter("anioNacimiento", anioNacimiento);

		if (hasDelegacion) {
			query.setParameter("delegacion", delegacion.getId());
		}
		if (hasSubDelegacion) {
			query.setParameter("delegacion", delegacion.getId());
			query.setParameter("subdelegacion", subdelegacion.getId());
		}

		List<Serie> series = query.getResultList();

		if (series != null && !series.isEmpty()) {
			/*
			 * regresamos la primer serie de resultado, ya que se encuentran
			 * ordenadas de manera ascendente
			 */

			Serie s = series.get(0);
			return s;
		} else {
			/*
			 * Si no existen series regresamos nulo.
			 */
			return null;
		}

	}

	@Override
	public Long incrementaFolioDeSerie(Serie serie, Long anioNacimiento)
			throws SerieNssAgotadaException {

		String secuenciaNss = serie.getSecuenciaNss();
		Long folioNuevo = null;
		
		StringBuffer sqlQuery = new StringBuffer();
		sqlQuery.append("SELECT ").append(secuenciaNss).append(".NEXTVAL ");
		sqlQuery.append("AS folio FROM DUAL");

		org.hibernate.Query query = this.getSession()
				.createSQLQuery(sqlQuery.toString())
				.addScalar("folio", StandardBasicTypes.LONG);
		
		try {
			folioNuevo = (Long) query.uniqueResult();
		} catch (HibernateException e) {
			/*
			 * Se busca el código ORA-08004, que es el que Oracle lanza cuando
			 * la secuenca ha llegado a su límite
			 */
			if(e.getCause().getMessage().contains("ORA-08004")) {
				this.log.error("La secuencia " + secuenciaNss + " ha llegado a su límite");
				throw new SerieNssAgotadaException(secuenciaNss);
			} else {
				this.log.error(e);
			}
		}
		
		return folioNuevo;
	}

	@Override
	public Serie getSerieInactivaDeFolio(AsignacionSerieNSS serieAnterior,
			Long anioNacimiento)
			throws NivelDeAsignacionSerieIndefinidoException {

		/*
		 * 1. Validar el nivel de asignacion de la serie solicitada
		 */
		this.utility.getNivelAsignacion(serieAnterior);

		/*
		 * 2. Buscamos una serie disponible del mismo nivel solicitado. En
		 * realidad lo que se busca es un Folio Inactivo
		 * 
		 * Folio inactivo es aquel que la fecha de baja y de alta son las mismas
		 * 
		 * Folio desactivado es aquel que la fecha de baja es mayor a la fecha
		 * de alta.
		 */

		Serie serieInactivaNuevaMismoNivel = this
				.getSerieInactivaPorFolioAnioNacimientoMismoNivel(
						serieAnterior, anioNacimiento.intValue());

		return serieInactivaNuevaMismoNivel;
	}

	/**
	 * 
	 * @param asignacionSerieNSS
	 * @return
	 * @throws NivelDeAsignacionSerieIndefinidoException
	 */
	private Serie getSerieInactivaPorFolioAnioNacimientoMismoNivel(
			AsignacionSerieNSS asignacionSerieNSS, Integer anioNacimiento)
			throws NivelDeAsignacionSerieIndefinidoException {
		
		/*
		 * Un folio esta Inactivo, cuando su fecha de baja es igual a la fecha
		 * de alta
		 */
		StringBuffer bfrQuery = new StringBuffer();
		bfrQuery.append("select new mx.gob.imss.ctirss.delta.model.gestion.nss.Serie ( series.cveIdSerie, ");
		bfrQuery.append("series.numSerie , series.numAnioRegistro, series.dicTipoSerie.cveIdTipoSerie , ");
		bfrQuery.append("folios.numFolio, folios.id.numAnioNacimiento, ");
		bfrQuery.append("folios.nomSecuenciaNss)");
		bfrQuery.append("from DitAsignacionSerie as asignacion ");
		bfrQuery.append("join asignacion.dicSeriesNss as series ");
		bfrQuery.append("join series.dicFolioNsses as folios ");
		bfrQuery.append("where series.dicTipoSerie.cveIdTipoSerie = :tipoSerie ");
		bfrQuery.append("and series.numAnioRegistro = :anioRegistro ");
		bfrQuery.append("and folios.id.numAnioNacimiento = :anioNacimiento ");
		bfrQuery.append("and folios.fecRegistroBaja is not null ");
		bfrQuery.append("and folios.fecRegistroBaja = folios.fecRegistroAlta ");

		Boolean hasDelegacion = Boolean.FALSE;
		Boolean hasSubDelegacion = Boolean.FALSE;

		Integer nivelAsignacion = this.utility
				.getNivelAsignacion(asignacionSerieNSS);

		if (nivelAsignacion.intValue() == SerieServiceUtility.ASIGNACION_SERIE_NIVEL_CENTRAL
				.intValue()) {
			bfrQuery.append("and asignacion.dicDelegacion.cveIdDelegacion is null ");
			bfrQuery.append("and asignacion.dicSubdelegacion.cveIdSubdelegacion is null ");
		} else if (nivelAsignacion.intValue() == SerieServiceUtility.ASIGNACION_SERIE_NIVEL_DELEGACION
				.intValue()) {
			bfrQuery.append("and asignacion.dicDelegacion.cveIdDelegacion = :delegacion ");
			bfrQuery.append("and asignacion.dicSubdelegacion.cveIdSubdelegacion is null ");
			hasDelegacion = true;
		} else if (nivelAsignacion.intValue() == SerieServiceUtility.ASIGNACION_SERIE_NIVEL_SUBDELEGACION
				.intValue()) {
			bfrQuery.append("and asignacion.dicDelegacion.cveIdDelegacion = :delegacion ");
			bfrQuery.append("and asignacion.dicSubdelegacion.cveIdSubdelegacion = :subdelegacion ");
			hasSubDelegacion = true;
		}
		bfrQuery.append("order by series.numSerie asc");
		
		TipoSerie tipoSerie = asignacionSerieNSS.getSerie().getTipoSerie();
		Delegacion delegacion = asignacionSerieNSS.getDelegacion();
		Subdelegacion subdelegacion = asignacionSerieNSS.getSubdelegacion();
		Integer anioRegistro = asignacionSerieNSS.getSerie().getAnioRegistro();
		
		Query query = this.em.createQuery(bfrQuery.toString());
		query.setParameter("tipoSerie", tipoSerie.getIdTipoSerie());
		query.setParameter("anioRegistro", anioRegistro);
		query.setParameter("anioNacimiento", anioNacimiento);
		
		if (hasDelegacion) {
			query.setParameter("delegacion", delegacion.getId());
		}
		
		if (hasSubDelegacion) {
			query.setParameter("delegacion", delegacion.getId());
			query.setParameter("subdelegacion", subdelegacion.getId());
		}

		List<Serie> series = query.getResultList();
		
		this.log.debug("Series localizadas desde getSerieInactivaPorFolioAnioNacimientoMismoNivel ----> " + series);

		if (series != null && !series.isEmpty()) {
			// Regresamos la primera serie....
			return series.get(0);
		} else {
			/*
			 * En caso de no encontrar con una serie/folio inactivo del mismo
			 * nivel.
			 */
			return null;
		}

	}

	@Override
	public AsignacionNSS asignarNSSAsegurado(AsignacionNSS asignacion) {

		this.log.debug("Asignando el NSS a la persona:" + asignacion);

		DitAsignacionNss ditAsignacionNss = new DitAsignacionNss();
		DitPersona ditPersona = new DitPersona();

		ditPersona.setCveIdPersona(asignacion.getIdPersona());
		ditAsignacionNss.setDitPersona(ditPersona);
		ditAsignacionNss.setNumNss(asignacion.getNss());
		ditAsignacionNss.setFecRegistroActualizado(new Date());
		ditAsignacionNss.setFecRegistroAlta(new Date());

		this.em.persist(ditAsignacionNss);
		this.em.flush();

		// Se inserta llaves de Asegurado
		DitLlaveAsegurado ditLlaveAsegurado = new DitLlaveAsegurado();
		ditLlaveAsegurado.setRefBusca(asignacion.getNss());
		ditLlaveAsegurado.setDitAsignacionNss(ditAsignacionNss);
		ditLlaveAsegurado.setDitPersona(ditPersona);
		this.em.persist(ditLlaveAsegurado);
		
		asignacion.setIdAsignacionNSS(ditAsignacionNss.getCveIdAsignacionNss());
		return asignacion;
	}

	@Override
	public Serie consultarSerieAsignadaPorTipoDeSerie(
			AsignacionSerieNSS asingacionSerie) {

		Serie serie = asingacionSerie.getSerie();
		TipoSerie tipo = serie.getTipoSerie();
		Delegacion delegacion = asingacionSerie.getDelegacion();
		Subdelegacion subdelegacion = asingacionSerie.getSubdelegacion();

		this.log.debug("Localizando la serie para el tipo de serie :"
				+ tipo.getIdTipoSerie());
		this.log.debug("Delegacion :" + delegacion.getId());
		this.log.debug("Subdelegacion :" + subdelegacion.getId());

		return null;
	}

	@Override
	public Serie guardarSerie(Serie serie) throws ErrorGuardarSerieException {

		this.log.debug("Guardando la Serie ..." + serie);
		try {

			DicSeriesNss dicSerieNss = this.utility
					.transformarSerieEntity(serie);

			// Seteamos los valores de las fechas
			dicSerieNss.setFecRegistroActualizado(new Date());
			dicSerieNss.setFecRegistroBaja(new Date());
			dicSerieNss.setFecRegistroAlta(new Date());

			this.em.persist(dicSerieNss);

			serie.setIdSerie(dicSerieNss.getCveIdSerie());

		} catch (Exception e) {
			this.log.error(e.getMessage(), e);
			throw new ErrorGuardarSerieException();
		}

		return serie;
	}

	@Override
	public Serie consultarNumSeriePorAnioRegistro(Serie serie)
			throws NumeroDeSeriePorAnioRegistroExisteException {

		if (serie == null) {
			this.log.error("No se recibieron suficientes parametros para la consulta ...");
		} else {
			if (serie.getNumSerie() == null) {
				this.log.error("No se recibio el numero de serie..");
				throw new NumeroDeSeriePorAnioRegistroExisteException();
			}
			if (serie.getAnioRegistro() == null) {
				this.log.error("No se recibio el anio de registro ...");
				throw new NumeroDeSeriePorAnioRegistroExisteException();
			}

			this.log.debug("Consultando el Numero de Serie : " + serie.getNumSerie());
			this.log.debug(" para el anio de registro  : " + serie.getAnioRegistro());
		}

		StringBuffer bfr = new StringBuffer();
		bfr.append(" from DicSeriesNss s");
		bfr.append(" where s.numSerie = :numSerie");
		bfr.append(" and   s.numAnioRegistro = :numAnioRegistro");

		Query query = this.em.createQuery(bfr.toString());
		query.setParameter("numSerie", serie.getNumSerie());
		query.setParameter("numAnioRegistro", serie.getAnioRegistro());

		List<DicSeriesNss> dicSeries = query.getResultList();

		// Validamos si es diferente de nulo y no esta vacia la lista
		if (dicSeries != null && !dicSeries.isEmpty()) {
			throw new NumeroDeSeriePorAnioRegistroExisteException();
		}

		return serie;

	}

	@Override
	public void crearFoliosDeSerie(Serie serie)
			throws ErrorCrearFoliosDeSerieException {

		this.log.debug("Inciando la creacion de los folios para la serie...."
				+ serie);

		if (serie.getIdSerie() == null) {
			this.log.error("No se recibio el Id de la serie a crear los folios ...");
			throw new ErrorCrearFoliosDeSerieException();
		}

		DicFolioNss dicFolio = null;
		DicFolioNssPK pk = null;
		DicSeriesNss dicSeries = new DicSeriesNss();
		dicSeries.setCveIdSerie(serie.getIdSerie());

		Integer count = new Integer(0);
		this.log.debug("Iniciando la persistencia de los folio ..");
		while (count < MAXIMO_ANIOS_FOLIO) {

			dicFolio = new DicFolioNss();
			dicFolio.setDicSeriesNss(dicSeries);
			dicFolio.setNumFolio(BigDecimal.ZERO);

			dicFolio.setFecRegistroActualizado(new Date());
			dicFolio.setFecRegistroAlta(new Date());
			dicFolio.setFecRegistroBaja(new Date());

			pk = new DicFolioNssPK();
			pk.setCveIdSerie(serie.getIdSerie());
			pk.setNumAnioNacimiento(count);
			serie.setAnioNacimiento(count);

			dicFolio.setId(pk);

			String nomSecuencia = crearSequenciaSerie(serie);
			dicFolio.setNomSecuenciaNss(nomSecuencia);

			this.em.persist(dicFolio);

			
			count++;
		}
		this.log.debug("Termino la persistencia de los folio ..");

	}

	@Override
	public AsignacionSerieNSS asignarSerie(AsignacionSerieNSS asignacionDeSerie)
			throws ErrorAsignarSerieException {

		this.log.debug("Iniciando la asignacion de la serie ...");

		Serie serie = asignacionDeSerie.getSerie();

		if (serie == null) {
			this.log.error("No se recibio la serie ...");
			throw new ErrorAsignarSerieException();
		}

		if (serie.getIdSerie() == null) {
			this.log.error("No se recibio el Id de la Serie , ...");
			throw new ErrorAsignarSerieException();
		}

		Delegacion delegacion = asignacionDeSerie.getDelegacion();
		Subdelegacion subdelegacion = asignacionDeSerie.getSubdelegacion();

		DitAsignacionSerie ditAsignacionSerie = new DitAsignacionSerie();
		DicSeriesNss dicSeriesNss = new DicSeriesNss();
		dicSeriesNss.setCveIdSerie(serie.getIdSerie());
		ditAsignacionSerie.setDicSeriesNss(dicSeriesNss);

		if ((delegacion != null && delegacion.getId() != null)
				&& (subdelegacion == null)) {
			this.log.debug("La asignacion sera de tipo delegacion ...");

			DicDelegacion dicDelegacion = new DicDelegacion();
			dicDelegacion.setCveIdDelegacion(delegacion.getId());

			ditAsignacionSerie.setDicDelegacion(dicDelegacion);

		}

		else if ((subdelegacion != null && subdelegacion.getId() != null)
				&& (delegacion != null && delegacion.getId() != null)) {
			this.log.debug("La asignacion sera de nivel subdelegacion ...");

			DicDelegacion dicDelegacion = new DicDelegacion();
			dicDelegacion.setCveIdDelegacion(delegacion.getId());
			ditAsignacionSerie.setDicDelegacion(dicDelegacion);

			DicSubdelegacion dicSubdelegacion = new DicSubdelegacion();
			dicSubdelegacion.setCveIdSubdelegacion(subdelegacion.getId());
			ditAsignacionSerie.setDicSubdelegacion(dicSubdelegacion);

		}

		else if (delegacion == null && subdelegacion == null) {
			this.log.debug("La asignacion sera de nivel general ...");
			// System.out.println("------ 1 ");
			// throw new ErrorAsignarSerieException();
		}

		this.em.persist(ditAsignacionSerie);

		return asignacionDeSerie;
	}

	@Override
	public void activarFolioDeSerie(Serie serie)
			throws ErrorAlActivarSerieException {

		this.log.debug("Activando la serie ..." + serie);

		DicFolioNss dicFolio = null;
		DicFolioNssPK pk = new DicFolioNssPK();
		pk.setCveIdSerie(serie.getIdSerie());
		pk.setNumAnioNacimiento(serie.getAnioNacimiento());

		dicFolio = this.em.find(DicFolioNss.class, pk);

		if (dicFolio == null) {
			throw new ErrorAlActivarSerieException(
					"No se activo la serie, por que no se localizo.");
		} else {

			dicFolio.setFecRegistroBaja(null);
			dicFolio.setFecRegistroActualizado(new Date());
		}

	}

	
	/*
	 * Este metodo se identifico que se requiere que este independiente de la transaccion global, para que en casos de falla ( en la transaccion global) 
	 * no realice el rollback de este metodo.
	 * 
	 * (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.asegurado.serie.service.entity.SerieServiceEntityLocal#desactivarFolioDeSerie(mx.gob.imss.ctirss.delta.model.gestion.nss.Serie)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void desactivarFolioDeSerie(Serie serie)
			throws ErrorAlActivarSerieException {

		this.log.debug("Desactivando  la serie ..." + serie);

		DicFolioNss dicFolio = null;
		DicFolioNssPK pk = new DicFolioNssPK();
		pk.setCveIdSerie(serie.getIdSerie());
		pk.setNumAnioNacimiento(serie.getAnioNacimiento());

		//dicFolio = this.em.find(DicFolioNss.class, pk);
		
		dicFolio =(DicFolioNss) this.getSession().load(DicFolioNss.class, pk);
		

		if (dicFolio == null) {
			throw new ErrorAlActivarSerieException(
					"No se desactivo la serie, por que no se localizo.");
		} else {
			dicFolio.setFecRegistroBaja(new Date());
			dicFolio.setFecRegistroActualizado(new Date());
			
		}

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean existeNSS(String nss) {

		boolean existeNSS = false;
		StringBuffer jpaQuery = new StringBuffer();
		Query query = null;

		this.log.debug("Se busca NSS (" + nss + ") en tabla DIT_ASIGNACION_NSS");

		jpaQuery.append("select new DitAsignacionNss(asig.numNss) ");
		jpaQuery.append("from DitAsignacionNss asig ");
		jpaQuery.append("where asig.numNss = :nss");

		query = this.em.createQuery(jpaQuery.toString());
		query.setParameter("nss", nss);

		List<DitAsignacionNss> nssEncontrados = query.getResultList();

		if (nssEncontrados != null && !nssEncontrados.isEmpty()) {
			existeNSS = true;
			this.log.warn("NSS (" + nss
					+ ") encontrado en tabla DIT_ASIGNACION_NSS");
		}
		
		if (!existeNSS) {

			this.log.debug("Se busca NSS (" + nss
					+ ") en tabla D_CANASE_PPCANA01");
			
			jpaQuery.delete(0, jpaQuery.length());
			jpaQuery.append("select count(*) from D_CANASE_PPCANA01 ");
			jpaQuery.append("where nss = :nss");
			
			query = this.em.createNativeQuery(jpaQuery.toString());
			query.setParameter("nss", nss);

			Number countNSS = (Number) query.getSingleResult();

			if (countNSS != null && countNSS.intValue() > 0) {
				existeNSS = true;
				this.log.warn("NSS ("+ nss
						+ ") encontrado en tabla D_CANASE_PPCANA01");
			}
		}		
		
		if (!existeNSS) {

			this.log.debug("Se busca NSS (" + nss
					+ ") en tabla D_SINDO_SSAS_ASEG_HIST_CENTRAL");
			
			jpaQuery.delete(0, jpaQuery.length());
			jpaQuery.append("select count(*) from D_SINDO_SSAS_ASEG_HIST_CENTRAL ");
			jpaQuery.append("where nss = :nss");

			query = this.em.createNativeQuery(jpaQuery.toString());
			query.setParameter("nss", nss);

			Number countNSS = (Number) query.getSingleResult();

			if (countNSS != null && countNSS.intValue() > 0) {
				existeNSS = true;
				this.log.warn("NSS ("+ nss
						+ ") encontrado en tabla D_SINDO_SSAS_ASEG_HIST_CENTRAL");
			}
		}

		if (!existeNSS) {

			this.log.debug("Se busca NSS (" + nss
					+ ") en tabla ASEGURADOS_TEMP_UNIF");
			
			jpaQuery.delete(0, jpaQuery.length());
			jpaQuery.append("select count(*) from ASEGURADOS_TEMP_UNIF ");
			jpaQuery.append("where cve_nss = :nss");

			query = this.em.createNativeQuery(jpaQuery.toString());
			query.setParameter("nss", nss);

			Number countNSS = (Number) query.getSingleResult();

			if (countNSS != null && countNSS.intValue() > 0) {
				existeNSS = true;
				this.log.warn("NSS ("+ nss
						+ ") encontrado en tabla ASEGURADOS_TEMP_UNIF");
			}
		}
		
		return existeNSS;
	}

	private String crearSequenciaSerie(Serie serie) {
		log.error("Se calcula secuencia");
		NumberFormat formato2Digitos = new DecimalFormat("00");

		Long numSerie = serie.getNumSerie();
		Integer anioRegistro = serie.getAnioRegistro();
		Integer anioNacimiento = serie.getAnioNacimiento();

		StringBuffer sbNombreSecuencia = new StringBuffer();
		
		sbNombreSecuencia.append("SEQ_NSS")
				.append(formato2Digitos.format(numSerie))
				//*Se comenta el registro por cambios para el proceso de reinicio de series de cada año*/
//				.append(formato2Digitos.format(anioRegistro))
				.append(formato2Digitos.format(anioNacimiento));
		System.out.println("Secuencia calculada: " + sbNombreSecuencia.toString());

		return sbNombreSecuencia.toString();
	}
}
