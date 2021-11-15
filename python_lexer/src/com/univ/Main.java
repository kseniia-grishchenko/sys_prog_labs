package com.univ;

import com.univ.lexer.Lexer;
import com.univ.lexer.token.Token;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Please, pass a file to the program");
        }

        Lexer lexer = new Lexer();
        try {
            List<Token> tokens = lexer.tokenize(args[0]);
            for (Token token : tokens) {
                System.out.println(token.toString());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
