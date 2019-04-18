package com;

import java.util.ArrayList;



public class DFAGenerator
{
    String regex;
    CharSequence regexOps = "()+*?|";
    CharSequence augmentedOperator = "()|+*?.#";


    DFAGenerator(String regex) {this.regex = regex;}

    public DFA create()
    {
        DFA output = new DFA();

        String augmented = augementRegex();
        buildSyntaxTree(augmented);

        return output;
    }

    boolean isRegexOp(char c)
    {
        for(int i = 0; i < regexOps.length(); i++)
        {
            if(regexOps.charAt(i) == c)
                return true;
        }
        return false;
    }

    boolean isAugmentedOp(char c)
    {
        for(int i = 0; i < augmentedOperator.length(); i++)
        {
            if(augmentedOperator.charAt(i) == c)
                return true;
        }
        return false;
    }



    String augementRegex()
    {
        String augmentedRegex = new String();
        boolean concatFlag = false;

        for(int i = 0; i < regex.length(); i++)
        {
            //if this is the first non regex op we see
            if(!isRegexOp(regex.charAt(i)))
            {
                if(concatFlag) {
                    augmentedRegex += ".";
                }
                else {
                    concatFlag = true;
                }
                augmentedRegex += regex.charAt(i);
            }
            else {
                augmentedRegex += regex.charAt(i);
                concatFlag = false;
                if(regex.charAt(i) == '*' || regex.charAt(i) == '+' || regex.charAt(i) == '?')
                    concatFlag = true;

            }
        }

        augmentedRegex+=".#";

        System.out.println("Augmented Regex : " + augmentedRegex);
        return augmentedRegex;
    }

    String buildSyntaxTree(String augmentedRegex)
    {

        //pick on the farthest to the right operator

        for(int i = augmentedRegex.length()-1; i > 0; i--)
        {
            if(isAugmentedOp(augmentedRegex.charAt(i)))
            {
                String remaining  = augmentedRegex.substring(0,i);
                String node = augmentedRegex.substring(i);
                System.out.println(remaining + "   :    " + node);
            }
        }

        return "Yes";
    }

}
