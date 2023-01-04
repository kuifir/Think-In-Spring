package kuifir.configuration.metadata;

import kuifir.ioc.overview.domain.User;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * Package: kuifir.configuration.metadata
 * <p>
 * Description： "user元素" {@link BeanDefinitionParser} 的实现
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/4 16:34
 * <p>
 * Version: 0.0.1
 */
public class UserBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    @Override
    protected Class<?> getBeanClass(Element element) {
        return User.class;
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder builder) {
        setPropertiesValue("id",element,builder);
        setPropertiesValue("name",element,builder);
        setPropertiesValue("city",element,builder);
    }
    private void setPropertiesValue(String attributeName, Element element, BeanDefinitionBuilder builder){
        String attributeValue = element.getAttribute(attributeName);
        if(StringUtils.hasText(attributeValue)){
            builder.addPropertyValue(attributeName, attributeValue);
        }
    }
}
