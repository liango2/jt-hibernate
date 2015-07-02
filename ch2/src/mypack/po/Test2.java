package mypack.po;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

/**
 * 测试java自带方法读取xml文件
 * <p/>
 * Created by liango on 2015-07-02.
 */
public class Test2 {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
//        method1_pretty();

        method_ugly();

    }

    private static void method_ugly() throws ParserConfigurationException, SAXException, IOException {
        InputStream resourceAsStream = Test2.class.getResourceAsStream("Customer.hbm.xml");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        db.setEntityResolver(new EntityResolver() {
            public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {

                System.out.println("publicId = " + publicId + ", systemId =" + systemId);

                if (systemId.contains("hibernate-mapping-3.0.dtd")) {
                    ///  return new InputSource(new ByteArrayInputStream("<?xml version='1.0' encoding='UTF-8'?>".getBytes())); // 返回空文档
                    ///  new InputSource（Test2.class.getResourceAsStream("/my/package/doc.dtd")); // 指定本地的dtd文件路径
                    return new InputSource(new StringReader(""));  // 返回空文档
                } else {
                    return null;
                }
            }
        });

        Document doc = db.parse(resourceAsStream);
        System.out.println("doc = " + doc.toString());
        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
        if (doc.hasChildNodes()) {
            printNote(doc.getChildNodes());
        }
    }

    /**
     * 做法一： 禁用自动检测
     *
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    private static void method1_pretty() throws ParserConfigurationException, SAXException, IOException {
        InputStream resourceAsStream = Test2.class.getResourceAsStream("Customer.hbm.xml");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

//        dbFactory.setValidating(false);
//        dbFactory.setNamespaceAware(true);
//        dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
//        dbFactory.setFeature("http://xml.org/sax/features/validation", false);
//        dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
        dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(resourceAsStream);
        System.out.println("doc = " + doc.toString());
        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
        if (doc.hasChildNodes()) {
            printNote(doc.getChildNodes());
        }
    }

    private static void printNote(NodeList nodeList) {

        for (int count = 0; count < nodeList.getLength(); count++) {

            Node tempNode = nodeList.item(count);

            // make sure it's element node.
            if (tempNode.getNodeType() == Node.ELEMENT_NODE) {

                // get node name and value
                System.out.println("\nNode Name =" + tempNode.getNodeName() + " 开始：");
                System.out.println("Node Value =" + tempNode.getTextContent());

                if (tempNode.hasAttributes()) {

                    // get attributes names and values
                    NamedNodeMap nodeMap = tempNode.getAttributes();

                    for (int i = 0; i < nodeMap.getLength(); i++) {

                        Node node = nodeMap.item(i);
                        System.out.println("attr name : " + node.getNodeName());
                        System.out.println("attr value : " + node.getNodeValue());

                    }

                }

                if (tempNode.hasChildNodes()) {

                    // loop again if has child nodes
                    printNote(tempNode.getChildNodes());

                }

                System.out.println("Node Name =" + tempNode.getNodeName() + " 结束:~");

            }

        }
    }


}
