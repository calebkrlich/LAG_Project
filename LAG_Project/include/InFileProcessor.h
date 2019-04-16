#ifndef INFILEPROCESSOR_H
#define INFILEPROCESSOR_H

#include <string>
#include <stdio.h>
#include <iostream>
#include <fstream>

class InFileProcessor
{
    public:
        InFileProcessor(char inFile[100]);
        virtual ~InFileProcessor();
        void process();

    protected:

    private:
        char fileToProcess[100];
};

#endif // INFILEPROCESSOR_H
