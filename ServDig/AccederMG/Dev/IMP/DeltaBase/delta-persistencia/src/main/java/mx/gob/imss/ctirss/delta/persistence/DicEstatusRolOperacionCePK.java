package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * The primary key class for the DIC_ESTATUS_ROL_OPERACION_CE database table.
 * 
 */
@Embeddable
public class DicEstatusRolOperacionCePK implements Serializable {

	/** Serial version */
	private static final long serialVersionUID = -4273795295661807274L;

	@ManyToOne
	@JoinColumn(name = "CVE_ID_ESTATUS_ANALISIS", unique = true, nullable = false)
	private DicEstatusAnalisisCe dicEstatusAnalisisCe;

	@ManyToOne
	@JoinColumn(name = "CVE_ID_ROL_CE", unique = true, nullable = false)
	private DicRolCe dicRolCe;

	@ManyToOne
	@JoinColumn(name = "CVE_ID_TIPO_OPERACION_CE", unique = true, nullable = false)
	private DicTipoOperacionCe dicTipoOperacionCe;

	public DicEstatusAnalisisCe getDicEstatusAnalisisCe() {
		return dicEstatusAnalisisCe;
	}

	public void setDicEstatusAnalisisCe(
			DicEstatusAnalisisCe dicEstatusAnalisisCe) {
		this.dicEstatusAnalisisCe = dicEstatusAnalisisCe;
	}

	public DicRolCe getDicRolCe() {
		return dicRolCe;
	}

	public void setDicRolCe(DicRolCe dicRolCe) {
		this.dicRolCe = dicRolCe;
	}

	public DicTipoOperacionCe getDicTipoOperacionCe() {
		return dicTipoOperacionCe;
	}

	public void setDicTipoOperacionCe(DicTipoOperacionCe dicTipoOperacionCe) {
		this.dicTipoOperacionCe = dicTipoOperacionCe;
	}

    public boolean equals(Object o) {
        if (o == null) { return false; }
        if (o == this) { return true; }
        if (this.getClass() != o.getClass()) { return false; }
        DicEstatusRolOperacionCePK othr = (DicEstatusRolOperacionCePK)o;
        return new EqualsBuilder()
                .append(this.dicEstatusAnalisisCe.getCveIdEstatusAnalisis(),
                        othr.dicEstatusAnalisisCe.getCveIdEstatusAnalisis())
                .append(this.dicRolCe.getCveIdRolCe(),
                        othr.dicRolCe.getCveIdRolCe())
                .append(this.dicTipoOperacionCe.getCveIdTipoOperacionCe(),
                        othr.dicTipoOperacionCe.getCveIdTipoOperacionCe())
                .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(17, 23)
                .append(dicEstatusAnalisisCe.getCveIdEstatusAnalisis())
                .append(dicRolCe.getCveIdRolCe())
                .append(dicTipoOperacionCe.getCveIdTipoOperacionCe())
                .hashCode();
    }

    public String toString() {
        return new ToStringBuilder(this)
                .append("dicEstatusAnalisisCe", dicEstatusAnalisisCe.getDesCausasAnalisis())
                .append("dicRolCe", dicRolCe.getDesRolCe())
                .append("dicTipoOperacionCe", dicTipoOperacionCe.getDesTipoOperacionCe())
                .toString();
    }

}
