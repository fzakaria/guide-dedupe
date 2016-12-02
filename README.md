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
