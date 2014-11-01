package common.xandayn.personalrecipes.util;

public class Util {

    public static boolean isStingNumeric(String string){
        try {
            Integer.parseInt(string);
            return true;
        } catch (Exception e){
            return false;
        }
    }

}
