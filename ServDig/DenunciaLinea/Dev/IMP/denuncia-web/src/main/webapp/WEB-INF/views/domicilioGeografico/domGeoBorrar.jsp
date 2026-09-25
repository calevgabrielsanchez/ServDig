<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div id="dgDomInegiBorrar" title=" Confirmacion de Eliminacion de Elemento" style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
	<div id="wrapperDialogDomInegi" style="background-color: #f2fff2; ">
		<div id="dialog-confirm" title="�Eliminar el registro?">
			<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>�Confirma que desea eliminar el registro seleccionado?</p>
		</div>
		<form:form modelAttribute="dgDomicilioInegiAux" action="/domiciliosGeograficos/eliminar.do" method="post" id="domInegiFormBorrar">
		 	<form:hidden path="domicilioInegi.domicilioId" />
		</form:form>		
	</div>
</div>