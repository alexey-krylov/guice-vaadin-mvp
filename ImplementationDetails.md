# Implementation details #
## Auto-search for Command/Event handlers ##
Use _@CommandHandlerComponent_ and _@EventHandlerComponent_ annotations to specify which classes should be instantiated as Axon's Command and Event handlers. Instances of this classes will be bound into Singleton scope.

_If you choose option of manual handlers specification than you can skip annotating step._

## Injections ##
Axon-Guice provides you possibilities of the following injections:
  * Injection of CommandBus, EventBus, CommandGateway, UnitOfWork, Repository;
  * Injection into Aggregate Roots;
  * Injection into methods marked with @CommandHandler/@EventHandler;
  * Injection into Command/Event handlers;
  * Injection into Sagas;
  * Resource injection with JSR-250 annotations - @Resource.

## JSR-250 Support ##
Axon-Guice bundled with JSR-250 support based on [Mycila Guice Project](http://code.google.com/p/mycila/wiki/MycilaGuice). Implementation of JSR-250 support can always be disabled or changed by overriding appropriate methods of _AxonGuiceModule_.

Currently there is no default @PreDestroy call point because each project is unique and has different shutdown mechanics.

To call @PreDestroy at shutdown just use this construction:
```
        injector.getInstance(Jsr250Destroyer.class).preDestroy();
```