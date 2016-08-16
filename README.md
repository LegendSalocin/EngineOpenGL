# EngineOpenGL

--


##Start Engine
```java
public class Test extends SimpleCorePlugin {
	
	public static void main(String[] args) {
		Engine.simpleStart(new Test());
	}
	
	@Override
	protected void onEnable() {
	}
	
	
}
```

##Start Engine Advanced
```java
public class Test extends CorePlugin {
	
	public static void main(String[] args) {
		try {
			Engine.start(new Test());
		} catch (EngineException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String getName() {
		return null;
	}
	
	@Override
	public String getVersion() {
		return null;
	}
	
	@Override
	protected void onEnable() {
	}
	
	@Override
	protected void onDisable() {
	}
	
}
```
