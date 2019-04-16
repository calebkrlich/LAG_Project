package com;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class InFileProcessor
{
    private String fileToProcess;


    InFileProcessor(String fileName)
    {
        fileToProcess = fileName;
    }

    public List<String> process()
    {
        List<String> linesFetched = new ArrayList<String>();    //hard coded for the win
        try
        {
            String lineRead;
            BufferedReader reader = new BufferedReader(new FileReader(fileToProcess));

            try
            {
                while((lineRead = reader.readLine()) != null) {
                    if (lineRead.contains("class")) {

                        System.out.println("Found class definition");
                        String[] partsOfClass = lineRead.split(" ");
                        String processedClass = "<" + partsOfClass[0] + "," + partsOfClass[1] + "," + partsOfClass[2] + ">";

                        linesFetched.add(processedClass);

                    }
                    if (lineRead.contains("token")) {

                        System.out.println("Found Token definition");               //output that we found a token def
                        String[] partsOfToken = lineRead.split(" ");          //split up the token def
                        lineRead = "<" + partsOfToken[0] + "," + partsOfToken[1] + "," + partsOfToken[2] + ">";  //Create a nice formated token

                        linesFetched.add(lineRead);
                    }

                    if (lineRead.contains("ignore")) {

                        System.out.println("Found ignore definition");
                        String[] partsOfIgnore = lineRead.split(" ");
                        String processedIgnore = "<" + partsOfIgnore[0] + "," + partsOfIgnore[1] + ">";

                        linesFetched.add(processedIgnore);

                    }
                    //linesFetched.add(lineRead);

                }
            }
            catch (IOException e)
            {

            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        return linesFetched;
    }
}
