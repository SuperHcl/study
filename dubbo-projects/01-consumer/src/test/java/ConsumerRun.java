import com.hcl.service.SupermanService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: Hucl
 * @date: 2019/11/11 15:32
 * @description:
 */
public class ConsumerRun {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-consumer.xml");
        SupermanService supermanService = (SupermanService) context.getBean("supermanService");
        String hello = supermanService.hello("01-consumer");
        System.out.println(hello);
    }
}
