package fr.askhim.api;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class AskHimApiApplication {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(AskHimApiApplication.class, args);
	}

}


//spring.datasource.url=jdbc:mysql://192.168.50.12:3306/askhim
//		spring.datasource.username=mmorel
//		spring.datasource.password=azerty


//{
//  "redis-host": "192.168.50.12:6379",
//  "redis-password": "azerty"
//}

// sudo systemctl restart askhimapi
// open consol screen -r AskHimAPI
// close : ctrl + a + d