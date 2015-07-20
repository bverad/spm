package cl.adexus.isl.suseso.xmldsig;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.KeyGenerator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.IOUtils;
import org.apache.xml.security.c14n.Canonicalizer;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.transforms.Transforms;
import org.apache.xml.security.utils.Base64;
import org.apache.xml.security.utils.Constants;
import org.apache.xml.security.utils.ElementProxy;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

/**
 * Firma un archivo o string xml con informacion variable de llaves y
 * certificados.
 * 
 * @author Tropo, 2013.
 * @version 0.7
 * 
 */
public class SusesoSignature {

	// private static final String PRIVATE_KEY_ALIAS = "isl-p12";
	// private static final String PRIVATE_KEY_PASS = "";
	// private static final String KEY_STORE_PASS = "password";
	// private static final String KEY_STORE_TYPE = "JKS";
	// private static final String KEY_STORE_FILE = "c:\\borrame\\certificados.jks";

	// private static final String PRIVATE_CERT_ALIAS = "suseso_cert2";
	
	private File keyStoreFile;
	private InputStream keyStoreInputStream;
	private String privateKeyAlias;
	private String privateKeyPass;
	private String keyStorePass;
	private String keyStoreType;

	static {
		org.apache.xml.security.Init.init();
	}	
	
	/**
	 * Crea una instancia del objeto.
	 * @param keyStoreFilename Nombre/ruta del keystore (jks)
	 * @param keyStorePass Password del keystore.
	 * @param privateKeyAlias Alias en el keystore de la llave privada.
	 * @param privateKeyPass Password de la llave privada.
	 * @param keyStoreType Tipo de keystore.
	 */
	public SusesoSignature(String keyStoreFilename, String keyStorePass, String privateKeyAlias, String privateKeyPass ) {
		super();
		this.keyStoreFile = new File(keyStoreFilename);
		this.privateKeyAlias = privateKeyAlias;
		this.privateKeyPass = privateKeyPass;
		this.keyStorePass = keyStorePass;
		this.keyStoreType = "JKS";
	}
	
	/**
	 * Crea una instancia del objeto.
	 * @param keyStoreInputStream InputStream del keystore (jks)
	 * @param keyStorePass Password del keystore.
	 * @param privateKeyAlias Alias en el keystore de la llave privada.
	 * @param privateKeyPass Password de la llave privada.
	 * @param keyStoreType Tipo de keystore.
	 */
	public SusesoSignature(InputStream keyStoreInputStream, String keyStorePass, String privateKeyAlias, String privateKeyPass ) {
		super();
		this.keyStoreInputStream = keyStoreInputStream;
		this.privateKeyAlias = privateKeyAlias;
		this.privateKeyPass = privateKeyPass;
		this.keyStorePass = keyStorePass;
		this.keyStoreType = "JKS";
	}


	/**
	 * Firma un string xml.
	 * 
	 * @param xml El string xml.
	 * @param privateKeyFile El archivo con la llave que se usara para firmar.
	 * @return Un {@link ByteArrayOutputStream} con el xml firmado.
	 * @throws Exception
	 */
	public ByteArrayOutputStream signXml(String xml) throws Exception {
		final Document doc = loadXMLFromString(xml);
		return signDocument(doc);
	}

	/**
	 * Firma un archivo xml.
	 * 
	 * @param xmlFile El archivo xml.
	 * @param privateKeyFile El archivo con la llave que se usara para firmar.
	 * @return Un {@link ByteArrayOutputStream} con el xml firmado.
	 * @throws Exception
	 */
	public ByteArrayOutputStream signXml(InputStream xmlFile) throws Exception {
		final Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
		return signDocument(doc);
	}
	
	/**
	 * Muestra un {@link Document} a la salida estandar (System.out).
	 * @param doc El documento.
	 * @throws Exception
	 */
	public void outputDocToSystemOut(Document doc) throws Exception {
	    
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		transformer.setOutputProperty(OutputKeys.INDENT, "no");
		
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(System.out);
		transformer.transform(source, result);

	}
	
