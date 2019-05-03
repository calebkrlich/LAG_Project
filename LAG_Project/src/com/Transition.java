package com;

import java.util.ArrayList;

public class Transition
{
    int from,to;
    char on;

    Transition(int inFrom, int inTo, char inOn)
    {
        from = inFrom;
        to = inTo;
        on = inOn;
    }

    ArrayList<String> getTransition()
    {
        ArrayList<String> out = new ArrayList<>();
        out.add(Integer.toString(from));
        out.add(Integer.toString(to));
        out.add(Character.toString(on));
        return out;
    }

    void addToEachTransition(int value)
    {
        from += value;
        to += value;
    }

    String getAsString()
    {
        return ("(" + from + "," + to + "," + on + ")");
    }

    void print()
    {
        System.out.println("(" + from + "," + to + "," + on + ")");
    }
}
