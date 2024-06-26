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
## پرسش‌ها
### سوال ۱)
کد تمیز: کدی است که به راحتی قابل خواندن، فهمیدن، و نگهداری است، و از بهترین شیوه‌های برنامه‌نویسی پیروی می‌کند.

بدهی فنی: مشکلات و نقص‌های بالقوه در کد یا سیستم که به دلیل تصمیمات سریع و موقتی ایجاد شده‌اند و نیاز به اصلاح دارند تا مانع مشکلات بزرگ‌تر در آینده شوند.

بوی بد: نشانه‌ها یا الگوهای مشکل‌زا در کد که نشان می‌دهند ممکن است نیاز به بازنویسی یا بهبود داشته باشد تا کد تمیزتر و قابل نگهداری‌تر شود.

### سوال ۲)
بوی کد (Code Smell) Bloaters در سایت Refactoring.guru به عنوان یکی از انواع بوی کد معرفی شده که به حجیم شدن و گسترش غیرضروری کدها اشاره دارد. این بوی کد زمانی رخ می‌دهد که کدها یا ساختارهای برنامه بیش از حد بزرگ، پیچیده یا طولانی شده‌اند، که باعث سختی در خواندن، نگهداری و تغییر کد می‌شود. Bloaters معمولاً نتیجه رشد تدریجی کد به مرور زمان بدون توجه به بهینه‌سازی و بازسازی مناسب هستند.
انواع رایج Bloaters شامل موارد زیر هستند:

Long Method:

روش‌های طولانی که انجام چندین کار مختلف را در خود جای داده‌اند. این روش‌ها باید به چندین روش کوچکتر و با هدف خاص تقسیم شوند.

Large Class:

کلاس‌های بزرگ که دارای تعداد زیادی ویژگی و متد هستند. این کلاس‌ها باید به کلاس‌های کوچکتر با وظایف مشخص‌تر تقسیم شوند.

Primitive Obsession:

استفاده بیش از حد از انواع داده‌های اولیه (مثل رشته‌ها و اعداد صحیح) به جای استفاده از اشیاء سطح بالاتر و انتزاعی‌تر که معانی بیشتری دارند.

Long Parameter List:

لیست‌های طولانی از پارامترها در متدها که می‌تواند فهم و استفاده از متدها را دشوار کند. این لیست‌ها باید کاهش یابند، مثلاً با استفاده از اشیاء یا کلاس‌های داده‌ای.

Data Clumps:

گروه‌هایی از داده‌ها که همیشه با هم استفاده می‌شوند. این داده‌ها باید در قالب کلاس‌ها یا ساختارهای داده‌ای مناسب‌تر تجمیع شوند.
<hr>

بوی کد (Code Smell) Object-Orientation Abusers در سایت Refactoring.guru به مجموعه‌ای از الگوهای ضعیف در طراحی شیءگرا اشاره دارد. این بوی کد زمانی رخ می‌دهد که اصول و مفاهیم شیءگرایی به درستی پیاده‌سازی نمی‌شوند یا به طور نادرست استفاده می‌شوند، که می‌تواند منجر به کد پیچیده، ناخوانا و سخت برای نگهداری شود.

انواع رایج Object-Orientation Abusers شامل موارد زیر هستند:


Switch Statements: 


استفاده بیش از حد از دستورهای شرطی switch یا if-else برای تعیین رفتارهای مختلف بر اساس نوع یا حالت یک شیء. این ساختارها می‌توانند به جای استفاده از الگوهای طراحی مناسب مانند الگوی State، الگوی Strategy یا الگوی Polymorphism به کار روند.

Temporary Field:


وجود فیلدهایی در یک کلاس که فقط در شرایط خاصی مقداردهی می‌شوند و در شرایط دیگر بی‌استفاده هستند. این فیلدها می‌توانند کد را پیچیده و گیج‌کننده کنند.

Refused Bequest:


کلاس‌های فرزند که به درستی از کلاس‌های والد ارث‌بری نمی‌کنند، یعنی یا متدهای والد را بازنویسی نمی‌کنند یا به درستی از آن‌ها استفاده نمی‌کنند. این وضعیت نشان‌دهنده یک طراحی نادرست در سلسله مراتب ارث‌بری است.

