package com;

public class Main {


    public static void main(String[] args)
    {
        int unionPriority = 0;
        int concatPriority = 1;
        int closurePriority = 2;


        String regexOne = "(a*b)";
        String regexTwo = "(abcd)*";
        String regexThree = "(ab|c)*";
        String regexFinal = "(ab|c)|(efd)";

        NFAGenerator gen = new NFAGenerator(regexOne);
        NFA finalNFA = gen.createNFA();

        System.out.println("FINAL NFA");
        finalNFA.printTransitions();
    }

}
