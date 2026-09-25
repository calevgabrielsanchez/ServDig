package mx.gob.imss.cit.cda.service.interfaces;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

/**
 * @author STK
 *
 */
@Remote
public interface DocumentoProbatorioRemote {
		
	Map<String,String> getDocumentosVentanilla();
	Map<String,String> getDocumentosInternet();
	Map<String,String> getDocumentosVentanillaDefuncionDescendiente();
	Map<String,String> getDocumentosVentanillaDefuncionPadres();
	Map<String,String> getDocumentosVentanillaDefuncionConcubino();
	Map<String,String> getDocumentosVentanillaDefuncionConyugue();
	Map<String,String> getDocumentosVentanillaRepresentateLegal();
	List<String> getDocumentosObligatoriosVentanilla();
	List<String> getDocumentosObligatoriosDefuncionPadres();
	List<String> getDocumentosObligatoriosRepresentanteLegal();
	List<String> getDocumentosObligatoriosDefuncionConyuge();
	List<String> getDocumentosObligatoriosDefuncionDescendiente();
	List<String> getDocumentosObligatoriosDefuncionConcubino();
	List<String> getDocumentosObligatoriosInternet();
}
