package controllers;

public class ValidationController {

    public Boolean lessThanNCharacters (String _s, Integer _n) {
        return _s.length() < _n ? true : false;
    }

    public Boolean lessThanOrEqualsToNCharacters (String _s, Integer _n) {
        return _s.length() <= _n ? true : false;
    }

    public Boolean longerThanNCharacters (String _s, Integer _n) {
        return _s.length() > _n ? true : false;
    }

    public Boolean longerThanOrEqualsToNCharacters (String _s, Integer _n) {
        return _s.length() >= _n ? true : false;
    }

    public Boolean isBlank (String _s) {
        return _s.isBlank();
    }

    public Boolean longerThanOrEqualsNNumber(Double _s, Double _n) {
        return _s != null && _s >= _n ? true : false;
    }
}
