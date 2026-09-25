package mx.gob.imss.ctirss.correccion.web.controller.monitor;



public class EstatusCorrecciones {

	public static final Integer ESTATUS_DESCARGADO =1;
    public static final Integer ESTATUS_CARGADO =2;
    public static final Integer ESTATUS_COMPLETADO =3;
    public static final Integer ESTATUS_ERROR_CARGA =4;
    public static final Integer ESTATUS_SIN_OPERACION =5;
    public static final Integer ESTATUS_EN_PROCESO =6;
    
    private Integer id;
    private String descripcion;
    
    public EstatusCorrecciones(){
        setId(ESTATUS_SIN_OPERACION);
        setDescripcion(ConstantesCedulas.ANEXO_SIN_OPERACION);
    }
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
