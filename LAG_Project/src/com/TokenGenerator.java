package com;

import java.util.ArrayList;
import java.util.List;

public class TokenGenerator
{
    private List<String> defintions;

    TokenGenerator(List<String> defintions)
    {
        this.defintions = defintions;
    }

    public List<String> getTokenNames()
    {
        List<String> tokensCreated = new ArrayList<>();

        for(int i = 0; i < defintions.size(); i++)
        {
            if(defintions.get(i).contains("token"))
            {
                String[] parts = defintions.get(i).split(",");
                tokensCreated.add(parts[1]);
            }
        }
        return tokensCreated;
    }


    public List<String> getTokenMatch()
    {
        List<String> tokenMatchDefinitions = new ArrayList<>();

        System.out.println("\n Token Regexs");

        for(int i = 0; i < defintions.size(); i++)
        {
            if(defintions.get(i).contains("token"))
            {
                String[] parts = defintions.get(i).split(",");
                String[] regex = parts[2].split(">");
                System.out.println(regex[0]);
            }
        }
        return tokenMatchDefinitions;
    }
}
