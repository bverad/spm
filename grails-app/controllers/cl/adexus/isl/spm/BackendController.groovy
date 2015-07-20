package cl.adexus.isl.spm

class BackendController {
	
	def DenunciaService
	
    def index() { }
	
	def enviarDIATaSUSESO(){
		def r=DenunciaService.enviarDIATaSUSESO(params.diatId)
		return r
	}
	
	def borrarXMLEnviado(){
		def diat=DIAT.get(params.diatId.toLong())
		diat.xmlEnviado=null
		diat.xmlRecibido=null
		diat.save(flush:true)
		redirect ([action: 'index'])
		
	}
	
	def borrarXMLRecibido(){
		def diat=DIAT.get(params.diatId.toLong())
		diat.xmlRecibido=null
		diat.save(flush:true)
		redirect ([action: 'index'])
		
	}
	
	def enviarDIEPaSUSESO(){
		def r=DenunciaService.enviarDIEPaSUSESO(params.diepId)
		return r
	}
	
	def borrarXMLEnviadoEP(){
		def diep=DIEP.get(params.diepId.toLong())
		diep.xmlEnviado=null
		diep.xmlRecibido=null
		diep.save(flush:true)
		redirect ([action: 'index'])
		
	}
	
	def borrarXMLRecibidoEP(){
		def diep=DIEP.get(params.diepId.toLong())
		diep.xmlRecibido=null
		diep.save(flush:true)
		redirect ([action: 'index'])
		
	}
}
