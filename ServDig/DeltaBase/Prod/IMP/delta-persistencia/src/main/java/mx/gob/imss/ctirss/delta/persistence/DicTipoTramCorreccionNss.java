package mx.gob.imss.ctirss.delta.persistence;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "DIC_TIPO_TRAM_CORRECCION_NSS")
public class DicTipoTramCorreccionNss {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "CVE_ID_TIPO_TRAM_CORREC_NSS")
    private Long cveIdTipoTramCorrecNss;
    
    @Column(name = "DES_TIPO_CORRECCION_TRAM_NSS")
    private String descTipoCorreccionTramNss;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name =  "FEC_REGISTRO_ALTA")
    private Date fecRegistroAlta;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name =  "FEC_REGISTRO_BAJA")
    private Date fecRegistroBaja;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FEC_REGISTRO_ACTUALIZADO")
    private Date fecRegistroActualizado;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FEC_REGISTRO_ALTA_186581")
    private Date fecRegistroAlta186581;

    public Long getCveIdTipoTramCorrecNss() {
        return cveIdTipoTramCorrecNss;
    }

    public void setCveIdTipoTramCorrecNss(Long cveIdTipoTramCorrecNss) {
        this.cveIdTipoTramCorrecNss = cveIdTipoTramCorrecNss;
    }

    public String getDescTipoCorreccionTramNss() {
        return descTipoCorreccionTramNss;
    }

    public void setDescTipoCorreccionTramNss(String descTipoCorreccionTramNss) {
        this.descTipoCorreccionTramNss = descTipoCorreccionTramNss;
    }

    public Date getFecRegistroAlta() {
        return fecRegistroAlta;
    }

    public void setFecRegistroAlta(Date fecRegistroAlta) {
        this.fecRegistroAlta = fecRegistroAlta;
    }

    public Date getFecRegistroBaja() {
        return fecRegistroBaja;
    }

    public void setFecRegistroBaja(Date fecRegistroBaja) {
        this.fecRegistroBaja = fecRegistroBaja;
    }

    public Date getFecRegistroActualizado() {
        return fecRegistroActualizado;
    }

    public void setFecRegistroActualizado(Date fecRegistroActualizado) {
        this.fecRegistroActualizado = fecRegistroActualizado;
    }

    public Date getFecRegistroAlta186581() {
        return fecRegistroAlta186581;
    }

    public void setFecRegistroAlta186581(Date fecRegistroAlta186581) {
        this.fecRegistroAlta186581 = fecRegistroAlta186581;
    }
   
}
