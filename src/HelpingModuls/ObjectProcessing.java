package HelpingModuls;

import HelpingModuls.ConsolePrinter;

import java.io.*;

public class ObjectProcessing {
    public static <T> byte[] serializeObject(T obj){
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = null;
            try {
                objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            } catch (IOException e) {
                ConsolePrinter.println("Проблема с созданием потока для серилизации объектов!");
            }
            byte[] serObj = null;

            try {
                objectOutputStream.writeObject(obj);
                serObj = byteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                ConsolePrinter.println("Проблема с серелизацией объекта для отправки на сервер!");
                e.printStackTrace();
            }


        return serObj;
    }

    public static Object deSerializeObject(byte[] objectByteArr) throws ClassNotFoundException, IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(objectByteArr);

        ObjectInputStream objectInputStream = null;

        try {
            objectInputStream = new ObjectInputStream(inputStream);
        } catch (IOException e) {
            ConsolePrinter.println("Проблема с созданием ObjectInputStream!");
            e.printStackTrace();
        }

        Object object = null;
            object = objectInputStream.readObject();

        return object;
    }
}
