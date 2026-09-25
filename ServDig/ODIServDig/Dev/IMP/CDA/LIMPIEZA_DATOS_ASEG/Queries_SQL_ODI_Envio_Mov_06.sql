--============================================================================================================
--Script del flujo "Envio de informacion a SINDO MOVIMIENTO 06" paquete: PKG_LOAD_CDA_SINDO_ENV_PROD
-- Ambiente:                 Prod
-- Autores                   Omar Pérez Chavira 
-- Fecha                     30/Nov/2020
--============================================================================================================


--======================================================
--- PASO: SP_CREA_TMP_ENV_SINDO_ACT
--======================================================
--- COMANDO: DROP TABLE 

DROP TABLE ENVIO_SINDO_SINDO_TMP

--- COMANDO: CREA_TMP_SINDO

CREATE TABLE ENVIO_SINDO_SINDO_TMP 
TABLESPACE MG_TS_N_DATA_002_TRABAJO 
AS
SELECT '00' AS DELORIG
        ,'00' AS SUBORIG
        ,2 AS CVEAPLIC
        ,'06' AS TPMOVTO
        ,'6' AS ORIGENMOV
        ,SUB.CLAVE_SUBDELEGACION||'411' AS NUMFOLIO
        ,'00' AS ARGUMENTO
        ,'          ' AS REGPATRON
        ,0 AS DIGVRPAT
        ,TO_CHAR(SYSDATE,'DDMMYYYY') AS FMOVTO
        ,TO_CHAR(SYSDATE,'DDMMYYYY') AS FRECEPMOVI                                                              
       ,(xmltype(REPLACE(DT.REF_DATOS_TRAMITE_XML,CHR(0),'')).extract('//./personaRENAPO/@curp').getStringVal()) AS CVEUNICA
        ,0 AS IDSUBRSERV
        ,0 AS IDEVENTUAL
        ,SUBSTR(PER.NSS,1,10)  AS NUMSEGSOC
        ,SUBSTR(PER.NSS,11,1) AS DIGVRNSS
        ,REPLACE(TRANSLATE(UPPER(PER.AP_PATERNO||'$'||PER.AP_MATERNO ||'$' || PER.NOMBRE), 'ÑÁÉÍÓÚÜÖ', '#AEIOU'||chr(220)||'O'),'#APOS;',chr(39)) AS NOMASEG
        ,0 AS IDEXTEMP
        ,0 AS REDUCPAGO
        ,0 AS EXTODEL
        ,'000000' AS SALBASE
        ,'000000' AS SALINFONAVIT
        ,'0' AS TPSALARIO
        --,PER.CVE_ID_SEXO AS SEXO
        ,to_number(EXTRACTVALUE (xmltype (REPLACE(DT.REF_DATOS_TRAMITE_XML,CHR(0),'')),'//personaRENAPO/sexo/idSexo/text()')) AS SEXO
        ,SUBSTR(xmltype(REPLACE(DT.REF_DATOS_TRAMITE_XML,CHR(0),'')).extract('//./personaRENAPO/@fechaNacimiento').getStringVal(),6,2) AS MESNAC
        ,EXTRACTVALUE (xmltype (REPLACE(DT.REF_DATOS_TRAMITE_XML,CHR(0),'')),'//personaRENAPO/lugarNacimiento/clave/text()') AS LUGARNAC
        ,'000' AS UMF
        ,0 AS AUTPERM
        ,'00' AS DELDEST
        ,'00' AS SUBDEST
        ,'0' AS TPDERECH
        ,'00' AS AANAC
        ,0 AS SITUACION
        ,0 AS TSALODEL
        ,'                                                  ' AS NOMBREDH
        ,'00' AS MESNACAP
        ,'0000000000' AS NSSCORR
        ,'0' AS DIGVRNSSCORR
        , REPLACE(TRANSLATE(UPPER((xmltype(REPLACE(DT.REF_DATOS_TRAMITE_XML,CHR(0),'')).extract('//./personaRENAPO/@primerApellido').getStringVal()
          ||'$'|| xmltype(REPLACE(DT.REF_DATOS_TRAMITE_XML,CHR(0),'')).extract('//./personaRENAPO/@segundoApellido').getStringVal()|| '$'||
          xmltype(REPLACE(DT.REF_DATOS_TRAMITE_XML,CHR(0),'')).extract('//./personaRENAPO/@nombre').getStringVal())), 'ÑÁÉÍÓÚÜÖ', '#AEIOU'||chr(220)||'O'),'&APOS;',chr(39))  as NOMASEGC
        ,'00' AS TPPENS
        ,'0' AS ALFGUAR
        ,'000' AS NUMGUAR
        ,'0' AS CONDICION
        ,MOVA.CVE_ID_MOV_ACLARACION_NSS AS IDTRAMITE
        ,'0' AS TPPRORROGA
        ,'00000000' AS FECTERPRORR
        ,'0' AS IDPD
        ,DELE.CVE_CIZ AS CVE_CIZ
