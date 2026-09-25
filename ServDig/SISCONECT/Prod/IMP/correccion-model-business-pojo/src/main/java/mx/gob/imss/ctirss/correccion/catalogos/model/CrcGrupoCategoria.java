package mx.gob.imss.ctirss.correccion.catalogos.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import mx.gob.imss.ctirss.correccion.catalogos.base.model.AbstractCrcGrupoCategoria;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;

@Entity
@Table(name="CRC_GRUPOCATEGORIA")
@OnSearchLlavePrimaria		(atributos={"cveGrupoCategoria"})
public class CrcGrupoCategoria extends AbstractCrcGrupoCategoria{

	@Transient
	private String cveSubdelegcionOficial;

	/**
	 * Retorna el valor cveSubdelegcionOficial
	 * @return  cveSubdelegcionOficial
	 */
	@Transient
	public String getCveSubdelegcionOficial() {
		return cveSubdelegcionOficial;
	}

	/**
	 * Asigna el valor del cveSubdelegcionOficial al atributo cveSubdelegcionOficial
	 * @param cveSubdelegcionOficial 
	 */
	public void setCveSubdelegcionOficial(String cveSubdelegcionOficial) {
		this.cveSubdelegcionOficial = cveSubdelegcionOficial;
	}
	
	

}
