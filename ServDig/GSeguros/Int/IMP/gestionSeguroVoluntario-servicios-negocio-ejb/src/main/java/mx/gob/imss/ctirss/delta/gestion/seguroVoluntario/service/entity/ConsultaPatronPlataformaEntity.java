package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.entity;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.exception.IvroException;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ConsultaPatronPlataformaLocal;
import mx.gob.imss.ctirss.delta.persistence.PptPatronPlataforma;

@Stateless(name = "consultaPatronPlataformaEntity", mappedName = "consultaPatronPlataformaEntity")
public class ConsultaPatronPlataformaEntity implements ConsultaPatronPlataformaLocal {

    /**
     * Loggerde la clase
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsultaPatronPlataformaEntity.class);

    /**
     * unidad de persistencia
     */
    @PersistenceContext(unitName = "deltaPersistenceUnit")
    private EntityManager entityManager;
	
	
	/**
     * Obtiene la lista de Patrones de Plataformas digitales con fecha de baja = null
     * 
     * @return la lista de Patrones de Plataformas digitales con fecha de baja = null
     */
    @Override
    public List<PptPatronPlataforma> getPttPatronPlataformaActivos() throws IvroException  {
		TypedQuery<PptPatronPlataforma> query;
		try{		
			 	StringBuilder q = new StringBuilder();
	
		        q.append("Select pptPatronPlataforma ")
		        .append("From PptPatronPlataforma pptPatronPlataforma ")
		        .append("WHERE pptPatronPlataforma.fecBaja IS NULL ")
				.append("ORDER BY pptPatronPlataforma.cveRegPatron asc ");
	
		        query = entityManager.createQuery(q.toString(), PptPatronPlataforma.class);
		}catch(Exception e){
			LOGGER.error("Ocurrio un error al acceder a la tabla PPT_PATRON_PLATAFORMA",e);
			throw new IvroException("Ocurrio un error al acceder a la tabla PPT_PATRON_PLATAFORMA: "+e.getMessage());
		}	

		return query.getResultList();
	}

	

}