FROM DIT_SOLICITUD SOL
INNER JOIN DIT_TRAMITE TRAM ON SOL.CVE_ID_SOLICITUD = TRAM.CVE_ID_SOLICITUD  
INNER JOIN DIT_DETALLE_TRAMITE DT ON DT.CVE_ID_TRAMITE = TRAM.CVE_ID_TRAMITE
INNER JOIN DIT_CORRECCION_DATOS_ASEG DA ON DA.CVE_ID_TRAMITE = TRAM.CVE_ID_TRAMITE  
INNER JOIN DIT_DETALLE_NSS_CDA NSS ON DA.CVE_ID_CORRECCION_DATOS_ASEG = NSS.CVE_ID_CORRECCION_DATOS_ASEG
INNER JOIN DIT_MOV_ACLARACION_NSS_CDA MOVA ON  MOVA.CVE_ID_DETALLE_NSS_CDA =  NSS.CVE_ID_DETALLE_NSS_CDA
/*INNER JOIN (
                    select   min (ANSS.CVE_ID_PERSONA) as CVE_ID_PERSONA  ,  ANSS.NUM_NSS 
                    from (
                        select asig.cve_id_persona,  asig.num_nss 
                        from DIT_ASIGNACION_NSS asig, DIT_DETALLE_NSS_CDA NSS
                        where NSS.NUM_NSS = ASIG.NUM_NSS
                    union
                        select asig.cve_id_persona,  asig.num_nss  
                        from DIT_ASIGNACION_NSS_CL2 asig , DIT_DETALLE_NSS_CDA NSS
                        where NSS.NUM_NSS = ASIG.NUM_NSS
                    union
                        select asig.cve_id_persona,  asig.num_nss 
                        from DIT_ASIGNACION_NSS_CL3 asig, DIT_DETALLE_NSS_CDA NSS
                        where NSS.NUM_NSS = ASIG.NUM_NSS
                     union
                        select p.cve_id_persona,  canase.nss 
                        from D_CANASE_PPCANA01 canase INNER join DIT_DETALLE_NSS_CDA NSS on NSS.NUM_NSS = canase.NSS  
                        inner join DIT_CORRECCION_DATOS_ASEG aseg on aseg.CVE_ID_CORRECCION_DATOS_ASEG = 	    		                        nss.CVE_ID_CORRECCION_DATOS_ASEG
                        inner join DIT_PERSONA p on p.CURP = aseg.REF_CURP 
                    )   ANSS
                    group by ANSS.NUM_NSS) ANSS ON NSS.NUM_NSS = ANSS.NUM_NSS */
