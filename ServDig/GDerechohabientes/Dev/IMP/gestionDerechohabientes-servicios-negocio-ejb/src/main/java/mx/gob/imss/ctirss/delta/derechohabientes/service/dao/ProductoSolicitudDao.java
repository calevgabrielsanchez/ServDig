package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import java.math.BigInteger;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@TransactionManagement(TransactionManagementType.CONTAINER)
@Stateless(name = "productoSolicitudDao", mappedName = "productoSolicitudDao")
public class ProductoSolicitudDao implements ProductoSolicitudDaoLocal{

	@PersistenceContext(unitName="deltaPersistenceUnit")
	private EntityManager em;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductoSolicitudDao.class);

	@Override
	public byte[] getWaterMarkProductoSolicitud() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminarProductoSolicitud(BigInteger id) {
		// TODO Auto-generated method stub
		
	}
	
	
    /*TODO REVISAR IMPLEMENTACION
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void altaProductoSolicitud(DitProductoSolicitud productoSolicitud){
		
		DitSolicitud ds = null;
		ds = em.find(DitSolicitud.class, productoSolicitud.getDitSolicitud().getCveIdSolicitud());
		productoSolicitud.setDitSolicitud(ds);
		em.merge(productoSolicitud);
	}
	*/
	
    /* TODO REVISAR IMPLEMENTACION
	@Override
	public void modificarProductoSolicitud(ProductoSolicitud producto) {
		// TODO Auto-generated method stub
		LOGGER.info("Adentro del metodo: modificarProductoSolicitud");
		DitProductoSolicitud dps = new DitProductoSolicitud();
		dps = em.find(DitProductoSolicitud.class, producto.getIdProductoSolicitud()); 
		
		dps.setNomArchivo(producto.getNomArchivo());
		dps.setRefDocumento(producto.getRefDocumento());
		dps.setTipContenido(producto.getTipContenido());
		dps.setCanLongitud(producto.getCanLongitud());
		
		LOGGER.info("producto: " + producto);
		
		em.merge(dps);
	}
*/
	
    /*TODO REVISAR IMPLEMENTACION	
	@Override
	public void eliminarProductoSolicitud(BigInteger id) {
		// TODO Auto-generated method stub
		LOGGER.info("Adentro del metodo: eliminarProductoSolicitud");
		DitProductoSolicitud dps = new DitProductoSolicitud();
		dps = em.find(DitProductoSolicitud.class, id);
		em.remove(dps);
	}
	*/
	
/*TODO REVISAR IMPLEMENTACION	
	@Override
	public List<DitProductoSolicitud> getProductosSolicitudTramite(BigInteger idTramite) {
	
		DitSolicitud solicitud;
		
		//solicitud=em.find(DitSolicitud.class, idTramite);
		solicitud=new DitSolicitud();
		solicitud.setCveIdSolicitud(idTramite.longValue());
		
		//crea criteria
		CriteriaBuilder cb = em.getCriteriaBuilder(); //Step 1 
		CriteriaQuery<Object> cqry= cb.createQuery();  //Step 1
		//se crea lo deseado
        Root<DitProductoSolicitud> root = cqry.from(DitProductoSolicitud.class); //Step 2 //se crea la raiz
        cqry.select(root); //Step 3 se agrega la raiz
        Predicate pGtAge = cb.equal(root.get("ditSolicitud"), solicitud); //Step 4 se crea el predicado
       
        cqry.where(pGtAge); //Step 5 se agrega el predicado

        
        //Se Obtiene el resultado
        Query qry = em.createQuery(cqry); //Step 6 se crea el query
        @SuppressWarnings("unchecked")
		List<DitProductoSolicitud> results = qry.getResultList(); //Step 6 se obtiene el resultado
        
        
		return results;
	}

*/

/*TODO REVISAR IMPLEMENTACION
	@Override
	public byte[] getWaterMarkProductoSolicitud() {
		//crea criteria
		DitProductoSolicitud ps=null;
		CriteriaBuilder cb = em.getCriteriaBuilder(); //Step 1 
		CriteriaQuery<Object> cqry= cb.createQuery();  //Step 1
		//se crea lo deseado
        Root<DitProductoSolicitud> root = cqry.from(DitProductoSolicitud.class); //Step 2 //se crea la raiz
        cqry.select(root); //Step 3 se agrega la raiz
        Predicate pGtAge = cb.equal(root.get("tipContenido"), "wm"); //Step 4 se crea el predicado
       
        cqry.where(pGtAge); //Step 5 se agrega el predicado

        
        //Se Obtiene el resultado
        Query qry = em.createQuery(cqry); //Step 6 se crea el query
     
		ps=(DitProductoSolicitud) qry.getSingleResult(); //Step 6 se obtiene el resultado
		return ps.getRefDocumento();
	}
	    */
}
 