package mx.gob.imss.ctirss.clasificador.model.business;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EquivalenciaId implements Serializable {
  private int cveFraccionAnt;
  
  private int cveGrupoAnt;
  
  private int cveDivisionAnt;
  
  private int cveFraccionNueva;
  
  private int cveGrupoNueva;
  
  private int cveDivisionNueva;
  
  public EquivalenciaId() {}
  
  public EquivalenciaId(int cveFraccionAnt, int cveGrupoAnt, int cveDivisionAnt, int cveFraccionNueva, int cveGrupoNueva, int cveDivisionNueva) {
    this.cveFraccionAnt = cveFraccionAnt;
    this.cveGrupoAnt = cveGrupoAnt;
    this.cveDivisionAnt = cveDivisionAnt;
    this.cveFraccionNueva = cveFraccionNueva;
    this.cveGrupoNueva = cveGrupoNueva;
    this.cveDivisionNueva = cveDivisionNueva;
  }
  
  @Column(name = "CVE_FRACCION_ANT", nullable = false)
  public int getCveFraccionAnt() {
    return this.cveFraccionAnt;
  }
  
  public void setCveFraccionAnt(int cveFraccionAnt) {
    this.cveFraccionAnt = cveFraccionAnt;
  }
  
  @Column(name = "CVE_GRUPO_ANT", nullable = false)
  public int getCveGrupoAnt() {
    return this.cveGrupoAnt;
  }
  
  public void setCveGrupoAnt(int cveGrupoAnt) {
    this.cveGrupoAnt = cveGrupoAnt;
  }
  
  @Column(name = "CVE_DIVISION_ANT", nullable = false)
  public int getCveDivisionAnt() {
    return this.cveDivisionAnt;
  }
  
  public void setCveDivisionAnt(int cveDivisionAnt) {
    this.cveDivisionAnt = cveDivisionAnt;
  }
  
  @Column(name = "CVE_FRACCION_NUEVA", nullable = false)
  public int getCveFraccionNueva() {
    return this.cveFraccionNueva;
  }
  
  public void setCveFraccionNueva(int cveFraccionNueva) {
    this.cveFraccionNueva = cveFraccionNueva;
  }
  
  @Column(name = "CVE_GRUPO_NUEVA", nullable = false)
  public int getCveGrupoNueva() {
    return this.cveGrupoNueva;
  }
  
  public void setCveGrupoNueva(int cveGrupoNueva) {
    this.cveGrupoNueva = cveGrupoNueva;
  }
  
  @Column(name = "CVE_DIVISION_NUEVA", nullable = false)
  public int getCveDivisionNueva() {
    return this.cveDivisionNueva;
  }
  
  public void setCveDivisionNueva(int cveDivisionNueva) {
    this.cveDivisionNueva = cveDivisionNueva;
  }
  
  public boolean equals(Object other) {
    if (this == other)
      return true; 
    if (other == null)
      return false; 
    if (!(other instanceof mx.gob.imss.ctirss.clasificador.model.business.EquivalenciaId))
      return false; 
    mx.gob.imss.ctirss.clasificador.model.business.EquivalenciaId castOther = (mx.gob.imss.ctirss.clasificador.model.business.EquivalenciaId)other;
    return (getCveFraccionAnt() == castOther.getCveFraccionAnt() && getCveGrupoAnt() == castOther.getCveGrupoAnt() && getCveDivisionAnt() == castOther.getCveDivisionAnt() && getCveFraccionNueva() == castOther.getCveFraccionNueva() && getCveGrupoNueva() == castOther.getCveGrupoNueva() && getCveDivisionNueva() == castOther.getCveDivisionNueva());
  }
  
  public int hashCode() {
    int result = 17;
    result = 37 * result + getCveFraccionAnt();
    result = 37 * result + getCveGrupoAnt();
    result = 37 * result + getCveDivisionAnt();
    result = 37 * result + getCveFraccionNueva();
    result = 37 * result + getCveGrupoNueva();
    result = 37 * result + getCveDivisionNueva();
    return result;
  }
}
