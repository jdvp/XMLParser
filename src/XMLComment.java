/**
 * XML comments element used so that comments aren't just thrown out
 */
public class XMLComment implements XMLNode{
    /**
     * The content of the comment
     */
    private String comment;
    /**
     * Whether the comment is a root element in the xml file
     */
    private boolean isTopLevel;

    /**
     * Constructor
     * @param content The content of the new comment
     * @param isTopLevelComment Whether the new comment is a top level element or not
     */
    public XMLComment(String content, boolean isTopLevelComment){
        comment = content;
        isTopLevel = isTopLevelComment;
    }

    /**
     * @return whether this comment is a top level element or not
     */
    @Override
    public boolean isTopLevelElement() {
        return isTopLevel;
    }

    /**
     * @return The content of this comment
     */
    @Override
    public String toString(){
        return comment;
    }
}
