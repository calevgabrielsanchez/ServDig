package mx.gob.imss.ctirss.delta.cobranza.service.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * The persistent class for the D_COP_SUBDELEGACION database table.
 * 
 */
@Entity
@Table(name="D_COP_SUBDELEGACION")
public class DCopSubdelegacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DCopSubdelegacionPK id;

	@Column(name="CVE_OFICINA")
	private String cveOficina;

	@Column(name="DESC_OFICINA")
	private String descOficina;

	@Column(name="DESC_SUBDELEGACION")
	private String descSubdelegacion;

	@Column(name="DOM_OFICINA")
	private String domOficina;

	@Column(name="DOM_SUBDELEGADO")
	private String domSubdelegado;

	@Column(name="JEFE_OFI")
	private String jefeOfi;

	@Column(name="JEFE_RFC")
	private String jefeRfc;

	@Column(name="NOM_SUBDELEGADO")
	private String nomSubdelegado;

	@Column(name="NUM_OFICINA")
	private Long numOficina;

	private String usuario;
	
	@OneToOne
	@JoinColumn(name = "CVE_DELEGACION",insertable = false, updatable=false)
	private DCopDelegacion dCopDelegacion;

	/*
	//bi-directional one-to-one association to DCopPatrone
	@OneToOne(mappedBy="DCopSubdelegacion")
	private DCopPatrone DCopPatrone;*/

    public DCopSubdelegacion() {
    }

	public DCopSubdelegacionPK getId() {
		return this.id;
	}

	public void setId(DCopSubdelegacionPK id) {
		this.id = id;
	}
	
	public String getCveOficina() {
		return this.cveOficina;
	}

	public void setCveOficina(String cveOficina) {
		this.cveOficina = cveOficina;
	}

	public String getDescOficina() {
		return this.descOficina;
	}

	public void setDescOficina(String descOficina) {
		this.descOficina = descOficina;
	}

	public String getDescSubdelegacion() {
		return this.descSubdelegacion;
	}

	public void setDescSubdelegacion(String descSubdelegacion) {
		this.descSubdelegacion = descSubdelegacion;
	}

	public String getDomOficina() {
		return this.domOficina;
	}

	public void setDomOficina(String domOficina) {
		this.domOficina = domOficina;
	}

	public String getDomSubdelegado() {
		return this.domSubdelegado;
	}

	public void setDomSubdelegado(String domSubdelegado) {
		this.domSubdelegado = domSubdelegado;
	}

	public String getJefeOfi() {
		return this.jefeOfi;
	}

	public void setJefeOfi(String jefeOfi) {
		this.jefeOfi = jefeOfi;
	}

	public String getJefeRfc() {
		return this.jefeRfc;
	}

	public void setJefeRfc(String jefeRfc) {
		this.jefeRfc = jefeRfc;
	}

	public String getNomSubdelegado() {
		return this.nomSubdelegado;
	}

	public void setNomSubdelegado(String nomSubdelegado) {
		this.nomSubdelegado = nomSubdelegado;
	}

	public Long getNumOficina() {
		return this.numOficina;
	}

	public void setNumOficina(Long numOficina) {
		this.numOficina = numOficina;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/*
	public DCopPatrone getDCopPatrone() {
		return this.DCopPatrone;
	}

	public void setDCopPatrone(DCopPatrone DCopPatrone) {
		this.DCopPatrone = DCopPatrone;
	}
*/
	public DCopDelegacion getdCopDelegacion() {
		return dCopDelegacion;
	}

	public void setdCopDelegacion(DCopDelegacion dCopDelegacion) {
		this.dCopDelegacion = dCopDelegacion;
	}
	
	
}