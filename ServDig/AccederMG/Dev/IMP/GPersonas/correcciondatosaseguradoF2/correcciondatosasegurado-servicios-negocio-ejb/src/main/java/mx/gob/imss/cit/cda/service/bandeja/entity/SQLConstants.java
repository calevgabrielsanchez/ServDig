package mx.gob.imss.cit.cda.service.bandeja.entity;

public interface SQLConstants {

    String NSS_INVOLUCRADOS = "NSS_INVOLUCRADOS";
    String MOVIMIENTOS = "MOVIMIENTOS";
	String RESPONSABLES_AUTORIZADORES = "RESPONSABLES_AUTORIZADORES";

    int INDEX_NSS_INVOLUCRADOS = 3;
    int INDEX_RESPONSABLE = 5;
    int INDEX_AUTORIZADOR = 6;
    int INDEX_MOVIMIENTOS = 9;
    int INDEX_CVE_ID_INSTANCIA = 14;
    int INDEX_CORRECCION_DATOS_ASEG = 15;

    String SQL_MAXIMO = "SELECT row_.*, rownum rownum_ FROM (#) row_ WHERE rownum <= MAX_";
    String SQL_MINIMO = "SELECT * FROM (#) WHERE rownum_ > MIN_";

    String SQL_SOLICITUDES = "SELECT distinct sol.REF_FOLIO AS refFolio, "
            + "to_char(sol.FEC_SOLICITUD,'DD/MM/YYYY') AS FECHA_SOLICITUD, "
            + "corr.REF_CURP, "
            + "' ' AS NSSINVOLUCRADOS, " /* INDEX_NSS_INVOLUCRADOS */
            + "(CASE sol.CVE_ID_ORIGEN_SOLICITUD "
            + "WHEN 6 THEN 'INTERNET' "
            + "WHEN 1 THEN 'VENTANILLA' "
            + "ELSE 'INTERNET' "
            + "END) \"ORIGEN\", "
            + "'SIN RESPONSABLE' AS Responsable, " /* INDEX_RESPONSABLE */
            + "'SIN AUTORIZADOR' AS Autorizador, " /* INDEX_AUTORIZADOR */
            + "to_char(sol.FEC_REGISTRO_ACTUALIZADO,'DD/MM/YYYY') AS FEC_REGISTRO_ACTUALIZADO, "
            + "tra.cve_id_estado_tramite, "
            + "'SIN TIPO' AS DESTIPOMOV, " /* INDEX_MOVIMIENTOS */
            + "tra.cve_id_tramite, "
            + "tarusu.cve_id_tarea_usuario, "
            + "tarusu.des_bdoc_tarea, "
            + "sol.cve_id_solicitud, "
            + "inst.CVE_ID_INSTANCIA, " /* INDEX_CVE_ID_INSTANCIA */
            + "corr.cve_id_correccion_datos_aseg " /* INDEX_CORRECCION_DATOS_ASEG */
            + "FROM DIT_SOLICITUD sol "
            + "INNER JOIN DIT_TRAMITE tra ON sol.CVE_ID_SOLICITUD = tra.CVE_ID_SOLICITUD AND sol.CVE_ID_TIPO_SOLICITUD = :idTipoSolicitud AND tra.CVE_ID_TIPO_TRAMITE = :idTipoTramite "
            + "INNER JOIN DIC_TIPO_TRAMITE ttra ON tra.CVE_ID_TIPO_TRAMITE = ttra.CVE_ID_TIPO_TRAMITE "
            + "INNER JOIN DIT_INSTANCIA inst ON tra.CVE_ID_TRAMITE = inst.CVE_ID_TRAMITE AND inst.CVE_ID_PROCESO = :proceso "
            + "INNER JOIN DIT_TAREA_USUARIO tarusu ON tarusu.CVE_ID_INSTANCIA = inst.CVE_ID_INSTANCIA "
            + "INNER JOIN DIT_CORRECCION_DATOS_ASEG corr ON corr.CVE_ID_TRAMITE = tra.CVE_ID_TRAMITE "
            + "INNER JOIN DIT_DETALLE_NSS_CDA ddnc ON corr.cve_id_correccion_datos_aseg=ddnc.cve_id_correccion_datos_aseg "
            + "LEFT JOIN dit_mov_aclaracion_nss_cda dmanc ON dmanc.cve_id_detalle_nss_cda=ddnc.cve_id_detalle_nss_cda "
            + "WHERE ddnc.num_nss IS NOT NULL ";

