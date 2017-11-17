public class Chessboard {
  public static class Field {
    private char row;
    private byte column;
    private Chesspiece piece = null;
    private boolean marked = false;
    public Field(char row, byte column) {
      this.row = row;
      this.column = column;
    }

    public void put(Chesspiece piece) {
      this.piece = piece;
    }
    public Chesspiece take() {
      this.piece = null;
      return null;
    }

    public void mark() {
      this.marked = true;
    }
    public void unmark() {
      this.marked = false;
    }
    public String toString() {
      return "";
    }
  }

  public static final int NUMBER_OF_ROWS = 8;
  public static final int NUMBER_OF_COLUMNS = 8;
  public static final int FIRST_ROW = 'A';
  public static final int FIRST_COLUMN = 1;
  private Field[][] fields;
  /* Knight squares */
  private static final byte[][] squares = {{2, 1}, {2, -1},
                                           {-2, 1}, {-2, -1},
                                           {1, 2}, {-1, 2},
                                           {1, -2}, {-1, -2}};

  public Chessboard() {
    fields = new Field[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];
    char row = 0;
    byte column = 0;
    for (int r = 0; r < NUMBER_OF_ROWS; r++) {
      row = (char)(FIRST_ROW + r);
      column = FIRST_COLUMN;
      for (int c = 0; c < NUMBER_OF_COLUMNS; c++) {
        fields[r][c] = new Field(row, column);
        column++;
      }
    }
  }
  public String toString() {
    String output = " ";
    for (int i = 0; i <= NUMBER_OF_ROWS; i++) {
      if (i == 0) {
        for (int j = 1; j <= NUMBER_OF_COLUMNS; j++) {
          output = output + " " + j + " ";
        }
      } else {
        output = output + (char)((i-1)+65); 
        for (int j = 1; j <= NUMBER_OF_COLUMNS; j++) {
            if (fields[i-1][j-1].marked == true) {
              output = output + " X ";
            } else {
              if (fields[i-1][j-1].piece != null) {
                output = output + fields[i-1][j-1].piece.toString(); 
              } else {
                output = output + " - ";
              }
            }
        }
      }
      output = output + "\n";
    }
    return output;
  }
  
  public boolean isValidField(char row, byte column) {
    return (row - FIRST_ROW < NUMBER_OF_ROWS &&
            row - FIRST_ROW >= 0 &&
            column - FIRST_COLUMN < NUMBER_OF_COLUMNS &&
            column - FIRST_COLUMN >= 0);
  }
  
  public abstract class Chesspiece {
    private char colour;
    // w - white, b - black

    private char name;
    protected char row = 0;
    protected byte column = -1;
    protected Chesspiece(char colour, char name) {
      this.colour = colour;
      this.name = name;
    }
    public String toString() {
      return " " + colour + name;
    }
    public boolean isOnBoard() {
      return Chessboard.this.isValidField(row, column);
    }
    public void moveTo(char row, byte column) throws NotValidFieldException {
      if (!Chessboard.this.isValidField(row, column)) {
        throw new NotValidFieldException("Bad field: " + row + column);
      }
      if (this.column != -1) {
        this.moveOut();
      }
      this.row = row;
      this.column = column;
      int r = row - FIRST_ROW;
      int c = column - FIRST_COLUMN;
      Chessboard.this.fields[r][c].put(this);
    }

    public void moveOut() {
      Chessboard.this.fields[this.row - FIRST_ROW]
                            [this.column - FIRST_COLUMN].put(null);
      this.row = 0;
      this.column = -1;
    }
    public abstract void markReachableFields();
    public abstract void unmarkReachableFields();
    protected void markDiagonals() {
      int side = 0;
      for (int i = 1; i <= NUMBER_OF_ROWS; i++) {
        side = i;
        for (int adj = 0; adj < 2; adj++) {
          /* int cord = (int)Math.round(Math.tan(Math.PI / 4 +
                                                 Math.PI / 2 * adj) * i); */
          side *= -1;
          if (Chessboard.this.isValidField((char)(this.row + side),
                                           (byte)(this.column + i))) {
            Chessboard.this.fields[this.row + side - FIRST_ROW]
                                  [this.column + i - FIRST_COLUMN].mark();
          }
          if (Chessboard.this.isValidField((char)(this.row + side),
                                           (byte)(this.column - i))) {
            Chessboard.this.fields[this.row + side - FIRST_ROW]
                                  [this.column - i - FIRST_COLUMN].mark();
          }
        }
      }
    }
    protected void markStraights() {
      for (int i = 1; i < NUMBER_OF_ROWS; i++) {
        /* STRAIGHT LINES */
        if (Chessboard.this.isValidField((char)(this.row + i), this.column)) {
          Chessboard.this.fields[this.row + i - FIRST_ROW]
                                [this.column - FIRST_COLUMN].mark();
        }
        if (Chessboard.this.isValidField((char)(this.row - i), this.column)) {
          Chessboard.this.fields[this.row - i - FIRST_ROW]
                                [this.column - FIRST_COLUMN].mark();
        }
        if (Chessboard.this.isValidField(this.row, (byte)(this.column + i))) {
          Chessboard.this.fields[this.row - FIRST_ROW]
                                [this.column + i - FIRST_COLUMN].mark();
        }
        if (Chessboard.this.isValidField(this.row, (byte)(this.column - i))) {
          Chessboard.this.fields[this.row - FIRST_ROW]
                                [this.column - i - FIRST_COLUMN].mark();
        }
      }
    }
    protected void unmarkStraights() {
      for (int i = 1; i < NUMBER_OF_ROWS; i++) {
        /* STRAIGHTS */
        if (Chessboard.this.isValidField((char)(this.row + i), this.column)) {
          Chessboard.this.fields[this.row + i - FIRST_ROW]
                                [this.column - FIRST_COLUMN].unmark();
        }
        if (Chessboard.this.isValidField((char)(this.row - i), this.column)) {
          Chessboard.this.fields[this.row - i - FIRST_ROW]
                                [this.column - FIRST_COLUMN].unmark();
        }
        if (Chessboard.this.isValidField(this.row, (byte)(this.column + i))) {
          Chessboard.this.fields[this.row - FIRST_ROW]
                                [this.column + i - FIRST_COLUMN].unmark();
        }
        if (Chessboard.this.isValidField(this.row, (byte)(this.column - i))) {
          Chessboard.this.fields[this.row - FIRST_ROW]
                                [this.column - i - FIRST_COLUMN].unmark();
        }
      }
    }
    protected void unmarkDiagonals() {
      for (int i = 1; i <= NUMBER_OF_ROWS; i++) {
        for (int adj = 0; adj < 2; adj++) {
          int cord = (int)Math.round(Math.tan(Math.PI / 4 +
                                              Math.PI / 2 * adj) * i);
          if (Chessboard.this.isValidField((char)(this.row + cord),
                                           (byte)(this.column + i))) {
            Chessboard.this.fields[this.row + cord - FIRST_ROW]
                                  [this.column + i - FIRST_COLUMN].unmark();
          }
          if (Chessboard.this.isValidField((char)(this.row + cord),
                                           (byte)(this.column - i))) {
            Chessboard.this.fields[this.row + cord - FIRST_ROW]
                                  [this.column - i - FIRST_COLUMN].unmark();
          }
        }
      }
    }
  }

  public class Pawn extends Chesspiece {
    public Pawn (char colour, char name) {
      super(colour, name);
    }
    public void markReachableFields() {
      byte col = (byte)(this.column + 1);
      if (Chessboard.this.isValidField(row, col)) {
        int r = row - FIRST_ROW;
        int c = col - FIRST_COLUMN;
        Chessboard.this.fields[r][c].mark();
      }
    }
    public void unmarkReachableFields() {
      byte col = (byte)(column + 1);
      if (Chessboard.this.isValidField(row, col)) {
        int r = row - FIRST_ROW;
        int c = col - FIRST_COLUMN;
        Chessboard.this.fields[r][c].unmark();
      }
    }
  }
  public class Rook extends Chesspiece {
    public Rook (char colour, char name) {
      super(colour, name);
    }
    public void markReachableFields() {
      this.markStraights();
    }
    public void unmarkReachableFields() {
      this.unmarkStraights();
    }
  }
  public class Bishop extends Chesspiece {
    public Bishop (char colour, char name) {
      super(colour, name);
    }
    public void markReachableFields() {
      this.markDiagonals();
    }
    public void unmarkReachableFields() {
      this.unmarkDiagonals();
    }
  }

  public class Knight extends Chesspiece {
    public Knight (char colour, char name) {
      super(colour, name);
    }
    public void markReachableFields() {
      for (int i = 0; i < squares.length; i++) {
        if (Chessboard.this.isValidField((char)(this.row + squares[i][1]),
                                         (byte)(this.column + squares[i][0]))) {
          Chessboard.this.fields[this.row + squares[i][1] - FIRST_ROW]
                                [this.column + squares[i][0] - FIRST_COLUMN].mark();
        }
      }
    }
    public void unmarkReachableFields() {
      for (int i = 0; i < squares.length; i++) {
        if (Chessboard.this.isValidField((char)(this.row + squares[i][1]),
                                         (byte)(this.column + squares[i][0]))) {
          Chessboard.this.fields[this.row + squares[i][1] - FIRST_ROW]
                                [this.column + squares[i][0] - FIRST_COLUMN].unmark();
        }
      }
    }
  }

  public class Queen extends Chesspiece {
    public Queen (char colour, char name) {
      super(colour, name);
    }
    public void markReachableFields() {
      this.markDiagonals();
      this.markStraights();
    }
    public void unmarkReachableFields() {
      this.unmarkDiagonals();
      this.unmarkStraights();
    }
  }
}
