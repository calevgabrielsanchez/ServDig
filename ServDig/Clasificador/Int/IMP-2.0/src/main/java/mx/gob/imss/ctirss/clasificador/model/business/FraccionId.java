 package mx.gob.imss.ctirss.clasificador.model.business;
 
 import java.io.Serializable;
 import javax.persistence.Column;
 import javax.persistence.Embeddable;
 
 
 
 
 
 @Embeddable
 public class FraccionId
   implements Serializable
 {
   private int cveFraccion;
   private int cveGrupo;
   private int cveDivision;
   
   public FraccionId() {}
   
   public FraccionId(int cveFraccion, int cveGrupo, int cveDivision) {
     this.cveFraccion = cveFraccion;
     this.cveGrupo = cveGrupo;
     this.cveDivision = cveDivision;
   }
   
   @Column(name = "CVE_FRACCION", nullable = false)
   public int getCveFraccion() {
     return this.cveFraccion;
   }
   
   public void setCveFraccion(int cveFraccion) {
     this.cveFraccion = cveFraccion;
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
     if (!(other instanceof mx.gob.imss.ctirss.clasificador.model.business.FraccionId))
       return false; 
     mx.gob.imss.ctirss.clasificador.model.business.FraccionId castOther = (mx.gob.imss.ctirss.clasificador.model.business.FraccionId)other;
     
     return (getCveFraccion() == castOther.getCveFraccion() && getCveGrupo() == castOther.getCveGrupo() && getCveDivision() == castOther.getCveDivision());
   }
 
 
   
   public int hashCode() {
     int result = 17;
     
     result = 37 * result + getCveFraccion();
     result = 37 * result + getCveGrupo();
     result = 37 * result + getCveDivision();
     return result;
   }
 }


