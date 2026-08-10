// Copyright (c) 2024 Preponderous Software
// MIT License

package preponderous.viron.config;

import java.lang.reflect.Method;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionAttributeSource;
import preponderous.viron.controllers.EntityController;
import preponderous.viron.controllers.EnvironmentController;
import preponderous.viron.factories.EnvironmentFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifies that the write paths annotated for #194 are genuinely advised at runtime: an
 * {@code @Transactional} annotation only takes effect through a proxy, and only while a
 * transaction manager is present. Rollback behaviour itself is proved against a real database in
 * {@code preponderous.viron.database.TransactionRollbackTest}.
 */
@SpringBootTest
class TransactionBoundaryWiringTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private TransactionAttributeSource transactionAttributeSource;

    @Test
    void contextExposesOneTransactionManagerBoundToThePooledDataSource() {
        assertThat(applicationContext.getBeanNamesForType(PlatformTransactionManager.class)).hasSize(1);

        DataSourceTransactionManager transactionManager =
                (DataSourceTransactionManager) applicationContext.getBean(PlatformTransactionManager.class);
        assertThat(transactionManager.getDataSource()).isSameAs(applicationContext.getBean(DataSource.class));
    }

    @Test
    void environmentCascadeDeleteRunsInATransaction() {
        assertTransactional(EnvironmentController.class, "deleteEnvironment", int.class);
    }

    @Test
    void entityDeleteRunsInATransaction() {
        assertTransactional(EntityController.class, "deleteEntity", int.class);
    }

    @Test
    void environmentCreationRunsInATransaction() {
        assertTransactional(EnvironmentFactory.class, "createEnvironment",
                String.class, int.class, int.class, int.class);
    }

    private void assertTransactional(Class<?> beanType, String methodName, Class<?>... parameterTypes) {
        Object bean = applicationContext.getBean(beanType);
        assertThat(AopUtils.isAopProxy(bean))
                .as("%s must be proxied for its transaction boundary to apply", beanType.getSimpleName())
                .isTrue();

        Method method = findMethod(beanType, methodName, parameterTypes);
        assertThat(transactionAttributeSource.getTransactionAttribute(method, AopUtils.getTargetClass(bean)))
                .as("%s.%s must carry a transaction attribute", beanType.getSimpleName(), methodName)
                .isNotNull();
    }

    private static Method findMethod(Class<?> beanType, String methodName, Class<?>... parameterTypes) {
        try {
            return beanType.getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new AssertionError(beanType.getSimpleName() + "." + methodName + " no longer exists", e);
        }
    }
}
