package L6User;

import CommonClasses.AbstractDataBlock;
import CommonClasses.CommandsData;
import CommonClasses.DataBlock;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Main {
    public static void main(String [] args) throws IOException, ClassNotFoundException {
        User user = new User();
        Scanner scanner = new Scanner(System.in);

        TransferCenter transferCenter = new TransferCenter();

//        =============================================
//
//        Object answerToServer = new AnswerToServer();
////        {
////            @Override
////            public boolean StartProcessingCommand(CommonClasses.tt answer) {
////                System.out.println("Rabotaet!");
////                return false;
////            }
////        };
//        Test answerToServer = new Test();
//        byte[] barr;
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
//        objectOutputStream.writeObject(answerToServer);
//        barr = outputStream.toByteArray();
//
//        SocketAddress socketAddress = new InetSocketAddress(InetAddress.getLocalHost(), 6666);
//        DatagramSocket datagramSocket = new DatagramSocket();
//        DatagramPacket datagramPacket = new DatagramPacket(barr, barr.length, socketAddress);
//        datagramSocket.send(datagramPacket);
//        System.out.println(barr[0]);

//        System.out.println();
//        for (byte b : barr){
//            System.out.println(b);
//        }


//
//        byte[] nbarr = new byte[1000];
//        for (byte b : nbarr){
//            System.out.println(b);
//        }
//
//        for (int i =0;i<barr.length;i++){
//            nbarr[i] = barr[i];
//        }
//
//
//
//        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(barr);
//        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
//        CommonClasses.tt answerToServer1 = (CommonClasses.tt) objectInputStream.readObject();
//        answerToServer1.StartProcessingCommand(new AnswerToServer());
//
//
//
//







//      ===============================================

        user.startCheckingCommands();
    }
}
