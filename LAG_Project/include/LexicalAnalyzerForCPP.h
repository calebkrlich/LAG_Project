#ifndef LEXICALANALYZERFORCPP_H
#define LEXICALANALYZERFORCPP_H

#include <fstream>
#include <iostream>
#include <string>

enum Token
{
    IDENTIFER,
    KEYWORD,
    SEPARATOR,
    OPERATOR,
    LITERAL,
    COMMENT
};

class LexicalAnalyzerForCPP
{
    public:
        LexicalAnalyzerForCPP(std::string fileName);
        virtual ~LexicalAnalyzerForCPP();
        void start();
        bool next(Token &t, std::string &lexeme);


    protected:

    private:
        std::string file;
        int fileReadPos = 0;
        int endOfFileValue = 0;

};

#endif // LEXICALANALYZERFORCPP_H
