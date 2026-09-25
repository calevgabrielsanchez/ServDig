<!-- JSP Contenido del Portlet de Representados Legales. -->
<%@ include file="../../general/taglibs.jsp"%>

<c:choose>
	<c:when test="${not empty grupoFamiliar }">
		<div id="grupoFamiliarWrapper" style="width: 100%; margin: 0 auto;">
			<table id="tblIntegrantesGrupoFamiliar" style="width: 100%;" class="table table-striped table-bordered" cellpadding="0"
				cellspacing="0" border="0">
				<thead>
					<tr>
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
								${integrante.parentesco.descripcion}
							</td>
							<td>
									<a href="#" class="integranteGrupoFamiliar link" id="${integrante.derechohabiente.idPersona}"
									nombre="${integrante.derechohabiente.nombre}" 
									primerApellido="${integrante.derechohabiente.primerApellido}" 
									segundoApellido="${integrante.derechohabiente.segundoApellido}" 
									curpIntegrante="${integrante.derechohabiente.curp}"
									idParentesco = "${integrante.parentesco.idParentesco}"
									parentesco="${integrante.parentesco.descripcion}"
									estadoDerechohabiente="${integrante.estadoDerechohabiente.idEstadoDerechohabiente}">
									
										${integrante.derechohabiente.nombre} ${integrante.derechohabiente.primerApellido} ${integrante.derechohabiente.segundoApellido}
									</a>
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
			<c:choose>
				<c:when test="${isPensionadoMod17Convenio}">
					<c:if test="${asegurado.conDerechoSm == 'NO'}">
						<div class="row" style="">
							<div class="col-sm-12" style="">    
								<span style="float: right; font-size: 16px;"><strong>Sin derecho al servicio m&eacute;dico</strong></span>
							</div>
						</div>
					</c:if>
				</c:when>
				<c:otherwise>
					<c:if test="${!isMod17Convenio}">
						<c:if test="${asegurado.conDerechoSm == 'NO'}">
							<div class="row" style="">
								<div class="col-sm-12" style="">    
									<span style="float: right; font-size: 16px;"><strong>Sin derecho al servicio m&eacute;dico</strong></span>
								</div>
							</div>
						</c:if>
					</c:if>
				</c:otherwise>
			</c:choose>				
		</div>
	</c:when>
	<c:otherwise>
		<div class="row-fluid empty-state">
			<div class="row-fluid">
				<!-- Imagen -->
				<div class="span12 imagen">
					<i class="glyphicon glyphicon-exclamation-sign"></i>
				</div>
			</div>

			<div class="row-fluid">
				<!-- Titulo -->
				<div class="span12 titulo">
					<c:if test="${empty error }">
						<spring:message code="label.portlet.sin.resultados.grupoFamiliar" />
					</c:if>
					<c:if test="${not empty error }">
						${error }
					</c:if>
				</div>
			</div>
						
			<!-- Opciones del empty state, si en el properties de opciones estan activas, aqui es donde se pondran -->
			<div id="opcNavEmptyStateGrupoFamiliar">
				
			</div>

		</div>
		
	</c:otherwise>
</c:choose>

<div id="integranteGrupo" style="display: none;">
			<form id="formIntegranteGrupo" name="formIntegranteGrupo" method="post" action="/portal-web/portal/derechohabiente/ingresar">
				<input type="hidden" id="asignacionNSS.idPersona" name="asignacionNSS.idPersona" value="${asignacionNSS.idPersona}">
				<input type="hidden" id="asignacionNSS.idAsignacionNSS" name="asignacionNSS.idAsignacionNSS" value ="${asignacionNSS.idAsignacionNSS}">
				<input type="hidden" id="asignacionNSS.nss" name="asignacionNSS.nss" value = "${asignacionNSS.nssStr}">
				<input type="hidden" id="asignacionNSS.nombre" name="asignacionNSS.nombre" value = "${asignacionNSS.nombre}">
				<input type="hidden" id="asignacionNSS.primerApellido" name="asignacionNSS.primerApellido" value = "${asignacionNSS.primerApellido}">
				<input type="hidden" id="asignacionNSS.segundoApellido" name="asignacionNSS.segundoApellido" value = "${asignacionNSS.segundoApellido}">
				<input type="hidden" id="asignacionNSS.curp" name="asignacionNSS.curp" value = "${asignacionNSS.curp}">
				
				<input type="hidden" id="asignacionNSS.fechaRegistro" name="asignacionNSS.fechaRegistro" value = "">
				<input type="hidden" id="asignacionNSS.fechaBaja" name="asignacionNSS.fechaBaja" value = "">
 				<input type="hidden" id="asignacionNSS.sexo.idSexo" name="asignacionNSS.sexo.idSexo" value = ""/>
 				<input type="hidden" id="asignacionNSS.estadoCivil.idEstadoCivil" name="asignacionNSS.estadoCivil.idEstadoCivil" value = ""/>
 				
				<input type="hidden" id="derechohabiente.idPersona" name="derechohabiente.idPersona" value="">
				<input type="hidden" id="derechohabiente.nombre" name="derechohabiente.nombre" value = "">
				<input type="hidden" id="derechohabiente.primerApellido" name="derechohabiente.primerApellido" value = "">
				<input type="hidden" id="derechohabiente.segundoApellido" name="derechohabiente.segundoApellido" value = "">
				<input type="hidden" id="derechohabiente.curp" name="derechohabiente.curp" value = "">
				<input type="hidden" id="parentesco.idParentesco" name="parentesco.idParentesco" value = "">
				<input type="hidden" id="parentesco.descripcion" name="parentesco.descripcion" value = "">
				<input type="hidden" id="estadoDerechohabiente.idEstadoDerechohabiente" name="estadoDerechohabiente.idEstadoDerechohabiente" value = "">
			</form>
