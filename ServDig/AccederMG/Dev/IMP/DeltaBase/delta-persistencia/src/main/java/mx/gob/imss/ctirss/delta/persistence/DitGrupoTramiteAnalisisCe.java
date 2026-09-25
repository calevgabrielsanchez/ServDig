package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The persistent class for the DIT_GRUPO_TRAMITE_ANALISIS_CE database table.
 * 
 */
@Entity
@Table(name = "DIT_GRUPO_TRAMITE_ANALISIS_CE")
public class DitGrupoTramiteAnalisisCe implements Serializable {

	private static final long serialVersionUID = -1630066584423711154L;

	@EmbeddedId
	private DitGrupoTramiteAnalisisCePK id;

	public DitGrupoTramiteAnalisisCePK getId() {
		return id;
	}

	public void setId(DitGrupoTramiteAnalisisCePK id) {
		this.id = id;
	}

}
