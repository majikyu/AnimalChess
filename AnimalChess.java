import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.lang.Object;
import java.nio.charset.Charset;
import java.io.*;
public class AnimalChess{
    private static StringBuffer Kifu;

    public static void main(String[] args){
        Pieceboard board = new Pieceboard();
        System.out.print("1:対局開始 2:棋譜読み込み>");
        int MorR = new java.util.Scanner(System.in).nextInt();
        if(MorR == 2){
            ReadAcr(board);
        }else{
            makeAcrHeader();
            System.out.print("1:対人戦 2:対CPU戦>");
            int isCpu = new java.util.Scanner(System.in).nextInt();
            System.out.println("動物将棋スタート!"); 
            board.printstate();
            if(isCpu == 2){

                vsCpu(board);
            }else{
                match(board);
            }
            System.out.println("動物将棋エンド!");
        }
    }

    static void match(Pieceboard board){
        while(true){
        System.out.println("先手番です。");
        Mymove(board);
        if(board.getFin() == true){
            System.out.println("先手の勝ちです。");
            writeAcr();
            break;
            }
        if(board.getSur() == true){
            System.out.println("後手の勝ちです。");
            writeAcr();
            break;
            } 
        System.out.println("後手番です。");        
        Yourmove(board);
        if(board.getFin() == true){
            System.out.println("後手の勝ちです。");
            writeAcr();
            break;
            }
        if(board.getSur() == true){
            System.out.println("先手の勝ちです。");
            writeAcr();
            break;
            }
        }
    }

    static void vsCpu(Pieceboard board){
        while(true){
            System.out.println("先手番です。");
            Mymove(board);
            if(board.getFin() == true){
                System.out.println("先手の勝ちです。");
                writeAcr();
                break;
                }
            if(board.getSur() == true){
                System.out.println("後手の勝ちです。");
                writeAcr();
                break;
                } 
            System.out.println("後手番です。");        
            Cpumove(board);
            if(board.getFin() == true){
                System.out.println("後手の勝ちです。");
                writeAcr();
                break;
                }
            if(board.getSur() == true){
                System.out.println("先手の勝ちです。");
                writeAcr();
                break;
                }
            }
    }

    static void Mymove(Pieceboard board){
        String move = getmove(board);
        char[] moves = move.toCharArray(); //char[]に変換
            while(board.movefirst(moves[0]-49,moves[1]-65,moves[2]-49,moves[3]-65) == 0){
                System.out.println("不正な動きです。");
                move = getmove(board);
                moves = move.toCharArray(); //char[]に変換
            }
            if(board.movesecond(moves[0]-49,moves[1]-65,moves[2]-49,moves[3]-65) == 2){
                Kifu.append("\nFir:surrender");
                }else{
                Kifu.append("\nFir:" + move);
                }
            board.printstate();
    }

    static void Yourmove(Pieceboard board){
        String move = getmove(board);
        char[] moves = move.toCharArray(); //char[]に変換
        while(board.movesecond(moves[0]-49,moves[1]-65,moves[2]-49,moves[3]-65) == 0){
            System.out.println("不正な動きです。");
            move = getmove(board);
            moves = move.toCharArray(); //char[]に変換
        }
        if(board.movesecond(moves[0]-49,moves[1]-65,moves[2]-49,moves[3]-65) == 2){
            Kifu.append("\nSec:surrender");
            }else{
            Kifu.append("\nSec:" + move);
            }
        board.printstate();
    }

    static void Cpumove(Pieceboard board){
        String move = getCpumove(board);
        char[] moves = move.toCharArray(); //char[]に変換
        while(board.movesecond(moves[0]-49,moves[1]-65,moves[2]-49,moves[3]-65) == 0){
            move = getCpumove(board);
            moves = move.toCharArray(); //char[]に変換
        }
            Kifu.append("\nCpu:" + move);
        board.printstate();
    }

    static String getmove(Pieceboard board){
        String move;
        int colmove;
        do{
            move = new java.util.Scanner(System.in).next();
            colmove = checkmove(move);
                if(colmove == 0){
                    System.out.println("不正な入力です。");
                }
                if(colmove == 2)board.printstate();
                if(colmove == 3)return "SSSS";
        } while(colmove != 1);
        return move;
    }



    static int checkmove(String move){
        if(move.equals("M")){
            printManual();
            return 2;
        }else if(move.equals("S")){
            return 3;
        }{
            char [] prechar = move.toCharArray(); //char[]に変換
            if(prechar.length != 4)return 0;
            if(prechar[0]=='U'){//持ち駒を使う
                if(prechar[1] < '1' || prechar[1] > '6')return 0;
                if(prechar[2] < '1' || prechar[2] > '4')return 0;
                if(prechar[3] < 'A' || prechar[3] > 'C')return 0;
            }else{
                if(prechar[0] < '1' || prechar[0] > '4')return 0;
                if(prechar[1] < 'A' || prechar[1] > 'C')return 0;
                if(prechar[2] < '1' || prechar[2] > '4')return 0;
                if(prechar[3] < 'A' || prechar[3] > 'C')return 0;
            }
        }
    return 1;
    }

