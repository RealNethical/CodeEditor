/*
 *    CodeEditor - the awesome code editor for Android
 *    Copyright (C) 2020-2021  Rosemoe
 *
 *     This library is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU Lesser General Public
 *     License as published by the Free Software Foundation; either
 *     version 2.1 of the License, or (at your option) any later version.
 *
 *     This library is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public
 *     License along with this library; if not, write to the Free Software
 *     Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301
 *     USA
 *
 *     Please contact Rosemoe by email 2073412493@qq.com if you need
 *     additional information or have any questions
 */
package io.github.rosemoe.editor.langs.html;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.Token;

import java.io.IOException;
import java.io.StringReader;

import io.github.rosemoe.editor.interfaces.CodeAnalyzer;
import io.github.rosemoe.editor.text.TextAnalyzeResult;
import io.github.rosemoe.editor.text.TextAnalyzer;
import io.github.rosemoe.editor.widget.EditorColorScheme;

public class HTMLAnalyzer implements CodeAnalyzer {
    @Override
    public void analyze(CharSequence content, TextAnalyzeResult colors, TextAnalyzer.AnalyzeThread.Delegate delegate) {
        try {
            CodePointCharStream stream = CharStreams.fromReader(new StringReader(content.toString()));
            HTMLLexer lexer = new HTMLLexer(stream);
            Token token = null;
            boolean first = true;
            int lastLine = 1;
            int line = 0, column = 0;
            while (delegate.shouldAnalyze()) {
                token = lexer.nextToken();
                if (token == null) break;
                if (token.getType() == HTMLLexer.EOF) {
                    lastLine = token.getLine() - 1;
                    break;
                }
                line = token.getLine() - 1;
                column = token.getCharPositionInLine();
                lastLine = line;

                switch (token.getType()) {
                    case HTMLLexer.TAG_WHITESPACE:
                        if (first) colors.addNormalIfNull();
                        break;
                    case HTMLLexer.TAG_OPEN:
                    case HTMLLexer.TAG_SLASH:
                    case HTMLLexer.TAG_SLASH_CLOSE:
                    case HTMLLexer.TAG_CLOSE:
                    case HTMLLexer.TAG_EQUALS:
                        colors.addIfNeeded(line, column, EditorColorScheme.OPERATOR);
                        break;
                    case HTMLLexer.TAG_NAME:
                    case HTMLLexer.XML:
                        colors.addIfNeeded(line, column, EditorColorScheme.HTML_TAG);
                        break;
                    case HTMLLexer.CDATA:
                    case HTMLLexer.ATTRIBUTE:
                        colors.addIfNeeded(line, column, EditorColorScheme.ATTRIBUTE_NAME);
                        break;
                    case HTMLLexer.ATTVALUE_VALUE:
                        colors.addIfNeeded(line, column, EditorColorScheme.ATTRIBUTE_VALUE);
                        break;
                    case HTMLLexer.HTML_CONDITIONAL_COMMENT:
                    case HTMLLexer.HTML_COMMENT:
                        colors.addIfNeeded(line, column, EditorColorScheme.COMMENT);
                        break;
                    case HTMLLexer.HTML_TEXT:
                    default:
                        colors.addIfNeeded(line, column, EditorColorScheme.TEXT_NORMAL);
                        break;
                }

                first = false;
            }
            colors.determine(lastLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
