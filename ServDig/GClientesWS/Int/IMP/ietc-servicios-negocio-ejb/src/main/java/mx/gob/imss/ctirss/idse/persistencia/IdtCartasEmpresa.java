package mx.gob.imss.ctirss.idse.persistencia;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * The persistent class for the IDT_CARTAS_EMPRESAS database table.
 * 
 */
@Entity
@Table(name="IDT_CARTAS_EMPRESAS")
public class IdtCartasEmpresa implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_REGISTRO_PATRONAL")
	private Long cveRegistroPatronal;

	@Column(name="REF_DIGESTION_CARTA")
	private String refDigestionCarta;

	@Column(name="REF_FOLIO_NOTARIAL")
	private String refFolioNotarial;

	@Column(name="REF_SEQ_FIRMA_CARTA")
	private String refSeqFirmaCarta;

    @Lob()
	@Column(name="TIP_CARTA_ACTIVACION")
	private byte[] tipCartaActivacion;

    @Lob()
	@Column(name="TIP_CARTA_CONDICIONES")
	private byte[] tipCartaCondiciones;

    @Lob()
	@Column(name="TIP_CARTA_SINCRONIZACION")
	private byte[] tipCartaSincronizacion;

	//bi-directional one-to-one association to IdtRegistrosPatronale
	@OneToOne
	@JoinColumn(name="CVE_REGISTRO_PATRONAL")
	private IdtRegistrosPatronale idtRegistrosPatronale;

    public IdtCartasEmpresa() {
    }

	public Long getCveRegistroPatronal() {
		return this.cveRegistroPatronal;
	}

	public void setCveRegistroPatronal(Long cveRegistroPatronal) {
		this.cveRegistroPatronal = cveRegistroPatronal;
	}

	public String getRefDigestionCarta() {
		return this.refDigestionCarta;
	}

	public void setRefDigestionCarta(String refDigestionCarta) {
		this.refDigestionCarta = refDigestionCarta;
	}

	public String getRefFolioNotarial() {
		return this.refFolioNotarial;
	}

	public void setRefFolioNotarial(String refFolioNotarial) {
		this.refFolioNotarial = refFolioNotarial;
	}

	public String getRefSeqFirmaCarta() {
		return this.refSeqFirmaCarta;
	}

	public void setRefSeqFirmaCarta(String refSeqFirmaCarta) {
		this.refSeqFirmaCarta = refSeqFirmaCarta;
	}

	public byte[] getTipCartaActivacion() {
		return this.tipCartaActivacion;
	}

	public void setTipCartaActivacion(byte[] tipCartaActivacion) {
		this.tipCartaActivacion = tipCartaActivacion != null ? tipCartaActivacion.clone() : null;
	}

	public byte[] getTipCartaCondiciones() {
		return this.tipCartaCondiciones;
	}

	public void setTipCartaCondiciones(byte[] tipCartaCondiciones) {
		this.tipCartaCondiciones = tipCartaCondiciones != null ? tipCartaCondiciones.clone() : null;
	}

	public byte[] getTipCartaSincronizacion() {
		return this.tipCartaSincronizacion;
	}

	public void setTipCartaSincronizacion(byte[] tipCartaSincronizacion) {
		this.tipCartaSincronizacion = tipCartaSincronizacion != null ? tipCartaSincronizacion.clone(): null;
	}

	public IdtRegistrosPatronale getIdtRegistrosPatronale() {
		return this.idtRegistrosPatronale;
	}

	public void setIdtRegistrosPatronale(IdtRegistrosPatronale idtRegistrosPatronale) {
		this.idtRegistrosPatronale = idtRegistrosPatronale;
	}
	
    @Override
    public Object clone(){
    	Object obj = null;    	
    	try {
			obj = super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
    }
    
	@Override
	public String toString() {
		return "IdtCartasEmpresa [cveRegistroPatronal=" + cveRegistroPatronal 
			+ ", refDigestionCarta=" + refDigestionCarta 
			+ ", refFolioNotarial=" + refFolioNotarial 
			+ ", refSeqFirmaCarta=" + refSeqFirmaCarta
			+ ", tipCartaActivacion=" + tipCartaActivacion
			+ ", tipCartaCondiciones=" + tipCartaCondiciones
			+ ", tipCartaSincronizacion=" + tipCartaSincronizacion
			+ ", idtRegistrosPatronale=" + idtRegistrosPatronale + "]";
	}
	
}