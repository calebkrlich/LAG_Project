#include "LexicalAnalyzerForCPP.h"

using namespace std;
LexicalAnalyzerForCPP::LexicalAnalyzerForCPP(std::string fileName)
{
    file = fileName;
}

LexicalAnalyzerForCPP::~LexicalAnalyzerForCPP()
{

}

void LexicalAnalyzerForCPP::start()
{

}

bool LexicalAnalyzerForCPP::next(Token &t, std::string &lexeme)
{
    fstream fileOut;
    fileOut.open("test.txt",fstream::in);

    bool tokenFound = false;
    char buffer[100]; //Fix this eventually

    int charsToRead = 2;    //read one char to begin with

    fileOut.seekg(0,fileOut.end);       //go to end
    endOfFileValue = fileOut.tellg();   //fetch distance
    fileOut.seekg(fileReadPos);         //go to read

    while(!tokenFound && endOfFileValue != fileReadPos)
    {
        //read a char
        fileOut.get(buffer,charsToRead);

        //if it is a valid token, break and return a new token an value
        if(buffer[0] == '+' || buffer[0] == '-' || buffer[0] == '=' ||
           buffer[0] == '%' || buffer[0] == '*' || buffer[0] == '/')
        {
            //cout << "Found +" << endl;
            t = OPERATOR;
            lexeme = buffer[0];

            tokenFound = true;
            fileReadPos++;
            return true;
        }

        else if(isalpha(buffer[0]))
        {
            t = LITERAL;
            lexeme = buffer[0];

            charsToRead++;
            return true;
        }
        else if(isdigit(buffer[0]))
        {
            t = LITERAL;
            lexeme = buffer[0];

            fileReadPos++;
            return true;

        }
        else
        {
            cout << "no match" << endl;
            tokenFound = true;
            fileReadPos++;
            return true;
        }


        //else, loop back and search ahead one more char
        //if we can't find a valid token
        //break and return false
    }
    fileOut.close();
    return false;
}
