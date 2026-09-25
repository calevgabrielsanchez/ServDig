<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div id="dgPercepcionBorrar">
	<div id="wrapperDialogBorrar">
		<div id="dialog-confirm" title="¿Eliminar el registro?">
			<p>
				<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
				<label>¿Confirma que desea eliminar el registro seleccionado?</label>
			</p>
		</div>
		<form:form modelAttribute="crcPercepciones" action="/catalogo/percepciones/eliminar.do" method="post" id="percepcionesFormBorrar">
		 	<form:hidden path="cvePercepcion" />
		</form:form>		
	</div>
</div>