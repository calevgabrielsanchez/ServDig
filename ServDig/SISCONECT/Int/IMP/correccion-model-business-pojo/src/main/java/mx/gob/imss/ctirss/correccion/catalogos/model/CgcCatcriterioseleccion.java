package mx.gob.imss.ctirss.correccion.catalogos.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import mx.gob.imss.ctirss.correccion.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.model.CgtCatCriterioSeleccion;


/**
 * The persistent class for the CGC_CATCRITERIOSELECCION database table.
 * Esta Entidad tiene varios duplicados {@link CgtCatCriterioeleccion} el cual esta mal escrito y {@link CgtCatCriterioSeleccion} existen 3 mapeos contando
 * esta clase. 
 */
@Entity
@Table(name="CGC_CATCRITERIOSELECCION")
@OnSearchLlavePrimaria			(atributos={"idCriterioseleccion"})
@ComponentComboCampoDescripcion (atributo="descCriterioseleccion")
public class CgcCatcriterioseleccion extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_CRITERIOSELECCION")
	private long idCriterioseleccion;

	@Column(name="DESC_CRITERIOSELECCION")
	private String descCriterioseleccion;

	@Column(name="ID_ORIGEN")
	private Long idOrigen;

	@Column(name="ID_TIPO")
	private Long idTipo;
	
	@Column(name="FEC_FECHAREG")
	private Date fecFechaReg;
	
	@Column(name="CVE_USUARIO")
	private String cveUsuario;
	
	@Transient
	private String descTipo;
	@Transient
	private String descOrigen;	

	@Transient
   	public String getDescTipo() {
		return descTipo;
	}

	public void setDescTipo(String descTipo) {
		this.descTipo = descTipo;
	}

	@Transient
	public String getDescOrigen() {
		return descOrigen;
	}

	public void setDescOrigen(String descOrigen) {
		this.descOrigen = descOrigen;
	}



	public CgcCatcriterioseleccion() {
		super();
		// TODO Auto-generated constructor stub
	}



	public CgcCatcriterioseleccion(long idCriterioseleccion,
			String descCriterioseleccion, Long idOrigen, Long idTipo) {
		super();
		this.idCriterioseleccion = idCriterioseleccion;
		this.descCriterioseleccion = descCriterioseleccion;
		this.idOrigen = idOrigen;
		this.idTipo = idTipo;
	}



	public long getIdCriterioseleccion() {
		return this.idCriterioseleccion;
	}

	public void setIdCriterioseleccion(long idCriterioseleccion) {
		this.idCriterioseleccion = idCriterioseleccion;
	}

	public String getDescCriterioseleccion() {
		return this.descCriterioseleccion;
	}

	public void setDescCriterioseleccion(String descCriterioseleccion) {
		this.descCriterioseleccion = descCriterioseleccion;
	}

	public Long getIdOrigen() {
		return this.idOrigen;
	}

	public void setIdOrigen(Long idOrigen) {
		this.idOrigen = idOrigen;
	}

	public Long getIdTipo() {
		return this.idTipo;
	}

	public void setIdTipo(Long idTipo) {
		this.idTipo = idTipo;
	}

	public Date getFecFechaReg() {
		return fecFechaReg;
	}

	public void setFecFechaReg(Date fecFechaReg) {
		this.fecFechaReg = fecFechaReg;
	}

	public String getCveUsuario() {
		return cveUsuario;
	}

	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}
	
}