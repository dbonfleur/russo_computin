/* Computin.java */
/* Generated By:JavaCC: Do not edit this line. Computin.java */
package RussoComputin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.StringReader;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Computin implements ComputinConstants {
  private int lineCount = 0;
  private int tokenCount = 0;

  public static void main(String[] args) throws ParseException, IOException {
    if (args.length == 0) {
      // Se nenhum arquivo for passado, abre a interface gráfica
      SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
          new ComputinGUI().setVisible(true);
        }
      });
    } else {
      // Lê o arquivo e executa o parser
      String filePath = args[0];
      String input = new String(Files.readAllBytes(Paths.get(filePath)));

      Computin parser = new Computin(new java.io.StringReader(input));
      try {
        parser.start();
        System.out.println("\nComunismo gostou disso camarada!");
        System.out.println("Linhas de c\u00f3digo: " + parser.getLineCount());
        System.out.println("Tokens reconhecidos: " + parser.getTokenCount());
      } catch (ParseException e) {
        System.err.println("\nErro de an\u00e1lise comunista: " + geraMensagemErro(e.currentToken, e.expectedTokenSequences, e.tokenImage));
      } catch (TokenMgrError e) {
        System.err.println("\nErro l\u00e9xico: " + e.getMessage());
      }
    }
  }

  // Função que calcula a distância de Levenshtein entre duas strings
  private static int calculaSimilaridade(String s1, String s2) {
    int[][] dp = new int[s1.length() + 1][s2.length() + 1];
    for (int i = 0; i <= s1.length(); i++) {
      for (int j = 0; j <= s2.length(); j++) {
        if (i == 0) {
          dp[i][j] = j;
        } else if (j == 0) {
          dp[i][j] = i;
        } else {
          dp[i][j] = Math.min(dp[i - 1][j - 1]
                        + (s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1),
                        Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1));
        }
      }
    }
    return dp[s1.length()][s2.length()];
  }

  // Encontra o token mais similar ao token inesperado
  private static String encontraTokenSimilar(String tokenInesperado, String[] tokenImage) {
    String tokenMaisSimilar = null;
    int menorDistancia = Integer.MAX_VALUE;
    for (String token : tokenImage) {
      int distancia = calculaSimilaridade(tokenInesperado, token);
      if (distancia < menorDistancia) {
        menorDistancia = distancia;
        tokenMaisSimilar = token;
      }
    }
    return tokenMaisSimilar;
  }

  public static String geraMensagemErro(Token tokenAtual, int[][] expectedTokenSequences, String[] tokenImage) {
    StringBuilder erro = new StringBuilder();
    erro.append("\nErro comunismo funcionou na linha ")
        .append(tokenAtual.beginLine)
        .append(", coluna ")
        .append(tokenAtual.beginColumn)
        .append(".\n");

    erro.append("Foice inesperado: \"").append(tokenAtual.image).append("\"\n");

    // Verifica se o token esperado é um símbolo importante ausente e gera mensagens apropriadas
    boolean faltaPontoVirgula = false;
    boolean faltaAbreParenteses = false;
    boolean faltaFechaParenteses = false;
    boolean faltaAbreChave = false;
    boolean faltaFechaChave = false;

    // Itera sobre os tokens esperados para verificar ausências comuns
    for (int[] seq : expectedTokenSequences) {
        for (int tokenIndex : seq) {
            String esperado = tokenImage[tokenIndex];
            if (esperado.equals("\";\"")) faltaPontoVirgula = true;
            if (esperado.equals("\"(\"")) faltaAbreParenteses = true;
            if (esperado.equals("\")\"")) faltaFechaParenteses = true;
            if (esperado.equals("\"{\"")) faltaAbreChave = true;
            if (esperado.equals("\"}\"")) faltaFechaChave = true;
        }
    }

    // Adiciona mensagens específicas para tokens faltantes
    if (faltaPontoVirgula) {
        erro.append("Erro de partilha de bens: Comunismo esperava compartilhar um ';' entre os camaradas.\n");
    }
    if (faltaAbreParenteses) {
        erro.append("Erro de partilha de bens: Comunismo esperava de voc\u00ea comunhar um '(' camarada.\n");
    }
    if (faltaFechaParenteses) {
        erro.append("Erro de partilha de bens: Comunismo esperava de voc\u00ea comunhar um ')' camarada.\n");
    }
    if (faltaAbreChave) {
        erro.append("Erro de partilha de bens: Comunismo esperava de voc\u00ea comunhar um '{' camarada.\n");
    }
    if (faltaFechaChave) {
        erro.append("Erro de partilha de bens: Comunismo esperava de voc\u00ea comunhar um '}' camarada.\n");
    }

    if(!faltaPontoVirgula
        && !faltaAbreParenteses
        && !faltaFechaParenteses
        && !faltaAbreChave
        && !faltaFechaChave)
    {
        // Sugestão do token mais próximo
        String tokenSugerido = encontraTokenSimilar(tokenAtual.image, tokenImage);
        if (tokenSugerido != null) {
          erro.append("Martelo esperado: ").append(tokenSugerido).append("\n");
        }

        erro.append(tokenAtual.image).append("\n");
        erro.append(" ".repeat(Math.max(0, tokenAtual.beginColumn - 1)) + "^\n");
    }
    return erro.toString();
  }

  public int getLineCount() {
    return lineCount;
  }

  public int getTokenCount() {
    return tokenCount;
  }

  static final public void start() throws ParseException {
    jj_consume_token(MAIN);
    jj_consume_token(ABREPAREN);
    jj_consume_token(FECHAPAREN);
    jj_consume_token(ABRECHAVE);
    label_1:
    while (true) {
      declaracao();
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case TIPOINT:
      case TIPOFLOAT:
      case TIPOSTRING:
      case IF:
      case WHILE:
      case FOR:
      case ID:{
        ;
        break;
        }
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
    }
    jj_consume_token(FECHACHAVE);
}

  static final public void declaracao() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case TIPOINT:
    case TIPOFLOAT:
    case TIPOSTRING:{
      declaraVariavel();
      break;
      }
    case IF:{
      testeCondicao();
      break;
      }
    case WHILE:{
      lacoWhile();
      break;
      }
    case FOR:{
      lacoFor();
      break;
      }
    case ID:{
      atribuicao();
      break;
      }
    default:
      jj_la1[1] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

  static final public void declaraVariavel() throws ParseException {
    tipoVariavel();
    jj_consume_token(ID);
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case IGUAL:{
      inicializacao();
      break;
      }
    default:
      jj_la1[2] = jj_gen;
      ;
    }
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case VIRGULA:{
        ;
        break;
        }
      default:
        jj_la1[3] = jj_gen;
        break label_2;
      }
      jj_consume_token(VIRGULA);
      jj_consume_token(ID);
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case IGUAL:{
        inicializacao();
        break;
        }
      default:
        jj_la1[4] = jj_gen;
        ;
      }
    }
    jj_consume_token(PONTOVIRGULA);
}

  static final public void tipoVariavel() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case TIPOINT:{
      jj_consume_token(TIPOINT);
      break;
      }
    case TIPOFLOAT:{
      jj_consume_token(TIPOFLOAT);
      break;
      }
    case TIPOSTRING:{
      jj_consume_token(TIPOSTRING);
      break;
      }
    default:
      jj_la1[5] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

  static final public void inicializacao() throws ParseException {
    jj_consume_token(IGUAL);
    expressao();
}

  static final public void expCondicionalUnica() throws ParseException {
    termo();
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case EQ:{
      jj_consume_token(EQ);
      break;
      }
    case NOT:{
      jj_consume_token(NOT);
      jj_consume_token(IGUAL);
      break;
      }
    case MENOR:{
      jj_consume_token(MENOR);
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case IGUAL:{
        jj_consume_token(IGUAL);
        break;
        }
      default:
        jj_la1[6] = jj_gen;
        ;
      }
      break;
      }
    case MAIOR:{
      jj_consume_token(MAIOR);
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case IGUAL:{
        jj_consume_token(IGUAL);
        break;
        }
      default:
        jj_la1[7] = jj_gen;
        ;
      }
      break;
      }
    default:
      jj_la1[8] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    termo();
}

