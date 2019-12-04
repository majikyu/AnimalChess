public class Kirin extends Piece{
    public int canMove(int rowdif, int coldif){//rowdif = dist - pre;
     switch (rowdif){
         case -1:
         case  1:
             if(coldif == 0)return 1;
             break;
         case 0:
             if(coldif == -1 || coldif == 1)return 1;
         }
     return 0;
     }
     Kirin(boolean isMine){
     super(isMine,"‚«");
     }
 }