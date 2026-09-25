<%@ include file="../../../general/taglibs.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/messages_es.js" htmlEscape="true" />"></script>

<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/common/comunesCombos.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/desacuerdo/consultaEscritoDesacuerdo.js" htmlEscape="true" />">	
</script>
<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<input type="hidden" id="idDelegacion" value="${usuario.usuarioFuncionario.delegacion.id}" name="idDelegacion" />
<input type="hidden" id="idSubdelegacion" value="${usuario.usuarioFuncionario.subdelegacion.id}" name="idSubdelegacion" />

<div class="contenedor">
	<div class="contenido">
		<div class="introduccion col-sm-12" style="margin-bottom: 15px">
			<div class="titulo">
				<h3>Consulta de escritos de desacuerdo</h3>
				<hr class="red m-b-md">
			</div>
			<div id="error" class="alert alert-danger" style="display:none"></div>
			
		<div class="row">
			<div class="col-sm-9">
			
				<ul class="nav nav-tabs">
				  <li class="active"><a data-toggle="tab" class="panelEscDes" id="escDes1" href="#tab1">Folio de Recepci&oacute;n</a></li>
				  <li><a data-toggle="tab" class="panelEscDes" id="escDes2" href="#tab1">Per&iacute;odo de Presentaci&oacute;n</a></li>
				  <!-- <li><a data-toggle="tab" class="panelEscDes" id="escDes3" href="#tab1">Periodo</a></li> -->
				</ul>
			
				<div class="tab-content">
					<div class="tab-pane active" id="tab1">
						<div class="row" id="opcionesFolioRecepcion">
					  		<div class="col-sm-12">
					  			<label for="folioRecepcion" class="control-label" style="text-align: center;">
					  				<span id="etiquetaBusqueda">Folio de recepci&oacute;n</span><span class="required">*</span>:
					  			</label> 
					  			<input class="form-control paramBusqueda" type="text" id="folioRecepcion" name="folioRecepcion" maxlength="20" placeholder="ED-L-NNNN-NN/NN">
					  			
					  		</div>					  		
					  	</div>

						<div class="row"  id="opcionesRegistroPatronal" style="display:none">
					  		<div class="col-sm-12">
					  			<label for="registroPatronal" class="control-label" style="text-align: center;">
					  				<span id="etiquetaBusqueda">N&uacute;mero Registro Patronal:</span>
									<%--<span class="required">*</span>:--%>
					  			</label> 
					  			<input class="form-control paramBusqueda" type="text" id="registroPatronal" name="registroPatronal" maxlength="10">
					  			
					  		</div>
					  		
						</div>
						<div class="row"  id="opcionesPeriodo" style="display:none">
							<div class="col-sm-6">
					  			<label for="fechaInicio" class="control-label" style="text-align: center;">Fecha Inicio:</label> 
					  			<input class="form-control paramBusqueda" type="text" id="fechaInicio" name="fechaInicio" maxlength="10">
	
							</div>
							<div class="col-sm-6">
					  			<label for="fechaFin" class="control-label" style="text-align: center;">Fecha Fin:</label> 
					  			<input class="form-control paramBusqueda" type="text" id="fechaFin" name="fechaFin" maxlength="10">
	
							</div>
						</div>
						<%-- <c:if test="${usuario.perfilUsuario.idPerfilUsuario != 3}">
						<div class="row" id="delegacionSubdelegacion" style="display:none">
							<c:if test="${usuario.perfilUsuario.idPerfilUsuario != 2}">					  		
					  		<div class="col-sm-6" id="divdelegacion">
					  				<label for="delegacion" class="control-label" style="text-align: center;">Delegaci&oacute;n:</label> 
					  				<select class="form-control" id="delegacion" name="delegacion">
					  					<option value="0">--Selecciona por favor--</option>
					  				</select>
							</div>
							</c:if>					  			
							<div class="col-sm-6" id="divsubdelegacion">
					  				<label for="subdelegacion" class="control-label" style="text-align: center;">Subdelegaci&oacute;n:</label> 
					  				<select class="form-control" id="subdelegacion" name="subdelegacion">
					  					<option value="0">--Selecciona por favor--</option>
					  				</select>							
							</div>
					  	</div>
					  	</c:if> --%>
					</div> 	
				</div>
			</div>
		</div>
		<div class="row" style="margin-top: 15px">
			<div class="col-sm-7 text-left" style="padding-top:10px" id="hideCampoOblig1">
				* Campos obligatorios.
			</div>
			<div class="col-sm-7 text-left hidden" style="padding-top:10px" id="hideCampoOblig2">
				Consulta por NRP, Periodo o Campos vac&iacute;os, este ultimo permite consultar todos los registros pertenecientes a la subdelegaci&oacute;n.
			</div>
			<div class="col-sm-2 text-right">
				<button type="button" id="buscarHistorial" class="btn btn-primary">	
					<span class="glyphicon glyphicon-search"></span> Buscar
				</button>
			</div>
		</div>
	</div>
	<div id="contenedorEscritoDesacuerdoHistorial"></div>
	<div id="mensajes"></div>
	
	
</div>
</div>
