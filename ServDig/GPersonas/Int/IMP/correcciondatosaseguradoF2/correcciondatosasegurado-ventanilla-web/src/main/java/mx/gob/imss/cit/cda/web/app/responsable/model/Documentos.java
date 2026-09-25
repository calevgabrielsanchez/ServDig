package mx.gob.imss.cit.cda.web.app.responsable.model;

import java.util.List;

import mx.gob.imss.cit.cda.web.support.model.BaseModel;

public class Documentos extends BaseModel {

    private static final long serialVersionUID = -1399002688397130907L;

    private List<Documento> documentos;

    public List<Documento> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<Documento> documentos) {
        this.documentos = documentos;
    }

    @Override
    public String toString() {
        StringBuffer docs = new StringBuffer();
        docs.append("Documentos [documentos=");
        for (Documento documento : documentos) {
            docs.append(documento.toString());
        }
        docs.append("]");
        return docs.toString();
    }
}
