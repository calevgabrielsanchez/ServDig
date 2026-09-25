--============================================================================================================
--Script del flujo "Comprobantes Fiscales" paquete: PROC_COMPROBANTES_FISCALES_V3
-- Ambiente:                 Prod
-- Autores                   Omar Pérez Chavira 
-- Fecha                     06/Ene/2021
--============================================================================================================


--======================================================
--- PASO: Inserta Fecha Proceso Inicio
--======================================================
--- COMANDO: Limpia Fecha Proceso Inicio

DELETE FROM PROCESO_ODI_FECHA_PROC WHERE PROCESO = 'PROC_COMPROBANTES_FISCALES'

--- COMANDO: Fecha Proceso Inicio

INSERT INTO PROCESO_ODI_FECHA_PROC (FEC_PROCESO,PROCESO,ESTATUS_PROCESO) 
VALUES (TO_DATE('#varFechaInputProceso','YYMMDD'),'PROC_COMPROBANTES_FISCALES','INICIO')


--======================================================
--- PASO: Carga Tabla Temp
--======================================================
--- COMANDO: Borrar Tabla Temp

DROP TABLE #varNombreTablaCompFisc CASCADE CONSTRAINTS

--- COMANDO: UPD_REF_OBSERVACION_MOVCI

CREATE TABLE  #varNombreTablaCompFisc
(
   CVE_REGISTRO    NUMBER,
   NRP           VARCHAR2 (11),
   RFC           VARCHAR2 (13),
   NOMBRE        VARCHAR2 (50),
   PER           NUMBER,
   FOLSUA        NUMBER,
   SUBTOTIMSS    NUMBER,
   RECIMSS       NUMBER,
   ACTIMSS       NUMBER,
   SUBTOTRCV     NUMBER,
   RECRCV        NUMBER,
   ACTRCV        NUMBER,
   FECPAGO       DATE,
   ER            NUMBER,
   ESTATUS       VARCHAR2 (2),
   CFDI_XML      NCLOB,
   UUID          VARCHAR2 (30),
   ODI_ESTATUS    VARCHAR2 (30),
   FEC_PROCESO   DATE,
   FEC_ARCHIVO   DATE,
   FEC_INICIO DATE,
   FEC_FIN DATE
)

--======================================================
--- PASO: Carga Tabla Final y Renombra
--======================================================


--- COMANDO: Borra Tabla Final

DELETE FROM PROC_ODI_COMP_FISC cp
WHERE EXISTS
  (SELECT 1
  FROM #varNombreTablaCompFisc  cp2
  WHERE cp.CVE_REGISTRO = cp2.CVE_REGISTRO
  )

--- COMANDO: Carga Tabla Final

INSERT INTO PROC_ODI_COMP_FISC 
 (
CVE_REGISTRO,
         NRP,
         RFC,
         NOMBRE,
         PER,
         FOLSUA,
         SUBTOTIMSS,
         RECIMSS,
         ACTIMSS,
         SUBTOTRCV,
         RECRCV,
         ACTRCV,
         FECPAGO,
         ER,
         ESTATUS,
         CFDI_XML,
         UUID,
         ODI_ESTATUS,
         FEC_PROCESO,
         FEC_ARCHIVO,
         FEC_INICIO,
         FEC_FIN
)
    SELECT 
        A.CVE_REGISTRO, 
        A.NRP, 
        A.RFC, 
        A.NOMBRE, 
        A.PER, 
        A.FOLSUA, 
        A.SUBTOTIMSS, 
        A.RECIMSS, 
        A.ACTIMSS, 
        A.SUBTOTRCV, 
        A.RECRCV, 
        A.ACTRCV, 
        A.FECPAGO, 
        A.ER, 
        A.ESTATUS, 
        A.CFDI_XML, 
        A.UUID, 
        A.ODI_ESTATUS, 
        TO_DATE('#varFechaProceso','YYMMDD'),--A.FEC_PROCESO, 
        A.FEC_ARCHIVO, 
        SYSDATE,--A.FEC_INICIO, 
        A.FEC_FIN
   FROM  #varNombreTablaCompFisc A


--======================================================
--- PASO: Bitacora
--======================================================

--- COMANDO:  Borrar en bitacora

DELETE FROM BITACORA_ODI_PROC_ARQ
WHERE FEC_PROCESO = TO_DATE('#varFechaProceso','YYMMDD')
AND PROCESO = 'PROC_COMPROBANTES_FISCALES'


--- COMANDO: Inserta Bitacora

INSERT INTO BITACORA_ODI_PROC_ARQ (FEC_PROCESO, FEC_EJECUCION, PROCESO, OBJETO, AGRUPADOR,REGISTROS)
SELECT TO_DATE('#varFechaProceso','YYMMDD'),SYSDATE,'PROC_COMPROBANTES_FISCALES','#varArchivoCompFisc','I', COUNT(1) TOTAL
FROM #varNombreTablaCompFisc

--======================================================
--- PASO: Limpia Fecha Proceso Fin
--======================================================

--- COMANDO: Fecha Proceso Fin

DELETE FROM PROCESO_ODI_FECHA_PROC WHERE PROCESO = 'PROC_COMPROBANTES_FISCALES'


--======================================================
--- PASO: Borra Tabla de Carga
--======================================================

--- COMANDO: Elimina tabla

DROP TABLE #varNombreTablaCompFisc CASCADE CONSTRAINTS