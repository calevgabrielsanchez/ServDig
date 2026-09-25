package mx.gob.imss.cit.clienteswebservices.boveda.rest;

import mx.gob.imss.cit.clienteswebservices.boveda.rest.bean.BovedaResponseException;
import mx.gob.imss.cit.clienteswebservices.boveda.rest.bean.ConsultaEstatusDocBovedaRequest;
import mx.gob.imss.cit.clienteswebservices.boveda.rest.bean.ConsultaEstatusDocBovedaResponse;

public interface IBovedaTspiRestLocal {
	
	ConsultaEstatusDocBovedaResponse consultaEstatusDoc(ConsultaEstatusDocBovedaRequest consulta) throws BovedaResponseException;
}
