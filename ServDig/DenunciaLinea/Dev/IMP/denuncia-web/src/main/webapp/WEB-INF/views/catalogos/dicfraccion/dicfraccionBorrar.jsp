<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div id="dgDicFraccionBorrar" title=" Confirmacion de Eliminacion de Elemento" style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
    <div id="wrapperDialogBorrar" style="background-color: #f2fff2; ">
        <div id="dialog-confirm" title="�Eliminar el registro?">
            <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>�Confirma que desea eliminar el registro seleccionado?</p>
        </div>
        <form:form modelAttribute="dicFraccion" action="/catalogo/dicfraccion/eliminar.do" method="post" id="dicfraccionFormBorrar">
            <form:hidden path="cveIdFraccion" />
        </form:form>        
    </div>
</div>