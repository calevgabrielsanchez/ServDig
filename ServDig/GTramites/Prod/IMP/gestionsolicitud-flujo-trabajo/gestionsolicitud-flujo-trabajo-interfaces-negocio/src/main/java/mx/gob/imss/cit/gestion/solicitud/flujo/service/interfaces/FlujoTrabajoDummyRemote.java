package mx.gob.imss.cit.gestion.solicitud.flujo.service.interfaces;

import java.util.Map;

import javax.ejb.Remote;

@Remote
public interface FlujoTrabajoDummyRemote {
	
	String testMethod(String nombre);
	
}
