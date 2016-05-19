package android.particles.com.retrofit.component.util;

import java.util.Arrays;
import java.util.List;

/**
 * Created by YLM on 2016/5/7.
 */
public class ToType
{
    private static final String[] SPELL = new String[]{
            "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
            "A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"
    };
    //是否为英文
    public static boolean isLetter( String inputStr ){
        char[] inputArray = inputStr.toCharArray( );
        List<String> spellList = Arrays.asList(SPELL);

        for( char input : inputArray ){
            if( !spellList.contains( input + "" ) ){
                return false;
            }
        }
        return true;
    }
}