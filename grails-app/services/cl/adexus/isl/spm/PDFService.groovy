package cl.adexus.isl.spm

import cl.adexus.isl.spm.domain.CuestionarioTrabajoPDF
import cl.adexus.isl.spm.domain.DiatPDF
import cl.adexus.isl.spm.domain.OpaPDF

import cl.adexus.isl.spm.domain.Constantes
import com.itextpdf.text.pdf.AcroFields
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.PdfStamper
import java.beans.BeanInfo
import java.beans.PropertyDescriptor
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.beans.Introspector;

class PDFService {

		
	/**
	 * @param data
	 * @param TIPOS_PDF
	 * @return
	 */
	def doPdf(Object data, String tiposPdf) {
		
				ByteArrayOutputStream baos;
				PdfReader reader;
				PdfStamper stamper;
				try {
					// creating PDF
					baos = new ByteArrayOutputStream();
					InputStream is = getPDFTemplate(tiposPdf);
					reader = new PdfReader(is, null);
					
					stamper = new PdfStamper(reader, baos);
					
					// Transformo a un map
					Map<String, String> map =  objectToMap(data);
					
					// Manipuluación pdf
					Iterator it = map.entrySet().iterator();
					while (it.hasNext()) {
						Map.Entry pair = (Map.Entry)it.next();
						String propNombre = pair.getKey().toString();
						String propValue = pair.getValue().toString();
						if (propNombre.equalsIgnoreCase("esBorrador") && propValue.equalsIgnoreCase("false")){
							stamper.getAcroFields().setField(propNombre, "");
						} else {
							stamper.getAcroFields().setField(propNombre, propValue);
						}
						
					}
					stamper.setFormFlattening(true);

				} catch (Exception e) {
					e.printStackTrace()
					
				} finally {
					// Gently close streams.
					stamper.close();
					reader.close();
				}
		
				return baos;
			}
	
	
	/**
	 * Devuelve un InputStream con el template del pdf con acroFrom
	 * 
	 * @param tipoPdf Enumeración
	 * @return
	 */
	private InputStream getPDFTemplate(String tipoPdf){
		InputStream inputStream;
		switch(tipoPdf) {
			case Constantes.TIPOS_PDF_ALLA:
				inputStream =  this.class.classLoader.getResourceAsStream('pdfs/ALLA.pdf');
				break;
				
			case Constantes.TIPOS_PDF_ALME:
				inputStream =  this.class.classLoader.getResourceAsStream('pdfs/ALME.pdf');
				break;
				
			case Constantes.TIPOS_PDF_CUESTIONARIO_TRABAJO:
				inputStream =  this.class.classLoader.getResourceAsStream('pdfs/Cuestionario-Trabajo.pdf');
				break;
				
			case Constantes.TIPOS_PDF_CUESTIONARIO_TRAYECTO:
				inputStream = this.class.classLoader.getResourceAsStream('pdfs/Cuestionario-Trayecto.pdf');
				break;
				
			case Constantes.TIPOS_PDF_DIAT:
				inputStream = this.class.classLoader.getResourceAsStream('pdfs/DIAT.pdf');
				break;
				
			case Constantes.TIPOS_PDF_DIEP:
				inputStream = this.class.classLoader.getResourceAsStream('pdfs/DIEP.pdf');
				break;

			case Constantes.TIPOS_PDF_OPA:
				inputStream = this.class.classLoader.getResourceAsStream('pdfs/OPA.pdf');
				break;
								
			case Constantes.TIPOS_PDF_OPAEP:
				inputStream = this.class.classLoader.getResourceAsStream('pdfs/OPAEP.pdf');
				break;	
				
			case Constantes.TIPOS_PDF_ODA_AHI:
				inputStream = this.class.classLoader.getResourceAsStream('pdfs/ODA_AHI.pdf');
				break;	
				
			case Constantes.TIPOS_PDF_ODA_EAVD:
				inputStream = this.class.classLoader.getResourceAsStream('pdfs/ODA_EAVD.pdf');
				break;	
				
			case Constantes.TIPOS_PDF_RECA:
				inputStream = this.class.classLoader.getResourceAsStream('pdfs/RECA.pdf');
				break;
				
			case Constantes.TIPOS_PDF_RELA:
				inputStream = this.class.classLoader.getResourceAsStream('pdfs/RELA.pdf');
				break;
				
			case Constantes.TIPOS_PDF_SOL_REEMBOLSO:
				inputStream =  this.class.classLoader.getResourceAsStream('pdfs/Solicitud_Reembolso.pdf');
				break;
				

		}
		return inputStream;
	}
	
	
	
	/**
	 * Obtiene un Map a partir de un POJO
	 * 
	 * @param someObject
	 * @return
	 */
	private Map<String, String> objectToMap(Object someObject){
		Map<String, String> map = new HashMap<String, String>();
		
		for (Field field : someObject.getClass().getDeclaredFields()) {
			field.setAccessible(true); // You might want to set modifier to public first.
			Object value = field.get(someObject);
			if (value != null) {
				map.putAt(field.getName(), value);
				//System.out.log.info(field.getName() + "=" + value);
			}
		}
		return map;
	}
		
}
