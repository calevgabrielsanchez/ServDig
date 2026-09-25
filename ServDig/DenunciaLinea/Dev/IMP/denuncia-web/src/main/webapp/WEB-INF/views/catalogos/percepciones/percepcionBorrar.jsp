<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div id="dgPercepcionBorrar" title=" Confirmacion de Eliminacion de Elemento" style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
	<div id="wrapperDialogBorrar" style="background-color: #f2fff2; ">
		<div id="dialog-confirm" title="�Eliminar el registro?">
			<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>�Confirma que desea eliminar el registro seleccionado?</p>
		</div>
		<form:form modelAttribute="crcPercepciones" action="/catalogo/percepciones/eliminar.do" method="post" id="percepcionesFormBorrar">
		 	<form:hidden path="cvePercepcion" />
		 	
		</form:form>		
	</div>
</div>