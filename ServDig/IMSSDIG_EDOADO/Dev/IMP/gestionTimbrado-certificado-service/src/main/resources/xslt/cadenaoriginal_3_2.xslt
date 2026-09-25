<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions" xmlns:cfdi="http://www.sat.gob.mx/cfd/3" xmlns:ecc="http://www.sat.gob.mx/ecc" xmlns:psgecfd="http://www.sat.gob.mx/psgecfd" xmlns:donat="http://www.sat.gob.mx/donat" xmlns:divisas="http://www.sat.gob.mx/divisas" xmlns:detallista="http://www.sat.gob.mx/detallista" xmlns:ecb="http://www.sat.gob.mx/ecb" xmlns:implocal="http://www.sat.gob.mx/implocal" xmlns:terceros="http://www.sat.gob.mx/terceros" xmlns:iedu="http://www.sat.gob.mx/iedu" xmlns:ventavehiculos="http://www.sat.gob.mx/ventavehiculos" xmlns:pfic="http://www.sat.gob.mx/pfic" xmlns:tpe="http://www.sat.gob.mx/TuristaPasajeroExtranjero" xmlns:leyendasFisc="http://www.sat.gob.mx/leyendasFiscales" xmlns:spei="http://www.sat.gob.mx/spei" xmlns:nomina="http://www.sat.gob.mx/nomina" xmlns:registrofiscal="http://www.sat.gob.mx/registrofiscal" xmlns:pagoenespecie="http://www.sat.gob.mx/pagoenespecie" xmlns:consumodecombustibles="http://www.sat.gob.mx/consumodecombustibles" xmlns:valesdedespensa="http://www.sat.gob.mx/valesdedespensa" xmlns:aerolineas="http://www.sat.gob.mx/aerolineas" xmlns:notariospublicos="http://www.sat.gob.mx/notariospublicos">



  <!-- Con el siguiente método se establece que la salida deberá ser en texto -->
  <xsl:output method="text" version="1.0" encoding="UTF-8" indent="no"/>
  <!--
		En esta sección se define la inclusión de las plantillas de utilerías para colapsar espacios
	-->
  
  <!-- 
		En esta sección se define la inclusión de las demás plantillas de transformación para 
		la generación de las cadenas originales de los complementos fiscales 
	-->
	<!--
	
	
	
  <xsl:include href="http://www.sat.gob.mx/sitio_internet/cfd/ecc/ecc.xslt"/>
  <xsl:include href="http://www.sat.gob.mx/sitio_internet/cfd/psgecfd/psgecfd.xslt"/>
  <xsl:include href="http://www.sat.gob.mx/sitio_internet/cfd/donat/donat11.xslt"/>
  <xsl:include href="http://www.sat.gob.mx/sitio_internet/cfd/divisas/divisas.xslt"/>
  <xsl:include href="http://www.sat.gob.mx/sitio_internet/cfd/ecb/ecb.xslt"/>
  <xsl:include href="http://www.sat.gob.mx/sitio_internet/cfd/detallista/detallista.xslt"/>
  <xsl:include href="http://www.sat.gob.mx/sitio_internet/cfd/implocal/implocal.xslt"/>
  <xsl:include href="http://www.sat.gob.mx/sitio_internet/cfd/terceros/terceros11.xslt"/>
  <xsl:include href="http://www.sat.gob.mx/sitio_internet/cfd/iedu/iedu.xslt"/>
  <xsl:include href="http://www.sat.gob.mx/sitio_internet/cfd/ventavehiculos/ventavehiculos11.xslt"/>
  <xsl:include href="http://www.sat.gob.mx/sitio_internet/cfd/pfic/pfic.xslt"/>
  <xsl:include href="http://www.sat.gob.mx/sitio_internet/cfd/TuristaPasajeroExtranjero/TuristaPasajeroExtranjero.xslt"/>
  <xsl:include href="http://www.sat.gob.mx/sitio_internet/cfd/leyendasFiscales/leyendasFisc.xslt"/>
  <xsl:include href="http://www.sat.gob.mx/sitio_internet/cfd/spei/spei.xslt"/>
  <xsl:include href="http://www.sat.gob.mx/sitio_internet/cfd/nomina/nomina11.xslt"/>
  <xsl:include href="http://www.sat.gob.mx/sitio_internet/cfd/cfdiregistrofiscal/cfdiregistrofiscal.xslt"/>
  <xsl:include href="http://www.sat.gob.mx/sitio_internet/cfd/pagoenespecie/pagoenespecie.xslt"/>
  <xsl:include href="http://www.sat.gob.mx/sitio_internet/cfd/consumodecombustibles/consumodecombustibles.xslt"/>
  <xsl:include href="http://www.sat.gob.mx/sitio_internet/cfd/valesdedespensa/valesdedespensa.xslt"/>
  <xsl:include href="http://www.sat.gob.mx/sitio_internet/cfd/aerolineas/aerolineas.xslt"/>
  <xsl:include href="http://www.sat.gob.mx/sitio_internet/cfd/notariospublicos/notariospublicos.xslt"/>
-->
	
	


	<!-- Manejador de datos requeridos -->
	<xsl:template name="Requerido">
		<xsl:param name="valor"/>|<xsl:call-template name="ManejaEspacios">
			<xsl:with-param name="s" select="$valor"/>
		</xsl:call-template>
	</xsl:template>

	<!-- Manejador de datos opcionales -->
	<xsl:template name="Opcional">
		<xsl:param name="valor"/>
		<xsl:if test="$valor">|<xsl:call-template name="ManejaEspacios"><xsl:with-param name="s" select="$valor"/></xsl:call-template></xsl:if>
	</xsl:template>
	
	<!-- Normalizador de espacios en blanco -->
	<xsl:template name="ManejaEspacios">
		<xsl:param name="s"/>
		<xsl:value-of select="normalize-space(string($s))"/>
	</xsl:template>

  <!-- Aquí iniciamos el procesamiento de la cadena original con su | inicial y el terminador || -->
  <xsl:template match="/">|<xsl:apply-templates select="/cfdi:Comprobante"/>||</xsl:template>
  <xsl:template match="cfdi:Comprobante">
    <!-- Iniciamos el tratamiento de los atributos de comprobante -->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@version"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@fecha"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@tipoDeComprobante"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@formaDePago"/>
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@condicionesDePago"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@subTotal"/>
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@descuento"/>
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@TipoCambio"/>
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@Moneda"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@total"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@metodoDePago"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@LugarExpedicion"/>
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@NumCtaPago"/>
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@FolioFiscalOrig"/>
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@SerieFolioFiscalOrig"/>
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@FechaFolioFiscalOrig"/>
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@MontoFolioFiscalOrig"/>
    </xsl:call-template>
    <!--
			Llamadas para procesar al los sub nodos del comprobante
		-->
    <xsl:apply-templates select="./cfdi:Emisor"/>
    <xsl:apply-templates select="./cfdi:Receptor"/>
    <xsl:apply-templates select="./cfdi:Conceptos"/>
    <xsl:apply-templates select="./cfdi:Impuestos"/>
    <xsl:apply-templates select="./cfdi:Complemento"/>
  </xsl:template>
  
</xsl:stylesheet>