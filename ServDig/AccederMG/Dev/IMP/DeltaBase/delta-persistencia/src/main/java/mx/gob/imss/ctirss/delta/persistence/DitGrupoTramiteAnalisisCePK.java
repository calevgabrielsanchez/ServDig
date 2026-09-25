package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The primary key class for the DIT_GRUPO_TRAMITE_ANALISIS_CE database table.
 * 
 */
@Embeddable
public class DitGrupoTramiteAnalisisCePK implements Serializable {

	private static final long serialVersionUID = 3314834105846212413L;

	@Column(name = "CVE_ID_GRUPO_ANALISIS_CE")
	private long cveIdGrupoAnalisisCe;

	@Column(name = "CVE_ID_MODULO")
	private long cveIdModulo;

	@Column(name = "CVE_ID_TIPO_TRAMITE")
	private long cveIdTipoTramite;

	public long getCveIdGrupoAnalisisCe() {
		return cveIdGrupoAnalisisCe;
	}

	public void setCveIdGrupoAnalisisCe(long cveIdGrupoAnalisisCe) {
		this.cveIdGrupoAnalisisCe = cveIdGrupoAnalisisCe;
	}

	public long getCveIdModulo() {
		return cveIdModulo;
	}

	public void setCveIdModulo(long cveIdModulo) {
		this.cveIdModulo = cveIdModulo;
	}

	public long getCveIdTipoTramite() {
		return cveIdTipoTramite;
	}

	public void setCveIdTipoTramite(long cveIdTipoTramite) {
		this.cveIdTipoTramite = cveIdTipoTramite;
	}

	/*
	 * private DicGrupoAnalisisCe dicGrupoAnalisisCe; private List<DicModulo>
	 * dicTipoTramites;
	 * 
	 * @Id
	 * 
	 * @ManyToOne(cascade=CascadeType.MERGE)
	 * 
	 * @JoinColumn(name="CVE_ID_GRUPO_ANALISIS_CE") public DicGrupoAnalisisCe
	 * getDicGrupoAnalisisCe() { return dicGrupoAnalisisCe; }
	 * 
	 * 
	 * public void setDicGrupoAnalisisCe(DicGrupoAnalisisCe dicGrupoAnalisisCe)
	 * { this.dicGrupoAnalisisCe = dicGrupoAnalisisCe; }
	 * 
	 * @Id
	 * 
	 * @OneToMany
	 * 
	 * @JoinTable( name="DIC_MODULO_TIPO_TRAMITE" , joinColumns={
	 * 
	 * @JoinColumn(name="CVE_ID_TIPO_TRAMITE"),
	 * 
	 * @JoinColumn(name="CVE_ID_MODULO") } , inverseJoinColumns={
	 * 
	 * @JoinColumn(name="CVE_ID_MODULO") } ) public List<DicModulo>
	 * getDicTipoTramites() { return dicTipoTramites; }
	 * 
	 * 
	 * public void setDicTipoTramites(List<DicModulo> dicTipoTramites) {
	 * this.dicTipoTramites = dicTipoTramites; }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * /*@ManyToOne
	 * 
	 * @JoinColumn(name = "CVE_ID_GRUPO_ANALISIS_CE") private DicGrupoAnalisisCe
	 * dicGrupoAnalisisCe;
	 * 
	 * @ManyToMany
	 * 
	 * @JoinTable( name="DIC_MODULO_TIPO_TRAMITE" , joinColumns={
	 * 
	 * @JoinColumn(name="CVE_ID_TIPO_TRAMITE") } , inverseJoinColumns={
	 * 
	 * @JoinColumn(name="CVE_ID_MODULO") } ) private List<DicModulo> dicModulos;
	 * 
	 * public DicGrupoAnalisisCe getDicGrupoAnalisisCe() { return
	 * dicGrupoAnalisisCe; }
	 * 
	 * public void setDicGrupoAnalisisCe(DicGrupoAnalisisCe dicGrupoAnalisisCe)
	 * { this.dicGrupoAnalisisCe = dicGrupoAnalisisCe; }
	 * 
	 * public List<DicModulo> getDicModulos() { return dicModulos; }
	 * 
	 * public void setDicModulos(List<DicModulo> dicModulos) { this.dicModulos =
	 * dicModulos; }
	 */

    public boolean equals(Object o) {
        if (o == null) { return false; }
        if (o == this) { return true; }
        if (this.getClass() != o.getClass()) { return false; }
        DitGrupoTramiteAnalisisCePK other = (DitGrupoTramiteAnalisisCePK)o;
        return new EqualsBuilder()
                .append(this.cveIdGrupoAnalisisCe, other.cveIdGrupoAnalisisCe)
                .append(this.cveIdModulo, other.cveIdModulo)
                .append(this.cveIdTipoTramite, other.cveIdTipoTramite)
                .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(7, 3)
                .append(cveIdGrupoAnalisisCe)
                .append(cveIdModulo)
                .append(cveIdTipoTramite)
                .hashCode();
    }

    public String toString() {
        return new ToStringBuilder(this)
                .append("cveIdGrupoAnalisisCe", cveIdGrupoAnalisisCe)
                .append("cveIdModulo", cveIdModulo)
                .append("cveIdTipoTramite", cveIdTipoTramite)
                .toString();
    }

}
