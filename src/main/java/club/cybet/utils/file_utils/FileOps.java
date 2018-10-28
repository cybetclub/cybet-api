package club.cybet.utils.file_utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.text.DecimalFormat;
import java.util.Set;

/**
 * cybet-api (club.cybet.utils.file_utils)
 * Created by: cybet
 * On: 28 Aug, 2018 8/28/18 11:22 PM
 **/
public class FileOps {

    public static boolean deleteFile(String path){
        return deleteFile(Paths.get(path));
    }

    public static boolean deleteFile(Path path){
        try{
            Files.delete(path);
            return true;
        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

    public static boolean moveFile(String source, String destination, Set<PosixFilePermission> perms){
        return moveFile(Paths.get(source), Paths.get(destination), perms);
    }

    public static boolean moveFile(Path source, String destination, Set<PosixFilePermission> perms){
        return moveFile(source, Paths.get(destination), perms);
    }

    public static boolean moveFile(String source, Path destination, Set<PosixFilePermission> perms){
        return moveFile(Paths.get(source), destination, perms);
    }

    public static boolean moveFile(Path source, Path destination, Set<PosixFilePermission> perms){
        try {
            Files.move(source, destination);
            Files.setPosixFilePermissions(destination, perms);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean copyFile(String source, String destination){
        return copyFile(Paths.get(source), Paths.get(destination));
    }

    public static boolean copyFile(Path source, String destination){
        return copyFile(source, Paths.get(destination));
    }

    public static boolean copyFile(String source, Path destination){
        return copyFile(Paths.get(source), destination);
    }

    public static boolean copyFile(Path source, Path destination){
        try {
            Files.copy(source, destination);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getFileExtension(String fileName){
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            return fileName.substring(i+1);
        }
        return "";
    }

    public static Long getFileSize(String file){
        return new File(file).length();
    }

    public static String prettifyFileSize(long size) {
        if(size <= 0) return "0";
        final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
        int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
        return new DecimalFormat("#,###.##")
                .format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

}
