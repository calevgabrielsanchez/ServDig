/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual;

import lombok.Getter;
import lombok.Setter;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.BaseModel;
import mx.gob.imss.ctirss.delta.model.enums.TipoRegularizacionPeriodoEnum;

/**
 *
 * @author antonio
 */
public class CuentaIndividualMovimiento extends BaseModel {
  private @Getter @Setter CuentaIndividualNss cuentaIndividualNss;
  private @Getter @Setter CuentaIndividualPeriodo cuentaIndividualPeriodo;
  private @Getter @Setter TipoRegularizacionPeriodoEnum tipoRegularizacionPeriodo;
}
