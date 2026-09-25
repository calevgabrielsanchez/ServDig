package mx.gob.imss.ctirss.delta.gestion.clasificacion.test.ejb.servicios;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.ReporteCifrasDTO;

//--Consulta movimientos patronales  por periodo
//SELECT count(*) cifras, t.cve_id_tipo_tramite, tt.des_tipo_tramite
// FROM MGPBDTU9X.dit_tramite t
// JOIN MGPBDTU9X.DIC_TIPO_TRAMITE tt ON t.cve_id_tipo_tramite = tt.cve_id_tipo_tramite
// WHERE 1=1
// AND t.CVE_ID_ESTADO_TRAMITE = 2
// AND t.cve_id_tipo_tramite in('13','14','15','16','17','18','74','75','7','90','21','20','19','12','22')
// AND t.FEC_REGISTRO_ACTUALIZADO >= to_date('01/01/2020','DD/MM/YYYY')
// AND t.FEC_REGISTRO_ACTUALIZADO <= to_date('31/12/2020','DD/MM/YYYY')
// GROUP BY t.cve_id_tipo_tramite, tt.des_tipo_tramite;
//
//-------------------------------------------------------------------------------------------------------------
//---Consulta para reportes para movimientos 19, 20, 21 y 175
//---tr.cve_id_tipo_tramite se agrega el valor del tipo de tramite que se quiere el reporte
//
//select
//EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/moral/rfc') AS "RFC_PAT_SUSTITUTO",
//      pg.reg_patron || m.num_modalidad || pg.dig_ver as "REG_PAT_SUSTITUTO",
//        EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/clasificacion/fraccion/clase/descripcion') AS "CLASE_PAT_SUSTITUTO",
//       EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/clasificacion/fraccion/grupo/division/numDivision')
//        || EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/clasificacion/fraccion/grupo/numGrupo')
//        || EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/clasificacion/fraccion/numFraccion') AS "FRACCION_PAT_SUSTITUTO",    
//        EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/clasificacion/fraccion/primaSRT') AS "PRIMA_PAT_SUSTITUTO",
//        EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/subdelegacion/delegacion/descripcion') as "DELEGACION_PAT_SUSTITUTO",
//        EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/subdelegacion/descripcion') as "SUBDELEGACION_PAT_SUSTITUTO",
//        EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[1]/numeroRegistroPatronal')
//        || EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[1]/modalidad/numModalidad')
//        || EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[1]/digVerificador') as "REGISTRO_PATRONAL_SUST1",  
//          EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[1]/clasificacion/fraccion/clase/descripcion') AS "CLASE_PAT_SUST1",
//         EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[1]/clasificacion/fraccion/grupo/division/numDivision')
//        || EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[1]/clasificacion/fraccion/grupo/numGrupo')
//        || EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[1]/clasificacion/fraccion/numFraccion') AS "FRACCION_PAT_SUST1",
//        EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[1]/clasificacion/fraccion/primaSRT') AS "PRIMA_PAT_SUST1",
//        EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[1]/subdelegacion/delegacion/descripcion') as "DELEGACION_PAT_SUST1",
//        EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[1]/subdelegacion/descripcion') as "SUBDELEGACION_PAT_SUST1",
//        
//      EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[2]/numeroRegistroPatronal')
//        || EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[2]/modalidad/numModalidad')
//        || EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[2]/digVerificador') as "REGISTRO_PATRONAL_SUST2",  
//          EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[2]/clasificacion/fraccion/clase/descripcion') AS "CLASE_PAT_SUST2",
//         EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[2]/clasificacion/fraccion/grupo/division/numDivision')
//        || EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[2]/clasificacion/fraccion/grupo/numGrupo')
//        || EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[2]/clasificacion/fraccion/numFraccion') AS "FRACCION_PAT_SUST2",
//        EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[2]/clasificacion/fraccion/primaSRT') AS "PRIMA_PAT_SUST2",
//        EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[2]/subdelegacion/delegacion/descripcion') as "DELEGACION_PAT_SUST2",
//        EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[2]/subdelegacion/descripcion') as "SUBDELEGACION_PAT_SUST2",
//        
//        EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[3]/numeroRegistroPatronal')
//        || EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[3]/modalidad/numModalidad')
//        || EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[3]/digVerificador') as "REGISTRO_PATRONAL_SUST3",  
//          EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[3]/clasificacion/fraccion/clase/descripcion') AS "CLASE_PAT_SUST3",
//         EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[3]/clasificacion/fraccion/grupo/division/numDivision')
//        || EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[3]/clasificacion/fraccion/grupo/numGrupo')
//        || EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[3]/clasificacion/fraccion/numFraccion') AS "FRACCION_PAT_SUST3",
//        EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[3]/clasificacion/fraccion/primaSRT') AS "PRIMA_PAT_SUST3",
//        EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[3]/subdelegacion/delegacion/descripcion') as "DELEGACION_PAT_SUST3",
//        EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[3]/subdelegacion/descripcion') as "SUBDELEGACION_PAT_SUST3",
//        
//        EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[4]/numeroRegistroPatronal')
//        || EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[4]/modalidad/numModalidad')
//        || EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[4]/digVerificador') as "REGISTRO_PATRONAL_SUST4",  
//          EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[4]/clasificacion/fraccion/clase/descripcion') AS "CLASE_PAT_SUST4",
//         EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[4]/clasificacion/fraccion/grupo/division/numDivision')
//        || EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[4]/clasificacion/fraccion/grupo/numGrupo')
//        || EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[4]/clasificacion/fraccion/numFraccion') AS "FRACCION_PAT_SUST4",
//        EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[4]/clasificacion/fraccion/primaSRT') AS "PRIMA_PAT_SUST4",
//        EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[4]/subdelegacion/delegacion/descripcion') as "DELEGACION_PAT_SUST4",
//        EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[4]/subdelegacion/descripcion') as "SUBDELEGACION_PAT_SUST4",
//        
//        EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[5]/numeroRegistroPatronal')
//        || EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[5]/modalidad/numModalidad')
//        || EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[5]/digVerificador') as "REGISTRO_PATRONAL_SUST5",  
//          EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[5]/clasificacion/fraccion/clase/descripcion') AS "CLASE_PAT_SUST5",
//         EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[5]/clasificacion/fraccion/grupo/division/numDivision')
//        || EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[5]/clasificacion/fraccion/grupo/numGrupo')
//        || EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[5]/clasificacion/fraccion/numFraccion') AS "FRACCION_PAT_SUST5",
//        EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[5]/clasificacion/fraccion/primaSRT') AS "PRIMA_PAT_SUST5",
//        EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[5]/subdelegacion/delegacion/descripcion') as "DELEGACION_PAT_SUST5",
//        EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[5]/subdelegacion/descripcion') as "SUBDELEGACION_PAT_SUST5",
//        
//        EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[6]/numeroRegistroPatronal')
//        || EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[6]/modalidad/numModalidad')
//        || EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[6]/digVerificador') as "REGISTRO_PATRONAL_SUST6",  
//          EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[6]/clasificacion/fraccion/clase/descripcion') AS "CLASE_PAT_SUST6",
//         EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[6]/clasificacion/fraccion/grupo/division/numDivision')
//        || EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[6]/clasificacion/fraccion/grupo/numGrupo')
//        || EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[6]/clasificacion/fraccion/numFraccion') AS "FRACCION_PAT_SUST6",
//        EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[6]/clasificacion/fraccion/primaSRT') AS "PRIMA_PAT_SUST6",
//        EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[6]/subdelegacion/delegacion/descripcion') as "DELEGACION_PAT_SUST6",
//        EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[6]/subdelegacion/descripcion') as "SUBDELEGACION_PAT_SUST6",
//        
//        EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[7]/numeroRegistroPatronal')
//        || EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[7]/modalidad/numModalidad')
//        || EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[7]/digVerificador') as "REGISTRO_PATRONAL_SUST7",  
//          EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[7]/clasificacion/fraccion/clase/descripcion') AS "CLASE_PAT_SUST7",
//         EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[7]/clasificacion/fraccion/grupo/division/numDivision')
//        || EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[7]/clasificacion/fraccion/grupo/numGrupo')
//        || EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[7]/clasificacion/fraccion/numFraccion') AS "FRACCION_PAT_SUST7",
//        EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[7]/clasificacion/fraccion/primaSRT') AS "PRIMA_PAT_SUST7",
//        EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[7]/subdelegacion/delegacion/descripcion') as "DELEGACION_PAT_SUST7",
//        EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[7]/subdelegacion/descripcion') as "SUBDELEGACION_PAT_SUST7",
//        
//        EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[8]/numeroRegistroPatronal')
//        || EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[8]/modalidad/numModalidad')
//        || EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[8]/digVerificador') as "REGISTRO_PATRONAL_SUST8",  
//          EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[8]/clasificacion/fraccion/clase/descripcion') AS "CLASE_PAT_SUST8",
//         EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[8]/clasificacion/fraccion/grupo/division/numDivision')
//        || EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[8]/clasificacion/fraccion/grupo/numGrupo')
//        || EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[8]/clasificacion/fraccion/numFraccion') AS "FRACCION_PAT_SUST8",
//        EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[8]/clasificacion/fraccion/primaSRT') AS "PRIMA_PAT_SUST8",
//        EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[8]/subdelegacion/delegacion/descripcion') as "DELEGACION_PAT_SUST8",
//        EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[8]/subdelegacion/descripcion') as "SUBDELEGACION_PAT_SUST8",
//        
//        EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[9]/numeroRegistroPatronal')
//        || EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[9]/modalidad/numModalidad')
//        || EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[9]/digVerificador') as "REGISTRO_PATRONAL_SUST9",  
//          EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[9]/clasificacion/fraccion/clase/descripcion') AS "CLASE_PAT_SUST9",
//         EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[9]/clasificacion/fraccion/grupo/division/numDivision')
//        || EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[9]/clasificacion/fraccion/grupo/numGrupo')
//        || EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[9]/clasificacion/fraccion/numFraccion') AS "FRACCION_PAT_SUST9",
//        EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[9]/clasificacion/fraccion/primaSRT') AS "PRIMA_PAT_SUST9",
//        EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[9]/subdelegacion/delegacion/descripcion') as "DELEGACION_PAT_SUST9",
//        EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[9]/subdelegacion/descripcion') as "SUBDELEGACION_PAT_SUST9",
//        
//        EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[10]/numeroRegistroPatronal')
//        || EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[10]/modalidad/numModalidad')
//        || EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[10]/digVerificador') as "REGISTRO_PATRONAL_SUST10",  
//          EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[10]/clasificacion/fraccion/clase/descripcion') AS "CLASE_PAT_SUST10",
//         EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[10]/clasificacion/fraccion/grupo/division/numDivision')
//        || EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[10]/clasificacion/fraccion/grupo/numGrupo')
//        || EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[10]/clasificacion/fraccion/numFraccion') AS "FRACCION_PAT_SUST10",
//        EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[10]/clasificacion/fraccion/primaSRT') AS "PRIMA_PAT_SUST10",
//        EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[10]/subdelegacion/delegacion/descripcion') as "DELEGACION_PAT_SUST10",
//        EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/sujetoObligado/sujetosObligados[10]/subdelegacion/descripcion') as "SUBDELEGACION_PAT_SUST10",
//        EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/fechaPresentacion') AS "FECHA_REGISTRO",
//       EXTRACTVALUE (xmltype (DDT.REF_DATOS_TRAMITE_XML), '/*/fechaEfecto') AS "FECHA_EFECTOS"
//from MGPBDTU9X.dit_solicitud sol
//join MGPBDTU9X.dit_tramite tr on(sol.cve_id_tipo_solicitud=11 AND TRUNC (tr.FEC_REGISTRO_ACTUALIZADO) >= TO_DATE('01/01/2024','DD/MM/YYYY') AND TRUNC (tr.FEC_REGISTRO_ACTUALIZADO) <= TO_DATE('07/03/2024','DD/MM/YYYY') and sol.cve_id_tipo_solicitud=11 and sol.cve_id_estado_solicitud=2 and tr.cve_id_tipo_tramite=20 and tr.cve_id_estado_tramite=2 and sol.cve_id_solicitud = tr.cve_id_solicitud)
//join MGPBDTU9X.dit_detalle_tramite ddt on (tr.cve_id_tramite= ddt.cve_id_tramite)
//left join MGPBDTU9X.dit_analisis_ce da on(sol.cve_id_solicitud = da.cve_id_solicitud)
//left join MGPBDTU9X.dic_estatus_analisis_ce dea on( da.cve_id_estatus_analisis = dea.cve_id_estatus_analisis)
//JOIN MGPBDTU9X.dit_tramite_pat_suj_obligado tpso ON (tpso.cve_id_tramite = tr.cve_id_tramite)
//JOIN MGPBDTU9X.dit_patron_sujeto_obligado pso ON (pso.cve_id_patron_sujeto_obligado = tpso.cve_id_patron_sujeto_obligado)
//JOIN MGPBDTU9X.dit_patron_general pg ON (pg.cve_id_patron_sujeto_obligado = pso.cve_id_patron_sujeto_obligado)
//JOIN MGPBDTU9X.dic_modalidad m ON (m.cve_id_modalidad = pso.cve_id_modalidad)
//left JOIN MGPBDTU9X.dit_persona_fisica pf ON (pf.fec_registro_baja IS NULL AND pf.cve_id_persona_fisica = pso.cve_id_persona_fisica)
//left JOIN MGPBDTU9X.dit_persona p ON (p.fec_registro_baja IS NULL AND p.cve_id_persona = pf.cve_id_persona)
//left JOIN MGPBDTU9X.dit_persona_moral pm ON (pm.fec_registro_baja IS NULL AND pm.cve_id_persona_moral = pso.cve_id_persona_moral)
//left join MGPBDTU9X.DIT_CLASIFICACION_PROPUESTA pro on(da.cve_id_analisis = pro.cve_id_analisis)
//left JOIN MGPBDTU9X.DIC_FRACCION fracpro ON (fracpro.CVE_ID_FRACCION = pro.CVE_ID_FRACCION)
//left JOIN MGPBDTU9X.DIC_FRACCION_CLASE fclaspro ON (fclaspro.FEC_FIN IS NULL AND fclaspro.CVE_ID_FRACCION = fracpro.CVE_ID_FRACCION)
//left JOIN MGPBDTU9X.DIC_CLASE claspro ON (claspro.CVE_ID_CLASE = fclaspro.CVE_ID_CLASE)
//left JOIN MGPBDTU9X.DIC_GRUPO gruppro ON (gruppro.CVE_ID_GRUPO = fracpro.CVE_ID_GRUPO)
//left JOIN MGPBDTU9X.DIC_DIVISION divpro ON (divpro.CVE_ID_DIVISION = gruppro.CVE_ID_DIVISION);

