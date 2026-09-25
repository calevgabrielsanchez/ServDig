<!-- JSP Contenido de listado de personas para tramites. -->
<%@ include file="../general/taglibs.jsp"%>

<input type="hidden" id="tipoTramite" name="tipoTramite" value="${tipoTramite}"/>
<input type="hidden" id="nss" name = "nss" value = "${nss}"/>
<input type="hidden" id="seleccionMultiple" value="${seleccionMultiple}"/>

<div class="contenedor col-sm-12">
<c:if test="${empty error }">
<c:choose>
	<c:when test="${not empty grupoFamiliar }">
		<div class="separadorseccion">
			<span> Candidatos a ${descripcionTipoTramite}</span>
		</div>
		<div class="alert alert-info">
			<c:if test="${seleccionMultiple eq 0}">
				Selecciona al integrante del grupo familiar que ser&aacute; afectado por el tr&aacute;mite  y da clic en Aceptar.
			</c:if>
			<c:if test="${seleccionMultiple eq 1}">
				Selecciona al o los integrantes del grupo familiar que ser&aacute;n afectados por el tr&aacute;mite  y da clic en Aceptar.
			</c:if>
		</div>
		<div id="grupoFamiliarWrapper" style="width: 100%; margin: 0 auto;">
			<table id="tblIntegrantesGrupoFamiliar" style="width: 100%;" class="table table-striped table-bordered" cellpadding="0"
				cellspacing="0" border="0">
				<thead>
					<tr>
						<th>Seleccionar</th>
						<th>Parentesco</th>
						<th>Nombre</th>
						<th>CURP</th>
						<th>Sexo</th>
						<th>Edad</th>
						<th>Situaci&oacute;n</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${grupoFamiliar}" var="integrante" varStatus="indice">
						<tr>
							<td>
								<c:if test="${seleccionMultiple eq 0}">
									<input type="radio" style="width: 20px" id="candidato" onclick="getCurpFromList('${integrante.derechohabiente.curp}');mostrarAvisoPrivacidad('${tipoTramite}','${integrante.parentesco.idParentesco}');" name="candidato" value="${integrante.derechohabiente.idPersona}" />
								</c:if>
								<c:if test="${seleccionMultiple eq 1}">
									<input type="checkbox" style="width: 20px" id="candidato" name="candidato" value="${integrante.derechohabiente.idPersona}">
								</c:if>
							</td>
							<td>
								${integrante.parentesco.descripcion}
							</td>
							<td>
								${integrante.derechohabiente.nombre} ${integrante.derechohabiente.primerApellido} ${integrante.derechohabiente.segundoApellido}
							</td>
							<td>
								${integrante.derechohabiente.curp}
							</td>
							<td>
								${integrante.derechohabiente.sexo.descripcion}
							</td>
							<td>
								<fmt:formatDate pattern="yyyy-MM-dd" value="${integrante.derechohabiente.fechaNacimiento}"/>
							</td>
							<td>
								${integrante.estadoDerechohabiente.descripcion}
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<br>
		<c:if test="${seleccionMultiple eq 1 }">
		<div align="center">
			<strong>
				<input type="checkbox" style="width: 20px" id="checkearTodos" value="">
				Seleccionar todos
			</strong>
		</div>
		</c:if>
		<br><br>
		<c:if test="${tipoTramite == 24 || tipoTramite == 6}">
		
			<div class="alert alert-info" style="margin-top: 20px; display: none" id="avisoPrivacidadCorreccion">
				<p><strong>Aviso de privacidad simplificado</strong></p>
				<p>
				La recolecci&oacute;n de datos personales se lleva a cabo a trav&eacute;s de la p&aacute;gina 
				electr&oacute;nica <a href="${mvn.avisos.contexto}/portal-web/portal"
				target="_blank">${mvn.avisos.contexto}/portal-web/portal</a> cuyo administrador y responsable del tratamiento 
				es la Coordinaci&oacute;n de Clasificaci&oacute;n de Empresas y Vigencia de Derechos del Instituto Mexicano del Seguro Social. 
				Los datos personales que se recaban ser&aacute;n utilizados con la finalidad de llevar a cabo el tr&aacute;mite de
				Actualizaci&oacute;n de datos personales 
				<span id="homoclaveTramite"></span>
				</p>
				
				<p>
 				Si deseas conocer nuestro aviso de privacidad integral, lo podr&aacute;s consultar en el portal: 
				<a href="${mvn.avisos.contexto}/gestionAsegurados-web-externo/avisoCorreccion.jsp"
				target="_blank">${mvn.avisos.contexto}/gestionAsegurados-web-externo/avisoCorreccion.jsp</a>
				</p>
			</div>
		</c:if>
	</c:when>
	<c:otherwise>
		<div class="container-fluid empty-state">
			<div class="row">
				<!-- Imagen -->
				<div class="col-xs-12 imagen">
					<i class="glyphicon glyphicon-exclamation-sign"></i>
				</div>
			</div>

			<div class="row">
				<!-- Titulo -->
				<div class="col-xs-12 titulo">
					Usted no cuenta con ningun candidato para este tr&aacute;mite.
				</div>
			</div>
			
			</br>
		</div>
		
	</c:otherwise>
