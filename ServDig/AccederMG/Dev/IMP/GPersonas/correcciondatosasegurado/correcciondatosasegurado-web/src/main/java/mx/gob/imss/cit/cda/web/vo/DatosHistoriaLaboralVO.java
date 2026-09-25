package mx.gob.imss.cit.cda.web.vo;

import java.io.Serializable;
import java.util.List;

public class DatosHistoriaLaboralVO implements Serializable {

	private static final long serialVersionUID = -170595729706091648L;

	private HistoriaLaboralVO historiaLaboralForm;
	private List<HistoriaLaboralVO> historiaLaboralGrid;
	private NSSVO nssvo;
	private List<NSSVO> NSSList;
	private DocumentoProbatorio documentoProbatorio;
	private List<DocumentoProbatorio> documentoProbatorioList;
	private List<String> tipoDocumentoProbatorio;

	public List<String> getTipoDocumentoProbatorio() {
		return tipoDocumentoProbatorio;
	}

	public void setTipoDocumentoProbatorio(List<String> tipoDocumentoProbatorio) {
		this.tipoDocumentoProbatorio = tipoDocumentoProbatorio;
	}

	public HistoriaLaboralVO getHistoriaLaboralForm() {
		return historiaLaboralForm;
	}

	public void setHistoriaLaboralForm(HistoriaLaboralVO historiaLaboralForm) {
		this.historiaLaboralForm = historiaLaboralForm;
	}

	public List<HistoriaLaboralVO> getHistoriaLaboralGrid() {
		return historiaLaboralGrid;
	}

	public void setHistoriaLaboralGrid(
			List<HistoriaLaboralVO> historiaLaboralGrid) {
		this.historiaLaboralGrid = historiaLaboralGrid;
	}

	public NSSVO getNssvo() {
		return nssvo;
	}

	public void setNssvo(NSSVO nssvo) {
		this.nssvo = nssvo;
	}

	public DocumentoProbatorio getDocumentoProbatorio() {
		return documentoProbatorio;
	}

	public void setDocumentoProbatorio(DocumentoProbatorio documentoProbatorio) {
		this.documentoProbatorio = documentoProbatorio;
	}

	public List<NSSVO> getNSSList() {
		return NSSList;
	}

	public void setNSSList(List<NSSVO> nSSList) {
		NSSList = nSSList;
	}

	public List<DocumentoProbatorio> getDocumentoProbatorioList() {
		return documentoProbatorioList;
	}

	public void setDocumentoProbatorioList(
			List<DocumentoProbatorio> documentoProbatorioList) {
		this.documentoProbatorioList = documentoProbatorioList;
	}
	
	@Override
	public String toString() {
		return "DatosHistoriaLaboralVO [historiaLaboralForm="
				+ historiaLaboralForm + ", historiaLaboralGrid="
				+ historiaLaboralGrid + ", nssvo=" + nssvo + ", NSSList="
				+ NSSList + ", documentoProbatorio=" + documentoProbatorio
				+ ", documentoProbatorioList=" + documentoProbatorioList + "]";
	}

	
}
