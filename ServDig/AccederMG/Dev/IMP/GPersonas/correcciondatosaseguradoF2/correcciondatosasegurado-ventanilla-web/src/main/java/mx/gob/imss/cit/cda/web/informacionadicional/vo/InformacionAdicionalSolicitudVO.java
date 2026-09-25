package mx.gob.imss.cit.cda.web.informacionadicional.vo;

import java.io.Serializable;
import java.util.List;

import mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio;
import mx.gob.imss.cit.cda.web.vo.NSSVO;

public class InformacionAdicionalSolicitudVO implements Serializable{
    
    private static final long serialVersionUID = 1L;

    private List<NSSVO> listNSS;
    private List<String> asociadosNSS;
    private DocumentoProbatorio documentoProbatorio;
    private List<DocumentoProbatorio> documentoProbatorioAseguradoList;
    private List<DocumentoProbatorio> documentoProbatorioBeneficiarioList;
    private boolean defuncion;
    private String tipoSolicitante;
    private String tipoBeneficiario;
    private String curp;
    private String listaDocumentosNssTemp;
    private String observaciones;
    private NSSVO nssvo;
    private boolean banderaAsegurado;
    private boolean banderaBeneficiario;
    private boolean banderaNss;

    public List<DocumentoProbatorio> getDocumentoProbatorioAseguradoList() {
        return documentoProbatorioAseguradoList;
    }

    public void setDocumentoProbatorioAseguradoList(
            List<DocumentoProbatorio> documentoProbatorioAseguradoList) {
        this.documentoProbatorioAseguradoList = documentoProbatorioAseguradoList;
    }

    public List<DocumentoProbatorio> getDocumentoProbatorioBeneficiarioList() {
        return documentoProbatorioBeneficiarioList;
    }

    public void setDocumentoProbatorioBeneficiarioList(
            List<DocumentoProbatorio> documentoProbatorioBeneficiarioList) {
        this.documentoProbatorioBeneficiarioList = documentoProbatorioBeneficiarioList;
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

    public List<NSSVO> getListNSS() {
        return listNSS;
    }

    public void setListNSS(List<NSSVO> listNSS) {
        this.listNSS = listNSS;
    }

    public List<String> getAsociadosNSS() {
        return asociadosNSS;
    }

    public void setAsociadosNSS(List<String> asociadosNSS) {
        this.asociadosNSS = asociadosNSS;
    }

    public DocumentoProbatorio getDocumentoProbatorio() {
        return documentoProbatorio;
    }

    public void setDocumentoProbatorio(DocumentoProbatorio documentoProbatorio) {
        this.documentoProbatorio = documentoProbatorio;
    }
    
    public String getListaDocumentosNssTemp() {
        return listaDocumentosNssTemp;
    }

    public void setListaDocumentosNssTemp(String listaDocumentosNssTemp) {
        this.listaDocumentosNssTemp = listaDocumentosNssTemp;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public NSSVO getNssvo() {
        return nssvo;
    }

    public void setNssvo(NSSVO nssvo) {
        this.nssvo = nssvo;
    }

    public boolean isBanderaAsegurado() {
        return banderaAsegurado;
    }

    public void setBanderaAsegurado(boolean banderaAsegurado) {
        this.banderaAsegurado = banderaAsegurado;
    }

    public boolean isBanderaBeneficiario() {
        return banderaBeneficiario;
    }

    public void setBanderaBeneficiario(boolean banderaBeneficiario) {
        this.banderaBeneficiario = banderaBeneficiario;
    }

    public boolean isBanderaNss() {
        return banderaNss;
    }

    public void setBanderaNss(boolean banderaNss) {
        this.banderaNss = banderaNss;
    }

    @Override
    public String toString() {
        return "DatosHistoriaLaboralVO [documentoProbatorioAsegurado="
                + documentoProbatorioAseguradoList
                + ", documentoProbatorioBeneficiario="
                + documentoProbatorioBeneficiarioList + ", NSSList=" + listNSS
                + ", defuncion=" + defuncion + ", tipoSolicitante="
                + tipoSolicitante + ", tipoBeneficiario=" + tipoBeneficiario
                + ", asociadosNSS=" + asociadosNSS + ", curp=" + curp + "]";
    }

}
