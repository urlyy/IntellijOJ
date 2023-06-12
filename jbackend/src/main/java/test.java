
    class Parent {
        // 父类的属性和方法
    }

    class Child1 extends Parent {
        // 子类1的属性和方法
    }

    class Child2 extends Parent {
        // 子类2的属性和方法
    }




class demo{
    public static void main(String[] args) {
        Parent parent = new Child2();

        if (parent instanceof Child1) {
            System.out.println("Parent is an instance of Child1");
        } else if (parent instanceof Child2) {
            System.out.println("Parent is an instance of Child2");
        } else {
            System.out.println("Parent is not an instance of any known subclass");
        }
    }
}