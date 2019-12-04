public class Hiyoko extends Piece{
    private boolean Niwatori = false;
   public int canMove(int rowdif, int coldif){//rowdif = dist - pre;
    if (this.Niwatori == true){
        switch (rowdif){
            case 1:
                if(coldif == -1 || coldif == 0 || coldif == 1)return 1;
                break;
            case 0:
                if(coldif == -1 || coldif == 1)return 1;
                break;
            case -1:
                if(coldif == 0)return 1;
            }
        }else{
        switch (rowdif){
            case 1:
             if(coldif == 0)return 1;
            }
        }
    return 0;
    }

    Hiyoko(boolean isMine){
    super(isMine,"‚Ð");
    }

    public void setNiwatori(){
        this.Niwatori = true;
        setIam("‚É");
    }

    public void setHiyoko(){
        this.Niwatori = false;
        setIam("‚Ð");
    }

    public boolean getNiwatori(){
    return this.Niwatori;
    }

}