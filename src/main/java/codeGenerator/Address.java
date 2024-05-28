package codeGenerator;

import codeGenerator.Types.Direct;
import codeGenerator.Types.TypeAddress;

/**
 * Created by mohammad hosein on 6/28/2015.
 */

public class Address {
    public int num;
    public TypeAddress Type;
    public varType varType;

    public Address(int num, varType varType, TypeAddress Type) {
        this.num = num;
        this.Type = Type;
        this.varType = varType;
    }

    public Address(int num, varType varType) {
        setNum(num);
        this.Type = new Direct();
        this.varType = varType;
    }

    public String toString() {
        return Type.toString(getNum());
    }


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
