package mx.gob.imss.cit.dacvass.servicios.externos.service.util;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.ErrorResponseBean;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siscob.CptCreinc14ImssRcv;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siscob.HCopEstadoCuenta;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siscob.HCopEstadoCuentaPK;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siscob.HRcvEstadoCuenta;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siscob.HRcvEstadoCuentaPK;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siscob.HistPatronesConvenioImssrcv;

@Resource
public class ParserSiscobEntityToRest {

	private static final Logger log = LoggerFactory
			.getLogger(ParserSiscobEntityToRest.class);


	public static List<CptCreinc14ImssRcv> parserCptCreinc14ImssRcvEntityToRest(
			List<mx.gob.imss.cit.dacvass.servicios.externos.persistence.siscob.CptCreinc14ImssRcv> lstCptCreinc14ImssRcv) throws ServiciosRestException{

		ValidacionesComunesUtil.validaListaNulaVacia(lstCptCreinc14ImssRcv, "No se encontro información de los creditos CptCreinc14ImssRcv");
		log.debug("llegando al parser parserCptCreinc14ImssRcvEntityToRest y la lista no esta vacia ");
		List<CptCreinc14ImssRcv> cptCreinc14ImssRcvList = new ArrayList<CptCreinc14ImssRcv>();
		try {
			for (mx.gob.imss.cit.dacvass.servicios.externos.persistence.siscob.CptCreinc14ImssRcv credito : lstCptCreinc14ImssRcv) {
				cptCreinc14ImssRcvList.add(parserCptCreinc14ImssRcvEntityToRest(credito));
			}	
			return  cptCreinc14ImssRcvList;
		}catch (ServiciosRestException ex) {
			throw ex;
		}catch (Exception e ) {
			log.error("ocurio un erro al parsear el objeto CptCreinc14ImssRcv" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto CptCreinc14ImssRcv", e.getMessage()));
		}

	}

	public static CptCreinc14ImssRcv parserCptCreinc14ImssRcvEntityToRest(
			mx.gob.imss.cit.dacvass.servicios.externos.persistence.siscob.CptCreinc14ImssRcv cptCreinc14ImssRcvEntity) throws ServiciosRestException{
		CptCreinc14ImssRcv cptCreinc14ImssRcvRest = new CptCreinc14ImssRcv(); 
		try {
			BeanUtils.copyProperties(cptCreinc14ImssRcvEntity, cptCreinc14ImssRcvRest);
			return cptCreinc14ImssRcvRest;
		}catch (Exception e ) {
			log.error("ocurio un erro al parsear el objeto CptCreinc14ImssRcv" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto CptCreinc14ImssRcv", e.getMessage()));
		}
	}

	public static List<HistPatronesConvenioImssrcv> parserHistPatronesConvenioImssrcvEntityToRest(
			List<mx.gob.imss.cit.dacvass.servicios.externos.persistence.siscob.HistPatronesConvenioImssrcv> lstHistPatronesConvenioImssrcv) throws ServiciosRestException{

		ValidacionesComunesUtil.validaListaNulaVacia(lstHistPatronesConvenioImssrcv, "No se encontro información de los creditos lstHistPatronesConvenioImssrcv");
		List<HistPatronesConvenioImssrcv> histPatronesConvenioImssrcvList = new ArrayList<HistPatronesConvenioImssrcv>();
		try {
			for (mx.gob.imss.cit.dacvass.servicios.externos.persistence.siscob.HistPatronesConvenioImssrcv credito : lstHistPatronesConvenioImssrcv) {
				histPatronesConvenioImssrcvList.add(parserHistPatronesConvenioImssrcvEntityToRest(credito));
			}	
			return  histPatronesConvenioImssrcvList;
		}catch (ServiciosRestException ex) {
			throw ex;
		}catch (Exception e ) {
			log.error("ocurio un erro al parsear el objeto HistPatronesConvenioImssrcv" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto HistPatronesConvenioImssrcv", e.getMessage()));
		}

	}

	public static HistPatronesConvenioImssrcv parserHistPatronesConvenioImssrcvEntityToRest(
			mx.gob.imss.cit.dacvass.servicios.externos.persistence.siscob.HistPatronesConvenioImssrcv histPatronesConvenioImssrcvEntity) throws ServiciosRestException{
		HistPatronesConvenioImssrcv histPatronesConvenioImssrcvRest = new HistPatronesConvenioImssrcv(); 
		try {
			BeanUtils.copyProperties(histPatronesConvenioImssrcvEntity, histPatronesConvenioImssrcvRest);
			return histPatronesConvenioImssrcvRest;
		}catch (Exception e ) {
			log.error("ocurio un erro al parsear el objeto histPatronesConvenioImssrcvRest" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto histPatronesConvenioImssrcvRest", e.getMessage()));
		}
	}
	
	
	public static List<HCopEstadoCuenta> parserHCopEstadoCuentaEntityToRest(
			List<mx.gob.imss.cit.dacvass.servicios.externos.persistence.siscob.HCopEstadoCuenta> lstHCopEstadoCuenta) throws ServiciosRestException{

		ValidacionesComunesUtil.validaListaNulaVacia(lstHCopEstadoCuenta, "No se encontro información de los creditos lstHCopEstadoCuenta");
		List<HCopEstadoCuenta> hCopEstadoCuentaList = new ArrayList<HCopEstadoCuenta>();
		try {
			for (mx.gob.imss.cit.dacvass.servicios.externos.persistence.siscob.HCopEstadoCuenta credito : lstHCopEstadoCuenta) {
				hCopEstadoCuentaList.add(parserHCopEstadoCuentaEntityToRest(credito));
			}	
			return  hCopEstadoCuentaList;
		}catch (ServiciosRestException ex) {
			throw ex;
		}catch (Exception e ) {
			log.error("ocurio un erro al parsear el objeto HCopEstadoCuenta" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto HCopEstadoCuenta", e.getMessage()));
		}

	}

	public static HCopEstadoCuenta parserHCopEstadoCuentaEntityToRest(
			mx.gob.imss.cit.dacvass.servicios.externos.persistence.siscob.HCopEstadoCuenta hCopEstadoCuentaEntity) throws ServiciosRestException{
		HCopEstadoCuenta hCopEstadoCuentaRest = new HCopEstadoCuenta();
		HCopEstadoCuentaPK pkRest = new HCopEstadoCuentaPK(); 
		try {
			
			BeanUtils.copyProperties(hCopEstadoCuentaEntity.getId(), pkRest);
			BeanUtils.copyProperties(hCopEstadoCuentaEntity,hCopEstadoCuentaRest, new String[]{"id"});
			hCopEstadoCuentaRest.setId(pkRest);
			return hCopEstadoCuentaRest;
		}catch (Exception e ) {
			log.error("ocurio un erro al parsear el objeto hCopEstadoCuentaRest" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto hCopEstadoCuentaRest", e.getMessage()));
		}
	}
	
	
	public static List<HRcvEstadoCuenta> parserHRcvEstadoCuentaEntityToRest(
			List<mx.gob.imss.cit.dacvass.servicios.externos.persistence.siscob.HRcvEstadoCuenta> lstHRcvEstadoCuenta) throws ServiciosRestException{

		ValidacionesComunesUtil.validaListaNulaVacia(lstHRcvEstadoCuenta, "No se encontro información de los creditos lstHRcvEstadoCuenta");
		List<HRcvEstadoCuenta> hRcvEstadoCuentaList = new ArrayList<HRcvEstadoCuenta>();
		try {
			for (mx.gob.imss.cit.dacvass.servicios.externos.persistence.siscob.HRcvEstadoCuenta credito : lstHRcvEstadoCuenta) {
				hRcvEstadoCuentaList.add(parserHRcvEstadoCuentaEntityToRest(credito));
			}	
			return  hRcvEstadoCuentaList;
		}catch (ServiciosRestException ex) {
			throw ex;
		}catch (Exception e ) {
			log.error("ocurio un erro al parsear el objeto HRcvEstadoCuenta" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error al parsear el objeto HRcvEstadoCuenta", e.getMessage()));
		}

	}

	public static HRcvEstadoCuenta parserHRcvEstadoCuentaEntityToRest(
			mx.gob.imss.cit.dacvass.servicios.externos.persistence.siscob.HRcvEstadoCuenta hRcvEstadoCuentaEntity) throws ServiciosRestException{
		HRcvEstadoCuenta hRcvEstadoCuentaRest = new HRcvEstadoCuenta();
		HRcvEstadoCuentaPK pkRest = new HRcvEstadoCuentaPK();
		try {
			BeanUtils.copyProperties(hRcvEstadoCuentaEntity.getId(), pkRest);
			BeanUtils.copyProperties(hRcvEstadoCuentaEntity, hRcvEstadoCuentaRest, new String[]{"id"});
			hRcvEstadoCuentaRest.setId(pkRest);
			return hRcvEstadoCuentaRest;
		}catch (Exception e ) {
			log.error("ocurio un erro al parsear el objeto hRcvEstadoCuentaRest" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error al parsear el objeto hRcvEstadoCuentaRest", e.getMessage()));
		}
	}


}
