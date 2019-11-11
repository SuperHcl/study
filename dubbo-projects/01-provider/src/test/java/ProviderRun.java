import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author: Hucl
 * @date: 2019/11/11 14:54
 * @description:
 */
public class ProviderRun {

    public static void main(String[] args) throws IOException {
        // 创建spring容器
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring.provider.xml");
        // 启动spring容器
        ((ClassPathXmlApplicationContext) context).start();
        // 使主线程阻塞
        System.in.read();
    }
}
