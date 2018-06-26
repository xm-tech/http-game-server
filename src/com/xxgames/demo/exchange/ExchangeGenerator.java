package com.xxgames.demo.exchange;

import com.xxgames.util.MathUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by PhonePadPC on 2017/9/4.
 */
public class ExchangeGenerator {
    public static void main(String[] args) throws IOException {

        char[] input = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
            'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
            'v', 'w', 'x', 'y', 'z' };

        FileOutputStream fos = new FileOutputStream(new File("D://excode/demo_c001.txt"));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        Set<String> ret = new HashSet<>();
        StringBuilder sb = new StringBuilder();
        StringBuilder out = new StringBuilder();
        int need = 0;
        while (need < 100) {
            sb.setLength(0);
            sb.append("c001-");
            while (sb.length() < 12) {
                sb.append(input[MathUtil.randUnsigned(36)]);
            }
            sb.append(System.getProperty("line.separator"));
            ret.add(sb.toString());
            out.append(sb);
            System.out.println(sb);
            need = ret.size();
        }
        System.out.println(need);
        bw.write(out.toString());
        bw.flush();
        bw.close();
    }
}
