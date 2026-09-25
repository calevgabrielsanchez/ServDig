/**
 * Proyecto: IMSS - SATIC
 *
 * Archivo: IntegracionSindoService.java
 *
 * Creado: 18/08/2008
 *
 * Derechos Reservados de Copia (c) - TCS / Instituto Mexicano del Seguro Social - 2008
 */

package mx.gob.imss.ctirss.correccion.service;

import mx.gob.imss.ctirss.satic.integracion.InformacionPatronSalidaVO;


 
/**
 * Proyecto: Sistema de Afiliacion de Trabajadores de la Industria de la Construccion. <BR>
 * Objetivo: Servicio integracion datos de SINDO
 * Fecha de creacion: Aug 18, 2008 <BR>
 * Copyright (c) IMSS
 * <BR>
 *
 * @author TCS
 * @version 1.0
 */

public interface IntegracionSindoService {


    /**
     * @param registroPatronal
     *  parametro registroPatronal
     * @param modalidad
     *  parametro modalidad
     * @param digitoVerificador
     *  parametro digitoVerificador
     * @return InformacionPatronSalidaVO
     */
    public InformacionPatronSalidaVO obtenerDatosPatron(String registroPatronal, String modalidad,String digitoVerificador);
    
    
}
