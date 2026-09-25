package mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.BaseModel;

/**
 * Modelo que representa la Cuenta Individual de un tramite correccion
 */


public class CuentaIndividual extends BaseModel{
    private @Getter @Setter String folioSolicitud;
    private @Getter @Setter List<String> listaTipoTramites;
    private @Getter @Setter List<CuentaIndividualNss> listaCuentaIndividualNssCertificador = new ArrayList<CuentaIndividualNss>();
    private @Getter @Setter List<CuentaIndividualNss> listaCuentaIndividualNssAsociado = new ArrayList<CuentaIndividualNss>();
    private @Getter @Setter List<CuentaIndividualNss> listaCuentaIndividualNssNoPertenece = new ArrayList<CuentaIndividualNss>();
    
    
  private @Getter @Setter Long cveIdCorreccionDatosAsegurado;  
  private @Getter @Setter List<CuentaIndividualNss> listaNssTipoCertificador = new ArrayList<CuentaIndividualNss>();
  private @Getter @Setter List<CuentaIndividualNss> listaNssTipoAsociadoAsegurado = new ArrayList<CuentaIndividualNss>();
  private @Getter @Setter List<CuentaIndividualNss> listaNssTipoNoCorrespondeAsegurado = new ArrayList<CuentaIndividualNss>();
}
