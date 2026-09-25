package mx.gob.imss.ctirss.clasificador.model.controller;

import mx.gob.imss.ctirss.clasificador.model.controller.AbstractDataTableSend;

public class FraccionDataTableSend
  extends AbstractDataTableSend
{
  private int cveGrupo;
  private int cveDivision;
  private boolean vigente;
  
  public int getCveGrupo() {
     return this.cveGrupo;
  }



  
  public void setCveGrupo(int cveGrupo) {
     this.cveGrupo = cveGrupo;
  }



  
  public int getCveDivision() {
     return this.cveDivision;
  }



  
  public void setCveDivision(int cveDivision) {
     this.cveDivision = cveDivision;
  }



  
  public boolean isVigente() {
     return this.vigente;
  }



  
  public void setVigente(boolean vigente) {
     this.vigente = vigente;
  }
}

