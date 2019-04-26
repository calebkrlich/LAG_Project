package com;

import java.util.ArrayList;

class Transition
{
    String from,to,on;

    Transition(String inFrom, String inTo, String inOn)
    {
        from = inFrom;
        to = inTo;
        on = inOn;
    }

    String getString()
    {
        return ("(" + to + "," + from + "," + on + ")");
    }
}

class Node
{
    ArrayList<Transition> transitions = new ArrayList<>();
    int stateValue;

    Node(int state)
    {
        transitions = null;
        stateValue = state;
    }

    Node()
    {
        transitions = null;
        stateValue = 0;
    }

    void addTransition(Transition t) {
        transitions.add(t);
    }

    int getStateValue() {
        return stateValue;
    }
    void setStateValue(int value)
    {
        stateValue = value;
    }
}

public class NFAGenerator
{
    String regexToProcess;


    NFAGenerator(String regex)
    {
        regexToProcess = regex;
    }

    //Handles transition to literal
    Node literal(String inChar)
    {
        Node outputNode = new Node();

        Transition entryTrans = new Transition("0" , "1", inChar);

        outputNode.addTransition(entryTrans);
        return outputNode;
    }

    //Handles concationation between to nodes
    Node concatination(Node one,Node two)
    {
        Node outputNode = new Node(two.getStateValue());   //take the largest state value

        return outputNode;
    }

    ArrayList<String> create()
    {
        ArrayList<String> outNFA = new ArrayList<>();

        




        return outNFA;
    }

}
