<%@ include file="../general/taglibs.jsp"%>

<style type="text/css">
	#divTC {
		padding-bottom: 10px;
	}
	.justificado {
		    text-align:justify;
	}
</style>

<div id="divTC">
	<div class="contenedor container-fluid m-b-xl">
		<div class="contenido row">
			<div class="col-xs-12">
				<fieldset>
					<!-- Terminos y condiciones -->
					<p class="justificado">
						<strong><spring:message code="carta.terminos.titulo" /></strong>
					</p>
					<br/>
					
					<p class="justificado">
						<spring:message code="carta.terminos.parrafo1" />
					</p>
					<p class="justificado">
						<spring:message code="carta.terminos.parrafo2" />
					</p>
					<p class="justificado">
						<spring:message code="carta.terminos.parrafo3" />
					</p>
					<p class="justificado">
						<spring:message code="carta.terminos.parrafo4" />
					</p>
					<p class="justificado">
						<spring:message code="carta.terminos.parrafo5" />
					</p>
					<p class="justificado">
						<spring:message code="carta.terminos.parrafo6" />
					</p>
					<p class="justificado">
						<spring:message code="carta.terminos.parrafo7" />
					</p>
					<p class="justificado">
						<spring:message code="carta.terminos.parrafo8" />
					</p>
					<p class="justificado">
						<spring:message code="carta.terminos.parrafo9" />
					</p>
					<p class="justificado">
						<spring:message code="carta.terminos.parrafo10" />
					</p>
					<p class="justificado">
						<spring:message code="carta.terminos.parrafo11" />
					</p>
					<br/>
				</fieldset>
				<div style="float: right;">
					<button id="cerrarTC" class="btn btn-default">Cerrar</button>
				</div>
			</div>
		</div>
	</div>
</div>
