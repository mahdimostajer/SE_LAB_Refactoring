## گزارش آزمایش

### استفاده از الگوی Facade برای CodeGenerator

کلاس CodeGenerator توابع زیادی دارد که کار با آن را مشکل می‌کند. اما تنها دو تابع آن در بقیه پروژه مورد استفاده قرار گرفته است. می‌توانیم این دو تابع را در کلاس جداگانه‌ای با اسم CodeGeneratorFacade قرار دهیم. مطابق زیر:

```java
package codeGenerator;

import scanner.token.Token;

public class CodeGeneratorFacade {
    private CodeGenerator codeGenerator;
    public CodeGeneratorFacade() {
        codeGenerator = new CodeGenerator();
    }

    public void semanticFunction(int func, Token next) {
        codeGenerator.semanticFunction(func, next);
    }

    public void printMemory() {
        codeGenerator.printMemory();
    }
}

```

### استفاده از الگوی Facade برای lexicalAnalyzer

برای پنهان کردن پیچیدگی کلاس lexicalAnalyzer از الگوی Facade استفاده می‌کنیم و کلاس LexicalAnalyzerFacade را مطابق زیر تعریف می‌کنیم:


```java
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

```

