package com;

import java.util.ArrayList;

class DFAGenerator
{
    NFA nfaToTranslate;
    ArrayList<Character> transitionTypes = new ArrayList<>();   //Holds the transition types/on
    ArrayList<String> transitions = new ArrayList<>();          //Holds full transition
    ArrayList<Integer> states = new ArrayList<>();

    DFAGenerator(NFA inNFA)
    {
        nfaToTranslate = inNFA;
    }


    DFA create() {
        DFA outDFA = new DFA();

        transitions = nfaToTranslate.getTransitionsAsStrings();


        //Fetches all the transition types that aren't E
        for (String i : transitions)   //fetch the transitions
        {
            String[] splitParts = i.split(","); //Split the transition up
            int fromState = Integer.parseInt("" + splitParts[0].charAt(1)); //Get the from state
            int toState = Integer.parseInt("" + splitParts[1].charAt(0));   //get the to state

            //add all possible states
            if (!states.contains(fromState))
                states.add(fromState);
            if (!states.contains(toState))
                states.add(toState);

            //get the transition char
            char transition = splitParts[2].charAt(0);

            if (transition != 'E')  //add all transitions that aren't E
            {
                transitionTypes.add(transition);
            }
        }

        //Build E* table
        int eStarXSize = transitionTypes.size() + 1;
        int eStarYSize = states.size();


        //needs to be strings
        String[][] eStarTable = new String[eStarXSize][eStarYSize];

        for (String t : transitions) {
            String[] splitParts = t.split(","); //Split the transition up
            int fromState = Integer.parseInt("" + splitParts[0].charAt(1)); //Get the from state
            int toState = Integer.parseInt("" + splitParts[1].charAt(0));   //get the to state
            char transition = splitParts[2].charAt(0);

            //fill out non E move in table first
            if (transition != 'E') {
                int xIndex = transitionTypes.indexOf(transition);
                int yIndex = states.indexOf(fromState);

                eStarTable[xIndex][yIndex] = String.valueOf(toState);
            }
        }


        //Add the basic loop back E transition
        for (int i = 0; i < eStarYSize; i++) {
            eStarTable[eStarXSize - 1][i] = String.valueOf(states.get(i));
        }

        //find all E* transitions
        for (String t : transitions) {
            String[] splitParts = t.split(","); //Split the transition up
            int fromState = Integer.parseInt("" + splitParts[0].charAt(1)); //Get the from state
            int toState = Integer.parseInt("" + splitParts[1].charAt(0));   //get the to state
            char transition = splitParts[2].charAt(0);

            if (transition == 'E') {
                int yIndex = states.indexOf(fromState);
                eStarTable[eStarXSize - 1][yIndex] += "," + String.valueOf(toState);
            }
        }

        String[][] dfaFinalTable = new String[eStarXSize][1000];    //big enough table for dynamic allocations
        boolean newRowAddedFlag = true;

        //creates the final dfa table
        for (int i = 0; i < eStarXSize; i++) {
            for (int j = 0; j < 1000; j++)
            {
                if(!newRowAddedFlag)
                    break;
                else
                {
                    //if it is not a null entry
                    if(eStarTable[i][j] != "-" && eStarTable[i][j] != null)
                    {
                        int yIndex = Integer.parseInt(eStarTable[i][j]);    //get the index for the table result

                        String rowResult = eStarTable[eStarXSize - 1][yIndex];
                        System.out.println(rowResult);
                    }
                }
            }
        }



        //For testing purposes
        for (int i = 0; i < eStarXSize; i++)
        {
            for(int j = 0; j < eStarYSize; j++)
            {
                if(eStarTable[i][j] != null)
                    System.out.print(eStarTable[i][j] + " ");
                else
                    System.out.print('-' + " ");
            }
            System.out.println();
        }

        return outDFA;
    }


}