package com.aetherwars.model.card.character;

import java.util.Comparator;

public class TypeComparator implements Comparator<Type> {

    @Override
    public int compare(Type t1, Type t2) {
        if ((t1 == Type.OVERWORLD && t2 == Type.END) || (t1 == Type.END && t2 == Type.NETHER) || (t1 == Type.NETHER && t2 == Type.OVERWORLD)) {
            return 1;
        } else if ((t1 == Type.END && t2 == Type.OVERWORLD) || (t1 == Type.NETHER && t2 == Type.END) || (t1 == Type.OVERWORLD && t2 == Type.NETHER)) {
            return -1;
        } else {
            return 0;
        }
    }
}
