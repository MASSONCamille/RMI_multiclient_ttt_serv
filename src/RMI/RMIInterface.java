package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIInterface extends Remote {
    public int ReStart() throws RemoteException;
    public void WaitOtherPlayer() throws RemoteException;
    public void Play(int pos) throws RemoteException;
    public int getCurrentPlayer() throws RemoteException;
    public int TestFin() throws RemoteException;
    public void NextPlayer() throws RemoteException;
    public String ImprGrille() throws RemoteException;
}
