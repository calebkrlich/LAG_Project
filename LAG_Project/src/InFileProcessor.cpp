#include "InFileProcessor.h"

#include <stdio.h>
#include <string.h>
InFileProcessor::InFileProcessor(char inFile[100])
{
    strcpy(fileToProcess,inFile);
}

InFileProcessor::~InFileProcessor()
{

}

void InFileProcessor::process()
{
    std::fstream file;//(fileToProcess,std::fstream::out|std::fstream::in);
    file.open(fileToProcess,std::fstream::in);
    file.close();
}
