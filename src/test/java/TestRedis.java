import org.junit.Test;
import redis.clients.jedis.Jedis;

public class TestRedis {
    @Test
    public void test(){
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("119.23.11.63");
     /*jedis.select(1);*/
        System.out.println("Connection to server sucessfully");
        //密码
        jedis.auth("root");


        //查看服务是否运行
        System.out.println("Server is running: "+jedis.ping());

        jedis.set("yzl", "Redis 1");
    }
}
