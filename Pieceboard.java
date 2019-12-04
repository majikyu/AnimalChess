// Nothing = 0; Hiyoko = 1; Kirin = 2; Zou = 3; Lion = 4; Niwatori = 5;
public class Pieceboard{
private int column = 3;
private int row = 4;
private Piece[][] board = new Piece[row][column];
private Piece[] Mypieces = new Piece[6];
private Piece[] Yourpieces = new Piece[6];
private int AmountMypieces = 0;
private int AmountYourpieces = 0;
private boolean fin = false;
private boolean sur = false;
    Pieceboard(){
    makeStartState();
    }

    public void makeStartState(){
    this.board[0][0] = new Kirin(false);
    this.board[0][1] = new Lion(false);
    this.board[0][2] = new Zou(false);
    this.board[1][1] = new Hiyoko(false);
    this.board[2][1] = new Hiyoko(true);
    this.board[3][0] = new Zou(true);
    this.board[3][1] = new Lion(true);
    this.board[3][2] = new Kirin(true);
    }

    public void printstate(){
        System.out.print("\n持ち駒:");

        for(int i=0;i<AmountYourpieces;i++)
        Yourpieces[i].printme();

        System.out.print("\n  ＡＢＣ\n --------\n");
        
        for(int i=0;i<row;i++){
            System.out.print(i+1 + "|");
            for(int j=0;j<column;j++){
                if(this.board[i][j] != null){
                this.board[i][j].printme();
                }else{
                System.out.print("　");
                }
            }
            System.out.print("|\n");// 改行
        }
        System.out.print(" --------\n持ち駒:");

        for(int i=0;i<AmountMypieces;i++)
        Mypieces[i].printme();

        System.out.print("\n\n");
    }

    public int movefirst(int prerow, int precol, int disrow, int discol){
        if(prerow == ('S'-49)){
            this.sur = true;
            return 2;
        }
        if(prerow == ('U'-49)){
            if(AmountMypieces < (precol+17))return 0;//持ち駒の有無 precol+17が入力された番目
            if(this.board[disrow][discol] != null)return 0;//移動先にコマがある

            this.board[disrow][discol] = Mypieces[precol+16];
            Mypieces[precol+16] = null;
            gapdel(true);
            AmountMypieces--;
        }else{
            int rowdif = prerow - disrow;
            int coldif = precol - discol;
            // System.out.printf("%d%d%d%d\n%d%d",prerow,precol,disrow,discol,rowdif,coldif);
            if(this.board[prerow][precol] == null || this.board[prerow][precol].getisMine() == false)return 0; //移動元にコマが無い
            if(this.board[prerow][precol].canMove(rowdif,coldif) == 0)return 0; //そのコマには移動不可能
            if(this.board[disrow][discol] != null){//移動先に自分のコマがある
                if(this.board[disrow][discol].getisMine() == true){
                    return 0;
                }else{
                    if(this.board[disrow][discol].getIam() == "ら")this.fin = true;    
                    this.board[disrow][discol].setisMine(true);
                    Mypieces[AmountMypieces] = this.board[disrow][discol];
                    AmountMypieces++;
                }
            }
        this.board[disrow][discol] = this.board[prerow][precol];
        this.board[prerow][precol] = null;        
            if(this.board[disrow][discol].getIam() == "ひ" && disrow == 0)
            this.board[disrow][discol].setNiwatori();
            if(this.board[disrow][discol].getIam() == "ら" && disrow == 0){
                if(isTry(disrow,discol) == 1)this.fin = true;
                }         
        }
    return 1;
    }

    public int movesecond(int prerow, int precol, int disrow, int discol){
        if(prerow == ('S'-49)){
            this.sur = true;
            return 2;
        }
        if(prerow == ('U'-49) ){
            if(AmountYourpieces < (precol+17))return 0;//持ち駒の有無
            if(this.board[disrow][discol] != null)return 0;//移動先にコマがある

            this.board[disrow][discol] = Yourpieces[precol+16];
            Yourpieces[precol+16] = null;
            gapdel(false);
            AmountYourpieces--;
        }else{
        int rowdif = disrow - prerow;
        int coldif = discol - precol;
        // System.out.printf("%d%d%d%d\n%d%d",prerow,precol,disrow,discol,rowdif,coldif);
            if(this.board[prerow][precol] == null || this.board[prerow][precol].getisMine() == true)return 0; //移動元にコマが無い
            if(this.board[prerow][precol].canMove(rowdif,coldif) == 0)return 0; //そのコマには移動不可能
            if(this.board[disrow][discol] != null){//移動先に自分のコマがある
                if(this.board[disrow][discol].getisMine() == false){
                    return 0;
                }else{
                    if(this.board[disrow][discol].getIam() == "ら")this.fin = true; 
                    if(this.board[disrow][discol].getIam() == "に")this.board[disrow][discol].setHiyoko();
                    this.board[disrow][discol].setisMine(false);                
                    Yourpieces[AmountYourpieces] = this.board[disrow][discol];                   
                    AmountYourpieces++;
                }
            }                             
            this.board[disrow][discol] = this.board[prerow][precol];
            this.board[prerow][precol] = null;
            if(this.board[disrow][discol].getIam() == "ひ" && disrow == 3){
            this.board[disrow][discol].setNiwatori();
                }
            if(this.board[disrow][discol].getIam() == "ら" && disrow == 3){
                if(isTry(disrow,discol) == 1)this.fin = true;
                }            
        }         
        return 1;
    }

