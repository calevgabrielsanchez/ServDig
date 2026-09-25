package mx.gob.imss.ctirss.delta.model.gestion.individuo.vo;

public interface CalificacionPersona {

    String CALIFICACION_1_VALIDADO_RENAPO = "Validado por RENAPO";
    String CALIFICACION_2_VALIDADO_SAT = "Validado por SAT";
    String CALIFICACION_3_VALIDADO_IMSS = "Validado por IMSS";
    String CALIFICACION_4_NO_VALIDADO = "No validado";
    
    String CALIFICACION_5_ENCONTRADO_IMSS = "Entontrado en IMSS"; // --> se econtro en el imss asi que no entra como tramite valido en la solicitud
    
    Integer VALIDADO_RENAPO = 1;
    Integer VALIDADO_SAT = 2;
    Integer VALIDADO_IMSS = 3;
    Integer NO_VALIDADO = 4;
    Integer ENCONTRADO_IMSS = 5;

}