INNER JOIN D_CANASE_PPCANA01 PER ON PER.NSS = NSS.NUM_NSS
INNER JOIN DIC_SUBDELEGACION SUB ON SOL.CVE_ID_SUBDELEGACION = SUB.CVE_ID_SUBDELEGACION
INNER JOIN DIC_DELEGACION DELE ON SUB.CVE_ID_DELEGACION = DELE.CVE_ID_DELEGACION
INNER JOIN(select  mov.CVE_ID_DETALLE_NSS_CDA, MAX(MOV.CVE_ID_MOV_ACLARACION_NSS)as CVE_ID_MOV_ACLARACION_NSS  
            from DIT_DETALLE_NSS_CDA NSS, DIT_MOV_ACLARACION_NSS_CDA MOV
            WHERE NSS.CVE_ID_DETALLE_NSS_CDA = MOV.CVE_ID_DETALLE_NSS_CDA
            AND MOV.CVE_ID_TIPO_NSS_ACLARACION IN (1,2)
            AND (MOV.CVE_ID_ESTADO_MOV_ENV_SINDO IS NULL OR MOV.CVE_ID_ESTADO_MOV_ENV_SINDO = 2)
            AND MOV.fec_registro_baja is null
            GROUP BY mov.CVE_ID_DETALLE_NSS_CDA, mov.CVE_ID_DETALLE_NSS_CDA) 
            movmax on movmax.CVE_ID_MOV_ACLARACION_NSS = MOVA.CVE_ID_MOV_ACLARACION_NSS 
WHERE SOL.CVE_ID_ESTADO_SOLICITUD = 5
AND SOL.CVE_ID_TIPO_SOLICITUD = 50
AND TRAM.CVE_ID_ESTADO_TRAMITE =75
AND TRAM.CVE_ID_TIPO_TRAMITE =139
AND (MOVA.CVE_ID_TIPO_NSS_ACLARACION IN (1,2) OR MOVA.CVE_ID_TIPO_TRAM_CORREC_NSS IN(1,2))
AND (MOVA.CVE_ID_ESTADO_MOV_ENV_SINDO IS NULL OR MOVA.CVE_ID_ESTADO_MOV_ENV_SINDO = 2)
AND MOVA.fec_registro_baja is null
UNION
SELECT   '00' AS DELORIG
        ,'00' AS SUBORIG
        ,2 AS CVEAPLIC
        ,'06' AS TPMOVTO
        ,'6' AS ORIGENMOV
        ,SUB.CLAVE_SUBDELEGACION||'411' AS NUMFOLIO
        ,'00' AS ARGUMENTO
        ,'          ' AS REGPATRON
        ,0 AS DIGVRPAT
        ,TO_CHAR(SYSDATE,'DDMMYYYY') AS FMOVTO
        ,TO_CHAR(SYSDATE,'DDMMYYYY') AS FRECEPMOVI                                                              
       -- ,(CASE WHEN NSS.CVE_ID_TIPO_NSS = 3 AND SUBSTR(PER.CURP, 1,10) LIKE CURP2 THEN  '                  ' ELSE PER.CURP END) AS CVEUNICA
        ,'                  ' AS CVEUNICA
        ,0 AS IDSUBRSERV
        ,0 AS IDEVENTUAL
        ,SUBSTR(PER.NSS,1,10) AS NUMSEGSOC
        ,SUBSTR(PER.NSS,11,1) AS DIGVRNSS
		,REPLACE(TRANSLATE(UPPER(PER.AP_PATERNO||'$'||PER.AP_MATERNO ||'$' || PER.NOMBRE), 'ÑÁÉÍÓÚÜÖ', '#AEIOU'||chr(220)||'O'),'#APOS;',chr(39)) AS NOMASEG
        ,0 AS IDEXTEMP
        ,0 AS REDUCPAGO
        ,0 AS EXTODEL
        ,'000000' AS SALBASE
        ,'000000' AS SALINFONAVIT
        ,'0' AS TPSALARIO
        ,to_number(LTRIM(PER.SEXO)) AS SEXO
        ,LTRIM(NVL(TO_CHAR(PER.MES_NAC,'00'), '00')) AS MESNAC
        ,LTRIM(NVL(TO_CHAR(PER.LUGAR_NAC,'00'), '00')) AS LUGARNAC
        ,'000' AS UMF
        ,0 AS AUTPERM
        ,'00' AS DELDEST
        ,'00' AS SUBDEST
        ,'0' AS TPDERECH
        ,'00' AS AANAC
        ,0 AS SITUACION
        ,0 AS TSALODEL
        ,'                                                  ' AS NOMBREDH
        ,'00' AS MESNACAP
        ,'0000000000' AS NSSCORR
        ,'0' AS DIGVRNSSCORR
        ,REPLACE(TRANSLATE(UPPER(PER.AP_PATERNO||'$'||PER.AP_MATERNO ||'$' || PER.NOMBRE), 'ÑÁÉÍÓÚÜÖ', '#AEIOU'||chr(220)||'O'),'#APOS;',chr(39)) AS NOMASEGC
        ,'00' AS TPPENS
        ,'0' AS ALFGUAR
        ,'006' AS NUMGUAR
        ,'0' AS CONDICION
        ,MOVA.CVE_ID_MOV_ACLARACION_NSS AS IDTRAMITE
        ,'0' AS TPPRORROGA
        ,'00000000' AS FECTERPRORR
        ,'0' AS IDPD
        ,DELE.CVE_CIZ AS CVE_CIZ
