package views.components.interfaces;

public interface CustomizableSpacing <T> {

    public T withNoSpacing ();
    public T withTightSpacing ();
    public T withNormalSpacing ();
    public T withLooseSpacing ();
    public T withVeryLooseSpacing ();
    public T withCustomSpacing (Integer _spacing);

}
