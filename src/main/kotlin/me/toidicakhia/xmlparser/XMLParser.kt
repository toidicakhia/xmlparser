package me.toidicakhia.xmlparser

import javax.xml.parsers.DocumentBuilderFactory
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

object XMLParser {
    @JvmStatic
    fun parse(text: String) = parse(text.byteInputStream())

    @JvmStatic
    fun parse(inputStream: InputStream): XMLElement {
        val docFactory = DocumentBuilderFactory.newInstance()
        val docBuilder = docFactory.newDocumentBuilder()
        val doc = docBuilder.parse(inputStream)

        return XMLElement(doc.documentElement)
    }

    @JvmStatic
    fun parse(file: File) = parse(FileInputStream(file))
}
