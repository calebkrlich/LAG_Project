package com;


class NFAState
{
    private String[][] goTo;        //The states that the NFA can goto when they consume the next input char
    private String comsumeOn;       //What character they must see inorder to advance
}


public class NFAGenerator
{

    private boolean nextState()     //returns true if next state is accepting
    {

        return true;
    }

    private boolean isAcceptingState() //returns true if current state is an accepting state
    {
        return true;
    }

    public void create()        //creates the NFA from the token
    {

    }
}
