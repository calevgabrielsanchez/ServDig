
package mx.gob.imss.cit.cda.web.vo;

import java.io.Serializable;
import java.util.List;

public class NSSVO implements Serializable {

    private static final long serialVersionUID = 2627447186585608487L;
    private String NSS;
    private List<DocumentoProbatorio> documentoProbatorioList;
    private String observaciones;

    public String getNSS() {
        return NSS;
    }

    public void setNSS(String nSS) {
        NSS = nSS;
    }

    public List<DocumentoProbatorio> getDocumentoProbatorioList() {
        return documentoProbatorioList;
    }

    public void setDocumentoProbatorioList(
            List<DocumentoProbatorio> documentoProbatorioList) {
        this.documentoProbatorioList = documentoProbatorioList;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return "NSSVO [NSS=" + NSS + "]";
    }

}
