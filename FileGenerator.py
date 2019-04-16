

class FileGenerator:
    
    baseName = ""
    headerFile = None
    sourceFile = None
    listingFile = None

    def __init__(self, baseOutputNames):
        self.baseName = baseOutputNames
        self.generateFiles()
        self.populateHeader()

    
    def generateFiles(self):
        self.headerFile = open((self.baseName + ".h"),"w")
        self.sourceFile = open((self.baseName + ".cpp"),"w")
        self.listingFile = open((self.baseName + ".txt"),"w") 

    def populateHeader(self):
        self.headerFile.write("#ifndef _" + self.baseName.upper() + "_H_" + "\n")
        self.headerFile.write("#define _" + self.baseName.upper() + "_H_" + "\n")
        self.headerFile.write("#include <iostream> \n" + "using namespace std; \n")
        self.headerFile.write("enum Token { \n")
        
        #RUN FUNCTION TO GENERATE TOKENS
        self.headerFile.write("//TOKENS GO HERE \n")

        self.headerFile.write("};\n")
        self.headerFile.write("class LexicalAnalyzer\n {\n")
        self.headerFile.write("public:\n LexicalAnalyzer(istream &input=cin);\n")
        self.headerFile.write(" void start();\n bool next(Token &t, string &lexeme);\n")
        self.headerFile.write("};\n")
        self.headerFile.write("#endif")


    def populateSource(self):
        #
        print()
    def populateListing(self):
        #
        print()
