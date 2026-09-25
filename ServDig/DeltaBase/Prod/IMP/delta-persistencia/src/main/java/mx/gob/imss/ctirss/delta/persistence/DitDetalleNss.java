package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "DIT_DETALLE_NSS_CDA")
public class DitDetalleNss implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SEQ_DITDETALLENSS", sequenceName = "SEQ_DITDETALLENSS")
    @GeneratedValue(generator = "SEQ_DITDETALLENSS")
    @Column(name = "CVE_ID_DETALLE_NSS_CDA")
    private Long cveDetalleNss;

    /** FK **/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CVE_ID_CORRECCION_DATOS_ASEG")
    private DitCorreccionDatosAsegurado correccionDatosAsegurado;

    @Column(name = "NUM_NSS", length = 11)
    private String nss;
	
	@Column(name = "OBSERVACIONES", length = 500)
    private String observaciones;
	

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FEC_REGISTRO_ALTA")
    private Date fecRegistroAlta;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FEC_REGISTRO_BAJA")
    private Date fecRegistroBaja;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FEC_REGISTRO_ACTUALIZADO")
    private Date fecRegistroActualizado;

    
    //@Column(name = "TRAMITE_INICIAL", length = 2)
    //private String tramiteInicial;
    
    
    
    /** FK **/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CVE_ID_ORIGEN_CAPTURA_NSS", referencedColumnName = "CVE_ID_ORIGEN_CAPTURA_NSS_CDA", nullable = false)
    private DicOrigenCapturaNssCda dicOrigenCapturaNssCda;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CVE_ID_TIPO_NSS")
    private DicTipoNssCorreccion dicTipoNss;
    
    @OneToMany(mappedBy="ditDetalleNss", fetch = FetchType.LAZY)
    private List<DitTipoCertificacionCorreccion> listaCertificacion;
    
    
    public Long getCveDetalleNss() {
        return cveDetalleNss;
    }

    public void setCveDetalleNss(Long cveDetalleNss) {
        this.cveDetalleNss = cveDetalleNss;
    }

    public DicOrigenCapturaNssCda getDicOrigenCapturaNssCda() {
        return dicOrigenCapturaNssCda;
    }

    public void setDicOrigenCapturaNssCda(
            DicOrigenCapturaNssCda dicOrigenCapturaNssCda) {
        this.dicOrigenCapturaNssCda = dicOrigenCapturaNssCda;
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

    public DitCorreccionDatosAsegurado getCorreccionDatosAsegurado() {
        return correccionDatosAsegurado;
    }

    public void setCorreccionDatosAsegurado(
            DitCorreccionDatosAsegurado correccionDatosAsegurado) {
        this.correccionDatosAsegurado = correccionDatosAsegurado;
    }

    public DicTipoNssCorreccion getDicTipoNss() {
        return dicTipoNss;
    }

    public void setDicTipoNss(DicTipoNssCorreccion dicTipoNss) {
        this.dicTipoNss = dicTipoNss;
    }
    

    public List<DitTipoCertificacionCorreccion> getListaCertificacion() {
        return listaCertificacion;
    }

    public void setListaCertificacion(
            List<DitTipoCertificacionCorreccion> listaCertificacion) {
        this.listaCertificacion = listaCertificacion;
    }

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }
	
	public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

	/*public String getTramiteInicial() {
		return tramiteInicial;
	}

	public void setTramiteInicial(String tramiteInicial) {
		this.tramiteInicial = tramiteInicial;
	}*/

    
    
}
