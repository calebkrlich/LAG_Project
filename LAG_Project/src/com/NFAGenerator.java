package com;

import java.util.ArrayList;

public class NFAGenerator {

    String regexToProcess;

    //Holds created regexs
    ArrayList<NFA> nfaTable = new ArrayList<>();

    NFAGenerator(String inRegex) {
        regexToProcess = inRegex;
    }

    NFA getNFAFromTable(int pos) {
        return nfaTable.get(pos);
    }

    //=======================================NFA CONNECTIONS TYPES
    NFA kleeneClosure(NFA input)
    {
        NFA outputNFA = new NFA();

        outputNFA.states.add(input.getLowestState());   //add the first state
        outputNFA.printStates();
        input.addToEachState(1);                  //add one to compensate from the first state
        outputNFA.states.addAll(input.states);          //add the modified states
        outputNFA.states.add(input.getHighestState() + 1); //add one more state at end

        outputNFA.printStates();

        outputNFA.transitions = input.transitions;      //copy all original tranitions
        outputNFA.addTranition(new Transition(outputNFA.getLowestState(), input.getLowestState(), 'E'));  //add the entry
        outputNFA.addTranition(new Transition(outputNFA.getLowestState(), outputNFA.getHighestState(), 'E'));   //add around tranisiton
        outputNFA.addTranition(new Transition(input.getHighestState(), input.getLowestState(), 'E')); //back in transition
        outputNFA.addTranition(new Transition(input.getHighestState(), outputNFA.getHighestState(), 'E'));   //final exit
        return outputNFA;
    }

    NFA plusClosure(NFA input)
    {
        NFA outputNFA = new NFA();

        outputNFA.states.add(input.getLowestState());   //add the first state
        outputNFA.printStates();
        input.addToEachState(1);                  //add one to compensate from the first state
        outputNFA.states.addAll(input.states);          //add the modified states
        outputNFA.states.add(input.getHighestState() + 1); //add one more state at end

        outputNFA.printStates();

        outputNFA.transitions = input.transitions;      //copy all original tranitions
        outputNFA.addTranition(new Transition(outputNFA.getLowestState(), input.getLowestState(), 'E'));  //add the entry
        outputNFA.addTranition(new Transition(input.getHighestState(), input.getLowestState(), 'E')); //back in transition
        outputNFA.addTranition(new Transition(input.getHighestState(), outputNFA.getHighestState(), 'E'));   //final exit
        return outputNFA;
    }

    NFA questionClosure(NFA input)
    {
        NFA outputNFA = new NFA();

        outputNFA.states.add(input.getLowestState());   //add the first state
        outputNFA.printStates();
        input.addToEachState(1);                  //add one to compensate from the first state
        outputNFA.states.addAll(input.states);          //add the modified states
        outputNFA.states.add(input.getHighestState() + 1); //add one more state at end

        outputNFA.printStates();

        outputNFA.transitions = input.transitions;      //copy all original tranitions
        outputNFA.addTranition(new Transition(outputNFA.getLowestState(), input.getLowestState(), 'E'));  //add the entry
        outputNFA.addTranition(new Transition(outputNFA.getLowestState(), outputNFA.getHighestState(), 'E'));   //add around tranisiton
        outputNFA.addTranition(new Transition(input.getHighestState(), outputNFA.getHighestState(), 'E'));   //final exit
        return outputNFA;
    }

    NFA concationation(NFA one, NFA two)
    {
        NFA outputNFA = new NFA();
        outputNFA.printTransitions();

        outputNFA.states = one.states;              //copy all the states from one
        outputNFA.transitions = one.transitions;    //copy all the transitions

        two.addToEachState(one.states.size());                //update all of the second nfa's states

        outputNFA.addTranition(new Transition(one.getHighestState(), two.getLowestState(), 'E'));
        outputNFA.states.addAll(two.states);        //copy all of those states
        outputNFA.transitions.addAll(two.transitions);  //and transitions

        outputNFA.printTransitions();


        return outputNFA;
    }

