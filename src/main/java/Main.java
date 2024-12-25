import me.toidicakhia.xmlparser.XMLParser;
import me.toidicakhia.xmlparser.XMLElement;

public class Main {
  private static final String text =
    "<metadata>\n" +
    "  <groupId>me.toidicakhia</groupId>\n" +
    "  <artifactId>viastar-java8</artifactId>\n" +
    "  <versioning>\n" +
    "    <latest>813cf81</latest>\n" +
    "    <release>813cf81</release>\n" +
    "    <versions>\n" +
    "      <version>813cf81</version>\n" +
    "      <version>22222</version>\n" +
    "    </versions>\n" +
    "    <lastUpdated>20241208115938</lastUpdated>\n" +
    "  </versioning>\n" +
    "</metadata>";
        
  public static void main(String[] args) {
    try {
        XMLElement xml = XMLParser.parse(text);
        XMLElement versioning = xml.getConfig("versioning");
        if (versioning == null) return;

        XMLElement versions = versioning.getConfig("versions");
        if (versions == null) return;

        versions.addPropertyString("version", "11111111");
        versions.removePropertyWithValue("version", "22222");
        versioning.setAttribute("abc", "123");
        versioning.setAttribute("jlasdjlas", "111");

        System.out.println(xml.toXML());
    } catch (Exception e) {
        e.printStackTrace();
    }
  }
}
