<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div id="dgSalirSistema">
  <c:if test="true" >
      <c:out value="Sus datos han sido guardados por el sistema."/>
  </c:if>
   Usted cuenta hasta el día <script>$('#hdFechaServidor').value();</script>
   para concluir con el trámite de presentación de su denuncia, debiendo ingresar el nombre de usuario y
   contraseña guardados por Usted.
</div>
