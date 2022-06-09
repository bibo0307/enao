<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/*">

		
			<FieldList>
			<xsl:for-each select = "*" >

				<Type>
					<xsl:value-of select = "/../Type"/>
				</Type>
				

			</xsl:for-each>	
			</FieldList>

		
	</xsl:template>
</xsl:stylesheet>