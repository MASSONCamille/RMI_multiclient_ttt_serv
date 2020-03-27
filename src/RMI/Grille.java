package RMI;

import java.util.ArrayList;

public class Grille {
    public ArrayList<Case> table;

    public Grille(){
        table = new ArrayList<>();
        for (int x=0; x<3; x++){
            for (int y=0; y<3; y++) {
                table.add(new Case(x, y));
            }
        }
    }

    public ArrayList<Case> getTable() {
        return table;
    }

    public void setTable(ArrayList<Case> table) {
        this.table = table;
    }

    public int search(int x, int y){
        int i=0;
        for (Case C : table) {
            if (C.getX() == x && C.getY() == y)
                return i;
            i++;
        }
        return -1;
    }

    public ArrayList<Case> getLigne(int x){
        ArrayList<Case> res = new ArrayList<Case>();
        int pos;
        for (int i=0; i<3;i++){
            pos = this.search(x, i);
            res.add(this.table.get(pos));
        }
        return res;
    }

    public ArrayList<Case> getColone(int y){
        ArrayList<Case> res = new ArrayList<Case>();
        int pos;
        for (int i=0; i<3;i++){
            pos = this.search(i, y);
            res.add(this.table.get(pos));
        }
        return res;
    }

    public ArrayList<ArrayList<Case>> getDiago(int pos){
        ArrayList<ArrayList<Case>> res = new ArrayList<ArrayList<Case>>();

        ArrayList<Case> diago1 = new ArrayList<Case>();
        diago1.add(this.table.get(0));
        diago1.add(this.table.get(4));
        diago1.add(this.table.get(8));

        ArrayList<Case> diago2 = new ArrayList<Case>();
        diago2.add(this.table.get(2));
        diago2.add(this.table.get(4));
        diago2.add(this.table.get(6));

        if (pos == 0 | pos == 4 | pos == 8) res.add(diago1);
        if (pos == 2 | pos == 4 | pos == 6) res.add(diago2);
        if (pos == 1 | pos == 3 | pos == 5 | pos == 7) res = null;

        return res;
    }

    @Override
    public String toString() {
        String str = "+---+---+---+\n";

        for (int y=0; y<3; y++){
            str += "|";
            for (int i=0; i<3; i++){
                str += " " + table.get(search(y, i)).toString() + " |";
            }
            str += "\n+---+---+---+\n";
        }
        return str;
    }
}
