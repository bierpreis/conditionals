package nfc.view.textArea;

public class ViewOptions {
    private boolean showNumbers;
    private boolean showLetters;
    private boolean showDots;

    public void setShowNumbers(boolean showNumbers) {
        this.showNumbers = showNumbers;
    }

    public void setShowLetters(boolean showLetters) {
        this.showLetters = showLetters;
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

    public boolean showLetters() {
        return showLetters;
    }

}
