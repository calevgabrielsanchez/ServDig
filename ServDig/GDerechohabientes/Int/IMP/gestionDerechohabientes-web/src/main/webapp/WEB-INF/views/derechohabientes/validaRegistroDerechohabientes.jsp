<%@ include file="/WEB-INF/views/general/taglibs.jsp" %>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<%@ include file="/WEB-INF/views/general/GuiaTramite/guiaTramiteImport.jsp" %> 
<link rel="stylesheet" type="text/css"	href="<spring:url value="/static/resources/estilos/imss/dialogStyle.css" htmlEscape="true" />" />
	
	<script type="text/javascript">
			var nombre = '${fisica.nombre}';  
			var primerApe = '${fisica.primerApellido}';
			var segundoApe = '${fisica.segundoApellido}';
			var fechaNac = '${fisica.fechaNacimiento}';
			var curp = '${fisica.curp}';
			var idLugarNacimiento = '${fisica.lugarNacimiento.clave}';
			var idSexo = '${fisica.sexo.idSexo}';
			var fechaNacForm = '${fisica.fechaNacimientoFormateada}'; 
	</script>
	
	<c:if test="${requiereDocs}">
		<%@ include file="/WEB-INF/views/general/fileUpload/FileUploadImport.jsp" %>
	</c:if>
	
 	<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/gestionCtrlSelect.js" htmlEscape="true" />"></script>
	<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/guiaTramite/guiaTramite.js" htmlEscape="true" />"></script>
	<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/validaRegDer.js" htmlEscape="true" />"></script>
	<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
	<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>		
	<script type="text/javascript" src="/${mvn.web.app.root}/resources/derechohabiente/js/cuestionario/js/cuestionario.js"></script>
	<script type="text/javascript" src="/${mvn.web.app.root}/resources/derechohabiente/js/cuestionario/js/corroborarCuestionario.js"></script>
	<%@ include file="/WEB-INF/views/general/impresionDocumentosImport.jsp" %>

	<script>

		 $(document).ready(function() {
		 	
			 var perfil=$("#perfil").val();
			 var patron = $("#patron").val();
			 var idRazonRegistro = $("#idRazonRegistro").val();

			/*
			if(patron=="0"){
				if(($("#parentesco").val() == "1" || $("#parentesco").val() == "4")){								
					$("#cuestionarios").show();
				}
			}else{
				if($("#parentesco").val() == "4"){
					$("#cuestionarios").show();
				}
			} */
			
			if(idRazonRegistro == "1"){
				$("#RazonR").hide();
				$("#tipoRegistro").hide();
				
			}else{
				if(idRazonRegistro == "8"){
					$("#tipoRegistro").show();
				}else{
					$("#tipoRegistro").hide();
				}					
				$("#RazonR").show();
				$("#tipoRegistro").hide();
			}
			
			validateForm.allowOnlyRegularExpression( $('.entero_15'),regularExpression.entero_15);
			validateForm.allowOnlyRegularExpression( $('.entero_4'),regularExpression.entero_4);
			validateForm.allowOnlyRegularExpression( $('.alfanumerico_espacios'),regularExpression.alfanumerico_espacios);  
		});
		
	</script>
	
		
<div class="form-comment">	
	<br><br><br><br><br><br>	
	<input id="requiereDocs" type="hidden" value="${!requiereDocs? 0 : 1}"/>
	<input id="perfil" type="hidden" value="${usuarioObj.perfilUsuario.idPerfilUsuario}"/>
	<input id="curpCap" type="hidden" value="${curpCap}"/>
	<input type="hidden" id="patron" name="patron" value="${patronImss}"/>
	<input type="hidden" id="cambio" name="cambio" value="${cambio}"/>
	
	<form:form commandName="validacion"  id="frmValidacion1" name="frmValidacion1" action="#" method="POST">		
		<jsp:include page="/WEB-INF/views/general/encabezadoGF.jsp"/>									
		<fieldset style="align:ceter" style="width: 890px">				
			<table>
				<tr id="RazonR">			
					<th>
						<spring:message code="label.registro"/>:
					</th>
					<td>		
						<form:input style="display:none" path="idRazonRegistro" value="${validacion.idRazonRegistro}"/>				
						<form:input path="razonRegistro" style="width: 150px" readonly="readonly" value="${validacion.razonRegistro}"/>
					</td>
				</tr>
				<tr id="tipoRegistro">
					<th>
						<spring:message code="label.registro.tipo"/>:
					</th>
					<td>		
						<form:input style="display:none" path="tipoRegistro.idRazonRegistro" value="${validacion.tipoRegistro.idRazonRegistro}"/>				
						<form:input path="tipoRegistro.descripcion" style="width: 150px" readonly="readonly" value="${validacion.tipoRegistro.descripcion}"/>
					</td>
				</tr>
			</table>			
		</fieldset>		
	</form:form>
