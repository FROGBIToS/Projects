package Utils;

import java.io.*;
import java.util.*;

public class Serializer implements Serializable {
    private static final long serialVersionUID = 1324523466234356L;
    public static final String FILE_NAME = "./data/data.ser";
    private static Map<Class, List<Serializer>> allExtents = new Hashtable<>();

    public Serializer() {
        List<Serializer> extent = null;
        Class theClass = this.getClass();

        if(allExtents.containsKey(theClass)){
            extent = allExtents.get(theClass);
        }
        else{
            extent = new ArrayList<>();
            allExtents.put(theClass, extent);
        }
        extent.add(this);
    }

    public static Map<Class, List<Serializer>> getExtent() {
        return allExtents;
    }

    public static void saveExtent() throws IOException {
        System.out.println("SAVING______________________________________");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(allExtents);
        }
    }

    public static void loadExtent() throws IOException, ClassNotFoundException {
        System.out.println("LOADING______________________________________");
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            allExtents = (Hashtable) ois.readObject();
        }
    }
}
