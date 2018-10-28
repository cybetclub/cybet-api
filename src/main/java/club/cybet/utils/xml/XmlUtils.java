package club.cybet.utils.xml;

import club.cybet.utils.Constants;
import org.fusesource.jansi.Ansi;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.util.Objects;

import static org.fusesource.jansi.Ansi.ansi;

public class XmlUtils {

    private static Document parseXML(){
        Document doc = null;
        try{
            File inputFile = new File(Constants.LOCAL_CONF_FILENAME);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
        }catch(Exception e){
//            e.printStackTrace();
            System.out.println(ansi().fg(Ansi.Color.RED).bold()
                    .a("ERROR: Conf file not found ("+Constants.LOCAL_CONF_FILENAME+")").reset());
            System.out.println(ansi().fg(Ansi.Color.YELLOW).bold().a("INFO: Terminating server"));
            System.exit(0);
        }
        return doc;
    }

    public static String readXMLTag(String tagPath){

        Document XML = parseXML();

        XPathExpression xp;
        try {
            xp = XPathFactory.newInstance().newXPath().compile(tagPath);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
            return Constants.SKY_DELIMITER;
        }

        try {
            return Objects.requireNonNull(xp).evaluate(XML).trim();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
            return Constants.SKY_DELIMITER;
        }
    }

    public static boolean updateXMLTag(String tagPath, String tagTextContent){
        Document XML = parseXML();

        try {
            NodeList nodeList = (NodeList) XPathFactory.newInstance().newXPath().compile(tagPath)
                    .evaluate(XML, XPathConstants.NODESET);

            nodeList.item(0).setTextContent(tagTextContent);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(XML);
            StreamResult streamResult =  new StreamResult(new File(Constants.LOCAL_CONF_FILENAME));
            transformer.transform(source, streamResult);

            return true;

        } catch (XPathExpressionException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static String readXMLTag(Document xmlDoc, String tagPath){

        XPathExpression xp;
        try {
            xp = XPathFactory.newInstance().newXPath().compile(tagPath);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
            return Constants.SKY_DELIMITER;
        }

        try {
            return Objects.requireNonNull(xp).evaluate(xmlDoc);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
            return Constants.SKY_DELIMITER;
        }
    }

    public static Document updateXMLTag(Document xmlDoc, String tagPath, String tagTextContent){

        try {
            NodeList nodeList = (NodeList) XPathFactory.newInstance().newXPath().compile(tagPath)
                    .evaluate(xmlDoc, XPathConstants.NODESET);

            nodeList.item(0).setTextContent(tagTextContent);

            return xmlDoc;

        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return xmlDoc;
    }

}