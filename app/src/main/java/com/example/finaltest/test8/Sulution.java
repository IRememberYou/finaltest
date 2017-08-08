package com.example.finaltest.test8;

/**
 * Created by pinan on 2017/7/25.
 * eg：给定一个正整数n，求出 0 到 n 中有几个数满足其二进制表示 不 包 含 连 续 1。
 * 1  <= n <= 10^9.
 */

public class Sulution {
    public int findIntergers(int num) {
        if (num < 2) {
            return num + 1;
        }
        
        StringBuilder str = new StringBuilder(Integer.toBinaryString(num)).reverse();
        
        int k = str.length();
        int[] f = new int[k];
        f[0] = 1;
        f[1] = 2;
        for (int i = 0; i < k; i++) {
            f[i] = f[i - 1] + f[i - 2];
        }
        
        int ans = 0;
        for (int i = k - 1; i >= 0; i--) {
            if (str.charAt(i) == '1') {
                ans = f[i];
                if (i < k - 1 && str.charAt(i + 1) == '1') {
                    return ans;
                }
            }
        }
        ans++;
        return ans;
    }
}
