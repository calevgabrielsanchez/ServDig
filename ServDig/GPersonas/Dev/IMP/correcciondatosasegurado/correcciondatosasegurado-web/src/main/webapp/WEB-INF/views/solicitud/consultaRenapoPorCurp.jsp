
<%@ include file="../general/taglibs.jsp"%>
<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="urlInformacionRenapo" value="${contextpath}/wizard/correccionDatosAsegurado/obtenerInformacionRenapo"></c:set>
<c:set var="curp" value="${fisica.curp}"/>

<div id="info-paso" style="margin-bottom: 50px;">
<script type="text/javascript">
	var contextPath="${contextpath}";
</script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/solicitud/consultaInformacionRenapo.js" htmlEscape="true"/>"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/common/common.js" htmlEscape="true" />"></script>

	<div class="contenedor">
		<jsp:include page="encabezado.jsp">
			<jsp:param name="paso" value="2" />
		</jsp:include>
		<form:form method="post" id="consultaRenapoForm"
			action="${urlInformacionRenapo}">
			<h4>
				<spring:message code="label.titulo.informacion.renapo" />
			</h4>
			<hr class="red" style="margin-bottom: 20px;">

			<br />
			<div class="col-md-12">
				<spring:message code="label.informacion.renapo" />
			</div>
			<br />
			<br />
			
			<div class="col-md-12">	
						<div class="col-md-3">
							<label class="control-label"><spring:message code="label.curp" /></label>
						</div>
						<div class="pull-left">${fisica.curp}</div>
						<input type="hidden" id="curp" value="${fisica.curp}"/>
			</div>
			<div class="col-md-12">	
						<div class="col-md-3">
							<label class="control-label"> <spring:message
								code="label.primer.apellido" />
							</label>
						</div>
						<div class="pull-left">${fisica.primerApellido}</div>
					</div>
					<div class="col-md-12">	
						<div class="col-md-3">
							<label class="control-label"> <spring:message
								code="label.segundo.apellido" />
							</label>
						</div>
						<div class="pull-left">${fisica.segundoApellido}</div>
					</div>
					<div class="col-md-12">	
						<div class="col-md-3">
							<label class="control-label"> <spring:message
								code="label.nombre" />
							</label>
						</div>
						<div class="pull-left">${fisica.nombre}</div>
					</div>
					<div class="col-md-12">	
						<div class="col-md-3">
							<label class="control-label"> <spring:message
								code="label.sexo"></spring:message>
							</label>
						</div>
						<div class="pull-left">${fisica.sexo.descripcion}</div>
					</div>
					<div class="col-md-12">	
						<div class="col-md-3">
							<label class="control-label"> <spring:message
								code="label.fecha.nacimiento"></spring:message>
							</label>
						</div>
						<div class="pull-left">${fisica.fechaNacimientoFormateada}</div>
					</div>
					<div class="col-md-12">	
						<div class="col-md-3">
							<label class="control-label"> <spring:message
								code="label.lugar.nacimiento" />
							</label>
						</div>
						<div class="pull-left">${fisica.lugarNacimiento.nombre}</div>
					</div>
					<div class="col-md-12">	
						<div class="col-md-3">
							<label class="control-label"> <spring:message
								code="label.nacionalidad" />
							</label>
						</div>
						<div class="pull-left">${fisica.pais.nacionalidad}</div>
					</div>

			<c:if test="${not empty fisica.curpsHistoricas}">

				<c:forEach var="historica" items="${fisica.curpsHistoricas}" varStatus="contador">
					<div class="col-md-12">
						<div class="col-md-3">
							<label class="control-label"> 
							<c:if test="${contador.index eq 0 }">
							<spring:message
									code="label.curps.historicas.confirmacion" />
							</c:if>
							</label>
						</div>
						<div class="pull-left">${historica}</div>
					</div>
				</c:forEach>

			</c:if>
			<div class="row"></div>
							<h4><label class="control-label">
							<spring:message code="label.documentos.probatorio" />
							</label class="control-label">
							</h4>
							<hr class="red" style="margin-bottom: 20px;">
                                    <c:if test="${fisica.actaNacimiento != null}">
					<div class="col-md-12">
						<div class="col-md-3">
							<label class="control-label"> <spring:message
								code="label.entidad" />
							</label>
						</div>
						<div class="pull-left">
							${fisica.actaNacimiento.municipio.entidadFederativa.nombre}</div>
					</div>
					<div class="col-md-12">
						<div class="col-md-3">
							<label class="control-label"> <spring:message
								code="label.municipio"></spring:message>
							</label>
						</div>
						<div class="pull-left">${fisica.actaNacimiento.municipio.nombre}</div>
					</div>
					<div class="col-md-12">
						<div class="col-md-3">
							<label class="control-label"> <spring:message
								code="label.anio"></spring:message>
							</label>
						</div>
						<c:if test="${fisica.actaNacimiento.anio > 0}">
						    <div class="pull-left">${fisica.actaNacimiento.anio}</div>
						</c:if>
					</div>
					<div class="col-md-12">
						<div class="col-md-3">
							<label class="control-label"> <spring:message
								code="label.tomo"></spring:message>
							</label>
						</div>
						<c:if test="${fisica.actaNacimiento.tomo > 0}">
						    <div class="pull-left">${fisica.actaNacimiento.tomo}</div>
						</c:if>
					</div>
					<div class="col-md-12">
						<div class="col-md-3">
							<label class="control-label"> <spring:message
									code="label.acta"></spring:message>
							</label>
						</div>
						<c:if test="${fisica.actaNacimiento.noActa > 0}">
						    <div class="pull-left">${fisica.actaNacimiento.noActa}</div>
						</c:if>
					</div>
					<div class="col-md-12">
						<div class="col-md-3">
							<label class="control-label"> <spring:message
								code="label.crip"></spring:message>
							</label>
						</div>
						<c:if test="${fisica.actaNacimiento.crip != null && not empty fisica.actaNacimiento.crip}">
						    <div class="pull-left">${fisica.actaNacimiento.crip}</div>
						</c:if>
					</div>
					<div class="col-md-12">
						<div class="col-md-3">
							<label class="control-label"> <spring:message
								code="label.libro"></spring:message>
							</label>
						</div>
						<c:if test="${fisica.actaNacimiento.noLibro > 0}">
						    <div class="pull-left">${fisica.actaNacimiento.noLibro}</div>
						</c:if>
					</div>
					<div class="col-md-12">
						<div class="col-md-3">
							<label class="control-label"> <spring:message
								code="label.foja"></spring:message>
							</label>
						</div>
						<c:if test="${fisica.actaNacimiento.noFoja > 0}">
						    <div class="pull-left">${fisica.actaNacimiento.noFoja}</div>
						</c:if>
					</div>
                                    </c:if>
                                    <c:if test="${fisica.actaNacimiento == null}">
                                    <c:forEach var="documento" varStatus="contadorDocumento" items="${fisica.documentosProbatorios}" >
                                        <div class="col-md-12">
						<div class="col-md-3">
							<label class="control-label"> <spring:message
								code="label.cadena"></spring:message>
							</label>
						</div>
						<div class="pull-left">${documento.numFolioExtranjero != null ? documento.numFolioExtranjero:"N/A"}</div>
					</div>
                                    </c:forEach>   
                                    </c:if>
					
	<br />
	<div class="pull-right">
		<br />
		<button class="btn btn-primary" id="continuarCarpturarDomicilio" type="button">Continuar</button>
	</div>
	</form:form>

	<form:form method="POST" id="cancelarCurpIncorrectaForm">
		<div class="pull-right">
			<div class="col-md-4">
			<br/>
				<button class="btn btn-default" id="cancelarCurpIncorrectaButton">Cancelar</button>
			</div>
		</div>
	</form:form>


</div>


</div>