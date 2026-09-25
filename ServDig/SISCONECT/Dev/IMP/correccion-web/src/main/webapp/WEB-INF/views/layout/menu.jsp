<%@page import="mx.gob.imss.ctirss.correccion.session.ConstantesSession"%>
<%@page import="mx.gob.imss.ctirss.correccion.session.UserSession"%>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/resources/css/correcion.css">
<link type="text/css"
	href="<%=request.getContextPath()%>/resources/css/menu/style.css"
	rel="stylesheet" />

		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/menu/superfish.css" media="screen">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/menu/superfish-vertical.css" media="screen">
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/menu/hoverIntent.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/menu/superfish.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/menu/supersubs.js"></script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/menu/controlMenu.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>

<%
	 UserSession usrSession = request.getSession().getAttribute(ConstantesSession.USR_SESSION)!=null?
										(UserSession)request.getSession().getAttribute(ConstantesSession.USR_SESSION):null;
	
%>
<table border="0">
	<tr>
		<td>
			<ul class="sf-menu sf-vertical" id="menu">
		</ul>
		</td>
	</tr>
</table>


<script>
<%
if(usrSession!=null){
	out.print(usrSession.getMenu());
	
}
%>

	//plugin descargado de http://users.tpg.com.au/j_birch/plugins/superfish/
// initialise plugins
 		jQuery(function(){
			jQuery('ul.sf-menu').superfish();
		});



</script>