package com.crazyxxl.easemob.annotation;

import com.crazyxxl.easemob.config.EasemobAutoConfiguration;
import com.crazyxxl.easemob.config.HttpClient;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({
        ElementType.TYPE
})
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Import({
        EasemobAutoConfiguration.class
})
public @interface EaseMob {
}