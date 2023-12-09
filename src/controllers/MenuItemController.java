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

    public MenuItemRepository.ValidateReturnDatatype post (MenuItem _objectToBeInserted) {

        MenuItemRepository.ValidateReturnDatatype returnObject = new MenuItemRepository.ValidateReturnDatatype();
        
        if ( validator.isBlank(_objectToBeInserted.getMenuItemName())) {
            returnObject.setState(ValidationState.EMPTY_MENUITEM_NAME);

            return returnObject;
        }

        if ( validator.longerThanNCharacters(_objectToBeInserted.getMenuItemDescription(), 10) ) {
            returnObject.setState(ValidationState.INVALID_MENUITEM_DESCRIPTION_LENGTH);

            return returnObject;
        }

        if ( validator.longerThanOrEqualsNNumber((double) _objectToBeInserted.getMenuItemPrice(), 2.5d) ) {
            returnObject.setState(ValidationState.INVALID_MENUITEM_PRICE_RANGE);

            return returnObject;
        }

        menuItemRepo.post(_objectToBeInserted);
        
        return null;
    }

    public Boolean put (MenuItem _replacementObject) {
        return menuItemRepo.put(_replacementObject);

    }

    public Boolean delete (Integer _idOfAnObjectToBeDeleted) {
        return menuItemRepo.delete(_idOfAnObjectToBeDeleted);

    }    
}
