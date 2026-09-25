<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<script language="JavaScript">
var fechaServidor =  new Date('<%=new SimpleDateFormat("MM/dd/yyyy").format(new Date())%>');

<%
	Calendar cal = Calendar.getInstance();
	cal.add(Calendar.DAY_OF_YEAR, -44);

	
	Calendar calMenosCatorce = Calendar.getInstance();
	calMenosCatorce.add(Calendar.DAY_OF_YEAR, -14);
	
%>

var fechaActualMenos45dia = new Date('<%=new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime())%>');
var fechaActualMenos14dia = new Date('<%=new SimpleDateFormat("MM/dd/yyyy").format(calMenosCatorce.getTime())%>');
function getFechaServidor(){	return fechaServidor; }
function getFechaServidorMenos45Dias(){	return fechaActualMenos45dia; }
function getFechaServidorMenos14Dias(){	return fechaActualMenos14dia; }
/**
 * Variables globales para control de la respuesta del web service
 * de validacion del registro patronal
 * validaRegistroPatronalWS(String registroPatronal, Long subdelegacion)
 * -1 Registro patronal invalido
 * -2 Registro patronal no pertenece a a la subdelegacion 
 */
var JSERROR_WS = -1;
var JSERROR_WS_SUBDEL = -2;

//Se deshabilitan los dias inhabiles para loos datepicker
var diasFeriado = [
	           [1, 1],[2, 6],[3, 19],[5, 1],[5, 5],[5, 10],[9, 15],[9, 16],[11, 19],[12, 1],[12, 25]
	       ];

	       function obtieneDiasFeriados(date) {
	           for (i = 0; i < diasFeriado.length; i++) {
	               if (date.getMonth() == diasFeriado[i][0] - 1 && date.getDate() == diasFeriado[i][1]) {
	                   return [false, diasFeriado[i][2] + '_day'];
	               }
	           }
	           return [true, ''];
	       }

	       function noWeekendsOrHolidays(date) {
	           var noWeekend = $.datepicker.noWeekends(date);
	           if (noWeekend[0]) {
	               return obtieneDiasFeriados(date);
	           } else {
	               return noWeekend;
	           }
	       }






</script>
