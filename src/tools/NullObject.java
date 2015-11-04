package tools;

/**
 * Created by pja on 2015-08-11.
 */
public class NullObject {

    public static boolean isNull(Object o){
        if(o == null){
            throw new NullPointerException();
        }
        return false;
    }
}
