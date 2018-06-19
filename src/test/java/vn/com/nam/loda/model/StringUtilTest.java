package vn.com.nam.loda.model;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.Test;
import vn.com.nam.loda.service.API;

import static org.junit.Assert.*;

public class StringUtilTest {

    @Test
    public void testgetIdFromUrl() {
        String s = "https://ereka.vn/post/co-nhung-dieu-khong-phai-ai-cung-biet-ve-ha-noi-529827528914458959";
        System.out.println(StringUtil.getIdFromUrl(s));
        s = "529827528914458959";
        System.out.println(StringUtil.getIdFromUrl(s));
    }

    @Test
    public void testLogin() throws UnirestException {
        System.out.println(API.login("0964852422","123456789").getObject().getString("token"));
    }
}