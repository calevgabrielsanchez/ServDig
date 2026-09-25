package mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.clasificacion.actividad.economica;
 
import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Bien;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clase;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Division;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EquipoTransporte;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Grupo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MaquinariaEquipo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MateriaPrima;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Personal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Proceso;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Producto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoCombustible;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoMaquinariaEquipo;
import mx.gob.imss.ctirss.delta.persistence.DicClase;
import mx.gob.imss.ctirss.delta.persistence.DicDivision;
import mx.gob.imss.ctirss.delta.persistence.DicFraccion;
import mx.gob.imss.ctirss.delta.persistence.DicFraccionClase;
import mx.gob.imss.ctirss.delta.persistence.DicGrupo;
import mx.gob.imss.ctirss.delta.persistence.DicTipoCombustible;
import mx.gob.imss.ctirss.delta.persistence.DicTipoMaquinariaEquipo;
import mx.gob.imss.ctirss.delta.persistence.DitBiene;
import mx.gob.imss.ctirss.delta.persistence.DitClasificacion;
import mx.gob.imss.ctirss.delta.persistence.DitEquipoTransporte;
import mx.gob.imss.ctirss.delta.persistence.DitMaquinariaEquipo;
import mx.gob.imss.ctirss.delta.persistence.DitMateriaPrimaMaterial;
import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DitPersonal;
import mx.gob.imss.ctirss.delta.persistence.DitProceso;
import mx.gob.imss.ctirss.delta.persistence.DitProducto;

