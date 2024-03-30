package backend.classes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

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

    public static void testSerialization() throws ClassNotFoundException {
        TestCase tc1 = new TestCase();
        tc1.setAttackType("SQL tc1");
        tc1.setInjected(false);
        tc1.setPayload("this is for tc1 test");

        TestCase tc2 = new TestCase();
        tc2.setAttackType("XSS tc2");
        tc2.setInjected(true);
        tc2.setPayload("this is for tc2 test");

        List<TestCase> tcList = new ArrayList<TestCase>(Arrays.asList(tc1, tc2));
        System.out.println("tcList before serialization: " + tcList);

        byte[] serializedArray = Serialize.serializeList(tcList);
        System.out.println("Serialized tcList into byte array: " + serializedArray);

        List<TestCase> deserializedTC = Serialize.deserializeList(serializedArray);
		System.out.println("Deserialized byte array into List<TestCase>: " + deserializedTC + "\n");

        for (TestCase tc : deserializedTC) {
            System.out.println(tc.getAttackType());
            System.out.println(tc.getInjected());
            System.out.println(tc.getPayload()+"\n");
        }
    }
}
