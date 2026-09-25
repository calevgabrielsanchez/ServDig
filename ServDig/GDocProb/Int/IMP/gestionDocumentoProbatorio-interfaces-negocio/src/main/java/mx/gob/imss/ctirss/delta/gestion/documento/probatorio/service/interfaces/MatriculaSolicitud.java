package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.solicitud;

import javax.ejb.Remote;
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.DocumentoProbatorioException;

@Remote
public interface MatriculaSolicitud {
    String consultaMatricula(String curp) throws DocumentoProbatorioException;
}
