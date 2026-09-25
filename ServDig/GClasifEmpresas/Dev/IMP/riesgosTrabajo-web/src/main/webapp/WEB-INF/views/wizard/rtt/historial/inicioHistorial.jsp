<%@ include file="../../../general/taglibs.jsp"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/common/comunesCombos.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/rtt/inciarRtt.js" htmlEscape="true" />">	</script>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<div class="contenedor">
	<div class="contenido">
		<div class="introduccion col-sm-12" style="margin-bottom: 15px">
			<div class="titulo">
				<h3>Historial de riesgos de trabajo</h3>
				<hr class="red m-b-md">
			</div>
			<div id="error" class="alert alert-danger" style="display:none"></div>
			<div class="alert alert-info" id="infoPeriodoActual">Nota: La consulta en los meses enero, febrero y marzo corresponde a la informaci&oacute;n al 31 de diciembre del periodo de revisi&oacute;n inmediato anterior.
			</div>
		<div class="row">
			<div class="col-sm-9">
			
				<ul class="nav nav-tabs">
				  <li class="active"><a data-toggle="tab" class="panelRtt" id="rtt1" href="#tab1">N&uacute;mero Registro Patronal</a></li>
				  <li><a data-toggle="tab" class="panelRtt" id="rtt2" href="#tab1">Nombre o Raz&oacute;n Social</a></li>
				  <li><a data-toggle="tab" class="panelRtt" id="rtt3" href="#tab1">R.F.C.</a></li>
				  <li><a data-toggle="tab" class="panelRtt" id="rtt4" href="#tab1">R.F.C. Total</a></li>
				  <li><a data-toggle="tab" class="panelRtt" id="rtt5" href="#tab1">NSS</a></li>
				</ul>
			
				<div class="tab-content">
					<div class="tab-pane active" id="tab1">
					  	<div class="row">
					  		<div class="col-sm-6">
					  			<label for="parametroBusqueda" class="control-label" style="text-align: center;">
					  				<span id="etiquetaBusqueda">N&uacute;mero Registro Patronal</span><span class="required">*</span>:
					  			</label> 
					  			<input class="form-control paramBusqueda" type="text" id="parametroBusqueda" name="parametroBusqueda" maxlength="10">
					  			<input type="hidden" id="idDelegacion" value="${usuario.usuarioFuncionario.delegacion.id}"/>
					  			<input type="hidden" id="idSubdelegacion" value="${usuario.usuarioFuncionario.subdelegacion.id}"/>
					  		</div>
					  		<div class="col-sm-6">
					  			<label for="periodoBusqueda" class="control-label" style="text-align: center;">Periodo de revisi&oacute;n:</label> 
					  			<select class="form-control" id="periodoBusqueda" name="periodoBusqueda">
					  				<option value="0">--Selecciona por favor--</option>
					  			</select>
							</div>
					  	</div>
					  	<div class="row" style="display:none" id="opcionesNombre">
					  		<div class="col-sm-12">
								<div class="radio">
									<label for="nombreRS" style="margin-right: 10px">
										<input type="radio" class="col-xs-0" name="busquedaNRS" value="begins" checked="checked"> Inicio
									</label> 
									<label for="nombreRS">
										<input type="radio" class="col-xs-0" name="busquedaNRS" value="like"> Contiene
									</label> 
								</div>
							</div>
						</div>
						

					  	<div class="row" id="delegacionSubdelegacion">
					  		<div class="col-sm-6" id="divdelegacion">
					  			<label for="delegacion" class="control-label" style="text-align: center;">Delegaci&oacute;n:</label> 
					  			<select class="form-control" id="delegacion" name="delegacion">
					  				<option value="0">--Selecciona por favor--</option>
					  			</select>
							</div>
							<div class="col-sm-6" id="divsubdelegacion">
					  			<label for="subdelegacion" class="control-label" style="text-align: center;">Subdelegaci&oacute;n:</label> 
					  			<select class="form-control" id="subdelegacion" name="subdelegacion">
					  				<option value="0">--Selecciona por favor--</option>
					  			</select>
							</div>
					  	</div>
					</div>
			</div>
		</div>
		

		</div>
		<div class="row" style="margin-top: 15px">
			<div class="col-sm-4 text-left" style="padding-top:10px">
				* Campos obligatorios.
			</div>
			<div class="col-sm-5 text-right">
				<button type="button" class="btn btn-link" id="abrirVisor">Consulta de solicitudes</button>
				<button type="button" id="buscarHistorial" class="btn btn-primary">	
					<span class="glyphicon glyphicon-search"></span> Buscar
				</button>
			</div>
		</div>
	</div>
	<div id="contenedorRiegosTrabajoHistorial"></div>
	<div id="mensajes">
	</div>
</div>
</div>