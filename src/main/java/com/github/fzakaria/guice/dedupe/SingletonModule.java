package com.github.fzakaria.guice.dedupe;

import com.google.inject.AbstractModule;

/**
 * Guice dedupes modules by using equals/hashcode. The problem with the default AbstractModule is that
 * it uses the default equals/hashcode which does object reference only. That means multiple new instantiations
 * of the same module are actually not equal and do not get deduped.
 *
 * If you don't use @Provides, you'll find the deduplication works though because Guice will still do deduplication
 * of the bindings. The problem is when you introduce @Provides cause now two modules provide the same interface
 * through a method!
 *
 * If you'd like to add deduplication to your codebase, simply change your {@link AbstractModule} to inherit from
 * {@link SingletonModule}
 */
public abstract class SingletonModule extends AbstractModule {

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
