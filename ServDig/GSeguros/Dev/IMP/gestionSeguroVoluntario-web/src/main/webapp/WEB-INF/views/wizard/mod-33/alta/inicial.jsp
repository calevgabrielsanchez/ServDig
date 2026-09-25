<%@ include file="../../../general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/wizard/mod-33/alta/inicial.js" htmlEscape="true" />"></script>

<c:set var="contextPath" value="<%=request.getContextPath()%>" />

<div class="contenedor col-sm-12">
	<c:choose>
		<c:when test="${empty error}">
			<div class="contenido row">
				<div class="introduccion col-sm-4">
					<div class="titulo" style="line-height: 1.6;">
						<span>
						<c:if test="${!enRenovacion or extemporanea}">
							<spring:message code="label.portlet.titulo.seguroFamiliar" />
						</c:if>
						<c:if test="${enRenovacion and not extemporanea}">
							<spring:message code="label.portlet.titulo.seguroFamiliar.renovacion" />
						</c:if>
						</span>
						<hr class="red m-b-none">
					</div>
					<div class="descripcion">
						<p>Este seguro permite a las personas que no cuentan con seguridad social acceder a 
						los servicios m&eacute;dicos que ofrece el IMSS, tales como asistencia m&eacute;dico quir&uacute;rgica, 
						farmac&eacute;utica y hospitalaria, mediante el pago de cuotas anuales anticipadas.
						</p>
					</div>
					<div class="opciones">
						<button class="btn btn-primary btn-block" id="btnIniciarSolicitudAlta">
							<span>Iniciar tr&aacute;mite</span>
						</button>
						<button class="btn btn-default btn-block" id="btnCancelarSolicitudAlta">
							<span>Cancelar</span>
						</button>
					</div>
				</div>
				<div class="col-sm-8">
					<div class="instrucciones">
						<div class="container-fluid empty-state">
							<div class="row">
								<!-- Imagen -->
								<div class="col-xs-12 imagen">
									<i class="fa fa-file-text"></i>
								</div>
							</div>
							<div class="row">
								<!-- Titulo -->
								<div class="col-xs-12 titulo">
									<spring:message code="label.wizard.solicitud.pasos.titulo" />
								</div>
							</div>

							<!-- Opciones -->
							<div class="row opciones">
								<!-- Opcion -->
								<div class="col-sm-6">
									<div class=" opcion">
										<p class="paso">
											<spring:message code="label.wizard.solicitud.iniciar.titulo.mod33" />
										</p>
										<p class="descripcion">Verifica que la direcci&oacute;n que tienes registrada ante el IMSS, est&eacute; actualizada.</p>
									</div>
								</div>
								<!-- Opcion -->
								<div class="col-sm-6 ">
									<div class=" opcion">
										<p class="paso">
											2. Cuestionario de salud
										</p>
										<p class="descripcion">Responder antecedentes de enfermedades. Conocer las enfermedades pasadas, es un dato importante 
										para conocer el estado de salud actual tuyo y de tu familia.</p>
									</div>
								</div>
							</div>

							<div class="row opciones">
								<!-- Opcion -->
								<div class="col-sm-6 ">
									<div class=" opcion">
										<p class="paso">
											3. Ingresar datos familiares
										</p>
										<p class="descripcion">Registra los datos de los familiares que deseas asegurar. Pueden ser: esposa(o) o concubina(rio), 
										hijos, padre, madre, o bien tus familiares adicionales, como abuelos, nietos, hermanos, primos,  sobrinos  y t&iacute;os.</p>
									</div>
								</div>
								<!-- Opcion -->
								<div class="col-sm-6 ">
									<div class=" opcion">
										<p class="paso">
											4. Confirmar tus datos
										</p>
										<p class="descripcion">Verifica que tus datos y los de tu familia sean correctos.</p>
									</div>
								</div>
							</div>
							
							<div class="row opciones">
								<!-- Opcion -->
								<div class="col-sm-6 ">
									<div class=" opcion">
										<p class="paso">
											5. Recibir resultado
										</p>
										<p class="descripcion">Obtén el comprobante del tr&aacute;mite y las l&iacute;neas de captura para 
										realizar el pago anual y obtener la Incorporaci&oacute;n al Seguro de Salud para la Familia.</p>
									</div>
								</div>
							</div>

							<strong>
								<a href="http://www.imss.gob.mx/cuotas-excepciones" target="_blank">
									Costos de la cuotas anuales anticipadas y las excepciones
								</a>
							</strong>

						</div>
					</div>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<div class="contenido row">
				<div class="alert alert-danger">
					<span>${error}</span>
				</div>
			</div>
			<div class="pie row">
				<div class="opciones col-sm-6"></div>
				<div class="controles col-sm-6">
					<div class="pull-right">
						<a id="btnCancelarSolicitudAlta" class="btn btn-default">Salir</a>
					</div>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
</div>

<form:form id="capturarDatosSolicitudAltaForm"
	action="${contextPath}/wizard/seguroFamiliar/comunes/solicitarDomicilio" method="post">
	<input name="idPersona" type="hidden" value="${persona.idPersona}" />
	<input name="idPatron" type="hidden" value="${persona.idPersona}" />
	<input name="otraInfo" type="hidden" value="${persona.idPersona}" />
</form:form>
