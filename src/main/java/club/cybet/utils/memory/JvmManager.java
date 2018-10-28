package club.cybet.utils.memory;

/**
 * sls-api (club.cybet.utils.memory)
 * Created by: cybet
 * On: 04 Jul, 2018 7/4/18 9:22 PM
 **/
public class JvmManager {

    public static void gc(boolean withSysGc, Object... objects){
        gc(objects);
        if(withSysGc) System.gc();
    }

    public static void gc(Object... objects){
        for (Object o : objects){
            o = null;
        }
    }

}
