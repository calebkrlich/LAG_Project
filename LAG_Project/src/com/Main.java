package com;

import java.util.ArrayList;
import java.util.List;

public class Main {


    public static void main(String[] args)
    {
        String inputFile = "";
        String outputFileName = "";

        String[] fileGenerics = {"GenericLexicalAnalyzer.h","GenericLexicalAnalyzer.cpp"};

        //Set the file in and outputs
        if(args.length == 2)
        {
            inputFile = args[0];
            outputFileName = args[1];
        }

        //testing regexs
        String regexOne = "(a+cd)";
        String regexTwo = "(abcd)*";
        String regexThree = "(ab|c)*";
        String regexFinal = "(ab|c)|(efd)";


        //process the input file, extracting tokens, classes and ignores
        InFileProcessor fileProcessor = new InFileProcessor(inputFile);
        List<String> defs = fileProcessor.process();

        FileGenerator fileGenerator = new FileGenerator(defs,outputFileName,fileGenerics);
        fileGenerator.generateFiles();

        //NFAGenerator gen = new NFAGenerator(regexOne);
        //NFA finalNFA = gen.createNFA();

        //System.out.println("FINAL NFA");
        //finalNFA.printTransitions();
    }

}
