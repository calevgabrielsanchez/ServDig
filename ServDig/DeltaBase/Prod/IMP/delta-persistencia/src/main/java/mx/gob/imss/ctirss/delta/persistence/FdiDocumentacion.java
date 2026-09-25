package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the FDI_DOCUMENTACION database table.
 * 
 */
@Entity
@Table(name="FDI_DOCUMENTACION")
public class FdiDocumentacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_DOCUMENTACION_FALTANTE", nullable=false, precision=22)
	private long idDocumentacionFaltante;

	@Column(name="TX_DOCUMENTO", nullable=false, length=50)
	private String txDocumento;

	//bi-directional many-to-one association to FdiOficio
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_OFICIO", nullable=false)
	private FdiOficio fdiOficio;

    public FdiDocumentacion() {
    }

	public long getIdDocumentacionFaltante() {
		return this.idDocumentacionFaltante;
	}

	public void setIdDocumentacionFaltante(long idDocumentacionFaltante) {
		this.idDocumentacionFaltante = idDocumentacionFaltante;
	}

	public String getTxDocumento() {
		return this.txDocumento;
	}

	public void setTxDocumento(String txDocumento) {
		this.txDocumento = txDocumento;
	}

	public FdiOficio getFdiOficio() {
		return this.fdiOficio;
	}

	public void setFdiOficio(FdiOficio fdiOficio) {
		this.fdiOficio = fdiOficio;
	}
	
}