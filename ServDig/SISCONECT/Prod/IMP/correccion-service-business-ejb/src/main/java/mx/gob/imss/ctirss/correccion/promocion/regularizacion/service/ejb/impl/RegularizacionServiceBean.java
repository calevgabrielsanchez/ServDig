package mx.gob.imss.ctirss.correccion.promocion.regularizacion.service.ejb.impl;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.deteccion.model.CrtDeteccion;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.base.repository.AbstractRespository;
import mx.gob.imss.ctirss.correccion.framework.base.service.AbstractService;
import mx.gob.imss.ctirss.correccion.model.CrtSelector;
import mx.gob.imss.ctirss.correccion.promocion.regularizacion.model.CrtRegulapagos;
import mx.gob.imss.ctirss.correccion.promocion.regularizacion.model.CrtRegulapagosdet;
import mx.gob.imss.ctirss.correccion.promocion.regularizacion.service.ejb.RegularizacionServiceRemote;
import mx.gob.imss.ctirss.correccion.promocion.regularizacion.service.ejb.dao.RegularizacionDAOLocal;
import mx.gob.imss.ctirss.correccion.utils.Functions;

@Stateless(name="regularizacionService", mappedName = "regularizacionService")
public class RegularizacionServiceBean<T extends AbstractModel> extends AbstractService implements RegularizacionServiceRemote<T>{
	
	@EJB RegularizacionDAOLocal<T> daoRegularizacion; 
	
	public T agregar(T model) {
		daoRegularizacion.agrega(setFieldsBeforeInsert(model));
		return model;
	}
	
	public void eliminar(T model){
		daoRegularizacion.elimina(model);
	}
		
	public List<T> consultar(T filtro) {
		return daoRegularizacion.consulta(filtro);
	}
	
	public List<T> consultaPagosDetPorCvePago(T filtro){
		return daoRegularizacion.consultaPagosDetPorCvePago(filtro);
	}
	
	public T consultaPorClave(T filtro) {
		return daoRegularizacion.consultaPorClave(filtro);
	}
	
	public T consultaPorClavePago(T filtro) {
		return daoRegularizacion.consultaPorClavePago(filtro);
	}
	
	public T consultaPorClavePromocion(T filtro) {
		return daoRegularizacion.consultaPorClavePromocion(filtro);
	}
	
	public DatosSalidaPaginador<T> paginaPagos(DatosEntradaPaginador<T> filtro) {
		DatosSalidaPaginador<T> salidaPaginador = daoRegularizacion.paginaPagos(filtro);
		BigDecimal totalCop=new BigDecimal(0);
		BigDecimal totalRcv=new BigDecimal(0);
		BigDecimal cero=new BigDecimal(0);
		int contador = 1;
		
		if(salidaPaginador.getAaData() != null && !salidaPaginador.getAaData().isEmpty()){
			for (Iterator iterator = salidaPaginador.getAaData().iterator(); iterator.hasNext();) {
				CrtRegulapagosdet pagoDet = (CrtRegulapagosdet) iterator.next();
				totalCop = cero;
				totalRcv = cero;
				pagoDet.setImpCopsp(pagoDet.getImpCopsp()!=null?pagoDet.getImpCopsp():cero);
				pagoDet.setImpCopact(pagoDet.getImpCopact()!=null?pagoDet.getImpCopact():cero);
				pagoDet.setImpCoprec(pagoDet.getImpCoprec()!=null?pagoDet.getImpCoprec():cero);
				pagoDet.setImpRcvsp(pagoDet.getImpRcvsp()!=null?pagoDet.getImpRcvsp():cero);
				pagoDet.setImpRcvact(pagoDet.getImpRcvact()!=null?pagoDet.getImpRcvact():cero);
				pagoDet.setImpRcvrec(pagoDet.getImpRcvrec()!=null?pagoDet.getImpRcvrec():cero);
				
				pagoDet.setImpMultasCop(pagoDet.getImpMultasCop()!=null?pagoDet.getImpMultasCop():cero);
				pagoDet.setImpMultasRcv(pagoDet.getImpMultasRcv()!=null?pagoDet.getImpMultasRcv():cero);
				pagoDet.setNumCredito(pagoDet.getNumCredito()!=null?pagoDet.getNumCredito():"");
				pagoDet.setNumAltas(pagoDet.getNumAltas()!=null?pagoDet.getNumAltas():0);
				pagoDet.setNumBajas(pagoDet.getNumBajas()!=null?pagoDet.getNumBajas():0);
				pagoDet.setNumTrabajadoresRegulariza(pagoDet.getNumTrabajadoresRegulariza()!=null?pagoDet.getNumTrabajadoresRegulariza():0);
				pagoDet.setNumModifSalario(pagoDet.getNumModifSalario()!=null?pagoDet.getNumModifSalario():0);
				
				pagoDet.setContador(contador);
				//suma los COP
				totalCop = totalCop.add(pagoDet.getImpCopsp()!=null?pagoDet.getImpCopsp():cero);
				totalCop = totalCop.add(pagoDet.getImpCopact()!=null?pagoDet.getImpCopact():cero);
				totalCop = totalCop.add(pagoDet.getImpCoprec()!=null?pagoDet.getImpCoprec():cero);
				pagoDet.setImpTotalCop(totalCop);
				
				//Suma los RCV
				totalRcv = totalRcv.add(pagoDet.getImpRcvsp()!=null?pagoDet.getImpRcvsp():cero);
				totalRcv = totalRcv.add(pagoDet.getImpRcvact()!=null?pagoDet.getImpRcvact():cero);
				totalRcv = totalRcv.add(pagoDet.getImpRcvrec()!=null?pagoDet.getImpRcvrec():cero);
				pagoDet.setImpTotalRcv(totalRcv);
				
				pagoDet.setFechaPago(Functions.dateToString(pagoDet.getFecFechapago()));
				
				contador = contador +1;
				
			}
		
		}
			
		return salidaPaginador;
	}
	
	public T modificar(T model) {
		daoRegularizacion.modifica(setFieldsBeforeUpdate(model));
		return model;
	}

}