Alternative Classes with Different Interfaces:


کلاس‌های متفاوتی که وظایف مشابهی را انجام می‌دهند اما این وظایف را از طریق رابط‌های مختلف ارائه می‌دهند. این حالت می‌تواند باعث سردرگمی و پیچیدگی در کد شود.


<hr>
بوی کد (Code Smell) Change Preventers در سایت Refactoring.guru به دسته‌ای از مشکلات در کد اشاره دارد که تغییر و توسعه‌ی کد را دشوار و پرهزینه می‌کنند. این بوی کد زمانی رخ می‌دهد که کد به گونه‌ای طراحی یا پیاده‌سازی شده که هر تغییری در آن نیازمند تغییرات گسترده و متعدد در بخش‌های مختلف کد است. Change Preventers یکی از خطرناک‌ترین بوی‌های کد محسوب می‌شود زیرا مستقیماً روی قابلیت نگهداری و توسعه‌پذیری نرم‌افزار تأثیر می‌گذارد.

انواع رایج Change Preventers شامل موارد زیر هستند:

Divergent Change:

زمانی که یک کلاس به‌طور مکرر به دلایل مختلف تغییر می‌کند. به عبارت دیگر، تغییر در یک کلاس نیازمند تغییرات مختلف و متعددی در همان کلاس است، که می‌تواند نشان‌دهنده‌ی این باشد که کلاس دارای وظایف زیادی است.

Shotgun Surgery: 

زمانی که هر تغییر در یک ویژگی سیستم نیازمند تغییرات در بخش‌های متعدد و مختلف کد است. این وضعیت نشان‌دهنده‌ی پراکندگی مسئولیت‌ها و عدم تمرکز است.


Parallel Inheritance Hierarchies:

زمانی که هر بار که یک کلاس جدید در یک سلسله مراتب ارث‌بری ایجاد می‌شود، باید یک کلاس متناظر در سلسله مراتب دیگری نیز ایجاد شود. این وضعیت باعث افزایش پیچیدگی و دشواری در نگهداری کد می‌شود.

<hr>

بوی کد (Code Smell) Dispensables در سایت Refactoring.guru به دسته‌ای از مشکلات کد اشاره دارد که نشان‌دهنده کدهای غیرضروری، اضافی یا بدون استفاده است. این نوع کدها می‌توانند باعث پیچیدگی غیرضروری، افزایش حجم کد و کاهش خوانایی و نگهداری شوند. به‌طور کلی، Dispensables شامل کدهایی است که می‌توانند بدون تأثیر منفی بر عملکرد یا رفتار سیستم حذف شوند.

انواع رایج Dispensables شامل موارد زیر هستند:

Comments:

 نظرات اضافی یا نامناسب که به جای توضیح مفید، باعث شلوغی کد می‌شوند. نظرات می‌توانند نشانه‌ای از این باشند که کد به اندازه کافی خود-مستند (self-explanatory) نیست.

Duplicate Code:

 کدهای تکراری که در بخش‌های مختلف برنامه ظاهر می‌شوند. این وضعیت می‌تواند نگهداری کد را دشوار کند زیرا هر تغییر باید در چندین مکان انجام شود.

Dead Code:

کدهایی که هرگز اجرا نمی‌شوند یا استفاده نمی‌شوند، مانند متغیرها، متدها یا کلاس‌های بدون استفاده.

Speculative Generality:

کدهایی که برای نیازهای احتمالی آینده نوشته شده‌اند اما در حال حاضر استفاده نمی‌شوند. این کدها معمولاً شامل کلاس‌ها، متدها، پارامترها و ارث‌بری‌های اضافی هستند.

Data Class: 

کلاس‌هایی که فقط شامل داده‌ها هستند و هیچ رفتار یا متدی ندارند. این کلاس‌ها اغلب به عنوان حمل‌کننده داده (Data Holder) استفاده می‌شوند و معمولاً نشان‌دهنده‌ی عدم استفاده مناسب از 
شیءگرایی هستند.

