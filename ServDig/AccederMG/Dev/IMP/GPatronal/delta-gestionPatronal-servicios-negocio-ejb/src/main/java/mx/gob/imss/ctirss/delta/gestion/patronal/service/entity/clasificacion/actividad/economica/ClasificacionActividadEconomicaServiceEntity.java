package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.clasificacion.actividad.economica;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.actividad.economica.ProductoServicioServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.actividad.economica.BienesServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.actividad.economica.MaquinariaEquipoServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.actividad.economica.MateriaPrimaServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.actividad.economica.PersonalServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.actividad.economica.ProcesosTrabajoServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.actividad.economica.ProductoServicioServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.actividad.economica.TransporteServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.clasificacion.actividad.economica.ClasificacionActividadEconomicaServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.clasificacion.AdjuntosClasificacion;
import mx.gob.imss.ctirss.delta.model.clasificacion.TipoCausaAnalisisEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Bien;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EquipoTransporte;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MaquinariaEquipo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MateriaPrima;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Personal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Producto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DicFraccion;
import mx.gob.imss.ctirss.delta.persistence.DicFraccionClase;
import mx.gob.imss.ctirss.delta.persistence.DicModalidad;
import mx.gob.imss.ctirss.delta.persistence.DicTipoCausaAnalisis;
import mx.gob.imss.ctirss.delta.persistence.DicTipoPersona;
import mx.gob.imss.ctirss.delta.persistence.DitAdjuntosClasificacion;
import mx.gob.imss.ctirss.delta.persistence.DitBiene;
import mx.gob.imss.ctirss.delta.persistence.DitBitacoraClasificacion;
import mx.gob.imss.ctirss.delta.persistence.DitClasificacion;
import mx.gob.imss.ctirss.delta.persistence.DitEquipoTransporte;
import mx.gob.imss.ctirss.delta.persistence.DitLlavePatron;
import mx.gob.imss.ctirss.delta.persistence.DitMaquinariaEquipo;
import mx.gob.imss.ctirss.delta.persistence.DitMateriaPrimaMaterial;
import mx.gob.imss.ctirss.delta.persistence.DitPatronGeneral;
import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DitPersonal;
import mx.gob.imss.ctirss.delta.persistence.DitProceso;
import mx.gob.imss.ctirss.delta.persistence.DitProducto;

