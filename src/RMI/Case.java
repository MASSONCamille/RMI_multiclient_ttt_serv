package RMI;

public class Case {
    private boolean empty;
    private int val;
    private final int X;
    private final int Y;

    public Case(int x, int y){
        empty = true;
        val = 0;
        X = x;
        Y = y;
    }

    public boolean isEmpty() {
        return empty;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
        this.empty = false;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    @Override
    public String toString() {
        if (empty) return " ";
        else if (val == 1) return "X";
        else if (val == 2) return "O";
        return "?";
    }
}
