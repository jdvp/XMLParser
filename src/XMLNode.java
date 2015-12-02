/**
 * Interface used for XML Elements. Mostly used so that dealing with comments is okay.
 *
 * @author JD Porterfield
 */
public interface XMLNode {
    boolean isTopLevelElement();

    String toString();
}
