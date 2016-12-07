package com.github.fzakaria.guice.dedupe;

import com.github.fzakaria.guice.dedupe.SingletonModules.FakeClassA;
import com.github.fzakaria.guice.dedupe.SingletonModules.FakeClassB;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Series of tests that check the validity of the deduplication in Guice.
 */
public class SingletonModuleTest {

    @Test
    public void testSingletonModulesDedupeProviderCorrectly() {
        Injector injector = Guice.createInjector(new SingletonModules.ModuleA());
        FakeClassA fakeClassA = injector.getInstance(FakeClassA.class);
        Assertions.assertThat(fakeClassA).isNotNull();
    }

    @Test
    public void testSingletonModulesDedupeBinderCorrectly() {
        Injector injector = Guice.createInjector(new SingletonModules.ModuleA());
        FakeClassB fakeClassB = injector.getInstance(FakeClassB.class);
        Assertions.assertThat(fakeClassB).isNotNull();
    }

    @Test
    public void testSingletonModulesDedupeProviderCorrectlyMultipleTimes() {
        //for good measure, just add twice the number of modules.
        Injector injector = Guice.createInjector(new SingletonModules.ModuleA(), new SingletonModules.ModuleA());
        FakeClassA fakeClassA = injector.getInstance(FakeClassA.class);
        Assertions.assertThat(fakeClassA).isNotNull();
    }

    @Test
    public void testSingletonModulesDedupeBinderCorrectlyMultipleTimes() {
        Injector injector = Guice.createInjector(new SingletonModules.ModuleA(), new SingletonModules.ModuleA());
        FakeClassB fakeClassB = injector.getInstance(FakeClassB.class);
        Assertions.assertThat(fakeClassB).isNotNull();
    }

}
