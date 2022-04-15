package com.aetherwars.model.card;

public interface Activable {
    boolean isActive();
    void apply(SummonedCharacter c) throws Exception;
    void revert(SummonedCharacter c) throws Exception;
}
