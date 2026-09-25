<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <title><tiles:insertAttribute name="title" ignore="true" /></title>
 
    <%-- Incluimos los recursos y estilos configurados --%>
    <jsp:include page="staticResources-institucional.jsp"></jsp:include>
</head>
<body>
 
    <div id="headerWrapper">
        <tiles:insertAttribute name="encabezado" />
    </div>
 
    <main class="container page-flex">
        <div class="row">
            <tiles:insertAttribute name="contenido" />
        </div>
    </main>
 
    <div id="footerWrapper">
        <tiles:insertAttribute name="pie" />
    </div>
    
    <jsp:include page="btn-accesibilidad.jsp"/>
</body>
</html>