    NFA union(NFA one, NFA two)
    {
        NFA outputNFA = new NFA();

        outputNFA.states.add(one.getLowestState());  //Take the first NFA lowest state
        one.addToEachState(1);  //add one to offset each node

        outputNFA.states.addAll(one.states);        //then copy the states
        two.addToEachState(one.states.size() + 1);  // then added the highest nodes worth to each node
        outputNFA.states.addAll(two.states);        //then add the new states

        outputNFA.transitions.addAll(one.transitions);
        outputNFA.transitions.addAll(two.transitions);
        outputNFA.states.add(two.getHighestState() + 1);

        outputNFA.addTranition(new Transition(outputNFA.getLowestState(),one.getLowestState(),'E'));    //add the into transition into one
        outputNFA.addTranition(new Transition(outputNFA.getLowestState(),two.getLowestState(),'E'));    //add the into transition into two

        outputNFA.addTranition(new Transition(one.getHighestState(),outputNFA.getHighestState(),'E'));
        outputNFA.addTranition(new Transition(two.getHighestState(),outputNFA.getHighestState(), 'E'));

        return outputNFA;
    }

    //=============================================END NFA CONNECTION TYPES

    //Used to process substring
    String regexToNFA(String regex) {

        if (regex.length() < 2) {
            return regex;
        } else {

            NFA outNFA = new NFA();
            boolean addConcatSymbol = false;
            String augmenetedRegex = "";

            //Adding the concat symbols
            for (int i = 0; i < regex.length(); i++) {
                if (Character.isLetter(regex.charAt(i))) {
                    if (addConcatSymbol)
                        augmenetedRegex += ".";


                    augmenetedRegex += regex.charAt(i);
                    addConcatSymbol = true;
                } else {
                    addConcatSymbol = false;
                    augmenetedRegex += regex.charAt(i);
                }
            }

            if (augmenetedRegex.charAt(augmenetedRegex.length() - 1) == '.') {
                augmenetedRegex = augmenetedRegex.substring(0, augmenetedRegex.length() - 1);
            }

            System.out.println("Concat Regex : " + augmenetedRegex);

            int highestPriority = -5;    //marker for highest priority
            int highestPriorityPosition = 0;
            ArrayList<Character> operators = new ArrayList<>();


            //finding the highest priority operator
            for (int i = 0; i < augmenetedRegex.length(); i++) {
                int currentPosPriority = -1;

                if (Character.isLetter(augmenetedRegex.charAt(i))) {
                    currentPosPriority = -1;
                }
                if (augmenetedRegex.charAt(i) == '|') {
                    currentPosPriority = 0;
                }
                if (augmenetedRegex.charAt(i) == '.') {
                    currentPosPriority = 1;
                }
                if (augmenetedRegex.charAt(i) == '*' || augmenetedRegex.charAt(i) == '+' || augmenetedRegex.charAt(i) == '?') {
                    currentPosPriority = 2;
                }

                if (currentPosPriority > highestPriority) {
                    highestPriority = currentPosPriority;
                    highestPriorityPosition = i;
                }
            }

            char highestPriorityOperator = augmenetedRegex.charAt(highestPriorityPosition);
            System.out.println("Highest Priority Position : " + highestPriorityPosition);
            System.out.println("Op Type : " + augmenetedRegex.charAt(highestPriorityPosition));


            //OPERATORS===================================================================================================


            //KLEENE CLOSURE OPERATOR
            if (highestPriorityOperator == '*')
            {
                System.out.println("Kleene...");

                NFA kleene = new NFA();
                String toRemove = "";
                String newTablePos = "";

                if (Character.isLetter(augmenetedRegex.charAt(highestPriorityPosition - 1)))          //if its a character
                {
                    NFA character = new NFA(augmenetedRegex.charAt(highestPriorityPosition - 1));
                    kleene = kleeneClosure(character);

                    kleene.printTransitions();
                    nfaTable.add(kleene);
                    newTablePos = String.valueOf(nfaTable.indexOf(kleene));
                } else {
                    System.out.println("Fetching processed...");

                    int tablePos = Integer.parseInt(Character.toString(augmenetedRegex.charAt(highestPriorityPosition - 1)));
                    NFA processed = nfaTable.get(tablePos);   //fetch the already processed NFA
                    kleene = kleeneClosure(processed);
                    newTablePos = String.valueOf(tablePos);
                    nfaTable.add(tablePos, kleene);
                }

                toRemove += (augmenetedRegex.charAt(highestPriorityPosition - 1) + "*");
                augmenetedRegex = augmenetedRegex.replace(toRemove, newTablePos + ".");  //add concat in case, snipped later
            }

            //PLUS CLOSURE OPERATOR
            if(highestPriorityOperator == '+')
            {
                System.out.println("Plus...");

                NFA kleene = new NFA();
                String toRemove = "";
                String newTablePos = "";

                if (Character.isLetter(augmenetedRegex.charAt(highestPriorityPosition - 1)))          //if its a character
                {
                    NFA character = new NFA(augmenetedRegex.charAt(highestPriorityPosition - 1));
                    kleene = plusClosure(character);

                    kleene.printTransitions();
                    nfaTable.add(kleene);
                    newTablePos = String.valueOf(nfaTable.indexOf(kleene));
                } else {
                    System.out.println("Fetching processed...");

                    int tablePos = Integer.parseInt(Character.toString(augmenetedRegex.charAt(highestPriorityPosition - 1)));
                    NFA processed = nfaTable.get(tablePos);   //fetch the already processed NFA
                    kleene = plusClosure(processed);
                    newTablePos = String.valueOf(tablePos);
                    nfaTable.add(tablePos, kleene);
                }

                toRemove += (augmenetedRegex.charAt(highestPriorityPosition - 1) + "+");
                augmenetedRegex = augmenetedRegex.replace(toRemove, newTablePos + ".");  //add concat in case, snipped later
            }

            //QUESTION CLOSURE OPERATOR
            if(highestPriorityOperator == '?')
            {
                System.out.println("Question...");

                NFA kleene = new NFA();
                String toRemove = "";
                String newTablePos = "";

                if (Character.isLetter(augmenetedRegex.charAt(highestPriorityPosition - 1)))          //if its a character
                {
                    NFA character = new NFA(augmenetedRegex.charAt(highestPriorityPosition - 1));
                    kleene = questionClosure(character);

                    kleene.printTransitions();
                    nfaTable.add(kleene);
                    newTablePos = String.valueOf(nfaTable.indexOf(kleene));

                } else {
                    System.out.println("Fetching processed...");

                    int tablePos = Integer.parseInt(Character.toString(augmenetedRegex.charAt(highestPriorityPosition - 1)));
                    NFA processed = nfaTable.get(tablePos);   //fetch the already processed NFA
                    kleene = questionClosure(processed);
                    newTablePos = String.valueOf(tablePos);
                    nfaTable.add(tablePos, kleene);
                }

                toRemove += (augmenetedRegex.charAt(highestPriorityPosition - 1) + "?");
                augmenetedRegex = augmenetedRegex.replace(toRemove, newTablePos + ".");  //add concat in case, snipped later
            }

            //CONCATIONATION OPERATOR
            if (highestPriorityOperator == '.')
            {
                System.out.println("Concatinating...");
                char charAtPosition = '#';
                NFA first = null;
                NFA second = null;

                char prior = augmenetedRegex.charAt(highestPriorityPosition - 1);
                char symbol = augmenetedRegex.charAt(highestPriorityPosition);
                char next = augmenetedRegex.charAt(highestPriorityPosition + 1);

                if (Character.isLetter(prior)) //if the prior position has a character
                {
                    charAtPosition = prior; //get the last char
                    first = new NFA(charAtPosition);                                      //create a new literal NFA
                } else {          //if its a number

                    System.out.println("fetching from table...");
                    int nfaTablePosition = Integer.parseInt(    //pass that reference to nfa
                            augmenetedRegex.substring(highestPriorityPosition - 1, highestPriorityPosition));
                    first = nfaTable.get(nfaTablePosition); //get that nfa in the table;
                }

                if (Character.isLetter(next)) {
                    charAtPosition = next;
                    second = new NFA(charAtPosition);
                } else {
                    int tablePos = Integer.parseInt(augmenetedRegex.substring(highestPriorityPosition + 1, highestPriorityPosition + 2));
                    second = getNFAFromTable(tablePos);
                }

                outNFA = concationation(first, second);

                nfaTable.add(outNFA);   //add to table for later processing
                int posInNFATable = nfaTable.indexOf(outNFA);

                CharSequence toReplace = Character.toString(prior) + Character.toString(symbol) + Character.toString(next);

                System.out.println(toReplace);
                augmenetedRegex = augmenetedRegex.replace(toReplace, Integer.toString(posInNFATable));

                System.out.println("New Aug regex : " + augmenetedRegex);
            }

            //UNION OPERATOR
            if(highestPriorityOperator == '|')
            {
                char charAtPosition = '#';
                NFA first = null;
                NFA second = null;

                char prior = augmenetedRegex.charAt(highestPriorityPosition - 1);
                char symbol = augmenetedRegex.charAt(highestPriorityPosition);
                char next = augmenetedRegex.charAt(highestPriorityPosition + 1);

                if (Character.isLetter(prior)) //if the prior position has a character
                {
                    charAtPosition = prior; //get the last char
                    first = new NFA(charAtPosition);                                      //create a new literal NFA
                } else {          //if its a number

                    System.out.println("fetching from table...");
                    int nfaTablePosition = Integer.parseInt(    //pass that reference to nfa
                            augmenetedRegex.substring(highestPriorityPosition - 1, highestPriorityPosition));
                    first = nfaTable.get(nfaTablePosition); //get that nfa in the table;
                }

                if (Character.isLetter(next)) {
                    charAtPosition = next;
                    second = new NFA(charAtPosition);
                } else {
                    int tablePos = Integer.parseInt(augmenetedRegex.substring(highestPriorityPosition + 1, highestPriorityPosition + 2));
                    second = getNFAFromTable(tablePos);
                }

                outNFA = union(first, second);

                nfaTable.add(outNFA);   //add to table for later processing
                int posInNFATable = nfaTable.indexOf(outNFA);

                CharSequence toReplace = Character.toString(prior) + Character.toString(symbol) + Character.toString(next);

                System.out.println(toReplace);
                augmenetedRegex = augmenetedRegex.replace(toReplace, Integer.toString(posInNFATable));

                System.out.println("New Aug regex : " + augmenetedRegex);
            }

            //OPERATORS====END==============================================

            return (regexToNFA(augmenetedRegex));
        }
    }

