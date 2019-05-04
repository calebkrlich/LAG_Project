package com;

import java.util.ArrayList;

public class DFA
{
    public ArrayList<Integer> states;
    public ArrayList<Transition> transitions;
    public ArrayList<Integer> acceptingStates;

    DFA()
    {
        states = new ArrayList<>();
        transitions = new ArrayList<>();
        acceptingStates = new ArrayList<>();

    }

    ArrayList<String> getDFAasArrayList()
    {
        ArrayList<String> returnedList = new ArrayList<>();

        for(Transition t: transitions) {
            returnedList.add(t.getAsString());
        }
        return returnedList;
    }
}
