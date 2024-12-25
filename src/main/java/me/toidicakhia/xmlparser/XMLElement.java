package me.toidicakhia.xmlparser;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XMLElement {
    private final Element element;

    public XMLElement(Element element) {
        this.element = element;
    }

    public XMLElement getConfig(String key) {
        List<XMLElement> configs = getConfigList(key);
        return configs.isEmpty() ? null : configs.get(0);
    }

    public List<XMLElement> getConfigList(String key) {
        List<XMLElement> configs = new ArrayList<>();
        Node children = element.getFirstChild();

        while (children != null) {
            if (children instanceof Element) {
                Element childElement = (Element) children;
                if (childElement.getTagName().equalsIgnoreCase(key) && childElement.getParentNode() == element) {
                    configs.add(new XMLElement(childElement));
                }
            }
            children = children.getNextSibling();
        }
        return configs;
    }

    public List<String> getStringList(String key) {
        List<XMLElement> configs = getConfigList(key);
        List<String> strings = new ArrayList<>();

        for (XMLElement config : configs) {
            Node child = config.element.getFirstChild();
            while (child != null) {
                if (child instanceof Text) {
                    strings.add(child.getTextContent());
                    break;
                }
                child = child.getNextSibling();
            }
        }
        return strings;
    }

    public String getString(String key) {
        List<String> strings = getStringList(key);
        return strings.isEmpty() ? "" : strings.get(0);
    }

    public XMLElement addPropertyString(String key, String value) {
        Element child = element.getOwnerDocument().createElement(key);
        child.setTextContent(value);
        element.appendChild(child);
        return new XMLElement(child);
    }

    public void removePropertyWithValue(String key, String value) {
        Node child = element.getFirstChild();

        while (child != null) {
            if (child instanceof Element) {
                Element childElement = (Element) child;
                if (childElement.getTagName().equalsIgnoreCase(key) &&
                    childElement.getParentNode() == element &&
                    childElement.getTextContent().equals(value)) {
                    element.removeChild(child);
                    return;
                }
            }
            child = child.getNextSibling();
        }
    }

    public void editProperty(String key, String value, String newValue) {
        removePropertyWithValue(key, value);
        addPropertyString(key, newValue);
    }

    public void removeAllProperties(String key) {
        Node child = element.getFirstChild();

        while (child != null) {
            Node next = child.getNextSibling();
            if (child instanceof Element) {
                Element childElement = (Element) child;
                if (childElement.getTagName().equalsIgnoreCase(key) &&
                    childElement.getParentNode() == element) {
                    element.removeChild(child);
                }
            }
            child = next;
        }
    }

    public String toXML() {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(element), new StreamResult(writer));
            return writer.toString().trim();
        } catch (Exception e) {
            throw new RuntimeException("Error converting XML to String", e);
        }
    }
}
