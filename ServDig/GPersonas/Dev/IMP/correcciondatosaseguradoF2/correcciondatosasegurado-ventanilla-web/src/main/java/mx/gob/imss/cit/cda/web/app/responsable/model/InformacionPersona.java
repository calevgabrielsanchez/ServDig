package mx.gob.imss.cit.cda.web.app.responsable.model;

import mx.gob.imss.cit.cda.web.support.model.BaseModel;

/**
*
* @author mon
*/
public class InformacionPersona extends BaseModel  {

    /**
     * 
     */
    private static final long serialVersionUID = -6006525309197293901L;
    
    private String curp;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombre;
    private String sexo;
    private String curpsHistoricas;
    
    /**
     * @return the curp
     */
    public String getCurp() {
        return curp;
    }

    /**
     * @param curp
     *            the curp to set
     */
    public void setCurp(String curp) {
        this.curp = curp;
    }

    /**
     * @return the apellidoPaterno
     */
    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    /**
     * @param apellidoPaterno
     *            the apellidoPaterno to set
     */
    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    /**
     * @return the apellidoMaterno
     */
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    /**
     * @param apellidoMaterno
     *            the apellidoMaterno to set
     */
    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre
     *            the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCurpsHistoricas() {
        return curpsHistoricas;
    }

    public void setCurpsHistoricas(String curpsHistoricas) {
        this.curpsHistoricas = curpsHistoricas;
    }

    
    @Override
    public String toString() {
        return "InformacionPersona [curp=" + curp + ", apellidoPaterno="
                + apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno
                + ", nombre=" + nombre + ", sexo=" + sexo
                + ", curpsHistoricas=" + curpsHistoricas + "]";
    }
   
}
