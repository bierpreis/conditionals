package kb_creator.model.pairs;

class CandidateGroup {
    private long firstNumber, lastNumber;

    public CandidateGroup(long firstNumber) {
        this.firstNumber = firstNumber;
    }


    public void setLastNumber(long lastNumber) {
        this.lastNumber = lastNumber;
    }

    @Override
    public String toString() {
        return firstNumber + "-" + lastNumber;
    }
}
