<%@ include file="../general/taglibs.jsp"%>

<style>
	DIV#loadera {  border: 1px solid #ccc;  width: 225px;  height: 300px;}
	DIV#loadera.loading {  background: url("${staticResourcesPath}/imagenes/loading.gif") no-repeat center center;}
</style>

<c:choose>
<c:when test="${empty errores}">

<table  style="width: 100% !important; font-size: smaller;"
			class="table table-bordered table-striped" cellpadding="0" cellspacing="0"
			border="0">
	<tr>
		<td align="center" rowspan="10">
			<!-- div id="loadera" align="center" class="loading" >
			</div-->
			<div id="sinImg" style="display: none" >
				<img height="250px" width="200px" alt="Sin Imagen" src="<spring:url value="/static/resources/imagenes/sinImagen.gif" htmlEscape="true" />" title="Portal IMSS"/>
			</div>
		</td>
		<td><strong>NSS</strong></td>
		<c:if test="${derechohabiente.asignacionNSS.pensionado}">
			<td colspan="2" align ="left"><strong>Tipo pensi&oacute;n</strong></td>
		</c:if>
		<c:if test="${not derechohabiente.asignacionNSS.pensionado}">
			<td colspan="2"></td>
		</c:if>
	</tr>
	<tr>
		<td>${derechohabiente.asignacionNSS.nssStr}</td>
		<c:if test="${derechohabiente.asignacionNSS.pensionado}">
			<td align ="left">${derechohabiente.asignacionNSS.tipoPension}</td>
		</c:if>
		<c:if test="${not derechohabiente.asignacionNSS.pensionado}">
			<td></td>
		</c:if>
		<c:if test="${patronImss || isPatronJCF}">
			<td>
				<c:if test="${patronImss}">
					<strong>CCT 74 IMSS</strong> 
				</c:if>
				<c:if test="${isPatronJCF}">
					<strong>STPS JCF</strong>
				</c:if>
			</td>
		</c:if>
		<c:if test="${!patronImss && !isPatronJCF}">
			<td></td>
		</c:if>
	</tr>
	<tr>
		<td><strong>Nombre(s)</strong></td>
		<td><strong>Primer apellido</strong></td>
		<td><strong>Segundo apellido</strong></td>
	</tr>
	<tr>
		<td>
			${derechohabiente.derechohabiente.nombre}
		</td>
		<td>
			${derechohabiente.derechohabiente.primerApellido}
		</td>
		<td>
			${derechohabiente.derechohabiente.segundoApellido}
		</td>
	</tr>
	<tr>
		<td><strong>Fecha de nacimiento</strong></td>
		<td><strong>Lugar de nacimiento</strong></td>
		<td><strong>CURP</strong></td>
	</tr>
	<tr>
		<td><fmt:formatDate pattern="dd/MM/yyyy" value="${derechohabiente.derechohabiente.fechaNacimiento}"/></td>
		<td>${derechohabiente.derechohabiente.lugarNacimiento.nombre}</td>
		<td>${derechohabiente.derechohabiente.curp}</td>
	</tr>
	<tr>
		<td><strong>Parentesco</strong></td>
		<td><strong>Calidad</strong></td>
		<td><strong>Agregado m&eacute;dico</strong></td>
	</tr>
	<tr>
		<td>${derechohabiente.parentesco.descripcion}</td>
		<td>${derechohabiente.calidad}</td>
		<td>${derechohabiente.agregadoMedico}</td>
	</tr>
	<tr>
		<td><strong>Sexo</strong></td>
		<td colspan="2" align ="left"><strong>Estado civil</strong></td>
	</tr>
	<tr>
		<td>${derechohabiente.derechohabiente.sexo.descripcion}</td>
		<td colspan="2" align ="left">${derechohabiente.derechohabiente.estadoCivil.descripcion}</td>
	</tr>
	<tr>
		<td><strong>UMF</strong></td>
		<td><strong>Turno</strong></td>
		<td><strong>Consultorio</strong></td>
		<td><strong>M&eacute;dico</strong></td>
	</tr>
	<tr>
		<td>${derechohabiente.medicoEnTurno.unidadMedicaFamiliar.descripcion} - ${derechohabiente.medicoEnTurno.unidadMedicaFamiliar.nombreCorto}</td>
		<td>${derechohabiente.medicoEnTurno.turno.descripcion}</td>
		<td>${derechohabiente.medicoEnTurno.consultorio.descripcion}</td>
		<td>${derechohabiente.medicoEnTurno.medicoFamiliar.nombre} ${derechohabiente.medicoEnTurno.medicoFamiliar.primerApellido} ${derechohabiente.medicoEnTurno.medicoFamiliar.segundoApellido}</td>
	</tr>
	<tr>
		<td><strong>Estado</strong></td>
		<td><strong>Sub - estado</strong></td>
		<td><strong>Inicio vigencia</strong></td>
		<td><strong>Fin vigencia</strong></td>
	</tr>
	<tr>
		<td>${derechohabiente.estadoDerechohabiente.descripcion}</td>
		<td>${derechohabiente.subEstadoDerechohabiente.descripcion}</td>
		<td><fmt:formatDate  pattern="dd/MM/yyyy"  value="${derechohabiente.fechaInicioVigencia}"/></td>
		<td><fmt:formatDate  pattern="dd/MM/yyyy"  value="${derechohabiente.fechaFinVigencia}"/></td>
	</tr>
	
	<tr>
		<c:choose>
			<c:when test="${isPensionadoMod17Convenio}">
				<td colspan="4"><span style="align: center"><strong>Con derecho al servicio m&eacute;dico ${derechohabiente.conDerechoSm} </strong> <br></span></td>
			</c:when>
			<c:otherwise>
				<td colspan="4"><span style="align: center"><strong>Con derecho al servicio m&eacute;dico ${derechohabiente.conDerechoSm} </strong> <br></span></td>
			</c:otherwise>
		</c:choose>
	</tr>
	
	
	<tr>
		<td colspan="2"><strong>Delegaci&oacute;n</strong></td>
		<td colspan="2"><strong>Subdelegaci&oacute;n</strong></td>
	</tr>
	<tr>
		<td colspan="2">${derechohabiente.medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.descripcion}</td>
		<td colspan="2">${derechohabiente.medicoEnTurno.unidadMedicaFamiliar.subdelegacion.descripcion}</td>
	</tr>
	<tr>
		<td colspan="4"><span style="align: center"><strong>Medios de contacto del ${fn:toLowerCase(derechohabiente.parentesco.descripcion)}</strong> <br></span></td>
	</tr>
	<tr>
		<td colspan="2"><strong>E-mail</strong></td>
		<td colspan="2"><strong>Tel&eacute;fono</strong></td>
	</tr>
	<tr>
		<td colspan="2">${derechohabiente.derechohabiente.correoElectronico.correo}&nbsp;</td>
		<td colspan="2">${derechohabiente.derechohabiente.telefonoFijo.claveLada}&nbsp;${derechohabiente.derechohabiente.telefonoFijo.numero}</td>
	</tr>
	<!-- 
	<c:if test="${not isAsegurado}">
		<jsp:include page="detalleDomicilio.jsp"></jsp:include>
	</c:if>
	 -->
