-- Verificar y resolver primero los NSS que ya tengan mas de un registro por dia:
SELECT TRIM(CVE_NSS) NSS, TRUNC(FEC_CONSULTA) DIA, COUNT(*) REGISTROS
FROM OPERBDTU.BDTUT_ULTIMO_TRABAJO
GROUP BY TRIM(CVE_NSS), TRUNC(FEC_CONSULTA)
HAVING COUNT(*) > 1;

-- Ejecutar solo despues de resolver los duplicados. Evita inserciones
-- simultaneas para el mismo NSS y dia que la consulta JPA no puede prevenir.
CREATE UNIQUE INDEX OPERBDTU.UX_BDTUT_NSS_DIA
ON OPERBDTU.BDTUT_ULTIMO_TRABAJO (TRIM(CVE_NSS), TRUNC(FEC_CONSULTA));