    static String getCpumove(Pieceboard board){
        String move;
        if(board.getAmountYourpieces() != 0){
            int M3 = new java.util.Random().nextInt(4);
            int M4 = new java.util.Random().nextInt(3);
            char[] movechar = new char[4];
            movechar[0] = 'U';
            movechar[1] = '1';
            movechar[2] = (char)(M3 + 49);
            movechar[3] = (char)(M4 + 65);
            move = String.valueOf(movechar);
        }else{
        int M1 = new java.util.Random().nextInt(4);
        int M2 = new java.util.Random().nextInt(3);
        int M3 = new java.util.Random().nextInt(4);
        int M4 = new java.util.Random().nextInt(3);
        char[] movechar = new char[4];
        movechar[0] = (char)(M1 + 49);
        movechar[1] = (char)(M2 + 65);
        movechar[2] = (char)(M3 + 49);
        movechar[3] = (char)(M4 + 65);
        move = String.valueOf(movechar);
        }
        return move;
    }

    static void printManual(){
        System.out.print("1:基本ルール 2:駒の動き 3:プログラムの仕様>");
       int M = new java.util.Scanner(System.in).nextInt();
       switch(M){
           case 1:
                Path p1 = Paths.get("./manuals/rule.txt");
                byte[] fileContentBytes;
                try{
                    fileContentBytes = Files.readAllBytes(p1);
                    }catch(IOException e){
                    System.out.println("マニュアルが存在しません");
                    break;
                    }
                    String fileContentStr;
                try{
                fileContentStr = new String(fileContentBytes, "SJIS");
                }catch(Exception e){
                    System.out.println("SJIJに対応していません");
                    break;
                }
                System.out.println(fileContentStr);
                break;
           case 2:
                Path p2 = Paths.get("./manuals/move.txt");
                // if(Files.exists(p2) != true)System.out.println("マニュアルが存在しません");
                byte[] fileContentBytes2;
                try{
                    fileContentBytes2 = Files.readAllBytes(p2);
                }catch(IOException e){
                    System.out.println("マニュアルが存在しません");
                    break;
                }
                String fileContentStr2;
                try{
                fileContentStr2 = new String(fileContentBytes2, "SJIS");
                }catch(Exception e){
                    System.out.println("SJIJに対応していません");
                    break;
                }
                System.out.println(fileContentStr2);
                break;
            case 3:
                Path p3 = Paths.get("./manuals/spec.txt");
                // if(Files.exists(p3) != true)System.out.println("マニュアルが存在しません");
                byte[] fileContentBytes3;
                try{
                    fileContentBytes3 = Files.readAllBytes(p3);
                }catch(IOException e){
                    System.out.println("マニュアルが存在しません");
                    break;
                }
                String fileContentStr3;
                try{
                fileContentStr3 = new String(fileContentBytes3, "SJIS");
                }catch(Exception e){
                    System.out.println("SJIJに対応していません");
                    break;
                }
                System.out.println(fileContentStr3);
                break;
            default:
                System.out.println("不正な入力です。");
       }
        System.out.print("対局に戻りますか?\n1:Yes 2:No>");
        int F = new java.util.Scanner(System.in).nextInt();
        if(F != 1)printManual();
    }
    
    public static void makeAcrHeader(){
        Date d = new Date();
        SimpleDateFormat d1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String q1 = d1.format(d);
        Kifu = new StringBuffer(q1);
    }

    public static void makeAcrEnd(){
        Date d = new Date();
        SimpleDateFormat d1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String q1 = d1.format(d);
        Kifu.append("\nFinish\n" + q1);
    }

    public static void writeAcr(){
        System.out.print("棋譜を保存しますか?\n1:Yes 2:No>");
        int S = new java.util.Scanner(System.in).nextInt();
            if(S == 1){
            makeAcrEnd();
            Path path;
            String name;
            do{
                System.out.print("棋譜ファイル名>");
                name = new java.util.Scanner(System.in).next();
                path = Paths.get("./Acr/" + name + ".acr");
                if(Files.exists(path) == true){
                        System.out.println("その名前は既に使われています");
                    }
                }while(Files.exists(path) == true);
            try{
            Files.createFile(path);
            Files.write(path, Kifu.toString().getBytes(StandardCharsets.UTF_8.name()));
            }catch(IOException e){
                System.out.println("ファイルを作成できません");
            }
        }
    }

    public static void ReadAcr(Pieceboard board){
        Path path;
        String name;
        Boolean mymove = true;
        do{
        System.out.print("棋譜ファイル名>");
        name = new java.util.Scanner(System.in).next();
        path = Paths.get("./Acr/" + name + ".acr");
        if(Files.exists(path) != true){
                System.out.println("そのファイルは存在しません");
            }
        }while(Files.exists(path) != true);
        // BufferedReader br;
        board.printstate();
        try{
            BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8);
            String start_time = br.readLine();
        while (true) {
            String line = br.readLine();
            char [] move = line.toCharArray(); //char[]に変換
            if(move.length != 8)break;
            System.out.print("次に進む>");
            String next = new java.util.Scanner(System.in).nextLine();
            mymove = linemove(board,move);
            }
        }catch(IOException e){
            System.out.println("棋譜の読み込みに失敗しました");
        }
        if(mymove == true)System.out.println("以上で先手の勝利");
        if(mymove == false)System.out.println("以上で後手の勝利");        
    }

    public static boolean linemove(Pieceboard board,char[] move){
        if(move[0] == 'F'){
            board.movefirst(move[4]-49,move[5]-65,move[6]-49,move[7]-65);
            board.printstate();
            return true;
        }
        if(move[0] == 'S' || move[0] == 'C'){
            board.movesecond(move[4]-49,move[5]-65,move[6]-49,move[7]-65);
            board.printstate();
            return false;
        }
        return true;
    }
    
}           