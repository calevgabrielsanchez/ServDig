package mx.gob.imss.ctirss.clasificador.model.business;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import mx.gob.imss.ctirss.clasificador.model.business.Equivalencia;
import mx.gob.imss.ctirss.clasificador.model.business.FraccionId;
import mx.gob.imss.ctirss.clasificador.model.business.Grupo;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@Table(name = "APC_FRACCION")
@JsonIgnoreProperties({"apcGrupo", "fraccionesAnteriores", "fraccionesNuevas"})
public class Fraccion implements Serializable {
  private FraccionId id;
  
  private Grupo apcGrupo;
  
  private String nomActividad;
  
  private String desActividad;
  
  private String cveClase;
  
  private BigDecimal numPrimaMedia;
  
  private Boolean indActivo;
  
  private Set<Equivalencia> fraccionesAnteriores = new HashSet<Equivalencia>(0);
  
  private Set<Equivalencia> fraccionesNuevas = new HashSet<Equivalencia>(0);
  
  private String desFraccion;
  
  public Fraccion() {}
  
  public Fraccion(int cveFraccion, int cveGrupo, int cveDivision, String nomActividad, String desActividad, String cveClase) {
    FraccionId id = new FraccionId(cveFraccion, cveGrupo, cveDivision);
    this.id = id;
    this.nomActividad = nomActividad;
    this.desActividad = desActividad;
    this.cveClase = cveClase;
  }
  
  public Fraccion(FraccionId id, Grupo apcGrupo) {
    this.id = id;
    this.apcGrupo = apcGrupo;
  }
  
  public Fraccion(FraccionId id, Grupo apcGrupo, String nomActividad, String desActividad, String cveClase, BigDecimal numPrimaMedia, Boolean indActivo, Set<Equivalencia> fraccionesAnteriores, Set<Equivalencia> fraccionesNuevas, String desFraccion) {
    this.id = id;
    this.apcGrupo = apcGrupo;
    this.nomActividad = nomActividad;
    this.desActividad = desActividad;
    this.cveClase = cveClase;
    this.numPrimaMedia = numPrimaMedia;
    this.indActivo = indActivo;
    this.fraccionesAnteriores = fraccionesAnteriores;
    this.fraccionesNuevas = fraccionesNuevas;
    this.desFraccion = desFraccion;
  }
  
  @EmbeddedId
  @AttributeOverrides({@AttributeOverride(name = "cveFraccion", column = @Column(name = "CVE_FRACCION", nullable = false)), @AttributeOverride(name = "cveGrupo", column = @Column(name = "CVE_GRUPO", nullable = false)), @AttributeOverride(name = "cveDivision", column = @Column(name = "CVE_DIVISION", nullable = false))})
  public FraccionId getId() {
    return this.id;
  }
  
  public void setId(FraccionId id) {
    this.id = id;
  }
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns({@JoinColumn(name = "CVE_GRUPO", referencedColumnName = "CVE_GRUPO", nullable = false, insertable = false, updatable = false), @JoinColumn(name = "CVE_DIVISION", referencedColumnName = "CVE_DIVISION", nullable = false, insertable = false, updatable = false)})
  public Grupo getApcGrupo() {
    return this.apcGrupo;
  }
  
  public void setApcGrupo(Grupo apcGrupo) {
    this.apcGrupo = apcGrupo;
  }
  
  @Column(name = "NOM_ACTIVIDAD", length = 200)
  public String getNomActividad() {
    return this.nomActividad;
  }
  
  public void setNomActividad(String nomActividad) {
    this.nomActividad = nomActividad;
  }
  
  @Column(name = "DES_ACTIVIDAD", length = 200)
  public String getDesActividad() {
    return this.desActividad;
  }
  
  public void setDesActividad(String desActividad) {
    this.desActividad = desActividad;
  }
  
  @Column(name = "CVE_CLASE", length = 2)
  public String getCveClase() {
    return this.cveClase;
  }
  
  public void setCveClase(String cveClase) {
    this.cveClase = cveClase;
  }
  
  @Column(name = "NUM_PRIMA_MEDIA", precision = 11, scale = 5)
  public BigDecimal getNumPrimaMedia() {
    return this.numPrimaMedia;
  }
  
  public void setNumPrimaMedia(BigDecimal numPrimaMedia) {
    this.numPrimaMedia = numPrimaMedia;
  }
  
  @Column(name = "IND_ACTIVO")
  public Boolean getIndActivo() {
    return this.indActivo;
  }
  
  public void setIndActivo(Boolean indActivo) {
    this.indActivo = indActivo;
  }
  
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "fraccionAnterior")
  public Set<Equivalencia> getFraccionesAnteriores() {
    return this.fraccionesAnteriores;
  }
  
  public void setFraccionesAnteriores(Set<Equivalencia> fraccionesAnteriores) {
    this.fraccionesAnteriores = fraccionesAnteriores;
  }
  
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "fraccionNueva")
  public Set<Equivalencia> getFraccionesNuevas() {
    return this.fraccionesNuevas;
  }
  
  public void setFraccionesNuevas(Set<Equivalencia> fraccionesNuevas) {
    this.fraccionesNuevas = fraccionesNuevas;
  }
  
  @Column(name = "DES_FRACCION", length = 7)
  public String getDesFraccion() {
    return this.desFraccion;
  }
  
  public void setDesFraccion(String desFraccion) {
    this.desFraccion = desFraccion;
  }
}
