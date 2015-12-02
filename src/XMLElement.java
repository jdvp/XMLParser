import java.util.ArrayList;
import java.util.HashMap;

/**
 * A standard XML Element that will store the various attributes and children.
 *
 * @author JD Porterfield
 */
public class XMLElement implements XMLNode{

    /**
     * The type, of this element which is just the tag name
     */
    private String type = "";
    /**
     * The attributes that this element possesses
     */
    private HashMap<String, String> attributes = new HashMap<>();
    /**
     * The children elements that are nested under this element
     */
    private ArrayList<XMLElement> children = new ArrayList<>();
    /**
     * Boolean that describes whether or not this element is a root element in the xml file
     */
    private boolean isTopLevel = false;

    /**
     * Constructor  
     * @param tag the tag name for this element
     * @param topLevel a boolean describing whether this element is a root element in the xml file
     */
    public XMLElement(String tag, boolean topLevel){
        type = tag;
        isTopLevel = topLevel;
    }

    /**
     * Adds an attribute to this element
     *
     * @param field The attribute's name
     * @param value The attribute's value
     */
    public void addAttribute(String field, String value){
        attributes.put(field, value);
    }

    /**
     * Adds a new child to this element
     *
     * @param child The element to add to this element's list of children
     */
    public void addChild(XMLElement child){
        children.add(child);
    }

    /**
     * @return The mapping of this element's attributes
     */
    public HashMap<String, String> getAttributes(){
        return attributes;
    }

    /**
     * Used to get the value of a specified named attribute within this element.
     * Will have to cast if the data is not actually String type (E.g. Long.parseLong(return value) for a date).
     * @param name The name of the attribute
     * @return The value of the attribute if found, null otherwise
     */
    public String getAttributeByName(String name){
        return attributes.get(name);
    }
    /*
     * @return The XML children of this element
     */
    public ArrayList<XMLElement> getChildren(){
        return children;
    }

    /**
     * @return The type of this element
     */
    public String getType(){
        return type;
    }

    /**
     * @return Whether this element is a top level element
     */
    @Override
    public boolean isTopLevelElement(){
        return isTopLevel;
    }

    /**
     * Creates a string representation of this element. Includes indentations so that the output is more easily read by
     * a human.
     * @return this element's string representation
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("<").append(type);
        if(attributes.size() != 0) {
            sb.append(" ");
            if (type.contains("?")) {
                String ver = "version";
                String val = attributes.get(ver);
                attributes.remove(ver);
                sb.append(ver).append("=").append(val).append(" ");
                for (String k : attributes.keySet())
                    sb.append(k).append("=").append(attributes.get(k)).append(" ");
                attributes.put(ver, val);
            } else {
                for (String k : attributes.keySet())
                    sb.append(k).append("=").append(attributes.get(k)).append(" ");
            }
        }
        if(children.size() == 0) {
            sb.append(type.contains("?") ? "?>" : "/>").append("\n");
        }else {
            sb.append(">\n");
            for(XMLElement c : children)
                sb.append(c.toString("\t"));
            sb.append("</").append(type).append(">\n");
        }
        return sb.toString();
    }

    /**
     * A helper for the toString method that inserts the correct number of tabs on children's lines.
     * @param tabs The tabbing of the parent line
     * @return String representation of this element
     */
    private String toString(String tabs){
        StringBuilder sb = new StringBuilder();
        sb.append(tabs).append("<").append(type);
        if(attributes.size() != 0) {
            sb.append(" ");
            for (String k : attributes.keySet())
                sb.append(k).append("=").append(attributes.get(k)).append(" ");
        }
        if(children.size() == 0) {
            sb.append(type.contains("?") ? "?>" : "/>").append("\n");
        }else {
            sb.append(">\n");
            for(XMLElement c : children)
                sb.append(tabs).append(c.toString(tabs));
            sb.append(tabs).append("</").append(type).append(">\n");
        }
        return sb.toString();
    }
}