    String SQL_HISTORICOS = "SELECT distinct sol.REF_FOLIO AS refFolio, "
            + "to_char(sol.FEC_SOLICITUD,'DD/MM/YYYY') AS FECHA_SOLICITUD, "
            + "corr.REF_CURP, "
            + "' ' AS NSSINVOLUCRADOS, "
            + "(CASE sol.CVE_ID_ORIGEN_SOLICITUD "
            + "WHEN 6 THEN 'INTERNET' "
            + "WHEN 1 THEN 'VENTANILLA' "
            + "ELSE 'INTERNET' "
            + "END) \"ORIGEN\", "
            + "'SIN RESPONSABLE' AS Responsable, " /* INDEX_RESPONSABLE */
            + "'SIN AUTORIZADOR' AS Autorizador, " /* INDEX_AUTORIZADOR */
            + "to_char(sol.FEC_REGISTRO_ACTUALIZADO,'DD/MM/YYYY') AS FEC_REGISTRO_ACTUALIZADO, "
            + "tra.cve_id_estado_tramite, "
            + "' ' AS DESTIPOMOV, " /* INDEX_MOVIMIENTOS */
            + "tra.cve_id_tramite, "
            + "0 as cve_id_tarea_usuario, "
            + "' ' as des_bdoc_tarea, "
            + "sol.cve_id_solicitud, "
            + "inst.CVE_ID_INSTANCIA, " /* INDEX_CVE_ID_INSTANCIA */
            + "corr.cve_id_correccion_datos_aseg " /* INDEX_CORRECCION_DATOS_ASEG */
            + "FROM DIT_SOLICITUD sol "
            + "INNER JOIN DIT_TRAMITE tra ON sol.CVE_ID_SOLICITUD = tra.CVE_ID_SOLICITUD AND sol.CVE_ID_TIPO_SOLICITUD = :idTipoSolicitud AND tra.CVE_ID_TIPO_TRAMITE = :idTipoTramite "
            + "INNER JOIN DIC_TIPO_TRAMITE ttra ON tra.CVE_ID_TIPO_TRAMITE = ttra.CVE_ID_TIPO_TRAMITE "
            + "INNER JOIN DIT_INSTANCIA inst ON tra.CVE_ID_TRAMITE = inst.CVE_ID_TRAMITE AND inst.CVE_ID_PROCESO = :proceso "
            + "INNER JOIN DIT_TAREA_USUARIO tarusu ON tarusu.CVE_ID_INSTANCIA = inst.CVE_ID_INSTANCIA "
            + "INNER JOIN DIT_CORRECCION_DATOS_ASEG corr ON corr.CVE_ID_TRAMITE = tra.CVE_ID_TRAMITE "
            + "INNER JOIN DIT_DETALLE_NSS_CDA ddnc ON corr.cve_id_correccion_datos_aseg=ddnc.cve_id_correccion_datos_aseg "
            + "LEFT JOIN dit_mov_aclaracion_nss_cda dmanc ON dmanc.cve_id_detalle_nss_cda=ddnc.cve_id_detalle_nss_cda "
            + "WHERE ddnc.num_nss IS NOT NULL ";

    String SQL_NSS_INVOLUCRADOS = "SELECT cve_id_correccion_datos_aseg, "
            + "NVL(LISTAGG(ddncBIS.num_nss,' ')  WITHIN group (order by ddncBIS.cve_id_correccion_datos_aseg),' ') AS NSS "
            + "FROM dit_detalle_nss_cda ddncBIS "
            + "WHERE ddncBIS.cve_id_correccion_datos_aseg IN (:idsCorreccionDatosAseg) "
            + "AND ddncBIS.fec_registro_baja IS NULL "
            + "GROUP BY cve_id_correccion_datos_aseg";

	String SQL_RESPONSABLES_AUTORIZADORES = "SELECT cve_id_instancia, CASE Responsable "
            + "WHEN 'BPM_ADMIN21' THEN 'SIN RESPONSABLE' "
			+ "WHEN 'BPM_ADMIN24' THEN 'SIN RESPONSABLE' "
            + "ELSE Responsable END ||'-'|| CASE Autorizador "
            + "WHEN 'BPM_ADMIN21' THEN 'SIN AUTORIZADOR' "
			+ "WHEN 'BPM_ADMIN24' THEN 'SIN AUTORIZADOR' "
            + "ELSE Autorizador END AS USUARIO FROM ( "
			+ "SELECT cve_id_instancia, "
			+ "extractValue(XMLTYPE(DES_BDOC_INSTANCIA), "
			+ "'/mx.gob.imss.cit.gestion.solicitud.flujo.model.InicioTramite/participantes/entry[1]/string[2]') RESPONSABLE, "
			+ "extractValue(XMLTYPE(DES_BDOC_INSTANCIA), "
			+ "'/mx.gob.imss.cit.gestion.solicitud.flujo.model.InicioTramite/participantes/entry[2]/string[2]') AUTORIZADOR "
			+ "FROM DIT_INSTANCIA WHERE cve_id_instancia IN (:idsInstancia))";

    String SQL_MOVIMIENTOS = "SELECT MOVDIFERENTES.B, "
            + "NVL(LISTAGG(MOVDIFERENTES.A,', ') WITHIN GROUP (ORDER BY MOVDIFERENTES.B), 'SIN TIPO') AS MOVDIFERENTES "
            + "FROM (SELECT distinct NVL(dttcBIS.des_tipo_correccion_tram_nss,'SIN TIPO') A, "
            + "ddncBIS.cve_id_correccion_datos_aseg B "
            + "FROM DIT_DETALLE_NSS_CDA ddncBIS "
            + "INNER JOIN dit_mov_aclaracion_nss_cda dmancBIS ON dmancBIS.cve_id_detalle_nss_cda = ddncBIS.cve_id_detalle_nss_cda "
            + "INNER JOIN dic_tipo_tram_correccion_nss dttcBIS ON dttcBIS.cve_id_tipo_tram_correc_nss = dmancBIS.cve_id_tipo_tram_correc_nss "
            + "WHERE cve_id_correccion_datos_aseg IN (:idsCorreccionDatosAseg)) MOVDIFERENTES "
            + "GROUP BY MOVDIFERENTES.B";


}
