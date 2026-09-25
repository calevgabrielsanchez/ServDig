package mx.gob.imss.ctirss.clasificador.model.business;

import java.io.Serializable;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import mx.gob.imss.ctirss.clasificador.model.business.EquivalenciaId;
import mx.gob.imss.ctirss.clasificador.model.business.Fraccion;





@Entity
@Table(name = "SMC_FRACCION_EQUIVALENCIA")
public class Equivalencia
  implements Serializable
{
  private EquivalenciaId id;
  private Fraccion fraccionAnterior;
  private Fraccion fraccionNueva;
  
  public Equivalencia() {}
  
  public Equivalencia(EquivalenciaId id, Fraccion fraccionAnterior, Fraccion fraccionNueva) {
     this.id = id;
     this.fraccionAnterior = fraccionAnterior;
     this.fraccionNueva = fraccionNueva;
  }






  
  @EmbeddedId
  @AttributeOverrides({@AttributeOverride(name = "cveFraccionAnt", column = @Column(name = "CVE_FRACCION_ANT", nullable = false)), @AttributeOverride(name = "cveGrupoAnt", column = @Column(name = "CVE_GRUPO_ANT", nullable = false)), @AttributeOverride(name = "cveDivisionAnt", column = @Column(name = "CVE_DIVISION_ANT", nullable = false)), @AttributeOverride(name = "cveFraccionNueva", column = @Column(name = "CVE_FRACCION_NUEVA", nullable = false)), @AttributeOverride(name = "cveGrupoNueva", column = @Column(name = "CVE_GRUPO_NUEVA", nullable = false)), @AttributeOverride(name = "cveDivisionNueva", column = @Column(name = "CVE_DIVISION_NUEVA", nullable = false))})
  public EquivalenciaId getId() {
     return this.id;
  }
  
  public void setId(EquivalenciaId id) {
     this.id = id;
  }






  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns({@JoinColumn(name = "CVE_FRACCION_ANT", referencedColumnName = "CVE_FRACCION", nullable = false, insertable = false, updatable = false), @JoinColumn(name = "CVE_GRUPO_ANT", referencedColumnName = "CVE_GRUPO", nullable = false, insertable = false, updatable = false), @JoinColumn(name = "CVE_DIVISION_ANT", referencedColumnName = "CVE_DIVISION", nullable = false, insertable = false, updatable = false)})
  public Fraccion getFraccionAnterior() {
     return this.fraccionAnterior;
  }

  
  public void setFraccionAnterior(Fraccion fraccionAnterior) {
     this.fraccionAnterior = fraccionAnterior;
  }



  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns({@JoinColumn(name = "CVE_FRACCION_NUEVA", referencedColumnName = "CVE_FRACCION", nullable = false, insertable = false, updatable = false), @JoinColumn(name = "CVE_GRUPO_NUEVA", referencedColumnName = "CVE_GRUPO", nullable = false, insertable = false, updatable = false), @JoinColumn(name = "CVE_DIVISION_NUEVA", referencedColumnName = "CVE_DIVISION", nullable = false, insertable = false, updatable = false)})
  public Fraccion getFraccionNueva() {
     return this.fraccionNueva;
  }

  
  public void setFraccionNueva(Fraccion fraccionNueva) {
     this.fraccionNueva = fraccionNueva;
  }
}


