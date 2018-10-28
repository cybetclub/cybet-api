package club.cybet.utils.xml;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Objects;

/**
 * cybet-api (club.cybet.utils.xml)
 * Created by: cybet
 * On: 14 Sep, 2018 9/14/18 6:31 PM
 **/
public class XmlObject {

    private String xmlString;
    private String oldXmlString;
    private Document xmlDoc;
    private Document oldXmlDoc;

    public XmlObject(String xmlString){
        this.xmlString = xmlString;
        this.xmlDoc = parseXml();
    }

    public void reconstruct(String xmlString){
        this.oldXmlString = this.xmlString;
        this.xmlString = xmlString;
        oldXmlDoc = xmlDoc;
        xmlDoc = parseXml();
    }

    public String getXmlString() {
        return xmlString;
    }

    public void setXmlString(String xmlString) {
        this.xmlString = xmlString;
    }

    public Document getXmlDoc() {
        return xmlDoc;
    }

    public void setXmlDoc(Document xmlDoc) {
        this.xmlDoc = xmlDoc;
    }

    public String getOldXmlString() {
        return oldXmlString;
    }

    public void setOldXmlString(String oldXmlString) {
        this.oldXmlString = oldXmlString;
    }

    public Document getOldXmlDoc() {
        return oldXmlDoc;
    }

    public void setOldXmlDoc(Document oldXmlDoc) {
        this.oldXmlDoc = oldXmlDoc;
    }

    public boolean editXmlTagValue(String tagPath, String value){
        if(xmlDoc != null){
            xmlDoc = XmlUtils.updateXMLTag(xmlDoc, tagPath, value);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer;
            try {
                transformer = tf.newTransformer();
                transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
                StringWriter writer = new StringWriter();
                transformer.transform(new DOMSource(xmlDoc), new StreamResult(writer));
                this.xmlString = writer.getBuffer().toString()
                        .replaceAll("[\n\r]", "");
                return true;
            } catch (TransformerException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public String getTagValue(String tagPath){
        if(xmlDoc != null){
            try{
                return XmlUtils.readXMLTag(xmlDoc, tagPath);
            }catch (Exception e){
                e.printStackTrace();
                System.err.println(e.toString());
                return "";
            }
        }
        return "";
    }

    public String getXmlContentAsString(String tagPath){
        NodeList nodeList = null;

        try {
            nodeList = (NodeList) XPathFactory.newInstance().newXPath().compile(tagPath)
                    .evaluate(xmlDoc, XPathConstants.NODESET);

        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        Transformer transformer;
        try {
            transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            StreamResult result = new StreamResult(new StringWriter());
            DOMSource source = new DOMSource(Objects.requireNonNull(nodeList).item(0));
            transformer.transform(source, result);

            return result.getWriter().toString();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        return "";
    }

    public String getInnerXmlContentAsString(String tagPath, String parentTag){
        String xmlContentAsString = getXmlContentAsString(tagPath);
        xmlContentAsString = xmlContentAsString.replaceAll(
                "<\\?xml version=\"1.0\" encoding=\"UTF-8\"\\?>", "");
        xmlContentAsString = xmlContentAsString.replaceAll("<"+parentTag+">", "");
        return xmlContentAsString.replaceAll("</"+parentTag+">", "").trim();
    }

    private Document parseXml(){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            return builder.parse(new InputSource(new StringReader(xmlString)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
