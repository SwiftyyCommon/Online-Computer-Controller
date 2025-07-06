package main;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;

public class DeviceInfo {

    public static String getSerialNumber() {
        String serial = "UNKNOWN";
        try {
            Process process = Runtime.getRuntime().exec("wmic bios get serialnumber");
            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.equalsIgnoreCase("serialnumber") && !line.isEmpty()) {
                    serial = line;
                    break;
                }
            }

            reader.close();
        } catch (Exception e) {
            serial = "ERROR: " + e.getMessage();
        }

        return serial;
    }
    
    public static String getProcessorName() {
        String processor = "UNKNOWN";
        try {
            Process process = Runtime.getRuntime().exec("wmic cpu get Name");
            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.equalsIgnoreCase("name") && !line.isEmpty()) {
                    processor = line;
                    break;
                }
            }

            reader.close();
        } catch (Exception e) {
            processor = "ERROR: " + e.getMessage();
        }

        return processor;
    }
    public static int getMemorySizeGB() {
        try {
            OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
            long totalBytes = osBean.getTotalPhysicalMemorySize();
            return (int) Math.round((double) totalBytes / (1024 * 1024 * 1024));  // Bytes → GB (rounded)
        } catch (Exception e) {
            return -1;  // In case of error
        }
    }
    public static String getManufacturer() {
        String manufacturer = "UNKNOWN";
        try {
            Process process = Runtime.getRuntime().exec("wmic computersystem get manufacturer");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.equalsIgnoreCase("manufacturer") && !line.isEmpty()) {
                    manufacturer = line;
                    break;
                }
            }
            reader.close();
        } catch (Exception e) {
            manufacturer = "ERROR: " + e.getMessage();
        }
        return manufacturer;
    }

    public static String getModel() {
        String model = "UNKNOWN";
        try {
            Process process = Runtime.getRuntime().exec("wmic computersystem get model");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.equalsIgnoreCase("model") && !line.isEmpty()) {
                    model = line;
                    break;
                }
            }
            reader.close();
        } catch (Exception e) {
            model = "ERROR: " + e.getMessage();
        }
        return model;
    }
}