@Stateless
public class ClasificacionActividadEconomicaServiceUtility extends
		AbstractServiceUtility implements
		ClasificacionActividadEconomicaServiceUtilityLocal {

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.SujetoObligadoUtilityLocal#convertirEntityToModelProducto(mx.gob.imss.ctirss.delta.persistence.DitProducto)
	 */
	@Override
	public Producto convertirEntityToModelProducto(DitProducto entity) {
		Producto model = new Producto();
		model.setId(entity.getCveIdProducto());
		model.setDescripcion(entity.getDesProducto());
		return model;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.SujetoObligadoUtilityLocal#convertirEntityToModelMateriaPrima(mx.gob.imss.ctirss.delta.persistence.DitMateriaPrimaMaterial)
	 */
	@Override
	public MateriaPrima convertirEntityToModelMateriaPrima(
			DitMateriaPrimaMaterial entity) {
		MateriaPrima model = new MateriaPrima();
		model.setId(entity.getCveIdMateriaPrimaMaterial());
		model.setDescripcion(entity.getDesMateriaPrimaMaterial());
		return model;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.SujetoObligadoUtilityLocal#convertirEntityToModelMaquinariaEquipo(mx.gob.imss.ctirss.delta.persistence.DitMaquinariaEquipo)
	 */
	@Override
	public MaquinariaEquipo convertirEntityToModelMaquinariaEquipo(
			DitMaquinariaEquipo entity) {
		MaquinariaEquipo model = new MaquinariaEquipo();
		model.setId(entity.getCveIdMaquinariaEquipo());
		model.setDesCapacidadPotencia(entity.getDesCapacidadPotencia());
		model.setDesUso(entity.getDesUso());
		model.setDesNombre(entity.getDesNombre());
		model.setTipo(convertirEntityToModelTipoMaquinariaEquipo(entity.getDicTipoMaquinariaEquipo()));
		model.setNumUnidades(entity.getNumUnidades());
		System.err.println("Asignando numero de unidades: "+model.getNumUnidades());
		return model;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.SujetoObligadoUtilityLocal#convertirEntityToModelTipoMaquinariaEquipo(mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.DicTipoMaquinariaEquipo)
	 */
	@Override
	public TipoMaquinariaEquipo convertirEntityToModelTipoMaquinariaEquipo(
			DicTipoMaquinariaEquipo entity) {
		TipoMaquinariaEquipo model = new TipoMaquinariaEquipo();
		if(entity==null)
			return model;
		
		model.setId(entity.getCveIdTipoMaquinariaEquipo());
		model.setDescripcion(entity.getDesTipoMaquinariaEquipo());
		return model;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.SujetoObligadoUtilityLocal#convertirEntityToModelEquipoTransporte(mx.gob.imss.ctirss.delta.persistence.DitEquipoTransporte)
	 */
	@Override
	public EquipoTransporte convertirEntityToModelEquipoTransporte(
			DitEquipoTransporte equipoTransporte) {
		EquipoTransporte model = new EquipoTransporte();
		model.setDesCapacidadPotencia(equipoTransporte.getDesCapacidadPotencia());
		model.setDesNombre(equipoTransporte.getDesNombre());
		model.setDesUso(equipoTransporte.getDesUso());
		model.setId(equipoTransporte.getCveIdEquipoTransporte());
		model.setNumUnidades(equipoTransporte.getNumUnidades());
		model.setTipoCombustible(convertirEntityToModelTipoCombustible(equipoTransporte.getDicTipoCombustible()));
		return model;
	}

	/**
	 * convierte de entity a model el tipo de combustible, se realiza cambio para que en caso de que
	 * venga null se permita la carga de datos en la pantalla de \"clasificacion\" 
	 */
	@Override
	public TipoCombustible convertirEntityToModelTipoCombustible(
			DicTipoCombustible entity) {
		
		TipoCombustible model = new TipoCombustible();
				
		if(entity != null){
			model.setClave(entity.getCveIdTipoCombustible());
			model.setDesTipoCombustible(entity.getDesTipoCombustible());
		}
		
		return model;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.SujetoObligadoUtilityLocal#convertirEntityToModelProceso(mx.gob.imss.ctirss.delta.persistence.DitProceso)
	 */
	@Override
	public Proceso convertirEntityToModelProceso(DitProceso entity) {
		Proceso model = new Proceso();
		model.setClave(entity.getCveIdProceso());
		model.setDesFinal(entity.getDesFinal());
		model.setDesInicial(entity.getDesInicial());
		model.setDesIntermedio(entity.getDesIntermedio());
		return model;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.SujetoObligadoUtilityLocal#convertirEntityToModelPersonal(mx.gob.imss.ctirss.delta.persistence.DitPersonal)
	 */
	@Override
	public Personal convertirEntityToModelPersonal(DitPersonal personal) {
		Personal model = new Personal();
		model.setClave(personal.getCveIdPersonal());
		model.setNumTrabajadores(personal.getNumeroTrabajadores());
		model.setOficioOcupacion(personal.getOficioOcupacion());
		return model;
	}
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.SujetoObligadoUtilityLocal#convertirEntityToModelPersonal(mx.gob.imss.ctirss.delta.persistence.DitPersonal)
	 */
	@Override
	public Clasificacion convertirEntityToModelClasificacion(DitClasificacion entity){
		System.out
				.println("::: Se valida que las marcas PSP, RPC no vengan NULL, en ClasificacionActividadEconomicaServiceUtility.convertirEntityToModelClasificacion");
		Clasificacion clasificacion = new Clasificacion();
		clasificacion.setId(entity.getCveIdClasificacion());
		Fraccion fraccion = construirFraccionVacia();
		if(entity.getDicFraccionClase()!=null)
			fraccion = convertirEntityToModelFraccion(entity.getDicFraccionClase().getDicFraccion());
		clasificacion.setFraccion(fraccion);
		clasificacion.setIndDistribuyeEntrega(entity.getIndDistribucionEntrega()== null ? 0 : entity.getIndDistribucionEntrega().intValue());
		clasificacion.setIndServiciosATerceros(entity.getIndServicioOtrasPersonas()== null ? 0 : entity.getIndServicioOtrasPersonas().intValue());
		System.err.println("ind transporte ajeno: "+entity.getIndTransporteAjeno());
		clasificacion.setIndTransporteAjeno(entity.getIndTransporteAjeno()== null ? 0 : entity.getIndTransporteAjeno().intValue());
		clasificacion.setIndTransportePropio(entity.getIndTransportePropio()==null ? 0 : entity.getIndTransportePropio().intValue());
		clasificacion.setIndPrestaServicioPersonal(entity.getIndPrestaServicioPersonal()==null ? -1 : entity.getIndPrestaServicioPersonal().intValue());
		clasificacion.setGiro(entity.getManifestacion());		
		clasificacion.setIndRegPatClase(entity.getIndRegPatClase() !=null ? entity.getIndRegPatClase().intValue() : 0);
		clasificacion.setPrimaSRTActual(entity.getNumPrimaPago() != null ? entity.getNumPrimaPago() : new BigDecimal(0));
		clasificacion.setNumCentrosTraba(entity.getNumCentrosTraba() != null ? entity.getNumCentrosTraba() : new BigDecimal(0));
		return clasificacion;
	}
	
	private Fraccion construirFraccionVacia(){
		Fraccion fraccion = new Fraccion();
		Division division = new Division();
		Grupo dicGrupo = new Grupo();
		dicGrupo.setDivision(division);
		fraccion.setGrupo(dicGrupo);
		fraccion.setClase(new Clase());
		return fraccion;
	}
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.SujetoObligadoUtilityLocal#convertirEntityToModelFraccion(mx.gob.imss.ctirss.delta.persistence.DitProducto)
	 */
	@Override
	public Fraccion convertirEntityToModelFraccion(DicFraccion entity){
		Fraccion model = new Fraccion();
		model.setId(entity.getCveIdFraccion());
		model.setDescripcion(entity.getDesFraccion());
		model.setDescripcionDetallada(entity.getDesActividad());
		model.setGrupo(convertirEntityToModelGrupo(entity.getDicGrupo()));
		DicFraccionClase fraccionActual = null;
		List<DicFraccionClase> clasesPorFraccion = entity.getDicFraccionClases();
		if(clasesPorFraccion!=null)
			for(DicFraccionClase fraccionClase:clasesPorFraccion)
				if(fraccionClase.getFecFin()==null){
					fraccionActual = fraccionClase;
					break;
				}
		DicClase clase = new DicClase();
		if(fraccionActual!=null){
			clase = fraccionActual.getDicClase();
		}
		model.setClase(convertirEntityToModelClase(clase));
		model.setPrimaSRT(clase.getNumPrimaMedia());
		System.err.println("Seteando numFraccion: "+entity.getNumFraccion());
		model.setNumFraccion(entity.getNumFraccion());
		return model;
	}
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.SujetoObligadoUtilityLocal#convertirEntityToModelGrupo(mx.gob.imss.ctirss.delta.persistence.DitProducto)
	 */
	@Override
	public Grupo convertirEntityToModelGrupo(DicGrupo entity){
		Grupo model = new Grupo();
		model.setId(entity.getCveIdGrupo());
		model.setDescripcion(entity.getDesGrupo());
		model.setDivision(convertirEntityToModelDivision(entity.getDicDivision()));
		System.err.println("Seteando numGrupo: "+entity.getNumGrupo());
		model.setNumGrupo(entity.getNumGrupo());
		return model;
	}
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.SujetoObligadoUtilityLocal#convertirEntityToModelDivision(mx.gob.imss.ctirss.delta.persistence.DitProducto)
	 */
	@Override
	public Division convertirEntityToModelDivision(DicDivision entity){
		Division model = new Division();
		model.setId(entity.getCveIdDivision());
		model.setDescripcion(entity.getDesDivision());
		System.err.println("Seteando numDivision: "+entity.getNumDivision());
		model.setNumDivision(entity.getNumDivision());
		return model;
	}
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.SujetoObligadoUtilityLocal#convertirEntityToModelClase(mx.gob.imss.ctirss.delta.persistence.DitProducto)
	 */
	@Override
	public Clase convertirEntityToModelClase(DicClase entity){
		Clase model = new Clase();
		model.setClave(entity.getCveIdClase());
		model.setDescripcion(entity.getDesClase());
		return model;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.clasificacion.actividad.economica.ClasificacionActividadEconomicaServiceUtilityLocal#convertirModelToEntity(mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion)
	 */
	@Override
	public DitClasificacion convertirModelToEntity(Clasificacion model) {
		System.out
				.println("::: Se valida que las marcas PSP, RPC no vengan NULL, en ClasificacionActividadEconomicaServiceUtility.convertirModelToEntity");
		System.err.println("Model Clasificacion a convertir: "+model);
		DitClasificacion entity = new DitClasificacion();
		DicFraccion entityFraccion = new DicFraccion();
		entityFraccion.setCveIdFraccion(model.getFraccion().getId());
		DicFraccionClase dicFraccionClase = new DicFraccionClase();
		dicFraccionClase.setCveIdFraccionClase(model.getCveIdFraccionClase());
		dicFraccionClase.setDicFraccion(entityFraccion);
		DitPatronSujetoObligado ditPatronSujetoObligado = new DitPatronSujetoObligado();
		ditPatronSujetoObligado.setCveIdPatronSujetoObligado(model.getSujetoObligado().getCveIdSujetoObligado());
		entity.setCveIdClasificacion(model.getId());
		entity.setDicFraccionClase(dicFraccionClase);
		entity.setDitPatronSujetoObligado(ditPatronSujetoObligado);
		entity.setIndDistribucionEntrega(new BigDecimal(model.getIndDistribuyeEntrega() != null 
			? model.getIndDistribuyeEntrega() : 0));		
		entity.setIndPrestaServicioPersonal(new BigDecimal(model.getIndPrestaServicioPersonal() != null 
			? model.getIndPrestaServicioPersonal() : 0));		
		entity.setIndServicioOtrasPersonas(new BigDecimal(model.getIndServiciosATerceros() != null 
			? model.getIndServiciosATerceros() : 0));
		entity.setIndTransporteAjeno(new BigDecimal(model.getIndTransporteAjeno() != null 
			? model.getIndTransporteAjeno() : 0));
		entity.setIndTransportePropio(new BigDecimal(model.getIndTransportePropio() != null 
			? model.getIndTransportePropio(): 0));
		entity.setIndRegPatClase(new BigDecimal( (model.getIndRegPatClase() != null && model.getIndRegPatClase().intValue()==1) 
			? model.getIndRegPatClase() : 0));
		entity.setNumCentrosTraba(model.getNumCentrosTraba() != null
			? model.getNumCentrosTraba() : new BigDecimal(0));
		entity.setManifestacion(model.getGiro() != null
			? model.getGiro() : "");
		entity.setNumPrimaPago(model.getPrimaSRTActual() != null
			? model.getPrimaSRTActual() : new BigDecimal(0));
		return entity;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.clasificacion.actividad.economica.ClasificacionActividadEconomicaServiceUtilityLocal#mergeEntities(mx.gob.imss.ctirss.delta.persistence.DitClasificacion, mx.gob.imss.ctirss.delta.persistence.DitClasificacion)
	 */
	@Override
	public DitClasificacion mergeEntities(DitClasificacion origen,
			DitClasificacion destino) {
		System.out
				.println("::: Se valida que las marcas PSP, RPC no vengan NULL, en ClasificacionActividadEconomicaServiceUtility.mergeEntities");
		destino.setCveIdClasificacion(origen.getCveIdClasificacion());
		destino.setDicFraccionClase(origen.getDicFraccionClase());
		destino.setDitPatronSujetoObligado(origen.getDitPatronSujetoObligado());
		destino.setFecRegistroActualizado(origen.getFecRegistroActualizado());
		destino.setFecRegistroAlta(origen.getFecRegistroAlta());
		destino.setFecRegistroBaja(origen.getFecRegistroBaja());
		destino.setIndDistribucionEntrega(origen.getIndDistribucionEntrega() != null
				? origen.getIndDistribucionEntrega() : new BigDecimal(0));
		destino.setIndEvaluada(origen.getIndEvaluada() != null
				? origen.getIndEvaluada() : new BigDecimal(0));
		destino.setIndPrestaServicioPersonal(origen.getIndPrestaServicioPersonal() != null
				? origen.getIndPrestaServicioPersonal() : new BigDecimal(0));
		destino.setIndRegPatClase(origen.getIndRegPatClase() != null
				? origen.getIndRegPatClase() : new BigDecimal(0));
		destino.setIndServicioOtrasPersonas(origen.getIndServicioOtrasPersonas() != null
				? origen.getIndServicioOtrasPersonas() : new BigDecimal(0));
		destino.setIndTransporteAjeno(origen.getIndTransporteAjeno() != null
				? origen.getIndTransporteAjeno(): new BigDecimal(0));
		destino.setIndTransportePropio(origen.getIndTransportePropio() != null
				? origen.getIndTransportePropio() : new BigDecimal(0));
		destino.setManifestacion(origen.getManifestacion() != null 
				? origen.getManifestacion() : "");
		destino.setNumCentrosTraba(origen.getNumCentrosTraba() != null
				? origen.getNumCentrosTraba() : new BigDecimal(0));
		destino.setNumPrimaPago(origen.getNumPrimaPago() != null
				? origen.getNumPrimaPago() : new BigDecimal(0));
		return destino;
	}
	
	public Bien convertirEntityToModelBien(DitBiene entity) {
		Bien model = new Bien();
		model.setId(entity.getCveIdBienes());
//		model.setDesAfectacion(entity.getDesAfectacion());
//		model.setDesUsosBienes(entity.getDesUsosBienes());
		model.setDesBienes(entity.getDesBienes()); 
		model.setNumCantidad(entity.getNumCantidad());
		return model;
	}
	
}
