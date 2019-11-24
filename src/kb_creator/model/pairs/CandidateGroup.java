package kb_creator.model.pairs;

class CandidateGroup {
    private int firstNumber, lastNumber;

    public CandidateGroup(int firstNumber) {
        this.firstNumber = firstNumber;
    }


    public void setLastNumber(int lastNumber) {
        this.lastNumber = lastNumber;
    }

    @Override
    public String toString() {
        return firstNumber + "-" + lastNumber;
    }
}