Lazy Class: 

کلاس‌هایی که عملکرد کافی ندارند و فقط مقدار کمی از رفتار را ارائه می‌دهند.

Data Class:

 کلاس‌هایی که فقط شامل داده‌ها هستند و هیچ رفتار یا متدی ندارند. این کلاس‌ها اغلب به عنوان حمل‌کننده داده (Data Holder) استفاده می‌شوند و معمولاً نشان‌دهنده‌ی عدم استفاده مناسب از شیءگرایی هستند.

<hr>

بوی کد (Code Smell) Couplers در سایت Refactoring.guru به مشکلاتی در کد اشاره دارد که ناشی از وابستگی‌های بیش از حد بین کلاس‌ها و اجزای مختلف برنامه است. وابستگی‌های زیاد باعث کاهش انعطاف‌پذیری، افزایش پیچیدگی و دشواری در نگهداری و تست کد می‌شود. Couplers زمانی رخ می‌دهد که کلاس‌ها یا ماژول‌ها به شدت به همدیگر وابسته‌اند، به طوری که تغییر در یکی از آن‌ها منجر به تغییرات گسترده در سایر بخش‌ها می‌شود.

انواع رایج Couplers شامل موارد زیر هستند:

Feature Envy: 

زمانی که یک متد در یک کلاس بیش از حد به داده‌ها یا متدهای کلاس دیگری وابسته است. این متد به جای استفاده از داده‌های داخلی کلاس خودش، بیشتر به داده‌های کلاس دیگر دسترسی دارد.

Inappropriate Intimacy: 

زمانی که دو کلاس بیش از حد به جزئیات داخلی یکدیگر دسترسی دارند و به شدت به هم وابسته‌اند. این وضعیت معمولاً منجر به نقض اصل کپسوله‌سازی (Encapsulation) می‌شود.

Message Chains:

زمانی که یک شیء به زنجیره‌ای از پیام‌ها برای دسترسی به داده‌ها یا متدهای یک شیء دیگر نیاز دارد. این وضعیت نشان‌دهنده‌ی وابستگی غیرضروری و پیچیده بین کلاس‌ها است.

Middle Man: 

زمانی که یک کلاس بیشتر متدهای خود را به کلاس‌های دیگر تفویض می‌کند بدون اینکه خودش کار زیادی انجام دهد. این کلاس‌ها معمولاً به عنوان واسطه‌های بی‌فایده شناخته می‌شوند.

### سوال ۳)
بوی بد Lazy Class یکی از انواع بوی بد کدها است که نشان‌دهنده کلاس‌هایی با تعداد اندک وظایف یا وظایف بسیار ساده می‌باشد که به اندازه‌ای کم‌کار هستند که بودن یا نبودن آن‌ها تفاوت چندانی در عملکرد برنامه ندارد. این بو در دسته‌بندی dispensables قرار می‌گیرد.

بازآرایی‌های پیشنهادی برای برطرف کردن بوی Lazy Class:

Inline Class (درون‌گذاری کلاس):

اگر کلاس مورد نظر تنها شامل چند متد ساده و کم‌کار است، میتوان این متدها را به کلاس دیگری که بیشتر مرتبط با وظایف اصلی این متدهاست منتقل کرد. این عمل باعث حذف کلاس Lazy می‌شود و وظایف آن را به کلاس‌های دیگر اضافه می‌کند.


Collapse Hierarchy (فروپاشی سلسله‌مراتب):


اگر کلاس Lazy بخشی از یک سلسله‌مراتب وراثتی است و تنها اندکی از متدها یا ویژگی‌های کلاس پایه را استفاده می‌کند، میتوان این سلسله‌مراتب را حذف کرد و متدها و ویژگی‌های لازم را به کلاس پایه یا زیرکلاس‌های دیگر منتقل کرد.


مواقعی که باید بوی Lazy Class را نادیده گرفت:


