package commands;

import javafx.scene.layout.Pane;

public class Command {
    protected Pane contextReference;

    public Command (Pane _context) {
        this.contextReference = _context;
    }
}
