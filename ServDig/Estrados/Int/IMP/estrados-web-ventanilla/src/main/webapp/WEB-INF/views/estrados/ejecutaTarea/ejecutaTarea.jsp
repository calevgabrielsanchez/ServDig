<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%-- <script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/commons/timerSession.js"></script> --%>
<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/commons/visorArchivoPDF.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/estrados/ejecutaTarea/ejecutaTarea.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/commons/jquery.dataTables.columnFilter.js"></script>

</head>
<body>
	<div style="width: 1010px">
		<table id="idTablaConsultaInterna" title="Notificaciones presentadas" class="table table-striped table-bordered">
			<thead>
				<tr>
					<th></th>
					<th></th>
	                <th id="idDocumentoCombo">SELECCIONE...</th>
	                <th></th>
	                <th id="idStatusCombo">SELECCIONE...</th>
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
    
    <table width="1010px">
    	<tr>
    		<td width="150px" align="right">
    			<form action="<%=request.getContextPath()%>/estrados/ejecutaTarea.do" >
				    <table>
				    	<tr>
				    		<td align="right" width="50%">
				    			<div class="btn-group">
									<button type="submit" class="btn btn-primary btn-sm">Ejecutar</button>
							 	</div>
				    		</td>
				    	</tr>
				    </table>
			    </form>
    		</td>
    		<td width="150px" align="right">
    			<form action="<%=request.getContextPath()%>/estrados/nuevoRegistro.do">
				    <table>
				    	<tr>
				    		<td align="right" width="50%">
				    			<div class="btn-group">
									<button type="submit" class="btn btn-primary btn-sm">Registrar Publicaci&oacute;n</button>
							 	</div>
				    		</td>
				    	</tr>
				    </table>
			    </form>
    		</td>
    	</tr>
    </table>
    
	<div id="visor">
	   <form action="/firmaElectronicaWeb/widget/chfecyn/imss/buscaSeguimiento" class = "formNotBlock" method="post" target="firmaIframe" id="formaAcuse">
		    <input type="hidden" name="params" id="idTramite"/>
		</form>
		<iframe id="firmaIframe" name="firmaIframe" height="500" width="850" style="display: none;"></iframe>
	</div>
	
</body>

 <form action="<%=request.getContextPath()%>/estrados/consultaInterna.do"  id="redireccionListado">
	    
	</form>
</html>