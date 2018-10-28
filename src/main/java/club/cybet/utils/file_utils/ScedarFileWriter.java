package club.cybet.utils.file_utils;

import club.cybet.domain.db.enums.FileWriters;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * cybet-api (club.cybet.utils.file_utils)
 * Created by: cybet
 * On: 16 Aug, 2018 8/16/18 12:47 PM
 **/
public class ScedarFileWriter {

    FileWriter writer;
    File output;

    public ScedarFileWriter(FileWriters type, String output) throws IOException {
        this.output = new File(output);
        if(type == FileWriters.FileWriter){
            this.writer = new FileWriter(this.output);
        }
    }

    public String[] write(List<Object[]> rows, String delimiter){
        long start = System.currentTimeMillis();
        try{
            for (Object[] row : rows) {

                //Write other rows
                writer.write(String.join(delimiter, (String[])row));
                writer.write("\n");
            }
            terminate();
        } catch (IOException io){
            io.printStackTrace();
        }
        long end = System.currentTimeMillis();

        return new String[] {String.valueOf((end - start)), String.valueOf(output.length())};
    }

    public void write(Object[] row, String delimiter){
        try{
            writer.write(String.join(delimiter, (String[])row));
            writer.write("\n");
        } catch (IOException io){
            io.printStackTrace();
        }
    }

    public void write(String row){
        try{
            writer.write(row+"\n");
        } catch (IOException io){
            io.printStackTrace();
        }
    }

    public void terminate(){
        try{
            writer.flush();
            writer.close();
        } catch (IOException io){
            io.printStackTrace();
        }
    }

}
