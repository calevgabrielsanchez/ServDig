<%@page import="mx.imss.ctirss.session.ConstantesSession" %>
<%@page import="mx.imss.ctirss.session.UserSession" %>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/correcion.css">
<link type="text/css"
	href="<%=request.getContextPath()%>/resources/css/menu/style.css"
	rel="stylesheet" />

<script type="text/javascript"
    var contextPath = "<%=request.getContextPath()%>";
	src="<%=request.getContextPath()%>/resources/js/menu/controlMenu.js">
	

</script>
	 
	 
<%
	 UserSession usrSession = request.getSession().getAttribute(ConstantesSession.USR_SESSION)!=null?
										(UserSession)request.getSession().getAttribute(ConstantesSession.USR_SESSION):null;
	
%>
<table border="0">
	<tr>	
		<td>
			<ul class="menu" id="menu">
			 
			</ul>
	</td>
  </tr>
</table>
	
		
<script>
var menu=new menu.dd("menu");
menu.init("menu","menuhover");
 
<%
if(usrSession!=null){
	out.print(usrSession.getMenu());
	
}
%>
	
</script>	

