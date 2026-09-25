/**
 * Proyecto: IMSS - SATIC
 *
 * Archivo: InformacionPatronVO.java
 *
 * Creado: 18/08/2008
 *
 * Derechos Reservados de Copia (c) - TCS / Instituto Mexicano del Seguro Social - 2008
 */

package mx.imss.ctirss.integracion.vo;

/**
 * Proyecto: Sistema de Afiliacion de Trabajadores de la Industria de la Construccion. <BR>
 * Objetivo: Vo para intercambio de datos con SINDO
 * Fecha de creacion: Aug 18, 2008 <BR>
 * Copyright (c) IMSS
 * <BR>
 *
 * @author TCS
 * @version 1.0
 */

public class InformacionPatronVO {

    private String registroPatronal;
    private String modalidad;
    private String digitoVerificador;
    /**
     * Metodo que regresa el valor relacionado al atributo digitoVerificador.
     * @return El valor relacionado al atributo digitoVerificador.
     */
    public String getDigitoVerificador() {
        return digitoVerificador;
    }
    /**
     * Metodo que establece el valor del atributo digitoVerificador.
     * @param digitoVerificador Valor a establecer en el atributo digitoVerificador.
     */
    public void setDigitoVerificador(String digitoVerificador) {
        this.digitoVerificador = digitoVerificador;
    }
    /**
     * Metodo que regresa el valor relacionado al atributo modalidad.
     * @return El valor relacionado al atributo modalidad.
     */
    public String getModalidad() {
        return modalidad;
    }
    /**
     * Metodo que establece el valor del atributo modalidad.
     * @param modalidad Valor a establecer en el atributo modalidad.
     */
    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }
    /**
     * Metodo que regresa el valor relacionado al atributo registroPatronal.
     * @return El valor relacionado al atributo registroPatronal.
     */
    public String getRegPatronal() {
        return registroPatronal;
    }
    /**
     * Metodo que establece el valor del atributo registroPatronal.
     * @param registroPatronal Valor a establecer en el atributo registroPatronal.
     */
    public void setRegPatronal(String registroPatronal) {
        this.registroPatronal = registroPatronal;
    }


}
