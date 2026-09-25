<%@ include file="../../general/taglibs.jsp"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

  <script type="text/javascript" src="<spring:url value="/static/resources/js/delta/ampliacionRegistroEventuales/contenido.js" htmlEscape="true" />"></script> 

<div class="contenedor container-fluid">

<!-- 	<div class="alert alert-info" style="width: 100%;">
		<h4>Datos de los Registros Eventuales Modalidad 14:</h4>
	</div> -->

	<div class="separadorseccion">
		<span> Datos de los Registros Eventuales Modalidad 14</span>
	</div>

	<form:form id="formIniciaTramite" method="post"
		modelAttribute="sujetoTramite"
		action="/delta-gestionPatronal-web/wizard/tramite
				 	/ampliacion/trabajadores/eventuales/crear/solicitud">

		<div class="row">
			<div class="col-xs-4">
				<div class="form-group">
					<label> N&uacute;mero de Registro Patronal</label>
					<div>
						<form:input path="numeroRegistroPatronal"
							id="numeroRegistroPatronal" class= "form-control input-sm" readonly="true" />
					</div>
				</div>
			</div>
			<div class="col-xs-4">
				<div class="form-group">
					<label >Modalidad</label>
					<div>
						<form:input path="modalidad.numModalidad"
							id="modalidad.numModalidad" class= "form-control input-sm" 
							readonly="true"/>
					</div>
				</div>
			</div>
			<div class="col-xs-4">
				<div class="form-group">
					<label>Digito Verificador</label>
					<div>
						<form:input path="digVerificador" id="digVerificador" class= "form-control input-sm" 
						readonly="true"/>
					</div>
				</div>

			</div>

		</div>
		<div class="row">
			<div class="col-xs-4">
				<div class="form-group">
					<label>N&uacute;mero Divisi&oacute;n</label>
					<div >
						<form:input
							path="clasificacion.fraccion.grupo.division.numDivision"
							id="clasificacion.fraccion.grupo.division.numDivision" class= "form-control input-sm" 
							readonly="true"/>
					</div>
				</div>
			</div>
			<div class="col-xs-4">
				<div class="form-group">
					<label>N&uacute;mero
						Grupo</label>
					<div>
						<form:input path="clasificacion.fraccion.grupo.numGrupo"
							id="clasificacion.fraccion.grupo.numGrupo" class= "form-control input-sm" 
							readonly="true"/>
					</div>

				</div>
			</div>
			<div class="col-xs-4">
				<div class="form-group">
					<label>N&uacute;mero Fracci&oacute;n</label>
					<div >
						<form:input path="clasificacion.fraccion.numFraccion"
							id="clasificacion.fraccion.numFraccion" class= "form-control input-sm" 
							readonly="true"/>
					</div>

				</div>

			</div>
		</div>
		<div class="row">
			<div class="col-xs-4">
				<div class="form-group">
					<label >Descripci&oacute;n</label>
					<div>
						<form:input path="clasificacion.fraccion.numFraccion"
							id="clasificacion.fraccion.numFraccion" class= "form-control input-sm" 
							readonly="true"/>
					</div>

				</div>

			</div>
			<div class="col-xs-4">
				<div class="form-group">
					<label >Persona Fisica</label>
					<div>
						<form:input path="tipoPersonaFiscal" id="tipoPersonaFiscal" class= "form-control input-sm" 
						readonly="true"/>
					</div>

				</div>

			</div>


		</div>
	</form:form>
	
</div>

<div class="contenedor container-fluid">

