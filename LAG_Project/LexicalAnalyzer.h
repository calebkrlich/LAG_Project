#ifndef _LEXICALANALYZER_H_ 
#define _LEXICALANALYZER_H_ 
#include "string"
using namespace std;

enum Token
{
KEYWORD, 
CONST
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
