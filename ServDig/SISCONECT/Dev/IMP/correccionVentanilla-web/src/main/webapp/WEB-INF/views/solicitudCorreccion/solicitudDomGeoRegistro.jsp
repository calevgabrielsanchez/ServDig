<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
	
	
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>

				     
		    <div id="dgDomGeoRegistro"  style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
			    <div id="wrapperDialogRegistro" style="background-color: #f2fff2;">	
					<form:form modelAttribute="crtSolicitudcorr" action="correcion/sessionDomicilioGeografico.do"
						method="post" id="domGeoFormRegistro">						
							<jsp:include page="../domicilioGeografico/domGeoRegistroSession.jsp" />							
					</form:form>							    
		    	</div>
			</div>
			