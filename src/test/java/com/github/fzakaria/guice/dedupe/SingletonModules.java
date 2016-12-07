package com.github.fzakaria.guice.dedupe;

import com.google.inject.Provides;

/**
 * A collection of modules that install bindings more than once.
 *
 * ModuleA: binds FakeClassB
 * ModuleB: binds FakeClassB
 * ModuleC: provides FakeClassA
 *
 *           ___ ModuleB --- ModuleC
 * Module A /
 *          \___ ModuleC
 */
public final class SingletonModules {

    /**
     * Class that is used just for test binding
     */
    public static class FakeClassA {

    }

    /**
     * Class that is used just for test binding
     */
    public static class FakeClassB {

    }

    public static class ModuleA extends SingletonModule {

        @Override
        protected void configure() {
            install(new ModuleB());
            install(new ModuleC());
            bind(FakeClassB.class);
        }
    }

    public static class ModuleB extends SingletonModule {

        @Override
        protected void configure() {
            install(new ModuleC());
            bind(FakeClassB.class);
        }
    }

    public static class ModuleC extends SingletonModule {

        @Override
        protected void configure() {
        }

        @Provides
        FakeClassA goodExample() {
            return new FakeClassA();
        }
    }


}
