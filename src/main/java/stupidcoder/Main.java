package stupidcoder;

import stupidcoder.simulator.Simulator;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] a) {
        while (true) {
            System.out.print("cs:");
            try {
                var r = new BufferedReader(new InputStreamReader(System.in));
                String line = r.readLine();
                if (line.equals("exit")) {
                    break;
                }
                programmeLoop(new Simulator(), line.split(" "));
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        }
    }

    private static void programmeLoop(Simulator s, String[] args) throws Exception {
        String file = "";
        boolean simulation = false;
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-w" -> setWaitTime(s, args[++i]);
                case "-d" -> setDelay(s, args[++i]);
                case "-f" -> file = args[++i];
                case "-s" -> simulation = true;
                default -> throw new Exception("Unknown argument: " + args[i]);
            }
        }
        if (file.isEmpty()) {
            throw new Exception("No file specified");
        }
        s.run(file, simulation);
    }

    private static void setWaitTime(Simulator s, String arg) {
        s.setWaitTime(Integer.parseInt(arg));
    }

    private static void setDelay(Simulator s, String arg) {
        s.setDelay(Integer.parseInt(arg));
    }
}
