package com.github.fzakaria.guice.dedupe;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;

/**
 * Guice can dedupe modules by using equals/hashcode. The problem with the default AbstractModule is that
 * it uses the default equals/hashcode which does object reference only. That means multiple new instantiations
 * (new ModuleC) are actually not equal and do not get deduped!
 *
 * If you don't use @Provides, you'll find the deduplication works though because Guice will still do deduplication
 * of the bindings. The problem is when you introduce @Provides cause now two modules provide the same interface
 * through a method!
 */
public class GoodExample {

    public static abstract class SingletonModule extends AbstractModule {

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            return true;
        }

        @Override
        public int hashCode() {
            return SingletonModule.class.hashCode();
        }

    }

    public static class ModuleA extends SingletonModule {

        @Override
        protected void configure() {
            install(new ModuleB());
            install(new ModuleC());
        }
    }

    public static class ModuleB extends SingletonModule {

        @Override
        protected void configure() {
            install(new ModuleC());
        }
    }

    public static class ModuleC extends SingletonModule {

        @Override
        protected void configure() {
        }

        @Provides
        GoodExample goodExample() {
            return new GoodExample();
        }
    }

    public static void main(String [] args) {
        //Install ModuleA which will double install Module C
        Injector injector = Guice.createInjector(new ModuleA());
        GoodExample goodExample = injector.getInstance(GoodExample.class);
        //To further prove the point, double install ModuleA
        injector = Guice.createInjector(new ModuleA(), new ModuleA());
        goodExample = injector.getInstance(GoodExample.class);
        System.out.println("GoodExample!");
    }

}
