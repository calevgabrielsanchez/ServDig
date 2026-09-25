package mx.gob.imss.ctirss.clasificador.model.business;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import mx.gob.imss.ctirss.clasificador.model.business.Grupo;

@Entity
@Table(name = "APC_DIVISION")
public class Division implements Serializable {
  private Integer cveDivision;
  
  private String nomDivision;
  
  private Boolean indActivo;
  
  private Set<Grupo> apcGrupos = new HashSet<Grupo>(0);
  
  public Division() {}
  
  public Division(int cveDivision) {
    this.cveDivision = Integer.valueOf(cveDivision);
  }
  
  public Division(int cveDivision, String nomDivision) {
    this.cveDivision = Integer.valueOf(cveDivision);
    this.nomDivision = nomDivision;
  }
  
  public Division(Integer cveDivision, String nomDivision, Boolean indActivo, Set<Grupo> apcGrupos) {
    this.cveDivision = cveDivision;
    this.nomDivision = nomDivision;
    this.indActivo = indActivo;
    this.apcGrupos = apcGrupos;
  }
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "CVE_DIVISION", unique = true, nullable = false)
  public Integer getCveDivision() {
    return this.cveDivision;
  }
  
  public void setCveDivision(Integer cveDivision) {
    this.cveDivision = cveDivision;
  }
  
  @Column(name = "NOM_DIVISION", length = 200)
  public String getNomDivision() {
    return this.nomDivision;
  }
  
  public void setNomDivision(String nomDivision) {
    this.nomDivision = nomDivision;
  }
  
  @Column(name = "IND_ACTIVO")
  public Boolean getIndActivo() {
    return this.indActivo;
  }
  
  public void setIndActivo(Boolean indActivo) {
    this.indActivo = indActivo;
  }
  
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "apcDivision")
  public Set<Grupo> getApcGrupos() {
    return this.apcGrupos;
  }
  
  public void setApcGrupos(Set<Grupo> apcGrupos) {
    this.apcGrupos = apcGrupos;
  }
}

