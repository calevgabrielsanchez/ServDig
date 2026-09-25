/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: ClasificacionServiceBusiness.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.clasificacion
 *  @Fecha: 04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.clasificacion;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.bitacora.BitacoraServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.clasificacion.ClasificacionEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.clasificacion.FraccionEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clasificacion.ClasificacionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisModel;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;

@Stateless(name = "clasificacionServiceBusiness", mappedName = "clasificacionServiceBusiness")
public class ClasificacionServiceBusiness extends AbstractServiceBusiness implements ClasificacionServiceBusinessRemote{
	
	@EJB
	private ClasificacionEntityLocal clasificacionEntity;
	
	@EJB
	private BitacoraServiceEntityLocal bitacoraServiceEntity;
	
	@EJB
	private FraccionEntityLocal fraccionEntity;

	@Override
	public Clasificacion obtenerDetalleFraccion(Clasificacion clasificacion)throws Exception{
		Clasificacion retVal = null;
		log.info("INICIO PARA CONSULTAR LA FRACCION X CLAVE");
		try {
			retVal = this.clasificacionEntity.consultaPorClave(clasificacion);
		} catch (Exception e) {
			log.info("Err: consulta clasificador");
			e.printStackTrace();
		}
		log.info("FIN PARA CONSULTAR LA FRACCION X CLAVE");
		return retVal;
	}

	@Override
	public EstatusAnalisisModel buscaClasificacionInicial(Long cveIdAnalisis) throws Exception{
		EstatusAnalisisModel model = new EstatusAnalisisModel();
		model = bitacoraServiceEntity.buscaClasificacionInicial(cveIdAnalisis);
		return model;
	}
	
	@Override
	public void actualizaFraccion(Clasificacion clasificacion, Fraccion fraccion) throws Exception{
		clasificacionEntity.actualizaFraccion(clasificacion, fraccion);
	}

	@Override
	public void actualizaFraccionyPrima(Clasificacion clasificacion, Fraccion fraccion) throws Exception{
		clasificacionEntity.actualizaFraccionyPrima(clasificacion, fraccion);
	}

	@Override
	public void elimina(long idClasificacion) throws Exception{
		clasificacionEntity.elimina(idClasificacion);
	}
	
	@Override
	public Clasificacion obtenerClasificacionEquivalente(Clasificacion clasificacion){
		try {
			Fraccion fraccionEquivalente = fraccionEntity.consultarFraccionEquivalente(clasificacion.getFraccion());
			clasificacion.setFraccion(fraccionEquivalente);
			log.info("hla::: pas\u00F3");
		} catch (PersistenceException e) {
			log.info("hla::: " + e.getMessage());
			e.printStackTrace();
		}catch (Exception e) {
			log.info("hla::: " + e.getMessage());
			e.printStackTrace();
		}
		return clasificacion;
	}
	
	@Override
	public Clasificacion obtenerDetalleFraccionPorId(Clasificacion clasificacion)throws Exception{
		Clasificacion retVal = null;
		log.info("INICIO PARA CONSULTAR LA FRACCION X ID");
		try {
			retVal = this.clasificacionEntity.consultaPorId(clasificacion);
		} catch (Exception e) {
			log.info("Err: consulta clasificador");
			e.printStackTrace();
		}
		log.info("FIN PARA CONSULTAR LA FRACCION X CLAVE");
		return retVal;
	}

	@Override
	public Clasificacion consultarFracEqPorNumero(Clasificacion clasificacion) {
		try {
			Fraccion fraccionEquivalente = fraccionEntity.consultarFracEqPorNumero(clasificacion.getFraccion());
			clasificacion.setFraccion(fraccionEquivalente);
			log.info("hla::: pas\u00F3");
		} catch (PersistenceException e) {
			log.info("hla::: " + e.getMessage());
			e.printStackTrace();
		}catch (Exception e) {
			log.info("hla::: " + e.getMessage());
			e.printStackTrace();
		}
		return clasificacion;
	}

	@Override
	public void actualizaSolicitudyTramite(String cveIdSolicitud, Long cveIdEstadoSol, Long cveIdEstadoTram) throws Exception{
		clasificacionEntity.actualizaSolicitudyTramite(cveIdSolicitud, cveIdEstadoSol, cveIdEstadoTram);
	}

	
}
