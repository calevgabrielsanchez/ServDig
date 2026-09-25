<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/commons/visorArchivoPDF.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/estrados/consultaInterna/consultaInterna.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/commons/jquery.dataTables.columnFilter.js"></script>

</head>
<body>
	<div style="width: 1010px">
		<table id="idTablaConsultaInterna" title="Notificaciones presentadas" class="table table-striped table-bordered">
			<thead>
				<tr>
					<th></th>
					<th></th>
	                <th>Documento a Notificar</th>
	                <th></th>
	                <th>Status</th>
	                <th></th>
				</tr>
				<tr>
					<th></th>
					<th></th>
					<th></th>
					<th></th>
					<th></th>
					<th></th>
				</tr>
			</thead>
	    </table>
    </div>
    
    <div id="dialogoMensaje"></div>
    
    <form action="<%=request.getContextPath()%>/estrados/nuevoRegistro.do">
	    <table>
	    	<tr>
	    		<td align="right" width="1010px">
	    			<div class="btn-group">
						<button type="submit" class="btn btn-primary">Registrar Notificaci&oacute;n</button>
				 	</div>
	    		</td>
	    	</tr>
	    </table>
    </form>
    
	<div id="visor">
	   <form action="/firmaElectronicaWeb/widget/chfecyn/imss/buscaSeguimiento" class = "formNotBlock" method="post" target="firmaIframe" id="formaAcuse">
		    <input type="hidden" name="params" id="idTramite"/>
		</form>
		<iframe id="firmaIframe" name="firmaIframe" height="500" width="850" style="display: none;"></iframe>
	</div>
	
</body>
</html>