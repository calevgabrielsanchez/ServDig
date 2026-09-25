<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<html lang="sp">
<jsp:include page="../../main/head.jsp" />


<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/catalogos/clase/clase.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/limpiaFormularios.js"></script>



<body>
	<div id="cuerpo_principal">
		<jsp:include page="../../main/menu_up_principal.jsp" />

	
		<div id="menuSistema" style="background-color: #FFFFFF;size: 100%">
				<jsp:include page="../../main/menu.jsp" />
		</div>

		<div id="cuerpo">
			
			 <div class="menu_principal" style="height: 2em !important;"> 
		      <!--inicia menu principal-->
		      <div align="center">
		        <div class="centrado">
		          <ul style="height: 1em !important;">
		            <li><a> Catalogo de Clase</a></li>
		          </ul>
		        </div>
		        <!--fin centrado--> 
		      </div>
		    </div>
			
			
			
			<div id="filtros">
				<jsp:include page="claseFiltros.jsp" />
			</div>
			<br>
			<div id="data">
				<jsp:include page="claseData.jsp" />
			</div>
			<br>
			<div id="nuevo">
				<jsp:include page="claseNuevo.jsp" />
			</div>
			<div id="modificar">
				<jsp:include page="claseModificar.jsp" />
			</div>
			<div id="borrar">
				<jsp:include page="claseBorrar.jsp" />
			</div>

		</div>
		<jsp:include page="../../main/menu_down.jsp" />
	</div>
</body>
</html>