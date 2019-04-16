package com;

import java.io.*;
import java.nio.Buffer;
import java.util.List;

public class FileGenerator
{

    private List<String> defs;
    private String baseName;
    private String[] generics;
    private TokenGenerator tokenGen;

    FileGenerator(List<String> definitions,String nameOutBaseName, String[] fileGenerics)
    {
        defs = definitions;
        baseName = nameOutBaseName;
        generics = fileGenerics;
        tokenGen = new TokenGenerator(defs);
    }

    public boolean generateFiles()
    {
        generateHeaderFile();
        generateSourceFile();
        generateListingFile();
        return true;
    }

    private boolean generateSourceFile()
    {
        try
        {
            BufferedWriter fileWriter = new BufferedWriter(new PrintWriter(baseName + ".cpp"));
            BufferedReader fileReader = new BufferedReader(new FileReader(generics[1]));
            String readLine;

            while((readLine = fileReader.readLine()) != null)
            {
                if(readLine.contains("#include"))
                    fileWriter.write("#include \"" + baseName + ".h" + "\"\n");
                else
                    fileWriter.write(readLine + "\n");
            }

            tokenGen.getTokenMatch();

            fileWriter.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return true;
    }

    private boolean generateHeaderFile()
    {
        try
        {
            BufferedReader fileReader = new BufferedReader(new FileReader(generics[0]));
            BufferedWriter fileWriter = new BufferedWriter(new PrintWriter(baseName + ".h"));

            String readLine;

            //read all lines in generic
            while((readLine = fileReader.readLine()) != null)
            {
                if(readLine.contains("#ifndef _GENERICLEXICALANALYZER_H_"))
                    fileWriter.write("#ifndef _" + baseName.toUpperCase() + "_H_ \n");

                else if(readLine.contains("#define _GENERICLEXICALANALYZER_H_"))
                    fileWriter.write("#define _" + baseName.toUpperCase() + "_H_ \n");


                //Once we find the tokens enum def, place all the tokens names into the enum
                else if(readLine.contains("TOKENS"))
                {
                    List<String> tokenNames = tokenGen.getTokenNames();

                    for(int i = 0; i < tokenNames.size(); i++) {

                        if(i == (tokenNames.size() - 1))
                            fileWriter.write(tokenNames.get(i) + "\n");
                        else
                            fileWriter.write(tokenNames.get(i) + ", \n");
                    }
                }
                else
                    fileWriter.write(readLine + "\n");
                
            }

            fileWriter.close();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return true;
    }

    private boolean generateListingFile()
    {
        return true;
    }
}
