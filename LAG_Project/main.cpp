#include <iostream>
#include "LexicalAnalyzerForCPP.h"
#include "InFileProcessor.h"

using namespace std;

int main(int argc, char *argv[])
{
    InFileProcessor inProcessor("test1.txt");
    inProcessor.process();


    /*
    LexicalAnalyzerForCPP lex("file.txt");
    Token t;
    string s;

    lex.start();

    while(lex.next(t,s))
    {
        cout << "TOKEN <" << t << "," << s << ">" << endl;
    }
    */
    return 0;
}
