package jenkov.IO;

import java.io.File;

public class DeleteDirExample {


    public static void main(String[] args) {
        File dir = new File("c:\\iegri");

    }

    public static boolean deleteDir(File dir) throws Exception{
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                deleteDir(file);
            } else {
                file.delete();
            }
        }

        return dir.delete();
    }
}
