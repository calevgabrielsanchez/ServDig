package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.entity;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.exception.IvroException;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ConsultaTablaCorreosLocal;
import mx.gob.imss.ctirss.delta.persistence.DitBitCorreosSivro;

@Stateless(name = "consultaTablaCorreosEntity", mappedName = "consultaTablaCorreosEntity")
public class ConsultaTablaCorreosEntity implements ConsultaTablaCorreosLocal {

	
    /**
     * Loggerde la clase
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsultaSeguroIvroEntity.class);

    /**
     * unidad de persistencia
     */
    @PersistenceContext(unitName = "deltaPersistenceUnit")
    private EntityManager entityManager;
	
	@Override
	public List<DitBitCorreosSivro> getSeguroIVROEstatusEnvio() throws IvroException  {
		TypedQuery<DitBitCorreosSivro> query;
		try{		
			 	StringBuilder q = new StringBuilder();
	
		        q.append("Select bitCorreosIvro ")
		        .append("From DitBitCorreosSivro bitCorreosIvro ")
		        .append("WHERE bitCorreosIvro.indEstatus = 1 ")
				.append("ORDER BY bitCorreosIvro.cveIdBitCorreosSivro asc ");
	
		        query = entityManager.createQuery(q.toString(), DitBitCorreosSivro.class);
		}catch(Exception e){
			LOGGER.error("Ocurrio un error al acceder a la tabla DIT_BIT_CORREOS_SIVRO",e);
			throw new IvroException("Ocurrio un error al acceder a la tabla DIT_BIT_CORREOS_SIVRO: "+e.getMessage());
		}	

		return query.getResultList();
	}

	@Override
	public void actualizaSeguroIVROPorCveSeguro(Long cveIdBitCorreosSivro, Integer estatus, String descError) throws IvroException {
		try{
		DitBitCorreosSivro ditBitSeguroIvro = entityManager.find(DitBitCorreosSivro.class, cveIdBitCorreosSivro);
		ditBitSeguroIvro.setIndEstatus(estatus);
		ditBitSeguroIvro.setDesError(descError);
        entityManager.merge(ditBitSeguroIvro);
		}catch(Exception e){
			LOGGER.error("Ocurrio un error al intentar actualizar a la tabla DIT_BIT_CORREOS_SIVRO",e);
			throw new IvroException("Ocurrio un error al actualizar a la tabla DIT_BIT_CORREOS_SIVRO: "+e.getMessage());
		}
	}

}
