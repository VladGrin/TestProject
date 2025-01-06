package org.vg.test.config;

public class Singl {
    private static Singl singl = null;
    private Singl() {}
    public static Singl getInstance() {
        if (singl == null) {
            singl = new Singl();
        }
        return singl;
    }
}
class Demo {
    public static void main(String[] args) {
        Singl singl1 = Singl.getInstance();
        Singl singl2 = Singl.getInstance();
        Singl singl3 = Singl.getInstance();
        Singl singl4 = Singl.getInstance();
        System.out.println(singl1.hashCode());
        System.out.println(singl2.hashCode());
        System.out.println(singl3.hashCode());
        System.out.println(singl4.hashCode());
    }
}