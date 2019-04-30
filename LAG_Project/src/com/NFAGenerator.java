package com;

import java.util.ArrayList;
import java.util.Stack;

class Transition
{
    public int from,to;
    public String on;


    Transition(int inFrom, int inTo, String inOn)
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
        //System.out.println("State " + stateValue + " transitions: ");

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
        int smallestValue = 100000;
        Node smallestNode = null;

        for(Node n : nodes)
        {
            if(n.getStateValue() < smallestValue) {
                smallestNode = n;
                smallestValue = smallestNode.getStateValue();
            }
        }
        return smallestNode;
    }

    ArrayList<Node> getNodes()
    {
        return nodes;
    }


    NFA(String literal) //Simple NFA for a literal
    {
        Node startingNode = new Node(0);
        startingNode.addTransition(new Transition(0,1,literal));

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

        for(Node n : first.getNodes())  //copy all nodes from first NFA
        {
            outNFA.nodes.add(n);
        }

        outNFA.printNFA();

        for(Node n : second.getNodes())
        {
            n.addToStateValue(firstLastNode.getStateValue() + 1);       //add two to all nodes from the second
            outNFA.nodes.add(n);        //append to outNFA
        }

        System.out.println("Second NFA new first Value : " + secondFirstNode.getStateValue());

        firstLastNode.printTransitions();

        firstLastNode.addTransition(new Transition(firstLastNode.getStateValue(),
                secondFirstNode.getStateValue(),"E"));

        System.out.println("\n\n");
        firstLastNode.printTransitions();

        first.printNFA();
        second.printNFA();

        return outNFA;
    }

    NFA union(NFA first, NFA second)
    {
        NFA out = new NFA();

        Node entryNode = new Node();
        Node exitNode = new Node();

        entryNode.stateValue = first.getFirstNode().getStateValue();    //set the entry node value
        exitNode.stateValue = first.getLastNode().getStateValue() + 1;  //set exit node value

        entryNode.addTransition(new Transition(entryNode.getStateValue(),first.getFirstNode().getStateValue() + 1, "E"));   // entry into nodes
        exitNode.addTransition(new Transition(first.getLastNode().getStateValue(), exitNode.getStateValue(), "E"));   //exit from first set
        exitNode.addTransition(new Transition(second.getLastNode().getStateValue(), exitNode.getStateValue(), "E"));

        for(Node n : first.getNodes())
        {
            n.addToStateValue(1);
            out.nodes.add(n);
        }

        for(Node n : second.getNodes())
        {
            n.addToStateValue(1);
            out.nodes.add(n);
        }

        return out;
    }

    NFA kleeneClosure(NFA input)
    {
        NFA out = new NFA();

        Node entryNode = new Node();    //add new entry node
        Node exitNode = new Node();     //add exit node


        entryNode.stateValue = input.getFirstNode().getStateValue();    //set the entry node equal to the
        exitNode.stateValue = input.getLastNode().getStateValue() + 2;

        entryNode.addTransition(new Transition(entryNode.getStateValue(),entryNode.getStateValue() + 1, "E"));  //into nfa
        entryNode.addTransition(new Transition(entryNode.getStateValue(),exitNode.getStateValue(), "E"));             //around the nfa
        exitNode.addTransition(new Transition(exitNode.getStateValue() -1 ,exitNode.getStateValue(),"E"));    //Out of nfa
        entryNode.addTransition(new Transition(exitNode.getStateValue() - 1, entryNode.getStateValue() + 1, "E"));
        out.nodes.add(entryNode);
        out.nodes.add(exitNode);

        for(Node n : input.getNodes())
        {
            n.addToStateValue(1);
            out.nodes.add(n);
        }
        return out;
    }

    NFA plusClosure(NFA input)
    {
        NFA out = new NFA();
        Node entryNode = new Node();    //add new entry node
        Node exitNode = new Node();     //add exit node


        entryNode.stateValue = input.getFirstNode().getStateValue();    //set the entry node equal to the
        exitNode.stateValue = input.getLastNode().getStateValue() + 2;

        entryNode.addTransition(new Transition(entryNode.getStateValue(),entryNode.getStateValue() + 1, "E"));  //into nfa
        exitNode.addTransition(new Transition(exitNode.getStateValue() -1 ,exitNode.getStateValue(),"E"));
        entryNode.addTransition(new Transition(exitNode.getStateValue() - 1, entryNode.getStateValue() + 1, "E"));

        out.nodes.add(entryNode);
        out.nodes.add(exitNode);

        for(Node n : input.getNodes())
        {
            n.addToStateValue(1);
            out.nodes.add(n);
        }

        return out;
    }

    NFA questionClosure(NFA input)
    {
        NFA out = new NFA();
        Node entryNode = new Node();    //add new entry node
        Node exitNode = new Node();     //add exit node


        entryNode.stateValue = input.getFirstNode().getStateValue();    //set the entry node equal to the
        exitNode.stateValue = input.getLastNode().getStateValue() + 2;

        entryNode.addTransition(new Transition(entryNode.getStateValue(),entryNode.getStateValue() + 1, "E"));  //into nfa
        exitNode.addTransition(new Transition(exitNode.getStateValue() -1 ,exitNode.getStateValue(),"E"));
        entryNode.addTransition(new Transition(entryNode.getStateValue(),exitNode.getStateValue(), "E"));             //around the nfa


        out.nodes.add(entryNode);
        out.nodes.add(exitNode);

        for(Node n : input.getNodes())
        {
            n.addToStateValue(1);
            out.nodes.add(n);
        }

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



    NFA create()
    {
        System.out.println("\n CREATING NFA");

        NFA outNFA = null;

        //Stacks for holding everything
        Stack<Character> literalStack = new Stack<>();
        Stack<Character> operatorStack = new Stack<>();
        Stack<Character> paranStack = new Stack<>();
        Stack<NFA> NFAStack = new Stack<>();

        boolean flagAddConcatSymbol = false;
        boolean flagPreformConcat = false;
        boolean flagProcessData = false;
        boolean flagPreformKeleen = false;
        boolean flagPreformPlus = false;
        boolean flagFindingClosingParam = false;
        boolean flagPreformUnion = false;

        for(int i = 0; i < regexToProcess.length(); i++) {

            //===============OPERATION STAGE=============
            if (flagFindingClosingParam) {

            }
            if (flagPreformKeleen) {
                System.out.println("Creating kleene closure");

                NFA kleene;

                NFA topNFA = NFAStack.pop();

                kleene = topNFA.kleeneClosure(topNFA);

                NFAStack.push(kleene);
            }

            if (flagPreformPlus) {
                System.out.println("Creating plus closure");

                NFA plus;
                NFA topNFA = NFAStack.pop();
                plus = topNFA.plusClosure(topNFA);

                NFAStack.push(plus);
            }

            if (flagPreformConcat)   //if we need to preform a concationation
            {
                System.out.println("Creating Concationation");
                NFA concatNFA = new NFA();

                NFA second = NFAStack.pop();
                NFA first = NFAStack.pop();

                concatNFA = concatNFA.concatination(first, second); //get the two nfas to concat

                NFAStack.push(concatNFA);

                flagPreformConcat = false;
            }
            if (flagPreformUnion)
            {
                System.out.println("Creating Union");
                NFA unionNFA = new NFA();

                NFA first = NFAStack.pop();
                NFA second = NFAStack.pop();

                unionNFA = unionNFA.concatination(first,second);

                NFAStack.push(unionNFA);

                flagPreformUnion = false;
            }


            Character charAt = regexToProcess.charAt(i);

            //=============PUSHING STAGE==================
            if (charAt == ')' || charAt == '(') {
                paranStack.push(charAt);
                flagAddConcatSymbol = false;

                if(charAt == '(')
                {
                    flagFindingClosingParam = true;     //Process regex until opening paran
                    System.out.println("Processing enclosed expression");
                }
                if(charAt == ')')
                {
                    flagFindingClosingParam = false;
                }
            }
            else if (charAt == '+' || charAt == '*' || charAt == '?' || charAt == '.' || charAt == '|') {
                operatorStack.push(charAt);
                flagAddConcatSymbol = false;

                if(charAt == '*')
                    flagPreformKeleen = true;

                if(charAt == '+')
                    flagPreformPlus = true;

                if(charAt == '|')
                    flagPreformUnion = true;
            }
            else {
                if(flagAddConcatSymbol)
                {
                    operatorStack.push('.');    //add concat to ops stack
                    flagPreformConcat = true;
                }

                NFAStack.push(new NFA(charAt.toString()));  //create a new NFA from the literal
                flagAddConcatSymbol = true;
            }
        }

        System.out.println("Literal :" + literalStack.toString());
        System.out.println("Ops  :" + operatorStack.toString());
        System.out.println("Parans :" + paranStack.toString());

        outNFA = NFAStack.pop();    //get the final NFA off the stack
        return outNFA;
    }

}
