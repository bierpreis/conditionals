package nfc.view.textArea;

public class ViewOptions {
    private boolean showNumbers;
    private boolean lettersView;
    private boolean showDots;
    private boolean twoLetters;

    public void setShowNumbers(boolean showNumbers) {
        this.showNumbers = showNumbers;
    }

    public void setTwoLetters(boolean twoLetters) {
        this.twoLetters = twoLetters;
    }

    public void setLettersView(boolean lettersView) {
        this.lettersView = lettersView;
    }

    public void setShowDots(boolean showDots) {
        this.showDots = showDots;
    }
    
    public boolean showDots() {
        return showDots;
    }

    public boolean showNumbers() {
        return showNumbers;
    }

    public boolean isLettersViewActive() {
        return lettersView;
    }

    public boolean isTwoLetters() {
        return twoLetters;
    }

}
