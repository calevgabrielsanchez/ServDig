package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the SPC_LUGAR_PAGO database table.
 * 
 */
@Entity
@Table(name="SPC_LUGAR_PAGO")
public class SpcLugarPago implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SpcLugarPagoPK id;

	@Column(name="IND_AUTORIZA_CP")
	private String indAutorizaCp;

	@Column(name="IND_AUTORIZA_IG")
	private String indAutorizaIg;

	@Column(name="IND_AUTORIZA_RP")
	private String indAutorizaRp;

	@Column(name="NUM_GRUPOS")
	private BigDecimal numGrupos;

	//bi-directional many-to-one association to SpcEntidadPago
    @ManyToOne
	@JoinColumn(name="ID_ENTIDAD_PAGO", updatable=false, insertable=false)
	private SpcEntidadPago spcEntidadPago;

	//bi-directional many-to-one association to SptPersRecibePagoPensDet
	@OneToMany(mappedBy="spcLugarPago")
	private List<SptPersRecibePagoPensDet> sptPersRecibePagoPensDets;

    public SpcLugarPago() {
    }

	public SpcLugarPagoPK getId() {
		return this.id;
	}

	public void setId(SpcLugarPagoPK id) {
		this.id = id;
	}
	
	public String getIndAutorizaCp() {
		return this.indAutorizaCp;
	}

	public void setIndAutorizaCp(String indAutorizaCp) {
		this.indAutorizaCp = indAutorizaCp;
	}

	public String getIndAutorizaIg() {
		return this.indAutorizaIg;
	}

	public void setIndAutorizaIg(String indAutorizaIg) {
		this.indAutorizaIg = indAutorizaIg;
	}

	public String getIndAutorizaRp() {
		return this.indAutorizaRp;
	}

	public void setIndAutorizaRp(String indAutorizaRp) {
		this.indAutorizaRp = indAutorizaRp;
	}

	public BigDecimal getNumGrupos() {
		return this.numGrupos;
	}

	public void setNumGrupos(BigDecimal numGrupos) {
		this.numGrupos = numGrupos;
	}

	public SpcEntidadPago getSpcEntidadPago() {
		return this.spcEntidadPago;
	}

	public void setSpcEntidadPago(SpcEntidadPago spcEntidadPago) {
		this.spcEntidadPago = spcEntidadPago;
	}
	
	public List<SptPersRecibePagoPensDet> getSptPersRecibePagoPensDets() {
		return this.sptPersRecibePagoPensDets;
	}

	public void setSptPersRecibePagoPensDets(List<SptPersRecibePagoPensDet> sptPersRecibePagoPensDets) {
		this.sptPersRecibePagoPensDets = sptPersRecibePagoPensDets;
	}
	
}