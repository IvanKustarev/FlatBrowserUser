package User;

import CommonClasses.FirstTimeConnectedData;

import java.net.InetSocketAddress;

public class CreateConnectionWithServer extends Thread {

//    private boolean connected = false;
//
//    public void setConnected(boolean connected) {
//        this.connected = connected;
//    }
//
//    @Override
//    public void run(){
//        try {
//            Thread.sleep(5000);
//            if(!connected){
//                System.out.println("Проблема с подключением к серверу, введите данные занова:");
//                (new User(new TransferCenter())).startCheckingCommands();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}

    TransferCenter transferCenter;
    boolean allRight = false;

    public CreateConnectionWithServer(TransferCenter transferCenter) {
        this.transferCenter = transferCenter;
    }

    public boolean isAllRight() {
        return allRight;
    }

    public void run() {
        FirstTimeConnectedData firstTimeConnectedData = new FirstTimeConnectedData();

        firstTimeConnectedData.setSocketAddress(transferCenter.socketAddressReceive);

        transferCenter.sendObjectToServer(firstTimeConnectedData);

//        System.out.println("a");
        try {
            Object o = transferCenter.receiveObjectFromServer();
//            System.out.println("a1");
            firstTimeConnectedData = (FirstTimeConnectedData) o;
        }catch (Exception e){
            e.printStackTrace();
        }
//        System.out.println("a");
        transferCenter.individualServerSocketAddress = (InetSocketAddress) firstTimeConnectedData.getSocketAddress();
//        System.out.println("a");
        allRight = true;
//        System.out.println("a");
    }
}