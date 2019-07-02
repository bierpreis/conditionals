package kb_creator.model.conditionals.pairs;

import kb_creator.model.conditionals.knowledge_base.AbstractKnowledgeBase;
import kb_creator.model.conditionals.NewConditional;


import java.util.List;

//this class was created for test issues
//it consumes much more memory then the alternative candidateNumbersArrayPair
//so it is not used and left here for maybe later tests
public class RealCandidatePair extends AbstractPair {
    private List<NewConditional> candidates;

    public RealCandidatePair(AbstractKnowledgeBase knowledgeBase, List<NewConditional> candidates) {
        this.knowledgeBase = knowledgeBase;
        this.candidates = candidates;

    }

    @Override
    public List<NewConditional> getCandidatesList() {
        return candidates;
    }

    //todo
    @Override
    public String toString() {
        return null;
    }

    @Override
    public void deleteCandidates() {
        candidates = null;
    }

    @Override
    public String toFileString() {
        return null;
    }
}