</div>
<script id="initPortlet">


$('.integranteGrupoFamiliar').live( 'click' , function(){
	
	//console.debug("Se presiona el link de derechohabiente");
	var idIntegrante = $(this).attr('id');
	
	<c:if test="${mostrarOpciones eq 0}">
		var idAsignacionNssForm = $("#asignacionNSS\\.idAsignacionNSS").val();
		var nssStrForm = $("#asignacionNSS\\.nss").val();
		
		WizardDetalleDerechohabienteCtrl.init('divDetalleDerechohabiente',nssStrForm,idAsignacionNssForm,idIntegrante);
		WizardDetalleDerechohabienteCtrl.abrir();
		
	</c:if>
	<c:if test="${mostrarOpciones eq 1}">
	var nombre = $(this).attr('nombre');
	var primerApellido = $(this).attr('primerApellido');
	var segundoApellido = $(this).attr('segundoApellido');
	var curpIntegrante = $(this).attr('curpIntegrante');
	var idParentesco = $(this).attr('idParentesco');
	var parentescoIntegrante = $(this).attr('parentesco');
	var estadoDerechohabiente = $(this).attr('estadoDerechohabiente');
	
	var idEstadoAsegurado = $("#hdnIdEstadoDerechohabiente").val();
	var fechaInicioVigencia = $("#hdnFechaInicioVigencia").val();
	var fechaFinVigencia = $("#hdnFechaFinVigencia").val();
	var parentescoA = $("#hdnIdParentesco").val();

	
	$("#derechohabiente\\.idPersona").val(idIntegrante);
	$("#derechohabiente\\.nombre").val(nombre);
	$("#derechohabiente\\.primerApellido").val(primerApellido);
	$("#derechohabiente\\.segundoApellido").val(segundoApellido);
	$("#derechohabiente\\.curp").val(curpIntegrante);
	$("#parentesco\\.idParentesco").val(idParentesco);
	$("#parentesco\\.descripcion").val(parentescoIntegrante);
	$("#estadoDerechohabiente\\.idEstadoDerechohabiente").val(estadoDerechohabiente);
	$("#asignacionNSS\\.fechaRegistro").val(fechaInicioVigencia);
	$("#asignacionNSS\\.fechaBaja").val(fechaFinVigencia);
	$("#asignacionNSS\\.sexo\\.idSexo").val(parentescoA);
	$("#asignacionNSS\\.estadoCivil\\.idEstadoCivil").val(idEstadoAsegurado);
	
	
		//console.debug("El id de la persona es: %s, El idAsignacionNss : %s",idIntegrante);
		$.blockUI();
		document.getElementById('formIntegranteGrupo').submit();
	</c:if>
});


	$('#tblIntegrantesGrupoFamiliar').dataTable({
		"bDestroy": true,
		"bLengthChange": false,
		"sPaginationType": "bootstrap",
		"bSort": false,
		"aoColumnDefs": [
		     			{ "fnRender": function ( oObj ) {				
		     				return calcularEdad(oObj.aData[4]);
		     			}, "aTargets": [ 4 ] }
		     		]
	});
	

	function calcularEdad(fechaNacimiento) {

		var edad = 'SIN EDAD';

		if (fechaNacimiento != null && fechaNacimiento != "") {
			//console.log("fecha de nacimiento sin formatear" + fechaNacimiento);
			var fecha = fechaNacimiento.toString().split("-");
		    var dia = fecha[2];
		    var mes = fecha[1];
		    var anio = fecha[0];

			// cogemos los valores actuales
			var fecha_hoy = new Date();
			var ahora_anio = fecha_hoy.getFullYear();
			var ahora_mes = fecha_hoy.getMonth() + 1;
			var ahora_dia = fecha_hoy.getDate();

			//console.log("loas nios son " + ahora_anio +" y nac " +anio);
			// realizamos el calculo
			var edad = ahora_anio - anio;
			//console.log("los meses son: " + ahora_mes + " y mes " + mes);
			//console.log("los dias son: " + ahora_dia + " y dia " + dia);

			if (ahora_mes < mes) {
				edad--;
			} else if ((mes == ahora_mes) && (ahora_dia < dia)) {
				edad--;
			}
		}

		return edad;
	}
</script>