package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.delta.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.delta.framework.annotations.OnSearchLlavePrimaria;

import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the DIT_TIPO_CONTACTO database table.
 * 
 */
@Entity
@Table(name="DIT_TIPO_CONTACTO")
@OnSearchLlavePrimaria(atributos="cveIdTipoContacto")
@ComponentComboCampoDescripcion(atributo="desTipoContacto")
public class DitTipoContacto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @SequenceGenerator(name = "DIT_TIPO_CONTACTO_GENERATOR", sequenceName = "SEQ_DITTIPOCONTACTO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_TIPO_CONTACTO_GENERATOR")
	@Column(name="CVE_ID_TIPO_CONTACTO", nullable=false, precision=22)
	private Long cveIdTipoContacto;

	@Column(name="DES_ESTRUCTURA", length=100)
	private String desEstructura;

	@Column(name="DES_MASCARA", length=100)
	private String desMascara;

	@Column(name="DES_TIPO_CONTACTO", length=100)
	private String desTipoContacto;

	//bi-directional many-to-one association to DitFormaContacto
	@OneToMany(mappedBy="ditTipoContacto")
	private List<DitFormaContacto> ditFormaContactos;

    public DitTipoContacto() {
    }

	public Long getCveIdTipoContacto() {
		return this.cveIdTipoContacto;
	}

	public void setCveIdTipoContacto(Long cveIdTipoContacto) {
		this.cveIdTipoContacto = cveIdTipoContacto;
	}

	public String getDesEstructura() {
		return this.desEstructura;
	}

	public void setDesEstructura(String desEstructura) {
		this.desEstructura = desEstructura;
	}

	public String getDesMascara() {
		return this.desMascara;
	}

	public void setDesMascara(String desMascara) {
		this.desMascara = desMascara;
	}

	public String getDesTipoContacto() {
		return this.desTipoContacto;
	}

	public void setDesTipoContacto(String desTipoContacto) {
		this.desTipoContacto = desTipoContacto;
	}

	public List<DitFormaContacto> getDitFormaContactos() {
	    if(ditFormaContactos == null) {
	        ditFormaContactos = new ArrayList<DitFormaContacto>();
	    }
		return this.ditFormaContactos;
	}

	public void setDitFormaContactos(List<DitFormaContacto> ditFormaContactos) {
		this.ditFormaContactos = ditFormaContactos;
	}
	
}