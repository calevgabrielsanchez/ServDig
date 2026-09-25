--============================================================================================================
--Script del flujo "Envio de informacion a SINDO MOVIMIENTO 05" paquete: PKG_LOAD_CDA_MOV05_ENV_PROD
-- Ambiente:                 Prod
-- Autores                   Omar Pérez Chavira 
-- Fecha                     30/Nov/2020
--============================================================================================================


--======================================================
--- PASO: SP_CREA_TMP_ENV_MOV05_ACT
--======================================================
--- COMANDO: DROP TABLE 

DROP TABLE  ENVIO_MOV05_SINDO_TMP

--- COMANDO: CREA_TMP_MOV05

CREATE TABLE ENVIO_MOV05_SINDO_TMP 
TABLESPACE MG_TS_N_DATA_002_TRABAJO 
AS
SELECT  --DELE.CLAVE_DELEGACION AS DELORIG
        --,SUB.CLAVE_SUBDELEGACION AS SUBORIG
        '00' AS DELORIG
        ,'00'  AS SUBORIG
        ,2 AS CVEAPLIC
        ,'05' AS TPMOVTO
        ,'6' AS ORIGENMOV
        ,SUB.CLAVE_SUBDELEGACION||'411' AS NUMFOLIO
        ,'00' AS ARGUMENTO
        ,'          ' AS REGPATRON
        ,0 AS DIGVRPAT
        ,TO_CHAR(SYSDATE,'DDMMYYYY') AS FMOVTO
        ,TO_CHAR(SYSDATE,'DDMMYYYY') AS FRECEPMOVI                                                              
        ,PER.CURP AS CVEUNICA
        ,0 AS IDSUBRSERV
        ,0 AS IDEVENTUAL
        ,SUBSTR(NSS2,1,10) AS NUMSEGSOC1
        ,SUBSTR(NSS2,11,1) AS DIGVRNSS1
        , TRANSLATE(UPPER(PER.AP_PATERNO||'$'||PER.AP_MATERNO ||'$' || PER.NOMBRE), 'ÑÁÉÍÓÚÜÖ', '#AEIOU'||chr(220)||'O') AS NOMASEG
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
        ,SUBSTR(NSS.NUM_NSS,1,10) AS NUMSEGSOC
        ,SUBSTR(NSS.NUM_NSS,11,1) AS DIGVRNSS
        ,TRANSLATE(UPPER(PER.AP_PATERNO||'$'||PER.AP_MATERNO ||'$' || PER.NOMBRE), 'ÑÁÉÍÓÚÜÖ', '#AEIOU'||chr(220)||'O') AS NOMASEGC
        ,'00' AS TPPENS
        ,' ' AS ALFGUAR
        ,'006' AS NUMGUAR
        ,'0' AS CONDICION
        ,MOVA.CVE_ID_MOV_ACLARACION_NSS AS IDTRAMITE
        ,'0' AS TPPRORROGA
        ,'00000000' AS FECTERPRORR
        ,'0' AS IDPD
        ,DELE.CVE_CIZ AS CVE_CIZ
FROM DIT_SOLICITUD SOL
INNER JOIN DIT_TRAMITE TRAM ON SOL.CVE_ID_SOLICITUD = TRAM.CVE_ID_SOLICITUD  
INNER JOIN DIT_CORRECCION_DATOS_ASEG DA ON DA.CVE_ID_TRAMITE = TRAM.CVE_ID_TRAMITE  
INNER JOIN DIT_DETALLE_NSS_CDA NSS ON DA.CVE_ID_CORRECCION_DATOS_ASEG = NSS.CVE_ID_CORRECCION_DATOS_ASEG
INNER JOIN DIT_MOV_ACLARACION_NSS_CDA MOVA ON  MOVA.CVE_ID_DETALLE_NSS_CDA =  NSS.CVE_ID_DETALLE_NSS_CDA
INNER JOIN DIC_SUBDELEGACION SUB ON SOL.CVE_ID_SUBDELEGACION = SUB.CVE_ID_SUBDELEGACION
INNER JOIN DIC_DELEGACION DELE ON SUB.CVE_ID_DELEGACION = DELE.CVE_ID_DELEGACION
INNER JOIN  (
            SELECT SOL.CVE_ID_SOLICITUD AS CVE_ID_SOL2, NSS.NUM_NSS AS NSS2  
            FROM DIT_SOLICITUD SOL
            INNER JOIN DIT_TRAMITE TRAM ON SOL.CVE_ID_SOLICITUD = TRAM.CVE_ID_SOLICITUD
            INNER JOIN DIT_CORRECCION_DATOS_ASEG DA ON DA.CVE_ID_TRAMITE = TRAM.CVE_ID_TRAMITE
            INNER JOIN DIT_DETALLE_NSS_CDA NSS ON DA.CVE_ID_CORRECCION_DATOS_ASEG = NSS.CVE_ID_CORRECCION_DATOS_ASEG
            WHERE NSS.CVE_ID_TIPO_NSS IN (1)
            ) SOL3 ON SOL3.CVE_ID_SOL2 = SOL.CVE_ID_SOLICITUD
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
                        inner join DIT_CORRECCION_DATOS_ASEG aseg on aseg.CVE_ID_CORRECCION_DATOS_ASEG = nss.CVE_ID_CORRECCION_DATOS_ASEG
                        inner join DIT_PERSONA p on p.CURP = aseg.REF_CURP 
                    )   ANSS
                    group by ANSS.NUM_NSS) ANSS ON SOL3.NSS2 = ANSS.NUM_NSS */
