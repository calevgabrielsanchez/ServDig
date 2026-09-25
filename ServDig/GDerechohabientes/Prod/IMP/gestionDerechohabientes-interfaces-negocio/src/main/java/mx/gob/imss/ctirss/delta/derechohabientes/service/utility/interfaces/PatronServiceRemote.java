package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.asegurado.Asegurado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;


@Remote
public interface PatronServiceRemote {
	
	
	public boolean getPatronIMSS(SujetoObligado unPatronSujetoObligado) throws DerechohabientesBusinessException, Exception;
	public String getRegistroPatronal(long idPatronSujetoObligado) throws Exception;
	public String getRegistroPatronalSinDV(long idPatronSujetoObligado) throws DerechohabientesBusinessException, Exception;
	public boolean getPensionado(long idAsegurado) throws DerechohabientesBusinessException, Exception;
	public List<Asegurado> getAseguradoList(long idAsignacionNss) throws DerechohabientesBusinessException, Exception;
	public boolean circunscripcionAsegurado(String codigoPostalAseg,SujetoObligado unPatronSujetoObligado) throws DerechohabientesBusinessException, Exception;			
	public Asegurado getAsegurado( Long idAsignaccionNSS, Long cveIdPatronSujeroObligado ) throws DerechohabientesBusinessException, Exception;
//	public String calculaAgregadoMedico(AsignacionNSS an, RegistroDto registro) throws DerechohabientesBusinessException, Exception;
	
	/**
	 * Consulta que valida si un patron se encuentra en la tabla de patrones de instituciones educativas
	 * @param cveNRP
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	public boolean isRegistroPatronalnstitucionEducativa(String cveNRP);
}
