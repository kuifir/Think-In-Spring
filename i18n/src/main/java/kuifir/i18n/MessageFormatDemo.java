package kuifir.i18n;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Package: kuifir.i18n
 * <p>
 * Description： {@link java.text.MessageFormat}
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/5 22:45
 * <p>
 * Version: 0.0.1
 */
public class MessageFormatDemo {
    public static void main(String[] args) {
        int planet = 7;
        String event = "a disturbance in the Force";
        String pattern = "At {1,time} on {1,date}, there was {2} on planet {0,number,integer}.";
        MessageFormat messageFormat = new MessageFormat(pattern);
        String result = messageFormat.format(new Object[]{planet, new Date(), event});
        System.out.println(result);
        // 重置MessagePattern
        messageFormat.applyPattern("this is a text : {0}");
        result = messageFormat.format(new Object[]{"hello, world"});
        System.out.println(result);
        // 重置 local
        messageFormat.setLocale(Locale.ENGLISH);
        messageFormat.applyPattern("At {1,time,full} on {1,date,full}, there was {2} on planet {0,number,integer}.");
        result = messageFormat.format(new Object[] {planet,new Date(),event});
        System.out.println(result);
        // 重置Format
        messageFormat.setFormat(1,new SimpleDateFormat("YYYY-MM-dd"));
        result = messageFormat.format(new Object[] {planet,new Date(),event});
        System.out.println(result);
    }
}
