package com;

import com.sun.source.tree.Tree;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/*
class Node
{
    private char id;
    private Node leftChild;
    private Node rightChild;
    private Node parent;

    public Node(Node parent)
    {
        this.parent = parent;
        this.leftChild = null;
        this.rightChild = null;
    }
    public char getId() {return id;}
    public Node getParent() {return parent;}
    public Node getLeftChild() {return leftChild;}
    public Node getRightChild() {return rightChild;}


    public void setId(char id) {this.id = id;}
    public void setParent(Node parent) {this.parent = parent;}
    public void setLeftChild(Node left) {this.leftChild = left;}
    public void setRightChild(Node right) {this.rightChild = right;}

}


public class DFAGenerator
{
    String regex;
    CharSequence regexOps = "()+*?|";
    CharSequence augmentedOperator = "()|+*?.";


    DFAGenerator(String regex) {this.regex = regex;}

    public DFA create()
    {
        DFA output = new DFA();

        String augmented = augementRegex();
        Node syntaxTree = buildSyntaxTree(augmented);

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

    Node buildSyntaxTree(String augmentedRegex)
    {
        Node root = new Node(null);

        for(int i = 0; i < augmentedRegex.length(); i++) {

            //if the char is a concat or OR (. or |)
            if (augmentedRegex.charAt(i) == '.' ||
                    augmentedRegex.charAt(i) == '|')
            {
                if (i > 0 && (Character.isLetter(augmentedRegex.charAt(i - 1)) || isRegexOp(augmentedRegex.charAt(i-1))))  //change to accept classes from input
                {
                    System.out.println("Valid");

                    Node newParent = new Node(null);
                    newParent.setId(augmentedRegex.charAt(i));

                    Node leftNode = new Node(newParent);
                    leftNode.setId(augmentedRegex.charAt(i - 1));

                    Node rightNode = new Node(newParent);
                    rightNode.setId(augmentedRegex.charAt(i + 1));

                    newParent.setLeftChild(leftNode);
                    newParent.setRightChild(rightNode);
                    root = newParent;
                }
                //invalid regex
                else {
                    System.out.println("REGEX ERROR: NOT VALID REGEX");
                    break;
                }
            }

            else if (augmentedRegex.charAt(i) == '*' ||
                        augmentedRegex.charAt(i) == '+' ||
                        augmentedRegex.charAt(i) == '?')
            {
                Node newParent = new Node(null);
                newParent.setId(augmentedRegex.charAt(i));

                Node newChild = new Node(newParent);
                root.setParent(newParent);

                newChild.setLeftChild(newChild);

                root = newParent;

            }
            else
            {
                System.out.println("Found ID, continuing");
            }

        }
        return root;
    }

}
