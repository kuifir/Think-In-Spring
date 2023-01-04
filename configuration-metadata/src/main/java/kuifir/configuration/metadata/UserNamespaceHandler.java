package kuifir.configuration.metadata;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Package: kuifir.configuration.metadata
 * <p>
 * Description： "user元素" {@link }
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/4 16:32
 * <p>
 * Version: 0.0.1
 */
public class UserNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("users", new UserBeanDefinitionParser());

    }
}