FROM DIT_SOLICITUD SOL
INNER JOIN DIT_TRAMITE TRAM ON SOL.CVE_ID_SOLICITUD = TRAM.CVE_ID_SOLICITUD  
INNER JOIN DIT_DETALLE_TRAMITE DT ON DT.CVE_ID_TRAMITE = TRAM.CVE_ID_TRAMITE
INNER JOIN DIT_CORRECCION_DATOS_ASEG DA ON DA.CVE_ID_TRAMITE = TRAM.CVE_ID_TRAMITE  
INNER JOIN DIT_DETALLE_NSS_CDA NSS ON DA.CVE_ID_CORRECCION_DATOS_ASEG = NSS.CVE_ID_CORRECCION_DATOS_ASEG
INNER JOIN DIT_MOV_ACLARACION_NSS_CDA MOVA ON  MOVA.CVE_ID_DETALLE_NSS_CDA =  NSS.CVE_ID_DETALLE_NSS_CDA
--INNER JOIN DIT_ASIGNACION_NSS ANSS ON NSS.NUM_NSS = ANSS.NUM_NSS
/*INNER JOIN (
                    select   min (ANSS.CVE_ID_PERSONA) as CVE_ID_PERSONA  ,  ANSS.NUM_NSS 
                    from (
                        select asig.cve_id_persona,  asig.num_nss 
                        from DIT_ASIGNACION_NSS asig, DIT_DETALLE_NSS_CDA NSS
                        where NSS.NUM_NSS = ASIG.NUM_NSS
                    union
                        select asig.cve_id_persona,  asig.num_nss  
                        from DIT_ASIGNACION_NSS_CL2 asig , DIT_DETALLE_NSS_CDA NSS
                        where NSS.NUM_NSS = ASIG.NUM_NSS
                    union
                        select asig.cve_id_persona,  asig.num_nss 
                        from DIT_ASIGNACION_NSS_CL3 asig, DIT_DETALLE_NSS_CDA NSS
                        where NSS.NUM_NSS = ASIG.NUM_NSS 
                     union
                        select p.cve_id_persona,  canase.nss 
                        from D_CANASE_PPCANA01 canase INNER join DIT_DETALLE_NSS_CDA NSS on NSS.NUM_NSS = canase.NSS  
                        inner join DIT_CORRECCION_DATOS_ASEG aseg on aseg.CVE_ID_CORRECCION_DATOS_ASEG =            			                        nss.CVE_ID_CORRECCION_DATOS_ASEG
                        inner join DIT_PERSONA p on p.CURP = aseg.REF_CURP 
                    )   ANSS
                    group by ANSS.NUM_NSS) ANSS ON NSS.NUM_NSS = ANSS.NUM_NSS */
