package mx.gob.imss.cit.dacvass.servicios.externos.remote;

import mx.gob.imss.cit.dacvass.servicios.externos.model.TokenSISTRequestBean;
import mx.gob.imss.cit.dacvass.servicios.externos.model.TokenSISTResponseException;

public interface ITokenAccesoSISTRemote {
	
	String getTokenAccesoSIST(TokenSISTRequestBean token) throws TokenSISTResponseException, Exception;

}
