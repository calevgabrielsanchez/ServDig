<%@ include file="../../../general/taglibs.jsp"%>
<%@ taglib prefix="domicilio"
	uri="http://www.serviciosdigitales.imss.gob.mx/tags/domicilio"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/wizard/mod-33/comunes/seleccionarDomicilio.js" htmlEscape="true" />"></script>

<c:set var="contextPath" value="<%=request.getContextPath()%>" />

<style>
.ui-selectable li {
	padding: 15px 25px;
	word-wrap: break-word;
}

table.table {
	font-size: initial !important;
}
</style>

<div class="contenedor col-sm-12" id="divContenedorDom">
	<div class="contenido row">
		<div class="col-sm-12">
			<div class="titulo">
				<c:if test="${enRenovacion and not extemporanea}">
					<span>Paso 1 de 4: Iniciar tr&aacute;mite</span>
				</c:if>
				<c:if test="${!enRenovacion or extemporanea}">
					<span>Paso 1 de 5: Iniciar tr&aacute;mite</span>
				</c:if>
				
			</div>
			<div class="titulo" id="titDomFam">
				<span>Domicilio de tu familia</span>
				<hr class="red m-b-md">
			</div>
			<div class="alert alert-danger" style="display: none;"
				id="validacion">
				<span id="mensaje-validacion"></span>
			</div>
			<c:choose>
				<c:when
					test="${empty solicitante.domicilioParticular and empty domicilioOtraUbicacion.codigoPostal}">
					<div class="alert alert-warning">
						<span>No tienes registrado ning&uacute;n domicilio
							particular en el Instituto, para continuar con el tr&aacute;mite
							selecciona &quot;Otra Ubicaci&oacute;n&quot;</span>
					</div>
				</c:when>
				<c:otherwise>
					<c:if test="${not empty solicitante.domicilioParticular}">
						<c:if test="${!enRenovacion}">
							<div class="alert alert-info">A continuaci&oacute;n se
								muestra el domicilio particular que tienes registrado en el
								Instituto, si deseas puede elegir la opci&oacute;n de &quot;Otra
								Ubicaci&oacute;n&quot; dentro del bot&oacute;n de
								&quot;Acciones&quot;</div>
						</c:if>
					</c:if>
					<ol id="listDomicilios" class="m-b-lg">
						<c:if test="${not empty solicitante.domicilioParticular}">
							<li class="ui-state-default  domicilioParticular"
								idDomicilio="${solicitante.domicilioParticular.idDomicilio}"
								codigoPostal="${solicitante.domicilioParticular.codigoPostal}"
								idEntidad="${solicitante.domicilioParticular.localidad.municipio.entidadFederativa.clave}"
								idMunicipio="${solicitante.domicilioParticular.localidad.municipio.clave}">
								<h5>
									<domicilio:generar-descripcion
										domicilio="${solicitante.domicilioParticular}" />
								</h5>
							</li>
						</c:if>
						<c:if test="${not empty domicilioOtraUbicacion.codigoPostal}">
							<li class="ui-state-default domicilioOtraUbicacion"
								idDomicilio="${domicilioOtraUbicacion.idDomicilio}"
								codigoPostal="${domicilioOtraUbicacion.codigoPostal}"
								idEntidad="${domicilioOtraUbicacion.localidad.municipio.entidadFederativa.clave}"
								idMunicipio="${domicilioOtraUbicacion.localidad.municipio.clave}">
								<h5>
									<domicilio:generar-descripcion
										domicilio="${domicilioOtraUbicacion}" />
								</h5>
							</li>
						</c:if>
					</ol>
					<hr>
					<div class="form-horizontal">
						<div class="form-group">
							<div class="col-sm-4">
								<p class="form-control-static">Desde el extranjero:</p>
							</div>
							<div class="col-sm-8 m-b-sm">
								<div class="checkbox">
									<label> <input type="checkbox" id="desdeExtranjeroChk">
										Quiero incorporarme e inscribir a mi familia que vive en
										M&eacute;xico.
									</label>
								</div>
							</div>
						</div>
					</div>

					<div>
						<hr>
						<div>
							<h5>Unidad M&eacute;dico Familiar</h5>
							<hr class="red m-b-md">
						</div>
						<div id="umfContenedor">
							<div class="text-center" style="display: none;">
								<img alt="CARGANDO..."
									src="${staticResourcesPath}/imagenes/loading.gif" />
							</div>
						</div>
					</div>
				</c:otherwise>
			</c:choose>

			<form:form id="umfForm" modelAttribute="umf"
				action="${contextPath}/wizard/seguroFamiliar/comunes/agregarUmf" accept-charset="ISO-8859-1">
				<form:hidden path="idUMF" id="idUmf" />
				<form:hidden path="noEconomico" id="noEconomicoUmf" />
				<form:hidden path="subdelegacion.id" id="idSubdelegacionUmf" />
				<form:hidden path="subdelegacion.clave" id="cveSubdelegacionUmf" />
				<form:hidden path="subdelegacion.delegacion.id" id="idDelegacionUmf" />
				<form:hidden path="subdelegacion.delegacion.clave"
					id="cveDelegacionUmf" />
				<form:hidden path="subdelegacion.delegacion.ciz" id="cveCizUmf" />
			</form:form>

			<form:form id="nextStepForm" modelAttribute="tramiteSeguro"
				action="${contextPath}/wizard/seguroFamiliar/comunes/seleccionarDomicilio">
				<form:hidden id="idDomSeguro" path="domicilioSeguro.idDomicilio" />
				<form:hidden id="cpDomSeguro" path="domicilioSeguro.codigoPostal" />
				<form:hidden id="entidadDomSeguro"
					path="domicilioSeguro.asentamiento.localidad.municipio.entidadFederativa.clave" />
				<form:hidden id="municipioDomSeguro"
					path="domicilioSeguro.asentamiento.localidad.municipio.clave" />
				<form:hidden id="desdeExtranjero" path="desdeExtranjero" />
			</form:form>
                        <form:form 
				action="${contextPath}/wizard/seguroFamiliar/comunes/otraUbicacion"
				id="otraUbicacionForm" accept-charset="ISO-8859-1">
                        </form:form>

		</div>
	</div>
	<div class="pie row">
		<div class="opciones col-sm-6">
			<c:if test="${enRenovacion and (not empty domicilioOtraUbicacion.codigoPostal or not empty solicitante.domicilioParticular)}">
				<a id="agregarDomicilio" class="btn btn-primary" href="#">Modificar</a>
			</c:if>
			<c:if test="${not enRenovacion or (empty solicitante.domicilioParticular and empty domicilioOtraUbicacion.codigoPostal)}">
				<a id="agregarDomicilio" class="btn btn-primary" href="#">Otra
					Ubicaci&oacute;n</a>
			</c:if>
		</div>
		<div class="controles col-sm-6">
			<div class="pull-right">
				<button id="cerrar" class="btn btn-default">Cancelar</button>
				<a id="siguientePaso" class="btn btn-primary"><i
					class="glyphicon glyphicon-step-forward"></i> Continuar</a>
			</div>
		</div>
	</div>
    <c:if test="${not empty umfSeguroAsociado and empty domicilioOtraUbicacion.codigoPostal}">
            <script language="javascript">      
                var elemento = $('ol#listDomicilios li.domicilioParticular');
                //esta variable fue declarada en initComponenteUMFs para poner en checked el radio que viene por defecto
                idUMFInicial = '<c:out value="${umfSeguroAsociado}"/>';
                obtenerUMFs(elemento.attr('codigoPostal'));
                elemento.addClass("ui-selected");
                elemento.selectable('refresh');
            </script>
    </c:if>
    <c:if test="${desdeExtranjero}">
            <script language="javascript">   
                $('#desdeExtranjeroChk').attr('checked',true);  
            </script>
    </c:if>
</div>