INNER JOIN D_CANASE_PPCANA01 PER ON PER.NSS = NSS.NUM_NSS
INNER JOIN DIC_SUBDELEGACION SUB ON SOL.CVE_ID_SUBDELEGACION = SUB.CVE_ID_SUBDELEGACION
INNER JOIN DIC_DELEGACION DELE ON SUB.CVE_ID_DELEGACION = DELE.CVE_ID_DELEGACION
/*INNER JOIN (
            SELECT SOL.CVE_ID_SOLICITUD AS CVE_ID_SOL2, PER.CURP AS CURP2  
            FROM DIT_SOLICITUD SOL
            INNER JOIN DIT_TRAMITE TRAM ON SOL.CVE_ID_SOLICITUD = TRAM.CVE_ID_SOLICITUD
            INNER JOIN DIT_CORRECCION_DATOS_ASEG DA ON DA.CVE_ID_TRAMITE = TRAM.CVE_ID_TRAMITE
            INNER JOIN DIT_DETALLE_NSS_CDA NSS ON DA.CVE_ID_CORRECCION_DATOS_ASEG = NSS.CVE_ID_CORRECCION_DATOS_ASEG
            INNER JOIN DIT_ASIGNACION_NSS ANSS ON NSS.NUM_NSS = ANSS.NUM_NSS
            INNER JOIN DIT_PERSONA PER ON PER.CVE_ID_PERSONA = ANSS.CVE_ID_PERSONA
            WHERE NSS.CVE_ID_TIPO_NSS =1
            AND PER.CURP IS NOT NULL
          ) CERTI ON CERTI.CVE_ID_SOL2 = SOL.CVE_ID_SOLICITUD*/
WHERE SOL.CVE_ID_ESTADO_SOLICITUD = 5
AND SOL.CVE_ID_TIPO_SOLICITUD = 50
AND TRAM.CVE_ID_ESTADO_TRAMITE IN (75,37)
AND TRAM.CVE_ID_TIPO_TRAMITE =139
and (MOVA.CVE_ID_TIPO_NSS_ACLARACION  IN (4,5) AND  mova.cve_id_tipo_tram_correc_nss =8)  
AND (MOVA.CVE_ID_ESTADO_MOV_ENV_SINDO IS NULL OR MOVA.CVE_ID_ESTADO_MOV_ENV_SINDO = 2)
AND NSS.CVE_ID_TIPO_NSS =3
AND PER.CURP IS NOT NULL
AND MOVA.fec_registro_baja is null


--======================================================
--- PASO: OdiSqlUnload1 CREA TMP_CDA_MOV06_CIZ01.txt
--======================================================

SELECT 
(CASE WHEN LENGTH(DELORIG) = 1 THEN '0'||DELORIG ELSE DELORIG END) AS DELORIG             
,(CASE WHEN LENGTH(SUBORIG) = 1 THEN '0'||SUBORIG ELSE SUBORIG END) AS SUBORIG             
,CVEAPLIC                  
,TPMOVTO                   
,ORIGENMOV                 
,LPAD(NUMFOLIO,5,'0') AS NUMFOLIO             
,ARGUMENTO                 
,REGPATRON                
,DIGVRPAT                   
,FMOVTO                
,FRECEPMOVI            
,nvl(CVEUNICA,'                  ') as CVEUNICA                     
,IDSUBRSERV                
,IDEVENTUAL                 
,NUMSEGSOC            
,DIGVRNSS              
,RPAD(NOMASEG,50,' ') as NOMASEG               
,IDEXTEMP                
,REDUCPAGO                  
,EXTODEL                    
,SALBASE                   
,SALINFONAVIT              
,TPSALARIO                 
,SEXO                  
,MESNAC                
,nvl(LUGARNAC,'  ') as LUGARNAC              
,UMF                       
,AUTPERM                    
,DELDEST                   
,SUBDEST                   
,TPDERECH                  
,AANAC                     
,SITUACION                  
,TSALODEL                   
,NOMBREDH                
,MESNACAP                 
,NSSCORR                  
,DIGVRNSSCORR              
,RPAD(NOMASEGC,50,' ') as NOMASEGC           
,TPPENS                    
,ALFGUAR                   
,NUMGUAR                   
,CONDICION                 
,LPAD(IDTRAMITE,22,'0')||'                         CDA' AS IDTRAMITE              
,TPPRORROGA                
,FECTERPRORR               
,IDPD                      
from ENVIO_SINDO_SINDO_TMP


