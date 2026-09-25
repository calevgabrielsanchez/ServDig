/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.Page;

/**
 *
 * @author antonio
 */
public class PageCuentaIndividualPeriodo extends Page<CuentaIndividualPeriodo>{
  private @Getter @Setter List<CuentaIndividualCorreccion> nuevos = new ArrayList<CuentaIndividualCorreccion>();
  private @Getter @Setter List<CuentaIndividualCorreccion> modificados = new ArrayList<CuentaIndividualCorreccion>();
  private @Getter @Setter List<CuentaIndividualCorreccion> eliminados = new ArrayList<CuentaIndividualCorreccion>();
  private @Getter @Setter List<CuentaIndividualCorreccion> incluidos = new ArrayList<CuentaIndividualCorreccion>();
  private @Getter @Setter CuentaIndividualRegistroPatronal registroPatronal = new CuentaIndividualRegistroPatronal();
  
}
