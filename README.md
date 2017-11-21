Note:

1、该程序实现了springboot集成环形的功能

2、此程序实现了服务端环形功能的部分接口

3、由于改程序使用了HttpClient来向环形服务端发送请求，因此，需要在配置文件里面添加httpClient的相关配置

使用改程序配置如下：

1、添加maven依赖：

```xml
		<dependency>
			<groupId>com.github.crazyxxl</groupId>
			<artifactId>spring-boot-starter-easemob</artifactId>
			<version>1.0.0</version>
		</dependency>
```

2、添加配置文件：

```properties
#HttpClient 配置Start
http.maxTotal=500
http.defaultMaxPerRoute=100
http.connectTimeout=5000
http.connectionRequestTimeout=2000
http.socketTimeout=10000
http.maxIdleTime =1
http.staleConnectionCheckEnabled=true
#HttpClient 配置End
#环形配置Start
easemob.url=http://a1.easemob.com/org_name/app_name
easemob.userId=client_id
easemob.passwd=client_secret
#环形配置End
```

3、注入环形客户端

```java
package com.crazyxxl.easemobTest;

import com.crazyxxl.easemob.models.UserRegisterDto;
import com.crazyxxl.easemob.service.IEasemobClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EasemobTestApplicationTests {
	@Autowired
	private IEasemobClient easemobClient;

	@Test
	public void contextLoads() {
	}

	@Test
	public void userRegister(){
		UserRegisterDto userRegister= new UserRegisterDto();
		userRegister.setPassword("123");
		userRegister.setUsername(("hahaha"));
		String result = easemobClient.userRegister(userRegister);
		System.out.print(result);
	}

	@Test
	public void usersRegister(){
		UserRegisterDto userRegister1= new UserRegisterDto();
		userRegister1.setPassword("1231");
		userRegister1.setUsername(("hahaha2"));
		UserRegisterDto userRegister2= new UserRegisterDto();
		userRegister2.setPassword("1232");
		userRegister2.setUsername(("hahaha1"));
		List<UserRegisterDto> users =new ArrayList<>();
		users.add(userRegister1);
		users.add(userRegister2);
		String result = easemobClient.usersRegister(users);
		System.out.print(result);
	}

}
```





