package com;

import java.util.ArrayList;
import java.util.List;

public class NFA
{
    public ArrayList<Integer> states = new ArrayList<>();
    public ArrayList<Transition> transitions = new ArrayList<>();
    int finalState = 0;
    int totalStates;

    NFA()
    {
        finalState = 0;
        totalStates = 0;
    }

    NFA(char literal)
    {
        finalState = 1;
        totalStates = 2;
        Transition t  = new Transition(0,1,literal);
        transitions.add(t);
        states.add(0);
        states.add(1);
    }

    void addTranition(Transition t)
    {
        transitions.add(t);
    }

    void addToEachState(int value)
    {
        ArrayList<Integer> updatedStates = new ArrayList<>();
        for(Integer i : states)
        {
            i += value;         //update the states
            updatedStates.add(i);
            finalState += value;
            totalStates += value;
        }
        for(Transition t: transitions)
        {
            t.addToEachTransition(value);   //updates the tranitions
        }

        states = updatedStates;
    }

    int getHighestState()
    {
        int highest = -1;

        for(Integer i : states)
        {
            if(highest < i)
                highest = i;
        }
        return highest;
    }

    int getLowestState()
    {
        int lowest = 1000000;

        for(Integer i : states)
        {
            if(i < lowest)
                lowest = i;
        }
        return lowest;
    }

    void printTransitions()
    {
        System.out.println("NFA Transitions");
        for(Transition t : transitions)
            t.print();
    }

    ArrayList<String> getTransitionsAsStrings()
    {
        ArrayList<String> out = new ArrayList<>();
        for(Transition t : transitions)
        {
            out.add(t.getAsString());
        }
        return out;
    }

    void printStates()
    {
        System.out.println("States");
        for(Integer t : states)
            System.out.println(t);
    }


    int getFinalState()
    {
        return finalState;
    }


}
