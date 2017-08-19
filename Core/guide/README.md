#Welcome!
Welcome to visit my project, the simple Basic language Interpreter.

As we know, there are something hard in the School Math Book in China, the Algorithm with Basic.  
The TBeko Project devotes to make it easy. It is a made to measure interpreter for it.

The codes what is in the `tdiant.tbeko.math` package is from the Internet, but I editted a little.

#Why I do it?
Well, in my opinion, it was written by many stupid codes.  

The important code of the Core originated a stake. My friend told to me, can I make a Basic Interpreter with "String.split()" method and char Array? Then he told that if I can and I finished it, he will give me a boxful of Youlemei milky tea. So I did it and I get it......  
A funny story, right? :)  

So, please don't dispute with the codes,for example, "WHY STUPID CODES THE AUTHOR WRITE!!!". Come on, I just make it for fun.

#How can I use?
If you want to run a Basic Program on it, you need to make a `TBekoCore` Object and a `InteractBlock` Object first.  

The `TBekoCore` is the core of the Basic Interpreter system. And the `InteractBlock` can make you interact to the system.  
I make a `DefaultInteractBlock` and it can allow you interact to the system with the Standard Input and Standard Output. (Just write by `System.out` and `System.in`);

So, there is an example:
```
public class JavaTest {
    public static void main(String[] args) {
        String str = "PRINT \"Hello World!\"";
        DefaultInteractBlock dib = new DefaultInteractBlock();
        TBekoCore tbc = new TBekoCore(str,dib);
        tbc.read();
    }
}
```

The interpreter will output the message by `DefaultInteractBlock` to the console : 
```
Hello World!
```
