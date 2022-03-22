package com.gb.kotlin_1728_2_1.lesson4;

import android.util.Log;

public class JavaLambda {
    public  void main() {
        Operation op = Integer::sum;
        int z = op.calculate( 200,5);
        Log.d("mylog",z + " it here");
    }
}

interface Operation{
    int calculate(int x, int y);
}
