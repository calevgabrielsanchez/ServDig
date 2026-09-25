<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/js/delta/denuncia/salir.js"></script>


<div id="dgSalirSinGuardar">
Usted no ha guardado sus datos. Si contin&uacute;a con la opci&oacute;n Salir, deber&aacute; ingresar nuevamente al sistema y finalizar la captura.
</div>

<div> 
 <jsp:include page="salirSistema.jsp"/>
</div>

