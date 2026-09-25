/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:TipoMedioContacto.java
 *  @Paquete:mx.gob.imss.ctirss.delta.model.gestion.medio.contacto
 *  @Fecha:03/05/2012
 */
package mx.gob.imss.ctirss.delta.model.gestion.medio.contacto;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * @author Cesar Garcia Mauricio
 * @author Lucio Duran Silva
 * 
 */
public class TipoMedioContacto extends AbstractModel {
	
	
	public static final Long TIPO_CORREO_ELECTRONICO  = new Long(1);
	
	public static final Long TIPO_TELEFONO_FIJO  = new Long(2);
	
	public static final Long TIPO_TELEFONO_MOVIL = new Long(3);
	
	public static final Long TIPO_FACEBOOK = new Long(4);
	
	public static final Long TIPO_TWITTER = new Long(5);

    public Long getIdTipoMedioContacto() {
        return idTipoMedioContacto;
    }

    public void setIdTipoMedioContacto(Long idTipoMedioContacto) {
        this.idTipoMedioContacto = idTipoMedioContacto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    private static final long serialVersionUID = 1L;

    private Long idTipoMedioContacto;
    private String descripcion;

}
