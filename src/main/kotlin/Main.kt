import me.toidicakhia.xmlparser.XMLParser

const val text = """
<metadata>
  <groupId>me.toidicakhia</groupId>
  <artifactId>viastar-java8</artifactId>
  <versioning>
    <latest>813cf81</latest>
    <release>813cf81</release>
    <versions>
      <version>813cf81</version>
      <version>22222</version>
    </versions>
    <lastUpdated>20241208115938</lastUpdated>
  </versioning>
</metadata>
"""

fun main() {
  val xml = XMLParser.parse(text)
  val versioning = xml.getConfig("versioning") ?: return
  val versions = versioning.getConfig("versions") ?: return
  versions.addPropertyString("version", "11111111")
  versions.removePropertyWithValue("version", "22222")
  println(xml.toXML())
}