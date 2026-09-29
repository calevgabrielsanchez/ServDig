package mx.gob.imss.cit.clienteswebservices.modalidad40.rest;

import mx.gob.imss.cit.clienteswebservices.modalidad40.rest.bean.CalculoPagosRequest;
import mx.gob.imss.cit.clienteswebservices.modalidad40.rest.bean.CalculoPagosResponse;
import mx.gob.imss.cit.clienteswebservices.modalidad40.rest.bean.GeneracionMultilineaConsultaResponse;
import mx.gob.imss.cit.clienteswebservices.modalidad40.rest.bean.GeneracionMultilineaRequest;
import mx.gob.imss.cit.clienteswebservices.modalidad40.rest.bean.GeneracionMultilineaResponse;
import mx.gob.imss.cit.clienteswebservices.modalidad40.rest.bean.ModalidadResponseException;
import mx.gob.imss.cit.clienteswebservices.modalidad40.rest.bean.ValidaRetroactividadRequest;
import mx.gob.imss.cit.clienteswebservices.modalidad40.rest.bean.ValidaRetroactividadResponse;

public interface Modalidad40RestLocal {
	
	ValidaRetroactividadResponse validaRetroactividad(ValidaRetroactividadRequest consulta) throws ModalidadResponseException;
	
	CalculoPagosResponse calculoPagos(CalculoPagosRequest cpr) throws ModalidadResponseException;
	
	GeneracionMultilineaResponse generacionMultilinea(GeneracionMultilineaRequest consulta) throws ModalidadResponseException;
	
	GeneracionMultilineaConsultaResponse generacionMultilineaConsulta(String nss) throws ModalidadResponseException;
}