--======================================================
--- PASO: OdiSqlUnload1 CREA TMP_CDA_MOV06_CIZ02.txt
--======================================================

SELECT 
(CASE WHEN LENGTH(DELORIG) = 1 THEN '0'||DELORIG ELSE DELORIG END) AS DELORIG             
,(CASE WHEN LENGTH(SUBORIG) = 1 THEN '0'||SUBORIG ELSE SUBORIG END) AS SUBORIG             
,CVEAPLIC                  
,TPMOVTO                   
,ORIGENMOV                 
,LPAD(NUMFOLIO,5,'0') AS NUMFOLIO             
,ARGUMENTO                 
,REGPATRON                
,DIGVRPAT                   
,FMOVTO                
,FRECEPMOVI            
,nvl(CVEUNICA,'                  ') as CVEUNICA             
,IDSUBRSERV                
,IDEVENTUAL                 
,NUMSEGSOC            
,DIGVRNSS              
,RPAD(NOMASEG,50,' ') as NOMASEG               
,IDEXTEMP                
,REDUCPAGO                  
,EXTODEL                    
,SALBASE                   
,SALINFONAVIT              
,TPSALARIO                 
,SEXO                  
,MESNAC                
,nvl(LUGARNAC,'  ') as LUGARNAC              
,UMF                       
,AUTPERM                    
,DELDEST                   
,SUBDEST                   
,TPDERECH                  
,AANAC                     
,SITUACION                  
,TSALODEL                   
,NOMBREDH                  
,MESNACAP                 
,NSSCORR                  
,DIGVRNSSCORR              
,RPAD(NOMASEGC,50,' ') as NOMASEGC           
,TPPENS                    
,ALFGUAR                   
,NUMGUAR                   
,CONDICION                 
,LPAD(IDTRAMITE,22,'0')||'                         CDA' AS IDTRAMITE              
,TPPRORROGA                
,FECTERPRORR               
,IDPD                      
from ENVIO_SINDO_SINDO_TMP

--======================================================
--- PASO: OdiSqlUnload1 CREA TMP_CDA_MOV06_CIZ03.txt
--======================================================

SELECT 
(CASE WHEN LENGTH(DELORIG) = 1 THEN '0'||DELORIG ELSE DELORIG END) AS DELORIG             
,(CASE WHEN LENGTH(SUBORIG) = 1 THEN '0'||SUBORIG ELSE SUBORIG END) AS SUBORIG             
,CVEAPLIC                  
,TPMOVTO                   
,ORIGENMOV                 
,LPAD(NUMFOLIO,5,'0') AS NUMFOLIO             
,ARGUMENTO                 
,REGPATRON                
,DIGVRPAT                   
,FMOVTO                
,FRECEPMOVI            
,nvl(CVEUNICA,'                  ') as CVEUNICA                           
,IDSUBRSERV                
,IDEVENTUAL                 
,NUMSEGSOC            
,DIGVRNSS              
,RPAD(NOMASEG,50,' ') as NOMASEG               
,IDEXTEMP                
,REDUCPAGO                  
,EXTODEL                    
,SALBASE                   
,SALINFONAVIT              
,TPSALARIO                 
,SEXO                  
,MESNAC                
,nvl(LUGARNAC,'  ') as LUGARNAC              
,UMF                       
,AUTPERM                    
,DELDEST                   
,SUBDEST                   
,TPDERECH                  
,AANAC                     
,SITUACION                  
,TSALODEL                   
,NOMBREDH                  
,MESNACAP                 
,NSSCORR                  
,DIGVRNSSCORR              
,RPAD(NOMASEGC,50,' ') as NOMASEGC           
,TPPENS                    
,ALFGUAR                   
,NUMGUAR                   
,CONDICION                 
,LPAD(IDTRAMITE,22,'0')||'                         CDA' AS IDTRAMITE              
,TPPRORROGA                
,FECTERPRORR               
,IDPD                      
from ENVIO_SINDO_SINDO_TMP

--======================================================
--- PASO: SP_UPDDROP_TMP_ENV_SINDO
--======================================================

