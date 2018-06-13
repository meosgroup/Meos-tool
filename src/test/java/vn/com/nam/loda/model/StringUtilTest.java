package vn.com.nam.loda.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringUtilTest {

    @Test
    public void testgetIdFromUrl() {
        String s = "https://ereka.vn/post/co-nhung-dieu-khong-phai-ai-cung-biet-ve-ha-noi-529827528914458959";
        System.out.println(StringUtil.getIdFromUrl(s));
        s = "529827528914458959";
        System.out.println(StringUtil.getIdFromUrl(s));
    }
}