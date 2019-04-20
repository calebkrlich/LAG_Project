package com;

import com.sun.source.tree.Tree;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

class Node
{
    private char id;
    private List<Node> childern = new ArrayList<>();
    private Node parent;

    public Node(Node parent) {this.parent = parent;}
    public char getId() {return id;}
    public List<Node> getChildren() {return childern;}
    public void setId(char id) {this.id = id;}
    public Node getParent() {return parent;}


    public static Node addChildren(Node parent, char id)
    {
        Node node = new Node(parent);
        node.setId(id);
        parent.getChildren().add(node);
        return node;
    }
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
        //buildSyntaxTree();

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

    /*
    TODO: FINISH RECURSIVELY BUILDING TREE
     */
    Node buildSyntaxTree(Node inNode, String augmentedRegex)
    {
        //create the first node
        boolean rootNodeSet = false;
        Node rootNode = new Node(null);

        //pick on the farthest to the right operator
        for(int i = augmentedRegex.length()-1; i > 0; i--)
        {
            if(isAugmentedOp(augmentedRegex.charAt(i)))
            {
                if(!rootNodeSet)
                {
                    rootNode.setId(augmentedRegex.charAt(i));
                    System.out.println("Root node set " + rootNode.getId());
                    rootNodeSet = true;
                }

                //if its a concat
                if(augmentedRegex.charAt(i) == '.')
                {
                    System.out.println("splitting");

                    rootNode.addChildren(rootNode,augmentedRegex.charAt(i-1));
                    rootNode.addChildren(rootNode,augmentedRegex.charAt(i+1));
                }

                else if(augmentedRegex.charAt(i) == '|')
                {
                    rootNode.addChildren(rootNode,'|');
                }
                String remaining  = augmentedRegex.substring(0,i);
                String node = augmentedRegex.substring(i);
                //System.out.println(remaining + "   :    " + node);
            }
        }

        for(Node n : rootNode.getChildren())
        {
            System.out.println(n.getId());
        }
        return inNode;
    }

}
