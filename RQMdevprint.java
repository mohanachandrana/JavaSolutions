
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
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
public class RQMdevprint {
  public static void main(String argv[]) {
   
    try {
//input file---------------------------------
 File fXmlFile = new File("C:/work/Inputtestcase.xml");
 DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
 dbFactory.setNamespaceAware(true);
 DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
 Document doc = dBuilder.parse(fXmlFile);
 doc.getDocumentElement().normalize();
 doc.setXmlVersion("1.0");
 doc.setXmlStandalone(true);

   
 NodeList nList = doc.getElementsByTagName("testcase");
 NodeList stList = doc.getElementsByTagName("step");
//these lines are not printing in the output txt file................................................
 System.out.println("xmlns=http://jazz.net/xmlns/alm/qm/v0.1/");
 System.out.println("xmlns:jzalm=http://jazz.net/xmlns/alm/v0.1/");
 System.out.println("xmlns:alm=http://jazz.net/xmlns/alm/v0.1/");
 System.out.println("xmlns:dc=http://purl.org/dc/elements/1.1/");
 System.out.println("xmlns:myns=http://mydomain.net/some/custom/namespace/v0.1/");
 System.out.println("xmlns:ts=http://jazz.net/xmlns/alm/qm/v0.1/testscript/v0.1/");
 for (int temp = 0; temp < nList.getLength(); temp++)
  {
  Node nNode = nList.item(temp);
    
  if (nNode.getNodeType() == Node.ELEMENT_NODE) 
  {
   Element eElement = (Element) nNode;
   //eElement.removeAttributeNS("http://purl.org/dc/elements/1.1/", "xmlns:dc");
   eElement.setAttributeNS("http://www.w3.org/2000/xmlns/","xmlns:dc","http://www.w3.org/2000/xmlns/");
   eElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:jzalm", "http://www.w3.org/2000/xmlns/");
   
   
   System.out.println("internalid : " + eElement.getAttribute("internalid"));
   System.out.println("name : " + eElement.getAttribute("name"));
   System.out.println("node_order : " + eElement.getElementsByTagName("node_order").item(0).getTextContent());
   System.out.println("externalid : " + eElement.getElementsByTagName("externalid").item(0).getTextContent());
   System.out.println("version : " + eElement.getElementsByTagName("version").item(0).getTextContent());
   System.out.println("summary : " + eElement.getElementsByTagName("summary").item(0).getTextContent());
   System.out.println("preconditions : " + eElement.getElementsByTagName("preconditions").item(0).getTextContent());
   System.out.println("execution_type : " + eElement.getElementsByTagName("execution_type").item(0).getTextContent());
   System.out.println("importance : " + eElement.getElementsByTagName("importance").item(0).getTextContent());
   System.out.println("estimated_exec_duration : " + eElement.getElementsByTagName("estimated_exec_duration").item(0).getTextContent());
   System.out.println("status : " + eElement.getElementsByTagName("status").item(0).getTextContent());
    }
  }
  for (int temp = 0; temp < stList.getLength(); temp++) {
  Node nNode = stList.item(temp);
    
  System.out.println("\nCurrent Element :" + nNode.getNodeName());
    
  if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	  
   Element eElement = (Element) nNode;
   System.out.println("step_number : " + eElement.getElementsByTagName("step_number").item(0).getTextContent());
   System.out.println("actions : " + eElement.getElementsByTagName("actions").item(0).getTextContent());
   System.out.println("expectedresults : " + eElement.getElementsByTagName("expectedresults").item(0).getTextContent());
   System.out.println("execution_type : " + eElement.getElementsByTagName("execution_type").item(0).getTextContent());
     }
// Added below lines to put the output in xml file
  TransformerFactory transformerFactory = TransformerFactory.newInstance();
  Transformer transformer = transformerFactory.newTransformer();
  transformer.setOutputProperty(OutputKeys.INDENT, "yes");
  transformer.setOutputProperty(OutputKeys.METHOD, "xml");
  transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
  DOMSource source = new DOMSource(doc);
//output txt file............................
  StreamResult result = new StreamResult(new File("C:\\work\\RTCTemp.xml"));
  transformer.transform(source, result);
  System.out.println("File saved!");
 }
    } catch (Exception e) {
 e.printStackTrace();
    }
  }
}


