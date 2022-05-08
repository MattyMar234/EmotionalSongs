package Java.DataClass;

public class Common {

    protected String name;
    protected int [] cap ;

    public Common(String name, int [] cap) {
        this.name = name;
        this.cap = cap;
    }

    @Override
    public String toString() {
        return new String("commune: " + name);
    }

    public boolean testCap(int capToTest) {
        for(Integer c : cap) {
            if(c == capToTest) {
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

    public int getCap(int index) {
        if(index < cap.length) {
            return cap[index];
        }

        return -1;
    }

    public void setCap(int newCap, int index) {
        if(index < cap.length) {
            this.cap[index] = newCap;
        }
    }
}
