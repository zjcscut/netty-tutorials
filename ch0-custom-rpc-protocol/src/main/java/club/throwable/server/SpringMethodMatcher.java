package club.throwable.server;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/1/12 12:15
 */
@Component
public class SpringMethodMatcher extends BaseMethodMatcher implements BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(@NonNull BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    HostClassMethodInfo findHostClassMethodInfo(Class<?> interfaceClass) {
        HostClassMethodInfo info = new HostClassMethodInfo();
        // 从容器中通过接口类型获取对应的实现,实现必须只有一个
        Object bean = beanFactory.getBean(interfaceClass);
        info.setHostTarget(bean);
        info.setHostClass(bean.getClass());
        info.setHostUserClass(ClassUtils.getUserClass(bean.getClass()));
        return info;
    }
}
