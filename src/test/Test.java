package test;

import javax.swing.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;


public class Test extends UnicastRemoteObject implements TestInterface {
    private int elem[];

    public Test() throws RemoteException {
        elem = null;
    }

    public static void main(String[] args){
        try {
            int port = 8000;
            LocateRegistry.createRegistry(port);
            Naming.rebind("rmi://localhost:" + port +"/test", new Test());
            System.out.println("Server ready");
        }catch (Exception e) {
            System.err.println("Server exception : " + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    synchronized public boolean insert(int n) throws RemoteException {
        this.elem = new int[]{n};
        notify();
        return true;
    }

    @Override
    synchronized public int getOtherExcept() throws RemoteException {
        try {
            Thread.sleep(10000);
        }catch (InterruptedException e){ e.printStackTrace(); }

        return 4;
//        try {
//            while (elem == null) {
//                wait();
//            }
//        }catch (InterruptedException e){ e.printStackTrace(); }
//        return elem[0];
    }

    @Override
    synchronized public String InternTest() throws RemoteException {
        return "test reussi ";
    }
}