    NFA createNFA() {

        NFA out = new NFA();


        boolean flagProcessParans = false;

        for (int i = 0; i < regexToProcess.length(); i++) {
            if (regexToProcess.charAt(i) == '(') {
                int closingParan = i;
                String toProcess = "";       //The regexToProcess part to process

                System.out.println("Finding Paran...");

                while (closingParan < regexToProcess.length() &&
                        regexToProcess.charAt(closingParan) != ')') {
                    toProcess += regexToProcess.charAt(closingParan);
                    closingParan++;
                }
                if (closingParan >= regexToProcess.length()) {
                    System.out.println("ERROR! Unbalanced Closing Parans");
                    break;
                }

                if (regexToProcess.charAt(closingParan) == ')') {
                    System.out.println("Found Closing Paran");
                    toProcess = regexToProcess.substring(i + 1, closingParan);
                    System.out.println("Substring To Process : " + toProcess);

                    String replacedWith = regexToNFA(toProcess);

                    String toBeRemoved = "";        //Remove the processed String

                    toBeRemoved += regexToProcess.charAt(i);
                    toBeRemoved += toProcess;
                    toBeRemoved += regexToProcess.charAt(closingParan);

                    regexToProcess = regexToProcess.replace(toBeRemoved, replacedWith);

                    System.out.println("New Regex To work with : " + regexToProcess);
                }
            } else if (regexToProcess.charAt(i) == '*') {
                System.out.println("Kleene...");

                NFA kleene = new NFA();
                String toRemove = "";
                String newTablePos = "";

                if (Character.isLetter(regexToProcess.charAt(i - 1)))          //if its a character
                {
                    NFA character = new NFA(regexToProcess.charAt(i - 1));
                    kleene = kleeneClosure(character);

                    kleene.printTransitions();
                    nfaTable.add(kleene);
                    newTablePos = String.valueOf(nfaTable.indexOf(kleene));
                } else {
                    System.out.println("Fetching processed...");

                    int tablePos = Integer.parseInt(Character.toString(regexToProcess.charAt(i - 1)));
                    NFA processed = nfaTable.get(tablePos);   //fetch the already processed NFA
                    kleene = kleeneClosure(processed);
                    newTablePos = String.valueOf(tablePos);
                    nfaTable.add(tablePos, kleene);
                }

                toRemove += (regexToProcess.charAt(i - 1) + "*");
                regexToProcess = regexToProcess.replace(toRemove, newTablePos);
            }
        }

        out = nfaTable.get(Integer.parseInt(regexToProcess));
        return out;  //The last bit of information in the regex to parse should be the location of the final regex
    }
}


