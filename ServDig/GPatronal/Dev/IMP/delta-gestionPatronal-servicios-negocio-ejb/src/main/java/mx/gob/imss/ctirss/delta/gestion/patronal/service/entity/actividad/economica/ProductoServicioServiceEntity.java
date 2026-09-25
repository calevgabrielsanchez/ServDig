package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.actividad.economica;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.actividad.economica.ProductoServicioServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Producto;
import mx.gob.imss.ctirss.delta.persistence.DitProducto;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

@Stateless
public class ProductoServicioServiceEntity extends AbstractServiceEntity
		implements ProductoServicioServiceEntityLocal {
	
	@EJB
	private ProductoServicioServiceUtilityLocal productoServicioServiceUtility;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public DatosSalidaPaginador<Producto> paginar(
			DatosEntradaPaginador<Producto> params) {
		List<Producto> aaData = null;
		DitProducto entity = new DitProducto();
		DatosSalidaPaginador<Producto> salida = new DatosSalidaPaginador<Producto>();
		List entities = null;
		System.out.println("Modelo para paginar producto: "+params.getModelo());
		try {
			entity = this.productoServicioServiceUtility.convertirModelToEntity(params.getModelo());
			salida = this.paginar(params ,entity);
			entities = salida.getAaData();
			aaData = this.productoServicioServiceUtility.convertListOfEntitiesToListOfModel(entities);
			salida.setAaData(aaData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return salida;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public DatosSalidaPaginador<Producto> paginar(
			DatosEntradaPaginador<Producto> params, DitProducto entity)
			throws Exception {
		
		DatosSalidaPaginador<Producto> response = new DatosSalidaPaginador<Producto>();
		List<Producto> result = null;		
		
		Criteria criteria = this.getSession().createCriteria(DitProducto.class);
		criteria.add(Restrictions.eq("ditPatronSujetoObligado.cveIdPatronSujetoObligado", entity.getDitPatronSujetoObligado().getCveIdPatronSujetoObligado()));
		criteria.add(Restrictions.isNull("fecRegistroBaja")); 
		/**
		 * Total records, before filtering (i.e. the total number of records in
		 * the database)
		 */
		int iTotalRecords = 0;
		/*Se debe de obtener el numero total de registros en la base de datos*/
		criteria.setProjection(Projections.rowCount());
		
		iTotalRecords = ((Long) criteria.setProjection(Projections.rowCount())
				.list().get(0)).intValue();
		
		criteria.setProjection(null);
		

		
		criteria.setResultTransformer(Criteria.ROOT_ENTITY);
		
		 
		List entities = criteria
				.setFirstResult(params.getiDisplayStart())
				.setMaxResults(params.getiDisplayLength()).list();
		
		

		try {
			result = this.productoServicioServiceUtility.convertListOfEntitiesToListOfModel(entities);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		response.setAaData(result);
		response.setiTotalDisplayRecords(0);
		response.setiTotalRecords(iTotalRecords);
		

		return response;
	}

	@Override
	public Producto get(Producto producto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Producto persistir(Producto model) {
		DitProducto	entity = null;
		entity = this.productoServicioServiceUtility.convertirModelToEntity(model);
		this.em.persist(entity);

		return model;
	}

	@Override
	public void borrar(Producto instance) throws Exception {
		DitProducto findById = this.findById(instance.getId(), DitProducto.class);
		this.em.remove(findById);
	}

	@Override
	public void validaLimMaxRegProducto(Producto producto) throws Exception {
		
		//Validamos que el limite máximo de registros no sea rebasado,
        // caso de excepcion AE02
		DitProducto entity = null;
		
		entity = this.productoServicioServiceUtility.convertirModelToEntity(producto);

        // verificar el limite maximo de registros (12 para este caso)
        int numRegistros = this.consultarNumRegistros(entity);

        log.debug("validaLimMaxRegProducto.numRegistros: " + numRegistros);

        if (numRegistros >= 12) {
            String numRegsExcedido = "Se ha execido del numero maximo de registros, registros en BD: " + numRegistros;
			log.debug(numRegsExcedido);
            throw new Exception(numRegsExcedido);
        }
		
	}

	@Override
	public void validaLimMinRegProducto(Producto producto) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void validaExisteProducto(Producto producto) throws Exception {
		System.out.println("**************Validando existencia producto: "+producto);
		DitProducto result = null;
		
		result = this.consultarProductoPorDescripcion( this.productoServicioServiceUtility.convertirModelToEntity(producto));
		
		if(result != null){
			String msg = "Si existen productos con la misma descripcion [" + result.getDesProducto() +"]";
			log.debug(msg);
			//Si existe un producto entonces no se cumple con la regla.
			throw new Exception(msg);
		}
		
	}

	@Override
	public void validaBorrarProducto(Producto producto) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Producto actualizar(Producto instance) throws Exception {
		DitProducto entity = null;
		
		// TODO yorch: cambiar esta implementacion por obtener primero el obj y luego actualizar
		entity = this.productoServicioServiceUtility.convertirModelToEntity(instance);
		this.em.merge(entity);
		
		return instance;
	}

	@Override
	public int consultarNumRegistrosPorActividadEconomica(
			Long cveActividadEconomica) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Producto> consultaPorClaveActividad(Producto producto)
			throws Exception {

		return null;
	}
	
	private int consultarNumRegistros(DitProducto entity) {

		Criteria criteria = this.getSession().createCriteria(
				DitProducto.class);
		
		criteria.add(Restrictions.eq(
				"ditPatronSujetoObligado.cveIdPatronSujetoObligado", entity
						.getDitPatronSujetoObligado().getCveIdPatronSujetoObligado()));
		return criteria.list().size();

	}
	
	@SuppressWarnings("unchecked")
	private DitProducto consultarProductoPorDescripcion(
			DitProducto entity) {
		
		this.log.debug("consultarProductoPorDescripcion [" + entity.getDesProducto() +" ]");
		System.out.println("consultarProductoPorDescripcion [" + entity.getDesProducto() +" ]");
		DitProducto entityResponse = null;
		
		
		Criteria criteria =  this.getSession().createCriteria(DitProducto.class);
		criteria.add(Restrictions.eq("ditPatronSujetoObligado.cveIdPatronSujetoObligado", entity.getDitPatronSujetoObligado().getCveIdPatronSujetoObligado()));
		criteria.add(  Restrictions.ilike( "desProducto", entity.getDesProducto().toLowerCase() , MatchMode.EXACT));
		
		
		if(entity.getCveIdProducto() > 0){
			//SI es diferente de nulo debemos de agregar el filtro por clave de producto
			criteria.add(  Restrictions.ne ( "cveIdProducto", entity.getCveIdProducto() ));
		}
		
		
		
		List <DitProducto> result = criteria.list();
		
		if( result != null && !result.isEmpty()){
			this.log.debug("Si existe mas de un producto con la descripcion");
			entityResponse = 	result.get(0);
		}
		return entityResponse;
	}
	
	private DitProducto findById(Long cveIdProducto,
			Class<DitProducto> class1) {
		
		DitProducto ditProducto = null;
		
		Query querySmtProductoServicio = em.createNamedQuery("DitProducto.findById");
		querySmtProductoServicio.setParameter("cveIdProducto", cveIdProducto);
		
		Object singleResult = querySmtProductoServicio.getSingleResult();
		
		if (singleResult != null){
			ditProducto = (DitProducto) singleResult;
		}
		return ditProducto;
	}

}
