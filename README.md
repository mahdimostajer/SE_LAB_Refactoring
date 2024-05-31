# گزارش آزمایش



## استفاده از Polymorphism به جای شرط

عملکرد type های مختلف کلاس Address با استفاده از switch پیاده سازی شده است. بهتر است آنرا با polymorphism جایگزین کنیم.

```java
public interface TypeAddress {

    public String toString(int num);
}

public class Indirect implements TypeAddress {
    public String toString(int num) {
        return "@" + num;
    }
}

public class Imidiate implements TypeAddress{
    public String toString(int num) {
        return "#" + num;
    }
}

public class Direct implements TypeAddress{
    public String toString(int num) {
        return num + "";
    }
}
```

## استفاده از  Separate Query From Modifier
کلاس Memory شامل توابعی است که هم مقدار متغیرهای داخلی کلاس را تغییر می‌دهند و هم مقدار آنرا باز می‌گردانند. بهتر است این موارد را از هم جدا کنیم:

```java
private int getLastTempIndex() {
        return lastTempIndex;
}

private int addTempIndex() {
        return lastTempIndex += tempSize;
}
```


## استفاده از الگوی Facade برای CodeGenerator

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

## استفاده از الگوی Facade برای lexicalAnalyzer

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

## استفاده از Self Encapsulate Field

کلاس Address متغیر داخلی با اسم num دارد که به طور مستقیم به آن دسترسی دارد و آنرا تغییر می‌دهد. به جای دسترسی مستقیم برای این متغیر getter و setter اضافه می‌کنیم.

## استفاده از Extract Method
بخش‌هایی از کد که با یکدیگر مرتبط هستند را می توانیم در یک تابع جداگانه قرار دهیم. خصوصا اگر این کد در جاهای مختلف استفاده شده است می‌توانیم با استفاده از این روش کد‌های duplicate را حذف کنیم. دو نمونه از توابع ایجاد شده در فرایند بازآرایی:

```java
private varType getVarType(SymbolType s) {
    switch (s) {
        case Bool:
            return varType.Bool;
        case Int:
            return varType.Int;
    }
    return varType.Int;
}
```

```java
private List<Address> getOperandAddresses() {
    Address s2 = ss.pop();
    Address s1 = ss.pop();
    if (s1.varType != varType.Int || s2.varType != varType.Int) {
        ErrorHandler.printError("In sub two operands must be integer");
    }
    List<Address> addresses = new ArrayList<>();
    addresses.add(s1);
    addresses.add(s2);
    return addresses;
}
```

## استفاده از Replace Nested Conditional with Guard Clauses

از این روش می‌توان برای حذف شرط‌های تو در تو استفاده کرد تا دنبال کردن جریان اجرای کد آسان شود. هر کدام از حالت‌های خاص را در یک clause جداگانه قرار می‌دهیم. دو نمونه از کاربرد این روش:

```java
for (Type t : Type.values()) {
    if(matcher.group(t.name()) == null) {
        continue;
    }
    if (matcher.group(Type.COMMENT.name()) != null) {
        break;
    }
    if (matcher.group(Type.ErrorID.name()) != null) {
        ErrorHandler.printError("The id must start with character");
        break;
    }
    return new Token(t, matcher.group(t.name()));
}
```

```java
for (int j = 1; j < cols.length; j++) {
    if(cols[j].isEmpty()) {
        continue;
    }
    if (cols[j].equals("acc")) {
        actionTable.get(actionTable.size() - 1).put(terminals.get(j), new Action(act.accept, 0));
        continue;
    }
    if (terminals.containsKey(j)) {
        Token t = terminals.get(j);
        Action a = new Action(cols[j].charAt(0) == 'r' ? act.reduce : act.shift, Integer.parseInt(cols[j].substring(1)));
        actionTable.get(actionTable.size() - 1).put(t, a);
        continue;
    }
    if (nonTerminals.containsKey(j)) {
        gotoTable.get(gotoTable.size() - 1).put(nonTerminals.get(j), Integer.parseInt(cols[j]));
        continue;
    }

    throw new Exception();
}
```