</c:choose>
</c:if> 
<c:if test="${not empty error}">
	<div class="container-fluid empty-state">
			<div class="row">
				<!-- Imagen -->
				<div class="col-xs-12 imagen">
					<i class="glyphicon glyphicon-exclamation-sign"></i>
				</div>
			</div>

			<div class="row">
				<!-- Titulo -->
				<div class="col-xs-12 titulo">
					${error}
				</div>
			</div>
			
			</br>
	</div>
</c:if>
</div>
<script id="initPortlet">


	$('#tblIntegrantesGrupoFamiliar').dataTable({
		"bDestroy": true,
		"bLengthChange": false,
		"sPaginationType": "bootstrap",
		"aoColumnDefs": [
		     			{ "fnRender": function ( oObj ) {				
		     				return calcularEdad(oObj.aData[5]);
		     			}, "aTargets": [ 5 ] },
		     			{ "bSortable": false, "aTargets": [ 5 ] }
		     			
		     		]
	});
	
	function calcularEdad(fechaNac) {
 	   //RNGD0029 Edad de una persona física
 	   try{
 		   var fechaParseada = Date.parse(fechaNac);
 		   //console.debug("fecha de nacimiento del integrante IE: %s",fechaParseada);
     	   var fecha = new Date(fechaParseada);
     	   //console.debug("fecha de nacimiento al aplicarle el parser ISO8601: %s", fecha);
     	   var hoy = new Date();
     	   //console.debug("fecha de nacimiento del integrante: %s, fecha de hoy: %s",fechaNac,hoy);
     	   var ed = datediff(hoy, fecha);
     	   //console.debug("diferencias entre edad %s" , ed[0]);
			   return ed[0];
			 
 	   }catch(err){
 		   return "SIN EDAD";
 	   }
	}
	
	function datediff(date1, date2) {
		 
		var y1 = date1.getFullYear(), m1 = date1.getMonth(), d1 = date1.getDate(),
		y2 = date2.getFullYear(), m2 = date2.getMonth(), d2 = date2.getDate();
		
		//console.debug("Fechas 1: %s,%s,%d,%s,%s,%s",y1,m1,d1,y2,m2,d2);

		if (d1 < d2) {
				m1--;
				d1 += DaysInMonth(y2, m2);
		}
		if (m1 < m2) {
				y1--;
				m1 += 12;
		}
		
		return [y1 - y2, m1 - m2, d1 - d2];
	}


	function DaysInMonth(Y, M) {
			with (new Date(Y, M, 1, 12)) {
				setDate(0);
				return getDate();
			}
	}
</script>