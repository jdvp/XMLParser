import java.util.ArrayList;
import java.util.LinkedList;

/**
 * XML File object that stores all of the data for one XML file
 *
 * @author JD Porterfield
 */
public class XMLFile {

    /**
     * List of root level elements in this xml file
     */
    private ArrayList<XMLElement> elements = new ArrayList<>();

    /**
     * Constructor that does nothing because the XMLFile object is created and then added to as the parser goes along
     */
    public XMLFile(){}

    /**
     * Adds a new top level element to this file
     * @param node The element to add
     */
    public void addElement(XMLElement node){
        elements.add(node);
    }

    /**
     * Returns the top level elements in this file (which is essentially the whole file)
     * @return The list of elements
     */
    public ArrayList<XMLElement> getElements(){
        return elements;
    }

    /**
     * Returns a list of the XMLElements that have the specified tag name
     * @param name the tag to search for
     * @return a list of elements with the matching tag
     */
    public ArrayList<XMLElement> getElementsByTag(String name){
        ArrayList<XMLElement> matchingElements =  new ArrayList<>();
        LinkedList<XMLElement> notVisited = new LinkedList<>();
        elements.forEach(notVisited::offer);

        while(notVisited.size() != 0){
            XMLElement e = notVisited.poll();
            if(e.getType().equals(name))
                matchingElements.add(e);
            e.getChildren().forEach(notVisited::offer);
        }
        return matchingElements;
    }

    /**
     * Returns the String representation of this file.
     * (I.e. basically the file as it was input)
     * @return a String representing this file
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(XMLElement n : elements)
            sb.append(n.toString());
        return sb.toString();
    }
}
