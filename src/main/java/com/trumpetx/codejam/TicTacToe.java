package com.trumpetx.codejam;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class TicTacToe {

    static boolean completed = true;
    static BufferedReader r;
    static char[] line;
    static File outputFile;
    //col1, col2, col3, col4, row1, row2, row3, row4, diagonal top left to lower right, diagonal top right to lower left
    static byte[] winners = new byte[]{3, 3, 3, 3, 3, 3, 3, 3, 3, 3};

    public static void main(String[] args) throws Exception {
        File f = new File(args.length > 0 ? args[0] : "test.txt");
        r = new BufferedReader(new FileReader(f));
        outputFile = new File(args.length > 1 ? args[1] : "results.txt");
        outputFile.delete();

        int times = Integer.parseInt(r.readLine());

        testcase:
        for (int i = 0; i < times; i++) {
            for (int row = 0; row < 4; row++) {
                line = r.readLine().toCharArray();
                for (int col = 0; col < 4; col++) {
                    byte val = 0;
                    if (line[col] == 'X') {
                        val = 1;
                    } else if (line[col] == 'O') {
                        val = 2;
                    } else if (line[col] == 'T') {
                        val = 3;
                    } else if (line[col] == '.') {
                        completed = false;
                    }

                    if ((row == 0 && col == 0) || (row == 3 && col == 3) || (row == 2 && col == 2) || (row == 1 && col == 1)) {
                        winners[8] &= val;
                    } else if ((row == 0 && col == 3) || (row == 2 && col == 1) || (row == 1 && col == 2) || (row == 3 && col == 0)) {
                        winners[9] &= val;
                    }

                    winners[col] &= val;
                    winners[row + 4] &= val;
                }
            }
            for (byte possibility : winners) {
                switch (possibility) {
                    case 1:
                        output(new StringBuffer("Case #").append(i + 1).append(": X won"));
                        reset();
                        continue testcase;
                    case 2:
                        output(new StringBuffer("Case #").append(i + 1).append(": O won"));
                        reset();
                        continue testcase;
                }
            }
            if (completed) {
                System.out.println(new StringBuffer("Case #").append(i + 1).append(": Draw"));
            } else {
                System.out.println(new StringBuffer("Case #").append(i + 1).append(": Game has not completed"));
            }
            reset();
        }
    }

    private static void output(StringBuffer out) throws Exception {
        FileUtils.write(outputFile, out.append("\n"), "UTF-8", true);
        System.out.print(out);
    }

    private static void reset() throws Exception {
        winners = new byte[]{3, 3, 3, 3, 3, 3, 3, 3, 3, 3};
        completed = true;
        r.readLine();
    }

}