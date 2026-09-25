/**
 * 
 */
package mx.gob.imss.ctirss.delta.model.domicilio;

/**
 * 
 * @Cliente: Instituto Mexicano del Seguro Social
 * @Autor: Hugo Armando Mart-nez Cham-nica
 * @Proyecto: delta
 * @Archivo: TipoDomicilioEnum.java
 * @Paquete: mx.gob.imss.ctirss.delta.model.domicilio
 * @Fecha: 17:55:33
 */
public enum TipoDomicilioEnum {
    FISCAL(1L), CENTRO_TRABAJO(3L), PARTICULAR(4L);

    Long codigo;

    private TipoDomicilioEnum(Long valor) {
        codigo = valor;
    }

    public Long getCodigo() {
        return codigo;
    }

}
