#ifndef INFILEPROCESSOR_H
#define INFILEPROCESSOR_H

#include "string"

class InFileProcessor
{
    public:
        InFileProcessor(std::string inFile);
        virtual ~InFileProcessor();
        void process();

    protected:

    private:
        std::string fileToProcess;
};

#endif // INFILEPROCESSOR_H