--- COMANDO: INS_BIT_CDA_SINDO

INSERT INTO DIT_BITACORA_PROC_ENVIO_CDA
SELECT NUMFOLIO,'MOV06', 37, 'ODI', 'SINDO', SYSDATE, NULL, NULL 
FROM ENVIO_SINDO_SINDO_TMP

--- COMANDO: UPDATE DIT_MOV_ACLARACION_NSS_CDA

UPDATE DIT_MOV_ACLARACION_NSS_CDA MOV SET MOV.CVE_ID_ESTADO_MOV_ENV_SINDO = 1 , MOV.FEC_REGISTRO_ACTUALIZADO = SYSDATE, MOV.FEC_MOV_ENV_SINDO = SYSDATE
WHERE EXISTS(
   SELECT IDTRAMITE FROM ENVIO_SINDO_SINDO_TMP ENVIOS
   WHERE ENVIOS.IDTRAMITE = MOV.CVE_ID_MOV_ACLARACION_NSS
  )
  
--- COMANDO: UPDATE_DIT_MOV_ACLARACION_NSS

UPDATE DIT_MOV_ACLARACION_NSS_CDA MOVA SET MOVA.CVE_ID_ESTADO_MOV_ENV_SINDO = 1 , MOVA.FEC_REGISTRO_ACTUALIZADO = SYSDATE, MOVA.FEC_MOV_ENV_SINDO = SYSDATE
--SELECT * FROM DIT_MOV_ACLARACION_NSS_CDA MOVA
where (MOVA.CVE_ID_ESTADO_MOV_ENV_SINDO IS NULL or MOVA.CVE_ID_ESTADO_MOV_ENV_SINDO = 2)
AND MOVA.CVE_ID_TIPO_NSS_ACLARACION IN (1,2)
AND MOVA.FEC_REGISTRO_BAJA IS NULL
AND EXISTS(select MOV.CVE_ID_DETALLE_NSS_CDA 
           from DIT_MOV_ACLARACION_NSS_CDA MOV
          where MOV.CVE_ID_ESTADO_MOV_ENV_SINDO = 1
          AND MOV.CVE_ID_TIPO_NSS_ACLARACION IN (1,2)
          AND TRUNC(MOV.FEC_MOV_ENV_SINDO)  = TRUNC(SYSDATE)
          AND MOVA.CVE_ID_DETALLE_NSS_CDA = MOV.CVE_ID_DETALLE_NSS_CDA
          )

--- COMANDO: UPDATE DIT_TRAMITE

UPDATE DIT_TRAMITE T SET T.CVE_ID_ESTADO_TRAMITE = 37,  T.FEC_REGISTRO_ACTUALIZADO = SYSDATE 
WHERE EXISTS(
SELECT TRAM.CVE_ID_TRAMITE
FROM DIT_TRAMITE TRAM
    INNER JOIN DIT_CORRECCION_DATOS_ASEG DA ON DA.CVE_ID_TRAMITE = TRAM.CVE_ID_TRAMITE
    INNER JOIN DIT_DETALLE_NSS_CDA NSS ON DA.CVE_ID_CORRECCION_DATOS_ASEG = NSS.CVE_ID_CORRECCION_DATOS_ASEG
    INNER JOIN DIT_MOV_ACLARACION_NSS_CDA MOVA ON  MOVA.CVE_ID_DETALLE_NSS_CDA =  NSS.CVE_ID_DETALLE_NSS_CDA
WHERE TRAM.CVE_ID_ESTADO_TRAMITE = 75
    AND TRAM.CVE_ID_TIPO_TRAMITE =139
    AND MOVA.CVE_ID_ESTADO_MOV_ENV_SINDO = 1
    AND TRUNC(MOVA.FEC_MOV_ENV_SINDO) = TRUNC(SYSDATE)
    AND TRAM.CVE_ID_TRAMITE = T.CVE_ID_TRAMITE
  )

--- COMANDO: DROP_ENV_SINDO

DROP TABLE ENVIO_SINDO_SINDO_TMP  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  








