package com;

import java.util.ArrayList;
import java.util.Stack;

class Transition
{
    public String from,to,on;

    Transition(String inFrom, String inTo, String inOn)
    {
        from = inFrom;
        to = inTo;
        on = inOn;
    }

    String getString()
    {
        return ("(" + from + "," + to + "," + on + ")");
    }



}

class Node
{
    ArrayList<Transition> transitions = new ArrayList<>();
    int stateValue;

    Node(int state)
    {
        stateValue = state;
    }

    Node()
    {
        stateValue = 0;
    }

    void addTransition(Transition t) {
        transitions.add(t);
    }

    int getStateValue() {
        return stateValue;
    }

    void addToStateValue(int value)
    {
        stateValue += value;

        for(Transition t: transitions)
        {
            t.from += value;
            t.to += value;
        }
    }


    void clearTransitions() {transitions.clear();}

    void printTransitions()
    {
        System.out.println("State " + stateValue + " transitions: ");

        for(Transition t : transitions)
        {
            System.out.println(t.getString());
        }
    }

}

class NFA {

    ArrayList<Node> nodes = new ArrayList<>();
    int acceptingState = 0;

    NFA()
    {

    }

    Node getLastNode() {

        int biggestNodeValue = 0;

        Node biggestNode = null;

        for (Node n : nodes)
        {
            if(n.getStateValue() > biggestNodeValue) {
                biggestNode = n;
                biggestNodeValue = n.getStateValue();
            }
        }

        return biggestNode;
    }

    Node getFirstNode()
    {
        for(Node n : nodes)
        {
            if(n.getStateValue() == 0)
                return n;
        }
        return new Node();  //hopefully never happen
    }


    NFA(String literal) //Simple NFA for a literal
    {
        Node startingNode = new Node(0);
        startingNode.addTransition(new Transition("0","1",literal));

        Node endingNode = new Node(1);

        nodes.add(startingNode);   //entrance node
        nodes.add(endingNode);   //ending node

        acceptingState = 1;
    }

    NFA concatination(NFA first, NFA second)
    {
        NFA outNFA = new NFA();

        Node firstLastNode = first.getLastNode();
        Node secondFirstNode = second.getFirstNode();


        secondFirstNode.addToStateValue(2);

        firstLastNode.printTransitions();

        firstLastNode.addTransition(new Transition(Integer.toString(firstLastNode.getStateValue()),
                Integer.toString(secondFirstNode.getStateValue()),"E"));

        firstLastNode.printTransitions();

        first.printNFA();
        second.printNFA();

        return first;
    }

    NFA kleeneClosure(NFA input)
    {
        NFA out = input;



        return out;
    }

    void printNFA()
    {
        System.out.println("NFA STATES: ");
        for(Node n : nodes)
        {
            n.printTransitions();
        }
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
    ArrayList<String> create()
    {
        ArrayList<String> outNFA = new ArrayList<>();

        //Stacks for holding everything
        Stack<Character> literalStack = new Stack<>();
        Stack<Character> operatorStack = new Stack<>();
        Stack<Character> paranStack = new Stack<>();
        Stack<NFA> NFAStack = new Stack<>();

        boolean flagAddConcatSymbol = false;
        boolean flagPreformConcat = false;

        for(int i = 0; i < regexToProcess.length(); i++) {

            Character charAt = regexToProcess.charAt(i);

            //=============PUSHING STAGE==================
            if (charAt == ')' || charAt == '(') {
                paranStack.push(charAt);
                flagAddConcatSymbol = false;
            }
            else if (charAt == '+' || charAt == '*' || charAt == '?' || charAt == '.' || charAt == '|') {
                operatorStack.push(charAt);
                flagAddConcatSymbol = false;
            }
            else {
                if(flagAddConcatSymbol)
                {
                    operatorStack.push('.');    //add concat to ops stack
                }

                NFAStack.push(new NFA(charAt.toString()));
                flagAddConcatSymbol = true;
            }

            //===============OPERATION STAGE=============

            if(operatorStack.size() > 0) {
                if (operatorStack.peek() == '.') {
                    if (flagPreformConcat) {

                        System.out.println("preforming concat");
                        NFA out = new NFA();
                        NFA top = NFAStack.pop();
                        NFA second = NFAStack.pop();

                        out = new NFA().concatination(top, second);
                    } else {
                        System.out.println("Not Preforming concat");
                        flagPreformConcat = true;
                    }
                }
                if (operatorStack.peek() == '?') {

                }
                if (operatorStack.peek() == '+') {

                }
                if (operatorStack.peek() == '*') {

                }
            }

        }

        System.out.println("Literal :" + literalStack.toString());
        System.out.println("Ops  :" + operatorStack.toString());
        System.out.println("Parans :" + paranStack.toString());

        return outNFA;
    }

}
