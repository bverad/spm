package cl.adexus.isl.suseso.clients


import wslite.http.HTTPRequest
import wslite.http.HTTPResponse
import wslite.soap.SOAPClient

/**
 * Clientes de los servicios web de suseso.
 * 
 * @author Tropo, 2013
 *
 */
class SusesoWSClients {
	
	def String username;
	def String password;
	def String WSIngreso;
	def String WSToken;
	def String WSAnulacion;

	/**
	 * Cliente xml-rpc para ingreso de documentos.
	 * 
	 * @param token 
	 * @param funcion 'I' Ingreso
	 * @param tipoDocumento '12'
	 * @param xml Los datos.
	 * @param cun El CUN.
	 * @return String xml retornado por suseso
	 */
	def String wsIngresoServiceClient(String token, String tipoDocumento, String xml, String cun){
		log.info("Ejecutando metodo wsIngresoServiceClient")
		def funcion = "I"
		
		def client = new SOAPClient(WSIngreso+'?wsdl')

		def response = client.send(SOAPAction:WSIngreso,
							connectTimeout:2000,
                            readTimeout:4000) {
			envelopeAttributes ('xmlns:wst':'http://WSIngreso/')
			body {
				'wst:WsSiatepIng'{
					CtaUsr(username)
					PswUsr(password)
					Token(token)
					TipoDoc(tipoDocumento)
					Funcion(funcion)
					Xml(xml)
					CUN(cun)
				}
			}
		}

		log(response?.httpRequest, response?.httpResponse)

		return response.WsSiatepIngResponse.return
		
		
	}

	/**
	 * Cliente soap para obtencion de token.
	 * @return Token.
	 */
	def String wsTokenClient(){

		def ctaUsr = username
		def claveUsr = password
		def funcion = "PT"
		def token = ""

		def client = new SOAPClient(WSToken+'?wsdl')

		def response = client.send(SOAPAction:WSToken,
							connectTimeout:2000,
                            readTimeout:4000) {
			envelopeAttributes ('xmlns:wst':'http://WSToken/')
			body {
				'wst:WSToken'{
					CtaUsr(ctaUsr)
					ClaveUsr(claveUsr)
					Funcion(funcion)
					Token(token)
				}
			}
		}

		log(response?.httpRequest, response?.httpResponse)

		return response.WSTokenResponse.return
	}

	/**
	 * Cliente para wsAnulacion.
	 * @param token Token.
	 * @param cun CUN.
	 * @param folio Folio.
	 * @param tipoDoc Tipo de documento.
	 * @return Resultado de la operacion.
	 */
	def String wsAnulacionClient(String token, String cun, String folio, String tipoDoc){
		def ctaUsr = username
		def claveUsr = password

		def client = new SOAPClient(WSAnulacion+'?wsdl')

		def response = client.send(SOAPAction:WSAnulacion,
							connectTimeout:2000,
                            readTimeout:4000) {
			envelopeAttributes ('xmlns:ws':'http://ws/')
			body {
				'ws:anular'() {
					Token(token)
					CtaUsr(ctaUsr)
					PswUsr(claveUsr)
					CUN(cun)
					TipoDoc(tipoDoc)
					Folio(folio)
				}
			}
		}

		log(response?.httpRequest, response?.httpResponse)

		return response.anularResponse.return
	}

	private void log(HTTPRequest request, HTTPResponse response) {
		// log.info ("HTTPRequest $request with content:\n${request?.contentAsString}")
		// log.info ("HTTPResponse $response with content:\n${response?.contentAsString}")
	}
}