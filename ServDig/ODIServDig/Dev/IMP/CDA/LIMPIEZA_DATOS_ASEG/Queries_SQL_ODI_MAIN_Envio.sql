--============================================================================================================
--Script del flujo "MAIN ENVIO CARGA CDA" paquete: MAIN_LOAD_CDA_ENV
-- Ambiente:                 Prod
-- Autores                   Omar Pérez Chavira 
-- Fecha                     01/Dic/2020
--============================================================================================================


--======================================================
--- PASO: act_bitac_env_SINDO
--======================================================

--- COMANDO: Act_BITACORA

update DIT_DETALLE_TRAMITE dt set (dt.fec_registro_actualizado,  dt.REF_DATOS_TRAMITE_XML) =
      (select SYSDATE, 
      INSERTCHILDXML(XMLType(REPLACE(DT.REF_DATOS_TRAMITE_XML,CHR(0),'')), '/tramiteCorreccionCurp',
      'observacionesSubdelegacion', XMLType('<observacionesSubdelegacion><cveEstado>'||t.CVE_ID_ESTADO_TRAMITE ||'</cveEstado>
                                              <asignado>'||s.CVE_ID_USUARIO ||'</asignado>
                                              <fechaActualizacion>'||to_char(current_timestamp, 'YYYY-MM-DD"T"HH24:MI:SSX"00-06:00"')||'</fechaActualizacion>
                                              <usuario>SERVICIO DE CORRECCIÓN DE DATOS</usuario>
                                              <detalle>'||t.REF_OBSERVACION ||'</detalle>
                                              </observacionesSubdelegacion>')
      ).getclobval()
        from dit_solicitud s, DIT_TRAMITE t
        where s.CVE_ID_SOLICITUD = t.CVE_ID_SOLICITUD
        and t.CVE_ID_TRAMITE = dt.CVE_ID_TRAMITE
        AND T.CVE_ID_TIPO_TRAMITE =139
        AND T.CVE_ID_ESTADO_TRAMITE =37
        AND TRUNC(T.FEC_REGISTRO_ACTUALIZADO) = TRUNC(SYSDATE)
        --and dt.REF_DATOS_TRAMITE_XML is not null 
      )
where exists(
      select t.rowid
      from dit_solicitud s, DIT_TRAMITE t
        where s.CVE_ID_SOLICITUD = t.CVE_ID_SOLICITUD
        and t.CVE_ID_TRAMITE = dt.CVE_ID_TRAMITE
        AND T.CVE_ID_TIPO_TRAMITE =139
        AND T.CVE_ID_ESTADO_TRAMITE =37
        AND TRUNC(T.FEC_REGISTRO_ACTUALIZADO) = TRUNC(SYSDATE)
        --and dt.REF_DATOS_TRAMITE_XML is not null
)