    public int isTry(int disrow,int discol){
        switch(disrow){
            case 0://先手の場合
                switch(discol){
                    case 0://1Aでトライ
                        if(this.board[0][1] != null && this.board[0][1].getisMine() == false && this.board[0][1].canMove(0,1) == 1)return 0;
                        if(this.board[1][0] != null && this.board[1][0].getisMine() == false && this.board[1][0].canMove(1,0) == 1)return 0;
                        if(this.board[1][1] != null && this.board[1][1].getisMine() == false && this.board[1][1].canMove(1,1) == 1)return 0;     
                        break;                   
                    case 1://1Bでトライ
                        if(this.board[0][0] != null && this.board[0][0].getisMine() == false && this.board[0][0].canMove(0,-1) == 1)return 0;     
                        if(this.board[0][2] != null && this.board[0][2].getisMine() == false && this.board[0][2].canMove(0,1) == 1)return 0;     
                        if(this.board[1][0] != null && this.board[1][0].getisMine() == false && this.board[1][0].canMove(1,-1) == 1)return 0;     
                        if(this.board[1][1] != null && this.board[1][1].getisMine() == false && this.board[1][1].canMove(1,0) == 1)return 0;     
                        if(this.board[1][2] != null && this.board[1][2].getisMine() == false && this.board[1][2].canMove(1,1) == 1)return 0;                             
                        break;
                    case 2://1Cでトライ
                        if(this.board[0][1] != null && this.board[0][1].getisMine() == false && this.board[0][1].canMove(0,-1) == 1)return 0;                             
                        if(this.board[1][1] != null && this.board[1][1].getisMine() == false && this.board[1][1].canMove(1,-1) == 1)return 0;                             
                        if(this.board[1][2] != null && this.board[1][2].getisMine() == false && this.board[1][2].canMove(1,0) == 1)return 0;                                                 
                }break;
            case 3://後手の場合
                switch(discol){
                    case 0://4Aでトライ
                        if(this.board[2][0] != null && this.board[2][0].getisMine() == true && this.board[2][0].canMove(1,0) == 1)return 0;
                        if(this.board[2][1] != null && this.board[2][1].getisMine() == true && this.board[2][1].canMove(1,-1) == 1)return 0;
                        if(this.board[3][1] != null && this.board[3][1].getisMine() == true && this.board[3][1].canMove(0,-1) == 1)return 0;
                        break;
                    case 1://4Bでトライ
                        if(this.board[2][0] != null && this.board[2][0].getisMine() == true && this.board[2][0].canMove(1,1) == 1)return 0;
                        if(this.board[2][1] != null && this.board[2][1].getisMine() == true && this.board[2][1].canMove(1,0) == 1)return 0;
                        if(this.board[2][2] != null && this.board[2][2].getisMine() == true && this.board[2][2].canMove(1,-1) == 1)return 0;
                        if(this.board[3][0] != null && this.board[3][0].getisMine() == true && this.board[3][0].canMove(0,1) == 1)return 0;
                        if(this.board[3][2] != null && this.board[3][2].getisMine() == true && this.board[3][2].canMove(0,-1) == 1)return 0;
                        break;
                    case 2://4Cでトライ
                        if(this.board[2][1] != null && this.board[2][1].getisMine() == true && this.board[2][1].canMove(1,1) == 1)return 0;
                        if(this.board[2][2] != null && this.board[2][2].getisMine() == true && this.board[2][2].canMove(1,0) == 1)return 0;
                        if(this.board[3][1] != null && this.board[3][1].getisMine() == true && this.board[3][1].canMove(0,1) == 1)return 0;
                }
        }
        return 1;
    }
        
    public void gapdel(boolean isFirst){
        if(isFirst == true){
            for(int i=1;i<AmountMypieces;i++){
                if(Mypieces[i] != null && Mypieces[i-1] == null){
                    Mypieces[i-1] = Mypieces[i];
                    Mypieces[i] = null;
                }
            }
        }else{
            for(int i=1;i<AmountYourpieces;i++){
                if(Yourpieces[i] != null && Yourpieces[i-1] == null){
                    Yourpieces[i-1] = Yourpieces[i];
                    Yourpieces[i] = null;
                }
            }
        }
    }

    public boolean getFin(){
        return this.fin;
    }

    public boolean getSur(){
        return this.sur;
    }

    public int getAmountYourpieces(){
        return AmountYourpieces;
    }
    
}