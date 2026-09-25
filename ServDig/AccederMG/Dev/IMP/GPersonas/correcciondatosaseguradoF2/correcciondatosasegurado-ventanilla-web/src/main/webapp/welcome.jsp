<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    
     import="java.util.*,
        org.springframework.context.ApplicationContext,
    	org.springframework.web.context.support.WebApplicationContextUtils,
        mx.gob.imss.cit.cda.web.utils.AppConfigUtil" 
    
   %>
<%
  ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
    AppConfigUtil appConfigUtil = (AppConfigUtil) applicationContext.getBean("appConfigUtil");
    String redirect = (String) appConfigUtil.getRedirect();
	response.sendRedirect(request.getContextPath()+redirect);
%>