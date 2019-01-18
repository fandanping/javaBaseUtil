package 反射;
interface  CarFactoryImp {
    public void sellCar();
}
class CarFactory implements CarFactoryImp {
    @Override
    public void sellCar() {
        System.out.println("sell car");
    }
}
class CarProxy implements  CarFactoryImp{
   //接收保存目标对象
    private CarFactoryImp target;
    @Override
    public void sellCar() {
        if(target == null){
            target = new CarFactory();
        }
        target.sellCar();
    }
}
public class StaticProxy {
    public static void main(String[] args){
        CarFactoryImp imp = new CarProxy();
        imp.sellCar();
    }
}