	/**
	 * Canoniza un {@link Document}
	 * @param doc El documento.
	 * @return El documento canonizado ALGO_ID_C14N_OMIT_COMMENTS.
	 */
	public Document canonizeDocument(Document doc) {
		if (null == doc) {
			return null;
		}
		Document retDoc;
		try {
			org.apache.xml.security.Init.init();

			Canonicalizer c14n = Canonicalizer.getInstance(Canonicalizer.ALGO_ID_C14N_OMIT_COMMENTS);
			byte outputBytes[] = c14n.canonicalizeSubtree(doc);

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			docFactory.setNamespaceAware(true);
			
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			docBuilder.setErrorHandler(new org.apache.xml.security.utils.IgnoreAllErrorHandler());
			
			retDoc = docBuilder.parse(new ByteArrayInputStream(outputBytes));
			
		} catch (Exception e) {
			return null;
		}
		return retDoc;
	}
	
	/**
	 * Firma un string xml (ojo: retorna {@link Document})
	 * 
	 * @param xml El string xml.
	 * @param privateKeyFile El archivo con la llave que se usara para firmar.
	 * @return Un {@link Document} con el xml firmado.
	 * @throws Exception
	 */
	public Document firmarXml(String xml) throws Exception {
		final Document doc = loadXMLFromString(xml);
		return firmarDocumento(doc);
	}
	
	/**
	 * Firma un {@link Document}
	 * @param doc El documento.
	 * @return El documento firmado.
	 * @throws Exception
	 */
	public Document firmarDocumento(Document doc) throws Exception {

		ElementProxy.setDefaultPrefix(Constants.SignatureSpecNS, "");

		KeyStore keyStore = null;
		if (this.keyStoreFile != null) {
			keyStore = loadKeyStore(this.keyStoreFile);
		} else if (this.keyStoreInputStream != null) {
			keyStore = loadKeyStore(this.keyStoreInputStream);
		}

		final XMLSignature sig = new XMLSignature(doc, null, XMLSignature.ALGO_ID_SIGNATURE_RSA, org.apache.xml.security.transforms.Transforms.TRANSFORM_C14N_OMIT_COMMENTS);

		// firma incluyendo al contenedor 'Signature'
		// agrega la firma despues del tag 'seguridad'
		Element elem = (Element) doc.getElementsByTagName("seguridad").item(0);
		
		// agrega la firma vacia (el contenedor)
		elem.appendChild(sig.getElement());

		final Transforms transforms = new Transforms(doc);
		transforms.addTransform(Transforms.TRANSFORM_ENVELOPED_SIGNATURE);
		sig.addDocument("", transforms, Constants.ALGO_ID_DIGEST_SHA1);

		final PrivateKey privateKey = (PrivateKey) keyStore.getKey(this.privateKeyAlias, this.privateKeyPass.toCharArray());

		// usar el certificado que acompana a la private key
		final X509Certificate cert = (X509Certificate) keyStore.getCertificate(this.privateKeyAlias);

		sig.addKeyInfo(cert); // agrega informacion del certificado
		// agrega informacion de la llave publica (modulus/exponent)
		// sig.addKeyInfo(cert.getPublicKey());
		sig.sign(privateKey); // firma con la llave privada

		// retorna el documento con la informacion de la firma

		return doc;
	}


