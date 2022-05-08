package Java.DataClass;

import java.util.HashMap;

public class Province {

    protected HashMap<String, Common> commons = new HashMap<String, Common>();
    protected String name;

    public Province(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new String("provincia: " + name);
    }

    public Common findCommons(String name) {
        return commons.get(name);
    }

    public String getName() {
        return this.name;
    }

    public void add(Common common) {
        commons.put(common.getName(), common);
    }
    
}
