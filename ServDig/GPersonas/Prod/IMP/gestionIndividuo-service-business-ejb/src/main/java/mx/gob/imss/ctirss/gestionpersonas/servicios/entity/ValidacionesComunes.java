package mx.gob.imss.ctirss.gestionpersonas.servicios.entity;

import mx.gob.imss.ctirss.delta.framework.exceptions.NumeroMaximoResultadosSuperadoException;

public class ValidacionesComunes {
    private static final int MAXIMO_RESULTADOS_MOSTRAR = 1000;

    public static void validaMaximoResultadosConsulta(Integer numResultados) throws NumeroMaximoResultadosSuperadoException {

        System.out.println("ValidacionesComunes.validaMaximoResultadosConsulta. Cantidad de resultados a validar: [" + numResultados + "]");

        if (numResultados >= MAXIMO_RESULTADOS_MOSTRAR) {
            throw new NumeroMaximoResultadosSuperadoException();
        }

    }
}