// Estrutura condiciona do if
  static final public void expCondicional() throws ParseException {
    expCondicionalUnica();
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case AND:
      case OR:{
        ;
        break;
        }
      default:
        jj_la1[9] = jj_gen;
        break label_3;
      }
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case AND:{
        jj_consume_token(AND);
        break;
        }
      case OR:{
        jj_consume_token(OR);
        break;
        }
      default:
        jj_la1[10] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      expCondicionalUnica();
    }
}

  static final public void atribuicao() throws ParseException {
    jj_consume_token(ID);
    jj_consume_token(IGUAL);
    expressao();
    jj_consume_token(PONTOVIRGULA);
}

  static final public void expressao() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case ID:
    case EXPRESSAO_INT:
    case EXPRESSAO_FLOAT:
    case EXPRESSAO_STRING:{
      termo();
      break;
      }
    default:
      jj_la1[11] = jj_gen;
      ;
    }
    label_4:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case MAIS:
      case MENOS:
      case MULT:
      case DIVIS:{
        ;
        break;
        }
      default:
        jj_la1[12] = jj_gen;
        break label_4;
      }
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case MAIS:{
        jj_consume_token(MAIS);
        break;
        }
      case MENOS:{
        jj_consume_token(MENOS);
        break;
        }
      case MULT:{
        jj_consume_token(MULT);
        break;
        }
      case DIVIS:{
        jj_consume_token(DIVIS);
        break;
        }
      default:
        jj_la1[13] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      termo();
    }
}

  static final public void termo() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case EXPRESSAO_INT:{
      jj_consume_token(EXPRESSAO_INT);
      break;
      }
    case EXPRESSAO_FLOAT:{
      jj_consume_token(EXPRESSAO_FLOAT);
      break;
      }
    case EXPRESSAO_STRING:{
      jj_consume_token(EXPRESSAO_STRING);
      break;
      }
    case ID:{
      jj_consume_token(ID);
      break;
      }
    default:
      jj_la1[14] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

  static final public void testeCondicao() throws ParseException {
    jj_consume_token(IF);
    jj_consume_token(ABREPAREN);
    expCondicional();
    jj_consume_token(FECHAPAREN);
    jj_consume_token(ABRECHAVE);
    label_5:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case TIPOINT:
      case TIPOFLOAT:
      case TIPOSTRING:
      case IF:
      case WHILE:
      case FOR:
      case ID:{
        ;
        break;
        }
      default:
        jj_la1[15] = jj_gen;
        break label_5;
      }
      declaracao();
    }
    jj_consume_token(FECHACHAVE);
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case ELSE:{
      jj_consume_token(ELSE);
      jj_consume_token(ABRECHAVE);
      label_6:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case TIPOINT:
        case TIPOFLOAT:
        case TIPOSTRING:
        case IF:
        case WHILE:
        case FOR:
        case ID:{
          ;
          break;
          }
        default:
          jj_la1[16] = jj_gen;
          break label_6;
        }
        declaracao();
      }
      jj_consume_token(FECHACHAVE);
      break;
      }
    default:
      jj_la1[17] = jj_gen;
      ;
    }
}

  static final public void lacoWhile() throws ParseException {
    jj_consume_token(WHILE);
    jj_consume_token(ABREPAREN);
    expCondicional();
    jj_consume_token(FECHAPAREN);
    jj_consume_token(ABRECHAVE);
    label_7:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case TIPOINT:
      case TIPOFLOAT:
      case TIPOSTRING:
      case IF:
      case WHILE:
      case FOR:
      case ID:{
        ;
        break;
        }
      default:
        jj_la1[18] = jj_gen;
        break label_7;
      }
      declaracao();
    }
    jj_consume_token(FECHACHAVE);
}

  static final public void lacoFor() throws ParseException {
    jj_consume_token(FOR);
    jj_consume_token(ABREPAREN);
    jj_consume_token(ID);
    jj_consume_token(IGUAL);
    jj_consume_token(EXPRESSAO_INT);
    jj_consume_token(PONTOVIRGULA);
    expCondicionalUnica();
    jj_consume_token(PONTOVIRGULA);
    jj_consume_token(ID);
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case MAIS:{
      jj_consume_token(MAIS);
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case MAIS:{
        jj_consume_token(MAIS);
        break;
        }
      case IGUAL:{
        jj_consume_token(IGUAL);
        jj_consume_token(EXPRESSAO_INT);
        break;
        }
      default:
        jj_la1[19] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      break;
      }
    case MENOS:{
      jj_consume_token(MENOS);
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case MENOS:{
        jj_consume_token(MENOS);
        break;
        }
      case IGUAL:{
        jj_consume_token(IGUAL);
        jj_consume_token(EXPRESSAO_INT);
        break;
        }
      default:
        jj_la1[20] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      break;
      }
    default:
      jj_la1[21] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    jj_consume_token(FECHAPAREN);
    jj_consume_token(ABRECHAVE);
    label_8:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case TIPOINT:
      case TIPOFLOAT:
      case TIPOSTRING:
      case IF:
      case WHILE:
      case FOR:
      case ID:{
        ;
        break;
        }
      default:
        jj_la1[22] = jj_gen;
        break label_8;
      }
      declaracao();
    }
    jj_consume_token(FECHACHAVE);
}

