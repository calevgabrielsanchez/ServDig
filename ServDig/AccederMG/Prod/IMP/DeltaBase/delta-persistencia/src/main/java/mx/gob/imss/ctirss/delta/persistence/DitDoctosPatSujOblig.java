package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the DIT_DOCTOS_PAT_SUJ_OBLIG database table.
 * 
 */
@Entity
@Table(name = "DIT_DOCTOS_PAT_SUJ_OBLIG")
public class DitDoctosPatSujOblig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CVE_ID_DOCTOS_PAT_SUJ_OBLIG", nullable = false, precision = 22)
	private long cveIdDoctosPatSujOblig;

	@Column(name = "CAN_LONGITUD", precision = 22)
	private BigDecimal canLongitud;

	@Temporal( TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Column(name = "NOM_ARCHIVO", length = 255)
	private String nomArchivo;

	@Lob()
	@Column(name = "REF_DOCUMENTO")
	private byte[] refDocumento;

	@Column(name = "TIP_CONTENIDO", length = 255)
	private String tipContenido;


	public DitDoctosPatSujOblig() {
	}

	public long getCveIdDoctosPatSujOblig() {
		return this.cveIdDoctosPatSujOblig;
	}

	public void setCveIdDoctosPatSujOblig(long cveIdDoctosPatSujOblig) {
		this.cveIdDoctosPatSujOblig = cveIdDoctosPatSujOblig;
	}

	public BigDecimal getCanLongitud() {
		return this.canLongitud;
	}

	public void setCanLongitud(BigDecimal canLongitud) {
		this.canLongitud = canLongitud;
	}

	public Date getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public String getNomArchivo() {
		return this.nomArchivo;
	}

	public void setNomArchivo(String nomArchivo) {
		this.nomArchivo = nomArchivo;
	}

	public byte[] getRefDocumento() {
		return this.refDocumento;
	}

	public void setRefDocumento(byte[] refDocumento) {
		this.refDocumento = refDocumento != null ? refDocumento.clone() : null;
	}

	public String getTipContenido() {
		return this.tipContenido;
	}

	public void setTipContenido(String tipContenido) {
		this.tipContenido = tipContenido;
	}

}