package mx.gob.imss.cit.dacvass.servicios.externos.remote;

import mx.gob.imss.cit.dacvass.servicios.externos.model.TokenConveniosRequestBean;
import mx.gob.imss.cit.dacvass.servicios.externos.model.TokenConveniosResponseException;

public interface ITokenAccesoConveniosRemote {
	
	String getTokenAccesoConvenios(TokenConveniosRequestBean token) throws TokenConveniosResponseException, Exception;

}
