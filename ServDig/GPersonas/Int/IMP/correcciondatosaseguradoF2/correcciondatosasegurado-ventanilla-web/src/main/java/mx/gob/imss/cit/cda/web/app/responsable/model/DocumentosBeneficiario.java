package mx.gob.imss.cit.cda.web.app.responsable.model;

public class DocumentosBeneficiario extends Documentos {

    private static final long serialVersionUID = -3316782103197276641L;

    private String parentesco;

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    @Override
    public String toString() {
        super.toString();
        return "DocumentosBeneficiario [parentesco=" + parentesco + "]";
    }

}
