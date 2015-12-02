import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Parser for actually parsing apart XML files
 *
 * @author JD Porterfield
 */
public class XMLParser {

    /**
     * The XMLFile object that will be returned. Can be used for traversing the file or simply printing.
     */
    private XMLFile xmlFile = new XMLFile();
    /**
     * Stack used to keep track of the current node to add new children to
     */
    private Stack<XMLElement> currentNode = new Stack<>();
    /**
     * XML file to parse
     */
    private File file = null;

    /**
     * Constructor which sets the file for this parser to use
     * @param fileIn The XML file to open
     */
    public XMLParser(File fileIn){
        file = fileIn;
    }


    /**
     * The method that will actually be used to parse the XML.
     * Basically goes line-by-line and decides where in the XML hierarchy where the line begins and then parses out the
     * appropriate data.
     */
    private void parse(){
        Scanner fileReader = new Scanner("");
        try {
            fileReader = new Scanner(file);
        }
        catch (FileNotFoundException e) {
            System.err.println("ERROR: FILE NOT FOUND");
            e.printStackTrace();
        }
        while(fileReader.hasNextLine()){
            boolean isTopLevel = currentNode.empty();
            String line = fileReader.nextLine();
            try {
                line = line.trim();
                line = line.replaceAll("\\?>", " ?>");
                line = line.replaceAll("\\\\>", " \\\\>");
                String[] parts = splitSpacesNotBetweenQuotes(line);
                if (line.contains("<!--") && line.endsWith("-->")) {
                    continue;
                    //don't really need comments...?
                }
                if (line.length() > 0 && line.charAt(0) == '<') {
                    if (line.charAt(1) == '/' && line.replaceAll("</", "").replaceAll(">", "").equals(currentNode.peek().getType())) {
                        if (currentNode.peek().isTopLevelElement())
                            xmlFile.addElement(currentNode.peek());
                        currentNode.pop();
                        continue;
                    }
                    XMLElement newNode = new XMLElement(parts[0].replace("<", "").replace(">", ""), isTopLevel);
                    if (!currentNode.empty())
                        currentNode.peek().addChild(newNode);
                    currentNode.push(newNode);
                }
                for (int i = 1; i < parts.length; i++) {
                    parts[i] = parts[i].trim();
                    if (parts[i].equals("/>") || parts[i].equals("?>"))
                        break;
                    if (parts[i].length() == 0)
                        continue;
                    String f = parts[i].split("=")[0];
                    String v = parts[i].split("=")[1];
                    currentNode.peek().addAttribute(f, v);
                }
                if (line.contains("/>") || line.contains("?>")) {
                    if (currentNode.peek().isTopLevelElement())
                        xmlFile.addElement(currentNode.peek());
                    currentNode.pop();
                }
            } catch (Exception e){
                continue;
            }
        }

        //just in case we have malformed xml. In that case, the xml hierarchy produced is not likely to be correct
        while(!currentNode.empty())
            xmlFile.addElement(currentNode.pop());
        fileReader.close();
    }

    /**
     * Creates an XML file by parsing the designated file for this parser and then returns the result
     * @return The parsed XML file
     */
    public XMLFile getXMLFile(){
        xmlFile = new XMLFile();
        parse();
        return xmlFile;
    }

    /**
     * Splits the line by spacing but only if it is not part of the line that is between quotes (doesn't split up values)
     * @param s The string to split
     * @return an array of strings based on the splitting operation
     */
    private String[] splitSpacesNotBetweenQuotes(String s){
        List<String> strings = new ArrayList<>();
        boolean betweenQuotes = false;
        int start = 0;
        int end = 0;
        char quoteType = '\"';
        while(end < s.length() - 1){
            end ++;
            if(s.charAt(end) == '=' && (s.charAt(end + 1) == '\'' || s.charAt(end + 1) == '\"'))
                quoteType = s.charAt(end + 1);
            if(s.charAt(end) == quoteType)
                betweenQuotes = !betweenQuotes;
            if((s.charAt(end) == ' ') && !betweenQuotes){
                strings.add(s.substring(start, end));
                start = end + 1;
                end = start;
            }
        }
        if(strings.size() == 0)
            strings.add(s);
        return strings.toArray(new String[strings.size()]);
    }
}
