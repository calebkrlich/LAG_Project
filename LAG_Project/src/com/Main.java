package com;
import java.util.*;

public class Main {


    public static void main(String[] args)
    {
        //process the in file
        String[] genericFiles = {"GenericLexicalAnalyzer.h","GenericLexicalAnalyzer.cpp"};

        InFileProcessor processor = new InFileProcessor(args[0]);
        List<String> definitions;
        definitions = processor.process();

        //DEBUGGING
        System.out.println("DEFINITIONS FOUND:");
        for(int i = 0; i < definitions.size(); i++)
        {
            System.out.println(definitions.get(i));
        }


        //generate the output files
        FileGenerator generator = new FileGenerator(definitions,args[1],genericFiles);
        generator.generateFiles();



    }
}
