<%@ include file="../general/taglibs.jsp"%>


<!DOCTYPE html>
<html lang="es">
<head>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="max-age=100, must-revalidate" />
<meta name="description"
	content="Instituto Mexicano del Seguro Social Portal DELTA" />
<meta name="viewport" content="width=device-width, initial-scale=1">

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><tiles:insertAttribute name="title" ignore="true" /></title>

<jsp:include page="staticResources.jsp">
	<jsp:param value="true" name="isGobMxIncluded"/>
</jsp:include>

<script>
	var context_path = '<%= request.getContextPath()%>';
	history.go(1);
	$(function(){
		$('form:not(.formNotBlock)').submit(function(){
			$.blockUI();
		});
	});
	
	/**
	 * Evita que el boton back regrese a la pagina anterior
	 * 
	 */
	function blockBackButton(){
		window.location.hash="ciudadano";
		window.location.hash="ciudadano";
		window.location.hash="ciudadano";
		window.onhashchange=function(){window.location.hash="ciudadano";};
	}
</script>

<!--<style>
    .u1st .accessibilityClass {
        position: fixed !important;
        top: 55px;
        left: -50px;
        transform: rotate(90deg);
        _position: absolute !important;
       _top: expression(document.compatMode && document.compatMode = 'CSS1Compat' ? documentElement.scrollTop : document.body.scrollTop);
        z-index: 1000000;
        cursor: pointer;
        text-align: center;
        padding: 3px 0;
        border-radius: 4px;
        font: bold 15px Arial !important;
        background:  #F39200 !important;
        color: #3B3835 !important;
        padding: 7px 20px 7px 20px;

    }
</style>
<script type="text/javascript" id="User1st_Settings" >

	window['User1st'] = {
		Web : {
			settings : {
				accessibilityBtn : {
					useMyClass : 'accessibilityClass'
				}
			}
		}
	};
</script>


<script type="text/javascript" id="User1st_Loader" src="https://fe.user1st.info/Loader/head"></script>-->

</head>

<body>	
	<!-- Begin Digital Analytix Tag 1.1302.13 -->
	<script type="text/javascript">
		function udm_(e){var t="comScore=",n=document,r=n.cookie,i="",s="indexOf",o="substring",u="length",a=2048,f,l="&ns_",c="&",h,p,d,v,m=window,g=m.encodeURIComponent||escape;if(r[s](t)+1)for(d=0,p=r.split(";"),v=p[u];d<v;d++)h=p[d][s](t),h+1&&(i=c+unescape(p[d][o](h+t[u])));e+=l+"_t="+ +(new Date)+l+"c="+(n.characterSet||n.defaultCharset||"")+"&c8="+g(n.title)+i+"&c7="+g(n.URL)+"&c9="+g(n.referrer),e[u]>a&&e[s](c)>0&&(f=e[o](0,a-8).lastIndexOf(c),e=(e[o](0,f)+l+"cut="+g(e[o](f+1)))[o](0,a)),n.images?(h=new Image,m.ns_p||(ns_p=h),h.src=e):n.write("<","p","><",'img src="',e,'" height="1" width="1" alt="*"',"><","/p",">")};
		function uid_call(a, b){
			ui_c2 = 17183199; // your corporate c2 client value
			ui_ns_site = 'gobmx'; // your sites identifier
			window.b_ui_event = window.c_ui_event != null ? window.c_ui_event:"",window.c_ui_event = a;
			var ui_pixel_url = 'https://sb.scorecardresearch.com/p?c1=2&c2='+ui_c2+'&ns_site='+ui_ns_site+'&name='+a+'&ns_type=hidden&type=hidden&ns_ui_type='+b;
			var b="comScore=",c=document,d=c.cookie,e="",f="indexOf",g="substring",h="length",i=2048,j,k="&ns_",l="&",m,n,o,p,q=window,r=q.encodeURIComponent||escape;if(d[f](b)+1)for(o=0,n=d.split(";"),p=n[h];o<p;o++)m=n[o][f](b),m+1&&(e=l+unescape(n[o][g](m+b[h])));ui_pixel_url+=k+"_t="+ +(new Date)+k+"c="+(c.characterSet||c.defaultCharset||"")+"&c8="+r(c.title)+e+"&c7="+r(c.URL)+"&c9="+r(c.referrer)+"&b_ui_event="+b_ui_event+"&c_ui_event="+c_ui_event,ui_pixel_url[h]>i&&ui_pixel_url[f](l)>0&&(j=ui_pixel_url[g](0,i-8).lastIndexOf(l),ui_pixel_url=(ui_pixel_url[g](0,j)+k+"cut="+r(ui_pixel_url[g](j+1)))[g](0,i)),c.images?(m=new Image,q.ns_p||(ns_p=m),m.src=ui_pixel_url):c.write("<p><img src='",ui_pixel_url,"' height='1' width='1' alt='*'></p>");
		}
		udm_('https://sb.scorecardresearch.com/b?c1=2&c2=17183199&ns_site=gobmx&name=imss.ciudadano<tiles:insertAttribute name="nameComScore"/>');
	</script>
	<noscript><p><img src="https://sb.scorecardresearch.com/p?c1=2&amp;c2=17183199&amp;ns_site=gobmx&amp;name=imss.ciudadano<tiles:insertAttribute name="nameComScore"/>" height="1" width="1" alt="*"></p></noscript> 
	
	
	<!-- Contenido -->
	<main class="page">
		<div class="container top-buffer">
			<tiles:insertAttribute name="navegacion" />
			<tiles:insertAttribute name="contenido" />
		</div>
	</main>
			
	<div style="display: none;">
		<c:set var="contextpath" value="<%=request.getContextPath()%>" />
		<form id="formCerrarSesion"
			action="${pageContext.servletContext.contextPath}/j_spring_security_logout"
			method="get"></form>
	</div>
	
	<div id="dialogEndOfSession" style="display: none"
		title="Sesi&oacute;n terminada por inactividad">
		<span>Su sesi&oacute;n se ha desactivado debido a inactividad.</span>
	</div>
	
	<div>
		<!-- GobMx -->
		<jsp:include page="footer.jsp"/>
	</div>
	
	<script>
	 	$gmx(document).ready(function() {  
	 		/* 
	 		 * Fix para que los dialogos creados con jQueryUI
	 		 * no tengan conflictos con bootstrap
	 		 */
			$.fn.button.noConflict();
		});
	</script>
	
	<!-- Begin DAx cs.js import -->
	<script type="text/javascript" src="https://sb.scorecardresearch.com/c2/17183199/ct.js"></script>
	<script type="text/JavaScript" src="${mvn.url.encuesta}"></script>
    <jsp:include page="btn-accesibilidad.jsp"/>
</body>
</html>
