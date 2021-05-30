package User;

//import CommonClasses.AbstractDataBlock;
//import CommonClasses.DataBlock;

import CommonClasses.FirstTimeConnectedData;
import GraphicalUserInterface.*;
import GraphicalUserInterface.GPanes.GIpAndPortEntering;
import HelpingModuls.ConnectionException;
import HelpingModuls.ConsolePrinter;
import HelpingModuls.ObjectProcessing;
import HelpingModuls.TimeLimitedCode;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TransferCenter{

    DatagramSocket datagramSocket;
    DatagramSocket datagramSocketForWork;

    InetSocketAddress mainServerSocketAddress;
    InetSocketAddress socketAddressReceive;
    InetSocketAddress individualServerSocketAddress;

    private final int SIZEOFBUFFER = 500;

    public TransferCenter() { }


    public void connect(GInterface gInterface){
        boolean isExceptions = true;
        while (isExceptions) {
            isExceptions = false;
            try {
                createMainServerSocketAddress(gInterface);
                createSocketAddressForReceive();
                CreateConnectionWithServer createConnectionWithServer = new CreateConnectionWithServer(this);
                createConnectionWithServer.start();
                long timeOfStart = new Date().getTime();

//              Жуткий колхоз! ==============================
                while (!createConnectionWithServer.isAllRight() & !(timeOfStart < (new Date().getTime() - 5000))){
                    Thread.sleep(10);

                }
                if(createConnectionWithServer.isAllRight()){

                }
                else {

                    gInterface.sendNotification("Проблема с подключением к серверу. Пробуем всё заново!", "Ошибка подключения");
                    isExceptions = true;
                }
//              ==============================================


            } catch (Exception e) {
                gInterface.sendNotification("Введены некорректные данные!", "Ошибка подключения");
                isExceptions = true;
            }
        }
    };

    public boolean reConnect(){
        TransferCenter transferCenter = this;
        try {
            TimeLimitedCode timeLimitedCode = new TimeLimitedCode(5, TimeUnit.SECONDS){
                @Override
                public void codeBlock() {
                    transferCenter.createSocketAddressForReceive();
                    CreateConnectionWithServer createConnectionWithServer = new CreateConnectionWithServer(transferCenter);

//                    Такая конструкция экономичнее, тк не расходуются лишние ресурсы на доп поток. При этом,
//                    если даже run пройдёт(что случается крайне редко), то цикл всё равно вернёт всё назад
                    Date date = new Date();
                    while (date.getTime() + 10000 > new Date().getTime()) {
                        createConnectionWithServer.run();
                        if (createConnectionWithServer.isAllRight()) {

                            return;
                        }
                    }
                }
            };
            timeLimitedCode.start();
            return true;
        }catch (ConnectionException connectionException){
            connectionException.printStackTrace();
            return false;
        }

    }


    public TransferCenter(TransferCenter transferCenter){
        this.mainServerSocketAddress = transferCenter.mainServerSocketAddress;
        createSocketAddressForReceive();
        createConnectionWithServer();
    }

    public void createConnectionWithServer(){
        FirstTimeConnectedData firstTimeConnectedData = new FirstTimeConnectedData();
        firstTimeConnectedData.setSocketAddress(socketAddressReceive);
        sendObjectToServer(firstTimeConnectedData);
        firstTimeConnectedData = (FirstTimeConnectedData) receiveObjectFromServer();

        individualServerSocketAddress = (InetSocketAddress) firstTimeConnectedData.getSocketAddress();
    }

    public Object receiveObjectFromServer(){

        Object obj = null;
        boolean endOfReceive = false;
        byte[] objByteArr = new byte[0];
        byte[] receivedArr;

        while (!endOfReceive){
            receivedArr = receiveByteArr(datagramSocket);
            byte[] newArr = new byte[objByteArr.length + receivedArr.length];

            for(int i =0;i<(objByteArr.length + receivedArr.length);i++){
                if(i<objByteArr.length){
                    newArr[i] = objByteArr[i];
                }
                else {
                    newArr[i] = receivedArr[i-objByteArr.length];
                }
            }
            objByteArr = newArr;


            try {
                obj = ObjectProcessing.deSerializeObject(objByteArr);
                endOfReceive = true;

            }catch (Exception e){
            }
        }
        return  obj;
    }

    private byte[] receiveByteArr(DatagramSocket datagramSocket) {
        final int size = SIZEOFBUFFER;
        byte[] bytes = new byte[size];
        DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);
        try {
            datagramSocket.receive(datagramPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

//    /**устанавливает связь с сервером (заполняет поля datagramSocket и socketAddress)*/
    public void createMainServerSocketAddress(GInterface gInterface){
        Boolean err = false;


        GIpAndPortEntering gIpAndPortEntering = null;
        try {
            Lock lock = new ReentrantLock();
            lock.lock();
            Condition condition = lock.newCondition();
            gIpAndPortEntering = new GIpAndPortEntering(lock, condition, gInterface);
            gInterface.setGPane(gIpAndPortEntering);
            condition.await();
            lock.unlock();
        }catch (Exception e){
            err = true;
            e.printStackTrace();
        }


        String ip = gIpAndPortEntering.getIp();
        int port = Integer.valueOf(gIpAndPortEntering.getPort());

        mainServerSocketAddress = null;
        mainServerSocketAddress = new InetSocketAddress(ip, port);

        if(err == true){
//            JOptionPane.showConfirmDialog(new JOptionPane(), "Попробуем снова...", "Ошибка подключения", JOptionPane.OK_CANCEL_OPTION);
            gInterface.sendNotification("Попробуем снова...", "Ошибка подключения");

            createMainServerSocketAddress(gInterface);
        }

    }

    private int createSocketAddressForReceive(){
        Random random = new Random();

        int port = -1;

        boolean workingSocket = false;
        while (!workingSocket) {
            port = random.nextInt(65535);

            try {
                socketAddressReceive = new InetSocketAddress(InetAddress.getLocalHost(), port);
                workingSocket = true;
            } catch (IOException e) {
                ConsolePrinter.println("Проблема с созданием порта!");
//                e.printStackTrace();
            }

        }
        try {
            datagramSocket = null;
            datagramSocket = new DatagramSocket(socketAddressReceive);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return port;
    }

    public /*synchronized*/  <T> void sendObjectToServer(T object){

        byte[] serObject = ObjectProcessing.serializeObject(object);
        if(object.getClass().getName().equals("CommonClasses.FirstTimeConnectedData")){
            sendByteArr(serObject, mainServerSocketAddress);
        }
        else {
            sendByteArr(serObject, individualServerSocketAddress);
        }
    }


    private void sendByteArr(byte[] bArr, SocketAddress socketAddress){
        final int size = SIZEOFBUFFER;
        byte[] bigArr = new byte[bArr.length + size - (bArr.length % size)];
        for (int i =0; i < bArr.length; i++){
            bigArr[i] = bArr[i];
        }
        bArr = bigArr;


        for(int i = 0; i < Math.ceil(Float.valueOf(bArr.length)/size); i++){
            byte[] data = new byte[size];
            for (int j = 0; j<(size);j++){
                data[j] = bArr[j+(size)*i];
            }

            DatagramPacket datagramPacket = null;
            try {
                datagramPacket = new DatagramPacket(data, size, socketAddress);
            }catch (Exception e){
                ConsolePrinter.println("Проблема с отправкой объекта!");
            }
            try {
                datagramSocket.send(datagramPacket);
            } catch (IOException e) {
                ConsolePrinter.println("Проблема с отправкой объекта!");
            }
        }
    }
}
