package mx.gob.imss.ctirss.delta.model.asegurado.cda;

import java.io.Serializable;
import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.CuentaIndividualAsegurado;

/**
 * Modelo que representa la cuenta individual de un tramite correcion
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CuentaIndividualConsulta implements Serializable {


    private CuentaIndividualAsegurado consultaPrevia;
    private CuentaIndividualAsegurado consultaActual;
    
    public CuentaIndividualAsegurado getConsultaPrevia() {
        return consultaPrevia;
    }

    public void setConsultaPrevia(CuentaIndividualAsegurado consultaPrevia) {
        this.consultaPrevia = consultaPrevia;
    }

    public CuentaIndividualAsegurado getConsultaActual() {
        return consultaActual;
    }

    public void SetConsultaActual(CuentaIndividualAsegurado consultaActual) {
        this.consultaActual = consultaActual;
    }
  

  
}
