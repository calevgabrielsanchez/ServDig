package mx.gob.imss.cit.clienteServiciosComunes.services.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.BeanUtils;

import mx.gob.imss.cit.clienteServiciosComunes.consultaPatron.model.InfoPatronEntradaxRFC;
import mx.gob.imss.cit.clienteServiciosComunes.consultaPatron.model.InfoPatronSalidaxRFC;
import mx.gob.imss.cit.clienteServiciosComunes.consultaPatron.model.InfoPatronVOxRFC;
import mx.gob.imss.cit.clienteServiciosComunes.consultaPatron.ws.IConsultaPatronWSService;
import mx.gob.imss.cit.clienteServiciosComunes.model.InformacionPatron;
import mx.gob.imss.cit.clienteServiciosComunes.model.RespuestaConsultaPatron;
import mx.gob.imss.cit.clienteServiciosComunes.services.ConsultaPatronService;

public class ConsultaPatronServiceImpl implements ConsultaPatronService {

	IConsultaPatronWSService iConsultaPatronWSService;

	public RespuestaConsultaPatron obtenerInformacionPatronPorRFC(String rfc) {
		RespuestaConsultaPatron respuestaConsultaPatron = new RespuestaConsultaPatron();
		List<InformacionPatron> lstInformacionPatron = new ArrayList<InformacionPatron>();
		InfoPatronEntradaxRFC infoPatronEntradaxRFC = new InfoPatronEntradaxRFC();
		infoPatronEntradaxRFC.setRfc(rfc);
		InfoPatronSalidaxRFC infoPatronSalidaxRFC = iConsultaPatronWSService
				.getInformacionPatronxRFC(infoPatronEntradaxRFC);
		BeanUtils.copyProperties(infoPatronSalidaxRFC, respuestaConsultaPatron);
		InformacionPatron informacionPatron = null;
		for (InfoPatronVOxRFC infoPatronVOxRFC: infoPatronSalidaxRFC.getInfoPatronVOxRFC()) {
			informacionPatron = new InformacionPatron(); 
			BeanUtils.copyProperties(infoPatronVOxRFC, informacionPatron);
			lstInformacionPatron.add(informacionPatron);
		}
		respuestaConsultaPatron.setLstInformacionPatron(lstInformacionPatron);
		return respuestaConsultaPatron;
	}

	public IConsultaPatronWSService getiConsultaPatronWSService() {
		return iConsultaPatronWSService;
	}

	public void setiConsultaPatronWSService(IConsultaPatronWSService iConsultaPatronWSService) {
		this.iConsultaPatronWSService = iConsultaPatronWSService;
	}

}
