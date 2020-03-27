package test;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TestInterface extends Remote {
    public boolean insert(int n) throws RemoteException;
    public int getOtherExcept() throws RemoteException;
    public String InternTest() throws RemoteException;
}
