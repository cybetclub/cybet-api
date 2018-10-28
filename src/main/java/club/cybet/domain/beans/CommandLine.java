package club.cybet.domain.beans;

import club.cybet.utils.hibernate.HibernateSetup;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.fusesource.jansi.Ansi;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * sls-api (club.cybet.domain.beans)
 * Created by: cybet
 * On: 03 Jul, 2018 7/3/18 12:59 AM
 **/
public class CommandLine {
    
    public static class Args {
        String flag, opt;
    public Args(String flag, String opt) { this.flag = flag.toLowerCase(); this.opt = opt; }

        @Override
        public String toString() {
            return "Option{" +
                    "flag='" + flag + '\'' +
                    ", opt='" + opt + '\'' +
                    '}';
        }
    }
    
    public static List<Args> args = new ArrayList<>();
    public static List<String> argzList = new ArrayList<String>();
    public static List<String> doubleOptsList = new ArrayList<String>();
    
    public static void populateArgs(String... argz){
        for (int i = 0; i < argz.length; i++) {
            switch (argz[i].charAt(0)) {
                case '-':
                    if (argz[i].length() < 2)
                        throw new IllegalArgumentException("Not a valid argument: "+argz[i]);
                    if (argz[i].charAt(1) == '-') {
                        if (argz[i].length() < 3)
                            throw new IllegalArgumentException("Not a valid argument: "+argz[i]);
                        // --opt
                        doubleOptsList.add(argz[i].substring(2, argz[i].length()));
                    } else {
                        if (argz.length-1 == i)
                            throw new IllegalArgumentException("Expected arg after: "+argz[i]);
                        // -opt
                        args.add(new Args(argz[i], argz[i+1]));
                        i++;
                    }
                    break;
                default:
                    // arg
                    argzList.add(argz[i]);
                    break;
            }
        }
    }
    
    public static String getArg(String flag){
        for (Args arg : args){
            if(arg.flag.equals(flag.toLowerCase()))
                return arg.opt;
        }
        return null;
    }
    
    public static void addArg(Args arg){
        args.add(arg);
    }

    public static boolean seedDatabase(){
        boolean seedStatus = false;
        String seed = CommandLine.getArg("-s") == null ? "cybet_seed.sql" : CommandLine.getArg("-s");
        if(new File(seed).exists()){
            seedStatus =  executeSql(seed);
        }else{
            System.out.println(ansi().fg(Ansi.Color.RED).bold().a("ERROR: ")
                    .a("Skipping database seeding. File not found ("+seed+")").reset());
        }

        return seedStatus;

    }

    private static boolean executeSql(String sql){
        try (Stream<String> stream = Files.lines(Paths.get(sql))) {

            Session session = HibernateSetup.getMainSession();
            Transaction transaction = session.beginTransaction();

            Logger.getLogger("org.hibernate").setLevel(Level.OFF);
            AtomicInteger lineCounter = new AtomicInteger(1);

            stream.forEach(line -> {
                try {
                    session.createNativeQuery(line).executeUpdate();
                    lineCounter.getAndIncrement();
                } catch (Exception e) {
                    System.out.println(ansi().bold().fg(Ansi.Color.RED).a("ERROR: " + e.getMessage()).reset());
                    System.out.println(ansi().bold().fg(Ansi.Color.RED)
                            .a("CAUSE: " + e.getCause() + ". Line: " + lineCounter.get()).reset());
                }
            });

            transaction.commit();
            session.close();

            Logger.getLogger("org.hibernate").setLevel(Level.ERROR);

            return true;
        }  catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
