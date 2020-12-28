package io.henriquels25.cloudstream.demo.flightapi.messaging;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.function.context.config.ContextFunctionCatalogAutoConfiguration;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.cloud.stream.config.*;
import org.springframework.cloud.stream.function.FunctionConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@SpringBootTest(classes = {ContextFunctionCatalogAutoConfiguration.class,
        BinderFactoryAutoConfiguration.class,
        BindersHealthIndicatorAutoConfiguration.class,
        BindingsEndpointAutoConfiguration.class,
        ChannelBindingAutoConfiguration.class,
        ChannelsEndpointAutoConfiguration.class,
        FunctionConfiguration.class,
        BindingServiceConfiguration.class,
        TestChannelBinderConfiguration.class})
public @interface CloudStreamTest {
}