/*INNER JOIN DIT_ASIGNACION_NSS ANSS ON SOL3.NSS2 = ANSS.NUM_NSS*/
INNER JOIN D_CANASE_PPCANA01 PER ON PER.NSS = NSS.NUM_NSS
WHERE 1=1
AND SOL.CVE_ID_ESTADO_SOLICITUD = 5
AND SOL.CVE_ID_TIPO_SOLICITUD = 50
AND TRAM.CVE_ID_ESTADO_TRAMITE IN(75,37)
AND TRAM.CVE_ID_TIPO_TRAMITE =139
AND MOVA.CVE_ID_TIPO_TRAM_CORREC_NSS = 4
AND (MOVA.CVE_ID_ESTADO_MOV_ENV_SINDO IS NULL OR MOVA.CVE_ID_ESTADO_MOV_ENV_SINDO = 2)
AND NSS.CVE_ID_TIPO_NSS =2
AND mova.fec_registro_baja is null
AND MOVA.IND_ENV_SINDO = '1'
AND NOT EXISTS(
            SELECT SOL2.CVE_ID_SOLICITUD
            FROM DIT_SOLICITUD SOL2 
            INNER JOIN DIT_TRAMITE TRAM ON SOL2.CVE_ID_SOLICITUD = TRAM.CVE_ID_SOLICITUD
            INNER JOIN DIT_CORRECCION_DATOS_ASEG DA ON DA.CVE_ID_TRAMITE = TRAM.CVE_ID_TRAMITE
            INNER JOIN DIT_DETALLE_NSS_CDA NSS ON DA.CVE_ID_CORRECCION_DATOS_ASEG = NSS.CVE_ID_CORRECCION_DATOS_ASEG
            INNER JOIN DIT_MOV_ACLARACION_NSS_CDA MOVA ON  MOVA.CVE_ID_DETALLE_NSS_CDA =  NSS.CVE_ID_DETALLE_NSS_CDA
            WHERE SOL2.CVE_ID_ESTADO_SOLICITUD = 5
            AND SOL.CVE_ID_SOLICITUD = SOL2.CVE_ID_SOLICITUD
            AND TRAM.CVE_ID_TIPO_TRAMITE = 139
            AND TRAM.CVE_ID_ESTADO_TRAMITE IN(75,37)
            AND (MOVA.CVE_ID_TIPO_NSS_ACLARACION IN (1,2) OR MOVA.CVE_ID_TIPO_TRAM_CORREC_NSS IN(1,2))
            AND (MOVA.CVE_ID_ESTADO_MOV_ENV_SINDO IS NULL or  MOVA.CVE_ID_ESTADO_MOV_ENV_SINDO != 3)
            AND NSS.CVE_ID_TIPO_NSS IN (1,2)
            AND MOVA.fec_registro_baja is null
            )


--======================================================
--- PASO: generaCIZ1
--======================================================

SELECT 
(CASE WHEN length(DELORIG) = 1 THEN '0'||DELORIG ELSE DELORIG END) AS DELORIG                   
,(CASE WHEN length(SUBORIG) = 1 THEN '0'||SUBORIG ELSE SUBORIG END) AS SUBORIG              
,CVEAPLIC                
,TPMOVTO                 
,ORIGENMOV               
,LPAD(NUMFOLIO,5,'0') AS NUMFOLIO           
,ARGUMENTO               
,REGPATRON              
,DIGVRPAT                 
,FMOVTO                 
,FRECEPMOVI           
,NVL(CVEUNICA,'                  ') as CVEUNICA            
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
,LUGARNAC            
,UMF                     
,AUTPERM                  
,DELDEST                 
,SUBDEST                
,TPDERECH                
,AANAC                   
,SITUACION                
,TSALODEL                 
,NVL(NOMBREDH,'                                                  ') AS NOMBREDH               
,MESNACAP                
,NUMSEGSOC1    
,DIGVRNSS1      
,RPAD(NOMASEGC,50,' ') as NOMASEGC          
,TPPENS                  
,ALFGUAR                 
,NUMGUAR                 
,CONDICION               
,LPAD(IDTRAMITE,22,'0')||'                         CDA' AS IDTRAMITE           
,TPPRORROGA              
,FECTERPRORR             
,IDPD                     
FROM ENVIO_MOV05_SINDO_TMP

