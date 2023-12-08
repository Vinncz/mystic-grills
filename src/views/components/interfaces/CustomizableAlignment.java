package views.components.interfaces;

public interface CustomizableAlignment <T> {

    /**
     * <pre>
     * content starts here:
     *- ○ -
     *- - -
     *- - -
     * </pre>
     */
    public T centerContentHorizontally ();

    /**
     * <pre>
     * content starts here:
     *- - -
     *○ - -
     *- - -
     * </pre>
     */
    public T centerContentVertically ();

    /**
     * <pre>
     * content starts here:
     *- - -
     *- ○ -
     *- - -
     * </pre>
     */
    public T centerContentBothAxis ();

}
