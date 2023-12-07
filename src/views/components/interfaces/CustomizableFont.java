package views.components.interfaces;

public interface CustomizableFont <T> {

    /**
     * Sets the font to be {@code Cabinet Grotesk} (versatile for any kind of usage)
     */
    public T withDefaultFont ();
    /**
     * Sets the font to be {@code Clash Display} (wider, usually used for display)
     */
    public T withAlternateFont ();

    /**
     * Sets the font to be in their <b><b>thickest</b></b> variant
     */
    public T withBlackFont();
    /**
     * Sets the font to be <b>thicker than bold</b> variant
     */
    public T withExtraBoldFont();
    /**
     * Sets the font to the <b>bold</b> variant
     */
    public T withBoldFont();
    /**
     * Sets the font to be slightly thinner than the bold variant
     */
    public T withSemiboldFont();
    /**
     * Sets the font to be normal thickness
     */
    public T withRegularFont();
    /**
     * Sets the font to be as thin as it can manage
     */
    public T withLightFont();

}