<!-- 	<div class="alert alert-info" style="width: 100%;">
		<h4>Datos de los Registros Eventuales Modalidad 14:</h4>
	</div> -->

	<!-- SE DEBE PRRESENTAR LA TABLA CON LOS NRP-->
	
	<div class="separadorseccion">
		<span> Registro Patronales </span>
	</div>

	<form:form id="formIniciaTramite" method="post"
		modelAttribute="sujetoTramite"
		action="/delta-gestionPatronal-web/wizard/tramite
				 	/ampliacion/trabajadores/eventuales/crear/solicitud">

		<div class="row">
			<div class="col-xs-4">
				<div class="form-group">
					<label> N&uacute;mero de Registro Patronal</label>
					<div>
						<form:input path="numeroRegistroPatronal"
							id="numeroRegistroPatronal" class= "form-control input-sm" readonly="true" />
					</div>
				</div>
			</div>
			<div class="col-xs-4">
				<div class="form-group">
					<label >Modalidad</label>
					<div>
						<form:input path="modalidad.numModalidad"
							id="modalidad.numModalidad" class= "form-control input-sm" 
							readonly="true"/>
					</div>
				</div>
			</div>
			<div class="col-xs-4">
				<div class="form-group">
					<label>Digito Verificador</label>
					<div>
						<form:input path="digVerificador" id="digVerificador" class= "form-control input-sm" 
						readonly="true"/>
					</div>
				</div>

			</div>

		</div>
		<div class="row">
			<div class="col-xs-4">
				<div class="form-group">
					<label>N&uacute;mero Divisi&oacute;n</label>
					<div >
						<form:input
							path="clasificacion.fraccion.grupo.division.numDivision"
							id="clasificacion.fraccion.grupo.division.numDivision" class= "form-control input-sm" 
							readonly="true"/>
					</div>
				</div>
			</div>
			<div class="col-xs-4">
				<div class="form-group">
					<label>N&uacute;mero
						Grupo</label>
					<div>
						<form:input path="clasificacion.fraccion.grupo.numGrupo"
							id="clasificacion.fraccion.grupo.numGrupo" class= "form-control input-sm" 
							readonly="true"/>
					</div>

				</div>
			</div>
			<div class="col-xs-4">
				<div class="form-group">
					<label>N&uacute;mero Fracci&oacute;n</label>
					<div >
						<form:input path="clasificacion.fraccion.numFraccion"
							id="clasificacion.fraccion.numFraccion" class= "form-control input-sm" 
							readonly="true"/>
					</div>

				</div>

			</div>
		</div>
		<div class="row">
			<div class="col-xs-4">
				<div class="form-group">
					<label >Descripci&oacute;n</label>
					<div>
						<form:input path="clasificacion.fraccion.numFraccion"
							id="clasificacion.fraccion.numFraccion" class= "form-control input-sm" 
							readonly="true"/>
					</div>

				</div>

			</div>
			<div class="col-xs-4">
				<div class="form-group">
					<label >Persona Fisica</label>
					<div>
						<form:input path="tipoPersonaFiscal" id="tipoPersonaFiscal" class= "form-control input-sm" 
						readonly="true"/>
					</div>

				</div>

			</div>


		</div>
	</form:form>
</div>


<div class="pie" style="margin-top: 20px;">
	<div class="opciones">
		<c:if test="${empty error}">
			<div class="btn-group">
				<a href="#" class="btn btn-primary">Acciones</a> <a href="#"
					data-toggle="dropdown" class="btn btn-primary dropdown-toggle"><span
					class="caret"></span></a>
				<ul class="dropdown-menu">
					<li><a id="btnFinalizarTramite"><i class="glyphicon glyphicon-ok"></i>
							Finalizar Tr&aacute;mite</a></li>
					<li><a id="cancelarTramite"><i class="glyphicon glyphicon-trash"></i>
							Cancelar Tr&aacute;mite</a></li>
				</ul>
			</div>
		</c:if>
		<button class="btn btn-primary" id="btnInicioCerrarTramite">CERRAR</button>
	</div>
	<div class="controles"></div>
</div>





<!--  

<form:form id="formIniciaTramite" method="post" modelAttribute="sujetoTramite"
	action="/delta-gestionPatronal-web/wizard/tramite/ampliacion/trabajadores/eventuales/crear/solicitud">
	<form:label path="numeroRegistroPatronal" id="numeroRegistroPatronal" />	
	<form:label path="modalidad.numModalidad" id="modalidad.numModalidad" />	
	<form:label path="digVerificador" id="digVerificador" />	
	<form:label path="clasificacion.fraccion.grupo.division.numDivision" id="clasificacion.fraccion.grupo.division.numDivision"/>
	<form:label path="clasificacion.fraccion.grupo.numGrupo" id="clasificacion.fraccion.grupo.numGrupo"/>
	
	<form:label path="clasificacion.fraccion.numFraccion" id="clasificacion.fraccion.numFraccion"/>
	<form:label path="clasificacion.fraccion.descripcion" id="clasificacion.fraccion.descripcion"/>
	<form:label path="tipoPersonaFiscal" id="tipoPersonaFiscal" />
</form:form>-->














			
			
			
			
			


