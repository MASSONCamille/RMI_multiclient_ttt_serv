package RMI;

import javax.swing.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class TictactoServ extends UnicastRemoteObject implements RMIInterface {
    private Grille grille;
    private int JoueurActif;
    private boolean FirstToConnect = true;
    private int LastPlay;

    protected TictactoServ() throws RemoteException {
        super();
    }

    public static void main(String[] args){
        try {

            int port = 8000;
            LocateRegistry.createRegistry(port);
            Naming.rebind("rmi://localhost:" + port +"/tictacto", new TictactoServ());
            System.out.println("Server ready");
        }catch (Exception e) {
            System.err.println("Server exception : " + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public int getCurrentPlayer() throws RemoteException {
        return this.JoueurActif;
    }

    @Override
    synchronized public int ReStart() throws RemoteException {
        int role;
        if (FirstToConnect) {
            role = 1;
            FirstToConnect = false;
            try {
                wait();
            }catch (Exception e){ JOptionPane.showMessageDialog(null, e.toString()); }
            FirstToConnect = true;
        }
        else {
            role = 2;
            this.grille = new Grille();
            this.JoueurActif = 1;
            this.LastPlay = -1;
            notify();
        }
        return role;
    }

    @Override
    synchronized public void WaitOtherPlayer() throws RemoteException {
        if (FirstToConnect){
            FirstToConnect = false;
            try {
                wait();
            }catch (Exception e){ JOptionPane.showMessageDialog(null, e.toString()); }
            FirstToConnect = true;
        }
    }

    @Override
    synchronized public void Play(int pos) throws RemoteException {
        if (FirstToConnect) FirstToConnect = false;
        else notify();

        LastPlay = pos;
        this.grille.getTable().get(pos).setVal(JoueurActif);
    }

    @Override
    public int TestFin() throws RemoteException { // return : 0 si jeu non fini; ou le num du joueur gagnant

        if (TestGagne()) return JoueurActif;
        else if (TestEgal()) return 3;
        else return 0;

    }

    public boolean TestGagne() throws RemoteException {

        ArrayList<Case> colone = this.grille.getColone(LastPlay%3);
        System.out.println("Colone: " + colone.toString()); // test en console

        ArrayList<Case> ligne = this.grille.getLigne(LastPlay/3);
        System.out.println("Ligne: " + ligne.toString()); // test en console

        ArrayList<ArrayList<Case>> diagos = this.grille.getDiago(LastPlay);
        if (diagos != null) {
            System.out.println("Diago 1: " + diagos.get(0)); // test en console
            if (diagos.size() > 1) System.out.println("Diago 2: " + diagos.get(1)); // test en console
        }

        boolean gagne = true;
        for (Case c: colone) {
            gagne = gagne && (c.getVal() == JoueurActif);
        }
        if (gagne) return true;

        gagne = true;
        for (Case c: ligne) {
            gagne = gagne && (c.getVal() == JoueurActif);
        }
        if (gagne) return true;

        if (diagos != null){
            for (ArrayList<Case> diago: diagos) {
                gagne = true;
                for (Case c: diago) {
                    gagne = gagne && (c.getVal() == JoueurActif);
                }
                if (gagne) return true;
            }
        }
        return false;
    }

    public boolean TestEgal() throws RemoteException {
        boolean res = true;
        for (Case c: grille.getTable()) res = res && !c.isEmpty();
        return res;
    }

    @Override
    synchronized public void NextPlayer() throws RemoteException {
        if (FirstToConnect){
            FirstToConnect = false;
            try {
                wait();
            }catch (Exception e){ JOptionPane.showMessageDialog(null, e.toString()); }
            FirstToConnect = true;

        }else {
            if (JoueurActif == 1) JoueurActif = 2;
            else JoueurActif = 1;

            notify();
        }
    }

    @Override
    public String ImprGrille() throws RemoteException {
        return grille.toString();
    }
}
