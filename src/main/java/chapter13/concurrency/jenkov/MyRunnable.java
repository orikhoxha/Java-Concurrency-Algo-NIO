package chapter13.concurrency.jenkov;

public class MyRunnable implements Runnable{


    @Override
    public void run() {
        methodOne();
    }

    public void methodOne() {
        int localVariable1 = 45;
        MySharedObject localVariable2 = MySharedObject.sharedInstance;
        methodTwo();
    }


    public void methodTwo() {
        Integer localVariable1 = 99;
    }
}

class MySharedObject {
    public static final MySharedObject sharedInstance = new MySharedObject();

    public Integer object2 = 22;
    public Integer object3 = 44;

    public long member1 = 12345;
    public long member2 = 67890;

}
