package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="DIT_DETALLE_ACLARACION")
public class DitDetalleAclaracion implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "SEQ_DITDETALLEACLARACION", sequenceName = "SEQ_DITDETALLEACLARACION")
    @GeneratedValue(generator = "SEQ_DITDETALLEACLARACION")
	@Column(name="CVE_ID_DETALLE_ACLARACION")
	private Long cveIdDetalleAclaracion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CVE_ID_MOTIVO_ACLARACION")
	private DicMotivoAclaracion motivoAclaracion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CVE_ID_CORRECCION_DATOS_ASEG")
	private DitCorreccionDatosAsegurado correccionDatosAsegurado;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CVE_ID_INSTITUCION")
	private DicInstitucion institucion;
	
	@Column(name="DES_DETALLE", length=4000)
	private String desDetalle;
	
	@Column(name="DES_MOTIVO_ACLARACION")
	private String desMotivoAclaracion;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	public Long getCveIdDetalleAclaracion() {
		return cveIdDetalleAclaracion;
	}

	public void setCveIdDetalleAclaracion(Long cveIdDetalleAclaracion) {
		this.cveIdDetalleAclaracion = cveIdDetalleAclaracion;
	}

	

	public DicMotivoAclaracion getMotivoAclaracion() {
		return motivoAclaracion;
	}

	public void setMotivoAclaracion(DicMotivoAclaracion motivoAclaracion) {
		this.motivoAclaracion = motivoAclaracion;
	}

	public DitCorreccionDatosAsegurado getCorreccionDatosAsegurado() {
		return correccionDatosAsegurado;
	}

	public void setCorreccionDatosAsegurado(DitCorreccionDatosAsegurado correccionDatosAsegurado) {
		this.correccionDatosAsegurado = correccionDatosAsegurado;
	}

	public DicInstitucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(DicInstitucion institucion) {
		this.institucion = institucion;
	}

	public String getDesDetalle() {
		return desDetalle;
	}

	public void setDesDetalle(String desDetalle) {
		this.desDetalle = desDetalle;
	}

	public String getDesMotivoAclaracion() {
		return desMotivoAclaracion;
	}

	public void setDesMotivoAclaracion(String desMotivoAclaracion) {
		this.desMotivoAclaracion = desMotivoAclaracion;
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
	
}
