package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DIT_PATRON_CARGA_ARCHIVO database table.
 * 
 */
@Entity
@Table(name="DIT_PATRON_CARGA_ARCHIVO")
public class DitPatronCargaArchivo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_PATRON_CARGA_ARCHIVO", nullable=false, precision=22)
	private long cveIdPatronCargaArchivo;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Column(precision=22)
	private BigDecimal longitud;

	@Column(name="NOMBRE_ARCHIVO", length=255)
	private String nombreArchivo;

	@Column(name="NUM_FOLIO_TRANSACCION", length=100)
	private String numFolioTransaccion;

    @Lob()
	@Column(name="REF_DOCUMENTO")
	private byte[] refDocumento;

	@Column(name="TIPO_CONTENIDO", length=255)
	private String tipoContenido;

	//bi-directional many-to-one association to DitPatronSujetoObligado
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PATRON_SUJETO_OBLIGADO")
	private DitPatronSujetoObligado ditPatronSujetoObligado;

    public DitPatronCargaArchivo() {
    }

	public long getCveIdPatronCargaArchivo() {
		return this.cveIdPatronCargaArchivo;
	}

	public void setCveIdPatronCargaArchivo(long cveIdPatronCargaArchivo) {
		this.cveIdPatronCargaArchivo = cveIdPatronCargaArchivo;
	}

	public Date getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public BigDecimal getLongitud() {
		return this.longitud;
	}

	public void setLongitud(BigDecimal longitud) {
		this.longitud = longitud;
	}

	public String getNombreArchivo() {
		return this.nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getNumFolioTransaccion() {
		return this.numFolioTransaccion;
	}

	public void setNumFolioTransaccion(String numFolioTransaccion) {
		this.numFolioTransaccion = numFolioTransaccion;
	}

	public byte[] getRefDocumento() {
		return this.refDocumento;
	}

	public void setRefDocumento(byte[] refDocumento) {
		this.refDocumento = refDocumento != null ? refDocumento.clone() : null;
	}

	public String getTipoContenido() {
		return this.tipoContenido;
	}

	public void setTipoContenido(String tipoContenido) {
		this.tipoContenido = tipoContenido;
	}

	public DitPatronSujetoObligado getDitPatronSujetoObligado() {
		return this.ditPatronSujetoObligado;
	}

	public void setDitPatronSujetoObligado(DitPatronSujetoObligado ditPatronSujetoObligado) {
		this.ditPatronSujetoObligado = ditPatronSujetoObligado;
	}
	
}