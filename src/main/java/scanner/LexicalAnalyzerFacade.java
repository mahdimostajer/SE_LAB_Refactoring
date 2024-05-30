package scanner;

import scanner.token.Token;

public class LexicalAnalyzerFacade {

    private lexicalAnalyzer analyzer;
    public LexicalAnalyzerFacade(java.util.Scanner sc) {
        analyzer = new lexicalAnalyzer(sc);
    }

    public Token getNextToken() {
        return analyzer.getNextToken();
    }
}
