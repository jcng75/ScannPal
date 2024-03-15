package backend.classes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class Serialize {
    
    public static byte[] serializeList(List<TestCase> tc) {
        try {
            ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOutputStream);
            out.writeObject(tc);
            out.close();
            return byteOutputStream.toByteArray();
        } catch (IOException e) {
            System.out.printf("IOException '%s' is caught\n", e);
        }
        return null;
    }

    public static List<TestCase> deserializeList(byte[] byteArray) throws ClassNotFoundException {
        try {
            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteArray);
            ObjectInputStream objInputStream = new ObjectInputStream(byteInputStream);
            @SuppressWarnings("unchecked")
            List<TestCase> deserializedList = (List<TestCase>) objInputStream.readObject();
            return deserializedList;
        } catch (IOException e) {
            System.out.printf("IOException '%s' is caught\n", e);
        }
        return null;
    }
}
