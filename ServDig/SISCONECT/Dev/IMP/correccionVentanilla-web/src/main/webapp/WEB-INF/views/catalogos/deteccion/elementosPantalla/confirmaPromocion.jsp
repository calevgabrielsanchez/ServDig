<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div id="dgConfirmaPromocion"  style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
	<div id="wrapperDialogPromocion" style="background-color: #f2fff2; ">
		<div id="dialog-confirm" title="¿?">
			<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>¿Seguro que desea Promover la Obra?</p>
		</div>
		<form:form modelAttribute="crtDeteccion" action="" method="post" id="deteccionPromocionForm">
		 <form:hidden path="cveDeteccion" id="cveDeteccion"/>
		</form:form>		
	</div>
</div>