// Método de sincronização do pânico
  static final public void panicMode() throws ParseException {
    label_9:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case PONTOVIRGULA:
      case ABREPAREN:
      case FECHAPAREN:
      case ABRECHAVE:
      case FECHACHAVE:
      case 34:{
        ;
        break;
        }
      default:
        jj_la1[23] = jj_gen;
        break label_9;
      }
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case PONTOVIRGULA:{
        jj_consume_token(PONTOVIRGULA);
        break;
        }
      case ABRECHAVE:{
        jj_consume_token(ABRECHAVE);
        break;
        }
      case FECHACHAVE:{
        jj_consume_token(FECHACHAVE);
        break;
        }
      case ABREPAREN:{
        jj_consume_token(ABREPAREN);
        break;
        }
      case FECHAPAREN:{
        jj_consume_token(FECHAPAREN);
        break;
        }
      case 34:{
        jj_consume_token(34);
        break;
        }
      default:
        jj_la1[24] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
}

  static private boolean jj_initialized_once = false;
  /** Generated Token Manager. */
  static public ComputinTokenManager token_source;
  static SimpleCharStream jj_input_stream;
  /** Current token. */
  static public Token token;
  /** Next token. */
  static public Token jj_nt;
  static private int jj_ntk;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[25];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
	   jj_la1_init_0();
	   jj_la1_init_1();
	}
	private static void jj_la1_init_0() {
	   jj_la1_0 = new int[] {0x40001bc0,0x40001bc0,0x20000,0x2000000,0x20000,0x1c0,0x20000,0x20000,0x9c0000,0x600000,0x600000,0xc0000000,0x1e000,0x1e000,0xc0000000,0x40001bc0,0x40001bc0,0x400,0x40001bc0,0x22000,0x24000,0x6000,0x40001bc0,0x3d000000,0x3d000000,};
	}
	private static void jj_la1_init_1() {
	   jj_la1_1 = new int[] {0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x3,0x0,0x0,0x3,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x4,0x4,};
	}

  /** Constructor with InputStream. */
  public Computin(java.io.InputStream stream) {
	  this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public Computin(java.io.InputStream stream, String encoding) {
	 if (jj_initialized_once) {
	   System.out.println("ERROR: Second call to constructor of static parser.  ");
	   System.out.println("	   You must either use ReInit() or set the JavaCC option STATIC to false");
	   System.out.println("	   during parser generation.");
	   throw new Error();
	 }
	 jj_initialized_once = true;
	 try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	 token_source = new ComputinTokenManager(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 25; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream) {
	  ReInit(stream, null);
  }
  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream, String encoding) {
	 try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	 token_source.ReInit(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 25; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public Computin(java.io.Reader stream) {
	 if (jj_initialized_once) {
	   System.out.println("ERROR: Second call to constructor of static parser. ");
	   System.out.println("	   You must either use ReInit() or set the JavaCC option STATIC to false");
	   System.out.println("	   during parser generation.");
	   throw new Error();
	 }
	 jj_initialized_once = true;
	 jj_input_stream = new SimpleCharStream(stream, 1, 1);
	 token_source = new ComputinTokenManager(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 25; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.Reader stream) {
	if (jj_input_stream == null) {
	   jj_input_stream = new SimpleCharStream(stream, 1, 1);
	} else {
	   jj_input_stream.ReInit(stream, 1, 1);
	}
	if (token_source == null) {
 token_source = new ComputinTokenManager(jj_input_stream);
	}

	 token_source.ReInit(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 25; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public Computin(ComputinTokenManager tm) {
	 if (jj_initialized_once) {
	   System.out.println("ERROR: Second call to constructor of static parser. ");
	   System.out.println("	   You must either use ReInit() or set the JavaCC option STATIC to false");
	   System.out.println("	   during parser generation.");
	   throw new Error();
	 }
	 jj_initialized_once = true;
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 25; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(ComputinTokenManager tm) {
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 25; i++) jj_la1[i] = -1;
  }

  static private Token jj_consume_token(int kind) throws ParseException {
	 Token oldToken;
	 if ((oldToken = token).next != null) token = token.next;
	 else token = token.next = token_source.getNextToken();
	 jj_ntk = -1;
	 if (token.kind == kind) {
	   jj_gen++;
	   return token;
	 }
	 token = oldToken;
	 jj_kind = kind;
	 throw generateParseException();
  }


/** Get the next Token. */
  static final public Token getNextToken() {
	 if (token.next != null) token = token.next;
	 else token = token.next = token_source.getNextToken();
	 jj_ntk = -1;
	 jj_gen++;
	 return token;
  }

/** Get the specific Token. */
  static final public Token getToken(int index) {
	 Token t = token;
	 for (int i = 0; i < index; i++) {
	   if (t.next != null) t = t.next;
	   else t = t.next = token_source.getNextToken();
	 }
	 return t;
  }

  static private int jj_ntk_f() {
	 if ((jj_nt=token.next) == null)
	   return (jj_ntk = (token.next=token_source.getNextToken()).kind);
	 else
	   return (jj_ntk = jj_nt.kind);
  }

  static private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  static private int[] jj_expentry;
  static private int jj_kind = -1;

  /** Generate ParseException. */
  static public ParseException generateParseException() {
	 jj_expentries.clear();
	 boolean[] la1tokens = new boolean[35];
	 if (jj_kind >= 0) {
	   la1tokens[jj_kind] = true;
	   jj_kind = -1;
	 }
	 for (int i = 0; i < 25; i++) {
	   if (jj_la1[i] == jj_gen) {
		 for (int j = 0; j < 32; j++) {
		   if ((jj_la1_0[i] & (1<<j)) != 0) {
			 la1tokens[j] = true;
		   }
		   if ((jj_la1_1[i] & (1<<j)) != 0) {
			 la1tokens[32+j] = true;
		   }
		 }
	   }
	 }
	 for (int i = 0; i < 35; i++) {
	   if (la1tokens[i]) {
		 jj_expentry = new int[1];
		 jj_expentry[0] = i;
		 jj_expentries.add(jj_expentry);
	   }
	 }
	 int[][] exptokseq = new int[jj_expentries.size()][];
	 for (int i = 0; i < jj_expentries.size(); i++) {
	   exptokseq[i] = jj_expentries.get(i);
	 }
	 return new ParseException(token, exptokseq, tokenImage);
  }

  static private boolean trace_enabled;

/** Trace enabled. */
  static final public boolean trace_enabled() {
	 return trace_enabled;
  }

  /** Enable tracing. */
  static final public void enable_tracing() {
  }

  /** Disable tracing. */
  static final public void disable_tracing() {
  }

}

// Interface gráfica
class ComputinGUI extends JFrame {
  private JTextPane codeArea;
  private JTextArea outputArea;
  private JButton parseButton;

  public ComputinGUI() {
    setTitle("RussoComputin - Interface de Intera\u00e7\u00e3o");
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Área de código com números de linha
    codeArea = new JTextPane();
    JScrollPane codeScrollPane = new JScrollPane(codeArea);
    codeScrollPane.setRowHeaderView(new LineNumberView(codeArea));

    outputArea = new JTextArea();
    parseButton = new JButton("Analisar");

    // Atualiza os números de linha em tempo real
    codeArea.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        updateLineNumbers();
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        updateLineNumbers();
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        updateLineNumbers();
      }

      private void updateLineNumbers() {
        codeScrollPane.getRowHeader().repaint();
      }
    });

    parseButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String input = codeArea.getText();
        Computin parser = new Computin(new StringReader(input));
        try {
          parser.start();
          outputArea.setText("Comunismo gostou disso camarada!\n");
          outputArea.append("Linhas de c\u00f3digo: " + parser.getLineCount() + "\n");
          outputArea.append("Tokens reconhecidos: " + parser.getTokenCount() + "\n");
        } catch (ParseException ex) {
          outputArea.setText("Erro de an\u00e1lise comunista: " + geraMensagemErro(ex.currentToken, ex.expectedTokenSequences, ex.tokenImage));
        } catch (TokenMgrError ex) {
          outputArea.setText("Erro l\u00e9xico: " + ex.getMessage());
        }
      }
    });

    JPanel panel = new JPanel(new BorderLayout());
    panel.add(codeScrollPane, BorderLayout.CENTER);
    panel.add(parseButton, BorderLayout.SOUTH);

    JPanel outputPanel = new JPanel(new BorderLayout());
    outputPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);

    JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panel, outputPanel);
    splitPane.setResizeWeight(0.7);

    add(splitPane);
  }

  // Classe para exibir números de linha
  private class LineNumberView extends JComponent {
    private final JTextPane textPane;

    public LineNumberView(JTextPane textPane) {
      this.textPane = textPane;
      setPreferredSize(new Dimension(30, 0));
    }

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      int lineHeight = textPane.getFontMetrics(textPane.getFont()).getHeight();
      int startOffset = textPane.getDocument().getDefaultRootElement().getElement(0).getStartOffset();
      int endOffset = textPane.getDocument().getDefaultRootElement().getElement(textPane.getDocument().getDefaultRootElement().getElementCount() - 1).getEndOffset();
      int startLine = textPane.getDocument().getDefaultRootElement().getElementIndex(startOffset);
      int endLine = textPane.getDocument().getDefaultRootElement().getElementIndex(endOffset);

      for (int i = startLine; i <= endLine; i++) {
        String lineNumber = String.valueOf(i + 1);
        int x = getWidth() - g.getFontMetrics().stringWidth(lineNumber) - 5;
        int y = (i - startLine) * lineHeight + lineHeight - 5;
        g.drawString(lineNumber, x, y);
      }
    }
  }
}
