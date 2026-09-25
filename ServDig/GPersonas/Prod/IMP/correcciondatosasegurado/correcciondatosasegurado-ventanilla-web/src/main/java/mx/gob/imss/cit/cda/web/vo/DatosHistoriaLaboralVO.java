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
	private boolean defuncion;
	private String tipoSolicitante;
	private String tipoBeneficiario;
	private String curp ;
	private List<String> tipoDocumentoProbatorio;

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

	public boolean isDefuncion() {
		return defuncion;
	}

	public void setDefuncion(boolean defuncion) {
		this.defuncion = defuncion;
	}

	public String getTipoSolicitante() {
		return tipoSolicitante;
	}

	public void setTipoSolicitante(String tipoSolicitante) {
		this.tipoSolicitante = tipoSolicitante;
	}

	public String getTipoBeneficiario() {
		return tipoBeneficiario;
	}

	public void setTipoBeneficiario(String tipoBeneficiario) {
		this.tipoBeneficiario = tipoBeneficiario;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}
	
	public List<String> getTipoDocumentoProbatorio() {
		return tipoDocumentoProbatorio;
	}

	public void setTipoDocumentoProbatorio(List<String> tipoDocumentoProbatorio) {
		this.tipoDocumentoProbatorio = tipoDocumentoProbatorio;
	}

	@Override
	public String toString() {
		return "DatosHistoriaLaboralVO [historiaLaboralForm="
				+ historiaLaboralForm + ", historiaLaboralGrid="
				+ historiaLaboralGrid + ", nssvo=" + nssvo + ", NSSList="
				+ NSSList + ", documentoProbatorio=" + documentoProbatorio
				+ ", documentoProbatorioList=" + documentoProbatorioList
				+ ", defuncion=" + defuncion + ", tipoSolicitante="
				+ tipoSolicitante + ", tipoBeneficiario=" + tipoBeneficiario
				+ ", curp=" + curp + ", tipoDocumentoProbatorio="
				+ tipoDocumentoProbatorio + "]";
	}

}