	/**
	 * Firma un {@link Document}
	 * 
	 * @param doc El {@link Document}
	 * @param privateKeyFile El archivo con la llave que se usara para firmar.
	 * @return Un {@link ByteArrayOutputStream} con el archivo firmado.
	 * @throws Exception
	 */
	private ByteArrayOutputStream signDocument(Document doc) throws Exception {

		ElementProxy.setDefaultPrefix(Constants.SignatureSpecNS, "");

		KeyStore keyStore=null;
		if(this.keyStoreFile!=null){
			keyStore= loadKeyStore(this.keyStoreFile);
		}else if(this.keyStoreInputStream!=null){
			keyStore= loadKeyStore(this.keyStoreInputStream);
		}
			
		final XMLSignature sig = new XMLSignature(doc, null, XMLSignature.ALGO_ID_SIGNATURE_RSA);

		// firma incluyendo al contenedor 'Signature'
		// agrega la firma despues del tag 'seguridad'
		Element elem = (Element) doc.getElementsByTagName("seguridad").item(0);
		// agrega la firma vacia (el contenedor)
		elem.appendChild(sig.getElement());

		final Transforms transforms = new Transforms(doc);
		transforms.addTransform(Transforms.TRANSFORM_ENVELOPED_SIGNATURE);
		// transforms.addTransform(Transforms.TRANSFORM_C14N_WITH_COMMENTS);
		sig.addDocument("", transforms, Constants.ALGO_ID_DIGEST_SHA1);

		final PrivateKey privateKey = (PrivateKey) keyStore.getKey(this.privateKeyAlias, this.privateKeyPass.toCharArray());

		// final X509Certificate cert =
		// (X509Certificate)keyStore.getCertificate(PRIVATE_KEY_ALIAS);
		// usar el certificado que acompaña a la private key
		final X509Certificate cert = (X509Certificate) keyStore.getCertificate(this.privateKeyAlias);

		sig.addKeyInfo(cert); // agrega informacion del certificado
		// agrega informacion de la llave publica (modulus/exponent)
		// sig.addKeyInfo(cert.getPublicKey());
		sig.sign(privateKey); // firma con la llave privada

		// firma sin incluir el contenedor 'Signature'
		// para agregar la firma antes del ultimo tag
		// doc.getDocumentElement().appendChild(sig.getElement());

		// agrega la firma despues del tag 'seguridad'
		// Element elem = (Element)doc.getElementsByTagName("seguridad").item(0);
        // agrega la firma
		// elem.appendChild(sig.getElement());

		// prepara el stream
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		// XMLUtils.outputDOMc14nWithComments(doc, outputStream);
		outputStream.write(Canonicalizer.getInstance(Canonicalizer.ALGO_ID_C14N_WITH_COMMENTS).canonicalizeSubtree(doc));

		// retorna el documento con la informacion de la firma
		return outputStream;
	}

	public ByteArrayOutputStream encDIATElements(String xml) throws NoSuchAlgorithmException {

		KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");
		keyGenerator.init(192); // 3des, 192 bits

		return null;
	}

	public PublicKey getPemPublicKey(String filename, String algorithm) throws Exception {
		File f = new File(filename);
		FileInputStream fis = new FileInputStream(f);
		DataInputStream dis = new DataInputStream(fis);
		byte[] keyBytes = new byte[(int) f.length()];
		dis.readFully(keyBytes);
		dis.close();

		String temp = new String(keyBytes);
		String publicKeyPEM = temp.replace("-----BEGIN PUBLIC KEY-----\n", "");
		publicKeyPEM = publicKeyPEM.replace("-----END PUBLIC KEY-----", "");

		// Base64 b64 = new Base64();
		byte[] decoded = Base64.decode(publicKeyPEM);

		X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
		KeyFactory kf = KeyFactory.getInstance(algorithm);
		return kf.generatePublic(spec);
	}

	/**
	 * Carga keystore desde un archivo
	 * 
	 * @param privateKeyFile Archivo que contiene la llave privada
	 * @return Un {@link Keystore}
	 * @throws Exception
	 */
	private KeyStore loadKeyStore(File privateKeyFile) throws Exception {
		final InputStream fileInputStream = new FileInputStream(privateKeyFile);
		return loadKeyStore(fileInputStream);
	}
	
	/**
	 * Carga keystore desde un InputStream
	 * 
	 * @param privateKeyIS InputStream que contiene la llave privada
	 * @return Un {@link Keystore}
	 * @throws Exception
	 */
	private KeyStore loadKeyStore(InputStream privateKeyIS) throws Exception {
		try {
			final KeyStore keyStore = KeyStore.getInstance(this.keyStoreType);
			keyStore.load(privateKeyIS, this.keyStorePass.toCharArray());
			return keyStore;
		} finally {
			IOUtils.closeQuietly(privateKeyIS);
		}
	}
	

	/**
	 * Crea un {@link Document} a partir de un string 'xml'.
	 * 
	 * @param xml String 'xml'.
	 * @return {@link Document}
	 * @throws Exception
	 */
	public Document loadXMLFromString(String xml) throws Exception {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(xml));
		
		return builder.parse(is);
	}

	/**
	 * Escribe un stream a un archivo.
	 * 
	 * @param signedOutputStream El stream.
	 * @param fileName El archivo.
	 * @throws IOException
	 */
	public void output(ByteArrayOutputStream signedOutputStream, String fileName) throws IOException {
		final OutputStream fileOutputStream = new FileOutputStream(fileName);
		try {
			fileOutputStream.write(signedOutputStream.toByteArray());
			fileOutputStream.flush();
		} finally {
			IOUtils.closeQuietly(fileOutputStream);
		}
	}
}
