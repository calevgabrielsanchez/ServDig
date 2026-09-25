<xsl:stylesheet version="1.0"
 xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
 xmlns:mx="http://mx.gob.imss.email.model">
 <xsl:output omit-xml-declaration="yes" indent="yes"/>

 <xsl:template match="node()|@*">
  <xsl:copy>
   <xsl:apply-templates select="node()|@*"/>
  </xsl:copy>
 </xsl:template>

 <xsl:template match="*">
  <xsl:element name="mx:{name()}" namespace="http://mx.gob.imss.email.model">
    <xsl:copy-of select="namespace::*"/>
    <xsl:apply-templates select="node()|@*"/>
  </xsl:element>
 </xsl:template>
</xsl:stylesheet>