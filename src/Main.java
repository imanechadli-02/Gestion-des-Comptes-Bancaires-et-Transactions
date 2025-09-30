//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import Controller.AuthController;
import Service.AuthService;

public class Main {
    public static void main(String[] var0) {
        AuthService var1 = new AuthService();
        AuthController var2 = new AuthController(var1);
        var2.menuAuth();
    }
}
