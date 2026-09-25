<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html;charset=ISO-8859-1"%>
<html>
  <head>
  	<title>Instituto Mexicano del Seguro Social
  	</title>

		<script type="text/javascript">
			function invocaServlet(){ 
				document.forms[0].documento.value = parent.document.forms[0].documento.value;
				document.forms[0].nombreArchivo.value = parent.document.forms[0].nombreArchivo.value;
				document.forms[0].action="<%=request.getContextPath()%>/servlet/EnviaArchivoServlet";
				document.forms[0].target="_self";
				document.forms[0].submit();
			}
		</script>
  </head>
  <body onload="invocaServlet()">
    <form action="" method="post">
    	<input type="hidden" name="documento" value="" />
    	<input type="hidden" name="tipoDescarga" value="muestra" />
    	<input type="hidden" name="nombreArchivo" value="" />
    </form>
  </body>
</html>
