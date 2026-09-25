<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../../general/taglibs.jsp"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/afiliacion/datosContacto.js" htmlEscape="true" />"></script>

<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.PropietarioMedioContactoEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum"%>

<script>
	var tramiteDatosContactoActivo = ${tramiteDatosContactoActivo};
	var tramiteDatosContactoRatificado = ${tramiteDatosContactoRatificado};
	var idSolicitud='${idSolicitud}' == '' ? 0 : '${idSolicitud}';
	var mc;
			
	var tpPropietario = null;
	var idPropietario = null;
	<c:if test="${bFisica}">
		tpPropietario = <%=PropietarioMedioContactoEnum.PERSONA_FISICA.getCodigo()%>;
		idPropietario = ${sujetoObligado.fisica.idPersona};
	</c:if>
	<c:if test="${!bFisica}">
		tpPropietario = <%=PropietarioMedioContactoEnum.PERSONA_MORAL.getCodigo()%>;
		idPropietario = ${sujetoObligado.moral.cveMoral};
	</c:if>
	
	var idSujetoOblgiado = 0;
	var tipoTramiteDatosContacto='<%=TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO%>';	
</script>

<div id="mediosContactoContenedor"></div>


<div id="divDatosContactoBoton">
	<form>
		<table style="margin: 0px; width: 100%; border: none !important;">
			<tr>
				<td style="border: none !important;">
					<input type="button" id="btnModificarDatosContacto" class="mboton" style="width:200px;" 
						onclick="modificarDatosContacto();" value="Modificar">
					<input type="button" id="btnGuardarDatosContacto" class="mboton" style="width:200px;" 
						onclick="validarDC();" value="Guardar">
				</td>
				<td style="border: none !important;" align="right">
					<div id="grupoRatificarDC">
						<!-- 
						<input type="checkbox" name="chkRatificaDC" id="chkRatificaDC" onclick="ratificaDT();" />
						<b><spring:message code="label.ratificar"/></b>
						 -->
					</div>
				</td>				
			</tr>
			<tr>
				<td colspan="2" align="center" style="border: none !important;" >
					<div id="mesajeRatificacionDatosContacto">
						<legend class="legendaConfirmacion">
							La informaci&oacute;n del tr&aacute;mite ha sido ratificada 
						</legend>
					</div>
				</td>
			</tr>
		</table>
	</form>		
</div>

<script language="javascript">
	function validarDC(){
		if(validaMediosContactoRequeridosDeRFC(mc.obtenerListaMediosContacto())){
			var rfcEnviarValidacion;
			if (tipoPersonaFiscal == "FISICA") {
				rfcEnviarValidacion=$("#fisica\\.rfc").val();
			}else{
				rfcEnviarValidacion=$("#moral\\.rfc").val();
			}
			validaSolicitudTramiteActivo('/afiliacion/validarTramiteActivo?tipoTramite='+datosContacto+'&idSolicitud='+idSolicitud+'&rfc='+rfcEnviarValidacion,callbackActualizacionContactoTramite);
		}
	}
</script>

