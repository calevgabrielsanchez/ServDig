package mx.gob.imss.cit.cda.web.app.responsable.model;

/**
*
* @author mon
*/
public class InformacionBeneficiario  extends InformacionPersona  {

    /**
     * 
     */
    private static final long serialVersionUID = 5559704362760132223L;
    
    private String parentesco;

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    @Override
    public String toString() {
        return super.toString()+ "InformacionBeneficiario [parentesco=" + parentesco + "]";
    }
    
}
