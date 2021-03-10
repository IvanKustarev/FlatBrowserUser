package L6User;

import CommonClasses.AbstractDataBlock;
import CommonClasses.DataBlock;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Random;
import java.util.Scanner;

public class TransferCenter{

    DatagramSocket datagramSocketForSend;
    InetSocketAddress socketAddressForSend;

    InetSocketAddress socketAddressReceive;
    DatagramChannel datagramChannelForReceive;

    public TransferCenter(){
        createConnection();
        createSocketAddressForReceive();
        DataBlock dataBlock = new DataBlock();
        dataBlock.parameter = socketAddressReceive.getAddress().getHostAddress() + "; " + socketAddressReceive.getPort();
        sendObjectToServer(dataBlock);

        dataBlock = (DataBlock) receiveObjectFromServer();
    }

    public Object receiveObjectFromServer(){
        return receiveObjectFromServer(receiveObjectArrSize());
    }

    private Object receiveObjectFromServer(int objectArrSize){
        ByteBuffer buffer = ByteBuffer.allocate(objectArrSize);
        try {
            datagramChannelForReceive.receive(buffer);
        } catch (IOException e) {
            System.out.println("Проблема с переходом из канала в буфер!");
            e.printStackTrace();
        }
//        buffer.array();
        byte[] objectByteArr = buffer.array()/*new byte[objectArrSize]*/;
//        buffer.get(objectByteArr, 0, objectArrSize);
        return deSerialize(objectByteArr);
    }

    private int receiveObjectArrSize(){
        AbstractDataBlock objectWithSizeParameter = (AbstractDataBlock) receiveObjectFromServer(500);
        return Integer.valueOf(objectWithSizeParameter.parameter);
    }

    private Object deSerialize(byte[] objectByteArr){
        ByteArrayInputStream inputStream = new ByteArrayInputStream(objectByteArr);

        ObjectInputStream objectInputStream = null;

        try {
            objectInputStream = new ObjectInputStream(inputStream);
        } catch (IOException e) {
            System.out.println("Проблема с созданием ObjectInputStream!");
            e.printStackTrace();
        }

        Object object = null;
        try {
            object = objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return object;
    }

    /**устанавливает связь с сервером (заполняет поля datagramSocket и socketAddress)*/
    public void createConnection(){
        Scanner scanner = new Scanner(System.in);
        Boolean err = false;

        System.out.println("Введите ip адрес сервера: ");
        String ip = scanner.nextLine();
        System.out.println("Введите port: ");
        int port = scanner.nextInt();


        //"192.168.1.135"
        socketAddressForSend = null;
        //            System.out.println(InetAddress.getLocalHost().getHostAddress());
        socketAddressForSend = new InetSocketAddress(ip, port);

        datagramSocketForSend = null;
        try {
            datagramSocketForSend = new DatagramSocket();
        } catch (SocketException e) {
            err = true;
            System.out.println("Проблемы с datagramSocket!");
        }


        if(err == true){
            System.out.println("Попробуем снова...");
            createConnection();
        }

//        SocketAddress socketAddress = new InetSocketAddress(InetAddress.getLocalHost(), 6666);
//        DatagramSocket datagramSocket = new DatagramSocket();
//        DatagramPacket datagramPacket = new DatagramPacket(b, b.length, socketAddress);
//        datagramSocket.send(datagramPacket);
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
                System.out.println("Проблема с созданием порта!");
                e.printStackTrace();
            }

        }
        try {
            datagramChannelForReceive = DatagramChannel.open();
            datagramChannelForReceive.bind(socketAddressReceive);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return port;
    }

    public <T> void sendObjectToServer(T object){
        byte[] serObject = serializeObject(object);
        DataBlock warningAboutSize = new DataBlock();
        warningAboutSize.parameter = String.valueOf(serObject.length);
        sendByteArr(serializeObject(warningAboutSize));
        //Необходимо получать allRight --- доделать
        sendByteArr(serializeObject(object));
    }


    private void sendByteArr(byte[] bArr){
        DatagramPacket datagramPacket = new DatagramPacket(bArr, bArr.length, socketAddressForSend);
        try {
            datagramSocketForSend.send(datagramPacket);
        } catch (IOException e) {
            System.out.println("Проблема с отправкой объекта!");
        }
    }

    public <T> byte[] serializeObject(T obj){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        } catch (IOException e) {
            System.out.println("Проблема с созданием потока для серилизации объектов!");
        }
        byte[] serObj = null;

        try {
            objectOutputStream.writeObject(obj);
            serObj = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            System.out.println("Проблема с серелизацией объекта для отправки на сервер!");
            e.printStackTrace();
        }

        return serObj;
    }




//    public void sendRequestToServer(Object commandsDataForSendToServer){
////        File dataToServ = new File("dataToServ");
//        byte[] serObj = new byte[1000];
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        ObjectOutputStream objectOutputStream = null;
//        try {
//            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
//        } catch (IOException e) {
//            System.out.println("Проблема с созданием потока для серилизации объектов!");
//        }
//        try {
//            objectOutputStream.writeObject(commandsDataForSendToServer);
//            serObj = byteArrayOutputStream.toByteArray();
//        }catch (IOException e){
//            System.out.println("Проблема с серелизацией объекта для отправки на сервер!");
//        }
//        DatagramPacket datagramPacket = new DatagramPacket(serObj, serObj.length, socketAddress);
//        try {
//            datagramSocket.send(datagramPacket);
//        } catch (IOException e) {
//            System.out.println("Проблема с отправкой объекта!");
//        }
//
////        System.out.println(serObj.length);
////        for(byte b : serObj){
////            System.out.println(b);
////        }
////        System.out.println(serObj.length);
////        for(byte b : serObj){
////            System.out.println(b);
////        }
////        byte[] b = {1};
////        DatagramPacket datagramPacket = null;
////        datagramPacket = new DatagramPacket(b, b.length, socketAddress);
////        try {
////            datagramSocket.send(datagramPacket);
////        } catch (IOException e) {
////            System.out.println("Проблемы с отправкой запроса на сервер!");
////        }
//    }
//
//    public DataBlock reciveAnswerFromServer(){
//
//        byte[] bArr = new byte[1000];
//        DatagramChannel channel = null;
//
//        try {
//            channel = DatagramChannel.open();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        SocketAddress socketAddress = new InetSocketAddress(6667);
//        try {
//            channel.bind(socketAddress);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        ByteBuffer byteBuffer = ByteBuffer.wrap(bArr);
//            byteBuffer.clear();
//        try {
//            socketAddress = channel.receive(byteBuffer);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("take == true");
//
//        ByteArrayInputStream inputStream = new ByteArrayInputStream(bArr);
//        ObjectInputStream objectInputStream = null;
//        DataBlock dataBlock = null;
//        try {
//            objectInputStream = new ObjectInputStream(inputStream);
//            dataBlock = (DataBlock) objectInputStream.readObject();
//        } catch (Exception e) {
//            System.out.println("Проблема с загрузкой потока ObjectInputStream!");
//        }
////        System.out.println("ttttttt");
//        return dataBlock;
//    }
//
//    public void sendAnswerToServer(DataBlock dataBlock){
//        sendRequestToServer(dataBlock);
//    }
}
