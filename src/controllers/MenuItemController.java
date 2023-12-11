package controllers;

import java.util.ArrayList;
import java.util.Optional;

import models.MenuItem;
import repositories.MenuItemRepository;
import values.strings.ValidationState;

public class MenuItemController {

    private MenuItemRepository menuItemRepo;
    private ValidationController validator;

    public MenuItemController(){
        this.menuItemRepo = new MenuItemRepository();
        this.validator = new ValidationController();
    }

    public Optional<MenuItem> getMenuItemByName(String _menuItemName) {
        return menuItemRepo.getMenuItemByName(_menuItemName);
    }

    public Optional<MenuItem> getById (Integer _id) {
        return menuItemRepo.getById(_id);

    }

    public ArrayList<MenuItem> getAll () {
        return menuItemRepo.getAll();

    }

    public MenuItemRepository.ValidateReturnDatatype post (String menuItemName , String menuItemPrice , String menuItemDescription) {

        MenuItemRepository.ValidateReturnDatatype returnObject = new MenuItemRepository.ValidateReturnDatatype();
        
        if ( validator.isBlank(menuItemName)) {
            returnObject.setState(ValidationState.EMPTY_MENUITEM_NAME);

            return returnObject;
        }

        if(menuItemRepo.getMenuItemByName(menuItemName) != null) {
            returnObject.setState(ValidationState.DUPLICATE_MENUITEM_NAME);

            return returnObject;
        }

        if(validator.isBlank(menuItemPrice)) {
            returnObject.setState(ValidationState.EMPTY_MENUITEM_PRICE);

            return returnObject;  
        }

        if(validator.isBlank(menuItemDescription)) {
            returnObject.setState(ValidationState.EMPTY_MENUITEM_DESCRIPTION);

            return returnObject;   
        }

        if ( validator.longerThanNCharacters(menuItemDescription, 10) ) {
                returnObject.setState(ValidationState.INVALID_MENUITEM_DESCRIPTION_LENGTH);

                return returnObject;
        }

        
        if ( validator.lessThanNNumber(Double.parseDouble(menuItemPrice) , 2.5d) ) {
            returnObject.setState(ValidationState.INVALID_MENUITEM_PRICE_RANGE);

            return returnObject;
        }

        MenuItem menuItem = new MenuItem();
        menuItem.setMenuItemName(menuItemName);
        menuItem.setMenuItemPrice(Integer.parseInt(menuItemPrice));
        menuItem.setMenuItemDescription(menuItemDescription);

        menuItemRepo.post(menuItem);

        return null;
    }

    public MenuItemRepository.ValidateReturnDatatype put (Integer menuItemId , String menuItemName , String menuItemPrice , String menuItemDescription) {
        MenuItemRepository.ValidateReturnDatatype returnObject = new MenuItemRepository.ValidateReturnDatatype();
        
        if ( validator.isBlank(menuItemName)) {
            returnObject.setState(ValidationState.EMPTY_MENUITEM_NAME);

            return returnObject;
        }

        if(menuItemRepo.getMenuItemByName(menuItemName) != null) {
            returnObject.setState(ValidationState.DUPLICATE_MENUITEM_NAME);

            return returnObject;
        }

        if(validator.isBlank(menuItemPrice)) {
            returnObject.setState(ValidationState.EMPTY_MENUITEM_PRICE);

            return returnObject;  
        }

        if(validator.isBlank(menuItemDescription)) {
            returnObject.setState(ValidationState.EMPTY_MENUITEM_DESCRIPTION);

            return returnObject;   
        }

        if ( validator.longerThanNCharacters(menuItemDescription, 10) ) {
                returnObject.setState(ValidationState.INVALID_MENUITEM_DESCRIPTION_LENGTH);

                return returnObject;
        }

        
        if ( validator.lessThanNNumber(Double.parseDouble(menuItemPrice) , 2.5d) ) {
            returnObject.setState(ValidationState.INVALID_MENUITEM_PRICE_RANGE);

            return returnObject;
        }

        Optional<MenuItem> menuItem = menuItemRepo.getById(menuItemId);
        menuItem.get().setMenuItemName(menuItemName);
        menuItem.get().setMenuItemPrice(Integer.parseInt(menuItemPrice));
        menuItem.get().setMenuItemDescription(menuItemDescription);

        menuItemRepo.put(menuItem.get());

        return null;
    }

    public Boolean delete (Integer _idOfAnObjectToBeDeleted) {
        return menuItemRepo.delete(_idOfAnObjectToBeDeleted);

    }    
}
