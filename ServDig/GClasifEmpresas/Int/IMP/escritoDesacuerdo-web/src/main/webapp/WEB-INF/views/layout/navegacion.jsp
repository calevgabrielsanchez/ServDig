<%@ include file="../general/taglibs.jsp"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.PortalContextEnum"%>

<script type="text/JavaScript">
	$(function() {
		$(".linkRedireccion").on("click", function(e){
			e.preventDefault();
			var url = $(this).attr("url");
			
			//var opciones = {
			//	titulo : 'Confirmaci&oacute;n',
			//	mensaje : "Se perder&aacute; toda la informaci&oacute;n capturada. &iquest; Estas seguro que deseas continuar ?",
			//	buttons : {
			//		'No' : dialogosCtrl.close,
			//		'Si' : function() {
			//			$(this).dialog('close');
						$.blockUI();
						location.href = url;
			//		}
			//	}
			//};
			
			//dialogosCtrl.abrirDialogo(opciones);
		});
	});
</script>

<div>
	<nav role="navigation"
		class="navbar navbar-inverse sub-navbar navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
		      <a class="navbar-brand" href="/">IMSS Digital</a>
		    </div>
		    <div class="collapse navbar-collapse" id="subenlaces">
		      <ul class="nav navbar-nav navbar-right">
		        <li><a class="linkRedireccion" url="${contextpath}/escrito/">Escrito de desacuerdo</a></li>
		      </ul>
		    </div>
		</div>
		
	</nav>
</div>

<div class="pull-right" style="border: 1px solid #ccc; padding: 10px">
	<span style="margin-right: 10px; float: left"> <%-- ${funcionario.nombre}--%>
		<span class="glyphicon glyphicon-user"></span> ${usuario.usuario} <br>
		<span class="glyphicon glyphicon-briefcase"></span> ${usuario.perfilUsuario.descripcion} <br>
		<c:if test="${usuario.perfilUsuario.idPerfilUsuario != 1}">
		<span id="spanDelegacion"> 
			<span class=" glyphicon glyphicon-home"></span> [${usuario.usuarioFuncionario.delegacion.clave}] - ${usuario.usuarioFuncionario.delegacion.descripcion} <br>
		</span> 
		<c:if test="${usuario.perfilUsuario.idPerfilUsuario != 2}">
		<span id="spanSubdelegacion"> 
			<span class=" glyphicon glyphicon-home"></span> [${usuario.usuarioFuncionario.subdelegacion.clave}] - ${usuario.usuarioFuncionario.subdelegacion.descripcion}
		</span>
		</c:if>
		</c:if>
	</span> 
	<div style="float:right; vertical-align: middle" >
	<a href="${contextpath}/j_spring_security_logout" id="cerrarSesionLink" style="float: right">Salir</a>
	</div>
</div>
