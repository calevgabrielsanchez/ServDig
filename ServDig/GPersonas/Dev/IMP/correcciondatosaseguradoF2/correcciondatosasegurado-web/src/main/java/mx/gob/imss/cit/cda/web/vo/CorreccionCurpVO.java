package mx.gob.imss.cit.cda.web.vo;

import java.io.Serializable;
import java.util.List;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

public class CorreccionCurpVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Fisica personaRenapo;
    private DomicilioAclaracionVO domicilioCorrecion;
    private List<DocumentoProbatorio> listaDocumentos;
    private DatosAdicionalesHistoriaLaboral datosAdicionalesHistoriaLaboral;
    private DatosHistoriaLaboralVO datosHistoriaLaboralVO;

    public Fisica getPersonaRenapo() {
        return personaRenapo;
    }

    public void setPersonaRenapo(Fisica personaRenapo) {
        this.personaRenapo = personaRenapo;
    }

    public DomicilioAclaracionVO getDomicilioCorrecion() {
        return domicilioCorrecion;
    }

    public void setDomicilioCorrecion(DomicilioAclaracionVO domicilioCorrecion) {
        this.domicilioCorrecion = domicilioCorrecion;
    }

    public List<DocumentoProbatorio> getListaDocumentos() {
        return listaDocumentos;
    }

    public void setListaDocumentos(List<DocumentoProbatorio> listaDocumentos) {
        this.listaDocumentos = listaDocumentos;
    }

    public DatosAdicionalesHistoriaLaboral getDatosAdicionalesHistoriaLaboral() {
        return datosAdicionalesHistoriaLaboral;
    }

    public void setDatosAdicionalesHistoriaLaboral(
            DatosAdicionalesHistoriaLaboral datosAdicionalesHistoriaLaboral) {
        this.datosAdicionalesHistoriaLaboral = datosAdicionalesHistoriaLaboral;
    }

    public DatosHistoriaLaboralVO getDatosHistoriaLaboralVO() {
        return datosHistoriaLaboralVO;
    }

    public void setDatosHistoriaLaboralVO(
            DatosHistoriaLaboralVO datosHistoriaLaboralVO) {
        this.datosHistoriaLaboralVO = datosHistoriaLaboralVO;
    }

}
