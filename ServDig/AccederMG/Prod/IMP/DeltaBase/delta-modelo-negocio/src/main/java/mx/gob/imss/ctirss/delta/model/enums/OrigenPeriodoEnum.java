/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.ctirss.delta.model.enums;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author guadalupe.gomez
 */
public enum OrigenPeriodoEnum { 

    CUENTA_INDIVIDUAL(1L, "CUENTA INDIVIDUAL"),
    VENTANILLA(2L, "VENTANILLA");
    

    private OrigenPeriodoEnum(Long id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    private Long id;
    private String desc;

    private static final Map<Long, OrigenPeriodoEnum> origenes = new HashMap<Long, OrigenPeriodoEnum>();
    private static final Map<String, OrigenPeriodoEnum> origenesByDesc = new HashMap<String, OrigenPeriodoEnum>();

    static {
        for (OrigenPeriodoEnum origen : OrigenPeriodoEnum.values()) {
            origenes.put(origen.getId(), origen);
            origenesByDesc.put(origen.getDesc(), origen);
        }
    }

    public Long getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    public static OrigenPeriodoEnum getById(Long id) {
        return origenes.get(id);
    }

    public static OrigenPeriodoEnum getByDesc(String origen) {
        return origenesByDesc.get(origen);
    }

}
