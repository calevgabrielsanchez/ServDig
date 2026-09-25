package mx.gob.imss.cit.cda.service.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import mx.gob.imss.cit.cda.service.interfaces.DocumentoProbatorioRemote;

@Stateless(name = "documentoProbatorioBusiness", mappedName = "documentoProbatorioBusiness")
public class DocumentoProbatorioBusiness implements
		DocumentoProbatorioRemote {

	@Override
	public Map<String, String> getDocumentosVentanilla() {
		Map<String, String> documentosProbatorios = new HashMap<String, String>();
		documentosProbatorios.put("1", "Acta de nacimiento de asegurado. (obligatorio)");
		documentosProbatorios.put("2", "Identificaci\u00F3n oficial de asegurado. (obligatorio)");
		documentosProbatorios.put("3", "Documento expedido por el IMSS que contenga el NSS de asegurado.  (obligatorio)");
		documentosProbatorios.put("4", "Formato de solicitud de correcci\u00F3n de datos de asegurado. (obligatorio)");
		return documentosProbatorios;
	}

	@Override
	public Map<String, String> getDocumentosInternet() {
		Map<String, String> documentosProbatorios = new HashMap<String, String>();		
		documentosProbatorios.put("1", "Acta de nacimiento. (obligatorio)");
		documentosProbatorios.put("2", "Identificaci\u00F3n oficial. (obligatorio)");
		documentosProbatorios.put("3", "Documento expedido por el IMSS que contenga el NSS.  (obligatorio)");
		return documentosProbatorios;
	}

	@Override
	public List<String> getDocumentosObligatoriosVentanilla() {
		List<String> documentosObligatorios= new ArrayList<String>();
		documentosObligatorios.add("1");
		documentosObligatorios.add("2");
		documentosObligatorios.add("3");
		documentosObligatorios.add("4");
		return documentosObligatorios;
	}

	@Override
	public List<String> getDocumentosObligatoriosInternet() {
		List<String> documentosObligatorios= new ArrayList<String>();
		documentosObligatorios.add("1");
		documentosObligatorios.add("2");
		documentosObligatorios.add("3");
		return documentosObligatorios;
	}

	@Override
	public Map<String, String> getDocumentosVentanillaDefuncionDescendiente() {
		Map<String, String> documentosProbatorios = new HashMap<String, String>();
		documentosProbatorios.putAll(this.getDocumentosVentanilla());
		documentosProbatorios.put("7", "Acta de defunci\u00F3n del asegurado. (obligatorio)");
		documentosProbatorios.put("10", "Acta de nacimiento de hijo o hija. (obligatorio)");
		documentosProbatorios.put("11", "Identificaci\u00F3n oficial de hijo o hija.");
		return documentosProbatorios;
	}

	@Override
	public Map<String, String> getDocumentosVentanillaDefuncionPadres() {
		Map<String, String> documentosProbatorios = new HashMap<String, String>();
		documentosProbatorios.putAll(this.getDocumentosVentanilla());
		documentosProbatorios.put("7", "Acta de defunci\u00F3n del asegurado. (obligatorio)");
		documentosProbatorios.put("12", "Identificaci\u00F3n oficial de padre o la madre.");
		return documentosProbatorios;
	}

	@Override
	public Map<String, String> getDocumentosVentanillaDefuncionConcubino() {
		Map<String, String> documentosProbatorios = new HashMap<String, String>();
		documentosProbatorios.putAll(this.getDocumentosVentanilla());
		documentosProbatorios.put("7", "Acta de defunci\u00F3n del asegurado. (obligatorio)");
		documentosProbatorios.put("13", "Constancia testimonial de concubina/concubinario.");
		documentosProbatorios.put("14", "Identificaci\u00F3n oficial de concubina/concubinario.");
		return documentosProbatorios;
	}

	@Override
	public Map<String, String> getDocumentosVentanillaDefuncionConyugue() {
		Map<String, String> documentosProbatorios = new HashMap<String, String>();
		documentosProbatorios.putAll(this.getDocumentosVentanilla());
		documentosProbatorios.put("7", "Acta de defunci\u00F3n del asegurado. (obligatorio)");
		documentosProbatorios.put("8", "Acta de matrimonio.");
		documentosProbatorios.put("9", "Identificaci\u00F3n oficial del conyuge.");
		return documentosProbatorios;
	}

	@Override
	public Map<String, String> getDocumentosVentanillaRepresentateLegal() {
		Map<String, String> documentosProbatorios = new HashMap<String, String>();
		documentosProbatorios.putAll(this.getDocumentosVentanilla());
		documentosProbatorios.put("5", "Poder notarial que acredite al Representante legal.");
		documentosProbatorios.put("6", "Identificaci\u00F3n oficial del Representante legal.");
		return documentosProbatorios;
	}

	@Override
	public List<String> getDocumentosObligatoriosDefuncionPadres() {
		List<String> documentosObligatorios = new ArrayList<String>();
		documentosObligatorios.addAll(this.getDocumentosObligatoriosVentanilla());
		documentosObligatorios.add("7");
		documentosObligatorios.add("12");
		return documentosObligatorios;
	}

	@Override
	public List<String> getDocumentosObligatoriosRepresentanteLegal() {
		List<String> documentosObligatorios = new ArrayList<String>();
		documentosObligatorios.addAll(this.getDocumentosObligatoriosVentanilla());
		documentosObligatorios.add("5");
		documentosObligatorios.add("6");
		return documentosObligatorios;
	}

	@Override
	public List<String> getDocumentosObligatoriosDefuncionConyuge() {
		List<String> documentosObligatorios = new ArrayList<String>();
		documentosObligatorios.addAll(this.getDocumentosObligatoriosVentanilla());
		documentosObligatorios.add("7");
		documentosObligatorios.add("8");
		documentosObligatorios.add("9");
		return documentosObligatorios;
	}

	@Override
	public List<String> getDocumentosObligatoriosDefuncionDescendiente() {
		List<String> documentosObligatorios = new ArrayList<String>();
		documentosObligatorios.addAll(this.getDocumentosObligatoriosVentanilla());
		documentosObligatorios.add("7");
		documentosObligatorios.add("10");
		documentosObligatorios.add("11");
		return documentosObligatorios;
	}

	@Override
	public List<String> getDocumentosObligatoriosDefuncionConcubino() {
		List<String> documentosObligatorios = new ArrayList<String>();
		documentosObligatorios.addAll(this.getDocumentosObligatoriosVentanilla());
		documentosObligatorios.add("7");
		documentosObligatorios.add("13");
		documentosObligatorios.add("14");
		return documentosObligatorios;
	}

}
