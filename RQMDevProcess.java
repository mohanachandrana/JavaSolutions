

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.OutputKeys;
import org.w3c.dom.Attr;
public class RQMDevProcess {
	
public File fXmlFile = new File("C:/work/Inputtestcase.xml");
public File outputFile = new File("C:/work/RTCTemp.xml");
public DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	 
	
	public void processXMLinput(){
		try{
		 dbFactory.setNamespaceAware(true);
		 DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		 Document doc = dBuilder.parse(fXmlFile);
		 Document outputDoc = dBuilder.newDocument();
		 doc.getDocumentElement().normalize();
		 outputDoc.setXmlVersion("1.0");
		 outputDoc.setXmlStandalone(true);
		 NodeList nList = doc.getElementsByTagName("testcase");
		 for (int temp = 0; temp < nList.getLength(); temp++)
		  {
			 
			 Node nNode = nList.item(temp);
			 System.out.println("The Node is "+nNode.getLocalName());
			 if (nNode.getNodeType() == Node.ELEMENT_NODE) 
			 {
				 Element eElement = (Element) nNode;
				 //eElement.removeAttributeNS("http://purl.org/dc/elements/1.1/", "xmlns:dc");
				 eElement.setAttributeNS("http://purl.org/dc/elements/1.1/", "mlns:alm","http://purl.org/dc/elements/1.1/");
				 eElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:jzalm", "http://www.w3.org/2000/xmlns/");
				// outputDoc.appendChild(eElement.cloneNode(true));
				 Element testCase = outputDoc.createElementNS("http://www.w3.org/2000/xmlns/", "testcase");
				 testCase.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:dc", "http://purl.org/dc/elements/1.1/");
				 outputDoc.appendChild(testCase);
				 NodeList testCaseChild = eElement.getChildNodes();
				
				for(int i=0;i<testCaseChild.getLength();i++){
					
					System.out.println("The Child Node Name  :"+testCaseChild.item(i).getNodeName());
					System.out.println("The Child Nodes Value: "+testCaseChild.item(i).getTextContent());
					String child = testCaseChild.item(i).getNodeName();
					if (testCaseChild.item(i).getNodeType() == Node.ELEMENT_NODE) {
						Element childEle = outputDoc.createElement(child);
						childEle.setNodeValue(testCaseChild.item(i).getNodeValue());
						testCase.appendChild(childEle);
					}
								
					
				 }
				 
			 }
			 
		  }
		  TransformerFactory transformerFactory = TransformerFactory.newInstance();
		  Transformer transformer = transformerFactory.newTransformer();
		  transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		  transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		  transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		  DOMSource source = new DOMSource(outputDoc);
		  StreamResult result = new StreamResult(outputFile);
		  transformer.transform(source, result);
		   
			 }catch(DOMException dome){
				  System.out.println("There is an Excetion occured while processing XML:"+ dome);
				  dome.printStackTrace();
				}catch(Exception e){
			  System.out.println("There is an Excetion occured while processing XML:"+e);
			  e.printStackTrace();
		}
		
		
	}
	
	public static void main(String s[]){
		
		RQMDevProcess RQM = new RQMDevProcess();
		RQM.processXMLinput();
		
	}

}