public class ReporteCifrasTest {
	
	private static final Logger log = LoggerFactory.getLogger(ReporteCifrasTest.class);
	private BufferedReader obj;
	private StringBuffer datosTabla = new StringBuffer();
	private ReporteCifrasDTO datoTabla = null;
			
	@Test
	public void obtenerReporteCifras() {
		
		System.out.println("INFO SE ESTA CREANDO EL TEST");
		String nameFile = "Tramite_21"+".txt";
		String urlExcel = "C:\\Users\\alsantos\\Documents\\Scripts\\PENDIENTE_DITANALISISCE\\"+nameFile;
		try {
			obtenerDatosCifras(urlExcel);
			
			sendText(datosTabla.toString(), nameFile);
			System.out.println("CREADO EL DOCUMENTO");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
	}
	
	private void obtenerDatosCifras(String rutaExcel) throws IOException{
		File f = new File(rutaExcel);
		
		obj = new BufferedReader(new FileReader(f));
		
		Object[] lines = obj.lines().toArray();
		
	
		for(int i = 1; i < lines.length;i++) {
			
			String[] row = lines[i].toString().split("\\|");
			this.generarFila1(row, i);
			this.generarFila(row);
			
		}
	
	}
	
	private void sendText(String contenido, String nameFile){
        try {
            String ruta = "C:\\Users\\alsantos\\Documents\\Scripts\\Corregida\\"+nameFile;
            File file = new File(ruta);
            // Si el archivo no existe es creado
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(contenido);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	
	private void generarFila(String[] row) {
		ReporteCifrasDTO datoTabla = null;

		for (int j = 13; j < row.length; j++) {

			if(j >= 67) { break; }
			

			datoTabla  = new ReporteCifrasDTO((String) row[0], (String) row[1], (String) row[2],
					(String)row[3] , (String) row[4] , (String) row[5],
					(String) row[6], (String) row[67], (String) row[68]);
			
			if(row[j]!=null && !row[j].isEmpty()) {

				datoTabla.setRegistroPatronalSustituido((String) row[j++]);
				datoTabla.setClaseSustituido((String) row[j++]);
				datoTabla.setFraccionSustituido((String)row[j++]);
				datoTabla.setPrimaSustituido((String)row[j++]);
				datoTabla.setDelegacionSustituido((String) row[j++]);
				datoTabla.setSubdelegacionSustituido((String) row[j]);
			
				datosTabla.append(datoTabla.toString());
				}
			else {
				break;
			}
			
		}
		
	}
	
	
	private void generarFila1(String[] row, int i) {
		System.out.println("FILA RECORRIDA [" + (i+1)+"]");
		datoTabla  = new ReporteCifrasDTO((String) row[0], (String) row[1], (String) row[2],
				(String)row[3] , (String) row[4] , (String) row[5],
				(String) row[6], (String) row[67], (String) row[68]);
		System.out.println("ya paso reporte");
		if(row[7] != null && !row[7].isEmpty()) {
			this.datoTabla.setRegistroPatronalSustituido((String) row[7]);
			this.datoTabla.setClaseSustituido((String) row[8]);
			this.datoTabla.setFraccionSustituido((String) row[9]);
			this.datoTabla.setPrimaSustituido((String) row[10]);
			this.datoTabla.setDelegacionSustituido((String) row[11]);
			this.datoTabla.setSubdelegacionSustituido((String) row[12]);
		
			datosTabla.append(datoTabla.toString());
		} else {
			datosTabla.append(datoTabla.toString());
		}
	}
	
	
}
