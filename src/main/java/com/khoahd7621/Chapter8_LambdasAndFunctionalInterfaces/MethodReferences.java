package com.khoahd7621.Chapter8_LambdasAndFunctionalInterfaces;

public class MethodReferences {
     class StaticMethod {
        interface Converter {
            long round(double num);
        }

        public static void main(String[] args) {
            Converter c = Math::round;
            System.out.println(c.round(3.5));
        }
    }

    class InstanceMethod {
        interface StringStart {
            boolean beginningCheck(String prefix);
        }

        interface StringChecker {
            boolean check();
        }

        public static void main(String[] args) {
            var str = "Zoo";
            StringStart methodRef = str::startsWith;
            StringStart lambda = s -> str.startsWith(s);

            System.out.println(methodRef.beginningCheck("A")); // false

            StringChecker methodRef1 = str::isEmpty;
            StringChecker lambda1 = () -> str.isEmpty();

            System.out.println(methodRef1.check()); // false
        }
    }

    class InstanceMethodOnParameter {
        interface StringParameterChecker {
            boolean check(String text);
        }

        interface StringTwoParameterChecker {
            boolean check(String text, String prefix);
        }

        public static void main(String[] args) {
            StringParameterChecker methodRef = String::isEmpty;
            StringParameterChecker lambda = s -> s.isEmpty();

            System.out.println(methodRef.check("Zoo")); // false

            StringTwoParameterChecker methodRef1 = String::startsWith;
            StringTwoParameterChecker lambda1 = (s, p) -> s.startsWith(p);

            System.out.println(methodRef1.check("Zoo", "Z")); // true
        }
    }

    class Constructors {
        interface EmptyStringCreator {
            String create();
        }

        interface StringCopier {
            String copy(String value);
        }

        public static void main(String[] args) {
            EmptyStringCreator methodRef = String::new;
            EmptyStringCreator lambda = () -> new String();

            var myString = methodRef.create();
            System.out.println(myString.equals("Snake")); // false

            StringCopier methodRef1 = String::new;
            StringCopier lambda1 = s -> new String(s);

            var myString1 = methodRef1.copy("Zebra");
            System.out.println(myString1.equals("Zebra")); // true
        }
    }
}