طراحی آینده‌نگر: اگر در حال طراحی یک سیستم هستیم که ممکن است در آینده توسعه یابد و کلاس Lazy در حال حاضر وظایف کمی دارد اما در آینده وظایف بیشتری به آن اضافه خواهد شد، بهتر است کلاس را نگه داریم.


سهولت در تست و نگهداری: اگر نگه‌داشتن کلاس Lazy به دلیل تمیز و سازمان‌دهی‌شده بودن کد و سهولت در تست و نگهداری کدها مفید است، می‌توان آن را نگه داشت.


بنابراین، بوی Lazy Class معمولاً در دسته‌بندی داده‌های بی‌استفاده قرار می‌گیرد و برای رفع آن بازآرایی‌هایی مانند درون‌گذاری کلاس و فروپاشی سلسله‌مراتب پیشنهاد می‌شود. با این حال، در مواقعی که طراحی آینده‌نگر یا سهولت در نگهداری و تست کدها مهم است، می‌توان این بو را نادیده گرفت.


### سوال ۴)
### سوال ۵)

پلاگین Formatter یک ابزار است که برای قالب‌بندی و زیبا کردن کدهای برنامه‌نویسی استفاده می‌شود. هدف اصلی این پلاگین‌ها، اعمال یک الگوی استاندارد و یکنواخت بر روی کدها است تا کد خواناتر، مرتب‌تر و قابل‌فهم‌تر شود. این پلاگین‌ها معمولاً به صورت خودکار قالب‌بندی کد را انجام می‌دهند و نیازی به تغییر دستی در کد توسط برنامه‌نویس نیست.


مزایای استفاده از پلاگین Formatter:


افزایش خوانایی کد:


قالب‌بندی یکنواخت کد باعث می‌شود که کدها به راحتی توسط دیگران قابل خواندن و درک باشند. این امر در کارهای تیمی بسیار مهم است.


کاهش خطاهای برنامه‌نویسی:


کدهای مرتب و منظم احتمال وجود خطاهای نگارشی و منطقی را کاهش می‌دهند. خطاهای ناشی از فاصله‌گذاری نامناسب یا استفاده نادرست از پرانتزها به حداقل می‌رسد.


صرفه‌جویی در زمان:


برنامه‌نویسان نیازی به صرف زمان زیاد برای قالب‌بندی دستی کد ندارند. پلاگین Formatter این کار را به صورت خودکار انجام می‌دهد.


تطابق با استانداردهای کدنویسی:


بسیاری از پلاگین‌های Formatter به طور پیش‌فرض با استانداردهای کدنویسی شناخته شده (مثل PEP 8 برای Python) سازگار هستند و کدها را بر اساس این استانداردها قالب‌بندی می‌کنند.


رابطه پلاگین Formatter با بازآرایی کد (Refactoring):


بازآرایی کد یا Refactoring به فرآیندی گفته می‌شود که در آن ساختار داخلی کد بدون تغییر در رفتار خارجی آن بهبود می‌یابد. هدف از Refactoring افزایش کیفیت کد، بهبود خوانایی، و نگهداری بهتر است.


قالب‌بندی و بازآرایی کد: پلاگین‌های Formatter به بازآرایی کد کمک می‌کنند چرا که قالب‌بندی یکنواخت و منظم کد، پیدا کردن نقاط ضعف و بهبودهای ممکن در ساختار کد را آسان‌تر می‌کند. وقتی کدها قالب‌بندی مناسبی داشته باشند، برنامه‌نویس می‌تواند به سادگی نواحی مشکل‌دار را شناسایی و بهینه‌سازی کند.


یکپارچگی تیمی: در پروژه‌های تیمی، استفاده از پلاگین Formatter می‌تواند همه اعضای تیم را به استفاده از یک سبک واحد وادار کند، که این امر بازآرایی و اصلاح کد توسط افراد مختلف را ساده‌تر و بی‌دردسرتر می‌سازد.


به طور خلاصه، پلاگین Formatter به بهبود خوانایی و سازمان‌دهی کد کمک می‌کند و می‌تواند زمینه را برای بازآرایی مؤثرتر و راحت‌تر کد فراهم سازد.
