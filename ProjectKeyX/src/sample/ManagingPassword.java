package sample;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ManagingPassword {
    private static File mainFile;
    private static final int odstup = 15;
    public static String username;
    public static String password;
    public static String data;
    private String toCheckingUsername;
    private String toCheckingPassword;
    private List<String> fileInside = new ArrayList<>();
    private static List<String> passwordsList = new LinkedList<>();
    private boolean isThisUser = false;
    private boolean isThisNewUser = false;

    ManagingPassword(File file){
        this.mainFile = file;
        try {
            FileReader fr = new FileReader(file);
            BufferedReader bf = new BufferedReader(fr);
            String line = bf.readLine();
            if (line==null){
                isThisNewUser = true;
            }
            else{
                oldUser(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void oldUser(File file)throws IOException{
            int numberLine = 0;
            FileReader fr = new FileReader(file);
            BufferedReader bf = new BufferedReader(fr);
            String line = bf.readLine();
            while (line != null){
                line = Szyfr.decode(line,odstup);
                fileInside.add(numberLine,line);
                numberLine++;
                line = bf.readLine();
            }

        if (fileInside.get(0).matches("name:(.*)")){
            String[] tmp;
            tmp = fileInside.get(0).split(":");
            username = tmp[1];
        }
        if (fileInside.get(1).matches("password:(.*)")){
            String[] tmp;
            tmp = fileInside.get(1).split(":");
            password = tmp[1];
        }
        if (fileInside.get(2).matches("time:(.*)")){
            String[] tmp;
            tmp = fileInside.get(2).split(":");
            data = tmp[1];
        }
        for (int i = 3; i < fileInside.size(); i++) {
            passwordsList.add(fileInside.get(i));
        }
    }

    boolean toCheckicng(String toCheckingUsername, String toCheckingPassword)throws Exception{
        if (isThisNewUser){
            FileWriter writer = new FileWriter(mainFile, false);

            String userStr = Szyfr.encode("name:" + toCheckingUsername + "\n",odstup);
            String passwordStr = Szyfr.encode("password:" + toCheckingPassword + "\n",odstup);
            String timeStr = Szyfr.encode("time:" + LocalDate.now() + "\n",odstup);

            writer.write(userStr);
            writer.write(passwordStr);
            writer.write(timeStr);

            password = toCheckingPassword;
            username = toCheckingUsername;
            data = "" + LocalDate.now();
            isThisUser = true;

            writer.flush();
        }else
            if (toCheckingUsername !=null && toCheckingPassword !=null){
            this.toCheckingUsername = toCheckingUsername;
            this.toCheckingPassword = toCheckingPassword;
            if (toCheckingUsername.equals(username) && toCheckingPassword.equals(password)){
                isThisUser = true;
                fileInside.remove(2);
                fileInside.add(2,"time:" + LocalDate.now());
                FileWriter writer = new FileWriter(mainFile, false);
                for (int i = 0; i < fileInside.size(); i++) {
                    String tmp = Szyfr.encode(fileInside.get(i),odstup);
                    writer.write(tmp+"\n");
                }
                writer.flush();
            }
        }
        return isThisUser;
    }

    public List<String> getPasswordsList() {
        return passwordsList;
    }

    public static void writingInFile()throws Exception{
        FileWriter writer = new FileWriter(mainFile, false);
        String userStr = Szyfr.encode("name:" + username + "\n",odstup);
        String passwordStr = Szyfr.encode("password:" + password + "\n",odstup);
        String timeStr = Szyfr.encode("time:" + LocalDate.now() + "\n",odstup);

        writer.write(userStr);
        writer.write(passwordStr);
        writer.write(timeStr);

        for (int i = 0; i < passwordsList.size(); i++) {
            writer.write(Szyfr.encode(passwordsList.get(i),odstup)+"\n");
        }
        writer.flush();
    }
}