@Stateless
public class ClasificacionActividadEconomicaServiceEntity extends
		AbstractServiceEntity implements
		ClasificacionActividadEconomicaServiceEntityLocal {

    private static final Logger log = LoggerFactory.getLogger(ClasificacionActividadEconomicaServiceEntity.class);
	
	@EJB
	ClasificacionActividadEconomicaServiceUtilityLocal clasificacionUtlity;
	@EJB
	ProductoServicioServiceUtilityLocal productoUtility;
	@EJB
	MateriaPrimaServiceUtilityLocal materiaPrimaUtility;
	@EJB
	MaquinariaEquipoServiceUtilityLocal equipoUtility;
	@EJB
	TransporteServiceUtilityLocal transporteUtility;
	@EJB
	ProcesosTrabajoServiceUtilityLocal procesoUtility;
	@EJB
	PersonalServiceUtilityLocal personalUtility;
	@EJB
	BienesServiceUtilityLocal bienUtility;
	@EJB
	ProductoServicioServiceEntityLocal productoEntity;
	
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.clasificacion.actividad.economica.ClasificacionActividadEconomicaServiceEntityLocal#consultarPorPatronSujetoObligado(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Clasificacion consultarPorPatronSujetoObligado(
			Long cveIdPatronSujetoObligado) {
		Criteria criteria = this.getSession().createCriteria(
				DitClasificacion.class);
		criteria.createAlias("ditPatronSujetoObligado",
				"ditPatronSujetoObligado").add(
				Restrictions.eq(
						"ditPatronSujetoObligado.cveIdPatronSujetoObligado",
						cveIdPatronSujetoObligado));
		List<Clasificacion> clasificaciones = new ArrayList<Clasificacion>();
		List<DitClasificacion> dtClasificaciones = criteria.list();
		for (DitClasificacion entityClasificacion : dtClasificaciones) {
			clasificaciones.add(clasificacionUtlity
					.convertirEntityToModelClasificacion(entityClasificacion));
		}

		Clasificacion clasificacion = new Clasificacion();
		if (clasificaciones.size() > 0)
			clasificacion = clasificaciones.get(0);
		else
			clasificacion = null;
		
		return clasificacion;
	}


	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.clasificacion.actividad.economica.ClasificacionActividadEconomicaServiceEntityLocal#*(mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion)
	 */
	@Override
	public void actualizarClasificacion(Clasificacion clasificacion, Long cveCausa) {
//		DicFraccion nuevaFraccion = (DicFraccion)this.getSession().load(DicFraccion.class, clasificacion.getFraccion().getId());		
		DicFraccionClase fraccionClase = obtenerFraccionActivaPorIdentificador(clasificacion.getFraccion().getId());
		clasificacion.setCveIdFraccionClase(fraccionClase.getCveIdFraccionClase());
		DitClasificacion entity = clasificacionUtlity.convertirModelToEntity(clasificacion);
		DitClasificacion entityActual = (DitClasificacion)this.em.find(DitClasificacion.class, clasificacion.getId());
//		BigDecimal primaParaAsignar = evaluarPrima(cveCausa, fraccionClase, entityActual);
//		entityActual.setNumPrimaPago(primaParaAsignar);
		log.error("prima asignada: "+entity.getNumPrimaPago());
		Date fechaReg = entityActual.getFecRegistroAlta();
		Date fechaModificacion = Calendar.getInstance().getTime();
		clasificacionUtlity.mergeEntities(entity, entityActual);
		entityActual.setDicFraccionClase(fraccionClase);
		entityActual.setFecRegistroAlta(fechaReg);
		entityActual.setFecRegistroActualizado(fechaModificacion);
		System.err.println("Causa por la cual se actualiza la clasificacion: "+cveCausa);
		
		this.em.merge(entityActual);
	}
	
	
	public BigDecimal evaluarPrima(Long cveCausa, DicFraccionClase nuevaFraccion, DitClasificacion clasificacionActual){
		log.error("Evaluando prima");
		BigDecimal numPrimaPago = null;
		BigDecimal primaActual = clasificacionActual.getNumPrimaPago();
		BigDecimal nuevaPrimaMedia = nuevaFraccion.getDicClase().getNumPrimaMedia();
		Long idFraccionActual=clasificacionActual.getDicFraccionClase().getDicFraccion().getCveIdFraccion();
		Long idFraccionNueva=nuevaFraccion.getDicFraccion().getCveIdFraccion();
		
		Long idClaseActual = clasificacionActual.getDicFraccionClase().getDicClase().getCveIdClase();
		Long idClaseNuevaFraccion = nuevaFraccion.getDicClase().getCveIdClase();
		
		if(cveCausa.intValue() == TipoCausaAnalisisEnum.INSCRIPCION_INICIAL.getClave()){
			log.error("Se asigna Prima media para el alta patronal: "+nuevaPrimaMedia);
			return nuevaPrimaMedia;
		}
		
		if(cveCausa.intValue() != TipoCausaAnalisisEnum.CAMBIO_POR_DISPOSICION_DE_LEY_O_DEL_RACERF.getClave()){
			if(idFraccionActual.equals(idFraccionNueva)){
				log.error("Misma fracción, se conserva la prima: "+primaActual);
				numPrimaPago = primaActual;
			}else {//Distinta Fracción
				log.error("Distinta fraccion");
				if(idClaseActual.equals(idClaseNuevaFraccion)){ //Misma clase conserva la prima
					log.error("Misma clase, se conserva la prima: "+primaActual);
					numPrimaPago = primaActual;
				}else{
					log.error("Distinta clase, se asigna la prima media de la nueva clase: "+nuevaPrimaMedia);
					numPrimaPago = nuevaPrimaMedia;
				}
				
			}
			System.err.println("Se actualiza la prima por no ser cambio por disposicion de ley");
		}else{
			log.error("Cambio por disposición de ley, se conserva la prima: "+primaActual);
			numPrimaPago = primaActual;//Se conserva la prima anterior en cambio por disposición de ley
		}
		
		if(numPrimaPago.longValue() < primaActual.longValue()){
			log.error("Se marcará registro para ser verificado en MAC");
			//TODO Marcar registro, se marca en un proceso anterior poniendo una nota a la solicitud en ref observaciones
		}
		return numPrimaPago;
	}
	
	
	
	private DicFraccionClase obtenerFraccionActivaPorIdentificador(Long cveIdFraccion){
		StringBuffer bfr = new StringBuffer();		
		
		bfr.append("select fc from DicFraccionClase fc where ");
		//bfr.append(" trunc( :fecActual ) between TO_DATE(fc.fecInicio ,'yyyy-MM-dd') and TO_DATE(fc.fecFin, 'yyyy-MM-dd') ");
		bfr.append(" fc.fecFin is null");
		bfr.append(" and fc.dicFraccion.cveIdFraccion = :idFraccion");
		
		Query query = this.em.createQuery(bfr.toString());
//		query.setParameter("fecActual", Calendar.getInstance().getTime());
		query.setParameter("idFraccion", cveIdFraccion);
		DicFraccionClase fraccionDisponible = null;
		try{
			fraccionDisponible = (DicFraccionClase) query.getSingleResult();
		}catch(NoResultException nre){
			fraccionDisponible = null;
		}
		return fraccionDisponible;
	}
	
	
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.clasificacion.actividad.economica.ClasificacionActividadEconomicaServiceEntityLocal#consultarFraccionPorIdentificador(java.lang.Long)
	 */
	@Override
	public Fraccion consultarFraccionPorIdentificador(Long idFraccion) {
		DicFraccion fraccion = (DicFraccion)super.getSession().load(DicFraccion.class, idFraccion);
		return clasificacionUtlity.convertirEntityToModelFraccion(fraccion);
	}
	
	
	@Override
	public Fraccion consultarFraccionPorFraccionCompleta(String fraccionCompleta) throws GestionPatronalBusinessException {
		StringBuffer query = new StringBuffer();
		query.append("select fraccion from DicFraccion fraccion ");
		query.append("join fraccion.dicGrupo grupo ");
		query.append("join grupo.dicDivision division ");
		query.append("where division.numDivision=:numDivision ");
		query.append("and grupo.numGrupo =:numGrupo ");
		query.append("and fraccion.numFraccion =:numFraccion ");
		query.append("and fraccion.fecRegistroBaja is null ");
		
		Query queryHql = this.em.createQuery(query.toString());
		queryHql.setParameter("numDivision", fraccionCompleta.substring(0, 1));
		queryHql.setParameter("numGrupo", fraccionCompleta.substring(1, 2));
		queryHql.setParameter("numFraccion", fraccionCompleta.substring(2));
		DicFraccion fraccion = null;
		try{
			fraccion = (DicFraccion)queryHql.getSingleResult();
		}catch(NoResultException nre){
			throw new GestionPatronalBusinessException("No se localizó la fracción proporcionada");
		}
		return clasificacionUtlity.convertirEntityToModelFraccion(fraccion);
	}

	@Override
	public void guardarNuevaClasificacionActividadEconomica(
			final SujetoObligado sujetoTramite, Long cveCausa) {
			
		try{
			DitPatronSujetoObligado sujetoObligadoActual = (DitPatronSujetoObligado)this.em.find(DitPatronSujetoObligado.class, sujetoTramite.getCveIdSujetoObligado());
			//Se actualiza la nueva clasificación actual
			actualizarClasificacion(sujetoTramite.getClasificacion(), cveCausa);
			//Se asignan los nuevos productos
			reemplazarProductosServicios(sujetoObligadoActual, sujetoTramite);
			//Se asignan las nuevas materias primas
			reemplazarMateriasPrimas(sujetoObligadoActual, sujetoTramite);
			//Se asignan la nueva maquinaria
			reemplazarMaquinaria(sujetoObligadoActual, sujetoTramite);
			//Se asignan los equipos de transporte
			reemplazarEquipoTransporte(sujetoObligadoActual, sujetoTramite);
			//Se actualiza la información de los procesos
			actualizarProceso(sujetoObligadoActual, sujetoTramite);
			//Se actualiza el personal
			reemplazarPersonal(sujetoObligadoActual, sujetoTramite);
			//Se actualizan los bienes
			reemplazarBienes(sujetoObligadoActual, sujetoTramite);
			
			DicFraccion nuevaFraccion = (DicFraccion)em.find(DicFraccion.class, sujetoTramite.getClasificacion().getFraccion().getId());
			if(cveCausa.equals(0L))
				registrarMovimientoDeClasificacionEnBitacora(nuevaFraccion, sujetoObligadoActual, cveCausa.intValue(), 01);
			else if(cveCausa.equals(13L))//TODO agregado en noviembre del 2018 para movimientos de reanudacion de actividades
				registrarMovimientoDeClasificacionEnBitacora(nuevaFraccion, sujetoObligadoActual, cveCausa.intValue(), 03);
			else
				registrarMovimientoDeClasificacionEnBitacora(nuevaFraccion, sujetoObligadoActual, cveCausa.intValue(), 06);
			
		}catch(HibernateException he){
			he.printStackTrace();
		}
	}
	
	private void reemplazarProductosServicios(DitPatronSujetoObligado sujetoObligadoActual, SujetoObligado sujetoTramite){
		List<Producto> productosNuevos =  sujetoTramite.getProductos();
		
		System.out.println("BORRANDO PRODUCTOS");
		for(DitProducto producto : sujetoObligadoActual.getDitProductos())
			this.em.remove(producto);
		
		if(productosNuevos == null )
			return;
		
		System.out.println("AGREGANDO PRODUCTOS");
		for(Producto nuevoProd:productosNuevos){
			if(nuevoProd.getSujetoObligado() == null) {
				SujetoObligado sujetoId = new SujetoObligado();
				sujetoId.setCveIdSujetoObligado(sujetoTramite.getCveIdSujetoObligado());
				nuevoProd.setSujetoObligado(sujetoId);
			}
			DitProducto	entity = null;
			entity = this.productoUtility.convertirModelToEntity(nuevoProd);
			entity.setFecRegistroAlta(Calendar.getInstance().getTime());
			this.em.merge(entity);
		}
		
	}
	
	private void reemplazarMateriasPrimas(DitPatronSujetoObligado sujetoObligadoActual, SujetoObligado sujetoTramite){
		List<MateriaPrima> materiasPrimasNuevas =  sujetoTramite.getMateriaPrimaMateriales();
		
		
		System.out.println("BORRANDO MATERIAS PRIMAS");
		
		for(DitMateriaPrimaMaterial entidad : sujetoObligadoActual.getDitMateriaPrimaMaterials())
			this.em.remove(entidad);
		
		if(materiasPrimasNuevas==null)
			return;
		
		for(MateriaPrima model:materiasPrimasNuevas){
			if(model.getSujetoObligado() == null) {
				SujetoObligado sujetoId = new SujetoObligado();
				sujetoId.setCveIdSujetoObligado(sujetoTramite.getCveIdSujetoObligado());
				model.setSujetoObligado(sujetoId);
			
			}
			DitMateriaPrimaMaterial	entity = null;
			entity = this.materiaPrimaUtility.convertirModelToEntity(model);
			entity.setDitPatronSujetoObligado(sujetoObligadoActual);
			entity.setFecRegistroAlta(Calendar.getInstance().getTime());
			this.em.merge(entity);
		}
		
		
//		sujetoObligadoActual.setDitMateriaPrimaMaterials(materiaPrimaUtility.convertirListOfModelToListOfEntities(materiasPrimasNuevas));
	}
	
	private void reemplazarMaquinaria(DitPatronSujetoObligado sujetoObligadoActual, SujetoObligado sujetoTramite){
		List<MaquinariaEquipo> equipos = sujetoTramite.getEquipos();
		
		System.out.println("BORRANDO MAQUINARIA");
		for(DitMaquinariaEquipo entidad : sujetoObligadoActual.getDitMaquinariaEquipos())
			this.em.remove(entidad);
		
		if(equipos==null)
			return;
		
		for(MaquinariaEquipo model:equipos){
			if(model.getSujetoObligado() == null) {
				SujetoObligado sujetoId = new SujetoObligado();
				sujetoId.setCveIdSujetoObligado(sujetoTramite.getCveIdSujetoObligado());
				model.setSujetoObligado(sujetoId);
			}
			DitMaquinariaEquipo	entity = null;
			entity = this.equipoUtility.convertirModelToEntity(model);
			entity.setFecRegistroAlta(Calendar.getInstance().getTime());
			this.em.merge(entity);
		}
		
		
		//		sujetoObligadoActual.setDitMaquinariaEquipos(equipoUtility.convertListOfModelToListOfEntity(equipos));
	}
	
	private void reemplazarEquipoTransporte(DitPatronSujetoObligado sujetoObligadoActual, SujetoObligado sujetoTramite){
		List<EquipoTransporte> models = sujetoTramite.getEquiposTransporte();
		System.out.println("BORRANDO TRANSPORTE");
		for(DitEquipoTransporte entidad : sujetoObligadoActual.getDitEquipoTransportes())
			this.em.remove(entidad);
		
		if(models==null)
			return;
		
		
		for(EquipoTransporte model:models){
			if(model.getSujetoObligado() == null) {
				SujetoObligado sujetoId = new SujetoObligado();
				sujetoId.setCveIdSujetoObligado(sujetoTramite.getCveIdSujetoObligado());
				model.setSujetoObligado(sujetoId);
			}
			DitEquipoTransporte	entity = null;
			entity = this.transporteUtility.convertirModelToEntity(model);
			entity.setFecRegistroAlta(Calendar.getInstance().getTime());
			this.em.merge(entity);
		}
		
		
		//		sujetoObligadoActual.setDitEquipoTransportes(transporteUtility.convertListOfModelToListOfEntities(models));
	}
	
	private void actualizarProceso(DitPatronSujetoObligado sujetoObligadoActual, SujetoObligado sujetoTramite) {
		DitProceso entity = procesoUtility.convertirModelToEntityProceso(sujetoTramite.getProceso());
		DitPatronSujetoObligado sujetoObligado = (DitPatronSujetoObligado)em.find(
				DitPatronSujetoObligado.class, sujetoTramite.getCveIdSujetoObligado());
		DitProceso actualEntity = null;
		
		
		if(sujetoObligado.getDitProcesos()==null || ( sujetoObligado.getDitProcesos()!=null && sujetoObligado.getDitProcesos().size()==0)){
			actualEntity = new DitProceso();
		}else{
			actualEntity = sujetoObligado.getDitProcesos().get(0);
		}
		
		actualEntity.setDesFinal(entity.getDesFinal());
		actualEntity.setDesInicial(entity.getDesInicial());
		actualEntity.setDesIntermedio(entity.getDesIntermedio());
		actualEntity.setFecRegistroActualizado(Calendar.getInstance().getTime());
		
		actualEntity.setDitPatronSujetoObligado(sujetoObligado);
		
		em.merge(actualEntity);
		
	}
	
	private void reemplazarPersonal(DitPatronSujetoObligado sujetoObligadoActual, SujetoObligado sujetoTramite){
		List<Personal> models = sujetoTramite.getPersonal();
		
		System.out.println("BORRANDO PERSONAL");
		for(DitPersonal entidad : sujetoObligadoActual.getDitPersonals())
			em.remove(entidad);
		
		if(models==null)
			return;
		
		for(Personal model:models){
			if(model.getSujetoObligado() == null) {
				SujetoObligado sujetoId = new SujetoObligado();
				sujetoId.setCveIdSujetoObligado(sujetoTramite.getCveIdSujetoObligado());
				model.setSujetoObligado(sujetoId);
			}
			DitPersonal	entity = null;
			entity = this.personalUtility.convertirModelToEntity(model);
			entity.setFecRegistroAlta(Calendar.getInstance().getTime());
			em.merge(entity);
		}
		
		
		
//		sujetoObligadoActual.setDitPersonals(personalUtility.convertListOfModelToListOfEntity(models));
	}
	
	
	private void reemplazarBienes(DitPatronSujetoObligado sujetoObligadoActual, SujetoObligado sujetoTramite){
		List<Bien> models = sujetoTramite.getBienes();
		
		System.out.println("BORRANDO BIENES");
		for(DitBiene entidad : sujetoObligadoActual.getDitBienes())
			em.remove(entidad);
		
		if(models==null)
			return;
		
		for(Bien model:models){
			if(model.getSujetoObligado() == null) {
				SujetoObligado sujetoId = new SujetoObligado();
				sujetoId.setCveIdSujetoObligado(sujetoTramite.getCveIdSujetoObligado());
				model.setSujetoObligado(sujetoId);
			}
			DitBiene entity = null;
			entity = this.bienUtility.convertirModelToEntity(model);
			entity.setFecRegistroAlta(Calendar.getInstance().getTime());
			em.merge(entity);
		}
		
		sujetoObligadoActual.setDesAfectacion(sujetoTramite.getDesAfectacion());
		sujetoObligadoActual.setDesUsosBienes(sujetoTramite.getDesUsosBienes());
		em.merge(sujetoObligadoActual);
		
		// Se actualiza la llave Patronal
        updateLlavePatron(sujetoObligadoActual);
//		sujetoObligadoActual.setDitBienes(bienUtility.convertListOfModelToListOfEntity(models));
	}

	@Override
	public Fraccion consultarFraccionEquivalente(Fraccion fraccion) {
		Criteria criteria = this.getSession().createCriteria(DicFraccion.class);
		criteria.add(Restrictions.eq("numFraccion", fraccion.getId().toString()));
		criteria.createAlias("dicGrupo", "grupo").add(Restrictions.eq("grupo.numGrupo", fraccion.getGrupo().getId().toString()));
		criteria.createAlias("grupo.dicDivision", "division").add(Restrictions.eq("division.numDivision", fraccion.getGrupo().getDivision().getId().toString()));
		DicFraccion entity = (DicFraccion)criteria.uniqueResult();
		super.log.debug("Fraccion equivalente consultada: "+entity);
		Fraccion fraccionEquivalente = null;
		if(entity!= null)
			fraccionEquivalente = clasificacionUtlity.convertirEntityToModelFraccion(entity);
		else
			fraccionEquivalente = fraccion;
		
		return fraccionEquivalente;
	}


	@Override
	public Long obtenerCausaPorTipoTramite(Long idTipoTramite,
			Long idTipoProceso) {
		StringBuffer hql = new StringBuffer();
		hql.append("select causaAnalisis from DicTipoCausaAnalisis causaAnalisis where causaAnalisis.dicTipoTramite.cveIdTipoTramite =:idTipoTramite");
		hql.append(" and causaAnalisis.tipoProceso =:idTipoProceso");
        Query query = this.em.createQuery(hql.toString());
        query.setParameter("idTipoTramite",idTipoTramite);
        query.setParameter("idTipoProceso",idTipoProceso);
        
        DicTipoCausaAnalisis tca = (DicTipoCausaAnalisis)query.getSingleResult();
        
		return tca.getCveIdTipoCausa();
	}


	@Override
	public void actualizarClasificacionPorIdentificador(
			Clasificacion clasificacion, Integer cveCausa, Integer tpoMovimiento) {
		DitClasificacion ditClasificacion = em.find(DitClasificacion.class, clasificacion.getId());
		
		Long idFraccion = clasificacion.getFraccion().getId();
        if(idFraccion==null)
        	idFraccion = consultarFraccionEquivalente(clasificacion.getFraccion()).getId();
        
		DicFraccion dicFraccion = em.find(DicFraccion.class, idFraccion);
        DicFraccionClase dicFraccionClase = obtenerFraccionActivaPorIdentificador(idFraccion);
		ditClasificacion.setDicFraccionClase(dicFraccionClase);
		ditClasificacion.setFecRegistroActualizado(Calendar.getInstance().getTime());
		
		if(clasificacion.getPrimaSugerida() != null) {
			log.debug("::: Se guarda el valor de la prima sugerida");
	        ditClasificacion.setNumPrimaPago(clasificacion.getPrimaSugerida());
        } else{
	        ditClasificacion.setNumPrimaPago(dicFraccionClase.getDicClase().getNumPrimaMedia());
        }
		
		registrarMovimientoDeClasificacionEnBitacora(dicFraccion, ditClasificacion.getDitPatronSujetoObligado(), cveCausa, tpoMovimiento);
	}


	@Override
	public void actualizarClasificacionPorRegistroPatronal(
			Clasificacion clasificacion, Integer cveCausa, Integer tpoMovimiento) {
		StringBuffer hql = new StringBuffer();
		hql.append("select clasificacion from DitClasificacion clasificacion ");
		hql.append("where clasificacion.ditPatronSujetoObligado.ditPatronGenerals.regPatron =:numeroRegistroPatronal ");		
		hql.append("and clasificacion.ditPatronSujetoObligado.dicModalidad.numModalidad =:numModalidad");
		
		if(clasificacion.getSujetoObligado().getDigVerificador()!=null && clasificacion.getSujetoObligado().getDigVerificador()!="")
			hql.append("and clasificacion.ditPatronSujetoObligado.ditPatronGenerals.digVer =:digVer");
		
		
        Query query = this.em.createQuery(hql.toString());
        query.setParameter("numeroRegistroPatronal",clasificacion.getSujetoObligado().getNumeroRegistroPatronal());
        query.setParameter("numModalidad",clasificacion.getSujetoObligado().getModalidad().getNumModalidad());
        if(clasificacion.getSujetoObligado().getDigVerificador()!=null && clasificacion.getSujetoObligado().getDigVerificador()!="")
        	query.setParameter("digVer",clasificacion.getSujetoObligado().getDigVerificador());
        
        DitClasificacion ditClasificacion = (DitClasificacion)query.getSingleResult();
        
        Long idFraccion = clasificacion.getFraccion().getId();
        if(idFraccion==null)
        	idFraccion = consultarFraccionEquivalente(clasificacion.getFraccion()).getId();
        
        
        DicFraccion dicFraccion = this.em.find(DicFraccion.class, idFraccion);
        DicFraccionClase dicFraccionClase = obtenerFraccionActivaPorIdentificador(idFraccion);
        ditClasificacion.setDicFraccionClase(dicFraccionClase);
        ditClasificacion.setFecRegistroActualizado(Calendar.getInstance().getTime());
                
        if(clasificacion.getPrimaSugerida() != null) {
        	log.debug("::: Se guarda el valor de la prima sugerida");
            ditClasificacion.setNumPrimaPago(clasificacion.getPrimaSugerida());
        } else {
            ditClasificacion.setNumPrimaPago(dicFraccionClase.getDicClase().getNumPrimaMedia());
        }
        
        registrarMovimientoDeClasificacionEnBitacora(dicFraccion, ditClasificacion.getDitPatronSujetoObligado(), cveCausa, tpoMovimiento);
	}
	
	private void registrarMovimientoDeClasificacionEnBitacora(DicFraccion dicFraccionNueva, 
			DitPatronSujetoObligado ditPatronSujetoObligado, Integer cveCausa, Integer tpoMovimiento){
		StringBuffer hql = new StringBuffer();
		hql.append("select ditBitacoraClasificacion from DitBitacoraClasificacion ditBitacoraClasificacion ");
		hql.append("where ditBitacoraClasificacion.ditPatronSujetoObligado.cveIdPatronSujetoObligado =:idPatron ");		
//		hql.append("and ditBitacoraClasificacion.fecRegistroBaja is null");
		
		Query query = this.em.createQuery(hql.toString());
		
		query.setParameter("idPatron", ditPatronSujetoObligado.getCveIdPatronSujetoObligado());
		
		
		StringBuffer hqltp = new StringBuffer();
		hqltp.append("select dicTipoCausa from DicTipoCausaAnalisis dicTipoCausa ");
		hqltp.append("where dicTipoCausa.dicTipoTramite.cveIdTipoTramite =:idTramite ");		
//		hql.append("and ditBitacoraClasificacion.fecRegistroBaja is null");
		
		DicFraccionClase dicFraccionClase = obtenerFraccionActivaPorIdentificador(dicFraccionNueva.getCveIdFraccion());
		
		@SuppressWarnings("unchecked")
		List<DitBitacoraClasificacion> bitacorasVigentes = query.getResultList();
		
        bajaBitacorasClasificacion(bitacorasVigentes);
		
		DitBitacoraClasificacion bitacoraNueva = new DitBitacoraClasificacion();
		bitacoraNueva.setDicFraccion(dicFraccionNueva);
		bitacoraNueva.setDitPatronSujetoObligado(ditPatronSujetoObligado);
		bitacoraNueva.setFecRegistroAlta(Calendar.getInstance().getTime());
		bitacoraNueva.setNumPrima(dicFraccionClase.getDicClase().getNumPrimaMedia());
		bitacoraNueva.setNumConsec(bitacorasVigentes.size()+1);
		bitacoraNueva.setFecIniPrim(Calendar.getInstance().getTime());
		bitacoraNueva.setNumCausa(cveCausa);
		bitacoraNueva.setNumTpoMovto(tpoMovimiento);
		
		
		this.em.persist(bitacoraNueva);
	}

    private void bajaBitacorasClasificacion(List<DitBitacoraClasificacion> bitacorasVigentes) {
		for(DitBitacoraClasificacion bitacora: bitacorasVigentes) {
			if(bitacora.getFecRegistroBaja()==null){
                log.info("baja bitacora con id: {}", bitacora.getCveBitacoraClasificacion());
				bitacora.setFecRegistroBaja(Calendar.getInstance().getTime());
            }
        }
    }

    public void bajaClasificacionPorRegistroPatronal(String registroPatronal, Integer causa) {
        log.info("bajaClasificacionPorRegistroPatronal('{}')", registroPatronal);

        @SuppressWarnings("unchecked")
		List<DitClasificacion> clasificaciones = em.createQuery(new StringBuilder("select clasificacion from\n")
                .append("DitClasificacion clasificacion\n")
                .append("join clasificacion.ditPatronSujetoObligado patronSujetoObligado\n")
                .append("join patronSujetoObligado.ditPatronGenerals patronGeneral\n")
                .append("where :registroPatronal = patronGeneral.regPatron\n")
                .append("and clasificacion.fecRegistroBaja is null")
                .toString())
            .setParameter("registroPatronal", registroPatronal)
            .getResultList();

        Date today = new Date();

        for (DitClasificacion clasificacion : clasificaciones) {
            DitPatronSujetoObligado patronSujetoObligado = clasificacion.getDitPatronSujetoObligado();
            log.info("Baja de PatronSujetoObligado con id: {}", patronSujetoObligado.getCveIdPatronSujetoObligado());
            patronSujetoObligado.setFecRegistroBaja(today);
            
            // Se actualiza la llave Patronal
            updateLlavePatron(patronSujetoObligado);
 
            log.info("Baja de clasificacion para registro patronal: {}", registroPatronal);
            clasificacion.setFecRegistroBaja(today);

            registrarMovimientoBajaClasificacionEnBitacora(patronSujetoObligado, clasificacion, causa, 6);
        }
    }

    private void registrarMovimientoBajaClasificacionEnBitacora(DitPatronSujetoObligado ditPatronSujetoObligado, DitClasificacion clasificacion, Integer cveCausa, Integer tpoMovimiento) {
        Date today = new Date();
        //Obtener bitacoras actuales
        List<DitBitacoraClasificacion> bitacorasVigentes = em.createQuery(new StringBuilder()
                .append("select ditBitacoraClasificacion from DitBitacoraClasificacion ditBitacoraClasificacion\n")
                .append("where ditBitacoraClasificacion.ditPatronSujetoObligado.cveIdPatronSujetoObligado = :idPatron\n")
                .toString())
                .setParameter("idPatron", ditPatronSujetoObligado.getCveIdPatronSujetoObligado())
                .getResultList();

        bajaBitacorasClasificacion(bitacorasVigentes);

        DitBitacoraClasificacion bitacora = new DitBitacoraClasificacion();

        DicFraccion fraccion = clasificacion.getDicFraccionClase().getDicFraccion();
		bitacora.setDicFraccion(fraccion);
		bitacora.setDitPatronSujetoObligado(ditPatronSujetoObligado);
		bitacora.setFecRegistroAlta(today);
        bitacora.setNumPrima(clasificacion.getDicFraccionClase().getDicClase().getNumPrimaMedia());
		bitacora.setNumConsec(bitacorasVigentes.size()+1);
		bitacora.setFecIniPrim(today);
		bitacora.setNumCausa(cveCausa);
		bitacora.setNumTpoMovto(tpoMovimiento);

        em.persist(bitacora);
    }


	@Override
	public Fraccion obtenerFraccionClaseActiva(Long cveIdFraccion) {
		DicFraccionClase dicFraccionClase = obtenerFraccionActivaPorIdentificador(cveIdFraccion);
		return dicFraccionClase!= null ? clasificacionUtlity.convertirEntityToModelFraccion(dicFraccionClase.getDicFraccion()) : null;
	}


	@Override
	public Clasificacion obtenerClasificacionPorId(Long idClasificacion) {
		DitClasificacion clasificacion = this.em.find(DitClasificacion.class, idClasificacion);
		return clasificacionUtlity.convertirEntityToModelClasificacion(clasificacion);
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
	public void guardarArchivoAdjunto(AdjuntosClasificacion adjuntosClasificacion) {
		DitAdjuntosClasificacion entity = new DitAdjuntosClasificacion();
		entity.setCveIdTipoTramite(adjuntosClasificacion.getCveIdTipoTramite());
		entity.setNombreArchivo(adjuntosClasificacion.getNombreArchivo());
		entity.setRefFolio(adjuntosClasificacion.getRefFolio());
		entity.setRutaArchivo(adjuntosClasificacion.getRutaArchivo());
		entity.setFecRegistroAlta(Calendar.getInstance().getTime());
		entity.setFecRegistroActualizado(Calendar.getInstance().getTime());
		em.persist(entity);
	}	
	
	@Override
	public void quitarArchivoAdjunto(String folio, String nombreArchivo) {
		
		StringBuffer hql = new StringBuffer();
		hql.append("select ditAdjuntosClasificacion from DitAdjuntosClasificacion ditAdjuntosClasificacion ");
		hql.append("where ditAdjuntosClasificacion.refFolio =:folio ");		
		hql.append("and ditAdjuntosClasificacion.nombreArchivo=:nombreArchivo");
		
		Query query = this.em.createQuery(hql.toString());		
		query.setParameter("folio", folio);
		query.setParameter("nombreArchivo", nombreArchivo);
					
		@SuppressWarnings("unchecked")
		List<DitAdjuntosClasificacion> adjuntosList = query.getResultList();
		
		for(DitAdjuntosClasificacion entity: adjuntosList){
			entity.setFecRegistroBaja(Calendar.getInstance().getTime());
			em.merge(entity);
		}
		
	}


	@Override
	public List<AdjuntosClasificacion> consultarArchivoAdjunto(String folio) {
		AdjuntosClasificacion adj = null;
		List<AdjuntosClasificacion> adjList = new ArrayList<AdjuntosClasificacion>();
		StringBuffer hql = new StringBuffer();
		hql.append("select ditAdjuntosClasificacion from DitAdjuntosClasificacion ditAdjuntosClasificacion ");
		hql.append("where ditAdjuntosClasificacion.refFolio =:folio ");		
		hql.append("and ditAdjuntosClasificacion.fecRegistroBaja is null");
		
		Query query = this.em.createQuery(hql.toString());		
		query.setParameter("folio", folio);
					
		@SuppressWarnings("unchecked")
		List<DitAdjuntosClasificacion> adjuntosList = query.getResultList();
		for (Iterator<DitAdjuntosClasificacion> iterator = adjuntosList.iterator(); iterator.hasNext();) {
			DitAdjuntosClasificacion ditAdjuntosClasificacion = iterator.next();
			adj = new AdjuntosClasificacion();
			adj.setCveIdTipoTramite(ditAdjuntosClasificacion.getCveIdTipoTramite());
			adj.setFecRegistroActualizado(ditAdjuntosClasificacion.getFecRegistroActualizado());
			adj.setFecRegistroAlta(ditAdjuntosClasificacion.getFecRegistroAlta());
			adj.setFecRegistroBaja(ditAdjuntosClasificacion.getFecRegistroBaja());
			adj.setNombreArchivo(ditAdjuntosClasificacion.getNombreArchivo());
			adj.setRefFolio(ditAdjuntosClasificacion.getRefFolio());
			adj.setRutaArchivo(ditAdjuntosClasificacion.getRutaArchivo());
			adjList.add(adj);
		}
		
		return adjList;
	}
	
}
