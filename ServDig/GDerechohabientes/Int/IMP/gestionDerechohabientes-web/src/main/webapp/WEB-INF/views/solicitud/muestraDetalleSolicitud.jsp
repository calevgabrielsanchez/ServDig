<head>

<script type="text/javascript">

 $(document).ready(function() {  
	var idSolicitud= '<%=request.getAttribute("idSolicitud")%>'; 
	var ajaxSource = context_path + '/solicitud/detalle/';
	var solicitud = {'idSolicitud': idSolicitud};
	$('#documento').html("Cargando detalle de la solicitud...");
	$('#documento').load(ajaxSource,solicitud);
	
	
	}
);	
</script>
</head>
<body>
	<br>
	<br>
	<div id="documento">
	 
	</div>
</body>
