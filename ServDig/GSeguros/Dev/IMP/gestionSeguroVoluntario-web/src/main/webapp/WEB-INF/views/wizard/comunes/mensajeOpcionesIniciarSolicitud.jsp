<%@ include file="../../general/taglibs.jsp"%>

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
					<spring:message
						code="label.wizard.solicitud.pasos.titulo" />
		</div>
	</div>

	<!-- Opciones -->
	<div class="row opciones">
		<!-- Opcion -->
		<div class="col-xs-6">

			<div class=" opcion">
				<div class="">
				</div>
				<div class="">
					<p class="paso">
						<spring:message code="label.wizard.solicitud.iniciar.titulo" />
					</p>
					<p class="descripcion">
						<spring:message code="label.wizard.solicitud.iniciar.descripcion" />
					</p>
				</div>
			</div>
		</div>
		<!-- Opcion -->
		<div class="col-xs-6 ">

			<div class=" opcion">

				<div class="">
				</div>
				<div class="">
					<p class="paso">
						<spring:message code="label.wizard.solicitud.capturar.titulo" />
					</p>
					<p class="descripcion">
						<spring:message
							code="label.wizard.solicitud.capturar.descripcion" />
					</p>
				</div>

			</div>
		</div>
	</div>
		
	<div class="row opciones">
		<c:if test="${empty tipoOperacion}">
			<!-- Opcion -->
			<div class="col-xs-6 ">
	
				<div class="opcion">
	
					<div class="">
					</div>
					<div class="">
						<p class="paso">
							<spring:message code="label.wizard.solicitud.cuestionario.titulo" />
						</p>
						<p class="descripcion">
							<spring:message
								code="label.wizard.solicitud.cuestionario.descripcion" />
						</p>
					</div>
	
				</div>
			</div>
		</c:if>
		<!-- Opcion -->
		<div class="col-xs-6 ">

			<div class="opcion">

				<div class="">
				</div>
				<div class="">
					<p class="paso">
						<c:choose>
							<c:when test="${empty tipoOperacion}">
								<spring:message code="label.wizard.solicitud.finalizar.titulo" />
							</c:when>
							<c:otherwise>
								<spring:message code="label.wizard.solicitud.renovacion.finalizar.titulo" />
							</c:otherwise>
						</c:choose>
					</p>
					<div class="descripcion">
						<p>
							<spring:message
								code="label.wizard.solicitud.finalizar.descripcion" />
						<p>
						<p>
							Para concluir el tr&aacute;mite de incorporaci&oacute;n al Seguro Social de tu trabajador dom&eacute;stico es
							necesario realizar el pago correspondiente que se te indica al final de tu solicitud.
						</p>
					</div>
				</div>

			</div>
		</div>
	</div>

</div>