</div>			
	<c:if test="${requiereDocs}">
		<jsp:include page="/WEB-INF/views/general/fileUpload/fileUpload.jsp"/>	
		<div id="msgDocumentosProb" title ="<spring:message code="titulo.mensajeAviso"/>" style="display:none"> 
			<spring:message code="msgDocumentosProb"/>		
		</div>
	</c:if>
	
	<form:form commandName="validacion"  id="frmValidacion" name="frmValidacion" action="/${mvn.web.app.root}/derechohabientes/registro/actualizar" method="POST">		
		<form:input path="idSolicitud" type="hidden" value="${validacion.idSolicitud}"/>			
			<form:input path="idRazonRegistro" type="hidden" value="${validacion.idRazonRegistro}"/>	
			<form:input path="razonRegistro" type="hidden" value="${validacion.razonRegistro}"/>			
			<form:input path="tipoTramite" type="hidden" value="${validacion.tipoTramite}"/>
			<form:input path="idTramite" type="hidden" value="${validacion.idTramite}"/>
			<form:input path="parentesco" type="hidden" value="${validacion.parentesco}"/>								
			<form:input path="tipoRegistro.idRazonRegistro" type="hidden" value="${validacion.tipoRegistro.idRazonRegistro}"/>				
			<form:input path="tipoRegistro.descripcion" type="hidden" value="${validacion.tipoRegistro.descripcion}"/>
			<form:input path="domDif" type="hidden" value="${validacion.domDif}"/>
			<form:input path="umfAsegurado" type="hidden" value="${validacion.umfAsegurado}"/>
			<form:input path="umfNvoIntegrante" type="hidden" value="${validacion.umfNvoIntegrante}"/>
			<form:input path="fechaCambioUmf" type="hidden" value="${validacion.fechaCambioUmf}"/>
		
		<!-- 
		<div id="cuestionarios" style="display:none">		
			
			<input type="hidden" id="capturado" name="capturado">			
						
			<fieldset style="align:center" style="width: 890px">	
				<legend><b><spring:message code="label.titulo.cuestionario"/></b></legend>						
				<br>		
				<table style="width: 860px">					
					<tr>
						<td align="center">
							<input type="button" onclick="getPDF(${validacion.idTramite});" class="mboton" id="imprimir" value="<spring:message code="button.imprimir.cuestionario"/>" >						 
							<input type="button" onclick="getCuestionario(${validacion.idTramite});" class="mboton" id="capturar" value="<spring:message code="button.capturar"/>">							
							<input type="button" onclick="getVistaCuestionario(${validacion.idTramite});" class="mboton" id="resultado" value="<spring:message code="button.corrobora"/>" >
						<td> 					
					</tr>
				</table>
				<br>
			</fieldset>	
		</div>	 -->	
		<div id="servicioMedico" class="form-comment">
			<fieldset style="align:ceter" style="width: 890px">
				<legend><b><spring:message code="label.titulo.servicio"/></b></legend>
				<table class="page_holder_no_height" style="width: 860px">
					<tr>
						<td>
							<spring:message code="tramite.detalle.umf"/>
						</td>
						<td>
							<form:input path="medicoEnTurno.unidadMedicaFamiliar.idUMF" type="hidden" value="${medicoEnTurno.unidadMedicaFamiliar.idUMF}"/>
							<form:input path="medicoEnTurno.unidadMedicaFamiliar.nombreCorto" value="${medicoEnTurno.unidadMedicaFamiliar.nombreCorto}"/>
						</td>
					</tr>
					<tr>
						<td>
							<spring:message code="label.turno"/>
						</td>						
						<td>							
							<form:input path="medicoEnTurno.turno.descripcion" value="${medicoEnTurno.turno.descripcion}"/>
							<form:input path="medicoEnTurno.turno.idTurno" value="${medicoEnTurno.turno.idTurno}" type="hidden"/>							
							<div id="comboTurno">
							<combo:creaCombo idHtml="turno"
									idHtmlContenedor="frmValidacion"
									entidad="mx.gob.imss.ctirss.delta.persistence.DicTurno"
									mostrarSoloActivos = "true" />
							</div>
							<div id="msgConsultorio" title ="<spring:message code="titulo.mensajeAviso"/>" style="display:none"> 
								<spring:message code="msgConsultorio"/>		
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<spring:message code="label.consultorio"/>
						</td>
						<td>
							<form:input path="medicoEnTurno.consultorio.descripcion" value="${medicoEnTurno.consultorio.descripcion}"/>
							<form:input path="medicoEnTurno.consultorio.idConsultorio" type="hidden"/>
							<select id="consultorio"
									name="consultorio">
										<option value="">--POR FAVOR SELECCIONE--</option>
							</select>
							<div id="msgTurno" title ="<spring:message code="titulo.mensajeAviso"/>" style="display:none"> 
								<spring:message code="msgTurno"/>		
							</div>							
						</td>
					</tr>
					<tr>						
						<td>
							<spring:message code="label.medico"/>
						</td>						
						<td>
							<input id="medicoEnTurno.medicoFamiliar.nombre" style="width: 300px" value="${validacion.medicoEnTurno.medicoFamiliar.nombre} ${validacion.medicoEnTurno.medicoFamiliar.primerApellido} ${validacion.medicoEnTurno.medicoFamiliar.segundoApellido} "/>
							<form:input path="medicoEnTurno.idMedicoContultorioTurno" type="hidden"/>
							<select id="medico"
									name="medico">
										<option value="">--POR FAVOR SELECCIONE--</option>
							</select>							
							<div id="msgMedico" title ="<spring:message code="titulo.mensajeAviso"/>" style="display:none"> 
								<spring:message code="msgMedico"/>		
							</div>	
							<div id="msgConfirmaMedico" title ="<spring:message code="titulo.mensajeAviso"/>" style="display:none"> 
								<spring:message code="msgConfirmaMedico"/>		
							</div>								
						</td>
					</tr>																			
					<tr>
						<td>
							<spring:message code="label.observaciones"/>
						</td>
						<td>
						<form:textarea path="observaciones" style="width: 450px" rows="3"/>
						</td>
					</tr>
				</table>
			</fieldset>
		</div>
		<br><br>		
		<table style="width: 870px">
			<tr>				
				<td align="center">													
					<input type="button" id="registrar" onclick="valDatos();" name="registrar" class="mboton" value="<spring:message code="button.registrar"/>"/>
					<!--  input type="button" onclick="cancela();" id="regresar" name="regresar" class="mboton" value="<spring:message code="button.regresar"/>"/-->
					<input type="button"  id="rechazarTramite" name="rechazarTramite" class="mboton" value="<spring:message code="button.cancelar"/>"/>  <!--onclick="rechazarSolicitud(${validar.solicitud});"-->						
					<!-- Parametros  TipoTramite, Rol (Aqui Siempre Tramitador)-->
					<input type="button" id="guia" name="guia" onclick="showGuiaTramite(44,1)" value="<spring:message code="button.guiaTramite"/>" class="mboton"/>
					<div id=msgCuestionario title ="<spring:message code="titulo.mensajeAviso"/>" style="display:none"> 
						<spring:message code="msgCuestionario"/>		
					</div>							
				</td>			
			</tr>		
		</table>
	</form:form>
	<div id="razonRechazo" title ="<spring:message code="titulo.mensajeConfirmacion"/>" style="display:none">
		<spring:message code="msg12"/>
		<table>
			<tr>
				<td>
					<spring:message code="label.rechazo"/>:
				</td>
				<td>
					<select id="idRazon">						
						<option value="1">DOCUMENTOS PROBATORIOS INCOMPLETOS</option>
						<option value="2">DOCUMENTOS AP&Oacute;CRIFOS</option>
						<option value="3">IMPROCEDENCIA</option>
						<option value="4" selected="selected">CONVIVENCIA-DEPENDENCIA NO COMPROBADAS</option>
					</select>
				</td>
			</tr>		
		</table>
	</div>
	<table>		
	</table>
	
	<c:if test="${requiereDocs}">
		<script type="text/javascript">		
			if($("#umfNvoIntegrante").val() != $("#umfAsegurado")){
				loadFileUpload(${validacion.tipoTramite},'${lnuevosDocsRequeridos}');
			}else{
				loadFileUpload(${validacion.tipoTramite});
			}
		</script>
	</c:if>