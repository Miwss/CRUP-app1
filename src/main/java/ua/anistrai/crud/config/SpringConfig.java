package ua.anistrai.crud.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;


//Заменяет файл ApplicationContext.xml
@Configuration
@ComponentScan("ua.anistrai.crud")
@EnableWebMvc
public class SpringConfig implements WebMvcConfigurer {//данный интерфейс реализуется тогда, когда мы хотим настроить спринг мвс под себя.

    private final ApplicationContext applicationContext;

    //С помощью аннотации внедряем applicationContext
    @Autowired
    public SpringConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    //данный applicationContext используется в данном бине для настройки нашего шаблонизатора
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/"); //указываем папку где будут лежать наши представления
        templateResolver.setSuffix(".html"); //указываем их расширегия
        return templateResolver;
    }

    //В данном бине так же производится конфигурация наших представлений
    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    //В данном методе мы задаем наш шаблонизатор! //передаем спрингу что хотим использовать thymeleaf!
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        registry.viewResolver(resolver);
    }
}
//данная конфигурация полностью эквивалентна файлу applicationContext.xml