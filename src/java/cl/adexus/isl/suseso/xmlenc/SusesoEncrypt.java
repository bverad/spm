package cl.adexus.isl.suseso.xmlenc;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Iterator;
import java.util.Vector;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xml.security.encryption.EncryptedData;
import org.apache.xml.security.encryption.EncryptedKey;
import org.apache.xml.security.encryption.XMLCipher;
import org.apache.xml.security.keys.KeyInfo;
import org.apache.xml.security.utils.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public class SusesoEncrypt {
	
	private InputStream publicKeyInputStream;
	
	static {
		org.apache.xml.security.Init.init();
	}
	
	public SusesoEncrypt(InputStream publicKeyInputStream ) {
		super();
		this.publicKeyInputStream = publicKeyInputStream;
	}
	
	public ByteArrayOutputStream encryptXml(String xml) throws Exception {
		final Document doc = loadXMLFromString(xml);
		return encryptDocument(doc);
	}
	
	private Document loadXMLFromString(String xml) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(xml));
		return builder.parse(is);
	}

	/**
	 * Encripta en el documento los elementos listados en el vector.
	 * @param elementos Vector con los tag's a encriptar.
	 * @param document {@link Document} a modificar.
	 * @return El {@link Document} <b>normalizado</b> y con los elementos encriptados.
	 * @throws Exception
	 */
	public Document encriptar(Vector<String> elementos, Document document) throws Exception {

		if (document == null) {
			return null;
		}

		// Traemos la llave publica
		PublicKey rsaPubKey = getPemPublicKey(this.publicKeyInputStream, "RSA");

		if (elementos != null) {
			
			Iterator<String> itr = elementos.iterator();

			while (itr.hasNext()) {

				String tag = (String) itr.next();
				int items = document.getElementsByTagName(tag).getLength();

				for (int i = 0; i < items; i++) {

					Element e = (Element) document.getElementsByTagName(tag).item(i);

					Key symmetricKey = GenerateDataEncryptionKey("DESede", 168);

					// cifra la llave
					XMLCipher keyCipher = XMLCipher.getInstance(XMLCipher.RSA_v1dot5);
					keyCipher.init(XMLCipher.WRAP_MODE, rsaPubKey);
					EncryptedKey encryptedKey = keyCipher.encryptKey(document, symmetricKey);

					// cifra el xml
					XMLCipher xmlCipher = XMLCipher.getInstance(XMLCipher.TRIPLEDES);
					xmlCipher.init(XMLCipher.ENCRYPT_MODE, symmetricKey);

					EncryptedData encryptedData = xmlCipher.getEncryptedData();
					KeyInfo keyInfo = new KeyInfo(document);
					keyInfo.add(encryptedKey);
					encryptedData.setKeyInfo(keyInfo);

					xmlCipher.doFinal(document, e, true);

				}
			}

		}

		document.normalizeDocument();

		return document;
	}

	/**
	 * Genera una {@link SecretKey}.
	 * @param algName Nombre estandar del algoritmo que se requiere.
	 * @param bitSize Depende del algoritmo. Se indica en bits.
	 * @return La {@link SecretKey}
	 * @throws Exception
	 */
	private SecretKey GenerateDataEncryptionKey(final String algName, final int bitSize) throws Exception {
		
		KeyGenerator keyGenerator = KeyGenerator.getInstance(algName);
		keyGenerator.init(bitSize);

		return keyGenerator.generateKey();
	}
	

	private ByteArrayOutputStream encryptDocument(Document doc) throws Exception {
		
		
		//Traemos la llave publica
		PublicKey rsaPubKey=getPemPublicKey(this.publicKeyInputStream,"RSA");
		
		Vector<String> elementos=new Vector<String>(4);
		elementos.add("diagnostico");
		//elementos.add("codigo_diagnostico");
		elementos.add("ubicacion");
		//elementos.add("codigo_ubicacion");
			
		Iterator<String> itr = elementos.iterator();
		while(itr.hasNext()){
	    	String tag=(String) itr.next();
			int items=doc.getElementsByTagName(tag).getLength();
			for(int i=0;i<items;i++){
			
				Element e = (Element) doc.getElementsByTagName(tag).item(i);
		
				//Generacion de Llave de Session (TripleDES 192bit)
				KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");
				Key sessionKey = keyGenerator.generateKey();
				
				//Wrap de la llave de session con RSA
		        XMLCipher cipher = XMLCipher.getInstance(XMLCipher.RSA_v1dot5);
				cipher.init(XMLCipher.WRAP_MODE, rsaPubKey);
				EncryptedKey encryptedKey = cipher.encryptKey(doc, sessionKey);
				
		        // encrypta
		        cipher = XMLCipher.getInstance(XMLCipher.TRIPLEDES);
		        cipher.init(XMLCipher.ENCRYPT_MODE, sessionKey);
		   
		        //Agregamos la llave de session encriptada al mensaje
		        EncryptedData encryptedData = cipher.getEncryptedData();
		        KeyInfo encryptedDataKeyInfo = new KeyInfo(doc);
		        encryptedDataKeyInfo.add(encryptedKey);
		        encryptedData.setKeyInfo(encryptedDataKeyInfo);
		        
		        cipher.doFinal(doc, e,true);
			}
		}
        
        //Enchulamos el XML
		Writer out = new StringWriter();
		Transformer tf = TransformerFactory.newInstance().newTransformer();
		tf.setOutputProperty(OutputKeys.INDENT, "no");
		tf.transform(new DOMSource(doc), new StreamResult(out));
		
		String outXml=out.toString();
		
		//String outXml=Canonicalizer.getInstance(Canonicalizer.ALGO_ID_C14N_EXCL_OMIT_COMMENTS).canonicalizeSubtree(doc).toString();
		
       
        

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(outXml.getBytes());

		// retorna el documento con la informacion de la firma
		return outputStream;
	}
	
	public PublicKey getPemPublicKey(InputStream inputStream, String algorithm) throws Exception {
		
		//Lee el archivo completo
		byte[] contents = new byte[1024];

        int bytesRead=0;
        String strFileContents=""; 
        while( (bytesRead = inputStream.read(contents)) != -1){ 
            strFileContents = new String(contents, 0, bytesRead);               
        }
        
        System.out.println("archivo inicio : " + strFileContents);
        
        String publicKeyPEM = strFileContents.replace("-----BEGIN PUBLIC KEY-----", "");
		publicKeyPEM = publicKeyPEM.replace("-----END PUBLIC KEY-----", "");
		
		System.out.println("archivo fin : " + publicKeyPEM);
		

		// Base64 b64 = new Base64();
		byte[] decoded = Base64.decode(publicKeyPEM);

		X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
		KeyFactory kf = KeyFactory.getInstance(algorithm);
		return kf.generatePublic(spec);
	}
	
}
