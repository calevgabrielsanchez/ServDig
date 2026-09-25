<%@ include file="../general/taglibs.jsp"%>
		<c:if test="${usuarioObj.perfilUsuario.idPerfilUsuario == 1}">
		<script type="text/javascript">
			$(document).ready(
				function() {
					var requisitos = ${requisitos};
					if(requisitos == 1) {
						$('#resultado').html(mensajeInformativo("Aprobado"));
						//$('#razon').html(mensajeInformativo("${razones}"));
					} else {
						$('#resultado').html(mensajeError("No Aprobado"));
						//$('#razon').html(mensajeInformativo("${razones}"));
					}
				}	
			);
			
			function mensajeInformativo(mensaje) {
				var mensajeA = '<div class="ui-widget">' +
				'<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> ' +
				'<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>' +
				'<strong>' + mensaje + '</strong></p></div></div>';
				
				return mensajeA;
			}

			function mensajeError(mensaje) {
				
				var mensajeR = '<div class="ui-widget">' +
				'<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">'+
				'<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>' +
				'<strong>' + mensaje + '</strong></p></div></div>';
				
				return mensajeR;
			}

		</script>
		</c:if>
		<fieldset><legend><strong><spring:message
			code="tramite.detalle.tituloRegistro" /></strong></legend>
		<table>
			<tr>
				<td align="left"><spring:message code="tramite.detalle.parentesco" />: </td>
				<td><input type="text" readonly="readonly" value="${registro.parentesco.descripcion}" style="width: 150px" /></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td colspan="2"></td>
			</tr>
			<tr>
				<td align="left"><spring:message code="tramite.detalle.nombre" />: </td>
				<td><input type="text" readonly="readonly" style="width: 150px" value="${registro.fisica.nombre}" /></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.estadoCivil" />: </td>
				<td><input type="text" readonly="readonly" value="${registro.fisica.estadoCivil.descripcion}" style="width: 150px" /></td>
			</tr>
			<tr>
				<td align="left"><spring:message code="tramite.detalle.aPaterno" />: </td>
				<td><input type="text" readonly="readonly" value="${registro.fisica.primerApellido}" style="width: 150px" /></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.curp" />: </td>
				<td><input type="text" readonly="readonly" value="${registro.fisica.curp}" style="width: 150px" /></td>
			</tr>
			
			<tr>
				<td align="left"><spring:message code="tramite.detalle.aMaterno" />: </td>
				<td><input type="text" readonly="readonly" value="${registro.fisica.segundoApellido}" style="width: 150px" /></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.fNacimiento" />: </td>
				<td><input type="text" readonly="readonly" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${registro.fisica.fechaNacimiento}"/>" style="width: 150px" /></td>
			</tr>
			<tr>
				<td align="left"><spring:message code="tramite.detalle.lNacimiento" /> : </td>
				<td><input type="text" readonly="readonly" value="${registro.fisica.lugarNacimiento.nombre}" style="width: 150px" /></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.sexo" />: </td>
				<td><input type="text" readonly="readonly" value="${registro.fisica.sexo.descripcion}" style="width: 150px" /></td>
			</tr>
		<!-- 
		<c:if test="${registro.tipoTramite.idTipoTramite == 46 || registro.tipoTramite.idTipoTramite ==49}">				
			<tr>
				<td align="left"><spring:message code="tramite.detalle.resultadoCuestionario" /> : </td>
				<td><input type="text" readonly="readonly" value="${registro.evaluacionCuestionario}" style="width: 150px" /></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.evaluador" />: </td>
				<td><input type="text" readonly="readonly" value="" style="width: 150px" /></td>
			</tr>
		</c:if> -->
			
			<tr>
				<td align="left"><spring:message code="tramite.detalle.tipoRegistro" />: </td>
				<td colspan="5"><input type="text" readonly="readonly" value="${registro.razonRegistro.descripcion}" style="width: 570px" /></td>
			</tr>
			
		</table>
		</fieldset>
		<c:if test="${usuarioObj.perfilUsuario.idPerfilUsuario == 1}">
		<fieldset><legend><strong>Requisitos Minimos</strong></legend>
		<table>
			<tr align="center">
				<td>Resultado: </td>
				<td><div id="resultado"></div></td>
			</tr>
			<!--
			<tr align="center">
				<td>Razon: </td>
				<td><div id="razon"></div></td>
			</tr>
		-->
		</table>
		</fieldset>
		</c:if>
		
				
	