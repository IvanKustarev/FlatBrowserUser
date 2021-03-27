package L6User;

public class RestartConnectionWithServer extends Thread{

    private boolean connected = false;

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    @Override
    public void run(){
        try {
            Thread.sleep(5000);
            if(!connected){
                System.out.println("Проблема с подключением к серверу, введите данные занова:");
                (new User(new TransferCenter())).startCheckingCommands();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
