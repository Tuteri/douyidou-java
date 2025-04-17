package cc.douyidou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author ruoyi
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class DouYiDouApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(DouYiDouApplication.class, args);
        System.out.println(
                "                 (♥◠‿◠)ﾉﾞ  抖一抖启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                ".______       __    __  .__   __. .__   __.  __  .__   __.   _______ \n" +
                "|   _  \\     |  |  |  | |  \\ |  | |  \\ |  | |  | |  \\ |  |  /  _____|\n" +
                "|  |_)  |    |  |  |  | |   \\|  | |   \\|  | |  | |   \\|  | |  |  __  \n" +
                "|      /     |  |  |  | |  . `  | |  . `  | |  | |  . `  | |  | |_ | \n" +
                "|  |\\  \\----.|  `--'  | |  |\\   | |  |\\   | |  | |  |\\   | |  |__| | \n" +
                "| _| `._____| \\______/  |__| \\__| |__| \\__| |__| |__| \\__|  \\______|");
    }
}