</table>

<!--
<script type="text/javascript">
$(document).ready(function(){
	cargarImagen();
});

function cargarImagen() {
	var imagen = $("<img>");
	var calidadDer = '${derechohabiente.calidad}';
	var url = "";
	var nombre = "/${derechohabiente.derechohabiente.nombre}_${derechohabiente.derechohabiente.primerApellido}_${derechohabiente.derechohabiente.segundoApellido}";
	
	if( calidadDer.length != 0 ){
		if(calidadDer == 1)
			url = "/${mvn.web.app.root}/derechohabientesImg/getFotografiaAsegurado/${derechohabiente.calidad}/${derechohabiente.asignacionNSS.nssStr}";
		else
			url = "/${mvn.web.app.root}/derechohabientesImg/getFotografiaDerechohabiente/${derechohabiente.calidad}/${derechohabiente.asignacionNSS.nssStr}"+nombre;

					
		imagen.attr("src",url).load(function() {
				$('#loadera').removeClass('loading');
				$('#loadera').html("");
				$('#loadera').append(imagen);
			}	
		).error(function() {
				$('#loadera').removeClass('loading');
				$('#loadera').hide();
				$('#sinImg').show();
			}	
		).css({
			width:'225px',
		 	height:'300px'
		});
	}else{

		$('#loadera').removeClass('loading');
		$('#loadera').hide();
		$('#sinImg').show();
		
	}
}
</script>
-->
<script type="text/javascript">
$(document).ready(function(){
	cargarImagen();
});
function cargarImagen() {
	$('#sinImg').show();
	}
</script>

</c:when>
<c:otherwise>
	<div class="ui-widget-content ui-corner-all">
	<div class="ui-state-error ui-corner-all" align="center">
	<p class="ui-helper-reset ui-state-error-text"><div class="ui-icon ui-icon-alert"></div><spring:message code="${errores}"></spring:message></p>
	</div>
	</div>
</c:otherwise>
</c:choose>	