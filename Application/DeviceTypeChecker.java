package main;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class DeviceTypeChecker {

    public static boolean isLaptop() {
        try {
            String os = System.getProperty("os.name").toLowerCase();

            Process process;
            if (os.contains("win")) {
                process = Runtime.getRuntime().exec("wmic path Win32_Battery get BatteryStatus");
            } else if (os.contains("linux")) {
                process = Runtime.getRuntime().exec("ls /sys/class/power_supply/BAT0");
            } else {
                return false; // Unknown OS — assume desktop
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty() && !line.toLowerCase().contains("batterystatus")) {
                        return true; // Battery detected ⇒ likely a laptop
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false; // No battery detected ⇒ likely a desktop
    }
}


