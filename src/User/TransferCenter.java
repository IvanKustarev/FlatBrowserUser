package User;

//import CommonClasses.AbstractDataBlock;
//import CommonClasses.DataBlock;

import CommonClasses.FirstTimeConnectedData;
import GraphicalUserInterface.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TransferCenter{

    DatagramSocket datagramSocket;
    DatagramSocket datagramSocketForWork;

    InetSocketAddress mainServerSocketAddress;
    InetSocketAddress socketAddressReceive;
    InetSocketAddress individualServerSocketAddress;
    WorkingWithGInterface gInterface;
//    MainWindow mainWindow;

    private final int SIZEOFBUFFER = 500;

    public TransferCenter(WorkingWithGInterface gInterface ){
//        this.mainWindow = new MainWindow();
        this.gInterface = gInterface;
        boolean isExceptions = true;
        while (isExceptions) {
            isExceptions = false;
            try {
                createMainServerSocketAddress();
                createSocketAddressForReceive();
                CreateConnectionWithServer createConnectionWithServer = new CreateConnectionWithServer(this);
                createConnectionWithServer.start();
                long timeOfStart = new Date().getTime();

//              Жуткий колхоз! ==============================
                while (!createConnectionWithServer.isAllRight() & !(timeOfStart < (new Date().getTime() - 5000))){
                    Thread.sleep(10);

                }
                if(createConnectionWithServer.isAllRight()){
//                    Printer.println("Пользователь успешно создан!");
//                    JOptionPane.showConfirmDialog(new JOptionPane(), "Пользователь успешно создан!", "Уведомление", JOptionPane.OK_CANCEL_OPTION);

                }
                else {

                    JOptionPane.showConfirmDialog(new JOptionPane(), "Проблема с подключением к серверу. Пробуем всё заново!", "Ошибка подключения", JOptionPane.OK_CANCEL_OPTION);
//                    Printer.println("Проблема с подключением к серверу. Пробуем всё заново!");
                    isExceptions = true;
                }
//              ==============================================


            } catch (Exception e) {
//                Printer.println("Введены некорректные данные!");
                JOptionPane.showConfirmDialog(new JOptionPane(), "Введены некорректные данные!", "Ошибка подключения", JOptionPane.OK_CANCEL_OPTION);
                isExceptions = true;
            }
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

            }catch (Exception e){}
        }

        return  obj;
    }

    private byte[] receiveByteArr(DatagramSocket datagramSocket) {
        final int size = SIZEOFBUFFER;
//        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[size]);
        byte[] bytes = new byte[size];
        DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);
        try {
            datagramSocket.receive(datagramPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }


//    private Object deSerialize(byte[] objectByteArr){
//        ByteArrayInputStream inputStream = new ByteArrayInputStream(objectByteArr);
//
//        ObjectInputStream objectInputStream = null;
//
//        try {
//            objectInputStream = new ObjectInputStream(inputStream);
//        } catch (IOException e) {
//            System.out.println("Проблема с созданием ObjectInputStream!");
//            e.printStackTrace();
//        }
//
//        Object object = null;
//        try {
//            object = objectInputStream.readObject();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        return object;
//    }

//    public boolean checkConnection(){
//        CheckConnection checkConnection = new CheckConnection(this);
//        checkConnection.start();
//        long time = new Date().getTime() + 1000;
////        System.out.println();
//        while((new Date().getTime() < time) & !checkConnection.connectionWorking){
////            System.out.println(new DataBlock());
//        }
//        if(!checkConnection.connectionWorking){
//            System.out.println("Нет ответа от сервера...");
//            checkConnection();
//        }
//        return false;
//    }

//    /**устанавливает связь с сервером (заполняет поля datagramSocket и socketAddress)*/
    public void createMainServerSocketAddress(){
        Scanner scanner = new Scanner(System.in);
        Boolean err = false;


        IpAndPortEntering ipAndPortEntering = null;
        try {
            Lock lock = new ReentrantLock();
            lock.lock();
            Condition condition = lock.newCondition();
            ipAndPortEntering = new IpAndPortEntering(lock, condition);
            gInterface.setSpaceForInteraction(ipAndPortEntering.getPanel());
            condition.await();
            lock.unlock();
        }catch (Exception e){
            err = true;
            e.printStackTrace();
        }


        String ip = ipAndPortEntering.getIp();
        int port = Integer.valueOf(ipAndPortEntering.getPort());

//        Printer.println("Введите ip адрес сервера: ");
//        String ip = scanner.nextLine();
//        Printer.println("Введите port: ");
//        int port = scanner.nextInt();


        //"192.168.1.135"
        mainServerSocketAddress = null;
        mainServerSocketAddress = new InetSocketAddress(ip, port);

        if(err == true){
//            Printer.println("Попробуем снова...");
            JOptionPane.showConfirmDialog(new JOptionPane(), "Попробуем снова...", "Ошибка подключения", JOptionPane.OK_CANCEL_OPTION);

            createMainServerSocketAddress();
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
                Printer.println("Проблема с созданием порта!");
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

    public <T> void sendObjectToServer(T object){

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
                Printer.println("Проблема с отправкой объекта!");
            }
            try {
                datagramSocket.send(datagramPacket);
            } catch (IOException e) {
                Printer.println("Проблема с отправкой объекта!");
            }
        }
    }
}
