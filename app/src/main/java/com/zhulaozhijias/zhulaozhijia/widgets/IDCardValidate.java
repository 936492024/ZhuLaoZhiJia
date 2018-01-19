package com.zhulaozhijias.zhulaozhijia.widgets;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by asus on 2017/9/16.
 */

public class IDCardValidate {
    private Context context;
    // 身份证的最小出生日期,1900年1月1日
    private final static Date MINIMAL_BIRTH_DATE = new Date(-2209017600000L);
    private static final String BIRTH_DATE_FORMAT="yyyy";
    private final static int NEW_CARD_NUMBER_LENGTH = 18;
    private final static String LENGTH_ERROR="1";//身份证长度必须为18位！
    private final static String NUMBER_ERROR="2";//身份证前17位应该都为数字！
    private final static String DATE_ERROR="3";//身份证日期验证无效！

    public IDCardValidate(Context context) {
        this.context=context;

    }
    public static String validate_effective(String idcardNumber){
        String Ai=idcardNumber.trim();
        if(Ai.length()!=18){
            return LENGTH_ERROR;
        }
        // 身份证号的前15,17位必须是阿拉伯数字
        for (int i = 0;  i < NEW_CARD_NUMBER_LENGTH - 1; i++) {
            char ch = Ai.charAt(i);
            if( ch < '0' || ch > '9'){
                return NUMBER_ERROR;
            }
        }
        //校验身份证日期信息是否有效 ，出生日期不能晚于当前时间，并且不能早于1900年

            Date birthDate =getBirthDate(Ai);

            if(null == birthDate){
                return DATE_ERROR;
            }
            if(!birthDate.before(new Date())){
                return DATE_ERROR;
            }
            if(!birthDate.after(MINIMAL_BIRTH_DATE)){
                return DATE_ERROR;
            }
            /**
             * 出生日期中的年、月、日必须正确,比如月份范围是[1,12],日期范围是[1,31]，还需要校验闰年、大月、小月的情况时，
             * 月份和日期相符合
             */
            String birthdayPart = getBirthDayPart(Ai);
            String realBirthdayPart =createBirthDateParser().format(birthDate);
            String realBirthdayParts =createBirthDateParser().format(new Date());
            int a = Integer.parseInt(realBirthdayPart);
            int b = Integer.parseInt(realBirthdayParts);
            int c=b-a;
            if(!birthdayPart.equals(realBirthdayPart)){
                return DATE_ERROR;
            }
        return String.valueOf(c);
    }

    /**
     * 功能：设置地区编码
     *
     * @return Hashtable 对象
     */
    private static Date getBirthDate(String idcard) {
        Date cacheBirthDate=null;
        try {
            cacheBirthDate = createBirthDateParser().parse(getBirthDayPart(idcard));
        } catch (Exception e) {
            throw new RuntimeException("身份证的出生日期无效");
        }
        return new Date(cacheBirthDate.getTime());
    }

    private static SimpleDateFormat createBirthDateParser() {
        return new SimpleDateFormat(BIRTH_DATE_FORMAT);
    }

    private static String getBirthDayPart(String idcardnumber) {
        return idcardnumber.substring(6, 10);
    }

}
