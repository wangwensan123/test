package json;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.JsonObject;

/**
 *@auth wws
 *@date 2018年3月21日---下午6:12:47
 *
 **/
public class JsonTest {
  
  
        public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
          
          String json = "{\"name\":\"zhangsan\",\"age\":20,\"birthday\":844099200000,\"email\":\"zhangsan@163.com\"}";
//          ObjectMapper mapper = new ObjectMapper();
//          User user = mapper.readValue(json, User.class);
//          System.out.println(user.getName());
            User user = new User();
            user.setName("zhangsan");
            user.setAge(20);
            user.setBirthday(new Date());
            user.setEmail("zhangsan@163.com");
            JSON aa = JSON.fromObject(user);
             System.out.println(aa.toString());

        }
      
      
}
