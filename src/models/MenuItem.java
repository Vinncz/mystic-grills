package models;

public class MenuItem {

    private Integer menuItemId;
    private String menuItemName;
    private String menuItemDescription;
    private Integer menuItemPrice;

    public void setMenuItemId(Integer _id){
        this.menuItemId = _id;
    }

    public Integer getMenuItemId(){
        return this.menuItemId;
    }

    public void setMenuItemName(String _menuItemName){
        this.menuItemName = _menuItemName;
    }

    public String getMenuItemName(){
        return this.menuItemName;
    }

    public void setMenuItemDescription(String _menuItemDescription){
        this.menuItemDescription = _menuItemDescription;
    }

    public String getMenuItemDescription(){
        return this.menuItemDescription;
    }

    public void setMenuItemPrice(Integer _menuItemPrice){
        this.menuItemPrice = _menuItemPrice;
    }

    public Integer getMenuItemPrice(){
        return this.menuItemPrice;
    }

}
