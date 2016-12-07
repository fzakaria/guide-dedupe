# Guice Deduplication

Guice modules allow nesting through the `install` method.
It is useful to have modules that are related/dependent install each other,
to reduce the burden on the consumers of your library and/or application.

However you might have run into this very annoying problem:
```
Error while trying to start the application com.google.inject.CreationException: Unable to create injector, see the following errors: 
   1) A binding to SomeClass was already configured at SomeModule.someClassProvider() 
   (via modules: ModuleA -> SomeModule).
```

The code here shows the *proper* way to solve this problem, along with a broken implementation to demonstrate it.

## Details
 Guice dedupes modules by using equals/hashcode. The problem with the default AbstractModule is that
 it uses the default equals/hashcode which does object reference only. That means multiple new instantiations
 of the same module are actually not equal and do not get deduped.

 If you don't use `@Provides`, you'll find the deduplication works though because Guice will still do deduplication
 of the bindings. The problem is when you introduce `@Provides` cause now two modules provide the same interface
 through a method!
 
 If you'd like to add deduplication to your codebase, simply change your AbstractModule
 to inherit from SingletonModule.
