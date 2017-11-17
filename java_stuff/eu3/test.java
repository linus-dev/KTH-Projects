class test {
  public static void main(String[] args) {
    Chessboard board = new Chessboard();
    java.util.Random rand = new java.util.Random();
    Chessboard.Chesspiece[] pieces = new Chessboard.Chesspiece[5];
    pieces[0] = board.new Pawn ('w', 'P');
    pieces[1] = board.new Rook ('b', 'R');
    pieces[2] = board.new Queen ('w', 'Q');
    pieces[3] = board.new Bishop ('w', 'B');
    //pieces[4] = board.new King ('b', 'K');
    pieces[4] = board.new Knight ('w', 'N'); 
    for (int i = 0; i < pieces.length; i++) {
      try {
        pieces[i].moveTo((char)(65 + rand.nextInt(8)),
                         (byte)(rand.nextInt(8) + 1));
      } catch (NotValidFieldException err) {
        System.err.println("ERROR: Not a valid field. " + err.getMessage());
      }
      pieces[i].markReachableFields();
      System.out.println(board.toString());
      pieces[i].unmarkReachableFields();
      pieces[i].moveOut();
    }
  }
}
