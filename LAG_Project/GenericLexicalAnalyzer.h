#ifndef _GENERICLEXICALANALYZER_H_
#define _GENERICLEXICALANALYZER_H_
#include "string"
using namespace std;

enum Token
{
//TOKENS GO HERE
};


class LexicalAnalyzer
{
private:
public:
    LexicalAnalyzer(istream &input=cin);
    void start();
    bool next(Token &t, string lexeme);
};

#endif