package mx.gob.imss.ctirss.clasificador.model.business;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class GrupoId implements Serializable {
  private int cveGrupo;
  
  private int cveDivision;
  
  public GrupoId() {}
  
  public GrupoId(int cveGrupo, int cveDivision) {
    this.cveGrupo = cveGrupo;
    this.cveDivision = cveDivision;
  }
  
  @Column(name = "CVE_GRUPO", nullable = false)
  public int getCveGrupo() {
    return this.cveGrupo;
  }
  
  public void setCveGrupo(int cveGrupo) {
    this.cveGrupo = cveGrupo;
  }
  
  @Column(name = "CVE_DIVISION", nullable = false)
  public int getCveDivision() {
    return this.cveDivision;
  }
  
  public void setCveDivision(int cveDivision) {
    this.cveDivision = cveDivision;
  }
  
  public boolean equals(Object other) {
    if (this == other)
      return true; 
    if (other == null)
      return false; 
    if (!(other instanceof mx.gob.imss.ctirss.clasificador.model.business.GrupoId))
      return false; 
    mx.gob.imss.ctirss.clasificador.model.business.GrupoId castOther = (mx.gob.imss.ctirss.clasificador.model.business.GrupoId)other;
    return (getCveGrupo() == castOther.getCveGrupo() && getCveDivision() == castOther.getCveDivision());
  }
  
  public int hashCode() {
    int result = 17;
    result = 37 * result + getCveGrupo();
    result = 37 * result + getCveDivision();
    return result;
  }
}
