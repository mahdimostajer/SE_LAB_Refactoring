package codeGenerator.Types;

public class Indirect implements TypeAddress {
    public String toString(int num) {
        return "@" + num;
    }
}