--======================================================
--- PASO: generaCIZ2
--======================================================

SELECT 
(CASE WHEN length(DELORIG) = 1 THEN '0'||DELORIG ELSE DELORIG END) AS DELORIG                   
,(CASE WHEN length(SUBORIG) = 1 THEN '0'||SUBORIG ELSE SUBORIG END) AS SUBORIG              
,CVEAPLIC                
,TPMOVTO                 
,ORIGENMOV               
,LPAD(NUMFOLIO,5,'0') AS NUMFOLIO           
,ARGUMENTO               
,REGPATRON              
,DIGVRPAT                 
,FMOVTO                          
,FRECEPMOVI          
,NVL(CVEUNICA,'                  ') as CVEUNICA              
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
,LUGARNAC            
,UMF                     
,AUTPERM                  
,DELDEST                 
,SUBDEST                
,TPDERECH                
,AANAC                   
,SITUACION                
,TSALODEL                 
,NVL(NOMBREDH,'                                                  ') AS NOMBREDH                
,MESNACAP                
,NUMSEGSOC1    
,DIGVRNSS1      
,RPAD(NOMASEGC,50,' ') as NOMASEGC          
,TPPENS                  
,ALFGUAR                 
,NUMGUAR                 
,CONDICION               
,LPAD(IDTRAMITE,22,'0')||'                         CDA' AS IDTRAMITE            
,TPPRORROGA              
,FECTERPRORR             
,IDPD                     
FROM ENVIO_MOV05_SINDO_TMP

--======================================================
--- PASO: generaCIZ3
--======================================================

SELECT 
(CASE WHEN length(DELORIG) = 1 THEN '0'||DELORIG ELSE DELORIG END) AS DELORIG                   
,(CASE WHEN length(SUBORIG) = 1 THEN '0'||SUBORIG ELSE SUBORIG END) AS SUBORIG              
,CVEAPLIC                
,TPMOVTO                 
,ORIGENMOV               
,LPAD(NUMFOLIO,5,'0') AS NUMFOLIO           
,ARGUMENTO               
,REGPATRON              
,DIGVRPAT                 
,FMOVTO               
,FRECEPMOVI           
,NVL(CVEUNICA,'                  ') as CVEUNICA            
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
,LUGARNAC            
,UMF                     
,AUTPERM                  
,DELDEST                 
,SUBDEST                
,TPDERECH                
,AANAC                   
,SITUACION                
,TSALODEL                 
,NVL(NOMBREDH,'                                                  ') AS NOMBREDH                
,MESNACAP                
,NUMSEGSOC1    
,DIGVRNSS1      
,RPAD(NOMASEGC,50,' ') as NOMASEGC          
,TPPENS                  
,ALFGUAR                 
,NUMGUAR                 
,CONDICION               
,LPAD(IDTRAMITE,22,'0')||'                         CDA' AS IDTRAMITE            
,TPPRORROGA              
,FECTERPRORR             
,IDPD                     
FROM ENVIO_MOV05_SINDO_TMP

--======================================================
--- PASO: SP_UPDDROP_TMP_ENV_MOV05
--======================================================

--- COMANDO: INS_BIT_CDA

INSERT INTO MGPBDTU9X.DIT_BITACORA_PROC_ENVIO_CDA
SELECT NUMFOLIO,'MOV05', 37, 'ODI', 'SINDO', SYSDATE, NULL, NULL 
FROM MGPBDTU9X.ENVIO_MOV05_SINDO_TMP

--- COMANDO: UPDATE DIT_MOV_ACLARACION_NSS_CDA

UPDATE DIT_MOV_ACLARACION_NSS_CDA MOV SET MOV.CVE_ID_ESTADO_MOV_ENV_SINDO = 1 , MOV.FEC_REGISTRO_ACTUALIZADO = SYSDATE, MOV.FEC_MOV_ENV_SINDO = SYSDATE
WHERE EXISTS(
   SELECT IDTRAMITE FROM ENVIO_MOV05_SINDO_TMP ENVIOS
  WHERE ENVIOS.IDTRAMITE = MOV.CVE_ID_MOV_ACLARACION_NSS
  )

--- COMANDO: UPDATE DIT_TRAMITE

-- ACTUALIZACIÓN DEL TRAMITE AL GENERAR LOS ARCHIVOS 5  
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

--- COMANDO: DROP_ENV_MOV05

DROP TABLE ENVIO_MOV05_SINDO_TMP 
  
  
