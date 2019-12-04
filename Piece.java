public abstract class Piece {
  private boolean isMine;
  private String Iam;

  public Piece (boolean isMine,String Iam){
    this.isMine = isMine;
    this.Iam = Iam;
  }

  public void printme(){
    if(isMine == true){
    System.out.print(this.Iam);
    }else{
      StringBuffer buf = new StringBuffer();
      for (int i = 0; i < Iam.length(); i++) {
        char code = Iam.charAt(i);
        if ((code >= 0x3041) && (code <= 0x3093)) {
          buf.append((char) (code + 0x60));
        } else {
          buf.append(code);
        }
      }
      System.out.print(buf);
    }
  }

  public void setNiwatori(){
  }
  public void setHiyoko(){
  }
  public void setIam(String Iam){
    this.Iam = Iam;
  }

  public String getIam(){
    return this.Iam;
  }

  public boolean getisMine(){
    return this.isMine;
  }
  
  public void setisMine(boolean isMine){
    this.isMine = isMine;
  }

  public int canMove(int rowdif, int coldif){
    return 0;
  }
}
