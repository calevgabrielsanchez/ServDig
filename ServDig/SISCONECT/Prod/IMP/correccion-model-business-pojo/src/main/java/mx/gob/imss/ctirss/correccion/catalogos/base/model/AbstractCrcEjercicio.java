package mx.gob.imss.ctirss.correccion.catalogos.base.model;

import java.util.Set;

import javax.persistence.*;

import mx.gob.imss.ctirss.correccion.base.model.AbstractCrcPatronPK;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcTrabajadores;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.model.CgcCatTipoOrigen;
import mx.gob.imss.ctirss.correccion.model.CrtAnexosolcorrpat;

/**
 * The persistent class for the CRT_RP_EJERCICIO database table.
 * 
 */
@MappedSuperclass
@IdClass(AbstractCrcEjercicioPK.class) 
public class AbstractCrcEjercicio extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CVE_EJERCICIO")
	private Long cveEjercicio;

	@Id
	@Column(name = "CVE_ANEXOSOLCORRPAT")
	private Long cveAcexoCorrPat;

	
	public Long getCveEjercicio() {
		return cveEjercicio;
	}

	public void setCveEjercicio(Long cveEjercicio) {
		this.cveEjercicio = cveEjercicio;
	}

	public Long getCveAcexoCorrPat() {
		return cveAcexoCorrPat;
	}

	public void setCveAcexoCorrPat(Long cveAcexoCorrPat) {
		this.cveAcexoCorrPat = cveAcexoCorrPat;
	}
	
	


}