import java.util.Scanner;

class MyCalculator {
    private String[] expression;
    private int manyItems;
    private static int sizeOfExpression = 0;

    MyCalculator(int initialCapacity) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("initialCapacity too small " + initialCapacity);
        manyItems = 0;
        expression = new String[initialCapacity];
    }

    void ensureCapacity(int minimumCapacity) {
        String biggerArray[];

        if (expression.length < minimumCapacity) {
            biggerArray = new String[minimumCapacity];
            System.arraycopy(expression, 0, biggerArray, 0, manyItems);
            expression = biggerArray;
        }
    }

    int getCapacity() {
        return expression.length;
    }

    boolean isEmpty() {
        return (manyItems == 0);
    }

    String peek() {
        if (manyItems == 0)
            throw new java.util.EmptyStackException();
        return expression[manyItems - 1];
    }

    String pop() {
        if (manyItems == 0)
            throw new java.util.EmptyStackException();
        return expression[--manyItems];
    }

    void push(String item) {
        if (manyItems == expression.length) {
            ensureCapacity(manyItems * 2 + 1);
        }
        expression[manyItems] = item;
        manyItems++;
    }

    int size() {
        return manyItems;
    }

    static String[] convertInfix(String[] inputArray)
    {
        System.out.print("Postfix>> ");
        MyCalculator infix = new MyCalculator(inputArray.length);
        sizeOfExpression =0;
        String[] postfixArray = new String[inputArray.length];
        int i;
        for (i = 0; i < inputArray.length; i++)
        {
            try
            {

                System.out.print(Double.parseDouble(inputArray[i]) + " ");
                postfixArray[sizeOfExpression]=inputArray[i];
                sizeOfExpression++;

            }
            catch (NumberFormatException e)
            {
                switch (inputArray[i])
                {
                    case "(":
                        infix.push("(");
                        break;
                    case "*":
                        try {
                            if (infix.expression[infix.manyItems - 1].equals("*") || infix.expression[infix.manyItems - 1].equals("/")) {
                                String a = infix.pop();
                                System.out.print(a + " ");
                                postfixArray[sizeOfExpression]=a;
                                sizeOfExpression++;
                            }
                        }
                        catch (ArrayIndexOutOfBoundsException e2)
                        {}
                        infix.push("*");
                        break;
                    case "/":
                        try {
                            if (infix.expression[infix.manyItems - 1].equals("*") || infix.expression[infix.manyItems - 1].equals("/")) {
                                String a = infix.pop();
                                System.out.print(a + " ");
                                postfixArray[sizeOfExpression]=a;
                                sizeOfExpression++;
                            }
                        }
                        catch (ArrayIndexOutOfBoundsException e2)
                        {}
                        infix.push("/");
                        break;
                    case "+":
                        try {
                            if (infix.expression[infix.manyItems - 1].equals("*") || infix.expression[infix.manyItems - 1].equals("/")) {
                                String a = infix.pop();
                                System.out.print(a + " ");
                                postfixArray[sizeOfExpression]=a;
                                sizeOfExpression++;
                            }
                            if (infix.expression[infix.manyItems - 1].equals("+") || infix.expression[infix.manyItems - 1].equals("-")) {
                                String a = infix.pop();
                                System.out.print(a + " ");
                                postfixArray[sizeOfExpression]=a;
                                sizeOfExpression++;
                            }
                        }
                        catch (ArrayIndexOutOfBoundsException e2)
                        {}
                        infix.push("+");
                        break;
                    case "-":
                        try {
                            if (infix.expression[infix.manyItems - 1].equals("*") || infix.expression[infix.manyItems - 1].equals("/")) {
                                String a = infix.pop();
                                System.out.print(a + " ");
                                postfixArray[sizeOfExpression]=a;
                                sizeOfExpression++;
                            }
                            if (infix.expression[infix.manyItems - 1].equals("+") || infix.expression[infix.manyItems - 1].equals("-")) {
                                String a = infix.pop();
                                System.out.print(a + " ");
                                postfixArray[sizeOfExpression]=a;
                                sizeOfExpression++;
                            }
                        }
                        catch (ArrayIndexOutOfBoundsException e2)
                        {}
                        infix.push("-");
                        break;
                    case ")":
                        while(infix.peek()!="(")
                        {
                            String a = infix.pop();
                            System.out.print(a + " ");
                            postfixArray[sizeOfExpression]=a;
                            sizeOfExpression++;
                        }
                        infix.pop();
                        break;
                }
            }
        }
        while(infix.manyItems>0)
        {
            if(infix.peek()=="(")
                throw new IllegalArgumentException("잘못된 수식입니다. 프로그램을 종료합니다.");
            if(infix.manyItems>2)
                throw new IllegalArgumentException("잘못된 수식입니다. 프로그램을 종료합니다.");
            String a = infix.pop();
            System.out.print(a + " ");
            postfixArray[sizeOfExpression]=a;
            sizeOfExpression++;
        }
        System.out.println("");

        return postfixArray;
    }

    static double calculatePostfix(String[] postfixArray)
    {
        String[] array =  new String[sizeOfExpression];
        MyCalculator postfix = new MyCalculator(sizeOfExpression);
        System.arraycopy(postfixArray, 0, array, 0, sizeOfExpression);

        double num1, num2;
        int i;
        for(i=0; i<array.length; i++)
        {
            try
            {
                Double.parseDouble(array[i]);
                postfix.push(array[i]);
            }
            catch (NumberFormatException e)
            {
                num2=Double.parseDouble(postfix.pop());
                num1=Double.parseDouble(postfix.pop());
                switch (array[i]){
                    case "+":
                        postfix.push(Double.toString(num1+num2));
                        break;
                    case "-":
                        postfix.push(Double.toString(num1-num2));
                        break;
                    case "*":
                        postfix.push(Double.toString(num1*num2));
                        break;
                    case "/":
                        postfix.push(Double.toString(num1/num2));
                        if(num2==0.0)
                            throw new ArithmeticException("0으로 나눌 수 없음. 프로그램을 종료합니다.");
                        break;
                }
            }
        }
        if(postfix.manyItems>1)
            throw new IllegalArgumentException("잘못된 수식입니다.");
        return Double.parseDouble(postfix.pop());
    }


    public static void main(String[] args) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>My Calculator<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        while(true)
        {
            System.out.print("Infix로 표현된 수식을 입력해 주세요.(각 요소들은 띄어쓰기를 해주세요.)(종료를 입력하면 종료됩니다.)>> ");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            String[] postfixArray;
            if(input.equals("종료"))
            {
                break;
            }
            postfixArray=MyCalculator.convertInfix(input.split(" "));
            System.out.println(calculatePostfix(postfixArray));
        }
    }
}
