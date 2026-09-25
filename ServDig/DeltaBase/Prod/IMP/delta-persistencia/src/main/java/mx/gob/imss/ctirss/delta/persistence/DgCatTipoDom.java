package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the DG_CAT_TIPO_DOM database table.
 * 
 */
@Entity
@Table(name="DG_CAT_TIPO_DOM")
public class DgCatTipoDom implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_TIPO_DOM", nullable=false, precision=1)
	private Integer cveTipoDom;

	@Column(name="COMP_ESPAC", nullable=false, length=240)
	private String compEspac;

	@Column(nullable=false, length=240)
	private String descripcion;

	//bi-directional many-to-one association to DgDomicilioGeografico
	@OneToMany(mappedBy="dgCatTipoDom")
	private List<DgDomicilioGeografico> dgDomicilioGeograficos;

    public DgCatTipoDom() {
    }

	public Integer getCveTipoDom() {
		return this.cveTipoDom;
	}

	public void setCveTipoDom(Integer cveTipoDom) {
		this.cveTipoDom = cveTipoDom;
	}

	public String getCompEspac() {
		return this.compEspac;
	}

	public void setCompEspac(String compEspac) {
		this.compEspac = compEspac;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<DgDomicilioGeografico> getDgDomicilioGeograficos() {
		return this.dgDomicilioGeograficos;
	}

	public void setDgDomicilioGeograficos(List<DgDomicilioGeografico> dgDomicilioGeograficos) {
		this.dgDomicilioGeograficos = dgDomicilioGeograficos;
	}
	
}