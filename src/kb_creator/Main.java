package kb_creator;

import kb_creator.gui.KBMainWindow;

public class Main {

    public static void main(String[] args){
        KBCreatorObserver observer = new KBCreatorObserver();
        new KBMainWindow(observer);
        
    }
}
