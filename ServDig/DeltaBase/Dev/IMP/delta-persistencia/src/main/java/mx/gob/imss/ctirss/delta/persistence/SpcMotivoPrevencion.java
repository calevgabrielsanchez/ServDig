package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the SPC_MOTIVO_PREVENCION database table.
 * 
 */
@Entity
@Table(name="SPC_MOTIVO_PREVENCION")
@NamedQuery(name="SpcMotivoPrevencion.findAll", query="SELECT s FROM SpcMotivoPrevencion s")
public class SpcMotivoPrevencion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SPC_MOTIVO_PREVENCION_IDMOTIVOPREVENCION_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SPC_MOTIVO_PREVENCION_IDMOTIVOPREVENCION_GENERATOR")
	@Column(name="ID_MOTIVO_PREVENCION")
	private long idMotivoPrevencion;

	@Column(name="DES_MOTIVO")
	private String desMotivo;

	@Column(name="NUM_DIAS_DESAHOGO")
	private BigDecimal numDiasDesahogo;

	public SpcMotivoPrevencion() {
	}

	public long getIdMotivoPrevencion() {
		return this.idMotivoPrevencion;
	}

	public void setIdMotivoPrevencion(long idMotivoPrevencion) {
		this.idMotivoPrevencion = idMotivoPrevencion;
	}

	public String getDesMotivo() {
		return this.desMotivo;
	}

	public void setDesMotivo(String desMotivo) {
		this.desMotivo = desMotivo;
	}

	public BigDecimal getNumDiasDesahogo() {
		return this.numDiasDesahogo;
	}

	public void setNumDiasDesahogo(BigDecimal numDiasDesahogo) {
		this.numDiasDesahogo = numDiasDesahogo;
	}

}