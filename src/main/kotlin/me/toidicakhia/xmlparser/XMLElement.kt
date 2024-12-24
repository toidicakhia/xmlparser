package me.toidicakhia.xmlparser

import org.w3c.dom.Element
import org.w3c.dom.Text
import java.io.StringWriter
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

class XMLElement(val element: Element) {
	fun getConfig(key: String) = getConfigList(key).firstOrNull()

	fun getConfigList(key: String): List<XMLElement> {
		val children = element.childNodes
		val configs = mutableListOf<XMLElement>()

		for (i in 0..children.length) {
			val node = children.item(i) ?: continue

			if (node is Element && node.tagName.equals(key, true) && node.parentNode == element)
				configs.add(XMLElement(node))
		}

		return configs
	}

	fun getStringList(key: String): List<String> {
		val configs = getConfigList(key)
		val strings = mutableListOf<String>()

		for (config in configs) {
			val children = config.element.childNodes
			for (i in 0..children.length) {
				val node = children.item(i) ?: continue

				if (node is Text) {
					strings.add(node.data)
					break
				}
			}
		}

		return strings
	}

	fun getString(key: String) = getStringList(key).firstOrNull() ?: ""

	fun addPropertyString(key: String, value: String): XMLElement {
		val document = element.ownerDocument
		val child = document.createElement(key)
		child.textContent = value
		element.appendChild(child)

		return XMLElement(child)
	}

	fun removePropertyWithValue(key: String, value: String) {
		val children = element.childNodes

	    for (i in 0 until children.length) {
	        val node = children.item(i)
	        if (node is Element && node.parentNode == element && 
	        	node.tagName.equals(key, true) && node.textContent == value) {
	            element.removeChild(node)
	            return
	        }
	    }
	}

	fun editProperty(key: String, value: String, newValue: String) {
		removePropertyWithValue(key, value)
		addPropertyString(key, newValue)
	}

	fun removeAllProperties(key: String) {
		val children = element.childNodes

	    for (i in 0 until children.length) {
	        val node = children.item(i)
	        if (node is Element && node.parentNode == element && node.tagName.equals(key, true))
	            element.removeChild(node)
	    }
	}

	fun toXML(): String {
	    val transformer = TransformerFactory.newInstance().newTransformer()
	    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes")
        transformer.setOutputProperty(OutputKeys.INDENT, "yes")
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4")

	    val source = DOMSource(element)
	    val result = StringWriter()
	    transformer.transform(source, StreamResult(result))

	    return result.toString().trim()
	}
}