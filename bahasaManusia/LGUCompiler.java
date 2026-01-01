package bahasaManusia;

import bahasaManusia.lexer.*;
import bahasaManusia.node.*;
import java.io.*;

enum Language {
    JAPANESE,
    MELAYU,
    JAVANESE,
    HIRAGANA,
    UNKNOWN
}

public class LGUCompiler {

    static Lexer lexer;

    public static void main(String[] args) {

        if (args.length == 0) {
            System.err.println("Error: Please provide one of these options: japanese, melayu, or javanese.");
            return;
        }

        String option = args[0].toLowerCase();
        String fileName;

if (option.equals("japanese")) {
            fileName = "JapaneseCompiler.source";  
        } else if (option.equals("hiragana")) {
            fileName = "HiraganaCompiler.source";
        } else if (option.equals("melayu")) {
            fileName = "MelayuCompiler.source";
        } else if (option.equals("javanese")) {
            fileName = "JavaneseCompiler.source"; 
        } else {
            System.err.println("Invalid option. Use: japanese, hiragana, melayu, or javanese.");
            return;
        }

        try {
            lexer = new Lexer(
                    new PushbackReader(new FileReader(fileName), 1024));

            int tokenCount = 0;
            Token token;

            while (true) {
                token = lexer.next();

                if (token instanceof EOF) {
                    break;
                }

                if (token instanceof TBlank ||
                        token instanceof TComment1 ||
                        token instanceof TComment2 ||
                        isUnclassified(token)) {
                    continue;
                }

                tokenCount++;
                String tokenType = classifyToken(token);

                System.out.printf(
                        "[%3d] %-22s %s%n",
                        tokenCount,
                        tokenType + ":",
                        token.getText());
            }

            System.out.println("\n✅ Lexical analysis completed successfully!");

        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found - " + fileName);
        } catch (LexerException e) {
            System.err.println("Lexer Error: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IO Error: " + e.getMessage());
        }
    }

    // Identify Tokens
    private static String classifyToken(Token token) {

        // Keywords
        if (token instanceof TClas)
            return "Keyword class";
        if (token instanceof TPublic)
            return "Keyword public";
        if (token instanceof TStatic)
            return "Keyword static";
        if (token instanceof TVoid)
            return "Keyword void";
        if (token instanceof TFor)
            return "Keyword for";
        if (token instanceof TDo)
            return "Keyword do";
        if (token instanceof TWhile)
            return "Keyword while";
        if (token instanceof TIf)
            return "Keyword if";
        if (token instanceof TElse)
            return "Keyword else";
        if (token instanceof TMain)
            return "Keyword main";
        if (token instanceof TInt)
            return "Keyword int";
        if (token instanceof TFloat)
            return "Keyword float";
        if (token instanceof TString)
            return "Keyword String";

        // Operators
        if (token instanceof TAssign)
            return "Operator =";
        if (token instanceof TCompare)
            return "Operator comparison";
        if (token instanceof TPlus)
            return "Operator +";
        if (token instanceof TMinus)
            return "Operator -";
        if (token instanceof TMult)
            return "Operator *";
        if (token instanceof TDiv)
            return "Operator /";

        // Separators
        if (token instanceof TLPar)
            return "Separator (";
        if (token instanceof TRPar)
            return "Separator )";
        if (token instanceof TLBrace)
            return "Separator {";
        if (token instanceof TRBrace)
            return "Separator }";
        if (token instanceof TLBracket)
            return "Separator [";
        if (token instanceof TRBracket)
            return "Separator ]";
        if (token instanceof TSemi)
            return "Separator ;";
        if (token instanceof TColon)
            return "Separator :";
        if (token instanceof TComma)
            return "Separator ,";

        // Identifier
        if (token instanceof TIdent)
            return "Identifier";

        // Literals
        if (token instanceof TIntLiteral)
            return "Integer Literal";
        if (token instanceof TFloatLiteral)
            return "Float Literal";

        // Unknown
        if (token instanceof TUnknown)
            return "Unknown";

        return null;
    }

    private static boolean isUnclassified(Token token) {
        return classifyToken(token) == null;
    }

    private static Language detectLanguage(String fileName) throws IOException, LexerException {

        Lexer detectLexer = new Lexer(
                new PushbackReader(new FileReader(fileName), 1024));

        int japanese = 0; // Romaji
        int hiragana = 0; // Hiragana script
        int melayu = 0;
        int javanese = 0;

        Token token;

        while (true) {
            token = detectLexer.next();

            if (token instanceof EOF)
                break;

            String text = token.getText();

            // Japanese (Romaji) keywords
            if (text.equals("kurasu") || text.equals("koukai")
                    || text.equals("seiteki") || text.equals("muko")
                    || text.equals("moshi") || text.equals("aida")
                    || text.equals("mein") || text.equals("seisu")
                    || text.equals("fudo") || text.equals("mojiretsu")) {
                japanese++;
            }

            // Japanese (Hiragana) keywords
            else if (text.equals("くらす") || text.equals("こうかい")
                    || text.equals("せいてき") || text.equals("むこう")
                    || text.equals("もし") || text.equals("あいだ")
                    || text.equals("めいん") || text.equals("せいすう")
                    || text.equals("ふどう") || text.equals("もじれつ")
                    || text.equals("くりかえし") || text.equals("じっこう")
                    || text.equals("ほかに")) {
                hiragana++;
            }

            // Melayu keywords
            else if (text.equals("darjah") || text.equals("umum")
                    || text.equals("pegun") || text.equals("gelap")
                    || text.equals("jika") || text.equals("apabila")
                    || text.equals("utama") || text.equals("angka")
                    || text.equals("ambang") || text.equals("ayat")) {
                melayu++;
            }

            // Javanese keywords
            else if (text.equals("klas") || text.equals("awam")
                    || text.equals("tetep") || text.equals("kosong")
                    || text.equals("yen") || text.equals("nalika")
                    || text.equals("utama") || text.equals("nomborbulat")
                    || text.equals("perpuluhan") || text.equals("tulisan")) {
                javanese++;
            }
        }

        // Logic to determine the language
        // Combining Romaji and Hiragana into one Japanese category
        int totalJapanese = japanese + hiragana;

        if (totalJapanese > melayu && totalJapanese > javanese)
            return Language.JAPANESE;

        if (melayu > totalJapanese && melayu > javanese)
            return Language.MELAYU;

        if (javanese > totalJapanese && javanese > melayu)
            return Language.JAVANESE;

        return Language.UNKNOWN;
    }

}

