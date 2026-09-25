/**
 * 
 */
package mx.gob.imss.digital.modelo.seguros;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import mx.gob.imss.digital.modelo.cuestionario.RespuestasCuestionario;

/**
 * MOdelo que contiene los datos para imprimir los resultados de los cuestionarios
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cuestionarioSeguroReporte", namespace = "http://mx.gob.imss.digital.modelo.seguros")
@XmlRootElement(name = "cuestionarioSeguroReporte", namespace = "http://mx.gob.imss.digital.modelo.seguros")
public class CuestionarioSeguroReporte implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Respuestas del cuestionario asociadas a una persona
     */
    private RespuestasCuestionario respuestasCuestionario;
    
    /** The titular. */
    private String titular = "X";   
    
    /** The ap paterno. */
    private String apPaterno;
    
    /** The ap materno. */
    private String apMaterno;
    
    /** The nombres. */
    private String nombres;
    
    /** The nss. */
    private String nss;
    
    /** The calle io manzana. */
    private String calleIOManzana;
    
    /** The numero. */
    private String numero;
    
    /** The colonia. */
    private String colonia;
    
    /** The curp. */
    private String curp;
    
    /** The poblacion. */
    private String poblacion;
    
    /** The estado. */
    private String estado;
    
    /** The cod postal. */
    private String codPostal;
    
    /** The telefono. */
    private String telefono;
    
    /** The lug nacimiento. */
    private String lugNacimiento;
    
    /** The fecha nacimiento. */
    private String fechaNacimiento;
    
    /** The edad. */
    private String edad;
    
    /** The sex fem. */
    private String sexFem;
    
    /** The sex mas. */
    private String sexMas;
    
    /** The est civil. */
    private String estCivil;
    
    /** The cve deleg. */
    private String cveDeleg;
    
    /** The unidad med fam. */
    private String unidadMedFam;
    
    /** The mod34. */
    private String mod34;
    
    /** The mod35. */
    private String mod35;    
        
    /** The mod43. */
    private String mod43;
    
    /** The mod44. */
    private String mod44;

    

    /**
     * @return the respuestasCuestionario
     */
    public RespuestasCuestionario getRespuestasCuestionario() {
        return respuestasCuestionario;
    }

    /**
     * @param respuestasCuestionario the respuestasCuestionario to set
     */
    public void setRespuestasCuestionario(RespuestasCuestionario respuestasCuestionario) {
        this.respuestasCuestionario = respuestasCuestionario;
    }

    /**
     * @return the titular
     */
    public String getTitular() {
        return titular;
    }

    /**
     * @param titular the titular to set
     */
    public void setTitular(String titular) {
        this.titular = titular;
    }

    /**
     * @return the apPaterno
     */
    public String getApPaterno() {
        return apPaterno;
    }

    /**
     * @param apPaterno the apPaterno to set
     */
    public void setApPaterno(String apPaterno) {
        this.apPaterno = apPaterno;
    }

    /**
     * @return the apMaterno
     */
    public String getApMaterno() {
        return apMaterno;
    }

    /**
     * @param apMaterno the apMaterno to set
     */
    public void setApMaterno(String apMaterno) {
        this.apMaterno = apMaterno;
    }

    /**
     * @return the nombres
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * @param nombres the nombres to set
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     * @return the nss
     */
    public String getNss() {
        return nss;
    }

    /**
     * @param nss the nss to set
     */
    public void setNss(String nss) {
        this.nss = nss;
    }

    /**
     * @return the calleIOManzana
     */
    public String getCalleIOManzana() {
        return calleIOManzana;
    }

    /**
     * @param calleIOManzana the calleIOManzana to set
     */
    public void setCalleIOManzana(String calleIOManzana) {
        this.calleIOManzana = calleIOManzana;
    }

    /**
     * @return the numero
     */
    public String getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * @return the colonia
     */
    public String getColonia() {
        return colonia;
    }

    /**
     * @param colonia the colonia to set
     */
    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    /**
     * @return the curp
     */
    public String getCurp() {
        return curp;
    }

    /**
     * @param curp the curp to set
     */
    public void setCurp(String curp) {
        this.curp = curp;
    }

    /**
     * @return the poblacion
     */
    public String getPoblacion() {
        return poblacion;
    }

    /**
     * @param poblacion the poblacion to set
     */
    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the codPostal
     */
    public String getCodPostal() {
        return codPostal;
    }

    /**
     * @param codPostal the codPostal to set
     */
    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the lugNacimiento
     */
    public String getLugNacimiento() {
        return lugNacimiento;
    }

    /**
     * @param lugNacimiento the lugNacimiento to set
     */
    public void setLugNacimiento(String lugNacimiento) {
        this.lugNacimiento = lugNacimiento;
    }

    /**
     * @return the fechaNacimiento
     */
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * @param fechaNacimiento the fechaNacimiento to set
     */
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * @return the edad
     */
    public String getEdad() {
        return edad;
    }

    /**
     * @param edad the edad to set
     */
    public void setEdad(String edad) {
        this.edad = edad;
    }

    /**
     * @return the sexFem
     */
    public String getSexFem() {
        return sexFem;
    }

    /**
     * @param sexFem the sexFem to set
     */
    public void setSexFem(String sexFem) {
        this.sexFem = sexFem;
    }

    /**
     * @return the sexMas
     */
    public String getSexMas() {
        return sexMas;
    }

    /**
     * @param sexMas the sexMas to set
     */
    public void setSexMas(String sexMas) {
        this.sexMas = sexMas;
    }

    /**
     * @return the estCivil
     */
    public String getEstCivil() {
        return estCivil;
    }

    /**
     * @param estCivil the estCivil to set
     */
    public void setEstCivil(String estCivil) {
        this.estCivil = estCivil;
    }

    /**
     * @return the cveDeleg
     */
    public String getCveDeleg() {
        return cveDeleg;
    }

    /**
     * @param cveDeleg the cveDeleg to set
     */
    public void setCveDeleg(String cveDeleg) {
        this.cveDeleg = cveDeleg;
    }

    /**
     * @return the unidadMedFam
     */
    public String getUnidadMedFam() {
        return unidadMedFam;
    }

    /**
     * @param unidadMedFam the unidadMedFam to set
     */
    public void setUnidadMedFam(String unidadMedFam) {
        this.unidadMedFam = unidadMedFam;
    }

    /**
     * @return the mod34
     */
    public String getMod34() {
        return mod34;
    }

    /**
     * @param mod34 the mod34 to set
     */
    public void setMod34(String mod34) {
        this.mod34 = mod34;
    }

    /**
     * @return the mod35
     */
    public String getMod35() {
        return mod35;
    }

    /**
     * @param mod35 the mod35 to set
     */
    public void setMod35(String mod35) {
        this.mod35 = mod35;
    }

    /**
     * @return the mod43
     */
    public String getMod43() {
        return mod43;
    }

    /**
     * @param mod43 the mod43 to set
     */
    public void setMod43(String mod43) {
        this.mod43 = mod43;
    }

    /**
     * @return the mod44
     */
    public String getMod44() {
        return mod44;
    }

    /**
     * @param mod44 the mod44 to set
     */
    public void setMod44(String mod44) {
        this.mod44 = mod44;
    }
    
    
    
    
}
