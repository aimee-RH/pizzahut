package back_end;
import com.example.adminservice.AdminserviceApplication;
import com.example.customerservice.CustomerserviceApplication;
import com.example.deliverservice.DeliverserviceApplication;
import com.example.feign_api.FeignApiApplication;
import com.example.gateway_server.GatewayServerApplication;
import com.example.shopservice.ShopserviceApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({AdminserviceApplication.class, CustomerserviceApplication.class, DeliverserviceApplication.class, GatewayServerApplication.class,
ShopserviceApplication.class,FeignApiApplication.class})
@SpringBootApplication
public class BackEndApplication {
	public static void main(String[] args) {

		SpringApplication.run(AdminserviceApplication.class, args);
		SpringApplication.run(CustomerserviceApplication.class, args);
		SpringApplication.run(GatewayServerApplication.class, args);
		SpringApplication.run(FeignApiApplication.class, args);
		SpringApplication.run(DeliverserviceApplication.class, args);
		SpringApplication.run(ShopserviceApplication.class, args);
//		SpringApplication.run(BackEndApplication.class, args);

	}
}
