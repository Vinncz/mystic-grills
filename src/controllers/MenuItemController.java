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

        if ( validator.isBlank(menuItemName) ) {
            returnObject.setState(ValidationState.EMPTY_MENUITEM_NAME);

            return returnObject;
        }

        if( menuItemRepo.getMenuItemByName(menuItemName).isPresent() ) {
            returnObject.setState(ValidationState.DUPLICATE_MENUITEM_NAME);

            return returnObject;
        }

        if( validator.isBlank(menuItemPrice) ) {
            returnObject.setState(ValidationState.EMPTY_MENUITEM_PRICE);

            return returnObject;
        }

        if(validator.isBlank(menuItemDescription)) {
            returnObject.setState(ValidationState.EMPTY_MENUITEM_DESCRIPTION);

            return returnObject;
        }

        if ( validator.lessThanNCharacters(menuItemDescription, 11) ) {
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

        menuItem = menuItemRepo.post(menuItem);
        returnObject.setAssociatedMenuItem(menuItem);

        return returnObject;
    }

    public MenuItemRepository.ValidateReturnDatatype put (Integer _menuItemId , String _menuItemName , String _menuItemPrice , String _menuItemDescription) {
        MenuItemRepository.ValidateReturnDatatype returnObject = new MenuItemRepository.ValidateReturnDatatype();

        if ( validator.isBlank(_menuItemName)) {
            returnObject.setState(ValidationState.EMPTY_MENUITEM_NAME);

            return returnObject;
        }

        Optional<MenuItem> objectWithTheSameName = menuItemRepo.getMenuItemByName(_menuItemName);
        if ( objectWithTheSameName.isPresent() && objectWithTheSameName.get().getMenuItemId() != _menuItemId ) {
            returnObject.setState(ValidationState.DUPLICATE_MENUITEM_NAME);

            return returnObject;
        }

        if(validator.isBlank(_menuItemPrice)) {
            returnObject.setState(ValidationState.EMPTY_MENUITEM_PRICE);

            return returnObject;
        }

        if(validator.isBlank(_menuItemDescription)) {
            returnObject.setState(ValidationState.EMPTY_MENUITEM_DESCRIPTION);

            return returnObject;
        }

        if ( validator.lessThanNCharacters(_menuItemDescription, 10) ) {
            returnObject.setState(ValidationState.INVALID_MENUITEM_DESCRIPTION_LENGTH);

            return returnObject;
        }


        if ( validator.lessThanNNumber(Double.parseDouble(_menuItemPrice) , 2.5d) ) {
            returnObject.setState(ValidationState.INVALID_MENUITEM_PRICE_RANGE);

            return returnObject;
        }

        Optional<MenuItem> menuItem = menuItemRepo.getById(_menuItemId);
        if ( menuItem.isPresent() ) {
            MenuItem mi = menuItem.get();

            mi.setMenuItemName(_menuItemName);
            mi.setMenuItemPrice(Integer.parseInt(_menuItemPrice));
            mi.setMenuItemDescription(_menuItemDescription);

            menuItemRepo.put(mi);
            returnObject.setAssociatedMenuItem(mi);
        }

        return returnObject;
    }

    public Boolean delete (Integer _idOfAnObjectToBeDeleted) {
        return menuItemRepo.delete(_idOfAnObjectToBeDeleted);

    }
}
