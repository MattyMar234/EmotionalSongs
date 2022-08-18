package Java.DataClasses;

public class Common {

    protected String name;
    public String [] cap ;

    public Common(String name, String [] cap) {
        this.name = name;
        this.cap = cap;
    }

    @Override
    public String toString() {
        return new String("commune: " + name);
    }

    public boolean testCap(String capToTest) {
        for(String c : cap) {
            if(c.equals(capToTest)) {
                return true;
            }
        }
        return false;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getCap(int index) {
        if(index < cap.length) {
            return cap[index];
        }

        return "00000";
    }

    public void setCap(String newCap, int index) {
        if(index < cap.length) {
            this.cap[index] = newCap;
        }
    }
}
