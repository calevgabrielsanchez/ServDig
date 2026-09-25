package mx.gob.imss.ctirss.correccion.catalogos.service.ejb.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcTrabajadores;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrtEjertrabajador;
import mx.gob.imss.ctirss.correccion.catalogos.service.ejb.TrabajadoresServiceRemote;
import mx.gob.imss.ctirss.correccion.catalogos.service.ejb.dao.TrabajadoresDAOLocal;
import mx.gob.imss.ctirss.correccion.correccion.service.interfaces.SolicitudService;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.base.service.AbstractService;
import mx.gob.imss.ctirss.correccion.model.CrtAnexosolcorrpat;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.prorroga.service.ejb.dao.ProrrogaDAOBean;
import mx.gob.imss.ctirss.correccion.solicitud.service.ejb.SolicitudServiceRemote;

@Stateless(name="trabajadoresService", mappedName = "trabajadoresService")
public class TrabajadoresServiceBean<T extends AbstractModel> extends AbstractService implements TrabajadoresServiceRemote<T>{
	
	/**
	 * Logger
	 */
	private final static Logger logger = Logger.getLogger(TrabajadoresServiceBean.class);
	
	@EJB TrabajadoresDAOLocal<T> dao;
	@EJB SolicitudServiceRemote solicitudServiceBean;
	
	public T agregar(T model) {
		dao.agrega(setFieldsBeforeInsert(model));
		return model;
	}
	
	public T eliminar(T model){
		CrtEjertrabajador ejertrabajador = new CrtEjertrabajador();
		ejertrabajador.setCveTrabajador(new BigDecimal(((CrcTrabajadores)model).getCveTrabajador().longValue()));
		List<CrtEjertrabajador> lstHijos = (List<CrtEjertrabajador>) dao.obtenerHijos((T) ejertrabajador);
		
		if(lstHijos==null)
		dao.elimina(model);
		
		else{
			logger.debug("Tamanio de Lista para los hijos: " + lstHijos.size());
			dao.eliminaHijos((List<T>) lstHijos);
		    dao.elimina(model);
		    }
		
		return model;
	}
	
	public T modificar(T model) {
		dao.modifica(setFieldsBeforeUpdate(model));
		return model;
	}
	
	public List<T> consultar(T filtro) {
		return dao.consulta(filtro);
	}
	
	public T consultaPorClave(T filtro) {
		return dao.consultaPorClave(filtro);
	}

	public DatosSalidaPaginador<T> pagina(DatosEntradaPaginador<T> params){
		
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();		
		List<T> result = null;
		int iTotalRecords = 0;
		int iTotalDisplayRecords = 0; 
		CrtSolicitudcorr patrones = null;
		List<T> lsTrabajadoresPatron = null;
		
		if(((CrcTrabajadores)params.getModelo()).getPeriodo()==null){
			CrcTrabajadores crcTrabajadores = (CrcTrabajadores)params.getModelo();
			result = dao.paginadorTrabajadoresSinPeriodo((T) crcTrabajadores);
		}
		else{
			
			CrcTrabajadores auxTrabajadores = (CrcTrabajadores)params.getModelo();
			patrones = new CrtSolicitudcorr();
			patrones.setNuFolio(auxTrabajadores.getFolioCorreccion());
			patrones.setPeriodo(auxTrabajadores.getPeriodo());
			
			patrones = solicitudServiceBean.consultarPatrones(patrones);
			
			
			if(patrones!=null && patrones.getLstAnexoSolicitudesCorr()!=null && !patrones.getLstAnexoSolicitudesCorr().isEmpty()){
				 result = new ArrayList<T>();
				Iterator<CrtAnexosolcorrpat> iter =  (Iterator<CrtAnexosolcorrpat>) patrones.getLstAnexoSolicitudesCorr().iterator();
				CrtAnexosolcorrpat currentItem = null;
				
				while(iter.hasNext()){
					currentItem = iter.next();
					
					lsTrabajadoresPatron =  dao.paginadorTrabajadoresPeriodo((T) auxTrabajadores,currentItem.getCrcEjercicio().getCveAcexoCorrPat(), currentItem.getCrcEjercicio().getCveEjercicio());
					
					if(lsTrabajadoresPatron!=null&&!lsTrabajadoresPatron.isEmpty()){
						Iterator<CrcTrabajadores> iterTrabajadores =  (Iterator<CrcTrabajadores>) lsTrabajadoresPatron.iterator();
						CrcTrabajadores currentItemTrabajador = null;
						
						while(iterTrabajadores.hasNext()){
							currentItemTrabajador = iterTrabajadores.next();
							currentItemTrabajador.setPeriodo(auxTrabajadores.getPeriodo());
							currentItemTrabajador.setRegistroPatronal(currentItem.getRegistroPatronal());
							result.add((T) currentItemTrabajador);
						}
					}
				}
			}
			
			
		}
	
		
		if(result!= null){
			
			/*Se debe de obtener el numero total de registros en la base de datos*/
			iTotalRecords = result.size();
			

			/**
			 * Total records, after filtering (i.e. the total number of records
			 * after filtering has been applied - not just the number of records
			 * being returned in this result set)
			 */
			
			iTotalDisplayRecords = result.size();
		}else{
			result = new ArrayList<T>();
		}
 
			response.setAaData(result);
			response.setiTotalDisplayRecords(iTotalDisplayRecords);
			response.setiTotalRecords(iTotalRecords);
				
		
		//return dao.pagina(params);
			return response;
	}

	
	public List<T> consultarTrabajadores(T filtro) {
		// TODO Auto-generated method stub
		return dao.consultarTrabajadores(filtro);
	}

	@Override
	public T consultaPorClaveDatos(T model) {
		// TODO Auto-generated method stub
		return dao.consultaPorClaveDatos(model);
	}

	@Override
	public List<CrcTrabajadores> consultarTrabajadoresSinPeriodo(CrcTrabajadores trabajadores) {
		// TODO Auto-generated method stub
		return  (List<CrcTrabajadores>) dao.paginadorTrabajadoresSinPeriodo((T) trabajadores);
	}
}
