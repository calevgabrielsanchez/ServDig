package mx.gob.imss.ctirss.delta.derechohabientes.service.entity;

import java.util.List;

import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.BloqueoDerechosArcoParser;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.GenericDerechohabientesException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.derechohabiente.BloqueoDerechosArco;
import mx.gob.imss.ctirss.delta.persistence.DitBloqueoDerechosArco;

import javax.ejb.Stateless;
import javax.persistence.Query;

@Stateless(name = "derechosArcoEntity" , mappedName = "derechosArcoEntity")
public class DerechosArcoEntity extends AbstractServiceEntity implements DerechosArcoEntityLocal {

    @Override
    public void bloquear(BloqueoDerechosArco bloqueoDerechosArco) throws GenericDerechohabientesException {

        try {
            DitBloqueoDerechosArco ditBloqueoDerechosArco = BloqueoDerechosArcoParser.modelToPersist(bloqueoDerechosArco);
            em.persist(ditBloqueoDerechosArco);
            em.flush();
        } catch (GenericDerechohabientesException e) {
            throw e;
        } catch (Exception e) {
            log.error("Ocurrio un error al guardar los datos de bloqueo: " + e);
            throw new GenericDerechohabientesException(e);
        }


    }

    @Override
    public void actualizar(BloqueoDerechosArco bloqueoDerechosArco) throws GenericDerechohabientesException {
    		
    	try {
            DitBloqueoDerechosArco ditBloqueoDerechosArco = BloqueoDerechosArcoParser.modelToPersist(bloqueoDerechosArco);
            em.merge(ditBloqueoDerechosArco);
            em.flush();
        } catch (GenericDerechohabientesException e) {
            throw e;
        } catch (Exception e) {
            log.error("Ocurrio un error al guardar los datos de bloqueo: " + e);
            throw new GenericDerechohabientesException(e);
        }
    }

    @SuppressWarnings("unchecked")
	@Override
    public BloqueoDerechosArco consultarAseguradoBloqueado(Long cveIdAsignacion, Long tipoTramite) throws GenericDerechohabientesException {
    	BloqueoDerechosArco bloqueoDerechosArco;
    	
    	try {

    	    log.info("Consultando derechos arco idAsignacion: " + cveIdAsignacion);
            String sSql = "SELECT b FROM DitBloqueoDerechosArco b  WHERE b.cveIdAsignacionNss = :cveIdAsignacion "
                    + " and b.cveIdTipoTramite = :idTipoTramite";

            Query query = this.em.createQuery(sSql);
			query.setParameter("cveIdAsignacion", cveIdAsignacion);
			query.setParameter("idTipoTramite", tipoTramite);

			List<DitBloqueoDerechosArco> ditARCO = (List<DitBloqueoDerechosArco>)query.getResultList();
			if(ditARCO != null && !ditARCO.isEmpty()){
				bloqueoDerechosArco = BloqueoDerechosArcoParser.persistToModel(ditARCO.get(0));
				return bloqueoDerechosArco;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
    }
}
