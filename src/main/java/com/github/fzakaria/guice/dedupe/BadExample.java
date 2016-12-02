package com.github.fzakaria.guice.dedupe;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;

/**
 * In this example, the deduplication of ModuleC does not work because
 * the hashcode/equals method is using the default one which checks object reference only.
 * It cannot dedupe then multiple instances of ModuleC
 */
public class BadExample {

    public static class ModuleA extends AbstractModule {

        @Override
        protected void configure() {
            install(new ModuleB());
            install(new ModuleC());
        }
    }

    public static class ModuleB extends AbstractModule {

        @Override
        protected void configure() {
            install(new ModuleC());
        }
    }

    public static class ModuleC extends AbstractModule {

        @Override
        protected void configure() {
        }

        @Provides
        BadExample badExample() {
            return new BadExample();
        }
    }

    public static void main(String [] args) {
        //Install ModuleA which will double install Module C
        Injector injector = Guice.createInjector(new ModuleA());
        BadExample badExample = injector.getInstance(BadExample.class);
        System.out.println("You'll never see this!");
    }

}
