package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the SPT_ACC_SINDO_PEN database table.
 * 
 */
@Entity
@Table(name="SPT_ACC_SINDO_PEN")
@NamedQuery(name="SptAccSindoPen.findAll", query="SELECT s FROM SptAccSindoPen s")
public class SptAccSindoPen implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SptAccSindoPenPK id;

	@Column(name="IND_ACCESO_SINDO")
	private BigDecimal indAccesoSindo;

	//bi-directional many-to-one association to SpcTipoPension
	@ManyToOne
	@JoinColumn(name="ID_TIPO_PENSION",insertable=false, updatable=false)
	private SpcTipoPension spcTipoPension;

	public SptAccSindoPen() {
	}

	public SptAccSindoPenPK getId() {
		return this.id;
	}

	public void setId(SptAccSindoPenPK id) {
		this.id = id;
	}

	public BigDecimal getIndAccesoSindo() {
		return this.indAccesoSindo;
	}

	public void setIndAccesoSindo(BigDecimal indAccesoSindo) {
		this.indAccesoSindo = indAccesoSindo;
	}

	public SpcTipoPension getSpcTipoPension() {
		return this.spcTipoPension;
	}

	public void setSpcTipoPension(SpcTipoPension spcTipoPension) {
		this.spcTipoPension = spcTipoPension;
	}

}